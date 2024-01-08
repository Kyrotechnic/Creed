package net.kore.mixins;

import net.kore.Kore;
import net.kore.events.RightClickEvent;
import net.kore.modules.combat.Aura;
import net.kore.modules.render.ServerRotations;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow private Entity renderViewEntity;

    @Shadow private int rightClickDelayTimer;

    @Inject(method = "startGame", at = @At("TAIL"), cancellable = false)
    public void startGame(CallbackInfo ci)
    {
        Kore.mc = Minecraft.getMinecraft();
    }

    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V") })
    public void keyPresses(final CallbackInfo ci) {
        final int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey();
        final char aChar = Keyboard.getEventCharacter();
        if (Keyboard.getEventKeyState()) {
            if (Kore.mc.currentScreen == null) {
                Kore.handleKey(k);
            }
        }
    }


    @Inject(method = { "getRenderViewEntity" }, at = { @At("HEAD") })
    public void getRenderViewEntity(final CallbackInfoReturnable<Entity> cir) {
        if (!ServerRotations.getInstance().isToggled() || this.renderViewEntity == null || this.renderViewEntity != Kore.mc.thePlayer) {
            return;
        }
        if (!ServerRotations.getInstance().onlyKillAura.isEnabled() || Aura.target != null) {
            ((EntityLivingBase)this.renderViewEntity).rotationYawHead = ((PlayerSPAccessor)this.renderViewEntity).getLastReportedYaw();
            ((EntityLivingBase)this.renderViewEntity).renderYawOffset = ((PlayerSPAccessor)this.renderViewEntity).getLastReportedYaw();
        }
    }

    @Inject(method = "rightClickMouse", at = @At("HEAD"), cancellable = true)
    public void rightClick(CallbackInfo ci)
    {
        if (MinecraftForge.EVENT_BUS.post(new RightClickEvent()))
        {
            ci.cancel();
        }
    }

    @Inject(method = { "rightClickMouse" }, at = { @At("RETURN") }, cancellable = true)
    public void onRightClickPost(final CallbackInfo callbackInfo) {
        if (Kore.fastPlace.isToggled()) {
            this.rightClickDelayTimer = (int)Kore.fastPlace.placeDelay.getValue();
        }
    }
}
