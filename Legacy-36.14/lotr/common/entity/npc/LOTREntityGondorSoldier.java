package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorSoldier extends LOTREntityGondorLevyman {
   public LOTREntityGondorSoldier(World world) {
      super(world);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(6) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_GONDOR;
   }

   public EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.45D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.75D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(6);
      if (i != 0 && i != 1 && i != 2 && i != 3) {
         if (i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
         } else if (i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeGondor));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerGondor));
      }

      if (this.field_70146_Z.nextInt(3) == 0) {
         this.npcItemsInv.setMeleeWeaponMounted(new ItemStack(LOTRMod.lanceGondor));
      } else {
         this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearGondor));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsGondor));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsGondor));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyGondor));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetGondor));
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         if (mounted) {
            this.func_70062_b(0, this.npcItemsInv.getIdleItemMounted());
         } else {
            this.func_70062_b(0, this.npcItemsInv.getIdleItem());
         }
      } else if (mounted) {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeaponMounted());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public LOTRMiniQuest createMiniQuest() {
      return this.field_70146_Z.nextInt(8) == 0 ? LOTRMiniQuestFactory.GONDOR_KILL_RENEGADE.createQuest(this) : super.createMiniQuest();
   }
}
