package lotr.common.entity.npc.data.name;

import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class NameBank {
   private final ResourceLocation path;
   private final List names;

   public NameBank(ResourceLocation path, List names) {
      this.path = path;
      this.names = names;
   }

   public String getRandomName(Random rand) {
      return this.names.isEmpty() ? String.format("Name bank %s was empty!", this.path) : (String)this.names.get(rand.nextInt(this.names.size()));
   }
}
