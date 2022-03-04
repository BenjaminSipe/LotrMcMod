package lotr.curuquesta.condition.predicate;

import java.util.function.Predicate;

@FunctionalInterface
public interface PredicateParser {
   Predicate parsePredicateFromString(String var1);
}
