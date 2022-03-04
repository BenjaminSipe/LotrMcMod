package io.gitlab.dwarfyassassin.lotrucp.core;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.Patcher;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class UCPClassTransformer implements IClassTransformer {
   public byte[] transform(String name, String transformedName, byte[] classBytes) {
      boolean ran = false;
      Iterator var5 = UCPCoreMod.activePatches.iterator();

      while(var5.hasNext()) {
         Patcher patcher = (Patcher)var5.next();
         if (patcher.canRun(name)) {
            ran = true;
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBytes);
            classReader.accept(classNode, 0);
            UCPCoreMod.log.info("Running patcher " + patcher.getName() + " for " + name);
            patcher.run(name, classNode);
            ClassWriter writer = new ClassWriter(1);
            classNode.accept(writer);
            classBytes = writer.toByteArray();
         }
      }

      if (ran) {
         Set removes = new HashSet();
         Iterator var11 = UCPCoreMod.activePatches.iterator();

         while(var11.hasNext()) {
            Patcher patcher = (Patcher)var11.next();
            if (patcher.isDone()) {
               removes.add(patcher);
            }
         }

         UCPCoreMod.activePatches.removeAll(removes);
         if (UCPCoreMod.activePatches.isEmpty()) {
            UCPCoreMod.log.info("Ran all active patches.");
         }
      }

      return classBytes;
   }

   static {
      FMLLaunchHandler launchHandler = (FMLLaunchHandler)ReflectionHelper.getPrivateValue(FMLLaunchHandler.class, (Object)null, new String[]{"INSTANCE"});
      LaunchClassLoader var1 = (LaunchClassLoader)ReflectionHelper.getPrivateValue(FMLLaunchHandler.class, launchHandler, new String[]{"classLoader"});
   }
}
