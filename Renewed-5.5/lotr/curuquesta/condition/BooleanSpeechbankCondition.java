package lotr.curuquesta.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import lotr.curuquesta.condition.predicate.PredicateParser;
import lotr.curuquesta.condition.predicate.SimplePredicateParsers;

public class BooleanSpeechbankCondition extends SpeechbankCondition {
   public BooleanSpeechbankCondition(String conditionName, Function valueFromContext, PredicateParser predicateParser) {
      super(conditionName, valueFromContext, predicateParser);
   }

   public BooleanSpeechbankCondition(String conditionName, Function valueFromContext) {
      this(conditionName, valueFromContext, SimplePredicateParsers::booleanEquality);
   }

   public boolean isValidValue(Boolean value) {
      return value != null;
   }

   protected void writeValue(Boolean value, ByteBuf buf) {
      buf.writeBoolean(value);
   }

   protected Boolean readValue(ByteBuf buf) {
      return buf.readBoolean();
   }
}
