package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockGoran extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] goranIcons;
   private static final String[] goranNames = new String[]{"", "rock"};
   public static final String[] displayNames = new String[]{"Goran", "Cargoran"};

   public LOTRBlockGoran() {
      super(Material.field_151576_e);
      this.func_149647_a((CreativeTabs)null);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!world.field_72995_K) {
         if (!MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(entityplayer.func_146103_bH())) {
            return false;
         }

         for(int i1 = i - 32; i1 <= i + 32; ++i1) {
            for(int j1 = j - 32; j1 <= j + 32; ++j1) {
               for(int k1 = k - 32; k1 <= k + 32; ++k1) {
                  if (world.func_72899_e(i1, j1, k1) && world.func_147437_c(i1, j1, k1)) {
                     world.func_147449_b(i1, j1, k1, Blocks.field_150355_j);
                  }
               }
            }
         }
      }

      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= goranNames.length) {
         j = 0;
      }

      return this.goranIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.goranIcons = new IIcon[goranNames.length];

      for(int i = 0; i < goranNames.length; ++i) {
         String iconName = this.func_149641_N();
         if (!goranNames[i].equals("")) {
            iconName = iconName + "_" + goranNames[i];
         }

         this.goranIcons[i] = iconregister.func_94245_a(iconName);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < goranNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
