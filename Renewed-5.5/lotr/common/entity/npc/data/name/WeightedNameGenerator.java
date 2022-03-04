package lotr.common.entity.npc.data.name;

import java.util.Random;
import net.minecraft.util.WeightedList;

public class WeightedNameGenerator implements NPCNameGenerator {
   private final WeightedList generators;

   private WeightedNameGenerator(WeightedList generators) {
      this.generators = generators;
   }

   public String generateName(Random rand, boolean male) {
      return ((NPCNameGenerator)this.generators.func_226318_b_(rand)).generateName(rand, male);
   }

   public static WeightedNameGenerator.WeightedNameGeneratorBuilder builder() {
      return new WeightedNameGenerator.WeightedNameGeneratorBuilder();
   }

   // $FF: synthetic method
   WeightedNameGenerator(WeightedList x0, Object x1) {
      this(x0);
   }

   public static class WeightedNameGeneratorBuilder {
      private final WeightedList generators = new WeightedList();

      public WeightedNameGenerator.WeightedNameGeneratorBuilder add(NPCNameGenerator generator, int weight) {
         this.generators.func_226313_a_(generator, weight);
         return this;
      }

      public WeightedNameGenerator build() {
         return new WeightedNameGenerator(this.generators);
      }
   }
}
