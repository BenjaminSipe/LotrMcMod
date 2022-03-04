package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainWarrior extends LOTREntityMoredain {
   private static ItemStack[] weaponsMoredain;
   private static ItemStack[] weaponsIron;
   private static ItemStack[] weaponsBronze;

   public LOTREntityMoredainWarrior(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
      this.spawnRidingHorse = this.field_70146_Z.nextInt(10) == 0;
   }

   protected EntityAIBase createHaradrimAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.7D, true);
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i;
      if (this.field_70146_Z.nextInt(5) == 0) {
         if (this.field_70146_Z.nextBoolean()) {
            i = this.field_70146_Z.nextInt(weaponsIron.length);
            this.npcItemsInv.setMeleeWeapon(weaponsIron[i].func_77946_l());
         } else {
            i = this.field_70146_Z.nextInt(weaponsBronze.length);
            this.npcItemsInv.setMeleeWeapon(weaponsBronze[i].func_77946_l());
         }
      } else {
         i = this.field_70146_Z.nextInt(weaponsMoredain.length);
         this.npcItemsInv.setMeleeWeapon(weaponsMoredain[i].func_77946_l());
      }

      if (this.field_70146_Z.nextInt(3) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearMoredain));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsMoredain));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsMoredain));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyMoredain));
      if (this.field_70146_Z.nextInt(3) == 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetMoredain));
      }

      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   static {
      weaponsMoredain = new ItemStack[]{new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.daggerMoredain), new ItemStack(LOTRMod.daggerMoredainPoisoned), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.swordMoredain), new ItemStack(LOTRMod.swordMoredain)};
      weaponsIron = new ItemStack[]{new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.spearNearHarad)};
      weaponsBronze = new ItemStack[]{new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad)};
   }
}
