package lotr.curuquesta.condition.predicate;

import java.util.function.Predicate;
import lotr.curuquesta.SpeechbankContext;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.structure.SpeechbankContextSatisfier;

public class SpeechbankConditionAndPredicate implements SpeechbankContextSatisfier {
   private final SpeechbankCondition condition;
   private final Predicate predicate;

   private SpeechbankConditionAndPredicate(SpeechbankCondition condition, Predicate predicate) {
      this.condition = condition;
      this.predicate = predicate;
   }

   public static SpeechbankConditionAndPredicate of(SpeechbankCondition condition, Predicate predicate) {
      return new SpeechbankConditionAndPredicate(condition, predicate);
   }

   public boolean satisfiesContext(SpeechbankContext context) {
      return this.predicate.test(context.getConditionValue(this.condition));
   }
}
