package lotr.client;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import lotr.client.fx.LOTREffectRenderer;
import lotr.client.fx.LOTREntityAlignmentBonus;
import lotr.client.fx.LOTREntityAngryFX;
import lotr.client.fx.LOTREntityBlueFlameFX;
import lotr.client.fx.LOTREntityBossSpawnFX;
import lotr.client.fx.LOTREntityChillFX;
import lotr.client.fx.LOTREntityDeadMarshFace;
import lotr.client.fx.LOTREntityElvenGlowFX;
import lotr.client.fx.LOTREntityGandalfFireballExplodeFX;
import lotr.client.fx.LOTREntityLargeBlockFX;
import lotr.client.fx.LOTREntityLeafFX;
import lotr.client.fx.LOTREntityMTCHealFX;
import lotr.client.fx.LOTREntityMallornEntHealFX;
import lotr.client.fx.LOTREntityMallornEntSummonFX;
import lotr.client.fx.LOTREntityMarshFlameFX;
import lotr.client.fx.LOTREntityMarshLightFX;
import lotr.client.fx.LOTREntityMorgulPortalFX;
import lotr.client.fx.LOTREntityMusicFX;
import lotr.client.fx.LOTREntityPickpocketFX;
import lotr.client.fx.LOTREntityPickpocketFailFX;
import lotr.client.fx.LOTREntityQuenditeSmokeFX;
import lotr.client.fx.LOTREntityRiverWaterFX;
import lotr.client.fx.LOTREntitySwordCommandMarker;
import lotr.client.fx.LOTREntityUtumnoKillFX;
import lotr.client.fx.LOTREntityWaveFX;
import lotr.client.fx.LOTREntityWhiteSmokeFX;
import lotr.client.gui.LOTRGuiAlignmentChoices;
import lotr.client.gui.LOTRGuiBanner;
import lotr.client.gui.LOTRGuiFactions;
import lotr.client.gui.LOTRGuiFastTravel;
import lotr.client.gui.LOTRGuiFellowships;
import lotr.client.gui.LOTRGuiHiredFarmer;
import lotr.client.gui.LOTRGuiHiredWarrior;
import lotr.client.gui.LOTRGuiMap;
import lotr.client.gui.LOTRGuiMessage;
import lotr.client.gui.LOTRGuiMiniquestOffer;
import lotr.client.model.LOTRArmorModels;
import lotr.client.render.LOTRRenderBlocks;
import lotr.client.render.LOTRRenderPlayer;
import lotr.client.render.entity.LOTRRenderAlignmentBonus;
import lotr.client.render.entity.LOTRRenderAngmarHillman;
import lotr.client.render.entity.LOTRRenderArrowPoisoned;
import lotr.client.render.entity.LOTRRenderAurochs;
import lotr.client.render.entity.LOTRRenderBalrog;
import lotr.client.render.entity.LOTRRenderBandit;
import lotr.client.render.entity.LOTRRenderBanner;
import lotr.client.render.entity.LOTRRenderBannerWall;
import lotr.client.render.entity.LOTRRenderBarrowWight;
import lotr.client.render.entity.LOTRRenderBear;
import lotr.client.render.entity.LOTRRenderBearRug;
import lotr.client.render.entity.LOTRRenderBird;
import lotr.client.render.entity.LOTRRenderBossTrophy;
import lotr.client.render.entity.LOTRRenderBreeMan;
import lotr.client.render.entity.LOTRRenderBreeRuffian;
import lotr.client.render.entity.LOTRRenderBreeTrader;
import lotr.client.render.entity.LOTRRenderButterfly;
import lotr.client.render.entity.LOTRRenderCamel;
import lotr.client.render.entity.LOTRRenderCrocodile;
import lotr.client.render.entity.LOTRRenderCrossbowBolt;
import lotr.client.render.entity.LOTRRenderDaleMan;
import lotr.client.render.entity.LOTRRenderDaleTrader;
import lotr.client.render.entity.LOTRRenderDart;
import lotr.client.render.entity.LOTRRenderDeadMarshFace;
import lotr.client.render.entity.LOTRRenderDeer;
import lotr.client.render.entity.LOTRRenderDikDik;
import lotr.client.render.entity.LOTRRenderDorwinionElfVintner;
import lotr.client.render.entity.LOTRRenderDorwinionMan;
import lotr.client.render.entity.LOTRRenderDunedain;
import lotr.client.render.entity.LOTRRenderDunedainTrader;
import lotr.client.render.entity.LOTRRenderDunlending;
import lotr.client.render.entity.LOTRRenderDunlendingBase;
import lotr.client.render.entity.LOTRRenderDwarf;
import lotr.client.render.entity.LOTRRenderDwarfCommander;
import lotr.client.render.entity.LOTRRenderDwarfSmith;
import lotr.client.render.entity.LOTRRenderEasterling;
import lotr.client.render.entity.LOTRRenderEasterlingTrader;
import lotr.client.render.entity.LOTRRenderElf;
import lotr.client.render.entity.LOTRRenderElk;
import lotr.client.render.entity.LOTRRenderElvenSmith;
import lotr.client.render.entity.LOTRRenderElvenTrader;
import lotr.client.render.entity.LOTRRenderEnt;
import lotr.client.render.entity.LOTRRenderEntityBarrel;
import lotr.client.render.entity.LOTRRenderFallingCoinPile;
import lotr.client.render.entity.LOTRRenderFallingFireJar;
import lotr.client.render.entity.LOTRRenderFish;
import lotr.client.render.entity.LOTRRenderFlamingo;
import lotr.client.render.entity.LOTRRenderGaladhrimWarden;
import lotr.client.render.entity.LOTRRenderGandalf;
import lotr.client.render.entity.LOTRRenderGandalfFireball;
import lotr.client.render.entity.LOTRRenderGemsbok;
import lotr.client.render.entity.LOTRRenderGiraffe;
import lotr.client.render.entity.LOTRRenderGiraffeRug;
import lotr.client.render.entity.LOTRRenderGollum;
import lotr.client.render.entity.LOTRRenderGondorMan;
import lotr.client.render.entity.LOTRRenderGondorRenegade;
import lotr.client.render.entity.LOTRRenderGondorTrader;
import lotr.client.render.entity.LOTRRenderHalfTroll;
import lotr.client.render.entity.LOTRRenderHalfTrollScavenger;
import lotr.client.render.entity.LOTRRenderHaradSlave;
import lotr.client.render.entity.LOTRRenderHaradrimTrader;
import lotr.client.render.entity.LOTRRenderHobbit;
import lotr.client.render.entity.LOTRRenderHobbitTrader;
import lotr.client.render.entity.LOTRRenderHorse;
import lotr.client.render.entity.LOTRRenderHuorn;
import lotr.client.render.entity.LOTRRenderInvasionSpawner;
import lotr.client.render.entity.LOTRRenderKineAraw;
import lotr.client.render.entity.LOTRRenderLion;
import lotr.client.render.entity.LOTRRenderLionRug;
import lotr.client.render.entity.LOTRRenderMallornEnt;
import lotr.client.render.entity.LOTRRenderMallornLeafBomb;
import lotr.client.render.entity.LOTRRenderMarshWraith;
import lotr.client.render.entity.LOTRRenderMidges;
import lotr.client.render.entity.LOTRRenderMirkTroll;
import lotr.client.render.entity.LOTRRenderMirkwoodSpider;
import lotr.client.render.entity.LOTRRenderMordorSpider;
import lotr.client.render.entity.LOTRRenderMoredain;
import lotr.client.render.entity.LOTRRenderMountainTroll;
import lotr.client.render.entity.LOTRRenderMountainTrollChieftain;
import lotr.client.render.entity.LOTRRenderNPCRespawner;
import lotr.client.render.entity.LOTRRenderNearHaradTrader;
import lotr.client.render.entity.LOTRRenderNearHaradrim;
import lotr.client.render.entity.LOTRRenderNearHaradrimWarlord;
import lotr.client.render.entity.LOTRRenderNurnSlave;
import lotr.client.render.entity.LOTRRenderOlogHai;
import lotr.client.render.entity.LOTRRenderOrc;
import lotr.client.render.entity.LOTRRenderOrcBomb;
import lotr.client.render.entity.LOTRRenderPlate;
import lotr.client.render.entity.LOTRRenderPortal;
import lotr.client.render.entity.LOTRRenderRabbit;
import lotr.client.render.entity.LOTRRenderRanger;
import lotr.client.render.entity.LOTRRenderRhino;
import lotr.client.render.entity.LOTRRenderRohanTrader;
import lotr.client.render.entity.LOTRRenderRohirrim;
import lotr.client.render.entity.LOTRRenderSaruman;
import lotr.client.render.entity.LOTRRenderSauron;
import lotr.client.render.entity.LOTRRenderScorpion;
import lotr.client.render.entity.LOTRRenderScrapTrader;
import lotr.client.render.entity.LOTRRenderShirePony;
import lotr.client.render.entity.LOTRRenderSkeleton;
import lotr.client.render.entity.LOTRRenderSmokeRing;
import lotr.client.render.entity.LOTRRenderSnowTroll;
import lotr.client.render.entity.LOTRRenderSpear;
import lotr.client.render.entity.LOTRRenderStoneTroll;
import lotr.client.render.entity.LOTRRenderSwan;
import lotr.client.render.entity.LOTRRenderSwanKnight;
import lotr.client.render.entity.LOTRRenderSwordCommandMarker;
import lotr.client.render.entity.LOTRRenderTauredain;
import lotr.client.render.entity.LOTRRenderTauredainShaman;
import lotr.client.render.entity.LOTRRenderTermite;
import lotr.client.render.entity.LOTRRenderThrowingAxe;
import lotr.client.render.entity.LOTRRenderThrownRock;
import lotr.client.render.entity.LOTRRenderTraderRespawn;
import lotr.client.render.entity.LOTRRenderTroll;
import lotr.client.render.entity.LOTRRenderUtumnoIceSpider;
import lotr.client.render.entity.LOTRRenderUtumnoTroll;
import lotr.client.render.entity.LOTRRenderWarg;
import lotr.client.render.entity.LOTRRenderWargskinRug;
import lotr.client.render.entity.LOTRRenderWhiteOryx;
import lotr.client.render.entity.LOTRRenderWickedDwarf;
import lotr.client.render.entity.LOTRRenderWildBoar;
import lotr.client.render.entity.LOTRRenderWraithBall;
import lotr.client.render.entity.LOTRRenderZebra;
import lotr.client.render.entity.LOTRSwingHandler;
import lotr.client.render.tileentity.LOTRRenderAnimalJar;
import lotr.client.render.tileentity.LOTRRenderArmorStand;
import lotr.client.render.tileentity.LOTRRenderBeacon;
import lotr.client.render.tileentity.LOTRRenderChest;
import lotr.client.render.tileentity.LOTRRenderCommandTable;
import lotr.client.render.tileentity.LOTRRenderDartTrap;
import lotr.client.render.tileentity.LOTRRenderDwarvenDoor;
import lotr.client.render.tileentity.LOTRRenderElvenPortal;
import lotr.client.render.tileentity.LOTRRenderEntJar;
import lotr.client.render.tileentity.LOTRRenderGuldurilGlow;
import lotr.client.render.tileentity.LOTRRenderKebabStand;
import lotr.client.render.tileentity.LOTRRenderMorgulPortal;
import lotr.client.render.tileentity.LOTRRenderMug;
import lotr.client.render.tileentity.LOTRRenderPlateFood;
import lotr.client.render.tileentity.LOTRRenderSignCarved;
import lotr.client.render.tileentity.LOTRRenderSignCarvedIthildin;
import lotr.client.render.tileentity.LOTRRenderSpawnerChest;
import lotr.client.render.tileentity.LOTRRenderTrollTotem;
import lotr.client.render.tileentity.LOTRRenderUnsmeltery;
import lotr.client.render.tileentity.LOTRRenderUtumnoPortal;
import lotr.client.render.tileentity.LOTRRenderUtumnoReturnPortal;
import lotr.client.render.tileentity.LOTRRenderWeaponRack;
import lotr.client.render.tileentity.LOTRTileEntityMobSpawnerRenderer;
import lotr.client.sound.LOTRMusic;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRCommonProxy;
import lotr.common.LOTRConfig;
import lotr.common.LOTRGuiMessageTypes;
import lotr.common.LOTRMod;
import lotr.common.LOTRTickHandlerServer;
import lotr.common.entity.LOTREntityFallingFireJar;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.LOTRInvasionStatus;
import lotr.common.entity.animal.LOTREntityAurochs;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.entity.animal.LOTREntityCrocodile;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.animal.LOTREntityDikDik;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.animal.LOTREntityFish;
import lotr.common.entity.animal.LOTREntityFlamingo;
import lotr.common.entity.animal.LOTREntityGemsbok;
import lotr.common.entity.animal.LOTREntityGiraffe;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityKineAraw;
import lotr.common.entity.animal.LOTREntityLionBase;
import lotr.common.entity.animal.LOTREntityMidges;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.entity.animal.LOTREntityScorpion;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.animal.LOTREntitySwan;
import lotr.common.entity.animal.LOTREntityTermite;
import lotr.common.entity.animal.LOTREntityWhiteOryx;
import lotr.common.entity.animal.LOTREntityWildBoar;
import lotr.common.entity.animal.LOTREntityZebra;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.entity.item.LOTREntityBannerWall;
import lotr.common.entity.item.LOTREntityBarrel;
import lotr.common.entity.item.LOTREntityBearRug;
import lotr.common.entity.item.LOTREntityBossTrophy;
import lotr.common.entity.item.LOTREntityFallingTreasure;
import lotr.common.entity.item.LOTREntityGiraffeRug;
import lotr.common.entity.item.LOTREntityLionRug;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.item.LOTREntityPortal;
import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.common.entity.item.LOTREntityTraderRespawn;
import lotr.common.entity.item.LOTREntityWargskinRug;
import lotr.common.entity.npc.LOTREntityAngmarHillman;
import lotr.common.entity.npc.LOTREntityAngmarHillmanWarrior;
import lotr.common.entity.npc.LOTREntityBalrog;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.entity.npc.LOTREntityBlueDwarfCommander;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityBlueMountainsSmith;
import lotr.common.entity.npc.LOTREntityBreeBaker;
import lotr.common.entity.npc.LOTREntityBreeBlacksmith;
import lotr.common.entity.npc.LOTREntityBreeBrewer;
import lotr.common.entity.npc.LOTREntityBreeButcher;
import lotr.common.entity.npc.LOTREntityBreeFlorist;
import lotr.common.entity.npc.LOTREntityBreeHobbitBaker;
import lotr.common.entity.npc.LOTREntityBreeHobbitBrewer;
import lotr.common.entity.npc.LOTREntityBreeHobbitButcher;
import lotr.common.entity.npc.LOTREntityBreeHobbitFlorist;
import lotr.common.entity.npc.LOTREntityBreeHobbitInnkeeper;
import lotr.common.entity.npc.LOTREntityBreeInnkeeper;
import lotr.common.entity.npc.LOTREntityBreeMan;
import lotr.common.entity.npc.LOTREntityBreeMason;
import lotr.common.entity.npc.LOTREntityBreeRuffian;
import lotr.common.entity.npc.LOTREntityDaleBaker;
import lotr.common.entity.npc.LOTREntityDaleBlacksmith;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.common.entity.npc.LOTREntityDolAmrothSoldier;
import lotr.common.entity.npc.LOTREntityDorwinionElfVintner;
import lotr.common.entity.npc.LOTREntityDorwinionMan;
import lotr.common.entity.npc.LOTREntityDunedain;
import lotr.common.entity.npc.LOTREntityDunedainBlacksmith;
import lotr.common.entity.npc.LOTREntityDunlending;
import lotr.common.entity.npc.LOTREntityDunlendingWarrior;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.entity.npc.LOTREntityDwarfCommander;
import lotr.common.entity.npc.LOTREntityDwarfSmith;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.entity.npc.LOTREntityGaladhrimSmith;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.entity.npc.LOTREntityGaladhrimWarden;
import lotr.common.entity.npc.LOTREntityGandalf;
import lotr.common.entity.npc.LOTREntityGollum;
import lotr.common.entity.npc.LOTREntityGondorBaker;
import lotr.common.entity.npc.LOTREntityGondorBartender;
import lotr.common.entity.npc.LOTREntityGondorBlacksmith;
import lotr.common.entity.npc.LOTREntityGondorBrewer;
import lotr.common.entity.npc.LOTREntityGondorButcher;
import lotr.common.entity.npc.LOTREntityGondorFlorist;
import lotr.common.entity.npc.LOTREntityGondorGreengrocer;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityGondorMason;
import lotr.common.entity.npc.LOTREntityGondorRenegade;
import lotr.common.entity.npc.LOTREntityGulfBartender;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import lotr.common.entity.npc.LOTREntityHalfTrollScavenger;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import lotr.common.entity.npc.LOTREntityHarnedorBartender;
import lotr.common.entity.npc.LOTREntityHighElfSmith;
import lotr.common.entity.npc.LOTREntityHobbit;
import lotr.common.entity.npc.LOTREntityHobbitBartender;
import lotr.common.entity.npc.LOTREntityHuornBase;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.common.entity.npc.LOTREntityMarshWraith;
import lotr.common.entity.npc.LOTREntityMirkTroll;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.entity.npc.LOTREntityMordorSpider;
import lotr.common.entity.npc.LOTREntityMoredain;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityNearHaradBlacksmith;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarlord;
import lotr.common.entity.npc.LOTREntityNurnSlave;
import lotr.common.entity.npc.LOTREntityOlogHai;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.entity.npc.LOTREntityRanger;
import lotr.common.entity.npc.LOTREntityRivendellSmith;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityRohanBaker;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import lotr.common.entity.npc.LOTREntityRohanBrewer;
import lotr.common.entity.npc.LOTREntityRohanBuilder;
import lotr.common.entity.npc.LOTREntityRohanButcher;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohanMeadhost;
import lotr.common.entity.npc.LOTREntityRohanOrcharder;
import lotr.common.entity.npc.LOTREntitySaruman;
import lotr.common.entity.npc.LOTREntitySauron;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntitySkeletalWraith;
import lotr.common.entity.npc.LOTREntitySnowTroll;
import lotr.common.entity.npc.LOTREntitySouthronBartender;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.common.entity.npc.LOTREntityTauredainShaman;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.entity.npc.LOTREntityUmbarBartender;
import lotr.common.entity.npc.LOTREntityUtumnoIceSpider;
import lotr.common.entity.npc.LOTREntityUtumnoTroll;
import lotr.common.entity.npc.LOTREntityWarg;
import lotr.common.entity.npc.LOTREntityWickedDwarf;
import lotr.common.entity.npc.LOTREntityWoodElfSmith;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.projectile.LOTREntityConker;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.entity.projectile.LOTREntityFirePot;
import lotr.common.entity.projectile.LOTREntityGandalfFireball;
import lotr.common.entity.projectile.LOTREntityMallornLeafBomb;
import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import lotr.common.entity.projectile.LOTREntityMysteryWeb;
import lotr.common.entity.projectile.LOTREntityPebble;
import lotr.common.entity.projectile.LOTREntityPlate;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import lotr.common.entity.projectile.LOTREntitySpear;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import lotr.common.entity.projectile.LOTREntityThrownTermite;
import lotr.common.fac.LOTRAlignmentBonusMap;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.LOTRPacketClientInfo;
import lotr.common.network.LOTRPacketFellowshipAcceptInviteResult;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import lotr.common.tileentity.LOTRTileEntityChest;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import lotr.common.tileentity.LOTRTileEntityElvenPortal;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import lotr.common.tileentity.LOTRTileEntityMorgulPortal;
import lotr.common.tileentity.LOTRTileEntityMug;
import lotr.common.tileentity.LOTRTileEntityPlate;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import lotr.common.tileentity.LOTRTileEntitySignCarvedIthildin;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import lotr.common.tileentity.LOTRTileEntityTrollTotem;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import lotr.common.tileentity.LOTRTileEntityUtumnoPortal;
import lotr.common.tileentity.LOTRTileEntityUtumnoReturnPortal;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import lotr.common.util.LOTRFunctions;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRAbstractWaypoint;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import org.lwjgl.opengl.GL11;

