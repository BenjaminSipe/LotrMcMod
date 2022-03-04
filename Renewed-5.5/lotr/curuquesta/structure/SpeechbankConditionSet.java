package lotr.curuquesta.structure;

import java.util.Set;
import lotr.curuquesta.SpeechbankContext;

public class SpeechbankConditionSet implements SpeechbankContextSatisfier {
   private final Set conditionsAndPredicates;

   public SpeechbankConditionSet(Set conditionsAndPredicates) {
      this.conditionsAndPredicates = conditionsAndPredicates;
   }

   public boolean satisfiesContext(SpeechbankContext context) {
      return this.conditionsAndPredicates.stream().allMatch((p) -> {
         return p.satisfiesContext(context);
      });
   }
}
