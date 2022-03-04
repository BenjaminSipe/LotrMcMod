package lotr.common.quest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;

public class LOTRMiniQuestPickpocket extends LOTRMiniQuestCollectBase {
   public LOTRFaction pickpocketFaction;
   private Set pickpocketedEntityIDs = new HashSet();

   public LOTRMiniQuestPickpocket(LOTRPlayerData pd) {
      super(pd);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.func_74778_a("PickpocketFaction", this.pickpocketFaction.codeName());
      NBTTagList ids = new NBTTagList();
      Iterator var3 = this.pickpocketedEntityIDs.iterator();

      while(var3.hasNext()) {
         UUID id = (UUID)var3.next();
         ids.func_74742_a(new NBTTagString(id.toString()));
      }

      nbt.func_74782_a("PickpocketedIDs", ids);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.pickpocketFaction = LOTRFaction.forName(nbt.func_74779_i("PickpocketFaction"));
      this.pickpocketedEntityIDs.clear();
      NBTTagList ids = nbt.func_150295_c("PickpocketedIDs", 8);

      for(int i = 0; i < ids.func_74745_c(); ++i) {
         UUID id = UUID.fromString(ids.func_150307_f(i));
         if (id != null) {
            this.pickpocketedEntityIDs.add(id);
         }
      }

   }

   public boolean isValidQuest() {
      return super.isValidQuest() && this.pickpocketFaction != null;
   }

   public String getQuestObjective() {
      return StatCollector.func_74837_a("lotr.miniquest.pickpocket", new Object[]{this.collectTarget, this.pickpocketFaction.factionEntityName()});
   }

   public String getObjectiveInSpeech() {
      return this.pickpocketFaction.factionEntityName();
   }

   public String getProgressedObjectiveInSpeech() {
      return this.collectTarget - this.amountGiven + " " + this.pickpocketFaction.factionEntityName();
   }

   public String getQuestProgress() {
      return StatCollector.func_74837_a("lotr.miniquest.pickpocket.progress", new Object[]{this.amountGiven, this.collectTarget});
   }

   public ItemStack getQuestIcon() {
      return createPickpocketIcon();
   }

   public static ItemStack createPickpocketIcon() {
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      LOTRItemLeatherHat.setHatColor(hat, 0);
      LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
      return hat;
   }

   protected boolean isQuestItem(ItemStack itemstack) {
      return IPickpocketable.Helper.isPickpocketed(itemstack) && this.entityUUID.equals(IPickpocketable.Helper.getWanterID(itemstack));
   }

