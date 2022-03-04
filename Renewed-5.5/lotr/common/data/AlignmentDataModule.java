package lotr.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import lotr.common.entity.misc.AlignmentBonusEntity;
import lotr.common.fac.AlignmentBonus;
import lotr.common.fac.AlignmentBonusMap;
import lotr.common.fac.AlignmentPredicate;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointer;
import lotr.common.fac.FactionRank;
import lotr.common.fac.FactionRegion;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.network.CPacketToggle;
import lotr.common.network.CPacketViewedFactions;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketAlignment;
import lotr.common.network.SPacketAlignmentBonus;
import lotr.common.network.SPacketAlignmentDrain;
import lotr.common.network.SPacketPledge;
import lotr.common.network.SPacketPledgeBreak;
import lotr.common.network.SPacketRegionLastViewedFaction;
import lotr.common.network.SPacketToggle;
import lotr.common.network.SPacketViewingFaction;
import lotr.common.network.SidedTogglePacket;
import lotr.common.util.LOTRUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

public class AlignmentDataModule extends PlayerDataModule {
   private Faction currentViewedFaction;
   private final Map regionLastViewedFactions = new HashMap();
   private final Map alignments = new HashMap();
   private Faction pledgeFaction;
   private int pledgeKillCooldown;
   public static final int PLEDGE_KILL_COOLDOWN_MAX = 24000;
   private int pledgeBreakCooldown;
   private int pledgeBreakCooldownStart;
   private Faction brokenPledgeFaction;
   private boolean friendlyFire;

   protected AlignmentDataModule(LOTRPlayerData pd) {
      super(pd);
   }

   public void save(CompoundNBT playerNBT) {
      this.writeFactionToNBT(playerNBT, "CurrentFaction", this.currentViewedFaction);
      playerNBT.func_218657_a("PrevRegionFactions", DataUtil.saveMapAsListNBT(this.regionLastViewedFactions, (nbt, region, fac) -> {
         nbt.func_74778_a("Region", region.getName().toString());
         nbt.func_74778_a("Faction", fac.getName().toString());
      }));
      playerNBT.func_218657_a("AlignmentMap", DataUtil.saveMapAsListNBT(this.alignments, (nbt, fac, alignment) -> {
         nbt.func_74778_a("Faction", fac.getName().toString());
         nbt.func_74776_a("AlignF", alignment);
      }));
      this.writeFactionToNBT(playerNBT, "PledgeFac", this.pledgeFaction);
      playerNBT.func_74768_a("PledgeKillCD", this.pledgeKillCooldown);
      playerNBT.func_74768_a("PledgeBreakCD", this.pledgeBreakCooldown);
      playerNBT.func_74768_a("PledgeBreakCDStart", this.pledgeBreakCooldownStart);
      this.writeFactionToNBT(playerNBT, "BrokenPledgeFac", this.brokenPledgeFaction);
      playerNBT.func_74757_a("FriendlyFire", this.friendlyFire);
   }

   public void load(CompoundNBT playerNBT) {
      this.currentViewedFaction = this.loadFactionFromNBT(playerNBT, "CurrentFaction", "Loaded nonexistent viewing faction %s");
      DataUtil.loadMapFromListNBT(this.regionLastViewedFactions, playerNBT.func_150295_c("PrevRegionFactions", 10), (nbt) -> {
         String regionName = nbt.func_74779_i("Region");
         String facName = nbt.func_74779_i("Faction");
         FactionRegion region = this.currentFactionSettings().getRegionByName(new ResourceLocation(regionName));
         if (region != null) {
            Faction faction = this.currentFactionSettings().getFactionByName(new ResourceLocation(facName));
            if (faction != null) {
               return Pair.of(region, faction);
            } else {
               this.playerData.logPlayerError("Loaded nonexistent faction ID %s", facName);
               return null;
            }
         } else {
            this.playerData.logPlayerError("Loaded nonexistent faction region ID %s", regionName);
            return null;
         }
      });
      DataUtil.loadMapFromListNBT(this.alignments, playerNBT.func_150295_c("AlignmentMap", 10), (nbt) -> {
         String facName = nbt.func_74779_i("Faction");
         Faction faction = this.currentFactionSettings().getFactionByName(new ResourceLocation(facName));
         if (faction != null) {
            float alignment = nbt.func_74760_g("AlignF");
            return Pair.of(faction, alignment);
         } else {
            this.playerData.logPlayerError("Loaded nonexistent faction ID %s", facName);
            return null;
         }
      });
      this.pledgeFaction = this.loadFactionFromNBT(playerNBT, "PledgeFac", "Loaded nonexistent pledge faction %s");
      this.pledgeKillCooldown = playerNBT.func_74762_e("PledgeKillCD");
      this.pledgeBreakCooldown = playerNBT.func_74762_e("PledgeBreakCD");
      this.pledgeBreakCooldownStart = playerNBT.func_74762_e("PledgeBreakCDStart");
      this.brokenPledgeFaction = this.loadFactionFromNBT(playerNBT, "BrokenPledgeFac", "Loaded nonexistent broken pledge faction %s");
      this.friendlyFire = playerNBT.func_74767_n("FriendlyFire");
   }

