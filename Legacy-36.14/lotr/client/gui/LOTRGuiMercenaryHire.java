package lotr.client.gui;

import lotr.common.entity.npc.LOTRMercenary;
import lotr.common.entity.npc.LOTRMercenaryTradeEntry;
import lotr.common.entity.npc.LOTRUnitTradeEntries;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTRGuiMercenaryHire extends LOTRGuiHireBase {
   public LOTRGuiMercenaryHire(EntityPlayer entityplayer, LOTRMercenary mercenary, World world) {
      super(entityplayer, mercenary, world);
      LOTRUnitTradeEntry e = LOTRMercenaryTradeEntry.createFor(mercenary);
      LOTRUnitTradeEntries trades = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{e});
      this.setTrades(trades);
   }
}
