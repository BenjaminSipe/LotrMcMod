package lotr.client.text;

import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class QuoteList {
   private final ResourceLocation path;
   private final List quotes;

   public QuoteList(ResourceLocation path, List quotes) {
      this.path = path;
      this.quotes = quotes;
   }

   public String getRandomQuote(Random rand) {
      return this.quotes.isEmpty() ? String.format("Quote list %s was empty!", this.path) : (String)this.quotes.get(rand.nextInt(this.quotes.size()));
   }
}