   protected void sendLoginData(PacketBuffer buf) {
      this.writeFactionToBuffer(buf, this.currentViewedFaction);
      DataUtil.writeMapToBuffer(buf, this.regionLastViewedFactions, (region, faction) -> {
         buf.func_150787_b(region.getAssignedId());
         buf.func_150787_b(faction.getAssignedId());
      });
      DataUtil.writeMapToBuffer(buf, this.alignments, (faction, alignment) -> {
         buf.func_150787_b(faction.getAssignedId());
         buf.writeFloat(alignment);
      });
      this.writeFactionToBuffer(buf, this.pledgeFaction);
      buf.writeBoolean(this.friendlyFire);
   }

   protected void receiveLoginData(PacketBuffer buf) {
      this.currentViewedFaction = this.readFactionFromBuffer(buf, "Received nonexistent viewing faction ID %d from server");
      DataUtil.fillMapFromBuffer(buf, this.regionLastViewedFactions, () -> {
         int regionId = buf.func_150792_a();
         int factionId = buf.func_150792_a();
         FactionRegion region = this.currentFactionSettings().getRegionByID(regionId);
         Faction faction = this.currentFactionSettings().getFactionByID(factionId);
         if (region == null) {
            LOTRLog.warn("Received nonexistent faction region ID %d from server", regionId);
            return null;
         } else if (faction == null) {
            LOTRLog.warn("Received nonexistent faction ID %d from server", factionId);
            return null;
         } else {
            return Pair.of(region, faction);
         }
      });
      DataUtil.fillMapFromBuffer(buf, this.alignments, () -> {
         int factionId = buf.func_150792_a();
         Faction faction = this.currentFactionSettings().getFactionByID(factionId);
         float alignment = buf.readFloat();
         if (faction == null) {
            LOTRLog.warn("Received nonexistent faction ID %d from server", factionId);
            return null;
         } else {
            return Pair.of(faction, alignment);
         }
      });
      this.pledgeFaction = this.readFactionFromBuffer(buf, "Received nonexistent pledge faction ID %d from server");
      this.friendlyFire = buf.readBoolean();
   }

   protected void onUpdate(ServerPlayerEntity player, ServerWorld world, int tick) {
      RegistryKey currentDimension = LOTRDimensions.getCurrentLOTRDimensionOrFallback(world);
      if (this.currentViewedFaction != null) {
         FactionRegion currentRegion = this.currentViewedFaction.getRegion();
         if (!currentRegion.getDimension().equals(currentDimension)) {
            this.updateCurrentViewedFactionToNewDimension(currentDimension);
         }
      } else {
         this.updateCurrentViewedFactionToNewDimension(currentDimension);
      }

      this.runAlignmentDraining(player, tick);
      if (this.pledgeKillCooldown > 0) {
         --this.pledgeKillCooldown;
         if (this.pledgeKillCooldown == 0 || isTimerAutosaveTick()) {
            this.markDirty();
         }
      }

      if (this.pledgeBreakCooldown > 0) {
         this.setPledgeBreakCooldown(this.pledgeBreakCooldown - 1);
      }

   }

