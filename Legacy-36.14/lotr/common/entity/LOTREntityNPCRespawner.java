package lotr.common.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCRespawner;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityNPCRespawner extends Entity {
   public float spawnerSpin;
   public float prevSpawnerSpin;
   private static final int spawnInterval_default = 3600;
   public int spawnInterval = 3600;
   public int noPlayerRange = 24;
   public Class spawnClass1;
   public Class spawnClass2;
   public int checkHorizontalRange = 8;
   public int checkVerticalMin = -4;
   public int checkVerticalMax = 4;
   public int spawnCap = 4;
   public int spawnHorizontalRange = 4;
   public int spawnVerticalMin = -2;
   public int spawnVerticalMax = 2;
   public int homeRange = -1;
   private boolean setHomePosFromSpawn = false;
   public int mountSetting = 0;
   public int blockEnemySpawns = 0;
   public static final int MAX_SPAWN_BLOCK_RANGE = 64;

   public LOTREntityNPCRespawner(World world) {
      super(world);
      this.func_70105_a(1.0F, 1.0F);
      this.spawnerSpin = this.field_70146_Z.nextFloat() * 360.0F;
   }

   public void setSpawnClass(Class c) {
      this.spawnClass1 = c;
   }

   public void setSpawnClasses(Class c1, Class c2) {
      this.spawnClass1 = c1;
      this.spawnClass2 = c2;
   }

   public void setCheckRanges(int xz, int y, int y1, int l) {
      this.checkHorizontalRange = xz;
      this.checkVerticalMin = y;
      this.checkVerticalMax = y1;
      this.spawnCap = l;
   }

   public void setSpawnRanges(int xz, int y, int y1, int h) {
      this.spawnHorizontalRange = xz;
      this.spawnVerticalMin = y;
      this.spawnVerticalMax = y1;
      this.homeRange = h;
   }

   public void setHomePosFromSpawn() {
      this.setHomePosFromSpawn = true;
   }

   public boolean hasHomeRange() {
      return this.homeRange >= 0;
   }

   public void setMountSetting(int i) {
      this.mountSetting = i;
   }

   public void toggleMountSetting() {
      if (this.mountSetting == 0) {
         this.mountSetting = 1;
      } else if (this.mountSetting == 1) {
         this.mountSetting = 2;
      } else {
         this.mountSetting = 0;
      }

   }

   public void setNoPlayerRange(int i) {
      this.noPlayerRange = i;
   }

   public boolean blockEnemySpawns() {
      return this.blockEnemySpawns > 0;
   }

   public void setBlockEnemySpawnRange(int i) {
      i = Math.min(i, 64);
      this.blockEnemySpawns = i;
   }

   public void setSpawnInterval(int i) {
      this.spawnInterval = i;
   }

   public void setSpawnIntervalMinutes(int m) {
      int s = m * 60;
      int t = s * 20;
      this.setSpawnInterval(t);
   }

   protected void func_70088_a() {
   }

   public boolean func_82150_aj() {
      if (!this.field_70170_p.field_72995_K) {
         return super.func_82150_aj();
      } else {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         return entityplayer == null || !entityplayer.field_71075_bZ.field_75098_d;
      }
   }

   public void func_70014_b(NBTTagCompound nbt) {
      this.writeSpawnerDataToNBT(nbt);
   }

   public void writeSpawnerDataToNBT(NBTTagCompound nbt) {
      nbt.func_74768_a("SpawnInterval", this.spawnInterval);
      nbt.func_74774_a("NoPlayerRange", (byte)this.noPlayerRange);
      String class1String = "";
      String class2String = "";
      if (this.spawnClass1 != null) {
         class1String = LOTREntities.getStringFromClass(this.spawnClass1);
      }

      if (this.spawnClass2 != null) {
         class2String = LOTREntities.getStringFromClass(this.spawnClass2);
      }

      nbt.func_74778_a("SpawnClass1", class1String == null ? "" : class1String);
      nbt.func_74778_a("SpawnClass2", class2String == null ? "" : class2String);
      nbt.func_74774_a("CheckHorizontal", (byte)this.checkHorizontalRange);
      nbt.func_74774_a("CheckVerticalMin", (byte)this.checkVerticalMin);
      nbt.func_74774_a("CheckVerticalMax", (byte)this.checkVerticalMax);
      nbt.func_74774_a("SpawnCap", (byte)this.spawnCap);
      nbt.func_74774_a("SpawnHorizontal", (byte)this.spawnHorizontalRange);
      nbt.func_74774_a("SpawnVerticalMin", (byte)this.spawnVerticalMin);
      nbt.func_74774_a("SpawnVerticalMax", (byte)this.spawnVerticalMax);
      nbt.func_74774_a("HomeRange", (byte)this.homeRange);
      nbt.func_74757_a("HomeSpawn", this.setHomePosFromSpawn);
      nbt.func_74774_a("MountSetting", (byte)this.mountSetting);
      nbt.func_74774_a("BlockEnemy", (byte)this.blockEnemySpawns);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.readSpawnerDataFromNBT(nbt);
   }

   public void readSpawnerDataFromNBT(NBTTagCompound nbt) {
      this.spawnInterval = nbt.func_74762_e("SpawnInterval");
      if (this.spawnInterval <= 0) {
         this.spawnInterval = 3600;
      }

      this.noPlayerRange = nbt.func_74771_c("NoPlayerRange");
      this.spawnClass1 = LOTREntities.getClassFromString(nbt.func_74779_i("SpawnClass1"));
      this.spawnClass2 = LOTREntities.getClassFromString(nbt.func_74779_i("SpawnClass2"));
      if (this.spawnClass1 != null && !LOTREntityNPC.class.isAssignableFrom(this.spawnClass1)) {
         this.spawnClass1 = null;
      }

      if (this.spawnClass2 != null && !LOTREntityNPC.class.isAssignableFrom(this.spawnClass2)) {
         this.spawnClass2 = null;
      }

      this.checkHorizontalRange = nbt.func_74771_c("CheckHorizontal");
      this.checkVerticalMin = nbt.func_74771_c("CheckVerticalMin");
      this.checkVerticalMax = nbt.func_74771_c("CheckVerticalMax");
      this.spawnCap = nbt.func_74771_c("SpawnCap");
      this.spawnHorizontalRange = nbt.func_74771_c("SpawnHorizontal");
      this.spawnVerticalMin = nbt.func_74771_c("SpawnVerticalMin");
      this.spawnVerticalMax = nbt.func_74771_c("SpawnVerticalMax");
      this.homeRange = nbt.func_74771_c("HomeRange");
      this.setHomePosFromSpawn = nbt.func_74767_n("HomeSpawn");
      this.mountSetting = nbt.func_74771_c("MountSetting");
      this.blockEnemySpawns = nbt.func_74771_c("BlockEnemy");
   }

   public void onBreak() {
      this.field_70170_p.func_72956_a(this, Blocks.field_150359_w.field_149762_H.func_150495_a(), (Blocks.field_150359_w.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150359_w.field_149762_H.func_150494_d() * 0.8F);
      this.field_70170_p.func_72960_a(this, (byte)16);
      this.func_70106_y();
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 16) {
         for(int l = 0; l < 16; ++l) {
            double d = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            double d1 = this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O;
            double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            this.field_70170_p.func_72869_a("iconcrack_" + Item.func_150891_b(LOTRMod.npcRespawner), d, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.prevSpawnerSpin = this.spawnerSpin;
      this.spawnerSpin += 6.0F;
      this.prevSpawnerSpin = MathHelper.func_76142_g(this.prevSpawnerSpin);
      this.spawnerSpin = MathHelper.func_76142_g(this.spawnerSpin);
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      if (!this.field_70170_p.field_72995_K && this.field_70173_aa % this.spawnInterval == 0 && (this.spawnClass1 != null || this.spawnClass2 != null)) {
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         int minX = i - this.checkHorizontalRange;
         int minY = j + this.checkVerticalMin;
         int minZ = k - this.checkHorizontalRange;
         int maxX = i + this.checkHorizontalRange;
         int maxY = j + this.checkVerticalMax;
         int maxZ = k + this.checkHorizontalRange;
         if (this.field_70170_p.func_72904_c(minX, minY, minZ, maxX, maxY, maxZ) && this.field_70170_p.func_72977_a((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, (double)this.noPlayerRange) == null) {
            AxisAlignedBB checkAABB = AxisAlignedBB.func_72330_a((double)minX, (double)minY, (double)minZ, (double)(maxX + 1), (double)(maxY + 1), (double)(maxZ + 1));
            IEntitySelector checkSelector = new IEntitySelector() {
               public boolean func_82704_a(Entity entity) {
                  if (!entity.func_70089_S()) {
                     return false;
                  } else {
                     Class entityClass = entity.getClass();
                     return LOTREntityNPCRespawner.this.spawnClass1 != null && LOTREntityNPCRespawner.this.spawnClass1.isAssignableFrom(entityClass) || LOTREntityNPCRespawner.this.spawnClass2 != null && LOTREntityNPCRespawner.this.spawnClass2.isAssignableFrom(entityClass);
                  }
               }
            };
            List nearbyEntities = this.field_70170_p.func_82733_a(EntityLiving.class, checkAABB, checkSelector);
            int entities = nearbyEntities.size();
            if (entities < this.spawnCap) {
               int attempts = 16;

               for(int l = 0; l < attempts; ++l) {
                  int spawnX = i + MathHelper.func_76136_a(this.field_70146_Z, -this.spawnHorizontalRange, this.spawnHorizontalRange);
                  int spawnY = j + MathHelper.func_76136_a(this.field_70146_Z, this.spawnVerticalMin, this.spawnVerticalMax);
                  int spawnZ = k + MathHelper.func_76136_a(this.field_70146_Z, -this.spawnHorizontalRange, this.spawnHorizontalRange);
                  Block belowBlock = this.field_70170_p.func_147439_a(spawnX, spawnY - 1, spawnZ);
                  this.field_70170_p.func_72805_g(spawnX, spawnY - 1, spawnZ);
                  boolean belowSolid = belowBlock.isSideSolid(this.field_70170_p, spawnX, spawnY - 1, spawnZ, ForgeDirection.UP);
                  if (belowSolid && !this.field_70170_p.func_147439_a(spawnX, spawnY, spawnZ).func_149721_r() && !this.field_70170_p.func_147439_a(spawnX, spawnY + 1, spawnZ).func_149721_r()) {
                     Class entityClass = null;
                     if (this.spawnClass1 != null && this.spawnClass2 != null) {
                        entityClass = this.field_70146_Z.nextInt(3) == 0 ? this.spawnClass2 : this.spawnClass1;
                     } else if (this.spawnClass1 != null) {
                        entityClass = this.spawnClass1;
                     } else if (this.spawnClass2 != null) {
                        entityClass = this.spawnClass2;
                     }

                     String entityName = LOTREntities.getStringFromClass(entityClass);
                     LOTREntityNPC entity = (LOTREntityNPC)EntityList.func_75620_a(entityName, this.field_70170_p);
                     entity.func_70012_b((double)spawnX + 0.5D, (double)spawnY, (double)spawnZ + 0.5D, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
                     entity.isNPCPersistent = true;
                     entity.liftSpawnRestrictions = true;
                     if (entity.func_70601_bi()) {
                        entity.liftSpawnRestrictions = false;
                        this.field_70170_p.func_72838_d(entity);
                        if (this.mountSetting == 0) {
                           entity.spawnRidingHorse = false;
                        } else if (this.mountSetting == 1) {
                           entity.spawnRidingHorse = true;
                        }

                        entity.func_110161_a((IEntityLivingData)null);
                        if (this.hasHomeRange()) {
                           if (this.setHomePosFromSpawn) {
                              entity.func_110171_b(spawnX, spawnY, spawnZ, this.homeRange);
                           } else {
                              entity.func_110171_b(i, j, k, this.homeRange);
                           }
                        } else {
                           entity.func_110177_bN();
                        }

                        ++entities;
                        if (entities >= this.spawnCap) {
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public boolean func_130002_c(EntityPlayer entityplayer) {
      if (entityplayer.field_71075_bZ.field_75098_d) {
         if (!this.field_70170_p.field_72995_K) {
            LOTRPacketNPCRespawner packet = new LOTRPacketNPCRespawner(this);
            LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_70067_L() {
      if (!this.field_70170_p.field_72995_K) {
         return false;
      } else {
         EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
         return entityplayer == null ? false : entityplayer.field_71075_bZ.field_75098_d;
      }
   }

   public void func_70108_f(Entity entity) {
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.npcRespawner);
   }

   public AxisAlignedBB createSpawnBlockRegion() {
      if (!this.blockEnemySpawns()) {
         return null;
      } else {
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         int range = this.blockEnemySpawns;
         return AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).func_72314_b((double)range, (double)range, (double)range);
      }
   }

   public boolean isEnemySpawnBlocked(LOTREntityNPC npc) {
      return this.isEnemySpawnBlocked(npc.getFaction());
   }

   public boolean isEnemySpawnBlocked(LOTRFaction spawnFaction) {
      LOTRFaction faction2;
      if (this.spawnClass1 != null) {
         faction2 = ((LOTREntityNPC)EntityList.func_75620_a(LOTREntities.getStringFromClass(this.spawnClass1), this.field_70170_p)).getFaction();
         if (faction2 != null && faction2.isBadRelation(spawnFaction)) {
            return true;
         }
      }

      if (this.spawnClass2 != null) {
         faction2 = ((LOTREntityNPC)EntityList.func_75620_a(LOTREntities.getStringFromClass(this.spawnClass2), this.field_70170_p)).getFaction();
         if (faction2 != null && faction2.isBadRelation(spawnFaction)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSpawnBlocked(LOTREntityNPC npc) {
      return isSpawnBlocked(npc, npc.getFaction());
   }

   public static boolean isSpawnBlocked(Entity entity, LOTRFaction spawnFaction) {
      World world = entity.field_70170_p;
      int i = MathHelper.func_76128_c(entity.field_70165_t);
      int j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(entity.field_70161_v);
      AxisAlignedBB originBB = AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1));
      int range = 64;
      AxisAlignedBB searchBB = originBB.func_72314_b((double)range, (double)range, (double)range);
      List spawners = world.func_72872_a(LOTREntityNPCRespawner.class, searchBB);
      if (!spawners.isEmpty()) {
         Iterator var10 = spawners.iterator();

         while(var10.hasNext()) {
            Object obj = var10.next();
            LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)obj;
            if (spawner.blockEnemySpawns()) {
               AxisAlignedBB spawnBlockBB = spawner.createSpawnBlockRegion();
               if (spawnBlockBB.func_72326_a(searchBB) && spawnBlockBB.func_72326_a(originBB) && spawner.isEnemySpawnBlocked(spawnFaction)) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
