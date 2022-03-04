package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTRBlockUtumnoBrickEntrance extends Block {
   public LOTRBlockUtumnoBrickEntrance() {
      super(Material.field_151576_e);
      this.func_149711_c(-1.0F);
      this.func_149752_b(Float.MAX_VALUE);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return LOTRMod.utumnoBrick.func_149691_a(i, 2);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister register) {
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(LOTRMod.utumnoBrick, 1, 2));
      return drops;
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer player) {
      return new ItemStack(LOTRMod.utumnoBrick, 1, 2);
   }
}
