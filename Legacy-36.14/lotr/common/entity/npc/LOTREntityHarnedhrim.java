package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class LOTREntityHarnedhrim extends LOTREntityNearHaradrimBase {
   public LOTREntityHarnedhrim(World world) {
      super(world);
      this.addTargetTasks(true);
   }

   protected LOTRFoods getHaradrimFoods() {
      return LOTRFoods.HARNEDOR;
   }

   protected LOTRFoods getHaradrimDrinks() {
      return LOTRFoods.HARNEDOR_DRINK;
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getHarnennorName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   public String getNPCFormattedName(String npcName, String entityName) {
      return this.getClass() == LOTREntityHarnedhrim.class ? StatCollector.func_74837_a("entity.lotr.Harnedhrim.entityName", new Object[]{npcName}) : super.getNPCFormattedName(npcName, entityName);
   }

   protected void dropHaradrimItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.dropChestContents(LOTRChestContents.HARNENNOR_HOUSE, 1, 2 + i);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/harnennor/haradrim/friendly" : "nearHarad/harnennor/haradrim/hostile";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.HARNENNOR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.HARNENNOR;
   }
}
