package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGundabadOrc extends LOTREntityOrc {
   private static ItemStack[] weapons;
   private static ItemStack[] spears;
   private static ItemStack[] helmets;
   private static ItemStack[] bodies;
   private static ItemStack[] legs;
   private static ItemStack[] boots;

   public LOTREntityGundabadOrc(World world) {
      super(world);
   }

   public EntityAIBase createOrcAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weapons.length);
      this.npcItemsInv.setMeleeWeapon(weapons[i].func_77946_l());
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
         i = this.field_70146_Z.nextInt(spears.length);
         this.npcItemsInv.setMeleeWeapon(spears[i].func_77946_l());
      }

      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      i = this.field_70146_Z.nextInt(boots.length);
      this.func_70062_b(1, boots[i].func_77946_l());
      i = this.field_70146_Z.nextInt(legs.length);
      this.func_70062_b(2, legs[i].func_77946_l());
      i = this.field_70146_Z.nextInt(bodies.length);
      this.func_70062_b(3, bodies[i].func_77946_l());
      if (this.field_70146_Z.nextInt(3) != 0) {
         i = this.field_70146_Z.nextInt(helmets.length);
         this.func_70062_b(4, helmets[i].func_77946_l());
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.GUNDABAD;
   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killGundabadOrc;
   }

   protected void dropOrcItems(boolean flag, int i) {
      if (this.field_70146_Z.nextInt(6) == 0) {
         this.dropChestContents(LOTRChestContents.GUNDABAD_TENT, 1, 2 + i);
      }

      if (this.field_70146_Z.nextInt(4000) == 0) {
         ItemStack dirt = new ItemStack(Blocks.field_150346_d);
         dirt.func_151001_c("Such Wealth");
         this.func_70099_a(dirt, 0.0F);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "gundabad/orc/hired";
         } else {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F ? "gundabad/orc/friendly" : "gundabad/orc/neutral";
         }
      } else {
         return "gundabad/orc/hostile";
      }
   }

   protected String getOrcSkirmishSpeech() {
      return "gundabad/orc/skirmish";
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.GUNDABAD.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.GUNDABAD;
   }

   static {
      weapons = new ItemStack[]{new ItemStack(Items.field_151052_q), new ItemStack(Items.field_151049_t), new ItemStack(Items.field_151050_s), new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151036_c), new ItemStack(Items.field_151035_b), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerIronPoisoned), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.pickaxeBronze), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerBronzePoisoned), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.swordAngmar), new ItemStack(LOTRMod.axeAngmar), new ItemStack(LOTRMod.pickaxeAngmar), new ItemStack(LOTRMod.daggerAngmar), new ItemStack(LOTRMod.daggerAngmarPoisoned), new ItemStack(LOTRMod.battleaxeAngmar), new ItemStack(LOTRMod.hammerAngmar), new ItemStack(LOTRMod.scimitarOrc), new ItemStack(LOTRMod.axeOrc), new ItemStack(LOTRMod.pickaxeOrc), new ItemStack(LOTRMod.daggerOrc), new ItemStack(LOTRMod.daggerOrcPoisoned), new ItemStack(LOTRMod.battleaxeOrc), new ItemStack(LOTRMod.hammerOrc), new ItemStack(LOTRMod.polearmOrc), new ItemStack(LOTRMod.swordDolGuldur), new ItemStack(LOTRMod.axeDolGuldur), new ItemStack(LOTRMod.pickaxeDolGuldur), new ItemStack(LOTRMod.daggerDolGuldur), new ItemStack(LOTRMod.daggerDolGuldurPoisoned), new ItemStack(LOTRMod.battleaxeDolGuldur), new ItemStack(LOTRMod.hammerDolGuldur), new ItemStack(LOTRMod.swordGundabadUruk), new ItemStack(LOTRMod.battleaxeGundabadUruk), new ItemStack(LOTRMod.hammerGundabadUruk), new ItemStack(LOTRMod.pikeGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUrukPoisoned), new ItemStack(LOTRMod.polearmAngmar), new ItemStack(LOTRMod.pikeDolGuldur)};
      spears = new ItemStack[]{new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.spearAngmar), new ItemStack(LOTRMod.spearOrc), new ItemStack(LOTRMod.spearDolGuldur), new ItemStack(LOTRMod.spearGundabadUruk)};
      helmets = new ItemStack[]{new ItemStack(Items.field_151024_Q), new ItemStack(LOTRMod.helmetBronze), new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.helmetBone), new ItemStack(LOTRMod.helmetAngmar), new ItemStack(LOTRMod.helmetOrc), new ItemStack(LOTRMod.helmetDolGuldur)};
      bodies = new ItemStack[]{new ItemStack(Items.field_151027_R), new ItemStack(LOTRMod.bodyBronze), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyOrc), new ItemStack(LOTRMod.bodyDolGuldur), new ItemStack(LOTRMod.bodyGundabadUruk)};
      legs = new ItemStack[]{new ItemStack(Items.field_151026_S), new ItemStack(LOTRMod.legsBronze), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsOrc), new ItemStack(LOTRMod.legsDolGuldur), new ItemStack(LOTRMod.legsGundabadUruk)};
      boots = new ItemStack[]{new ItemStack(Items.field_151021_T), new ItemStack(LOTRMod.bootsBronze), new ItemStack(LOTRMod.bootsFur), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsOrc), new ItemStack(LOTRMod.bootsDolGuldur), new ItemStack(LOTRMod.bootsGundabadUruk)};
   }
}
