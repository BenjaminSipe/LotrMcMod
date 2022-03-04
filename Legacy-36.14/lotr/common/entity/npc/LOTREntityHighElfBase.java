package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityHighElfBase extends LOTREntityElf {
   public LOTREntityHighElfBase(World world) {
      super(world);
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.HIGH_ELF;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected void dropElfItems(boolean flag, int i) {
      super.dropElfItems(flag, i);
      if (flag) {
         int dropChance = 20 - i * 4;
         dropChance = Math.max(dropChance, 1);
         if (this.field_70146_Z.nextInt(dropChance) == 0) {
            ItemStack elfDrink = new ItemStack(LOTRMod.mugMiruvor);
            elfDrink.func_77964_b(1 + this.field_70146_Z.nextInt(3));
            LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(this.field_70146_Z), true);
            this.func_70099_a(elfDrink, 0.0F);
         }
      }

   }

   public boolean canElfSpawnHere() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      return j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == Blocks.field_150349_c;
   }
}
