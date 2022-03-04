package io.gitlab.dwarfyassassin.lotrucp.core.utils;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import java.util.Iterator;
import net.minecraftforge.classloading.FMLForgePlugin;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public class ASMUtils {
   public static MethodNode findMethod(ClassNode classNode, String targetMethodName, String obfTargetMethodName, String targetMethodDesc) {
      return findMethod(classNode, FMLForgePlugin.RUNTIME_DEOBF ? obfTargetMethodName : targetMethodName, targetMethodDesc);
   }

   public static MethodNode findMethod(ClassNode classNode, String targetMethodName, String targetMethodDesc) {
      Iterator var3 = classNode.methods.iterator();

      MethodNode method;
      do {
         if (!var3.hasNext()) {
            UCPCoreMod.log.error("Couldn't find method " + targetMethodName + " with desc " + targetMethodDesc + " in " + classNode.name);
            return null;
         }

         method = (MethodNode)var3.next();
      } while(!method.name.equals(targetMethodName) || !method.desc.equals(targetMethodDesc));

      return method;
   }

   public static void removePreviousNodes(InsnList list, AbstractInsnNode start, int amount) {
      for(int i = 0; i < amount; ++i) {
         AbstractInsnNode prevNode = start.getPrevious();
         list.remove(prevNode);
      }

   }
}
