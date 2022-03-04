package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityFallingFireJar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class LOTRBlockRhunFireJar extends BlockFalling {
   public static int renderingStage = 0;
   public static final int renderBase = 1;
   public static final int renderNeck = 2;
   public static final int renderLid = 3;
   public static final int renderCap = 4;
   public static final int renderCrown = 5;
   public static final int renderHandle = 6;
   @SideOnly(Side.CLIENT)
   private IIcon iconBaseSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconBaseTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconBaseBottom;
   @SideOnly(Side.CLIENT)
   private IIcon iconNeckSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconLidSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconLidTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconLidBottom;
   @SideOnly(Side.CLIENT)
   private IIcon iconCapSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconCapTop;
   @SideOnly(Side.CLIENT)
   private IIcon iconCapBottom;
   @SideOnly(Side.CLIENT)
   private IIcon iconCrownSide;
   @SideOnly(Side.CLIENT)
   private IIcon iconHandleSide;
   public static boolean explodeOnAdded = true;
   private static Material materialFireJar;

   public LOTRBlockRhunFireJar() {
      super(materialFireJar);
      this.func_149675_a(true);
      this.func_149647_a(LOTRCreativeTabs.tabCombat);
      this.func_149676_a(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149769_e);
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.iconBaseSide = iconregister.func_94245_a(this.func_149641_N() + "_base_side");
      this.iconBaseTop = iconregister.func_94245_a(this.func_149641_N() + "_base_top");
      this.iconBaseBottom = iconregister.func_94245_a(this.func_149641_N() + "_base_bottom");
      this.iconNeckSide = iconregister.func_94245_a(this.func_149641_N() + "_neck_side");
      this.iconLidSide = iconregister.func_94245_a(this.func_149641_N() + "_lid_side");
      this.iconLidTop = iconregister.func_94245_a(this.func_149641_N() + "_lid_top");
      this.iconLidBottom = iconregister.func_94245_a(this.func_149641_N() + "_lid_bottom");
      this.iconCapSide = iconregister.func_94245_a(this.func_149641_N() + "_cap_side");
      this.iconCapTop = iconregister.func_94245_a(this.func_149641_N() + "_cap_top");
      this.iconCapBottom = iconregister.func_94245_a(this.func_149641_N() + "_cap_bottom");
      this.iconCrownSide = iconregister.func_94245_a(this.func_149641_N() + "_crown_side");
      this.iconHandleSide = iconregister.func_94245_a(this.func_149641_N() + "_handle_side");
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (renderingStage == 1) {
         return i == 0 ? this.iconBaseBottom : (i == 1 ? this.iconBaseTop : this.iconBaseSide);
      } else if (renderingStage == 2) {
         return this.iconNeckSide;
      } else if (renderingStage == 3) {
         return i == 0 ? this.iconLidBottom : (i == 1 ? this.iconLidTop : this.iconLidSide);
      } else if (renderingStage == 4) {
         return i == 0 ? this.iconCapBottom : (i == 1 ? this.iconCapTop : this.iconCapSide);
      } else if (renderingStage == 5) {
         return this.iconCrownSide;
      } else {
         return renderingStage == 6 ? this.iconHandleSide : LOTRMod.brick5.func_149691_a(i, 11);
      }
   }

   public boolean func_149662_c() {
      return false;
   }

   public boolean func_149686_d() {
      return false;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getRhunFireJarRenderID();
   }

   public void func_149726_b(World world, int i, int j, int k) {
      if (world.func_72864_z(i, j, k)) {
         if (explodeOnAdded) {
            this.explode(world, i, j, k);
         }
      } else {
         super.func_149726_b(world, i, j, k);
      }

   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (LOTRMod.doFireTick(world)) {
         boolean foundFire = false;

         for(int l = 0; l < 12; ++l) {
            int range = 1 + random.nextInt(4);
            int i1 = i + MathHelper.func_76136_a(random, -range, range);
            int j1 = j + MathHelper.func_76136_a(random, -range, range);
            int k1 = k + MathHelper.func_76136_a(random, -range, range);
            Block block = world.func_147439_a(i1, j1, k1);
            Material material = block.func_149688_o();
            if (material == Material.field_151581_o || material == Material.field_151587_i) {
               foundFire = true;
               break;
            }
         }

         if (foundFire) {
            this.explode(world, i, j, k);
         }
      }

      if (world.func_147439_a(i, j, k) == this && !world.field_72995_K && BlockFalling.func_149831_e(world, i, j - 1, k) && j >= 0) {
         int b0 = 32;
         if (world.func_72904_c(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
            EntityFallingBlock falling = new LOTREntityFallingFireJar(world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, this, world.func_72805_g(i, j, k));
            this.func_149829_a(falling);
            world.func_72838_d(falling);
         }
      }

   }

   public void func_149695_a(World world, int i, int j, int k, Block block) {
      super.func_149695_a(world, i, j, k, block);
      if (world.func_147439_a(i, j, k) == this) {
         if (world.func_72864_z(i, j, k)) {
            this.explode(world, i, j, k);
         } else if (!world.field_72995_K) {
            this.func_149674_a(world, i, j, k, world.field_73012_v);
         }
      }

   }

   public void explode(World world, int i, int j, int k) {
      if (!world.field_72995_K) {
         world.func_72876_a((Entity)null, (double)i, (double)j, (double)k, 2.0F, false);
         world.func_147468_f(i, j, k);
         int range = 2;

         for(int l = 0; l < 64; ++l) {
            int i1 = i + MathHelper.func_76136_a(world.field_73012_v, -range, range);
            int j1 = j + MathHelper.func_76136_a(world.field_73012_v, -range, range);
            int k1 = k + MathHelper.func_76136_a(world.field_73012_v, -range, range);
            Block block = world.func_147439_a(i1, j1, k1);
            if ((block.isAir(world, i1, j1, k1) || block.isReplaceable(world, i1, j1, k1)) && !block.func_149688_o().func_76224_d()) {
               world.func_147465_d(i1, j1, k1, LOTRMod.rhunFire, 0, 3);
            }
         }
      }

   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      ItemStack itemstack = entityplayer.func_71045_bC();
      if (itemstack != null && itemstack.func_77973_b() instanceof ItemFlintAndSteel) {
         this.explode(world, i, j, k);
         return true;
      } else {
         return false;
      }
   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      double speed = Math.sqrt(entity.field_70159_w * entity.field_70159_w + entity.field_70181_x * entity.field_70181_x + entity.field_70179_y * entity.field_70179_y);
      if (speed >= MathHelper.func_82716_a(world.field_73012_v, 0.3D, 0.8D)) {
         this.explode(world, i, j, k);
      }

   }

   public void onBlockExploded(World world, int i, int j, int k, Explosion explosion) {
      this.explode(world, i, j, k);
      super.onBlockExploded(world, i, j, k, explosion);
   }

   public void func_149828_a(World world, int i, int j, int k, int meta) {
      this.explode(world, i, j, k);
   }

   static {
      materialFireJar = new MaterialLogic(MapColor.field_151665_m);
   }
}
