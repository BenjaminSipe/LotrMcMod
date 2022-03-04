package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.block.LOTRBlockMug;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class LOTRWorldGenStructureBase extends WorldGenerator {
   public boolean restrictions = true;
   public EntityPlayer usingPlayer = null;
   protected boolean notifyChanges;
   public LOTRStructureTimelapse.ThreadTimelapse threadTimelapse;

   public LOTRWorldGenStructureBase(boolean flag) {
      super(flag);
      this.notifyChanges = flag;
   }

   protected int usingPlayerRotation() {
      return MathHelper.func_76128_c((double)(this.usingPlayer.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
   }

   protected void func_150516_a(World world, int i, int j, int k, Block block, int meta) {
      super.func_150516_a(world, i, j, k, block, meta);
      if (block != Blocks.field_150350_a && this.threadTimelapse != null) {
         this.threadTimelapse.onBlockSet();
      }

   }

   protected void setBlockMetadata(World world, int i, int j, int k, int meta) {
      world.func_72921_c(i, j, k, meta, this.notifyChanges ? 3 : 2);
   }

   protected void placeOrcTorch(World world, int i, int j, int k) {
      this.func_150516_a(world, i, j, k, LOTRMod.orcTorch, 0);
      this.func_150516_a(world, i, j + 1, k, LOTRMod.orcTorch, 1);
   }

   protected void placeMobSpawner(World world, int i, int j, int k, Class entityClass) {
      this.func_150516_a(world, i, j, k, LOTRMod.mobSpawner, 0);
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityMobSpawner) {
         ((LOTRTileEntityMobSpawner)tileentity).setEntityClassID(LOTREntities.getEntityIDFromClass(entityClass));
      }

   }

   protected void placeSpawnerChest(World world, int i, int j, int k, Block block, int meta, Class entityClass) {
      this.func_150516_a(world, i, j, k, block, 0);
      this.setBlockMetadata(world, i, j, k, meta);
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntitySpawnerChest) {
         ((LOTRTileEntitySpawnerChest)tileentity).setMobID(entityClass);
      }

   }

   protected void placePlate(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
      this.placePlate_do(world, random, i, j, k, plateBlock, foodList, false);
   }

   protected void placePlateWithCertainty(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList) {
      this.placePlate_do(world, random, i, j, k, plateBlock, foodList, true);
   }

   private void placePlate_do(World world, Random random, int i, int j, int k, Block plateBlock, LOTRFoods foodList, boolean certain) {
      if (certain || !random.nextBoolean()) {
         this.func_150516_a(world, i, j, k, plateBlock, 0);
         if (certain || random.nextBoolean()) {
            TileEntity tileentity = world.func_147438_o(i, j, k);
            if (tileentity != null && tileentity instanceof LOTRTileEntityPlate) {
               LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
               ItemStack food = foodList.getRandomFoodForPlate(random);
               if (random.nextInt(4) == 0) {
                  food.field_77994_a += 1 + random.nextInt(3);
               }

               plate.setFoodItem(food);
            }
         }

      }
   }

   protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
      this.placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
   }

   protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, ItemStack drink) {
      this.func_150516_a(world, i, j, k, LOTRMod.barrel, meta);
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity instanceof LOTRTileEntityBarrel) {
         LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)tileentity;
         barrel.barrelMode = 2;
         drink = drink.func_77946_l();
         LOTRItemMug.setStrengthMeta(drink, MathHelper.func_76136_a(random, 1, 3));
         LOTRItemMug.setVessel(drink, LOTRItemMug.Vessel.MUG, true);
         drink.field_77994_a = MathHelper.func_76136_a(random, LOTRBrewingRecipes.BARREL_CAPACITY / 2, LOTRBrewingRecipes.BARREL_CAPACITY);
         barrel.func_70299_a(9, drink);
      }

   }

   protected void placeMug(World world, Random random, int i, int j, int k, int meta, LOTRFoods foodList) {
      this.placeMug(world, random, i, j, k, meta, foodList.getRandomPlaceableDrink(random), foodList);
   }

   protected void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRFoods foodList) {
      this.placeMug(world, random, i, j, k, meta, drink, foodList.getPlaceableDrinkVessels());
   }

   protected void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, LOTRItemMug.Vessel[] vesselTypes) {
      LOTRItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
      this.func_150516_a(world, i, j, k, vessel.getBlock(), meta);
      if (random.nextInt(3) != 0) {
         drink = drink.func_77946_l();
         drink.field_77994_a = 1;
         if (drink.func_77973_b() instanceof LOTRItemMug && ((LOTRItemMug)drink.func_77973_b()).isBrewable) {
            LOTRItemMug.setStrengthMeta(drink, MathHelper.func_76136_a(random, 1, 3));
         }

         LOTRItemMug.setVessel(drink, vessel, true);
         LOTRBlockMug.setMugItem(world, i, j, k, drink, vessel);
      }

   }

   protected void placeFlowerPot(World world, int i, int j, int k, ItemStack itemstack) {
      this.func_150516_a(world, i, j, k, LOTRMod.flowerPot, 0);
      LOTRBlockFlowerPot.setPlant(world, i, j, k, itemstack);
   }

   protected void spawnItemFrame(World world, int i, int j, int k, int direction, ItemStack itemstack) {
      EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
      frame.func_82334_a(itemstack);
      world.func_72838_d(frame);
   }

   protected void placeArmorStand(World world, int i, int j, int k, int direction, ItemStack[] armor) {
      this.func_150516_a(world, i, j, k, LOTRMod.armorStand, direction);
      this.func_150516_a(world, i, j + 1, k, LOTRMod.armorStand, direction | 4);
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity != null && tileentity instanceof LOTRTileEntityArmorStand) {
         LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand)tileentity;

         for(int l = 0; l < armor.length; ++l) {
            ItemStack armorPart = armor[l];
            if (armorPart == null) {
               armorStand.func_70299_a(l, (ItemStack)null);
            } else {
               armorStand.func_70299_a(l, armor[l].func_77946_l());
            }
         }
      }

   }

   protected void placeBanner(World world, int i, int j, int k, int direction, LOTRItemBanner.BannerType type) {
      LOTREntityBanner banner = new LOTREntityBanner(world);
      banner.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, (float)direction * 90.0F, 0.0F);
      banner.setBannerType(type);
      world.func_72838_d(banner);
   }

   protected void placeWallBanner(World world, int i, int j, int k, int direction, LOTRItemBanner.BannerType type) {
      LOTREntityBannerWall banner = new LOTREntityBannerWall(world, i, j, k, direction);
      banner.setBannerType(type);
      world.func_72838_d(banner);
   }

   protected void placeNPCRespawner(LOTREntityNPCRespawner entity, World world, int i, int j, int k) {
      entity.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
      world.func_72838_d(entity);
   }

   protected void setGrassToDirt(World world, int i, int j, int k) {
      world.func_147439_a(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
   }

   protected void setAir(World world, int i, int j, int k) {
      this.func_150516_a(world, i, j, k, Blocks.field_150350_a, 0);
   }

   protected void placeSkull(World world, Random random, int i, int j, int k) {
      this.func_150516_a(world, i, j, k, Blocks.field_150465_bP, 1);
      TileEntity tileentity = world.func_147438_o(i, j, k);
      if (tileentity != null && tileentity instanceof TileEntitySkull) {
         TileEntitySkull skull = (TileEntitySkull)tileentity;
         skull.func_145903_a(random.nextInt(16));
      }

   }
}
