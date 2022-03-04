package lotr.common.speech;

import lotr.common.LOTRLog;
import lotr.curuquesta.SpeechbankContext;
import lotr.curuquesta.SpeechbankEngine;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.condition.SpeechbankConditionAndValue;
import lotr.curuquesta.replaceablevar.ReplaceableSpeechVariable;
import net.minecraft.network.PacketBuffer;

public class SpeechbankContextSerializer {
   private final SpeechbankEngine engine;

   public SpeechbankContextSerializer(SpeechbankEngine engine) {
      this.engine = engine;
   }

   public void write(SpeechbankContext context, PacketBuffer buf) {
      buf.func_150787_b(context.getNumConditions());
      context.forEachCondition((conditionAndValue) -> {
         buf.func_180714_a(conditionAndValue.getCondition().getConditionName());
         conditionAndValue.writeValue(buf);
      });
      buf.func_150787_b(context.getNumReplaceableVariables());
      context.forEachReplaceableVariable((variable, value) -> {
         buf.func_180714_a(variable.getShortAlias());
         buf.func_180714_a(value);
      });
   }

   public SpeechbankContext read(PacketBuffer buf) {
      SpeechbankContext context = SpeechbankContext.newContext();
      int numConditions = buf.func_150792_a();

      int numReplaceableVariables;
      for(numReplaceableVariables = 0; numReplaceableVariables < numConditions; ++numReplaceableVariables) {
         String conditionName = buf.func_218666_n();
         SpeechbankCondition condition = this.engine.getCondition(conditionName);
         if (condition == null) {
            LOTRLog.warn("Received speechbank context from server with an unknown condition name '%s! Exiting read now to prevent continuing to read malformed data", conditionName);
            return context;
         }

         SpeechbankConditionAndValue conditionAndValue = SpeechbankConditionAndValue.readValue(condition, buf);
         context.withCondition(conditionAndValue);
      }

      numReplaceableVariables = buf.func_150792_a();

      for(int i = 0; i < numReplaceableVariables; ++i) {
         String shortAlias = buf.func_218666_n();
         String value = buf.func_218666_n();
         ReplaceableSpeechVariable variable = this.engine.getReplaceableVariableByShortAlias(shortAlias);
         if (variable == null) {
            LOTRLog.warn("Received speechbank context from server with an unknown replaceable variable alias '%s'! Exiting read now to prevent continuing to read malformed data", shortAlias);
            return context;
         }

         context.withReplaceableVariable(variable, value);
      }

      return context;
   }
}
