//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package baby.creed.mixins.entity;

import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;

@Mixin(value = { AbstractClientPlayer.class }, priority = 1)
public abstract class AbstractClientPlayerMixin extends PlayerMixin
{
    @Shadow protected abstract NetworkPlayerInfo getPlayerInfo();
    /*private static ResourceLocation getCape(final String uuid) {
        if (Creed.capeManager.capeList.containsKey(uuid))
            return Creed.capeManager.capeList.get(uuid).getCapeLocation();
        return null;
    }
    
    @Inject(method = { "getLocationCape" }, at = { @At("RETURN") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> cir) {
        final ResourceLocation minecons = getCape(this.getUniqueID().toString());
        if (minecons != null) {
            cir.setReturnValue(minecons);
        }
    }*/
}
