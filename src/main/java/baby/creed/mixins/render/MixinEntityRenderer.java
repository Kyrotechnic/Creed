package baby.creed.mixins.render;

import baby.creed.Creed;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer
{
    @Shadow
    private float thirdPersonDistanceTemp;
    @Shadow
    private float thirdPersonDistance;

    @Redirect(method = { "setupFog" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
    public boolean removeBlindness(final EntityLivingBase instance, final Potion potionIn) {
        return false;
    }

    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void hurtCam(final float entitylivingbase, final CallbackInfo ci) {
        /*if (Creed.camera.noHurtCam.isEnabled() && Creed.camera.isToggled()) {
            ci.cancel();
        }*/
    }

    /*@Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistanceTemp:F"))
    public float thirdPersonDistanceTemp(final EntityRenderer instance) {
        return (Creed.camera.isToggled() && !Creed.camera.smoothF5.isEnabled()) ? ((float)Creed.camera.cameraDistance.getValue()) : this.thirdPersonDistanceTemp;
    }

    @Redirect(method = { "orientCamera" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;thirdPersonDistance:F"))
    public float thirdPersonDistance(final EntityRenderer instance) {
        return (Creed.camera.isToggled() && !Creed.camera.smoothF5.isEnabled()) ? ((float)Creed.camera.cameraDistance.getValue()) : this.thirdPersonDistance;
    }

    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"))
    public double onCamera(final Vec3 instance, final Vec3 vec) {
        return (Creed.camera.isToggled() && Creed.camera.cameraClip.isEnabled()) ? Creed.camera.cameraDistance.getValue() : instance.distanceTo(vec);
    }

    @Inject(method = { "updateRenderer" }, at = { @At("RETURN") })
    public void onUpdate(final CallbackInfo ci) {
        if (Creed.camera.isToggled() && Creed.camera.smoothF5.isEnabled()) {
            if (Creed.mc.gameSettings.thirdPersonView > 0) {
                this.thirdPersonDistance = (float) MathUtil.clamp(this.thirdPersonDistance + Creed.camera.speed.getValue(), Creed.camera.cameraDistance.getValue(), 0.0);
            }
            else {
                final float n = (float)Creed.camera.startSize.getValue();
                this.thirdPersonDistanceTemp = n;
                this.thirdPersonDistance = n;
            }
        }
    }*/

    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getBlockReachDistance()F"))
    private float getBlockReachDistance(final PlayerControllerMP instance) {
        return Creed.reach.isToggled() ? ((float)Creed.reach.blockReach.getValue()) : Creed.mc.playerController.getBlockReachDistance();
    }

    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D", ordinal = 2))
    private double distanceTo(final Vec3 instance, final Vec3 vec) {
        return (Creed.reach.isToggled() && instance.distanceTo(vec) <= Creed.reach.reach.getValue()) ? 0.0 : instance.distanceTo(vec);
    }
}