   private void updateCurrentViewedFactionToNewDimension(RegistryKey currentDimension) {
      List dimensionRegions = this.currentFactionSettings().getRegionsForDimension(currentDimension);
      if (dimensionRegions != null && !dimensionRegions.isEmpty()) {
         Iterator var3 = dimensionRegions.iterator();

         while(var3.hasNext()) {
            FactionRegion region = (FactionRegion)var3.next();
            List regionFacs = this.currentFactionSettings().getFactionsForRegion(region);
            if (regionFacs != null && !regionFacs.isEmpty()) {
               Faction fac = this.getRegionLastViewedFaction(region);
               if (fac != null) {
                  this.setCurrentViewedFaction(fac);
                  break;
               }
            }
         }
      }

   }

   public float getAlignment(Faction faction) {
      return (Float)this.alignments.getOrDefault(faction, 0.0F);
   }

   public float getAlignment(FactionPointer pointer) {
      Optional faction = pointer.resolveFaction(this.currentFactionSettings());
      if (faction.isPresent()) {
         return this.getAlignment((Faction)faction.get());
      } else {
         LOTRLog.warn("Tried to get player %s alignment for faction %s - but faction isn't defined in the current world!", this.getPlayerUUID(), pointer.getName());
         return 0.0F;
      }
   }

   public boolean hasAlignment(Faction faction, AlignmentPredicate predicate) {
      return predicate.test(this.getAlignment(faction));
   }

   public boolean hasAlignmentWithAny(Collection factions, AlignmentPredicate predicate) {
      return factions.stream().anyMatch((fac) -> {
         return this.hasAlignment(fac, predicate);
      });
   }

   public boolean hasAlignmentWithAll(Collection factions, AlignmentPredicate predicate) {
      return factions.stream().allMatch((fac) -> {
         return this.hasAlignment(fac, predicate);
      });
   }

   public float getHighestAlignmentAmong(Collection factions) {
      return (Float)factions.stream().map(this::getAlignment).sorted(Comparator.reverseOrder()).findFirst().orElse(Float.MIN_VALUE);
   }

   public Map getAlignmentsView() {
      return new HashMap(this.alignments);
   }

   public void setAlignment(Faction faction, float alignment) {
      if (faction.isPlayableAlignmentFaction()) {
         float prevAlignment = this.getAlignment(faction);
         if (alignment != prevAlignment) {
            this.alignments.put(faction, alignment);
            this.markDirty();
            this.sendPacketToClient(new SPacketAlignment(faction, alignment));
            this.executeIfPlayerExistsServerside((player) -> {
               SPacketAlignment packetForOtherPlayers = new SPacketAlignment(faction, alignment, player);
               LOTRPacketHandler.sendToAllExcept(packetForOtherPlayers, player);
            });
         }
      }

      if (this.pledgeFaction != null && !this.canPledgeToNow(this.pledgeFaction)) {
         this.executeIfPlayerExistsServerside((player) -> {
            this.revokePledgeFaction(player, false);
         });
      }

   }

   public void addAlignment(Faction faction, float alignmentAdd) {
      this.setAlignment(faction, this.getAlignment(faction) + alignmentAdd);
   }

   public AlignmentBonusMap addAlignmentFromBonus(ServerPlayerEntity player, AlignmentBonus source, Faction mainFaction, List forcedBonusFactions, Entity entity) {
      return this.addAlignmentFromBonus(player, source, mainFaction, forcedBonusFactions, entity.func_213303_ch().func_72441_c(0.0D, (double)entity.func_213302_cg() * 0.7D, 0.0D));
   }

   public AlignmentBonusMap addAlignmentFromBonus(ServerPlayerEntity player, AlignmentBonus source, Faction mainFaction, Vector3d pos) {
      return this.addAlignmentFromBonus(player, source, mainFaction, (List)null, (Vector3d)pos);
   }

