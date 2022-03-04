package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREntityUmbarian extends LOTREntityNearHaradrimBase {
   public LOTREntityUmbarian(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   protected LOTRFoods getHaradrimFoods() {
      return LOTRFoods.SOUTHRON;
   }

   protected LOTRFoods getHaradrimDrinks() {
      return LOTRFoods.SOUTHRON_DRINK;
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getUmbarName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorUmbar));
      return horse;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerNearHarad));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   public String getNPCFormattedName(String npcName, String entityName) {
      return this.getClass() == LOTREntityUmbarian.class ? StatCollector.func_74837_a("entity.lotr.Umbarian.entityName", new Object[]{npcName}) : super.getNPCFormattedName(npcName, entityName);
   }

   protected void dropHaradrimItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.dropChestContents(LOTRChestContents.NEAR_HARAD_HOUSE, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killNearHaradrim;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/umbar/haradrim/friendly" : "nearHarad/umbar/haradrim/hostile";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.UMBAR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.UMBAR;
   }
}