public class LOTRClientProxy extends LOTRCommonProxy {
   public static final ResourceLocation enchantmentTexture = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   public static final ResourceLocation alignmentTexture = new ResourceLocation("lotr:gui/alignment.png");
   public static final ResourceLocation particlesTexture = new ResourceLocation("lotr:misc/particles.png");
   public static final ResourceLocation particles2Texture = new ResourceLocation("lotr:misc/particles2.png");
   public static final ResourceLocation customPotionsTexture = new ResourceLocation("lotr:gui/effects.png");
   public static final int TESSELLATOR_MAX_BRIGHTNESS = 15728880;
   public static final int FONTRENDERER_ALPHA_MIN = 4;
   public static LOTREffectRenderer customEffectRenderer;
   public static LOTRRenderPlayer specialPlayerRenderer = new LOTRRenderPlayer();
   public static LOTRSwingHandler swingHandler = new LOTRSwingHandler();
   public static LOTRTickHandlerClient tickHandler = new LOTRTickHandlerClient();
   public static LOTRKeyHandler keyHandler = new LOTRKeyHandler();
   private static LOTRGuiHandler guiHandler = new LOTRGuiHandler();
   public static LOTRMusic musicHandler;
   private int beaconRenderID;
   private int barrelRenderID;
   private int orcBombRenderID;
   private int doubleTorchRenderID;
   private int mobSpawnerRenderID;
   private int plateRenderID;
   private int stalactiteRenderID;
   private int flowerPotRenderID;
   private int cloverRenderID;
   private int entJarRenderID;
   private int trollTotemRenderID;
   private int fenceRenderID;
   private int grassRenderID;
   private int fallenLeavesRenderID;
   private int commandTableRenderID;
   private int butterflyJarRenderID;
   private int unsmelteryRenderID;
   private int chestRenderID;
   private int reedsRenderID;
   private int wasteRenderID;
   private int beamRenderID;
   private int vCauldronRenderID;
   private int grapevineRenderID;
   private int thatchFloorRenderID;
   private int treasureRenderID;
   private int flowerRenderID;
   private int doublePlantRenderID;
   private int birdCageRenderID;
   private int rhunFireJarRenderID;
   private int coralRenderID;
   private int doorRenderID;
   private int ropeRenderID;
   private int orcChainRenderID;
   private int guldurilRenderID;
   private int orcPlatingRenderID;
   private int trapdoorRenderID;

