package lotr.common.world.map;

import com.google.common.collect.Iterators;
import cpw.mods.fml.common.FMLLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRRoads {
   private static List allRoads = new ArrayList();
   private static List displayOnlyRoads = new ArrayList();
   private static LOTRRoads.RoadPointDatabase roadPointDatabase = new LOTRRoads.RoadPointDatabase();
   public LOTRRoads.RoadPoint[] roadPoints;
   public List endpoints;
   public final String roadName;

   private LOTRRoads(String name, LOTRRoads.RoadPoint... ends) {
      this.endpoints = new ArrayList();
      this.roadName = name;
      LOTRRoads.RoadPoint[] var3 = ends;
      int var4 = ends.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         LOTRRoads.RoadPoint e = var3[var5];
         this.endpoints.add(e);
      }

   }

   public String getDisplayName() {
      return StatCollector.func_74838_a("lotr.road." + this.roadName);
   }

   private static void registerRoad(String name, Object... waypoints) {
      registerRoadToList(allRoads, name, waypoints);
   }

   private static void registerDisplayOnlyRoad(String name, Object... waypoints) {
      registerRoadToList(displayOnlyRoads, name, waypoints);
   }

   private static void registerRoadToList(List targetList, String name, Object... waypoints) {
      List points = new ArrayList();
      Object[] var4 = waypoints;
      int var5 = waypoints.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object obj = var4[var6];
         if (obj instanceof LOTRWaypoint) {
            LOTRWaypoint wp = (LOTRWaypoint)obj;
            points.add(new LOTRRoads.RoadPoint((double)wp.getXCoord(), (double)wp.getZCoord(), true));
         } else if (obj instanceof int[]) {
            int[] coords = (int[])((int[])obj);
            if (coords.length != 2) {
               throw new IllegalArgumentException("Coords length must be 2!");
            }

            points.add(new LOTRRoads.RoadPoint((double)LOTRWaypoint.mapToWorldX((double)coords[0]), (double)LOTRWaypoint.mapToWorldZ((double)coords[1]), false));
         } else {
            if (!(obj instanceof double[])) {
               throw new IllegalArgumentException("Wrong road parameter!");
            }

            double[] coords = (double[])((double[])obj);
            if (coords.length != 2) {
               throw new IllegalArgumentException("Coords length must be 2!");
            }

            points.add(new LOTRRoads.RoadPoint((double)LOTRWaypoint.mapToWorldX(coords[0]), (double)LOTRWaypoint.mapToWorldZ(coords[1]), false));
         }
      }

      LOTRRoads.RoadPoint[] array = (LOTRRoads.RoadPoint[])points.toArray(new LOTRRoads.RoadPoint[0]);
      LOTRRoads[] roads = LOTRRoads.BezierCurves.getSplines(name, array);
      targetList.addAll(Arrays.asList(roads));
   }

   public static void createRoads() {
      FMLLog.info("LOTRRoads: Creating roads (reticulating splines)", new Object[0]);
      long time = System.nanoTime();
      allRoads.clear();
      displayOnlyRoads.clear();
      roadPointDatabase = new LOTRRoads.RoadPointDatabase();
      registerRoad("EredLuin", LOTRWaypoint.NOGROD, LOTRWaypoint.BELEGOST);
      registerRoad("NogrodForlond", LOTRWaypoint.NOGROD, LOTRWaypoint.FORLOND);
      registerRoad("NogrodMithlond", LOTRWaypoint.NOGROD, new int[]{654, 650}, LOTRWaypoint.MITHLOND_NORTH);
      registerRoad("Mithlond", LOTRWaypoint.HARLOND, new int[]{658, 755}, LOTRWaypoint.MITHLOND_SOUTH, new int[]{690, 711}, new int[]{681, 705}, LOTRWaypoint.MITHLOND_NORTH, new int[]{644, 733}, new int[]{603, 733}, new int[]{554, 715}, LOTRWaypoint.FORLOND);
      registerRoad("WestEast", LOTRWaypoint.MITHLOND_SOUTH, LOTRWaypoint.TOWER_HILLS, LOTRWaypoint.GREENHOLM, LOTRWaypoint.MICHEL_DELVING, LOTRWaypoint.WAYMEET, LOTRWaypoint.BYWATER, LOTRWaypoint.FROGMORTON, LOTRWaypoint.WHITFURROWS, LOTRWaypoint.BRANDYWINE_BRIDGE, new int[]{870, 718}, new int[]{902, 729}, LOTRWaypoint.BREE);
      registerRoad("WestEast", LOTRWaypoint.BREE, new double[]{LOTRWaypoint.BREE.getX() + 0.5D, LOTRWaypoint.BREE.getY()});
      registerRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() + 2.0D, LOTRWaypoint.BREE.getY() + 1.5D}, new double[]{LOTRWaypoint.STADDLE.getX(), LOTRWaypoint.STADDLE.getY() + 5.0D}, LOTRWaypoint.FORSAKEN_INN, new double[]{LOTRWaypoint.WEATHERTOP.getX(), LOTRWaypoint.WEATHERTOP.getY() + 2.0D}, LOTRWaypoint.LAST_BRIDGE, new int[]{1132, 723}, new int[]{1178, 704}, LOTRWaypoint.HIGH_PASS, LOTRWaypoint.OLD_FORD, LOTRWaypoint.RIVER_GATE, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.REDWATER_FORD, new int[]{1785, 775}, LOTRWaypoint.RHUN_NORTH_FORD, LOTRWaypoint.RHUN_NORTHEAST, LOTRWaypoint.RHUN_ROAD_WAY, LOTRWaypoint.BARAZ_DUM);
      registerRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() - 0.375D, LOTRWaypoint.BREE.getY() - 2.476D}, new double[]{LOTRWaypoint.BREE.getX() + 2.0D, LOTRWaypoint.BREE.getY() - 1.5D});
      registerDisplayOnlyRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() + 0.5D, LOTRWaypoint.BREE.getY()}, new double[]{LOTRWaypoint.BREE.getX() + 2.0D, LOTRWaypoint.BREE.getY()});
      registerDisplayOnlyRoad("WestEast", new double[]{LOTRWaypoint.BREE.getX() + 2.0D, LOTRWaypoint.BREE.getY() - 1.5D}, new double[]{LOTRWaypoint.BREE.getX() + 2.0D, LOTRWaypoint.BREE.getY() + 1.5D});
      registerRoad("BywaterRoad", LOTRWaypoint.BYWATER, LOTRWaypoint.HOBBITON);
      registerRoad("Overhill", LOTRWaypoint.HOBBITON, LOTRWaypoint.OVERHILL);
      registerRoad("BucklandRoad", LOTRWaypoint.HAY_GATE, LOTRWaypoint.BUCKLEBURY, LOTRWaypoint.HAYSEND);
      registerRoad("Chetroad", new double[]{LOTRWaypoint.STADDLE.getX(), LOTRWaypoint.STADDLE.getY() + 5.0D}, LOTRWaypoint.STADDLE, LOTRWaypoint.COMBE, LOTRWaypoint.ARCHET);
      registerRoad("Chetroad", LOTRWaypoint.STADDLE, new double[]{LOTRWaypoint.STADDLE.getX() - 0.5D, LOTRWaypoint.STADDLE.getY()});
      registerRoad("Chetroad", LOTRWaypoint.COMBE, new double[]{LOTRWaypoint.COMBE.getX() + 0.5D, LOTRWaypoint.COMBE.getY()});
      registerRoad("Chetroad", LOTRWaypoint.ARCHET, new double[]{LOTRWaypoint.ARCHET.getX(), LOTRWaypoint.ARCHET.getY() - 0.5D});
      registerRoad("ElfPath", LOTRWaypoint.FOREST_GATE, LOTRWaypoint.ENCHANTED_RIVER, LOTRWaypoint.THRANDUIL_HALLS);
      registerRoad("EreborRoad", LOTRWaypoint.LONG_LAKE, LOTRWaypoint.DALE_CITY, LOTRWaypoint.EREBOR);
      registerRoad("DalePortRoad", LOTRWaypoint.DALE_CITY, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.DALE_PORT);
      registerRoad("DaleSouthRoad", LOTRWaypoint.EAST_RHOVANION_ROAD, LOTRWaypoint.OLD_RHOVANION, LOTRWaypoint.RUNNING_FORD, LOTRWaypoint.DALE_CROSSROADS, LOTRWaypoint.WEST_PEAK);
      registerRoad("IronHills", LOTRWaypoint.WEST_PEAK, new int[]{1652, 621}, LOTRWaypoint.EAST_PEAK);
      registerRoad("DorwinionSouthRoad", LOTRWaypoint.DALE_PORT, LOTRWaypoint.DORWINION_CROSSROADS, LOTRWaypoint.DORWINION_COURT, LOTRWaypoint.DORWINION_FORD);
      registerRoad("DorwinionEastRoad", LOTRWaypoint.OLD_RHOVANION, LOTRWaypoint.DORWINION_CROSSROADS, LOTRWaypoint.DORWINION_PORT);
      registerRoad("RhunRoad", LOTRWaypoint.DORWINION_FORD, LOTRWaypoint.BORDER_TOWN, LOTRWaypoint.RHUN_SEA_CITY, LOTRWaypoint.RHUN_CAPITAL, new int[]{1888, 958}, LOTRWaypoint.RHUN_NORTH_CITY, LOTRWaypoint.BAZYLAN, LOTRWaypoint.RHUN_NORTHEAST);
      registerRoad("RhunEastRoad", LOTRWaypoint.RHUN_NORTH_CITY, LOTRWaypoint.RHUN_EAST_TOWN, LOTRWaypoint.RHUN_EAST_CITY);
      registerRoad("Nobottle", LOTRWaypoint.TIGHFIELD, LOTRWaypoint.LITTLE_DELVING, LOTRWaypoint.NOBOTTLE, LOTRWaypoint.NEEDLEHOLE);
      registerRoad("Oatbarton", LOTRWaypoint.OATBARTON, LOTRWaypoint.FROGMORTON);
      registerRoad("Stock", LOTRWaypoint.TUCKBOROUGH, LOTRWaypoint.STOCK);
      registerRoad("Deephallow", LOTRWaypoint.SCARY, LOTRWaypoint.WHITFURROWS, LOTRWaypoint.STOCK, LOTRWaypoint.DEEPHALLOW);
      registerRoad("Willowbottom", LOTRWaypoint.WILLOWBOTTOM, LOTRWaypoint.DEEPHALLOW);
      registerRoad("ArnorRoad", LOTRWaypoint.ANNUMINAS, LOTRWaypoint.FORNOST);
      registerRoad("Greenway", LOTRWaypoint.FORNOST, LOTRWaypoint.BREE, LOTRWaypoint.GREENWAY_CROSSROADS);
      registerRoad("ElvenWay", LOTRWaypoint.WEST_GATE, new int[]{1133, 867}, new int[]{1124, 868}, LOTRWaypoint.OST_IN_EDHIL, new int[]{1073, 864}, LOTRWaypoint.OLD_ELF_WAY, new int[]{1002, 849}, new int[]{992, 860}, LOTRWaypoint.THARBAD, new int[]{959, 889}, new int[]{926, 913}, new int[]{902, 942}, LOTRWaypoint.LOND_DAER);
      registerRoad("BruinenPath", LOTRWaypoint.FORD_BRUINEN, LOTRWaypoint.RIVENDELL);
      registerRoad("NimrodelRoad", LOTRWaypoint.DIMRILL_DALE, LOTRWaypoint.NIMRODEL);
      registerRoad("AnduinRoad", LOTRWaypoint.MORANNON, new int[]{1428, 1066}, LOTRWaypoint.EAST_RHOVANION_ROAD, LOTRWaypoint.ANDUIN_CROSSROADS, new int[]{1325, 820}, new int[]{1318, 735}, LOTRWaypoint.FOREST_GATE);
      registerRoad("DolGuldurRoad", LOTRWaypoint.ANDUIN_CROSSROADS, LOTRWaypoint.DOL_GULDUR);
      registerRoad("Framsburg", LOTRWaypoint.FOREST_GATE, new int[]{1278, 605}, LOTRWaypoint.FRAMSBURG, new int[]{1260, 565}, LOTRWaypoint.DAINS_HALLS);
      registerRoad("NorthSouth", LOTRWaypoint.LITTLE_DELVING, LOTRWaypoint.WAYMEET, LOTRWaypoint.LONGBOTTOM, LOTRWaypoint.SARN_FORD, LOTRWaypoint.GREENWAY_CROSSROADS, LOTRWaypoint.THARBAD, LOTRWaypoint.ENEDWAITH_ROAD, LOTRWaypoint.FORDS_OF_ISEN, LOTRWaypoint.HELMS_CROSSROADS, LOTRWaypoint.GRIMSLADE, LOTRWaypoint.EDORAS, LOTRWaypoint.ALDBURG, LOTRWaypoint.MERING_STREAM, LOTRWaypoint.AMON_DIN);
      registerRoad("TirithRoad", LOTRWaypoint.AMON_DIN, LOTRWaypoint.MINAS_TIRITH);
      registerRoad("OsgiliathRoad", LOTRWaypoint.MINAS_TIRITH, LOTRWaypoint.OSGILIATH_WEST);
      registerRoad("OsgiliathCrossing", LOTRWaypoint.OSGILIATH_WEST, LOTRWaypoint.OSGILIATH_EAST);
      registerRoad("OsgiliathMorgulRoad", LOTRWaypoint.OSGILIATH_EAST, LOTRWaypoint.CROSSROADS_ITHILIEN, LOTRWaypoint.MINAS_MORGUL);
      registerRoad("GondorSouthRoad", LOTRWaypoint.MINAS_TIRITH, LOTRWaypoint.CROSSINGS_ERUI, new int[]{1408, 1291}, LOTRWaypoint.PELARGIR, LOTRWaypoint.LINHIR, new int[]{1266, 1301}, LOTRWaypoint.ETHRING, LOTRWaypoint.CALEMBEL, LOTRWaypoint.TARLANG, LOTRWaypoint.ERECH);
      registerRoad("IsengardRoad", LOTRWaypoint.FORDS_OF_ISEN, LOTRWaypoint.ISENGARD);
      registerRoad("IsengardRoad", LOTRWaypoint.ISENGARD, new double[]{LOTRWaypoint.ISENGARD.getX(), LOTRWaypoint.ISENGARD.getY() - 3.5D});
      registerRoad("HelmRoad", LOTRWaypoint.HELMS_CROSSROADS, LOTRWaypoint.HELMS_DEEP);
      registerRoad("WoldRoad", LOTRWaypoint.EDORAS, LOTRWaypoint.ENTWADE, new int[]{1260, 1060}, LOTRWaypoint.WOLD);
      registerRoad("DolAmroth", new int[]{1266, 1301}, LOTRWaypoint.TARNOST, LOTRWaypoint.EDHELLOND, new int[]{1185, 1325}, LOTRWaypoint.DOL_AMROTH);
      registerRoad("Pelargir", LOTRWaypoint.PELARGIR, new int[]{1394, 1352});
      registerRoad("Poros", new int[]{1397, 1355}, LOTRWaypoint.CROSSINGS_OF_POROS);
      registerRoad("CairAndros", LOTRWaypoint.AMON_DIN, LOTRWaypoint.CAIR_ANDROS, LOTRWaypoint.NORTH_ITHILIEN);
      registerRoad("SauronRoad", LOTRWaypoint.MINAS_MORGUL, LOTRWaypoint.MOUNT_DOOM, LOTRWaypoint.BARAD_DUR, LOTRWaypoint.SEREGOST, new int[]{1742, 1209}, new int[]{1809, 1172}, LOTRWaypoint.EASTERN_GUARD, LOTRWaypoint.MORDOR_FORD, LOTRWaypoint.RHUN_SOUTH_PASS, new int[]{1875, 1003}, new int[]{1867, 996}, LOTRWaypoint.RHUN_CAPITAL);
      registerRoad("MorannonRoad", LOTRWaypoint.MORANNON, LOTRWaypoint.UDUN);
      registerRoad("MorannonRhunRoad", LOTRWaypoint.MORANNON, new int[]{1520, 1130}, new int[]{1658, 1140}, new int[]{1780, 1115}, LOTRWaypoint.MORDOR_FORD, LOTRWaypoint.RHUN_SOUTHEAST, LOTRWaypoint.KHAND_NORTH_ROAD, LOTRWaypoint.KHAND_FORD, LOTRWaypoint.HARNEN_BLACK_TOWN, LOTRWaypoint.CROSSINGS_OF_LITHNEN, LOTRWaypoint.HARNEN_ROAD_TOWN, LOTRWaypoint.HARNEN_RIVER_TOWN, LOTRWaypoint.HARNEN_SEA_TOWN, LOTRWaypoint.COAST_FORTRESS, LOTRWaypoint.GATE_FUINUR, LOTRWaypoint.UMBAR_CITY, LOTRWaypoint.GATE_HERUMOR);
      registerRoad("GorgorothRoad", LOTRWaypoint.UDUN, LOTRWaypoint.CARACH_ANGREN, LOTRWaypoint.BARAD_DUR, LOTRWaypoint.THAURBAND);
      registerRoad("HaradRoad", LOTRWaypoint.MORANNON, LOTRWaypoint.NORTH_ITHILIEN, LOTRWaypoint.CROSSROADS_ITHILIEN, LOTRWaypoint.CROSSINGS_OF_POROS, new int[]{1429, 1394}, new int[]{1408, 1432}, new int[]{1428, 1470}, new int[]{1435, 1526}, LOTRWaypoint.CROSSINGS_OF_HARAD, LOTRWaypoint.HARNEN_ROAD_TOWN, LOTRWaypoint.DESERT_TOWN);
      registerRoad("UmbarRoad", LOTRWaypoint.UMBAR_CITY, LOTRWaypoint.UMBAR_GATE, LOTRWaypoint.AIN_AL_HARAD, LOTRWaypoint.GARDENS_BERUTHIEL, LOTRWaypoint.FERTILE_VALLEY, LOTRWaypoint.SOUTH_DESERT_TOWN);
      registerRoad("GulfRoad", LOTRWaypoint.TOWN_BONES, new int[]{1794, 2110}, LOTRWaypoint.GULF_FORD, LOTRWaypoint.GULF_TRADE_TOWN, LOTRWaypoint.GULF_CITY, LOTRWaypoint.GULF_NORTH_TOWN, new int[]{1702, 1940}, LOTRWaypoint.GULF_OF_HARAD, new int[]{1775, 2002}, LOTRWaypoint.GULF_EAST_PORT);
      registerRoad("JungleNorthRoad", LOTRWaypoint.JUNGLE_CITY_TRADE, LOTRWaypoint.JUNGLE_CITY_OLD, LOTRWaypoint.JUNGLE_CITY_NORTH);
      registerRoad("JungleMangroveRoad", LOTRWaypoint.JUNGLE_CITY_NORTH, LOTRWaypoint.JUNGLE_CITY_EAST, LOTRWaypoint.HARADUIN_MOUTH);
      registerRoad("JungleDeepRoad", LOTRWaypoint.JUNGLE_CITY_NORTH, LOTRWaypoint.JUNGLE_CITY_CAPITAL, LOTRWaypoint.JUNGLE_CITY_CAVES, LOTRWaypoint.JUNGLE_CITY_DEEP);
      registerRoad("JungleWestEastRoad", LOTRWaypoint.JUNGLE_CITY_OLD, LOTRWaypoint.JUNGLE_CITY_STONE, LOTRWaypoint.JUNGLE_CITY_CAPITAL, LOTRWaypoint.JUNGLE_LAKES, LOTRWaypoint.JUNGLE_CITY_WATCH);
      registerRoad("JungleLakeRoad", LOTRWaypoint.JUNGLE_LAKES, LOTRWaypoint.JUNGLE_CITY_EAST, LOTRWaypoint.HARADUIN_BRIDGE, LOTRWaypoint.OLD_JUNGLE_RUIN);
      long newTime = System.nanoTime();
      int roads = allRoads.size();
      int points = 0;
      int dbEntries = 0;
      int dbPoints = 0;

      Iterator var8;
      LOTRRoads road;
      for(var8 = allRoads.iterator(); var8.hasNext(); points += road.roadPoints.length) {
         road = (LOTRRoads)var8.next();
      }

      Entry e;
      for(var8 = roadPointDatabase.pointMap.entrySet().iterator(); var8.hasNext(); dbPoints += ((List)e.getValue()).size()) {
         e = (Entry)var8.next();
         ++dbEntries;
      }

      FMLLog.info("LOTRRoads: Created roads in " + (double)(newTime - time) / 1.0E9D + "s", new Object[0]);
      FMLLog.info("LOTRRoads: roads=" + roads + ", points=" + points + ", dbEntries=" + dbEntries + ", dbPoints=" + dbPoints, new Object[0]);
   }

   public static List getAllRoadsInWorld() {
      return allRoads;
   }

   public static Iterator getAllRoadsForDisplay() {
      return Iterators.concat(allRoads.iterator(), displayOnlyRoads.iterator());
   }

   public static boolean isRoadAt(int x, int z) {
      return isRoadNear(x, z, 4) >= 0.0F;
   }

   public static float isRoadNear(int x, int z, int width) {
      double widthSq = (double)(width * width);
      float leastSqRatio = -1.0F;
      List points = roadPointDatabase.getPointsForCoords(x, z);
      Iterator var7 = points.iterator();

      while(var7.hasNext()) {
         LOTRRoads.RoadPoint point = (LOTRRoads.RoadPoint)var7.next();
         double dx = point.x - (double)x;
         double dz = point.z - (double)z;
         double distSq = dx * dx + dz * dz;
         if (distSq < widthSq) {
            float f = (float)(distSq / widthSq);
            if (leastSqRatio == -1.0F) {
               leastSqRatio = f;
            } else if (f < leastSqRatio) {
               leastSqRatio = f;
            }
         }
      }

      return leastSqRatio;
   }

   // $FF: synthetic method
   LOTRRoads(String x0, LOTRRoads.RoadPoint[] x1, Object x2) {
      this(x0, x1);
   }

   private static class BezierCurves {
      private static int roadLengthFactor = 1;

      private static LOTRRoads[] getSplines(String name, LOTRRoads.RoadPoint[] waypoints) {
         int i;
         LOTRRoads.RoadPoint cp1;
         int i;
         if (waypoints.length == 2) {
            LOTRRoads.RoadPoint p1 = waypoints[0];
            LOTRRoads.RoadPoint p2 = waypoints[1];
            LOTRRoads road = new LOTRRoads(name, new LOTRRoads.RoadPoint[]{p1, p2});
            double dx = p2.x - p1.x;
            double dz = p2.z - p1.z;
            int roadLength = (int)Math.round(Math.sqrt(dx * dx + dz * dz));
            i = roadLength * roadLengthFactor;
            road.roadPoints = new LOTRRoads.RoadPoint[i];

            for(i = 0; i < i; ++i) {
               double t = (double)i / (double)i;
               cp1 = new LOTRRoads.RoadPoint(p1.x + dx * t, p1.z + dz * t, false);
               road.roadPoints[i] = cp1;
               LOTRRoads.roadPointDatabase.add(cp1);
            }

            return new LOTRRoads[]{road};
         } else {
            int length = waypoints.length;
            double[] x = new double[length];
            double[] z = new double[length];

            for(int i = 0; i < length; ++i) {
               x[i] = waypoints[i].x;
               z[i] = waypoints[i].z;
            }

            double[][] controlX = getControlPoints(x);
            double[][] controlZ = getControlPoints(z);
            int controlPoints = controlX[0].length;
            LOTRRoads.RoadPoint[] controlPoints1 = new LOTRRoads.RoadPoint[controlPoints];
            LOTRRoads.RoadPoint[] controlPoints2 = new LOTRRoads.RoadPoint[controlPoints];

            LOTRRoads.RoadPoint p1;
            for(i = 0; i < controlPoints; ++i) {
               LOTRRoads.RoadPoint p1 = new LOTRRoads.RoadPoint(controlX[0][i], controlZ[0][i], false);
               p1 = new LOTRRoads.RoadPoint(controlX[1][i], controlZ[1][i], false);
               controlPoints1[i] = p1;
               controlPoints2[i] = p1;
            }

            LOTRRoads[] roads = new LOTRRoads[length - 1];

            for(i = 0; i < roads.length; ++i) {
               p1 = waypoints[i];
               LOTRRoads.RoadPoint p2 = waypoints[i + 1];
               cp1 = controlPoints1[i];
               LOTRRoads.RoadPoint cp2 = controlPoints2[i];
               LOTRRoads road = new LOTRRoads(name, new LOTRRoads.RoadPoint[]{p1, p2});
               roads[i] = road;
               double dx = p2.x - p1.x;
               double dz = p2.z - p1.z;
               int roadLength = (int)Math.round(Math.sqrt(dx * dx + dz * dz));
               int points = roadLength * roadLengthFactor;
               road.roadPoints = new LOTRRoads.RoadPoint[points];

               for(int l = 0; l < points; ++l) {
                  double t = (double)l / (double)points;
                  LOTRRoads.RoadPoint point = bezier(p1, cp1, cp2, p2, t);
                  road.roadPoints[l] = point;
                  LOTRRoads.roadPointDatabase.add(point);
               }
            }

            return roads;
         }
      }

      private static LOTRRoads.RoadPoint lerp(LOTRRoads.RoadPoint a, LOTRRoads.RoadPoint b, double t) {
         double x = a.x + (b.x - a.x) * t;
         double z = a.z + (b.z - a.z) * t;
         return new LOTRRoads.RoadPoint(x, z, false);
      }

      private static LOTRRoads.RoadPoint bezier(LOTRRoads.RoadPoint a, LOTRRoads.RoadPoint b, LOTRRoads.RoadPoint c, LOTRRoads.RoadPoint d, double t) {
         LOTRRoads.RoadPoint ab = lerp(a, b, t);
         LOTRRoads.RoadPoint bc = lerp(b, c, t);
         LOTRRoads.RoadPoint cd = lerp(c, d, t);
         LOTRRoads.RoadPoint abbc = lerp(ab, bc, t);
         LOTRRoads.RoadPoint bccd = lerp(bc, cd, t);
         return lerp(abbc, bccd, t);
      }

      private static double[][] getControlPoints(double[] src) {
         int length = src.length - 1;
         double[] p1 = new double[length];
         double[] p2 = new double[length];
         double[] a = new double[length];
         double[] b = new double[length];
         double[] c = new double[length];
         double[] r = new double[length];
         a[0] = 0.0D;
         b[0] = 2.0D;
         c[0] = 1.0D;
         r[0] = src[0] + 2.0D * src[1];

         int i;
         for(i = 1; i < length - 1; ++i) {
            a[i] = 1.0D;
            b[i] = 4.0D;
            c[i] = 1.0D;
            r[i] = 4.0D * src[i] + 2.0D * src[i + 1];
         }

         a[length - 1] = 2.0D;
         b[length - 1] = 7.0D;
         c[length - 1] = 0.0D;
         r[length - 1] = 8.0D * src[length - 1] + src[length];

         double p;
         for(i = 1; i < length; ++i) {
            p = a[i] / b[i - 1];
            b[i] -= p * c[i - 1];
            r[i] -= p * r[i - 1];
         }

         p1[length - 1] = r[length - 1] / b[length - 1];

         for(i = length - 2; i >= 0; --i) {
            p = (r[i] - c[i] * p1[i + 1]) / b[i];
            p1[i] = p;
         }

         for(i = 0; i < length - 1; ++i) {
            p2[i] = 2.0D * src[i + 1] - p1[i + 1];
         }

         p2[length - 1] = 0.5D * (src[length] + p1[length - 1]);
         return new double[][]{p1, p2};
      }
   }

   public static class RoadPoint {
      public final double x;
      public final double z;
      public final boolean isWaypoint;

      public RoadPoint(double i, double j, boolean flag) {
         this.x = i;
         this.z = j;
         this.isWaypoint = flag;
      }
   }

   private static class RoadPointDatabase {
      private Map pointMap;
      private static final int COORD_LOOKUP_SIZE = 1000;

      private RoadPointDatabase() {
         this.pointMap = new HashMap();
      }

      public List getPointsForCoords(int x, int z) {
         int x1 = x / 1000;
         int z1 = z / 1000;
         return this.getRoadList(x1, z1, false);
      }

      public void add(LOTRRoads.RoadPoint point) {
         int x = (int)Math.round(point.x / 1000.0D);
         int z = (int)Math.round(point.z / 1000.0D);
         int overlap = 1;

         for(int i = -overlap; i <= overlap; ++i) {
            for(int k = -overlap; k <= overlap; ++k) {
               int xKey = x + i;
               int zKey = z + k;
               this.getRoadList(xKey, zKey, true).add(point);
            }
         }

      }

      private List getRoadList(int xKey, int zKey, boolean addToMap) {
         Pair key = Pair.of(xKey, zKey);
         List list = (List)this.pointMap.get(key);
         if (list == null) {
            list = new ArrayList();
            if (addToMap) {
               this.pointMap.put(key, list);
            }
         }

         return (List)list;
      }

      // $FF: synthetic method
      RoadPointDatabase(Object x0) {
         this();
      }
   }
}
