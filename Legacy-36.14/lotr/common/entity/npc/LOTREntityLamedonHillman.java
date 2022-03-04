package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLamedonHillman extends LOTREntityGondorLevyman {
   private static ItemStack[] hillmanWeapons;
   private static int[] dyedHatColors;
   private static int[] featherColors;

   public LOTREntityLamedonHillman(World world) {
      super(world);
   }

   protected EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.6D, true);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(hillmanWeapons.length);
      this.npcItemsInv.setMeleeWeapon(hillmanWeapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(Items.field_151021_T));
      this.func_70062_b(2, (ItemStack)null);
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyLamedonJacket));
      if (this.field_70146_Z.nextInt(3) == 0) {
         ItemStack hat = new ItemStack(LOTRMod.leatherHat);
         if (this.field_70146_Z.nextBoolean()) {
            LOTRItemLeatherHat.setHatColor(hat, dyedHatColors[this.field_70146_Z.nextInt(dyedHatColors.length)]);
         }

         if (this.field_70146_Z.nextBoolean()) {
            LOTRItemLeatherHat.setFeatherColor(hat, featherColors[this.field_70146_Z.nextInt(featherColors.length)]);
         }

         this.func_70062_b(4, hat);
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   static {
      hillmanWeapons = new ItemStack[]{new ItemStack(Items.field_151036_c), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze)};
      dyedHatColors = new int[]{6316128, 2437173, 0};
      featherColors = new int[]{16777215, 10526880, 5658198, 2179924, 798013};
   }
}