   public void onPreload() {
      System.setProperty("fml.skipFirstTextureLoad", "false");
      LOTRItemRendererManager.load();
      LOTRArmorModels.setupArmorModels();
   }

   public void onLoad() {
      customEffectRenderer = new LOTREffectRenderer(Minecraft.func_71410_x());
      LOTRTextures.load();
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityPortal.class, new LOTRRenderPortal());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHorse.class, new LOTRRenderHorse());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHobbit.class, new LOTRRenderHobbit());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHobbitBartender.class, new LOTRRenderHobbitTrader("outfit_bartender"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySmokeRing.class, new LOTRRenderSmokeRing());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityOrc.class, new LOTRRenderOrc());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityShirePony.class, new LOTRRenderShirePony());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityOrcBomb.class, new LOTRRenderOrcBomb());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityWarg.class, new LOTRRenderWarg());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGandalfFireball.class, new LOTRRenderGandalfFireball());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySpear.class, new LOTRRenderSpear());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySauron.class, new LOTRRenderSauron());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityElf.class, new LOTRRenderElf());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityPlate.class, new LOTRRenderPlate());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityWargskinRug.class, new LOTRRenderWargskinRug());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySkeletalWraith.class, new LOTRRenderSkeleton());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBlacksmith.class, new LOTRRenderGondorTrader("outfit_blacksmith"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimTrader.class, new LOTRRenderElvenTrader("galadhrimTrader_cloak"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityAlignmentBonus.class, new LOTRRenderAlignmentBonus());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarf.class, new LOTRRenderDwarf());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMarshWraith.class, new LOTRRenderMarshWraith());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMarshWraithBall.class, new LOTRRenderWraithBall());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarfCommander.class, new LOTRRenderDwarfCommander());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueDwarfCommander.class, new LOTRRenderDwarfCommander());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueDwarfMerchant.class, new LOTRRenderDwarfCommander());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrowingAxe.class, new LOTRRenderThrowingAxe());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityCrossbowBolt.class, new LOTRRenderCrossbowBolt());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityTroll.class, new LOTRRenderTroll());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityOlogHai.class, new LOTRRenderOlogHai());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityStoneTroll.class, new LOTRRenderStoneTroll());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGollum.class, new LOTRRenderGollum());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMirkwoodSpider.class, new LOTRRenderMirkwoodSpider());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanMan.class, new LOTRRenderRohirrim());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityPebble.class, new RenderSnowball(LOTRMod.pebble));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMysteryWeb.class, new RenderSnowball(LOTRMod.mysteryWeb));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBlacksmith.class, new LOTRRenderRohanTrader("outfit_blacksmith"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRanger.class, new LOTRRenderRanger());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunlending.class, new LOTRRenderDunlending());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunlendingWarrior.class, new LOTRRenderDunlendingBase());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityEnt.class, new LOTRRenderEnt());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityTraderRespawn.class, new LOTRRenderTraderRespawn());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMountainTroll.class, new LOTRRenderMountainTroll());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrownRock.class, new LOTRRenderThrownRock());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMountainTrollChieftain.class, new LOTRRenderMountainTrollChieftain());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHuornBase.class, new LOTRRenderHuorn());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanMeadhost.class, new LOTRRenderRohanTrader("outfit_meadhost"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityButterfly.class, new LOTRRenderButterfly());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBarrel.class, new LOTRRenderEntityBarrel());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMidges.class, new LOTRRenderMidges());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDeadMarshFace.class, new LOTRRenderDeadMarshFace());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityNurnSlave.class, new LOTRRenderNurnSlave());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRabbit.class, new LOTRRenderRabbit());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityWildBoar.class, new LOTRRenderWildBoar());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMordorSpider.class, new LOTRRenderMordorSpider());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBanner.class, new LOTRRenderBanner());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBannerWall.class, new LOTRRenderBannerWall());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityLionBase.class, new LOTRRenderLion());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGiraffe.class, new LOTRRenderGiraffe());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityZebra.class, new LOTRRenderZebra());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRhino.class, new LOTRRenderRhino());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityCrocodile.class, new LOTRRenderCrocodile());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradrimBase.class, new LOTRRenderNearHaradrim());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradrimWarlord.class, new LOTRRenderNearHaradrimWarlord());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGemsbok.class, new LOTRRenderGemsbok());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityFlamingo.class, new LOTRRenderFlamingo());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityScorpion.class, new LOTRRenderScorpion());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBird.class, new LOTRRenderBird());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityCamel.class, new LOTRRenderCamel());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBandit.class, new LOTRRenderBandit("bandit"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySaruman.class, new LOTRRenderSaruman());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityInvasionSpawner.class, new LOTRRenderInvasionSpawner());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityElk.class, new LOTRRenderElk());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMirkTroll.class, new LOTRRenderMirkTroll());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityTermite.class, new LOTRRenderTermite());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityThrownTermite.class, new RenderSnowball(LOTRMod.termite));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDikDik.class, new LOTRRenderDikDik());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityUtumnoIceSpider.class, new LOTRRenderUtumnoIceSpider());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityConker.class, new RenderSnowball(LOTRMod.chestnut));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityUtumnoTroll.class, new LOTRRenderUtumnoTroll());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBalrog.class, new LOTRRenderBalrog());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHalfTroll.class, new LOTRRenderHalfTroll());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHalfTrollScavenger.class, new LOTRRenderHalfTrollScavenger());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimSmith.class, new LOTRRenderElvenSmith("galadhrimSmith_cloak", "galadhrimSmith_cape"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHighElfSmith.class, new LOTRRenderElvenSmith("highElfSmith_cloak", "highElfSmith_cape"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityWoodElfSmith.class, new LOTRRenderElvenSmith("woodElfSmith_cloak", "woodElfSmith_cape"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDolAmrothSoldier.class, new LOTRRenderSwanKnight());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySwan.class, new LOTRRenderSwan());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMoredain.class, new LOTRRenderMoredain());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityAngmarHillman.class, new LOTRRenderAngmarHillman(true));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityAngmarHillmanWarrior.class, new LOTRRenderAngmarHillman(false));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityIronHillsMerchant.class, new LOTRRenderDwarfCommander());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBossTrophy.class, new LOTRRenderBossTrophy());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMallornEnt.class, new LOTRRenderMallornEnt());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityMallornLeafBomb.class, new LOTRRenderMallornLeafBomb());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityScrapTrader.class, new LOTRRenderScrapTrader());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityTauredain.class, new LOTRRenderTauredain());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDart.class, new LOTRRenderDart());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBarrowWight.class, new LOTRRenderBarrowWight());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityTauredainShaman.class, new LOTRRenderTauredainShaman());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGaladhrimWarden.class, new LOTRRenderGaladhrimWarden());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDwarfSmith.class, new LOTRRenderDwarfSmith());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBlueMountainsSmith.class, new LOTRRenderDwarfSmith());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBanditHarad.class, new LOTRRenderBandit("harad"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDeer.class, new LOTRRenderDeer());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleMan.class, new LOTRRenderDaleMan());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleBlacksmith.class, new LOTRRenderDaleTrader("blacksmith_apron"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityNPCRespawner.class, new LOTRRenderNPCRespawner());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDorwinionMan.class, new LOTRRenderDorwinionMan());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDaleBaker.class, new LOTRRenderDaleTrader("baker_apron"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDorwinionElfVintner.class, new LOTRRenderDorwinionElfVintner());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityAurochs.class, new LOTRRenderAurochs());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityKineAraw.class, new LOTRRenderKineAraw());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorMan.class, new LOTRRenderGondorMan());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityFallingTreasure.class, new LOTRRenderFallingCoinPile());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBartender.class, new LOTRRenderGondorTrader("outfit_bartender"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorGreengrocer.class, new LOTRRenderGondorTrader("outfit_greengrocer"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorMason.class, new LOTRRenderGondorTrader("outfit_mason"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBrewer.class, new LOTRRenderGondorTrader("outfit_brewer"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorFlorist.class, new LOTRRenderGondorTrader("outfit_florist"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorButcher.class, new LOTRRenderGondorTrader("outfit_butcher"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorBaker.class, new LOTRRenderGondorTrader("outfit_baker"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunedain.class, new LOTRRenderDunedain());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityDunedainBlacksmith.class, new LOTRRenderDunedainTrader("outfit_blacksmith"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBuilder.class, new LOTRRenderRohanTrader("outfit_builder"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBrewer.class, new LOTRRenderRohanTrader("outfit_brewer"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanButcher.class, new LOTRRenderRohanTrader("outfit_butcher"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanBaker.class, new LOTRRenderRohanTrader("outfit_baker"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRohanOrcharder.class, new LOTRRenderRohanTrader("outfit_orcharder"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBear.class, new LOTRRenderBear());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityEasterling.class, new LOTRRenderEasterling());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityEasterlingBlacksmith.class, new LOTRRenderEasterlingTrader("outfit_blacksmith"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityFallingFireJar.class, new LOTRRenderFallingFireJar());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityFirePot.class, new RenderSnowball(LOTRMod.rhunFirePot));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRivendellSmith.class, new LOTRRenderElvenSmith("rivendellSmith_cloak", "rivendellSmith_cape"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityRivendellTrader.class, new LOTRRenderElvenTrader("rivendellTrader_cloak"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityFish.class, new LOTRRenderFish());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityArrowPoisoned.class, new LOTRRenderArrowPoisoned());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityNearHaradBlacksmith.class, new LOTRRenderNearHaradTrader("outfit_blacksmith"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySnowTroll.class, new LOTRRenderSnowTroll());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityLionRug.class, new LOTRRenderLionRug());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBearRug.class, new LOTRRenderBearRug());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGiraffeRug.class, new LOTRRenderGiraffeRug());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHaradSlave.class, new LOTRRenderHaradSlave());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGondorRenegade.class, new LOTRRenderGondorRenegade());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityHarnedorBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySouthronBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityUmbarBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGulfBartender.class, new LOTRRenderHaradrimTrader("outfit_bartender"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityWhiteOryx.class, new LOTRRenderWhiteOryx());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityGandalf.class, new LOTRRenderGandalf());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityWickedDwarf.class, new LOTRRenderWickedDwarf());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeMan.class, new LOTRRenderBreeMan());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntitySwordCommandMarker.class, new LOTRRenderSwordCommandMarker());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBlacksmith.class, new LOTRRenderBreeTrader("outfit_blacksmith"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeInnkeeper.class, new LOTRRenderBreeTrader("outfit_innkeeper"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeRuffian.class, new LOTRRenderBreeRuffian());
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitInnkeeper.class, new LOTRRenderHobbitTrader("outfit_bartender"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBaker.class, new LOTRRenderBreeTrader("outfit_baker"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeButcher.class, new LOTRRenderBreeTrader("outfit_butcher"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeBrewer.class, new LOTRRenderBreeTrader("outfit_brewer"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeMason.class, new LOTRRenderBreeTrader("outfit_mason"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeFlorist.class, new LOTRRenderBreeTrader("outfit_florist"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitBaker.class, new LOTRRenderHobbitTrader("outfit_baker"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitButcher.class, new LOTRRenderHobbitTrader("outfit_butcher"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitBrewer.class, new LOTRRenderHobbitTrader("outfit_brewer"));
      RenderingRegistry.registerEntityRenderingHandler(LOTREntityBreeHobbitFlorist.class, new LOTRRenderHobbitTrader("outfit_florist"));
      RenderingRegistry.registerEntityRenderingHandler(EntityPotion.class, new RenderSnowball(Items.field_151068_bn, 16384));
      this.beaconRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.barrelRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.orcBombRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.doubleTorchRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.mobSpawnerRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.plateRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.stalactiteRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.flowerPotRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.cloverRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.entJarRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.trollTotemRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.fenceRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.grassRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.fallenLeavesRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.commandTableRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.butterflyJarRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.unsmelteryRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.chestRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.reedsRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.wasteRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.beamRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.vCauldronRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.grapevineRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.thatchFloorRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.treasureRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.flowerRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.doublePlantRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.birdCageRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.rhunFireJarRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.coralRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.doorRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.ropeRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.orcChainRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.guldurilRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.orcPlatingRenderID = RenderingRegistry.getNextAvailableRenderId();
      this.trapdoorRenderID = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(this.beaconRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.barrelRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.orcBombRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.doubleTorchRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.mobSpawnerRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.plateRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.stalactiteRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.flowerPotRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.cloverRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.entJarRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.trollTotemRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.fenceRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.grassRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.fallenLeavesRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.commandTableRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.butterflyJarRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.unsmelteryRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.chestRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.reedsRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.wasteRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.beamRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.vCauldronRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.grapevineRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.thatchFloorRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.treasureRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.flowerRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.doublePlantRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.birdCageRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.rhunFireJarRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.coralRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.doorRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.ropeRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.orcChainRenderID, new LOTRRenderBlocks(false));
      RenderingRegistry.registerBlockHandler(this.guldurilRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.orcPlatingRenderID, new LOTRRenderBlocks(true));
      RenderingRegistry.registerBlockHandler(this.trapdoorRenderID, new LOTRRenderBlocks(true));
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityBeacon.class, new LOTRRenderBeacon());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMobSpawner.class, new LOTRTileEntityMobSpawnerRenderer());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityPlate.class, new LOTRRenderPlateFood());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityElvenPortal.class, new LOTRRenderElvenPortal());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySpawnerChest.class, new LOTRRenderSpawnerChest());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityGulduril.class, new LOTRRenderGuldurilGlow());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityDwarvenDoor.class, new LOTRRenderDwarvenDoor());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMorgulPortal.class, new LOTRRenderMorgulPortal());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityArmorStand.class, new LOTRRenderArmorStand());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityMug.class, new LOTRRenderMug());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityEntJar.class, new LOTRRenderEntJar());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityTrollTotem.class, new LOTRRenderTrollTotem());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUtumnoPortal.class, new LOTRRenderUtumnoPortal());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUtumnoReturnPortal.class, new LOTRRenderUtumnoReturnPortal());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityCommandTable.class, new LOTRRenderCommandTable());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityAnimalJar.class, new LOTRRenderAnimalJar());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityUnsmeltery.class, new LOTRRenderUnsmeltery());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityDartTrap.class, new LOTRRenderDartTrap());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityChest.class, new LOTRRenderChest());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityWeaponRack.class, new LOTRRenderWeaponRack());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntityKebabStand.class, new LOTRRenderKebabStand());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySignCarved.class, new LOTRRenderSignCarved());
      ClientRegistry.bindTileEntitySpecialRenderer(LOTRTileEntitySignCarvedIthildin.class, new LOTRRenderSignCarvedIthildin());
   }

   public void onPostload() {
      if (LOTRConfig.updateLangFiles) {
         LOTRLang.runUpdateThread();
      }

      musicHandler = new LOTRMusic();
   }

   public void testReflection(World world) {
      super.testReflection(world);
      LOTRReflectionClient.testAll(world, Minecraft.func_71410_x());
   }

   public static void renderEnchantmentEffect() {
      Tessellator tessellator = Tessellator.field_78398_a;
      TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
      GL11.glDepthFunc(514);
      GL11.glDisable(2896);
      texturemanager.func_110577_a(enchantmentTexture);
      GL11.glEnable(3042);
      GL11.glBlendFunc(768, 1);
      float shade = 0.76F;
      GL11.glColor4f(0.5F * shade, 0.25F * shade, 0.8F * shade, 1.0F);
      GL11.glMatrixMode(5890);
      GL11.glPushMatrix();
      float scale = 0.125F;
      GL11.glScalef(scale, scale, scale);
      float randomShift = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0F * 8.0F;
      GL11.glTranslatef(randomShift, 0.0F, 0.0F);
      GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
      ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glScalef(scale, scale, scale);
      randomShift = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0F * 8.0F;
      GL11.glTranslatef(-randomShift, 0.0F, 0.0F);
      GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
      ItemRenderer.func_78439_a(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
      GL11.glPopMatrix();
      GL11.glMatrixMode(5888);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glDepthFunc(515);
   }

   public static void sendClientInfoPacket(LOTRFaction viewingFaction, Map changedRegionMap) {
      boolean showWP = LOTRGuiMap.showWP;
      boolean showCWP = LOTRGuiMap.showCWP;
      boolean showHiddenSWP = LOTRGuiMap.showHiddenSWP;
      LOTRPacketClientInfo packet = new LOTRPacketClientInfo(viewingFaction, changedRegionMap, showWP, showCWP, showHiddenSWP);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }

   public static int getAlphaInt(float alphaF) {
      int alphaI = (int)(alphaF * 255.0F);
      alphaI = MathHelper.func_76125_a(alphaI, 4, 255);
      return alphaI;
   }

   public boolean isClient() {
      return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
   }

   public boolean isSingleplayer() {
      return Minecraft.func_71410_x().func_71356_B();
   }

   public World getClientWorld() {
      return Minecraft.func_71410_x().field_71441_e;
   }

   public EntityPlayer getClientPlayer() {
      return Minecraft.func_71410_x().field_71439_g;
   }

   public static boolean doesClientChunkExist(World world, int i, int k) {
      int chunkX = i >> 4;
      int chunkZ = k >> 4;
      Chunk chunk = world.func_72863_F().func_73154_d(chunkX, chunkZ);
      return !(chunk instanceof EmptyChunk);
   }

   public boolean isPaused() {
      return Minecraft.func_71410_x().func_147113_T();
   }

   public void setClientDifficulty(EnumDifficulty difficulty) {
      Minecraft.func_71410_x().field_71474_y.field_74318_M = difficulty;
   }

   public void setWaypointModes(boolean showWP, boolean showCWP, boolean showHiddenSWP) {
      LOTRGuiMap.showWP = showWP;
      LOTRGuiMap.showCWP = showCWP;
      LOTRGuiMap.showHiddenSWP = showHiddenSWP;
   }

   public void spawnAlignmentBonus(LOTRFaction faction, float prevMainAlignment, LOTRAlignmentBonusMap factionBonusMap, String name, boolean isKill, boolean isHiredKill, float conquestBonus, double posX, double posY, double posZ) {
      World world = this.getClientWorld();
      if (world != null) {
         LOTREntityAlignmentBonus entity = new LOTREntityAlignmentBonus(world, posX, posY, posZ, name, faction, prevMainAlignment, factionBonusMap, isKill, isHiredKill, conquestBonus);
         world.func_72838_d(entity);
      }

   }

   public void displayAlignDrain(int numFactions) {
      LOTRTickHandlerClient.alignDrainTick = 200;
      LOTRTickHandlerClient.alignDrainNum = numFactions;
   }

   public void queueAchievement(LOTRAchievement achievement) {
      LOTRTickHandlerClient.notificationDisplay.queueAchievement(achievement);
   }

   public void queueFellowshipNotification(IChatComponent message) {
      LOTRTickHandlerClient.notificationDisplay.queueFellowshipNotification(message);
   }

   public void displayFellowshipAcceptInvitationResult(UUID fellowshipID, String name, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = mc.field_71462_r;
      if (gui instanceof LOTRGuiFellowships) {
         ((LOTRGuiFellowships)gui).displayAcceptInvitationResult(fellowshipID, name, result);
      }

   }

   public void queueConquestNotification(LOTRFaction fac, float conq, boolean isCleansing) {
      LOTRTickHandlerClient.notificationDisplay.queueConquest(fac, conq, isCleansing);
   }

   public void displayMessage(LOTRGuiMessageTypes message) {
      Minecraft.func_71410_x().func_147108_a(new LOTRGuiMessage(message));
   }

   public void openHiredNPCGui(LOTREntityNPC npc) {
      Minecraft mc = Minecraft.func_71410_x();
      if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
         mc.func_147108_a(new LOTRGuiHiredWarrior(npc));
      } else if (npc.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.FARMER) {
         mc.func_147108_a(new LOTRGuiHiredFarmer(npc));
      }

   }

   public void setMapIsOp(boolean isOp) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = mc.field_71462_r;
      if (gui instanceof LOTRGuiMap) {
         LOTRGuiMap map = (LOTRGuiMap)gui;
         map.isPlayerOp = isOp;
      }

   }

   public void displayFTScreen(LOTRAbstractWaypoint waypoint, int startX, int startZ) {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_147108_a(new LOTRGuiFastTravel(waypoint, startX, startZ));
   }

   public void showFrostOverlay() {
      tickHandler.onFrostDamage();
   }

   public void showBurnOverlay() {
      tickHandler.onBurnDamage();
   }

   public void clearMapPlayerLocations() {
      LOTRGuiMap.clearPlayerLocations();
   }

   public void addMapPlayerLocation(GameProfile player, double posX, double posZ) {
      LOTRGuiMap.addPlayerLocationInfo(player, posX, posZ);
   }

   public void setMapCWPProtectionMessage(IChatComponent message) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = mc.field_71462_r;
      if (gui instanceof LOTRGuiMap) {
         ((LOTRGuiMap)gui).setCWPProtectionMessage(message);
      }

   }

   public void displayBannerGui(LOTREntityBanner banner) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = new LOTRGuiBanner(banner);
      mc.func_147108_a(gui);
   }

   public void validateBannerUsername(LOTREntityBanner banner, int slot, String prevText, boolean valid) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = mc.field_71462_r;
      if (gui instanceof LOTRGuiBanner) {
         LOTRGuiBanner guiBanner = (LOTRGuiBanner)gui;
         if (guiBanner.theBanner == banner) {
            guiBanner.validateUsername(slot, prevText, valid);
         }
      }

   }

   public void clientReceiveSpeech(LOTREntityNPC npc, String speech) {
      LOTRSpeechClient.receiveSpeech(npc, speech);
   }

   public void displayNewDate() {
      tickHandler.updateDate();
   }

   public void displayMiniquestOffer(LOTRMiniQuest quest, LOTREntityNPC npc) {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_147108_a(new LOTRGuiMiniquestOffer(quest, npc));
   }

   public void setTrackedQuest(LOTRMiniQuest quest) {
      LOTRTickHandlerClient.miniquestTracker.setTrackedQuest(quest);
   }

   public void displayAlignmentSee(String username, Map alignments) {
      LOTRGuiFactions gui = new LOTRGuiFactions();
      gui.setOtherPlayer(username, alignments);
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_147108_a(gui);
   }

   public void displayAlignmentChoice() {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_147108_a(new LOTRGuiAlignmentChoices());
   }

   public void cancelItemHighlight() {
      tickHandler.cancelItemHighlight = true;
   }

   public void receiveConquestGrid(LOTRFaction conqFac, List allZones) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = mc.field_71462_r;
      if (gui instanceof LOTRGuiMap) {
         ((LOTRGuiMap)gui).receiveConquestGrid(conqFac, allZones);
      }

   }

   public void handleInvasionWatch(int invasionEntityID, boolean overrideAlreadyWatched) {
      LOTRInvasionStatus status = LOTRTickHandlerClient.watchedInvasion;
      if (overrideAlreadyWatched || !status.isActive()) {
         World world = this.getClientWorld();
         Entity e = world.func_73045_a(invasionEntityID);
         if (e instanceof LOTREntityInvasionSpawner) {
            status.setWatchedInvasion((LOTREntityInvasionSpawner)e);
         }
      }

   }

   public void setInPortal(EntityPlayer entityplayer) {
      if (!LOTRTickHandlerClient.playersInPortals.containsKey(entityplayer)) {
         LOTRTickHandlerClient.playersInPortals.put(entityplayer, 0);
      }

      if (Minecraft.func_71410_x().func_71356_B() && !LOTRTickHandlerServer.playersInPortals.containsKey(entityplayer)) {
         LOTRTickHandlerServer.playersInPortals.put(entityplayer, 0);
      }

   }

   public void setInElvenPortal(EntityPlayer entityplayer) {
      if (!LOTRTickHandlerClient.playersInElvenPortals.containsKey(entityplayer)) {
         LOTRTickHandlerClient.playersInElvenPortals.put(entityplayer, 0);
      }

      if (Minecraft.func_71410_x().func_71356_B() && !LOTRTickHandlerServer.playersInElvenPortals.containsKey(entityplayer)) {
         LOTRTickHandlerServer.playersInElvenPortals.put(entityplayer, 0);
      }

   }

   public void setInMorgulPortal(EntityPlayer entityplayer) {
      if (!LOTRTickHandlerClient.playersInMorgulPortals.containsKey(entityplayer)) {
         LOTRTickHandlerClient.playersInMorgulPortals.put(entityplayer, 0);
      }

      if (Minecraft.func_71410_x().func_71356_B() && !LOTRTickHandlerServer.playersInMorgulPortals.containsKey(entityplayer)) {
         LOTRTickHandlerServer.playersInMorgulPortals.put(entityplayer, 0);
      }

   }

   public void setInUtumnoReturnPortal(EntityPlayer entityplayer) {
      if (entityplayer == Minecraft.func_71410_x().field_71439_g) {
         tickHandler.inUtumnoReturnPortal = true;
      }

   }

   public void setUtumnoReturnPortalCoords(EntityPlayer entityplayer, int x, int z) {
      if (entityplayer == Minecraft.func_71410_x().field_71439_g) {
         tickHandler.inUtumnoReturnPortal = true;
         tickHandler.utumnoReturnX = x;
         tickHandler.utumnoReturnZ = z;
      }

   }

   public void spawnParticle(String type, double d, double d1, double d2, double d3, double d4, double d5) {
      Minecraft mc = Minecraft.func_71410_x();
      if (mc.field_71451_h != null && mc.field_71441_e != null) {
         World world = mc.field_71441_e;
         Random rand = world.field_73012_v;
         int i = mc.field_71474_y.field_74362_aa;
         if (i == 1 && rand.nextInt(3) == 0) {
            i = 2;
         }

         if (i > 1) {
            return;
         }

         if (type.equals("angry")) {
            customEffectRenderer.addEffect(new LOTREntityAngryFX(world, d, d1, d2, d3, d4, d5));
         } else if (type.equals("blueFlame")) {
            customEffectRenderer.addEffect(new LOTREntityBlueFlameFX(world, d, d1, d2, d3, d4, d5));
         } else if (type.equals("chill")) {
            mc.field_71452_i.func_78873_a(new LOTREntityChillFX(world, d, d1, d2, d3, d4, d5));
         } else {
            LOTREntityElvenGlowFX block;
            int summonerID;
            int color;
            if (type.startsWith("elvenGlow")) {
               block = new LOTREntityElvenGlowFX(world, d, d1, d2, d3, d4, d5);
               summonerID = type.indexOf("_");
               if (summonerID > -1) {
                  String hex = type.substring(summonerID + 1);
                  color = Integer.parseInt(hex, 16);
                  block.setElvenGlowColor(color);
               }

               mc.field_71452_i.func_78873_a(block);
            } else if (type.equals("gandalfFireball")) {
               mc.field_71452_i.func_78873_a(new LOTREntityGandalfFireballExplodeFX(world, d, d1, d2));
            } else if (type.equals("largeStone")) {
               mc.field_71452_i.func_78873_a(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.field_150348_b, 0));
            } else {
               int summonedID;
               if (type.startsWith("leaf")) {
                  String s = type.substring("leaf".length());
                  int[] texIndices = null;
                  if (s.startsWith("Gold")) {
                     if (rand.nextBoolean()) {
                        texIndices = LOTRFunctions.intRange(0, 5);
                     } else {
                        texIndices = LOTRFunctions.intRange(8, 13);
                     }
                  } else if (s.startsWith("Red")) {
                     if (rand.nextBoolean()) {
                        texIndices = LOTRFunctions.intRange(16, 21);
                     } else {
                        texIndices = LOTRFunctions.intRange(24, 29);
                     }
                  } else if (s.startsWith("Mirk")) {
                     if (rand.nextBoolean()) {
                        texIndices = LOTRFunctions.intRange(32, 37);
                     } else {
                        texIndices = LOTRFunctions.intRange(40, 45);
                     }
                  } else if (s.startsWith("Green")) {
                     if (rand.nextBoolean()) {
                        texIndices = LOTRFunctions.intRange(48, 53);
                     } else {
                        texIndices = LOTRFunctions.intRange(56, 61);
                     }
                  }

                  if (texIndices != null) {
                     if (type.indexOf("_") > -1) {
                        summonedID = Integer.parseInt(type.substring(type.indexOf("_") + 1));
                        customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices, summonedID));
                     } else {
                        customEffectRenderer.addEffect(new LOTREntityLeafFX(world, d, d1, d2, d3, d4, d5, texIndices));
                     }
                  }
               } else if (type.equals("marshFlame")) {
                  mc.field_71452_i.func_78873_a(new LOTREntityMarshFlameFX(world, d, d1, d2, d3, d4, d5));
               } else if (type.equals("marshLight")) {
                  customEffectRenderer.addEffect(new LOTREntityMarshLightFX(world, d, d1, d2, d3, d4, d5));
               } else {
                  String[] args;
                  if (type.startsWith("mEntHeal")) {
                     args = type.split("_", 3);
                     Block block = Block.func_149729_e(Integer.parseInt(args[1]));
                     summonedID = Integer.parseInt(args[2]);
                     color = block.func_149741_i(summonedID);
                     mc.field_71452_i.func_78873_a(new LOTREntityMallornEntHealFX(world, d, d1, d2, d3, d4, d5, block, summonedID, color));
                  } else if (type.equals("mEntJumpSmash")) {
                     mc.field_71452_i.func_78873_a(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, LOTRMod.wood, 1));
                  } else {
                     boolean meta;
                     byte meta;
                     if (type.equals("mEntSpawn")) {
                        block = null;
                        meta = false;
                        Block block;
                        if (world.field_73012_v.nextBoolean()) {
                           block = Blocks.field_150346_d;
                           meta = 0;
                        } else {
                           block = LOTRMod.wood;
                           meta = 1;
                        }

                        mc.field_71452_i.func_78873_a(new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, block, meta));
                     } else if (type.startsWith("mEntSummon")) {
                        args = type.split("_", 6);
                        summonerID = Integer.parseInt(args[1]);
                        summonedID = Integer.parseInt(args[2]);
                        float arcParam = Float.parseFloat(args[3]);
                        Block block = Block.func_149729_e(Integer.parseInt(args[4]));
                        int meta = Integer.parseInt(args[5]);
                        int color = block.func_149741_i(meta);
                        mc.field_71452_i.func_78873_a(new LOTREntityMallornEntSummonFX(world, d, d1, d2, d3, d4, d5, summonerID, summonedID, arcParam, block, meta, color));
                     } else if (type.equals("mirkwoodWater")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.mirkwoodCorrupted.getWaterColorMultiplier()));
                     } else if (type.equals("morgulPortal")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityMorgulPortalFX(world, d, d1, d2, d3, d4, d5));
                     } else if (type.equals("morgulWater")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityRiverWaterFX(world, d, d1, d2, d3, d4, d5, LOTRBiome.morgulVale.getWaterColorMultiplier()));
                     } else if (type.equals("mtcArmor")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityLargeBlockFX(world, d, d1, d2, d3, d4, d5, Blocks.field_150339_S, 0));
                     } else if (type.equals("mtcHeal")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityMTCHealFX(world, d, d1, d2, d3, d4, d5));
                     } else if (type.equals("mtcSpawn")) {
                        block = null;
                        meta = false;
                        Object block;
                        if (world.field_73012_v.nextBoolean()) {
                           block = Blocks.field_150348_b;
                           meta = 0;
                        } else if (world.field_73012_v.nextBoolean()) {
                           block = Blocks.field_150346_d;
                           meta = 0;
                        } else if (world.field_73012_v.nextBoolean()) {
                           block = Blocks.field_150351_n;
                           meta = 0;
                        } else {
                           block = Blocks.field_150354_m;
                           meta = 0;
                        }

                        mc.field_71452_i.func_78873_a(new LOTREntityBossSpawnFX(world, d, d1, d2, d3, d4, d5, (Block)block, meta));
                     } else if (type.equals("music")) {
                        double pitch = world.field_73012_v.nextDouble();
                        EntityFX note = new LOTREntityMusicFX(world, d, d1, d2, d3, d4, d5, pitch);
                        mc.field_71452_i.func_78873_a(note);
                     } else if (type.equals("pickpocket")) {
                        customEffectRenderer.addEffect(new LOTREntityPickpocketFX(world, d, d1, d2, d3, d4, d5));
                     } else if (type.equals("pickpocketFail")) {
                        customEffectRenderer.addEffect(new LOTREntityPickpocketFailFX(world, d, d1, d2, d3, d4, d5));
                     } else if (type.equals("quenditeSmoke")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityQuenditeSmokeFX(world, d, d1, d2, d3, d4, d5));
                     } else if (type.equals("utumnoKill")) {
                        customEffectRenderer.addEffect(new LOTREntityUtumnoKillFX(world, d, d1, d2, d3, d4, d5));
                     } else if (type.equals("wave")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityWaveFX(world, d, d1, d2, d3, d4, d5));
                     } else if (type.equals("whiteSmoke")) {
                        mc.field_71452_i.func_78873_a(new LOTREntityWhiteSmokeFX(world, d, d1, d2, d3, d4, d5));
                     }
                  }
               }
            }
         }
      }

   }

   public void placeFlowerInPot(World world, int i, int j, int k, int side, ItemStack itemstack) {
      if (!world.field_72995_K) {
         super.placeFlowerInPot(world, i, j, k, side, itemstack);
      } else {
         Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0F, 0.0F, 0.0F));
      }

   }

   public void fillMugFromCauldron(World world, int i, int j, int k, int side, ItemStack itemstack) {
      if (!world.field_72995_K) {
         super.fillMugFromCauldron(world, i, j, k, side, itemstack);
      } else {
         Minecraft.func_71410_x().field_71439_g.field_71174_a.func_147297_a(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0F, 0.0F, 0.0F));
      }

   }

   public void usePouchOnChest(EntityPlayer entityplayer, World world, int i, int j, int k, int side, ItemStack itemstack, int pouchSlot) {
      if (!world.field_72995_K) {
         super.usePouchOnChest(entityplayer, world, i, j, k, side, itemstack, pouchSlot);
      } else {
         ((EntityClientPlayerMP)entityplayer).field_71174_a.func_147297_a(new C08PacketPlayerBlockPlacement(i, j, k, side, itemstack, 0.0F, 0.0F, 0.0F));
      }

   }

   public void renderCustomPotionEffect(int x, int y, PotionEffect effect, Minecraft mc) {
      Potion potion = Potion.field_76425_a[effect.func_76456_a()];
      mc.func_110434_K().func_110577_a(customPotionsTexture);
      int l = potion.func_76392_e();
      GuiScreen screen = mc.field_71462_r;
      if (screen != null) {
         screen.func_73729_b(x + 6, y + 7, 0 + l % 8 * 18, 0 + l / 8 * 18, 18, 18);
      }

   }

   public int getBeaconRenderID() {
      return this.beaconRenderID;
   }

   public int getBarrelRenderID() {
      return this.barrelRenderID;
   }

   public int getOrcBombRenderID() {
      return this.orcBombRenderID;
   }

   public int getDoubleTorchRenderID() {
      return this.doubleTorchRenderID;
   }

   public int getMobSpawnerRenderID() {
      return this.mobSpawnerRenderID;
   }

   public int getPlateRenderID() {
      return this.plateRenderID;
   }

   public int getStalactiteRenderID() {
      return this.stalactiteRenderID;
   }

   public int getFlowerPotRenderID() {
      return this.flowerPotRenderID;
   }

   public int getCloverRenderID() {
      return this.cloverRenderID;
   }

   public int getEntJarRenderID() {
      return this.entJarRenderID;
   }

   public int getTrollTotemRenderID() {
      return this.trollTotemRenderID;
   }

   public int getFenceRenderID() {
      return this.fenceRenderID;
   }

   public int getGrassRenderID() {
      return this.grassRenderID;
   }

   public int getFallenLeavesRenderID() {
      return this.fallenLeavesRenderID;
   }

   public int getCommandTableRenderID() {
      return this.commandTableRenderID;
   }

   public int getButterflyJarRenderID() {
      return this.butterflyJarRenderID;
   }

   public int getUnsmelteryRenderID() {
      return this.unsmelteryRenderID;
   }

   public int getChestRenderID() {
      return this.chestRenderID;
   }

   public int getReedsRenderID() {
      return this.reedsRenderID;
   }

   public int getWasteRenderID() {
      return this.wasteRenderID;
   }

   public int getBeamRenderID() {
      return this.beamRenderID;
   }

   public int getVCauldronRenderID() {
      return this.vCauldronRenderID;
   }

   public int getGrapevineRenderID() {
      return this.grapevineRenderID;
   }

   public int getThatchFloorRenderID() {
      return this.thatchFloorRenderID;
   }

   public int getTreasureRenderID() {
      return this.treasureRenderID;
   }

   public int getFlowerRenderID() {
      return this.flowerRenderID;
   }

   public int getDoublePlantRenderID() {
      return this.doublePlantRenderID;
   }

   public int getBirdCageRenderID() {
      return this.birdCageRenderID;
   }

   public int getRhunFireJarRenderID() {
      return this.rhunFireJarRenderID;
   }

   public int getCoralRenderID() {
      return this.coralRenderID;
   }

   public int getDoorRenderID() {
      return this.doorRenderID;
   }

   public int getRopeRenderID() {
      return this.ropeRenderID;
   }

   public int getOrcChainRenderID() {
      return this.orcChainRenderID;
   }

   public int getGuldurilRenderID() {
      return this.guldurilRenderID;
   }

   public int getOrcPlatingRenderID() {
      return this.orcPlatingRenderID;
   }

   public int getTrapdoorRenderID() {
      return this.trapdoorRenderID;
   }
}
