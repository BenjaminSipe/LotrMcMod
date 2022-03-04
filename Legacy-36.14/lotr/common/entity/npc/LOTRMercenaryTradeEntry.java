package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRMercenaryTradeEntry extends LOTRUnitTradeEntry {
   private LOTRMercenary theMerc;

   private LOTRMercenaryTradeEntry(LOTRMercenary merc) {
      super(merc.getClass(), merc.getMercBaseCost(), merc.getMercAlignmentRequired());
      this.theMerc = merc;
   }

   public static LOTRMercenaryTradeEntry createFor(LOTRMercenary merc) {
      return new LOTRMercenaryTradeEntry(merc);
   }

   public LOTREntityNPC getOrCreateHiredNPC(World world) {
      return (LOTREntityNPC)this.theMerc;
   }

   public boolean hasRequiredCostAndAlignment(EntityPlayer entityplayer, LOTRHireableBase trader) {
      return ((LOTREntityNPC)this.theMerc).hiredNPCInfo.isActive ? false : super.hasRequiredCostAndAlignment(entityplayer, trader);
   }
}
