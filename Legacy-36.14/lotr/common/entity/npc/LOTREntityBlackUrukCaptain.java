package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackUrukCaptain extends LOTREntityBlackUruk implements LOTRUnitTradeable {
   public LOTREntityBlackUrukCaptain(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.orcSkullStaff));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsBlackUruk));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsBlackUruk));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyBlackUruk));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.BLACK_URUK_CAPTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.MORDOR_BLACK_URUK;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 400.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeBlackUrukCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "mordor/blackUrukCaptain/friendly" : "mordor/blackUrukCaptain/neutral";
      } else {
         return "mordor/orc/hostile";
      }
   }
}
