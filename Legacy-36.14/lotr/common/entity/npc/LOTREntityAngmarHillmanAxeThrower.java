package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarHillmanAxeThrower extends LOTREntityAngmarHillmanWarrior {
   public LOTREntityAngmarHillmanAxeThrower(World world) {
      super(world);
   }

   public EntityAIBase getHillmanAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.4D, 40, 60, 12.0F);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeIron));
      } else {
         this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeBronze));
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

   public void func_82196_d(EntityLivingBase target, float f) {
      ItemStack axeItem = this.npcItemsInv.getRangedWeapon();
      if (axeItem == null) {
         axeItem = new ItemStack(LOTRMod.throwingAxeIron);
      }

      LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(this.field_70170_p, this, target, axeItem, 1.0F, (float)this.func_110148_a(npcRangedAccuracy).func_111126_e());
      this.func_85030_a("random.bow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(axe);
      this.func_71038_i();
   }
}
