package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockOreGem extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] oreIcons;
   private String[] oreNames = new String[]{"topaz", "amethyst", "sapphire", "ruby", "amber", "diamond", "opal", "emerald"};

   public LOTRBlockOreGem() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(3.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.oreIcons = new IIcon[this.oreNames.length];

      for(int i = 0; i < this.oreNames.length; ++i) {
         this.oreIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.oreNames[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.oreNames.length) {
         j = 0;
      }

      return this.oreIcons[j];
   }

   public Item func_149650_a(int i, Random random, int j) {
      if (i == 0) {
         return LOTRMod.topaz;
      } else if (i == 1) {
         return LOTRMod.amethyst;
      } else if (i == 2) {
         return LOTRMod.sapphire;
      } else if (i == 3) {
         return LOTRMod.ruby;
      } else if (i == 4) {
         return LOTRMod.amber;
      } else if (i == 5) {
         return LOTRMod.diamond;
      } else if (i == 6) {
         return LOTRMod.opal;
      } else {
         return i == 7 ? LOTRMod.emerald : Item.func_150898_a(this);
      }
   }

   public int func_149745_a(Random random) {
      return 1 + random.nextInt(2);
   }

   public int func_149679_a(int i, Random random) {
      if (i > 0 && Item.func_150898_a(this) != this.func_149650_a(0, random, i)) {
         int drops = this.func_149745_a(random);
         drops += random.nextInt(i + 1);
         return drops;
      } else {
         return this.func_149745_a(random);
      }
   }

   public void func_149690_a(World world, int i, int j, int k, int meta, float f, int fortune) {
      super.func_149690_a(world, i, j, k, meta, f, fortune);
      if (this.func_149650_a(meta, world.field_73012_v, fortune) != Item.func_150898_a(this)) {
         int amountXp = MathHelper.func_76136_a(world.field_73012_v, 0, 2);
         this.func_149657_c(world, i, j, k, amountXp);
      }

   }

   public int func_149643_k(World world, int i, int j, int k) {
      return world.func_72805_g(i, j, k);
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.oreNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
