package lotr.common.world.spawning;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.entity.EntityType;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandom.Item;

public interface NPCEntityTypeProvider {
   EntityType getTypeToSpawn(Random var1);

   Stream getAllPossibleTypes();

   public static class MixedEntry extends Item {
      public final EntityType entityType;

      public MixedEntry(EntityType entityType, int weight) {
         super(weight);
         this.entityType = entityType;
      }
   }

   public static class Mixed implements NPCEntityTypeProvider {
      private final List mixedEntries;

      public Mixed(List mixedEntries) {
         this.mixedEntries = mixedEntries;
      }

      public EntityType getTypeToSpawn(Random rand) {
         return ((NPCEntityTypeProvider.MixedEntry)WeightedRandom.func_76271_a(rand, this.mixedEntries)).entityType;
      }

      public Stream getAllPossibleTypes() {
         return this.mixedEntries.stream().map((e) -> {
            return e.entityType;
         });
      }
   }

   public static class Single implements NPCEntityTypeProvider {
      private final EntityType type;

      public Single(EntityType type) {
         this.type = type;
      }

      public EntityType getTypeToSpawn(Random rand) {
         return this.type;
      }

      public Stream getAllPossibleTypes() {
         return Stream.of(this.type);
      }
   }
}
