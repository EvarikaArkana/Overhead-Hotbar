package eva.overbar.util;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import static net.minecraft.client.gui.Gui.HOTBAR_SPRITE;

public class DrawHotbar {
    private static final GuiRenderState guiRenderState = new GuiRenderState();
    private static final Minecraft minecraft = Minecraft.getInstance();

    static void drawInBatch(
            Player player,
            double x,
            double y,
            Matrix4f pose
    ) {
        GuiGraphics guiGraphics = new GuiGraphics(minecraft, guiRenderState);
        int i = guiGraphics.guiWidth() / 2;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, HOTBAR_SPRITE, i - 91, guiGraphics.guiHeight() - 22, 182, 22);
        int l = 1;

        for(int m = 0; m < 9; ++m) {
            int n = i - 90 + m * 20 + 2;
            int o = guiGraphics.guiHeight() - 16 - 3;
            renderSlot(guiGraphics, n, o, minecraft.getDeltaTracker(), player, player.getInventory().getItem(m), l++);
        }
    }

    private static void renderSlot(GuiGraphics guiGraphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack stack, int seed) {
        if (!stack.isEmpty()) {
            float f = (float)stack.getPopTime() - deltaTracker.getGameTimeDeltaPartialTick(false);
            if (f > 0.0F) {
                float g = 1.0F + f / 5.0F;
                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().translate((float)(x + 8), (float)(y + 12));
                guiGraphics.pose().scale(1.0F / g, (g + 1.0F) / 2.0F);
                guiGraphics.pose().translate((float)(-(x + 8)), (float)(-(y + 12)));
            }

            guiGraphics.renderItem(player, stack, x, y, seed);
            if (f > 0.0F) {
                guiGraphics.pose().popMatrix();
            }

            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, stack, x, y);
        }
    }
}
