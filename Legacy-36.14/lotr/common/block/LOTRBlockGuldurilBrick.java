package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTRBlockGuldurilBrick extends Block {
   public LOTRBlockGuldurilBrick() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(3.0F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149769_e);
      this.func_149715_a(0.75F);
   }

   public static int guldurilMetaForBlock(Block block, int i) {
      if (block == null) {
         return -1;
      } else if (block == LOTRMod.brick && i == 0) {
         return 0;
      } else if (block == LOTRMod.brick && i == 7) {
         return 1;
      } else if (block == LOTRMod.brick2 && i == 0) {
         return 2;
      } else if (block == LOTRMod.brick2 && i == 1) {
         return 3;
      } else if (block == LOTRMod.brick2 && i == 8) {
         return 4;
      } else if (block == LOTRMod.brick2 && i == 9) {
         return 5;
      } else if (block == LOTRMod.brick && i == 1) {
         return 6;
      } else if (block == LOTRMod.brick && i == 2) {
         return 7;
      } else if (block == LOTRMod.brick && i == 3) {
         return 8;
      } else {
         return block == LOTRMod.brick2 && i == 11 ? 9 : -1;
      }
   }

   public static ItemStack blockForGuldurilMeta(int i) {
      if (i == 0) {
         return new ItemStack(LOTRMod.brick, 1, 0);
      } else if (i == 1) {
         return new ItemStack(LOTRMod.brick, 1, 7);
      } else if (i == 2) {
         return new ItemStack(LOTRMod.brick2, 1, 0);
      } else if (i == 3) {
         return new ItemStack(LOTRMod.brick2, 1, 1);
      } else if (i == 4) {
         return new ItemStack(LOTRMod.brick2, 1, 8);
      } else if (i == 5) {
         return new ItemStack(LOTRMod.brick2, 1, 9);
      } else if (i == 6) {
         return new ItemStack(LOTRMod.brick, 1, 1);
      } else if (i == 7) {
         return new ItemStack(LOTRMod.brick, 1, 2);
      } else if (i == 8) {
         return new ItemStack(LOTRMod.brick, 1, 3);
      } else {
         return i == 9 ? new ItemStack(LOTRMod.brick2, 1, 11) : null;
      }
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
      return new ItemStack(this, 1, world.func_72805_g(i, j, k));
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      ItemStack itemstack = blockForGuldurilMeta(j);
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (item instanceof ItemBlock) {
            Block block = ((ItemBlock)item).field_150939_a;
            int meta = itemstack.func_77960_j();
            return block.func_149691_a(i, meta);
         }
      }

      return LOTRMod.brick.func_149691_a(i, 0);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getGuldurilRenderID();
   }

   public ArrayList getDrops(World world, int i, int j, int k, int metadata, int fortune) {
      ArrayList drops = new ArrayList();
      ItemStack drop = blockForGuldurilMeta(metadata);
      if (drop != null) {
         drops.add(drop);
      }

      return drops;
   }

   public boolean hasTileEntity(int metadata) {
      return true;
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new LOTRTileEntityGulduril();
   }

   protected boolean func_149700_E() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i <= 9; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
