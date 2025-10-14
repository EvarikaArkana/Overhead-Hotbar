package eva.overbar.util;

import com.mojang.blaze3d.vertex.PoseStack;
import eva.overbar.SubmitNodeCollectionAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class HotbarFeatureRenderer {

    public void render(SubmitNodeCollection nodeCollection, MultiBufferSource.BufferSource bufferSource) {
        HotbarFeatureRenderer.Storage storage = ((SubmitNodeCollectionAccess) nodeCollection).getHotbarSubmits();
        storage.hotbarSubmitsSeeThrough.sort(Comparator.comparing(HotbarSubmit::distanceToCameraSq).reversed());

        for (HotbarSubmit hotbarSubmit : storage.hotbarSubmitsSeeThrough) {

        }

        for (HotbarSubmit hotbarSubmit : storage.hotbarSubmitsNormal) {

        }
    }

    public static class Storage {
        final List<HotbarSubmit> hotbarSubmitsSeeThrough = new ArrayList<>();
        final List<HotbarSubmit> hotbarSubmitsNormal = new ArrayList<>();

        public void add(
                PoseStack poseStack,
                @Nullable Vec3 pos,
                int yOffset,
                Hotbar hotbar,
                boolean seethrough,
                int packedLight,
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
                //hotbar sprite is 182px wide
                float f = 91;
                int i = (int)(minecraft.options.getBackgroundOpacity(0.25F) * 255.0F) << 24;
                if (seethrough) {
                    this.hotbarSubmitsNormal
                            .add(new HotbarSubmit(matrix4f, f, yOffset, hotbar, LightTexture.lightCoordsWithEmission(packedLight, 2), -1, 0, distanceToCameraSq));
                    this.hotbarSubmitsSeeThrough.add(new HotbarSubmit(matrix4f, f, yOffset, hotbar, packedLight, -2130706433, i, distanceToCameraSq));
                } else {
                    this.hotbarSubmitsNormal.add(new HotbarSubmit(matrix4f, f, yOffset, hotbar, packedLight, -2130706433, i, distanceToCameraSq));
                }

                poseStack.popPose();
            }
        }

        public void clear() {
            this.hotbarSubmitsNormal.clear();
            this.hotbarSubmitsSeeThrough.clear();
        }
    }
}
