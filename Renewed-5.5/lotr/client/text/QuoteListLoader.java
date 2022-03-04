package lotr.client.text;

import com.google.common.collect.ImmutableList;
import java.io.BufferedReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.StringUtils;

public class QuoteListLoader extends TranslatableTextReloadListener {
   private static final Random RANDOM = new Random();

   public QuoteListLoader(Minecraft mc) {
      super(mc, ".txt");
   }

   public String getRandomQuote(ResourceLocation basePath) {
      return ((QuoteList)this.getOrLoadTextResource(basePath)).getRandomQuote(RANDOM);
   }

   public IFormattableTextComponent getRandomQuoteComponent(ResourceLocation basePath) {
      return new StringTextComponent(this.getRandomQuote(basePath));
   }

   protected ResourceLocation convertToFullResourcePath(ResourceLocation basePath) {
      return new ResourceLocation(basePath.func_110624_b(), String.format("quotes/%s", basePath.func_110623_a()));
   }

   protected QuoteList loadResource(ResourceLocation langPath, BufferedReader reader, ParentTextResourceLoader.NoopParentLoader parentLoader) {
      List quotes = (List)reader.lines().map(this::stripCommentsOut).map(String::trim).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
      return new QuoteList(langPath, quotes);
   }

   protected ParentTextResourceLoader.NoopParentLoader createNewParentLoader(ResourceLocation topLevelPath, String lang) {
      return new ParentTextResourceLoader.NoopParentLoader();
   }

   protected QuoteList loadErroredFallbackResource(ResourceLocation langPath, String errorMsg) {
      return new QuoteList(langPath, ImmutableList.of(errorMsg));
   }

   private String stripCommentsOut(String line) {
      line = stripCommentTypeOut(line, "#");
      line = stripCommentTypeOut(line, "//");
      return line;
   }

   private static String stripCommentTypeOut(String line, String commentStart) {
      if (line.contains(commentStart)) {
         line = line.substring(0, line.indexOf(commentStart));
      }

      return line;
   }
}
