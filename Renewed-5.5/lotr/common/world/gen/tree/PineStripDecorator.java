package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.IntPredicate;
import lotr.common.LOTRLog;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.common.ToolType;

public class PineStripDecorator extends TreeDecorator {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((deco) -> {
         return deco.prob;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("strip_begin_height_fraction").forGetter((deco) -> {
         return deco.stripBeginHeightFraction;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("strip_complete_height_fraction").forGetter((deco) -> {
         return deco.stripCompleteHeightFraction;
      })).apply(instance, PineStripDecorator::new);
   });
   private final float prob;
   private final float stripBeginHeightFraction;
   private final float stripCompleteHeightFraction;

   public PineStripDecorator(float f, float s0, float s1) {
      this.prob = f;
      this.stripBeginHeightFraction = s0;
      this.stripCompleteHeightFraction = s1;
   }

   protected TreeDecoratorType func_230380_a_() {
      return LOTRTreeDecorators.PINE_STRIP;
   }

   public void func_225576_a_(ISeedReader world, Random rand, List trunk, List leaves, Set decoSet, MutableBoundingBox bb) {
      if (rand.nextFloat() < this.prob) {
         int trunkBase = ((BlockPos)trunk.get(0)).func_177956_o();
         int trunkHeight = trunk.size();
         int stripBeginY = Math.round((float)trunkHeight * this.stripBeginHeightFraction);
         int stripCompleteY = Math.round((float)trunkHeight * this.stripCompleteHeightFraction);
         IntPredicate stripTest = (y) -> {
            int yRel = y - trunkBase;
            if (yRel < stripBeginY) {
               return false;
            } else if (yRel >= stripCompleteY) {
               return true;
            } else {
               return rand.nextFloat() < (float)(yRel - stripBeginY + 1) / (float)(stripCompleteY - stripBeginY + 1);
            }
         };
         this.stripWood(world, trunk, stripTest, bb);
         this.stripWood(world, decoSet, stripTest, bb);
      }

   }

   private void stripWood(ISeedReader world, Collection posCollection, IntPredicate stripTest, MutableBoundingBox bb) {
      Set temp = new HashSet();
      Iterator var6 = posCollection.iterator();

      while(var6.hasNext()) {
         BlockPos pos = (BlockPos)var6.next();
         if (stripTest.test(pos.func_177956_o())) {
            BlockState state = world.func_180495_p(pos);

            try {
               BlockState strippedState = state.getToolModifiedState(world.func_201672_e(), pos, (PlayerEntity)null, ItemStack.field_190927_a, ToolType.AXE);
               if (strippedState != null) {
                  this.func_227423_a_(world, pos, strippedState, temp, bb);
               }
            } catch (Exception var10) {
               LOTRLog.error("PineStripDecorator caught an exception while trying to obtain the stripped state of blockstate %s - not altering the blockstate", state.toString());
               var10.printStackTrace();
            }
         }
      }

   }
}
