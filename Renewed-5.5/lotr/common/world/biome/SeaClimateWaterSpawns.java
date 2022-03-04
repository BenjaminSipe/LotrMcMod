package lotr.common.world.biome;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;

public class SeaClimateWaterSpawns {
   private Map typeSpawns = new HashMap();

   public List getSpawns(EntityClassification creatureType) {
      return (List)this.typeSpawns.computeIfAbsent(creatureType, (t) -> {
         return ImmutableList.of();
      });
   }

   public void add(Spawners spawners) {
      EntityClassification type = spawners.field_242588_c.func_220339_d();
      if (type != EntityClassification.WATER_AMBIENT && type != EntityClassification.WATER_CREATURE) {
         throw new IllegalArgumentException("Warning: this is intended for WATER_AMBIENT or WATER_CREATURE types, but tried to add " + type + "!");
      } else {
         ((List)this.typeSpawns.computeIfAbsent(type, (t) -> {
            return new ArrayList();
         })).add(spawners);
      }
   }
}
