/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package varmite.verity.mixin;

import java.util.List;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import varmite.verity.client.DynamicLightManager;

@Mixin(value={EntityRenderer.class})
public abstract class MixinEntityRenderer<T extends Entity> {
    @Inject(method={"getBlockLightLevel"}, at={@At(value="RETURN")}, cancellable=true)
    protected void verity_injectEntityLighting(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        List<DynamicLightManager.Beam> activeBeams = DynamicLightManager.getCurrentFrameBeams();
        if (activeBeams.isEmpty()) {
            return;
        }
        Vec3 p = entity.getBoundingBox().getCenter();
        int highestExtraLight = (Integer)cir.getReturnValue();
        for (DynamicLightManager.Beam beam : activeBeams) {
            double dist;
            int extraLight;
            double radius;
            double cz;
            double dz;
            double cy;
            double dy;
            Vec3 a = beam.start;
            Vec3 b = beam.end;
            double abx = b.x - a.x;
            double aby = b.y - a.y;
            double abz = b.z - a.z;
            double apx = p.x - a.x;
            double apy = p.y - a.y;
            double apz = p.z - a.z;
            double ab_ab = abx * abx + aby * aby + abz * abz;
            if (ab_ab == 0.0) continue;
            double t = (apx * abx + apy * aby + apz * abz) / ab_ab;
            double cx = a.x + (t = Math.max(0.0, Math.min(1.0, t))) * abx;
            double dx = p.x - cx;
            double distSqr = dx * dx + (dy = p.y - (cy = a.y + t * aby)) * dy + (dz = p.z - (cz = a.z + t * abz)) * dz;
            if (!(distSqr < (radius = 2.0 + t * 5.0) * radius) || (extraLight = (int)(15.0 * (1.0 - (dist = Math.sqrt(distSqr)) / radius))) <= highestExtraLight) continue;
            highestExtraLight = extraLight;
        }
        if (highestExtraLight > (Integer)cir.getReturnValue()) {
            cir.setReturnValue(highestExtraLight);
        }
    }
}

