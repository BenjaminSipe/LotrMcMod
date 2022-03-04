package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockChandelier extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] chandelierIcons;
   private String[] chandelierNames = new String[]{"bronze", "iron", "silver", "gold", "mithril", "mallornSilver", "woodElven", "orc", "dwarven", "uruk", "highElven", "blueDwarven", "morgul", "mallornBlue", "mallornGold", "mallornGreen"};

   public LOTRBlockChandelier() {
      super(Material.field_151594_q);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.0F);
      this.func_149752_b(2.0F);
      this.func_149672_a(Block.field_149777_j);
      this.func_149715_a(0.9375F);
      this.func_149676_a(0.0625F, 0.1875F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
   }

   public IIcon func_149691_a(int i, int j) {
      if (j >= this.chandelierNames.length) {
         j = 0;
      }

      return this.chandelierIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.chandelierIcons = new IIcon[this.chandelierNames.length];

      for(int i = 0; i < this.chandelierNames.length; ++i) {
         this.chandelierIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.chandelierNames[i]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return 1;
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j + 1, k);
      int meta = world.func_72805_g(i, j + 1, k);
      if (!(block instanceof BlockFence) && !(block instanceof BlockWall)) {
         if (block instanceof BlockSlab && !block.func_149662_c() && (meta & 8) == 0) {
            return true;
         } else if (block instanceof BlockStairs && (meta & 4) == 0) {
            return true;
         } else {
            return block instanceof LOTRBlockOrcChain ? true : world.func_147439_a(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
         }
      } else {
         return true;
      }
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return this.func_149718_j(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.chandelierNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k);
      double d = 0.13D;
      double d1 = 1.0D - d;
      double d2 = 0.6875D;
      this.spawnChandelierParticles(world, (double)i + d, (double)j + d2, (double)k + d, random, meta);
      this.spawnChandelierParticles(world, (double)i + d1, (double)j + d2, (double)k + d1, random, meta);
      this.spawnChandelierParticles(world, (double)i + d, (double)j + d2, (double)k + d1, random, meta);
      this.spawnChandelierParticles(world, (double)i + d1, (double)j + d2, (double)k + d, random, meta);
   }

   private void spawnChandelierParticles(World world, double d, double d1, double d2, Random random, int meta) {
      if (meta != 5 && meta != 13 && meta != 14 && meta != 15) {
         if (meta == 6) {
            String s = "leafRed_" + (10 + random.nextInt(20));
            double d3 = -0.005D + (double)(random.nextFloat() * 0.01F);
            double d4 = -0.005D + (double)(random.nextFloat() * 0.01F);
            double d5 = -0.005D + (double)(random.nextFloat() * 0.01F);
            LOTRMod.proxy.spawnParticle(s, d, d1, d2, d3, d4, d5);
         } else if (meta == 10) {
            LOTRMod.proxy.spawnParticle("elvenGlow", d, d1, d2, 0.0D, 0.0D, 0.0D);
         } else if (meta == 12) {
            double d3 = -0.05D + (double)random.nextFloat() * 0.1D;
            double d4 = 0.1D + (double)random.nextFloat() * 0.1D;
            double d5 = -0.05D + (double)random.nextFloat() * 0.1D;
            LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
         } else {
            world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
            world.func_72869_a("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      } else {
         LOTRBlockTorch torchBlock = null;
         if (meta == 5) {
            torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchSilver;
         } else if (meta == 13) {
            torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchBlue;
         } else if (meta == 14) {
            torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchGold;
         } else if (meta == 15) {
            torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchGreen;
         }

         LOTRBlockTorch.TorchParticle particle = torchBlock.createTorchParticle(random);
         if (particle != null) {
            particle.spawn(d, d1, d2);
         }
      }

   }
}
