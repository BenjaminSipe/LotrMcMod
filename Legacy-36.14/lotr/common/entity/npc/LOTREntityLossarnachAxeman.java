package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLossarnachAxeman extends LOTREntityGondorSoldier {
   private EntityAIBase rangedAttackAI = this.createGondorRangedAI();
   private EntityAIBase meleeAttackAI;

   public LOTREntityLossarnachAxeman(World world) {
      super(world);
      this.spawnRidingHorse = false;
      this.npcShield = LOTRShields.ALIGNMENT_LOSSARNACH;
   }

   public EntityAIBase createGondorAttackAI() {
      return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   protected EntityAIBase createGondorRangedAI() {
      return new LOTREntityAIRangedAttack(this, 1.3D, 30, 50, 16.0F);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeLossarnach));
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeLossarnach));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsLossarnach));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsLossarnach));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyLossarnach));
      if (this.field_70146_Z.nextInt(3) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetLossarnach));
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   public void func_82196_d(EntityLivingBase target, float f) {
      ItemStack axeItem = this.npcItemsInv.getRangedWeapon();
      if (axeItem == null) {
         axeItem = new ItemStack(LOTRMod.throwingAxeLossarnach);
      }

      LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(this.field_70170_p, this, target, axeItem, 1.0F, (float)this.func_110148_a(npcRangedAccuracy).func_111126_e());
      this.func_85030_a("random.bow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(axe);
      this.func_71038_i();
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      }

      if (mode == LOTREntityNPC.AttackMode.MELEE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

      if (mode == LOTREntityNPC.AttackMode.RANGED) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }
}
