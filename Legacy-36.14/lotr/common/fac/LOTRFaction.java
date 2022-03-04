package lotr.common.fac;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRAchievementRank;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTRNPCSelectForInfluence;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public enum LOTRFaction {
   HOBBIT(5885518, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(830, 745, 100), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN)),
   BREE(11373426, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(925, 735, 50), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN)),
   RANGER_NORTH(3823170, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1070, 760, 150), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN)),
   BLUE_MOUNTAINS(6132172, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(650, 600, 125), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_DWARF)),
   HIGH_ELF(13035007, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(570, 770, 200), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_ELF)),
   GUNDABAD(9858132, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1160, 670, 150), EnumSet.of(LOTRFaction.FactionType.TYPE_ORC)),
   ANGMAR(7836023, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1080, 600, 125), EnumSet.of(LOTRFaction.FactionType.TYPE_ORC, LOTRFaction.FactionType.TYPE_TROLL)),
   WOOD_ELF(3774030, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1400, 640, 75), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_ELF)),
   DOL_GULDUR(3488580, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1380, 870, 100), EnumSet.of(LOTRFaction.FactionType.TYPE_ORC)),
   DALE(13535071, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1530, 670, 100), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN)),
   DURINS_FOLK(4940162, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1650, 650, 125), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_DWARF)),
   LOTHLORIEN(15716696, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1230, 900, 75), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_ELF)),
   DUNLAND(11048079, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1090, 1030, 125), EnumSet.of(LOTRFaction.FactionType.TYPE_MAN)),
   ISENGARD(3356723, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1110, 1070, 50), EnumSet.of(LOTRFaction.FactionType.TYPE_ORC)),
   FANGORN(4831058, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1200, 1000, 75), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_TREE)),
   ROHAN(3508007, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1230, 1090, 150), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN)),
   GONDOR(16382457, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1170, 1300, 300), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN)),
   MORDOR(3481375, LOTRDimension.DimensionRegion.WEST, new LOTRMapRegion(1620, 1290, 225), EnumSet.of(LOTRFaction.FactionType.TYPE_ORC)),
   DORWINION(7155816, LOTRDimension.DimensionRegion.EAST, new LOTRMapRegion(1750, 900, 100), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN, LOTRFaction.FactionType.TYPE_ELF)),
   RHUDEL(12882471, LOTRDimension.DimensionRegion.EAST, new LOTRMapRegion(1890, 980, 200), EnumSet.of(LOTRFaction.FactionType.TYPE_MAN)),
   NEAR_HARAD(11868955, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1400, 1730, 375), EnumSet.of(LOTRFaction.FactionType.TYPE_MAN)),
   MORWAITH(14266458, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1400, 2360, 450), EnumSet.of(LOTRFaction.FactionType.TYPE_MAN)),
   TAURETHRIM(3040066, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1250, 2870, 400), EnumSet.of(LOTRFaction.FactionType.TYPE_FREE, LOTRFaction.FactionType.TYPE_MAN)),
   HALF_TROLL(10388339, LOTRDimension.DimensionRegion.SOUTH, new LOTRMapRegion(1900, 2500, 200), EnumSet.of(LOTRFaction.FactionType.TYPE_MAN, LOTRFaction.FactionType.TYPE_TROLL)),
   DARK_HUORN(0, (LOTRDimension)null, (LOTRDimension.DimensionRegion)null, true, true, -1, (LOTRMapRegion)null, (EnumSet)null),
   RUFFIAN(0, (LOTRDimension)null, (LOTRDimension.DimensionRegion)null, true, true, 0, (LOTRMapRegion)null, (EnumSet)null),
   UTUMNO(3343616, LOTRDimension.UTUMNO, -66666, EnumSet.of(LOTRFaction.FactionType.TYPE_ORC)),
   HOSTILE(true, -1),
   UNALIGNED(false, 0);

   private static Random factionRand = new Random();
   public LOTRDimension factionDimension;
   public LOTRDimension.DimensionRegion factionRegion;
   private String factionName;
   private Color factionColor;
   private Map facRGBCache;
   private Set factionTypes;
   public List factionBanners;
   public boolean allowPlayer;
   public boolean allowEntityRegistry;
   public boolean hasFixedAlignment;
   public int fixedAlignment;
   private List ranksSortedDescending;
   private LOTRFactionRank pledgeRank;
   private LOTRAchievement.Category achieveCategory;
   public LOTRMapRegion factionMapInfo;
   private List controlZones;
   public boolean isolationist;
   public boolean approvesWarCrimes;
   private List legacyAliases;
   public static final int CONTROL_ZONE_EXTRA_RANGE = 50;

   private LOTRFaction(int color, LOTRDimension.DimensionRegion region, LOTRMapRegion mapInfo, EnumSet types) {
      this(color, LOTRDimension.MIDDLE_EARTH, region, mapInfo, types);
   }

   private LOTRFaction(int color, LOTRDimension dim, LOTRDimension.DimensionRegion region, LOTRMapRegion mapInfo, EnumSet types) {
      this(color, dim, region, true, true, Integer.MIN_VALUE, mapInfo, types);
   }

   private LOTRFaction(int color, LOTRDimension dim, int alignment, EnumSet types) {
      this(color, dim, (LOTRDimension.DimensionRegion)dim.dimensionRegions.get(0), true, true, alignment, (LOTRMapRegion)null, types);
   }

   private LOTRFaction(boolean registry, int alignment) {
      this(0, (LOTRDimension)null, (LOTRDimension.DimensionRegion)null, false, registry, alignment, (LOTRMapRegion)null, (EnumSet)null);
   }

   private LOTRFaction(int color, LOTRDimension dim, LOTRDimension.DimensionRegion region, boolean player, boolean registry, int alignment, LOTRMapRegion mapInfo, EnumSet types) {
      this.facRGBCache = new HashMap();
      this.factionTypes = new HashSet();
      this.factionBanners = new ArrayList();
      this.ranksSortedDescending = new ArrayList();
      this.controlZones = new ArrayList();
      this.isolationist = false;
      this.approvesWarCrimes = true;
      this.legacyAliases = new ArrayList();
      this.allowPlayer = player;
      this.allowEntityRegistry = registry;
      this.factionColor = new Color(color);
      this.factionDimension = dim;
      if (this.factionDimension != null) {
         this.factionDimension.factionList.add(this);
      }

      this.factionRegion = region;
      if (this.factionRegion != null) {
         this.factionRegion.factionList.add(this);
         if (this.factionRegion.getDimension() != this.factionDimension) {
            throw new IllegalArgumentException("Faction dimension region must agree with faction dimension!");
         }
      }

      if (alignment != Integer.MIN_VALUE) {
         this.setFixedAlignment(alignment);
      }

      if (mapInfo != null) {
         this.factionMapInfo = mapInfo;
      }

      if (types != null) {
         this.factionTypes.addAll(types);
      }

   }

   private void setFixedAlignment(int alignment) {
      this.hasFixedAlignment = true;
      this.fixedAlignment = alignment;
   }

   private void setAchieveCategory(LOTRAchievement.Category cat) {
      this.achieveCategory = cat;
   }

   public LOTRAchievement.Category getAchieveCategory() {
      return this.achieveCategory;
   }

   private LOTRFactionRank addRank(float alignment, String name) {
      return this.addRank(alignment, name, false);
   }

   private LOTRFactionRank addRank(float alignment, String name, boolean gendered) {
      LOTRFactionRank rank = new LOTRFactionRank(this, alignment, name, gendered);
      this.ranksSortedDescending.add(rank);
      Collections.sort(this.ranksSortedDescending);
      return rank;
   }

   public void setPledgeRank(LOTRFactionRank rank) {
      if (rank.fac == this) {
         if (this.pledgeRank == null) {
            this.pledgeRank = rank;
         } else {
            throw new IllegalArgumentException("Faction already has a pledge rank!");
         }
      } else {
         throw new IllegalArgumentException("Incompatible faction!");
      }
   }

   public LOTRFactionRank getPledgeRank() {
      return this.pledgeRank != null ? this.pledgeRank : null;
   }

   public float getPledgeAlignment() {
      return this.pledgeRank != null ? this.pledgeRank.alignment : 0.0F;
   }

   public void checkAlignmentAchievements(EntityPlayer entityplayer, float alignment) {
      LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
      Iterator var4 = this.ranksSortedDescending.iterator();

      while(var4.hasNext()) {
         LOTRFactionRank rank = (LOTRFactionRank)var4.next();
         LOTRAchievementRank rankAch = rank.getRankAchievement();
         if (rankAch != null && rankAch.isPlayerRequiredRank(entityplayer)) {
            playerData.addAchievement(rankAch);
         }
      }

   }

   private void addControlZone(LOTRControlZone zone) {
      this.controlZones.add(zone);
   }

   public List getControlZones() {
      return this.controlZones;
   }

   public static void initAllProperties() {
      LOTRFactionRelations.setDefaultRelations(HOBBIT, BREE, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, RANGER_NORTH, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, BLUE_MOUNTAINS, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, HIGH_ELF, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, WOOD_ELF, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, DALE, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, DURINS_FOLK, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, LOTHLORIEN, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, ROHAN, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, GONDOR, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(BREE, RANGER_NORTH, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(BREE, BLUE_MOUNTAINS, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(BREE, HIGH_ELF, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(BREE, WOOD_ELF, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(BREE, DALE, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(BREE, DURINS_FOLK, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(BREE, LOTHLORIEN, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, HIGH_ELF, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, WOOD_ELF, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, LOTHLORIEN, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, ROHAN, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, GONDOR, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(BLUE_MOUNTAINS, DURINS_FOLK, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, WOOD_ELF, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, LOTHLORIEN, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, FANGORN, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, GONDOR, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, ANGMAR, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, DOL_GULDUR, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, MORDOR, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, DOL_GULDUR, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, MORDOR, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, LOTHLORIEN, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, FANGORN, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, DORWINION, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, MORDOR, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(DALE, DURINS_FOLK, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(DALE, ROHAN, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(DALE, GONDOR, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(DURINS_FOLK, DUNLAND, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(LOTHLORIEN, FANGORN, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(DUNLAND, ISENGARD, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(ISENGARD, HALF_TROLL, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(FANGORN, TAURETHRIM, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(ROHAN, GONDOR, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(MORDOR, RHUDEL, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(MORDOR, NEAR_HARAD, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(MORDOR, MORWAITH, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(MORDOR, HALF_TROLL, LOTRFactionRelations.Relation.ALLY);
      LOTRFactionRelations.setDefaultRelations(NEAR_HARAD, MORWAITH, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(NEAR_HARAD, HALF_TROLL, LOTRFactionRelations.Relation.FRIEND);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HOBBIT, DARK_HUORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BREE, GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BREE, ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BREE, DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BREE, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BREE, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BREE, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BREE, DARK_HUORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, DUNLAND, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, RHUDEL, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, MORWAITH, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(RANGER_NORTH, DARK_HUORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BLUE_MOUNTAINS, GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BLUE_MOUNTAINS, ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BLUE_MOUNTAINS, DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BLUE_MOUNTAINS, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BLUE_MOUNTAINS, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(BLUE_MOUNTAINS, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, GUNDABAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, ANGMAR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, RHUDEL, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(HIGH_ELF, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, WOOD_ELF, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, DALE, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, DURINS_FOLK, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, LOTHLORIEN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GUNDABAD, DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, WOOD_ELF, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, DALE, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, DURINS_FOLK, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, LOTHLORIEN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ANGMAR, DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, DOL_GULDUR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, RHUDEL, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(WOOD_ELF, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, DALE, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, DURINS_FOLK, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, LOTHLORIEN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DOL_GULDUR, DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DALE, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DALE, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DALE, RHUDEL, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(DALE, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DURINS_FOLK, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DURINS_FOLK, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DURINS_FOLK, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(LOTHLORIEN, ISENGARD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(LOTHLORIEN, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(LOTHLORIEN, RHUDEL, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(LOTHLORIEN, NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(LOTHLORIEN, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DUNLAND, ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DUNLAND, GONDOR, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(ISENGARD, FANGORN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ISENGARD, ROHAN, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ISENGARD, GONDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ISENGARD, DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(FANGORN, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(FANGORN, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ROHAN, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ROHAN, RHUDEL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(ROHAN, NEAR_HARAD, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(ROHAN, MORWAITH, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(ROHAN, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GONDOR, MORDOR, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GONDOR, RHUDEL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GONDOR, NEAR_HARAD, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(GONDOR, MORWAITH, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(GONDOR, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(MORDOR, DORWINION, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(MORDOR, TAURETHRIM, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(DORWINION, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(NEAR_HARAD, TAURETHRIM, LOTRFactionRelations.Relation.ENEMY);
      LOTRFactionRelations.setDefaultRelations(MORWAITH, TAURETHRIM, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFactionRelations.setDefaultRelations(TAURETHRIM, HALF_TROLL, LOTRFactionRelations.Relation.MORTAL_ENEMY);
      LOTRFaction[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         LOTRFaction f = var0[var2];
         if (f.allowPlayer && f != UTUMNO) {
            LOTRFactionRelations.setDefaultRelations(f, UTUMNO, LOTRFactionRelations.Relation.MORTAL_ENEMY);
         }
      }

      HOBBIT.approvesWarCrimes = false;
      HOBBIT.isolationist = true;
      HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.BYWATER, 40));
      HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.BUCKLEBURY, 15));
      HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.HAYSEND, 10));
      HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.MICHEL_DELVING, 35));
      HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.GREENHOLM, 10));
      HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.LONGBOTTOM, 30));
      HOBBIT.addControlZone(new LOTRControlZone(LOTRWaypoint.BREE, 15));
      BREE.approvesWarCrimes = false;
      BREE.addControlZone(new LOTRControlZone(LOTRWaypoint.BREE, 25));
      BREE.addControlZone(new LOTRControlZone(LOTRWaypoint.ARCHET, 20));
      BREE.addControlZone(new LOTRControlZone(LOTRWaypoint.FORSAKEN_INN, 15));
      RANGER_NORTH.approvesWarCrimes = false;
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.BYWATER, 110));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.SARN_FORD, 60));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.LAST_BRIDGE, 110));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.BREE, 100));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.ANNUMINAS, 50));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.FORNOST, 50));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GRAM, 100));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.CARN_DUM, 60));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.GREENWAY_CROSSROADS, 60));
      RANGER_NORTH.addControlZone(new LOTRControlZone(LOTRWaypoint.THARBAD, 50));
      BLUE_MOUNTAINS.approvesWarCrimes = false;
      BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(LOTRWaypoint.BELEGOST, 40));
      BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(LOTRWaypoint.NOGROD, 40));
      BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(LOTRWaypoint.THORIN_HALLS, 50));
      BLUE_MOUNTAINS.addControlZone(new LOTRControlZone(695.0D, 820.0D, 80));
      HIGH_ELF.approvesWarCrimes = false;
      HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.MITHLOND_SOUTH, 60));
      HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.FORLOND, 80));
      HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.HARLOND, 80));
      HIGH_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.FORD_BRUINEN, 50));
      GUNDABAD.approvesWarCrimes = true;
      GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GUNDABAD, 200));
      GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GRAM, 200));
      GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.GOBLIN_TOWN, 150));
      GUNDABAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_CARADHRAS, 100));
      ANGMAR.approvesWarCrimes = true;
      ANGMAR.addControlZone(new LOTRControlZone(LOTRWaypoint.CARN_DUM, 75));
      ANGMAR.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GRAM, 125));
      ANGMAR.addControlZone(new LOTRControlZone(LOTRWaypoint.THE_TROLLSHAWS, 50));
      WOOD_ELF.approvesWarCrimes = false;
      WOOD_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.ENCHANTED_RIVER, 75));
      WOOD_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.FOREST_GATE, 20));
      WOOD_ELF.addControlZone(new LOTRControlZone(LOTRWaypoint.DOL_GULDUR, 30));
      DOL_GULDUR.approvesWarCrimes = true;
      DOL_GULDUR.addControlZone(new LOTRControlZone(LOTRWaypoint.DOL_GULDUR, 125));
      DOL_GULDUR.addControlZone(new LOTRControlZone(LOTRWaypoint.ENCHANTED_RIVER, 75));
      DALE.approvesWarCrimes = false;
      DALE.addControlZone(new LOTRControlZone(LOTRWaypoint.DALE_CROSSROADS, 175));
      DURINS_FOLK.approvesWarCrimes = false;
      DURINS_FOLK.addControlZone(new LOTRControlZone(LOTRWaypoint.EREBOR, 75));
      DURINS_FOLK.addControlZone(new LOTRControlZone(LOTRWaypoint.WEST_PEAK, 100));
      DURINS_FOLK.addControlZone(new LOTRControlZone(LOTRWaypoint.EAST_PEAK, 75));
      DURINS_FOLK.addControlZone(new LOTRControlZone(LOTRWaypoint.REDWATER_FORD, 75));
      DURINS_FOLK.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_CARADHRAS, 100));
      DURINS_FOLK.addControlZone(new LOTRControlZone(LOTRWaypoint.MOUNT_GUNDABAD, 100));
      DURINS_FOLK.addControlZone(new LOTRControlZone(LOTRWaypoint.DAINS_HALLS, 50));
      LOTHLORIEN.approvesWarCrimes = false;
      LOTHLORIEN.addControlZone(new LOTRControlZone(LOTRWaypoint.CARAS_GALADHON, 100));
      DUNLAND.approvesWarCrimes = true;
      DUNLAND.addControlZone(new LOTRControlZone(LOTRWaypoint.SOUTH_DUNLAND, 125));
      ISENGARD.approvesWarCrimes = true;
      ISENGARD.addControlZone(new LOTRControlZone(LOTRWaypoint.ISENGARD, 100));
      ISENGARD.addControlZone(new LOTRControlZone(LOTRWaypoint.EDORAS, 50));
      FANGORN.approvesWarCrimes = false;
      FANGORN.isolationist = true;
      FANGORN.addControlZone(new LOTRControlZone(1180.0D, 1005.0D, 70));
      ROHAN.approvesWarCrimes = false;
      ROHAN.addControlZone(new LOTRControlZone(LOTRWaypoint.ENTWADE, 150));
      ROHAN.addControlZone(new LOTRControlZone(LOTRWaypoint.ISENGARD, 100));
      GONDOR.approvesWarCrimes = false;
      GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.MINAS_TIRITH, 200));
      GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.EDHELLOND, 125));
      GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.GREEN_HILLS, 100));
      GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_POROS, 150));
      GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_HARAD, 75));
      GONDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.UMBAR_CITY, 150));
      MORDOR.approvesWarCrimes = true;
      MORDOR.addControlZone(new LOTRControlZone(LOTRWaypoint.BARAD_DUR, 500));
      DORWINION.approvesWarCrimes = false;
      DORWINION.addControlZone(new LOTRControlZone(LOTRWaypoint.DORWINION_COURT, 175));
      DORWINION.addControlZone(new LOTRControlZone(LOTRWaypoint.DALE_PORT, 30));
      RHUDEL.approvesWarCrimes = false;
      RHUDEL.addControlZone(new LOTRControlZone(LOTRWaypoint.RHUN_CAPITAL, 175));
      RHUDEL.addControlZone(new LOTRControlZone(LOTRWaypoint.MINAS_TIRITH, 100));
      RHUDEL.addControlZone(new LOTRControlZone(LOTRWaypoint.DALE_CITY, 50));
      NEAR_HARAD.approvesWarCrimes = false;
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.UMBAR_CITY, 200));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.FERTILE_VALLEY, 150));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.HARNEN_SEA_TOWN, 60));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.HARNEN_RIVER_TOWN, 60));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.DESERT_TOWN, 50));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.SOUTH_DESERT_TOWN, 50));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.GULF_CITY, 150));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_HARAD, 75));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_POROS, 50));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.MINAS_TIRITH, 50));
      NEAR_HARAD.addControlZone(new LOTRControlZone(1210.0D, 1340.0D, 75));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.PELARGIR, 75));
      NEAR_HARAD.addControlZone(new LOTRControlZone(LOTRWaypoint.LINHIR, 75));
      MORWAITH.approvesWarCrimes = true;
      MORWAITH.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_SOUTH, 350));
      MORWAITH.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_WEST, 170));
      MORWAITH.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_EAST, 200));
      MORWAITH.addControlZone(new LOTRControlZone(LOTRWaypoint.GREAT_PLAINS_NORTH, 75));
      TAURETHRIM.approvesWarCrimes = true;
      TAURETHRIM.addControlZone(new LOTRControlZone(LOTRWaypoint.JUNGLE_CITY_CAPITAL, 400));
      TAURETHRIM.addControlZone(new LOTRControlZone(LOTRWaypoint.OLD_JUNGLE_RUIN, 75));
      HALF_TROLL.approvesWarCrimes = true;
      HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.TROLL_ISLAND, 100));
      HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.BLOOD_RIVER, 200));
      HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.SHADOW_POINT, 100));
      HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.CROSSINGS_OF_POROS, 40));
      HALF_TROLL.addControlZone(new LOTRControlZone(LOTRWaypoint.HARADUIN_BRIDGE, 100));
      UTUMNO.approvesWarCrimes = true;
      HOBBIT.setAchieveCategory(LOTRAchievement.Category.SHIRE);
      HOBBIT.addRank(10.0F, "guest").makeAchievement().makeTitle();
      HOBBIT.addRank(100.0F, "friend").makeAchievement().makeTitle().setPledgeRank();
      HOBBIT.addRank(250.0F, "hayward").makeAchievement().makeTitle();
      HOBBIT.addRank(500.0F, "bounder").makeAchievement().makeTitle();
      HOBBIT.addRank(1000.0F, "shirriff").makeAchievement().makeTitle();
      HOBBIT.addRank(2000.0F, "chief").makeAchievement().makeTitle();
      HOBBIT.addRank(3000.0F, "thain").makeAchievement().makeTitle();
      BREE.setAchieveCategory(LOTRAchievement.Category.BREE_LAND);
      BREE.addRank(10.0F, "guest").makeAchievement().makeTitle();
      BREE.addRank(50.0F, "friend").makeAchievement().makeTitle();
      BREE.addRank(100.0F, "townsman").makeAchievement().makeTitle().setPledgeRank();
      BREE.addRank(200.0F, "trustee").makeAchievement().makeTitle();
      BREE.addRank(500.0F, "champion").makeAchievement().makeTitle();
      BREE.addRank(1000.0F, "captain").makeAchievement().makeTitle();
      BREE.addRank(2000.0F, "master").makeAchievement().makeTitle();
      RANGER_NORTH.setAchieveCategory(LOTRAchievement.Category.ERIADOR);
      RANGER_NORTH.addRank(10.0F, "friend").makeAchievement().makeTitle();
      RANGER_NORTH.addRank(50.0F, "warden").makeAchievement().makeTitle();
      RANGER_NORTH.addRank(100.0F, "ranger").makeAchievement().makeTitle().setPledgeRank();
      RANGER_NORTH.addRank(200.0F, "ohtar").makeAchievement().makeTitle();
      RANGER_NORTH.addRank(500.0F, "roquen").makeAchievement().makeTitle();
      RANGER_NORTH.addRank(1000.0F, "champion").makeAchievement().makeTitle();
      RANGER_NORTH.addRank(2000.0F, "captain").makeAchievement().makeTitle();
      BLUE_MOUNTAINS.setAchieveCategory(LOTRAchievement.Category.BLUE_MOUNTAINS);
      BLUE_MOUNTAINS.addRank(10.0F, "guest").makeAchievement().makeTitle();
      BLUE_MOUNTAINS.addRank(50.0F, "friend").makeAchievement().makeTitle();
      BLUE_MOUNTAINS.addRank(100.0F, "warden").makeAchievement().makeTitle().setPledgeRank();
      BLUE_MOUNTAINS.addRank(200.0F, "axebearer").makeAchievement().makeTitle();
      BLUE_MOUNTAINS.addRank(500.0F, "champion").makeAchievement().makeTitle();
      BLUE_MOUNTAINS.addRank(1000.0F, "captain").makeAchievement().makeTitle();
      BLUE_MOUNTAINS.addRank(1500.0F, "noble").makeAchievement().makeTitle();
      BLUE_MOUNTAINS.addRank(3000.0F, "lord", true).makeAchievement().makeTitle();
      HIGH_ELF.setAchieveCategory(LOTRAchievement.Category.LINDON);
      HIGH_ELF.addRank(10.0F, "guest").makeAchievement().makeTitle();
      HIGH_ELF.addRank(50.0F, "friend").makeAchievement().makeTitle();
      HIGH_ELF.addRank(100.0F, "warrior").makeAchievement().makeTitle().setPledgeRank();
      HIGH_ELF.addRank(200.0F, "herald").makeAchievement().makeTitle();
      HIGH_ELF.addRank(500.0F, "captain").makeAchievement().makeTitle();
      HIGH_ELF.addRank(1000.0F, "noble").makeAchievement().makeTitle();
      HIGH_ELF.addRank(2000.0F, "commander").makeAchievement().makeTitle();
      HIGH_ELF.addRank(3000.0F, "lord", true).makeAchievement().makeTitle();
      GUNDABAD.setAchieveCategory(LOTRAchievement.Category.ERIADOR);
      GUNDABAD.addRank(10.0F, "thrall").makeAchievement().makeTitle();
      GUNDABAD.addRank(50.0F, "snaga").makeAchievement().makeTitle();
      GUNDABAD.addRank(100.0F, "raider").makeAchievement().makeTitle().setPledgeRank();
      GUNDABAD.addRank(200.0F, "ravager").makeAchievement().makeTitle();
      GUNDABAD.addRank(500.0F, "scourge").makeAchievement().makeTitle();
      GUNDABAD.addRank(1000.0F, "warlord").makeAchievement().makeTitle();
      GUNDABAD.addRank(2000.0F, "chieftain").makeAchievement().makeTitle();
      ANGMAR.setAchieveCategory(LOTRAchievement.Category.ANGMAR);
      ANGMAR.addRank(10.0F, "thrall").makeAchievement().makeTitle();
      ANGMAR.addRank(50.0F, "servant").makeAchievement().makeTitle();
      ANGMAR.addRank(100.0F, "kinsman").makeAchievement().makeTitle().setPledgeRank();
      ANGMAR.addRank(200.0F, "warrior").makeAchievement().makeTitle();
      ANGMAR.addRank(500.0F, "champion").makeAchievement().makeTitle();
      ANGMAR.addRank(1000.0F, "warlord").makeAchievement().makeTitle();
      ANGMAR.addRank(2000.0F, "chieftain").makeAchievement().makeTitle();
      WOOD_ELF.setAchieveCategory(LOTRAchievement.Category.MIRKWOOD);
      WOOD_ELF.addRank(50.0F, "guest").makeAchievement().makeTitle();
      WOOD_ELF.addRank(100.0F, "friend").makeAchievement().makeTitle().setPledgeRank();
      WOOD_ELF.addRank(200.0F, "guard").makeAchievement().makeTitle();
      WOOD_ELF.addRank(500.0F, "herald").makeAchievement().makeTitle();
      WOOD_ELF.addRank(1000.0F, "captain").makeAchievement().makeTitle();
      WOOD_ELF.addRank(2000.0F, "noble").makeAchievement().makeTitle();
      WOOD_ELF.addRank(3000.0F, "lord", true).makeAchievement().makeTitle();
      DOL_GULDUR.setAchieveCategory(LOTRAchievement.Category.MIRKWOOD);
      DOL_GULDUR.addRank(10.0F, "thrall").makeAchievement().makeTitle();
      DOL_GULDUR.addRank(50.0F, "servant").makeAchievement().makeTitle();
      DOL_GULDUR.addRank(100.0F, "brigand").makeAchievement().makeTitle().setPledgeRank();
      DOL_GULDUR.addRank(200.0F, "torchbearer").makeAchievement().makeTitle();
      DOL_GULDUR.addRank(500.0F, "despoiler").makeAchievement().makeTitle();
      DOL_GULDUR.addRank(1000.0F, "captain").makeAchievement().makeTitle();
      DOL_GULDUR.addRank(2000.0F, "lieutenant").makeAchievement().makeTitle();
      DALE.setAchieveCategory(LOTRAchievement.Category.DALE);
      DALE.addRank(10.0F, "guest").makeAchievement().makeTitle();
      DALE.addRank(50.0F, "friend").makeAchievement().makeTitle();
      DALE.addRank(100.0F, "soldier").makeAchievement().makeTitle().setPledgeRank();
      DALE.addRank(200.0F, "herald").makeAchievement().makeTitle();
      DALE.addRank(500.0F, "captain").makeAchievement().makeTitle();
      DALE.addRank(1000.0F, "marshal").makeAchievement().makeTitle();
      DALE.addRank(2000.0F, "lord", true).makeAchievement().makeTitle();
      DURINS_FOLK.setAchieveCategory(LOTRAchievement.Category.IRON_HILLS);
      DURINS_FOLK.addRank(10.0F, "guest").makeAchievement().makeTitle();
      DURINS_FOLK.addRank(50.0F, "friend").makeAchievement().makeTitle();
      DURINS_FOLK.addRank(100.0F, "oathfriend").makeAchievement().makeTitle().setPledgeRank();
      DURINS_FOLK.addRank(200.0F, "axebearer").makeAchievement().makeTitle();
      DURINS_FOLK.addRank(500.0F, "champion").makeAchievement().makeTitle();
      DURINS_FOLK.addRank(1000.0F, "commander").makeAchievement().makeTitle();
      DURINS_FOLK.addRank(1500.0F, "lord", true).makeAchievement().makeTitle();
      DURINS_FOLK.addRank(3000.0F, "uzbad", true).makeAchievement().makeTitle();
      LOTHLORIEN.setAchieveCategory(LOTRAchievement.Category.LOTHLORIEN);
      LOTHLORIEN.addRank(10.0F, "guest").makeAchievement().makeTitle();
      LOTHLORIEN.addRank(50.0F, "friend").makeAchievement().makeTitle();
      LOTHLORIEN.addRank(100.0F, "warden").makeAchievement().makeTitle().setPledgeRank();
      LOTHLORIEN.addRank(200.0F, "warrior").makeAchievement().makeTitle();
      LOTHLORIEN.addRank(500.0F, "herald", true).makeAchievement().makeTitle();
      LOTHLORIEN.addRank(1000.0F, "captain").makeAchievement().makeTitle();
      LOTHLORIEN.addRank(2000.0F, "noble").makeAchievement().makeTitle();
      LOTHLORIEN.addRank(3000.0F, "lord", true).makeAchievement().makeTitle();
      DUNLAND.setAchieveCategory(LOTRAchievement.Category.DUNLAND);
      DUNLAND.addRank(10.0F, "guest").makeAchievement().makeTitle();
      DUNLAND.addRank(50.0F, "kinsman").makeAchievement().makeTitle();
      DUNLAND.addRank(100.0F, "warrior").makeAchievement().makeTitle().setPledgeRank();
      DUNLAND.addRank(200.0F, "bearer").makeAchievement().makeTitle();
      DUNLAND.addRank(500.0F, "avenger").makeAchievement().makeTitle();
      DUNLAND.addRank(1000.0F, "warlord").makeAchievement().makeTitle();
      DUNLAND.addRank(2000.0F, "chieftain").makeAchievement().makeTitle();
      ISENGARD.setAchieveCategory(LOTRAchievement.Category.ROHAN);
      ISENGARD.addRank(10.0F, "thrall").makeAchievement().makeTitle();
      ISENGARD.addRank(50.0F, "snaga").makeAchievement().makeTitle();
      ISENGARD.addRank(100.0F, "soldier").makeAchievement().makeTitle().setPledgeRank();
      ISENGARD.addRank(200.0F, "treefeller").makeAchievement().makeTitle();
      ISENGARD.addRank(500.0F, "berserker").makeAchievement().makeTitle();
      ISENGARD.addRank(1000.0F, "corporal").makeAchievement().makeTitle();
      ISENGARD.addRank(1500.0F, "hand").makeAchievement().makeTitle();
      ISENGARD.addRank(3000.0F, "captain").makeAchievement().makeTitle();
      FANGORN.setAchieveCategory(LOTRAchievement.Category.FANGORN);
      FANGORN.addRank(10.0F, "newcomer").makeAchievement().makeTitle();
      FANGORN.addRank(50.0F, "friend").makeAchievement().makeTitle();
      FANGORN.addRank(100.0F, "treeherd").makeAchievement().makeTitle().setPledgeRank();
      FANGORN.addRank(250.0F, "master").makeAchievement().makeTitle();
      FANGORN.addRank(500.0F, "elder").makeAchievement().makeTitle();
      ROHAN.setAchieveCategory(LOTRAchievement.Category.ROHAN);
      ROHAN.addRank(10.0F, "guest").makeAchievement().makeTitle();
      ROHAN.addRank(50.0F, "footman").makeAchievement().makeTitle();
      ROHAN.addRank(100.0F, "atarms").makeAchievement().makeTitle().setPledgeRank();
      ROHAN.addRank(250.0F, "rider").makeAchievement().makeTitle();
      ROHAN.addRank(500.0F, "esquire").makeAchievement().makeTitle();
      ROHAN.addRank(1000.0F, "captain").makeAchievement().makeTitle();
      ROHAN.addRank(2000.0F, "marshal").makeAchievement().makeTitle();
      GONDOR.setAchieveCategory(LOTRAchievement.Category.GONDOR);
      GONDOR.addRank(10.0F, "guest").makeAchievement().makeTitle();
      GONDOR.addRank(50.0F, "friend").makeAchievement().makeTitle();
      GONDOR.addRank(100.0F, "atarms").makeAchievement().makeTitle().setPledgeRank();
      GONDOR.addRank(200.0F, "soldier").makeAchievement().makeTitle();
      GONDOR.addRank(500.0F, "knight").makeAchievement().makeTitle();
      GONDOR.addRank(1000.0F, "champion").makeAchievement().makeTitle();
      GONDOR.addRank(1500.0F, "captain").makeAchievement().makeTitle();
      GONDOR.addRank(3000.0F, "lord", true).makeAchievement().makeTitle();
      MORDOR.setAchieveCategory(LOTRAchievement.Category.MORDOR);
      MORDOR.addRank(10.0F, "thrall").makeAchievement().makeTitle();
      MORDOR.addRank(50.0F, "snaga").makeAchievement().makeTitle();
      MORDOR.addRank(100.0F, "brigand").makeAchievement().makeTitle().setPledgeRank();
      MORDOR.addRank(200.0F, "slavedriver").makeAchievement().makeTitle();
      MORDOR.addRank(500.0F, "despoiler").makeAchievement().makeTitle();
      MORDOR.addRank(1000.0F, "captain").makeAchievement().makeTitle();
      MORDOR.addRank(1500.0F, "lieutenant").makeAchievement().makeTitle();
      MORDOR.addRank(3000.0F, "commander").makeAchievement().makeTitle();
      DORWINION.setAchieveCategory(LOTRAchievement.Category.DORWINION);
      DORWINION.addRank(10.0F, "guest").makeAchievement().makeTitle();
      DORWINION.addRank(50.0F, "vinehand").makeAchievement().makeTitle();
      DORWINION.addRank(100.0F, "merchant").makeAchievement().makeTitle().setPledgeRank();
      DORWINION.addRank(200.0F, "guard").makeAchievement().makeTitle();
      DORWINION.addRank(500.0F, "captain").makeAchievement().makeTitle();
      DORWINION.addRank(1000.0F, "master").makeAchievement().makeTitle();
      DORWINION.addRank(1500.0F, "chief").makeAchievement().makeTitle();
      DORWINION.addRank(3000.0F, "lord", true).makeAchievement().makeTitle();
      RHUDEL.setAchieveCategory(LOTRAchievement.Category.RHUN);
      RHUDEL.addRank(10.0F, "bondsman").makeAchievement().makeTitle();
      RHUDEL.addRank(50.0F, "levyman").makeAchievement().makeTitle();
      RHUDEL.addRank(100.0F, "clansman").makeAchievement().makeTitle().setPledgeRank();
      RHUDEL.addRank(200.0F, "warrior").makeAchievement().makeTitle();
      RHUDEL.addRank(500.0F, "champion").makeAchievement().makeTitle();
      RHUDEL.addRank(1000.0F, "golden").makeAchievement().makeTitle();
      RHUDEL.addRank(1500.0F, "warlord").makeAchievement().makeTitle();
      RHUDEL.addRank(3000.0F, "chieftain").makeAchievement().makeTitle();
      NEAR_HARAD.setAchieveCategory(LOTRAchievement.Category.NEAR_HARAD);
      NEAR_HARAD.addRank(10.0F, "guest").makeAchievement().makeTitle();
      NEAR_HARAD.addRank(50.0F, "friend").makeAchievement().makeTitle();
      NEAR_HARAD.addRank(100.0F, "kinsman").makeAchievement().makeTitle().setPledgeRank();
      NEAR_HARAD.addRank(200.0F, "warrior").makeAchievement().makeTitle();
      NEAR_HARAD.addRank(500.0F, "champion").makeAchievement().makeTitle();
      NEAR_HARAD.addRank(1000.0F, "serpentguard").makeAchievement().makeTitle();
      NEAR_HARAD.addRank(1500.0F, "warlord").makeAchievement().makeTitle();
      NEAR_HARAD.addRank(3000.0F, "prince", true).makeAchievement().makeTitle();
      MORWAITH.setAchieveCategory(LOTRAchievement.Category.FAR_HARAD_SAVANNAH);
      MORWAITH.addRank(10.0F, "guest").makeAchievement().makeTitle();
      MORWAITH.addRank(50.0F, "friend").makeAchievement().makeTitle();
      MORWAITH.addRank(100.0F, "kinsman").makeAchievement().makeTitle().setPledgeRank();
      MORWAITH.addRank(250.0F, "hunter").makeAchievement().makeTitle();
      MORWAITH.addRank(500.0F, "warrior").makeAchievement().makeTitle();
      MORWAITH.addRank(1000.0F, "chief").makeAchievement().makeTitle();
      MORWAITH.addRank(3000.0F, "greatchief").makeAchievement().makeTitle();
      TAURETHRIM.setAchieveCategory(LOTRAchievement.Category.FAR_HARAD_JUNGLE);
      TAURETHRIM.addRank(10.0F, "guest").makeAchievement().makeTitle();
      TAURETHRIM.addRank(50.0F, "friend").makeAchievement().makeTitle();
      TAURETHRIM.addRank(100.0F, "forestman").makeAchievement().makeTitle().setPledgeRank();
      TAURETHRIM.addRank(200.0F, "warrior").makeAchievement().makeTitle();
      TAURETHRIM.addRank(500.0F, "champion").makeAchievement().makeTitle();
      TAURETHRIM.addRank(1000.0F, "warlord").makeAchievement().makeTitle();
      TAURETHRIM.addRank(3000.0F, "splendour").makeAchievement().makeTitle();
      HALF_TROLL.setAchieveCategory(LOTRAchievement.Category.PERDOROGWAITH);
      HALF_TROLL.addRank(10.0F, "guest").makeAchievement().makeTitle();
      HALF_TROLL.addRank(50.0F, "scavenger").makeAchievement().makeTitle();
      HALF_TROLL.addRank(100.0F, "kin").makeAchievement().makeTitle().setPledgeRank();
      HALF_TROLL.addRank(200.0F, "warrior").makeAchievement().makeTitle();
      HALF_TROLL.addRank(500.0F, "raider").makeAchievement().makeTitle();
      HALF_TROLL.addRank(1000.0F, "warlord").makeAchievement().makeTitle();
      HALF_TROLL.addRank(2000.0F, "chieftain").makeAchievement().makeTitle();
      DURINS_FOLK.addLegacyAlias("DWARF");
      LOTHLORIEN.addLegacyAlias("GALADHRIM");
      ISENGARD.addLegacyAlias("URUK_HAI");
      RHUDEL.addLegacyAlias("RHUN");
      MORWAITH.addLegacyAlias("MOREDAIN");
      TAURETHRIM.addLegacyAlias("TAUREDAIN");
   }

   public String codeName() {
      return this.name();
   }

   private boolean matchesNameOrAlias(String name) {
      if (this.codeName().equals(name)) {
         return true;
      } else {
         Iterator var2 = this.legacyAliases.iterator();

         String alias;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            alias = (String)var2.next();
         } while(!alias.equals(name));

         return true;
      }
   }

   private void addLegacyAlias(String s) {
      this.legacyAliases.add(s);
   }

   public List listAliases() {
      return new ArrayList(this.legacyAliases);
   }

   public static LOTRFaction forName(String name) {
      LOTRFaction[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRFaction f = var1[var3];
         if (f.matchesNameOrAlias(name)) {
            return f;
         }
      }

      return null;
   }

   public static LOTRFaction forID(int ID) {
      LOTRFaction[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRFaction f = var1[var3];
         if (f.ordinal() == ID) {
            return f;
         }
      }

      return null;
   }

   public String untranslatedFactionName() {
      return "lotr.faction." + this.codeName() + ".name";
   }

   public String factionName() {
      if (LOTRMod.isAprilFools()) {
         String[] names = new String[]{"Britain Stronger in Europe", "Vote Leave"};
         int i = this.ordinal();
         i = (int)((long)i + ((long)i ^ 62341L) + 28703L * ((long)(i * i) ^ 3195015L));
         factionRand.setSeed((long)i);
         List list = Arrays.asList(names);
         Collections.shuffle(list, factionRand);
         return (String)list.get(0);
      } else {
         return StatCollector.func_74838_a(this.untranslatedFactionName());
      }
   }

   public String factionEntityName() {
      return StatCollector.func_74838_a("lotr.faction." + this.codeName() + ".entity");
   }

   public String factionSubtitle() {
      return StatCollector.func_74838_a("lotr.faction." + this.codeName() + ".subtitle");
   }

   public LOTRFactionRank getRank(EntityPlayer entityplayer) {
      return this.getRank(LOTRLevelData.getData(entityplayer));
   }

   public LOTRFactionRank getRank(LOTRPlayerData pd) {
      float alignment = pd.getAlignment(this);
      return this.getRank(alignment);
   }

   public LOTRFactionRank getRank(float alignment) {
      Iterator var2 = this.ranksSortedDescending.iterator();

      LOTRFactionRank rank;
      do {
         if (!var2.hasNext()) {
            if (alignment >= 0.0F) {
               return LOTRFactionRank.RANK_NEUTRAL;
            }

            return LOTRFactionRank.RANK_ENEMY;
         }

         rank = (LOTRFactionRank)var2.next();
      } while(rank.isDummyRank() || alignment < rank.alignment);

      return rank;
   }

   public LOTRFactionRank getRankAbove(LOTRFactionRank curRank) {
      return this.getRankNAbove(curRank, 1);
   }

   public LOTRFactionRank getRankBelow(LOTRFactionRank curRank) {
      return this.getRankNAbove(curRank, -1);
   }

   public LOTRFactionRank getRankNAbove(LOTRFactionRank curRank, int n) {
      if (!this.ranksSortedDescending.isEmpty() && curRank != null) {
         int index = -1;
         if (curRank.isDummyRank()) {
            index = this.ranksSortedDescending.size();
         } else if (this.ranksSortedDescending.contains(curRank)) {
            index = this.ranksSortedDescending.indexOf(curRank);
         }

         if (index >= 0) {
            index -= n;
            if (index < 0) {
               return (LOTRFactionRank)this.ranksSortedDescending.get(0);
            } else {
               return index > this.ranksSortedDescending.size() - 1 ? LOTRFactionRank.RANK_NEUTRAL : (LOTRFactionRank)this.ranksSortedDescending.get(index);
            }
         } else {
            return LOTRFactionRank.RANK_NEUTRAL;
         }
      } else {
         return LOTRFactionRank.RANK_NEUTRAL;
      }
   }

   public LOTRFactionRank getFirstRank() {
      return this.ranksSortedDescending.isEmpty() ? LOTRFactionRank.RANK_NEUTRAL : (LOTRFactionRank)this.ranksSortedDescending.get(this.ranksSortedDescending.size() - 1);
   }

   public int getFactionColor() {
      return this.factionColor.getRGB();
   }

   public float[] getFactionRGB() {
      return this.getFactionRGB_MinBrightness(0.0F);
   }

   public float[] getFactionRGB_MinBrightness(float minBrightness) {
      float[] rgb = (float[])this.facRGBCache.get(minBrightness);
      if (rgb == null) {
         float[] hsb = Color.RGBtoHSB(this.factionColor.getRed(), this.factionColor.getGreen(), this.factionColor.getBlue(), (float[])null);
         hsb[2] = Math.max(hsb[2], minBrightness);
         int alteredColor = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
         rgb = (new Color(alteredColor)).getColorComponents((float[])null);
         this.facRGBCache.put(minBrightness, rgb);
      }

      return rgb;
   }

   public boolean isPlayableAlignmentFaction() {
      return this.allowPlayer && !this.hasFixedAlignment;
   }

   public boolean isGoodRelation(LOTRFaction other) {
      LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
      return rel == LOTRFactionRelations.Relation.ALLY || rel == LOTRFactionRelations.Relation.FRIEND;
   }

   public boolean isAlly(LOTRFaction other) {
      LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
      return rel == LOTRFactionRelations.Relation.ALLY;
   }

   public boolean isNeutral(LOTRFaction other) {
      return LOTRFactionRelations.getRelations(this, other) == LOTRFactionRelations.Relation.NEUTRAL;
   }

   public boolean isBadRelation(LOTRFaction other) {
      LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
      return rel == LOTRFactionRelations.Relation.ENEMY || rel == LOTRFactionRelations.Relation.MORTAL_ENEMY;
   }

   public boolean isMortalEnemy(LOTRFaction other) {
      LOTRFactionRelations.Relation rel = LOTRFactionRelations.getRelations(this, other);
      return rel == LOTRFactionRelations.Relation.MORTAL_ENEMY;
   }

   public List getOthersOfRelation(LOTRFactionRelations.Relation rel) {
      List list = new ArrayList();
      LOTRFaction[] var3 = values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRFaction f = var3[var5];
         if (f != this && f.isPlayableAlignmentFaction() && LOTRFactionRelations.getRelations(this, f) == rel) {
            list.add(f);
         }
      }

      return list;
   }

   public List getBonusesForKilling() {
      List list = new ArrayList();
      LOTRFaction[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRFaction f = var2[var4];
         if (f != this && this.isBadRelation(f)) {
            list.add(f);
         }
      }

      return list;
   }

   public List getPenaltiesForKilling() {
      List list = new ArrayList();
      list.add(this);
      LOTRFaction[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRFaction f = var2[var4];
         if (f != this && this.isGoodRelation(f)) {
            list.add(f);
         }
      }

      return list;
   }

   public List getConquestBoostRelations() {
      List list = new ArrayList();
      LOTRFaction[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRFaction f = var2[var4];
         if (f != this && f.isPlayableAlignmentFaction() && LOTRFactionRelations.getRelations(this, f) == LOTRFactionRelations.Relation.ALLY) {
            list.add(f);
         }
      }

      return list;
   }

   public static boolean controlZonesEnabled(World world) {
      return LOTRLevelData.enableAlignmentZones() && world.func_72912_H().func_76067_t() != LOTRMod.worldTypeMiddleEarthClassic;
   }

   public boolean inControlZone(EntityPlayer entityplayer) {
      return this.inControlZone(entityplayer.field_70170_p, entityplayer.field_70165_t, entityplayer.field_70121_D.field_72338_b, entityplayer.field_70161_v);
   }

   public boolean inControlZone(World world, double d, double d1, double d2) {
      if (this.inDefinedControlZone(world, d, d1, d2)) {
         return true;
      } else {
         double nearbyRange = 24.0D;
         AxisAlignedBB aabb = AxisAlignedBB.func_72330_a(d, d1, d2, d, d1, d2).func_72314_b(nearbyRange, nearbyRange, nearbyRange);
         List nearbyNPCs = world.func_82733_a(EntityLivingBase.class, aabb, new LOTRNPCSelectForInfluence(this));
         return !nearbyNPCs.isEmpty();
      }
   }

   public boolean inDefinedControlZone(EntityPlayer entityplayer) {
      return this.inDefinedControlZone(entityplayer, 0);
   }

   public boolean inDefinedControlZone(EntityPlayer entityplayer, int extraMapRange) {
      return this.inDefinedControlZone(entityplayer.field_70170_p, entityplayer.field_70165_t, entityplayer.field_70121_D.field_72338_b, entityplayer.field_70161_v, extraMapRange);
   }

   public boolean inDefinedControlZone(World world, double d, double d1, double d2) {
      return this.inDefinedControlZone(world, d, d1, d2, 0);
   }

   public boolean inDefinedControlZone(World world, double d, double d1, double d2, int extraMapRange) {
      if (this.isFactionDimension(world)) {
         if (!controlZonesEnabled(world)) {
            return true;
         }

         Iterator var9 = this.controlZones.iterator();

         while(var9.hasNext()) {
            LOTRControlZone zone = (LOTRControlZone)var9.next();
            if (zone.inZone(d, d1, d2, extraMapRange)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isFactionDimension(World world) {
      return world.field_73011_w instanceof LOTRWorldProvider && ((LOTRWorldProvider)world.field_73011_w).getLOTRDimension() == this.factionDimension;
   }

   public int getControlZoneReducedRange() {
      return this.isolationist ? 0 : 50;
   }

   public float getControlZoneAlignmentMultiplier(EntityPlayer entityplayer) {
      if (this.inControlZone(entityplayer)) {
         return 1.0F;
      } else {
         if (this.isFactionDimension(entityplayer.field_70170_p)) {
            int reducedRange = this.getControlZoneReducedRange();
            double dist = this.distanceToNearestControlZoneInRange(entityplayer.field_70170_p, entityplayer.field_70165_t, entityplayer.field_70121_D.field_72338_b, entityplayer.field_70161_v, reducedRange);
            if (dist >= 0.0D) {
               double mapDist = (double)LOTRWaypoint.worldToMapR(dist);
               float frac = (float)mapDist / (float)reducedRange;
               float mplier = 1.0F - frac;
               mplier = MathHelper.func_76131_a(mplier, 0.0F, 1.0F);
               return mplier;
            }
         }

         return 0.0F;
      }
   }

   public double distanceToNearestControlZoneInRange(World world, double d, double d1, double d2, int mapRange) {
      double closestDist = -1.0D;
      if (this.isFactionDimension(world)) {
         int coordRange = LOTRWaypoint.mapToWorldR((double)mapRange);
         Iterator var12 = this.controlZones.iterator();

         while(true) {
            double dToEdge;
            do {
               do {
                  if (!var12.hasNext()) {
                     return closestDist;
                  }

                  LOTRControlZone zone = (LOTRControlZone)var12.next();
                  double dx = d - (double)zone.xCoord;
                  double dz = d2 - (double)zone.zCoord;
                  double dSq = dx * dx + dz * dz;
                  dToEdge = Math.sqrt(dSq) - (double)zone.radiusCoord;
               } while(dToEdge > (double)coordRange);
            } while(closestDist >= 0.0D && dToEdge >= closestDist);

            closestDist = dToEdge;
         }
      } else {
         return closestDist;
      }
   }

   public int[] calculateFullControlZoneWorldBorders() {
      int xMin = 0;
      int xMax = 0;
      int zMin = 0;
      int zMax = 0;
      boolean first = true;
      Iterator var6 = this.controlZones.iterator();

      while(var6.hasNext()) {
         LOTRControlZone zone = (LOTRControlZone)var6.next();
         int cxMin = zone.xCoord - zone.radiusCoord;
         int cxMax = zone.xCoord + zone.radiusCoord;
         int czMin = zone.zCoord - zone.radiusCoord;
         int czMax = zone.zCoord + zone.radiusCoord;
         if (first) {
            xMin = cxMin;
            xMax = cxMax;
            zMin = czMin;
            zMax = czMax;
            first = false;
         } else {
            xMin = Math.min(xMin, cxMin);
            xMax = Math.max(xMax, cxMax);
            zMin = Math.min(zMin, czMin);
            zMax = Math.max(zMax, czMax);
         }
      }

      return new int[]{xMin, xMax, zMin, zMax};
   }

   public boolean sharesControlZoneWith(LOTRFaction other) {
      return this.sharesControlZoneWith(other, 0);
   }

   public boolean sharesControlZoneWith(LOTRFaction other, int extraMapRadius) {
      if (other.factionDimension == this.factionDimension) {
         Iterator var3 = this.controlZones.iterator();

         while(var3.hasNext()) {
            LOTRControlZone zone = (LOTRControlZone)var3.next();
            Iterator var5 = other.controlZones.iterator();

            while(var5.hasNext()) {
               LOTRControlZone otherZone = (LOTRControlZone)var5.next();
               if (zone.intersectsWith(otherZone, extraMapRadius)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static List getPlayableAlignmentFactions() {
      List factions = new ArrayList();
      LOTRFaction[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRFaction f = var1[var3];
         if (f.isPlayableAlignmentFaction()) {
            factions.add(f);
         }
      }

      return factions;
   }

   public static List getPlayableAlignmentFactionNames() {
      List factions = getPlayableAlignmentFactions();
      List names = new ArrayList();
      Iterator var2 = factions.iterator();

      while(var2.hasNext()) {
         LOTRFaction f = (LOTRFaction)var2.next();
         names.add(f.codeName());
      }

      return names;
   }

   public static List getAllRegional(LOTRDimension.DimensionRegion region) {
      List factions = new ArrayList();
      LOTRFaction[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRFaction f = var2[var4];
         if (f.factionRegion == region) {
            factions.add(f);
         }
      }

      return factions;
   }

   public static List getAllRhun() {
      return getAllRegional(LOTRDimension.DimensionRegion.EAST);
   }

   public static List getAllHarad() {
      return getAllRegional(LOTRDimension.DimensionRegion.SOUTH);
   }

   public static List getAllOfType(LOTRFaction.FactionType... types) {
      return getAllOfType(false, types);
   }

   public static List getAllOfType(boolean includeUnplayable, LOTRFaction.FactionType... types) {
      List factions = new ArrayList();
      LOTRFaction[] var3 = values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRFaction f = var3[var5];
         if (includeUnplayable || f.allowPlayer && !f.hasFixedAlignment) {
            boolean match = false;
            LOTRFaction.FactionType[] var8 = types;
            int var9 = types.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               LOTRFaction.FactionType t = var8[var10];
               if (f.isOfType(t)) {
                  match = true;
                  break;
               }
            }

            if (match) {
               factions.add(f);
            }
         }
      }

      return factions;
   }

   public boolean isOfType(LOTRFaction.FactionType type) {
      return this.factionTypes.contains(type);
   }

   public static enum FactionType {
      TYPE_FREE,
      TYPE_ELF,
      TYPE_MAN,
      TYPE_DWARF,
      TYPE_ORC,
      TYPE_TROLL,
      TYPE_TREE;
   }
}
