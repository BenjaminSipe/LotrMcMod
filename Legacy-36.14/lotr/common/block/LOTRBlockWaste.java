package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockWaste extends Block {
   private static Random wasteRand = new Random();
   @SideOnly(Side.CLIENT)
   private IIcon[] randomIcons;

   public LOTRBlockWaste() {
      super(Material.field_151578_c);
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149776_m);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.randomIcons = new IIcon[8];

      for(int l = 0; l < this.randomIcons.length; ++l) {
         this.randomIcons[l] = iconregister.func_94245_a(this.func_149641_N() + "_var" + l);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      int hash = i * 25799626 ^ k * 6879038 ^ j;
      hash += side;
      wasteRand.setSeed((long)hash);
      wasteRand.setSeed(wasteRand.nextLong());
      return this.randomIcons[wasteRand.nextInt(this.randomIcons.length)];
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      int hash = i * 334224425 ^ i;
      hash = hash * hash * 245256 + hash * 113549945;
      wasteRand.setSeed((long)hash);
      wasteRand.setSeed(wasteRand.nextLong());
      return this.randomIcons[wasteRand.nextInt(this.randomIcons.length)];
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getWasteRenderID();
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      float f = 0.125F;
      return AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)((float)(j + 1) - f), (double)(k + 1));
   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      double slow = 0.4D;
      entity.field_70159_w *= slow;
      entity.field_70179_y *= slow;
   }

   public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
      return side == ForgeDirection.UP;
   }
}
