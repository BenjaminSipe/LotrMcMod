package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;

public class LOTRSaplingBlock extends SaplingBlock {
   public LOTRSaplingBlock(Tree tree) {
      super(tree, Properties.func_200945_a(Material.field_151585_k).func_200942_a().func_200944_c().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
      CompostingHelper.prepareCompostable(this, 0.3F);
   }
}
