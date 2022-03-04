package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.tileentity.LOTRTileEntityTrollTotem;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockTrollTotem extends BlockContainer {
   public LOTRBlockTrollTotem() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityTrollTotem();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getTrollTotemRenderID();
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150348_b.func_149691_a(i, 0);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entity, ItemStack itemstack) {
      int meta = itemstack.func_77960_j();
      int rotation = MathHelper.func_76128_c((double)(entity.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
      world.func_72921_c(i, j, k, meta | rotation << 2, 2);
      if (meta == 0 && world.func_147439_a(i, j - 1, k) == this && (world.func_72805_g(i, j - 1, k) & 3) == 1) {
         world.func_72921_c(i, j - 1, k, 1 | rotation << 2, 3);
         if (world.func_147439_a(i, j - 2, k) == this && (world.func_72805_g(i, j - 2, k) & 3) == 2) {
            world.func_72921_c(i, j - 2, k, 2 | rotation << 2, 3);
         }
      }

      if (meta == 1) {
         if (world.func_147439_a(i, j - 1, k) == this && (world.func_72805_g(i, j - 1, k) & 3) == 2) {
            world.func_72921_c(i, j - 1, k, 2 | rotation << 2, 3);
         }

         if (world.func_147439_a(i, j + 1, k) == this && (world.func_72805_g(i, j + 1, k) & 3) == 0) {
            world.func_72921_c(i, j + 1, k, 0 | rotation << 2, 3);
         }
      }

      if (meta == 2 && world.func_147439_a(i, j + 1, k) == this && (world.func_72805_g(i, j + 1, k) & 3) == 1) {
         world.func_72921_c(i, j + 1, k, 1 | rotation << 2, 3);
         if (world.func_147439_a(i, j + 2, k) == this && (world.func_72805_g(i, j + 2, k) & 3) == 0) {
            world.func_72921_c(i, j + 2, k, 0 | rotation << 2, 3);
         }
      }

   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if ((world.func_72805_g(i, j, k) & 3) == 0) {
         TileEntity tileentity = world.func_147438_o(i, j, k);
         if (tileentity instanceof LOTRTileEntityTrollTotem) {
            LOTRTileEntityTrollTotem totem = (LOTRTileEntityTrollTotem)tileentity;
            if (totem.canSummon() && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.ANGMAR) < 0.0F) {
               ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
               if (itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone")) {
                  if (!entityplayer.field_71075_bZ.field_75098_d) {
                     --itemstack.field_77994_a;
                     if (itemstack.field_77994_a <= 0) {
                        entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
                     }
                  }

                  totem.summon();
                  return true;
               }
            }
         }
      }

      return false;
   }

   public int func_149692_a(int i) {
      return i & 3;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i <= 2; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
