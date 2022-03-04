package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarfAxeThrower extends LOTREntityBlueDwarfWarrior {
   public LOTREntityBlueDwarfAxeThrower(World world) {
      super(world);
   }

   public EntityAIBase getDwarfAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 40, 12.0F);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.75D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeBlueDwarven));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      return data;
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }

   public void func_82196_d(EntityLivingBase target, float f) {
      ItemStack axeItem = this.npcItemsInv.getRangedWeapon();
      if (axeItem == null) {
         axeItem = new ItemStack(LOTRMod.throwingAxeBlueDwarven);
      }

      LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(this.field_70170_p, this, target, axeItem, 1.0F, (float)this.func_110148_a(npcRangedAccuracy).func_111126_e());
      this.func_85030_a("random.bow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(axe);
      this.func_71038_i();
   }
}
