package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDunlendingWarrior extends LOTREntityDunlending {
   public LOTREntityDunlendingWarrior(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_DUNLAND;
   }

   public EntityAIBase getDunlendingAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(7);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.field_151040_l));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordBronze));
      } else if (i == 2) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
      } else if (i == 3) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
      } else if (i == 4) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeIron));
      } else if (i == 5) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBronze));
      } else if (i == 6) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeIron));
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         if (this.field_70146_Z.nextBoolean()) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearIron));
         } else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBronze));
         }
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDunlending));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDunlending));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDunlending));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetDunlending));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
