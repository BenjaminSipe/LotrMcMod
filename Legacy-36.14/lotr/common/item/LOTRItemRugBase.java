package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class LOTRItemRugBase extends Item {
   @SideOnly(Side.CLIENT)
   private IIcon[] rugIcons;
   private String[] rugNames;

   public LOTRItemRugBase(String... names) {
      this.rugNames = names;
      this.func_77637_a(LOTRCreativeTabs.tabDeco);
      this.func_77625_d(1);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= this.rugIcons.length) {
         i = 0;
      }

      return this.rugIcons[i];
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      this.rugIcons = new IIcon[this.rugNames.length];

      for(int i = 0; i < this.rugIcons.length; ++i) {
         this.rugIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + this.rugNames[i]);
      }

   }

   protected abstract LOTREntityRugBase createRug(World var1, ItemStack var2);

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
      Block block = world.func_147439_a(i, j, k);
      if (block == Blocks.field_150431_aC) {
         l = 1;
      } else if (!block.isReplaceable(world, i, j, k)) {
         if (l == 0) {
            --j;
         }

         if (l == 1) {
            ++j;
         }

         if (l == 2) {
            --k;
         }

         if (l == 3) {
            ++k;
         }

         if (l == 4) {
            --i;
         }

         if (l == 5) {
            ++i;
         }
      }

      if (!entityplayer.func_82247_a(i, j, k, l, itemstack)) {
         return false;
      } else {
         if (world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && !world.field_72995_K) {
            LOTREntityRugBase rug = this.createRug(world, itemstack);
            rug.func_70012_b((double)((float)i + f), (double)j, (double)((float)k + f2), 180.0F - entityplayer.field_70177_z % 360.0F, 0.0F);
            if (world.func_72855_b(rug.field_70121_D) && world.func_72945_a(rug, rug.field_70121_D).size() == 0 && !world.func_72953_d(rug.field_70121_D)) {
               world.func_72838_d(rug);
               world.func_72956_a(rug, Blocks.field_150325_L.field_149762_H.func_150496_b(), (Blocks.field_150325_L.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150325_L.field_149762_H.func_150494_d() * 0.8F);
               --itemstack.field_77994_a;
               return true;
            }

            rug.func_70106_y();
         }

         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.rugNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
