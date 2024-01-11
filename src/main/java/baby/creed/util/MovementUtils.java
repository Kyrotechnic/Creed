package baby.creed.util;

import baby.creed.events.MoveEvent;
import baby.creed.Creed;
import baby.creed.modules.combat.Aura;

public class MovementUtils
{
    public static MilliTimer strafeTimer;

    public static float getSpeed() {
        return (float)Math.sqrt(Creed.mc.thePlayer.motionX * Creed.mc.thePlayer.motionX + Creed.mc.thePlayer.motionZ * Creed.mc.thePlayer.motionZ);
    }

    public static float getSpeed(final double x, final double z) {
        return (float)Math.sqrt(x * x + z * z);
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static boolean isMoving() {
        return Creed.mc.thePlayer.moveForward != 0.0f || Creed.mc.thePlayer.moveStrafing != 0.0f;
    }

    public static boolean hasMotion() {
        return Creed.mc.thePlayer.motionX != 0.0 && Creed.mc.thePlayer.motionZ != 0.0 && Creed.mc.thePlayer.motionY != 0.0;
    }

    public static boolean isOnGround(final double height) {
        return !Creed.mc.theWorld.getCollidingBoundingBoxes(Creed.mc.thePlayer, Creed.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }

    public static void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        Creed.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        Creed.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        MovementUtils.strafeTimer.reset();
    }

    public static void strafe(final float speed, final float yaw) {
        if (!isMoving() || !MovementUtils.strafeTimer.hasTimePassed(150L)) {
            return;
        }
        Creed.mc.thePlayer.motionX = -Math.sin(Math.toRadians(yaw)) * speed;
        Creed.mc.thePlayer.motionZ = Math.cos(Math.toRadians(yaw)) * speed;
        MovementUtils.strafeTimer.reset();
    }

    public static void forward(final double length) {
        final double yaw = Math.toRadians(Creed.mc.thePlayer.rotationYaw);
        Creed.mc.thePlayer.setPosition(Creed.mc.thePlayer.posX + -Math.sin(yaw) * length, Creed.mc.thePlayer.posY, Creed.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }

    public static double getDirection() {
        return Math.toRadians(getYaw());
    }

    public static void setMotion(final MoveEvent em, final double speed) {
        double forward = Creed.mc.thePlayer.movementInput.moveForward;
        double strafe = Creed.mc.thePlayer.movementInput.moveStrafe;
        float yaw = ((Aura.target != null && Creed.aura.movementFix.isEnabled())) ? RotationUtils.getRotations(Aura.target).getYaw() : Creed.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Creed.mc.thePlayer.motionX = 0.0;
            Creed.mc.thePlayer.motionZ = 0.0;
            if (em != null) {
                em.setX(0.0);
                em.setZ(0.0);
            }
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            Creed.mc.thePlayer.motionX = forward * speed * cos + strafe * speed * sin;
            Creed.mc.thePlayer.motionZ = forward * speed * sin - strafe * speed * cos;
            if (em != null) {
                em.setX(Creed.mc.thePlayer.motionX);
                em.setZ(Creed.mc.thePlayer.motionZ);
            }
        }
    }

    public static float getYaw() {
        float yaw = ((Aura.target != null && Creed.aura.movementFix.isEnabled())) ? RotationUtils.getRotations(Aura.target).getYaw() : Creed.mc.thePlayer.rotationYaw;
        if (Creed.mc.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (Creed.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (Creed.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Creed.mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (Creed.mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw;
    }

    static {
        MovementUtils.strafeTimer = new MilliTimer();
    }
}

