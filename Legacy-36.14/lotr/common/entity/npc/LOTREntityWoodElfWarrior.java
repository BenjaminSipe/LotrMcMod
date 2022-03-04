package lotr.common.entity.npc;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityWoodElfWarrior extends LOTREntityWoodElf {
   public LOTREntityWoodElfWarrior(World world) {
      super(world);
      this.field_70714_bg.func_85156_a(this.rangedAttackAI);
      this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(4) == 0;
      this.npcShield = LOTRShields.ALIGNMENT_WOOD_ELF;
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityElk elk = new LOTREntityElk(this.field_70170_p);
      elk.setMountArmor(new ItemStack(LOTRMod.elkArmorWoodElven));
      return elk;
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 25, 35, 24.0F);
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
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmWoodElven));
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearWoodElven));
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordWoodElven));
      }

      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearWoodElven));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsWoodElven));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsWoodElven));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyWoodElven));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetWoodElven));
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "woodElf/elf/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= getWoodlandTrustLevel() ? "woodElf/warrior/friendly" : "woodElf/elf/neutral";
         }
      } else {
         return "woodElf/warrior/hostile";
      }
   }
}
