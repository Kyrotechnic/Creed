package baby.creed.modules.combat;

import baby.creed.Creed;
import baby.creed.events.MotionUpdateEvent;
import baby.creed.modules.Module;
import baby.creed.settings.BooleanSetting;
import baby.creed.settings.ModeSetting;
import baby.creed.settings.NumberSetting;
import baby.creed.util.MilliTimer;
import baby.creed.util.MovementUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoBlock extends Module
{
    public ModeSetting mode;
    public NumberSetting blockTime;
    public BooleanSetting players;
    public BooleanSetting mobs;
    public BooleanSetting onDamage;
    public BooleanSetting noSlow;
    public MilliTimer blockTimer;
    private boolean isBlocking;

    public AutoBlock() {
        super("AutoBlock", Category.COMBAT);
        this.mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Vanilla" });
        this.blockTime = new NumberSetting("Block time", 500.0, 50.0, 2000.0, 50.0);
        this.players = new BooleanSetting("Players", true);
        this.mobs = new BooleanSetting("Mobs", false);
        this.onDamage = new BooleanSetting("on Damage", true);
        this.noSlow = new BooleanSetting("No Slow", false);
        this.blockTimer = new MilliTimer();
        this.addSettings(this.mode, this.blockTime, this.players, this.mobs, this.onDamage, this.noSlow);

        this.flagRisky();
    }

    @Override
    public void assign()
    {
        Creed.autoBlock = this;
    }

    @SubscribeEvent
    public void onAttacK(final AttackEntityEvent event) {
        if (!this.isToggled() || Creed.aura.isToggled()) {
            return;
        }
        if ((event.entityPlayer == Creed.mc.thePlayer && ((event.target instanceof EntityPlayer && this.players.isEnabled()) || (!(event.target instanceof EntityPlayer) && this.mobs.isEnabled()))) || (event.target == Creed.mc.thePlayer && this.onDamage.isEnabled())) {
            this.blockTimer.reset();
            if (event.entityPlayer == Creed.mc.thePlayer && (!MovementUtils.isMoving() || this.mode.is("Vanilla")) && this.isBlocking) {
                this.stopBlocking();
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Post event) {
        if (!this.isToggled() || Creed.aura.isToggled()) {
            return;
        }
        if (!this.blockTimer.hasTimePassed((long)this.blockTime.getValue())) {
            if ((!this.isBlocking || this.mode.is("Hypixel")) && this.canBlock()) {
                this.startBlocking();
            }
        }
        else if (this.isBlocking) {
            this.stopBlocking();
        }
    }

    public boolean canBlock() {
        return Creed.mc.thePlayer.getHeldItem() != null && Creed.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }

    private void startBlocking() {
        Creed.mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(Creed.mc.thePlayer.getHeldItem()));
        this.isBlocking = true;
    }

    private void stopBlocking() {
        if (this.isBlocking) {
            Creed.mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.isBlocking = false;
        }
    }

    public boolean isBlocking() {
        return Creed.autoBlock.canBlock() && this.isBlocking && !this.noSlow.isEnabled();
    }
}

