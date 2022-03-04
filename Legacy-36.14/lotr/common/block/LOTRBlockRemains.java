package lotr.common.block;

import java.util.ArrayList;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockRemains extends Block {
   public LOTRBlockRemains() {
      super(LOTRMaterialRemains.remains);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(3.0F);
      this.func_149672_a(Block.field_149767_g);
   }

   public ArrayList getDrops(World world, int i, int j, int k, int metadata, int fortune) {
      ArrayList drops = new ArrayList();
      int dropCount = MathHelper.func_76136_a(world.field_73012_v, 1, 3) + world.field_73012_v.nextInt(1 + fortune * 2);
      if (world.field_73012_v.nextBoolean()) {
         ++dropCount;
      }

      IInventory dropInv = new InventoryBasic("remains", true, dropCount * 5);
      LOTRChestContents.fillInventory(dropInv, world.field_73012_v, LOTRChestContents.MARSH_REMAINS, dropCount);

      for(int l = 0; l < dropInv.func_70302_i_(); ++l) {
         ItemStack drop = dropInv.func_70301_a(l);
         if (drop != null) {
            drops.add(drop);
         }
      }

      return drops;
   }

   public void func_149636_a(World world, EntityPlayer entityplayer, int i, int j, int k, int l) {
      super.func_149636_a(world, entityplayer, i, j, k, l);
      if (!world.field_72995_K) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.mineRemains);
      }

   }
}
