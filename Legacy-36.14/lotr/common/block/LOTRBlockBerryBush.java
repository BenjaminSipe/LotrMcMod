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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockBerryBush extends Block implements IPlantable, IGrowable {
   public LOTRBlockBerryBush() {
      super(Material.field_151585_k);
      this.func_149675_a(true);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.4F);
      this.func_149672_a(Block.field_149779_h);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      int berryType = getBerryType(j);
      LOTRBlockBerryBush.BushType type = LOTRBlockBerryBush.BushType.forMeta(berryType);
      return hasBerries(j) ? type.iconGrown : type.iconBare;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      LOTRBlockBerryBush.BushType[] var2 = LOTRBlockBerryBush.BushType.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRBlockBerryBush.BushType type = var2[var4];
         type.iconBare = iconregister.func_94245_a(this.func_149641_N() + "_" + type.bushName + "_bare");
         type.iconGrown = iconregister.func_94245_a(this.func_149641_N() + "_" + type.bushName);
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      LOTRBlockBerryBush.BushType[] var4 = LOTRBlockBerryBush.BushType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         LOTRBlockBerryBush.BushType type = var4[var6];
         int meta = type.bushMeta;
         list.add(new ItemStack(item, 1, setHasBerries(meta, true)));
         list.add(new ItemStack(item, 1, setHasBerries(meta, false)));
      }

   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149655_b(IBlockAccess world, int i, int j, int k) {
      return false;
   }

   public static boolean hasBerries(int meta) {
      return (meta & 8) != 0;
   }

   public static int getBerryType(int meta) {
      return meta & 7;
   }

   public static int setHasBerries(int meta, boolean flag) {
      return flag ? getBerryType(meta) | 8 : getBerryType(meta);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k);
      if (!world.field_72995_K && !hasBerries(meta)) {
         float growth = this.getGrowthFactor(world, i, j, k);
         if (random.nextFloat() < growth) {
            this.growBerries(world, i, j, k);
         }
      }

   }

   private void growBerries(World world, int i, int j, int k) {
      int meta = world.func_72805_g(i, j, k);
      world.func_72921_c(i, j, k, setHasBerries(meta, true), 3);
   }

   private float getGrowthFactor(World world, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      float growth;
      if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, this) && world.func_72957_l(i, j + 1, k) >= 9) {
         growth = 1.0F;
         boolean bushAdjacent = false;
         boolean bushAdjacentCorner = false;

         int i1;
         int k1;
         label84:
         for(i1 = i - 1; i1 <= i + 1; ++i1) {
            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               if ((i1 != i || k1 != k) && world.func_147439_a(i1, j, k1) instanceof LOTRBlockBerryBush) {
                  bushAdjacent = true;
                  break label84;
               }
            }
         }

         for(i1 = i - 1; i1 <= i + 1; ++i1) {
            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               float growthBonus = 0.0F;
               if (world.func_147439_a(i1, j - 1, k1).canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, this)) {
                  growthBonus = 1.0F;
                  if (world.func_147439_a(i1, j - 1, k1).isFertile(world, i1, j - 1, k1)) {
                     growthBonus = 3.0F;
                  }
               }

               if (i1 != i || k1 != k) {
                  growthBonus /= 4.0F;
               }

               growth += growthBonus;
            }
         }

         if (growth > 0.0F) {
            if (bushAdjacent) {
               growth /= 2.0F;
            }

            if (world.func_72896_J()) {
               growth *= 3.0F;
            }

            return growth / 150.0F;
         }
      }

      if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
         growth = (float)world.func_72957_l(i, j + 1, k) / 2000.0F;
         if (world.func_72896_J()) {
            growth *= 3.0F;
         }

         return growth;
      } else {
         return 0.0F;
      }
   }

   public int func_149692_a(int i) {
      return i;
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      int meta = world.func_72805_g(i, j, k);
      if (!hasBerries(meta)) {
         return false;
      } else {
         world.func_72921_c(i, j, k, setHasBerries(meta, false), 3);
         if (!world.field_72995_K) {
            List drops = this.getBerryDrops(world, i, j, k, meta);
            Iterator var12 = drops.iterator();

            while(var12.hasNext()) {
               ItemStack berry = (ItemStack)var12.next();
               this.func_149642_a(world, i, j, k, berry);
            }
         }

         return true;
      }
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      drops.add(new ItemStack(this, 1, setHasBerries(meta, false)));
      drops.addAll(this.getBerryDrops(world, i, j, k, meta));
      return drops;
   }

   private ArrayList getBerryDrops(World world, int i, int j, int k, int meta) {
      ArrayList drops = new ArrayList();
      if (hasBerries(meta)) {
         int berryType = getBerryType(meta);
         Item berry = null;
         int berries = 1 + world.field_73012_v.nextInt(4);
         switch(berryType) {
         case 0:
            berry = LOTRMod.blueberry;
            break;
         case 1:
            berry = LOTRMod.blackberry;
            break;
         case 2:
            berry = LOTRMod.raspberry;
            break;
         case 3:
            berry = LOTRMod.cranberry;
            break;
         case 4:
            berry = LOTRMod.elderberry;
            break;
         case 5:
            berry = LOTRMod.wildberry;
         }

         if (berry != null) {
            for(int l = 0; l < berries; ++l) {
               drops.add(new ItemStack(berry));
            }
         }
      }

      return drops;
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
      return !hasBerries(world.func_72805_g(i, j, k));
   }

   public boolean func_149852_a(World world, Random random, int i, int j, int k) {
      return true;
   }

   public void func_149853_b(World world, Random random, int i, int j, int k) {
      if (random.nextInt(3) == 0) {
         this.growBerries(world, i, j, k);
      }

   }

   public static enum BushType {
      BLUEBERRY(0, "blueberry", false),
      BLACKBERRY(1, "blackberry", false),
      RASPBERRY(2, "raspberry", false),
      CRANBERRY(3, "cranberry", false),
      ELDERBERRY(4, "elderberry", false),
      WILDBERRY(5, "wildberry", true);

      public final int bushMeta;
      public final String bushName;
      public final boolean poisonous;
      @SideOnly(Side.CLIENT)
      public IIcon iconBare;
      @SideOnly(Side.CLIENT)
      public IIcon iconGrown;

      private BushType(int i, String s, boolean flag) {
         this.bushMeta = i;
         this.bushName = s;
         this.poisonous = flag;
      }

      public static LOTRBlockBerryBush.BushType randomType(Random rand) {
         return values()[rand.nextInt(values().length)];
      }

      public static LOTRBlockBerryBush.BushType forMeta(int i) {
         LOTRBlockBerryBush.BushType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRBlockBerryBush.BushType type = var1[var3];
            if (type.bushMeta == i) {
               return type;
            }
         }

         return values()[0];
      }
   }
}
