package lotr.common.entity;

import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.ForochelBiome;
import lotr.common.world.biome.ForodwaithBiome;
import lotr.common.world.biome.LOTRBiomeWrapper;
import lotr.common.world.biome.SeaBiome;
import lotr.common.world.map.BothWaterLatitudeSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry.Entry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.IPlacementPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.ForgeRegistries;

public class VanillaEntitySpawnChanges {
   private static boolean madeChanges = false;

   public static void makeChanges(Register event) {
      if (event.getRegistry() == ForgeRegistries.ENTITIES) {
         if (!madeChanges) {
            Entry polarBearSpawn = (Entry)EntitySpawnPlacementRegistry.field_209347_a.get(EntityType.field_200786_Z);
            IPlacementPredicate oldPolarBearPredicate = polarBearSpawn.field_223513_c;
            polarBearSpawn.field_223513_c = extendPolarBearSpawnPredicate(oldPolarBearPredicate);
            madeChanges = true;
         }

      }
   }

   private static IPlacementPredicate extendPolarBearSpawnPredicate(IPlacementPredicate oldPredicate) {
      return (type, world, reason, pos, rand) -> {
         Biome biome = world.func_226691_t_(pos);
         if (oldPredicate.test(type, world, reason, pos, rand)) {
            LOTRBiomeWrapper biomeWrapper = LOTRBiomes.getWrapperFor(biome, world);
            return biomeWrapper instanceof SeaBiome ? ((SeaBiome)biomeWrapper).isSeaFrozen(world, pos) : true;
         } else if (isSuitableFrozenBiome(world, biome, pos) && world.func_226659_b_(pos, 0) > 8 && isSuitableFrozenSpawnBlock(world, pos.func_177977_b())) {
            return rand.nextInt(30) == 0;
         } else {
            return false;
         }
      };
   }

   private static boolean isSuitableFrozenBiome(IWorld world, Biome biome, BlockPos pos) {
      LOTRBiomeWrapper biomeWrapper = LOTRBiomes.getWrapperFor(biome, world);
      return biomeWrapper instanceof ForodwaithBiome || biomeWrapper instanceof ForochelBiome || biomeWrapper instanceof SeaBiome && BothWaterLatitudeSettings.isNorthOfSouthernIceSheet(world, pos);
   }

   private static boolean isSuitableFrozenSpawnBlock(IWorld world, BlockPos pos) {
      BlockState state = world.func_180495_p(pos);
      Material material = state.func_185904_a();
      return material == Material.field_151596_z || material == Material.field_151588_w || material == Material.field_151598_x;
   }
}
