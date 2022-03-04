package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCommonProxy;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.LOTRSquadrons;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockCommandTable extends BlockContainer {
   @SideOnly(Side.CLIENT)
   private IIcon topIcon;
   @SideOnly(Side.CLIENT)
   private IIcon sideIcon;

   public LOTRBlockCommandTable() {
      super(Material.field_151573_f);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149711_c(2.5F);
      this.func_149672_a(Block.field_149777_j);
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityCommandTable();
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getCommandTableRenderID();
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (entityplayer.func_70093_af()) {
         LOTRTileEntityCommandTable table = (LOTRTileEntityCommandTable)world.func_147438_o(i, j, k);
         if (table != null) {
            if (!world.field_72995_K) {
               table.toggleZoomExp();
            }

            return true;
         }
      }

      ItemStack itemstack = entityplayer.func_71045_bC();
      LOTRCommonProxy var10000;
      if (itemstack != null && itemstack.func_77973_b() instanceof LOTRSquadrons.SquadronItem) {
         if (!world.field_72995_K) {
            var10000 = LOTRMod.proxy;
            LOTRCommonProxy.sendClientsideGUI((EntityPlayerMP)entityplayer, 33, 0, 0, 0);
            world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, this.field_149762_H.func_150495_a(), (this.field_149762_H.func_150497_c() + 1.0F) / 2.0F, this.field_149762_H.func_150494_d() * 0.5F);
         }

         return true;
      } else if (LOTRConquestGrid.conquestEnabled(world)) {
         if (!world.field_72995_K) {
            var10000 = LOTRMod.proxy;
            LOTRCommonProxy.sendClientsideGUI((EntityPlayerMP)entityplayer, 60, 0, 0, 0);
            world.func_72908_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, this.field_149762_H.func_150495_a(), (this.field_149762_H.func_150497_c() + 1.0F) / 2.0F, this.field_149762_H.func_150494_d() * 0.5F);
         }

         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i != 1 && i != 0 ? this.sideIcon : this.topIcon;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.sideIcon = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.topIcon = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }
}
