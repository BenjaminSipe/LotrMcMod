package lotr.common.speech;

import java.util.Optional;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.dim.LOTRDimensionType;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.Faction;
import lotr.common.fac.RankGender;
import lotr.common.init.LOTRBiomes;
import lotr.common.speech.condition.BiomeWithTags;
import lotr.common.speech.condition.OptionallyUnderspecifiedFactionRank;
import lotr.curuquesta.SpeechbankContextProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class NPCSpeechbankContext implements SpeechbankContextProvider {
   private final NPCEntity npc;
   private final PlayerEntity player;

   public NPCSpeechbankContext(NPCEntity npc, PlayerEntity player) {
      this.npc = npc;
      this.player = player;
   }

   public NPCEntity getNPC() {
      return this.npc;
   }

   public BlockPos getNPCPosition() {
      return this.npc.func_233580_cy_();
   }

   public PlayerEntity getPlayer() {
      return this.player;
   }

   public World getWorld() {
      return this.npc.func_130014_f_();
   }

   public Biome getNPCBiome() {
      return this.getWorld().func_226691_t_(this.getNPCPosition());
   }

   public BiomeWithTags getNPCBiomeWithTags() {
      Biome biome = this.getNPCBiome();
      ResourceLocation biomeName = LOTRBiomes.getBiomeRegistryName(biome, this.getWorld());
      boolean isHomeBiome = this.getNPCFaction().isSpeechbankHomeBiome(biomeName);
      return new BiomeWithTags(biomeName, isHomeBiome);
   }

   public boolean isUnderground() {
      World world = this.getWorld();
      BlockPos pos = this.getNPCPosition();
      return pos.func_177956_o() < world.func_181545_F() && !world.func_175710_j(pos);
   }

   public boolean isLunarEclipse() {
      World world = this.getWorld();
      DimensionType dimType = world.func_230315_m_();
      return dimType instanceof LOTRDimensionType && ((LOTRDimensionType)dimType).isLunarEclipse(world);
   }

   private Faction getNPCFaction() {
      return this.npc.getFaction();
   }

   public float getPlayerAlignmentWithNPCFaction() {
      return this.getAlignmentData().getAlignment(this.getNPCFaction());
   }

   private LOTRPlayerData getPlayerData() {
      return LOTRLevelData.sidedInstance((IWorldReader)this.getWorld()).getData(this.player);
   }

   private AlignmentDataModule getAlignmentData() {
      return this.getPlayerData().getAlignmentData();
   }

   public OptionallyUnderspecifiedFactionRank getPlayerRankWithNPCFaction() {
      return OptionallyUnderspecifiedFactionRank.fullySpecified(this.getNPCFaction().getRankFor(this.getPlayerAlignmentWithNPCFaction()));
   }

   private Faction getPledgeFaction() {
      return this.getAlignmentData().getPledgeFaction();
   }

   public ResourceLocation getPledgeFactionName() {
      return (ResourceLocation)Optional.ofNullable(this.getPledgeFaction()).map(Faction::getName).orElse((Object)null);
   }

   public SpeechEnums.PledgeRelation getPledgeFactionRelation() {
      Faction pledgeFac = this.getPledgeFaction();
      if (pledgeFac == null) {
         return SpeechEnums.PledgeRelation.NONE;
      } else if (pledgeFac == this.getNPCFaction()) {
         return SpeechEnums.PledgeRelation.THIS;
      } else if (pledgeFac.isGoodRelation(this.getNPCFaction())) {
         return SpeechEnums.PledgeRelation.GOOD;
      } else {
         return pledgeFac.isBadRelation(this.getNPCFaction()) ? SpeechEnums.PledgeRelation.BAD : SpeechEnums.PledgeRelation.NEUTRAL;
      }
   }

   public RankGender getPreferredRankGender() {
      return this.getPlayerData().getMiscData().getPreferredRankGender();
   }

   public float getPlayerHungerLevel() {
      return (float)this.player.func_71024_bL().func_75116_a() / 20.0F;
   }

   public boolean isNPCDrunk() {
      return this.npc.isDrunk();
   }

   public boolean isPlayerDrunk() {
      return this.player.func_70644_a(Effects.field_76431_k);
   }
}
