package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRCapes;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenIthilien;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityRangerIthilien extends LOTREntityRanger {
   public LOTREntityRangerIthilien(World world) {
      super(world);
      this.npcCape = LOTRCapes.RANGER_ITHILIEN;
   }

   protected LOTRFoods getDunedainFoods() {
      return LOTRFoods.GONDOR;
   }

   protected LOTRFoods getDunedainDrinks() {
      return LOTRFoods.GONDOR_DRINK;
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(4);
      if (i != 0 && i != 1 && i != 2) {
         if (i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGondor));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerGondor));
      }

      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.gondorBow));
      this.func_70062_b(1, new ItemStack(LOTRMod.bootsRangerIthilien));
      this.func_70062_b(2, new ItemStack(LOTRMod.legsRangerIthilien));
      this.func_70062_b(3, new ItemStack(LOTRMod.bodyRangerIthilien));
      this.func_70062_b(4, new ItemStack(LOTRMod.helmetRangerIthilien));
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.GONDOR;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = super.func_70783_a(i, j, k);
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenIthilien) {
         f += 20.0F;
      }

      return f;
   }

   protected void dropDunedainItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.GONDOR_HOUSE, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killRangerIthilien;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "gondor/ranger/hired" : "gondor/ranger/friendly";
      } else {
         return "gondor/ranger/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.GONDOR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.GONDOR;
   }
}
