package lotr.common.world.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lotr.common.init.LOTRWorldTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

public class RoadPointCache {
   private Map pointMap = new HashMap();
   private static final int COORD_LOOKUP_SCALE = 1000;
   private static final int ROAD_RADIUS = 4;
   private Map roadAtQueryCache = new HashMap();
   private static final int QUERY_CACHE_SIZE = 60000;

   public boolean isRoadAt(int x, int z) {
      Pair key = Pair.of(x, z);
      Boolean cachedResult = (Boolean)this.roadAtQueryCache.get(key);
      if (cachedResult == null) {
         cachedResult = this.getRoadCentreCloseness(x, z, 4) >= 0.0F;
         if (this.roadAtQueryCache.size() > 60000) {
            this.roadAtQueryCache.clear();
         }

         this.roadAtQueryCache.put(key, cachedResult);
      }

      return cachedResult;
   }

   public boolean isRoadAtPositionSurface(BlockPos pos) {
      return this.isRoadAt(pos.func_177958_n(), pos.func_177952_p());
   }

   public boolean isPartOfRoadWithinRange(int x, int z, int range) {
      return this.getRoadCentreCloseness(x, z, 4 + range) >= 0.0F;
   }

   public float getRoadCentreCloseness(int x, int z, int width) {
      double widthSq = (double)(width * width);
      float mostCloseness = -1.0F;
      List points = this.getPointsForCoords(x, z);
      Iterator var8 = points.iterator();

      while(var8.hasNext()) {
         RoadPoint point = (RoadPoint)var8.next();
         double dx = point.getWorldX() - (double)x;
         double dz = point.getWorldZ() - (double)z;
         double distSq = dx * dx + dz * dz;
         if (distSq < widthSq) {
            double sqDistRatio = distSq / widthSq;
            float distRatio = 1.0F / (float)MathHelper.func_181161_i(sqDistRatio);
            float closeness = 1.0F - distRatio;
            if (mostCloseness == -1.0F) {
               mostCloseness = closeness;
            } else if (closeness > mostCloseness) {
               mostCloseness = closeness;
            }
         }
      }

      return mostCloseness;
   }

   public List getPointsForCoords(int x, int z) {
      int x1 = x / 1000;
      int z1 = z / 1000;
      return this.getRoadList(x1, z1, false);
   }

   public void add(RoadPoint point) {
      int x = (int)Math.round(point.getWorldX() / 1000.0D);
      int z = (int)Math.round(point.getWorldZ() / 1000.0D);
      int overlap = 1;

      for(int i = -overlap; i <= overlap; ++i) {
         for(int k = -overlap; k <= overlap; ++k) {
            int xKey = x + i;
            int zKey = z + k;
            this.getRoadList(xKey, zKey, true).add(point);
         }
      }

   }

   private List getRoadList(int xKey, int zKey, boolean addToMap) {
      Pair key = Pair.of(xKey, zKey);
      List list = (List)this.pointMap.get(key);
      if (list == null) {
         list = new ArrayList();
         if (addToMap) {
            this.pointMap.put(key, list);
         }
      }

      return (List)list;
   }

   public static Predicate filterNotGeneratingOnRoad(ISeedReader seedReader) {
      ServerWorld world = seedReader.func_201672_e();
      return (pos) -> {
         return !LOTRWorldTypes.hasMapFeatures(world) || !MapSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedMap().getRoadPointCache().isRoadAtPositionSurface(pos);
      };
   }

   public static boolean checkNotGeneratingOnRoad(ISeedReader seedReader, BlockPos pos) {
      return filterNotGeneratingOnRoad(seedReader).test(pos);
   }

   public static boolean checkNotGeneratingWithinRangeOfRoad(ISeedReader seedReader, BlockPos pos, int range) {
      ServerWorld world = seedReader.func_201672_e();
      return !LOTRWorldTypes.hasMapFeatures(world) || !MapSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedMap().getRoadPointCache().isPartOfRoadWithinRange(pos.func_177958_n(), pos.func_177952_p(), range);
   }
}
