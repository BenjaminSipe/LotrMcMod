package lotr.curuquesta.structure;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.curuquesta.SpeechbankContext;

public class SpeechbankEntry {
   private final Set contextSatisfiers;
   private final List lines;

   public SpeechbankEntry(Set contextSatisfiers, List lines) {
      this.contextSatisfiers = contextSatisfiers;
      this.lines = lines;
   }

   public boolean doesContextSatisfyConditions(SpeechbankContext context) {
      try {
         return this.contextSatisfiers.stream().allMatch((p) -> {
            return p.satisfiesContext(context);
         });
      } catch (Exception var3) {
         throw new IllegalStateException(String.format("Error while processing speech entry conditions for entry %s", this.toString()), var3);
      }
   }

   public Stream streamLines() {
      return this.lines.stream();
   }

   public String toString() {
      return String.format("SpeechbankEntry: [contextSatisfiers = %s, lines = %s]", this.contextSatisfiers.stream().map(Object::toString).collect(Collectors.joining(", ")), this.lines.stream().collect(Collectors.joining(", ")));
   }
}
