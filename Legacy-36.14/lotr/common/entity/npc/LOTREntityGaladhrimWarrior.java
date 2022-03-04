package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityGaladhrimWarrior extends LOTREntityGaladhrimElf {
   public boolean isDefendingTree;

   public LOTREntityGaladhrimWarrior(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(4) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_GALADHRIM;
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 30, 40, 24.0F);
   }

   protected EntityAIBase createElfMeleeAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(6);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmElven));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearElven));
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordElven));
      }

      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.elvenBow));
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearElven));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsElven));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsElven));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyElven));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetElven));
      }

      return data;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("DefendingTree", this.isDefendingTree);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.isDefendingTree = nbt.func_74767_n("DefendingTree");
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && this.isDefendingTree && damagesource.func_76346_g() instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.takeMallornWood);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "galadhrim/elf/hired" : "galadhrim/warrior/friendly";
      } else {
         return "galadhrim/warrior/hostile";
      }
   }
}
