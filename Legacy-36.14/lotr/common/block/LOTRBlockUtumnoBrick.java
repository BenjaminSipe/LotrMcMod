package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockUtumnoBrick extends Block implements LOTRWorldProviderUtumno.UtumnoBlock {
   @SideOnly(Side.CLIENT)
   private IIcon[] brickIcons;
   @SideOnly(Side.CLIENT)
   private IIcon iceGlowingTop;
   @SideOnly(Side.CLIENT)
   private IIcon fireTileSide;
   private String[] brickNames = new String[]{"fire", "burning", "ice", "iceGlowing", "obsidian", "obsidianFire", "iceTile", "obsidianTile", "fireTile"};

   public LOTRBlockUtumnoBrick() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.5F);
      this.func_149752_b(Float.MAX_VALUE);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int side, int meta) {
      if (meta >= this.brickNames.length) {
         meta = 0;
      }

      if (meta == 3 && side == 1) {
         return this.iceGlowingTop;
      } else {
         return meta == 8 && side != 1 && side != 0 ? this.fireTileSide : this.brickIcons[meta];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.brickIcons = new IIcon[this.brickNames.length];

      for(int i = 0; i < this.brickNames.length; ++i) {
         String subName = this.func_149641_N() + "_" + this.brickNames[i];
         this.brickIcons[i] = iconregister.func_94245_a(subName);
         if (i == 3) {
            this.iceGlowingTop = iconregister.func_94245_a(subName + "_top");
         }

         if (i == 8) {
            this.fireTileSide = iconregister.func_94245_a(subName + "_side");
         }
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   public int getLightValue(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      return meta != 1 && meta != 3 && meta != 5 ? super.getLightValue(world, i, j, k) : 12;
   }

   public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
      return this.isFlammable(world, i, j, k, side);
   }

   public boolean isFlammable(IBlockAccess world, int i, int j, int k, ForgeDirection side) {
      int meta = world.func_72805_g(i, j, k);
      return meta != 0 && meta != 1 ? super.isFlammable(world, i, j, k, side) : true;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.brickNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
