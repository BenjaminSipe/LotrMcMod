package lotr.common.item;

import lotr.common.entity.item.LOTREntityLionRug;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemLionRug extends LOTRItemRugBase {
   public LOTRItemLionRug() {
      super(LOTRItemLionRug.LionRugType.lionRugNames());
   }

   protected LOTREntityRugBase createRug(World world, ItemStack itemstack) {
      LOTREntityLionRug rug = new LOTREntityLionRug(world);
      rug.setRugType(LOTRItemLionRug.LionRugType.forID(itemstack.func_77960_j()));
      return rug;
   }

   public static enum LionRugType {
      LION(0),
      LIONESS(1);

      public final int lionID;

      private LionRugType(int i) {
         this.lionID = i;
      }

      public String textureName() {
         return this.name().toLowerCase();
      }

      public static LOTRItemLionRug.LionRugType forID(int ID) {
         LOTRItemLionRug.LionRugType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRItemLionRug.LionRugType t = var1[var3];
            if (t.lionID == ID) {
               return t;
            }
         }

         return LION;
      }

      public static String[] lionRugNames() {
         String[] names = new String[values().length];

         for(int i = 0; i < names.length; ++i) {
            names[i] = values()[i].textureName();
         }

         return names;
      }
   }
}
