package lotr.common.speech.condition;

import io.netty.buffer.ByteBuf;
import java.util.function.Function;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.condition.predicate.ComplexPredicateParsers;
import lotr.curuquesta.util.StringSerializer;
import net.minecraft.util.ResourceLocation;

public class NullableResourceLocationSpeechbankCondition extends SpeechbankCondition {
   public NullableResourceLocationSpeechbankCondition(String conditionName, Function valueFromContext) {
      super(conditionName, valueFromContext, ComplexPredicateParsers.logicalOrOfValues(NullableResourceLocationSpeechbankCondition::parseNullableResourceLocation));
   }

   private static ResourceLocation parseNullableResourceLocation(String s) {
      return s == null ? null : new ResourceLocation(s);
   }

   public boolean isValidValue(ResourceLocation value) {
      return true;
   }

   protected void writeValue(ResourceLocation value, ByteBuf buf) {
      buf.writeBoolean(value != null);
      if (value != null) {
         StringSerializer.write(value.toString(), buf);
      }

   }

   protected ResourceLocation readValue(ByteBuf buf) {
      boolean hasValue = buf.readBoolean();
      return hasValue ? new ResourceLocation(StringSerializer.read(buf)) : null;
   }
}
