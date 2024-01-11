package baby.creed.modules.render;

import baby.creed.util.render.RenderUtils;
import baby.creed.Creed;
import baby.creed.events.PacketSentEvent;
import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;
import baby.creed.settings.NumberSetting;
import baby.creed.util.MovementUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FreeCam extends Module
{
    private EntityOtherPlayerMP playerEntity;
    public NumberSetting speed;
    public BooleanSetting tracer;

    public FreeCam() {
        super("FreeCam", Category.RENDER);
        this.speed = new NumberSetting("Speed", 3.0, 0.1, 5.0, 0.1);
        this.tracer = new BooleanSetting("Show tracer", false);
        this.addSettings(this.speed, this.tracer);
    }

    @Override
    public void onEnable() {
        if (Creed.mc.theWorld != null) {
            (this.playerEntity = new EntityOtherPlayerMP(Creed.mc.theWorld, Creed.mc.thePlayer.getGameProfile())).copyLocationAndAnglesFrom(Creed.mc.thePlayer);
            this.playerEntity.onGround = Creed.mc.thePlayer.onGround;
            Creed.mc.theWorld.addEntityToWorld(-2137,this.playerEntity);
        }
    }

    @Override
    public void assign()
    {
        Creed.freeCam = this;
    }

    @Override
    public void onDisable() {
        if (Creed.mc.thePlayer == null || Creed.mc.theWorld == null || this.playerEntity == null) {
            return;
        }
        Creed.mc.thePlayer.noClip = false;
        Creed.mc.thePlayer.setPosition(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ);
        Creed.mc.theWorld.removeEntityFromWorld(-2137);
        this.playerEntity = null;
        Creed.mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
    }

    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (this.isToggled()) {
            Creed.mc.thePlayer.noClip = true;
            Creed.mc.thePlayer.fallDistance = 0.0f;
            Creed.mc.thePlayer.onGround = false;
            Creed.mc.thePlayer.capabilities.isFlying = false;
            Creed.mc.thePlayer.motionY = 0.0;
            if (!MovementUtils.isMoving()) {
                Creed.mc.thePlayer.motionZ = 0.0;
                Creed.mc.thePlayer.motionX = 0.0;
            }
            final double speed = this.speed.getValue() * 0.1;
            Creed.mc.thePlayer.jumpMovementFactor = (float)speed;
            if (Creed.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP thePlayer = Creed.mc.thePlayer;
                thePlayer.motionY += speed * 3.0;
            }
            if (Creed.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP thePlayer2 = Creed.mc.thePlayer;
                thePlayer2.motionY -= speed * 3.0;
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (this.isToggled() && this.playerEntity != null && this.tracer.isEnabled()) {
            RenderUtils.tracerLine(this.playerEntity, event.partialTicks, 1.0f, Creed.themeManager.getSecondaryColor());
        }
    }

    @SubscribeEvent
    public void onWorldChange(final WorldEvent.Load event) {
        if (this.isToggled()) {
            this.toggle();
        }
    }

    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (this.isToggled() && event.packet instanceof C03PacketPlayer) {
            event.setCanceled(true);
        }
    }
}

