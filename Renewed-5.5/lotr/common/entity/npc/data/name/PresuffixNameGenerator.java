package lotr.common.entity.npc.data.name;

import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class PresuffixNameGenerator implements NPCNameGenerator {
   private final ResourceLocation prefixBank;
   private final ResourceLocation suffixBank;

   public PresuffixNameGenerator(ResourceLocation prefix, ResourceLocation suffix) {
      this.prefixBank = prefix;
      this.suffixBank = suffix;
   }

   public String generateName(Random rand, boolean male) {
      return this.getRandomNameFromBank(this.prefixBank, rand) + this.getRandomNameFromBank(this.suffixBank, rand);
   }
}
