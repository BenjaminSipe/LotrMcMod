package lotr.curuquesta;

import java.util.HashMap;
import java.util.Map;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.replaceablevar.ReplaceableSpeechVariable;

public class SpeechbankEngine {
   private final Map conditions = new HashMap();
   private final Map replaceableVariablesByShortAlias = new HashMap();

   private SpeechbankEngine() {
   }

   public static SpeechbankEngine createInstance() {
      return new SpeechbankEngine();
   }

   public SpeechbankEngine registerCondition(SpeechbankCondition condition) {
      String name = condition.getConditionName();
      if (this.conditions.containsKey(name)) {
         throw new IllegalStateException(String.format("Speechbank condition %s is already registered", name));
      } else {
         this.conditions.put(name, condition);
         return this;
      }
   }

   public SpeechbankCondition getCondition(String name) {
      return (SpeechbankCondition)this.conditions.get(name);
   }

   public SpeechbankEngine registerReplaceableVariable(ReplaceableSpeechVariable variable) {
      if (this.replaceableVariablesByShortAlias.values().stream().anyMatch((v) -> {
         return v.aliasMatches(variable);
      })) {
         throw new IllegalStateException(String.format("Speech variable %s conflicts with an already-registered alias", variable));
      } else {
         this.replaceableVariablesByShortAlias.put(variable.getShortAlias(), variable);
         return this;
      }
   }

   public ReplaceableSpeechVariable getReplaceableVariableByShortAlias(String shortAlias) {
      return (ReplaceableSpeechVariable)this.replaceableVariablesByShortAlias.get(shortAlias);
   }

   public SpeechbankContext populateContext(SpeechbankContextProvider contextProvider) {
      SpeechbankContext context = SpeechbankContext.newContext();
      this.conditions.values().forEach((condition) -> {
         context.withCondition(condition, condition.getValueFromContext(contextProvider));
      });
      this.replaceableVariablesByShortAlias.values().forEach((variable) -> {
         context.withReplaceableVariable(variable, variable.getValueFromContext(contextProvider));
      });
      return context;
   }
}
