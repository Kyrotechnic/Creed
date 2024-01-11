package net.kore.mixins.forge;

import net.kore.Kore;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FMLNetworkHandler.class)
public class MixinFMLNetworkHandler {
    /**
     * @author Kyrotechnics
     * @reason Stop forge gay thing
     */
    @Overwrite
    public static void fmlClientHandshake(NetworkManager manager)
    {
        if (Kore.customBrand.hideForge())
        {
            //return;
        }

        NetworkDispatcher.allocAndSet(manager).clientToServerHandshake();
    }
}
