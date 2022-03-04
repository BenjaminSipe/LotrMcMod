package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityFirePot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingFireThrower extends LOTREntityEasterlingWarrior {
   public EntityAIBase rangedAttackAI = this.createEasterlingRangedAI();
   public EntityAIBase meleeAttackAI;

   public LOTREntityEasterlingFireThrower(World world) {
      super(world);
      this.spawnRidingHorse = false;
   }

   protected EntityAIBase createEasterlingAttackAI() {
      return this.meleeAttackAI = super.createEasterlingAttackAI();
   }

   protected EntityAIBase createEasterlingRangedAI() {
      return new LOTREntityAIRangedAttack(this, 1.3D, 20, 30, 16.0F);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhun));
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rhunFirePot));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
      return data;
   }

   public void func_82196_d(EntityLivingBase target, float f) {
      EntityArrow template = new EntityArrow(this.field_70170_p, this, target, 1.0F, 0.5F);
      LOTREntityFirePot pot = new LOTREntityFirePot(this.field_70170_p, this);
      pot.func_70012_b(template.field_70165_t, template.field_70163_u, template.field_70161_v, template.field_70177_z, template.field_70125_A);
      pot.field_70159_w = template.field_70159_w;
      pot.field_70181_x = template.field_70181_x;
      pot.field_70179_y = template.field_70179_y;
      this.func_85030_a("random.bow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(pot);
   }

   public double getMeleeRange() {
      EntityLivingBase target = this.func_70638_az();
      return target != null && target.func_70027_ad() ? Double.MAX_VALUE : super.getMeleeRange();
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
