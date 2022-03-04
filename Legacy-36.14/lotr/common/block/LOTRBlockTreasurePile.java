package lotr.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.item.LOTREntityFallingTreasure;
import lotr.common.recipe.LOTRRecipesTreasurePile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class LOTRBlockTreasurePile extends Block {
   public static final SoundType soundTypeTreasure = new SoundType("lotr:treasure", 1.0F, 1.0F) {
      private Random rand = new Random();

      public float func_150494_d() {
         return super.func_150494_d() * (0.85F + this.rand.nextFloat() * 0.3F);
      }

      public String func_150495_a() {
         return "lotr:block.treasure.break";
      }

      public String func_150498_e() {
         return "lotr:block.treasure.step";
      }

      public String func_150496_b() {
         return "lotr:block.treasure.place";
      }
   };
   public static final int MAX_META = 7;
   @SideOnly(Side.CLIENT)
   private IIcon sideIcon;

   public LOTRBlockTreasurePile() {
      super(Material.field_151594_q);
      this.func_149711_c(0.0F);
      this.func_149672_a(soundTypeTreasure);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      super.func_149651_a(iconregister);
      this.sideIcon = iconregister.func_94245_a(this.func_149641_N() + "_side");
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return i != 0 && i != 1 ? this.sideIcon : this.field_149761_L;
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      this.func_149719_a(world, i, j, k);
      if (this.field_149756_F >= 1.0D) {
         this.field_149756_F = 1.0D;
      } else if (this.field_149756_F >= 0.5D) {
         this.field_149756_F = 0.5D;
      } else {
         this.field_149756_F = 0.0625D;
      }

      return super.func_149668_a(world, i, j, k);
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      this.setBlockBoundsMeta(meta);
   }

   public void func_149683_g() {
      this.setBlockBoundsMeta(0);
   }

   private void setBlockBoundsMeta(int meta) {
      float f = (float)(meta + 1) / 8.0F;
      this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
   }

   public static void setTreasureBlockBounds(Block block, int meta) {
      if (block instanceof LOTRBlockTreasurePile) {
         ((LOTRBlockTreasurePile)block).setBlockBoundsMeta(meta);
      }

   }

   public boolean isSideSolid(IBlockAccess world, int i, int j, int k, ForgeDirection side) {
      int meta = world.func_72805_g(i, j, k);
      return meta == 7 && side == ForgeDirection.UP ? true : super.isSideSolid(world, i, j, k, side);
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getTreasureRenderID();
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      return super.func_149742_c(world, i, j, k);
   }

   public void func_149726_b(World world, int i, int j, int k) {
      world.func_147464_a(i, j, k, this, this.func_149738_a(world));
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      world.func_147464_a(i, j, k, this, this.func_149738_a(world));
   }

   public int func_149738_a(World world) {
      return 2;
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (!world.field_72995_K && !this.tryFall(world, i, j, k) && !this.func_149718_j(world, i, j, k)) {
         this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
         world.func_147468_f(i, j, k);
      }

   }

   private boolean tryFall(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      if (canFallUpon(world, i, j - 1, k, this, meta) && j >= 0) {
         int range = 32;
         if (!BlockFalling.field_149832_M && world.func_72904_c(i - range, j - range, k - range, i + range, j + range, k + range)) {
            if (!world.field_72995_K) {
               LOTREntityFallingTreasure fallingBlock = new LOTREntityFallingTreasure(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), this, meta);
               world.func_72838_d(fallingBlock);
               return true;
            }
         } else {
            world.func_147468_f(i, j, k);

            while(canFallUpon(world, i, j - 1, k, this, meta) && j > 0) {
               --j;
            }

            if (j > 0) {
               world.func_147465_d(i, j, k, this, meta, 3);
               return true;
            }
         }
      }

      return false;
   }

   public static boolean canFallUpon(World world, int i, int j, int k, Block thisBlock, int thisMeta) {
      Block block = world.func_147439_a(i, j, k);
      int meta = world.func_72805_g(i, j, k);
      return block == thisBlock && meta < 7 ? true : BlockFalling.func_149831_e(world, i, j, k);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack itemstack = entityplayer.func_70694_bm();
      if (itemstack != null && itemstack.func_77973_b() == Item.func_150898_a(this) && side == 1) {
         int itemMeta = itemstack.func_77960_j();
         boolean placedTreasure = false;
         int meta = world.func_72805_g(i, j, k);
         if (meta < 7) {
            while(meta < 7 && itemMeta >= 0) {
               ++meta;
               --itemMeta;
            }

            world.func_72921_c(i, j, k, meta, 3);
            placedTreasure = true;
            if (itemMeta >= 0) {
               Block above = world.func_147439_a(i, j + 1, k);
               if (above.isReplaceable(world, i, j + 1, k)) {
                  world.func_147465_d(i, j + 1, k, this, itemMeta, 3);
                  itemMeta = -1;
                  placedTreasure = true;
               }
            }

            if (placedTreasure) {
               world.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), this.field_149762_H.func_150496_b(), (this.field_149762_H.func_150497_c() + 1.0F) / 2.0F, this.field_149762_H.func_150494_d() * 0.8F);
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  if (itemMeta < 0) {
                     --itemstack.field_77994_a;
                  } else {
                     --itemstack.field_77994_a;
                     ItemStack remainder = itemstack.func_77946_l();
                     remainder.field_77994_a = 1;
                     remainder.func_77964_b(itemMeta);
                     if (itemstack.field_77994_a <= 0) {
                        entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, remainder);
                     } else if (!entityplayer.field_71071_by.func_70441_a(remainder)) {
                        entityplayer.func_71019_a(remainder, false);
                     }
                  }

                  if (!world.field_72995_K) {
                     entityplayer.field_71070_bA.func_75142_b();
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   public int func_149692_a(int i) {
      return i;
   }

   public int quantityDropped(int meta, int fortune, Random random) {
      return 1;
   }

   public void func_149724_b(World world, int i, int j, int k, Entity entity) {
      this.func_149719_a(world, i, j, k);

      for(int l = 0; l < 8; ++l) {
         double d = (double)((float)i + world.field_73012_v.nextFloat());
         double d1 = (double)j + this.field_149756_F;
         double d2 = (double)((float)k + world.field_73012_v.nextFloat());
         double d3 = (double)MathHelper.func_151240_a(world.field_73012_v, -0.15F, 0.15F);
         double d4 = (double)MathHelper.func_151240_a(world.field_73012_v, 0.1F, 0.4F);
         double d5 = (double)MathHelper.func_151240_a(world.field_73012_v, -0.15F, 0.15F);
         world.func_72869_a("blockdust_" + Block.func_149682_b(this) + "_0", d, d1, d2, d3, d4, d5);
      }

   }

   public void func_149746_a(World world, int i, int j, int k, Entity entity, float f) {
      this.func_149724_b(world, i, j, k, entity);
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      list.add(new ItemStack(item, 1, 0));
      list.add(new ItemStack(item, 1, 7));
   }

   public static void generateTreasureRecipes(Block block, Item ingot) {
      GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 8, 0), new Object[]{"XX", "XX", 'X', ingot}));
      GameRegistry.addRecipe(new LOTRRecipesTreasurePile(block, ingot));
   }
}
