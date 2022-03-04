package lotr.common.world.gen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lotr.common.world.map.RoadPointCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

public class TreeClusters extends Placement {
   private static final Random CLUSTER_RAND = new Random(2353233561358230584L);

   public TreeClusters(Codec codec) {
      super(codec);
   }

   public Stream getPositions(WorldDecoratingHelper helper, Random rand, TreeClustersConfig config, BlockPos pos) {
      int numPositions = config.count;
      if (rand.nextFloat() < config.extraChance) {
         numPositions += config.extraCount;
      }

      float reciprocalTreeFactor = 1.0F;
      int cluster = Math.round((float)config.clusterChance * reciprocalTreeFactor);
      if (cluster > 0) {
         long seed = rand.nextLong();
         seed += (long)(pos.func_177958_n() / config.clusterScale * 3129871) ^ (long)(pos.func_177952_p() / config.clusterScale) * 116129781L;
         seed = seed * seed * 42317861L + seed * 11L;
         CLUSTER_RAND.setSeed(seed);
         if (CLUSTER_RAND.nextInt(cluster) == 0) {
            numPositions += config.clusterExtraCount + rand.nextInt(config.clusterRandomExtraCount + 1);
         }
      }

      Stream positions = IntStream.range(0, numPositions).mapToObj((index) -> {
         int x = rand.nextInt(16) + pos.func_177958_n();
         int z = rand.nextInt(16) + pos.func_177952_p();
         int y = helper.func_242893_a(Type.MOTION_BLOCKING, x, z);
         return new BlockPos(x, y, z);
      }).filter(RoadPointCache.filterNotGeneratingOnRoad(helper.field_242889_a));
      if (config.layerLimit >= 0) {
         positions = positions.filter((aPos) -> {
            if (config.isLayerUpperLimit) {
               return aPos.func_177956_o() <= config.layerLimit;
            } else {
               return aPos.func_177956_o() >= config.layerLimit;
            }
         });
      }

      return positions;
   }
}