   public boolean onInteractOther(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
      if (entityplayer.func_70093_af() && entityplayer.func_70694_bm() == null && npc.getFaction() == this.pickpocketFaction && npc instanceof IPickpocketable) {
         IPickpocketable ppable = (IPickpocketable)npc;
         UUID id = npc.getPersistentID();
         if (ppable.canPickpocket() && !this.pickpocketedEntityIDs.contains(id)) {
            if (npc.func_70638_az() != null) {
               entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.pickpocket.inCombat", new Object[0]));
               return true;
            }

            if (this.isEntityWatching(npc, entityplayer)) {
               entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.pickpocket.watched", new Object[0]));
               return true;
            }

            Random rand = npc.func_70681_au();
            boolean success = rand.nextInt(3) == 0;
            boolean noticed = success ? rand.nextInt(3) == 0 : rand.nextInt(4) == 0;
            boolean anyoneNoticed = noticed;
            if (success) {
               ItemStack picked = ppable.createPickpocketItem();
               IPickpocketable.Helper.setPickpocketData(picked, npc.getNPCName(), this.entityNameFull, this.entityUUID);
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, picked);
               entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.pickpocket.success", new Object[]{picked.field_77994_a, picked.func_82833_r(), npc.getNPCName()}));
               npc.func_85030_a("lotr:event.trade", 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.1F);
               npc.func_85030_a("mob.horse.leather", 0.5F, 1.0F);
               this.spawnPickingFX("pickpocket", 1.0D, npc);
               this.pickpocketedEntityIDs.add(id);
               this.updateQuest();
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.pickpocket);
            } else {
               entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.pickpocket.missed", new Object[]{npc.getNPCName()}));
               npc.func_85030_a(Blocks.field_150325_L.field_149762_H.func_150495_a(), 0.5F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
               this.spawnPickingFX("pickpocketFail", 0.4D, npc);
            }

            if (noticed) {
               entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.pickpocket.noticed", new Object[]{npc.getNPCName()}));
               npc.setAttackTarget(entityplayer, true);
               npc.func_70604_c(entityplayer);
               this.spawnAngryFX(npc);
            }

            if (!noticed || rand.nextFloat() < 0.5F) {
               double civilianRange = 8.0D;
               double guardRange = 16.0D;
               double fullAlertRange = 4.0D;
               List nearbyFriends = npc.field_70170_p.func_82733_a(LOTREntityNPC.class, npc.field_70121_D.func_72314_b(16.0D, 16.0D, 16.0D), new IEntitySelector() {
                  public boolean func_82704_a(Entity entity) {
                     LOTREntityNPC otherNPC = (LOTREntityNPC)entity;
                     if (otherNPC.func_70089_S() && otherNPC.getFaction().isGoodRelation(npc.getFaction())) {
                        return otherNPC.hiredNPCInfo.getHiringPlayer() != entityplayer;
                     } else {
                        return false;
                     }
                  }
               });
               Iterator var16 = nearbyFriends.iterator();

               while(var16.hasNext()) {
                  Object o = var16.next();
                  LOTREntityNPC otherNPC = (LOTREntityNPC)o;
                  if (otherNPC != npc) {
                     boolean civilian = otherNPC.isCivilianNPC();
                     double maxRange = civilian ? 8.0D : 16.0D;
                     double dist = (double)otherNPC.func_70032_d(npc);
                     if (dist <= maxRange && otherNPC.func_70638_az() == null && this.isEntityWatching(otherNPC, entityplayer)) {
                        float distFactor = 1.0F - (float)((dist - 4.0D) / (maxRange - 4.0D));
                        float chance = 0.5F + distFactor * 0.5F;
                        if (civilian) {
                           chance *= 0.25F;
                        }

                        boolean otherNoticed = rand.nextFloat() < chance;
                        if (otherNoticed) {
                           entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.pickpocket.otherNoticed", new Object[]{otherNPC.getEntityClassName()}));
                           otherNPC.setAttackTarget(entityplayer, true);
                           otherNPC.func_70604_c(entityplayer);
                           this.spawnAngryFX(otherNPC);
                           anyoneNoticed = true;
                        }
                     }
                  }
               }
            }

            if (anyoneNoticed) {
               LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.PICKPOCKET_PENALTY, npc.getFaction(), npc);
            }

            return true;
         }
      }

      return false;
   }

   private boolean isEntityWatching(EntityLiving watcher, EntityLivingBase target) {
      Vec3 look = watcher.func_70040_Z();
      Vec3 watcherEyes = Vec3.func_72443_a(watcher.field_70165_t, watcher.field_70121_D.field_72338_b + (double)watcher.func_70047_e(), watcher.field_70161_v);
      Vec3 targetEyes = Vec3.func_72443_a(target.field_70165_t, target.field_70121_D.field_72338_b + (double)target.func_70047_e(), target.field_70161_v);
      Vec3 disp = Vec3.func_72443_a(targetEyes.field_72450_a - watcherEyes.field_72450_a, targetEyes.field_72448_b - watcherEyes.field_72448_b, targetEyes.field_72449_c - watcherEyes.field_72449_c);
      double dot = disp.func_72432_b().func_72430_b(look.func_72432_b());
      float fov = (float)Math.toRadians(130.0D);
      float fovCos = MathHelper.func_76134_b(fov / 2.0F);
      return dot >= (double)fovCos ? watcher.func_70635_at().func_75522_a(target) : false;
   }

   private void spawnPickingFX(String particle, double upSpeed, EntityLivingBase npc) {
      Random rand = npc.func_70681_au();
      int particles = 3 + rand.nextInt(8);

      for(int p = 0; p < particles; ++p) {
         double x = npc.field_70165_t;
         double y = npc.field_70121_D.field_72338_b + (double)(npc.field_70131_O * 0.5F);
         double z = npc.field_70161_v;
         float w = npc.field_70130_N * 0.1F;
         float ang = rand.nextFloat() * 3.1415927F * 2.0F;
         x += (double)(MathHelper.func_76134_b(ang) * w);
         z += (double)(MathHelper.func_76126_a(ang) * w);
         double hSpeed = MathHelper.func_82716_a(rand, 0.05D, 0.08D);
         double vx = (double)MathHelper.func_76134_b(ang) * hSpeed;
         double vz = (double)MathHelper.func_76126_a(ang) * hSpeed;
         double vy = MathHelper.func_82716_a(rand, 0.1D, 0.25D) * upSpeed;
         LOTRMod.proxy.spawnParticle(particle, x, y, z, vx, vy, vz);
      }

   }

   private void spawnAngryFX(EntityLivingBase npc) {
      LOTRMod.proxy.spawnParticle("angry", npc.field_70165_t, npc.field_70121_D.field_72338_b + (double)(npc.field_70131_O * 2.0F), npc.field_70161_v, npc.field_70159_w, Math.max(0.0D, npc.field_70181_x), npc.field_70179_y);
   }

   public int getCoinBonus() {
      return Math.round(this.getAlignmentBonus() * 5.0F);
   }

   public static class QFPickpocket extends LOTRMiniQuest.QuestFactoryBase {
      private LOTRFaction pickpocketFaction;
      private int minTarget;
      private int maxTarget;

      public QFPickpocket(String name) {
         super(name);
      }

      public LOTRMiniQuestPickpocket.QFPickpocket setPickpocketFaction(LOTRFaction f, int min, int max) {
         this.pickpocketFaction = f;
         this.minTarget = min;
         this.maxTarget = max;
         return this;
      }

      public Class getQuestClass() {
         return LOTRMiniQuestPickpocket.class;
      }

      public LOTRMiniQuestPickpocket createQuest(LOTREntityNPC npc, Random rand) {
         LOTRMiniQuestPickpocket quest = (LOTRMiniQuestPickpocket)super.createQuest(npc, rand);
         quest.pickpocketFaction = this.pickpocketFaction;
         quest.collectTarget = MathHelper.func_76136_a(rand, this.minTarget, this.maxTarget);
         return quest;
      }
   }
}
