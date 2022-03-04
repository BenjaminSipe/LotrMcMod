package lotr.common.entity;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import org.apache.commons.io.input.BOMInputStream;

public class LOTREntityRegistry {
   public static Map registeredNPCs = new HashMap();

   public static void loadRegisteredNPCs(FMLPreInitializationEvent event) {
      StringBuilder stringbuilder = new StringBuilder();
      LOTRFaction[] var2 = LOTRFaction.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRFaction faction = var2[var4];
         if (faction.allowEntityRegistry) {
            if (faction.ordinal() > 0) {
               stringbuilder.append(", ");
            }

            stringbuilder.append(faction.codeName());
         }
      }

      String allFactions = stringbuilder.toString();

      try {
         File file = event.getModConfigurationDirectory();
         File config = new File(file, "LOTR_EntityRegistry.txt");
         if (!config.exists()) {
            if (config.createNewFile()) {
               PrintStream writer = new PrintStream(new FileOutputStream(config));
               writer.println("#Lines starting with '#' will be ignored");
               writer.println("#");
               writer.println("#Use this file to register entities with the LOTR alignment system.");
               writer.println("#");
               writer.println("#An example format for registering an entity is as follows: (do not use spaces)");
               writer.println("#name=" + LOTREntities.getStringFromClass(LOTREntityMordorOrc.class) + ",faction=" + LOTRFaction.MORDOR.codeName() + ",targetEnemies=true,bonus=1");
               writer.println("#");
               writer.println("#'name' is the entity name, prefixed with the associated mod ID.");
               writer.println("#The mod ID can be found in the Mod List on the main menu - for example, \"lotr\" for the LOTR mod.");
               writer.println("#The entity name is not necessarily the in-game name. It is the name used to register the entity in the code.");
               writer.println("#You may be able to discover the entity name in the mod's language file if there is one - otherwise, contact the mod author.");
               writer.println("#The mod ID and entity name must be separated by a '.' character.");
               writer.println("#Vanilla entities have no mod ID and therefore no prefix.");
               writer.println("#");
               writer.println("#'faction' can be " + allFactions);
               writer.println("#");
               writer.println("#'targetEnemies' can be true or false.");
               writer.println("#If true, the entity will be equipped with AI modules to target its enemies.");
               writer.println("#Actual combat behaviour may or may not be present, depending on whether the entity is designed with combat AI modules.");
               writer.println("#");
               writer.println("#'bonus' is the alignment bonus awarded to a player who kills the entity.");
               writer.println("#It can be positive, negative, or zero, in which case no bonus will be awarded.");
               writer.println("#");
               writer.close();
            }
         } else {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(config))));
            String s = "";

            while(true) {
               String line;
               String name;
               LOTRFaction faction;
               boolean targetEnemies;
               int k;
               while(true) {
                  int j;
                  do {
                     int i;
                     do {
                        do {
                           do {
                              do {
                                 do {
                                    do {
                                       do {
                                          if ((s = bufferedreader.readLine()) == null) {
                                             bufferedreader.close();
                                             return;
                                          }

                                          line = s;
                                       } while(s.startsWith("#"));

                                       faction = null;
                                    } while(!s.startsWith("name="));

                                    s = s.substring("name=".length());
                                 } while(s.toLowerCase().startsWith("lotr".toLowerCase()));

                                 i = s.indexOf(",faction=");
                              } while(i < 0);

                              j = s.indexOf(",targetEnemies=");
                           } while(j < 0);

                           k = s.indexOf(",bonus=");
                        } while(k < 0);

                        name = s.substring(0, i);
                     } while(name.length() == 0);

                     String factionString = s.substring(i + ",faction=".length(), j);
                     faction = LOTRFaction.forName(factionString);
                  } while(faction == null);

                  String targetEnemiesString = s.substring(j + ",targetEnemies=".length(), k);
                  if (targetEnemiesString.equals("true")) {
                     targetEnemies = true;
                     break;
                  }

                  if (targetEnemiesString.equals("false")) {
                     targetEnemies = false;
                     break;
                  }
               }

               String bonusString = s.substring(k + ",bonus=".length());
               int bonus = Integer.parseInt(bonusString);
               registeredNPCs.put(name, new LOTREntityRegistry.RegistryInfo(name, faction, targetEnemies, bonus));
               FMLLog.info("Successfully registered entity " + name + " with the LOTR alignment system as " + line, new Object[0]);
            }
         }
      } catch (Exception var18) {
         var18.printStackTrace();
      }

   }

   public static class RegistryInfo {
      public LOTRFaction alignmentFaction;
      public boolean shouldTargetEnemies;
      public LOTRAlignmentValues.AlignmentBonus alignmentBonus;

      public RegistryInfo(String entityName, LOTRFaction side, boolean flag, int bonus) {
         this.alignmentFaction = side;
         this.shouldTargetEnemies = flag;
         this.alignmentBonus = new LOTRAlignmentValues.AlignmentBonus((float)bonus, "entity." + entityName + ".name");
         this.alignmentBonus.needsTranslation = true;
      }
   }
}
