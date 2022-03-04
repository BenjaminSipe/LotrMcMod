package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGundabadOrcArcher extends LOTREntityGundabadOrc {
   public LOTREntityGundabadOrcArcher(World world) {
      super(world);
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 30, 60, 16.0F);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextInt(4) == 0) {
         if (this.field_70146_Z.nextBoolean()) {
            this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.ironCrossbow));
         } else {
            this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.bronzeCrossbow));
         }
      } else if (this.field_70146_Z.nextBoolean()) {
         this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.orcBow));
      } else {
         this.npcItemsInv.setRangedWeapon(new ItemStack(Items.field_151031_f));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }

   private boolean isCrossbowOrc() {
      ItemStack itemstack = this.npcItemsInv.getRangedWeapon();
      return itemstack != null && itemstack.func_77973_b() instanceof LOTRItemCrossbow;
   }

   public void func_82196_d(EntityLivingBase target, float f) {
      if (this.isCrossbowOrc()) {
         this.npcCrossbowAttack(target, f);
      } else {
         this.npcArrowAttack(target, f);
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      if (this.isCrossbowOrc()) {
         this.dropNPCCrossbowBolts(i);
      } else {
         this.dropNPCArrows(i);
      }

   }
}
