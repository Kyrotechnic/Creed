package net.kore.mixins;

import net.kore.Kore;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {
    /**
     * @author
     * @reason
     */
    @Overwrite
    public static String getClientModName()
    {
        return Kore.customBrand.isToggled() ? Kore.customBrand.brand.getValue() : "Forge";
    }
}
