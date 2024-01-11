package baby.creed.modules.player;

import baby.creed.util.SkyblockUtils;
import baby.creed.Creed;
import baby.creed.events.PostGuiOpenEvent;
import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;
import baby.creed.settings.NumberSetting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GuiMove extends Module
{
    private BooleanSetting rotate;
    private BooleanSetting drag;
    private BooleanSetting hideTerminalGui;
    private NumberSetting sensivity;
    public static KeyBinding[] binds;

    public GuiMove() {
        super("InvMove", Category.PLAYER);
        this.rotate = new BooleanSetting("Rotate", false);
        this.drag = new BooleanSetting("Alt drag", true) {
            @Override
            public boolean isHidden() {
                return !GuiMove.this.rotate.isEnabled();
            }
        };
        this.hideTerminalGui = new BooleanSetting("Hide terminals", false);
        this.sensivity = new NumberSetting("Sensivity", 1.5, 0.1, 3.0, 0.01, aBoolean -> !this.rotate.isEnabled());
        this.addSettings(this.hideTerminalGui, this.rotate, this.sensivity, this.drag);

        this.flagRisky();
    }

    @Override
    public boolean isToggled() {
        return super.isToggled();
    }

    @Override
    public void assign()
    {
        Creed.guiMove = this;
    }

    @Override
    public void onDisable() {
        if (Creed.mc.currentScreen != null) {
            for (final KeyBinding bind : GuiMove.binds) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), false);
            }
        }
    }

    private void updateBinds()
    {
        binds = new KeyBinding[] { Creed.mc.gameSettings.keyBindSneak, Creed.mc.gameSettings.keyBindJump, Creed.mc.gameSettings.keyBindSprint, Creed.mc.gameSettings.keyBindForward, Creed.mc.gameSettings.keyBindBack, Creed.mc.gameSettings.keyBindLeft, Creed.mc.gameSettings.keyBindRight };
    }

    @SubscribeEvent
    public void onGui(final PostGuiOpenEvent event) {
        if (binds == null) updateBinds();
        if (!(event.gui instanceof GuiChat) && this.isToggled()) {
            for (final KeyBinding bind : GuiMove.binds) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), GameSettings.isKeyDown(bind));
            }
        }
    }

    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (Creed.mc.currentScreen != null && !(Creed.mc.currentScreen instanceof GuiChat) && this.isToggled()) {
            if (binds == null) updateBinds();
            for (final KeyBinding bind : GuiMove.binds) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), GameSettings.isKeyDown(bind));
            }
            if ((Creed.mc.currentScreen instanceof GuiContainer || Creed.mc.currentScreen instanceof ICrafting) && this.rotate.isEnabled()) {
                Creed.mc.mouseHelper.mouseXYChange();
                float f = Creed.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
                f *= (float)this.sensivity.getValue();
                final float f2 = f * f * f * 8.0f;
                final float f3 = Creed.mc.mouseHelper.deltaX * f2;
                final float f4 = Creed.mc.mouseHelper.deltaY * f2;
                int i = 1;
                if (Creed.mc.gameSettings.invertMouse) {
                    i = -1;
                }
                if (Keyboard.isKeyDown(56) && Mouse.isButtonDown(2) && this.drag.isEnabled()) {
                    Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 6);
                    Creed.mc.setIngameNotInFocus();
                    Mouse.setGrabbed(false);
                }
                Creed.mc.thePlayer.setAngles(f3, f4 * i);
            }
        }
    }

    public boolean shouldHideGui(final ContainerChest chest) {
        return SkyblockUtils.isTerminal(chest.getLowerChestInventory().getName()) && this.isToggled() && this.hideTerminalGui.isEnabled();
    }
}

