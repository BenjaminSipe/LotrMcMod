package lotr.curuquesta.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.curuquesta.condition.predicate.ComplexPredicateParsers;
import lotr.curuquesta.condition.predicate.PredicateParser;

public class EnumSpeechbankCondition extends SpeechbankCondition {
   private final Enum[] enumValues;

   public EnumSpeechbankCondition(String conditionName, Enum[] enumValues, Function valueFromContext, PredicateParser predicateParser) {
      super(conditionName, valueFromContext, predicateParser);
      this.enumValues = enumValues;
   }

   public EnumSpeechbankCondition(String conditionName, Enum[] enumValues, Function valueFromContext) {
      this(conditionName, enumValues, valueFromContext, ComplexPredicateParsers.logicalOrOfValues((s) -> {
         return parseEnum(enumValues, s);
      }));
   }

   public static EnumSpeechbankCondition enumWithComparableExpressions(String conditionName, Enum[] enumValues, Function valueFromContext) {
      return new EnumSpeechbankCondition(conditionName, enumValues, valueFromContext, ComplexPredicateParsers.logicalExpressionOfComparableSubpredicates((s) -> {
         return parseEnum(enumValues, s);
      }));
   }

   private static Enum parseEnum(Enum[] enumValues, String s) {
      return (Enum)Stream.of(enumValues).filter((e) -> {
         return e.name().equalsIgnoreCase(s);
      }).findFirst().orElseThrow(() -> {
         String errorMsg = String.format("No such value '%s' in enum - acceptable values are [%s]", s, Stream.of(enumValues).map((e) -> {
            return e.name().toLowerCase();
         }).collect(Collectors.joining(", ")));
         return new IllegalArgumentException(errorMsg);
      });
   }

   public boolean isValidValue(Enum value) {
      return value != null;
   }

   protected void writeValue(Enum value, ByteBuf buf) {
      buf.writeInt(value.ordinal());
   }

   protected Enum readValue(ByteBuf buf) {
      int ordinal = buf.readInt();
      if (ordinal < 0 || ordinal >= this.enumValues.length) {
         ordinal = 0;
      }

      return this.enumValues[ordinal];
   }
}
