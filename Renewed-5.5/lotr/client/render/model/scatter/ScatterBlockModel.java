package lotr.client.render.model.scatter;

import com.google.common.math.LongMath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import lotr.client.render.model.BlockModelQuadsHolder;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

public class ScatterBlockModel extends SimpleBakedModel {
   private final List scatterVariantModels;

   public ScatterBlockModel(List variantModels, boolean ambOcc, boolean sideLight, boolean g3d, TextureAtlasSprite partTex, ItemCameraTransforms transform, ItemOverrideList overrides) {
      super(new ArrayList(), new HashMap(), ambOcc, sideLight, g3d, partTex, transform, overrides);
      this.scatterVariantModels = variantModels;
      if (this.scatterVariantModels.isEmpty()) {
         throw new IllegalArgumentException("Model variant list cannot be empty!");
      }
   }

   public List func_200117_a(BlockState state, Direction side, Random rand) {
      return this.getQuads(state, side, rand, ScatterPositionContext.newEmptyContext());
   }

   public List getQuads(BlockState state, Direction side, Random rand, IModelData extraData) {
      if (extraData instanceof ScatterPositionContext) {
         ScatterPositionContext posContext = (ScatterPositionContext)extraData;
         long hash = posContext.getPositionHash();
         int index = LongMath.mod(hash, this.scatterVariantModels.size());
         return ((BlockModelQuadsHolder)this.scatterVariantModels.get(index)).getQuads(side);
      } else if (extraData instanceof EmptyModelData) {
         return this.func_200117_a(state, side, rand);
      } else {
         throw new IllegalArgumentException("ScatterBlockModel can only take ScatterPositionContext model data or EmptyModelData, but " + extraData.getClass().getName() + " was supplied");
      }
   }

   public IModelData getModelData(IBlockDisplayReader world, BlockPos pos, BlockState state, IModelData tileData) {
      return ScatterPositionContext.forPosition(world, pos, state);
   }
}
