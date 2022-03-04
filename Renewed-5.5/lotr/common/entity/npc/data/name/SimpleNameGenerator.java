package lotr.common.entity.npc.data.name;

import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class SimpleNameGenerator implements NPCNameGenerator {
   private final ResourceLocation nameBank;

   public SimpleNameGenerator(ResourceLocation bank) {
      this.nameBank = bank;
   }

   public String generateName(Random rand, boolean male) {
      return this.getRandomNameFromBank(this.nameBank, rand);
   }
}
