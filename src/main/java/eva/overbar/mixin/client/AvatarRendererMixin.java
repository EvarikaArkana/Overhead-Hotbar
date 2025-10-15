package eva.overbar.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import eva.overbar.access.SubmitNodeCollectionAccess;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {
    @Inject(
            method = "submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"
            )
    )
    private void submitHotbar(
            AvatarRenderState renderState,
            PoseStack poseStack,
            SubmitNodeCollector nodeCollector,
            CameraRenderState cameraRenderState,
            CallbackInfo ci
    ) {
        int i = renderState.showExtraEars ? -10 : 0;
        if (renderState.nameTag != null) {
            AvatarlikeEntity entity = new AvatarlikeEntity();
            if (entity instanceof Player player)
                ((SubmitNodeCollectionAccess) nodeCollector).submitHotbar(
                        poseStack,
                        renderState.nameTagAttachment,
                        i - 15,
                        player,
                        renderState.distanceToCameraSq,
                        cameraRenderState
                );
        }
    }
}
