package lotr.common.tileentity;

import java.util.ArrayList;
import java.util.List;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRMobSpawnerCondition;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class LOTRTileEntityMobSpawner extends TileEntity {
   public int delay = -1;
   private String entityClassName = "";
   public double yaw;
   public double prevYaw = 0.0D;
   private Entity spawnedMob;
   public int active = 1;
   public boolean spawnsPersistentNPCs = false;
   public int minSpawnDelay = 200;
   public int maxSpawnDelay = 800;
   public int nearbyMobLimit = 6;
   public int nearbyMobCheckRange = 8;
   public int requiredPlayerRange = 16;
   public int maxSpawnCount = 4;
   public int maxSpawnRange = 4;
   public int maxSpawnRangeVertical = 1;
   public int maxHealth = 20;
   public int navigatorRange = 16;
   public float moveSpeed = 0.2F;
   public float attackDamage = 2.0F;
   private NBTTagCompound customSpawnData;

   public LOTRTileEntityMobSpawner() {
      this.delay = 20;
   }

   public String getEntityClassName() {
      return this.entityClassName;
   }

   public void setEntityClassID(int i) {
      this.setEntityClassName(LOTREntities.getStringFromID(i));
   }

   public void setEntityClassName(String s) {
      this.entityClassName = s;
      if (!this.field_145850_b.field_72995_K) {
         Entity entity = EntityList.func_75620_a(this.entityClassName, this.field_145850_b);
         if (entity instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving)entity;
            if (entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111267_a) != null) {
               this.maxHealth = (int)entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111267_a).func_111125_b();
            }

            if (entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111265_b) != null) {
               this.navigatorRange = (int)entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111265_b).func_111125_b();
            }

            if (entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111263_d) != null) {
               this.moveSpeed = (float)entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111263_d).func_111125_b();
            }

            if (entityliving.func_110140_aT().func_111151_a(LOTREntityNPC.npcAttackDamage) != null) {
               this.attackDamage = (float)entityliving.func_110140_aT().func_111151_a(LOTREntityNPC.npcAttackDamage).func_111125_b();
            }
         }
      }

   }

   public boolean anyPlayerInRange() {
      return this.field_145850_b.func_72977_a((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D, (double)this.requiredPlayerRange) != null;
   }

   public boolean isActive() {
      if (this.active == 1) {
         return true;
      } else {
         return this.active == 2 ? this.field_145850_b.func_72864_z(this.field_145851_c, this.field_145848_d, this.field_145849_e) : false;
      }
   }

   public void func_145845_h() {
      if (this.anyPlayerInRange() && this.isActive()) {
         if (this.field_145850_b.field_72995_K) {
            double d = (double)((float)this.field_145851_c + this.field_145850_b.field_73012_v.nextFloat());
            double d1 = (double)((float)this.field_145848_d + this.field_145850_b.field_73012_v.nextFloat());
            double d2 = (double)((float)this.field_145849_e + this.field_145850_b.field_73012_v.nextFloat());
            this.field_145850_b.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
            this.field_145850_b.func_72869_a("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
            if (this.delay > 0) {
               --this.delay;
            }

            this.prevYaw = this.yaw;
            this.yaw = (this.yaw + (double)(1000.0F / ((float)this.delay + 200.0F))) % 360.0D;
         } else {
            if (this.delay == -1) {
               this.updateDelay();
            }

            if (this.delay > 0) {
               --this.delay;
               return;
            }

            boolean needsDelayUpdate = false;

            for(int i = 0; i < this.maxSpawnCount; ++i) {
               Entity entity = EntityList.func_75620_a(this.entityClassName, this.field_145850_b);
               if (entity == null) {
                  return;
               }

               List nearbyEntitiesList = this.field_145850_b.func_72872_a(entity.getClass(), AxisAlignedBB.func_72330_a((double)this.field_145851_c, (double)this.field_145848_d, (double)this.field_145849_e, (double)(this.field_145851_c + 1), (double)(this.field_145848_d + 1), (double)(this.field_145849_e + 1)).func_72314_b((double)this.nearbyMobCheckRange, (double)this.nearbyMobCheckRange, (double)this.nearbyMobCheckRange));
               List nearbySameEntitiesList = new ArrayList();

               int nearbyEntities;
               for(nearbyEntities = 0; nearbyEntities < nearbyEntitiesList.size(); ++nearbyEntities) {
                  Entity nearbyEntity = (Entity)nearbyEntitiesList.get(nearbyEntities);
                  if (nearbyEntity.getClass() == entity.getClass()) {
                     nearbySameEntitiesList.add(nearbyEntity);
                  }
               }

               nearbyEntities = nearbySameEntitiesList.size();
               if (nearbyEntities >= this.nearbyMobLimit) {
                  this.updateDelay();
                  return;
               }

               if (entity != null) {
                  double d = (double)this.field_145851_c + (this.field_145850_b.field_73012_v.nextDouble() - this.field_145850_b.field_73012_v.nextDouble()) * (double)this.maxSpawnRange;
                  double d1 = (double)this.field_145848_d + (this.field_145850_b.field_73012_v.nextDouble() - this.field_145850_b.field_73012_v.nextDouble()) * (double)this.maxSpawnRangeVertical;
                  double d2 = (double)this.field_145849_e + (this.field_145850_b.field_73012_v.nextDouble() - this.field_145850_b.field_73012_v.nextDouble()) * (double)this.maxSpawnRange;
                  EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
                  entity.func_70012_b(d, d1, d2, this.field_145850_b.field_73012_v.nextFloat() * 360.0F, 0.0F);
                  if (entityliving instanceof LOTREntityNPC) {
                     ((LOTREntityNPC)entityliving).isNPCPersistent = this.spawnsPersistentNPCs;
                     ((LOTREntityNPC)entityliving).liftSpawnRestrictions = true;
                  }

                  if (entity instanceof LOTRMobSpawnerCondition) {
                     ((LOTRMobSpawnerCondition)entity).setSpawningFromMobSpawner(true);
                  }

                  if (entityliving == null || entityliving.func_70601_bi()) {
                     if (entityliving instanceof LOTREntityNPC) {
                        ((LOTREntityNPC)entityliving).liftSpawnRestrictions = false;
                     }

                     if (entity instanceof LOTRMobSpawnerCondition) {
                        ((LOTRMobSpawnerCondition)entity).setSpawningFromMobSpawner(false);
                     }

                     this.writeNBTTagsTo(entity);
                     if (entity instanceof LOTREntityNPC) {
                        ((LOTREntityNPC)entity).onArtificalSpawn();
                     }

                     this.field_145850_b.func_72838_d(entity);
                     this.field_145850_b.func_72926_e(2004, this.field_145851_c, this.field_145848_d, this.field_145849_e, 0);
                     if (entityliving != null) {
                        entityliving.func_70656_aK();
                     }

                     needsDelayUpdate = true;
                     ++nearbyEntities;
                     if (nearbyEntities >= this.nearbyMobLimit) {
                        break;
                     }
                  }
               }
            }

            if (needsDelayUpdate) {
               this.updateDelay();
            }
         }

         super.func_145845_h();
      }

   }

   public void writeNBTTagsTo(Entity entity) {
      if (entity instanceof EntityLiving && entity.field_70170_p != null) {
         EntityLiving entityliving = (EntityLiving)entity;
         if (!this.field_145850_b.field_72995_K) {
            if (entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111267_a) != null) {
               entityliving.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)this.maxHealth);
            }

            if (entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111265_b) != null) {
               entityliving.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a((double)this.navigatorRange);
            }

            if (entityliving.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111263_d) != null) {
               entityliving.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a((double)this.moveSpeed);
            }

            if (entityliving.func_110140_aT().func_111151_a(LOTREntityNPC.npcAttackDamage) != null) {
               entityliving.func_110148_a(LOTREntityNPC.npcAttackDamage).func_111128_a((double)this.attackDamage);
            }
         }

         entityliving.func_110161_a((IEntityLivingData)null);
         if (this.customSpawnData != null) {
            entityliving.func_70020_e(this.customSpawnData);
         }
      }

   }

   private void updateDelay() {
      if (this.maxSpawnDelay <= this.minSpawnDelay) {
         this.delay = this.minSpawnDelay;
      } else {
         this.delay = this.minSpawnDelay + this.field_145850_b.field_73012_v.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
      }

      this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q(), 1, 0);
   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      if (nbt.func_150297_b("EntityID", 3)) {
         int id = nbt.func_74762_e("EntityID");
         this.entityClassName = LOTREntities.getStringFromID(id);
      } else {
         this.entityClassName = nbt.func_74779_i("EntityID");
      }

      this.delay = nbt.func_74765_d("Delay");
      if (nbt.func_74764_b("MinSpawnDelay")) {
         this.minSpawnDelay = nbt.func_74765_d("MinSpawnDelay");
         this.maxSpawnDelay = nbt.func_74765_d("MaxSpawnDelay");
         this.maxSpawnCount = nbt.func_74765_d("MaxSpawnCount");
      }

      if (nbt.func_74764_b("NearbyMobLimit")) {
         this.nearbyMobLimit = nbt.func_74765_d("NearbyMobLimit");
         this.requiredPlayerRange = nbt.func_74765_d("RequiredPlayerRange");
      }

      if (nbt.func_74764_b("MaxSpawnRange")) {
         this.maxSpawnRange = nbt.func_74765_d("MaxSpawnRange");
      }

      if (nbt.func_74764_b("MaxSpawnRangeVertical")) {
         this.maxSpawnRangeVertical = nbt.func_74765_d("MaxSpawnRangeVertical");
      }

      if (nbt.func_74764_b("SpawnsPersistentNPCs")) {
         this.spawnsPersistentNPCs = nbt.func_74767_n("SpawnsPersistentNPCs");
         this.active = nbt.func_74771_c("ActiveMode");
         this.nearbyMobCheckRange = nbt.func_74765_d("MobCheckRange");
      }

      if (nbt.func_74764_b("MaxHealth")) {
         this.maxHealth = nbt.func_74765_d("MaxHealth");
         this.navigatorRange = nbt.func_74765_d("NavigatorRange");
         this.moveSpeed = nbt.func_74760_g("MoveSpeed");
         this.attackDamage = nbt.func_74760_g("AttackDamage");
      }

      if (nbt.func_74764_b("CustomSpawnData")) {
         this.customSpawnData = nbt.func_74775_l("CustomSpawnData");
      }

      if (this.field_145850_b != null && this.field_145850_b.field_72995_K) {
         this.spawnedMob = null;
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74778_a("EntityID", this.getEntityClassName());
      nbt.func_74777_a("Delay", (short)this.delay);
      nbt.func_74777_a("MinSpawnDelay", (short)this.minSpawnDelay);
      nbt.func_74777_a("MaxSpawnDelay", (short)this.maxSpawnDelay);
      nbt.func_74777_a("MaxSpawnCount", (short)this.maxSpawnCount);
      nbt.func_74777_a("NearbyMobLimit", (short)this.nearbyMobLimit);
      nbt.func_74777_a("RequiredPlayerRange", (short)this.requiredPlayerRange);
      nbt.func_74777_a("MaxSpawnRange", (short)this.maxSpawnRange);
      nbt.func_74777_a("MaxSpawnRangeVertical", (short)this.maxSpawnRangeVertical);
      nbt.func_74757_a("SpawnsPersistentNPCs", this.spawnsPersistentNPCs);
      nbt.func_74774_a("ActiveMode", (byte)this.active);
      nbt.func_74777_a("MobCheckRange", (short)this.nearbyMobCheckRange);
      nbt.func_74777_a("MaxHealth", (short)this.maxHealth);
      nbt.func_74777_a("NavigatorRange", (short)this.navigatorRange);
      nbt.func_74776_a("MoveSpeed", this.moveSpeed);
      nbt.func_74776_a("AttackDamage", this.attackDamage);
      if (this.customSpawnData != null) {
         nbt.func_74782_a("CustomSpawnData", this.customSpawnData);
      }

   }

   public Entity getMobEntity(World world) {
      if (this.spawnedMob == null) {
         Entity entity = EntityList.func_75620_a(this.entityClassName, world);
         if (entity instanceof LOTREntityNPC) {
            ((LOTREntityNPC)entity).onArtificalSpawn();
         }

         this.writeNBTTagsTo(entity);
         this.spawnedMob = entity;
      }

      return this.spawnedMob;
   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.func_145841_b(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.func_145839_a(data);
   }

   public boolean func_145842_c(int i, int j) {
      if (i == 1 && this.field_145850_b.field_72995_K) {
         this.delay = this.minSpawnDelay;
         return true;
      } else if (i == 2 && this.field_145850_b.field_72995_K) {
         this.delay = -1;
         return true;
      } else {
         return false;
      }
   }
}
