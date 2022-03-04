package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.animal.LOTREntityTermite;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class LOTRBlockTermite extends Block {
   @SideOnly(Side.CLIENT)
   protected IIcon sideIcon;
   @SideOnly(Side.CLIENT)
   protected IIcon topIcon;

   public LOTRBlockTermite() {
      super(Material.field_151578_c);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(0.5F);
      this.func_149752_b(3.0F);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i != 0 && i != 1 ? this.sideIcon : this.topIcon;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.topIcon = iconregister.func_94245_a(this.func_149641_N());
      this.sideIcon = iconregister.func_94245_a(this.func_149641_N() + "_side");
   }

   public void func_149664_b(World world, int i, int j, int k, int meta) {
      if (!world.field_72995_K && meta == 0 && world.field_73012_v.nextBoolean()) {
         int termites = 1 + world.field_73012_v.nextInt(3);

         for(int l = 0; l < termites; ++l) {
            this.spawnTermite(world, i, j, k);
         }
      }

   }

   public void onBlockExploded(World world, int i, int j, int k, Explosion explosion) {
      int meta = world.func_72805_g(i, j, k);
      if (!world.field_72995_K && meta == 0 && world.field_73012_v.nextBoolean()) {
         this.spawnTermite(world, i, j, k);
      }

      super.onBlockExploded(world, i, j, k, explosion);
   }

   private void spawnTermite(World world, int i, int j, int k) {
      LOTREntityTermite termite = new LOTREntityTermite(world);
      termite.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
      world.func_72838_d(termite);
   }

   public int func_149692_a(int i) {
      return i;
   }

   public int quantityDropped(int meta, int fortune, Random random) {
      return meta == 1 ? 1 : 0;
   }

   protected ItemStack func_149644_j(int i) {
      return new ItemStack(this, 1, 1);
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i <= 1; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
