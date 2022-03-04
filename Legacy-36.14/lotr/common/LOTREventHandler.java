package lotr.common;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lotr.common.block.LOTRBlockArmorStand;
import lotr.common.block.LOTRBlockBarrel;
import lotr.common.block.LOTRBlockBookshelfStorage;
import lotr.common.block.LOTRBlockCommandTable;
import lotr.common.block.LOTRBlockCraftingTable;
import lotr.common.block.LOTRBlockEntJar;
import lotr.common.block.LOTRBlockFlowerPot;
import lotr.common.block.LOTRBlockGate;
import lotr.common.block.LOTRBlockGrapevine;
import lotr.common.block.LOTRBlockKebabStand;
import lotr.common.block.LOTRBlockMechanisedRail;
import lotr.common.block.LOTRBlockMug;
import lotr.common.block.LOTRBlockPlaceableFood;
import lotr.common.block.LOTRBlockPlate;
import lotr.common.block.LOTRBlockRottenLog;
import lotr.common.block.LOTRBlockSaplingBase;
import lotr.common.block.LOTRBlockUtumnoReturnPortalBase;
import lotr.common.block.LOTRBlockWeaponRack;
import lotr.common.block.LOTRVanillaSaplings;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.enchant.LOTREnchantmentWeaponSpecial;
import lotr.common.entity.LOTRBannerProtectable;
import lotr.common.entity.LOTREntityRegistry;
import lotr.common.entity.LOTRPlateFallingInfo;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.entity.npc.LOTREntityGaladhrimWarden;
import lotr.common.entity.npc.LOTREntityHobbitBounder;
import lotr.common.entity.npc.LOTREntityHuornBase;
import lotr.common.entity.npc.LOTREntityMarshWraith;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityOlogHai;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.entity.npc.LOTREntityRanger;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityTree;
import lotr.common.entity.npc.LOTREntityWargBombardier;
import lotr.common.entity.npc.LOTREntityWoodElfScout;
import lotr.common.entity.npc.LOTRMercenary;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.entity.npc.LOTRUnitTradeable;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.entity.projectile.LOTREntityFishHook;
import lotr.common.entity.projectile.LOTREntitySpear;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionBounties;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.inventory.LOTRContainerCraftingTable;
import lotr.common.item.LOTRItemBow;
import lotr.common.item.LOTRItemBrandingIron;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemDagger;
import lotr.common.item.LOTRItemDye;
import lotr.common.item.LOTRItemFeatherDyed;
import lotr.common.item.LOTRItemHaradRobes;
import lotr.common.item.LOTRItemHobbitPipe;
import lotr.common.item.LOTRItemLance;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.item.LOTRItemMug;
import lotr.common.item.LOTRItemPartyHat;
import lotr.common.item.LOTRItemPouch;
import lotr.common.item.LOTRMaterial;
import lotr.common.item.LOTRPoisonedDrinks;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.network.LOTRPacketEntityUUID;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketStopItemUse;
import lotr.common.network.LOTRPacketUtumnoKill;
import lotr.common.network.LOTRPacketWeaponFX;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.recipe.LOTRRecipePoisonWeapon;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.common.world.LOTRTeleporter;
import lotr.common.world.LOTRTeleporterUtumno;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.LOTRWorldProviderUtumno;
import lotr.common.world.LOTRWorldTypeMiddleEarth;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenDeadMarshes;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import lotr.common.world.biome.LOTRBiomeGenForodwaith;
import lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import lotr.common.world.biome.LOTRBiomeGenShire;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.structure2.LOTRStructureTimelapse;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent.Finish;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent.Stop;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ChunkDataEvent.Load;
import net.minecraftforge.event.world.ChunkWatchEvent.UnWatch;
import net.minecraftforge.event.world.ChunkWatchEvent.Watch;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.event.world.WorldEvent.Unload;
import org.apache.commons.lang3.StringUtils;

public class LOTREventHandler implements IFuelHandler {
   private LOTRItemBow proxyBowItemServer;
   private LOTRItemBow proxyBowItemClient;

   public LOTREventHandler() {
      FMLCommonHandler.instance().bus().register(this);
      MinecraftForge.EVENT_BUS.register(this);
      MinecraftForge.TERRAIN_GEN_BUS.register(this);
      GameRegistry.registerFuelHandler(this);
      new LOTRStructureTimelapse();
   }

   @SubscribeEvent
   public void onConfigChanged(OnConfigChangedEvent event) {
      if (event.modID.equals("lotr")) {
         LOTRConfig.load();
      }

   }

