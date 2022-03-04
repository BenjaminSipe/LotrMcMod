package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockGoblet extends LOTRBlockMug {
   public LOTRBlockGoblet() {
      super(2.5F, 9.0F);
      this.func_149672_a(Block.field_149777_j);
   }

   public static class Wood extends LOTRBlockGoblet {
      public Wood() {
         this.func_149672_a(Block.field_149766_f);
      }

      @SideOnly(Side.CLIENT)
      public IIcon func_149691_a(int i, int j) {
         return Blocks.field_150344_f.func_149691_a(i, 0);
      }
   }

   public static class Copper extends LOTRBlockGoblet {
      @SideOnly(Side.CLIENT)
      public IIcon func_149691_a(int i, int j) {
         return LOTRMod.blockOreStorage.func_149691_a(i, 0);
      }
   }

   public static class Silver extends LOTRBlockGoblet {
      @SideOnly(Side.CLIENT)
      public IIcon func_149691_a(int i, int j) {
         return LOTRMod.blockOreStorage.func_149691_a(i, 3);
      }
   }

   public static class Gold extends LOTRBlockGoblet {
      @SideOnly(Side.CLIENT)
      public IIcon func_149691_a(int i, int j) {
         return Blocks.field_150340_R.func_149691_a(i, 0);
      }
   }
}
