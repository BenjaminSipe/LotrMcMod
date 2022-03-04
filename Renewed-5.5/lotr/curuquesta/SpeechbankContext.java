package lotr.curuquesta;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lotr.curuquesta.condition.SpeechbankCondition;
import lotr.curuquesta.condition.SpeechbankConditionAndValue;
import lotr.curuquesta.replaceablevar.ReplaceableSpeechVariable;

public class SpeechbankContext {
   private Map conditionValueMap = new HashMap();
   private Map replaceVariableValues = new HashMap();

   private SpeechbankContext() {
   }

   public static SpeechbankContext newContext() {
      return new SpeechbankContext();
   }

   public SpeechbankContext withCondition(SpeechbankCondition condition, Object value) {
      return this.withCondition(SpeechbankConditionAndValue.of(condition, value));
   }

   public SpeechbankContext withCondition(SpeechbankConditionAndValue conditionAndValue) {
      if (this.conditionValueMap.containsKey(conditionAndValue.getConditionName())) {
         throw new IllegalStateException("Speechbank context already has a value set for condition " + conditionAndValue.getConditionName());
      } else {
         this.conditionValueMap.put(conditionAndValue.getConditionName(), conditionAndValue);
         return this;
      }
   }

   public SpeechbankContext withReplaceableVariable(ReplaceableSpeechVariable variable, String value) {
      if (this.replaceVariableValues.containsKey(variable)) {
         throw new IllegalStateException("Speechbank context already has a value set for variable " + variable);
      } else {
         this.replaceVariableValues.put(variable, value);
         return this;
      }
   }

   public Object getConditionValue(SpeechbankCondition condition) {
      if (!this.conditionValueMap.containsKey(condition.getConditionName())) {
         throw new IllegalStateException("Asked speechbank context for the value of condition " + condition + ", but no value was set!");
      } else {
         return ((SpeechbankConditionAndValue)this.conditionValueMap.get(condition.getConditionName())).getValue();
      }
   }

   public int getNumConditions() {
      return this.conditionValueMap.size();
   }

   public void forEachCondition(Consumer action) {
      this.conditionValueMap.values().forEach(action);
   }

   public int getNumReplaceableVariables() {
      return this.replaceVariableValues.size();
   }

   public void forEachReplaceableVariable(BiConsumer action) {
      this.replaceVariableValues.entrySet().forEach((e) -> {
         action.accept(e.getKey(), e.getValue());
      });
   }

   public String toString() {
      return String.format("SpeechbankContext: conditions = [%s], replaceable variables = [%s]", this.conditionValueMap.values().stream().sorted(Comparator.comparing(SpeechbankConditionAndValue::getConditionName)).map(SpeechbankConditionAndValue::toString).collect(Collectors.joining(", ")), this.replaceVariableValues.entrySet().stream().sorted(Comparator.comparing((e) -> {
         return ((ReplaceableSpeechVariable)e.getKey()).getLongAlias();
      })).map((e) -> {
         return String.format("%s=%s", ((ReplaceableSpeechVariable)e.getKey()).getLongAlias(), e.getValue());
      }).collect(Collectors.joining(", ")));
   }
}
