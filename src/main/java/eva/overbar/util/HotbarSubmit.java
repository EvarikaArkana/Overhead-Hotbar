package eva.overbar.util;

import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;

public record HotbarSubmit(
        Matrix4f pose,
        Player player,
        float x,
        float y,
        double distanceToCameraSq
) {}
