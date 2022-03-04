package lotr.common.entity.animal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockBerryBush;
import lotr.common.entity.AnimalJarUpdater;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.LOTRScarecrows;
import lotr.common.inventory.LOTREntityInventory;
import lotr.common.item.LOTRValuableItems;
import lotr.common.world.biome.LOTRBiomeGenFarHarad;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityBird extends EntityLiving implements LOTRAmbientCreature, LOTRRandomSkinEntity, AnimalJarUpdater {
   private ChunkCoordinates currentFlightTarget;
   private int flightTargetTime = 0;
   private static final int flightTargetTimeMax = 400;
   public int flapTime = 0;
   private LOTREntityInventory birdInv = new LOTREntityInventory("BirdItems", this, 9);
   private EntityItem stealTargetItem;
   private EntityPlayer stealTargetPlayer;
   private static final int maxStealAmount = 4;
   private int stolenTime = 0;
   private static final int stolenTimeMax = 200;
   private boolean stealingCrops = false;

   public LOTREntityBird(World world) {
      super(world);
      this.func_70105_a(0.5F, 0.5F);
      this.field_70714_bg.func_75776_a(0, new EntityAIWatchClosest(this, EntityPlayer.class, 12.0F, 0.05F));
      this.field_70714_bg.func_75776_a(1, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F, 0.1F));
      this.field_70714_bg.func_75776_a(2, new EntityAILookIdle(this));
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, (byte)0);
      this.field_70180_af.func_75682_a(17, (byte)1);
   }

   public LOTREntityBird.BirdType getBirdType() {
      int i = this.field_70180_af.func_75683_a(16);
      if (i < 0 || i >= LOTREntityBird.BirdType.values().length) {
         i = 0;
      }

      return LOTREntityBird.BirdType.values()[i];
   }

   public void setBirdType(LOTREntityBird.BirdType type) {
      this.setBirdType(type.ordinal());
   }

   public void setBirdType(int i) {
      this.field_70180_af.func_75692_b(16, (byte)i);
   }

   public boolean isBirdStill() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setBirdStill(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public String getBirdTextureDir() {
      return this.getBirdType().textureDir;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(MathHelper.func_82716_a(this.field_70146_Z, 0.08D, 0.13D));
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
      if (biome instanceof LOTRBiomeGenFarHarad) {
         if (this.field_70146_Z.nextInt(8) == 0) {
            this.setBirdType(LOTREntityBird.BirdType.CROW);
         } else {
            this.setBirdType(LOTREntityBird.BirdType.FAR_HARAD);
         }
      } else if (this.field_70146_Z.nextInt(6) == 0) {
         this.setBirdType(LOTREntityBird.BirdType.CROW);
      } else if (this.field_70146_Z.nextInt(10) == 0) {
         this.setBirdType(LOTREntityBird.BirdType.MAGPIE);
      } else {
         this.setBirdType(LOTREntityBird.BirdType.COMMON);
      }

      return data;
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   public boolean func_70104_M() {
      return false;
   }

   protected void func_82167_n(Entity entity) {
   }

   protected void func_85033_bc() {
   }

   protected boolean func_70650_aV() {
      return true;
   }

   protected boolean canStealItems() {
      return this.getBirdType().canSteal;
   }

   protected boolean isStealable(ItemStack itemstack) {
      LOTREntityBird.BirdType type = this.getBirdType();
      Item item = itemstack.func_77973_b();
      if (type == LOTREntityBird.BirdType.COMMON) {
         return item instanceof IPlantable && ((IPlantable)item).getPlantType(this.field_70170_p, -1, -1, -1) == EnumPlantType.Crop;
      } else if (type != LOTREntityBird.BirdType.CROW) {
         return type == LOTREntityBird.BirdType.MAGPIE ? LOTRValuableItems.canMagpieSteal(itemstack) : false;
      } else {
         return item instanceof ItemFood || LOTRMod.isOreNameEqual(itemstack, "bone");
      }
   }

   public ItemStack getStolenItem() {
      return this.func_71124_b(4);
   }

   public void setStolenItem(ItemStack itemstack) {
      this.func_70062_b(4, itemstack);
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.isBirdStill()) {
         this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
         this.field_70163_u = (double)MathHelper.func_76128_c(this.field_70163_u);
         if (this.field_70170_p.field_72995_K) {
            if (this.field_70146_Z.nextInt(200) == 0) {
               this.flapTime = 40;
            }

            if (this.flapTime > 0) {
               --this.flapTime;
            }
         }
      } else {
         this.field_70181_x *= 0.6D;
         if (this.field_70170_p.field_72995_K) {
            this.flapTime = 0;
         }
      }

   }

   public void updateInAnimalJar() {
      this.setBirdStill(false);
   }

   protected void func_70619_bc() {
      super.func_70619_bc();
      if (this.getStolenItem() != null) {
         ++this.stolenTime;
         if (this.stolenTime >= 200) {
            this.setStolenItem((ItemStack)null);
            this.stolenTime = 0;
         }
      }

      if (this.isBirdStill()) {
         if (!this.canBirdSit()) {
            this.setBirdStill(false);
         } else if (this.field_70146_Z.nextInt(400) == 0 || this.field_70170_p.func_72890_a(this, 6.0D) != null) {
            this.setBirdStill(false);
         }
      } else {
         double speed;
         if (this.canStealItems() && !this.stealingCrops && this.stealTargetItem == null && this.stealTargetPlayer == null && !this.birdInv.isFull() && this.field_70146_Z.nextInt(100) == 0) {
            speed = 16.0D;
            List players = this.field_70170_p.func_82733_a(EntityPlayer.class, this.field_70121_D.func_72314_b(speed, speed, speed), new IEntitySelector() {
               public boolean func_82704_a(Entity e) {
                  if (e instanceof EntityPlayer) {
                     EntityPlayer entityplayer = (EntityPlayer)e;
                     if (LOTREntityBird.this.canStealPlayer(entityplayer)) {
                        ChunkCoordinates coords = LOTREntityBird.this.getPlayerFlightTarget(entityplayer);
                        return LOTREntityBird.this.isValidFlightTarget(coords);
                     }
                  }

                  return false;
               }
            });
            if (!players.isEmpty()) {
               this.stealTargetPlayer = (EntityPlayer)players.get(this.field_70146_Z.nextInt(players.size()));
               this.currentFlightTarget = this.getPlayerFlightTarget(this.stealTargetPlayer);
               this.newFlight();
            } else {
               List entityItems = this.field_70170_p.func_82733_a(EntityItem.class, this.field_70121_D.func_72314_b(speed, speed, speed), new IEntitySelector() {
                  public boolean func_82704_a(Entity e) {
                     if (e instanceof EntityItem) {
                        EntityItem eItem = (EntityItem)e;
                        if (LOTREntityBird.this.canStealItem(eItem)) {
                           ChunkCoordinates coords = LOTREntityBird.this.getItemFlightTarget(eItem);
                           return LOTREntityBird.this.isValidFlightTarget(coords);
                        }
                     }

                     return false;
                  }
               });
               if (!entityItems.isEmpty()) {
                  this.stealTargetItem = (EntityItem)entityItems.get(this.field_70146_Z.nextInt(entityItems.size()));
                  this.currentFlightTarget = this.getItemFlightTarget(this.stealTargetItem);
                  this.newFlight();
               }
            }
         }

         int randSlot;
         if (this.stealTargetItem == null && this.stealTargetPlayer == null) {
            int i;
            int j;
            if (this.stealingCrops) {
               if (!LOTRMod.canGrief(this.field_70170_p)) {
                  this.stealingCrops = false;
               } else if (this.currentFlightTarget != null && this.isValidFlightTarget(this.currentFlightTarget)) {
                  i = this.currentFlightTarget.field_71574_a;
                  j = this.currentFlightTarget.field_71572_b;
                  randSlot = this.currentFlightTarget.field_71573_c;
                  if (this.getDistanceSqToFlightTarget() < 1.0D) {
                     if (this.canStealCrops(i, j, randSlot)) {
                        this.eatCropBlock(i, j, randSlot);
                        this.func_85030_a("random.eat", 1.0F, (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F + 1.0F);
                     }

                     this.cancelFlight();
                  } else if (!this.canStealCrops(i, j, randSlot)) {
                     this.cancelFlight();
                  } else if (this.flightTargetTime % 100 == 0 && LOTRScarecrows.anyScarecrowsNearby(this.field_70170_p, i, j, randSlot)) {
                     this.cancelFlight();
                  }
               } else {
                  this.cancelFlight();
               }
            } else {
               if (LOTRMod.canGrief(this.field_70170_p) && !this.stealingCrops && this.field_70146_Z.nextInt(100) == 0) {
                  i = MathHelper.func_76128_c(this.field_70165_t);
                  j = MathHelper.func_76128_c(this.field_70163_u);
                  randSlot = MathHelper.func_76128_c(this.field_70161_v);
                  int range = 16;
                  int yRange = 8;
                  int attempts = 32;

                  for(int l = 0; l < attempts; ++l) {
                     int i1 = i + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
                     int j1 = j + MathHelper.func_76136_a(this.field_70146_Z, -yRange, yRange);
                     int k1 = randSlot + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
                     if (this.canStealCrops(i1, j1, k1) && !LOTRScarecrows.anyScarecrowsNearby(this.field_70170_p, i1, j1, k1)) {
                        this.stealingCrops = true;
                        this.currentFlightTarget = new ChunkCoordinates(i1, j1, k1);
                        this.newFlight();
                        break;
                     }
                  }
               }

               if (!this.stealingCrops) {
                  if (this.currentFlightTarget != null && !this.isValidFlightTarget(this.currentFlightTarget)) {
                     this.cancelFlight();
                  }

                  if (this.currentFlightTarget == null || this.field_70146_Z.nextInt(50) == 0 || this.getDistanceSqToFlightTarget() < 4.0D) {
                     i = MathHelper.func_76128_c(this.field_70165_t);
                     j = MathHelper.func_76128_c(this.field_70163_u);
                     randSlot = MathHelper.func_76128_c(this.field_70161_v);
                     i += this.field_70146_Z.nextInt(16) - this.field_70146_Z.nextInt(16);
                     randSlot += this.field_70146_Z.nextInt(16) - this.field_70146_Z.nextInt(16);
                     j += MathHelper.func_76136_a(this.field_70146_Z, -2, 3);
                     this.currentFlightTarget = new ChunkCoordinates(i, j, randSlot);
                     this.newFlight();
                  }
               }
            }
         } else if (!this.birdInv.isFull() && this.currentFlightTarget != null && this.isValidFlightTarget(this.currentFlightTarget)) {
            if (this.stealTargetItem != null && !this.canStealItem(this.stealTargetItem)) {
               this.cancelFlight();
            } else if (this.stealTargetPlayer != null && !this.canStealPlayer(this.stealTargetPlayer)) {
               this.cancelFlight();
            } else {
               if (this.stealTargetItem != null) {
                  this.currentFlightTarget = this.getItemFlightTarget(this.stealTargetItem);
               } else if (this.stealTargetPlayer != null) {
                  this.currentFlightTarget = this.getPlayerFlightTarget(this.stealTargetPlayer);
               }

               if (this.getDistanceSqToFlightTarget() < 1.0D) {
                  ItemStack stolenItem = null;
                  ItemStack itemstack;
                  if (this.stealTargetItem != null) {
                     ItemStack itemstack = this.stealTargetItem.func_92059_d();
                     ItemStack stealCopy = itemstack.func_77946_l();
                     stealCopy.field_77994_a = MathHelper.func_76136_a(this.field_70146_Z, 1, Math.min(stealCopy.field_77994_a, 4));
                     itemstack = stealCopy.func_77946_l();
                     if (this.birdInv.addItemToInventory(stealCopy)) {
                        itemstack.field_77994_a -= itemstack.field_77994_a - stealCopy.field_77994_a;
                        if (itemstack.field_77994_a <= 0) {
                           this.stealTargetItem.func_70106_y();
                        }

                        stolenItem = itemstack;
                     }
                  } else if (this.stealTargetPlayer != null) {
                     List slots = this.getStealablePlayerSlots(this.stealTargetPlayer);
                     randSlot = (Integer)slots.get(this.field_70146_Z.nextInt(slots.size()));
                     itemstack = this.stealTargetPlayer.field_71071_by.func_70301_a(randSlot);
                     ItemStack stealCopy = itemstack.func_77946_l();
                     stealCopy.field_77994_a = MathHelper.func_76136_a(this.field_70146_Z, 1, Math.min(stealCopy.field_77994_a, 4));
                     ItemStack safeCopy = stealCopy.func_77946_l();
                     if (this.birdInv.addItemToInventory(stealCopy)) {
                        itemstack.field_77994_a -= safeCopy.field_77994_a - stealCopy.field_77994_a;
                        if (itemstack.field_77994_a <= 0) {
                           itemstack = null;
                        }

                        this.stealTargetPlayer.field_71071_by.func_70299_a(randSlot, itemstack);
                        stolenItem = safeCopy;
                     }
                  }

                  if (stolenItem != null) {
                     this.stolenTime = 0;
                     this.setStolenItem(stolenItem);
                     this.func_85030_a("random.pop", 0.5F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                  }

                  this.cancelFlight();
               }
            }
         } else {
            this.cancelFlight();
         }

         if (this.currentFlightTarget != null) {
            speed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
            double d0 = (double)this.currentFlightTarget.field_71574_a + 0.5D - this.field_70165_t;
            double d1 = (double)this.currentFlightTarget.field_71572_b + 0.5D - this.field_70163_u;
            double d2 = (double)this.currentFlightTarget.field_71573_c + 0.5D - this.field_70161_v;
            this.field_70159_w += (Math.signum(d0) * 0.5D - this.field_70159_w) * speed;
            this.field_70181_x += (Math.signum(d1) * 0.8D - this.field_70181_x) * speed;
            this.field_70179_y += (Math.signum(d2) * 0.5D - this.field_70179_y) * speed;
            float f = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0D / 3.141592653589793D) - 90.0F;
            float f1 = MathHelper.func_76142_g(f - this.field_70177_z);
            this.field_70701_bs = 0.5F;
            this.field_70177_z += f1;
            ++this.flightTargetTime;
            if (this.flightTargetTime >= 400) {
               this.cancelFlight();
            }
         }

         if (this.field_70146_Z.nextInt(200) == 0 && this.canBirdSit()) {
            this.setBirdStill(true);
            this.cancelFlight();
         }
      }

   }

   private boolean canBirdSit() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      Block block = this.field_70170_p.func_147439_a(i, j, k);
      Block below = this.field_70170_p.func_147439_a(i, j - 1, k);
      return block.func_149655_b(this.field_70170_p, i, j, k) && below.isSideSolid(this.field_70170_p, i, j - 1, k, ForgeDirection.UP);
   }

   private boolean isValidFlightTarget(ChunkCoordinates coords) {
      int i = coords.field_71574_a;
      int j = coords.field_71572_b;
      int k = coords.field_71573_c;
      if (j >= 1) {
         Block block = this.field_70170_p.func_147439_a(i, j, k);
         return block.func_149655_b(this.field_70170_p, i, j, k);
      } else {
         return false;
      }
   }

   private double getDistanceSqToFlightTarget() {
      double d = (double)this.currentFlightTarget.field_71574_a + 0.5D;
      double d1 = (double)this.currentFlightTarget.field_71572_b + 0.5D;
      double d2 = (double)this.currentFlightTarget.field_71573_c + 0.5D;
      return this.func_70092_e(d, d1, d2);
   }

   private void cancelFlight() {
      this.currentFlightTarget = null;
      this.flightTargetTime = 0;
      this.stealTargetItem = null;
      this.stealTargetPlayer = null;
      this.stealingCrops = false;
   }

   private void newFlight() {
      this.flightTargetTime = 0;
   }

   private boolean canStealItem(EntityItem entity) {
      return entity.func_70089_S() && this.isStealable(entity.func_92059_d());
   }

   private boolean canStealPlayer(EntityPlayer entityplayer) {
      if (!entityplayer.field_71075_bZ.field_75098_d && entityplayer.func_70089_S()) {
         List slots = this.getStealablePlayerSlots(entityplayer);
         return !slots.isEmpty();
      } else {
         return false;
      }
   }

   private List getStealablePlayerSlots(EntityPlayer entityplayer) {
      List slots = new ArrayList();

      for(int i = 0; i <= 8; ++i) {
         if (i == entityplayer.field_71071_by.field_70461_c) {
            ItemStack itemstack = entityplayer.field_71071_by.func_70301_a(i);
            if (itemstack != null && this.isStealable(itemstack)) {
               slots.add(i);
            }
         }
      }

      return slots;
   }

   private ChunkCoordinates getItemFlightTarget(EntityItem entity) {
      int i = MathHelper.func_76128_c(entity.field_70165_t);
      int j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(entity.field_70161_v);
      return new ChunkCoordinates(i, j, k);
   }

   private ChunkCoordinates getPlayerFlightTarget(EntityPlayer entityplayer) {
      int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
      int j = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b + 1.0D);
      int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
      return new ChunkCoordinates(i, j, k);
   }

   private boolean canStealCrops(int i, int j, int k) {
      Block block = this.field_70170_p.func_147439_a(i, j, k);
      if (block instanceof BlockCrops) {
         return true;
      } else if (block instanceof LOTRBlockBerryBush) {
         int meta = this.field_70170_p.func_72805_g(i, j, k);
         return LOTRBlockBerryBush.hasBerries(meta);
      } else {
         return false;
      }
   }

   private void eatCropBlock(int i, int j, int k) {
      Block block = this.field_70170_p.func_147439_a(i, j, k);
      if (block instanceof LOTRBlockBerryBush) {
         int meta = this.field_70170_p.func_72805_g(i, j, k);
         meta = LOTRBlockBerryBush.setHasBerries(meta, false);
         this.field_70170_p.func_72921_c(i, j, k, meta, 3);
      } else {
         this.field_70170_p.func_147468_f(i, j, k);
      }

   }

   protected boolean func_70041_e_() {
      return false;
   }

   protected void func_70069_a(float f) {
   }

   protected void func_70064_a(double d, boolean flag) {
   }

   public boolean func_145773_az() {
      return true;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean flag = super.func_70097_a(damagesource, f);
      if (flag && !this.field_70170_p.field_72995_K && this.isBirdStill()) {
         this.setBirdStill(false);
      }

      return flag;
   }

   protected void func_70628_a(boolean flag, int i) {
      int feathers = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < feathers; ++l) {
         this.func_145779_a(Items.field_151008_G, 1);
      }

   }

   protected void func_82160_b(boolean flag, int i) {
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K) {
         this.setStolenItem((ItemStack)null);
         this.birdInv.dropAllItems();
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setBirdType(nbt.func_74762_e("BirdType"));
      this.setBirdStill(nbt.func_74767_n("BirdStill"));
      this.birdInv.writeToNBT(nbt);
      nbt.func_74777_a("StealTime", (short)this.stolenTime);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("BirdType", this.getBirdType().ordinal());
      nbt.func_74757_a("BirdStill", this.isBirdStill());
      this.birdInv.readFromNBT(nbt);
      this.stolenTime = nbt.func_74765_d("StealTime");
   }

   protected boolean func_70692_ba() {
      return super.func_70692_ba();
   }

   public boolean func_70601_bi() {
      return super.func_70601_bi() ? this.canBirdSpawnHere() : false;
   }

   protected boolean canBirdSpawnHere() {
      return LOTRAmbientSpawnChecks.canSpawn(this, 8, 12, 40, 4, Material.field_151584_j);
   }

   public boolean func_110164_bC() {
      return false;
   }

   protected boolean func_70085_c(EntityPlayer entityplayer) {
      return false;
   }

   public int func_70627_aG() {
      return 60;
   }

   public void func_70642_aH() {
      boolean sound = true;
      if (!this.field_70170_p.func_72935_r()) {
         sound = this.field_70146_Z.nextInt(20) == 0;
      }

      if (sound) {
         super.func_70642_aH();
      }

   }

   protected float func_70599_aP() {
      return 1.0F;
   }

   protected String func_70639_aQ() {
      LOTREntityBird.BirdType type = this.getBirdType();
      return type == LOTREntityBird.BirdType.CROW ? "lotr:bird.crow.say" : "lotr:bird.say";
   }

   protected String func_70621_aR() {
      LOTREntityBird.BirdType type = this.getBirdType();
      return type == LOTREntityBird.BirdType.CROW ? "lotr:bird.crow.hurt" : "lotr:bird.hurt";
   }

   protected String func_70673_aS() {
      LOTREntityBird.BirdType type = this.getBirdType();
      return type == LOTREntityBird.BirdType.CROW ? "lotr:bird.crow.hurt" : "lotr:bird.hurt";
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
   }

   public static enum BirdType {
      COMMON("common", true),
      CROW("crow", true),
      MAGPIE("magpie", true),
      FAR_HARAD("farHarad", true);

      public final String textureDir;
      public final boolean canSteal;

      private BirdType(String s, boolean flag) {
         this.textureDir = s;
         this.canSteal = flag;
      }
   }
}
