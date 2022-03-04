package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfWarrior extends LOTREntityDorwinionElf {
   public LOTREntityDorwinionElfWarrior(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
      this.npcShield = LOTRShields.ALIGNMENT_DORWINION_ELF;
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
      int i = this.field_70146_Z.nextInt(2);
      if (i == 0) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDorwinionElf));
         if (this.field_70146_Z.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
         }
      } else if (i == 1) {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
         this.npcItemsInv.setSpearBackup((ItemStack)null);
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDorwinionElf));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDorwinionElf));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDorwinionElf));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetDorwinionElf));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 3.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "dorwinion/elfWarrior/hired" : "dorwinion/elfWarrior/friendly";
      } else {
         return "dorwinion/elfWarrior/hostile";
      }
   }
}
