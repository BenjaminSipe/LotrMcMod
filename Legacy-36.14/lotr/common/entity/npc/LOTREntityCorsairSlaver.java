package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityCorsairSlaver extends LOTREntityCorsair implements LOTRUnitTradeable {
   public LOTREntityCorsairSlaver(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.brandingIron));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.CORSAIR_SLAVER;
   }

   public LOTRInvasions getWarhorn() {
      return null;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireHaradSlave);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/umbar/corsairSlaver/friendly" : "nearHarad/umbar/corsairSlaver/hostile";
   }
}
