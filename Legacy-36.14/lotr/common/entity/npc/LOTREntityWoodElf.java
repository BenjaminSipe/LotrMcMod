package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetWoodElf;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.biome.LOTRBiomeGenWoodlandRealm;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityWoodElf extends LOTREntityElf {
   public LOTREntityWoodElf(World world) {
      super(world);
      this.field_70714_bg.func_75776_a(2, this.rangedAttackAI);
      this.addTargetTasks(true, LOTREntityAINearestAttackableTargetWoodElf.class);
   }

   protected LOTRFoods getElfDrinks() {
      return LOTRFoods.WOOD_ELF_DRINK;
   }

   protected EntityAIBase createElfMeleeAttackAI() {
      return this.createElfRangedAttackAI();
   }

   protected EntityAIBase createElfRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.25D, 30, 50, 16.0F);
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getSindarinName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
      this.npcItemsInv.setMeleeWeapon(this.npcItemsInv.getRangedWeapon());
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.WOOD_ELF;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killWoodElf;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected void dropElfItems(boolean flag, int i) {
      super.dropElfItems(flag, i);
      if (flag) {
         int dropChance = 20 - i * 4;
         dropChance = Math.max(dropChance, 1);
         if (this.field_70146_Z.nextInt(dropChance) == 0) {
            ItemStack elfDrink = new ItemStack(LOTRMod.mugRedWine);
            elfDrink.func_77964_b(1 + this.field_70146_Z.nextInt(3));
            LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(this.field_70146_Z), true);
            this.func_70099_a(elfDrink, 0.0F);
         }
      }

      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.WOOD_ELF_HOUSE, 1, 1 + i);
      }

   }

   public boolean canElfSpawnHere() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      return j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == Blocks.field_150349_c;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenWoodlandRealm) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "woodElf/elf/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= getWoodlandTrustLevel() ? "woodElf/elf/friendly" : "woodElf/elf/neutral";
         }
      } else {
         return "woodElf/elf/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.WOOD_ELF.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.WOOD_ELF;
   }

   public static float getWoodlandTrustLevel() {
      return LOTRFaction.WOOD_ELF.getFirstRank().alignment;
   }
}
