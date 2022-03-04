package lotr.common.entity.npc;

import lotr.common.LOTRCapes;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntitySouthronChampion extends LOTREntityNearHaradrimWarrior {
   private static ItemStack[] weaponsChampion;

   public LOTREntitySouthronChampion(World world) {
      super(world);
      this.spawnRidingHorse = true;
      this.npcCape = LOTRCapes.SOUTHRON_CHAMPION;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(npcRangedAccuracy).func_111128_a(0.5D);
      this.func_110148_a(horseAttackSpeed).func_111128_a(1.9D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weaponsChampion.length);
      this.npcItemsInv.setMeleeWeapon(weaponsChampion[i].func_77946_l());
      this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsNearHarad));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsNearHarad));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyNearHarad));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetNearHaradWarlord));
      return data;
   }

   static {
      weaponsChampion = new ItemStack[]{new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad)};
   }
}
