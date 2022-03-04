package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.entity.model.DwarfModel;
import lotr.common.entity.npc.DwarfEntity;
import lotr.common.util.CalendarUtil;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Vector3f;

public abstract class AbstractDwarfRenderer extends LOTRBipedRenderer {
   private static final float DWARF_SCALE = 0.77F;

   public AbstractDwarfRenderer(EntityRendererManager mgr) {
      super(mgr, DwarfModel::new, new DwarfModel(0.5F), new DwarfModel(1.0F), 0.385F);
   }

   protected void preRenderCallback(DwarfEntity dwarf, MatrixStack matStack, float f) {
      super.preRenderCallback(dwarf, matStack, f);
      matStack.func_227862_a_(0.77F, 0.77F, 0.77F);
      if (CalendarUtil.isAprilFools()) {
         matStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
      }

   }
}
