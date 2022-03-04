package lotr.common.block;

import java.util.function.Supplier;
import lotr.common.event.CompostingHelper;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.IItemProvider;

public class LOTRCropBlock extends CropsBlock {
   private final Supplier seedsItemSup;

   public LOTRCropBlock(Properties properties, Supplier sup) {
      super(properties);
      this.seedsItemSup = sup;
      CompostingHelper.prepareCompostable(this, 0.65F);
   }

   public LOTRCropBlock(Supplier sup) {
      this(Properties.func_200945_a(Material.field_151585_k).func_200942_a().func_200944_c().func_200943_b(0.0F).func_200947_a(SoundType.field_222472_s), sup);
   }

   protected IItemProvider func_199772_f() {
      return (IItemProvider)this.seedsItemSup.get();
   }
}
