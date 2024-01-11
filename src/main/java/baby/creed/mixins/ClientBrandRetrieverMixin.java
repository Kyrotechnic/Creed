package baby.creed.mixins;

import baby.creed.Creed;
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
        return Creed.customBrand.isToggled() ? Creed.customBrand.brand.getValue() : "Forge";
    }
}
