package baby.creed.mixins.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = { NetHandlerPlayClient.class }, priority = 1)
public abstract class MixinNetHandlerPlayClient {
    @Shadow
    private Minecraft gameController;
    @Shadow
    private WorldClient clientWorldController;
    @Shadow
    private boolean doneLoadingTerrain;
    @Shadow
    @Final
    private NetworkManager netManager;

    /*@Inject(method = { "handleExplosion" }, at = { @At("HEAD") }, cancellable = true)
    private void handleExplosion(final S27PacketExplosion packetIn, final CallbackInfo ci) {
        if (Creed.velocity.isToggled() || Creed.speed.isToggled()) {
            PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler) Creed.mc.getNetHandler(), (IThreadListener)this.gameController);
            final Explosion explosion = new Explosion((World)this.gameController.theWorld, (Entity)null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
            explosion.doExplosionB(true);
            final boolean shouldTakeKB = Creed.velocity.skyblockKB.isEnabled() && (Minecraft.getMinecraft().thePlayer.isInLava() || SkyblockUtils.getDisplayName(Minecraft.getMinecraft().thePlayer.getHeldItem()).contains("Bonzo's Staff") || SkyblockUtils.getDisplayName(Minecraft.getMinecraft().thePlayer.getHeldItem()).contains("Jerry-chine Gun"));
            if ((shouldTakeKB || Creed.velocity.hModifier.getValue() != 0.0 || Creed.velocity.vModifier.getValue() != 0.0) && !Creed.speed.isToggled()) {
                final EntityPlayerSP thePlayer = this.gameController.thePlayer;
                thePlayer.motionX += packetIn.func_149149_c() * (shouldTakeKB ? 1.0 : Creed.velocity.hModifier.getValue());
                final EntityPlayerSP thePlayer2 = this.gameController.thePlayer;
                thePlayer2.motionY += packetIn.func_149144_d() * (shouldTakeKB ? 1.0 : Creed.velocity.vModifier.getValue());
                final EntityPlayerSP thePlayer3 = this.gameController.thePlayer;
                thePlayer3.motionZ += packetIn.func_149147_e() * (shouldTakeKB ? 1.0 : Creed.velocity.hModifier.getValue());
            }
            ci.cancel();
        }
    }

    @Inject(method = { "handleEntityVelocity" }, at = { @At("HEAD") }, cancellable = true)
    public void handleEntityVelocity(final S12PacketEntityVelocity packetIn, final CallbackInfo ci) {
        if (Creed.velocity.isToggled() || Creed.speed.isToggled()) {
            PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)Creed.mc.getNetHandler(), (IThreadListener)this.gameController);
            final Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
            if (entity != null) {
                if (entity.equals(Creed.mc.thePlayer)) {
                    final boolean shouldTakeKB = Creed.velocity.skyblockKB.isEnabled() && (Minecraft.getMinecraft().thePlayer.isInLava() || SkyblockUtils.getDisplayName(Minecraft.getMinecraft().thePlayer.getHeldItem()).contains("Bonzo's Staff") || SkyblockUtils.getDisplayName(Minecraft.getMinecraft().thePlayer.getHeldItem()).contains("Jerry-chine Gun"));
                    if ((shouldTakeKB || Creed.velocity.hModifier.getValue() != 0.0 || Creed.velocity.vModifier.getValue() != 0.0) && !Creed.speed.isToggled()) {
                        entity.setVelocity(packetIn.getMotionX() * (shouldTakeKB ? 1.0 : Creed.velocity.hModifier.getValue()) / 8000.0, packetIn.getMotionY() * (shouldTakeKB ? 1.0 : Creed.velocity.vModifier.getValue()) / 8000.0, packetIn.getMotionZ() * (shouldTakeKB ? 1.0 : Creed.velocity.hModifier.getValue()) / 8000.0);
                    }
                }
                else {
                    entity.setVelocity(packetIn.getMotionX() / 8000.0, packetIn.getMotionY() / 8000.0, packetIn.getMotionZ() / 8000.0);
                }
            }
            ci.cancel();
        }
    }*/
}
