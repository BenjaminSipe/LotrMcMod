package lotr.common.entity.ai;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTREntityAIRabbitEatCrops extends EntityAIBase {
   private LOTREntityRabbit theRabbit;
   private double xPos;
   private double yPos;
   private double zPos;
   private double moveSpeed;
   private World theWorld;
   private int pathingTick;
   private static final int maxPathingTick = 200;
   private int eatingTick;
   private static final int maxEatingTick = 60;
   private int rePathDelay;

   public LOTREntityAIRabbitEatCrops(LOTREntityRabbit rabbit, double d) {
      this.theRabbit = rabbit;
      this.moveSpeed = d;
      this.theWorld = rabbit.field_70170_p;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (!LOTRMod.canGrief(this.theWorld)) {
         return false;
      } else {
         if (this.theRabbit.func_70681_au().nextInt(20) == 0) {
            Vec3 vec3 = this.findCropsLocation();
            if (vec3 != null) {
               this.xPos = vec3.field_72450_a;
               this.yPos = vec3.field_72448_b;
               this.zPos = vec3.field_72449_c;
               return true;
            }
         }

         return false;
      }
   }

   public boolean func_75253_b() {
      if (!LOTRMod.canGrief(this.theWorld)) {
         return false;
      } else if (this.pathingTick < 200 && this.eatingTick < 60) {
         int i = MathHelper.func_76128_c(this.xPos);
         int j = MathHelper.func_76128_c(this.yPos);
         int k = MathHelper.func_76128_c(this.zPos);
         return this.canEatBlock(i, j, k);
      } else {
         return false;
      }
   }

   public void func_75251_c() {
      this.pathingTick = 0;
      this.eatingTick = 0;
      this.rePathDelay = 0;
      this.theRabbit.setRabbitEating(false);
   }

   public void func_75246_d() {
      if (this.theRabbit.func_70092_e(this.xPos, this.yPos, this.zPos) > 1.0D) {
         this.theRabbit.setRabbitEating(false);
         this.theRabbit.func_70671_ap().func_75650_a(this.xPos, this.yPos - 0.5D, this.zPos, 10.0F, (float)this.theRabbit.func_70646_bf());
         --this.rePathDelay;
         if (this.rePathDelay <= 0) {
            this.rePathDelay = 10;
            this.theRabbit.func_70661_as().func_75492_a(this.xPos, this.yPos, this.zPos, this.moveSpeed);
         }

         ++this.pathingTick;
      } else {
         this.theRabbit.setRabbitEating(true);
         ++this.eatingTick;
         if (this.eatingTick % 6 == 0) {
            this.theRabbit.func_85030_a("random.eat", 1.0F, (this.theWorld.field_73012_v.nextFloat() - this.theWorld.field_73012_v.nextFloat()) * 0.2F + 1.0F);
         }

         if (this.eatingTick >= 60) {
            this.theWorld.func_147468_f(MathHelper.func_76128_c(this.xPos), MathHelper.func_76128_c(this.yPos), MathHelper.func_76128_c(this.zPos));
         }
      }

   }

   private Vec3 findCropsLocation() {
      Random random = this.theRabbit.func_70681_au();

      for(int l = 0; l < 32; ++l) {
         int i = MathHelper.func_76128_c(this.theRabbit.field_70165_t) + MathHelper.func_76136_a(random, -16, 16);
         int j = MathHelper.func_76128_c(this.theRabbit.field_70121_D.field_72338_b) + MathHelper.func_76136_a(random, -8, 8);
         int k = MathHelper.func_76128_c(this.theRabbit.field_70161_v) + MathHelper.func_76136_a(random, -16, 16);
         if (this.canEatBlock(i, j, k)) {
            return Vec3.func_72443_a((double)i + 0.5D, (double)j, (double)k + 0.5D);
         }
      }

      return null;
   }

   private boolean canEatBlock(int i, int j, int k) {
      Block block = this.theWorld.func_147439_a(i, j, k);
      return block instanceof BlockCrops && !this.theRabbit.anyFarmhandsNearby(i, j, k);
   }
}
