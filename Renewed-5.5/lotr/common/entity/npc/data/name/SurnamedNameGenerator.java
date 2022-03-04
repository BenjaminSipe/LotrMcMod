package lotr.common.entity.npc.data.name;

import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class SurnamedNameGenerator implements NPCNameGenerator {
   private final NPCNameGenerator forenameGenerator;
   private final NPCNameGenerator surnameGenerator;
   private final float surnameChance;
   private static final float DEFAULT_SURNAME_CHANCE = 1.0F;

   public SurnamedNameGenerator(NPCNameGenerator forenameGenerator, NPCNameGenerator surnameGenerator, float surnameChance) {
      this.forenameGenerator = forenameGenerator;
      this.surnameGenerator = surnameGenerator;
      this.surnameChance = surnameChance;
   }

   public SurnamedNameGenerator(NPCNameGenerator forenameGenerator, NPCNameGenerator surnameGenerator) {
      this(forenameGenerator, surnameGenerator, 1.0F);
   }

   public SurnamedNameGenerator(ResourceLocation maleForename, ResourceLocation femaleForename, ResourceLocation surname, float surnameChance) {
      this(new SimpleGenderedNameGenerator(maleForename, femaleForename), new SimpleNameGenerator(surname), surnameChance);
   }

   public SurnamedNameGenerator(ResourceLocation maleForename, ResourceLocation femaleForename, ResourceLocation surname) {
      this(maleForename, femaleForename, surname, 1.0F);
   }

   public String generateName(Random rand, boolean male) {
      String forename = this.forenameGenerator.generateName(rand, male);
      return rand.nextFloat() < this.surnameChance ? String.format("%s %s", forename, this.surnameGenerator.generateName(rand, male)) : forename;
   }
}
