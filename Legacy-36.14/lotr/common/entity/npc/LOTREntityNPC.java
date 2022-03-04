package lotr.common.entity.npc;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRShields;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.LOTREntityUtils;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIBurningPanic;
import lotr.common.entity.ai.LOTREntityAIHiringPlayerHurtByTarget;
import lotr.common.entity.ai.LOTREntityAIHiringPlayerHurtTarget;
import lotr.common.entity.ai.LOTREntityAINPCHurtByTarget;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTRNPCTargetSelector;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.item.LOTREntityArrowPoisoned;
import lotr.common.entity.item.LOTREntityTraderRespawn;
import lotr.common.entity.projectile.LOTREntityCrossbowBolt;
import lotr.common.entity.projectile.LOTREntityPebble;
import lotr.common.entity.projectile.LOTREntityPlate;
import lotr.common.fac.LOTRFaction;
import lotr.common.inventory.LOTRContainerAnvil;
import lotr.common.inventory.LOTRContainerCoinExchange;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.inventory.LOTRContainerUnitTrade;
import lotr.common.item.LOTRItemBow;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.item.LOTRItemModifierTemplate;
import lotr.common.item.LOTRItemOwnership;
import lotr.common.item.LOTRItemPartyHat;
import lotr.common.item.LOTRItemPouch;
import lotr.common.item.LOTRItemSpear;
import lotr.common.item.LOTRItemTrident;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCCombatStance;
import lotr.common.network.LOTRPacketNPCFX;
import lotr.common.network.LOTRPacketNPCIsEating;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.world.LOTRUtumnoLevel;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.command.IEntitySelector;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTREntityNPC extends EntityCreature implements IRangedAttackMob, LOTRRandomSkinEntity {
   public static IAttribute npcAttackDamage = (new RangedAttribute("lotr.npcAttackDamage", 2.0D, 0.0D, Double.MAX_VALUE)).func_111117_a("LOTR NPC Attack Damage");
   public static IAttribute npcAttackDamageExtra = (new RangedAttribute("lotr.npcAttackDamageExtra", 0.0D, 0.0D, Double.MAX_VALUE)).func_111117_a("LOTR NPC Extra Attack Damage");
   public static IAttribute npcAttackDamageDrunk = (new RangedAttribute("lotr.npcAttackDamageDrunk", 4.0D, 0.0D, Double.MAX_VALUE)).func_111117_a("LOTR NPC Drunken Attack Damage");
   public static IAttribute npcRangedAccuracy = (new RangedAttribute("lotr.npcRangedAccuracy", 1.0D, 0.0D, Double.MAX_VALUE)).func_111117_a("LOTR NPC Ranged Accuracy");
   public static IAttribute horseAttackSpeed = (new RangedAttribute("lotr.horseAttackSpeed", 1.7D, 0.0D, Double.MAX_VALUE)).func_111117_a("LOTR Horse Attack Speed");
   public static float MOUNT_RANGE_BONUS = 1.5F;
   protected float npcWidth = -1.0F;
   protected float npcHeight;
   private boolean loadingFromNBT = false;
   public boolean isPassive = false;
   public boolean isImmuneToFrost = false;
   protected boolean isChilly = false;
   protected boolean spawnsInDarkness = false;
   public boolean isNPCPersistent = false;
   public boolean liftSpawnRestrictions = false;
   private boolean isConquestSpawning = false;
   public boolean liftBannerRestrictions = false;
   private boolean addedBurningPanic = false;
   public List killBonusFactions = new ArrayList();
   private UUID invasionID = null;
   private boolean isTargetSeeker = false;
   public String npcLocationName;
   private boolean hasSpecificLocationName;
   public boolean spawnRidingHorse;
   protected boolean canBannerBearerSpawnRiding = false;
   private boolean ridingMount;
   public LOTRFamilyInfo familyInfo;
   public LOTREntityQuestInfo questInfo;
   public LOTRHiredNPCInfo hiredNPCInfo;
   public LOTRTraderNPCInfo traderNPCInfo;
   public LOTRTravellingTraderInfo travellingTraderInfo;
   public boolean isTraderEscort = false;
   private boolean shouldTraderRespawn = false;
   public LOTRBossInfo bossInfo;
   private boolean setInitialHome = false;
   private int initHomeX;
   private int initHomeY;
   private int initHomeZ;
   private int initHomeRange = -1;
   private LOTREntityNPC.AttackMode currentAttackMode;
   private boolean firstUpdatedAttackMode;
   private UUID prevAttackTarget;
   private int combatCooldown;
   private boolean combatStance;
   private boolean prevCombatStance;
   public boolean clientCombatStance;
   public boolean clientIsEating;
   private int nearbyBannerFactor;
   public LOTRInventoryNPCItems npcItemsInv;
   public LOTRInventoryHiredReplacedItems hiredReplacedInv;
   private ItemStack[] festiveItems;
   private Random festiveRand;
   private boolean initFestiveItems;
   public LOTRShields npcShield;
   public ResourceLocation npcCape;
   public int npcTalkTick;
   private boolean hurtOnlyByPlates;
   private List enpouchedDrops;
   private boolean enpouchNPCDrops;

   public LOTREntityNPC(World world) {
      super(world);
      this.currentAttackMode = LOTREntityNPC.AttackMode.IDLE;
      this.firstUpdatedAttackMode = false;
      this.festiveItems = new ItemStack[5];
      this.festiveRand = new Random();
      this.initFestiveItems = false;
      this.npcTalkTick = 0;
      this.hurtOnlyByPlates = true;
      this.enpouchedDrops = new ArrayList();
      this.enpouchNPCDrops = false;
      if (this instanceof LOTRBoss || this instanceof LOTRCharacter) {
         this.isNPCPersistent = true;
      }

   }

   public final boolean isTrader() {
      return this instanceof LOTRTradeable || this instanceof LOTRUnitTradeable || this instanceof LOTRMercenary;
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.familyInfo = new LOTRFamilyInfo(this);
      this.questInfo = new LOTREntityQuestInfo(this);
      this.hiredNPCInfo = new LOTRHiredNPCInfo(this);
      this.traderNPCInfo = new LOTRTraderNPCInfo(this);
      if (this instanceof LOTRTravellingTrader) {
         this.travellingTraderInfo = new LOTRTravellingTraderInfo((LOTRTravellingTrader)this);
      }

      if (this instanceof LOTRBoss) {
         this.bossInfo = new LOTRBossInfo((LOTRBoss)this);
      }

      this.setupNPCGender();
      this.setupNPCName();
      this.npcItemsInv = new LOTRInventoryNPCItems(this);
      this.hiredReplacedInv = new LOTRInventoryHiredReplacedItems(this);
   }

   public void setupNPCGender() {
   }

   public void setupNPCName() {
   }

   public void startTraderVisiting(EntityPlayer entityplayer) {
      this.travellingTraderInfo.startVisiting(entityplayer);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(npcAttackDamage);
      this.func_110140_aT().func_111150_b(npcAttackDamageExtra);
      this.func_110140_aT().func_111150_b(npcAttackDamageDrunk);
      this.func_110140_aT().func_111150_b(npcRangedAccuracy);
      this.func_110140_aT().func_111150_b(horseAttackSpeed);
   }

   public void setUniqueID(UUID uuid) {
      this.field_96093_i = uuid;
   }

   public int addTargetTasks(boolean seekTargets) {
      return this.addTargetTasks(seekTargets, LOTREntityAINearestAttackableTargetBasic.class);
   }

   public int addTargetTasks(boolean seekTargets, Class c) {
      this.field_70715_bh.field_75782_a.clear();
      this.field_70715_bh.func_75776_a(1, new LOTREntityAIHiringPlayerHurtByTarget(this));
      this.field_70715_bh.func_75776_a(2, new LOTREntityAIHiringPlayerHurtTarget(this));
      this.field_70715_bh.func_75776_a(3, new LOTREntityAINPCHurtByTarget(this, false));
      this.isTargetSeeker = seekTargets;
      return seekTargets ? addTargetTasks(this, 4, c) : 3;
   }

   public static int addTargetTasks(EntityCreature entity, int index, Class c) {
      try {
         Constructor constructor = c.getConstructor(EntityCreature.class, Class.class, Integer.TYPE, Boolean.TYPE, IEntitySelector.class);
         entity.field_70715_bh.func_75776_a(index, (EntityAIBase)constructor.newInstance(entity, EntityPlayer.class, 0, true, null));
         entity.field_70715_bh.func_75776_a(index, (EntityAIBase)constructor.newInstance(entity, EntityLiving.class, 0, true, new LOTRNPCTargetSelector(entity)));
      } catch (Exception var4) {
         FMLLog.severe("Error adding LOTR target tasks to entity " + entity.toString(), new Object[0]);
         var4.printStackTrace();
      }

      return index;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_70112_a(double dist) {
      EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
      if (entityplayer != null) {
         LOTRPlayerData data = LOTRLevelData.getData(entityplayer);
         if (!data.getMiniQuestsForEntity(this, true).isEmpty()) {
            return true;
         }
      }

      return super.func_70112_a(dist);
   }

   protected boolean shouldBurningPanic() {
      return true;
   }

   public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
      this.spawnRidingHorse = false;
      return this.func_110161_a(data);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      if (!this.field_70170_p.field_72995_K) {
         if (this.spawnRidingHorse && (!(this instanceof LOTRBannerBearer) || this.canBannerBearerSpawnRiding)) {
            LOTRNPCMount mount = this.createMountToRide();
            EntityCreature livingMount = (EntityCreature)mount;
            livingMount.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
            if (this.field_70170_p.func_147461_a(livingMount.field_70121_D).isEmpty()) {
               livingMount.func_110161_a((IEntityLivingData)null);
               this.field_70170_p.func_72838_d(livingMount);
               this.func_70078_a(livingMount);
               if (!(mount instanceof LOTREntityNPC)) {
                  this.setRidingHorse(true);
                  mount.setBelongsToNPC(true);
                  LOTRMountFunctions.setNavigatorRangeFromNPC(mount, this);
               }
            }
         }

         if (this.traderNPCInfo.getBuyTrades() != null && this.field_70146_Z.nextInt(10000) == 0) {
            LOTRTradeEntry[] var6 = this.traderNPCInfo.getBuyTrades();
            int var7 = var6.length;

            for(int var4 = 0; var4 < var7; ++var4) {
               LOTRTradeEntry trade = var6[var4];
               trade.setCost(trade.getCost() * 100);
            }

            this.familyInfo.setName("Friendly Capitalist");
         }
      }

      return data;
   }

   public LOTRNPCMount createMountToRide() {
      return new LOTREntityHorse(this.field_70170_p);
   }

   public void setRidingHorse(boolean flag) {
      this.ridingMount = flag;
      double d = this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
      if (flag) {
         d *= 1.5D;
      } else {
         d /= 1.5D;
      }

      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(d);
   }

   public void onPlayerStartTracking(EntityPlayerMP entityplayer) {
      this.hiredNPCInfo.sendBasicData(entityplayer);
      this.familyInfo.sendData(entityplayer);
      this.questInfo.sendData(entityplayer);
      this.sendCombatStance(entityplayer);
   }

   public boolean func_70650_aV() {
      return true;
   }

   public final String func_70005_c_() {
      if (this.func_94056_bM()) {
         return super.func_70005_c_();
      } else {
         String entityName = this.getEntityClassName();
         String npcName = this.getNPCName();
         if (LOTRMod.isAprilFools()) {
            npcName = "Gandalf";
         }

         return this.getNPCFormattedName(npcName, entityName);
      }
   }

   public String getNPCFormattedName(String npcName, String entityName) {
      return npcName.equals(entityName) ? entityName : StatCollector.func_74837_a("entity.lotr.generic.entityName", new Object[]{npcName, entityName});
   }

   public String getEntityClassName() {
      return super.func_70005_c_();
   }

   public String getNPCName() {
      return super.func_70005_c_();
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UNALIGNED;
   }

   public LOTRFaction getHiringFaction() {
      return this.getFaction();
   }

   public LOTRFaction getInfluenceZoneFaction() {
      return this.getFaction();
   }

   public boolean isCivilianNPC() {
      return !this.isTargetSeeker && !(this instanceof LOTRUnitTradeable) && !(this instanceof LOTRMercenary) && !(this instanceof LOTRBoss);
   }

   public boolean generatesControlZone() {
      return true;
   }

   public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
      return true;
   }

   public int getNPCTalkInterval() {
      return 40;
   }

   public boolean canNPCTalk() {
      return this.func_70089_S() && this.npcTalkTick >= this.getNPCTalkInterval();
   }

   public void markNPCSpoken() {
      this.npcTalkTick = 0;
   }

   public final void func_70624_b(EntityLivingBase target) {
      boolean speak = target != null && this.func_70635_at().func_75522_a(target) && this.field_70146_Z.nextInt(3) == 0;
      this.setAttackTarget(target, speak);
   }

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      EntityLivingBase prevEntityTarget = this.func_70638_az();
      super.func_70624_b(target);
      this.hiredNPCInfo.onSetTarget(target, prevEntityTarget);
      if (target != null && !target.func_110124_au().equals(this.prevAttackTarget)) {
         this.prevAttackTarget = target.func_110124_au();
         if (!this.field_70170_p.field_72995_K) {
            if (this.getAttackSound() != null) {
               this.field_70170_p.func_72956_a(this, this.getAttackSound(), this.func_70599_aP(), this.func_70647_i());
            }

            if (target instanceof EntityPlayer && speak) {
               final EntityPlayer entityplayer = (EntityPlayer)target;
               String speechBank = this.getSpeechBank(entityplayer);
               if (speechBank != null) {
                  IEntitySelector selectorAttackingNPCs = new IEntitySelector() {
                     public boolean func_82704_a(Entity entity) {
                        if (!(entity instanceof LOTREntityNPC)) {
                           return false;
                        } else {
                           LOTREntityNPC npc = (LOTREntityNPC)entity;
                           return npc.func_70650_aV() && npc.func_70089_S() && npc.func_70638_az() == entityplayer;
                        }
                     }
                  };
                  double range = 16.0D;
                  List nearbyMobs = this.field_70170_p.func_94576_a(this, this.field_70121_D.func_72314_b(range, range, range), selectorAttackingNPCs);
                  if (nearbyMobs.size() <= 5) {
                     this.sendSpeechBank(entityplayer, speechBank);
                  }
               }
            }
         }
      }

   }

   public String getAttackSound() {
      return null;
   }

   public int func_70627_aG() {
      return 200;
   }

   public boolean func_70631_g_() {
      return this.familyInfo.getAge() < 0;
   }

   public void changeNPCNameForMarriage(LOTREntityNPC spouse) {
   }

   public void createNPCChildName(LOTREntityNPC maleParent, LOTREntityNPC femaleParent) {
   }

   public boolean func_70692_ba() {
      return !this.isNPCPersistent && !this.shouldTraderRespawn && !this.hiredNPCInfo.isActive && !this.questInfo.anyActiveQuestPlayers();
   }

   protected final void func_70105_a(float f, float f1) {
      boolean flag = this.npcWidth > 0.0F;
      this.npcWidth = f;
      this.npcHeight = f1;
      if (!flag) {
         this.rescaleNPC(1.0F);
      }

   }

   protected void rescaleNPC(float f) {
      super.func_70105_a(this.npcWidth * f, this.npcHeight * f);
   }

   protected float getNPCScale() {
      return this.func_70631_g_() ? 0.5F : 1.0F;
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.rescaleNPC(this.getNPCScale());
      this.updateCombat();
      if (this.field_70173_aa % 10 == 0) {
         this.updateNearbyBanners();
      }

      this.familyInfo.onUpdate();
      this.questInfo.onUpdate();
      this.hiredNPCInfo.onUpdate();
      if (this instanceof LOTRTradeable) {
         this.traderNPCInfo.onUpdate();
      }

      if (this.travellingTraderInfo != null) {
         this.travellingTraderInfo.onUpdate();
      }

      int homeX;
      if ((this instanceof LOTRTradeable || this instanceof LOTRUnitTradeable) && !this.field_70170_p.field_72995_K) {
         if (!this.setInitialHome) {
            if (this.func_110175_bO()) {
               this.initHomeX = this.func_110172_bL().field_71574_a;
               this.initHomeY = this.func_110172_bL().field_71572_b;
               this.initHomeZ = this.func_110172_bL().field_71573_c;
               this.initHomeRange = (int)this.func_110174_bM();
            }

            this.setInitialHome = true;
         }

         homeX = LOTRConfig.preventTraderKidnap;
         if (homeX > 0 && this.setInitialHome && this.initHomeRange > 0) {
            double dSqToInitHome = this.func_70092_e((double)this.initHomeX + 0.5D, (double)this.initHomeY + 0.5D, (double)this.initHomeZ + 0.5D);
            if (dSqToInitHome > (double)(homeX * homeX)) {
               if (this.field_70154_o != null) {
                  this.func_70078_a((Entity)null);
               }

               this.field_70170_p.func_72938_d(this.initHomeX, this.initHomeZ);
               this.func_70012_b((double)this.initHomeX + 0.5D, (double)this.initHomeY, (double)this.initHomeZ + 0.5D, this.field_70177_z, this.field_70125_A);
            }
         }
      }

      if (this.bossInfo != null) {
         this.bossInfo.onUpdate();
      }

      if (!this.field_70170_p.field_72995_K && !this.addedBurningPanic) {
         LOTREntityUtils.removeAITask(this, LOTREntityAIBurningPanic.class);
         if (this.shouldBurningPanic()) {
            this.field_70714_bg.func_75776_a(0, new LOTREntityAIBurningPanic(this, 1.5D));
         }

         this.addedBurningPanic = true;
      }

      int homeY;
      if (!this.field_70170_p.field_72995_K && this.func_70089_S() && (this.isTrader() || this.hiredNPCInfo.isActive) && this.func_70638_az() == null) {
         float healAmount = 0.0F;
         if (this.field_70173_aa % 40 == 0) {
            ++healAmount;
         }

         if (this.hiredNPCInfo.isActive && this.nearbyBannerFactor > 0) {
            homeY = 240 - this.nearbyBannerFactor * 40;
            if (this.field_70173_aa % homeY == 0) {
               ++healAmount;
            }
         }

         if (healAmount > 0.0F) {
            this.func_70691_i(healAmount);
            if (this.field_70154_o instanceof EntityLivingBase && !(this.field_70154_o instanceof LOTREntityNPC)) {
               ((EntityLivingBase)this.field_70154_o).func_70691_i(healAmount);
            }
         }
      }

      if (!this.field_70170_p.field_72995_K && this.func_70089_S() && this.func_70638_az() == null) {
         boolean guiOpen = false;
         if (this instanceof LOTRTradeable || this instanceof LOTRUnitTradeable || this instanceof LOTRMercenary) {
            for(homeY = 0; homeY < this.field_70170_p.field_73010_i.size(); ++homeY) {
               EntityPlayer entityplayer = (EntityPlayer)this.field_70170_p.field_73010_i.get(homeY);
               Container container = entityplayer.field_71070_bA;
               if (container instanceof LOTRContainerTrade && ((LOTRContainerTrade)container).theTraderNPC == this) {
                  guiOpen = true;
                  break;
               }

               if (container instanceof LOTRContainerUnitTrade && ((LOTRContainerUnitTrade)container).theLivingTrader == this) {
                  guiOpen = true;
                  break;
               }

               if (container instanceof LOTRContainerCoinExchange && ((LOTRContainerCoinExchange)container).theTraderNPC == this) {
                  guiOpen = true;
                  break;
               }

               if (container instanceof LOTRContainerAnvil && ((LOTRContainerAnvil)container).theNPC == this) {
                  guiOpen = true;
                  break;
               }
            }
         }

         if (this.hiredNPCInfo.isActive && this.hiredNPCInfo.isGuiOpen) {
            guiOpen = true;
         }

         if (this.questInfo.anyOpenOfferPlayers()) {
            guiOpen = true;
         }

         if (guiOpen) {
            this.func_70661_as().func_75499_g();
            if (this.field_70154_o instanceof LOTRNPCMount) {
               ((EntityLiving)this.field_70154_o).func_70661_as().func_75499_g();
            }
         }
      }

      this.func_82168_bl();
      if (this.npcTalkTick < this.getNPCTalkInterval()) {
         ++this.npcTalkTick;
      }

      double d1;
      double distToHome;
      if (!this.field_70170_p.field_72995_K && this.func_110175_bO() && !this.func_110173_bK()) {
         homeX = this.func_110172_bL().field_71574_a;
         homeY = this.func_110172_bL().field_71572_b;
         int homeZ = this.func_110172_bL().field_71573_c;
         int homeRange = (int)this.func_110174_bM();
         d1 = (double)homeRange + 128.0D;
         distToHome = this.func_70011_f((double)homeX + 0.5D, (double)homeY + 0.5D, (double)homeZ + 0.5D);
         if (distToHome > d1) {
            this.func_110177_bN();
         } else if (this.func_70638_az() == null && this.func_70661_as().func_75500_f()) {
            this.func_110177_bN();
            boolean goDirectlyHome = false;
            if (this.field_70170_p.func_72899_e(homeX, homeY, homeZ) && this.hiredNPCInfo.isGuardMode()) {
               int guardRange = this.hiredNPCInfo.getGuardRange();
               goDirectlyHome = distToHome < 16.0D;
            }

            double homeSpeed = 1.3D;
            if (goDirectlyHome) {
               this.func_70661_as().func_75492_a((double)homeX + 0.5D, (double)homeY + 0.5D, (double)homeZ + 0.5D, homeSpeed);
            } else {
               Vec3 path = null;

               for(int l = 0; l < 16 && path == null; ++l) {
                  path = RandomPositionGenerator.func_75464_a(this, 8, 7, Vec3.func_72443_a((double)homeX, (double)homeY, (double)homeZ));
               }

               if (path != null) {
                  this.func_70661_as().func_75492_a(path.field_72450_a, path.field_72448_b, path.field_72449_c, homeSpeed);
               }
            }

            this.func_110171_b(homeX, homeY, homeZ, homeRange);
         }
      }

      if (this.isChilly) {
         double speedSq = this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y;
         if (speedSq >= 0.01D) {
            double d = this.field_70165_t + (double)(MathHelper.func_151240_a(this.field_70146_Z, -0.3F, 0.3F) * this.field_70130_N);
            d1 = this.field_70121_D.field_72338_b + (double)(MathHelper.func_151240_a(this.field_70146_Z, 0.2F, 0.7F) * this.field_70131_O);
            distToHome = this.field_70161_v + (double)(MathHelper.func_151240_a(this.field_70146_Z, -0.3F, 0.3F) * this.field_70130_N);
            LOTRMod.proxy.spawnParticle("chill", d, d1, distToHome, -this.field_70159_w * 0.5D, 0.0D, -this.field_70179_y * 0.5D);
         }
      }

   }

   private void updateCombat() {
      if (!this.field_70170_p.field_72995_K && this.func_70638_az() != null) {
         EntityLivingBase entity = this.func_70638_az();
         if (!entity.func_70089_S() || entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
            this.func_70624_b((EntityLivingBase)null);
         }
      }

      boolean changedMounted = false;
      boolean changedAttackMode = false;
      if (!this.field_70170_p.field_72995_K) {
         boolean isRidingMountNow = this.field_70154_o instanceof EntityLiving && this.field_70154_o.func_70089_S() && !(this.field_70154_o instanceof LOTREntityNPC);
         if (this.ridingMount != isRidingMountNow) {
            this.setRidingHorse(isRidingMountNow);
            changedMounted = true;
         }
      }

      if (!this.field_70170_p.field_72995_K && !this.func_70631_g_()) {
         ItemStack weapon = this.func_71124_b(0);
         boolean carryingSpearWithBackup = weapon != null && weapon.func_77973_b() instanceof LOTRItemSpear && this.npcItemsInv.getSpearBackup() != null;
         if (this.func_70638_az() != null) {
            double d = this.func_70068_e(this.func_70638_az());
            if (d >= this.getMeleeRangeSq() && !carryingSpearWithBackup) {
               if (d < this.getMaxCombatRangeSq() && this.currentAttackMode != LOTREntityNPC.AttackMode.RANGED) {
                  this.currentAttackMode = LOTREntityNPC.AttackMode.RANGED;
                  changedAttackMode = true;
               }
            } else if (this.currentAttackMode != LOTREntityNPC.AttackMode.MELEE) {
               this.currentAttackMode = LOTREntityNPC.AttackMode.MELEE;
               changedAttackMode = true;
            }
         } else if (this.currentAttackMode != LOTREntityNPC.AttackMode.IDLE) {
            this.currentAttackMode = LOTREntityNPC.AttackMode.IDLE;
            changedAttackMode = true;
         }

         if (!this.firstUpdatedAttackMode) {
            this.firstUpdatedAttackMode = true;
            changedAttackMode = true;
         }
      }

      if (changedAttackMode || changedMounted) {
         this.onAttackModeChange(this.currentAttackMode, this.ridingMount);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.prevCombatStance = this.combatCooldown > 0;
         if (this.func_70638_az() != null) {
            this.combatCooldown = 40;
         } else if (this.combatCooldown > 0) {
            --this.combatCooldown;
         }

         this.combatStance = this.combatCooldown > 0;
         if (this.combatStance != this.prevCombatStance) {
            int x = MathHelper.func_76128_c(this.field_70165_t) >> 4;
            int z = MathHelper.func_76128_c(this.field_70161_v) >> 4;
            PlayerManager playermanager = ((WorldServer)this.field_70170_p).func_73040_p();
            List players = this.field_70170_p.field_73010_i;
            Iterator var7 = players.iterator();

            while(var7.hasNext()) {
               Object obj = var7.next();
               EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
               if (playermanager.func_72694_a(entityplayer, x, z)) {
                  this.sendCombatStance(entityplayer);
               }
            }
         }
      }

   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
   }

   public void refreshCurrentAttackMode() {
      this.onAttackModeChange(this.currentAttackMode, this.ridingMount);
   }

   protected LOTREntityNPC.AttackMode getCurrentAttackMode() {
      return this.currentAttackMode;
   }

   public double getMeleeRange() {
      double d = 4.0D + (double)(this.field_70130_N * this.field_70130_N);
      if (this.ridingMount) {
         d *= (double)MOUNT_RANGE_BONUS;
      }

      return d;
   }

   public final double getMeleeRangeSq() {
      double d = this.getMeleeRange();
      return d * d;
   }

   public final double getMaxCombatRange() {
      double d = this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
      return d * 0.95D;
   }

   public final double getMaxCombatRangeSq() {
      double d = this.getMaxCombatRange();
      return d * d;
   }

   public final boolean isAimingRanged() {
      ItemStack itemstack = this.func_70694_bm();
      if (itemstack != null) {
         Item item = itemstack.func_77973_b();
         if (!(item instanceof LOTRItemSpear) && !(item instanceof LOTRItemTrident) && itemstack.func_77975_n() == EnumAction.bow) {
            EntityLivingBase target = this.func_70638_az();
            return target != null && this.func_70068_e(target) < this.getMaxCombatRangeSq();
         }
      }

      return false;
   }

   private void sendCombatStance(EntityPlayerMP entityplayer) {
      LOTRPacketNPCCombatStance packet = new LOTRPacketNPCCombatStance(this.func_145782_y(), this.combatStance);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public void sendIsEatingToWatchers() {
      int x = MathHelper.func_76128_c(this.field_70165_t) >> 4;
      int z = MathHelper.func_76128_c(this.field_70161_v) >> 4;
      PlayerManager playermanager = ((WorldServer)this.field_70170_p).func_73040_p();
      List players = this.field_70170_p.field_73010_i;
      Iterator var5 = players.iterator();

      while(var5.hasNext()) {
         Object obj = var5.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         if (playermanager.func_72694_a(entityplayer, x, z)) {
            this.sendIsEatingPacket(entityplayer);
         }
      }

   }

   private void sendIsEatingPacket(EntityPlayerMP entityplayer) {
      LOTRPacketNPCIsEating packet = new LOTRPacketNPCIsEating(this.func_145782_y(), this.npcItemsInv.getIsEating());
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   protected void func_70069_a(float f) {
      if (this.bossInfo != null) {
         f = this.bossInfo.onFall(f);
      }

      super.func_70069_a(f);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      this.familyInfo.writeToNBT(nbt);
      this.questInfo.writeToNBT(nbt);
      this.hiredNPCInfo.writeToNBT(nbt);
      this.traderNPCInfo.writeToNBT(nbt);
      if (this.travellingTraderInfo != null) {
         this.travellingTraderInfo.writeToNBT(nbt);
      }

      if (this.bossInfo != null) {
         this.bossInfo.writeToNBT(nbt);
      }

      this.npcItemsInv.writeToNBT(nbt);
      this.hiredReplacedInv.writeToNBT(nbt);
      nbt.func_74768_a("NPCHomeX", this.func_110172_bL().field_71574_a);
      nbt.func_74768_a("NPCHomeY", this.func_110172_bL().field_71572_b);
      nbt.func_74768_a("NPCHomeZ", this.func_110172_bL().field_71573_c);
      nbt.func_74768_a("NPCHomeRadius", (int)this.func_110174_bM());
      nbt.func_74757_a("NPCPersistent", this.isNPCPersistent);
      if (this.npcLocationName != null) {
         nbt.func_74778_a("NPCLocationName", this.npcLocationName);
      }

      nbt.func_74757_a("SpecificLocationName", this.hasSpecificLocationName);
      nbt.func_74757_a("HurtOnlyByPlates", this.hurtOnlyByPlates);
      nbt.func_74757_a("RidingHorse", this.ridingMount);
      nbt.func_74757_a("NPCPassive", this.isPassive);
      nbt.func_74757_a("TraderEscort", this.isTraderEscort);
      nbt.func_74757_a("TraderShouldRespawn", this.shouldTraderRespawn);
      if (!this.killBonusFactions.isEmpty()) {
         NBTTagList bonusTags = new NBTTagList();
         Iterator var3 = this.killBonusFactions.iterator();

         while(var3.hasNext()) {
            LOTRFaction f = (LOTRFaction)var3.next();
            String fName = f.codeName();
            bonusTags.func_74742_a(new NBTTagString(fName));
         }

         nbt.func_74782_a("BonusFactions", bonusTags);
      }

      if (this.invasionID != null) {
         nbt.func_74778_a("InvasionID", this.invasionID.toString());
      }

      nbt.func_74757_a("SetInitHome", this.setInitialHome);
      nbt.func_74768_a("InitHomeX", this.initHomeX);
      nbt.func_74768_a("InitHomeY", this.initHomeY);
      nbt.func_74768_a("InitHomeZ", this.initHomeZ);
      nbt.func_74768_a("InitHomeR", this.initHomeRange);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.loadingFromNBT = true;
      super.func_70037_a(nbt);
      this.familyInfo.readFromNBT(nbt);
      this.questInfo.readFromNBT(nbt);
      this.hiredNPCInfo.readFromNBT(nbt);
      this.traderNPCInfo.readFromNBT(nbt);
      if (this.travellingTraderInfo != null) {
         this.travellingTraderInfo.readFromNBT(nbt);
      }

      if (this.bossInfo != null) {
         this.bossInfo.readFromNBT(nbt);
      }

      this.npcItemsInv.readFromNBT(nbt);
      this.hiredReplacedInv.readFromNBT(nbt);
      int i;
      if (nbt.func_74764_b("NPCHomeRadius")) {
         int x = nbt.func_74762_e("NPCHomeX");
         i = nbt.func_74762_e("NPCHomeY");
         int z = nbt.func_74762_e("NPCHomeZ");
         int r = nbt.func_74762_e("NPCHomeRadius");
         this.func_110171_b(x, i, z, r);
      }

      if (nbt.func_74764_b("NPCPersistent")) {
         this.isNPCPersistent = nbt.func_74767_n("NPCPersistent");
      }

      if (nbt.func_74764_b("NPCLocationName")) {
         this.npcLocationName = nbt.func_74779_i("NPCLocationName");
      }

      this.hasSpecificLocationName = nbt.func_74767_n("SpecificLocationName");
      this.hurtOnlyByPlates = nbt.func_74767_n("HurtOnlyByPlates");
      this.ridingMount = nbt.func_74767_n("RidingHorse");
      this.isPassive = nbt.func_74767_n("NPCPassive");
      this.isTraderEscort = nbt.func_74767_n("TraderEscort");
      if (nbt.func_74764_b("TraderShouldRespawn")) {
         this.shouldTraderRespawn = nbt.func_74767_n("TraderShouldRespawn");
      } else if (!(this instanceof LOTRTradeable) && !(this instanceof LOTRUnitTradeable)) {
         this.shouldTraderRespawn = false;
      } else if (this instanceof LOTRTravellingTrader) {
         this.shouldTraderRespawn = false;
      } else {
         this.shouldTraderRespawn = this.isNPCPersistent;
      }

      if (nbt.func_74764_b("BonusFactions")) {
         NBTTagList bonusTags = nbt.func_150295_c("BonusFactions", 8);

         for(i = 0; i < bonusTags.func_74745_c(); ++i) {
            String fName = bonusTags.func_150307_f(i);
            LOTRFaction f = LOTRFaction.forName(fName);
            if (f != null) {
               this.killBonusFactions.add(f);
            }
         }
      }

      if (nbt.func_74764_b("InvasionID")) {
         String invID = nbt.func_74779_i("InvasionID");

         try {
            this.invasionID = UUID.fromString(invID);
         } catch (IllegalArgumentException var6) {
            FMLLog.warning("LOTR: Error loading NPC - %s is not a valid invasion UUID", new Object[]{invID});
            var6.printStackTrace();
         }
      }

      this.setInitialHome = nbt.func_74767_n("SetInitHome");
      this.initHomeX = nbt.func_74762_e("InitHomeX");
      this.initHomeY = nbt.func_74762_e("InitHomeY");
      this.initHomeZ = nbt.func_74762_e("InitHomeZ");
      this.initHomeRange = nbt.func_74762_e("InitHomeR");
      this.loadingFromNBT = false;
   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      int id = LOTREntities.getEntityID(this);
      return LOTREntities.spawnEggs.containsKey(id) ? new ItemStack(LOTRMod.spawnEgg, 1, id) : null;
   }

   public boolean func_70652_k(Entity entity) {
      float damage = (float)this.func_110148_a(npcAttackDamage).func_111126_e();
      float weaponDamage = 0.0F;
      ItemStack weapon = this.func_71124_b(0);
      if (weapon != null) {
         weaponDamage = LOTRWeaponStats.getMeleeDamageBonus(weapon) * 0.75F;
      }

      if (weaponDamage > 0.0F) {
         damage = weaponDamage;
      }

      damage += (float)this.func_110148_a(npcAttackDamageExtra).func_111126_e();
      if (this.isDrunkard()) {
         damage += (float)this.func_110148_a(npcAttackDamageDrunk).func_111126_e();
      }

      damage += (float)this.nearbyBannerFactor * 0.5F;
      int knockbackModifier = 0;
      if (entity instanceof EntityLivingBase) {
         damage += EnchantmentHelper.func_77512_a(this, (EntityLivingBase)entity);
         knockbackModifier += EnchantmentHelper.func_77507_b(this, (EntityLivingBase)entity);
      }

      boolean flag = entity.func_70097_a(DamageSource.func_76358_a(this), damage);
      if (flag) {
         int fireAspectModifier;
         if (weapon != null && entity instanceof EntityLivingBase) {
            fireAspectModifier = weapon.func_77960_j();
            weapon.func_77973_b().func_77644_a(weapon, (EntityLivingBase)entity, this);
            weapon.func_77964_b(fireAspectModifier);
         }

         if (knockbackModifier > 0) {
            entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * (float)knockbackModifier * 0.5F), 0.1D, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * (float)knockbackModifier * 0.5F));
            this.field_70159_w *= 0.6D;
            this.field_70179_y *= 0.6D;
         }

         fireAspectModifier = EnchantmentHelper.func_90036_a(this);
         if (fireAspectModifier > 0) {
            entity.func_70015_d(fireAspectModifier * 4);
         }

         if (entity instanceof EntityLivingBase) {
            EnchantmentHelper.func_151384_a((EntityLivingBase)entity, this);
         }

         EnchantmentHelper.func_151385_b(this, entity);
      }

      return flag;
   }

   public void func_82196_d(EntityLivingBase target, float f) {
      this.npcArrowAttack(target, f);
   }

   protected void npcArrowAttack(EntityLivingBase target, float f) {
      ItemStack heldItem = this.func_70694_bm();
      float str = 1.3F + this.func_70032_d(target) / 80.0F;
      str *= LOTRItemBow.getLaunchSpeedFactor(heldItem);
      float accuracy = (float)this.func_110148_a(npcRangedAccuracy).func_111126_e();
      float poisonChance = this.getPoisonedArrowChance();
      Object arrow;
      if (this.field_70146_Z.nextFloat() < poisonChance) {
         arrow = new LOTREntityArrowPoisoned(this.field_70170_p, this, target, str, accuracy);
      } else {
         arrow = new EntityArrow(this.field_70170_p, this, target, str, accuracy);
      }

      if (heldItem != null) {
         LOTRItemBow.applyBowModifiers((EntityArrow)arrow, heldItem);
      }

      this.func_85030_a("random.bow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d((Entity)arrow);
   }

   protected void npcCrossbowAttack(EntityLivingBase target, float f) {
      ItemStack heldItem = this.func_70694_bm();
      float str = 1.0F + this.func_70032_d(target) / 16.0F * 0.015F;
      str *= LOTRItemCrossbow.getCrossbowLaunchSpeedFactor(heldItem);
      boolean poison = this.field_70146_Z.nextFloat() < this.getPoisonedArrowChance();
      ItemStack boltItem = poison ? new ItemStack(LOTRMod.crossbowBoltPoisoned) : new ItemStack(LOTRMod.crossbowBolt);
      LOTREntityCrossbowBolt bolt = new LOTREntityCrossbowBolt(this.field_70170_p, this, target, boltItem, str, 1.0F);
      if (heldItem != null) {
         LOTRItemCrossbow.applyCrossbowModifiers(bolt, heldItem);
      }

      this.func_85030_a("lotr:item.crossbow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(bolt);
   }

   protected float getPoisonedArrowChance() {
      return 0.0F;
   }

   public void func_70074_a(EntityLivingBase entity) {
      super.func_70074_a(entity);
      this.hiredNPCInfo.onKillEntity(entity);
      if (this.lootsExtraCoins() && !this.field_70170_p.field_72995_K && entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).canDropRares() && this.field_70146_Z.nextInt(2) == 0) {
         int coins = this.getRandomCoinDropAmount();
         coins = (int)((float)coins * MathHelper.func_151240_a(this.field_70146_Z, 1.0F, 3.0F));
         if (coins > 0) {
            entity.func_145779_a(LOTRMod.silverCoin, coins);
         }
      }

   }

   public boolean lootsExtraCoins() {
      return false;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (this.field_70153_n != null && damagesource.func_76346_g() == this.field_70153_n) {
         return false;
      } else {
         if (this.nearbyBannerFactor > 0) {
            int i = 12 - this.nearbyBannerFactor;
            float f1 = f * (float)i;
            f = f1 / 12.0F;
         }

         boolean flag = super.func_70097_a(damagesource, f);
         if (flag && damagesource.func_76346_g() instanceof LOTREntityNPC) {
            LOTREntityNPC attacker = (LOTREntityNPC)damagesource.func_76346_g();
            if (attacker.hiredNPCInfo.isActive && attacker.hiredNPCInfo.getHiringPlayer() != null) {
               this.field_70718_bc = 100;
               this.field_70717_bb = null;
            }
         }

         if (flag && !this.field_70170_p.field_72995_K && this.hurtOnlyByPlates) {
            this.hurtOnlyByPlates = damagesource.func_76364_f() instanceof LOTREntityPlate;
         }

         if (flag && !this.field_70170_p.field_72995_K && this.isInvasionSpawned() && damagesource.func_76346_g() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
            LOTREntityInvasionSpawner invasion = LOTREntityInvasionSpawner.locateInvasionNearby(this, this.invasionID);
            if (invasion != null) {
               invasion.setWatchingInvasion((EntityPlayerMP)entityplayer, true);
            }
         }

         return flag;
      }
   }

   protected void func_70665_d(DamageSource damagesource, float f) {
      super.func_70665_d(damagesource, f);
      if (this.bossInfo != null) {
         this.bossInfo.onHurt(damagesource, f);
      }

   }

   public final boolean func_98052_bS() {
      return false;
   }

   protected void func_70628_a(boolean flag, int i) {
      this.hiredReplacedInv.dropAllReplacedItems();
      this.dropNPCEquipment(flag, i);
      int modChance;
      int pickChance;
      if (flag && this.canDropRares()) {
         modChance = 8 - i * 2;
         modChance = Math.max(modChance, 1);
         if (this.field_70146_Z.nextInt(modChance) == 0) {
            pickChance = this.getRandomCoinDropAmount();
            pickChance *= MathHelper.func_76136_a(this.field_70146_Z, 1, i + 1);
            this.func_145779_a(LOTRMod.silverCoin, pickChance);
         }

         pickChance = 50 - i * 5;
         pickChance = Math.max(pickChance, 1);
         if (this.field_70146_Z.nextInt(pickChance) == 0) {
            this.dropChestContents(LOTRChestContents.RARE_DROPS, 1, 1);
         }
      }

      ItemStack keypart;
      if (flag && this.canDropRares()) {
         int modChance = 60;
         modChance = modChance - i * 5;
         modChance = Math.max(modChance, 1);
         if (this.field_70146_Z.nextInt(modChance) == 0) {
            keypart = LOTRItemModifierTemplate.getRandomCommonTemplate(this.field_70146_Z);
            this.func_70099_a(keypart, 0.0F);
         }
      }

      if (this.getFaction() == LOTRFaction.UTUMNO && LOTRDimension.getCurrentDimensionWithFallback(this.field_70170_p) == LOTRDimension.UTUMNO) {
         LOTRUtumnoLevel level = LOTRUtumnoLevel.forY((int)this.field_70163_u);
         int l;
         if (this.field_70146_Z.nextInt(12) == 0) {
            if (level == LOTRUtumnoLevel.ICE) {
               keypart = new ItemStack(LOTRMod.utumnoKey);
               l = this.field_70146_Z.nextInt(3);
               if (l == 0) {
                  keypart.func_77964_b(2);
               } else if (l == 1) {
                  keypart.func_77964_b(3);
               } else if (l == 2) {
                  keypart.func_77964_b(4);
               }

               this.func_70099_a(keypart, 0.0F);
            } else if (level == LOTRUtumnoLevel.OBSIDIAN) {
               keypart = new ItemStack(LOTRMod.utumnoKey);
               l = this.field_70146_Z.nextInt(3);
               if (l == 0) {
                  keypart.func_77964_b(5);
               } else if (l == 1) {
                  keypart.func_77964_b(6);
               } else if (l == 2) {
                  keypart.func_77964_b(7);
               }

               this.func_70099_a(keypart, 0.0F);
            }
         }

         byte pickChance;
         if (level == LOTRUtumnoLevel.ICE && this.isChilly) {
            pickChance = 30;
            pickChance = pickChance - i * 3;
            pickChance = Math.max(pickChance, 1);
            if (this.field_70146_Z.nextInt(pickChance) == 0) {
               l = 1;
               if (i > 0) {
                  float x;
                  for(x = MathHelper.func_151240_a(this.field_70146_Z, 0.0F, (float)i * 0.667F); x > 1.0F; ++l) {
                     --x;
                  }

                  if (this.field_70146_Z.nextFloat() < x) {
                     ++l;
                  }
               }

               for(int l = 0; l < l; ++l) {
                  this.func_145779_a(LOTRMod.chilling, 1);
               }
            }
         }

         if (level == LOTRUtumnoLevel.FIRE && this.canDropRares()) {
            pickChance = 100;
            pickChance = pickChance - i * 20;
            pickChance = Math.max(pickChance, 1);
            if (this.field_70146_Z.nextInt(pickChance) == 0) {
               this.func_70099_a(new ItemStack(LOTRMod.utumnoPickaxe), 0.0F);
            }
         }

         if (this.field_70146_Z.nextInt(20) == 0) {
            this.func_70099_a(new ItemStack(LOTRMod.mithrilNugget), 0.0F);
         }
      }

   }

   protected int getRandomCoinDropAmount() {
      return 1 + (int)Math.round(Math.pow(1.0D + Math.abs(this.field_70146_Z.nextGaussian()), 3.0D) * 0.25D);
   }

   public void dropNPCEquipment(boolean flag, int i) {
      if (flag) {
         int equipmentCount = 0;

         int j;
         for(j = 0; j < 5; ++j) {
            if (this.func_71124_b(j) != null) {
               ++equipmentCount;
            }
         }

         if (equipmentCount > 0) {
            for(j = 0; j < 5; ++j) {
               ItemStack equipmentDrop = this.func_71124_b(j);
               if (equipmentDrop != null) {
                  boolean dropGuaranteed = this.field_82174_bp[j] >= 1.0F;
                  int dropDamage;
                  if (!dropGuaranteed) {
                     dropDamage = 20 * equipmentCount - i * 4 * equipmentCount;
                     dropDamage = Math.max(dropDamage, 1);
                     if (this.field_70146_Z.nextInt(dropDamage) != 0) {
                        continue;
                     }
                  }

                  if (!dropGuaranteed) {
                     dropDamage = MathHelper.func_76128_c((double)((float)equipmentDrop.func_77973_b().func_77612_l() * (0.5F + this.field_70146_Z.nextFloat() * 0.25F)));
                     equipmentDrop.func_77964_b(dropDamage);
                  }

                  this.func_70099_a(equipmentDrop, 0.0F);
                  this.func_70062_b(j, (ItemStack)null);
               }
            }
         }
      }

   }

   protected void dropChestContents(LOTRChestContents itemPool, int min, int max) {
      IInventory drops = new InventoryBasic("drops", false, max * 5);
      LOTRChestContents.fillInventory(drops, this.field_70146_Z, itemPool, MathHelper.func_76136_a(this.field_70146_Z, min, max), true);

      for(int i = 0; i < drops.func_70302_i_(); ++i) {
         ItemStack item = drops.func_70301_a(i);
         if (item != null) {
            this.func_70099_a(item, 0.0F);
         }
      }

   }

   protected void dropNPCArrows(int i) {
      this.dropNPCAmmo(Items.field_151032_g, i);
   }

   protected void dropNPCCrossbowBolts(int i) {
      this.dropNPCAmmo(LOTRMod.crossbowBolt, i);
   }

   protected void dropNPCAmmo(Item item, int i) {
      int ammo = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < ammo; ++l) {
         this.func_145779_a(item, 1);
      }

   }

   public final void func_82160_b(boolean flag, int i) {
   }

   public final EntityItem func_70099_a(ItemStack item, float offset) {
      return this.npcDropItem(item, offset, true, true);
   }

   public final EntityItem npcDropItem(ItemStack item, float offset, boolean enpouch, boolean applyOwnership) {
      if (applyOwnership && item != null && item.func_77973_b() != null && item.func_77976_d() == 1) {
         LOTRItemOwnership.addPreviousOwner(item, this.func_70005_c_());
      }

      if (enpouch && this.enpouchNPCDrops && item != null) {
         this.enpouchedDrops.add(item);
         return null;
      } else {
         return super.func_70099_a(item, offset);
      }
   }

   public void func_70645_a(DamageSource damagesource) {
      this.enpouchNPCDrops = true;
      this.hiredNPCInfo.onDeath(damagesource);
      if (this.travellingTraderInfo != null) {
         this.travellingTraderInfo.onDeath();
      }

      if (this.bossInfo != null) {
         this.bossInfo.onDeath(damagesource);
      }

      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && this.field_70718_bc > 0 && this.canDropRares() && LOTRMod.canDropLoot(this.field_70170_p) && this.field_70146_Z.nextInt(60) == 0) {
         ItemStack pouch = this.createNPCPouchDrop();
         this.fillPouchFromListAndRetainUnfilled(pouch, this.enpouchedDrops);
         this.enpouchNPCDrops = false;
         this.func_70099_a(pouch, 0.0F);
      }

      this.enpouchNPCDrops = false;
      this.dropItemList(this.enpouchedDrops, false);
      EntityPlayer entityplayer;
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer) {
         entityplayer = (EntityPlayer)damagesource.func_76346_g();
         if (this.hurtOnlyByPlates && damagesource.func_76364_f() instanceof LOTREntityPlate) {
            if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) < 0.0F) {
            }

            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killUsingOnlyPlates);
         }

         if (damagesource.func_76364_f() instanceof LOTREntityPebble) {
            LOTREntityPebble pebble = (LOTREntityPebble)damagesource.func_76364_f();
            if (pebble.isSling()) {
               float size = this.field_70130_N * this.field_70130_N * this.field_70131_O;
               if (size > 5.0F) {
                  float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction());
                  if (alignment < 0.0F) {
                     LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killLargeMobWithSlingshot);
                  }
               }
            }
         }

         if (this instanceof LOTREntityOrc) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killOrc);
         }

         if (this instanceof LOTREntityWarg) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killWarg);
         }

         if (this.getKillAchievement() != null) {
            LOTRLevelData.getData(entityplayer).addAchievement(this.getKillAchievement());
         }
      }

      if (!this.field_70170_p.field_72995_K && (this instanceof LOTRTradeable || this instanceof LOTRUnitTradeable) && this.shouldTraderRespawn) {
         LOTREntityTraderRespawn entity = new LOTREntityTraderRespawn(this.field_70170_p);
         entity.func_70012_b(this.field_70165_t, this.field_70121_D.field_72338_b + (double)(this.field_70131_O / 2.0F), this.field_70161_v, 0.0F, 0.0F);
         entity.copyTraderDataFrom(this);
         this.field_70170_p.func_72838_d(entity);
         entity.onSpawn();
      }

      this.questInfo.onDeath();
      if (!this.field_70170_p.field_72995_K && this.isInvasionSpawned()) {
         entityplayer = LOTRMod.getDamagingPlayerIncludingUnits(damagesource);
         if (entityplayer != null) {
            LOTREntityInvasionSpawner invasion = LOTREntityInvasionSpawner.locateInvasionNearby(this, this.invasionID);
            if (invasion != null) {
               invasion.addPlayerKill(entityplayer);
               if (damagesource.func_76346_g() == entityplayer) {
                  invasion.setWatchingInvasion((EntityPlayerMP)entityplayer, true);
               }
            }
         }
      }

   }

   public ItemStack createNPCPouchDrop() {
      ItemStack pouch = new ItemStack(LOTRMod.pouch, 1, LOTRItemPouch.getRandomPouchSize(this.field_70146_Z));
      if (this.field_70146_Z.nextBoolean()) {
         LOTRFaction faction = this.getFaction();
         if (faction != null) {
            LOTRItemPouch.setPouchColor(pouch, faction.getFactionColor());
         }
      }

      return pouch;
   }

   public void fillPouchFromListAndRetainUnfilled(ItemStack pouch, List items) {
      ArrayList pouchContents = new ArrayList();

      while(!items.isEmpty()) {
         pouchContents.add(items.remove(0));
         if (pouchContents.size() >= LOTRItemPouch.getCapacity(pouch)) {
            break;
         }
      }

      Iterator var4 = pouchContents.iterator();

      while(var4.hasNext()) {
         ItemStack itemstack = (ItemStack)var4.next();
         if (!LOTRItemPouch.tryAddItemToPouch(pouch, itemstack, false)) {
            items.add(itemstack);
         }
      }

   }

   public void dropItemList(List items) {
      this.dropItemList(items, true);
   }

   public void dropItemList(List items, boolean applyOwnership) {
      if (!items.isEmpty()) {
         Iterator var3 = items.iterator();

         while(var3.hasNext()) {
            ItemStack item = (ItemStack)var3.next();
            this.npcDropItem(item, 0.0F, true, applyOwnership);
         }
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return null;
   }

   public void func_70106_y() {
      super.func_70106_y();
      if (this.field_70725_aQ == 0 && this.field_70154_o != null) {
         this.field_70154_o.func_70106_y();
      }

   }

   public boolean canDropRares() {
      return !this.hiredNPCInfo.isActive;
   }

   public float getAlignmentBonus() {
      return 0.0F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 4 + this.field_70146_Z.nextInt(3);
   }

   public float func_70783_a(int i, int j, int k) {
      if (this.liftSpawnRestrictions) {
         return 1.0F;
      } else {
         if (!this.isConquestSpawning || !this.conquestSpawnIgnoresDarkness()) {
            if (this.spawnsInDarkness) {
               BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
               if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
                  return 1.0F;
               }
            }

            if (this.spawnsInDarkness) {
               return 0.5F - this.field_70170_p.func_72801_o(i, j, k);
            }
         }

         return 0.0F;
      }
   }

   private boolean isValidLightLevelForDarkSpawn() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      if (this.spawnsInDarkness) {
         BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
         if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
            return true;
         }
      }

      if (this.field_70170_p.func_72972_b(EnumSkyBlock.Sky, i, j, k) > this.field_70146_Z.nextInt(32)) {
         return false;
      } else {
         int l = this.field_70170_p.func_72957_l(i, j, k);
         if (this.field_70170_p.func_72911_I()) {
            int i1 = this.field_70170_p.field_73008_k;
            this.field_70170_p.field_73008_k = 10;
            l = this.field_70170_p.func_72957_l(i, j, k);
            this.field_70170_p.field_73008_k = i1;
         }

         return l <= this.field_70146_Z.nextInt(8);
      }
   }

   public void setConquestSpawning(boolean flag) {
      this.isConquestSpawning = flag;
   }

   protected boolean conquestSpawnIgnoresDarkness() {
      return true;
   }

   public boolean func_70601_bi() {
      if ((!this.spawnsInDarkness || this.liftSpawnRestrictions || this.isConquestSpawning && this.conquestSpawnIgnoresDarkness() || this.isValidLightLevelForDarkSpawn()) && super.func_70601_bi()) {
         if (!this.liftBannerRestrictions) {
            if (LOTRBannerProtection.isProtected(this.field_70170_p, this, LOTRBannerProtection.forNPC(this), false)) {
               return false;
            }

            if (!this.isConquestSpawning && LOTREntityNPCRespawner.isSpawnBlocked(this)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public final int getSpawnCountValue() {
      if (!this.isNPCPersistent && !this.shouldTraderRespawn && !this.hiredNPCInfo.isActive) {
         int multiplier = 1;
         BiomeGenBase biome = this.field_70170_p.func_72807_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70161_v));
         if (biome instanceof LOTRBiome) {
            multiplier = ((LOTRBiome)biome).spawnCountMultiplier();
         }

         return multiplier;
      } else {
         return 0;
      }
   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K && this.canNPCTalk()) {
         if (this.questInfo.interact(entityplayer)) {
            return true;
         }

         if (this.func_70638_az() == null && this.speakTo(entityplayer)) {
            return true;
         }
      }

      return super.func_70085_c(entityplayer);
   }

   public void sendSpeechBank(EntityPlayer entityplayer, String speechBank) {
      this.sendSpeechBank(entityplayer, speechBank, (LOTRMiniQuest)null);
   }

   public void sendSpeechBank(EntityPlayer entityplayer, String speechBank, LOTRMiniQuest miniquest) {
      String location = null;
      String objective = null;
      if (this.npcLocationName != null) {
         if (!this.hasSpecificLocationName) {
            location = StatCollector.func_74837_a(this.npcLocationName, new Object[]{this.getNPCName()});
         } else {
            location = this.npcLocationName;
         }
      }

      if (miniquest != null) {
         objective = miniquest.getProgressedObjectiveInSpeech();
      }

      this.sendSpeechBank(entityplayer, speechBank, location, objective);
   }

   public void sendSpeechBank(EntityPlayer entityplayer, String speechBank, String location, String objective) {
      LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, speechBank, entityplayer, location, objective));
      this.markNPCSpoken();
   }

   public void sendSpeechBankLine(EntityPlayer entityplayer, String speechBank, int i) {
      LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getSpeechLineForPlayer(this, speechBank, i, entityplayer, (String)null, (String)null));
      this.markNPCSpoken();
   }

   public boolean isFriendlyAndAligned(EntityPlayer entityplayer) {
      return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0F && this.isFriendly(entityplayer);
   }

   public boolean isFriendly(EntityPlayer entityplayer) {
      return this.func_70638_az() != entityplayer && this.field_70717_bb != entityplayer;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return null;
   }

   public boolean speakTo(EntityPlayer entityplayer) {
      String speechBank = this.getSpeechBank(entityplayer);
      if (this.field_70146_Z.nextInt(8) == 0) {
         if (LOTRMod.isChristmas()) {
            speechBank = "special/christmas";
         } else if (LOTRMod.isNewYearsDay()) {
            speechBank = "special/newYear";
         } else if (LOTRMod.isAprilFools()) {
            speechBank = "special/aprilFool";
         } else if (LOTRMod.isHalloween()) {
            speechBank = "special/halloween";
         }
      }

      if (this.field_70146_Z.nextInt(10000) == 0) {
         speechBank = "special/smilebc";
      }

      if (speechBank != null) {
         this.sendSpeechBank(entityplayer, speechBank);
         if (this.getTalkAchievement() != null) {
            LOTRLevelData.getData(entityplayer).addAchievement(this.getTalkAchievement());
         }

         return true;
      } else {
         return false;
      }
   }

   protected LOTRAchievement getTalkAchievement() {
      return null;
   }

   public LOTRMiniQuest createMiniQuest() {
      return null;
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return null;
   }

   public int getMiniquestColor() {
      return this.getFaction().getFactionColor();
   }

   public void onArtificalSpawn() {
   }

   public boolean isDrunkard() {
      return this.familyInfo.isDrunk();
   }

   public boolean canGetDrunk() {
      if (this.func_70631_g_()) {
         return false;
      } else {
         return !this.isTrader() && !this.isTraderEscort && !this.hiredNPCInfo.isActive;
      }
   }

   public float getDrunkenSpeechFactor() {
      return this.field_70146_Z.nextInt(3) == 0 ? MathHelper.func_151240_a(this.field_70146_Z, 0.0F, 0.3F) : 0.0F;
   }

   public boolean shouldRenderNPCHair() {
      return true;
   }

   public boolean shouldRenderNPCChest() {
      return !this.familyInfo.isMale() && !this.func_70631_g_() && this.func_71124_b(3) == null;
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return true;
   }

   public void setSpecificLocationName(String name) {
      this.npcLocationName = name;
      this.hasSpecificLocationName = true;
   }

   public boolean getHasSpecificLocationName() {
      return this.hasSpecificLocationName;
   }

   public void setShouldTraderRespawn(boolean flag) {
      this.shouldTraderRespawn = flag;
   }

   public void setPersistentAndTraderShouldRespawn() {
      this.isNPCPersistent = true;
      this.setShouldTraderRespawn(true);
   }

   public boolean shouldTraderRespawn() {
      return this.shouldTraderRespawn;
   }

   private void updateNearbyBanners() {
      if (this.getFaction() != LOTRFaction.UNALIGNED && !(this instanceof LOTRBannerBearer)) {
         double range = 16.0D;
         List bannerBearers = this.field_70170_p.func_82733_a(LOTRBannerBearer.class, this.field_70121_D.func_72314_b(range, range, range), new IEntitySelector() {
            public boolean func_82704_a(Entity entity) {
               EntityLivingBase living = (EntityLivingBase)entity;
               return living != LOTREntityNPC.this && living.func_70089_S() && LOTRMod.getNPCFaction(living) == LOTREntityNPC.this.getFaction();
            }
         });
         this.nearbyBannerFactor = Math.min(bannerBearers.size(), 5);
      } else {
         this.nearbyBannerFactor = 0;
      }

   }

   public final ItemStack func_71124_b(int i) {
      if (this.field_70170_p.field_72995_K) {
         if (!this.initFestiveItems) {
            this.festiveRand.setSeed((long)this.func_145782_y() * 341873128712L);
            if (LOTRMod.isHalloween()) {
               if (this.festiveRand.nextInt(3) == 0) {
                  this.festiveItems[4] = this.festiveRand.nextInt(10) == 0 ? new ItemStack(Blocks.field_150428_aP) : new ItemStack(Blocks.field_150423_aK);
               }
            } else if (LOTRMod.isChristmas() && this.festiveRand.nextInt(3) == 0) {
               ItemStack hat;
               if (this.field_70146_Z.nextBoolean()) {
                  hat = new ItemStack(LOTRMod.leatherHat);
                  LOTRItemLeatherHat.setHatColor(hat, 13378587);
                  LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
                  this.festiveItems[4] = hat;
               } else {
                  hat = new ItemStack(LOTRMod.partyHat);
                  float hue = this.field_70146_Z.nextFloat();
                  LOTRItemPartyHat.setHatColor(hat, Color.HSBtoRGB(hue, 1.0F, 1.0F));
               }

               this.festiveItems[4] = hat;
            }

            this.initFestiveItems = true;
         }

         if (this.festiveItems[i] != null) {
            return this.festiveItems[i];
         }
      }

      return super.func_71124_b(i);
   }

   public final ItemStack func_130225_q(int i) {
      return this.func_71124_b(i + 1);
   }

   public boolean func_110164_bC() {
      return false;
   }

   public void func_94058_c(String name) {
      if (this.canRenameNPC() || this.loadingFromNBT) {
         super.func_94058_c(name);
      }

   }

   public boolean canRenameNPC() {
      return false;
   }

   public void func_110163_bv() {
   }

   public boolean shouldDismountInWater(Entity rider) {
      return false;
   }

   public void spawnHearts() {
      if (!this.field_70170_p.field_72995_K) {
         LOTRPacketNPCFX packet = new LOTRPacketNPCFX(this.func_145782_y(), LOTRPacketNPCFX.FXType.HEARTS);
         LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(this, 32.0D));
      } else {
         for(int i = 0; i < 8; ++i) {
            double d = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_72869_a("heart", this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d, d1, d2);
         }
      }

   }

   public void spawnSmokes() {
      if (!this.field_70170_p.field_72995_K) {
         LOTRPacketNPCFX packet = new LOTRPacketNPCFX(this.func_145782_y(), LOTRPacketNPCFX.FXType.SMOKE);
         LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(this, 32.0D));
      } else {
         for(int i = 0; i < 8; ++i) {
            double d = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_72869_a("smoke", this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + 0.5D + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d, d1, d2);
         }
      }

   }

   public void spawnFoodParticles() {
      if (this.func_70694_bm() != null) {
         if (!this.field_70170_p.field_72995_K) {
            LOTRPacketNPCFX packet = new LOTRPacketNPCFX(this.func_145782_y(), LOTRPacketNPCFX.FXType.EATING);
            LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(this, 32.0D));
         } else {
            for(int i = 0; i < 5; ++i) {
               Vec3 vec1 = Vec3.func_72443_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
               vec1.func_72440_a(-this.field_70125_A * 3.1415927F / 180.0F);
               vec1.func_72442_b(-this.field_70177_z * 3.1415927F / 180.0F);
               Vec3 vec2 = Vec3.func_72443_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.3D, (double)(-this.field_70146_Z.nextFloat()) * 0.6D - 0.3D, 0.6D);
               vec2.func_72440_a(-this.field_70125_A * 3.1415927F / 180.0F);
               vec2.func_72442_b(-this.field_70177_z * 3.1415927F / 180.0F);
               vec2 = vec2.func_72441_c(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
               this.field_70170_p.func_72869_a("iconcrack_" + Item.func_150891_b(this.func_70694_bm().func_77973_b()), vec2.field_72450_a, vec2.field_72448_b, vec2.field_72449_c, vec1.field_72450_a, vec1.field_72448_b + 0.05D, vec1.field_72449_c);
            }
         }

      }
   }

   public ItemStack getHeldItemLeft() {
      if (this instanceof LOTRBannerBearer) {
         LOTRBannerBearer bannerBearer = (LOTRBannerBearer)this;
         return new ItemStack(LOTRMod.banner, 1, bannerBearer.getBannerType().bannerID);
      } else {
         if (this.isTrader()) {
            boolean showCoin = false;
            if (this.npcShield == null) {
               showCoin = true;
            } else if (!this.clientCombatStance && this.hiredNPCInfo.getHiringPlayerUUID() == null) {
               showCoin = true;
            }

            if (showCoin) {
               return new ItemStack(LOTRMod.silverCoin);
            }
         }

         return null;
      }
   }

   public void playTradeSound() {
      this.func_85030_a("lotr:event.trade", 0.5F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
   }

   public boolean isInvasionSpawned() {
      return this.getInvasionID() != null;
   }

   public UUID getInvasionID() {
      return this.invasionID;
   }

   public void setInvasionID(UUID id) {
      this.invasionID = id;
   }

   protected static enum AttackMode {
      MELEE,
      RANGED,
      IDLE;
   }
}
