package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockGrapevine extends Block implements IPlantable, IGrowable {
   public final boolean hasGrapes;
   public static final int MAX_GROWTH = 7;
   public static final int MAX_HEIGHT = 3;
   public static boolean hoeing = false;
   @SideOnly(Side.CLIENT)
   private IIcon postIcon;
   @SideOnly(Side.CLIENT)
   private IIcon[] vineIcons;

   public LOTRBlockGrapevine(boolean grapes) {
      super(Material.field_151585_k);
      this.hasGrapes = grapes;
      if (!this.hasGrapes) {
         this.func_149647_a(LOTRCreativeTabs.tabDeco);
      } else {
         this.func_149647_a((CreativeTabs)null);
      }

      if (this.hasGrapes) {
         this.func_149672_a(Block.field_149779_h);
         this.func_149711_c(0.0F);
      } else {
         this.func_149672_a(Block.field_149766_f);
         this.func_149711_c(2.0F);
         this.func_149752_b(5.0F);
      }

      if (this.hasGrapes) {
         this.func_149675_a(true);
      }

   }

   public Item getGrapeItem() {
      return null;
   }

   public Item getGrapeSeedsItem() {
      return null;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (i == -1) {
         return j >= 7 ? this.vineIcons[1] : this.vineIcons[0];
      } else {
         return this.postIcon;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      if (!this.hasGrapes) {
         this.postIcon = iconregister.func_94245_a(this.func_149641_N());
      } else {
         this.postIcon = LOTRMod.grapevine.func_149691_a(0, 0);
      }

      if (this.hasGrapes) {
         this.vineIcons = new IIcon[2];
         this.vineIcons[0] = iconregister.func_94245_a(this.func_149641_N() + "_vine");
         this.vineIcons[1] = iconregister.func_94245_a(this.func_149641_N() + "_grapes");
      }

   }

   public AxisAlignedBB func_149668_a(World world, int i, int j, int k) {
      this.func_149719_a(world, i, j, k);
      return super.func_149668_a(world, i, j, k);
   }

   public void func_149719_a(IBlockAccess world, int i, int j, int k) {
      float f = 0.125F;
      if (this.hasGrapes) {
         float min = 0.1875F;
         float max = 0.375F;
         int meta = world.func_72805_g(i, j, k);
         float f1 = (float)meta / 7.0F;
         f = min + (max - min) * f1;
      }

      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
   }

   public void func_149683_g() {
      float f = 0.125F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return this.hasGrapes ? LOTRMod.proxy.getGrapevineRenderID() : 0;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_149646_a(IBlockAccess world, int i, int j, int k, int side) {
      if (this.hasGrapes) {
         Block block = world.func_147439_a(i, j, k);
         int meta = world.func_72805_g(i, j, k);
         int i1 = i - Facing.field_71586_b[side];
         int j1 = j - Facing.field_71587_c[side];
         int k1 = k - Facing.field_71585_d[side];
         int metaThis = world.func_72805_g(i1, j1, k1);
         if (block instanceof LOTRBlockGrapevine && ((LOTRBlockGrapevine)block).hasGrapes && meta == metaThis && (side == 0 || side == 1)) {
            return false;
         }
      }

      return super.func_149646_a(world, i, j, k, side);
   }

   @SideOnly(Side.CLIENT)
   public Item func_149694_d(World world, int i, int j, int k) {
      return this.hasGrapes ? this.getGrapeSeedsItem() : Item.func_150898_a(this);
   }

   public boolean func_149742_c(World world, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      return below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP) || below.canSustainPlant(world, i, j, k, ForgeDirection.UP, this) || below instanceof LOTRBlockGrapevine;
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
         if (this.hasGrapes) {
            world.func_147465_d(i, j, k, LOTRMod.grapevine, 0, 3);
            Block newBlock = world.func_147439_a(i, j, k);
            newBlock.func_149674_a(world, i, j, k, world.field_73012_v);
         } else {
            world.func_147468_f(i, j, k);
         }

         return false;
      } else {
         return true;
      }
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      super.func_149674_a(world, i, j, k, random);
      if (this.checkCanStay(world, i, j, k)) {
         if (this.hasGrapes && world.func_72957_l(i, j + 1, k) >= 9) {
            int meta = world.func_72805_g(i, j, k);
            if (meta < 7) {
               float growth = this.getGrowthFactor(world, i, j, k);
               if (growth > 0.0F && random.nextInt((int)(80.0F / growth) + 1) == 0) {
                  ++meta;
                  world.func_72921_c(i, j, k, meta, 2);
               }
            }
         }

      }
   }

   private float getGrowthFactor(World world, int i, int j, int k) {
      if (!canPlantGrapesAt(world, i, j, k, this)) {
         return 0.0F;
      } else {
         int farmlandHeight = 0;

         for(int l = 1; l <= 3; ++l) {
            int j1 = j - l;
            Block block = world.func_147439_a(i, j1, k);
            if (block.canSustainPlant(world, i, j1, k, ForgeDirection.UP, this)) {
               farmlandHeight = j1;
               break;
            }
         }

         float growth = 1.0F;
         int range = 1;

         for(int i1 = -range; i1 <= range; ++i1) {
            for(int k1 = -range; k1 <= range; ++k1) {
               int i2 = i + i1;
               int k2 = k + k1;
               float f = 0.0F;
               Block block = world.func_147439_a(i2, farmlandHeight, k2);
               if (block.canSustainPlant(world, i2, farmlandHeight, k2, ForgeDirection.UP, this)) {
                  f = 1.0F;
                  if (block.isFertile(world, i2, farmlandHeight, k2)) {
                     f = 3.0F;
                  }
               }

               if (i1 != 0 || k1 != 0) {
                  f /= 4.0F;
               }

               growth += f;
            }
         }

         BiomeGenBase biome = world.func_72807_a(i, k);
         if (biome instanceof LOTRBiomeGenDorwinion) {
            growth *= 1.6F;
         }

         return growth;
      }
   }

   public static boolean canPlantGrapesAt(World world, int i, int j, int k, IPlantable plantable) {
      for(int l = 1; l <= 3; ++l) {
         int j1 = j - l;
         Block block = world.func_147439_a(i, j1, k);
         if (block.canSustainPlant(world, i, j1, k, ForgeDirection.UP, plantable)) {
            return true;
         }

         if (!(block instanceof LOTRBlockGrapevine)) {
            return false;
         }
      }

      return false;
   }

   public boolean func_149655_b(IBlockAccess world, int i, int j, int k) {
      return false;
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      if (this.hasGrapes) {
         int meta = world.func_72805_g(i, j, k);
         if (meta >= 7) {
            if (!world.field_72995_K) {
               List drops = this.getVineDrops(world, i, j, k, meta, 0);
               Iterator var12 = drops.iterator();

               while(var12.hasNext()) {
                  ItemStack itemstack = (ItemStack)var12.next();
                  this.func_149642_a(world, i, j, k, itemstack);
               }

               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.harvestGrapes);
            }

            world.func_147465_d(i, j, k, LOTRMod.grapevine, 0, 3);
            return true;
         }
      }

      return false;
   }

   public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      if (this.hasGrapes) {
         drops.addAll(this.getVineDrops(world, i, j, k, meta, fortune));
      } else {
         drops.add(new ItemStack(this));
      }

      return drops;
   }

   private ArrayList getVineDrops(World world, int i, int j, int k, int meta, int fortune) {
      ArrayList drops = new ArrayList();
      int seeds = 3 + fortune;

      int grapes;
      for(grapes = 0; grapes < seeds; ++grapes) {
         if (world.field_73012_v.nextInt(15) <= meta) {
            drops.add(new ItemStack(this.getGrapeSeedsItem()));
         }
      }

      if (meta >= 7) {
         grapes = 1 + world.field_73012_v.nextInt(fortune + 1);
         if (world.field_73012_v.nextInt(3) == 0) {
            ++grapes;
         }

         for(int l = 0; l < grapes; ++l) {
            drops.add(new ItemStack(this.getGrapeItem()));
         }
      }

      return drops;
   }

   public boolean removedByPlayer(World world, EntityPlayer entityplayer, int i, int j, int k, boolean willHarvest) {
      if (this.hasGrapes && entityplayer != null) {
         if (!world.field_72995_K) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.harvestGrapes);
         }

         return world.func_147465_d(i, j, k, LOTRMod.grapevine, 0, 3);
      } else {
         return super.removedByPlayer(world, entityplayer, i, j, k, willHarvest);
      }
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

   public boolean func_149851_a(World world, int i, int j, int k, boolean flag) {
      return this.hasGrapes && world.func_72805_g(i, j, k) < 7;
   }

   public boolean func_149852_a(World world, Random random, int i, int j, int k) {
      return true;
   }

   public void func_149853_b(World world, Random random, int i, int j, int k) {
      if (this.hasGrapes) {
         int meta = world.func_72805_g(i, j, k) + MathHelper.func_76136_a(random, 2, 5);
         if (meta > 7) {
            meta = 7;
         }

         world.func_72921_c(i, j, k, meta, 2);
      }

   }

   public boolean isAir(IBlockAccess world, int i, int j, int k) {
      if (hoeing) {
         hoeing = false;
         return true;
      } else {
         return super.isAir(world, i, j, k);
      }
   }

   public static boolean isFullGrownGrapes(Block block, int meta) {
      return block instanceof LOTRBlockGrapevine && ((LOTRBlockGrapevine)block).hasGrapes && meta >= 7;
   }
}
