package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNomad extends LOTREntityNearHaradrimBase implements LOTRBiomeGenNearHarad.ImmuneToHeat {
   protected static int[] nomadTurbanColors = new int[]{15392448, 13550476, 10063441, 8354400, 8343622};

   public LOTREntityNomad(World world) {
      super(world);
      this.addTargetTasks(false);
   }

   protected LOTRFoods getHaradrimFoods() {
      return LOTRFoods.NOMAD;
   }

   protected LOTRFoods getHaradrimDrinks() {
      return LOTRFoods.NOMAD_DRINK;
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getNomadName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      if (this.field_70146_Z.nextInt(4) == 0) {
         ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
         int robeColor = nomadTurbanColors[this.field_70146_Z.nextInt(nomadTurbanColors.length)];
         LOTRItemHaradRobes.setRobesColor(turban, robeColor);
         this.func_70062_b(4, turban);
      } else {
         this.func_70062_b(4, (ItemStack)null);
      }

      return data;
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityCamel camel = new LOTREntityCamel(this.field_70170_p);
      camel.setNomadChestAndCarpet();
      return camel;
   }

   protected void dropHaradrimItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(5) == 0) {
         this.dropChestContents(LOTRChestContents.NOMAD_TENT, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killNearHaradrim;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "nearHarad/nomad/nomad/friendly" : "nearHarad/nomad/nomad/hostile";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.NOMAD.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.NOMAD;
   }
}
