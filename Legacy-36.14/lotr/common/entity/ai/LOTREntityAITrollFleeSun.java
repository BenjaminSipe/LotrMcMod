package lotr.common.entity.ai;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityAITrollFleeSun extends EntityAIBase {
   private LOTREntityTroll theTroll;
   private double xPosition;
   private double yPosition;
   private double zPosition;
   private double moveSpeed;
   private World theWorld;

   public LOTREntityAITrollFleeSun(LOTREntityTroll troll, double d) {
      this.theTroll = troll;
      this.moveSpeed = d;
      this.theWorld = troll.field_70170_p;
      this.func_75248_a(1);
   }

   public boolean func_75250_a() {
      if (!this.theWorld.func_72935_r()) {
         return false;
      } else if (!this.theWorld.func_72937_j(MathHelper.func_76128_c(this.theTroll.field_70165_t), (int)this.theTroll.field_70121_D.field_72338_b, MathHelper.func_76128_c(this.theTroll.field_70161_v))) {
         return false;
      } else if (this.theTroll.trollImmuneToSun) {
         return false;
      } else {
         BiomeGenBase biome = this.theWorld.func_72807_a(MathHelper.func_76128_c(this.theTroll.field_70165_t), MathHelper.func_76128_c(this.theTroll.field_70161_v));
         if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
            return false;
         } else {
            if (this.theTroll.getTrollBurnTime() == -1) {
               this.theTroll.setTrollBurnTime(300);
            }

            Vec3 vec3 = this.findPossibleShelter();
            if (vec3 == null) {
               vec3 = RandomPositionGenerator.func_75463_a(this.theTroll, 12, 6);
               if (vec3 == null) {
                  return false;
               }
            }

            this.xPosition = vec3.field_72450_a;
            this.yPosition = vec3.field_72448_b;
            this.zPosition = vec3.field_72449_c;
            return true;
         }
      }
   }

   public boolean func_75253_b() {
      return !this.theTroll.func_70661_as().func_75500_f();
   }

   public void func_75249_e() {
      this.theTroll.func_70661_as().func_75492_a(this.xPosition, this.yPosition, this.zPosition, this.moveSpeed);
   }

   private Vec3 findPossibleShelter() {
      Random random = this.theTroll.func_70681_au();

      for(int l = 0; l < 32; ++l) {
         int i = MathHelper.func_76128_c(this.theTroll.field_70165_t) - 24 + random.nextInt(49);
         int j = MathHelper.func_76128_c(this.theTroll.field_70121_D.field_72338_b) - 12 + random.nextInt(25);
         int k = MathHelper.func_76128_c(this.theTroll.field_70161_v) - 24 + random.nextInt(49);
         if (!this.theWorld.func_72937_j(i, j, k) && this.theTroll.func_70783_a(i, j, k) < 0.0F) {
            return Vec3.func_72443_a((double)i, (double)j, (double)k);
         }
      }

      return null;
   }
}
