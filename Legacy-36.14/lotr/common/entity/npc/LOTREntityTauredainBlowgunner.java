package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemDart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityTauredainBlowgunner extends LOTREntityTauredain {
   public LOTREntityTauredainBlowgunner(World world) {
      super(world);
      this.addTargetTasks(true);
   }

   public EntityAIBase createHaradrimAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.5D, 10, 30, 16.0F);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.tauredainBlowgun));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsTauredain));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsTauredain));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyTauredain));
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (this.npcItemsInv.getRangedWeapon() == null) {
         this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.tauredainBlowgun));
         this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      }

   }

   public void func_82196_d(EntityLivingBase target, float f) {
      ItemStack heldItem = this.func_70694_bm();
      float str = 1.0F + this.func_70032_d(target) / 16.0F * 0.015F;
      str *= LOTRItemBlowgun.getBlowgunLaunchSpeedFactor(heldItem);
      LOTREntityDart dart = ((LOTRItemDart)LOTRMod.tauredainDart).createDart(this.field_70170_p, this, target, new ItemStack(LOTRMod.tauredainDart), str, 1.0F);
      if (heldItem != null) {
         LOTRItemBlowgun.applyBlowgunModifiers(dart, heldItem);
      }

      this.func_85030_a("lotr:item.dart", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 1.2F) + 0.5F);
      this.field_70170_p.func_72838_d(dart);
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      this.dropNPCAmmo(LOTRMod.tauredainDart, i);
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "tauredain/warrior/hired" : "tauredain/warrior/friendly";
      } else {
         return "tauredain/warrior/hostile";
      }
   }
}
