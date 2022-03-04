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

public class LOTREntityWoodElfCaptain extends LOTREntityWoodElfWarrior implements LOTRUnitTradeable {
   public LOTREntityWoodElfCaptain(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.WOOD_ELF;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordWoodElven));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsWoodElven));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsWoodElven));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyWoodElven));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.WOOD_ELF_CAPTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.WOOD_ELF;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 250.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeWoodElfCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "woodElf/captain/friendly" : "woodElf/captain/neutral";
      } else {
         return "woodElf/warrior/hostile";
      }
   }
}
