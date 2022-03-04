package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemEntDraught;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTREntJarRecipes;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockEntJar extends BlockContainer {
   @SideOnly(Side.CLIENT)
   private IIcon[] jarIcons;

   public LOTRBlockEntJar() {
      super(Material.field_151571_B);
      this.func_149676_a(0.25F, 0.0F, 0.25F, 0.75F, 0.875F, 0.75F);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityEntJar();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getEntJarRenderID();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i != 0 && i != 1 ? this.jarIcons[1] : this.jarIcons[0];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.jarIcons = new IIcon[2];
      this.jarIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.jarIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_side");
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
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

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack itemstack = entityplayer.func_70694_bm();
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityEntJar) {
         LOTRTileEntityEntJar jar = (LOTRTileEntityEntJar)tileentity;
         if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemEntDraught && jar.fillFromBowl(itemstack)) {
            if (!entityplayer.field_71075_bZ.field_75098_d) {
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, new ItemStack(Items.field_151054_z));
            }

            world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
            return true;
         }

         if (jar.drinkMeta >= 0) {
            ItemStack drink = new ItemStack(LOTRMod.entDraught, 1, jar.drinkMeta);
            if (itemstack != null && itemstack.func_77973_b() == Items.field_151054_z) {
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  --itemstack.field_77994_a;
               }

               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, drink.func_77946_l());
               } else if (!entityplayer.field_71071_by.func_70441_a(drink.func_77946_l())) {
                  entityplayer.func_71019_a(drink.func_77946_l(), false);
               }

               world.func_72956_a(entityplayer, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
               jar.consume();
               return true;
            }
         } else if (itemstack != null) {
            BiomeGenBase biome = world.func_72807_a(i, k);
            if (biome instanceof LOTRBiomeGenFangorn && jar.drinkAmount > 0) {
               ItemStack draught = LOTREntJarRecipes.findMatchingRecipe(itemstack);
               if (draught != null) {
                  jar.drinkMeta = draught.func_77960_j();
                  if (!entityplayer.field_71075_bZ.field_75098_d) {
                     --itemstack.field_77994_a;
                  }

                  if (!world.field_72995_K) {
                     world.func_72926_e(2005, i, j, k, 0);
                  }

                  return true;
               }
            }

            int var15;
            int var16;
            LOTRItemMug.Vessel vessel;
            ItemStack vesselEmpty;
            ItemStack vesselFull;
            LOTRItemMug.Vessel[] var21;
            if (jar.drinkAmount > 0) {
               if (this.tryTakeWaterFromJar(jar, world, entityplayer, new ItemStack(Items.field_151133_ar), new ItemStack(Items.field_151131_as), LOTRTileEntityEntJar.MAX_CAPACITY)) {
                  return true;
               }

               var21 = LOTRItemMug.Vessel.values();
               var15 = var21.length;

               for(var16 = 0; var16 < var15; ++var16) {
                  vessel = var21[var16];
                  vesselEmpty = vessel.getEmptyVessel();
                  vesselFull = new ItemStack(LOTRMod.mugWater);
                  LOTRItemMug.setVessel(vesselFull, vessel, true);
                  if (this.tryTakeWaterFromJar(jar, world, entityplayer, vesselEmpty, vesselFull, 1)) {
                     return true;
                  }
               }
            }

            if (jar.drinkAmount < LOTRTileEntityEntJar.MAX_CAPACITY) {
               if (this.tryAddWaterToJar(jar, world, entityplayer, new ItemStack(Items.field_151131_as), new ItemStack(Items.field_151133_ar), LOTRTileEntityEntJar.MAX_CAPACITY)) {
                  return true;
               }

               var21 = LOTRItemMug.Vessel.values();
               var15 = var21.length;

               for(var16 = 0; var16 < var15; ++var16) {
                  vessel = var21[var16];
                  vesselEmpty = vessel.getEmptyVessel();
                  vesselFull = new ItemStack(LOTRMod.mugWater);
                  LOTRItemMug.setVessel(vesselFull, vessel, true);
                  if (this.tryAddWaterToJar(jar, world, entityplayer, vesselFull, vesselEmpty, 1)) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean tryTakeWaterFromJar(LOTRTileEntityEntJar jar, World world, EntityPlayer entityplayer, ItemStack container, ItemStack filled, int amount) {
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      if (itemstack.func_77973_b() == container.func_77973_b() && itemstack.func_77960_j() == container.func_77960_j()) {
         for(int i = 0; i < amount; ++i) {
            jar.consume();
         }

         world.func_72956_a(entityplayer, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            --itemstack.field_77994_a;
            if (itemstack.field_77994_a <= 0) {
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, filled.func_77946_l());
            } else if (!entityplayer.field_71071_by.func_70441_a(filled.func_77946_l())) {
               entityplayer.func_71019_a(filled.func_77946_l(), false);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean tryAddWaterToJar(LOTRTileEntityEntJar jar, World world, EntityPlayer entityplayer, ItemStack filled, ItemStack container, int amount) {
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      if (itemstack.func_77973_b() == filled.func_77973_b() && itemstack.func_77960_j() == filled.func_77960_j()) {
         for(int i = 0; i < amount; ++i) {
            jar.fillWithWater();
         }

         world.func_72956_a(entityplayer, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, container.func_77946_l());
         }

         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(4) == 0) {
         TileEntity tileentity = world.func_147438_o(i, j, k);
         if (tileentity instanceof LOTRTileEntityEntJar) {
            LOTRTileEntityEntJar jar = (LOTRTileEntityEntJar)tileentity;
            if (jar.drinkMeta >= 0) {
               double d = (double)i + 0.25D + (double)(random.nextFloat() * 0.5F);
               double d1 = (double)j + 1.0D;
               double d2 = (double)k + 0.25D + (double)(random.nextFloat() * 0.5F);
               world.func_72869_a("happyVillager", d, d1, d2, 0.0D, 0.2D, 0.0D);
            }
         }
      }

   }
}
