package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRivendellLord extends LOTREntityRivendellWarrior implements LOTRUnitTradeable {
   public LOTREntityRivendellLord(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.RIVENDELL;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordRivendell));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsRivendell));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsRivendell));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyRivendell));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.RIVENDELL_LORD;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.HIGH_ELF_RIVENDELL;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 300.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeRivendellLord);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "rivendell/lord/friendly" : "rivendell/lord/neutral";
      } else {
         return "rivendell/warrior/hostile";
      }
   }
}
