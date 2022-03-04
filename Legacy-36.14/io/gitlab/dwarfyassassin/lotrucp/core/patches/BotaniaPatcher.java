package io.gitlab.dwarfyassassin.lotrucp.core.patches;

import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.ModPatcher;
import io.gitlab.dwarfyassassin.lotrucp.core.patches.base.Patcher;
import io.gitlab.dwarfyassassin.lotrucp.core.utils.ASMUtils;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class BotaniaPatcher extends ModPatcher {
   public BotaniaPatcher() {
      super("Botania", "Botania");
      this.classes.put("vazkii.botania.common.block.subtile.generating.SubTileKekimurus", new Patcher.ConsumerImplBecauseNoLambdas() {
         public void accept(ClassNode node) {
            BotaniaPatcher.this.patchKekimurus(node);
         }
      });
   }

   private void patchKekimurus(ClassNode classNode) {
      MethodNode method = ASMUtils.findMethod(classNode, "onUpdate", "()V");
      if (method != null) {
         AbstractInsnNode[] var3 = method.instructions.toArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AbstractInsnNode node = var3[var5];
            if (node instanceof TypeInsnNode) {
               TypeInsnNode typeNode = (TypeInsnNode)node;
               if (typeNode.desc.equals("net/minecraft/block/BlockCake")) {
                  typeNode.desc = "lotr/common/block/LOTRBlockPlaceableFood";
                  break;
               }
            }
         }

         UCPCoreMod.log.info("Patched the Kekimurus to eat all LOTR cakes.");
      }
   }
}
