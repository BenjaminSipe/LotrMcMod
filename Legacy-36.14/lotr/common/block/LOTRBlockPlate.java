package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockPlate extends BlockContainer {
   public static final SoundType soundTypePlate = new SoundType("lotr:plate", 1.0F, 1.0F) {
      private Random rand = new Random();

      public float func_150494_d() {
         return super.func_150494_d();
      }

      public String func_150495_a() {
         return "lotr:block.plate.break";
      }

      public String func_150498_e() {
         return Block.field_149769_e.func_150498_e();
      }

      public String func_150496_b() {
         return Block.field_149769_e.func_150496_b();
      }
   };
   @SideOnly(Side.CLIENT)
   private IIcon[] plateIcons;
   private Item plateItem;

   public LOTRBlockPlate() {
      super(Material.field_151594_q);
      this.func_149711_c(0.0F);
      this.func_149676_a(0.125F, 0.0F, 0.125F, 0.875F, 0.125F, 0.875F);
   }

   public void setPlateItem(Item item) {
      this.plateItem = item;
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityPlate();
   }

   public Item func_149650_a(int i, Random random, int j) {
      return this.plateItem;
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer entityplayer) {
      ItemStack foodItem = getFoodItem(world, i, j, k);
      if (foodItem != null) {
         ItemStack copy = foodItem.func_77946_l();
         copy.field_77994_a = 1;
         return copy;
      } else {
         int meta = world.func_72805_g(i, j, k);
         return new ItemStack(this.func_149650_a(meta, world.field_73012_v, 0), 1, this.func_149692_a(meta));
      }
   }

   public void func_149749_a(World world, int i, int j, int k, Block block, int meta) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (!world.field_72995_K && tileentity instanceof LOTRTileEntityPlate) {
         LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
         ItemStack foodItem = plate.getFoodItem();
         if (foodItem != null) {
            this.func_149642_a(world, i, j, k, foodItem);
         }
      }

      super.func_149749_a(world, i, j, k, block, meta);
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getPlateRenderID();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i == 1 ? this.plateIcons[0] : this.plateIcons[1];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.plateIcons = new IIcon[2];
      this.plateIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_top");
      this.plateIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_base");
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
      ItemStack itemstack = entityplayer.func_71045_bC();
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityPlate) {
         LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
         ItemStack plateItem = plate.getFoodItem();
         if (plateItem == null && LOTRTileEntityPlate.isValidFoodItem(itemstack)) {
            if (!world.field_72995_K) {
               plateItem = itemstack.func_77946_l();
               plateItem.field_77994_a = 1;
               plate.setFoodItem(plateItem);
            }

            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
            }

            return true;
         }

         if (plateItem != null) {
            if (itemstack != null && itemstack.func_77969_a(plateItem) && ItemStack.func_77970_a(itemstack, plateItem)) {
               if (plateItem.field_77994_a < plateItem.func_77976_d()) {
                  if (!world.field_72995_K) {
                     ++plateItem.field_77994_a;
                     plate.setFoodItem(plateItem);
                  }

                  if (!entityplayer.field_71075_bZ.field_75098_d) {
                     --itemstack.field_77994_a;
                  }

                  return true;
               }
            } else if (entityplayer.func_71043_e(false)) {
               plateItem.func_77973_b().func_77654_b(plateItem, world, entityplayer);
               if (!world.field_72995_K) {
                  plate.setFoodItem(plateItem);
               }

               return true;
            }
         }
      }

      return false;
   }

   public static ItemStack getFoodItem(World world, int i, int j, int k) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      return tileentity instanceof LOTRTileEntityPlate ? ((LOTRTileEntityPlate)tileentity).getFoodItem() : null;
   }

   public void dropPlateItem(LOTRTileEntityPlate plate) {
      this.dropPlateItem(plate, plate.getFoodItem());
   }

   public void dropOnePlateItem(LOTRTileEntityPlate plate) {
      ItemStack item = plate.getFoodItem().func_77946_l();
      item.field_77994_a = 1;
      this.dropPlateItem(plate, item);
   }

   private void dropPlateItem(LOTRTileEntityPlate plate, ItemStack itemstack) {
      this.func_149642_a(plate.func_145831_w(), plate.field_145851_c, plate.field_145848_d, plate.field_145849_e, itemstack);
   }
}
