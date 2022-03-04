package lotr.curuquesta.condition.predicate;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class SimplePredicateParsers {
   public static Predicate dummyAlwaysMatch(String s) {
      return (obj) -> {
         return true;
      };
   }

   public static Predicate booleanEquality(String s) {
      boolean expected = Boolean.parseBoolean(s);
      return (bVal) -> {
         return bVal != null && bVal == expected;
      };
   }

   public static Predicate genericToStringEquality(String s) {
      return (obj) -> {
         return String.valueOf(obj).equals(s);
      };
   }

   public static PredicateParser genericEqualityParsingFromString(Function objFromString) {
      return (s) -> {
         Object parsedObject = objFromString.apply(s);
         return (obj) -> {
            return Objects.equals(obj, parsedObject);
         };
      };
   }
}
