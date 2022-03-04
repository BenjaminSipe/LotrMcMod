package lotr.common.dim;

import java.util.OptionalLong;
import lotr.common.data.LOTRLevelData;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class MiddleEarthDimensionType extends LOTRDimensionType {
   public MiddleEarthDimensionType(ResourceLocation key) {
      super(OptionalLong.empty(), true, false, false, true, true, 256, BlockTags.field_241277_aC_.func_230234_a_(), key, 0.0F);
   }

   public BlockPos getDefaultPortalCoordinate(World world) {
      return new BlockPos(0, world.func_217301_I(), 0);
   }

   public BlockPos getSpawnCoordinate(World world) {
      return LOTRLevelData.sidedInstance((IWorldReader)world).getMiddleEarthPortalLocation();
   }
}