   @SubscribeEvent
   public void onCrafting(ItemCraftedEvent event) {
      EntityPlayer entityplayer = event.player;
      ItemStack itemstack = event.crafting;
      IInventory craftingInventory = event.craftMatrix;
      if (!entityplayer.field_70170_p.field_72995_K) {
         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Elven) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useElvenTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Uruk) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useUrukTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Rohirric) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRohirricTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Gondorian) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGondorianTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.WoodElven) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useWoodElvenTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Dwarven) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDwarvenTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Morgul) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMorgulTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Dunlending) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDunlendingTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Angmar) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useAngmarTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.NearHarad) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useNearHaradTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.HighElven) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHighElvenTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.BlueDwarven) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useBlueDwarvenTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Ranger) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRangerTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.DolGuldur) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDolGuldurTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Gundabad) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGundabadTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.HalfTroll) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHalfTrollTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.DolAmroth) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDolAmrothTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Moredain) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMoredainTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Tauredain) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useTauredainTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Dale) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDaleTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Dorwinion) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDorwinionTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Hobbit) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useHobbitTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Rhun) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRhunTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Rivendell) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useRivendellTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Umbar) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useUmbarTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Gulf) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useGulfTable);
         }

         if (entityplayer.field_71070_bA instanceof LOTRContainerCraftingTable.Bree) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useBreeTable);
         }

         if (itemstack.func_77973_b() == Items.field_151141_av) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftSaddle);
         }

         if (itemstack.func_77973_b() == LOTRMod.bronze) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftBronze);
         }

         if (itemstack.func_77973_b() == LOTRMod.appleCrumbleItem) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftAppleCrumble);
         }

         if (itemstack.func_77973_b() == LOTRMod.rabbitStew) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftRabbitStew);
         }

         if (itemstack.func_77973_b() == LOTRMod.saltedFlesh) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftSaltedFlesh);
         }

         if (itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.brick) && itemstack.func_77960_j() == 10) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftMithrilDwarvenBrick);
         }

         if (itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.orcBomb)) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftOrcBomb);
         }

         if (itemstack.func_77973_b() == LOTRMod.utumnoKey) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftUtumnoKey);
         }
      }

   }

   @SubscribeEvent
   public void onSmelting(ItemSmeltedEvent event) {
      EntityPlayer entityplayer = event.player;
      ItemStack itemstack = event.smelting;
      if (!entityplayer.field_70170_p.field_72995_K) {
         if (itemstack.func_77973_b() == LOTRMod.bronze) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.alloyBronze);
         }

         if (itemstack.func_77973_b() == LOTRMod.deerCooked) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.cookDeer);
         }

         if (itemstack.func_77973_b() == LOTRMod.blueDwarfSteel) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltBlueDwarfSteel);
         }

         if (itemstack.func_77973_b() == LOTRMod.elfSteel) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltElfSteel);
         }

         if (itemstack.func_77973_b() == LOTRMod.dwarfSteel) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltDwarfSteel);
         }

         if (itemstack.func_77973_b() == LOTRMod.urukSteel) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltUrukSteel);
         }

         if (itemstack.func_77973_b() == LOTRMod.orcSteel) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltOrcSteel);
         }

         if (itemstack.func_77973_b() == LOTRMod.blackUrukSteel) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.smeltBlackUrukSteel);
         }
      }

   }

   public int getBurnTime(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      if (item instanceof ItemBlock && ((ItemBlock)item).field_150939_a instanceof LOTRBlockSaplingBase) {
         return 100;
      } else if (item == LOTRMod.nauriteGem) {
         return 600;
      } else if (item == Item.func_150898_a(LOTRMod.blockOreStorage) && itemstack.func_77960_j() == 10) {
         return 6000;
      } else if (item == LOTRMod.mallornStick) {
         return 100;
      } else if (item instanceof ItemTool && ((ItemTool)item).func_150913_i() == LOTRMaterial.MALLORN.toToolMaterial()) {
         return 200;
      } else if (item instanceof ItemSword && ((ItemSword)item).func_150932_j().equals(LOTRMaterial.MALLORN.toToolMaterial().toString())) {
         return 200;
      } else if (item instanceof ItemHoe && ((ItemHoe)item).func_77842_f().equals(LOTRMaterial.MALLORN.toToolMaterial().toString())) {
         return 200;
      } else {
         return item != Items.field_151120_aE && item != Item.func_150898_a(LOTRMod.reeds) && item != Item.func_150898_a(LOTRMod.driedReeds) && item != Item.func_150898_a(LOTRMod.cornStalk) ? 0 : 100;
      }
   }

   @SubscribeEvent
   public void onPlayerLogin(PlayerLoggedInEvent event) {
      EntityPlayer entityplayer = event.player;
      World world = entityplayer.field_70170_p;
      if (!world.field_72995_K) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
         if (world.field_73011_w.field_76577_b instanceof LOTRWorldTypeMiddleEarth && entityplayermp.field_71093_bK == 0 && !LOTRLevelData.getData((EntityPlayer)entityplayermp).getTeleportedME()) {
            int dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
            Teleporter teleporter = new LOTRTeleporter(DimensionManager.getWorld(dimension), false);
            MinecraftServer.func_71276_C().func_71203_ab().transferPlayerToDimension(entityplayermp, dimension, teleporter);
            LOTRLevelData.getData((EntityPlayer)entityplayermp).setTeleportedME(true);
         }

         LOTRLevelData.sendLoginPacket(entityplayermp);
         LOTRLevelData.sendPlayerData(entityplayermp);
         LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, world);
         LOTRLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, world);
         LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayermp, world);
         LOTRLevelData.sendAllShieldsInWorldToPlayer(entityplayermp, world);
         LOTRDate.sendUpdatePacket(entityplayermp, false);
         LOTRFactionRelations.sendAllRelationsTo(entityplayermp);
         LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayermp);
         pd.send35AlignmentChoice(entityplayermp, world);
         pd.updateFastTravelClockFromLastOnlineTime(entityplayermp, world);
      }

   }

   @SubscribeEvent
   public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
      EntityPlayer entityplayer = event.player;
      if (!entityplayer.field_70170_p.field_72995_K) {
         LOTRLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.field_70170_p);
         LOTRLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, entityplayer.field_70170_p);
         LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayer, entityplayer.field_70170_p);
         LOTRLevelData.sendAllShieldsInWorldToPlayer(entityplayer, entityplayer.field_70170_p);
      }

   }

   @SubscribeEvent
   public void onPlayerRespawn(PlayerRespawnEvent event) {
      EntityPlayer entityplayer = event.player;
      World world = entityplayer.field_70170_p;
      if (!world.field_72995_K && entityplayer instanceof EntityPlayerMP && world instanceof WorldServer) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
         WorldServer worldserver = (WorldServer)world;
         ChunkCoordinates deathPoint = LOTRLevelData.getData((EntityPlayer)entityplayermp).getDeathPoint();
         int deathDimension = LOTRLevelData.getData((EntityPlayer)entityplayermp).getDeathDimension();
         if (deathDimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            if (LOTRConfig.middleEarthRespawning) {
               ChunkCoordinates bedLocation = entityplayermp.getBedLocation(entityplayermp.field_71093_bK);
               boolean hasBed = bedLocation != null;
               if (hasBed) {
                  hasBed = EntityPlayerMP.func_71056_a(worldserver, bedLocation, entityplayermp.isSpawnForced(entityplayermp.field_71093_bK)) != null;
               }

               ChunkCoordinates spawnLocation = hasBed ? bedLocation : worldserver.func_72861_E();
               double respawnThreshold = hasBed ? (double)LOTRConfig.MERBedRespawnThreshold : (double)LOTRConfig.MERWorldRespawnThreshold;
               if (deathPoint != null) {
                  boolean flag = (double)deathPoint.func_82371_e(spawnLocation) > respawnThreshold * respawnThreshold;
                  if (flag) {
                     double randomDistance = (double)MathHelper.func_76136_a(worldserver.field_73012_v, LOTRConfig.MERMinRespawn, LOTRConfig.MERMaxRespawn);
                     float angle = worldserver.field_73012_v.nextFloat() * 3.1415927F * 2.0F;
                     int i = deathPoint.field_71574_a + (int)(randomDistance * (double)MathHelper.func_76126_a(angle));
                     int k = deathPoint.field_71573_c + (int)(randomDistance * (double)MathHelper.func_76134_b(angle));
                     int j = LOTRMod.getTrueTopBlock(worldserver, i, k);
                     entityplayermp.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, entityplayermp.field_70177_z, entityplayermp.field_70125_A);
                     entityplayermp.field_71135_a.func_147364_a((double)i + 0.5D, (double)j, (double)k + 0.5D, entityplayermp.field_70177_z, entityplayermp.field_70125_A);
                  }
               }
            }
         } else if (deathDimension == LOTRDimension.UTUMNO.dimensionID) {
            LOTRTeleporterUtumno.newTeleporter(LOTRDimension.MIDDLE_EARTH.dimensionID).func_77185_a(entityplayermp, 0.0D, 0.0D, 0.0D, 0.0F);
            entityplayermp.field_71135_a.func_147364_a(entityplayermp.field_70165_t, entityplayermp.field_70163_u, entityplayermp.field_70161_v, entityplayermp.field_70177_z, entityplayermp.field_70125_A);
         }
      }

   }

   @SubscribeEvent
   public void onBlockInteract(PlayerInteractEvent event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = entityplayer.field_70170_p;
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      int i = event.x;
      int j = event.y;
      int k = event.z;
      int side = event.face;
      int logFacing;
      if (event.action == Action.RIGHT_CLICK_BLOCK) {
         Block block = world.func_147439_a(i, j, k);
         int meta = world.func_72805_g(i, j, k);
         LOTRBannerProtection.Permission perm = LOTRBannerProtection.Permission.FULL;
         boolean mightBeAbleToAlterWorld = entityplayer.func_70093_af() && itemstack != null;
         if (!mightBeAbleToAlterWorld) {
            if (!(block instanceof BlockDoor) && !(block instanceof BlockTrapDoor) && !(block instanceof BlockFenceGate) && !(block instanceof LOTRBlockGate)) {
               if (!(block instanceof BlockWorkbench) && !(block instanceof LOTRBlockCraftingTable) && !(block instanceof BlockAnvil) && !(block instanceof LOTRBlockCommandTable)) {
                  if (world.func_147438_o(i, j, k) instanceof IInventory) {
                     if (!(block instanceof LOTRBlockBarrel) && !(block instanceof LOTRBlockKebabStand)) {
                        perm = LOTRBannerProtection.Permission.CONTAINERS;
                     } else {
                        perm = LOTRBannerProtection.Permission.FOOD;
                     }
                  } else if (block instanceof LOTRBlockArmorStand) {
                     perm = LOTRBannerProtection.Permission.CONTAINERS;
                  } else if (!(block instanceof LOTRBlockWeaponRack) && block != Blocks.field_150342_X) {
                     if (block instanceof BlockJukebox) {
                        perm = LOTRBannerProtection.Permission.CONTAINERS;
                     } else if (block instanceof BlockEnderChest) {
                        perm = LOTRBannerProtection.Permission.PERSONAL_CONTAINERS;
                     } else if (!(block instanceof LOTRBlockPlate) && !(block instanceof BlockCake) && !(block instanceof LOTRBlockPlaceableFood) && !(block instanceof LOTRBlockMug) && !(block instanceof LOTRBlockEntJar)) {
                        if (block instanceof BlockBed) {
                           perm = LOTRBannerProtection.Permission.BEDS;
                        } else if (block instanceof BlockButton || block instanceof BlockLever) {
                           perm = LOTRBannerProtection.Permission.SWITCHES;
                        }
                     } else {
                        perm = LOTRBannerProtection.Permission.FOOD;
                     }
                  } else {
                     perm = LOTRBannerProtection.Permission.CONTAINERS;
                  }
               } else {
                  perm = LOTRBannerProtection.Permission.TABLES;
               }
            } else {
               perm = LOTRBannerProtection.Permission.DOORS;
            }
         }

         if (!world.field_72995_K && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, perm), true)) {
            event.setCanceled(true);
            if (block instanceof BlockDoor) {
               world.func_147471_g(i, j - 1, k);
               world.func_147471_g(i, j, k);
               world.func_147471_g(i, j + 1, k);
            } else if (block instanceof LOTRBlockPlate && LOTRBlockPlate.getFoodItem(world, i, j, k) != null) {
               world.func_147471_g(i, j, k);
            }

            return;
         }

         if (block == Blocks.field_150457_bL && meta == 0 && itemstack != null && LOTRBlockFlowerPot.canAcceptPlant(itemstack)) {
            LOTRMod.proxy.placeFlowerInPot(world, i, j, k, side, itemstack);
            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
            }

            event.setCanceled(true);
            return;
         }

         if (itemstack != null && block == Blocks.field_150383_bp && meta > 0) {
            LOTRItemMug.Vessel drinkVessel = null;
            LOTRItemMug.Vessel[] var14 = LOTRItemMug.Vessel.values();
            int var15 = var14.length;

            for(int var16 = 0; var16 < var15; ++var16) {
               LOTRItemMug.Vessel v = var14[var16];
               if (v.getEmptyVesselItem() == itemstack.func_77973_b()) {
                  drinkVessel = v;
                  break;
               }
            }

            if (drinkVessel != null) {
               LOTRMod.proxy.fillMugFromCauldron(world, i, j, k, side, itemstack);
               --itemstack.field_77994_a;
               ItemStack mugFill = new ItemStack(LOTRMod.mugWater);
               LOTRItemMug.setVessel(mugFill, drinkVessel, true);
               if (itemstack.field_77994_a <= 0) {
                  entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, mugFill);
               } else if (!entityplayer.field_71071_by.func_70441_a(mugFill)) {
                  entityplayer.func_71019_a(mugFill, false);
               }

               event.setCanceled(true);
               return;
            }
         }

         if (!world.field_72995_K && block instanceof LOTRBlockPlate && entityplayer.func_70093_af()) {
            TileEntity tileentity = world.func_147438_o(i, j, k);
            if (tileentity instanceof LOTRTileEntityPlate) {
               LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
               ItemStack plateItem = plate.getFoodItem();
               if (plateItem != null) {
                  ((LOTRBlockPlate)block).dropOnePlateItem(plate);
                  --plateItem.field_77994_a;
                  plate.setFoodItem(plateItem);
                  event.setCanceled(true);
                  return;
               }
            }
         }

         if (!world.field_72995_K && block instanceof LOTRBlockMechanisedRail && entityplayer.func_70093_af() && ((LOTRBlockMechanisedRail)block).onShiftClickActivateFirst(world, i, j, k, entityplayer, side)) {
            event.setCanceled(true);
            return;
         }

         boolean onInnerFace;
         if (!world.field_72995_K && block instanceof BlockCauldron && itemstack != null) {
            logFacing = BlockCauldron.func_150027_b(meta);
            if (logFacing > 0) {
               onInnerFace = false;
               Item item = itemstack.func_77973_b();
               if (LOTRRecipePoisonWeapon.poisonedToInput.containsKey(item)) {
                  Item inputWeapon = (Item)LOTRRecipePoisonWeapon.poisonedToInput.get(item);
                  itemstack.func_150996_a(inputWeapon);
                  onInnerFace = true;
               } else if (item instanceof LOTRItemPouch && LOTRItemPouch.isPouchDyed(itemstack)) {
                  LOTRItemPouch.removePouchDye(itemstack);
                  onInnerFace = true;
               } else if (item instanceof LOTRItemHobbitPipe && LOTRItemHobbitPipe.isPipeDyed(itemstack)) {
                  LOTRItemHobbitPipe.removePipeDye(itemstack);
                  onInnerFace = true;
               } else if (!(item instanceof LOTRItemLeatherHat) || !LOTRItemLeatherHat.isHatDyed(itemstack) && !LOTRItemLeatherHat.isFeatherDyed(itemstack)) {
                  if (item instanceof LOTRItemFeatherDyed && LOTRItemFeatherDyed.isFeatherDyed(itemstack)) {
                     LOTRItemFeatherDyed.removeFeatherDye(itemstack);
                     onInnerFace = true;
                  } else if (item instanceof LOTRItemHaradRobes && LOTRItemHaradRobes.areRobesDyed(itemstack)) {
                     LOTRItemHaradRobes.removeRobeDye(itemstack);
                     onInnerFace = true;
                  } else if (item instanceof LOTRItemPartyHat && LOTRItemPartyHat.isHatDyed(itemstack)) {
                     LOTRItemPartyHat.removeHatDye(itemstack);
                     onInnerFace = true;
                  }
               } else {
                  LOTRItemLeatherHat.removeHatAndFeatherDye(itemstack);
                  onInnerFace = true;
               }

               if (onInnerFace) {
                  ((BlockCauldron)block).func_150024_a(world, i, j, k, logFacing - 1);
                  event.setCanceled(true);
                  return;
               }
            }
         }

         if (!world.field_72995_K && itemstack != null && itemstack.func_77973_b() == Items.field_151100_aR && itemstack.func_77960_j() == 15 && block instanceof BlockLog) {
            logFacing = meta & 12;
            if (logFacing != 12) {
               onInnerFace = false;
               if (logFacing == 0) {
                  onInnerFace = side == 0 || side == 1;
               } else if (logFacing == 4) {
                  onInnerFace = side == 4 || side == 5;
               } else if (logFacing == 8) {
                  onInnerFace = side == 2 || side == 3;
               }

               if (onInnerFace) {
                  meta |= 12;
                  world.func_72921_c(i, j, k, meta, 3);
                  world.func_72926_e(2005, i, j, k, 0);
                  if (!entityplayer.field_71075_bZ.field_75098_d) {
                     --itemstack.field_77994_a;
                  }

                  event.setCanceled(true);
                  return;
               }
            }
         }

         if (!world.field_72995_K && LOTRBlockGrapevine.isFullGrownGrapes(block, meta)) {
            LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j, k);
         }

         if (block == Blocks.field_150342_X && !entityplayer.func_70093_af() && LOTRBlockBookshelfStorage.canOpenBookshelf(world, i, j, k, entityplayer) && !world.field_72995_K) {
            world.func_147465_d(i, j, k, LOTRMod.bookshelfStorage, 0, 3);
            boolean flag = LOTRMod.bookshelfStorage.func_149727_a(world, i, j, k, entityplayer, side, 0.5F, 0.5F, 0.5F);
            if (!flag) {
               world.func_147465_d(i, j, k, Blocks.field_150342_X, 0, 3);
            }

            event.setCanceled(true);
            return;
         }

         if (block == Blocks.field_150381_bn && !LOTRConfig.isEnchantingEnabled(world) && !world.field_72995_K) {
            LOTRLevelData.getData(entityplayer).sendMessageIfNotReceived(LOTRGuiMessageTypes.ENCHANTING);
            event.setCanceled(true);
            return;
         }

         if (block == Blocks.field_150382_bo && !LOTRConfig.enablePotionBrewing) {
            event.setCanceled(true);
            return;
         }

         if (block == Blocks.field_150477_bB && LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO && LOTRConfig.disableEnderChestsUtumno) {
            event.setCanceled(true);
            return;
         }

         if (block == Blocks.field_150467_bQ && (LOTRConfig.isLOTREnchantingEnabled(world) || !LOTRConfig.isEnchantingEnabled(world)) && !world.field_72995_K) {
            entityplayer.openGui(LOTRMod.instance, 53, world, i, j, k);
            event.setCanceled(true);
            return;
         }
      }

      if (event.action == Action.LEFT_CLICK_BLOCK) {
         world.func_147439_a(i, j, k);
         world.func_72805_g(i, j, k);
         ForgeDirection dir = ForgeDirection.getOrientation(side);
         int i1 = i + dir.offsetX;
         logFacing = j + dir.offsetY;
         int k1 = k + dir.offsetZ;
         Block block1 = world.func_147439_a(i1, logFacing, k1);
         if (!world.field_72995_K && LOTRBannerProtection.isProtected(world, i1, logFacing, k1, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true) && block1 instanceof BlockFire) {
            event.setCanceled(true);
            return;
         }
      }

   }

   @SubscribeEvent
   public void onAnvilUpdate(AnvilUpdateEvent event) {
      if (!LOTRConfig.enchantingVanilla && (event.left != null && event.left.func_77973_b() instanceof ItemEnchantedBook || event.right != null && event.right.func_77973_b() instanceof ItemEnchantedBook)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onHarvestCheck(HarvestCheck event) {
      EntityPlayer entityplayer = event.entityPlayer;
      Block block = event.block;
      ItemStack itemstack = entityplayer.func_71045_bC();
      if (itemstack != null && block instanceof BlockWeb && itemstack.func_77973_b() instanceof ItemShears) {
         event.success = true;
      }

   }

   @SubscribeEvent
   public void getBlockDrops(HarvestDropsEvent event) {
      EntityPlayer entityplayer = event.harvester;
      Block block = event.block;
      if (entityplayer != null) {
         ItemStack itemstack = entityplayer.func_71045_bC();
         if (itemstack != null && block instanceof BlockWeb && itemstack.func_77973_b() instanceof ItemShears) {
            int meta = 0;
            Item item = Item.func_150898_a(block);
            if (item != null && item.func_77614_k()) {
               meta = event.blockMetadata;
            }

            ItemStack silkDrop = new ItemStack(item, 1, meta);
            event.drops.clear();
            event.drops.add(silkDrop);
         }
      }

   }

   @SubscribeEvent
   public void onBreakingSpeed(BreakSpeed event) {
      EntityPlayer entityplayer = event.entityPlayer;
      Block block = event.block;
      int meta = event.metadata;
      float speed = event.newSpeed;
      ItemStack itemstack = entityplayer.func_71045_bC();
      if (itemstack != null) {
         float baseDigSpeed = itemstack.func_77973_b().getDigSpeed(itemstack, block, meta);
         if (baseDigSpeed > 1.0F) {
            speed *= LOTREnchantmentHelper.calcToolEfficiency(itemstack);
         }
      }

      event.newSpeed = speed;
   }

   @SubscribeEvent
   public void onBlockBreaking(BreakSpeed event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = entityplayer.field_70170_p;
      Block block = event.block;
      int i = event.x;
      int j = event.y;
      int k = event.z;
      if (block instanceof LOTRWorldProviderUtumno.UtumnoBlock && LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO) {
         boolean canMine = false;
         ItemStack itemstack = entityplayer.func_71045_bC();
         if (itemstack != null && itemstack.func_77973_b() == LOTRMod.utumnoPickaxe) {
            canMine = true;
            int levelFuzz = 2;

            for(int l = 0; l < LOTRUtumnoLevel.values().length - 1; ++l) {
               LOTRUtumnoLevel levelUpper = LOTRUtumnoLevel.values()[l];
               LOTRUtumnoLevel levelLower = LOTRUtumnoLevel.values()[l + 1];
               if (j >= levelLower.getHighestCorridorRoof() + levelFuzz && j <= levelUpper.getLowestCorridorFloor() - levelFuzz) {
                  canMine = false;
               }
            }
         }

         if (!canMine) {
            event.setCanceled(true);
            return;
         }
      }

   }

   @SubscribeEvent
   public void onBlockBreak(BreakEvent event) {
      EntityPlayer entityplayer = event.getPlayer();
      Block block = event.block;
      int meta = event.blockMetadata;
      World world = event.world;
      int i = event.x;
      int j = event.y;
      int k = event.z;
      if (!world.field_72995_K && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
         event.setCanceled(true);
      } else {
         if (!world.field_72995_K && entityplayer != null && !entityplayer.field_71075_bZ.field_75098_d && block.isWood(world, i, j, k) && !LOTRBlockRottenLog.isRottenWood(block)) {
            List trees = world.func_72872_a(LOTREntityTree.class, AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).func_72314_b(16.0D, 16.0D, 16.0D));
            if (!trees.isEmpty()) {
               boolean sentMessage = false;
               boolean penalty = false;

               for(int l = 0; l < trees.size(); ++l) {
                  LOTREntityTree tree = (LOTREntityTree)trees.get(l);
                  if (!tree.hiredNPCInfo.isActive || tree.hiredNPCInfo.getHiringPlayer() != entityplayer) {
                     tree.func_70624_b(entityplayer);
                     if (tree instanceof LOTREntityEnt && !sentMessage) {
                        tree.sendSpeechBank(entityplayer, "ent/ent/defendTrees");
                        sentMessage = true;
                     }

                     if (world.func_72807_a(i, k) instanceof LOTRBiomeGenFangorn && !penalty) {
                        LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.FANGORN_TREE_PENALTY, LOTRFaction.FANGORN, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D);
                        penalty = true;
                     }
                  }
               }
            }
         }

         if (!world.field_72995_K && entityplayer != null) {
            if (LOTRBlockGrapevine.isFullGrownGrapes(block, meta)) {
               LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j, k);
            } else {
               boolean grapesAbove = false;

               for(int j1 = 1; j1 <= 3; ++j1) {
                  int j2 = j + j1;
                  Block above = world.func_147439_a(i, j2, k);
                  int aboveMeta = world.func_72805_g(i, j2, k);
                  if (LOTRBlockGrapevine.isFullGrownGrapes(above, aboveMeta)) {
                     grapesAbove = true;
                  }
               }

               if (grapesAbove) {
                  LOTREntityDorwinionGuard.defendGrapevines(entityplayer, world, i, j + 1, k);
               }
            }
         }

      }
   }

   @SubscribeEvent
   public void onBlockPlace(PlaceEvent event) {
      EntityPlayer entityplayer = event.player;
      Block block = event.block;
      World world = event.world;
      int i = event.x;
      int j = event.y;
      int k = event.z;
      if (world.field_73011_w instanceof LOTRWorldProviderUtumno && LOTRUtumnoLevel.forY(j) == LOTRUtumnoLevel.FIRE && block == Blocks.field_150432_aD) {
         world.func_147465_d(i, j, k, Blocks.field_150350_a, 0, 3);
         LOTRWorldProviderUtumno.doEvaporateFX(world, i, j, k);
      }
   }

   @SubscribeEvent
   public void onArrowNock(ArrowNockEvent event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = entityplayer.field_70170_p;
      ItemStack itemstack = event.result;
      if (itemstack != null && itemstack.func_77973_b() instanceof ItemBow && !(itemstack.func_77973_b() instanceof LOTRItemBow) && !(itemstack.func_77973_b() instanceof LOTRItemCrossbow)) {
         if (!world.field_72995_K) {
            if (this.proxyBowItemServer == null) {
               this.proxyBowItemServer = new LOTRItemBow(ToolMaterial.WOOD);
               event.result = this.proxyBowItemServer.func_77659_a(itemstack, world, entityplayer);
               this.proxyBowItemServer = null;
               event.setCanceled(true);
               return;
            }
         } else if (this.proxyBowItemClient == null) {
            this.proxyBowItemClient = new LOTRItemBow(ToolMaterial.WOOD);
            event.result = this.proxyBowItemClient.func_77659_a(itemstack, world, entityplayer);
            this.proxyBowItemClient = null;
            event.setCanceled(true);
            return;
         }
      }

   }

   @SubscribeEvent
   public void onItemUseStop(Stop event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = entityplayer.field_70170_p;
      ItemStack itemstack = event.item;
      int usingTick = event.duration;
      if (itemstack != null && itemstack.func_77973_b() instanceof ItemBow && !(itemstack.func_77973_b() instanceof LOTRItemBow) && !(itemstack.func_77973_b() instanceof LOTRItemCrossbow)) {
         if (!world.field_72995_K) {
            if (this.proxyBowItemServer == null) {
               this.proxyBowItemServer = new LOTRItemBow(ToolMaterial.WOOD);
            }

            this.proxyBowItemServer.func_77615_a(itemstack, world, entityplayer, usingTick);
            this.proxyBowItemServer = null;
            event.setCanceled(true);
         } else {
            if (this.proxyBowItemClient == null) {
               this.proxyBowItemClient = new LOTRItemBow(ToolMaterial.WOOD);
            }

            this.proxyBowItemClient.func_77615_a(itemstack, world, entityplayer, usingTick);
            this.proxyBowItemClient = null;
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void onItemUseFinish(Finish event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = entityplayer.field_70170_p;
      ItemStack itemstack = event.item;
      ItemStack result = event.result;
      if (!world.field_72995_K && LOTRPoisonedDrinks.isDrinkPoisoned(itemstack)) {
         LOTRPoisonedDrinks.addPoisonEffect(entityplayer, itemstack);
      }

   }

   @SubscribeEvent
   public void onUseBonemeal(BonemealEvent event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = event.world;
      Random rand = world.field_73012_v;
      int i = event.x;
      int j = event.y;
      int k = event.z;
      if (!world.field_72995_K) {
         if (event.block instanceof LOTRBlockSaplingBase) {
            LOTRBlockSaplingBase sapling = (LOTRBlockSaplingBase)event.block;
            int meta = world.func_72805_g(i, j, k);
            if ((double)rand.nextFloat() < 0.45D) {
               sapling.incrementGrowth(world, i, j, k, rand);
            }

            if (sapling == LOTRMod.sapling4 && (meta & 7) == 1 && world.func_147439_a(i, j, k) == LOTRMod.wood4 && world.func_72805_g(i, j, k) == 1) {
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.growBaobab);
            }

            event.setResult(Result.ALLOW);
            return;
         }

         if (event.block.canSustainPlant(world, i, j, k, ForgeDirection.UP, Blocks.field_150329_H) && event.block instanceof IGrowable) {
            BiomeGenBase biomegenbase = world.func_72807_a(i, k);
            if (biomegenbase instanceof LOTRBiome) {
               LOTRBiome biome = (LOTRBiome)biomegenbase;

               label61:
               for(int attempts = 0; attempts < 128; ++attempts) {
                  int i1 = i;
                  int j1 = j + 1;
                  int k1 = k;

                  for(int subAttempts = 0; subAttempts < attempts / 16; ++subAttempts) {
                     i1 += rand.nextInt(3) - 1;
                     j1 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
                     k1 += rand.nextInt(3) - 1;
                     Block below = world.func_147439_a(i1, j1 - 1, k1);
                     if (!(below instanceof IGrowable) || !below.canSustainPlant(world, i1, j1 - 1, k1, ForgeDirection.UP, Blocks.field_150329_H) || world.func_147439_a(i1, j1, k1).func_149721_r()) {
                        continue label61;
                     }
                  }

                  if (world.func_147439_a(i1, j1, k1).func_149688_o() == Material.field_151579_a) {
                     if (rand.nextInt(8) > 0) {
                        LOTRBiome.GrassBlockAndMeta obj = biome.getRandomGrass(rand);
                        Block block = obj.block;
                        int meta = obj.meta;
                        if (block.func_149718_j(world, i1, j1, k1)) {
                           world.func_147465_d(i1, j1, k1, block, meta, 3);
                        }
                     } else {
                        biome.plantFlower(world, rand, i1, j1, k1);
                     }
                  }
               }

               event.setResult(Result.ALLOW);
               return;
            }
         }
      }

   }

   @SubscribeEvent
   public void onSaplingGrow(SaplingGrowTreeEvent event) {
      World world = event.world;
      int i = event.x;
      int j = event.y;
      int k = event.z;
      Block block = world.func_147439_a(i, j, k);
      if (block == Blocks.field_150345_g) {
         LOTRVanillaSaplings.growTree(world, i, j, k, event.rand);
         event.setResult(Result.DENY);
      }
   }

   @SubscribeEvent
   public void onUseHoe(UseHoeEvent event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = event.world;
      int i = event.x;
      int j = event.y;
      int k = event.z;
      Block block = world.func_147439_a(i, j, k);
      LOTRBlockGrapevine.hoeing = true;
      if (world.func_147439_a(i, j + 1, k).isAir(world, i, j + 1, k) && (block == LOTRMod.mudGrass || block == LOTRMod.mud)) {
         Block tilled = LOTRMod.mudFarmland;
         world.func_72908_a((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), tilled.field_149762_H.func_150498_e(), (tilled.field_149762_H.func_150497_c() + 1.0F) / 2.0F, tilled.field_149762_H.func_150494_d() * 0.8F);
         if (!world.field_72995_K) {
            world.func_147449_b(i, j, k, tilled);
         }

         event.setResult(Result.ALLOW);
      } else {
         LOTRBlockGrapevine.hoeing = true;
      }
   }

   @SubscribeEvent
   public void onFillBucket(FillBucketEvent event) {
      EntityPlayer entityplayer = event.entityPlayer;
      ItemStack itemstack = event.current;
      World world = event.world;
      MovingObjectPosition target = event.target;
      if (target.field_72313_a == MovingObjectType.BLOCK) {
         int i = target.field_72311_b;
         int j = target.field_72312_c;
         int k = target.field_72309_d;
         if (!world.field_72995_K && LOTRBannerProtection.isProtected(world, i, j, k, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
            event.setCanceled(true);
            return;
         }

         if (world.field_73011_w instanceof LOTRWorldProviderUtumno && LOTRUtumnoLevel.forY(j) == LOTRUtumnoLevel.FIRE && itemstack != null && itemstack.func_77973_b() == Items.field_151131_as) {
            LOTRWorldProviderUtumno.doEvaporateFX(world, i, j, k);
            event.result = new ItemStack(Items.field_151133_ar);
            event.setResult(Result.ALLOW);
            return;
         }
      }

   }

   @SubscribeEvent
   public void onItemPickup(EntityItemPickupEvent event) {
      EntityPlayer entityplayer = event.entityPlayer;
      ItemStack itemstack = event.item.func_92059_d();
      if (!entityplayer.field_70170_p.field_72995_K) {
         if (itemstack.field_77994_a > 0) {
            for(int i = 0; i < entityplayer.field_71071_by.func_70302_i_(); ++i) {
               ItemStack itemInSlot = entityplayer.field_71071_by.func_70301_a(i);
               if (itemInSlot != null && itemInSlot.func_77973_b() == LOTRMod.pouch) {
                  LOTRItemPouch.tryAddItemToPouch(itemInSlot, itemstack, true);
                  if (itemstack.field_77994_a <= 0) {
                     break;
                  }
               }
            }

            if (itemstack.field_77994_a <= 0) {
               event.setResult(Result.ALLOW);
            }
         }

         if (itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.athelas)) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.findAthelas);
         }

         if (itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.clover) && itemstack.func_77960_j() == 1) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.findFourLeafClover);
         }

         if (itemstack.func_77973_b() == LOTRMod.kineArawHorn) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getKineArawHorn);
         }

         if (LOTRConfig.enchantingAutoRemoveVanilla) {
            dechant(itemstack, entityplayer);
         }
      }

   }

   private static boolean dechant(ItemStack itemstack, EntityPlayer entityplayer) {
      if (!entityplayer.field_71075_bZ.field_75098_d && itemstack != null && itemstack.func_77948_v()) {
         Item item = itemstack.func_77973_b();
         if (!(item instanceof ItemFishingRod)) {
            itemstack.func_77978_p().func_82580_o("ench");
            return true;
         }
      }

      return false;
   }

   @SubscribeEvent
   public void onWorldSave(Save event) {
      World world = event.world;
      if (!world.field_72995_K && world.field_73011_w.field_76574_g == 0) {
         LOTRTime.save();
      }

   }

   @SubscribeEvent
   public void onWorldUnload(Unload event) {
      World world = event.world;
      if (world.field_73011_w instanceof LOTRWorldProvider) {
         LOTRBiomeVariantStorage.clearAllVariants(world);
      }

   }

   @SubscribeEvent
   public void onChunkDataLoad(Load event) {
      World world = event.world;
      Chunk chunk = event.getChunk();
      NBTTagCompound data = event.getData();
      if (!world.field_72995_K && world.field_73011_w instanceof LOTRWorldProvider) {
         LOTRBiomeVariantStorage.loadChunkVariants(world, chunk, data);
      }

   }

   @SubscribeEvent
   public void onChunkDataSave(net.minecraftforge.event.world.ChunkDataEvent.Save event) {
      World world = event.world;
      Chunk chunk = event.getChunk();
      NBTTagCompound data = event.getData();
      if (!world.field_72995_K && world.field_73011_w instanceof LOTRWorldProvider) {
         LOTRBiomeVariantStorage.saveChunkVariants(world, chunk, data);
      }

   }

   @SubscribeEvent
   public void onChunkStartWatching(Watch event) {
      EntityPlayerMP entityplayer = event.player;
      World world = entityplayer.field_70170_p;
      ChunkCoordIntPair chunkCoords = event.chunk;
      Chunk chunk = world.func_72964_e(chunkCoords.field_77276_a, chunkCoords.field_77275_b);
      if (!world.field_72995_K && world.field_73011_w instanceof LOTRWorldProvider) {
         LOTRBiomeVariantStorage.sendChunkVariantsToPlayer(world, chunk, entityplayer);
      }

   }

   @SubscribeEvent
   public void onChunkStopWatching(UnWatch event) {
      EntityPlayerMP entityplayer = event.player;
      World world = entityplayer.field_70170_p;
      ChunkCoordIntPair chunkCoords = event.chunk;
      Chunk chunk = world.func_72964_e(chunkCoords.field_77276_a, chunkCoords.field_77275_b);
      if (!world.field_72995_K && world.field_73011_w instanceof LOTRWorldProvider) {
         LOTRBiomeVariantStorage.sendUnwatchToPlayer(world, chunk, entityplayer);
      }

   }

   @SubscribeEvent
   public void onEntitySpawnAttempt(CheckSpawn event) {
      EntityLivingBase entity = event.entityLiving;
      World world = entity.field_70170_p;
      if (!world.field_72995_K && entity instanceof EntityMob && LOTRBannerProtection.isProtected(world, entity, LOTRBannerProtection.anyBanner(), false)) {
         event.setResult(Result.DENY);
      }
   }

   @SubscribeEvent
   public void onEntityJoinWorld(EntityJoinWorldEvent event) {
      Entity entity = event.entity;
      World world = entity.field_70170_p;
      if (!world.field_72995_K && entity instanceof EntityXPOrb && !LOTRConfig.enchantingVanilla && world.field_73011_w instanceof LOTRWorldProvider) {
         event.setCanceled(true);
      } else {
         if (!world.field_72995_K && entity instanceof EntityCreature) {
            EntityCreature entitycreature = (EntityCreature)entity;
            String s = EntityList.func_75621_b(entitycreature);
            Object obj = LOTREntityRegistry.registeredNPCs.get(s);
            if (obj != null) {
               LOTREntityRegistry.RegistryInfo info = (LOTREntityRegistry.RegistryInfo)obj;
               if (info.shouldTargetEnemies) {
                  LOTREntityNPC.addTargetTasks(entitycreature, 100, LOTREntityAINearestAttackableTargetBasic.class);
               }
            }
         }

         if (!world.field_72995_K && entity.getClass() == EntityFishHook.class && world.field_73011_w instanceof LOTRWorldProvider) {
            EntityFishHook oldFish = (EntityFishHook)entity;
            NBTTagCompound fishData = new NBTTagCompound();
            oldFish.func_70109_d(fishData);
            oldFish.func_70106_y();
            LOTREntityFishHook newFish = new LOTREntityFishHook(world);
            newFish.func_70020_e(fishData);
            newFish.field_146042_b = oldFish.field_146042_b;
            if (newFish.field_146042_b != null) {
               newFish.field_146042_b.field_71104_cf = newFish;
               newFish.setPlayerID(newFish.field_146042_b.func_145782_y());
            }

            world.func_72838_d(newFish);
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public void onStartTrackingEntity(StartTracking event) {
      Entity entity = event.target;
      EntityPlayer entityplayer = event.entityPlayer;
      if (!entity.field_70170_p.field_72995_K && entity instanceof LOTREntityNPC) {
         EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
         LOTREntityNPC npc = (LOTREntityNPC)entity;
         npc.onPlayerStartTracking(entityplayermp);
      }

      if (!entity.field_70170_p.field_72995_K && entity instanceof LOTRRandomSkinEntity) {
         LOTRPacketEntityUUID packet = new LOTRPacketEntityUUID(entity.func_145782_y(), entity.func_110124_au());
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

      if (!entity.field_70170_p.field_72995_K && entity instanceof LOTREntityBanner) {
         ((LOTREntityBanner)entity).sendBannerToPlayer(entityplayer, false, false);
      }

   }

   @SubscribeEvent
   public void onLivingUpdate(LivingUpdateEvent event) {
      EntityLivingBase entity = event.entityLiving;
      World world = entity.field_70170_p;
      if (!world.field_72995_K) {
         LOTREnchantmentHelper.onEntityUpdate(entity);
      }

      if (LOTRConfig.enchantingAutoRemoveVanilla && !world.field_72995_K && entity instanceof EntityPlayer && entity.field_70173_aa % 60 == 0) {
         EntityPlayer entityplayer = (EntityPlayer)entity;

         for(int l = 0; l < entityplayer.field_71071_by.func_70302_i_(); ++l) {
            ItemStack itemstack = entityplayer.field_71071_by.func_70301_a(l);
            if (itemstack != null) {
               dechant(itemstack, entityplayer);
            }
         }
      }

      boolean inWater = entity.func_70090_H();
      int j;
      int k;
      int i1;
      int j1;
      boolean flag;
      int i;
      int k1;
      if (!world.field_72995_K && LOTRMod.canSpawnMobs(world) && entity.func_70089_S() && inWater && entity.field_70154_o == null) {
         flag = true;
         if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
            flag = false;
         }

         if (entity instanceof EntityWaterMob || entity instanceof LOTREntityMarshWraith) {
            flag = false;
         }

         if (flag) {
            i = MathHelper.func_76128_c(entity.field_70165_t);
            j = MathHelper.func_76128_c(entity.field_70161_v);

            for(k = world.func_72825_h(i, j); world.func_147439_a(i, k + 1, j).func_149688_o().func_76224_d() || world.func_147439_a(i, k + 1, j).func_149688_o().func_76220_a(); ++k) {
            }

            if ((double)k - entity.field_70121_D.field_72338_b < 2.0D && world.func_147439_a(i, k, j).func_149688_o() == Material.field_151586_h && world.func_72807_a(i, j) instanceof LOTRBiomeGenDeadMarshes) {
               double wraithRange = 12.0D;
               double wraithRangeSq = 144.0D;
               double wraithCheckRange = 15.0D;
               List nearbyWraiths = world.func_72872_a(LOTREntityMarshWraith.class, entity.field_70121_D.func_72314_b(15.0D, 15.0D, 15.0D));
               boolean anyNearbyWraiths = false;

               for(i1 = 0; i1 < nearbyWraiths.size(); ++i1) {
                  LOTREntityMarshWraith wraith = (LOTREntityMarshWraith)nearbyWraiths.get(i1);
                  if (wraith.func_70638_az() == entity && wraith.getDeathFadeTime() == 0) {
                     anyNearbyWraiths = true;
                     break;
                  }
               }

               if (!anyNearbyWraiths) {
                  LOTREntityMarshWraith wraith = new LOTREntityMarshWraith(world);
                  k1 = i + MathHelper.func_76136_a(world.field_73012_v, -3, 3);
                  j1 = j + MathHelper.func_76136_a(world.field_73012_v, -3, 3);
                  int j1 = world.func_72825_h(k1, j1);
                  wraith.func_70012_b((double)k1 + 0.5D, (double)j1, (double)j1 + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                  if (wraith.func_70068_e(entity) <= 144.0D) {
                     world.func_72838_d(wraith);
                     wraith.func_70624_b(entity);
                     wraith.attackTargetUUID = entity.func_110124_au();
                     world.func_72956_a(wraith, "lotr:wraith.spawn", 1.0F, 0.7F + world.field_73012_v.nextFloat() * 0.6F);
                  }
               }
            }
         }
      }

      boolean attacked;
      float alignment;
      float level;
      int k;
      if (!world.field_72995_K && LOTRMod.canSpawnMobs(world) && entity.func_70089_S() && world.func_72935_r()) {
         float f = 0.0F;
         i = 0;
         if (LOTRFaction.HOBBIT.isBadRelation(LOTRMod.getNPCFaction(entity))) {
            alignment = entity.func_110138_aP() + (float)entity.func_70658_aO();
            f = alignment * 2.5F;
            k = (int)(alignment / 15.0F);
            i = 2 + world.field_73012_v.nextInt(k + 1);
         } else if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            level = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.HOBBIT);
            if (!entityplayer.field_71075_bZ.field_75098_d && level < 0.0F) {
               f = -level;
               k = (int)(f / 50.0F);
               i = 2 + world.field_73012_v.nextInt(k + 1);
            }
         }

         if (f > 0.0F) {
            f = Math.min(f, 2000.0F);
            j = (int)(2000000.0F / f);
            i = Math.min(i, 5);
            k = MathHelper.func_76128_c(entity.field_70165_t);
            k = MathHelper.func_76128_c(entity.field_70161_v);
            world.func_72825_h(k, k);
            if (world.field_73012_v.nextInt(j) == 0 && world.func_72807_a(k, k) instanceof LOTRBiomeGenShire) {
               List nearbyBounders = world.func_72872_a(LOTREntityHobbitBounder.class, entity.field_70121_D.func_72314_b(12.0D, 6.0D, 12.0D));
               if (nearbyBounders.isEmpty()) {
                  attacked = false;
                  boolean playedHorn = false;

                  for(int l = 0; l < i; ++l) {
                     LOTREntityHobbitBounder bounder = new LOTREntityHobbitBounder(world);

                     for(int l1 = 0; l1 < 32; ++l1) {
                        i1 = k - world.field_73012_v.nextInt(12) + world.field_73012_v.nextInt(12);
                        k1 = k - world.field_73012_v.nextInt(12) + world.field_73012_v.nextInt(12);
                        j1 = world.func_72825_h(i1, k1);
                        if (world.func_147439_a(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP) && !world.func_147439_a(i1, j1, k1).func_149721_r() && !world.func_147439_a(i1, j1 + 1, k1).func_149721_r()) {
                           bounder.func_70012_b((double)i1 + 0.5D, (double)j1, (double)k1 + 0.5D, 0.0F, 0.0F);
                           if (bounder.func_70601_bi() && (double)entity.func_70032_d(bounder) > 6.0D) {
                              bounder.func_110161_a((IEntityLivingData)null);
                              world.func_72838_d(bounder);
                              bounder.func_70624_b(entity);
                              if (!attacked && entity instanceof EntityPlayer) {
                                 EntityPlayer entityplayer = (EntityPlayer)entity;
                                 bounder.sendSpeechBank(entityplayer, bounder.getSpeechBank(entityplayer));
                                 attacked = true;
                              }

                              if (!playedHorn) {
                                 world.func_72956_a(bounder, "lotr:item.horn", 2.0F, 2.0F);
                                 playedHorn = true;
                              }
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      BiomeGenBase biome;
      if (!world.field_72995_K && entity.func_70089_S() && inWater && entity.field_70154_o == null && entity.field_70173_aa % 10 == 0) {
         flag = true;
         if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
            flag = false;
         }

         if (entity instanceof LOTREntityMirkwoodSpider) {
            flag = false;
         }

         if (flag) {
            i = MathHelper.func_76128_c(entity.field_70165_t);
            j = MathHelper.func_76128_c(entity.field_70161_v);
            biome = world.func_72807_a(i, j);
            if (biome instanceof LOTRBiomeGenMirkwoodCorrupted) {
               entity.func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, 600, 1));
               entity.func_70690_d(new PotionEffect(Potion.field_76419_f.field_76415_H, 600, 1));
               entity.func_70690_d(new PotionEffect(Potion.field_76437_t.field_76415_H, 600));
               entity.func_70690_d(new PotionEffect(Potion.field_76440_q.field_76415_H, 600));
            }
         }
      }

      if (!world.field_72995_K && entity.func_70089_S() && inWater && entity.field_70154_o == null && entity.field_70173_aa % 10 == 0) {
         flag = true;
         if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (entityplayer.field_71075_bZ.field_75098_d) {
               flag = false;
            } else {
               alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR);
               level = 100.0F;
               if (alignment > level) {
                  flag = false;
               } else {
                  k = Math.round(level);
                  k = Math.max(k, 1);
                  if ((float)world.field_73012_v.nextInt(k) < alignment) {
                     flag = false;
                  }
               }
            }
         }

         if (LOTRMod.getNPCFaction(entity).isGoodRelation(LOTRFaction.MORDOR)) {
            flag = false;
         }

         if (flag) {
            i = MathHelper.func_76128_c(entity.field_70165_t);
            j = MathHelper.func_76128_c(entity.field_70161_v);
            biome = world.func_72807_a(i, j);
            if (biome instanceof LOTRBiomeGenMorgulVale) {
               entity.func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, 600, 1));
               entity.func_70690_d(new PotionEffect(Potion.field_76419_f.field_76415_H, 600, 1));
               entity.func_70690_d(new PotionEffect(Potion.field_76437_t.field_76415_H, 600));
               entity.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 100));
            }
         }
      }

      if (!world.field_72995_K && entity.func_70089_S() && entity.field_70173_aa % 10 == 0) {
         flag = true;

         for(i = 0; i < 4; ++i) {
            ItemStack armour = entity.func_71124_b(i + 1);
            if (armour == null || !(armour.func_77973_b() instanceof ItemArmor) || ((ItemArmor)armour.func_77973_b()).func_82812_d() != LOTRMaterial.WOOD_ELVEN_SCOUT.toArmorMaterial()) {
               flag = false;
               break;
            }
         }

         IAttributeInstance speedAttribute = entity.func_110148_a(SharedMonsterAttributes.field_111263_d);
         if (speedAttribute.func_111127_a(LOTREntityWoodElfScout.scoutArmorSpeedBoost.func_111167_a()) != null) {
            speedAttribute.func_111124_b(LOTREntityWoodElfScout.scoutArmorSpeedBoost);
         }

         if (flag) {
            speedAttribute.func_111121_a(LOTREntityWoodElfScout.scoutArmorSpeedBoost);
         }
      }

      if (!world.field_72995_K && entity.func_70089_S()) {
         ItemStack weapon = entity.func_70694_bm();
         boolean lanceOnFoot = false;
         if (weapon != null && weapon.func_77973_b() instanceof LOTRItemLance && entity.field_70154_o == null) {
            lanceOnFoot = true;
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
               lanceOnFoot = false;
            }
         }

         IAttributeInstance speedAttribute = entity.func_110148_a(SharedMonsterAttributes.field_111263_d);
         if (speedAttribute.func_111127_a(LOTRItemLance.lanceSpeedBoost_id) != null) {
            speedAttribute.func_111124_b(LOTRItemLance.lanceSpeedBoost);
         }

         if (lanceOnFoot) {
            speedAttribute.func_111121_a(LOTRItemLance.lanceSpeedBoost);
         }
      }

      byte burnChance;
      int burnChance;
      BiomeGenBase biome;
      int burnProtection;
      int l;
      ItemStack armour;
      ArmorMaterial material;
      if (!world.field_72995_K && entity.func_70089_S() && entity.field_70173_aa % 20 == 0) {
         flag = true;
         if (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).isImmuneToFrost) {
            flag = false;
         }

         if (entity instanceof EntityPlayer) {
            flag = !((EntityPlayer)entity).field_71075_bZ.field_75098_d;
         }

         if (flag) {
            i = MathHelper.func_76128_c(entity.field_70165_t);
            j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
            k = MathHelper.func_76128_c(entity.field_70161_v);
            biome = world.func_72807_a(i, k);
            if (biome instanceof LOTRBiomeGenForodwaith && (world.func_72937_j(i, j, k) || inWater) && world.func_72972_b(EnumSkyBlock.Block, i, j, k) < 10) {
               burnChance = 50;
               burnProtection = 0;

               for(l = 0; l < 4; ++l) {
                  armour = entity.func_71124_b(l + 1);
                  if (armour != null && armour.func_77973_b() instanceof ItemArmor) {
                     material = ((ItemArmor)armour.func_77973_b()).func_82812_d();
                     Item materialItem = material.func_151685_b();
                     if (materialItem == Items.field_151116_aA) {
                        burnProtection += 50;
                     } else if (materialItem == LOTRMod.fur) {
                        burnProtection += 100;
                     }
                  }
               }

               burnChance = burnChance + burnProtection;
               if (world.func_72896_J()) {
                  burnChance /= 3;
               }

               if (inWater) {
                  burnChance /= 20;
               }

               burnChance = Math.max(burnChance, 1);
               if (world.field_73012_v.nextInt(burnChance) == 0) {
                  entity.func_70097_a(LOTRDamage.frost, 1.0F);
               }
            }
         }
      }

      if (!world.field_72995_K && entity.func_70089_S() && entity.field_70173_aa % 20 == 0) {
         flag = true;
         if (entity instanceof LOTREntityNPC) {
            flag = true;
         }

         if (entity instanceof EntityPlayer) {
            flag = !((EntityPlayer)entity).field_71075_bZ.field_75098_d;
         }

         if (entity instanceof LOTRBiomeGenNearHarad.ImmuneToHeat) {
            flag = false;
         }

         if (flag) {
            i = MathHelper.func_76128_c(entity.field_70165_t);
            j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
            k = MathHelper.func_76128_c(entity.field_70161_v);
            biome = world.func_72807_a(i, k);
            if (biome instanceof LOTRBiomeGenNearHarad && !inWater && world.func_72937_j(i, j, k) && world.func_72935_r()) {
               burnChance = 50;
               burnProtection = 0;

               for(l = 0; l < 4; ++l) {
                  armour = entity.func_71124_b(l + 1);
                  if (armour != null && armour.func_77973_b() instanceof ItemArmor) {
                     material = ((ItemArmor)armour.func_77973_b()).func_82812_d();
                     if (material.customCraftingMaterial == Items.field_151116_aA) {
                        burnProtection += 50;
                     }

                     if (material == LOTRMaterial.HARAD_ROBES.toArmorMaterial()) {
                        burnProtection += 400;
                     }

                     if (material == LOTRMaterial.HARAD_NOMAD.toArmorMaterial()) {
                        burnProtection += 200;
                     }
                  }
               }

               burnChance = burnChance + burnProtection;
               burnChance = Math.max(burnChance, 1);
               if (world.field_73012_v.nextInt(burnChance) == 0) {
                  attacked = entity.func_70097_a(DamageSource.field_76370_b, 1.0F);
                  if (attacked && entity instanceof EntityPlayerMP) {
                     LOTRDamage.doBurnDamage((EntityPlayerMP)entity);
                  }
               }
            }
         }
      }

      if (world.field_72995_K) {
         LOTRPlateFallingInfo.getOrCreateFor(entity, true).update();
      }

   }

   @SubscribeEvent
   public void onMinecartUpdate(MinecartUpdateEvent event) {
      EntityMinecart minecart = event.minecart;
   }

   @SubscribeEvent
   public void onMinecartInteract(MinecartInteractEvent event) {
      EntityPlayer entityplayer = event.player;
      World world = entityplayer.field_70170_p;
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      EntityMinecart minecart = event.minecart;
      if (minecart instanceof EntityMinecartChest && itemstack != null && itemstack.func_77973_b() instanceof LOTRItemPouch) {
         if (!world.field_72995_K) {
            int pouchSlot = entityplayer.field_71071_by.field_70461_c;
            entityplayer.openGui(LOTRMod.instance, LOTRCommonProxy.packGuiIDWithSlot(64, pouchSlot), world, minecart.func_145782_y(), 0, 0);
         }

         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onEntityInteract(EntityInteractEvent event) {
      EntityPlayer entityplayer = event.entityPlayer;
      World world = entityplayer.field_70170_p;
      ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
      Entity entity = event.target;
      if (!world.field_72995_K && (entity instanceof EntityHanging || entity instanceof LOTRBannerProtectable) && LOTRBannerProtection.isProtected(world, entity, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
         event.setCanceled(true);
      } else if ((entity instanceof EntityCow || entity instanceof LOTREntityZebra) && itemstack != null && LOTRItemMug.isItemEmptyDrink(itemstack)) {
         LOTRItemMug.Vessel vessel = LOTRItemMug.getVessel(itemstack);
         ItemStack milkItem = new ItemStack(LOTRMod.mugMilk);
         LOTRItemMug.setVessel(milkItem, vessel, true);
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            --itemstack.field_77994_a;
         }

         if (itemstack.field_77994_a > 0 && !entityplayer.field_71075_bZ.field_75098_d) {
            if (!entityplayer.field_71071_by.func_70441_a(milkItem)) {
               entityplayer.func_71019_a(milkItem, false);
            }
         } else {
            entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, milkItem);
         }

         event.setCanceled(true);
      } else {
         if (entity instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf)entity;
            if (itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone") && itemstack.func_77973_b() != Items.field_151103_aS) {
               Item prevItem = itemstack.func_77973_b();
               itemstack.func_150996_a(Items.field_151103_aS);
               boolean flag = wolf.func_70085_c(entityplayer);
               itemstack.func_150996_a(prevItem);
               if (flag) {
                  event.setCanceled(true);
                  return;
               }
            }

            if (itemstack != null) {
               int dyeType = LOTRItemDye.isItemDye(itemstack);
               if (dyeType >= 0 && itemstack.func_77973_b() != Items.field_151100_aR) {
                  Item prevItem = itemstack.func_77973_b();
                  int prevMeta = itemstack.func_77960_j();
                  itemstack.func_150996_a(Items.field_151100_aR);
                  itemstack.func_77964_b(dyeType);
                  boolean flag = wolf.func_70085_c(entityplayer);
                  itemstack.func_150996_a(prevItem);
                  itemstack.func_77964_b(prevMeta);
                  if (flag) {
                     event.setCanceled(true);
                     return;
                  }
               }
            }
         }

         if (entity instanceof LOTRTradeable && ((LOTRTradeable)entity).canTradeWith(entityplayer)) {
            if (entity instanceof LOTRUnitTradeable) {
               entityplayer.openGui(LOTRMod.instance, 24, world, entity.func_145782_y(), 0, 0);
            } else {
               entityplayer.openGui(LOTRMod.instance, 19, world, entity.func_145782_y(), 0, 0);
            }

            event.setCanceled(true);
         } else if (entity instanceof LOTRUnitTradeable && ((LOTRUnitTradeable)entity).canTradeWith(entityplayer)) {
            entityplayer.openGui(LOTRMod.instance, 20, world, entity.func_145782_y(), 0, 0);
            event.setCanceled(true);
         } else if (entity instanceof LOTRMercenary && ((LOTRMercenary)entity).canTradeWith(entityplayer) && ((LOTREntityNPC)entity).hiredNPCInfo.getHiringPlayerUUID() == null) {
            entityplayer.openGui(LOTRMod.instance, 58, world, entity.func_145782_y(), 0, 0);
            event.setCanceled(true);
         } else {
            if (entity instanceof LOTREntityNPC) {
               LOTREntityNPC npc = (LOTREntityNPC)entity;
               if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                  entityplayer.openGui(LOTRMod.instance, 21, world, entity.func_145782_y(), 0, 0);
                  event.setCanceled(true);
                  return;
               }

               if (npc.hiredNPCInfo.isActive && entityplayer.field_71075_bZ.field_75098_d && itemstack != null && itemstack.func_77973_b() == Items.field_151113_aN) {
                  if (!world.field_72995_K && MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(entityplayer.func_146103_bH())) {
                     UUID hiringUUID = npc.hiredNPCInfo.getHiringPlayerUUID();
                     if (hiringUUID != null) {
                        String playerName = getUsernameWithoutWebservice(hiringUUID);
                        if (playerName != null) {
                           IChatComponent msg = new ChatComponentText("Hired unit belongs to " + playerName);
                           msg.func_150256_b().func_150238_a(EnumChatFormatting.GREEN);
                           entityplayer.func_145747_a(msg);
                        }
                     }
                  }

                  event.setCanceled(true);
                  return;
               }
            }

            if (!world.field_72995_K && entityplayer.field_71075_bZ.field_75098_d && MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(entityplayer.func_146103_bH()) && itemstack != null && itemstack.func_77973_b() == Items.field_151113_aN && entity instanceof EntityLiving) {
               UUID brandingPlayer = LOTRItemBrandingIron.getBrandingPlayer(entity);
               if (brandingPlayer != null) {
                  String playerName = getUsernameWithoutWebservice(brandingPlayer);
                  if (playerName != null) {
                     IChatComponent msg = new ChatComponentText("Entity was branded by " + playerName);
                     msg.func_150256_b().func_150238_a(EnumChatFormatting.GREEN);
                     entityplayer.func_145747_a(msg);
                     event.setCanceled(true);
                     return;
                  }
               }
            }

            if (entity instanceof EntityVillager && !LOTRConfig.enableVillagerTrading) {
               event.setCanceled(true);
            }
         }
      }
   }

   public static String getUsernameWithoutWebservice(UUID player) {
      GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152652_a(player);
      if (profile != null && !StringUtils.isBlank(profile.getName())) {
         return profile.getName();
      } else {
         String cachedName = UsernameCache.getLastKnownUsername(player);
         return cachedName != null && !StringUtils.isBlank(cachedName) ? cachedName : player.toString();
      }
   }

   @SubscribeEvent
   public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
      boolean sneaking = false;
      if (event.target instanceof LOTREntityRanger && ((LOTREntityRanger)event.target).isRangerSneaking()) {
         sneaking = true;
      }

      if (event.target instanceof LOTREntityGaladhrimWarden && ((LOTREntityGaladhrimWarden)event.target).isElfSneaking()) {
         sneaking = true;
      }

      if (event.target instanceof LOTREntityHuornBase && !((LOTREntityHuornBase)event.target).isHuornActive()) {
         sneaking = true;
      }

      if (event.target instanceof EntityPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)event.target;
         boolean cloak = LOTRLevelData.getData((EntityPlayer)event.target).isPlayerWearingFull(entityplayer) == LOTRMaterial.HITHLAIN;
         if (cloak && entityplayer.func_110144_aD() != event.entityLiving && entityplayer.func_70068_e(event.entityLiving) >= 64.0D) {
            sneaking = true;
         }
      }

      if (event.entityLiving instanceof EntityLiving && sneaking) {
         ((EntityLiving)event.entityLiving).func_70624_b((EntityLivingBase)null);
      }

   }

   @SubscribeEvent
   public void onEntityAttackedByPlayer(AttackEntityEvent event) {
      Entity entity = event.target;
      World world = entity.field_70170_p;
      EntityPlayer entityplayer = event.entityPlayer;
      if (!world.field_72995_K && (entity instanceof EntityHanging || entity instanceof LOTRBannerProtectable) && LOTRBannerProtection.isProtected(world, entity, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onLivingAttacked(LivingAttackEvent event) {
      EntityLivingBase entity = event.entityLiving;
      EntityLivingBase attacker = event.source.func_76346_g() instanceof EntityLivingBase ? (EntityLivingBase)event.source.func_76346_g() : null;
      World world = entity.field_70170_p;
      if (entity instanceof LOTRNPCMount && entity.field_70153_n != null && attacker == entity.field_70153_n) {
         this.cancelAttackEvent(event);
      }

      if (attacker instanceof EntityPlayer && !LOTRMod.canPlayerAttackEntity((EntityPlayer)attacker, entity, true)) {
         this.cancelAttackEvent(event);
      }

      if (attacker instanceof EntityCreature && !LOTRMod.canNPCAttackEntity((EntityCreature)attacker, entity, false)) {
         this.cancelAttackEvent(event);
      }

      if (event.source instanceof EntityDamageSourceIndirect) {
         Entity projectile = event.source.func_76364_f();
         if (projectile instanceof EntityArrow || projectile instanceof LOTREntityCrossbowBolt || projectile instanceof LOTREntityDart) {
            boolean wearingAllGalvorn = true;

            for(int i = 0; i < 4; ++i) {
               ItemStack armour = entity.func_71124_b(i + 1);
               if (armour == null || !(armour.func_77973_b() instanceof ItemArmor) || ((ItemArmor)armour.func_77973_b()).func_82812_d() != LOTRMaterial.GALVORN.toArmorMaterial()) {
                  wearingAllGalvorn = false;
                  break;
               }
            }

            if (wearingAllGalvorn) {
               if (!world.field_72995_K && entity instanceof EntityPlayer) {
                  ((EntityPlayer)entity).field_71071_by.func_70449_g(event.ammount);
               }

               this.cancelAttackEvent(event);
            }
         }

         if (!world.field_72995_K && entity instanceof EntityPlayer && attacker instanceof LOTREntityOrc && projectile instanceof LOTREntitySpear) {
            ItemStack chestplate = entity.func_71124_b(3);
            if (chestplate != null && chestplate.func_77973_b() == LOTRMod.bodyMithril) {
               LOTRLevelData.getData((EntityPlayer)entity).addAchievement(LOTRAchievement.hitByOrcSpear);
            }
         }
      }

   }

   private void cancelAttackEvent(LivingAttackEvent event) {
      event.setCanceled(true);
      DamageSource source = event.source;
      if (source instanceof EntityDamageSourceIndirect) {
         Entity var3 = source.func_76364_f();
      }

   }

   @SubscribeEvent
   public void onLivingHurt(LivingHurtEvent event) {
      EntityLivingBase entity = event.entityLiving;
      EntityLivingBase attacker = event.source.func_76346_g() instanceof EntityLivingBase ? (EntityLivingBase)event.source.func_76346_g() : null;
      World world = entity.field_70170_p;
      if (entity instanceof EntityPlayerMP && event.source == LOTRDamage.frost) {
         LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
      }

      int damage;
      ItemStack armour;
      if (!world.field_72995_K) {
         int defaultMaxHurtResTime = true;
         int preMaxHurtResTime = entity.field_70771_an;
         damage = 20;
         if (attacker != null) {
            armour = attacker.func_70694_bm();
            if (LOTRWeaponStats.isMeleeWeapon(armour)) {
               damage = LOTRWeaponStats.getAttackTimeWithBase(armour, 20);
            }
         }

         damage = Math.min(damage, 20);
         entity.field_70771_an = damage;
         if (entity.field_70172_ad == preMaxHurtResTime) {
            entity.field_70172_ad = damage;
         }
      }

      if (attacker != null && event.source.func_76364_f() == attacker) {
         ItemStack weapon = attacker.func_70694_bm();
         if (!world.field_72995_K && entity instanceof EntityPlayerMP) {
            EntityPlayerMP entityplayer = (EntityPlayerMP)entity;
            if (entityplayer.func_71039_bw()) {
               ItemStack usingItem = entityplayer.func_70694_bm();
               if (usingItem != null && LOTRWeaponStats.isRangedWeapon(usingItem)) {
                  entityplayer.func_71041_bz();
                  LOTRPacketStopItemUse packet = new LOTRPacketStopItemUse();
                  LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
               }
            }
         }

         boolean wearingAllMorgul = true;

         for(damage = 0; damage < 4; ++damage) {
            armour = entity.func_71124_b(damage + 1);
            if (armour == null || !(armour.func_77973_b() instanceof ItemArmor) || ((ItemArmor)armour.func_77973_b()).func_82812_d() != LOTRMaterial.MORGUL.toArmorMaterial()) {
               wearingAllMorgul = false;
               break;
            }
         }

         if (wearingAllMorgul && !world.field_72995_K && weapon != null && weapon.func_77984_f()) {
            damage = weapon.func_77960_j();
            int maxDamage = weapon.func_77958_k();
            float durability = 1.0F - (float)damage / (float)maxDamage;
            durability *= 0.9F;
            int newDamage = Math.round((1.0F - durability) * (float)maxDamage);
            newDamage = Math.min(newDamage, maxDamage);
            weapon.func_77972_a(newDamage - damage, attacker);
         }

         if (weapon != null) {
            ToolMaterial material = null;
            if (weapon.func_77973_b() instanceof ItemTool) {
               material = ((ItemTool)weapon.func_77973_b()).func_150913_i();
            } else if (weapon.func_77973_b() instanceof ItemSword) {
               material = LOTRMaterial.getToolMaterialByName(((ItemSword)weapon.func_77973_b()).func_150932_j());
            }

            if (material != null && material == LOTRMaterial.MORGUL.toToolMaterial() && !world.field_72995_K) {
               entity.func_70690_d(new PotionEffect(Potion.field_82731_v.field_76415_H, 160));
            }
         }
      }

      if (event.source.func_76364_f() instanceof LOTREntityArrowPoisoned && !world.field_72995_K) {
         LOTRItemDagger.applyStandardPoison(entity);
      }

      if (!world.field_72995_K) {
         if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(event.source, LOTREnchantment.fire)) {
            LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.INFERNAL, entity);
            LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(entity, 64.0D));
         }

         if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(event.source, LOTREnchantment.chill)) {
            LOTREnchantmentWeaponSpecial.doChillAttack(entity);
         }
      }

   }

   @SubscribeEvent
   public void onLivingDeath(LivingDeathEvent event) {
      EntityLivingBase entity = event.entityLiving;
      World world = entity.field_70170_p;
      DamageSource source = event.source;
      EntityPlayer entityplayer;
      int i;
      int j;
      int k;
      if (!world.field_72995_K && entity instanceof EntityPlayer) {
         entityplayer = (EntityPlayer)entity;
         i = MathHelper.func_76128_c(entityplayer.field_70165_t);
         j = MathHelper.func_76128_c(entityplayer.field_70163_u);
         k = MathHelper.func_76128_c(entityplayer.field_70161_v);
         LOTRLevelData.getData(entityplayer).setDeathPoint(i, j, k);
         LOTRLevelData.getData(entityplayer).setDeathDimension(entityplayer.field_71093_bK);
      }

      boolean sentMessage;
      int sentSpeeches;
      if (!world.field_72995_K) {
         entityplayer = null;
         boolean creditHiredUnit = false;
         sentMessage = false;
         if (source.func_76346_g() instanceof EntityPlayer) {
            entityplayer = (EntityPlayer)source.func_76346_g();
         } else if (entity.func_94060_bK() instanceof EntityPlayer) {
            entityplayer = (EntityPlayer)entity.func_94060_bK();
         } else if (source.func_76346_g() instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC)source.func_76346_g();
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
               EntityPlayer hirer = npc.hiredNPCInfo.getHiringPlayer();
               entityplayer = hirer;
               creditHiredUnit = true;
               double nearbyDist = 64.0D;
               sentMessage = npc.func_70068_e(hirer) <= nearbyDist * nearbyDist;
            }
         }

         if (entityplayer != null) {
            LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
            final LOTRFaction entityFaction = LOTRMod.getNPCFaction(entity);
            float prevAlignment = playerData.getAlignment(entityFaction);
            List forcedBonusFactions = null;
            if (entity instanceof LOTREntityNPC) {
               forcedBonusFactions = ((LOTREntityNPC)entity).killBonusFactions;
            }

            boolean wasSelfDefenceAgainstAlliedUnit = false;
            if (!creditHiredUnit && prevAlignment > 0.0F && entity instanceof LOTREntityNPC) {
               LOTREntityNPC npc = (LOTREntityNPC)entity;
               if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.wasAttackCommanded) {
                  wasSelfDefenceAgainstAlliedUnit = true;
               }
            }

            LOTRAlignmentValues.AlignmentBonus alignmentBonus = null;
            if (!wasSelfDefenceAgainstAlliedUnit) {
               if (entity instanceof LOTREntityNPC) {
                  LOTREntityNPC npc = (LOTREntityNPC)entity;
                  alignmentBonus = new LOTRAlignmentValues.AlignmentBonus(npc.getAlignmentBonus(), npc.getEntityClassName());
                  alignmentBonus.needsTranslation = true;
                  alignmentBonus.isCivilianKill = npc.isCivilianNPC();
               } else {
                  String s = EntityList.func_75621_b(entity);
                  Object obj = LOTREntityRegistry.registeredNPCs.get(s);
                  if (obj != null) {
                     LOTREntityRegistry.RegistryInfo info = (LOTREntityRegistry.RegistryInfo)obj;
                     alignmentBonus = info.alignmentBonus;
                     alignmentBonus.isCivilianKill = false;
                  }
               }
            }

            if (!creditHiredUnit && wasSelfDefenceAgainstAlliedUnit) {
            }

            if (alignmentBonus != null && alignmentBonus.bonus != 0.0F && (!creditHiredUnit || creditHiredUnit && sentMessage)) {
               alignmentBonus.isKill = true;
               if (creditHiredUnit) {
                  alignmentBonus.killByHiredUnit = true;
               }

               playerData.addAlignment(entityplayer, alignmentBonus, entityFaction, forcedBonusFactions, entity);
            }

            if (!creditHiredUnit) {
               if (entityFaction.allowPlayer) {
                  playerData.getFactionData(entityFaction).addNPCKill();
                  List killBonuses = entityFaction.getBonusesForKilling();
                  Iterator var50 = killBonuses.iterator();

                  while(var50.hasNext()) {
                     LOTRFaction enemy = (LOTRFaction)var50.next();
                     playerData.getFactionData(enemy).addEnemyKill();
                  }

                  if (!entityplayer.field_71075_bZ.field_75098_d) {
                     boolean recordBountyKill = entityFaction.inDefinedControlZone(entityplayer, Math.max(entityFaction.getControlZoneReducedRange(), 50));
                     if (recordBountyKill) {
                        LOTRFactionBounties.forFaction(entityFaction).forPlayer(entityplayer).recordNewKill();
                     }
                  }

                  LOTRFaction pledgeFac = playerData.getPledgeFaction();
                  if (pledgeFac != null && (pledgeFac == entityFaction || pledgeFac.isAlly(entityFaction))) {
                     playerData.onPledgeKill(entityplayer);
                  }
               }

               float newAlignment = playerData.getAlignment(entityFaction);
               if (!wasSelfDefenceAgainstAlliedUnit && !entityplayer.field_71075_bZ.field_75098_d && entityFaction != LOTRFaction.UNALIGNED) {
                  sentSpeeches = 0;
                  int maxSpeeches = 5;
                  double range = 8.0D;
                  List nearbyAlliedNPCs = world.func_82733_a(EntityLiving.class, entity.field_70121_D.func_72314_b(range, range, range), new IEntitySelector() {
                     public boolean func_82704_a(Entity entitySelect) {
                        if (entitySelect.func_70089_S()) {
                           LOTRFaction fac = LOTRMod.getNPCFaction(entitySelect);
                           return fac.isGoodRelation(entityFaction);
                        } else {
                           return false;
                        }
                     }
                  });

                  for(int i = 0; i < nearbyAlliedNPCs.size(); ++i) {
                     EntityLiving npc = (EntityLiving)nearbyAlliedNPCs.get(i);
                     LOTREntityNPC lotrnpc;
                     if (npc instanceof LOTREntityNPC) {
                        lotrnpc = (LOTREntityNPC)npc;
                        if (lotrnpc.hiredNPCInfo.isActive && newAlignment > 0.0F || lotrnpc.hiredNPCInfo.isActive && lotrnpc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                           continue;
                        }
                     }

                     if (npc.func_70638_az() == null) {
                        npc.func_70624_b(entityplayer);
                        if (npc instanceof LOTREntityNPC && sentSpeeches < maxSpeeches) {
                           lotrnpc = (LOTREntityNPC)npc;
                           String speech = lotrnpc.getSpeechBank(entityplayer);
                           if (speech != null && lotrnpc.func_70068_e(entityplayer) < range) {
                              lotrnpc.sendSpeechBank(entityplayer, speech);
                              ++sentSpeeches;
                           }
                        }
                     }
                  }
               }

               if (!playerData.isSiegeActive()) {
                  List miniquests = playerData.getMiniQuests();
                  Iterator var58 = miniquests.iterator();

                  while(var58.hasNext()) {
                     LOTRMiniQuest quest = (LOTRMiniQuest)var58.next();
                     quest.onKill(entityplayer, entity);
                  }

                  if (entity instanceof EntityPlayer) {
                     EntityPlayer slainPlayer = (EntityPlayer)entity;
                     List slainMiniquests = LOTRLevelData.getData(slainPlayer).getMiniQuests();
                     Iterator var18 = slainMiniquests.iterator();

                     while(var18.hasNext()) {
                        LOTRMiniQuest quest = (LOTRMiniQuest)var18.next();
                        quest.onKilledByPlayer(slainPlayer, entityplayer);
                     }
                  }
               }
            }
         }
      }

      if (!world.field_72995_K && source.func_76346_g() instanceof EntityPlayer) {
         entityplayer = (EntityPlayer)source.func_76346_g();
         LOTREnchantmentHelper.onKillEntity(entityplayer, entity, source);
      }

      if (!world.field_72995_K && source.func_76346_g() instanceof EntityPlayer && source.func_76364_f() != null && source.func_76364_f().getClass() == LOTREntitySpear.class) {
         entityplayer = (EntityPlayer)source.func_76346_g();
         if (entity != entityplayer && entityplayer.func_70068_e(entity) >= 2500.0D) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useSpearFromFar);
         }
      }

      if (!world.field_72995_K && entity instanceof LOTREntityButterfly && source.func_76346_g() instanceof EntityPlayer) {
         entityplayer = (EntityPlayer)source.func_76346_g();
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killButterfly);
      }

      int range;
      if (!world.field_72995_K && entity.getClass() == LOTREntityHorse.class && source.func_76346_g() instanceof EntityPlayer) {
         entityplayer = (EntityPlayer)source.func_76346_g();
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            List rohirrimList = world.func_72872_a(LOTREntityRohanMan.class, entityplayer.field_70121_D.func_72314_b(16.0D, 16.0D, 16.0D));
            if (!rohirrimList.isEmpty()) {
               sentMessage = false;
               boolean penalty = false;

               for(range = 0; range < rohirrimList.size(); ++range) {
                  LOTREntityRohanMan rohirrim = (LOTREntityRohanMan)rohirrimList.get(range);
                  if (!rohirrim.hiredNPCInfo.isActive || rohirrim.hiredNPCInfo.getHiringPlayer() != entityplayer) {
                     rohirrim.func_70624_b(entityplayer);
                     if (!sentMessage) {
                        rohirrim.sendSpeechBank(entityplayer, "rohan/warrior/avengeHorse");
                        sentMessage = true;
                     }

                     if (!penalty) {
                        LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.ROHAN_HORSE_PENALTY, LOTRFaction.ROHAN, entity);
                        penalty = true;
                     }
                  }
               }
            }
         }
      }

      if (!world.field_72995_K) {
         entityplayer = null;
         LOTREntityNPC attackingHiredUnit = null;
         if (source.func_76346_g() instanceof EntityPlayer) {
            entityplayer = (EntityPlayer)source.func_76346_g();
         } else if (source.func_76346_g() instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC)source.func_76346_g();
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
               entityplayer = npc.hiredNPCInfo.getHiringPlayer();
               attackingHiredUnit = npc;
            }
         }

         if (entityplayer != null) {
            sentMessage = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction(entity)) < 0.0F;
            if (sentMessage) {
               if (attackingHiredUnit != null) {
                  if (attackingHiredUnit instanceof LOTREntityWargBombardier) {
                     LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireWargBombardier);
                  }

                  if (attackingHiredUnit instanceof LOTREntityOlogHai) {
                     LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireOlogHai);
                  }
               } else {
                  if (entityplayer.func_82165_m(Potion.field_76431_k.field_76415_H)) {
                     LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killWhileDrunk);
                  }

                  if (entity instanceof LOTREntityOrc) {
                     LOTREntityOrc orc = (LOTREntityOrc)entity;
                     if (orc.isOrcBombardier() && orc.npcItemsInv.getBomb() != null) {
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killBombardier);
                     }
                  }

                  if (source.func_76364_f() instanceof LOTREntityCrossbowBolt) {
                     LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useCrossbow);
                  }

                  if (source.func_76364_f() instanceof LOTREntityThrowingAxe) {
                     LOTREntityThrowingAxe axe = (LOTREntityThrowingAxe)source.func_76364_f();
                     if (axe.getProjectileItem().func_77973_b() == LOTRMod.throwingAxeDwarven) {
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useDwarvenThrowingAxe);
                     }
                  }
               }
            }
         }
      }

      if (!world.field_72995_K && LOTRMod.getNPCFaction(entity) == LOTRFaction.UTUMNO && LOTRDimension.getCurrentDimensionWithFallback(world) == LOTRDimension.UTUMNO) {
         Entity attacker = source.func_76346_g();
         if (attacker instanceof EntityPlayer) {
            i = MathHelper.func_76128_c(entity.field_70165_t);
            j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
            k = MathHelper.func_76128_c(entity.field_70161_v);
            range = LOTRBlockUtumnoReturnPortalBase.RANGE;

            for(int i1 = -range; i1 <= range; ++i1) {
               for(int j1 = -range; j1 <= range; ++j1) {
                  for(int k1 = -range; k1 <= range; ++k1) {
                     int i2 = i + i1;
                     int j2 = j + j1;
                     sentSpeeches = k + k1;
                     if (world.func_147439_a(i2, j2, sentSpeeches) == LOTRMod.utumnoReturnPortalBase) {
                        int meta = world.func_72805_g(i2, j2, sentSpeeches);
                        ++meta;
                        if (meta >= LOTRBlockUtumnoReturnPortalBase.MAX_SACRIFICE) {
                           world.func_72876_a(attacker, (double)i2 + 0.5D, (double)j2 + 0.5D, (double)sentSpeeches + 0.5D, 0.0F, false);
                           world.func_147465_d(i2, j2, sentSpeeches, LOTRMod.utumnoReturnPortal, 0, 3);
                        } else {
                           world.func_72921_c(i2, j2, sentSpeeches, meta, 3);
                        }

                        LOTRPacketUtumnoKill packet = new LOTRPacketUtumnoKill(entity.func_145782_y(), i2, j2, sentSpeeches);
                        LOTRPacketHandler.networkWrapper.sendToAllAround(packet, new TargetPoint(entity.field_71093_bK, (double)i2 + 0.5D, (double)j2 + 0.5D, (double)sentSpeeches + 0.5D, 32.0D));
                     }
                  }
               }
            }
         }
      }

      if (!world.field_72995_K && entity instanceof EntityPlayer) {
         entityplayer = (EntityPlayer)entity;
         if (LOTREnchantmentHelper.hasMeleeOrRangedEnchant(source, LOTREnchantment.headhunting)) {
            ItemStack playerHead = new ItemStack(Items.field_151144_bL, 1, 3);
            GameProfile profile = entityplayer.func_146103_bH();
            NBTTagCompound profileData = new NBTTagCompound();
            NBTUtil.func_152460_a(profileData, profile);
            playerHead.func_77983_a("SkullOwner", profileData);
            entityplayer.func_70099_a(playerHead, 0.0F);
         }
      }

   }

   @SubscribeEvent
   public void onLivingDrops(LivingDropsEvent event) {
      EntityLivingBase entity = event.entityLiving;
      Random rand = entity.func_70681_au();
      List drops = event.drops;
      int i = event.lootingLevel;
      boolean flag = event.recentlyHit;
      if (entity instanceof EntitySheep && LOTRConfig.dropMutton) {
         int meat = rand.nextInt(3) + rand.nextInt(1 + i);

         for(int l = 0; l < meat; ++l) {
            if (entity.func_70027_ad()) {
               entity.func_145779_a(LOTRMod.muttonCooked, 1);
            } else {
               entity.func_145779_a(LOTRMod.muttonRaw, 1);
            }
         }
      }

   }

   @SubscribeEvent
   public void onExplosionDetonate(Detonate event) {
      Explosion expl = event.explosion;
      World world = event.world;
      Entity exploder = expl.field_77283_e;
      if (!world.field_72995_K && exploder != null) {
         LOTRBannerProtection.IFilter protectFilter = null;
         if (exploder instanceof LOTREntityNPC) {
            protectFilter = LOTRBannerProtection.anyBanner();
         } else if (exploder instanceof EntityMob) {
            protectFilter = LOTRBannerProtection.anyBanner();
         } else if (exploder instanceof EntityThrowable) {
            protectFilter = LOTRBannerProtection.forThrown((EntityThrowable)exploder);
         } else if (exploder instanceof EntityTNTPrimed) {
            protectFilter = LOTRBannerProtection.forTNT((EntityTNTPrimed)exploder);
         } else if (exploder instanceof EntityMinecartTNT) {
            protectFilter = LOTRBannerProtection.forTNTMinecart((EntityMinecartTNT)exploder);
         }

         if (protectFilter != null) {
            List blockList = expl.field_77281_g;
            List removes = new ArrayList();
            Iterator var8 = blockList.iterator();

            while(var8.hasNext()) {
               ChunkPosition blockPos = (ChunkPosition)var8.next();
               int i = blockPos.field_151329_a;
               int j = blockPos.field_151327_b;
               int k = blockPos.field_151328_c;
               if (LOTRBannerProtection.isProtected(world, i, j, k, protectFilter, false)) {
                  removes.add(blockPos);
               }
            }

            blockList.removeAll(removes);
         }
      }

   }

   @SubscribeEvent
   public void onServerChat(ServerChatEvent event) {
      EntityPlayerMP entityplayer = event.player;
      String message = event.message;
      String username = event.username;
      ChatComponentTranslation chatComponent = event.component;
      if (LOTRConfig.drunkMessages) {
         PotionEffect nausea = entityplayer.func_70660_b(Potion.field_76431_k);
         if (nausea != null) {
            int duration = nausea.func_76459_b();
            float chance = (float)duration / 4800.0F;
            chance = Math.min(chance, 1.0F);
            chance *= 0.4F;
            Random rand = entityplayer.func_70681_au();
            String key = chatComponent.func_150268_i();
            Object[] formatArgs = chatComponent.func_150271_j();

            for(int a = 0; a < formatArgs.length; ++a) {
               Object arg = formatArgs[a];
               String chatText = null;
               if (arg instanceof ChatComponentText) {
                  ChatComponentText componentText = (ChatComponentText)arg;
                  chatText = componentText.func_150260_c();
               } else if (arg instanceof String) {
                  chatText = (String)arg;
               }

               if (chatText != null && chatText.equals(message)) {
                  String newText = LOTRDrunkenSpeech.getDrunkenSpeech(chatText, chance);
                  if (arg instanceof String) {
                     formatArgs[a] = newText;
                  } else if (arg instanceof ChatComponentText) {
                     formatArgs[a] = new ChatComponentText(newText);
                  }
               }
            }

            ChatComponentTranslation newComponent = new ChatComponentTranslation(key, formatArgs);
            chatComponent = newComponent;
         }
      }

      if (LOTRConfig.enableTitles) {
         LOTRTitle.PlayerTitle playerTitle = LOTRLevelData.getData((EntityPlayer)entityplayer).getPlayerTitle();
         if (playerTitle != null) {
            List newFormatArgs = new ArrayList();
            Object[] var18 = chatComponent.func_150271_j();
            int var19 = var18.length;

            for(int var22 = 0; var22 < var19; ++var22) {
               Object arg = var18[var22];
               if (arg instanceof ChatComponentText) {
                  ChatComponentText componentText = (ChatComponentText)arg;
                  if (componentText.func_150260_c().contains(username)) {
                     IChatComponent titleComponent = playerTitle.getFullTitleComponent(entityplayer);
                     IChatComponent fullUsernameComponent = (new ChatComponentText("")).func_150257_a(titleComponent).func_150257_a(componentText);
                     newFormatArgs.add(fullUsernameComponent);
                  } else {
                     newFormatArgs.add(componentText);
                  }
               } else {
                  newFormatArgs.add(arg);
               }
            }

            ChatComponentTranslation newChatComponent = new ChatComponentTranslation(chatComponent.func_150268_i(), newFormatArgs.toArray());
            newChatComponent.func_150255_a(chatComponent.func_150256_b().func_150232_l());
            Iterator var21 = chatComponent.func_150253_a().iterator();

            while(var21.hasNext()) {
               Object sibling = var21.next();
               newChatComponent.func_150257_a((IChatComponent)sibling);
            }

            chatComponent = newChatComponent;
         }
      }

      event.component = chatComponent;
   }
}
