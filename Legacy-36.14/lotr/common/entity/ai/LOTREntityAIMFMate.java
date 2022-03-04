package lotr.common.entity.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityAnimalMF;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityAIMFMate extends EntityAIBase {
   private LOTREntityAnimalMF theAnimal;
   World theWorld;
   public LOTREntityAnimalMF targetMate;
   int breeding = 0;
   double moveSpeed;

   public LOTREntityAIMFMate(LOTREntityAnimalMF animal, double d) {
      this.theAnimal = animal;
      this.theWorld = animal.field_70170_p;
      this.moveSpeed = d;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      if (!this.theAnimal.func_70880_s()) {
         return false;
      } else {
         this.targetMate = this.findMate();
         return this.targetMate != null;
      }
   }

   public boolean func_75253_b() {
      return this.targetMate.func_70089_S() && this.targetMate.func_70880_s() && this.breeding < 60;
   }

   public void func_75251_c() {
      this.targetMate = null;
      this.breeding = 0;
   }

   public void func_75246_d() {
      this.theAnimal.func_70671_ap().func_75651_a(this.targetMate, 10.0F, (float)this.theAnimal.func_70646_bf());
      this.theAnimal.func_70661_as().func_75497_a(this.targetMate, this.moveSpeed);
      ++this.breeding;
      if (this.breeding == 60 && this.theAnimal.func_70068_e(this.targetMate) < 9.0D) {
         this.procreate();
      }

   }

   private LOTREntityAnimalMF findMate() {
      float f = 8.0F;
      Class mateClass = this.theAnimal.getAnimalMFBaseClass();
      List list = this.theWorld.func_72872_a(mateClass, this.theAnimal.field_70121_D.func_72314_b((double)f, (double)f, (double)f));
      Iterator it = list.iterator();

      while(it.hasNext()) {
         LOTREntityAnimalMF mate = (LOTREntityAnimalMF)it.next();
         if (this.theAnimal.func_70878_b(mate)) {
            return mate;
         }
      }

      return null;
   }

   private void procreate() {
      EntityAgeable babyAnimal = this.theAnimal.func_90011_a(this.targetMate);
      if (babyAnimal != null) {
         this.theAnimal.func_70873_a(6000);
         this.targetMate.func_70873_a(6000);
         this.theAnimal.func_70875_t();
         this.targetMate.func_70875_t();
         babyAnimal.func_70873_a(-24000);
         babyAnimal.func_70012_b(this.theAnimal.field_70165_t, this.theAnimal.field_70163_u, this.theAnimal.field_70161_v, 0.0F, 0.0F);
         this.theWorld.func_72838_d(babyAnimal);
         Random rand = this.theAnimal.func_70681_au();

         for(int i = 0; i < 7; ++i) {
            double d = this.theAnimal.field_70165_t + (double)(MathHelper.func_151240_a(rand, -1.0F, 1.0F) * this.theAnimal.field_70130_N);
            double d1 = this.theAnimal.field_70163_u + 0.5D + (double)(rand.nextFloat() * this.theAnimal.field_70131_O);
            double d2 = this.theAnimal.field_70161_v + (double)(MathHelper.func_151240_a(rand, -1.0F, 1.0F) * this.theAnimal.field_70130_N);
            double d3 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            double d5 = rand.nextGaussian() * 0.02D;
            this.theWorld.func_72869_a("heart", d, d1, d2, d3, d4, d5);
         }

         if (LOTRMod.canDropLoot(this.theWorld)) {
            this.theWorld.func_72838_d(new EntityXPOrb(this.theWorld, this.theAnimal.field_70165_t, this.theAnimal.field_70163_u, this.theAnimal.field_70161_v, rand.nextInt(4) + 1));
         }
      }

   }
}
