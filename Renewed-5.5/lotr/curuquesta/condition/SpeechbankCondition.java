package lotr.curuquesta.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import java.util.function.Predicate;
import lotr.curuquesta.SpeechbankContextProvider;
import lotr.curuquesta.condition.predicate.PredicateParser;

public abstract class SpeechbankCondition {
   private final String conditionName;
   private final Function valueFromContext;
   private final PredicateParser predicateParser;

   protected SpeechbankCondition(String conditionName, Function valueFromContext, PredicateParser predicateParser) {
      this.conditionName = conditionName;
      this.valueFromContext = valueFromContext;
      this.predicateParser = predicateParser;
   }

   public String getConditionName() {
      return this.conditionName;
   }

   public final Object getValueFromContext(SpeechbankContextProvider contextProvider) {
      return this.valueFromContext.apply(contextProvider);
   }

   public abstract boolean isValidValue(Object var1);

   protected abstract void writeValue(Object var1, ByteBuf var2);

   protected abstract Object readValue(ByteBuf var1);

   public final Predicate parsePredicateFromJsonString(String s) {
      return this.predicateParser.parsePredicateFromString(s);
   }
}
