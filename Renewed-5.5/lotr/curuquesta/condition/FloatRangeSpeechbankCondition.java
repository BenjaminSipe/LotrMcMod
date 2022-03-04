package lotr.curuquesta.condition;

import java.util.function.Function;
import lotr.curuquesta.condition.predicate.PredicateParser;

public class FloatRangeSpeechbankCondition extends FloatSpeechbankCondition {
   private final float minValue;
   private final float maxValue;

   public FloatRangeSpeechbankCondition(String conditionName, Function valueFromContext, PredicateParser predicateParser, float minValue, float maxValue) {
      super(conditionName, valueFromContext, predicateParser);
      this.minValue = minValue;
      this.maxValue = maxValue;
   }

   public FloatRangeSpeechbankCondition(String conditionName, Function valueFromContext, float minValue, float maxValue) {
      super(conditionName, valueFromContext);
      this.minValue = minValue;
      this.maxValue = maxValue;
   }

   public boolean isValidValue(Float value) {
      return value != null && value >= this.minValue && value <= this.maxValue;
   }
}
