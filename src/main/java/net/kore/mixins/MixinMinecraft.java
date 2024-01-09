package net.kore.mixins;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.kore.Kore;
import net.kore.events.RightClickEvent;
import net.kore.modules.combat.Aura;
import net.kore.modules.render.ServerRotations;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.OpenGLException;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow private Entity renderViewEntity;

    @Shadow private int rightClickDelayTimer;

    @Shadow public GameSettings gameSettings;

    @Shadow @Final public File mcDataDir;

    @Shadow @Final private List<IResourcePack> defaultResourcePacks;

    @Shadow @Final public DefaultResourcePack mcDefaultResourcePack;

    @Shadow protected abstract void startTimerHackThread();

    @Shadow public int displayWidth;

    @Shadow public int displayHeight;

    @Shadow @Final private static Logger logger;

    @Shadow protected abstract void setWindowIcon();

    @Shadow protected abstract void setInitialDisplayMode() throws LWJGLException;

    @Shadow protected abstract void createDisplay() throws LWJGLException;

    @Shadow private Framebuffer framebufferMc;

    @Shadow protected abstract void registerMetadataSerializers();

    @Shadow private ResourcePackRepository mcResourcePackRepository;

    @Shadow @Final private File fileResourcepacks;

    @Shadow private IReloadableResourceManager mcResourceManager;

    @Shadow private LanguageManager mcLanguageManager;

    @Shadow public abstract void refreshResources();

    @Shadow public TextureManager renderEngine;

    @Shadow public abstract void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException;

    @Shadow protected abstract void initStream();

    @Shadow private SkinManager skinManager;

    @Shadow private ISaveFormat saveLoader;

    @Shadow private SoundHandler mcSoundHandler;

    @Shadow private MusicTicker mcMusicTicker;

    @Shadow public FontRenderer fontRendererObj;

    @Shadow @Final private File fileAssets;

    @Shadow @Final private MinecraftSessionService sessionService;

    @Shadow @Final private IMetadataSerializer metadataSerializer_;

    @Shadow public abstract boolean isUnicode();

    @Shadow public FontRenderer standardGalacticFontRenderer;

    @Shadow public MouseHelper mouseHelper;

    @Shadow protected abstract void checkGLError(String message);

    @Shadow private TextureMap textureMapBlocks;

    @Shadow private ModelManager modelManager;

    @Shadow private RenderItem renderItem;

    @Shadow private RenderManager renderManager;

    @Shadow private ItemRenderer itemRenderer;

    @Shadow public EntityRenderer entityRenderer;

    @Shadow private BlockRendererDispatcher blockRenderDispatcher;

    @Shadow public RenderGlobal renderGlobal;

    @Shadow public GuiAchievement guiAchievement;

    @Shadow public EffectRenderer effectRenderer;

    @Shadow public WorldClient theWorld;

    @Shadow private String serverName;

    @Shadow public abstract void displayGuiScreen(GuiScreen guiScreenIn);

    @Shadow private int serverPort;

    @Shadow public abstract void toggleFullscreen();

    @Shadow private boolean fullscreen;

    @Shadow private ResourceLocation mojangLogo;

    @Shadow public LoadingScreenRenderer loadingScreen;

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
