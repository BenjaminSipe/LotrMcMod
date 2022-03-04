package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityDunedain extends LOTREntityMan {
   private static ItemStack[] weapons;

   public LOTREntityDunedain(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, this.createDunedainAttackAI());
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIEat(this, this.getDunedainFoods(), 8000));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIDrink(this, this.getDunedainDrinks(), 8000));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.addTargetTasks(true);
   }

   protected EntityAIBase createDunedainAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   protected LOTRFoods getDunedainFoods() {
      return LOTRFoods.RANGER;
   }

   protected LOTRFoods getDunedainDrinks() {
      return LOTRFoods.RANGER_DRINK;
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorIron));
      return horse;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(this.field_70146_Z.nextBoolean());
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getGondorName(this.field_70146_Z, this.familyInfo.isMale()));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weapons.length);
      this.npcItemsInv.setMeleeWeapon(weapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem((ItemStack)null);
      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.RANGER_NORTH;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public String getNPCFormattedName(String npcName, String entityName) {
      return this.getClass() == LOTREntityDunedain.class ? StatCollector.func_74837_a("entity.lotr.Dunedain.entityName", new Object[]{npcName}) : super.getNPCFormattedName(npcName, entityName);
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

      this.dropDunedainItems(flag, i);
   }

   protected void dropDunedainItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.RANGER_HOUSE, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killDunedain;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         if (this.liftSpawnRestrictions) {
            return true;
         }

         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         Block block = this.field_70170_p.func_147439_a(i, j - 1, k);
         BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
         if (j > 62 && (block == biome.field_76752_A || block == Blocks.field_150349_c || block == Blocks.field_150354_m)) {
            return true;
         }
      }

      return false;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      this.field_70170_p.func_72807_a(i, k);
      f += 20.0F;
      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isDrunkard()) {
         return "rangerNorth/drunkard/neutral";
      } else {
         return this.isFriendlyAndAligned(entityplayer) ? "rangerNorth/man/friendly" : "rangerNorth/man/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return this.field_70146_Z.nextInt(8) == 0 ? LOTRMiniQuestFactory.RANGER_NORTH_ARNOR_RELIC.createQuest(this) : LOTRMiniQuestFactory.RANGER_NORTH.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.RANGER_NORTH;
   }

   static {
      weapons = new ItemStack[]{new ItemStack(LOTRMod.daggerBarrow), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(Items.field_151036_c), new ItemStack(LOTRMod.axeBronze), new ItemStack(Items.field_151049_t)};
   }
}
