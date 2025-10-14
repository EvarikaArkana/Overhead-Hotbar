package eva.overbar.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import eva.overbar.util.HotbarFeatureRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FeatureRenderDispatcher.class)
public class FeatureRenderDispatcherMixin {

    @Shadow
    @Final
    private MultiBufferSource.BufferSource bufferSource;

    @Unique
    private final HotbarFeatureRenderer hotbarFeatureRenderer = new HotbarFeatureRenderer();

    @Inject(
            method = "renderAllFeatures",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/feature/ParticleFeatureRenderer;render(Lnet/minecraft/client/renderer/SubmitNodeCollection;)V"
            )
    )
    private void render(CallbackInfo ci, @Local SubmitNodeCollection submitNodeCollection) {
        this.hotbarFeatureRenderer.render(submitNodeCollection, this.bufferSource);
    }
}