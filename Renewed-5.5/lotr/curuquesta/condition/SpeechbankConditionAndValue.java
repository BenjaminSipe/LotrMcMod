package lotr.curuquesta.condition;

import io.netty.buffer.ByteBuf;

public class SpeechbankConditionAndValue {
   private final SpeechbankCondition condition;
   private final Object value;

   private SpeechbankConditionAndValue(SpeechbankCondition condition, Object value) {
      this.condition = condition;
      this.value = value;
      if (!condition.isValidValue(value)) {
         throw new IllegalArgumentException("Speechbank condition " + condition.getConditionName() + " cannot accept a value of " + value + "!");
      }
   }

   public static SpeechbankConditionAndValue of(SpeechbankCondition condition, Object value) {
      return new SpeechbankConditionAndValue(condition, value);
   }

   public SpeechbankCondition getCondition() {
      return this.condition;
   }

   public String getConditionName() {
      return this.condition.getConditionName();
   }

   public Object getValue() {
      return this.value;
   }

   public void writeValue(ByteBuf buf) {
      this.condition.writeValue(this.value, buf);
   }

   public static SpeechbankConditionAndValue readValue(SpeechbankCondition condition, ByteBuf buf) {
      Object value = condition.readValue(buf);
      return of(condition, value);
   }

   public String toString() {
      return String.format("%s=%s", this.getCondition().getConditionName(), String.valueOf(this.getValue()));
   }
}
