package lotr.common.block;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRBlockCraftingTable extends Block {
   public static List allCraftingTables = new ArrayList();
   public final LOTRFaction tableFaction;
   public final int tableGUIID;

   public LOTRBlockCraftingTable(Material material, LOTRFaction faction, int guiID) {
      super(material);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
      this.func_149711_c(2.5F);
      this.tableFaction = faction;
      this.tableGUIID = guiID;
      allCraftingTables.add(this);
   }

   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
      boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.tableFaction) >= 1.0F;
      if (hasRequiredAlignment) {
         if (!world.field_72995_K) {
            entityplayer.openGui(LOTRMod.instance, this.tableGUIID, world, i, j, k);
         }
      } else {
         for(int l = 0; l < 8; ++l) {
            double d = (double)((float)i + world.field_73012_v.nextFloat());
            double d1 = (double)j + 1.0D;
            double d2 = (double)((float)k + world.field_73012_v.nextFloat());
            world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
         }

         if (!world.field_72995_K) {
            LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 1.0F, this.tableFaction);
         }
      }

      return true;
   }
}
