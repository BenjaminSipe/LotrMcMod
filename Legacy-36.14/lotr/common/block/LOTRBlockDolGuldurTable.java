package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockDolGuldurTable extends LOTRBlockCraftingTable {
   @SideOnly(Side.CLIENT)
   private IIcon[] tableIcons;

   public LOTRBlockDolGuldurTable() {
      super(Material.field_151576_e, LOTRFaction.DOL_GULDUR, 30);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == 1) {
         return this.tableIcons[1];
      } else {
         return i == 0 ? LOTRMod.brick2.func_149691_a(0, 8) : this.tableIcons[0];
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.tableIcons = new IIcon[2];
      this.tableIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_side");
      this.tableIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_top");
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(20) == 0) {
         for(int l = 0; l < 16; ++l) {
            double d = (double)i + 0.25D + (double)(random.nextFloat() * 0.5F);
            double d1 = (double)j + 1.0D;
            double d2 = (double)k + 0.25D + (double)(random.nextFloat() * 0.5F);
            double d3 = -0.05D + (double)random.nextFloat() * 0.1D;
            double d4 = 0.1D + (double)random.nextFloat() * 0.1D;
            double d5 = -0.05D + (double)random.nextFloat() * 0.1D;
            LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
         }
      }

   }
}
