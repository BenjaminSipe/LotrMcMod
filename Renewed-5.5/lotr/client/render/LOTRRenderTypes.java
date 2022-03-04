package lotr.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.State;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderTypes extends RenderType {
   public static final RenderType ENTITY_TRANSLUCENT_NO_TEXTURE;

   private LOTRRenderTypes(String name, VertexFormat format, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
      super(name, format, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
      throw new UnsupportedOperationException("Don't instantiate this class, it's just for protected access! Use RenderType.makeType instead.");
   }

   private static String makeExtendedName(String name) {
      return (new ResourceLocation("lotr", name)).toString();
   }

   static {
      ENTITY_TRANSLUCENT_NO_TEXTURE = func_228633_a_(makeExtendedName("entity_translucent_no_texture"), DefaultVertexFormats.field_227849_i_, 7, 256, true, true, State.func_228694_a_().func_228724_a_(field_228523_o_).func_228726_a_(field_228515_g_).func_228716_a_(field_228532_x_).func_228713_a_(field_228517_i_).func_228714_a_(field_228491_A_).func_228719_a_(field_228528_t_).func_228722_a_(field_228530_v_).func_228728_a_(true));
   }
}
