package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRCapes;
import lotr.common.LOTRGreyWandererTracker;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIGandalfSmoke;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestWelcome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityGandalf extends LOTREntityNPC {
   private static final double msgRange = 64.0D;

   public LOTREntityGandalf(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.8D, false));
      this.field_70714_bg.func_75776_a(2, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(3, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(4, new LOTREntityAIGandalfSmoke(this, 3000));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.05F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.05F));
      this.field_70714_bg.func_75776_a(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
      int target = this.addTargetTasks(false);
      this.field_70715_bh.func_75776_a(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityBalrog.class, 0, true));
      this.field_70715_bh.func_75776_a(target + 2, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntitySaruman.class, 0, true));
      this.npcCape = LOTRCapes.GANDALF;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.glamdring));
      this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.gandalfStaffGrey));
      return data;
   }

   public void onArtificalSpawn() {
      LOTRGreyWandererTracker.addNewWanderer(this.func_110124_au());
      this.arriveAt((EntityPlayer)null);
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public ItemStack getHeldItemLeft() {
      ItemStack heldItem = this.func_70694_bm();
      return heldItem != null && heldItem.func_77973_b() == LOTRMod.glamdring ? new ItemStack(LOTRMod.gandalfStaffGrey) : super.getHeldItemLeft();
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UNALIGNED;
   }

   public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
      return false;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      Entity entity = damagesource.func_76346_g();
      if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
         return super.func_70097_a(damagesource, f);
      } else {
         f = 0.0F;
         return super.func_70097_a(damagesource, f);
      }
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && !LOTRGreyWandererTracker.isWandererActive(this.func_110124_au()) && this.func_70638_az() == null) {
         this.depart();
      }

   }

   private void doGandalfFX() {
      this.func_85030_a("random.pop", 2.0F, 0.5F + this.field_70146_Z.nextFloat() * 0.5F);
      this.field_70170_p.func_72960_a(this, (byte)16);
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 16) {
         for(int i = 0; i < 20; ++i) {
            double d0 = this.field_70165_t + (double)(MathHelper.func_151240_a(this.field_70146_Z, -1.0F, 1.0F) * this.field_70130_N);
            double d1 = this.field_70163_u + (double)(MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 1.0F) * this.field_70131_O);
            double d2 = this.field_70161_v + (double)(MathHelper.func_151240_a(this.field_70146_Z, -1.0F, 1.0F) * this.field_70130_N);
            double d3 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d4 = 0.05D + this.field_70146_Z.nextGaussian() * 0.02D;
            double d5 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_72869_a("explode", d0, d1, d2, d3, d4, d5);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public void arriveAt(EntityPlayer entityplayer) {
      List msgPlayers = new ArrayList();
      if (entityplayer != null) {
         msgPlayers.add(entityplayer);
      }

      List worldPlayers = this.field_70170_p.field_73010_i;
      Iterator var4 = worldPlayers.iterator();

      while(var4.hasNext()) {
         Object obj = var4.next();
         EntityPlayer player = (EntityPlayer)obj;
         if (!msgPlayers.contains(player)) {
            double d = 64.0D;
            double dSq = d * d;
            if (this.func_70068_e(player) < dSq) {
               msgPlayers.add(player);
            }
         }
      }

      var4 = msgPlayers.iterator();

      while(var4.hasNext()) {
         EntityPlayer player = (EntityPlayer)var4.next();
         LOTRSpeech.sendSpeechBankWithChatMsg(player, this, "char/gandalf/arrive");
      }

      this.doGandalfFX();
   }

   private void depart() {
      List msgPlayers = new ArrayList();
      List worldPlayers = this.field_70170_p.field_73010_i;
      Iterator var3 = worldPlayers.iterator();

      while(var3.hasNext()) {
         Object obj = var3.next();
         EntityPlayer player = (EntityPlayer)obj;
         if (!msgPlayers.contains(player)) {
            double d = 64.0D;
            double dSq = d * d;
            if (this.func_70068_e(player) < dSq) {
               msgPlayers.add(player);
            }
         }
      }

      var3 = msgPlayers.iterator();

      while(var3.hasNext()) {
         EntityPlayer player = (EntityPlayer)var3.next();
         LOTRSpeech.sendSpeechBankWithChatMsg(player, this, "char/gandalf/depart");
      }

      this.doGandalfFX();
      this.func_70106_y();
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.isFriendlyAndAligned(entityplayer) ? "char/gandalf/friendly" : "char/gandalf/hostile";
   }

   public boolean speakTo(EntityPlayer entityplayer) {
      if (LOTRGreyWandererTracker.isWandererActive(this.func_110124_au())) {
         if (this.questInfo.getOfferFor(entityplayer) != null) {
            return super.speakTo(entityplayer);
         }

         if (this.addMQOfferFor(entityplayer)) {
            LOTRGreyWandererTracker.setWandererActive(this.func_110124_au());
            String speechBank = "char/gandalf/welcome";
            this.sendSpeechBank(entityplayer, speechBank);
            return true;
         }
      }

      return super.speakTo(entityplayer);
   }

   private boolean addMQOfferFor(EntityPlayer entityplayer) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      if (pd.getMiniQuestsForEntity(this, true).isEmpty()) {
         LOTRMiniQuest quest = new LOTRMiniQuestWelcome((LOTRPlayerData)null, this);
         if (quest.canPlayerAccept(entityplayer)) {
            this.questInfo.setPlayerSpecificOffer(entityplayer, quest);
            return true;
         }
      }

      return false;
   }

   public int getMiniquestColor() {
      return 10526880;
   }
}
