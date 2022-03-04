package lotr.common.speech.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.condition.predicate.ComplexPredicateParsers;
import lotr.curuquesta.condition.predicate.PredicateParser;

public class BiomeSpeechbankCondition extends SpeechbankCondition {
   public static final String HOME = "#home";
   public static final String FOREIGN = "#foreign";

   public BiomeSpeechbankCondition(String conditionName, Function valueFromContext) {
      super(conditionName, valueFromContext, parsePredicate());
   }

   private static PredicateParser parsePredicate() {
      return ComplexPredicateParsers.logicalOrOfSubpredicates((elem) -> {
         if (elem.equalsIgnoreCase("#home")) {
            return BiomeWithTags::isHomeBiome;
         } else {
            return elem.equalsIgnoreCase("#foreign") ? BiomeWithTags::isForeignBiome : (biomeWithTags) -> {
               return biomeWithTags.getBiomeName().toString().equals(elem);
            };
         }
      });
   }

   public boolean isValidValue(BiomeWithTags value) {
      return value != null;
   }

   protected void writeValue(BiomeWithTags value, ByteBuf buf) {
      value.write(buf);
   }

   protected BiomeWithTags readValue(ByteBuf buf) {
      return BiomeWithTags.read(buf);
   }
}
