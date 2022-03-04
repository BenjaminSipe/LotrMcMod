package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRItemHaradTurban;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBanditHarad extends LOTREntityBandit {
   private static ItemStack[] weapons;
   private static int[] robeColors;

   public LOTREntityBanditHarad(World world) {
      super(world);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weapons.length);
      this.npcItemsInv.setMeleeWeapon(weapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(4, (ItemStack)null);
      if (this.field_70146_Z.nextInt(4) == 0) {
         ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
         int robeColor = robeColors[this.field_70146_Z.nextInt(robeColors.length)];
         LOTRItemHaradRobes.setRobesColor(turban, robeColor);
         if (this.field_70146_Z.nextInt(3) == 0) {
            LOTRItemHaradTurban.setHasOrnament(turban, true);
         }

         this.func_70062_b(4, turban);
      }

      return data;
   }

   static {
      weapons = new ItemStack[]{new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned)};
      robeColors = new int[]{3354412, 5984843, 5968655, 3619908, 9007463, 3228720};
   }
}
