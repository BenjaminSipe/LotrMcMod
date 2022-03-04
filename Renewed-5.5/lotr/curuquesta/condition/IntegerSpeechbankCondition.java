package lotr.curuquesta.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import lotr.curuquesta.condition.predicate.ComplexPredicateParsers;
import lotr.curuquesta.condition.predicate.PredicateParser;

public class IntegerSpeechbankCondition extends SpeechbankCondition {
   public IntegerSpeechbankCondition(String conditionName, Function valueFromContext, PredicateParser predicateParser) {
      super(conditionName, valueFromContext, predicateParser);
   }

   public IntegerSpeechbankCondition(String conditionName, Function valueFromContext) {
      this(conditionName, valueFromContext, ComplexPredicateParsers.logicalExpressionOfComparableSubpredicates(Integer::parseInt));
   }

   public boolean isValidValue(Integer value) {
      return value != null;
   }

   protected void writeValue(Integer value, ByteBuf buf) {
      buf.writeInt(value);
   }

   protected Integer readValue(ByteBuf buf) {
      return buf.readInt();
   }
}
