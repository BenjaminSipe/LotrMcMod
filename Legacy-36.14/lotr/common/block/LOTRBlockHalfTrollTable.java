package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockHalfTrollTable extends LOTRBlockCraftingTable {
   @SideOnly(Side.CLIENT)
   private IIcon[] tableIcons;

   public LOTRBlockHalfTrollTable() {
      super(Material.field_151576_e, LOTRFaction.HALF_TROLL, 34);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 1) {
         return this.tableIcons[1];
      } else {
         return i == 0 ? Blocks.field_150347_e.func_149691_a(i, 0) : this.tableIcons[0];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.tableIcons = new IIcon[2];
      this.tableIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.tableIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }
}
