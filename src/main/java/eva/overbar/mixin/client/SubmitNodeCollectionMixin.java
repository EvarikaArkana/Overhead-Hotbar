package eva.overbar.mixin.client;

import eva.overbar.SubmitNodeCollectionAccess;
import eva.overbar.util.HotbarFeatureRenderer;
import net.minecraft.client.renderer.SubmitNodeCollection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin implements SubmitNodeCollectionAccess {
    @Unique
    private final HotbarFeatureRenderer.Storage hotbarSubmits = new HotbarFeatureRenderer.Storage();

    @Override
    public HotbarFeatureRenderer.Storage getHotbarSubmits() {
        return hotbarSubmits;
    }
}
