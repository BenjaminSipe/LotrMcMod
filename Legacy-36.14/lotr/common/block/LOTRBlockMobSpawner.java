package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTRBlockMobSpawner extends BlockMobSpawner {
   public LOTRBlockMobSpawner() {
      this.func_149647_a(LOTRCreativeTabs.tabSpawn);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (entityplayer.field_71075_bZ.field_75098_d) {
         entityplayer.openGui(LOTRMod.instance, 6, world, i, j, k);
         return true;
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150474_ac.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   public TileEntity func_149915_a(World world, int i) {
      return new LOTRTileEntityMobSpawner();
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k) {
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity != null && tileentity instanceof LOTRTileEntityMobSpawner) {
         LOTRTileEntityMobSpawner spawner = (LOTRTileEntityMobSpawner)tileentity;
         return new ItemStack(this, 1, LOTREntities.getIDFromString(spawner.getEntityClassName()));
      } else {
         return null;
      }
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getMobSpawnerRenderID();
   }
}
