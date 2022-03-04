package lotr.common.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lotr.common.LOTRLog;
import lotr.common.block.FallenLeavesBlock;
import lotr.common.block.HangingWebBlock;
import lotr.common.block.PlateBlock;
import lotr.common.block.SnowPathBlock;
import lotr.common.block.VerticalOnlySlabBlock;
import lotr.common.block.trees.BirchTreeAlt;
import lotr.common.block.trees.VanillaSaplingPartyTrees;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.MiscDataModule;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.NPCPredicates;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import lotr.common.fac.AlignmentBonus;
import lotr.common.fac.EntityFactionHelper;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointers;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.inv.OpenPouchContainer;
import lotr.common.inv.ShulkerBoxContainerFix;
import lotr.common.item.IEmptyVesselItem;
import lotr.common.item.PouchItem;
import lotr.common.item.RedBookItem;
import lotr.common.item.SmokingPipeItem;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselOperations;
import lotr.common.item.VesselType;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketSetAttackTarget;
import lotr.common.stat.LOTRStats;
import lotr.common.time.LOTRDate;
import lotr.common.time.LOTRTime;
import lotr.common.util.LOTRUtil;
import lotr.common.world.RingPortalTeleporter;
import lotr.common.world.map.CustomWaypointStructureHandler;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.HorseInventoryContainer;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.EntityEvent.Size;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent.Open;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.event.world.BlockEvent.BlockToolInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.event.world.PistonEvent.Pre;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class LOTREventHandler {
   private final SpeechGarbler speechGarbler = new SpeechGarbler();
   private final BreakMallornResponse breakMallornResponse = new BreakMallornResponse();

   public LOTREventHandler() {
      this.registerHandlers(this);
   }

   private void registerHandlers(Object... handlers) {
      Object[] var2 = handlers;
      int var3 = handlers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object handler = var2[var4];
         MinecraftForge.EVENT_BUS.register(handler);
      }

   }

   @SubscribeEvent
   public void onPlayerLogin(PlayerLoggedInEvent event) {
      PlayerEntity player = event.getPlayer();
      World world = player.field_70170_p;
      if (!world.field_72995_K) {
         ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;
         ServerWorld serverWorld = serverPlayer.func_71121_q();
         LOTRLevelData levelData = LOTRLevelData.serverInstance();
         MapSettingsManager.serverInstance().sendMapToPlayer(serverPlayer);
         FactionSettingsManager.serverInstance().sendFactionsToPlayer(serverPlayer);
         NPCEntitySettingsManager.serverInstance().sendEntitySettingsToPlayer(serverPlayer);
         if (LOTRWorldTypes.isInstantME(serverWorld) && LOTRDimensions.isDimension((World)serverWorld, World.field_234918_g_)) {
            MiscDataModule miscData = levelData.getData(serverPlayer).getMiscData();
            if (!miscData.getInitialSpawnedIntoME()) {
               RingPortalTeleporter.transferEntity(serverWorld, serverPlayer, Optional.empty(), false);
               miscData.setInitialSpawnedIntoME(true);
            }
         }

         levelData.sendLoginPacket(serverPlayer);
         levelData.playerDataHandleLogin(serverPlayer);
         levelData.sendAllOtherPlayerAlignmentsToPlayer(serverPlayer);
         levelData.sendPlayerAlignmentToAllOtherPlayers(serverPlayer);
         LOTRTime.sendLoginPacket(serverPlayer);
         LOTRDate.sendLoginPacket(serverPlayer);
      }

   }

   @SubscribeEvent
   public void onPlayerLogout(PlayerLoggedOutEvent event) {
      PlayerEntity player = event.getPlayer();
      World world = player.field_70170_p;
      if (!world.field_72995_K && player.func_184187_bx() instanceof AbstractChestedHorseEntity) {
         AbstractChestedHorseEntity logoutHorse = (AbstractChestedHorseEntity)player.func_184187_bx();
         double checkRange = 64.0D;
         List nearbyPlayers = world.func_217357_a(PlayerEntity.class, player.func_174813_aQ().func_72321_a(checkRange, checkRange, checkRange));
         Iterator var8 = nearbyPlayers.iterator();

         while(var8.hasNext()) {
            PlayerEntity otherPlayer = (PlayerEntity)var8.next();
            if (otherPlayer != player && otherPlayer.field_71070_bA instanceof HorseInventoryContainer) {
               HorseInventoryContainer horseInv = (HorseInventoryContainer)otherPlayer.field_71070_bA;
               AbstractHorseEntity openHorse = (AbstractHorseEntity)ObfuscationReflectionHelper.getPrivateValue(HorseInventoryContainer.class, horseInv, "field_111242_f");
               if (openHorse == logoutHorse) {
                  otherPlayer.func_71053_j();
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onPlayerInteract(PlayerInteractEvent event) {
      PlayerEntity player = event.getPlayer();
      World world = event.getWorld();
      Hand hand = event.getHand();
      ItemStack heldItem = event.getItemStack();
      BlockPos pos = event.getPos();
      Direction side = event.getFace();
      boolean sneaking = player.func_226563_dT_();
      if ((event instanceof RightClickBlock || event instanceof LeftClickBlock) && TerrainProtections.isTerrainProtectedFromPlayerEdits(event, heldItem, pos, side)) {
         event.setCanceled(true);
      } else {
         if (event instanceof RightClickBlock) {
            if (side != null && !player.func_175151_a(pos, side, heldItem)) {
               return;
            }

            BlockState state = world.func_180495_p(pos);
            RayTraceResult hitVec = ((RightClickBlock)event).getHitVec();
            if (!heldItem.func_190926_b() && heldItem.func_77973_b() instanceof BlockItem) {
               Block itemBlock = Block.func_149634_a(heldItem.func_77973_b());
               if (itemBlock instanceof SlabBlock) {
                  SlabBlock itemSlabBlock = (SlabBlock)itemBlock;
                  if (VerticalOnlySlabBlock.getVerticalSlabFor(itemSlabBlock) != null) {
                     VerticalOnlySlabBlock vSlab = VerticalOnlySlabBlock.getVerticalSlabFor(itemSlabBlock);
                     if (hitVec instanceof BlockRayTraceResult) {
                        BlockRayTraceResult blockRayTrace = (BlockRayTraceResult)hitVec;
                        boolean holdingAnyItem = true;
                        boolean sneakUsingItem = sneaking && holdingAnyItem && (!player.func_184614_ca().doesSneakBypassUse(world, pos, player) || !player.func_184592_cb().doesSneakBypassUse(world, pos, player));
                        ActionResultType verticalPlaceResult;
                        if (!sneakUsingItem) {
                           verticalPlaceResult = state.func_227031_a_(world, player, hand, blockRayTrace);
                           if (verticalPlaceResult.func_226246_a_()) {
                              event.setCancellationResult(verticalPlaceResult);
                              event.setCanceled(true);
                              return;
                           }
                        }

                        verticalPlaceResult = vSlab.placeVerticalOrVanilla(player, hand, heldItem, world, pos, side, blockRayTrace);
                        if (verticalPlaceResult != ActionResultType.PASS) {
                           event.setCancellationResult(verticalPlaceResult);
                           event.setCanceled(true);
                           return;
                        }
                     }
                  }
               }
            }

            if (heldItem.getToolTypes().contains(ToolType.SHOVEL)) {
               ActionResultType snowPathResult = SnowPathBlock.makeSnowPathUnderSnowLayer(world, pos, side, player, hand, heldItem);
               if (snowPathResult != ActionResultType.PASS) {
                  event.setCanceled(true);
                  return;
               }
            }

            if (!world.field_72995_K && hand == Hand.MAIN_HAND && state.func_177230_c() instanceof PlateBlock && sneaking && ((PlateBlock)state.func_177230_c()).popOffOneItem(world, pos, player)) {
               event.setCanceled(true);
               return;
            }

            if (state.func_177230_c() instanceof CauldronBlock && !heldItem.func_190926_b() && !sneaking) {
               int waterLevel = (Integer)state.func_177229_b(CauldronBlock.field_176591_a);
               if (waterLevel > 0) {
                  boolean isCauldronUse = false;
                  Item item = heldItem.func_77973_b();
                  if (item instanceof PouchItem && PouchItem.isPouchDyed(heldItem)) {
                     if (!world.field_72995_K) {
                        PouchItem.removePouchDye(heldItem);
                        player.func_195066_a(LOTRStats.CLEAN_POUCH);
                     }

                     isCauldronUse = true;
                  } else if (item instanceof SmokingPipeItem && SmokingPipeItem.isSmokeDyed(heldItem)) {
                     if (!world.field_72995_K) {
                        SmokingPipeItem.clearSmokeColor(heldItem);
                        player.func_195066_a(LOTRStats.CLEAN_SMOKING_PIPE);
                     }

                     isCauldronUse = true;
                  }

                  if (isCauldronUse) {
                     if (!world.field_72995_K) {
                        ((CauldronBlock)state.func_177230_c()).func_176590_a(world, pos, state, waterLevel - 1);
                     }

                     event.setCancellationResult(ActionResultType.func_233537_a_(world.field_72995_K));
                     event.setCanceled(true);
                     return;
                  }
               }
            }
         }

      }
   }

   @SubscribeEvent
   public void onUseBucket(FillBucketEvent event) {
      World world = event.getWorld();
      ItemStack heldItem = event.getEmptyBucket();
      RayTraceResult target = event.getTarget();
      if (target.func_216346_c() == Type.BLOCK) {
         BlockRayTraceResult blockRayTrace = (BlockRayTraceResult)target;
         BlockPos pos = blockRayTrace.func_216350_a();
         Direction side = blockRayTrace.func_216354_b();
         if (TerrainProtections.isTerrainProtectedFromPlayerEdits(event, heldItem, pos, side)) {
            event.setCanceled(true);
            BlockState state = world.func_180495_p(pos);
            world.func_184138_a(pos, state, state, 2);
            return;
         }
      }

   }

   @SubscribeEvent
   public void onExplosionDetonate(Detonate event) {
      Explosion explosion = event.getExplosion();
      World world = event.getWorld();
      if (!world.field_72995_K) {
         List explodingPositions = explosion.func_180343_e();
         explodingPositions.removeIf((pos) -> {
            return TerrainProtections.isTerrainProtectedFromExplosion(world, pos);
         });
      }

   }

   @SubscribeEvent
   public void onPistonMoveCheck(Pre event) {
   }

   @SubscribeEvent
   public void onEntityInteract(EntityInteract event) {
      PlayerEntity player = event.getPlayer();
      World world = event.getWorld();
      ItemStack heldItem = event.getItemStack();
      Hand hand = event.getHand();
      Entity target = event.getTarget();
      if (heldItem.func_77973_b() instanceof RedBookItem && target instanceof ItemFrameEntity) {
         RedBookItem redBook = (RedBookItem)heldItem.func_77973_b();
         ItemFrameEntity frame = (ItemFrameEntity)target;
         BlockPos frameSupportPos = CustomWaypointStructureHandler.getItemFrameSupportPos(frame);
         if (redBook.createCustomWaypointStructure(world, frameSupportPos, player)) {
            event.setCanceled(true);
            return;
         }
      }

      if (!player.field_71075_bZ.field_75098_d && VesselOperations.isItemEmptyVessel(heldItem) && IEmptyVesselItem.canMilk(target)) {
         VesselType vesselType = ((IEmptyVesselItem)heldItem.func_77973_b()).getVesselType();
         ItemStack milkDrink = new ItemStack((IItemProvider)LOTRItems.MILK_DRINK.get());
         VesselDrinkItem.setVessel(milkDrink, vesselType);
         player.func_184185_a(SoundEvents.field_187564_an, 1.0F, 1.0F);
         heldItem.func_190918_g(1);
         if (heldItem.func_190926_b()) {
            player.func_184611_a(hand, milkDrink);
         } else if (!player.field_71071_by.func_70441_a(milkDrink)) {
            player.func_71019_a(milkDrink, false);
         }

         event.setCanceled(true);
         event.setCancellationResult(ActionResultType.SUCCESS);
      } else {
         ActionResultType useSeedsResult;
         if (target instanceof WolfEntity) {
            WolfEntity wolf = (WolfEntity)target;
            useSeedsResult = this.handleProxyFakeItemEntityInteraction(player, hand, heldItem, wolf, Items.BONES, net.minecraft.item.Items.field_151103_aS);
            if (useSeedsResult.func_226246_a_()) {
               event.setCanceled(true);
               event.setCancellationResult(useSeedsResult);
               return;
            }
         }

         if (target instanceof ParrotEntity) {
            ParrotEntity parrot = (ParrotEntity)target;
            useSeedsResult = this.handleProxyFakeItemEntityInteraction(player, hand, heldItem, parrot, Items.SEEDS, net.minecraft.item.Items.field_151014_N);
            if (useSeedsResult.func_226246_a_()) {
               event.setCanceled(true);
               event.setCancellationResult(useSeedsResult);
               return;
            }
         }

      }
   }

   private ActionResultType handleProxyFakeItemEntityInteraction(PlayerEntity player, Hand hand, ItemStack heldItem, AnimalEntity target, ITag vanillaTagToMatch, Item proxyVanillaItem) {
      if (heldItem.func_77973_b().func_206844_a(vanillaTagToMatch) && heldItem.func_77973_b().getRegistryName().func_110624_b().equals("lotr")) {
         ItemStack faked = new ItemStack(proxyVanillaItem, heldItem.func_190916_E());
         player.func_184611_a(hand, faked);
         ActionResultType result = target.func_230254_b_(player, hand);
         heldItem.func_190920_e(faked.func_190916_E());
         player.func_184611_a(hand, heldItem);
         return result;
      } else {
         return ActionResultType.PASS;
      }
   }

   @SubscribeEvent
   public void onBoneMealGrow(BonemealEvent event) {
      World world = event.getWorld();
      Random rand = world.field_73012_v;
      BlockState state = event.getBlock();
      BlockPos pos = event.getPos();
      if (LOTRDimensions.isModDimension(world) && state.func_177230_c() == Blocks.field_196658_i) {
         IGrowable grassAsGrowable = (IGrowable)state.func_177230_c();
         if (grassAsGrowable.func_176473_a(world, pos, state, world.field_72995_K)) {
            if (world instanceof ServerWorld && grassAsGrowable.func_180670_a(world, world.field_73012_v, pos, state)) {
               BlockPos above = pos.func_177984_a();
               int tries = 128;

               label55:
               for(int i = 0; i < tries; ++i) {
                  BlockPos plantPos = above;

                  for(int triesHere = 0; triesHere < i / 16; ++triesHere) {
                     plantPos = plantPos.func_177982_a(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                     if (world.func_180495_p(plantPos.func_177977_b()).func_177230_c() != grassAsGrowable || world.func_180495_p(plantPos).func_235785_r_(world, plantPos)) {
                        continue label55;
                     }
                  }

                  BlockState curBlock = world.func_180495_p(plantPos);
                  if (curBlock.func_177230_c() instanceof TallGrassBlock && rand.nextInt(10) == 0) {
                     ((IGrowable)curBlock.func_177230_c()).func_225535_a_((ServerWorld)world, rand, plantPos, curBlock);
                  }

                  if (curBlock.isAir(world, plantPos)) {
                     Biome biome = world.func_226691_t_(plantPos);
                     BlockState plant;
                     if (rand.nextInt(8) == 0) {
                        List flowerList = world.func_226691_t_(plantPos).func_242440_e().func_242496_b();
                        if (flowerList.isEmpty()) {
                           continue;
                        }

                        ConfiguredFeature configuredFeature = (ConfiguredFeature)flowerList.get(0);
                        plant = ((FlowersFeature)configuredFeature.func_242766_b()).func_225562_b_(rand, plantPos, configuredFeature.func_242767_c());
                     } else {
                        plant = LOTRBiomes.getWrapperFor(biome, world).getGrassForBonemeal(rand, plantPos);
                     }

                     if (plant.func_196955_c(world, plantPos)) {
                        world.func_180501_a(plantPos, plant, 3);
                     }
                  }
               }
            }

            event.setResult(Result.ALLOW);
            return;
         }
      }

   }

   @SubscribeEvent
   public void onSaplingGrow(SaplingGrowTreeEvent event) {
      ServerWorld sWorld = (ServerWorld)event.getWorld();
      ChunkGenerator cg = sWorld.func_72863_F().func_201711_g();
      BlockPos pos = event.getPos();
      BlockState state = sWorld.func_180495_p(pos);
      Random rand = event.getRand();
      if (VanillaSaplingPartyTrees.attemptGrowPartyTree(state, sWorld, cg, pos, rand)) {
         event.setResult(Result.DENY);
      } else if (state.func_177230_c() == Blocks.field_196676_v) {
         Tree altTree = new BirchTreeAlt();
         altTree.func_230339_a_(sWorld, cg, pos, state, rand);
         event.setResult(Result.DENY);
      }
   }

   @SubscribeEvent
   public void onBlockToolInteract(BlockToolInteractEvent event) {
      if (event.getToolType() == ToolType.SHOVEL && event.getState().func_177230_c() == Blocks.field_196604_cC) {
         event.setFinalState(((Block)LOTRBlocks.SNOW_PATH.get()).func_176223_P());
      }
   }

   @SubscribeEvent
   public void onPlayerHarvestCheck(HarvestCheck event) {
      BlockState state = event.getTargetBlock();
      PlayerEntity player = event.getPlayer();
      ItemStack heldItem = player.func_184586_b(Hand.MAIN_HAND);
      if (state.func_177230_c() instanceof HangingWebBlock && !heldItem.func_190926_b() && heldItem.func_150998_b(Blocks.field_196553_aF.func_176223_P())) {
         event.setCanHarvest(true);
      }
   }

   @SubscribeEvent
   public void onBreakSpeedCheck(BreakSpeed event) {
      BlockState state = event.getState();
      PlayerEntity player = event.getPlayer();
      ItemStack heldItem = player.func_184586_b(Hand.MAIN_HAND);
      if (state.func_177230_c() instanceof HangingWebBlock && !heldItem.func_190926_b()) {
         this.adjustRelativeSpeedToMatch(event, Blocks.field_196553_aF);
      } else if (state.func_177230_c() instanceof FallenLeavesBlock && !heldItem.func_190926_b()) {
         this.adjustRelativeSpeedToMatch(event, ((FallenLeavesBlock)state.func_177230_c()).getBaseLeafBlock());
      }
   }

   private void adjustRelativeSpeedToMatch(BreakSpeed event, Block desiredBlockSpeed) {
      ItemStack heldItem = event.getPlayer().func_184586_b(Hand.MAIN_HAND);
      float desiredBaseSpeed = heldItem.func_150997_a(desiredBlockSpeed.func_176223_P());
      float actualBaseSpeed = heldItem.func_150997_a(event.getState());
      float relativeSpeed = desiredBaseSpeed / actualBaseSpeed;
      event.setNewSpeed(event.getOriginalSpeed() * relativeSpeed);
   }

   @SubscribeEvent
   public void onBlockBreak(BreakEvent event) {
      PlayerEntity player = event.getPlayer();
      World world = player.field_70170_p;
      BlockPos pos = event.getPos();
      BlockState state = event.getState();
      this.breakMallornResponse.handleBlockBreak(world, player, pos, state);
   }

   @SubscribeEvent
   public void onItemPickup(EntityItemPickupEvent event) {
      PlayerEntity player = event.getPlayer();
      World world = player.field_70170_p;
      ItemEntity pickupEntity = event.getItem();
      ItemStack pickupStack = pickupEntity.func_92059_d();
      if (!world.field_72995_K && !pickupStack.func_190926_b()) {
         boolean addedAnyToPouches = false;

         for(int i = 0; i < player.field_71071_by.func_70302_i_(); ++i) {
            ItemStack itemInSlot = player.field_71071_by.func_70301_a(i);
            if (itemInSlot.func_77973_b() instanceof PouchItem) {
               PouchItem.AddItemResult result = PouchItem.tryAddItemToPouch(itemInSlot, pickupStack, true);
               if (result != PouchItem.AddItemResult.NONE_ADDED) {
                  boolean markNewPickup = true;
                  if (player.field_71070_bA instanceof OpenPouchContainer) {
                     OpenPouchContainer pouchContainer = (OpenPouchContainer)player.field_71070_bA;
                     if (pouchContainer.isOpenPouch(itemInSlot)) {
                        pouchContainer.reloadPouchFromPickup();
                        markNewPickup = false;
                     }
                  }

                  if (markNewPickup) {
                     PouchItem.setPickedUpNewItems(itemInSlot, true);
                  }
               }

               if (pickupStack.func_190926_b()) {
                  break;
               }
            }
         }

         if (pickupStack.func_190926_b()) {
            event.setResult(Result.ALLOW);
         }
      }

   }

   @SubscribeEvent
   public void onOpenContainer(Open event) {
      Container container = event.getContainer();
      PlayerEntity player = event.getPlayer();
      if (container instanceof ShulkerBoxContainer) {
         ShulkerBoxContainerFix.fixContainerSlots((ShulkerBoxContainer)container, player);
      }

   }

   @SubscribeEvent
   public void onSleepFinished(SleepFinishedTimeEvent event) {
      ServerWorld world = (ServerWorld)event.getWorld();
      if (!world.field_72995_K && LOTRDimensions.isModDimension(world)) {
         LOTRTime.advanceToMorning(world);
         if (world.func_82736_K().func_223586_b(GameRules.field_223617_t)) {
            ServerWorld overworld = world.func_73046_m().func_241755_D_();
            IServerWorldInfo overworldInfo = (IServerWorldInfo)overworld.func_72912_H();
            overworldInfo.func_76080_g(0);
            overworldInfo.func_76084_b(false);
            overworldInfo.func_76090_f(0);
            overworldInfo.func_76069_a(false);
         }
      }

   }

   @SubscribeEvent
   public void onEntitySetSize(Size event) {
      Entity entity = event.getEntity();
      World world = entity.field_70170_p;
      if (BeeAdjustments.shouldApply(entity, world)) {
         BeeAdjustments.adjustSize(event);
      }

   }

   @SubscribeEvent
   public void onEntityInitialSpawn(SpecialSpawn event) {
      Entity entity = event.getEntity();
      World world = entity.field_70170_p;
      if (!world.field_72995_K && entity instanceof FoxEntity && LOTRDimensions.isModDimension(world)) {
         FoxEntity fox = (FoxEntity)entity;
         ItemStack itemInMouth = fox.func_184582_a(EquipmentSlotType.MAINHAND);
         boolean spawnedWithItem = !itemInMouth.func_190926_b();
         if (itemInMouth.func_77973_b() == net.minecraft.item.Items.field_151166_bC) {
            fox.func_184201_a(EquipmentSlotType.MAINHAND, ItemStack.field_190927_a);
            LOTRLog.info("Cancelled a fox spawning with emerald in mouth in %s at %s", world.func_234923_W_().func_240901_a_(), fox.func_233580_cy_());
         }

         if (spawnedWithItem) {
            float f = fox.func_70681_au().nextFloat();
            ItemStack replacedItem = null;
            if (f < 0.02F) {
               replacedItem = new ItemStack((IItemProvider)LOTRItems.GOLD_RING.get());
            } else if (f < 0.04F) {
               replacedItem = new ItemStack((IItemProvider)LOTRItems.SILVER_RING.get());
            } else if (f < 0.0405F) {
               replacedItem = new ItemStack((IItemProvider)LOTRItems.MITHRIL_RING.get());
            }

            if (replacedItem != null) {
               fox.func_184201_a(EquipmentSlotType.MAINHAND, replacedItem);
            }
         }
      }

   }

   @SubscribeEvent
   public void onEntityJoinWorld(EntityJoinWorldEvent event) {
      Entity entity = event.getEntity();
      World world = event.getWorld();
      if (!world.field_72995_K && LOTRDimensions.isModDimension(world) && entity instanceof SkeletonHorseEntity) {
         SkeletonHorseEntity skeleHorse = (SkeletonHorseEntity)entity;
         if (skeleHorse.func_190690_dh()) {
            LOTRLog.info("Cancelled the spawn of a skeleton trap horse in %s at %s", world.func_234923_W_().func_240901_a_(), skeleHorse.func_233580_cy_());
            event.setCanceled(true);
            return;
         }
      }

   }

   @SubscribeEvent
   public void onStartTrackingEntity(StartTracking event) {
      Entity entity = event.getTarget();
      PlayerEntity player = event.getPlayer();
      if (!entity.field_70170_p.field_72995_K) {
         if (entity instanceof MobEntity) {
            LOTRPacketHandler.sendTo(new SPacketSetAttackTarget((MobEntity)entity), (ServerPlayerEntity)player);
         }

         if (entity instanceof NPCEntity) {
            ((NPCEntity)entity).onPlayerStartTrackingNPC((ServerPlayerEntity)player);
         }
      }

   }

   @SubscribeEvent
   public void onCanLivingConvert(net.minecraftforge.event.entity.living.LivingConversionEvent.Pre event) {
      LivingEntity entity = event.getEntityLiving();
      EntityType outcome = event.getOutcome();
      World world = entity.field_70170_p;
      if (LOTRDimensions.isModDimension(world) && entity instanceof PigEntity && outcome == EntityType.field_233592_ba_) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onLivingUpdate(LivingUpdateEvent event) {
      LivingEntity entity = event.getEntityLiving();
      World world = entity.field_70170_p;
      if (!world.field_72995_K && entity instanceof FoxEntity && LOTRDimensions.isModDimension(world)) {
         FoxEntity fox = (FoxEntity)entity;
         String biomeCheckKey = String.format("%s:%s", "lotr", "FoxBiomeCheck");
         if (!fox.getPersistentData().func_74767_n(biomeCheckKey)) {
            net.minecraft.entity.passive.FoxEntity.Type initialFoxType = fox.func_213471_dV();
            net.minecraft.entity.passive.FoxEntity.Type newFoxType = initialFoxType;
            BlockPos entityPos = entity.func_233580_cy_();
            Biome biome = world.func_226691_t_(entity.func_233580_cy_());
            if (biome.func_225486_c(entityPos) < 0.15F) {
               newFoxType = net.minecraft.entity.passive.FoxEntity.Type.SNOW;
            }

            if (newFoxType != initialFoxType) {
               try {
                  Method m_setVariantType = ObfuscationReflectionHelper.findMethod(FoxEntity.class, "func_213474_a", new Class[]{net.minecraft.entity.passive.FoxEntity.Type.class});
                  LOTRUtil.unlockMethod(m_setVariantType);
                  m_setVariantType.invoke(fox, newFoxType);
               } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var11) {
                  LOTRLog.error("Error setting fox type based on biome");
                  var11.printStackTrace();
               }
            }

            fox.getPersistentData().func_74757_a(biomeCheckKey, true);
         }
      }

   }

   @SubscribeEvent
   public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
      LivingEntity entity = event.getEntityLiving();
      LivingEntity target = event.getTarget();
      if (!entity.field_70170_p.field_72995_K && entity instanceof MobEntity) {
         LOTRPacketHandler.sendToAllTrackingEntity(new SPacketSetAttackTarget((MobEntity)entity), entity);
      }

   }

   @SubscribeEvent
   public void onLivingAttacked(LivingAttackEvent event) {
      LivingEntity target = event.getEntityLiving();
      DamageSource source = event.getSource();
      LivingEntity attacker = source.func_76346_g() instanceof LivingEntity ? (LivingEntity)source.func_76346_g() : null;
      World world = target.field_70170_p;
      if (target instanceof AbstractHorseEntity && target.func_184188_bt().contains(attacker)) {
         event.setCanceled(true);
      } else if (attacker != null && !EntityFactionHelper.canEntityCauseDamageToTarget(attacker, target, true)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onProjectileImpact(ProjectileImpactEvent event) {
      Entity entity = event.getEntity();
      RayTraceResult rayTrace = event.getRayTraceResult();
      World world = entity.field_70170_p;
      if (entity instanceof ProjectileEntity) {
         ProjectileEntity projectile = (ProjectileEntity)entity;
         Entity shooter = projectile.func_234616_v_();
         if (rayTrace.func_216346_c() == Type.ENTITY) {
            Entity hitEntity = ((EntityRayTraceResult)rayTrace).func_216348_a();
            if (shooter instanceof LivingEntity && !EntityFactionHelper.canEntityCauseDamageToTarget((LivingEntity)shooter, hitEntity, true)) {
               event.setCanceled(true);
               return;
            }
         }
      }

   }

   @SubscribeEvent
   public void onLivingDeath(LivingDeathEvent event) {
      LivingEntity entity = event.getEntityLiving();
      DamageSource source = event.getSource();
      World world = entity.field_70170_p;
      if (!world.field_72995_K) {
         LivingEntity killerResponsible = null;
         PlayerEntity playerResponsible = null;
         boolean creditHiredUnit = false;
         boolean byNearbyUnit = false;
         if (source.func_76346_g() instanceof LivingEntity) {
            killerResponsible = (LivingEntity)source.func_76346_g();
         } else if (entity.func_94060_bK() != null) {
            killerResponsible = entity.func_94060_bK();
         }

         if (killerResponsible instanceof PlayerEntity) {
            playerResponsible = (PlayerEntity)killerResponsible;
         } else if (killerResponsible instanceof NPCEntity) {
            NPCEntity var9 = (NPCEntity)killerResponsible;
         }

         if (playerResponsible == null && entity.func_94060_bK() instanceof PlayerEntity) {
            playerResponsible = (PlayerEntity)entity.func_94060_bK();
         }

         Faction entityFaction = EntityFactionHelper.getFaction(entity);
         boolean wasPlayerSelfDefenceAgainstAlliedUnit = false;
         if (playerResponsible != null) {
            LOTRPlayerData playerData = LOTRLevelData.getSidedData(playerResponsible);
            AlignmentDataModule alignData = playerData.getAlignmentData();
            alignData.getAlignment(entityFaction);
            List forcedBonusFactions = null;
            AlignmentBonus alignmentBonus = null;
            if (!wasPlayerSelfDefenceAgainstAlliedUnit) {
               float bonus = NPCEntitySettingsManager.getEntityTypeSettings(entity).getKillAlignmentBonus();
               if (bonus != 0.0F && (!creditHiredUnit || creditHiredUnit && byNearbyUnit)) {
                  alignmentBonus = AlignmentBonus.forEntityKill(bonus, entity.func_200600_R().func_212546_e(), creditHiredUnit, EntityFactionHelper.isCivilian(entity));
               }
            }

            if (!creditHiredUnit && wasPlayerSelfDefenceAgainstAlliedUnit) {
            }

            if (alignmentBonus != null && alignmentBonus.bonus != 0.0F) {
               alignData.addAlignmentFromBonus((ServerPlayerEntity)playerResponsible, alignmentBonus, entityFaction, (List)forcedBonusFactions, (Entity)entity);
            }

            if (!creditHiredUnit && entityFaction.isPlayableAlignmentFaction()) {
               playerData.getFactionStatsData().getFactionStats(entityFaction).addMemberKill();
               entityFaction.getBonusesForKilling().forEach((enemyFac) -> {
                  playerData.getFactionStatsData().getFactionStats(enemyFac).addEnemyKill();
               });
               if (!playerResponsible.field_71075_bZ.field_75098_d && entityFaction.getAreasOfInfluence().isInAreaToRecordBountyKill(playerResponsible)) {
               }

               Faction pledgeFac = alignData.getPledgeFaction();
               if (pledgeFac != null && (pledgeFac == entityFaction || pledgeFac.isAlly(entityFaction))) {
                  alignData.onPledgeKill((ServerPlayerEntity)playerResponsible);
               }
            }
         }

         if (killerResponsible != null && !wasPlayerSelfDefenceAgainstAlliedUnit && EntityPredicates.field_188444_d.test(killerResponsible) && !FactionPointers.UNALIGNED.matches(entityFaction)) {
            int sentSpeeches = 0;
            int maxSpeeches = true;
            double angerRange = 16.0D;
            List nearbyAngerableNPCs = world.func_225316_b(MobEntity.class, entity.func_174813_aQ().func_186662_g(angerRange), NPCPredicates.selectAngerableByKill(entityFaction, killerResponsible));
            Iterator var24 = nearbyAngerableNPCs.iterator();

            while(var24.hasNext()) {
               MobEntity angered = (MobEntity)var24.next();
               if (angered.func_70638_az() == null) {
                  angered.func_70624_b(killerResponsible);
                  if (angered instanceof NPCEntity && angered.func_70638_az() == killerResponsible && killerResponsible instanceof PlayerEntity && sentSpeeches < 5) {
                     NPCEntity npc = (NPCEntity)angered;
                     if (npc.func_70068_e(killerResponsible) <= angerRange * angerRange && npc.sendNormalSpeechTo((ServerPlayerEntity)killerResponsible)) {
                        ++sentSpeeches;
                     }
                  }
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onWorldSave(Save event) {
      IWorld world = event.getWorld();
      if (world instanceof ServerWorld) {
         ServerWorld sWorld = (ServerWorld)world;
         boolean isCompleteGameSave = LOTRDimensions.isDimension((World)sWorld, World.field_234918_g_);
         if (isCompleteGameSave) {
            LOTRTime.save(sWorld);
         }
      }

   }

   @SubscribeEvent
   public void onServerChat(ServerChatEvent event) {
      this.speechGarbler.handle(event);
   }
}
