package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

public abstract class LOTRBlockSaplingBase extends LOTRBlockFlower {
   @SideOnly(Side.CLIENT)
   private IIcon[] saplingIcons;
   private String[] saplingNames;

   public LOTRBlockSaplingBase() {
      float f = 0.4F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   public void setSaplingNames(String... s) {
      this.saplingNames = s;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      j &= 7;
      if (j >= this.saplingNames.length) {
         j = 0;
      }

      return this.saplingIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.saplingIcons = new IIcon[this.saplingNames.length];

      for(int i = 0; i < this.saplingNames.length; ++i) {
         this.saplingIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.saplingNames[i]);
      }

   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (!world.field_72995_K) {
         super.func_149674_a(world, i, j, k, random);
         if (world.func_72957_l(i, j + 1, k) >= 9 && random.nextInt(7) == 0) {
            this.incrementGrowth(world, i, j, k, random);
         }
      }

   }

   public void incrementGrowth(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k);
      if ((meta & 8) == 0) {
         world.func_72921_c(i, j, k, meta | 8, 4);
      } else {
         if (!TerrainGen.saplingGrowTree(world, random, i, j, k)) {
            return;
         }

         this.growTree(world, i, j, k, random);
      }

   }

   public abstract void growTree(World var1, int var2, int var3, int var4, Random var5);

   public boolean isSameSapling(World world, int i, int j, int k, int meta) {
      return isSameSapling(world, i, j, k, this, meta);
   }

   public static boolean isSameSapling(World world, int i, int j, int k, Block block, int meta) {
      if (world.func_147439_a(i, j, k) == block) {
         int blockMeta = world.func_72805_g(i, j, k);
         return (blockMeta & 7) == meta;
      } else {
         return false;
      }
   }

   public static int[] findPartyTree(World world, int i, int j, int k, Block block, int meta) {
      return findSaplingSquare(world, i, j, k, block, meta, -1, 1, -2, 2);
   }

   public static int[] findSaplingSquare(World world, int i, int j, int k, Block block, int meta, int squareMin, int squareMax, int searchMin, int searchMax) {
      for(int i1 = searchMin; i1 <= searchMax; ++i1) {
         for(int k1 = searchMin; k1 <= searchMax; ++k1) {
            boolean canGenerate = true;

            label39:
            for(int i2 = squareMin; i2 <= squareMax; ++i2) {
               for(int k2 = squareMin; k2 <= squareMax; ++k2) {
                  int i3 = i + i1 + i2;
                  int k3 = k + k1 + k2;
                  if (!isSameSapling(world, i3, j, k3, block, meta)) {
                     canGenerate = false;
                     break label39;
                  }
               }
            }

            if (canGenerate) {
               return new int[]{i1, k1};
            }
         }
      }

      return null;
   }

   public static int[] findCrossShape(World world, int i, int j, int k, Block block, int meta) {
      for(int i1 = -2; i1 <= 2; ++i1) {
         for(int k1 = -2; k1 <= 2; ++k1) {
            if (Math.abs(i1) == 0 || Math.abs(k1) == 0) {
               boolean canGenerate = true;

               label50:
               for(int i2 = -1; i2 <= 1; ++i2) {
                  for(int k2 = -1; k2 <= 1; ++k2) {
                     if (Math.abs(i2) == 0 || Math.abs(k2) == 0) {
                        int i3 = i + i1 + i2;
                        int k3 = k + k1 + k2;
                        if (!isSameSapling(world, i3, j, k3, block, meta)) {
                           canGenerate = false;
                           break label50;
                        }
                     }
                  }
               }

               if (canGenerate) {
                  return new int[]{i1, k1};
               }
            }
         }
      }

      return null;
   }

   public int func_149692_a(int i) {
      return i & 7;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.saplingNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public int func_149645_b() {
      return 1;
   }
}
