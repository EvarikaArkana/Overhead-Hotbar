package eva.overbar.util;

import com.mojang.blaze3d.vertex.PoseStack;
import eva.overbar.access.SubmitNodeCollectionAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class HotbarFeatureRenderer {

    public void render(SubmitNodeCollection nodeCollection, MultiBufferSource.BufferSource bufferSource) {
        HotbarFeatureRenderer.Storage storage = ((SubmitNodeCollectionAccess) nodeCollection).getHotbarSubmits();

        for (HotbarSubmit hotbarSubmit : storage.hotbarSubmitsNormal) {
            DrawHotbar.drawInBatch(
                    hotbarSubmit.player(),
                    hotbarSubmit.x(),
                    hotbarSubmit.y(),
                    hotbarSubmit.pose()
            );
        }
    }

    public static class Storage {
        final List<HotbarSubmit> hotbarSubmitsNormal = new ArrayList<>();

        public void add(
                PoseStack poseStack,
                @Nullable Vec3 pos,
                int yOffset,
                Player player,
                double distanceToCameraSq,
                CameraRenderState cameraRenderState
        ) {
            if (pos != null) {
                Minecraft minecraft = Minecraft.getInstance();
                poseStack.pushPose();
                poseStack.translate(pos.x, pos.y + 0.5, pos.z);
                poseStack.mulPose(cameraRenderState.orientation);
                poseStack.scale(0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = new Matrix4f(poseStack.last().pose());
                //hotbar sprite is 182p wide
                float f = 91;
                int i = (int)(minecraft.options.getBackgroundOpacity(0.25F) * 255.0F) << 24;
                this.hotbarSubmitsNormal.add(new HotbarSubmit(matrix4f, player, f, yOffset, distanceToCameraSq));

                poseStack.popPose();
            }
        }

        public void clear() {
            this.hotbarSubmitsNormal.clear();
        }
    }
}
