package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockGateDwarven extends LOTRBlockGate {
   public LOTRBlockGateDwarven() {
      super(Material.field_151576_e, false);
      this.func_149711_c(4.0F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149769_e);
      this.setFullBlock();
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      LOTRConnectedTextures.registerNonConnectedGateIcons(iconregister, this, 0, Blocks.field_150348_b.func_149691_a(0, 0).func_94215_i());
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149673_e(IBlockAccess world, int i, int j, int k, int side) {
      boolean open = isGateOpen(world, i, j, k);
      return open ? LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false) : Blocks.field_150348_b.func_149691_a(side, 0);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return Blocks.field_150348_b.func_149691_a(i, 0);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      boolean flag = super.func_149727_a(world, i, j, k, entityplayer, side, f, f1, f2);
      if (flag && !world.field_72995_K) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDwarvenDoor);
      }

      return flag;
   }
}
