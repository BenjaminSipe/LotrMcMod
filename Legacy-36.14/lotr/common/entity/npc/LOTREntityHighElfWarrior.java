package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHighElfWarrior extends LOTREntityHighElf {
   public LOTREntityHighElfWarrior(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(4) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_HIGH_ELF;
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 25, 40, 24.0F);
   }

   protected EntityAIBase createElfMeleeAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(6);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmHighElven));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearHighElven));
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordHighElven));
      }

      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.highElvenBow));
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHighElven));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsHighElven));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsHighElven));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyHighElven));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetHighElven));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "highElf/elf/hired" : "highElf/warrior/friendly";
      } else {
         return "highElf/warrior/hostile";
      }
   }
}
