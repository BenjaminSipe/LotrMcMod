package io.gitlab.dwarfyassassin.lotrucp.core.patches;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.ModPatcher;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.Patcher;
import io.gitlab.dwarfyassassin.lotrucp.core.utils.ASMUtils;
import net.minecraftforge.classloading.FMLForgePlugin;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ScreenshotEnhancedPatcher extends ModPatcher {
   public ScreenshotEnhancedPatcher() {
      super("Screenshots Enhanced", "screenshots");
      this.classes.put("lotr.client.render.entity.LOTRRenderScrapTrader", new Patcher.ConsumerImplBecauseNoLambdas() {
         public void accept(ClassNode node) {
            ScreenshotEnhancedPatcher.this.patchScrapTraderRender(node);
         }
      });
   }

   private void patchScrapTraderRender(ClassNode classNode) {
      MethodNode method = ASMUtils.findMethod(classNode, "doRender", "func_76986_a", "(Lnet/minecraft/entity/EntityLiving;DDDFF)V");
      if (method != null) {
         AbstractInsnNode[] var3 = method.instructions.toArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AbstractInsnNode node = var3[var5];
            if (node.getOpcode() == 182) {
               MethodInsnNode methodNode = (MethodInsnNode)node;
               if (methodNode.name.equals(FMLForgePlugin.RUNTIME_DEOBF ? "func_151463_i" : "getKeyCode") && methodNode.desc.equals("()I")) {
                  ASMUtils.removePreviousNodes(method.instructions, methodNode, 3);
                  FieldInsnNode keyCodeField = new FieldInsnNode(178, "net/undoredo/screenshots/KeyScreenshotListener", "screenshotKeyBinding", "Lnet/minecraft/client/settings/KeyBinding;");
                  method.instructions.insertBefore(methodNode, keyCodeField);
                  break;
               }
            }
         }

         UCPCoreMod.log.info("Patched the Oddment Collector render to be compatible with Screenshots Enhanced.");
      }
   }
}
