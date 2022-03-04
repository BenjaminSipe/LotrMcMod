package lotr.curuquesta.structure;

import java.util.List;
import lotr.curuquesta.SpeechbankContext;

public class AlternativeConditionSets implements SpeechbankContextSatisfier {
   private final List alternatives;

   public AlternativeConditionSets(List alternatives) {
      this.alternatives = alternatives;
   }

   public boolean satisfiesContext(SpeechbankContext context) {
      return this.alternatives.stream().anyMatch((set) -> {
         return set.satisfiesContext(context);
      });
   }
}
