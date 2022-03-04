package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDorwinionElf extends LOTREntityElf {
   public LOTREntityDorwinionElf(World world) {
      super(world);
   }

   protected LOTRFoods getElfDrinks() {
      return LOTRFoods.DORWINION_DRINK;
   }

   protected EntityAIBase createElfMeleeAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return this.createElfMeleeAttackAI();
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getSindarinName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDorwinionElf));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public LOTRFaction getFaction() {
      return LOTRFaction.DORWINION;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killDorwinionElf;
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected void dropElfItems(boolean flag, int i) {
      super.dropElfItems(flag, i);
      if (flag) {
         int dropChance = 20 - i * 4;
         dropChance = Math.max(dropChance, 1);
         if (this.field_70146_Z.nextInt(dropChance) == 0) {
            ItemStack drink = LOTRFoods.DORWINION_DRINK.getRandomBrewableDrink(this.field_70146_Z);
            LOTRItemMug.setStrengthMeta(drink, 1 + this.field_70146_Z.nextInt(3));
            this.func_70099_a(drink, 0.0F);
         }
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.DORWINION_HOUSE, 1, 1 + i);
      }

   }

   public boolean canElfSpawnHere() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      return j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == biome.field_76752_A;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenDorwinion) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "dorwinion/elf/friendly" : "dorwinion/elf/hostile";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.DORWINION_ELF.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.DORWINION_ELF;
   }
}
