package lotr.common.world.spawning;

import cpw.mods.fml.common.eventhandler.Event.Result;
import java.util.Random;
import lotr.common.LOTRLevelData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTravellingTrader;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTRTravellingTraderSpawner {
   private static Random rand = new Random();
   private Class theEntityClass;
   public String entityClassName;
   private int timeUntilTrader;

   public LOTRTravellingTraderSpawner(Class entityClass) {
      this.theEntityClass = entityClass;
      this.entityClassName = LOTREntities.getStringFromClass(this.theEntityClass);
   }

   private static int getRandomTraderTime() {
      float minHours = 0.8F;
      float maxHours = 10.0F;
      return MathHelper.func_76136_a(rand, (int)(minHours * 3600.0F) * 20, (int)(maxHours * 3600.0F) * 20);
   }

   public void writeToNBT(NBTTagCompound nbt) {
      nbt.func_74768_a("TraderTime", this.timeUntilTrader);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      if (nbt.func_74764_b("TraderTime")) {
         this.timeUntilTrader = nbt.func_74762_e("TraderTime");
      } else {
         this.timeUntilTrader = getRandomTraderTime();
      }

   }

   public void performSpawning(World world) {
      if (this.timeUntilTrader > 0) {
         --this.timeUntilTrader;
      } else if (world.field_73012_v.nextInt(1000) == 0) {
         boolean spawned = false;
         LOTREntityNPC entityTrader = (LOTREntityNPC)EntityList.func_75620_a(LOTREntities.getStringFromClass(this.theEntityClass), world);
         LOTRTravellingTrader trader = (LOTRTravellingTrader)entityTrader;

         label57:
         for(int players = 0; players < world.field_73010_i.size(); ++players) {
            EntityPlayer entityplayer = (EntityPlayer)world.field_73010_i.get(players);
            if (LOTRLevelData.getData(entityplayer).getAlignment(entityTrader.getFaction()) >= 0.0F) {
               for(int attempts = 0; attempts < 16; ++attempts) {
                  float angle = world.field_73012_v.nextFloat() * 360.0F;
                  int i = MathHelper.func_76128_c(entityplayer.field_70165_t) + MathHelper.func_76128_c((double)(MathHelper.func_76126_a(angle) * (float)(48 + world.field_73012_v.nextInt(33))));
                  int k = MathHelper.func_76128_c(entityplayer.field_70161_v) + MathHelper.func_76128_c((double)(MathHelper.func_76134_b(angle) * (float)(48 + world.field_73012_v.nextInt(33))));
                  BiomeGenBase biome = world.func_72807_a(i, k);
                  if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnTravellingTrader(this.theEntityClass)) {
                     int j = world.func_72976_f(i, k);
                     Block block = world.func_147439_a(i, j - 1, k);
                     if (j > 62 && (block == biome.field_76752_A || block == biome.field_76753_B) && !world.func_147439_a(i, j, k).func_149721_r() && !world.func_147439_a(i, j + 1, k).func_149721_r()) {
                        entityTrader.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                        entityTrader.liftSpawnRestrictions = true;
                        Result canSpawn = ForgeEventFactory.canEntitySpawn(entityTrader, world, (float)entityTrader.field_70165_t, (float)entityTrader.field_70163_u, (float)entityTrader.field_70161_v);
                        if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT && entityTrader.func_70601_bi()) {
                           entityTrader.liftSpawnRestrictions = false;
                           entityTrader.spawnRidingHorse = false;
                           world.func_72838_d(entityTrader);
                           entityTrader.func_110161_a((IEntityLivingData)null);
                           entityTrader.isNPCPersistent = true;
                           entityTrader.setShouldTraderRespawn(false);
                           trader.startTraderVisiting(entityplayer);
                           spawned = true;
                           this.timeUntilTrader = getRandomTraderTime();
                           LOTRLevelData.markDirty();
                           break label57;
                        }
                     }
                  }
               }
            }
         }

         if (!spawned) {
            this.timeUntilTrader = 200 + world.field_73012_v.nextInt(400);
         }
      }

   }
}
