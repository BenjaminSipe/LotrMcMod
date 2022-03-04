package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public abstract class LOTRBlockPillarBase extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] pillarFaceIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] pillarSideIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] pillarSideTopIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] pillarSideMiddleIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] pillarSideBottomIcons;
   private String[] pillarNames;

   public LOTRBlockPillarBase() {
      this(Material.field_151576_e);
      this.func_149711_c(1.5F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   public LOTRBlockPillarBase(Material material) {
      super(material);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   protected void setPillarNames(String... names) {
      this.pillarNames = names;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      boolean pillarAbove = this.isPillarAt(world, i, j + 1, k);
      boolean pillarBelow = this.isPillarAt(world, i, j - 1, k);
      int meta = world.func_72805_g(i, j, k);
      if (meta >= this.pillarNames.length) {
         meta = 0;
      }

      if (side != 0 && side != 1) {
         if (pillarAbove && pillarBelow) {
            return this.pillarSideMiddleIcons[meta];
         } else if (pillarAbove) {
            return this.pillarSideBottomIcons[meta];
         } else {
            return pillarBelow ? this.pillarSideTopIcons[meta] : this.pillarSideIcons[meta];
         }
      } else {
         return this.pillarFaceIcons[meta];
      }
   }

   private boolean isPillarAt(IBlockAccess world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      return block instanceof LOTRBlockPillarBase;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.pillarNames.length) {
         j = 0;
      }

      return i != 0 && i != 1 ? this.pillarSideIcons[j] : this.pillarFaceIcons[j];
   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.pillarNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.pillarFaceIcons = new IIcon[this.pillarNames.length];
      this.pillarSideIcons = new IIcon[this.pillarNames.length];
      this.pillarSideTopIcons = new IIcon[this.pillarNames.length];
      this.pillarSideMiddleIcons = new IIcon[this.pillarNames.length];
      this.pillarSideBottomIcons = new IIcon[this.pillarNames.length];

      for(int i = 0; i < this.pillarNames.length; ++i) {
         String s = this.func_149641_N() + "_" + this.pillarNames[i];
         this.pillarFaceIcons[i] = iconregister.func_94245_a(s + "_face");
         this.pillarSideIcons[i] = iconregister.func_94245_a(s + "_side");
         this.pillarSideTopIcons[i] = iconregister.func_94245_a(s + "_sideTop");
         this.pillarSideMiddleIcons[i] = iconregister.func_94245_a(s + "_sideMiddle");
         this.pillarSideBottomIcons[i] = iconregister.func_94245_a(s + "_sideBottom");
      }

   }
}
