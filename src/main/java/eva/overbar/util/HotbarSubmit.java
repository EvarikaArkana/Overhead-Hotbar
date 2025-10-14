package eva.overbar.util;

import net.minecraft.client.player.inventory.Hotbar;
import org.joml.Matrix4f;

public record HotbarSubmit(Matrix4f pose, float x, float y, Hotbar hotbar, int lightCoords, int color, int backgroundColor, double distanceToCameraSq) {
}
