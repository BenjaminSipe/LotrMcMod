package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlueDwarf extends LOTREntityDwarf {
   public LOTREntityBlueDwarf(World world) {
      super(world);
      this.familyInfo.marriageEntityClass = LOTREntityBlueDwarf.class;
      this.familyInfo.marriageAchievement = LOTRAchievement.marryBlueDwarf;
   }

   protected LOTRFoods getDwarfFoods() {
      return LOTRFoods.BLUE_DWARF;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlueDwarven));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.BLUE_MOUNTAINS;
   }

   protected Item getDwarfSteelDrop() {
      return LOTRMod.blueDwarfSteel;
   }

   protected LOTRChestContents getLarderDrops() {
      return LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
   }

   protected LOTRChestContents getGenericDrops() {
      return LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killBlueDwarf;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "blueDwarf/dwarf/hired";
         } else {
            return this.func_70631_g_() ? "blueDwarf/child/friendly" : "blueDwarf/dwarf/friendly";
         }
      } else {
         return this.func_70631_g_() ? "blueDwarf/child/hostile" : "blueDwarf/dwarf/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.BLUE_MOUNTAINS.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.BLUE_MOUNTAINS;
   }
}
