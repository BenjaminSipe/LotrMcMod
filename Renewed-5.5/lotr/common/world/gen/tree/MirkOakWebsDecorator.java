package lotr.common.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lotr.common.block.HangingWebBlock;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.world.biome.MirkwoodBiome;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class MirkOakWebsDecorator extends TreeDecorator {
   public static final Codec CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(Codec.BOOL.fieldOf("only_in_mirkwood").orElse(false).forGetter((deco) -> {
         return deco.mirkwoodOnly;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((deco) -> {
         return deco.prob;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("per_block_chance").forGetter((deco) -> {
         return deco.perBlockChance;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("double_web_chance").forGetter((deco) -> {
         return deco.doubleWebChance;
      })).apply(instance, MirkOakWebsDecorator::new);
   });
   private final boolean mirkwoodOnly;
   private final float prob;
   private final float perBlockChance;
   private final float doubleWebChance;

   public MirkOakWebsDecorator(boolean flag, float f, float perBlock, float doubleWeb) {
      this.mirkwoodOnly = flag;
      this.prob = f;
      this.perBlockChance = perBlock;
      this.doubleWebChance = doubleWeb;
   }

   protected TreeDecoratorType func_230380_a_() {
      return LOTRTreeDecorators.MIRK_OAK_WEBS;
   }

   public void func_225576_a_(ISeedReader world, Random rand, List trunk, List leaves, Set decoSet, MutableBoundingBox bb) {
      boolean doDecorate = rand.nextFloat() < this.prob;
      if (doDecorate && this.mirkwoodOnly) {
         BlockPos centralBasePos = new BlockPos((bb.field_78897_a + bb.field_78893_d) / 2, bb.field_78895_b, (bb.field_78896_c + bb.field_78892_f) / 2);
         doDecorate &= LOTRBiomes.getWrapperFor(world.func_226691_t_(centralBasePos), world) instanceof MirkwoodBiome;
      }

      if (doDecorate) {
         Iterator var16 = leaves.iterator();

         while(true) {
            BlockPos webPos;
            do {
               BlockPos leavesPos;
               do {
                  if (!var16.hasNext()) {
                     return;
                  }

                  leavesPos = (BlockPos)var16.next();
               } while(rand.nextFloat() >= this.perBlockChance);

               webPos = leavesPos.func_177977_b();
            } while(!world.func_175623_d(webPos));

            BlockState baseWebState = ((Block)LOTRBlocks.HANGING_WEB.get()).func_176223_P();
            BlockPos belowWebPos = webPos.func_177977_b();
            boolean placeDouble = rand.nextFloat() < this.doubleWebChance && world.func_175623_d(belowWebPos);
            BlockState topWebState;
            if (placeDouble) {
               topWebState = (BlockState)baseWebState.func_206870_a(HangingWebBlock.WEB_TYPE, HangingWebBlock.Type.DOUBLE_TOP);
               BlockState bottomWebState = (BlockState)baseWebState.func_206870_a(HangingWebBlock.WEB_TYPE, HangingWebBlock.Type.DOUBLE_BOTTOM);
               this.func_227423_a_(world, webPos, topWebState, decoSet, bb);
               this.func_227423_a_(world, belowWebPos, bottomWebState, decoSet, bb);
            } else {
               topWebState = (BlockState)baseWebState.func_206870_a(HangingWebBlock.WEB_TYPE, HangingWebBlock.Type.SINGLE);
               this.func_227423_a_(world, webPos, topWebState, decoSet, bb);
            }

            world.func_180495_p(webPos).func_235734_a_(world, webPos, 3);
         }
      }
   }
}
