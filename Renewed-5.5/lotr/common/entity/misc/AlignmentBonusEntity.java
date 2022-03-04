package lotr.common.entity.misc;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import lotr.common.data.AlignmentDataModule;
import lotr.common.fac.AlignmentBonus;
import lotr.common.fac.AlignmentBonusMap;
import lotr.common.fac.Faction;
import lotr.common.init.LOTREntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class AlignmentBonusEntity extends Entity {
   private AlignmentBonus bonusSource;
   private Faction mainFaction;
   private float prevMainAlignment;
   private AlignmentBonusMap factionBonusMap;
   private float conquestBonus;
   private int particleAge;
   private int particlePrevAge;
   private int particleMaxAge;

   public AlignmentBonusEntity(EntityType type, World w) {
      super(type, w);
      this.particlePrevAge = this.particleAge = 0;
   }

   public static int getNextSafeEntityIdForBonusSpawn(ServerWorld world) {
      AlignmentBonusEntity entity = new AlignmentBonusEntity((EntityType)LOTREntities.ALIGNMENT_BONUS.get(), world);
      return entity.func_145782_y();
   }

   public static AlignmentBonusEntity createBonusEntityForClientSpawn(World world, int entityId, AlignmentBonus bonusSource, Faction mainFaction, float prevMainAlignment, AlignmentBonusMap factionBonusMap, float conquestBonus, Vector3d pos) {
      if (!world.field_72995_K) {
         throw new IllegalArgumentException("Alignment bonus entities cannot be spawned on the server side!");
      } else {
         AlignmentBonusEntity entity = new AlignmentBonusEntity((EntityType)LOTREntities.ALIGNMENT_BONUS.get(), world);
         entity.func_145769_d(entityId);
         entity.bonusSource = bonusSource;
         entity.mainFaction = mainFaction;
         entity.prevMainAlignment = prevMainAlignment;
         entity.factionBonusMap = factionBonusMap;
         entity.conquestBonus = conquestBonus;
         entity.func_70107_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c);
         entity.calcMaxAge();
         return entity;
      }
   }

   public IPacket func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   private void calcMaxAge() {
      float mostSignificantBonus = 0.0F;
      Iterator var2 = this.factionBonusMap.getChangedFactions().iterator();

      while(var2.hasNext()) {
         Faction fac = (Faction)var2.next();
         float bonus = Math.abs((Float)this.factionBonusMap.get(fac));
         if (bonus > mostSignificantBonus) {
            mostSignificantBonus = bonus;
         }
      }

      float conq = Math.abs(this.conquestBonus);
      if (conq > mostSignificantBonus) {
         mostSignificantBonus = conq;
      }

      this.particleMaxAge = 80;
      int extra = (int)(Math.min(1.0F, mostSignificantBonus / 50.0F) * 220.0F);
      this.particleMaxAge += extra;
   }

   public boolean shouldDisplayConquestBonus(AlignmentDataModule alignData) {
      Faction currentViewedFaction = alignData.getCurrentViewedFaction();
      if (this.conquestBonus > 0.0F && alignData.isPledgedTo(currentViewedFaction)) {
         return true;
      } else {
         return this.conquestBonus < 0.0F && (currentViewedFaction == this.mainFaction || alignData.isPledgedTo(currentViewedFaction));
      }
   }

   public Faction getFactionToDisplay(AlignmentDataModule alignData) {
      if (!this.factionBonusMap.isEmpty()) {
         Faction currentViewedFaction = alignData.getCurrentViewedFaction();
         if (this.factionBonusMap.containsKey(currentViewedFaction)) {
            return currentViewedFaction;
         }

         if (this.factionBonusMap.size() == 1 && this.mainFaction.isPlayableAlignmentFaction()) {
            return this.mainFaction;
         }

         if (this.mainFaction.isPlayableAlignmentFaction() && this.prevMainAlignment >= 0.0F && (Float)this.factionBonusMap.get(this.mainFaction) < 0.0F) {
            return this.mainFaction;
         }

         Optional highestFactionWithBonus = this.factionBonusMap.keySet().stream().filter(Faction::isPlayableAlignmentFaction).filter((fac) -> {
            return (Float)this.factionBonusMap.get(fac) > 0.0F;
         }).sorted(Comparator.comparingDouble((fac) -> {
            return (double)alignData.getAlignment(fac);
         }).reversed()).findFirst();
         if (highestFactionWithBonus.isPresent()) {
            return (Faction)highestFactionWithBonus.get();
         }

         if (this.mainFaction.isPlayableAlignmentFaction() && (Float)this.factionBonusMap.get(this.mainFaction) < 0.0F) {
            return this.mainFaction;
         }

         Optional highestFactionWithPenalty = this.factionBonusMap.keySet().stream().filter(Faction::isPlayableAlignmentFaction).filter((fac) -> {
            return (Float)this.factionBonusMap.get(fac) < 0.0F;
         }).sorted(Comparator.comparingDouble((fac) -> {
            return (double)alignData.getAlignment(fac);
         }).reversed()).findFirst();
         if (highestFactionWithPenalty.isPresent()) {
            return (Faction)highestFactionWithPenalty.get();
         }
      }

      return null;
   }

   public float getAlignmentBonusFor(Faction faction) {
      return (Float)this.factionBonusMap.getOrDefault(faction, 0.0F);
   }

   public float getConquestBonus() {
      return this.conquestBonus;
   }

   public ITextComponent getBonusDisplayText() {
      return this.bonusSource.name;
   }

   public boolean shouldShowBonusText(boolean showAlign, boolean showConquest) {
      return showAlign || showConquest && !this.bonusSource.isKillByHiredUnit;
   }

   public float getBonusAgeF(float f) {
      return ((float)this.particlePrevAge + (float)(this.particleAge - this.particlePrevAge) * f) / (float)this.particleMaxAge;
   }

   protected void func_70088_a() {
   }

   protected void func_70037_a(CompoundNBT nbt) {
   }

   protected void func_213281_b(CompoundNBT nbt) {
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.particlePrevAge = this.particleAge++;
      if (this.particleAge >= this.particleMaxAge) {
         this.func_241204_bJ_();
      }

   }

   protected boolean func_225502_at_() {
      return false;
   }

   public boolean func_190530_aW() {
      return true;
   }

   public boolean func_70104_M() {
      return false;
   }
}
