package lotr.common.dim;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRDimensions;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraft.world.storage.SaveFormat.LevelSave;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.commons.io.FileUtils;

public class AddModDimensionToOldWorlds {
   public static ResourceLocation displayRelocatingDimensionFolder;

   public static void operateOnWorldSave(DynamicOps nbtOps, LevelSave levelSave, ServerWorldInfo serverInfo) {
      if (!(nbtOps instanceof WorldSettingsImport)) {
         LOTRLog.error("Could not operate on world save - the DynamicOps was not an instance of WorldSettingsImport");
      } else {
         WorldSettingsImport worldSettingsImport = (WorldSettingsImport)nbtOps;
         DynamicRegistries dynRegs = (DynamicRegistries)ObfuscationReflectionHelper.getPrivateValue(WorldSettingsImport.class, worldSettingsImport, "field_240872_d_");
         if (dynRegs == null) {
            LOTRLog.error("Failed to fetch the dynamic registries from WorldSettingsImport");
         } else {
            DimensionGeneratorSettings dimGenSettings = serverInfo.func_230418_z_();
            SimpleRegistry dimReg = dimGenSettings.func_236224_e_();
            Registry dimTypeReg = dynRegs.func_243612_b(Registry.field_239698_ad_);
            Registry biomeReg = dynRegs.func_243612_b(Registry.field_239720_u_);
            Registry dimSettingsReg = dynRegs.func_243612_b(Registry.field_243549_ar);
            long seed = dimGenSettings.func_236221_b_();
            Iterator var12 = LOTRDimensions.viewAddedDimensions().iterator();

            while(var12.hasNext()) {
               RegistryKey modDimension = (RegistryKey)var12.next();
               ResourceLocation dimName = modDimension.func_240901_a_();
               if (!dimReg.func_148742_b().contains(dimName)) {
                  LOTRDimensions.addSpecificDimensionToWorldRegistry(modDimension, dimReg, dimTypeReg, biomeReg, dimSettingsReg, seed);
                  LOTRLog.info("Injected dimension %s into the registry for a pre-1.16 lotrmod world, or pre-existing lotrmodless 1.16 world", dimName);
                  relocateOldFolder(levelSave, modDimension, dimName);
               }
            }

         }
      }
   }

   private static void relocateOldFolder(LevelSave levelSave, RegistryKey modDimension, ResourceLocation dimName) {
      FolderName oldDimFolderName = new FolderName(String.format("%s/%s", dimName.func_110624_b(), dimName.func_110623_a()));
      File oldDimFolder = levelSave.func_237285_a_(oldDimFolderName).toFile();
      if (oldDimFolder.exists()) {
         RegistryKey dimWorldKey = RegistryKey.func_240903_a_(Registry.field_239699_ae_, modDimension.func_240901_a_());
         File newDimFolder = levelSave.func_237291_a_(dimWorldKey);
         if (!newDimFolder.exists()) {
            LOTRLog.info("Copying dimension data for %s from pre-1.16 dimension folder structure to new location...", dimName);
            displayRelocatingDimensionFolder = dimName;

            try {
               FileUtils.copyDirectory(oldDimFolder, newDimFolder);
               LOTRLog.info("Copied");
            } catch (IOException var8) {
               LOTRLog.warn("Copying failed!");
               var8.printStackTrace();
            }

            displayRelocatingDimensionFolder = null;
            File oldDimFolderRename = new File(oldDimFolder.getParent(), String.format("PRE_MC_116_BACKUP_%s", oldDimFolder.getName()));
            oldDimFolder.renameTo(oldDimFolderRename);
            LOTRLog.info("...and renamed old folder as a backup");
         }
      }

   }

   public static DataResult checkDecodableModWorldKey(DataResult defaultResult, Dynamic dynamic) {
      Optional optIntId = dynamic.asNumber().result();
      if (optIntId.isPresent()) {
         int dimId = ((Number)optIntId.get()).intValue();
         if (dimId == 2) {
            return DataResult.success(LOTRDimensions.MIDDLE_EARTH_WORLD_KEY);
         }
      }

      return defaultResult;
   }
}
