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

public class LOTREntityDorwinionElfCaptain extends LOTREntityDorwinionElfWarrior implements LOTRUnitTradeable {
   public LOTREntityDorwinionElfCaptain(World world) {
      super(world);
      this.addTargetTasks(false);
      this.npcCape = LOTRCapes.DORWINION_ELF_CAPTAIN;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
      this.npcItemsInv.setSpearBackup((ItemStack)null);
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsDorwinionElf));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsDorwinionElf));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyDorwinionElf));
      this.func_70062_b(4, (ItemStack)null);
      return data;
   }

   public float getAlignmentBonus() {
      return 5.0F;
   }

   public LOTRUnitTradeEntries getUnits() {
      return LOTRUnitTradeEntries.DORWINION_ELF_CAPTAIN;
   }

   public LOTRInvasions getWarhorn() {
      return LOTRInvasions.DORWINION_ELF;
   }

   public boolean canTradeWith(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 250.0F && this.isFriendlyAndAligned(entityplayer);
   }

   public void onUnitTrade(EntityPlayer entityplayer) {
      LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeDorwinionElfCaptain);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.canTradeWith(entityplayer) ? "dorwinion/elfCaptain/friendly" : "dorwinion/elfCaptain/neutral";
      } else {
         return "dorwinion/elfWarrior/hostile";
      }
   }
}
