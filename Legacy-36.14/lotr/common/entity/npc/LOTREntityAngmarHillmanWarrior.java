package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarHillmanWarrior extends LOTREntityAngmarHillman {
   private static ItemStack[] weapons;
   private static ItemStack[] helmets;
   private static ItemStack[] bodies;
   private static ItemStack[] legs;
   private static ItemStack[] boots;

   public LOTREntityAngmarHillmanWarrior(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_ANGMAR;
   }

   public EntityAIBase getHillmanAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, false);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weapons.length);
      this.npcItemsInv.setMeleeWeapon(weapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      i = this.field_70146_Z.nextInt(boots.length);
      this.func_70062_b(1, boots[i].func_77946_l());
      i = this.field_70146_Z.nextInt(legs.length);
      this.func_70062_b(2, legs[i].func_77946_l());
      i = this.field_70146_Z.nextInt(bodies.length);
      this.func_70062_b(3, bodies[i].func_77946_l());
      if (this.field_70146_Z.nextInt(5) != 0) {
         i = this.field_70146_Z.nextInt(helmets.length);
         this.func_70062_b(4, helmets[i].func_77946_l());
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public void dropHillmanItems(boolean flag, int i) {
   }

   static {
      weapons = new ItemStack[]{new ItemStack(LOTRMod.swordAngmar), new ItemStack(LOTRMod.battleaxeAngmar), new ItemStack(LOTRMod.hammerAngmar), new ItemStack(LOTRMod.daggerAngmar), new ItemStack(LOTRMod.polearmAngmar), new ItemStack(LOTRMod.spearAngmar)};
      helmets = new ItemStack[]{new ItemStack(LOTRMod.helmetBone), new ItemStack(LOTRMod.helmetFur)};
      bodies = new ItemStack[]{new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyFur)};
      legs = new ItemStack[]{new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsFur)};
      boots = new ItemStack[]{new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsFur)};
   }
}
