package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRBlockDoor extends BlockDoor {
   public LOTRBlockDoor() {
      this(Material.field_151575_d);
      this.func_149672_a(Block.field_149766_f);
      this.func_149711_c(3.0F);
   }

   public LOTRBlockDoor(Material material) {
      super(material);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
   }

   public Item func_149650_a(int i, Random random, int j) {
      return (i & 8) != 0 ? null : Item.func_150898_a(this);
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return Item.func_150898_a(this);
   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getDoorRenderID();
   }
}
