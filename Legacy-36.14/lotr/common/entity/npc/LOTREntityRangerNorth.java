package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityRangerNorth extends LOTREntityRanger {
   public LOTREntityRangerNorth(World world) {
      super(world);
      this.spawnRidingHorse = this.field_70146_Z.nextInt(20) == 0;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(5);
      if (i != 0 && i != 1) {
         if (i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
         } else if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBarrow));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
      }

      int r = this.field_70146_Z.nextInt(2);
      if (r == 0) {
         this.npcItemsInv.setRangedWeapon(new ItemStack(Items.field_151031_f));
      } else if (r == 1) {
         this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rangerBow));
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.RANGER_NORTH;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killRangerNorth;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "rangerNorth/ranger/hired" : "rangerNorth/ranger/friendly";
      } else {
         return "rangerNorth/ranger/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return this.field_70146_Z.nextInt(8) == 0 ? LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.createQuest(this) : LOTRMiniQuestFactory.RANGER_NORTH.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.RANGER_NORTH;
   }
}
