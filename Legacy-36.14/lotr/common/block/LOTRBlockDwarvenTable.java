package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockDwarvenTable extends LOTRBlockCraftingTable {
   @SideOnly(Side.CLIENT)
   private IIcon[] tableIcons;

   public LOTRBlockDwarvenTable() {
      super(Material.field_151576_e, LOTRFaction.DURINS_FOLK, 4);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 1) {
         return this.tableIcons[2];
      } else if (i == 0) {
         return LOTRMod.brick.func_149691_a(0, 6);
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
