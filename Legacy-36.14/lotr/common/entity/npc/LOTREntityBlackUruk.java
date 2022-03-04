package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackUruk extends LOTREntityMordorOrc {
   public LOTREntityBlackUruk(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.isWeakOrc = false;
      this.npcShield = LOTRShields.ALIGNMENT_BLACK_URUK;
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.5D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(7);
      if (i != 0 && i != 1 && i != 2) {
         if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBlackUruk));
         } else if (i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlackUruk));
         } else if (i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlackUrukPoisoned));
         } else if (i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerBlackUruk));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarBlackUruk));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBlackUruk));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsBlackUruk));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsBlackUruk));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyBlackUruk));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetBlackUruk));
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.MORDOR;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killBlackUruk;
   }

   protected void dropOrcItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.BLACK_URUK_FORT, 1, 2 + i);
      }

      if (flag) {
         int shinyShirtChance = 6000;
         int shinyShirtChance = shinyShirtChance - i * 500;
         shinyShirtChance = Math.max(shinyShirtChance, 1);
         if (this.field_70146_Z.nextInt(shinyShirtChance) == 0) {
            this.func_145779_a(LOTRMod.bodyMithril, 1);
         }
      }

   }

   protected float func_70647_i() {
      return super.func_70647_i() * 0.75F;
   }

   public boolean canOrcSkirmish() {
      return false;
   }
}
