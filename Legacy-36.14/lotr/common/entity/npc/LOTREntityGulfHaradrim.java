package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHaradrim extends LOTREntityNearHaradrimBase {
   public LOTREntityGulfHaradrim(World world) {
      super(world);
      this.addTargetTasks(true);
   }

   protected LOTRFoods getHaradrimFoods() {
      return LOTRFoods.GULF_HARAD;
   }

   protected LOTRFoods getHaradrimDrinks() {
      return LOTRFoods.GULF_HARAD_DRINK;
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getGulfHaradName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   protected void dropHaradrimItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.dropChestContents(LOTRChestContents.GULF_HOUSE, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killNearHaradrim;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/gulf/haradrim/friendly" : "nearHarad/gulf/haradrim/hostile";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.GULF_HARAD.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.GULF_HARAD;
   }
}
