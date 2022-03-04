package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.common.entity.animal.LOTREntityZebra;

public class LOTRUnitTradeEntries {
   public static LOTRUnitTradeEntries MORDOR_ORC_MERCENARY_CAPTAIN;
   public static LOTRUnitTradeEntries GONDORIAN_CAPTAIN;
   public static LOTRUnitTradeEntries DWARF_COMMANDER;
   public static LOTRUnitTradeEntries URUK_HAI_MERCENARY_CAPTAIN;
   public static LOTRUnitTradeEntries ELF_LORD;
   public static LOTRUnitTradeEntries ROHIRRIM_MARSHAL;
   public static LOTRUnitTradeEntries HOBBIT_SHIRRIFF;
   public static LOTRUnitTradeEntries DUNLENDING_WARLORD;
   public static LOTRUnitTradeEntries WOOD_ELF_CAPTAIN;
   public static LOTRUnitTradeEntries ANGMAR_ORC_MERCENARY_CAPTAIN;
   public static LOTRUnitTradeEntries MORDOR_ORC_SLAVER;
   public static LOTRUnitTradeEntries MORDOR_ORC_SPIDER_KEEPER;
   public static LOTRUnitTradeEntries GUNDABAD_ORC_MERCENARY_CAPTAIN;
   public static LOTRUnitTradeEntries RANGER_NORTH_CAPTAIN;
   public static LOTRUnitTradeEntries HOBBIT_FARMER;
   public static LOTRUnitTradeEntries HIGH_ELF_LORD;
   public static LOTRUnitTradeEntries NEAR_HARADRIM_WARLORD;
   public static LOTRUnitTradeEntries BLUE_DWARF_COMMANDER;
   public static LOTRUnitTradeEntries DOL_GULDUR_CAPTAIN;
   public static LOTRUnitTradeEntries RANGER_ITHILIEN_CAPTAIN;
   public static LOTRUnitTradeEntries HALF_TROLL_WARLORD;
   public static LOTRUnitTradeEntries DOL_AMROTH_CAPTAIN;
   public static LOTRUnitTradeEntries MOREDAIN_CHIEFTAIN;
   public static LOTRUnitTradeEntries ANGMAR_HILLMAN_CHIEFTAIN;
   public static LOTRUnitTradeEntries TAUREDAIN_CHIEFTAIN;
   public static LOTRUnitTradeEntries TAUREDAIN_FARMER;
   public static LOTRUnitTradeEntries DALE_CAPTAIN;
   public static LOTRUnitTradeEntries DORWINION_CAPTAIN;
   public static LOTRUnitTradeEntries DORWINION_ELF_CAPTAIN;
   public static LOTRUnitTradeEntries DORWINION_VINEKEEPER;
   public static LOTRUnitTradeEntries LOSSARNACH_CAPTAIN;
   public static LOTRUnitTradeEntries PELARGIR_CAPTAIN;
   public static LOTRUnitTradeEntries PINNATH_GELIN_CAPTAIN;
   public static LOTRUnitTradeEntries BLACKROOT_CAPTAIN;
   public static LOTRUnitTradeEntries GONDOR_FARMER;
   public static LOTRUnitTradeEntries LEBENNIN_CAPTAIN;
   public static LOTRUnitTradeEntries LAMEDON_CAPTAIN;
   public static LOTRUnitTradeEntries ROHAN_FARMER;
   public static LOTRUnitTradeEntries EASTERLING_WARLORD;
   public static LOTRUnitTradeEntries EASTERLING_FARMER;
   public static LOTRUnitTradeEntries RIVENDELL_LORD;
   public static LOTRUnitTradeEntries HARNEDOR_WARLORD;
   public static LOTRUnitTradeEntries UMBAR_CAPTAIN;
   public static LOTRUnitTradeEntries CORSAIR_CAPTAIN;
   public static LOTRUnitTradeEntries NOMAD_WARLORD;
   public static LOTRUnitTradeEntries GULF_WARLORD;
   public static LOTRUnitTradeEntries CORSAIR_SLAVER;
   public static LOTRUnitTradeEntries HARNEDOR_FARMER;
   public static LOTRUnitTradeEntries BLACK_URUK_CAPTAIN;
   public static LOTRUnitTradeEntries BREE_CAPTAIN;
   public static LOTRUnitTradeEntries BREE_FARMER;
   public LOTRUnitTradeEntry[] tradeEntries;
   public static final int MOREDAIN_MERCENARY_COST = 20;

