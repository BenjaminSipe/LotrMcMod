package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRDimension;
import lotr.common.LOTRMod;
import lotr.common.world.LOTRUtumnoLevel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRItemUtumnoKey extends Item {
   @SideOnly(Side.CLIENT)
   private static IIcon[] keyIcons;
   private static String[] keyTypes = new String[]{"ice", "obsidian", "ice0", "ice1", "ice2", "obsidian0", "obsidian1", "obsidian2"};

   public LOTRItemUtumnoKey() {
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
      this.func_77625_d(1);
      this.func_77656_e(0);
      this.func_77627_a(true);
   }

   public static ItemStack getRandomKeyPart(Random rand) {
      ItemStack itemstack = new ItemStack(LOTRMod.utumnoKey);
      itemstack.func_77964_b(MathHelper.func_76136_a(rand, 2, keyTypes.length - 1));
      return itemstack;
   }

   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
      if (LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO && side == 1) {
         Block block = world.func_147439_a(i, j, k);
         int meta = world.func_72805_g(i, j, k);
         LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
         LOTRUtumnoLevel keyUsageLevel = null;
         if (itemstack.func_77960_j() == 0) {
            keyUsageLevel = LOTRUtumnoLevel.ICE;
         } else if (itemstack.func_77960_j() == 1) {
            keyUsageLevel = LOTRUtumnoLevel.OBSIDIAN;
         }

         int l;
         if (utumnoLevel == keyUsageLevel && j < utumnoLevel.corridorBaseLevels[0] && block.func_149662_c()) {
            if (!world.field_72995_K) {
               world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "random.explode", 2.0F, 0.2F + world.field_73012_v.nextFloat() * 0.2F);
            }

            for(l = 0; l < 60; ++l) {
               world.func_72869_a("blockcrack_" + Block.func_149682_b(block) + "_" + meta, (double)i + 0.5D + world.field_73012_v.nextGaussian() * 1.0D, (double)j + 1.5D, (double)k + 0.5D + world.field_73012_v.nextGaussian() * 1.0D, 0.0D, 0.0D, 0.0D);
            }

            if (!world.field_72995_K) {
               LOTRUtumnoLevel targetLevel = LOTRUtumnoLevel.values()[keyUsageLevel.ordinal() + 1];
               int stair = 0;
               int stairX = i - 1;
               int stairZ = k - 1;

               for(int stairY = j; stairY >= targetLevel.corridorBaseLevels[0] && (LOTRUtumnoLevel.forY(stairY) != targetLevel || !world.func_147437_c(stairX, stairY + 1, stairZ) || !World.func_147466_a(world, stairX, stairY, stairZ)); stair %= 8) {
                  if (stair != 0 && stair != 2 && stair != 4 && stair != 6) {
                     if (stair == 1) {
                        world.func_147465_d(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 1, 3);
                     } else if (stair == 3) {
                        world.func_147465_d(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 3, 3);
                     } else if (stair == 5) {
                        world.func_147465_d(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 0, 3);
                     } else if (stair == 7) {
                        world.func_147465_d(stairX, stairY, stairZ, keyUsageLevel.brickStairBlock, 2, 3);
                     }
                  } else {
                     world.func_147465_d(stairX, stairY, stairZ, keyUsageLevel.brickBlock, keyUsageLevel.brickMeta, 3);
                  }

                  for(int j1 = 1; j1 <= 3; ++j1) {
                     world.func_147465_d(stairX, stairY + j1, stairZ, Blocks.field_150350_a, 0, 3);
                  }

                  world.func_147465_d(i, stairY, k, keyUsageLevel.brickGlowBlock, keyUsageLevel.brickGlowMeta, 3);
                  if (stair <= 1) {
                     ++stairX;
                  } else if (stair <= 3) {
                     ++stairZ;
                  } else if (stair <= 5) {
                     --stairX;
                  } else if (stair <= 7) {
                     --stairZ;
                  }

                  if (stair % 2 == 1) {
                     --stairY;
                  }

                  ++stair;
               }
            }

            --itemstack.field_77994_a;
            return true;
         } else {
            for(l = 0; l < 8; ++l) {
               double d = (double)i + (double)world.field_73012_v.nextFloat();
               double d1 = (double)j + 1.0D;
               double d2 = (double)k + (double)world.field_73012_v.nextFloat();
               world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
            }

            return false;
         }
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      if (i >= keyIcons.length) {
         i = 0;
      }

      return keyIcons[i];
   }

   public String func_77667_c(ItemStack itemstack) {
      return super.func_77658_a() + "." + itemstack.func_77960_j();
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      keyIcons = new IIcon[keyTypes.length];

      for(int i = 0; i < keyTypes.length; ++i) {
         keyIcons[i] = iconregister.func_94245_a(this.func_111208_A() + "_" + keyTypes[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_150895_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < keyTypes.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