   public AlignmentBonusMap addAlignmentFromBonus(ServerPlayerEntity player, AlignmentBonus source, Faction mainFaction, List forcedBonusFactions, Vector3d pos) {
      float bonus = source.bonus;
      AlignmentBonusMap factionBonusMap = new AlignmentBonusMap();
      float prevMainAlignment = this.getAlignment(mainFaction);
      float conquestBonus = 0.0F;
      float notPledgedMultiplier = 0.5F;
      if (source.isKill) {
         mainFaction.getBonusesForKilling().stream().filter(Faction::isPlayableAlignmentFaction).filter((fac) -> {
            return fac.approvesCivilianKills() || !source.isCivilianKill;
         }).forEach((bonusFaction) -> {
            if (!source.isKillByHiredUnit) {
               float mplier = 0.0F;
               if (forcedBonusFactions != null && forcedBonusFactions.contains(bonusFaction)) {
                  mplier = 1.0F;
               } else {
                  mplier = bonusFaction.getAreasOfInfluence().getAlignmentMultiplier(player);
               }

               if (mplier > 0.0F) {
                  float alignment = this.getAlignment(bonusFaction);
                  float factionBonus = Math.abs(bonus);
                  factionBonus *= mplier;
                  if (alignment >= bonusFaction.getPledgeAlignment() && !this.isPledgedTo(bonusFaction)) {
                     factionBonus *= 0.5F;
                  }

                  factionBonus = this.checkBonusForPledgeEnemyLimit(bonusFaction, factionBonus);
                  alignment += factionBonus;
                  this.setAlignment(bonusFaction, alignment);
                  factionBonusMap.put(bonusFaction, factionBonus);
               }
            }

            if (bonusFaction == this.getPledgeFaction()) {
            }

         });
         mainFaction.getPenaltiesForKilling().stream().filter(Faction::isPlayableAlignmentFaction).forEach((penaltyFaction) -> {
            if (!source.isKillByHiredUnit) {
               float mplier = 0.0F;
               if (penaltyFaction == mainFaction) {
                  mplier = 1.0F;
               } else {
                  mplier = penaltyFaction.getAreasOfInfluence().getAlignmentMultiplier(player);
               }

               if (mplier > 0.0F) {
                  float alignment = this.getAlignment(penaltyFaction);
                  float factionPenalty = -Math.abs(bonus);
                  factionPenalty *= mplier;
                  factionPenalty = AlignmentBonus.scaleKillPenalty(factionPenalty, alignment);
                  alignment += factionPenalty;
                  this.setAlignment(penaltyFaction, alignment);
                  factionBonusMap.put(penaltyFaction, factionPenalty);
               }
            }

         });
      } else if (mainFaction.isPlayableAlignmentFaction()) {
         float alignment = this.getAlignment(mainFaction);
         float factionBonus = bonus;
         if (bonus > 0.0F && alignment >= mainFaction.getPledgeAlignment() && !this.isPledgedTo(mainFaction)) {
            factionBonus = bonus * 0.5F;
         }

         factionBonus = this.checkBonusForPledgeEnemyLimit(mainFaction, factionBonus);
         alignment += factionBonus;
         this.setAlignment(mainFaction, alignment);
         factionBonusMap.put(mainFaction, factionBonus);
      }

      if (!factionBonusMap.isEmpty() || conquestBonus != 0.0F) {
         int entityId = AlignmentBonusEntity.getNextSafeEntityIdForBonusSpawn(player.func_71121_q());
         this.sendPacketToClient(new SPacketAlignmentBonus(entityId, source, mainFaction, prevMainAlignment, factionBonusMap, conquestBonus, pos));
      }

      return factionBonusMap;
   }

   private float checkBonusForPledgeEnemyLimit(Faction fac, float bonus) {
      if (this.isPledgeEnemyAlignmentLimited(fac)) {
         float alignment = this.getAlignment(fac);
         float limit = this.getPledgeEnemyAlignmentLimit(fac);
         if (alignment > limit) {
            bonus = 0.0F;
         } else if (alignment + bonus > limit) {
            bonus = limit - alignment;
         }
      }

      return bonus;
   }

   public Faction getCurrentViewedFaction() {
      return this.currentViewedFaction;
   }