   public LOTRUnitTradeEntries(float baseAlignment, LOTRUnitTradeEntry... trades) {
      this.tradeEntries = trades;
      LOTRUnitTradeEntry[] var3 = this.tradeEntries;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRUnitTradeEntry trade = var3[var5];
         trade.alignmentRequired += baseAlignment;
         if (trade.alignmentRequired < 0.0F) {
            throw new IllegalArgumentException("Units cannot require negative alignment!");
         }
      }

   }

   static {
      MORDOR_ORC_MERCENARY_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityMordorOrc.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityMordorOrcArcher.class, 40, 50.0F), new LOTRUnitTradeEntry(LOTREntityMordorOrcBombardier.class, 50, 100.0F), new LOTRUnitTradeEntry(LOTREntityMordorWarg.class, 20, 0.0F), (new LOTRUnitTradeEntry(LOTREntityMordorOrc.class, LOTREntityMordorWarg.class, "MordorOrc_Warg", 40, 100.0F)).setMountArmor(LOTRMod.wargArmorMordor, 0.5F), (new LOTRUnitTradeEntry(LOTREntityMordorOrcArcher.class, LOTREntityMordorWarg.class, "MordorOrcArcher_Warg", 60, 150.0F)).setMountArmor(LOTRMod.wargArmorMordor, 0.5F), new LOTRUnitTradeEntry(LOTREntityMordorWargBombardier.class, 50, 250.0F), (new LOTRUnitTradeEntry(LOTREntityOlogHai.class, 120, 350.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityMordorBannerBearer.class, 40, 150.0F), new LOTRUnitTradeEntry(LOTREntityMinasMorgulBannerBearer.class, 40, 150.0F)});
      GONDORIAN_CAPTAIN = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityGondorLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityGondorSoldier.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityGondorArcher.class, 50, 100.0F), (new LOTRUnitTradeEntry(LOTREntityGondorSoldier.class, LOTREntityHorse.class, "GondorSoldier_Horse", 50, 150.0F)).setMountArmor(LOTRMod.horseArmorGondor), (new LOTRUnitTradeEntry(LOTREntityGondorTowerGuard.class, 50, 250.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityGondorBannerBearer.class, 50, 200.0F)});
      DWARF_COMMANDER = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityDwarf.class, 20, 0.0F), (new LOTRUnitTradeEntry(LOTREntityDwarfWarrior.class, 30, 50.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityDwarfAxeThrower.class, 50, 100.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityDwarfWarrior.class, LOTREntityWildBoar.class, "DwarfWarrior_Boar", 50, 150.0F)).setMountArmor(LOTRMod.boarArmorDwarven).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityDwarfAxeThrower.class, LOTREntityWildBoar.class, "DwarfAxeThrower_Boar", 70, 200.0F)).setMountArmor(LOTRMod.boarArmorDwarven).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityDwarfBannerBearer.class, 50, 200.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF)});
      URUK_HAI_MERCENARY_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityIsengardSnaga.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityIsengardSnagaArcher.class, 40, 50.0F), new LOTRUnitTradeEntry(LOTREntityUrukHai.class, 40, 0.0F), new LOTRUnitTradeEntry(LOTREntityUrukHaiCrossbower.class, 60, 50.0F), new LOTRUnitTradeEntry(LOTREntityUrukHaiSapper.class, 70, 100.0F), (new LOTRUnitTradeEntry(LOTREntityUrukHaiBerserker.class, 60, 150.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityUrukWarg.class, 20, 0.0F), (new LOTRUnitTradeEntry(LOTREntityIsengardSnaga.class, LOTREntityUrukWarg.class, "IsengardSnaga_Warg", 40, 100.0F)).setMountArmor(LOTRMod.wargArmorUruk, 0.5F), (new LOTRUnitTradeEntry(LOTREntityIsengardSnagaArcher.class, LOTREntityUrukWarg.class, "IsengardSnagaArcher_Warg", 60, 150.0F)).setMountArmor(LOTRMod.wargArmorUruk, 0.5F), new LOTRUnitTradeEntry(LOTREntityUrukWargBombardier.class, 50, 250.0F), new LOTRUnitTradeEntry(LOTREntityUrukHaiBannerBearer.class, 60, 150.0F)});
      ELF_LORD = new LOTRUnitTradeEntries(300.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityGaladhrimElf.class, 30, 0.0F), (new LOTRUnitTradeEntry(LOTREntityGaladhrimWarden.class, 40, 50.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityGaladhrimWarrior.class, 50, 100.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityGaladhrimWarrior.class, LOTREntityHorse.class, "GaladhrimWarrior_Horse", 70, 200.0F)).setMountArmor(LOTRMod.horseArmorGaladhrim).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityGaladhrimBannerBearer.class, 70, 250.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF)});
      ROHIRRIM_MARSHAL = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityRohirrimWarrior.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityRohirrimArcher.class, 50, 50.0F), (new LOTRUnitTradeEntry(LOTREntityRohirrimWarrior.class, LOTREntityHorse.class, "Rohirrim_Horse", 50, 100.0F)).setMountArmor(LOTRMod.horseArmorRohan), (new LOTRUnitTradeEntry(LOTREntityRohirrimArcher.class, LOTREntityHorse.class, "RohirrimArcher_Horse", 70, 150.0F)).setMountArmor(LOTRMod.horseArmorRohan), new LOTRUnitTradeEntry(LOTREntityRohanBannerBearer.class, 50, 150.0F)});
      HOBBIT_SHIRRIFF = new LOTRUnitTradeEntries(50.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityHobbitBounder.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityHobbitBounder.class, LOTREntityShirePony.class, "HobbitBounder_Pony", 40, 50.0F)});
      DUNLENDING_WARLORD = new LOTRUnitTradeEntries(100.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityDunlending.class, 15, 0.0F), new LOTRUnitTradeEntry(LOTREntityDunlendingWarrior.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityDunlendingArcher.class, 50, 100.0F), new LOTRUnitTradeEntry(LOTREntityDunlendingAxeThrower.class, 50, 100.0F), (new LOTRUnitTradeEntry(LOTREntityDunlendingBerserker.class, 50, 200.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityDunlendingBannerBearer.class, 50, 200.0F)});
      WOOD_ELF_CAPTAIN = new LOTRUnitTradeEntries(250.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityWoodElf.class, 30, 0.0F), (new LOTRUnitTradeEntry(LOTREntityWoodElfScout.class, 40, 50.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityWoodElfWarrior.class, 50, 100.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityWoodElfWarrior.class, LOTREntityElk.class, "WoodElfWarrior_Elk", 70, 200.0F)).setMountArmor(LOTRMod.elkArmorWoodElven).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityWoodElfBannerBearer.class, 70, 250.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF)});
      ANGMAR_ORC_MERCENARY_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityAngmarOrc.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityAngmarOrcArcher.class, 40, 50.0F), new LOTRUnitTradeEntry(LOTREntityAngmarOrcBombardier.class, 50, 100.0F), new LOTRUnitTradeEntry(LOTREntityAngmarWarg.class, 20, 0.0F), (new LOTRUnitTradeEntry(LOTREntityAngmarOrc.class, LOTREntityAngmarWarg.class, "AngmarOrc_Warg", 40, 100.0F)).setMountArmor(LOTRMod.wargArmorAngmar, 0.5F), (new LOTRUnitTradeEntry(LOTREntityAngmarOrcArcher.class, LOTREntityAngmarWarg.class, "AngmarOrcArcher_Warg", 60, 150.0F)).setMountArmor(LOTRMod.wargArmorAngmar, 0.5F), new LOTRUnitTradeEntry(LOTREntityAngmarWargBombardier.class, 50, 250.0F), new LOTRUnitTradeEntry(LOTREntityTroll.class, 100, 250.0F), (new LOTRUnitTradeEntry(LOTREntityMountainTroll.class, 120, 350.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityAngmarBannerBearer.class, 40, 150.0F)});
      MORDOR_ORC_SLAVER = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityNurnSlave.class, 40, 0.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      MORDOR_ORC_SPIDER_KEEPER = new LOTRUnitTradeEntries(250.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityMordorSpider.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityMordorOrc.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityMordorOrcArcher.class, 40, 0.0F), new LOTRUnitTradeEntry(LOTREntityMordorOrc.class, LOTREntityMordorSpider.class, "MordorOrc_Spider", 50, 50.0F), new LOTRUnitTradeEntry(LOTREntityMordorOrcArcher.class, LOTREntityMordorSpider.class, "MordorOrcArcher_Spider", 70, 100.0F), new LOTRUnitTradeEntry(LOTREntityNanUngolBannerBearer.class, 40, 150.0F)});
      GUNDABAD_ORC_MERCENARY_CAPTAIN = new LOTRUnitTradeEntries(100.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityGundabadOrc.class, 15, 0.0F), new LOTRUnitTradeEntry(LOTREntityGundabadOrcArcher.class, 35, 50.0F), new LOTRUnitTradeEntry(LOTREntityGundabadWarg.class, 20, 50.0F), new LOTRUnitTradeEntry(LOTREntityGundabadOrc.class, LOTREntityGundabadWarg.class, "GundabadOrc_Warg", 35, 100.0F), new LOTRUnitTradeEntry(LOTREntityGundabadOrcArcher.class, LOTREntityGundabadWarg.class, "GundabadOrcArcher_Warg", 55, 150.0F), (new LOTRUnitTradeEntry(LOTREntityGundabadUruk.class, 40, 250.0F)).setPledgeExclusive(), (new LOTRUnitTradeEntry(LOTREntityGundabadUrukArcher.class, 60, 300.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityGundabadBannerBearer.class, 35, 150.0F)});
      RANGER_NORTH_CAPTAIN = new LOTRUnitTradeEntries(300.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityRangerNorth.class, 50, 0.0F), new LOTRUnitTradeEntry(LOTREntityRangerNorth.class, LOTREntityHorse.class, "RangerNorth_Horse", 70, 100.0F), new LOTRUnitTradeEntry(LOTREntityRangerNorthBannerBearer.class, 70, 150.0F)});
      HOBBIT_FARMER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityHobbitFarmhand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      HIGH_ELF_LORD = new LOTRUnitTradeEntries(300.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityHighElf.class, 30, 0.0F), (new LOTRUnitTradeEntry(LOTREntityHighElfWarrior.class, 50, 100.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityHighElfWarrior.class, LOTREntityHorse.class, "HighElfWarrior_Horse", 70, 200.0F)).setMountArmor(LOTRMod.horseArmorHighElven).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityHighElfBannerBearer.class, 70, 250.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF)});
      NEAR_HARADRIM_WARLORD = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityNearHaradrimWarrior.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityNearHaradrimArcher.class, 50, 50.0F), (new LOTRUnitTradeEntry(LOTREntitySouthronChampion.class, LOTREntityHorse.class, "SouthronChampion_Horse", 60, 100.0F)).setMountArmor(LOTRMod.horseArmorNearHarad).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityNearHaradBannerBearer.class, 50, 150.0F)});
      BLUE_DWARF_COMMANDER = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityBlueDwarf.class, 20, 0.0F), (new LOTRUnitTradeEntry(LOTREntityBlueDwarfWarrior.class, 30, 50.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityBlueDwarfAxeThrower.class, 50, 100.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityBlueDwarfWarrior.class, LOTREntityWildBoar.class, "BlueDwarfWarrior_Boar", 50, 150.0F)).setMountArmor(LOTRMod.boarArmorBlueDwarven).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityBlueDwarfAxeThrower.class, LOTREntityWildBoar.class, "BlueDwarfAxeThrower_Boar", 70, 200.0F)).setMountArmor(LOTRMod.boarArmorBlueDwarven).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF), (new LOTRUnitTradeEntry(LOTREntityBlueDwarfBannerBearer.class, 50, 200.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_DWARF)});
      DOL_GULDUR_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityDolGuldurOrc.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityDolGuldurOrcArcher.class, 40, 50.0F), new LOTRUnitTradeEntry(LOTREntityMirkwoodSpider.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityDolGuldurOrc.class, LOTREntityMirkwoodSpider.class, "DolGuldurOrc_Spider", 40, 100.0F), new LOTRUnitTradeEntry(LOTREntityDolGuldurOrcArcher.class, LOTREntityMirkwoodSpider.class, "DolGuldurOrcArcher_Spider", 60, 150.0F), (new LOTRUnitTradeEntry(LOTREntityMirkTroll.class, 100, 350.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityDolGuldurBannerBearer.class, 40, 0.0F)});
      RANGER_ITHILIEN_CAPTAIN = new LOTRUnitTradeEntries(300.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityRangerIthilien.class, 50, 0.0F), new LOTRUnitTradeEntry(LOTREntityRangerIthilienBannerBearer.class, 70, 150.0F)});
      HALF_TROLL_WARLORD = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityHalfTroll.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityHalfTrollWarrior.class, 50, 100.0F), (new LOTRUnitTradeEntry(LOTREntityHalfTrollWarrior.class, LOTREntityRhino.class, "HalfTrollWarrior_Rhino", 70, 200.0F)).setMountArmor(LOTRMod.rhinoArmorHalfTroll, 0.5F), new LOTRUnitTradeEntry(LOTREntityHalfTrollBannerBearer.class, 70, 150.0F)});
      DOL_AMROTH_CAPTAIN = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityDolAmrothSoldier.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityDolAmrothArcher.class, 50, 50.0F), (new LOTRUnitTradeEntry(LOTREntitySwanKnight.class, 50, 100.0F)).setPledgeExclusive(), (new LOTRUnitTradeEntry(LOTREntityDolAmrothSoldier.class, LOTREntityHorse.class, "DolAmrothSoldier_Horse", 50, 100.0F)).setMountArmor(LOTRMod.horseArmorDolAmroth, 0.5F), (new LOTRUnitTradeEntry(LOTREntitySwanKnight.class, LOTREntityHorse.class, "SwanKnight_Horse", 70, 200.0F)).setMountArmor(LOTRMod.horseArmorDolAmroth).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityDolAmrothBannerBearer.class, 50, 150.0F)});
      MOREDAIN_CHIEFTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityMoredainWarrior.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityMoredainWarrior.class, LOTREntityZebra.class, "MoredainWarrior_Zebra", 40, 100.0F), new LOTRUnitTradeEntry(LOTREntityMoredainBannerBearer.class, 40, 150.0F)});
      ANGMAR_HILLMAN_CHIEFTAIN = new LOTRUnitTradeEntries(100.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityAngmarHillman.class, 15, 0.0F), new LOTRUnitTradeEntry(LOTREntityAngmarHillmanWarrior.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityAngmarHillmanAxeThrower.class, 50, 100.0F), new LOTRUnitTradeEntry(LOTREntityAngmarHillman.class, LOTREntityAngmarWarg.class, "AngmarHillman_Warg", 35, 100.0F), (new LOTRUnitTradeEntry(LOTREntityAngmarHillmanWarrior.class, LOTREntityAngmarWarg.class, "AngmarHillmanWarrior_Warg", 50, 150.0F)).setMountArmor(LOTRMod.wargArmorAngmar, 0.3F), (new LOTRUnitTradeEntry(LOTREntityAngmarHillmanAxeThrower.class, LOTREntityAngmarWarg.class, "AngmarHillmanAxeThrower_Warg", 70, 200.0F)).setMountArmor(LOTRMod.wargArmorAngmar, 0.3F), new LOTRUnitTradeEntry(LOTREntityAngmarHillmanBannerBearer.class, 50, 200.0F)});
      TAUREDAIN_CHIEFTAIN = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityTauredainWarrior.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityTauredainBlowgunner.class, 50, 50.0F), new LOTRUnitTradeEntry(LOTREntityTauredainBannerBearer.class, 50, 150.0F)});
      TAUREDAIN_FARMER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityTauredainFarmhand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      DALE_CAPTAIN = new LOTRUnitTradeEntries(100.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityDaleLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityDaleSoldier.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityDaleArcher.class, 50, 100.0F), (new LOTRUnitTradeEntry(LOTREntityDaleSoldier.class, LOTREntityHorse.class, "DaleSoldier_Horse", 50, 150.0F)).setMountArmor(LOTRMod.horseArmorDale), new LOTRUnitTradeEntry(LOTREntityDaleBannerBearer.class, 50, 200.0F), new LOTRUnitTradeEntry(LOTREntityEsgarothBannerBearer.class, 50, 200.0F)});
      DORWINION_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityDorwinionGuard.class, 40, 0.0F), new LOTRUnitTradeEntry(LOTREntityDorwinionCrossbower.class, 60, 50.0F), new LOTRUnitTradeEntry(LOTREntityDorwinionBannerBearer.class, 60, 150.0F)});
      DORWINION_ELF_CAPTAIN = new LOTRUnitTradeEntries(250.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityDorwinionElfWarrior.class, 50, 0.0F), new LOTRUnitTradeEntry(LOTREntityDorwinionElfArcher.class, 70, 50.0F), new LOTRUnitTradeEntry(LOTREntityDorwinionElfBannerBearer.class, 70, 150.0F)});
      DORWINION_VINEKEEPER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityDorwinionVinehand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      LOSSARNACH_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityGondorLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityLossarnachAxeman.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityLossarnachBannerBearer.class, 50, 200.0F)});
      PELARGIR_CAPTAIN = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityLebenninLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityPelargirMarine.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityPelargirBannerBearer.class, 50, 200.0F)});
      PINNATH_GELIN_CAPTAIN = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityGondorLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityPinnathGelinSoldier.class, 30, 50.0F), (new LOTRUnitTradeEntry(LOTREntityPinnathGelinSoldier.class, LOTREntityHorse.class, "PinnathGelinSoldier_Horse", 50, 150.0F)).setMountArmor(LOTRMod.horseArmorGondor), new LOTRUnitTradeEntry(LOTREntityPinnathGelinBannerBearer.class, 50, 200.0F)});
      BLACKROOT_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityGondorLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityBlackrootSoldier.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityBlackrootArcher.class, 50, 100.0F), (new LOTRUnitTradeEntry(LOTREntityBlackrootSoldier.class, LOTREntityHorse.class, "BlackrootSoldier_Horse", 50, 150.0F)).setMountArmor(LOTRMod.horseArmorGondor), new LOTRUnitTradeEntry(LOTREntityBlackrootBannerBearer.class, 50, 200.0F)});
      GONDOR_FARMER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityGondorFarmhand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      LEBENNIN_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityLebenninLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityGondorSoldier.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityGondorArcher.class, 50, 100.0F), new LOTRUnitTradeEntry(LOTREntityLebenninBannerBearer.class, 40, 150.0F)});
      LAMEDON_CAPTAIN = new LOTRUnitTradeEntries(200.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityLamedonHillman.class, 15, 0.0F), new LOTRUnitTradeEntry(LOTREntityLamedonSoldier.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityLamedonArcher.class, 50, 100.0F), (new LOTRUnitTradeEntry(LOTREntityLamedonSoldier.class, LOTREntityHorse.class, "LamedonSoldier_Horse", 50, 150.0F)).setMountArmor(LOTRMod.horseArmorLamedon), new LOTRUnitTradeEntry(LOTREntityLamedonBannerBearer.class, 50, 200.0F)});
      ROHAN_FARMER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityRohanFarmhand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      EASTERLING_WARLORD = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityEasterlingLevyman.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityEasterlingWarrior.class, 30, 50.0F), new LOTRUnitTradeEntry(LOTREntityEasterlingArcher.class, 50, 100.0F), (new LOTRUnitTradeEntry(LOTREntityEasterlingGoldWarrior.class, 50, 200.0F)).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityEasterlingWarrior.class, LOTREntityHorse.class, "EasterlingWarrior_Horse", 50, 150.0F), new LOTRUnitTradeEntry(LOTREntityEasterlingArcher.class, LOTREntityHorse.class, "EasterlingArcher_Horse", 70, 200.0F), (new LOTRUnitTradeEntry(LOTREntityEasterlingGoldWarrior.class, LOTREntityHorse.class, "EasterlingGoldWarrior_Horse", 70, 300.0F)).setMountArmor(LOTRMod.horseArmorRhunGold).setPledgeExclusive(), new LOTRUnitTradeEntry(LOTREntityEasterlingFireThrower.class, 60, 150.0F), new LOTRUnitTradeEntry(LOTREntityEasterlingBannerBearer.class, 50, 200.0F)});
      EASTERLING_FARMER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityEasterlingFarmhand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      RIVENDELL_LORD = new LOTRUnitTradeEntries(300.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityRivendellElf.class, 30, 0.0F), (new LOTRUnitTradeEntry(LOTREntityRivendellWarrior.class, 50, 100.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityRivendellWarrior.class, LOTREntityHorse.class, "RivendellWarrior_Horse", 70, 200.0F)).setMountArmor(LOTRMod.horseArmorRivendell).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF), (new LOTRUnitTradeEntry(LOTREntityRivendellBannerBearer.class, 70, 250.0F)).setPledgeType(LOTRUnitTradeEntry.PledgeType.ANY_ELF)});
      HARNEDOR_WARLORD = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityHarnedorWarrior.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityHarnedorArcher.class, 40, 50.0F), new LOTRUnitTradeEntry(LOTREntityHarnedorWarrior.class, LOTREntityHorse.class, "HarnedorWarrior_Horse", 40, 100.0F), new LOTRUnitTradeEntry(LOTREntityHarnedorArcher.class, LOTREntityHorse.class, "HarnedorArcher_Horse", 60, 150.0F), new LOTRUnitTradeEntry(LOTREntityHarnedorBannerBearer.class, 40, 150.0F)});
      UMBAR_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityUmbarWarrior.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityUmbarArcher.class, 50, 50.0F), (new LOTRUnitTradeEntry(LOTREntityUmbarWarrior.class, LOTREntityHorse.class, "UmbarWarrior_Horse", 50, 100.0F)).setMountArmor(LOTRMod.horseArmorUmbar), new LOTRUnitTradeEntry(LOTREntityUmbarBannerBearer.class, 50, 150.0F)});
      CORSAIR_CAPTAIN = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityCorsair.class, 20, 0.0F)).setExtraInfo("Corsair")});
      NOMAD_WARLORD = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityNomadWarrior.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityNomadArcher.class, 40, 50.0F), new LOTRUnitTradeEntry(LOTREntityNomadWarrior.class, LOTREntityCamel.class, "NomadWarrior_Camel", 40, 100.0F), new LOTRUnitTradeEntry(LOTREntityNomadArcher.class, LOTREntityCamel.class, "NomadArcher_Camel", 60, 150.0F), new LOTRUnitTradeEntry(LOTREntityNomadBannerBearer.class, 40, 150.0F)});
      GULF_WARLORD = new LOTRUnitTradeEntries(150.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityGulfHaradWarrior.class, 30, 0.0F), new LOTRUnitTradeEntry(LOTREntityGulfHaradArcher.class, 50, 50.0F), new LOTRUnitTradeEntry(LOTREntityGulfHaradWarrior.class, LOTREntityHorse.class, "GulfWarrior_Horse", 50, 100.0F), new LOTRUnitTradeEntry(LOTREntityGulfHaradArcher.class, LOTREntityHorse.class, "GulfArcher_Horse", 70, 150.0F), new LOTRUnitTradeEntry(LOTREntityGulfHaradBannerBearer.class, 50, 150.0F)});
      CORSAIR_SLAVER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityHaradSlave.class, 40, 0.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      HARNEDOR_FARMER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityHarnedorFarmhand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
      BLACK_URUK_CAPTAIN = new LOTRUnitTradeEntries(400.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityBlackUruk.class, 60, 250.0F)).setPledgeExclusive(), (new LOTRUnitTradeEntry(LOTREntityBlackUrukArcher.class, 80, 300.0F)).setPledgeExclusive(), (new LOTRUnitTradeEntry(LOTREntityOlogHai.class, 120, 350.0F)).setPledgeExclusive(), (new LOTRUnitTradeEntry(LOTREntityBlackUrukBannerBearer.class, 80, 400.0F)).setPledgeExclusive()});
      BREE_CAPTAIN = new LOTRUnitTradeEntries(100.0F, new LOTRUnitTradeEntry[]{new LOTRUnitTradeEntry(LOTREntityBreeGuard.class, 20, 0.0F), new LOTRUnitTradeEntry(LOTREntityBreeBannerBearer.class, 40, 150.0F)});
      BREE_FARMER = new LOTRUnitTradeEntries(0.0F, new LOTRUnitTradeEntry[]{(new LOTRUnitTradeEntry(LOTREntityBreeFarmhand.class, 40, 50.0F)).setTask(LOTRHiredNPCInfo.Task.FARMER)});
   }
}