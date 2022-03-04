package lotr.common.world.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public enum LOTRWaypoint implements LOTRAbstractWaypoint {
   HIMLING(LOTRWaypoint.Region.OCEAN, LOTRFaction.UNALIGNED, 485.0D, 523.0D),
   TOL_FUIN(LOTRWaypoint.Region.OCEAN, LOTRFaction.UNALIGNED, 357.0D, 542.0D),
   TOL_MORWEN(LOTRWaypoint.Region.OCEAN, LOTRFaction.UNALIGNED, 87.0D, 698.0D),
   MENELTARMA_SUMMIT(LOTRWaypoint.Region.MENELTARMA, LOTRFaction.UNALIGNED, 64.0D, 1733.0D, true),
   HOBBITON(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 815.0D, 727.0D),
   BRANDYWINE_BRIDGE(LOTRWaypoint.Region.SHIRE, LOTRFaction.UNALIGNED, 853.0D, 725.0D),
   SARN_FORD(LOTRWaypoint.Region.SHIRE, LOTRFaction.UNALIGNED, 883.0D, 802.0D),
   LONGBOTTOM(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 820.0D, 765.0D),
   MICHEL_DELVING(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 796.0D, 739.0D),
   WILLOWBOTTOM(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 845.0D, 752.0D),
   BUCKLEBURY(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 857.0D, 734.0D),
   WHITFURROWS(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 843.0D, 727.0D),
   FROGMORTON(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 831.0D, 728.0D),
   OATBARTON(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 822.0D, 701.0D),
   SCARY(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 840.0D, 713.0D),
   NEEDLEHOLE(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 806.0D, 708.0D),
   LITTLE_DELVING(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 785.0D, 718.0D),
   WAYMEET(LOTRWaypoint.Region.SHIRE, LOTRFaction.UNALIGNED, 807.0D, 733.0D),
   TUCKBOROUGH(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 815.0D, 741.0D),
   NOBOTTLE(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 797.0D, 710.0D),
   TIGHFIELD(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 778.0D, 712.0D),
   BROCKENBORINGS(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 833.0D, 710.0D),
   DEEPHALLOW(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 850.0D, 749.0D),
   STOCK(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 849.0D, 737.0D),
   BYWATER(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 820.0D, 730.0D),
   OVERHILL(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 817.0D, 720.0D),
   HAYSEND(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 858.0D, 747.0D),
   HAY_GATE(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 856.0D, 728.0D),
   GREENHOLM(LOTRWaypoint.Region.SHIRE, LOTRFaction.HOBBIT, 762.0D, 745.0D),
   WITHYWINDLE_VALLEY(LOTRWaypoint.Region.OLD_FOREST, LOTRFaction.UNALIGNED, 881.0D, 749.0D),
   FORLOND(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 526.0D, 718.0D),
   HARLOND(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 605.0D, 783.0D),
   MITHLOND_NORTH(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 669.0D, 717.0D),
   MITHLOND_SOUTH(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 679.0D, 729.0D),
   FORLINDON(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 532.0D, 638.0D),
   HARLINDON(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 611.0D, 878.0D),
   TOWER_HILLS(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 710.0D, 742.0D),
   AMON_EREB(LOTRWaypoint.Region.LINDON, LOTRFaction.HIGH_ELF, 500.0D, 708.0D),
   BELEGOST(LOTRWaypoint.Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 622.0D, 600.0D),
   NOGROD(LOTRWaypoint.Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 626.0D, 636.0D),
   MOUNT_RERIR(LOTRWaypoint.Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 592.0D, 525.0D),
   MOUNT_DOLMED(LOTRWaypoint.Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 599.0D, 627.0D),
   THORIN_HALLS(LOTRWaypoint.Region.BLUE_MOUNTAINS, LOTRFaction.BLUE_MOUNTAINS, 641.0D, 671.0D),
   ARVEDUI_MINES(LOTRWaypoint.Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 663.0D, 489.0D),
   THRAIN_HALLS(LOTRWaypoint.Region.BLUE_MOUNTAINS, LOTRFaction.BLUE_MOUNTAINS, 669.0D, 793.0D),
   NORTH_DOWNS(LOTRWaypoint.Region.ERIADOR, LOTRFaction.UNALIGNED, 930.0D, 626.0D),
   SOUTH_DOWNS(LOTRWaypoint.Region.ERIADOR, LOTRFaction.UNALIGNED, 960.0D, 768.0D),
   ERYN_VORN(LOTRWaypoint.Region.ERIADOR, LOTRFaction.UNALIGNED, 747.0D, 957.0D),
   THARBAD(LOTRWaypoint.Region.ERIADOR, LOTRFaction.UNALIGNED, 979.0D, 878.0D),
   FORNOST(LOTRWaypoint.Region.ERIADOR, LOTRFaction.UNALIGNED, 897.0D, 652.0D),
   ANNUMINAS(LOTRWaypoint.Region.ERIADOR, LOTRFaction.UNALIGNED, 814.0D, 661.0D),
   GREENWAY_CROSSROADS(LOTRWaypoint.Region.ERIADOR, LOTRFaction.UNALIGNED, 920.0D, 810.0D),
   BREE(LOTRWaypoint.Region.BREE_LAND, LOTRFaction.BREE, 915.0D, 734.0D),
   STADDLE(LOTRWaypoint.Region.BREE_LAND, LOTRFaction.UNALIGNED, 924.0D, 734.0D),
   COMBE(LOTRWaypoint.Region.BREE_LAND, LOTRFaction.UNALIGNED, 927.0D, 731.0D),
   ARCHET(LOTRWaypoint.Region.BREE_LAND, LOTRFaction.UNALIGNED, 928.0D, 728.0D),
   FORSAKEN_INN(LOTRWaypoint.Region.BREE_LAND, LOTRFaction.UNALIGNED, 950.0D, 743.0D),
   WEATHERTOP(LOTRWaypoint.Region.LONE_LANDS, LOTRFaction.UNALIGNED, 998.0D, 723.0D),
   LAST_BRIDGE(LOTRWaypoint.Region.LONE_LANDS, LOTRFaction.UNALIGNED, 1088.0D, 714.0D),
   OLD_ELF_WAY(LOTRWaypoint.Region.LONE_LANDS, LOTRFaction.UNALIGNED, 1028.0D, 847.0D),
   RIVENDELL(LOTRWaypoint.Region.RIVENDELL_VALE, LOTRFaction.HIGH_ELF, 1173.0D, 721.0D),
   FORD_BRUINEN(LOTRWaypoint.Region.RIVENDELL_VALE, LOTRFaction.HIGH_ELF, 1163.0D, 723.0D),
   THE_TROLLSHAWS(LOTRWaypoint.Region.TROLLSHAWS, LOTRFaction.UNALIGNED, 1130.0D, 703.0D),
   CARN_DUM(LOTRWaypoint.Region.ANGMAR, LOTRFaction.ANGMAR, 1010.0D, 503.0D),
   WEST_GATE(LOTRWaypoint.Region.EREGION, LOTRFaction.UNALIGNED, 1134.0D, 873.0D),
   OST_IN_EDHIL(LOTRWaypoint.Region.EREGION, LOTRFaction.UNALIGNED, 1112.0D, 870.0D),
   NORTH_DUNLAND(LOTRWaypoint.Region.DUNLAND, LOTRFaction.DUNLAND, 1073.0D, 946.0D),
   SOUTH_DUNLAND(LOTRWaypoint.Region.DUNLAND, LOTRFaction.DUNLAND, 1070.0D, 1027.0D),
   FORDS_OF_ISEN(LOTRWaypoint.Region.DUNLAND, LOTRFaction.UNALIGNED, 1102.0D, 1087.0D),
   DWARROWVALE(LOTRWaypoint.Region.DUNLAND, LOTRFaction.UNALIGNED, 1080.0D, 990.0D),
   WULFBURG(LOTRWaypoint.Region.DUNLAND, LOTRFaction.UNALIGNED, 1077.0D, 1098.0D),
   LOND_DAER(LOTRWaypoint.Region.ENEDWAITH, LOTRFaction.UNALIGNED, 867.0D, 1004.0D),
   ENEDWAITH_ROAD(LOTRWaypoint.Region.ENEDWAITH, LOTRFaction.UNALIGNED, 1025.0D, 1050.0D),
   MOUTHS_ISEN(LOTRWaypoint.Region.ENEDWAITH, LOTRFaction.UNALIGNED, 871.0D, 1127.0D),
   ISENGARD(LOTRWaypoint.Region.NAN_CURUNIR, LOTRFaction.ISENGARD, 1102.0D, 1061.5D),
   CAPE_OF_FOROCHEL(LOTRWaypoint.Region.FORODWAITH, LOTRFaction.UNALIGNED, 786.0D, 390.0D),
   SOUTH_FOROCHEL(LOTRWaypoint.Region.FORODWAITH, LOTRFaction.UNALIGNED, 825.0D, 459.0D),
   WITHERED_HEATH(LOTRWaypoint.Region.FORODWAITH, LOTRFaction.UNALIGNED, 1441.0D, 556.0D),
   MOUNT_GUNDABAD(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.GUNDABAD, 1195.0D, 592.0D),
   MOUNT_GRAM(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1106.0D, 589.0D),
   HIGH_PASS(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1222.0D, 706.0D),
   MOUNT_CARADHRAS(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1166.0D, 845.0D),
   MOUNT_CELEBDIL(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1158.0D, 864.0D),
   MOUNT_FANUIDHOL(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1191.0D, 854.0D),
   MOUNT_METHEDRAS(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1111.0D, 1031.0D),
   GOBLIN_TOWN(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.GUNDABAD, 1220.0D, 696.0D),
   EAGLES_EYRIE(LOTRWaypoint.Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1246.0D, 685.0D),
   DAINS_HALLS(LOTRWaypoint.Region.GREY_MOUNTAINS, LOTRFaction.UNALIGNED, 1262.0D, 554.0D),
   SCATHA(LOTRWaypoint.Region.GREY_MOUNTAINS, LOTRFaction.UNALIGNED, 1335.0D, 562.0D),
   CARROCK(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1281.0D, 681.0D),
   OLD_FORD(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1284.0D, 702.0D),
   GLADDEN_FIELDS(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1294.0D, 790.0D),
   DIMRILL_DALE(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1177.0D, 864.0D),
   FIELD_OF_CELEBRANT(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1281.0D, 960.0D),
   RAUROS(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1345.0D, 1130.0D),
   BEORN(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1302.0D, 680.0D),
   FOREST_GATE(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1303.0D, 655.0D),
   FRAMSBURG(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1251.0D, 590.0D),
   ANDUIN_CROSSROADS(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1285.0D, 905.0D),
   EAST_RHOVANION_ROAD(LOTRWaypoint.Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1354.0D, 966.0D),
   THRANDUIL_HALLS(LOTRWaypoint.Region.WOODLAND_REALM, LOTRFaction.WOOD_ELF, 1420.0D, 633.0D),
   DOL_GULDUR(LOTRWaypoint.Region.MIRKWOOD, LOTRFaction.DOL_GULDUR, 1339.0D, 894.0D),
   MIRKWOOD_MOUNTAINS(LOTRWaypoint.Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1430.0D, 672.0D),
   RHOSGOBEL(LOTRWaypoint.Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1343.0D, 762.0D),
   ENCHANTED_RIVER(LOTRWaypoint.Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1396.0D, 650.0D),
   RIVER_GATE(LOTRWaypoint.Region.WILDERLAND, LOTRFaction.UNALIGNED, 1474.0D, 696.0D),
   EAST_BIGHT(LOTRWaypoint.Region.WILDERLAND, LOTRFaction.UNALIGNED, 1437.0D, 824.0D),
   OLD_RHOVANION(LOTRWaypoint.Region.WILDERLAND, LOTRFaction.UNALIGNED, 1524.0D, 870.0D),
   DORWINION_CROSSROADS(LOTRWaypoint.Region.WILDERLAND, LOTRFaction.UNALIGNED, 1680.0D, 882.0D),
   EREBOR(LOTRWaypoint.Region.DALE, LOTRFaction.DURINS_FOLK, 1463.0D, 609.0D),
   DALE_CITY(LOTRWaypoint.Region.DALE, LOTRFaction.DALE, 1464.0D, 615.0D),
   LONG_LAKE(LOTRWaypoint.Region.DALE, LOTRFaction.DALE, 1461.0D, 632.0D),
   RUNNING_FORD(LOTRWaypoint.Region.DALE, LOTRFaction.UNALIGNED, 1534.0D, 749.0D),
   REDWATER_FORD(LOTRWaypoint.Region.DALE, LOTRFaction.UNALIGNED, 1651.0D, 690.0D),
   DALE_CROSSROADS(LOTRWaypoint.Region.DALE, LOTRFaction.UNALIGNED, 1567.0D, 680.0D),
   DALE_PORT(LOTRWaypoint.Region.DALE, LOTRFaction.UNALIGNED, 1657.0D, 768.0D),
   WEST_PEAK(LOTRWaypoint.Region.IRON_HILLS, LOTRFaction.UNALIGNED, 1588.0D, 608.0D),
   EAST_PEAK(LOTRWaypoint.Region.IRON_HILLS, LOTRFaction.UNALIGNED, 1729.0D, 610.0D),
   CARAS_GALADHON(LOTRWaypoint.Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1242.0D, 902.0D),
   CERIN_AMROTH(LOTRWaypoint.Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1230.0D, 897.0D),
   NIMRODEL(LOTRWaypoint.Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1198.0D, 894.0D),
   DERNDINGLE(LOTRWaypoint.Region.FANGORN, LOTRFaction.FANGORN, 1163.0D, 1030.0D),
   WELLINGHALL(LOTRWaypoint.Region.FANGORN, LOTRFaction.FANGORN, 1153.0D, 1014.0D),
   TREEBEARD_HILL(LOTRWaypoint.Region.FANGORN, LOTRFaction.FANGORN, 1200.0D, 1030.0D),
   WOLD(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1285.0D, 1015.0D),
   EDORAS(LOTRWaypoint.Region.ROHAN, LOTRFaction.ROHAN, 1190.0D, 1148.0D),
   HELMS_DEEP(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1128.0D, 1115.0D),
   HELMS_CROSSROADS(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1136.0D, 1108.0D),
   URUK_HIGHLANDS(LOTRWaypoint.Region.ROHAN, LOTRFaction.ISENGARD, 1131.0D, 1057.0D),
   MERING_STREAM(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1299.0D, 1202.0D),
   ENTWADE(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1239.0D, 1104.0D),
   EASTMARK(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1286.0D, 1130.0D),
   ALDBURG(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1223.0D, 1178.0D),
   GRIMSLADE(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1153.0D, 1122.0D),
   CORSAIRS_LANDING(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 992.0D, 1113.0D),
   FRECA_HOLD(LOTRWaypoint.Region.ROHAN, LOTRFaction.UNALIGNED, 1099.0D, 1144.0D),
   DUNHARROW(LOTRWaypoint.Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 1175.0D, 1154.0D),
   TARLANG(LOTRWaypoint.Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 1205.0D, 1213.0D),
   RAS_MORTHIL(LOTRWaypoint.Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 845.0D, 1332.0D),
   MINAS_TIRITH(LOTRWaypoint.Region.GONDOR, LOTRFaction.GONDOR, 1419.0D, 1247.0D),
   CAIR_ANDROS(LOTRWaypoint.Region.GONDOR, LOTRFaction.GONDOR, 1427.0D, 1207.0D),
   HALIFIRIEN(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1309.0D, 1205.0D),
   CALENHAD(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1330.0D, 1212.0D),
   MINRIMMON(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1350.0D, 1219.0D),
   ERELAS(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1367.0D, 1222.0D),
   NARDOL(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1384.0D, 1228.0D),
   EILENACH(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1402.0D, 1228.0D),
   AMON_DIN(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1416.0D, 1231.0D),
   OSGILIATH_WEST(LOTRWaypoint.Region.GONDOR, LOTRFaction.UNALIGNED, 1428.0D, 1246.0D),
   OSGILIATH_EAST(LOTRWaypoint.Region.ITHILIEN, LOTRFaction.UNALIGNED, 1435.0D, 1246.0D),
   EMYN_ARNEN(LOTRWaypoint.Region.ITHILIEN, LOTRFaction.UNALIGNED, 1437.0D, 1267.0D),
   HENNETH_ANNUN(LOTRWaypoint.Region.ITHILIEN, LOTRFaction.GONDOR, 1443.0D, 1192.0D),
   CROSSROADS_ITHILIEN(LOTRWaypoint.Region.ITHILIEN, LOTRFaction.UNALIGNED, 1450.0D, 1236.0D),
   NORTH_ITHILIEN(LOTRWaypoint.Region.ITHILIEN, LOTRFaction.UNALIGNED, 1447.0D, 1151.0D),
   CROSSINGS_OF_POROS(LOTRWaypoint.Region.ITHILIEN, LOTRFaction.UNALIGNED, 1442.0D, 1370.0D),
   PELARGIR(LOTRWaypoint.Region.LEBENNIN, LOTRFaction.GONDOR, 1390.0D, 1348.0D),
   LINHIR(LOTRWaypoint.Region.LEBENNIN, LOTRFaction.UNALIGNED, 1292.0D, 1342.0D),
   ANDUIN_MOUTH(LOTRWaypoint.Region.LEBENNIN, LOTRFaction.UNALIGNED, 1273.0D, 1369.0D),
   IMLOTH_MELUI(LOTRWaypoint.Region.LOSSARNACH, LOTRFaction.UNALIGNED, 1397.0D, 1254.0D),
   CROSSINGS_ERUI(LOTRWaypoint.Region.LOSSARNACH, LOTRFaction.UNALIGNED, 1412.0D, 1272.0D),
   CALEMBEL(LOTRWaypoint.Region.LAMEDON, LOTRFaction.GONDOR, 1235.0D, 1248.0D),
   ETHRING(LOTRWaypoint.Region.LAMEDON, LOTRFaction.UNALIGNED, 1256.0D, 1259.0D),
   ERECH(LOTRWaypoint.Region.BLACKROOT_VALE, LOTRFaction.GONDOR, 1186.0D, 1205.0D),
   GREEN_HILLS(LOTRWaypoint.Region.PINNATH_GELIN, LOTRFaction.UNALIGNED, 1045.0D, 1273.0D),
   DOL_AMROTH(LOTRWaypoint.Region.DOR_EN_ERNIL, LOTRFaction.GONDOR, 1158.0D, 1333.0D),
   EDHELLOND(LOTRWaypoint.Region.DOR_EN_ERNIL, LOTRFaction.GONDOR, 1189.0D, 1293.0D),
   TARNOST(LOTRWaypoint.Region.DOR_EN_ERNIL, LOTRFaction.UNALIGNED, 1241.0D, 1300.0D),
   TOLFALAS_ISLAND(LOTRWaypoint.Region.TOLFALAS, LOTRFaction.UNALIGNED, 1240.0D, 1414.0D),
   AMON_HEN(LOTRWaypoint.Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1335.0D, 1131.0D),
   AMON_LHAW(LOTRWaypoint.Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1372.0D, 1120.0D),
   ARGONATH(LOTRWaypoint.Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1347.0D, 1112.0D),
   NORTH_UNDEEP(LOTRWaypoint.Region.BROWN_LANDS, LOTRFaction.UNALIGNED, 1319.0D, 988.0D),
   SOUTH_UNDEEP(LOTRWaypoint.Region.BROWN_LANDS, LOTRFaction.UNALIGNED, 1335.0D, 1024.0D),
   MORANNON(LOTRWaypoint.Region.DAGORLAD, LOTRFaction.UNALIGNED, 1470.0D, 1131.0D),
   UDUN(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1470.0D, 1145.0D),
   MOUNT_DOOM(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1533.0D, 1204.0D),
   BARAD_DUR(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1573.0D, 1196.0D),
   MINAS_MORGUL(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1461.0D, 1239.0D),
   DURTHANG(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1464.0D, 1159.0D),
   CARACH_ANGREN(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1493.0D, 1166.0D),
   CIRITH_UNGOL(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1479.0D, 1225.0D),
   MORIGOST(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1558.0D, 1286.0D),
   NARGROTH(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1640.0D, 1248.0D),
   AMON_ANGREN(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1663.0D, 1245.0D),
   SEREGOST(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1682.0D, 1214.0D),
   FELLBEASTS(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1754.0D, 1164.0D),
   EASTERN_GUARD(LOTRWaypoint.Region.MORDOR, LOTRFaction.MORDOR, 1840.0D, 1137.0D),
   NURNEN_NORTHERN_SHORE(LOTRWaypoint.Region.NURN, LOTRFaction.MORDOR, 1696.0D, 1324.0D),
   NURNEN_SOUTHERN_SHORE(LOTRWaypoint.Region.NURN, LOTRFaction.MORDOR, 1718.0D, 1369.0D),
   NURNEN_WESTERN_SHORE(LOTRWaypoint.Region.NURN, LOTRFaction.MORDOR, 1650.0D, 1363.0D),
   NURNEN_EASTERN_SHORE(LOTRWaypoint.Region.NURN, LOTRFaction.MORDOR, 1758.0D, 1316.0D),
   THAURBAND(LOTRWaypoint.Region.NURN, LOTRFaction.MORDOR, 1643.0D, 1354.0D),
   VALLEY_OF_SPIDERS(LOTRWaypoint.Region.NAN_UNGOL, LOTRFaction.MORDOR, 1512.0D, 1400.0D),
   DORWINION_PORT(LOTRWaypoint.Region.DORWINION, LOTRFaction.UNALIGNED, 1784.0D, 863.0D),
   DORWINION_COURT(LOTRWaypoint.Region.DORWINION, LOTRFaction.DORWINION, 1758.0D, 939.0D),
   DORWINION_FORD(LOTRWaypoint.Region.DORWINION, LOTRFaction.UNALIGNED, 1776.0D, 986.0D),
   DORWINION_HILLS(LOTRWaypoint.Region.DORWINION, LOTRFaction.UNALIGNED, 1733.0D, 950.0D),
   RHUN_ROAD_WAY(LOTRWaypoint.Region.RHUN, LOTRFaction.UNALIGNED, 2228.0D, 835.0D),
   BALCARAS(LOTRWaypoint.Region.RHUN, LOTRFaction.UNALIGNED, 2231.0D, 1058.0D),
   KHAND_NORTH_ROAD(LOTRWaypoint.Region.RHUN, LOTRFaction.UNALIGNED, 1932.0D, 1331.0D),
   RHUN_CAPITAL(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1867.0D, 984.0D),
   KHAMUL_TOWER(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1888.0D, 878.0D),
   MORDOR_FORD(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1834.0D, 1112.0D),
   RHUN_NORTH_CITY(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1903.0D, 914.0D),
   BAZYLAN(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1921.0D, 889.0D),
   BORDER_TOWN(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1794.0D, 979.0D),
   RHUN_SEA_CITY(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1837.0D, 956.0D),
   RHUN_NORTH_FORD(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 1942.0D, 811.0D),
   RHUN_NORTHEAST(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 2045.0D, 815.0D),
   RHUN_SOUTH_PASS(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1869.0D, 1055.0D),
   RHUN_EAST_CITY(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 2010.0D, 962.0D),
   RHUN_EAST_TOWN(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1983.0D, 936.0D),
   RHUN_SOUTHEAST(LOTRWaypoint.Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 1900.0D, 1141.0D),
   BARAZ_DUM(LOTRWaypoint.Region.RED_MOUNTAINS, LOTRFaction.UNALIGNED, 2326.0D, 800.0D),
   CROSSINGS_OF_HARAD(LOTRWaypoint.Region.HARONDOR, LOTRFaction.UNALIGNED, 1503.0D, 1544.0D),
   HARNEN_SEA_TOWN(LOTRWaypoint.Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1343.0D, 1561.0D),
   HARNEN_ROAD_TOWN(LOTRWaypoint.Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1518.0D, 1563.0D),
   HARNEN_BLACK_TOWN(LOTRWaypoint.Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1566.0D, 1482.0D),
   CROSSINGS_OF_LITHNEN(LOTRWaypoint.Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1539.0D, 1545.0D),
   HARNEN_RIVER_TOWN(LOTRWaypoint.Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1447.0D, 1558.0D),
   KHAND_FORD(LOTRWaypoint.Region.LOSTLADEN, LOTRFaction.UNALIGNED, 1778.0D, 1432.0D),
   UMBAR_CITY(LOTRWaypoint.Region.UMBAR, LOTRFaction.NEAR_HARAD, 1214.0D, 1689.0D),
   UMBAR_GATE(LOTRWaypoint.Region.UMBAR, LOTRFaction.UNALIGNED, 1252.0D, 1698.0D),
   GATE_HERUMOR(LOTRWaypoint.Region.UMBAR, LOTRFaction.NEAR_HARAD, 1097.0D, 1721.0D),
   CEDAR_ROAD(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1034.0D, 1848.0D),
   FERTILE_VALLEY(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1169.0D, 1821.0D),
   GARDENS_BERUTHIEL(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1245.0D, 1781.0D),
   AIN_AL_HARAD(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1265.0D, 1737.0D),
   GATE_FUINUR(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1218.0D, 1631.0D),
   COAST_FORTRESS(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1245.0D, 1582.0D),
   SANDHILL_TOWN(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1277.0D, 1600.0D),
   COAST_RIVER_TOWN(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1080.0D, 1837.0D),
   COAST_CITY(LOTRWaypoint.Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1165.0D, 1742.0D),
   DESERT_TOWN(LOTRWaypoint.Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1563.0D, 1611.0D),
   SOUTH_DESERT_TOWN(LOTRWaypoint.Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1141.0D, 1976.0D),
   DESERT_RIVER_TOWN(LOTRWaypoint.Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1191.0D, 1984.0D),
   GULF_OF_HARAD(LOTRWaypoint.Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1724.0D, 1982.0D),
   GULF_CITY(LOTRWaypoint.Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1640.0D, 1922.0D),
   GULF_FORD(LOTRWaypoint.Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1686.0D, 2032.0D),
   GULF_TRADE_TOWN(LOTRWaypoint.Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1692.0D, 2001.0D),
   GULF_NORTH_TOWN(LOTRWaypoint.Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1626.0D, 1874.0D),
   GULF_EAST_BAY(LOTRWaypoint.Region.GULF_HARAD, LOTRFaction.UNALIGNED, 2036.0D, 2081.0D),
   GULF_EAST_PORT(LOTRWaypoint.Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1847.0D, 2049.0D),
   MOUNT_SAND(LOTRWaypoint.Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 959.0D, 1899.0D),
   MOUNT_GREEN(LOTRWaypoint.Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 884.0D, 2372.0D),
   MOUNT_THUNDER(LOTRWaypoint.Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 1019.0D, 2590.0D),
   GREAT_PLAINS_NORTH(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1308.0D, 2067.0D),
   GREAT_PLAINS_SOUTH(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1462.0D, 2452.0D),
   GREAT_PLAINS_WEST(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1048.0D, 2215.0D),
   GREAT_PLAINS_EAST(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1637.0D, 2176.0D),
   GREEN_VALLEY(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1557.0D, 2622.0D),
   HARAD_LAKES(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1774.0D, 2310.0D),
   LAKE_HARAD(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1100.0D, 2592.0D),
   HARADUIN_MOUTH(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1846.0D, 2838.0D),
   ISLE_MIST(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1533.0D, 3573.0D),
   TAURELONDE(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 901.0D, 2613.0D),
   HARAD_HORN(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1105.0D, 3778.0D),
   TOWN_BONES(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1832.0D, 2188.0D),
   HARADUIN_BRIDGE(LOTRWaypoint.Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1621.0D, 2673.0D),
   JUNGLE_CITY_TRADE(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 952.0D, 2656.0D),
   JUNGLE_CITY_OLD(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1084.0D, 2670.0D),
   JUNGLE_CITY_NORTH(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1419.0D, 2604.0D),
   JUNGLE_CITY_EAST(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1594.0D, 2766.0D),
   JUNGLE_CITY_CAPITAL(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1380.0D, 2861.0D),
   JUNGLE_CITY_DEEP(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1184.0D, 3237.0D),
   JUNGLE_CITY_WATCH(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1590.0D, 2940.0D),
   JUNGLE_CITY_CAVES(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1257.0D, 3054.0D),
   JUNGLE_CITY_STONE(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1236.0D, 2787.0D),
   JUNGLE_LAKES(LOTRWaypoint.Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1550.0D, 2856.0D),
   TROLL_ISLAND(LOTRWaypoint.Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1966.0D, 2342.0D),
   BLACK_COAST(LOTRWaypoint.Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1936.0D, 2496.0D),
   BLOOD_RIVER(LOTRWaypoint.Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1897.0D, 2605.0D),
   SHADOW_POINT(LOTRWaypoint.Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1952.0D, 2863.0D),
   OLD_JUNGLE_RUIN(LOTRWaypoint.Region.PERTOROGWAITH_FOREST, LOTRFaction.UNALIGNED, 1834.0D, 2523.0D);

   private LOTRWaypoint.Region region;
   public LOTRFaction faction;
   private double imgX;
   private double imgY;
   private int xCoord;
   private int zCoord;
   private boolean isHidden;

   private LOTRWaypoint(LOTRWaypoint.Region r, LOTRFaction f, double x, double y) {
      this(r, f, x, y, false);
   }

   private LOTRWaypoint(LOTRWaypoint.Region r, LOTRFaction f, double x, double y, boolean hide) {
      this.region = r;
      this.region.waypoints.add(this);
      this.faction = f;
      this.imgX = x;
      this.imgY = y;
      this.xCoord = mapToWorldX(x);
      this.zCoord = mapToWorldZ(y);
      this.isHidden = hide;
   }

   public static int mapToWorldX(double x) {
      return (int)Math.round((x - 810.0D + 0.5D) * (double)LOTRGenLayerWorld.scale);
   }

   public static int mapToWorldZ(double z) {
      return (int)Math.round((z - 730.0D + 0.5D) * (double)LOTRGenLayerWorld.scale);
   }

   public static int mapToWorldR(double r) {
      return (int)Math.round(r * (double)LOTRGenLayerWorld.scale);
   }

   public static int worldToMapX(double x) {
      return (int)Math.round(x / (double)LOTRGenLayerWorld.scale - 0.5D + 810.0D);
   }

   public static int worldToMapZ(double z) {
      return (int)Math.round(z / (double)LOTRGenLayerWorld.scale - 0.5D + 730.0D);
   }

   public static int worldToMapR(double r) {
      return (int)Math.round(r / (double)LOTRGenLayerWorld.scale);
   }

   public double getX() {
      return this.imgX;
   }

   public double getY() {
      return this.imgY;
   }

   public int getXCoord() {
      return this.xCoord;
   }

   public int getZCoord() {
      return this.zCoord;
   }

   public int getYCoord(World world, int i, int k) {
      return LOTRMod.getTrueTopBlock(world, i, k);
   }

   public int getYCoordSaved() {
      return 64;
   }

   public String getCodeName() {
      return this.name();
   }

   public String getDisplayName() {
      return StatCollector.func_74838_a("lotr.waypoint." + this.getCodeName());
   }

   public String getLoreText(EntityPlayer entityplayer) {
      String key = "lotr.waypoint." + this.getCodeName() + ".info";
      if (this == FERTILE_VALLEY && LOTRLevelData.clientside_thisServer_commemorateEmpressShamiir) {
         key = key + ".shamiir";
      }

      return StatCollector.func_74838_a(key);
   }

   public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      if (pd.isFTRegionUnlocked(this.region)) {
         if (this.isCompatibleAlignment(entityplayer)) {
            return true;
         }

         if (this.isConquestUnlockable(entityplayer)) {
            return this.isConquered(entityplayer);
         }
      }

      return false;
   }

   public boolean isCompatibleAlignment(EntityPlayer entityplayer) {
      if (this.faction == LOTRFaction.UNALIGNED) {
         return true;
      } else {
         LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
         return pd.getAlignment(this.faction) >= 0.0F;
      }
   }

   public boolean isConquestUnlockable(EntityPlayer entityplayer) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      World world = entityplayer.field_70170_p;
      LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(this.getXCoord(), this.getZCoord());
      LOTRFaction pledgeFac = pd.getPledgeFaction();
      return pledgeFac != null && pledgeFac.isBadRelation(this.faction) && LOTRConquestGrid.getConquestEffectIn(world, zone, pledgeFac) == LOTRConquestGrid.ConquestEffective.EFFECTIVE;
   }

   private boolean isConquered(EntityPlayer entityplayer) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      World world = entityplayer.field_70170_p;
      LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(this.getXCoord(), this.getZCoord());
      LOTRFaction pledgeFac = pd.getPledgeFaction();
      return pledgeFac != null && zone.getConquestStrength(pledgeFac, world) >= 500.0F;
   }

   public boolean isUnlockedByConquest(EntityPlayer entityplayer) {
      return !this.isCompatibleAlignment(entityplayer) && this.isConquestUnlockable(entityplayer) && this.isConquered(entityplayer);
   }

   public LOTRAbstractWaypoint.WaypointLockState getLockState(EntityPlayer entityplayer) {
      if (this.hasPlayerUnlocked(entityplayer)) {
         return this.isUnlockedByConquest(entityplayer) ? LOTRAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED_CONQUEST : LOTRAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED;
      } else {
         return LOTRAbstractWaypoint.WaypointLockState.STANDARD_LOCKED;
      }
   }

   public boolean isHidden() {
      return this.isHidden;
   }

   public int getID() {
      return this.ordinal();
   }

   public static LOTRWaypoint waypointForName(String name) {
      LOTRWaypoint[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRWaypoint wp = var1[var3];
         if (wp.getCodeName().equals(name)) {
            return wp;
         }
      }

      return null;
   }

   public static List listAllWaypoints() {
      List list = new ArrayList();
      list.addAll(Arrays.asList(values()));
      return list;
   }

   public static LOTRWaypoint.Region regionForName(String name) {
      LOTRWaypoint.Region[] var1 = LOTRWaypoint.Region.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRWaypoint.Region region = var1[var3];
         if (region.name().equals(name)) {
            return region;
         }
      }

      return null;
   }

   public static LOTRWaypoint.Region regionForID(int id) {
      LOTRWaypoint.Region[] var1 = LOTRWaypoint.Region.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         LOTRWaypoint.Region region = var1[var3];
         if (region.ordinal() == id) {
            return region;
         }
      }

      return null;
   }

   public static enum Region {
      OCEAN,
      MENELTARMA,
      SHIRE,
      OLD_FOREST,
      LINDON,
      BLUE_MOUNTAINS,
      ERIADOR,
      BREE_LAND,
      MIDGEWATER,
      LONE_LANDS,
      RIVENDELL_VALE,
      TROLLSHAWS,
      COLDFELLS,
      ETTENMOORS,
      ANGMAR,
      EREGION,
      DUNLAND,
      ENEDWAITH,
      NAN_CURUNIR,
      FORODWAITH,
      MISTY_MOUNTAINS,
      GREY_MOUNTAINS,
      VALES_OF_ANDUIN,
      WOODLAND_REALM,
      MIRKWOOD,
      WILDERLAND,
      DALE,
      IRON_HILLS,
      LOTHLORIEN,
      FANGORN,
      ROHAN,
      WHITE_MOUNTAINS,
      PUKEL,
      GONDOR,
      ITHILIEN,
      LEBENNIN,
      LOSSARNACH,
      LAMEDON,
      BLACKROOT_VALE,
      PINNATH_GELIN,
      DOR_EN_ERNIL,
      TOLFALAS,
      EMYN_MUIL,
      NINDALF,
      BROWN_LANDS,
      DAGORLAD,
      MORDOR,
      NURN,
      NAN_UNGOL,
      DORWINION,
      RHUN,
      RHUN_KHAGANATE,
      TOL_RHUNAER,
      RED_MOUNTAINS,
      HARONDOR,
      HARNEDOR,
      LOSTLADEN,
      UMBAR,
      SOUTHRON_COASTS,
      HARAD_DESERT,
      GULF_HARAD,
      HARAD_MOUNTAINS,
      FAR_HARAD,
      FAR_HARAD_JUNGLE,
      PERTOROGWAITH,
      PERTOROGWAITH_FOREST;

      public List waypoints = new ArrayList();
   }
}
