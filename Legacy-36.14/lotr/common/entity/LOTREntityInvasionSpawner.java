package lotr.common.entity;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemConquestHorn;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketInvasionWatch;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTREntityInvasionSpawner extends Entity {
   public float spawnerSpin;
   public float prevSpawnerSpin;
   private int invasionSize;
   public static final int MAX_INVASION_SIZE = 10000;
   private int invasionRemaining;
   private int successiveFailedSpawns = 0;
   private int timeSincePlayerProgress = 0;
   private Map recentPlayerContributors = new HashMap();
   private static double INVASION_FOLLOW_RANGE = 40.0D;
   public boolean isWarhorn = false;
   public boolean spawnsPersistent = true;
   private List bonusFactions = new ArrayList();

   public LOTREntityInvasionSpawner(World world) {
      super(world);
      this.func_70105_a(1.5F, 1.5F);
      this.field_70155_l = 4.0D;
      this.spawnerSpin = this.field_70146_Z.nextFloat() * 360.0F;
   }

   public ItemStack getInvasionItem() {
      return this.getInvasionType().getInvasionIcon();
   }

   public void func_70088_a() {
      this.field_70180_af.func_75682_a(20, (byte)0);
      this.field_70180_af.func_75682_a(21, Short.valueOf((short)0));
      this.field_70180_af.func_75682_a(22, Short.valueOf((short)0));
   }

   public LOTRInvasions getInvasionType() {
      int i = this.field_70180_af.func_75683_a(20);
      LOTRInvasions type = LOTRInvasions.forID(i);
      return type != null ? type : LOTRInvasions.HOBBIT;
   }

   public void setInvasionType(LOTRInvasions type) {
      this.field_70180_af.func_75692_b(20, (byte)type.ordinal());
   }

   private void updateWatchedInvasionValues() {
      if (!this.field_70170_p.field_72995_K) {
         this.field_70180_af.func_75692_b(21, (short)this.invasionSize);
         this.field_70180_af.func_75692_b(22, (short)this.invasionRemaining);
      } else {
         this.invasionSize = this.field_70180_af.func_75693_b(21);
         this.invasionRemaining = this.field_70180_af.func_75693_b(22);
      }

   }

   public int getInvasionSize() {
      return this.invasionSize;
   }

   public float getInvasionHealthStatus() {
      return (float)this.invasionRemaining / (float)this.invasionSize;
   }

   public UUID getInvasionID() {
      return this.func_110124_au();
   }

   public boolean canInvasionSpawnHere() {
      if (LOTRBannerProtection.isProtected(this.field_70170_p, this, LOTRBannerProtection.forInvasionSpawner(this), false)) {
         return false;
      } else if (LOTREntityNPCRespawner.isSpawnBlocked(this, this.getInvasionType().invasionFaction)) {
         return false;
      } else {
         return this.field_70170_p.func_72855_b(this.field_70121_D) && this.field_70170_p.func_72945_a(this, this.field_70121_D).isEmpty() && !this.field_70170_p.func_72953_d(this.field_70121_D);
      }
   }

   private void playHorn() {
      this.field_70170_p.func_72956_a(this, "lotr:item.horn", 4.0F, 0.65F + this.field_70146_Z.nextFloat() * 0.1F);
   }

   public void startInvasion() {
      this.startInvasion((EntityPlayer)null);
   }

   public void startInvasion(EntityPlayer announcePlayer) {
      this.startInvasion(announcePlayer, -1);
   }

   public void startInvasion(EntityPlayer announcePlayer, int size) {
      if (size < 0) {
         size = MathHelper.func_76136_a(this.field_70146_Z, 30, 70);
      }

      this.invasionRemaining = this.invasionSize = size;
      this.playHorn();
      double announceRange = INVASION_FOLLOW_RANGE * 2.0D;
      List nearbyPlayers = this.field_70170_p.func_72872_a(EntityPlayer.class, this.field_70121_D.func_72314_b(announceRange, announceRange, announceRange));
      if (announcePlayer != null && !nearbyPlayers.contains(announcePlayer)) {
         nearbyPlayers.add(announcePlayer);
      }

      Iterator var6 = nearbyPlayers.iterator();

      while(var6.hasNext()) {
         EntityPlayer entityplayer = (EntityPlayer)var6.next();
         boolean announce = LOTRLevelData.getData(entityplayer).getAlignment(this.getInvasionType().invasionFaction) < 0.0F;
         if (entityplayer == announcePlayer) {
            announce = true;
         }

         if (announce) {
            this.announceInvasionTo(entityplayer);
            this.setWatchingInvasion((EntityPlayerMP)entityplayer, false);
         }
      }

   }

   private void announceInvasionTo(EntityPlayer entityplayer) {
      entityplayer.func_145747_a(new ChatComponentTranslation("chat.lotr.invasion.start", new Object[]{this.getInvasionType().invasionName()}));
   }

   public void setWatchingInvasion(EntityPlayerMP entityplayer, boolean overrideAlreadyWatched) {
      IMessage pkt = new LOTRPacketInvasionWatch(this, overrideAlreadyWatched);
      LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
   }

   public void selectAppropriateBonusFactions() {
      if (LOTRFaction.controlZonesEnabled(this.field_70170_p)) {
         LOTRFaction invasionFaction = this.getInvasionType().invasionFaction;
         Iterator var2 = invasionFaction.getBonusesForKilling().iterator();

         LOTRFaction nearest;
         while(var2.hasNext()) {
            nearest = (LOTRFaction)var2.next();
            if (!nearest.isolationist && nearest.inDefinedControlZone(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, 50)) {
               this.bonusFactions.add(nearest);
            }
         }

         if (this.bonusFactions.isEmpty()) {
            int nearestRange = 150;
            nearest = null;
            double nearestDist = Double.MAX_VALUE;
            Iterator var6 = invasionFaction.getBonusesForKilling().iterator();

            while(true) {
               LOTRFaction faction;
               double dist;
               do {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           if (nearest != null) {
                              this.bonusFactions.add(nearest);
                           }

                           return;
                        }

                        faction = (LOTRFaction)var6.next();
                     } while(faction.isolationist);

                     dist = faction.distanceToNearestControlZoneInRange(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, nearestRange);
                  } while(dist < 0.0D);
               } while(nearest != null && dist >= nearestDist);

               nearest = faction;
               nearestDist = dist;
            }
         }
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74778_a("InvasionType", this.getInvasionType().codeName());
      nbt.func_74768_a("InvasionSize", this.invasionSize);
      nbt.func_74768_a("InvasionRemaining", this.invasionRemaining);
      nbt.func_74768_a("SuccessiveFailedSpawns", this.successiveFailedSpawns);
      nbt.func_74768_a("TimeSinceProgress", this.timeSincePlayerProgress);
      NBTTagList bonusTags;
      Iterator var3;
      if (!this.recentPlayerContributors.isEmpty()) {
         bonusTags = new NBTTagList();
         var3 = this.recentPlayerContributors.entrySet().iterator();

         while(var3.hasNext()) {
            Entry e = (Entry)var3.next();
            UUID player = (UUID)e.getKey();
            int time = (Integer)e.getValue();
            NBTTagCompound playerData = new NBTTagCompound();
            playerData.func_74778_a("Player", player.toString());
            playerData.func_74777_a("Time", (short)time);
            bonusTags.func_74742_a(playerData);
         }

         nbt.func_74782_a("RecentPlayers", bonusTags);
      }

      nbt.func_74757_a("Warhorn", this.isWarhorn);
      nbt.func_74757_a("NPCPersistent", this.spawnsPersistent);
      if (!this.bonusFactions.isEmpty()) {
         bonusTags = new NBTTagList();
         var3 = this.bonusFactions.iterator();

         while(var3.hasNext()) {
            LOTRFaction f = (LOTRFaction)var3.next();
            String fName = f.codeName();
            bonusTags.func_74742_a(new NBTTagString(fName));
         }

         nbt.func_74782_a("BonusFactions", bonusTags);
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      LOTRInvasions type = LOTRInvasions.forName(nbt.func_74779_i("InvasionType"));
      if (type == null && nbt.func_74764_b("Faction")) {
         String factionName = nbt.func_74779_i("Faction");
         type = LOTRInvasions.forName(factionName);
      }

      if (type != null && !type.invasionMobs.isEmpty()) {
         this.setInvasionType(type);
         if (nbt.func_74764_b("MobsRemaining")) {
            this.invasionSize = this.invasionRemaining = nbt.func_74762_e("MobsRemaining");
         } else {
            this.invasionSize = nbt.func_74762_e("InvasionSize");
            if (nbt.func_74764_b("InvasionRemaining")) {
               this.invasionRemaining = nbt.func_74762_e("InvasionRemaining");
            } else {
               this.invasionRemaining = this.invasionSize;
            }
         }

         this.successiveFailedSpawns = nbt.func_74762_e("SuccessiveFailedSpawns");
         this.timeSincePlayerProgress = nbt.func_74762_e("TimeSinceProgress");
         this.recentPlayerContributors.clear();
         int i;
         NBTTagList bonusTags;
         if (nbt.func_74764_b("RecentPlayers")) {
            bonusTags = nbt.func_150295_c("RecentPlayers", 10);

            for(i = 0; i < bonusTags.func_74745_c(); ++i) {
               NBTTagCompound playerData = bonusTags.func_150305_b(i);
               String playerS = playerData.func_74779_i("Player");

               try {
                  UUID player = UUID.fromString(playerS);
                  if (player != null) {
                     int time = playerData.func_74765_d("Time");
                     this.recentPlayerContributors.put(player, Integer.valueOf(time));
                  }
               } catch (IllegalArgumentException var9) {
                  FMLLog.warning("LOTR: Error loading invasion recent players - %s is not a valid UUID", new Object[]{playerS});
                  var9.printStackTrace();
               }
            }
         }

         if (nbt.func_74764_b("Warhorn")) {
            this.isWarhorn = nbt.func_74767_n("Warhorn");
         }

         if (nbt.func_74764_b("NPCPersistent")) {
            this.spawnsPersistent = nbt.func_74767_n("NPCPersistent");
         }

         if (nbt.func_74764_b("BonusFactions")) {
            bonusTags = nbt.func_150295_c("BonusFactions", 8);

            for(i = 0; i < bonusTags.func_74745_c(); ++i) {
               String fName = bonusTags.func_150307_f(i);
               LOTRFaction f = LOTRFaction.forName(fName);
               if (f != null) {
                  this.bonusFactions.add(f);
               }
            }
         }
      } else {
         this.func_70106_y();
      }

   }

   private void endInvasion(boolean completed) {
      if (completed) {
         LOTRFaction invasionFac = this.getInvasionType().invasionFaction;
         float conqBoost = 50.0F;
         Set achievementPlayers = new HashSet();
         Set conqRewardPlayers = new HashSet();
         Iterator var6 = this.recentPlayerContributors.keySet().iterator();

         EntityPlayer entityplayer;
         while(var6.hasNext()) {
            UUID player = (UUID)var6.next();
            entityplayer = this.field_70170_p.func_152378_a(player);
            if (entityplayer != null) {
               double range = 100.0D;
               if (entityplayer.field_71093_bK == this.field_71093_bK && entityplayer.func_70068_e(this) < range * range) {
                  LOTRPlayerData pd = LOTRLevelData.getData(player);
                  if (pd.getAlignment(invasionFac) <= 0.0F) {
                     achievementPlayers.add(entityplayer);
                  }

                  LOTRFaction pledged = pd.getPledgeFaction();
                  if (pledged != null && pledged.isBadRelation(invasionFac)) {
                     conqRewardPlayers.add(entityplayer);
                  }
               }
            }
         }

         var6 = achievementPlayers.iterator();

         while(var6.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer)var6.next();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            pd.addAchievement(LOTRAchievement.defeatInvasion);
         }

         if (!conqRewardPlayers.isEmpty()) {
            float boostPerPlayer = 50.0F / (float)conqRewardPlayers.size();
            Iterator var15 = conqRewardPlayers.iterator();

            while(var15.hasNext()) {
               entityplayer = (EntityPlayer)var15.next();
               LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
               pd.givePureConquestBonus(entityplayer, pd.getPledgeFaction(), this.getInvasionType().invasionFaction, boostPerPlayer, "lotr.alignment.invasionDefeat", this.field_70165_t, this.field_70163_u, this.field_70161_v);
            }
         }
      }

      this.field_70170_p.func_72876_a(this, this.field_70165_t, this.field_70163_u + (double)this.field_70131_O / 2.0D, this.field_70161_v, 0.0F, false);
      this.func_70106_y();
   }

   public void func_70071_h_() {
      if (!this.field_70170_p.field_72995_K && this.field_70170_p.field_73013_u == EnumDifficulty.PEACEFUL) {
         this.endInvasion(false);
      } else {
         this.field_70169_q = this.field_70165_t;
         this.field_70167_r = this.field_70163_u;
         this.field_70166_s = this.field_70161_v;
         this.prevSpawnerSpin = this.spawnerSpin;
         this.spawnerSpin += 6.0F;
         this.prevSpawnerSpin = MathHelper.func_76142_g(this.prevSpawnerSpin);
         this.spawnerSpin = MathHelper.func_76142_g(this.spawnerSpin);
         this.field_70159_w = 0.0D;
         this.field_70181_x = 0.0D;
         this.field_70179_y = 0.0D;
         this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
         if (!this.field_70170_p.field_72995_K) {
            if (this.invasionRemaining > 0) {
               ++this.timeSincePlayerProgress;
               if (LOTRConfig.invasionProgressReverts && this.timeSincePlayerProgress >= 6000 && !this.isWarhorn && this.timeSincePlayerProgress % 1200 == 0) {
                  ++this.invasionRemaining;
                  this.invasionRemaining = Math.min(this.invasionRemaining, this.invasionSize);
               }

               if (!this.recentPlayerContributors.isEmpty()) {
                  Set removes = new HashSet();
                  Iterator var2 = this.recentPlayerContributors.entrySet().iterator();

                  while(var2.hasNext()) {
                     Entry e = (Entry)var2.next();
                     UUID player = (UUID)e.getKey();
                     int time = (Integer)e.getValue();
                     --time;
                     e.setValue(time);
                     if (time <= 0) {
                        removes.add(player);
                     }
                  }

                  var2 = removes.iterator();

                  while(var2.hasNext()) {
                     UUID player = (UUID)var2.next();
                     this.recentPlayerContributors.remove(player);
                  }
               }
            } else {
               this.endInvasion(true);
            }
         }

         if (!this.field_70170_p.field_72995_K && LOTRMod.canSpawnMobs(this.field_70170_p)) {
            LOTRInvasions invasionType = this.getInvasionType();
            EntityPlayer closePlayer = this.field_70170_p.func_72977_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, 80.0D);
            if (closePlayer != null && this.invasionRemaining > 0) {
               double nearbySearch = INVASION_FOLLOW_RANGE * 2.0D;
               List nearbyNPCs = this.field_70170_p.func_82733_a(LOTREntityNPC.class, this.field_70121_D.func_72314_b(nearbySearch, nearbySearch, nearbySearch), this.selectThisInvasionMobs());
               if (nearbyNPCs.size() < 16 && this.field_70146_Z.nextInt(160) == 0) {
                  int spawnAttempts = MathHelper.func_76136_a(this.field_70146_Z, 1, 6);
                  spawnAttempts = Math.min(spawnAttempts, this.invasionRemaining);
                  boolean spawnedAnyMobs = false;

                  for(int l = 0; l < spawnAttempts; ++l) {
                     LOTRInvasions.InvasionSpawnEntry entry = (LOTRInvasions.InvasionSpawnEntry)WeightedRandom.func_76271_a(this.field_70146_Z, invasionType.invasionMobs);
                     Class entityClass = entry.getEntityClass();
                     String entityName = LOTREntities.getStringFromClass(entityClass);
                     LOTREntityNPC npc = (LOTREntityNPC)EntityList.func_75620_a(entityName, this.field_70170_p);
                     if (this.attemptSpawnMob(npc)) {
                        spawnedAnyMobs = true;
                     }
                  }

                  if (spawnedAnyMobs) {
                     this.successiveFailedSpawns = 0;
                     this.playHorn();
                  } else {
                     ++this.successiveFailedSpawns;
                     if (this.successiveFailedSpawns >= 16) {
                        this.endInvasion(false);
                     }
                  }
               }
            }
         } else {
            String particle = this.field_70146_Z.nextBoolean() ? "smoke" : "flame";
            this.field_70170_p.func_72869_a(particle, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }

         this.updateWatchedInvasionValues();
      }
   }

   private boolean attemptSpawnMob(LOTREntityNPC npc) {
      for(int at = 0; at < 40; ++at) {
         int i = MathHelper.func_76128_c(this.field_70165_t) + MathHelper.func_76136_a(this.field_70146_Z, -6, 6);
         int k = MathHelper.func_76128_c(this.field_70161_v) + MathHelper.func_76136_a(this.field_70146_Z, -6, 6);
         int j = MathHelper.func_76128_c(this.field_70163_u) + MathHelper.func_76136_a(this.field_70146_Z, -8, 4);
         if (this.field_70170_p.func_147439_a(i, j - 1, k).isSideSolid(this.field_70170_p, i, j - 1, k, ForgeDirection.UP)) {
            npc.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
            npc.liftSpawnRestrictions = true;
            Result canSpawn = ForgeEventFactory.canEntitySpawn(npc, this.field_70170_p, (float)npc.field_70165_t, (float)npc.field_70163_u, (float)npc.field_70161_v);
            if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT && npc.func_70601_bi()) {
               npc.liftSpawnRestrictions = false;
               npc.func_110161_a((IEntityLivingData)null);
               npc.isNPCPersistent = false;
               if (this.spawnsPersistent) {
                  npc.isNPCPersistent = true;
               }

               npc.setInvasionID(this.getInvasionID());
               npc.killBonusFactions.addAll(this.bonusFactions);
               this.field_70170_p.func_72838_d(npc);
               IAttributeInstance followRangeAttrib = npc.func_110148_a(SharedMonsterAttributes.field_111265_b);
               double followRange = followRangeAttrib.func_111125_b();
               followRange = Math.max(followRange, INVASION_FOLLOW_RANGE);
               followRangeAttrib.func_111128_a(followRange);
               return true;
            }
         }
      }

      return false;
   }

   private IEntitySelector selectThisInvasionMobs() {
      return new IEntitySelector() {
         public boolean func_82704_a(Entity entity) {
            if (entity.func_70089_S() && entity instanceof LOTREntityNPC) {
               LOTREntityNPC npc = (LOTREntityNPC)entity;
               return npc.isInvasionSpawned() && npc.getInvasionID().equals(LOTREntityInvasionSpawner.this.getInvasionID());
            } else {
               return false;
            }
         }
      };
   }

   public void addPlayerKill(EntityPlayer entityplayer) {
      --this.invasionRemaining;
      this.timeSincePlayerProgress = 0;
      this.recentPlayerContributors.put(entityplayer.func_110124_au(), 2400);
   }

   public boolean func_70067_L() {
      return true;
   }

   public void func_70108_f(Entity entity) {
   }

   public boolean func_85031_j(Entity entity) {
      return entity instanceof EntityPlayer ? this.func_70097_a(DamageSource.func_76365_a((EntityPlayer)entity), 0.0F) : false;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      Entity entity = damagesource.func_76346_g();
      if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
         if (!this.field_70170_p.field_72995_K) {
            this.endInvasion(false);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_130002_c(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K && entityplayer.field_71075_bZ.field_75098_d && !this.bonusFactions.isEmpty()) {
         IChatComponent message = new ChatComponentText("");

         LOTRFaction f;
         for(Iterator var3 = this.bonusFactions.iterator(); var3.hasNext(); message.func_150257_a(new ChatComponentTranslation(f.factionName(), new Object[0]))) {
            f = (LOTRFaction)var3.next();
            if (!message.func_150253_a().isEmpty()) {
               message.func_150257_a(new ChatComponentText(", "));
            }
         }

         entityplayer.func_145747_a(message);
         return true;
      } else {
         return false;
      }
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      LOTRInvasions invasionType = this.getInvasionType();
      return invasionType != null ? LOTRItemConquestHorn.createHorn(invasionType) : null;
   }

   public static LOTREntityInvasionSpawner locateInvasionNearby(Entity seeker, final UUID id) {
      World world = seeker.field_70170_p;
      double search = 256.0D;
      List invasions = world.func_82733_a(LOTREntityInvasionSpawner.class, seeker.field_70121_D.func_72314_b(search, search, search), new IEntitySelector() {
         public boolean func_82704_a(Entity e) {
            return e.func_110124_au().equals(id);
         }
      });
      return !invasions.isEmpty() ? (LOTREntityInvasionSpawner)invasions.get(0) : null;
   }
}
