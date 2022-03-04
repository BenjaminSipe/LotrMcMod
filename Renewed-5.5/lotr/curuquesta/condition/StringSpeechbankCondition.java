package lotr.curuquesta.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import lotr.curuquesta.condition.predicate.PredicateParser;
import lotr.curuquesta.util.StringSerializer;

public class StringSpeechbankCondition extends SpeechbankCondition {
   public StringSpeechbankCondition(String conditionName, Function valueFromContext, PredicateParser predicateParser) {
      super(conditionName, valueFromContext, predicateParser);
   }

   public boolean isValidValue(String value) {
      return value != null && !value.isEmpty();
   }

   protected void writeValue(String value, ByteBuf buf) {
      StringSerializer.write(value, buf);
   }

   protected String readValue(ByteBuf buf) {
      return StringSerializer.read(buf);
   }
}
