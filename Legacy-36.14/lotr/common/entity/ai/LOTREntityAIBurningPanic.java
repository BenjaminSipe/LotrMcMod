package lotr.common.entity.ai;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTREntityAIBurningPanic extends EntityAIBase {
   private EntityCreature theEntity;
   private World theWorld;
   private double speed;
   private double randPosX;
   private double randPosY;
   private double randPosZ;
   private boolean avoidsWater;

   public LOTREntityAIBurningPanic(EntityCreature entity, double d) {
      this.theEntity = entity;
      this.theWorld = this.theEntity.field_70170_p;
      this.speed = d;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (this.theEntity.func_70027_ad() && this.theEntity.func_70638_az() == null) {
         Vec3 target = this.findWaterLocation();
         if (target == null) {
            target = RandomPositionGenerator.func_75463_a(this.theEntity, 5, 4);
         }

         if (target != null) {
            this.randPosX = target.field_72450_a;
            this.randPosY = target.field_72448_b;
            this.randPosZ = target.field_72449_c;
            return true;
         }
      }

      return false;
   }

   private Vec3 findWaterLocation() {
      Random random = this.theEntity.func_70681_au();

      for(int l = 0; l < 32; ++l) {
         int i = MathHelper.func_76128_c(this.theEntity.field_70165_t) + MathHelper.func_76136_a(random, -8, 8);
         int j = MathHelper.func_76128_c(this.theEntity.field_70121_D.field_72338_b) + MathHelper.func_76136_a(random, -8, 8);
         int k = MathHelper.func_76128_c(this.theEntity.field_70161_v) + MathHelper.func_76136_a(random, -8, 8);
         if (!this.theWorld.func_147439_a(i, j + 1, k).func_149721_r() && !this.theWorld.func_147439_a(i, j, k).func_149721_r() && this.theWorld.func_147439_a(i, j - 1, k).func_149688_o() == Material.field_151586_h) {
            return Vec3.func_72443_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
         }
      }

      return null;
   }

   public void func_75249_e() {
      this.avoidsWater = this.theEntity.func_70661_as().func_75486_a();
      this.theEntity.func_70661_as().func_75491_a(false);
      this.theEntity.func_70661_as().func_75492_a(this.randPosX, this.randPosY, this.randPosZ, this.speed);
   }

   public boolean func_75253_b() {
      return this.theEntity.func_70027_ad() && this.theEntity.func_70638_az() == null && !this.theEntity.func_70661_as().func_75500_f();
   }

   public void func_75251_c() {
      this.theEntity.func_70661_as().func_75491_a(this.avoidsWater);
   }
}
