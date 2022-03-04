package lotr.common.world.structure2;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRConfig;
import lotr.common.world.structure.LOTRWorldGenStructureBase;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Unload;

public class LOTRStructureTimelapse {
   private static List allThreads = Collections.synchronizedList(new ArrayList());

   public LOTRStructureTimelapse() {
      FMLCommonHandler.instance().bus().register(this);
      MinecraftForge.EVENT_BUS.register(this);
   }

   public static void start(WorldGenerator gen, World world, int i, int j, int k) {
      (new LOTRStructureTimelapse.ThreadTimelapse(gen, world, i, j, k)).start();
   }

   @SubscribeEvent
   public void onWorldUnload(Unload event) {
      World world = event.world;
      Iterator var3 = allThreads.iterator();

      while(var3.hasNext()) {
         LOTRStructureTimelapse.ThreadTimelapse thr = (LOTRStructureTimelapse.ThreadTimelapse)var3.next();
         thr.interrupt();
      }

      allThreads.clear();
   }

   public static class ThreadTimelapse extends Thread {
      private WorldGenerator structureGen;
      private World theWorld;
      private int posX;
      private int posY;
      private int posZ;

      public ThreadTimelapse(WorldGenerator gen, World world, int i, int j, int k) {
         this.structureGen = gen;
         this.theWorld = world;
         this.posX = i;
         this.posY = j;
         this.posZ = k;
      }

      public void start() {
         this.setDaemon(true);
         super.start();
         LOTRStructureTimelapse.allThreads.add(this);
      }

      public void run() {
         if (this.structureGen instanceof LOTRWorldGenStructureBase2) {
            LOTRWorldGenStructureBase2 str2 = (LOTRWorldGenStructureBase2)this.structureGen;
            str2.threadTimelapse = this;
            str2.generateWithSetRotation(this.theWorld, this.theWorld.field_73012_v, this.posX, this.posY, this.posZ, str2.usingPlayerRotation());
         } else if (this.structureGen instanceof LOTRWorldGenStructureBase) {
            LOTRWorldGenStructureBase str = (LOTRWorldGenStructureBase)this.structureGen;
            str.threadTimelapse = this;
            str.func_76484_a(this.theWorld, this.theWorld.field_73012_v, this.posX, this.posY, this.posZ);
         }

         LOTRStructureTimelapse.allThreads.remove(this);
      }

      public void onBlockSet() {
         if (LOTRConfig.strTimelapse) {
            try {
               sleep((long)LOTRConfig.strTimelapseInterval);
            } catch (InterruptedException var2) {
               var2.printStackTrace();
            }
         }

      }
   }
}
