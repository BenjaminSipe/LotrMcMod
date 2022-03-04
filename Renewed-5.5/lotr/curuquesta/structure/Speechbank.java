package lotr.curuquesta.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lotr.curuquesta.SpeechbankContext;

public class Speechbank {
   private final String speechbankName;
   private final List entries;

   public Speechbank(String speechbankName, List entries) {
      this.speechbankName = speechbankName;
      this.entries = entries;
   }

   public static Speechbank getFallbackSpeechbank(String name, List fallbackMessages) {
      return new Speechbank(name, ImmutableList.of(new SpeechbankEntry(ImmutableSet.of(), fallbackMessages)));
   }

   public String getRandomSpeech(SpeechbankContext context, Random rand) {
      List matchingLines = this.filterMatchingLines(context);
      if (matchingLines.isEmpty()) {
         return String.format("Speechbank %s found no lines that satisfy the current context!", this.speechbankName);
      } else {
         String line = (String)matchingLines.get(rand.nextInt(matchingLines.size()));
         line = this.fillAllVariablesInLine(line, context);
         return line;
      }
   }

   private List filterMatchingLines(SpeechbankContext context) {
      return (List)this.entries.stream().filter((e) -> {
         return e.doesContextSatisfyConditions(context);
      }).flatMap(SpeechbankEntry::streamLines).collect(Collectors.toList());
   }

   private String fillAllVariablesInLine(String line, SpeechbankContext context) {
      String[] callback = new String[]{line};
      context.forEachReplaceableVariable((variable, value) -> {
         callback[0] = variable.fillMatchesInSpeechLine(callback[0], value);
      });
      return callback[0];
   }

   public List getEntriesView() {
      return new ArrayList(this.entries);
   }
}
