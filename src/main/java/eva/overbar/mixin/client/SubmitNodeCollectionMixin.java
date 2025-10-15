package eva.overbar.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import eva.overbar.access.SubmitNodeCollectionAccess;
import eva.overbar.util.HotbarFeatureRenderer;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin implements SubmitNodeCollectionAccess {
    @Shadow
    private boolean wasUsed;

    @Unique
    private final HotbarFeatureRenderer.Storage hotbarSubmits = new HotbarFeatureRenderer.Storage();

    @Override
    public HotbarFeatureRenderer.Storage getHotbarSubmits() {
        return hotbarSubmits;
    }

    @Override
    public void submitHotbar(
            PoseStack poseStack,
            @Nullable Vec3 pos,
            int yOffset,
            Player player,
            double distanceToCameraSq,
            CameraRenderState cameraRenderState
    ) {
        this.wasUsed = true;
        this.hotbarSubmits.add(
                poseStack,
                pos,
                yOffset,
                player,
                distanceToCameraSq,
                cameraRenderState
        );
    }


}
