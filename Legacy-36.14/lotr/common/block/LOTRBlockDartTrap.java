package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockDartTrap extends BlockContainer {
   @SideOnly(Side.CLIENT)
   private IIcon trapIcon;
   private Block modelBlock;
   private int modelBlockMeta;

   public LOTRBlockDartTrap(Block block, int meta) {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149711_c(4.0F);
      this.func_149672_a(Block.field_149769_e);
      this.modelBlock = block;
      this.modelBlockMeta = meta;
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityDartTrap();
   }

   public void func_149726_b(World world, int i, int j, int k) {
      super.func_149726_b(world, i, j, k);
      this.setDefaultDirection(world, i, j, k);
   }

   private void setDefaultDirection(World world, int i, int j, int k) {
      if (!world.field_72995_K) {
         Block i1 = world.func_147439_a(i, j, k - 1);
         Block j1 = world.func_147439_a(i, j, k + 1);
         Block k1 = world.func_147439_a(i - 1, j, k);
         Block l1 = world.func_147439_a(i + 1, j, k);
         byte meta = 3;
         if (i1.func_149662_c() && !j1.func_149662_c()) {
            meta = 3;
         }

         if (j1.func_149662_c() && !i1.func_149662_c()) {
            meta = 2;
         }

         if (k1.func_149662_c() && !l1.func_149662_c()) {
            meta = 5;
         }

         if (l1.func_149662_c() && !k1.func_149662_c()) {
            meta = 4;
         }

         world.func_72921_c(i, j, k, meta, 2);
      }

   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      int rotation = MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      if (rotation == 0) {
         world.func_72921_c(i, j, k, 2, 2);
      }

      if (rotation == 1) {
         world.func_72921_c(i, j, k, 5, 2);
      }

      if (rotation == 2) {
         world.func_72921_c(i, j, k, 3, 2);
      }

      if (rotation == 3) {
         world.func_72921_c(i, j, k, 4, 2);
      }

      if (itemstack.func_82837_s()) {
         ((LOTRTileEntityDartTrap)world.func_147438_o(i, j, k)).func_146018_a(itemstack.func_82833_r());
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      int meta = world.func_72805_g(i, j, k);
      return side == meta ? this.trapIcon : this.modelBlock.func_149691_a(i, this.modelBlockMeta);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i == 3 ? this.trapIcon : this.modelBlock.func_149691_a(i, this.modelBlockMeta);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.trapIcon = iconregister.func_94245_a(this.func_149641_N() + "_face");
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!world.field_72995_K) {
         entityplayer.openGui(LOTRMod.instance, 40, world, i, j, k);
      }

      return true;
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityDartTrap trap = (LOTRTileEntityDartTrap)world.func_147438_o(i, j, k);
      if (trap != null) {
         LOTRMod.dropContainerItems(trap, world, i, j, k);
         world.func_147453_f(i, j, k, block);
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   public boolean func_149740_M() {
      return true;
   }

   public int func_149736_g(World world, int i, int j, int k, int direction) {
      return Container.func_94526_b((IInventory)world.func_147438_o(i, j, k));
   }
}