   public Faction getCurrentViewedFactionOrFallbackToFirstIn(RegistryKey currentDimension) {
      if (this.currentViewedFaction == null || !this.currentViewedFaction.getDimension().equals(currentDimension)) {
         this.updateCurrentViewedFactionToNewDimension(currentDimension);
      }

      return this.currentViewedFaction;
   }

   public void setCurrentViewedFaction(Faction fac) {
      if (fac != null) {
         this.currentViewedFaction = fac;
         this.markDirty();
         this.sendPacketToClient(new SPacketViewingFaction(this.currentViewedFaction));
      }

   }

   public Faction getRegionLastViewedFaction(FactionRegion region) {
      Faction fac = (Faction)this.regionLastViewedFactions.get(region);
      if (fac == null) {
         List regionFacs = this.currentFactionSettings().getFactionsForRegion(region);
         if (!regionFacs.isEmpty()) {
            fac = (Faction)regionFacs.get(0);
            this.regionLastViewedFactions.put(region, fac);
         }
      }

      return fac;
   }

   public void setRegionLastViewedFaction(FactionRegion region, Faction fac) {
      List regionFacs = this.currentFactionSettings().getFactionsForRegion(region);
      if (regionFacs.contains(fac)) {
         this.regionLastViewedFactions.put(region, fac);
         this.markDirty();
         this.sendPacketToClient(new SPacketRegionLastViewedFaction(region, fac));
      }

   }

   public void sendViewedFactionsToServer() {
      CPacketViewedFactions packet = new CPacketViewedFactions(this.currentViewedFaction, this.regionLastViewedFactions);
      LOTRPacketHandler.sendToServer(packet);
   }

   public Faction getPledgeFaction() {
      return this.pledgeFaction;
   }

   public boolean isPledgedTo(Faction faction) {
      return faction == this.pledgeFaction;
   }

   public void setPledgeFaction(Faction faction) {
      this.pledgeFaction = faction;
      this.pledgeKillCooldown = 0;
      this.markDirty();
      this.sendPacketToClient(new SPacketPledge(faction));
      this.executeIfPlayerExistsServerside((player) -> {
         if (faction != null) {
            player.field_70170_p.func_184133_a((PlayerEntity)null, player.func_233580_cy_(), LOTRSoundEvents.PLEDGE, SoundCategory.PLAYERS, 1.0F, 1.0F);
         }

      });
   }

   public boolean isValidPledgeFaction(Faction faction) {
      return faction.isPlayableAlignmentFaction();
   }

   public boolean canPledgeToNow(Faction faction) {
      if (!this.isValidPledgeFaction(faction)) {
         return false;
      } else {
         return this.hasPledgeAlignment(faction) && this.getFactionsPreventingPledgeTo(faction).isEmpty();
      }
   }

   public boolean hasPledgeAlignment(Faction faction) {
      return this.getAlignment(faction) >= faction.getPledgeAlignment();
   }

   public List getFactionsPreventingPledgeTo(Faction faction) {
      return (List)this.currentFactionSettings().getFactions().stream().filter((otherFac) -> {
         return otherFac.isPlayableAlignmentFaction() && this.doesFactionPreventPledge(faction, otherFac) && this.getAlignment(otherFac) > 0.0F;
      }).collect(Collectors.toList());
   }

   private boolean doesFactionPreventPledge(Faction pledgeFac, Faction otherFac) {
      return pledgeFac.isMortalEnemy(otherFac);
   }

   public boolean canMakeNewPledge() {
      return this.pledgeBreakCooldown <= 0;
   }

