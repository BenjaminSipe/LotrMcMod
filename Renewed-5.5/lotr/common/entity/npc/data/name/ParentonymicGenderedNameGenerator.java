package lotr.common.entity.npc.data.name;

import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class ParentonymicGenderedNameGenerator implements NPCNameGenerator {
   private final ResourceLocation maleNameBank;
   private final ResourceLocation femaleNameBank;

   public ParentonymicGenderedNameGenerator(ResourceLocation male, ResourceLocation female) {
      this.maleNameBank = male;
      this.femaleNameBank = female;
   }

   public String generateName(Random rand, boolean male) {
      return male ? String.format("%s son of %s", this.getRandomNameFromBank(this.maleNameBank, rand), this.getRandomNameFromBank(this.maleNameBank, rand)) : String.format("%s daughter of %s", this.getRandomNameFromBank(this.femaleNameBank, rand), this.getRandomNameFromBank(this.femaleNameBank, rand));
   }
}
