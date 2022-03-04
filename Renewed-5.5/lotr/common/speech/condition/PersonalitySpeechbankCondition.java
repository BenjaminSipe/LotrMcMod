package lotr.common.speech.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import java.util.function.Predicate;
import lotr.common.entity.npc.data.PersonalityTrait;
import lotr.common.entity.npc.data.PersonalityTraits;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.condition.predicate.ComplexPredicateParsers;
import net.minecraft.network.PacketBuffer;

public class PersonalitySpeechbankCondition extends SpeechbankCondition {
   public PersonalitySpeechbankCondition(String conditionName, Function valueFromContext) {
      super(conditionName, valueFromContext, ComplexPredicateParsers.logicalExpressionOfSubpredicates(PersonalitySpeechbankCondition::parsePersonalityPredicate));
   }

   private static Predicate parsePersonalityPredicate(String elem) {
      return (personalityTraits) -> {
         PersonalityTrait mainTrait = PersonalityTrait.fromMainName(elem);
         if (mainTrait != null) {
            return personalityTraits.hasTrait(mainTrait);
         } else {
            PersonalityTrait oppositeTrait = PersonalityTrait.fromOppositeName(elem);
            if (oppositeTrait != null) {
               return personalityTraits.hasOppositeTrait(oppositeTrait);
            } else {
               throw new IllegalArgumentException("Personality trait name '" + elem + "' does not refer to any known trait or opposite!");
            }
         }
      };
   }

   public boolean isValidValue(PersonalityTraits value) {
      return value != null;
   }

   protected void writeValue(PersonalityTraits value, ByteBuf buf) {
      value.write(new PacketBuffer(buf));
   }

   protected PersonalityTraits readValue(ByteBuf buf) {
      return PersonalityTraits.read(new PacketBuffer(buf));
   }
}