   public void revokePledgeFaction(ServerPlayerEntity player, boolean intentional) {
      Faction wasPledgedTo = this.pledgeFaction;
      float pledgeLvl = wasPledgedTo.getPledgeAlignment();
      float prevAlign = this.getAlignment(wasPledgedTo);
      float diff = prevAlign - pledgeLvl;
      float cdProportion = diff / 5000.0F;
      cdProportion = MathHelper.func_76131_a(cdProportion, 0.0F, 1.0F);
      int minCdTicks = LOTRUtil.minutesToTicks(30);
      int maxCdTicks = LOTRUtil.minutesToTicks(180);
      int cdTicks = Math.round(MathHelper.func_219799_g(cdProportion, (float)minCdTicks, (float)maxCdTicks));
      this.setPledgeFaction((Faction)null);
      this.setBrokenPledgeFaction(wasPledgedTo);
      this.setPledgeBreakCooldown(cdTicks);
      FactionRank rank = wasPledgedTo.getRankFor(prevAlign);
      FactionRank rank2Below = wasPledgedTo.getRankNBelow(rank, 2);
      float newAlign = rank2Below.getAlignment();
      newAlign = Math.max(newAlign, pledgeLvl / 2.0F);
      float alignPenalty = newAlign - prevAlign;
      if (alignPenalty < 0.0F) {
         AlignmentBonus penalty = AlignmentBonus.createPledgePenalty(alignPenalty);
         double lookRange = 2.0D;
         Vector3d posEye = new Vector3d(player.func_226277_ct_(), player.func_226280_cw_(), player.func_226281_cx_());
         Vector3d look = player.func_70676_i(1.0F);
         Vector3d posSight = posEye.func_178787_e(look.func_216372_d(lookRange, lookRange, lookRange));
         RayTraceResult blockLookAt = player.func_213324_a(lookRange, 1.0F, true);
         Vector3d penaltyPos;
         if (blockLookAt != null && blockLookAt.func_216346_c() == Type.BLOCK) {
            penaltyPos = blockLookAt.func_216347_e();
         } else {
            penaltyPos = posSight;
         }

         this.addAlignmentFromBonus(player, penalty, wasPledgedTo, penaltyPos);
      }

      player.field_70170_p.func_184133_a((PlayerEntity)null, player.func_233580_cy_(), LOTRSoundEvents.UNPLEDGE, SoundCategory.PLAYERS, 1.0F, 1.0F);
      TranslationTextComponent msg;
      if (intentional) {
         msg = new TranslationTextComponent("chat.lotr.pledge.break.intentional", new Object[]{wasPledgedTo.getDisplayName()});
         LOTRUtil.sendMessage(player, msg);
      } else {
         msg = new TranslationTextComponent("chat.lotr.pledge.break.unintentional", new Object[]{wasPledgedTo.getDisplayName()});
         LOTRUtil.sendMessage(player, msg);
      }

   }

   public void onPledgeKill(ServerPlayerEntity player) {
      this.pledgeKillCooldown += 24000;
      this.markDirty();
      if (this.pledgeKillCooldown > 24000) {
         this.revokePledgeFaction(player, false);
      } else if (this.pledgeFaction != null) {
         ITextComponent msg = new TranslationTextComponent("chat.lotr.pledge.killWarn", new Object[]{this.pledgeFaction.getDisplayName()});
         LOTRUtil.sendMessage(player, msg);
      }

   }

   public int getPledgeBreakCooldown() {
      return this.pledgeBreakCooldown;
   }

   public void setPledgeBreakCooldown(int i) {
      int preCD = this.pledgeBreakCooldown;
      Faction preBroken = this.brokenPledgeFaction;
      i = Math.max(0, i);
      this.pledgeBreakCooldown = i;
      boolean bigChange = (this.pledgeBreakCooldown == 0 || preCD == 0) && this.pledgeBreakCooldown != preCD;
      if (this.pledgeBreakCooldown > this.pledgeBreakCooldownStart) {
         this.setPledgeBreakCooldownStart(this.pledgeBreakCooldown);
         bigChange = true;
      }

      if (this.pledgeBreakCooldown <= 0 && preBroken != null) {
         this.setPledgeBreakCooldownStart(0);
         this.setBrokenPledgeFaction((Faction)null);
         bigChange = true;
      }

      if (bigChange || isTimerAutosaveTick()) {
         this.markDirty();
      }

      if (bigChange || this.pledgeBreakCooldown % 5 == 0) {
         this.sendPacketToClient(new SPacketPledgeBreak(this.pledgeBreakCooldown, this.pledgeBreakCooldownStart, this.brokenPledgeFaction));
      }

      if (this.pledgeBreakCooldown == 0 && preCD != this.pledgeBreakCooldown) {
         this.executeIfPlayerExistsServerside((player) -> {
            ITextComponent msg = new TranslationTextComponent("chat.lotr.pledge.breakCooldown", new Object[]{Faction.getFactionOrUnknownDisplayName(preBroken)});
            LOTRUtil.sendMessage(player, msg);
         });
      }

   }

