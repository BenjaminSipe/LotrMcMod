package lotr.common.world.spawning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarBannerBearer;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.entity.npc.LOTREntityAngmarHillmanAxeThrower;
import lotr.common.entity.npc.LOTREntityAngmarHillmanBannerBearer;
import lotr.common.entity.npc.LOTREntityAngmarHillmanWarrior;
import lotr.common.entity.npc.LOTREntityAngmarOrc;
import lotr.common.entity.npc.LOTREntityAngmarOrcArcher;
import lotr.common.entity.npc.LOTREntityAngmarOrcBombardier;
import lotr.common.entity.npc.LOTREntityAngmarWarg;
import lotr.common.entity.npc.LOTREntityAngmarWargBombardier;
import lotr.common.entity.npc.LOTREntityBlackUruk;
import lotr.common.entity.npc.LOTREntityBlackUrukArcher;
import lotr.common.entity.npc.LOTREntityBlackUrukBannerBearer;
import lotr.common.entity.npc.LOTREntityBlackrootArcher;
import lotr.common.entity.npc.LOTREntityBlackrootBannerBearer;
import lotr.common.entity.npc.LOTREntityBlackrootSoldier;
import lotr.common.entity.npc.LOTREntityBlueDwarfAxeThrower;
import lotr.common.entity.npc.LOTREntityBlueDwarfBannerBearer;
import lotr.common.entity.npc.LOTREntityBlueDwarfWarrior;
import lotr.common.entity.npc.LOTREntityBreeBannerBearer;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.entity.npc.LOTREntityCorsair;
import lotr.common.entity.npc.LOTREntityDaleArcher;
import lotr.common.entity.npc.LOTREntityDaleBannerBearer;
import lotr.common.entity.npc.LOTREntityDaleLevyman;
import lotr.common.entity.npc.LOTREntityDaleSoldier;
import lotr.common.entity.npc.LOTREntityDolAmrothArcher;
import lotr.common.entity.npc.LOTREntityDolAmrothBannerBearer;
import lotr.common.entity.npc.LOTREntityDolAmrothSoldier;
import lotr.common.entity.npc.LOTREntityDolGuldurBannerBearer;
import lotr.common.entity.npc.LOTREntityDolGuldurOrc;
import lotr.common.entity.npc.LOTREntityDolGuldurOrcArcher;
import lotr.common.entity.npc.LOTREntityDorwinionBannerBearer;
import lotr.common.entity.npc.LOTREntityDorwinionCrossbower;
import lotr.common.entity.npc.LOTREntityDorwinionElfArcher;
import lotr.common.entity.npc.LOTREntityDorwinionElfBannerBearer;
import lotr.common.entity.npc.LOTREntityDorwinionElfWarrior;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.entity.npc.LOTREntityDunlendingArcher;
import lotr.common.entity.npc.LOTREntityDunlendingAxeThrower;
import lotr.common.entity.npc.LOTREntityDunlendingBannerBearer;
import lotr.common.entity.npc.LOTREntityDunlendingBerserker;
import lotr.common.entity.npc.LOTREntityDunlendingWarrior;
import lotr.common.entity.npc.LOTREntityDwarfAxeThrower;
import lotr.common.entity.npc.LOTREntityDwarfBannerBearer;
import lotr.common.entity.npc.LOTREntityDwarfWarrior;
import lotr.common.entity.npc.LOTREntityEasterlingArcher;
import lotr.common.entity.npc.LOTREntityEasterlingBannerBearer;
import lotr.common.entity.npc.LOTREntityEasterlingFireThrower;
import lotr.common.entity.npc.LOTREntityEasterlingGoldWarrior;
import lotr.common.entity.npc.LOTREntityEasterlingLevyman;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.entity.npc.LOTREntityEsgarothBannerBearer;
import lotr.common.entity.npc.LOTREntityGaladhrimBannerBearer;
import lotr.common.entity.npc.LOTREntityGaladhrimWarrior;
import lotr.common.entity.npc.LOTREntityGondorArcher;
import lotr.common.entity.npc.LOTREntityGondorBannerBearer;
import lotr.common.entity.npc.LOTREntityGondorLevyman;
import lotr.common.entity.npc.LOTREntityGondorRenegade;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.entity.npc.LOTREntityGulfHaradArcher;
import lotr.common.entity.npc.LOTREntityGulfHaradBannerBearer;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.entity.npc.LOTREntityGundabadBannerBearer;
import lotr.common.entity.npc.LOTREntityGundabadOrc;
import lotr.common.entity.npc.LOTREntityGundabadOrcArcher;
import lotr.common.entity.npc.LOTREntityGundabadUruk;
import lotr.common.entity.npc.LOTREntityGundabadUrukArcher;
import lotr.common.entity.npc.LOTREntityGundabadWarg;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.common.entity.npc.LOTREntityHalfTrollBannerBearer;
import lotr.common.entity.npc.LOTREntityHalfTrollWarrior;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import lotr.common.entity.npc.LOTREntityHarnedorBannerBearer;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTREntityHighElfBannerBearer;
import lotr.common.entity.npc.LOTREntityHighElfWarrior;
import lotr.common.entity.npc.LOTREntityHobbitBounder;
import lotr.common.entity.npc.LOTREntityHuorn;
import lotr.common.entity.npc.LOTREntityIsengardSnaga;
import lotr.common.entity.npc.LOTREntityIsengardSnagaArcher;
import lotr.common.entity.npc.LOTREntityLamedonArcher;
import lotr.common.entity.npc.LOTREntityLamedonBannerBearer;
import lotr.common.entity.npc.LOTREntityLamedonHillman;
import lotr.common.entity.npc.LOTREntityLamedonSoldier;
import lotr.common.entity.npc.LOTREntityLebenninBannerBearer;
import lotr.common.entity.npc.LOTREntityLebenninLevyman;
import lotr.common.entity.npc.LOTREntityLossarnachAxeman;
import lotr.common.entity.npc.LOTREntityLossarnachBannerBearer;
import lotr.common.entity.npc.LOTREntityMinasMorgulBannerBearer;
import lotr.common.entity.npc.LOTREntityMirkTroll;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityMordorBannerBearer;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityMordorOrcArcher;
import lotr.common.entity.npc.LOTREntityMordorOrcBombardier;
import lotr.common.entity.npc.LOTREntityMordorSpider;
import lotr.common.entity.npc.LOTREntityMordorWarg;
import lotr.common.entity.npc.LOTREntityMordorWargBombardier;
import lotr.common.entity.npc.LOTREntityMoredainBannerBearer;
import lotr.common.entity.npc.LOTREntityMoredainMercenary;
import lotr.common.entity.npc.LOTREntityMoredainWarrior;
import lotr.common.entity.npc.LOTREntityNanUngolBannerBearer;
import lotr.common.entity.npc.LOTREntityNearHaradBannerBearer;
import lotr.common.entity.npc.LOTREntityNearHaradrimArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityNomadArcher;
import lotr.common.entity.npc.LOTREntityNomadBannerBearer;
import lotr.common.entity.npc.LOTREntityNomadWarrior;
import lotr.common.entity.npc.LOTREntityOlogHai;
import lotr.common.entity.npc.LOTREntityPelargirBannerBearer;
import lotr.common.entity.npc.LOTREntityPelargirMarine;
import lotr.common.entity.npc.LOTREntityPinnathGelinBannerBearer;
import lotr.common.entity.npc.LOTREntityPinnathGelinSoldier;
import lotr.common.entity.npc.LOTREntityRangerIthilien;
import lotr.common.entity.npc.LOTREntityRangerIthilienBannerBearer;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.entity.npc.LOTREntityRangerNorthBannerBearer;
import lotr.common.entity.npc.LOTREntityRivendellBannerBearer;
import lotr.common.entity.npc.LOTREntityRivendellWarrior;
import lotr.common.entity.npc.LOTREntityRohanBannerBearer;
import lotr.common.entity.npc.LOTREntityRohirrimArcher;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTREntitySouthronChampion;
import lotr.common.entity.npc.LOTREntitySwanKnight;
import lotr.common.entity.npc.LOTREntityTauredainBannerBearer;
import lotr.common.entity.npc.LOTREntityTauredainBlowgunner;
import lotr.common.entity.npc.LOTREntityTauredainWarrior;
import lotr.common.entity.npc.LOTREntityUmbarArcher;
import lotr.common.entity.npc.LOTREntityUmbarBannerBearer;
import lotr.common.entity.npc.LOTREntityUmbarWarrior;
import lotr.common.entity.npc.LOTREntityUrukHai;
import lotr.common.entity.npc.LOTREntityUrukHaiBannerBearer;
import lotr.common.entity.npc.LOTREntityUrukHaiBerserker;
import lotr.common.entity.npc.LOTREntityUrukHaiCrossbower;
import lotr.common.entity.npc.LOTREntityUrukHaiSapper;
import lotr.common.entity.npc.LOTREntityUrukWarg;
import lotr.common.entity.npc.LOTREntityUrukWargBombardier;
import lotr.common.entity.npc.LOTREntityWoodElfBannerBearer;
import lotr.common.entity.npc.LOTREntityWoodElfScout;
import lotr.common.entity.npc.LOTREntityWoodElfWarrior;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemConquestHorn;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public enum LOTRInvasions {
   HOBBIT(LOTRFaction.HOBBIT),
   BREE(LOTRFaction.BREE),
   RANGER_NORTH(LOTRFaction.RANGER_NORTH),
   BLUE_MOUNTAINS(LOTRFaction.BLUE_MOUNTAINS),
   HIGH_ELF_LINDON(LOTRFaction.HIGH_ELF, "lindon"),
   HIGH_ELF_RIVENDELL(LOTRFaction.HIGH_ELF, "rivendell"),
   GUNDABAD(LOTRFaction.GUNDABAD),
   GUNDABAD_WARG(LOTRFaction.GUNDABAD, "warg"),
   ANGMAR(LOTRFaction.ANGMAR),
   ANGMAR_HILLMEN(LOTRFaction.ANGMAR, "hillmen"),
   ANGMAR_WARG(LOTRFaction.ANGMAR, "warg"),
   WOOD_ELF(LOTRFaction.WOOD_ELF),
   DOL_GULDUR(LOTRFaction.DOL_GULDUR),
   DALE(LOTRFaction.DALE),
   DWARF(LOTRFaction.DURINS_FOLK),
   GALADHRIM(LOTRFaction.LOTHLORIEN),
   DUNLAND(LOTRFaction.DUNLAND),
   URUK_HAI(LOTRFaction.ISENGARD),
   FANGORN(LOTRFaction.FANGORN),
   ROHAN(LOTRFaction.ROHAN),
   GONDOR(LOTRFaction.GONDOR),
   GONDOR_ITHILIEN(LOTRFaction.GONDOR, "ithilien"),
   GONDOR_DOL_AMROTH(LOTRFaction.GONDOR, "dolAmroth"),
   GONDOR_LOSSARNACH(LOTRFaction.GONDOR, "lossarnach"),
   GONDOR_PELARGIR(LOTRFaction.GONDOR, "pelargir"),
   GONDOR_PINNATH_GELIN(LOTRFaction.GONDOR, "pinnathGelin"),
   GONDOR_BLACKROOT(LOTRFaction.GONDOR, "blackroot"),
   GONDOR_LEBENNIN(LOTRFaction.GONDOR, "lebennin"),
   GONDOR_LAMEDON(LOTRFaction.GONDOR, "lamedon"),
   MORDOR(LOTRFaction.MORDOR),
   MORDOR_BLACK_URUK(LOTRFaction.MORDOR, "blackUruk"),
   MORDOR_NAN_UNGOL(LOTRFaction.MORDOR, "nanUngol"),
   MORDOR_WARG(LOTRFaction.MORDOR, "warg"),
   DORWINION(LOTRFaction.DORWINION),
   DORWINION_ELF(LOTRFaction.DORWINION, "elf"),
   RHUN(LOTRFaction.RHUDEL),
   NEAR_HARAD_HARNEDOR(LOTRFaction.NEAR_HARAD, "harnedor"),
   NEAR_HARAD_COAST(LOTRFaction.NEAR_HARAD, "coast"),
   NEAR_HARAD_UMBAR(LOTRFaction.NEAR_HARAD, "umbar"),
   NEAR_HARAD_CORSAIR(LOTRFaction.NEAR_HARAD, "corsair"),
   NEAR_HARAD_NOMAD(LOTRFaction.NEAR_HARAD, "nomad"),
   NEAR_HARAD_GULF(LOTRFaction.NEAR_HARAD, "gulf"),
   MOREDAIN(LOTRFaction.MORWAITH),
   TAUREDAIN(LOTRFaction.TAURETHRIM),
   HALF_TROLL(LOTRFaction.HALF_TROLL);

   public final LOTRFaction invasionFaction;
   private final String subfaction;
   public List invasionMobs;
   private Item invasionIcon;

   private LOTRInvasions(LOTRFaction f) {
      this(f, (String)null);
   }

   private LOTRInvasions(LOTRFaction f, String s) {
      this.invasionMobs = new ArrayList();
      this.invasionFaction = f;
      this.subfaction = s;
   }

   public String codeName() {
      String s = this.invasionFaction.codeName();
      if (this.subfaction != null) {
         s = s + "_" + this.subfaction;
      }

      return s;
   }

   public List codeNameAndAliases() {
      List aliases = new ArrayList();
      if (this.subfaction != null) {
         String subfactionAdd = "_" + this.subfaction;
         aliases.add(this.invasionFaction.codeName() + subfactionAdd);
         Iterator var3 = this.invasionFaction.listAliases().iterator();

         while(var3.hasNext()) {
            String al = (String)var3.next();
            aliases.add(al + subfactionAdd);
         }
      } else {
         aliases.add(this.invasionFaction.codeName());
         aliases.addAll(this.invasionFaction.listAliases());
      }

      return aliases;
   }

   public String invasionName() {
      return this.subfaction == null ? this.invasionFaction.factionName() : StatCollector.func_74838_a("lotr.invasion." + this.codeName());
   }

   public String codeNameHorn() {
      return "lotr.invasion." + this.codeName() + ".horn";
   }

   public ItemStack getInvasionIcon() {
      Item sword = this.invasionIcon;
      if (sword == null) {
         sword = Items.field_151040_l;
      }

      return new ItemStack(sword);
   }

   public final ItemStack createConquestHorn() {
      ItemStack horn = new ItemStack(LOTRMod.conquestHorn);
      LOTRItemConquestHorn.setInvasionType(horn, this);
      return horn;
   }

   public static void createMobLists() {
      HOBBIT.invasionIcon = LOTRMod.hobbitPipe;
      HOBBIT.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHobbitBounder.class, 15));
      BREE.invasionIcon = LOTRMod.pikeIron;
      BREE.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBreeGuard.class, 15));
      BREE.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBreeBannerBearer.class, 2));
      RANGER_NORTH.invasionIcon = LOTRMod.rangerBow;
      RANGER_NORTH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRangerNorth.class, 15));
      RANGER_NORTH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRangerNorthBannerBearer.class, 2));
      BLUE_MOUNTAINS.invasionIcon = LOTRMod.hammerBlueDwarven;
      BLUE_MOUNTAINS.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlueDwarfWarrior.class, 10));
      BLUE_MOUNTAINS.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlueDwarfAxeThrower.class, 5));
      BLUE_MOUNTAINS.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlueDwarfBannerBearer.class, 2));
      HIGH_ELF_LINDON.invasionIcon = LOTRMod.swordHighElven;
      HIGH_ELF_LINDON.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHighElfWarrior.class, 15));
      HIGH_ELF_LINDON.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHighElfBannerBearer.class, 2));
      HIGH_ELF_RIVENDELL.invasionIcon = LOTRMod.swordRivendell;
      HIGH_ELF_RIVENDELL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRivendellWarrior.class, 15));
      HIGH_ELF_RIVENDELL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRivendellBannerBearer.class, 2));
      GUNDABAD.invasionIcon = LOTRMod.swordGundabadUruk;
      GUNDABAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGundabadOrc.class, 20));
      GUNDABAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGundabadOrcArcher.class, 10));
      GUNDABAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGundabadWarg.class, 20));
      GUNDABAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGundabadBannerBearer.class, 5));
      GUNDABAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGundabadUruk.class, 5));
      GUNDABAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGundabadUrukArcher.class, 2));
      GUNDABAD_WARG.invasionIcon = LOTRMod.wargBone;
      GUNDABAD_WARG.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGundabadWarg.class, 10));
      ANGMAR.invasionIcon = LOTRMod.swordAngmar;
      ANGMAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarOrc.class, 10));
      ANGMAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarOrcArcher.class, 5));
      ANGMAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarOrcBombardier.class, 3));
      ANGMAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarBannerBearer.class, 2));
      ANGMAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarWarg.class, 10));
      ANGMAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarWargBombardier.class, 1));
      ANGMAR_HILLMEN.invasionIcon = LOTRMod.swordBronze;
      ANGMAR_HILLMEN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarHillman.class, 10));
      ANGMAR_HILLMEN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarHillmanWarrior.class, 5));
      ANGMAR_HILLMEN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarHillmanAxeThrower.class, 5));
      ANGMAR_HILLMEN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarHillmanBannerBearer.class, 2));
      ANGMAR_WARG.invasionIcon = LOTRMod.wargBone;
      ANGMAR_WARG.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityAngmarWarg.class, 10));
      WOOD_ELF.invasionIcon = LOTRMod.swordWoodElven;
      WOOD_ELF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityWoodElfWarrior.class, 10));
      WOOD_ELF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityWoodElfScout.class, 5));
      WOOD_ELF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityWoodElfBannerBearer.class, 2));
      DOL_GULDUR.invasionIcon = LOTRMod.swordDolGuldur;
      DOL_GULDUR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMirkwoodSpider.class, 15));
      DOL_GULDUR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDolGuldurOrc.class, 10));
      DOL_GULDUR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDolGuldurOrcArcher.class, 5));
      DOL_GULDUR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDolGuldurBannerBearer.class, 2));
      DOL_GULDUR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMirkTroll.class, 3));
      DALE.invasionIcon = LOTRMod.swordDale;
      DALE.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDaleLevyman.class, 5));
      DALE.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDaleSoldier.class, 10));
      DALE.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDaleArcher.class, 5));
      DALE.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDaleBannerBearer.class, 1));
      DALE.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEsgarothBannerBearer.class, 1));
      DWARF.invasionIcon = LOTRMod.hammerDwarven;
      DWARF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDwarfWarrior.class, 10));
      DWARF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDwarfAxeThrower.class, 5));
      DWARF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDwarfBannerBearer.class, 2));
      GALADHRIM.invasionIcon = LOTRMod.swordElven;
      GALADHRIM.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGaladhrimWarrior.class, 15));
      GALADHRIM.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGaladhrimBannerBearer.class, 2));
      DUNLAND.invasionIcon = LOTRMod.dunlendingClub;
      DUNLAND.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDunlending.class, 10));
      DUNLAND.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDunlendingWarrior.class, 5));
      DUNLAND.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDunlendingArcher.class, 3));
      DUNLAND.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDunlendingAxeThrower.class, 3));
      DUNLAND.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDunlendingBerserker.class, 2));
      DUNLAND.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDunlendingBannerBearer.class, 2));
      URUK_HAI.invasionIcon = LOTRMod.scimitarUruk;
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityIsengardSnaga.class, 5));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityIsengardSnagaArcher.class, 5));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUrukHai.class, 10));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUrukHaiCrossbower.class, 5));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUrukHaiBerserker.class, 5));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUrukHaiSapper.class, 3));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUrukHaiBannerBearer.class, 2));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUrukWarg.class, 10));
      URUK_HAI.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUrukWargBombardier.class, 1));
      FANGORN.invasionIcon = Items.field_151055_y;
      FANGORN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEnt.class, 10));
      FANGORN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHuorn.class, 20));
      ROHAN.invasionIcon = LOTRMod.swordRohan;
      ROHAN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRohirrimWarrior.class, 10));
      ROHAN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRohirrimArcher.class, 5));
      ROHAN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRohanBannerBearer.class, 2));
      GONDOR.invasionIcon = LOTRMod.swordGondor;
      GONDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
      GONDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorSoldier.class, 10));
      GONDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorArcher.class, 5));
      GONDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorBannerBearer.class, 2));
      GONDOR_ITHILIEN.invasionIcon = LOTRMod.gondorBow;
      GONDOR_ITHILIEN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRangerIthilien.class, 15));
      GONDOR_ITHILIEN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityRangerIthilienBannerBearer.class, 2));
      GONDOR_DOL_AMROTH.invasionIcon = LOTRMod.swordDolAmroth;
      GONDOR_DOL_AMROTH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDolAmrothSoldier.class, 10));
      GONDOR_DOL_AMROTH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDolAmrothArcher.class, 5));
      GONDOR_DOL_AMROTH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntitySwanKnight.class, 5));
      GONDOR_DOL_AMROTH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDolAmrothBannerBearer.class, 2));
      GONDOR_LOSSARNACH.invasionIcon = LOTRMod.battleaxeLossarnach;
      GONDOR_LOSSARNACH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
      GONDOR_LOSSARNACH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLossarnachAxeman.class, 15));
      GONDOR_LOSSARNACH.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLossarnachBannerBearer.class, 2));
      GONDOR_PELARGIR.invasionIcon = LOTRMod.tridentPelargir;
      GONDOR_PELARGIR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLebenninLevyman.class, 5));
      GONDOR_PELARGIR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityPelargirMarine.class, 15));
      GONDOR_PELARGIR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityPelargirBannerBearer.class, 2));
      GONDOR_PINNATH_GELIN.invasionIcon = LOTRMod.swordGondor;
      GONDOR_PINNATH_GELIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
      GONDOR_PINNATH_GELIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityPinnathGelinSoldier.class, 15));
      GONDOR_PINNATH_GELIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityPinnathGelinBannerBearer.class, 2));
      GONDOR_BLACKROOT.invasionIcon = LOTRMod.blackrootBow;
      GONDOR_BLACKROOT.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
      GONDOR_BLACKROOT.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackrootSoldier.class, 10));
      GONDOR_BLACKROOT.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackrootArcher.class, 5));
      GONDOR_BLACKROOT.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackrootBannerBearer.class, 2));
      GONDOR_LEBENNIN.invasionIcon = LOTRMod.swordGondor;
      GONDOR_LEBENNIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLebenninLevyman.class, 10));
      GONDOR_LEBENNIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorSoldier.class, 10));
      GONDOR_LEBENNIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorArcher.class, 5));
      GONDOR_LEBENNIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLebenninBannerBearer.class, 2));
      GONDOR_LAMEDON.invasionIcon = LOTRMod.hammerGondor;
      GONDOR_LAMEDON.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLamedonHillman.class, 5));
      GONDOR_LAMEDON.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLamedonSoldier.class, 10));
      GONDOR_LAMEDON.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLamedonArcher.class, 5));
      GONDOR_LAMEDON.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityLamedonBannerBearer.class, 2));
      MORDOR.invasionIcon = LOTRMod.scimitarOrc;
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorOrc.class, 10));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorOrcArcher.class, 5));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorOrcBombardier.class, 2));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorBannerBearer.class, 2));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMinasMorgulBannerBearer.class, 1));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorWarg.class, 10));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorWargBombardier.class, 1));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackUruk.class, 2));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackUrukArcher.class, 1));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackUrukBannerBearer.class, 1));
      MORDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityOlogHai.class, 3));
      MORDOR_BLACK_URUK.invasionIcon = LOTRMod.scimitarBlackUruk;
      MORDOR_BLACK_URUK.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackUruk.class, 10));
      MORDOR_BLACK_URUK.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackUrukArcher.class, 5));
      MORDOR_BLACK_URUK.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityBlackUrukBannerBearer.class, 2));
      MORDOR_NAN_UNGOL.invasionIcon = LOTRMod.scimitarOrc;
      MORDOR_NAN_UNGOL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorOrc.class, 20));
      MORDOR_NAN_UNGOL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorOrcArcher.class, 10));
      MORDOR_NAN_UNGOL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityNanUngolBannerBearer.class, 5));
      MORDOR_NAN_UNGOL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorSpider.class, 30));
      MORDOR_WARG.invasionIcon = LOTRMod.wargBone;
      MORDOR_WARG.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMordorWarg.class, 10));
      DORWINION.invasionIcon = LOTRMod.mugRedWine;
      DORWINION.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDorwinionGuard.class, 10));
      DORWINION.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDorwinionCrossbower.class, 5));
      DORWINION.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDorwinionBannerBearer.class, 2));
      DORWINION_ELF.invasionIcon = LOTRMod.spearBladorthin;
      DORWINION_ELF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDorwinionElfWarrior.class, 10));
      DORWINION_ELF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDorwinionElfArcher.class, 5));
      DORWINION_ELF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityDorwinionElfBannerBearer.class, 2));
      RHUN.invasionIcon = LOTRMod.swordRhun;
      RHUN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEasterlingLevyman.class, 5));
      RHUN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEasterlingWarrior.class, 10));
      RHUN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEasterlingArcher.class, 5));
      RHUN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEasterlingGoldWarrior.class, 5));
      RHUN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEasterlingBannerBearer.class, 5));
      RHUN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityEasterlingFireThrower.class, 3));
      NEAR_HARAD_HARNEDOR.invasionIcon = LOTRMod.swordHarad;
      NEAR_HARAD_HARNEDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHarnedorWarrior.class, 10));
      NEAR_HARAD_HARNEDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHarnedorArcher.class, 5));
      NEAR_HARAD_HARNEDOR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHarnedorBannerBearer.class, 2));
      NEAR_HARAD_COAST.invasionIcon = LOTRMod.scimitarNearHarad;
      NEAR_HARAD_COAST.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityNearHaradrimWarrior.class, 8));
      NEAR_HARAD_COAST.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityNearHaradrimArcher.class, 5));
      NEAR_HARAD_COAST.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityNearHaradBannerBearer.class, 2));
      NEAR_HARAD_COAST.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntitySouthronChampion.class, 2));
      NEAR_HARAD_COAST.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMoredainMercenary.class, 5));
      NEAR_HARAD_UMBAR.invasionIcon = LOTRMod.scimitarNearHarad;
      NEAR_HARAD_UMBAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUmbarWarrior.class, 100));
      NEAR_HARAD_UMBAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUmbarArcher.class, 50));
      NEAR_HARAD_UMBAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityUmbarBannerBearer.class, 20));
      NEAR_HARAD_UMBAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMoredainMercenary.class, 30));
      NEAR_HARAD_UMBAR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGondorRenegade.class, 4));
      NEAR_HARAD_CORSAIR.invasionIcon = LOTRMod.swordCorsair;
      NEAR_HARAD_CORSAIR.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityCorsair.class, 10));
      NEAR_HARAD_NOMAD.invasionIcon = LOTRMod.swordHarad;
      NEAR_HARAD_NOMAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityNomadWarrior.class, 10));
      NEAR_HARAD_NOMAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityNomadArcher.class, 5));
      NEAR_HARAD_NOMAD.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityNomadBannerBearer.class, 2));
      NEAR_HARAD_GULF.invasionIcon = LOTRMod.swordGulfHarad;
      NEAR_HARAD_GULF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGulfHaradWarrior.class, 10));
      NEAR_HARAD_GULF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGulfHaradArcher.class, 5));
      NEAR_HARAD_GULF.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityGulfHaradBannerBearer.class, 2));
      MOREDAIN.invasionIcon = LOTRMod.spearMoredain;
      MOREDAIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMoredainWarrior.class, 15));
      MOREDAIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityMoredainBannerBearer.class, 2));
      TAUREDAIN.invasionIcon = LOTRMod.swordTauredain;
      TAUREDAIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityTauredainWarrior.class, 10));
      TAUREDAIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityTauredainBlowgunner.class, 5));
      TAUREDAIN.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityTauredainBannerBearer.class, 2));
      HALF_TROLL.invasionIcon = LOTRMod.scimitarHalfTroll;
      HALF_TROLL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHalfTroll.class, 10));
      HALF_TROLL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHalfTrollWarrior.class, 10));
      HALF_TROLL.invasionMobs.add(new LOTRInvasions.InvasionSpawnEntry(LOTREntityHalfTrollBannerBearer.class, 2));
   }

   public static LOTRInvasions forName(String name) {
      LOTRInvasions[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRInvasions i = var1[var3];
         List aliases = i.codeNameAndAliases();
         Iterator var6 = aliases.iterator();

         while(var6.hasNext()) {
            String al = (String)var6.next();
            if (al.equals(name)) {
               return i;
            }
         }
      }

      return null;
   }

   public static LOTRInvasions forID(int ID) {
      LOTRInvasions[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRInvasions i = var1[var3];
         if (i.ordinal() == ID) {
            return i;
         }
      }

      return null;
   }

   public static String[] listInvasionNames() {
      String[] names = new String[values().length];

      for(int i = 0; i < names.length; ++i) {
         names[i] = values()[i].codeName();
      }

      return names;
   }

   public static class InvasionSpawnEntry extends net.minecraft.util.WeightedRandom.Item {
      private Class entityClass;

      public InvasionSpawnEntry(Class c, int chance) {
         super(chance);
         this.entityClass = c;
      }

      public Class getEntityClass() {
         return this.entityClass;
      }
   }
}
