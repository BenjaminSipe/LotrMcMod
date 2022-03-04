package lotr.common.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;

public class SaveUtil {
   private static final FolderName LOTR_FOLDER = new FolderName("lotr");

   public static File getOrCreateLOTRDir(ServerWorld world) {
      MinecraftServer server = world.func_73046_m();
      File dir = server.func_240776_a_(LOTR_FOLDER).toFile();
      if (!dir.exists()) {
         dir.mkdirs();
      }

      return dir;
   }

   public static CompoundNBT loadNBTFromFile(File file) throws FileNotFoundException, IOException {
      if (file.exists()) {
         FileInputStream fis = new FileInputStream(file);
         CompoundNBT nbt = CompressedStreamTools.func_74796_a(fis);
         fis.close();
         return nbt;
      } else {
         return new CompoundNBT();
      }
   }

   public static void saveNBTToFile(File file, CompoundNBT nbt) throws FileNotFoundException, IOException {
      CompressedStreamTools.func_74799_a(nbt, new FileOutputStream(file));
   }
}
