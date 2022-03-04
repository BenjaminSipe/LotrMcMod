package io.gitlab.dwarfyassassin.lotrucp.core.patches.base;

import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.tree.ClassNode;

public abstract class Patcher {
   protected Map classes = new HashMap();
   private String patcherName;

   public Patcher(String name) {
      this.patcherName = name;
   }

   public Patcher.LoadingPhase getLoadPhase() {
      return Patcher.LoadingPhase.CORE_MOD_LOADING;
   }

   public boolean shouldInit() {
      return true;
   }

   public boolean isDone() {
      return this.classes.isEmpty();
   }

   public boolean canRun(String className) {
      return this.classes.containsKey(className);
   }

   public void run(String className, ClassNode classNode) {
      ((Patcher.ConsumerImplBecauseNoLambdas)this.classes.get(className)).accept(classNode);
      this.classes.remove(className);
   }

   public String getName() {
      return this.patcherName;
   }

   public static enum LoadingPhase {
      CORE_MOD_LOADING,
      FORGE_MOD_LOADING;
   }

   public interface ConsumerImplBecauseNoLambdas {
      void accept(Object var1);
   }
}
