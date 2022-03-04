package lotr.curuquesta.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import lotr.curuquesta.condition.predicate.ComplexPredicateParsers;
import lotr.curuquesta.condition.predicate.PredicateParser;

public class FloatSpeechbankCondition extends SpeechbankCondition {
   public FloatSpeechbankCondition(String conditionName, Function valueFromContext, PredicateParser predicateParser) {
      super(conditionName, valueFromContext, predicateParser);
   }

   public FloatSpeechbankCondition(String conditionName, Function valueFromContext) {
      this(conditionName, valueFromContext, ComplexPredicateParsers.logicalExpressionOfComparableSubpredicates(Float::parseFloat));
   }

   public boolean isValidValue(Float value) {
      return value != null;
   }

   protected void writeValue(Float value, ByteBuf buf) {
      buf.writeFloat(value);
   }

   protected Float readValue(ByteBuf buf) {
      return buf.readFloat();
   }
}
