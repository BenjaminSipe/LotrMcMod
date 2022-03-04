package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRShields;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDaleSoldier extends LOTREntityDaleLevyman {
   public LOTREntityDaleSoldier(World world) {
      super(world);
      this.npcShield = LOTRShields.ALIGNMENT_DALE;
      this.spawnRidingHorse = this.field_70146_Z.nextInt(8) == 0;
   }

   protected EntityAIBase createDaleAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(5);
      if (i != 0 && i != 1 && i != 2) {
         if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeDale));
         } else if (i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeDale));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDale));
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearDale));
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDale));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDale));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDale));
      if (this.field_70146_Z.nextInt(10) != 0) {
         this.func_70062_b(4, new ItemStack(LOTRMod.helmetDale));
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDale));
      return horse;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }
}
