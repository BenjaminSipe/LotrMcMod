package lotr.common.entity.ai;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRMod;
import lotr.common.LOTRReflection;
import lotr.common.block.LOTRBlockCorn;
import lotr.common.block.LOTRBlockGrapevine;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRFarmhand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityAIFarm extends EntityAIBase {
   private static final int DEPOSIT_THRESHOLD = 16;
   private static final int COLLECT_THRESHOLD = 16;
   private static final int MIN_CHEST_RANGE = 24;
   private LOTREntityNPC theEntity;
   private LOTRFarmhand theEntityFarmer;
   private World theWorld;
   private double moveSpeed;
   private float farmingEfficiency;
   private LOTREntityAIFarm.Action action = null;
   private ChunkCoordinates actionTarget;
   private ChunkCoordinates pathTarget;
   private int pathingTick;
   private int rePathDelay;
   private boolean harvestingSolidBlock;
   private FakePlayer fakePlayer;

   public LOTREntityAIFarm(LOTRFarmhand npc, double d, float f) {
      this.theEntity = (LOTREntityNPC)npc;
      this.theEntityFarmer = npc;
      this.theWorld = this.theEntity.field_70170_p;
      this.moveSpeed = d;
      this.func_75248_a(1);
      if (this.theWorld instanceof WorldServer) {
         this.fakePlayer = FakePlayerFactory.get((WorldServer)this.theWorld, new GameProfile((UUID)null, "LOTRFarming"));
      }

      this.farmingEfficiency = f;
   }

   public boolean func_75250_a() {
      boolean flag = this.shouldFarmhandExecute();
      return flag;
   }

   private boolean shouldFarmhandExecute() {
      if (this.theEntity.hiredNPCInfo.isActive && !this.theEntity.hiredNPCInfo.isGuardMode()) {
         return false;
      } else {
         this.setAppropriateHomeRange((LOTREntityAIFarm.Action)null);
         if (this.theEntity.func_110175_bO() && !this.theEntity.func_110173_bK()) {
            return false;
         } else {
            if (this.theEntity.func_70681_au().nextFloat() < this.farmingEfficiency * 0.1F) {
               LOTREntityAIFarm.TargetPair collectTarget;
               if (this.canDoDepositing()) {
                  collectTarget = this.findTarget(LOTREntityAIFarm.Action.DEPOSITING);
                  if (collectTarget != null) {
                     this.actionTarget = collectTarget.actionTarget;
                     this.pathTarget = collectTarget.pathTarget;
                     this.action = LOTREntityAIFarm.Action.DEPOSITING;
                     return true;
                  }
               }

               if (this.canDoHoeing()) {
                  collectTarget = this.findTarget(LOTREntityAIFarm.Action.HOEING);
                  if (collectTarget != null) {
                     this.actionTarget = collectTarget.actionTarget;
                     this.pathTarget = collectTarget.pathTarget;
                     this.action = LOTREntityAIFarm.Action.HOEING;
                     return true;
                  }
               }

               if (this.canDoPlanting()) {
                  collectTarget = this.findTarget(LOTREntityAIFarm.Action.PLANTING);
                  if (collectTarget != null) {
                     this.actionTarget = collectTarget.actionTarget;
                     this.pathTarget = collectTarget.pathTarget;
                     this.action = LOTREntityAIFarm.Action.PLANTING;
                     return true;
                  }
               }

               if (this.canDoHarvesting()) {
                  collectTarget = this.findTarget(LOTREntityAIFarm.Action.HARVESTING);
                  if (collectTarget != null) {
                     this.actionTarget = collectTarget.actionTarget;
                     this.pathTarget = collectTarget.pathTarget;
                     this.action = LOTREntityAIFarm.Action.HARVESTING;
                     return true;
                  }
               }

               if (this.canDoBonemealing()) {
                  collectTarget = this.findTarget(LOTREntityAIFarm.Action.BONEMEALING);
                  if (collectTarget != null) {
                     this.actionTarget = collectTarget.actionTarget;
                     this.pathTarget = collectTarget.pathTarget;
                     this.action = LOTREntityAIFarm.Action.BONEMEALING;
                     return true;
                  }
               }

               if (this.canDoCollecting()) {
                  collectTarget = this.findTarget(LOTREntityAIFarm.Action.COLLECTING);
                  if (collectTarget != null) {
                     this.actionTarget = collectTarget.actionTarget;
                     this.pathTarget = collectTarget.pathTarget;
                     this.action = LOTREntityAIFarm.Action.COLLECTING;
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   private boolean isFarmingGrapes() {
      IPlantable seed = this.getSeedsToPlant();
      return seed.getPlant(this.theWorld, -1, -1, -1) instanceof LOTRBlockGrapevine;
   }

   private boolean canDoHoeing() {
      return true;
   }

   private boolean canDoPlanting() {
      if (!this.theEntity.hiredNPCInfo.isActive) {
         return true;
      } else {
         ItemStack invSeeds = this.getInventorySeeds();
         return invSeeds != null && invSeeds.field_77994_a > 1;
      }
   }

   private boolean canDoHarvesting() {
      if (!this.theEntity.hiredNPCInfo.isActive) {
         return false;
      } else {
         return this.getInventorySeeds() != null && this.hasSpaceForCrops() && this.getCropForSeed(this.getSeedsToPlant()) != null;
      }
   }

   private boolean canDoDepositing() {
      if (this.theEntity.hiredNPCInfo.isActive) {
         for(int l = 1; l <= 2; ++l) {
            ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(l);
            if (itemstack != null && itemstack.field_77994_a >= 16) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean canDoBonemealing() {
      if (this.theEntity.hiredNPCInfo.isActive) {
         ItemStack invBmeal = this.getInventoryBonemeal();
         return invBmeal != null;
      } else {
         return false;
      }
   }

   private boolean canDoCollecting() {
      if (this.theEntity.hiredNPCInfo.isActive) {
         ItemStack seeds = this.getInventorySeeds();
         if (seeds != null && seeds.field_77994_a <= 16) {
            return true;
         }

         ItemStack bonemeal = this.getInventoryBonemeal();
         if (bonemeal == null || bonemeal != null && bonemeal.field_77994_a <= 16) {
            return true;
         }
      }

      return false;
   }

   private ItemStack getInventorySeeds() {
      if (this.theEntity.hiredNPCInfo.getHiredInventory() == null) {
         return null;
      } else {
         ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(0);
         if (itemstack != null) {
            Item item = itemstack.func_77973_b();
            if (item instanceof IPlantable) {
               IPlantable iplantable = (IPlantable)item;
               if (iplantable.getPlantType(this.theWorld, -1, -1, -1) == EnumPlantType.Crop) {
                  return itemstack;
               }
            }
         }

         return null;
      }
   }

   private IPlantable getSeedsToPlant() {
      if (this.theEntity.hiredNPCInfo.isActive) {
         ItemStack invSeeds = this.getInventorySeeds();
         if (invSeeds != null) {
            return (IPlantable)invSeeds.func_77973_b();
         }
      }

      return this.theEntityFarmer.getUnhiredSeeds();
   }

   private boolean hasSpaceForCrops() {
      if (this.theEntity.hiredNPCInfo.getHiredInventory() == null) {
         return false;
      } else {
         for(int l = 1; l <= 2; ++l) {
            ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(l);
            if (itemstack == null || itemstack.field_77994_a < itemstack.func_77976_d() && itemstack.func_77969_a(this.getCropForSeed(this.getSeedsToPlant()))) {
               return true;
            }
         }

         return false;
      }
   }

   private ItemStack getInventoryBonemeal() {
      if (this.theEntity.hiredNPCInfo.getHiredInventory() == null) {
         return null;
      } else {
         ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(3);
         return itemstack != null && itemstack.func_77973_b() == Items.field_151100_aR && itemstack.func_77960_j() == 15 ? itemstack : null;
      }
   }

   private ItemStack getCropForSeed(IPlantable seed) {
      Block block = seed.getPlant(this.theWorld, -1, -1, -1);
      if (block instanceof BlockCrops) {
         return new ItemStack(LOTRReflection.getCropItem((BlockCrops)block));
      } else if (block instanceof BlockStem) {
         return new ItemStack(LOTRReflection.getStemFruitBlock((BlockStem)block).func_149650_a(0, this.theWorld.field_73012_v, 0), 1, 0);
      } else if (block instanceof LOTRBlockCorn) {
         return new ItemStack(LOTRMod.corn);
      } else {
         return block instanceof LOTRBlockGrapevine ? new ItemStack(((LOTRBlockGrapevine)block).getGrapeItem()) : null;
      }
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.setAppropriateHomeRange(this.action);
   }

   private void setAppropriateHomeRange(LOTREntityAIFarm.Action targetAction) {
      if (this.theEntity.hiredNPCInfo.isActive) {
         int hRange = this.theEntity.hiredNPCInfo.getGuardRange();
         ChunkCoordinates home = this.theEntity.func_110172_bL();
         if (targetAction != null && (targetAction == LOTREntityAIFarm.Action.DEPOSITING || targetAction == LOTREntityAIFarm.Action.COLLECTING) && hRange < 24) {
            hRange = 24;
         }

         this.theEntity.func_110171_b(home.field_71574_a, home.field_71572_b, home.field_71573_c, hRange);
      }

   }

   public boolean func_75253_b() {
      if (this.theEntity.hiredNPCInfo.isActive && !this.theEntity.hiredNPCInfo.isGuardMode()) {
         return false;
      } else if (this.theEntity.func_70661_as().func_75500_f()) {
         return false;
      } else {
         if (this.pathingTick < 200) {
            if (this.action == LOTREntityAIFarm.Action.HOEING) {
               return this.canDoHoeing() && this.isSuitableForHoeing(this.actionTarget);
            }

            if (this.action == LOTREntityAIFarm.Action.PLANTING) {
               return this.canDoPlanting() && this.isSuitableForPlanting(this.actionTarget);
            }

            if (this.action == LOTREntityAIFarm.Action.HARVESTING) {
               return this.canDoHarvesting() && this.isSuitableForHarvesting(this.actionTarget);
            }

            if (this.action == LOTREntityAIFarm.Action.DEPOSITING) {
               return this.canDoDepositing() && this.isSuitableForDepositing(this.actionTarget);
            }

            if (this.action == LOTREntityAIFarm.Action.BONEMEALING) {
               return this.canDoBonemealing() && this.isSuitableForBonemealing(this.actionTarget);
            }

            if (this.action == LOTREntityAIFarm.Action.COLLECTING) {
               return this.canDoCollecting() && this.isSuitableForCollecting(this.actionTarget);
            }
         }

         return false;
      }
   }

   public void func_75251_c() {
      this.action = null;
      this.setAppropriateHomeRange(this.action);
      this.actionTarget = null;
      this.pathTarget = null;
      this.pathingTick = 0;
      this.rePathDelay = 0;
      this.harvestingSolidBlock = false;
   }

   public void func_75246_d() {
      boolean canDoAction = false;
      double distSq = this.theEntity.func_70092_e((double)this.pathTarget.field_71574_a + 0.5D, (double)this.pathTarget.field_71572_b, (double)this.pathTarget.field_71573_c + 0.5D);
      if (this.action != LOTREntityAIFarm.Action.HOEING && this.action != LOTREntityAIFarm.Action.PLANTING) {
         canDoAction = distSq < 9.0D;
      } else {
         int i = MathHelper.func_76128_c(this.theEntity.field_70165_t);
         int j = MathHelper.func_76128_c(this.theEntity.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.theEntity.field_70161_v);
         canDoAction = i == this.pathTarget.field_71574_a && j == this.pathTarget.field_71572_b && k == this.pathTarget.field_71573_c;
      }

      if (!canDoAction) {
         this.theEntity.func_70671_ap().func_75650_a((double)this.actionTarget.field_71574_a + 0.5D, (double)this.actionTarget.field_71572_b + 0.5D, (double)this.actionTarget.field_71573_c + 0.5D, 10.0F, (float)this.theEntity.func_70646_bf());
         --this.rePathDelay;
         if (this.rePathDelay <= 0) {
            this.rePathDelay = 10;
            this.theEntity.func_70661_as().func_75492_a((double)this.pathTarget.field_71574_a + 0.5D, (double)this.pathTarget.field_71572_b, (double)this.pathTarget.field_71573_c + 0.5D, this.moveSpeed);
         }

         ++this.pathingTick;
      } else {
         int l;
         int z;
         int slot;
         int z;
         int l;
         boolean canCollect;
         ItemStack bonemeal;
         if (this.action == LOTREntityAIFarm.Action.HOEING) {
            canCollect = this.isSuitableForHoeing(this.actionTarget);
            if (canCollect) {
               this.theEntity.func_71038_i();
               bonemeal = new ItemStack(Items.field_151019_K);
               int hoeRange = 1;

               for(l = -hoeRange; l <= hoeRange; ++l) {
                  for(z = -hoeRange; z <= hoeRange; ++z) {
                     if (Math.abs(l) + Math.abs(z) <= hoeRange) {
                        slot = this.actionTarget.field_71574_a + l;
                        z = this.actionTarget.field_71573_c + z;
                        l = this.actionTarget.field_71572_b;
                        boolean alreadyChecked = l == 0 && z == 0;
                        if (alreadyChecked || this.isSuitableForHoeing(slot, l, z)) {
                           if (this.isReplaceable(slot, l + 1, z)) {
                              this.theWorld.func_147468_f(slot, l + 1, z);
                           }

                           bonemeal.func_77943_a(this.fakePlayer, this.theWorld, slot, l, z, 1, 0.5F, 0.5F, 0.5F);
                        }
                     }
                  }
               }
            }
         } else if (this.action == LOTREntityAIFarm.Action.PLANTING) {
            canCollect = this.isSuitableForPlanting(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
            if (canCollect) {
               this.theEntity.func_71038_i();
               IPlantable seed = this.getSeedsToPlant();
               Block plant = seed.getPlant(this.theWorld, this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
               l = seed.getPlantMetadata(this.theWorld, this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
               this.theWorld.func_147465_d(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c, plant, l, 3);
               if (this.theEntity.hiredNPCInfo.isActive) {
                  this.theEntity.hiredNPCInfo.getHiredInventory().func_70298_a(0, 1);
               }
            }
         } else {
            ItemStack chestItem;
            ItemStack itemstack;
            if (this.action == LOTREntityAIFarm.Action.HARVESTING) {
               canCollect = this.isSuitableForHarvesting(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
               if (canCollect) {
                  this.theEntity.func_71038_i();
                  Block block = this.theWorld.func_147439_a(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                  ArrayList drops = new ArrayList();
                  if (block instanceof LOTRBlockCorn) {
                     l = this.actionTarget.field_71574_a;
                     z = this.actionTarget.field_71573_c;

                     for(slot = 0; slot <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; ++slot) {
                        z = this.actionTarget.field_71572_b + slot;
                        if (this.theWorld.func_147439_a(l, z, z) == block && LOTRBlockCorn.hasCorn(this.theWorld, l, z, z)) {
                           l = this.theWorld.func_72805_g(l, z, z);
                           drops.addAll(((LOTRBlockCorn)block).getCornDrops(this.theWorld, l, z, z, l));
                           LOTRBlockCorn.setHasCorn(this.theWorld, l, z, z, false);
                        }
                     }
                  } else if (block instanceof LOTRBlockGrapevine) {
                     l = this.theWorld.func_72805_g(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                     drops.addAll(block.getDrops(this.theWorld, this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c, l, 0));
                     block.removedByPlayer(this.theWorld, this.fakePlayer, this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c, true);
                  } else {
                     l = this.theWorld.func_72805_g(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                     drops.addAll(block.getDrops(this.theWorld, this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c, l, 0));
                     this.theWorld.func_147468_f(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                  }

                  SoundType cropSound = block.field_149762_H;
                  this.theWorld.func_72908_a((double)this.actionTarget.field_71574_a + 0.5D, (double)this.actionTarget.field_71572_b + 0.5D, (double)this.actionTarget.field_71573_c + 0.5D, cropSound.func_150495_a(), (cropSound.func_150497_c() + 1.0F) / 2.0F, cropSound.func_150494_d() * 0.8F);
                  itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(0);
                  ItemStack cropItem = this.getCropForSeed(this.getSeedsToPlant());
                  boolean addedOneCropSeed = false;
                  Iterator var32 = drops.iterator();

                  while(true) {
                     while(true) {
                        while(var32.hasNext()) {
                           Object obj = var32.next();
                           ItemStack drop = (ItemStack)obj;
                           if (drop.func_77969_a(cropItem)) {
                              if (drop.func_77969_a(itemstack) && !addedOneCropSeed) {
                                 addedOneCropSeed = true;
                                 chestItem = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(0);
                                 if (chestItem.field_77994_a + drop.field_77994_a <= chestItem.func_77976_d()) {
                                    ++chestItem.field_77994_a;
                                    this.theEntity.hiredNPCInfo.getHiredInventory().func_70299_a(0, chestItem);
                                    continue;
                                 }
                              }

                              for(int l = 1; l <= 2; ++l) {
                                 ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(l);
                                 if (itemstack == null) {
                                    this.theEntity.hiredNPCInfo.getHiredInventory().func_70299_a(l, drop);
                                    break;
                                 }

                                 if (itemstack.field_77994_a + drop.field_77994_a <= itemstack.func_77976_d() && itemstack.func_77969_a(cropItem)) {
                                    ++itemstack.field_77994_a;
                                    this.theEntity.hiredNPCInfo.getHiredInventory().func_70299_a(l, itemstack);
                                    break;
                                 }
                              }
                           } else if (drop.func_77969_a(itemstack)) {
                              chestItem = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(0);
                              if (chestItem.field_77994_a + drop.field_77994_a <= chestItem.func_77976_d()) {
                                 ++chestItem.field_77994_a;
                                 this.theEntity.hiredNPCInfo.getHiredInventory().func_70299_a(0, chestItem);
                              }
                           }
                        }

                        return;
                     }
                  }
               }
            } else {
               TileEntity te;
               TileEntityChest chest;
               if (this.action == LOTREntityAIFarm.Action.DEPOSITING) {
                  canCollect = this.isSuitableForDepositing(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                  if (canCollect) {
                     this.theEntity.func_71038_i();
                     te = this.theWorld.func_147438_o(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                     if (te instanceof TileEntityChest) {
                        chest = (TileEntityChest)te;

                        for(l = 1; l <= 2; ++l) {
                           itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(l);
                           if (itemstack != null) {
                              for(slot = 0; slot < chest.func_70302_i_(); ++slot) {
                                 ItemStack chestItem = chest.func_70301_a(slot);
                                 if (chestItem == null || chestItem.func_77969_a(itemstack) && ItemStack.func_77970_a(chestItem, itemstack) && chestItem.field_77994_a < chestItem.func_77976_d()) {
                                    if (chestItem == null) {
                                       chestItem = itemstack.func_77946_l();
                                       chestItem.field_77994_a = 0;
                                    }

                                    while(itemstack.field_77994_a > 0 && chestItem.field_77994_a < chestItem.func_77976_d()) {
                                       ++chestItem.field_77994_a;
                                       --itemstack.field_77994_a;
                                    }

                                    chest.func_70299_a(slot, chestItem);
                                    if (itemstack.field_77994_a <= 0) {
                                       this.theEntity.hiredNPCInfo.getHiredInventory().func_70299_a(l, (ItemStack)null);
                                       break;
                                    }
                                 }
                              }
                           }
                        }

                        this.theWorld.func_72908_a((double)this.actionTarget.field_71574_a + 0.5D, (double)this.actionTarget.field_71572_b + 0.5D, (double)this.actionTarget.field_71573_c + 0.5D, "random.chestclosed", 0.5F, this.theWorld.field_73012_v.nextFloat() * 0.1F + 0.9F);
                     }
                  }
               } else if (this.action == LOTREntityAIFarm.Action.BONEMEALING) {
                  canCollect = this.isSuitableForBonemealing(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                  if (canCollect) {
                     this.theEntity.func_71038_i();
                     bonemeal = this.getInventoryBonemeal();
                     if (ItemDye.applyBonemeal(this.getInventoryBonemeal(), this.theWorld, this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c, this.fakePlayer)) {
                        this.theWorld.func_72926_e(2005, this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c, 0);
                     }

                     if (bonemeal.field_77994_a <= 0) {
                        bonemeal = null;
                     }

                     this.theEntity.hiredNPCInfo.getHiredInventory().func_70299_a(3, bonemeal);
                  }
               } else if (this.action == LOTREntityAIFarm.Action.COLLECTING) {
                  canCollect = this.isSuitableForCollecting(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                  if (canCollect) {
                     this.theEntity.func_71038_i();
                     te = this.theWorld.func_147438_o(this.actionTarget.field_71574_a, this.actionTarget.field_71572_b, this.actionTarget.field_71573_c);
                     if (te instanceof TileEntityChest) {
                        chest = (TileEntityChest)te;
                        int[] invSlots = new int[]{0, 3};
                        int[] var27 = invSlots;
                        slot = invSlots.length;

                        for(z = 0; z < slot; ++z) {
                           l = var27[z];
                           ItemStack itemstack = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(l);
                           if (itemstack == null && l == 3) {
                              itemstack = new ItemStack(Items.field_151100_aR, 0, 15);
                           }

                           if (itemstack != null) {
                              for(int slot = 0; slot < chest.func_70302_i_(); ++slot) {
                                 chestItem = chest.func_70301_a(slot);
                                 if (chestItem != null && chestItem.func_77969_a(itemstack) && ItemStack.func_77970_a(chestItem, itemstack) && chestItem.field_77994_a > 0) {
                                    while(itemstack.field_77994_a < itemstack.func_77976_d() && chestItem.field_77994_a > 0) {
                                       --chestItem.field_77994_a;
                                       ++itemstack.field_77994_a;
                                    }

                                    if (itemstack.field_77994_a <= 0) {
                                       itemstack = null;
                                    }

                                    if (chestItem.field_77994_a <= 0) {
                                       chestItem = null;
                                    }

                                    this.theEntity.hiredNPCInfo.getHiredInventory().func_70299_a(l, itemstack);
                                    chest.func_70299_a(slot, chestItem);
                                    if (itemstack.field_77994_a >= itemstack.func_77976_d()) {
                                       break;
                                    }
                                 }
                              }
                           }
                        }

                        this.theWorld.func_72908_a((double)this.actionTarget.field_71574_a + 0.5D, (double)this.actionTarget.field_71572_b + 0.5D, (double)this.actionTarget.field_71573_c + 0.5D, "random.chestopen", 0.5F, this.theWorld.field_73012_v.nextFloat() * 0.1F + 0.9F);
                     }
                  }
               }
            }
         }
      }

   }

   private LOTREntityAIFarm.TargetPair findTarget(LOTREntityAIFarm.Action targetAction) {
      this.setAppropriateHomeRange(targetAction);
      Random rand = this.theEntity.func_70681_au();
      boolean isChestAction = targetAction == LOTREntityAIFarm.Action.DEPOSITING || targetAction == LOTREntityAIFarm.Action.COLLECTING;
      List chests = new ArrayList();
      if (isChestAction) {
         chests = this.gatherNearbyChests();
      }

      for(int l = 0; l < 32; ++l) {
         int i = 0;
         int j = 0;
         int k = 0;
         boolean suitable = false;
         if (isChestAction) {
            if (!((List)chests).isEmpty()) {
               TileEntityChest chest = (TileEntityChest)((List)chests).get(rand.nextInt(((List)chests).size()));
               i = chest.field_145851_c;
               j = chest.field_145848_d;
               k = chest.field_145849_e;
               if (targetAction == LOTREntityAIFarm.Action.DEPOSITING) {
                  suitable = this.isSuitableForDepositing(i, j, k);
               } else if (targetAction == LOTREntityAIFarm.Action.COLLECTING) {
                  suitable = this.isSuitableForCollecting(i, j, k);
               }
            } else {
               suitable = false;
            }
         } else {
            i = MathHelper.func_76128_c(this.theEntity.field_70165_t) + MathHelper.func_76136_a(rand, -8, 8);
            j = MathHelper.func_76128_c(this.theEntity.field_70121_D.field_72338_b) + MathHelper.func_76136_a(rand, -4, 4);
            k = MathHelper.func_76128_c(this.theEntity.field_70161_v) + MathHelper.func_76136_a(rand, -8, 8);
            if (targetAction == LOTREntityAIFarm.Action.HOEING) {
               suitable = this.isSuitableForHoeing(i, j, k);
            } else if (targetAction == LOTREntityAIFarm.Action.PLANTING) {
               suitable = this.isSuitableForPlanting(i, j, k);
            } else if (targetAction == LOTREntityAIFarm.Action.HARVESTING) {
               suitable = this.isSuitableForHarvesting(i, j, k);
            } else if (targetAction == LOTREntityAIFarm.Action.BONEMEALING) {
               suitable = this.isSuitableForBonemealing(i, j, k);
            }
         }

         if (suitable && this.theEntity.func_110176_b(i, j, k)) {
            ChunkCoordinates target = new ChunkCoordinates(i, j, k);
            ChunkCoordinates path = this.getPathTarget(i, j, k, targetAction);
            PathEntity pathCheck = this.theEntity.func_70661_as().func_75488_a((double)path.field_71574_a, (double)path.field_71572_b, (double)path.field_71573_c);
            if (pathCheck != null) {
               return new LOTREntityAIFarm.TargetPair(target, path);
            }
         }
      }

      return null;
   }

   private List gatherNearbyChests() {
      int x = MathHelper.func_76128_c(this.theEntity.field_70165_t);
      int y = MathHelper.func_76128_c(this.theEntity.field_70121_D.field_72338_b);
      int z = MathHelper.func_76128_c(this.theEntity.field_70161_v);
      int searchRange = (int)this.theEntity.func_110174_bM();
      int chunkX = x >> 4;
      int chunkZ = z >> 4;
      int chunkRange = (searchRange >> 4) + 1;
      List nearbyChests = new ArrayList();

      for(int i = -chunkRange; i <= chunkRange; ++i) {
         for(int k = -chunkRange; k <= chunkRange; ++k) {
            int nearChunkX = chunkX + i;
            int nearChunkZ = chunkZ + k;
            if (this.theWorld.func_72863_F().func_73149_a(nearChunkX, nearChunkZ)) {
               Chunk chunk = this.theWorld.func_72964_e(nearChunkX, nearChunkZ);
               Iterator var14 = chunk.field_150816_i.values().iterator();

               while(var14.hasNext()) {
                  Object obj = var14.next();
                  TileEntity te = (TileEntity)obj;
                  if (te instanceof TileEntityChest && !te.func_145837_r()) {
                     TileEntityChest chest = (TileEntityChest)te;
                     if (this.theEntity.func_110176_b(chest.field_145851_c, chest.field_145848_d, chest.field_145849_e)) {
                        nearbyChests.add(chest);
                     }
                  }
               }
            }
         }
      }

      return nearbyChests;
   }

   private ChunkCoordinates getPathTarget(int i, int j, int k, LOTREntityAIFarm.Action targetAction) {
      if (targetAction == LOTREntityAIFarm.Action.HOEING) {
         return this.isReplaceable(i, j + 1, k) ? new ChunkCoordinates(i, j + 1, k) : this.getAdjacentSolidOpenWalkTarget(i, j + 1, k);
      } else if (targetAction != LOTREntityAIFarm.Action.PLANTING && targetAction != LOTREntityAIFarm.Action.HARVESTING && targetAction != LOTREntityAIFarm.Action.BONEMEALING) {
         return targetAction != LOTREntityAIFarm.Action.DEPOSITING && targetAction != LOTREntityAIFarm.Action.COLLECTING ? new ChunkCoordinates(i, j, k) : this.getAdjacentSolidOpenWalkTarget(i, j, k);
      } else if (this.harvestingSolidBlock) {
         return new ChunkCoordinates(i, j + 1, k);
      } else if (!this.isFarmingGrapes()) {
         return new ChunkCoordinates(i, j, k);
      } else {
         int groundY = j;

         for(int j1 = 1; j1 <= 2; ++j1) {
            if (!(this.theWorld.func_147439_a(i, j - j1 - 1, k) instanceof LOTRBlockGrapevine)) {
               groundY = j - j1 - 1;
               break;
            }
         }

         return this.getAdjacentSolidOpenWalkTarget(i, groundY + 1, k);
      }
   }

   private boolean isSolidOpenWalkTarget(int i, int j, int k) {
      Block below = this.theWorld.func_147439_a(i, j - 1, k);
      if (below.func_149662_c() || below.canSustainPlant(this.theWorld, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150464_aj)) {
         List bounds = new ArrayList();
         AxisAlignedBB aabb = AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 2), (double)(k + 1));

         for(int j1 = j; j1 <= j + 1; ++j1) {
            this.theWorld.func_147439_a(i, j1, k).func_149743_a(this.theWorld, i, j1, k, aabb, bounds, this.theEntity);
         }

         if (bounds.isEmpty()) {
            return this.theEntity.func_70661_as().func_75488_a((double)i, (double)j, (double)k) != null;
         }
      }

      return false;
   }

   private ChunkCoordinates getAdjacentSolidOpenWalkTarget(int i, int j, int k) {
      List possibleCoords = new ArrayList();

      for(int i1 = -1; i1 <= 1; ++i1) {
         for(int k1 = -1; k1 <= 1; ++k1) {
            int i2 = i + i1;
            int k2 = k + k1;

            for(int j1 = 1; j1 >= -1; --j1) {
               int j2 = j + j1;
               if (this.isSolidOpenWalkTarget(i2, j2, k2)) {
                  possibleCoords.add(new ChunkCoordinates(i2, j2, k2));
               }
            }
         }
      }

      if (!possibleCoords.isEmpty()) {
         return (ChunkCoordinates)possibleCoords.get(0);
      } else {
         return new ChunkCoordinates(i, j, k);
      }
   }

   private boolean isSuitableForHoeing(ChunkCoordinates pos) {
      return this.isSuitableForHoeing(pos.field_71574_a, pos.field_71572_b, pos.field_71573_c);
   }

   private boolean isSuitableForHoeing(int i, int j, int k) {
      this.harvestingSolidBlock = false;
      Block block = this.theWorld.func_147439_a(i, j, k);
      boolean isGrassDirt = block.canSustainPlant(this.theWorld, i, j, k, ForgeDirection.UP, Blocks.field_150329_H);
      boolean isFarmland = block.canSustainPlant(this.theWorld, i, j, k, ForgeDirection.UP, (IPlantable)Blocks.field_150464_aj);
      if (isGrassDirt && !isFarmland && (this.isReplaceable(i, j + 1, k) || this.theWorld.func_147439_a(i, j + 1, k) == LOTRMod.grapevine)) {
         Block below = this.theWorld.func_147439_a(i, j - 1, k);
         if (below == Blocks.field_150354_m) {
            return false;
         } else {
            boolean waterNearby = false;
            int range = 4;

            for(int i1 = i - range; i1 <= i + range; ++i1) {
               for(int k1 = k - range; k1 <= k + range; ++k1) {
                  if (this.theWorld.func_147439_a(i1, j, k1).func_149688_o() == Material.field_151586_h) {
                     waterNearby = true;
                     return waterNearby;
                  }
               }
            }

            return waterNearby;
         }
      } else {
         return false;
      }
   }

   private boolean isSuitableForPlanting(ChunkCoordinates pos) {
      return this.isSuitableForPlanting(pos.field_71574_a, pos.field_71572_b, pos.field_71573_c);
   }

   private boolean isSuitableForPlanting(int i, int j, int k) {
      this.harvestingSolidBlock = false;
      if (this.isFarmingGrapes()) {
         return this.theWorld.func_147439_a(i, j, k) == LOTRMod.grapevine && LOTRBlockGrapevine.canPlantGrapesAt(this.theWorld, i, j, k, this.getSeedsToPlant());
      } else {
         return this.theWorld.func_147439_a(i, j - 1, k).isFertile(this.theWorld, i, j - 1, k) && this.isReplaceable(i, j, k);
      }
   }

   private boolean isSuitableForHarvesting(ChunkCoordinates pos) {
      return this.isSuitableForHarvesting(pos.field_71574_a, pos.field_71572_b, pos.field_71573_c);
   }

   private boolean isSuitableForHarvesting(int i, int j, int k) {
      this.harvestingSolidBlock = false;
      IPlantable seed = this.getSeedsToPlant();
      Block plantBlock = seed.getPlant(this.theWorld, i, j, k);
      if (plantBlock instanceof BlockCrops) {
         this.harvestingSolidBlock = false;
         return this.theWorld.func_147439_a(i, j, k) == plantBlock && this.theWorld.func_72805_g(i, j, k) >= 7;
      } else if (plantBlock instanceof BlockStem) {
         this.harvestingSolidBlock = true;
         return this.theWorld.func_147439_a(i, j, k) == LOTRReflection.getStemFruitBlock((BlockStem)plantBlock);
      } else {
         if (plantBlock instanceof LOTRBlockCorn) {
            this.harvestingSolidBlock = false;
            if (this.theWorld.func_147439_a(i, j, k) == plantBlock) {
               for(int j1 = 0; j1 <= LOTRBlockCorn.MAX_GROW_HEIGHT - 1; ++j1) {
                  int j2 = j + j1;
                  if (this.theWorld.func_147439_a(i, j2, k) == plantBlock && LOTRBlockCorn.hasCorn(this.theWorld, i, j2, k)) {
                     return true;
                  }
               }
            }
         } else if (plantBlock instanceof LOTRBlockGrapevine) {
            this.harvestingSolidBlock = false;
            return this.theWorld.func_147439_a(i, j, k) == seed.getPlant(this.theWorld, i, j, k) && this.theWorld.func_72805_g(i, j, k) >= 7;
         }

         return false;
      }
   }

   private boolean isSuitableForDepositing(ChunkCoordinates pos) {
      return this.isSuitableForDepositing(pos.field_71574_a, pos.field_71572_b, pos.field_71573_c);
   }

   private boolean isSuitableForDepositing(int i, int j, int k) {
      this.harvestingSolidBlock = false;
      TileEntityChest chest = this.getSuitableChest(i, j, k);
      if (chest != null) {
         for(int l = 1; l <= 2; ++l) {
            ItemStack depositItem = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(l);
            if (depositItem != null) {
               for(int slot = 0; slot < chest.func_70302_i_(); ++slot) {
                  ItemStack chestItem = chest.func_70301_a(slot);
                  if (chestItem == null || chestItem.func_77969_a(depositItem) && ItemStack.func_77970_a(chestItem, depositItem) && chestItem.field_77994_a < chestItem.func_77976_d()) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean isSuitableForBonemealing(ChunkCoordinates pos) {
      return this.isSuitableForBonemealing(pos.field_71574_a, pos.field_71572_b, pos.field_71573_c);
   }

   private boolean isSuitableForBonemealing(int i, int j, int k) {
      this.harvestingSolidBlock = false;
      IPlantable seed = this.getSeedsToPlant();
      Block plantBlock = seed.getPlant(this.theWorld, i, j, k);
      if (plantBlock instanceof IGrowable && this.theWorld.func_147439_a(i, j, k) == plantBlock) {
         IGrowable growableBlock = (IGrowable)plantBlock;
         if (growableBlock.func_149851_a(this.theWorld, i, j, k, this.theWorld.field_72995_K)) {
            this.harvestingSolidBlock = plantBlock.func_149662_c();
            return true;
         }
      }

      return false;
   }

   private boolean isSuitableForCollecting(ChunkCoordinates pos) {
      return this.isSuitableForCollecting(pos.field_71574_a, pos.field_71572_b, pos.field_71573_c);
   }

   private boolean isSuitableForCollecting(int i, int j, int k) {
      this.harvestingSolidBlock = false;
      TileEntityChest chest = this.getSuitableChest(i, j, k);
      if (chest != null) {
         int[] invSlots = new int[]{0, 3};
         int[] var6 = invSlots;
         int var7 = invSlots.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int l = var6[var8];
            ItemStack collectMatch = this.theEntity.hiredNPCInfo.getHiredInventory().func_70301_a(l);
            if (collectMatch == null && l == 3) {
               collectMatch = new ItemStack(Items.field_151100_aR, 0, 15);
            }

            if (collectMatch != null && collectMatch.field_77994_a <= 16) {
               for(int slot = 0; slot < chest.func_70302_i_(); ++slot) {
                  ItemStack chestItem = chest.func_70301_a(slot);
                  if (chestItem != null && chestItem.func_77969_a(collectMatch) && ItemStack.func_77970_a(chestItem, collectMatch) && chestItem.field_77994_a > 0) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private TileEntityChest getSuitableChest(int i, int j, int k) {
      Block block = this.theWorld.func_147439_a(i, j, k);
      int meta = this.theWorld.func_72805_g(i, j, k);
      TileEntityChest suitableChest = null;
      if (block.hasTileEntity(meta)) {
         TileEntity te = this.theWorld.func_147438_o(i, j, k);
         if (te instanceof TileEntityChest) {
            TileEntityChest chest = (TileEntityChest)te;
            boolean flag = false;
            if (this.isFarmhandMarked(chest)) {
               flag = true;
            } else if (chest.field_145991_k != null && this.isFarmhandMarked(chest.field_145991_k)) {
               flag = true;
            } else if (chest.field_145990_j != null && this.isFarmhandMarked(chest.field_145990_j)) {
               flag = true;
            } else if (chest.field_145992_i != null && this.isFarmhandMarked(chest.field_145992_i)) {
               flag = true;
            } else if (chest.field_145988_l != null && this.isFarmhandMarked(chest.field_145988_l)) {
               flag = true;
            }

            if (flag) {
               suitableChest = chest;
            }
         }
      }

      return suitableChest;
   }

   private boolean isFarmhandMarked(TileEntityChest chest) {
      int i = chest.field_145851_c;
      int j = chest.field_145848_d;
      int k = chest.field_145849_e;
      AxisAlignedBB chestBB = AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1));
      List entities = this.theWorld.func_72872_a(EntityItemFrame.class, chestBB.func_72314_b(2.0D, 2.0D, 2.0D));
      Iterator var7 = entities.iterator();

      while(var7.hasNext()) {
         Object obj = var7.next();
         EntityItemFrame frame = (EntityItemFrame)obj;
         if (frame.field_146063_b == i && frame.field_146064_c == j && frame.field_146062_d == k) {
            ItemStack frameItem = frame.func_82335_i();
            if (frameItem != null && frameItem.func_77973_b() instanceof ItemHoe) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isReplaceable(int i, int j, int k) {
      Block block = this.theWorld.func_147439_a(i, j, k);
      return !block.func_149688_o().func_76224_d() && block.isReplaceable(this.theWorld, i, j, k);
   }

   public static class TargetPair {
      public final ChunkCoordinates actionTarget;
      public final ChunkCoordinates pathTarget;

      public TargetPair(ChunkCoordinates action, ChunkCoordinates path) {
         this.actionTarget = action;
         this.pathTarget = path;
      }
   }

   public static enum Action {
      HOEING,
      PLANTING,
      HARVESTING,
      DEPOSITING,
      BONEMEALING,
      COLLECTING;
   }
}
