package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.IPickpocketable;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenBreeland;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityBreeHobbit extends LOTREntityHobbit implements IPickpocketable {
   public LOTREntityBreeHobbit(World world) {
      super(world);
      this.familyInfo.marriageEntityClass = LOTREntityBreeHobbit.class;
   }

   protected LOTRFoods getHobbitFoods() {
      return LOTRFoods.BREE;
   }

   protected LOTRFoods getHobbitDrinks() {
      return LOTRFoods.BREE_DRINK;
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getBreeHobbitName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
      this.familyInfo.setName(LOTRNames.getBreeHobbitChildNameForParent(this.field_70146_Z, this.familyInfo.isMale(), (LOTREntityHobbit)maleParent));
   }

   public void changeNPCNameForMarriage(LOTREntityNPC spouse) {
      super.changeNPCNameForMarriage(spouse);
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.BREE;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killBreeHobbit;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected void dropHobbitItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.BREE_HOUSE, 1, 2 + i);
      }

   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenBreeland) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isDrunkard()) {
         return "bree/hobbit/drunkard/neutral";
      } else if (this.isFriendlyAndAligned(entityplayer)) {
         return this.func_70631_g_() ? "bree/hobbit/child/friendly" : "bree/hobbit/friendly";
      } else {
         return this.func_70631_g_() ? "bree/hobbit/child/hostile" : "bree/hobbit/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.BREE.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.BREE;
   }

   public boolean canPickpocket() {
      return true;
   }

   public ItemStack createPickpocketItem() {
      return LOTRChestContents.BREE_PICKPOCKET.getOneItem(this.field_70146_Z, true);
   }
}
