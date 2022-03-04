package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockBreeTable extends LOTRBlockCraftingTable {
   @SideOnly(Side.CLIENT)
   private IIcon[] tableIcons;

   public LOTRBlockBreeTable() {
      super(Material.field_151575_d, LOTRFaction.BREE, 62);
      this.func_149672_a(Block.field_149766_f);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 1) {
         return this.tableIcons[2];
      } else if (i == 0) {
         return LOTRMod.planks.func_149691_a(0, 9);
      } else {
         return i != 4 && i != 5 ? this.tableIcons[1] : this.tableIcons[0];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.tableIcons = new IIcon[3];
      this.tableIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_side0");
      this.tableIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_side1");
      this.tableIcons[2] = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }
}
