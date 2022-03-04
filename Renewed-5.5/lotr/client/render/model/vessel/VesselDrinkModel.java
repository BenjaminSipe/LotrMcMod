package lotr.client.render.model.vessel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.BakedItemModel;

public class VesselDrinkModel extends BakedItemModel {
   private RenderMaterial liquidMaterial;

   public VesselDrinkModel(ImmutableList quads, TextureAtlasSprite particle, ImmutableMap transforms, ItemOverrideList overrides, boolean untransformed, boolean isSideLit) {
      super(quads, particle, transforms, overrides, untransformed, isSideLit);
   }

   public void setLiquidTexture(RenderMaterial mat) {
      this.liquidMaterial = mat;
   }

   public static TextureAtlasSprite getLiquidIconFor(ItemStack stack) {
      RenderMaterial material = null;
      IBakedModel model = Minecraft.func_71410_x().func_175599_af().func_175037_a().func_178089_a(stack);
      if (model instanceof VesselDrinkModel) {
         material = ((VesselDrinkModel)model).liquidMaterial;
      } else {
         material = new RenderMaterial(AtlasTexture.field_110575_b, MissingTextureSprite.func_195675_b());
      }

      return material.func_229314_c_();
   }
}
