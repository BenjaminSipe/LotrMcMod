package lotr.curuquesta.condition.predicate;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComplexPredicateParsers {
   public static PredicateParser logicalOrOfValues(Function objFromString) {
      return logicalOrOfSubpredicates((elem) -> {
         return (obj) -> {
            return Objects.equals(obj, objFromString.apply(elem));
         };
      });
   }

   public static PredicateParser logicalOrOfSubpredicates(PredicateParser subpredicateFromString) {
      return (s) -> {
         Stream var10000 = Stream.of(s.split(Pattern.quote("|"))).map(String::trim);
         subpredicateFromString.getClass();
         List anyOfSubpredicates = (List)var10000.map(subpredicateFromString::parsePredicateFromString).collect(Collectors.toList());
         return (obj) -> {
            return anyOfSubpredicates.stream().anyMatch((predicate) -> {
               return predicate.test(obj);
            });
         };
      };
   }

   public static PredicateParser logicalExpressionOfSubpredicates(PredicateParser subpredicateFromString) {
      return logicalOrOfSubpredicates((elem) -> {
         return parseLogicalAndOfSubpredicates(elem, subpredicateFromString);
      });
   }

   private static Predicate parseLogicalAndOfSubpredicates(String elem, PredicateParser subpredicateFromString) {
      Stream var10000 = Stream.of(elem.split(Pattern.quote("&"))).map(String::trim);
      subpredicateFromString.getClass();
      List logicalANDClauses = (List)var10000.map(subpredicateFromString::parsePredicateFromString).collect(Collectors.toList());
      Predicate composedAND = null;
      Iterator var4 = logicalANDClauses.iterator();

      while(var4.hasNext()) {
         Predicate clause = (Predicate)var4.next();
         if (composedAND == null) {
            composedAND = clause;
         } else {
            composedAND = composedAND.and(clause);
         }
      }

      return composedAND;
   }

   public static PredicateParser logicalExpressionOfComparableSubpredicates(Function objFromString) {
      return logicalExpressionOfComparableSubpredicates(objFromString, AsymmetricComparator.naturalComparator());
   }

   public static PredicateParser logicalExpressionOfComparableSubpredicates(Function objFromString, AsymmetricComparator comparator) {
      return logicalExpressionOfSubpredicates((elem) -> {
         return parseComparableSubpredicate(elem, objFromString, comparator);
      });
   }

   private static Predicate parseComparableSubpredicate(String elem, Function objFromString, AsymmetricComparator comparator) {
      if (elem.startsWith(">=")) {
         return (obj) -> {
            return comparator.compareAndTestResult(obj, objFromString.apply(elem.substring(">=".length())), (result) -> {
               return result >= 0;
            });
         };
      } else if (elem.startsWith("<=")) {
         return (obj) -> {
            return comparator.compareAndTestResult(obj, objFromString.apply(elem.substring("<=".length())), (result) -> {
               return result <= 0;
            });
         };
      } else if (elem.startsWith(">")) {
         return (obj) -> {
            return comparator.compareAndTestResult(obj, objFromString.apply(elem.substring(">".length())), (result) -> {
               return result > 0;
            });
         };
      } else if (elem.startsWith("<")) {
         return (obj) -> {
            return comparator.compareAndTestResult(obj, objFromString.apply(elem.substring("<".length())), (result) -> {
               return result < 0;
            });
         };
      } else {
         String equalityElem = elem.startsWith("=") ? elem.substring("=".length()) : elem;
         return (obj) -> {
            return Objects.equals(obj, objFromString.apply(equalityElem));
         };
      }
   }
}
