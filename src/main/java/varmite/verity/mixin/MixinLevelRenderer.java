/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package varmite.verity.mixin;

import java.util.List;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import varmite.verity.client.DynamicLightManager;

@Mixin(value={LevelRenderer.class})
public class MixinLevelRenderer {
    @Inject(method={"getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I"}, at={@At(value="RETURN")}, cancellable=true)
    private static void injectDynamicLighting(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        List<DynamicLightManager.Beam> activeBeams = DynamicLightManager.getCurrentFrameBeams();
        if (activeBeams.isEmpty()) {
            return;
        }
        double px = (double)pos.getX() + 0.5;
        double py = (double)pos.getY() + 0.5;
        double pz = (double)pos.getZ() + 0.5;
        int originalLight = (Integer)cir.getReturnValue();
        int highestExtraLight = 0;
        for (DynamicLightManager.Beam beam : activeBeams) {
            double distance;
            int extraLight;
            double radius;
            double maxRadiusSqr;
            double cz;
            double dz;
            double cy;
            double dy;
            double ax = beam.start.x;
            double ay = beam.start.y;
            double az = beam.start.z;
            double bx = beam.end.x;
            double by = beam.end.y;
            double bz = beam.end.z;
            double abx = bx - ax;
            double aby = by - ay;
            double abz = bz - az;
            double apx = px - ax;
            double apy = py - ay;
            double apz = pz - az;
            double ab_ab = abx * abx + aby * aby + abz * abz;
            if (ab_ab == 0.0) continue;
            double t = (apx * abx + apy * aby + apz * abz) / ab_ab;
            double cx = ax + (t = Math.max(0.0, Math.min(1.0, t))) * abx;
            double dx = px - cx;
            double distanceSqr = dx * dx + (dy = py - (cy = ay + t * aby)) * dy + (dz = pz - (cz = az + t * abz)) * dz;
            if (!(distanceSqr < (maxRadiusSqr = (radius = 2.0 + t * 5.0) * radius)) || (extraLight = (int)(15.0 * (1.0 - (distance = Math.sqrt(distanceSqr)) / radius))) <= highestExtraLight) continue;
            highestExtraLight = extraLight;
        }
        if (highestExtraLight > 0) {
            int blockLight = originalLight & 0xFFFF;
            int skyLight = originalLight >> 16 & 0xFFFF;
            int newBlockLight = Math.max(blockLight, highestExtraLight * 16);
            cir.setReturnValue((Object)(newBlockLight | skyLight << 16));
        }
    }
}