   public int getPledgeBreakCooldownStart() {
      return this.pledgeBreakCooldownStart;
   }

   public void setPledgeBreakCooldownStart(int i) {
      this.pledgeBreakCooldownStart = i;
      this.markDirty();
   }

   public float getPledgeBreakCooldownFraction() {
      return this.pledgeBreakCooldownStart == 0 ? 0.0F : (float)this.pledgeBreakCooldown / (float)this.pledgeBreakCooldownStart;
   }

   public Faction getBrokenPledgeFaction() {
      return this.brokenPledgeFaction;
   }

   public void setBrokenPledgeFaction(Faction f) {
      this.brokenPledgeFaction = f;
      this.markDirty();
   }

   public boolean isPledgeEnemyAlignmentLimited(Faction faction) {
      return this.pledgeFaction != null && this.doesFactionPreventPledge(this.pledgeFaction, faction);
   }

   public float getPledgeEnemyAlignmentLimit(Faction faction) {
      return 0.0F;
   }

   private void runAlignmentDraining(ServerPlayerEntity player, int tick) {
      if ((Boolean)LOTRConfig.COMMON.alignmentDraining.get() && tick % 1000 == 0) {
         float drainLimit = 0.0F;
         List drainFactions = new ArrayList();
         List allFacs = this.currentFactionSettings().getAllPlayableAlignmentFactions();
         Iterator var6 = allFacs.iterator();

         Faction fac1;
         while(var6.hasNext()) {
            fac1 = (Faction)var6.next();
            Iterator var8 = allFacs.iterator();

            while(var8.hasNext()) {
               Faction fac2 = (Faction)var8.next();
               if (this.doFactionsDrain(fac1, fac2)) {
                  float align1 = this.getAlignment(fac1);
                  float align2 = this.getAlignment(fac2);
                  if (align1 > 0.0F && align2 > 0.0F) {
                     if (!drainFactions.contains(fac1)) {
                        drainFactions.add(fac1);
                     }

                     if (!drainFactions.contains(fac2)) {
                        drainFactions.add(fac2);
                     }
                  }
               }
            }
         }

         if (!drainFactions.isEmpty()) {
            var6 = drainFactions.iterator();

            while(var6.hasNext()) {
               fac1 = (Faction)var6.next();
               float align = this.getAlignment(fac1);
               float alignLoss = 5.0F;
               alignLoss = Math.min(alignLoss, align - 0.0F);
               align -= alignLoss;
               this.setAlignment(fac1, align);
            }

            LOTRPacketHandler.sendTo(new SPacketAlignmentDrain(drainFactions.size()), player);
            this.playerData.getMessageData().sendMessageIfNotReceived(PlayerMessageType.ALIGN_DRAIN);
         }
      }

   }

   private boolean doFactionsDrain(Faction fac1, Faction fac2) {
      return fac1.isMortalEnemy(fac2);
   }

   public boolean isFriendlyFireEnabled() {
      return this.friendlyFire;
   }

   public void setFriendlyFireEnabled(boolean flag) {
      if (this.friendlyFire != flag) {
         this.friendlyFire = flag;
         this.markDirty();
         this.sendPacketToClient(new SPacketToggle(SidedTogglePacket.ToggleType.FRIENDLY_FIRE, this.friendlyFire));
      }

   }

   public void toggleFriendlyFireEnabledAndSendToServer() {
      this.friendlyFire = !this.friendlyFire;
      LOTRPacketHandler.sendToServer(new CPacketToggle(SidedTogglePacket.ToggleType.FRIENDLY_FIRE, this.friendlyFire));
   }

   public boolean displayAlignmentAboveHead() {
      return true;
   }
}
