package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockQuenditeGrass extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon grassSideIcon;

   public LOTRBlockQuenditeGrass() {
      super(Material.field_151577_b);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 0) {
         return Blocks.field_150346_d.func_149691_a(i, j);
      } else {
         return i == 1 ? this.field_149761_L : this.grassSideIcon;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.field_149761_L = iconregister.func_94245_a("lotr:quenditeGrass_top");
      this.grassSideIcon = iconregister.func_94245_a("lotr:quenditeGrass_side");
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(Blocks.field_150346_d);
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(8) == 0) {
         double d = (double)((float)i + random.nextFloat());
         double d1 = (double)j + 1.0D;
         double d2 = (double)((float)k + random.nextFloat());
         LOTRMod.proxy.spawnParticle("quenditeSmoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
      }

   }
}
