package io.gitlab.dwarfyassassin.lotrucp.core.patches;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.Patcher;
import io.gitlab.dwarfyassassin.lotrucp.core.utils.ASMUtils;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class FMLPatcher extends Patcher {
   public FMLPatcher() {
      super("FML");
      this.classes.put("cpw.mods.fml.common.LoadController", new Patcher.ConsumerImplBecauseNoLambdas() {
         public void accept(ClassNode node) {
            FMLPatcher.this.patchLoadController(node);
         }
      });
   }

   private void patchLoadController(ClassNode classNode) {
      MethodNode method = ASMUtils.findMethod(classNode, "buildModList", "(Lcpw/mods/fml/common/event/FMLLoadEvent;)V");
      if (method != null) {
         method.instructions.insert(new MethodInsnNode(184, "io/gitlab/dwarfyassassin/lotrucp/core/hooks/PreMCHooks", "postFMLLoad", "()V", false));
         UCPCoreMod.log.info("Patched the FML load controller.");
      }
   }

   private void patchModContainer(ClassNode classNode) {
      MethodNode method = ASMUtils.findMethod(classNode, "bindMetadata", "(Lcpw/mods/fml/common/MetadataCollection;)V");
      if (method != null) {
         AbstractInsnNode[] var3 = method.instructions.toArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AbstractInsnNode node = var3[var5];
            if (node instanceof FieldInsnNode) {
               FieldInsnNode fieldNode = (FieldInsnNode)node;
               if (fieldNode.name.equals("dependants")) {
                  InsnList insList = new InsnList();
                  insList.add(new VarInsnNode(25, 0));
                  insList.add(new FieldInsnNode(180, "cpw/mods/fml/common/FMLModContainer", "modMetadata", "Lcpw/mods/fml/common/ModMetadata;"));
                  insList.add(new MethodInsnNode(184, "io/gitlab/dwarfyassassin/lotrucp/core/hooks/PreMCHooks", "forgeLoadOrderHook", "(Lcpw/mods/fml/common/ModMetadata;)V", false));
                  method.instructions.insert(fieldNode, insList);
                  break;
               }
            }
         }

         UCPCoreMod.log.info("Patched the FML dependency loader.");
      }
   }

   private void patchLoader(ClassNode classNode) {
      MethodNode method = ASMUtils.findMethod(classNode, "loadMods", "()V");
      if (method != null) {
         AbstractInsnNode[] var3 = method.instructions.toArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AbstractInsnNode node = var3[var5];
            if (node instanceof MethodInsnNode && node.getOpcode() == 184) {
               MethodInsnNode methodNode = (MethodInsnNode)node;
               if (methodNode.name.equals("copyOf") && methodNode.owner.equals("com/google/common/collect/ImmutableList")) {
                  MethodInsnNode insertNode = new MethodInsnNode(184, "io/gitlab/dwarfyassassin/lotrucp/core/hooks/PreMCHooks", "postFMLLoad", "()V", false);
                  method.instructions.insert(methodNode.getNext(), insertNode);
                  break;
               }
            }
         }

         UCPCoreMod.log.info("Patched the FML loader.");
      }
   }
}
