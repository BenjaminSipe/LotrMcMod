package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBottlePoison;
import lotr.common.item.LOTRItemMug;
import lotr.common.tileentity.LOTRTileEntityMug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTRBlockMug extends BlockContainer {
   public static final float MUG_SCALE = 0.75F;

   public LOTRBlockMug() {
      this(3.0F, 8.0F);
   }

   public LOTRBlockMug(float f, float f1) {
      super(Material.field_151594_q);
      f /= 16.0F;
      f1 /= 16.0F;
      f *= 0.75F;
      f1 *= 0.75F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
      this.func_149711_c(0.0F);
      this.func_149672_a(Block.field_149766_f);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityMug();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return -1;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150344_f.func_149691_a(i, 0);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j - 1, k);
      return block.canPlaceTorchOnTop(world, i, j - 1, k);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return this.func_149718_j(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         this.func_149697_b(world, i, j, k, meta, 0);
         world.func_147468_f(i, j, k);
      }

   }

   public void func_149681_a(World world, int i, int j, int k, int meta, EntityPlayer entityplayer) {
      if (entityplayer.field_71075_bZ.field_75098_d) {
         meta |= 4;
         world.func_72921_c(i, j, k, meta, 4);
      }

      this.func_149697_b(world, i, j, k, meta, 0);
      super.func_149681_a(world, i, j, k, meta, entityplayer);
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      if ((meta & 4) == 0) {
         ItemStack itemstack = getMugItem(world, i, j, k);
         LOTRTileEntityMug mug = (LOTRTileEntityMug)world.func_147438_o(i, j, k);
         if (mug != null) {
            drops.add(itemstack);
         }
      }

      return drops;
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
      return getMugItem(world, i, j, k);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack itemstack = entityplayer.func_71045_bC();
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity != null && tileentity instanceof LOTRTileEntityMug) {
         LOTRTileEntityMug mug = (LOTRTileEntityMug)tileentity;
         ItemStack mugItem = mug.getMugItem();
         ItemStack containerItem;
         if (!mug.isEmpty() && LOTRItemMug.isItemEmptyDrink(itemstack)) {
            containerItem = mugItem.func_77946_l();
            LOTRItemMug.Vessel v = LOTRItemMug.getVessel(itemstack);
            LOTRItemMug.setVessel(containerItem, v, true);
            if (entityplayer.field_71075_bZ.field_75098_d) {
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, containerItem);
            } else {
               --itemstack.field_77994_a;
               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, containerItem);
               } else if (!entityplayer.field_71071_by.func_70441_a(containerItem)) {
                  entityplayer.func_71019_a(containerItem, false);
               }
            }

            mug.setEmpty();
            world.func_72956_a(entityplayer, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
            return true;
         }

         if (mug.isEmpty() && LOTRItemMug.isItemFullDrink(itemstack)) {
            containerItem = LOTRItemMug.getVessel(itemstack).getEmptyVessel();
            entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, containerItem);
            ItemStack mugFill = itemstack.func_77946_l();
            mugFill.field_77994_a = 1;
            mug.setMugItem(mugFill);
            world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, "lotr:item.mug_fill", 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
            return true;
         }

         if (!mug.isEmpty()) {
            if (itemstack != null && itemstack.func_77973_b() instanceof LOTRItemBottlePoison && mug.canPoisonMug()) {
               if (!world.field_72995_K) {
                  mug.poisonMug(entityplayer);
                  if (!entityplayer.field_71075_bZ.field_75098_d) {
                     containerItem = itemstack.func_77973_b().getContainerItem(itemstack);
                     entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, containerItem);
                  }

                  entityplayer.field_71070_bA.func_75142_b();
                  ((EntityPlayerMP)entityplayer).func_71120_a(entityplayer.field_71070_bA);
               }

               return true;
            }

            containerItem = LOTRItemMug.getEquivalentDrink(mugItem);
            Item eqItem = containerItem.func_77973_b();
            boolean canDrink = false;
            if (eqItem instanceof LOTRItemMug) {
               canDrink = ((LOTRItemMug)eqItem).canPlayerDrink(entityplayer);
            }

            if (canDrink) {
               ItemStack mugItemResult = mugItem.func_77950_b(world, entityplayer);
               ForgeEventFactory.onItemUseFinish(entityplayer, mugItem, mugItem.func_77988_m(), mugItemResult);
               mug.setEmpty();
               world.func_147471_g(i, j, k);
               world.func_72956_a(entityplayer, "random.drink", 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
               return true;
            }
         }
      }

      return false;
   }

   public static ItemStack getMugItem(World world, int i, int j, int k) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityMug) {
         LOTRTileEntityMug mug = (LOTRTileEntityMug)tileentity;
         return mug.getMugItem();
      } else {
         return new ItemStack(LOTRMod.mug);
      }
   }

   public static void setMugItem(World world, int i, int j, int k, ItemStack itemstack, LOTRItemMug.Vessel vessel) {
      TileEntity te = world.func_147438_o(i, j, k);
      if (te instanceof LOTRTileEntityMug) {
         LOTRTileEntityMug mug = (LOTRTileEntityMug)te;
         mug.setMugItem(itemstack);
         mug.setVessel(vessel);
      }

   }
}
