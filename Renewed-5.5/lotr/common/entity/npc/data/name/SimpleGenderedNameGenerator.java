package lotr.common.entity.npc.data.name;

import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class SimpleGenderedNameGenerator implements NPCNameGenerator {
   private final ResourceLocation maleNameBank;
   private final ResourceLocation femaleNameBank;

   public SimpleGenderedNameGenerator(ResourceLocation male, ResourceLocation female) {
      this.maleNameBank = male;
      this.femaleNameBank = female;
   }

   public String generateName(Random rand, boolean male) {
      return male ? this.getRandomNameFromBank(this.maleNameBank, rand) : this.getRandomNameFromBank(this.femaleNameBank, rand);
   }
}
