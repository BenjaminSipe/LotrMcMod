package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityChest;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockChest extends BlockContainer {
   private Block baseBlock;
   private int baseMeta;
   private String chestTextureName;

   public LOTRBlockChest(Material m, Block b, int i, String s) {
      super(m);
      this.baseBlock = b;
      this.baseMeta = i;
      this.func_149672_a(b.field_149762_H);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149676_a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      this.chestTextureName = s;
   }

   public TileEntity func_149915_a(World world, int i) {
      LOTRTileEntityChest chest = new LOTRTileEntityChest();
      chest.textureName = this.getChestTextureName();
      return chest;
   }

   public String getChestTextureName() {
      return this.chestTextureName;
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (!world.field_72995_K) {
         IInventory chestInv = this.getModChestAt(world, i, j, k);
         if (chestInv != null) {
            entityplayer.openGui(LOTRMod.instance, 41, world, i, j, k);
         }
      }

      return true;
   }

   public IInventory getModChestAt(World world, int i, int j, int k) {
      return world.isSideSolid(i, j + 1, k, ForgeDirection.DOWN) ? null : (IInventory)world.func_147438_o(i, j, k);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.baseBlock.func_149691_a(i, this.baseMeta);
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getChestRenderID();
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
      int meta = 0;
      int l = MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      if (l == 0) {
         meta = 2;
      }

      if (l == 1) {
         meta = 5;
      }

      if (l == 2) {
         meta = 3;
      }

      if (l == 3) {
         meta = 4;
      }

      world.func_72921_c(i, j, k, meta, 3);
      if (itemstack.func_82837_s()) {
         ((LOTRTileEntityChest)world.func_147438_o(i, j, k)).setCustomName(itemstack.func_82833_r());
      }

   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityChest chest = (LOTRTileEntityChest)world.func_147438_o(i, j, k);
      if (chest != null) {
         LOTRMod.dropContainerItems(chest, world, i, j, k);
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
