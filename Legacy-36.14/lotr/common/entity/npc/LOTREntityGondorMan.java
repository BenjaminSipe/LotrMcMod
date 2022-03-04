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
import lotr.common.world.biome.LOTRBiomeGenGondor;
import lotr.common.world.structure.LOTRChestContents;
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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityGondorMan extends LOTREntityMan {
   private static ItemStack[] weapons;

   public LOTREntityGondorMan(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, this.createGondorAttackAI());
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIEat(this, LOTRFoods.GONDOR, 8000));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIDrink(this, LOTRFoods.GONDOR_DRINK, 8000));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.addTargetTasks(false);
   }

   protected EntityAIBase createGondorAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.3D, false);
   }

   public LOTRNPCMount createMountToRide() {
      LOTREntityHorse horse = (LOTREntityHorse)super.createMountToRide();
      horse.setMountArmor(new ItemStack(LOTRMod.horseArmorGondor));
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
      return LOTRFaction.GONDOR;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public String getNPCFormattedName(String npcName, String entityName) {
      return this.getClass() == LOTREntityGondorMan.class ? StatCollector.func_74837_a("entity.lotr.GondorMan.entityName", new Object[]{npcName}) : super.getNPCFormattedName(npcName, entityName);
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

      this.dropGondorItems(flag, i);
   }

   protected void dropGondorItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.GONDOR_HOUSE, 1, 2 + i);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killGondorian;
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
         if (j > 62 && this.field_70170_p.func_147439_a(i, j - 1, k) == this.field_70170_p.func_72807_a(i, k).field_76752_A) {
            return true;
         }
      }

      return false;
   }

   public float func_70783_a(int i, int j, int k) {
      float f = 0.0F;
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenGondor) {
         f += 20.0F;
      }

      return f;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isDrunkard()) {
         return "gondor/drunkard/neutral";
      } else {
         return this.isFriendlyAndAligned(entityplayer) ? "gondor/man/friendly" : "gondor/man/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.GONDOR.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.GONDOR;
   }

   static {
      weapons = new ItemStack[]{new ItemStack(LOTRMod.daggerGondor), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(Items.field_151036_c), new ItemStack(LOTRMod.axeBronze), new ItemStack(Items.field_151049_t)};
   }
}
