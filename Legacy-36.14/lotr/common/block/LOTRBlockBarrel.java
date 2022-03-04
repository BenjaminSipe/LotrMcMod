package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBarrel;
import lotr.common.item.LOTRItemBottlePoison;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTRBlockBarrel extends BlockContainer {
   @SideOnly(Side.CLIENT)
   private IIcon[] barrelIcons;

   public LOTRBlockBarrel() {
      super(Material.field_151575_d);
      this.func_149647_a(LOTRCreativeTabs.tabFood);
      this.func_149676_a(0.125F, 0.0F, 0.125F, 0.875F, 0.8125F, 0.875F);
      this.func_149711_c(3.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149766_f);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityBarrel();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == -1) {
         return this.barrelIcons[2];
      } else {
         return i < 2 ? this.barrelIcons[1] : this.barrelIcons[0];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.barrelIcons = new IIcon[3];
      this.barrelIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.barrelIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.barrelIcons[2] = iconregister.func_94245_a(this.func_149641_N() + "_tap");
   }

   public boolean func_149686_d() {
      return false;
   }

   public boolean func_149662_c() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getBarrelRenderID();
   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      int rotation = MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      int meta = 0;
      if (rotation == 0) {
         meta = 2;
      } else if (rotation == 1) {
         meta = 5;
      } else if (rotation == 2) {
         meta = 3;
      } else if (rotation == 3) {
         meta = 4;
      }

      world.func_72921_c(i, j, k, meta, 2);
      if (itemstack.func_82837_s()) {
         ((LOTRTileEntityBarrel)world.func_147438_o(i, j, k)).setBarrelName(itemstack.func_82833_r());
      }

   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.func_147438_o(i, j, k);
      ItemStack barrelDrink = barrel.getBrewedDrink();
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      Item item = itemstack == null ? null : itemstack.func_77973_b();
      ItemStack containerItem;
      if (side == world.func_72805_g(i, j, k)) {
         LOTRItemMug.Vessel v;
         if (barrelDrink != null && LOTRItemMug.isItemEmptyDrink(itemstack)) {
            containerItem = barrelDrink.func_77946_l();
            containerItem.field_77994_a = 1;
            v = LOTRItemMug.getVessel(itemstack);
            LOTRItemMug.setVessel(containerItem, v, true);
            --itemstack.field_77994_a;
            if (itemstack.field_77994_a <= 0) {
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, containerItem);
            } else if (!entityplayer.field_71071_by.func_70441_a(containerItem)) {
               entityplayer.func_71019_a(containerItem, false);
            }

            barrel.consumeMugRefill();
            world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
            return true;
         }

         if (itemstack != null && item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
            boolean match = false;
            if (barrel.barrelMode == 0) {
               match = true;
            } else if (barrelDrink != null && barrelDrink.field_77994_a < LOTRBrewingRecipes.BARREL_CAPACITY) {
               match = barrelDrink.func_77973_b() == itemstack.func_77973_b() && LOTRItemMug.getStrength(barrelDrink) == LOTRItemMug.getStrength(itemstack);
            }

            if (match) {
               if (barrelDrink == null) {
                  ItemStack barrelFill = itemstack.func_77946_l();
                  barrelFill.field_77994_a = 1;
                  LOTRItemMug.setVessel(barrelFill, LOTRItemMug.Vessel.MUG, false);
                  barrel.func_70299_a(9, barrelFill);
               } else {
                  ++barrelDrink.field_77994_a;
                  barrel.func_70299_a(9, barrelDrink);
               }

               barrel.barrelMode = 2;
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  v = LOTRItemMug.getVessel(itemstack);
                  ItemStack emptyMug = v.getEmptyVessel();
                  --itemstack.field_77994_a;
                  if (itemstack.field_77994_a <= 0) {
                     entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, emptyMug);
                     entityplayer.field_71070_bA.func_75142_b();
                  } else if (!entityplayer.field_71071_by.func_70441_a(emptyMug)) {
                     entityplayer.func_71019_a(emptyMug, false);
                  }
               }

               world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
               return true;
            }
         }
      }

      if (itemstack != null && item instanceof LOTRItemBottlePoison && barrel.canPoisonBarrel()) {
         if (!world.field_72995_K) {
            barrel.poisonBarrel(entityplayer);
            if (!entityplayer.field_71075_bZ.field_75098_d) {
               containerItem = itemstack.func_77973_b().getContainerItem(itemstack);
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, containerItem);
            }

            entityplayer.field_71070_bA.func_75142_b();
            ((EntityPlayerMP)entityplayer).func_71120_a(entityplayer.field_71070_bA);
         }

         return true;
      } else {
         if (!world.field_72995_K) {
            entityplayer.openGui(LOTRMod.instance, 16, world, i, j, k);
         }

         return true;
      }
   }

   public void func_149681_a(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
      if (entityplayer.field_71075_bZ.field_75098_d) {
         meta |= 8;
         world.func_72921_c(i, j, k, meta, 4);
      }

      super.func_149681_a(world, i, j, k, meta, entityplayer);
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.func_147438_o(i, j, k);
      if (barrel != null) {
         ItemStack brewing = barrel.func_70301_a(9);
         barrel.func_70299_a(9, (ItemStack)null);
         LOTRMod.dropContainerItems(barrel, world, i, j, k);

         for(int slot = 0; slot < barrel.func_70302_i_(); ++slot) {
            barrel.func_70299_a(slot, (ItemStack)null);
         }

         barrel.func_70299_a(9, brewing);
         if (!world.field_72995_K && (meta & 8) == 0) {
            this.func_149642_a(world, i, j, k, this.getBarrelDrop(world, i, j, k, meta));
         }
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   public ItemStack getBarrelDrop(World world, int i, int j, int k, int metadata) {
      ItemStack itemstack = new ItemStack(Item.func_150898_a(this));
      LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.func_147438_o(i, j, k);
      if (barrel != null && barrel.barrelMode != 0) {
         LOTRItemBarrel.setBarrelDataFromTE(itemstack, barrel);
      }

      return itemstack;
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
      world.func_147471_g(i, j, k);
      return this.getBarrelDrop(world, i, j, k, world.func_72805_g(i, j, k));
   }

   public Item func_149650_a(int i, Random random, int j) {
      return null;
   }
}
