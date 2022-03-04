package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockWoodElvenTable extends LOTRBlockCraftingTable {
   @SideOnly(Side.CLIENT)
   private IIcon[] tableIcons;

   public LOTRBlockWoodElvenTable() {
      super(Material.field_151575_d, LOTRFaction.WOOD_ELF, 12);
      this.func_149672_a(Block.field_149766_f);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 1) {
         return this.tableIcons[1];
      } else {
         return i == 0 ? LOTRMod.planks.func_149691_a(2, 2) : this.tableIcons[0];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.tableIcons = new IIcon[2];
      this.tableIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.tableIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }
}
