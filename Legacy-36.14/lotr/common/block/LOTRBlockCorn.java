package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockCorn extends Block implements IPlantable, IGrowable {
   public static int MAX_GROW_HEIGHT = 3;
   private static int META_GROW_END = 7;
   @SideOnly(Side.CLIENT)
   private IIcon cornIcon;

   public LOTRBlockCorn() {
      super(Material.field_151585_k);
      float f = 0.375F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
      this.func_149675_a(true);
      this.func_149711_c(0.0F);
      this.func_149672_a(field_149779_h);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (this.checkCanStay(world, i, j, k)) {
         int cornHeight;
         for(cornHeight = 1; world.func_147439_a(i, j - cornHeight, k) == this; ++cornHeight) {
         }

         float growth = this.getGrowthFactor(world, i, j - cornHeight + 1, k);
         if (world.func_147437_c(i, j + 1, k) && cornHeight < MAX_GROW_HEIGHT) {
            int meta = world.func_72805_g(i, j, k);
            int corn = meta & 8;
            int grow = meta & 7;
            if (grow == META_GROW_END) {
               world.func_147465_d(i, j + 1, k, this, 0, 3);
               grow = 0;
            } else {
               ++grow;
            }

            meta = corn | grow;
            world.func_72921_c(i, j, k, meta, 4);
         }

         if (!hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k) && world.field_73012_v.nextFloat() < growth) {
            setHasCorn(world, i, j, k, true);
         }
      }

   }

   private float getGrowthFactor(World world, int i, int j, int k) {
      float growth = 1.0F;
      Block below = world.func_147439_a(i, j - 1, k);
      if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150464_aj)) {
         growth = 3.0F;
         if (below.isFertile(world, i, j - 1, k)) {
            growth = 9.0F;
         }
      }

      if (world.func_72896_J()) {
         growth *= 3.0F;
      }

      return growth / 250.0F;
   }

   private boolean canGrowCorn(World world, int i, int j, int k) {
      return world.func_147439_a(i, j - 1, k) == this;
   }

   public static boolean hasCorn(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      return metaHasCorn(meta);
   }

   private static boolean metaHasCorn(int l) {
      return (l & 8) != 0;
   }

   public static void setHasCorn(World world, int i, int j, int k, boolean flag) {
      int meta = world.func_72805_g(i, j, k);
      if (flag) {
         meta |= 8;
      } else {
         meta &= 7;
      }

      world.func_72921_c(i, j, k, meta, 3);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      if (below == this) {
         return true;
      } else {
         IPlantable beachTest = new IPlantable() {
            public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
               return EnumPlantType.Beach;
            }

            public Block getPlant(IBlockAccess world, int i, int j, int k) {
               return LOTRBlockCorn.this;
            }

            public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
               return 0;
            }
         };
         return below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, this) || below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, beachTest);
      }
   }

   public boolean func_149718_j(World world, int i, int j, int k) {
      return this.func_149742_c(world, i, j, k);
   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      this.checkCanStay(world, i, j, k);
   }

   private boolean checkCanStay(World world, int i, int j, int k) {
      if (!this.func_149718_j(world, i, j, k)) {
         int meta = world.func_72805_g(i, j, k);
         this.func_149697_b(world, i, j, k, meta, 0);
         world.func_147468_f(i, j, k);
         return false;
      } else {
         return true;
      }
   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      return null;
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return 1;
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
      if (!hasCorn(world, i, j, k)) {
         return false;
      } else {
         if (!world.field_72995_K) {
            int preMeta = world.func_72805_g(i, j, k);
            setHasCorn(world, i, j, k, false);
            if (!world.field_72995_K) {
               List cornDrops = this.getCornDrops(world, i, j, k, preMeta);
               Iterator var12 = cornDrops.iterator();

               while(var12.hasNext()) {
                  ItemStack corn = (ItemStack)var12.next();
                  this.func_149642_a(world, i, j, k, corn);
               }
            }
         }

         return true;
      }
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(this);
   }

   public int func_149692_a(int i) {
      return 0;
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      drops.addAll(super.getDrops(world, i, j, k, meta, fortune));
      drops.addAll(this.getCornDrops(world, i, j, k, meta));
      return drops;
   }

   public ArrayList getCornDrops(World world, int i, int j, int k, int meta) {
      ArrayList drops = new ArrayList();
      if (metaHasCorn(meta)) {
         int corns = 1;
         if (world.field_73012_v.nextInt(4) == 0) {
            ++corns;
         }

         for(int l = 0; l < corns; ++l) {
            drops.add(new ItemStack(LOTRMod.corn));
         }
      }

      return drops;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return metaHasCorn(j) ? this.cornIcon : super.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      super.func_149651_a(iconregister);
      this.cornIcon = iconregister.func_94245_a(this.func_149641_N() + "_corn");
   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }

   public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
      return EnumPlantType.Crop;
   }

   public Block getPlant(IBlockAccess world, int i, int j, int k) {
      return this;
   }

   public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
      return world.func_72805_g(i, j, k);
   }

   public boolean func_149851_a(World world, int i, int j, int k, boolean isRemote) {
      return world.func_147439_a(i, j - 1, k) != this && world.func_147437_c(i, j + 1, k) || !hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k);
   }

   public boolean func_149852_a(World world, Random random, int i, int j, int k) {
      return true;
   }

   public void func_149853_b(World world, Random random, int i, int j, int k) {
      if (world.func_147439_a(i, j - 1, k) != this && world.func_147437_c(i, j + 1, k)) {
         world.func_147465_d(i, j + 1, k, this, 0, 3);
      } else if (!hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k) && random.nextInt(2) == 0) {
         setHasCorn(world, i, j, k, true);
      }

   }
}
