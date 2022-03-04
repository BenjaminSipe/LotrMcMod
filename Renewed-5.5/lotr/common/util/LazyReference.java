package lotr.common.util;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.util.ResourceLocation;

public class LazyReference {
   private final ResourceLocation referenceName;
   private final Function referenceResolver;
   private final Consumer errorLoggerIfResolvingFails;
   private Object resolvedReference;
   private boolean attemptedToResolve = false;

   private LazyReference(ResourceLocation name, Function resolver, Consumer errorLogger) {
      Objects.requireNonNull(name, "Reference name must not be null");
      Objects.requireNonNull(resolver, "Reference resolver function must not be null");
      Objects.requireNonNull(errorLogger, "Error logger also must not be null");
      this.referenceName = name;
      this.referenceResolver = resolver;
      this.errorLoggerIfResolvingFails = errorLogger;
   }

   public static LazyReference of(ResourceLocation name, Function resolver, Consumer errorLogger) {
      return new LazyReference(name, resolver, errorLogger);
   }

   public Object resolveReference() {
      if (!this.attemptedToResolve) {
         this.resolvedReference = this.referenceResolver.apply(this.referenceName);
         this.attemptedToResolve = true;
         if (this.resolvedReference == null) {
            this.errorLoggerIfResolvingFails.accept(this.referenceName);
         }
      }

      return this.resolvedReference;
   }

   public ResourceLocation getReferenceName() {
      return this.referenceName;
   }
}
