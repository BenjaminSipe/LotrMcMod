package lotr.curuquesta.condition.predicate;

import java.util.OptionalInt;
import java.util.function.IntPredicate;

public interface AsymmetricComparator {
   OptionalInt compare(Object var1, Object var2);

   default boolean compareAndTestResult(Object objectInContext, Object objectInPredicate, IntPredicate comparisonTest) {
      OptionalInt comparison = this.compare(objectInContext, objectInPredicate);
      return comparison.isPresent() && comparisonTest.test(comparison.getAsInt());
   }

   static AsymmetricComparator naturalComparator() {
      return (objectInContext, objectInPredicate) -> {
         return OptionalInt.of(objectInContext.compareTo(objectInPredicate));
      };
   }
}
