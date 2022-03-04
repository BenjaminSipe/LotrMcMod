package lotr.common.entity.npc;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.fac.LOTRFaction;
import lotr.common.inventory.LOTRInventoryNPC;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketHiredGui;
import lotr.common.network.LOTRPacketHiredInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRHiredNPCInfo {
   private LOTREntityNPC theEntity;
   private UUID hiringPlayerUUID;
   public boolean isActive;
   public float alignmentRequiredToCommand;
   public LOTRUnitTradeEntry.PledgeType pledgeType;
   private LOTRHiredNPCInfo.Task hiredTask;
   private boolean canMove;
   public boolean teleportAutomatically;
   public int mobKills;
   public int xp;
   public int xpLevel;
   public static final int XP_COLOR = 16733440;
   private static final float LEVEL_UP_HEALTH_GAIN = 1.0F;
   private String hiredSquadron;
   public boolean guardMode;
   public static int GUARD_RANGE_MIN = 1;
   public static int GUARD_RANGE_DEFAULT = 8;
   public static int GUARD_RANGE_MAX = 64;
   private int guardRange;
   private LOTRInventoryNPC hiredInventory;
   public boolean inCombat;
   private boolean prevInCombat;
   public boolean isGuiOpen;
   private boolean targetFromCommandSword;
   public boolean wasAttackCommanded;
   private boolean doneFirstUpdate;
   private boolean resendBasicData;

   public LOTRHiredNPCInfo(LOTREntityNPC npc) {
      this.pledgeType = LOTRUnitTradeEntry.PledgeType.NONE;
      this.hiredTask = LOTRHiredNPCInfo.Task.WARRIOR;
      this.canMove = true;
      this.teleportAutomatically = true;
      this.xp = 0;
      this.xpLevel = 1;
      this.guardRange = GUARD_RANGE_DEFAULT;
      this.wasAttackCommanded = false;
      this.doneFirstUpdate = false;
      this.resendBasicData = true;
      this.theEntity = npc;
   }

   public void hireUnit(EntityPlayer entityplayer, boolean setLocation, LOTRFaction hiringFaction, LOTRUnitTradeEntry tradeEntry, String squadron, Entity mount) {
      float alignment = tradeEntry.alignmentRequired;
      LOTRUnitTradeEntry.PledgeType pledge = tradeEntry.getPledgeType();
      LOTRHiredNPCInfo.Task task = tradeEntry.task;
      if (setLocation) {
         this.theEntity.func_70012_b(entityplayer.field_70165_t, entityplayer.field_70121_D.field_72338_b, entityplayer.field_70161_v, entityplayer.field_70177_z + 180.0F, 0.0F);
      }

      this.isActive = true;
      this.alignmentRequiredToCommand = alignment;
      this.pledgeType = pledge;
      this.setHiringPlayer(entityplayer);
      this.setTask(task);
      this.setSquadron(squadron);
      if (hiringFaction != null && hiringFaction.isPlayableAlignmentFaction()) {
         LOTRLevelData.getData(entityplayer).getFactionData(hiringFaction).addHire();
      }

      if (mount != null) {
         mount.func_70012_b(this.theEntity.field_70165_t, this.theEntity.field_70121_D.field_72338_b, this.theEntity.field_70161_v, this.theEntity.field_70177_z, 0.0F);
         if (mount instanceof LOTREntityNPC) {
            LOTREntityNPC hiredMountNPC = (LOTREntityNPC)mount;
            hiredMountNPC.hiredNPCInfo.hireUnit(entityplayer, setLocation, hiringFaction, tradeEntry, squadron, (Entity)null);
         }

         this.theEntity.func_70078_a(mount);
         if (mount instanceof LOTRNPCMount && !(mount instanceof LOTREntityNPC)) {
            this.theEntity.setRidingHorse(true);
            LOTRNPCMount hiredHorse = (LOTRNPCMount)mount;
            hiredHorse.setBelongsToNPC(true);
            LOTRMountFunctions.setNavigatorRangeFromNPC(hiredHorse, this.theEntity);
         }
      }

   }

   public void setHiringPlayer(EntityPlayer entityplayer) {
      if (entityplayer == null) {
         this.hiringPlayerUUID = null;
      } else {
         this.hiringPlayerUUID = entityplayer.func_110124_au();
      }

      this.markDirty();
   }

   public EntityPlayer getHiringPlayer() {
      return this.hiringPlayerUUID == null ? null : this.theEntity.field_70170_p.func_152378_a(this.hiringPlayerUUID);
   }

   public UUID getHiringPlayerUUID() {
      return this.hiringPlayerUUID;
   }

   public LOTRHiredNPCInfo.Task getTask() {
      return this.hiredTask;
   }

   public void setTask(LOTRHiredNPCInfo.Task t) {
      if (t != this.hiredTask) {
         this.hiredTask = t;
         this.markDirty();
      }

      if (this.hiredTask == LOTRHiredNPCInfo.Task.FARMER) {
         this.hiredInventory = new LOTRInventoryNPC("HiredInventory", this.theEntity, 4);
      }

   }

   public LOTRInventoryNPC getHiredInventory() {
      return this.hiredInventory;
   }

   private void markDirty() {
      if (!this.theEntity.field_70170_p.field_72995_K) {
         if (this.theEntity.field_70173_aa > 0) {
            this.resendBasicData = true;
         } else {
            this.sendBasicDataToAllWatchers();
         }
      }

   }

   public boolean hasHiringRequirements() {
      return this.theEntity.getHiringFaction().isPlayableAlignmentFaction() && this.alignmentRequiredToCommand >= 0.0F;
   }

   public void onUpdate() {
      if (!this.theEntity.field_70170_p.field_72995_K) {
         if (!this.doneFirstUpdate) {
            this.doneFirstUpdate = true;
         }

         if (this.resendBasicData) {
            this.sendBasicDataToAllWatchers();
            this.resendBasicData = false;
         }

         EntityPlayer hiringPlayer;
         if (this.hasHiringRequirements() && this.isActive) {
            hiringPlayer = this.getHiringPlayer();
            if (hiringPlayer != null) {
               LOTRFaction fac = this.theEntity.getHiringFaction();
               LOTRPlayerData pd = LOTRLevelData.getData(hiringPlayer);
               boolean canCommand = true;
               if (pd.getAlignment(fac) < this.alignmentRequiredToCommand) {
                  canCommand = false;
               }

               if (!this.pledgeType.canAcceptPlayer(hiringPlayer, fac)) {
                  canCommand = false;
               }

               if (!canCommand) {
                  this.dismissUnit(true);
               }
            }
         }

         this.inCombat = this.theEntity.func_70638_az() != null;
         if (this.inCombat != this.prevInCombat) {
            this.sendClientPacket(false);
         }

         this.prevInCombat = this.inCombat;
         if (this.getTask() == LOTRHiredNPCInfo.Task.WARRIOR && !this.inCombat && this.shouldFollowPlayer() && this.theEntity.func_70681_au().nextInt(4000) == 0) {
            hiringPlayer = this.getHiringPlayer();
            double range = 16.0D;
            if (hiringPlayer != null && this.theEntity.func_70068_e(hiringPlayer) < range * range) {
               String speechBank = this.theEntity.getSpeechBank(hiringPlayer);
               if (speechBank != null) {
                  this.theEntity.sendSpeechBank(hiringPlayer, speechBank);
               }
            }
         }
      }

   }

   public void dismissUnit(boolean isDesertion) {
      if (isDesertion) {
         this.getHiringPlayer().func_145747_a(new ChatComponentTranslation("lotr.hiredNPC.desert", new Object[]{this.theEntity.func_70005_c_()}));
      } else {
         this.getHiringPlayer().func_145747_a(new ChatComponentTranslation("lotr.hiredNPC.dismiss", new Object[]{this.theEntity.func_70005_c_()}));
      }

      if (this.hiredTask == LOTRHiredNPCInfo.Task.FARMER && this.hiredInventory != null) {
         this.hiredInventory.dropAllItems();
      }

      this.isActive = false;
      this.canMove = true;
      this.sendClientPacket(false);
      this.setHiringPlayer((EntityPlayer)null);
   }

   public void onDeath(DamageSource damagesource) {
      if (!this.theEntity.field_70170_p.field_72995_K && this.isActive && this.getHiringPlayer() != null) {
         EntityPlayer hiringPlayer = this.getHiringPlayer();
         if (LOTRLevelData.getData(hiringPlayer).getEnableHiredDeathMessages()) {
            hiringPlayer.func_145747_a(new ChatComponentTranslation("lotr.hiredNPC.death", new Object[]{this.theEntity.func_110142_aN().func_151521_b()}));
         }
      }

      if (!this.theEntity.field_70170_p.field_72995_K && this.hiredInventory != null) {
         this.hiredInventory.dropAllItems();
      }

   }

   public void halt() {
      this.canMove = false;
      this.theEntity.func_70624_b((EntityLivingBase)null);
      this.sendClientPacket(false);
   }

   public void ready() {
      this.canMove = true;
      this.sendClientPacket(false);
   }

   public boolean isHalted() {
      return !this.guardMode && !this.canMove;
   }

   public boolean shouldFollowPlayer() {
      return !this.guardMode && this.canMove;
   }

   public boolean getObeyHornHaltReady() {
      if (this.hiredTask != LOTRHiredNPCInfo.Task.WARRIOR) {
         return false;
      } else {
         return !this.guardMode;
      }
   }

   public boolean getObeyHornSummon() {
      if (this.hiredTask != LOTRHiredNPCInfo.Task.WARRIOR) {
         return false;
      } else {
         return !this.guardMode;
      }
   }

   public boolean getObeyCommandSword() {
      if (this.hiredTask != LOTRHiredNPCInfo.Task.WARRIOR) {
         return false;
      } else {
         return !this.guardMode;
      }
   }

   public boolean isGuardMode() {
      return this.guardMode;
   }

   public void setGuardMode(boolean flag) {
      this.guardMode = flag;
      if (flag) {
         int i = MathHelper.func_76128_c(this.theEntity.field_70165_t);
         int j = MathHelper.func_76128_c(this.theEntity.field_70163_u);
         int k = MathHelper.func_76128_c(this.theEntity.field_70161_v);
         this.theEntity.func_110171_b(i, j, k, this.guardRange);
      } else {
         this.theEntity.func_110177_bN();
      }

   }

   public int getGuardRange() {
      return this.guardRange;
   }

   public void setGuardRange(int range) {
      this.guardRange = MathHelper.func_76125_a(range, GUARD_RANGE_MIN, GUARD_RANGE_MAX);
      if (this.guardMode) {
         int i = MathHelper.func_76128_c(this.theEntity.field_70165_t);
         int j = MathHelper.func_76128_c(this.theEntity.field_70163_u);
         int k = MathHelper.func_76128_c(this.theEntity.field_70161_v);
         this.theEntity.func_110171_b(i, j, k, this.guardRange);
      }

   }

   public String getSquadron() {
      return this.hiredSquadron;
   }

   public void setSquadron(String s) {
      this.hiredSquadron = s;
      this.markDirty();
   }

   public String getStatusString() {
      String status = "";
      if (this.hiredTask == LOTRHiredNPCInfo.Task.WARRIOR) {
         if (this.inCombat) {
            status = StatCollector.func_74838_a("lotr.hiredNPC.status.combat");
         } else if (this.isHalted()) {
            status = StatCollector.func_74838_a("lotr.hiredNPC.status.halted");
         } else if (this.guardMode) {
            status = StatCollector.func_74838_a("lotr.hiredNPC.status.guard");
         } else {
            status = StatCollector.func_74838_a("lotr.hiredNPC.status.ready");
         }
      } else if (this.hiredTask == LOTRHiredNPCInfo.Task.FARMER) {
         if (this.guardMode) {
            status = StatCollector.func_74838_a("lotr.hiredNPC.status.farming");
         } else {
            status = StatCollector.func_74838_a("lotr.hiredNPC.status.following");
         }
      }

      String s = StatCollector.func_74837_a("lotr.hiredNPC.status", new Object[]{status});
      return s;
   }

   public void onSetTarget(EntityLivingBase newTarget, EntityLivingBase prevTarget) {
      if (newTarget == null || newTarget != prevTarget) {
         this.targetFromCommandSword = false;
         this.wasAttackCommanded = false;
      }

   }

   public void commandSwordAttack(EntityLivingBase target) {
      if (target != null && LOTRMod.canNPCAttackEntity(this.theEntity, target, true)) {
         this.theEntity.func_70661_as().func_75499_g();
         this.theEntity.func_70604_c(target);
         this.theEntity.func_70624_b(target);
         this.targetFromCommandSword = true;
      }

   }

   public void commandSwordCancel() {
      if (this.targetFromCommandSword) {
         this.theEntity.func_70661_as().func_75499_g();
         this.theEntity.func_70604_c((EntityLivingBase)null);
         this.theEntity.func_70624_b((EntityLivingBase)null);
         this.targetFromCommandSword = false;
      }

   }

   public void onKillEntity(EntityLivingBase target) {
      if (!this.theEntity.field_70170_p.field_72995_K && this.isActive) {
         ++this.mobKills;
         this.sendClientPacket(false);
         if (this.getTask() == LOTRHiredNPCInfo.Task.WARRIOR) {
            boolean wasEnemy = false;
            int addXP = 0;
            LOTRFaction unitFaction = this.theEntity.getHiringFaction();
            if (target instanceof EntityPlayer) {
               wasEnemy = LOTRLevelData.getData((EntityPlayer)target).getAlignment(unitFaction) < 0.0F;
            } else {
               LOTRFaction targetFaction = LOTRMod.getNPCFaction(target);
               if (targetFaction.isBadRelation(unitFaction) || unitFaction == LOTRFaction.RUFFIAN && targetFaction != LOTRFaction.UNALIGNED && targetFaction != LOTRFaction.RUFFIAN) {
                  wasEnemy = true;
                  addXP = 1;
               }
            }

            if (wasEnemy && this.theEntity.func_70681_au().nextInt(3) == 0) {
               EntityPlayer hiringPlayer = this.getHiringPlayer();
               double range = 16.0D;
               if (hiringPlayer != null && this.theEntity.func_70068_e(hiringPlayer) < 256.0D) {
                  String speechBank = this.theEntity.getSpeechBank(hiringPlayer);
                  if (speechBank != null) {
                     this.theEntity.sendSpeechBank(hiringPlayer, speechBank);
                  }
               }
            }

            if (addXP > 0 && LOTRConfig.enableUnitLevelling) {
               this.addExperience(addXP);
            }
         }
      }

   }

   private void addExperience(int xpAdd) {
      this.addExperience(xpAdd, true);
   }

   private void addExperience(int xpAdd, boolean passToRiderOrMount) {
      this.xp += xpAdd;

      while(this.xp >= totalXPForLevel(this.xpLevel + 1)) {
         ++this.xpLevel;
         this.markDirty();
         this.onLevelUp();
      }

      this.sendClientPacket(false);
      if (passToRiderOrMount) {
         this.addExperienceIfApplicable(this.theEntity.field_70153_n, xpAdd);
         this.addExperienceIfApplicable(this.theEntity.field_70154_o, xpAdd);
      }

   }

   private void addExperienceIfApplicable(Entity maybeNPC, int xpAdd) {
      if (maybeNPC instanceof LOTREntityNPC) {
         LOTREntityNPC otherNPC = (LOTREntityNPC)maybeNPC;
         if (otherNPC.hiredNPCInfo.isActive && this.getHiringPlayerUUID().equals(otherNPC.hiredNPCInfo.getHiringPlayerUUID())) {
            otherNPC.hiredNPCInfo.addExperience(xpAdd, false);
         }
      }

   }

   public static int totalXPForLevel(int lvl) {
      if (lvl <= 1) {
         return 0;
      } else {
         double d = 3.0D * (double)(lvl - 1) * Math.pow(1.08D, (double)(lvl - 2));
         return MathHelper.func_76128_c(d);
      }
   }

   public float getProgressToNextLevel() {
      int cap = totalXPForLevel(this.xpLevel + 1);
      int start = totalXPForLevel(this.xpLevel);
      return (float)(this.xp - start) / (float)(cap - start);
   }

   private void onLevelUp() {
      this.addLevelUpHealthGain(this.theEntity);
      Entity mount = this.theEntity.field_70154_o;
      if (mount instanceof EntityLivingBase && !(mount instanceof LOTREntityNPC)) {
         this.addLevelUpHealthGain((EntityLivingBase)mount);
      }

      EntityPlayer hirer = this.getHiringPlayer();
      if (hirer != null) {
         hirer.func_145747_a(new ChatComponentTranslation("lotr.hiredNPC.levelUp", new Object[]{this.theEntity.func_70005_c_(), this.xpLevel}));
      }

      this.spawnLevelUpFireworks();
   }

   private void addLevelUpHealthGain(EntityLivingBase gainingEntity) {
      float healthBoost = 1.0F;
      IAttributeInstance attrHealth = gainingEntity.func_110148_a(SharedMonsterAttributes.field_111267_a);
      attrHealth.func_111128_a(attrHealth.func_111125_b() + (double)healthBoost);
      gainingEntity.func_70691_i(healthBoost);
   }

   private void spawnLevelUpFireworks() {
      boolean bigLvlUp = this.xpLevel % 5 == 0;
      World world = this.theEntity.field_70170_p;
      ItemStack itemstack = new ItemStack(Items.field_151152_bP);
      NBTTagCompound itemData = new NBTTagCompound();
      NBTTagCompound fireworkData = new NBTTagCompound();
      NBTTagList explosionsList = new NBTTagList();
      int explosions = 1;

      NBTTagCompound fireworkNBT;
      for(int l = 0; l < explosions; ++l) {
         fireworkNBT = new NBTTagCompound();
         fireworkNBT.func_74757_a("Flicker", true);
         fireworkNBT.func_74757_a("Trail", bigLvlUp);
         int[] colors = new int[]{16733440, this.theEntity.getFaction().getFactionColor()};
         fireworkNBT.func_74783_a("Colors", colors);
         int effectType = bigLvlUp ? 1 : 0;
         fireworkNBT.func_74774_a("Type", (byte)effectType);
         explosionsList.func_74742_a(fireworkNBT);
      }

      fireworkData.func_74782_a("Explosions", explosionsList);
      itemData.func_74782_a("Fireworks", fireworkData);
      itemstack.func_77982_d(itemData);
      EntityFireworkRocket firework = new EntityFireworkRocket(world, this.theEntity.field_70165_t, this.theEntity.field_70121_D.field_72338_b + (double)this.theEntity.field_70131_O, this.theEntity.field_70161_v, itemstack);
      fireworkNBT = new NBTTagCompound();
      firework.func_70014_b(fireworkNBT);
      fireworkNBT.func_74768_a("LifeTime", bigLvlUp ? 20 : 15);
      firework.func_70037_a(fireworkNBT);
      world.func_72838_d(firework);
   }

   public boolean tryTeleportToHiringPlayer(boolean failsafe) {
      World world = this.theEntity.field_70170_p;
      if (!world.field_72995_K) {
         EntityPlayer entityplayer = this.getHiringPlayer();
         if (this.isActive && entityplayer != null && this.theEntity.field_70153_n == null) {
            int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
            int j = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b);
            int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
            float minDist = 3.0F;
            float maxDist = 6.0F;
            float extraDist = this.theEntity.field_70130_N / 2.0F;
            if (this.theEntity.field_70154_o instanceof EntityLiving) {
               extraDist = Math.max(this.theEntity.field_70130_N, this.theEntity.field_70154_o.field_70130_N) / 2.0F;
            }

            minDist += extraDist;
            maxDist += extraDist;
            int attempts = 120;

            for(int l = 0; l < attempts; ++l) {
               float angle = world.field_73012_v.nextFloat() * 3.1415927F * 2.0F;
               float sin = MathHelper.func_76126_a(angle);
               float cos = MathHelper.func_76134_b(angle);
               float r = MathHelper.func_151240_a(world.field_73012_v, minDist, maxDist);
               int i1 = MathHelper.func_76128_c((double)i + 0.5D + (double)(cos * r));
               int k1 = MathHelper.func_76128_c((double)k + 0.5D + (double)(sin * r));
               int j1 = MathHelper.func_76136_a(world.field_73012_v, j - 4, j + 4);
               double d = (double)i1 + 0.5D;
               double d1 = (double)j1;
               double d2 = (double)k1 + 0.5D;
               float halfWidth = this.theEntity.field_70130_N / 2.0F;
               float height = this.theEntity.field_70131_O;
               float yExtra = -this.theEntity.field_70129_M + this.theEntity.field_70139_V;
               AxisAlignedBB npcBB = AxisAlignedBB.func_72330_a(d - (double)halfWidth, d1 + (double)yExtra, d2 - (double)halfWidth, d + (double)halfWidth, d1 + (double)yExtra + (double)height, d2 + (double)halfWidth);
               if (world.func_147461_a(npcBB).isEmpty() && world.func_147439_a(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP)) {
                  if (!(this.theEntity.field_70154_o instanceof EntityLiving)) {
                     this.theEntity.func_70012_b(d, d1, d2, this.theEntity.field_70177_z, this.theEntity.field_70125_A);
                     this.theEntity.field_70143_R = 0.0F;
                     this.theEntity.func_70661_as().func_75499_g();
                     this.theEntity.func_70624_b((EntityLivingBase)null);
                     return true;
                  }

                  EntityLiving mount = (EntityLiving)this.theEntity.field_70154_o;
                  float mHalfWidth = mount.field_70130_N / 2.0F;
                  float mHeight = mount.field_70131_O;
                  float mYExtra = -mount.field_70129_M + mount.field_70139_V;
                  AxisAlignedBB mountBB = AxisAlignedBB.func_72330_a(d - (double)mHalfWidth, d1 + (double)mYExtra, d2 - (double)mHalfWidth, d + (double)mHalfWidth, d1 + (double)mYExtra + (double)mHeight, d2 + (double)mHalfWidth);
                  if (world.func_147461_a(mountBB).isEmpty()) {
                     mount.func_70012_b(d, d1, d2, this.theEntity.field_70177_z, this.theEntity.field_70125_A);
                     mount.field_70143_R = 0.0F;
                     mount.func_70661_as().func_75499_g();
                     mount.func_70624_b((EntityLivingBase)null);
                     this.theEntity.field_70143_R = 0.0F;
                     this.theEntity.func_70661_as().func_75499_g();
                     this.theEntity.func_70624_b((EntityLivingBase)null);
                     return true;
                  }
               }
            }

            if (failsafe) {
               double d = (double)i + 0.5D;
               double d1 = (double)j;
               double d2 = (double)k + 0.5D;
               if (world.func_147439_a(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP)) {
                  if (this.theEntity.field_70154_o instanceof EntityLiving) {
                     EntityLiving mount = (EntityLiving)this.theEntity.field_70154_o;
                     mount.func_70012_b(d, d1, d2, this.theEntity.field_70177_z, this.theEntity.field_70125_A);
                     mount.field_70143_R = 0.0F;
                     mount.func_70661_as().func_75499_g();
                     mount.func_70624_b((EntityLivingBase)null);
                     this.theEntity.field_70143_R = 0.0F;
                     this.theEntity.func_70661_as().func_75499_g();
                     this.theEntity.func_70624_b((EntityLivingBase)null);
                     return true;
                  }

                  this.theEntity.func_70012_b(d, d1, d2, this.theEntity.field_70177_z, this.theEntity.field_70125_A);
                  this.theEntity.field_70143_R = 0.0F;
                  this.theEntity.func_70661_as().func_75499_g();
                  this.theEntity.func_70624_b((EntityLivingBase)null);
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void writeToNBT(NBTTagCompound nbt) {
      NBTTagCompound data = new NBTTagCompound();
      data.func_74757_a("IsActive", this.isActive);
      if (this.hiringPlayerUUID != null) {
         data.func_74778_a("HiringPlayerUUID", this.hiringPlayerUUID.toString());
      }

      data.func_74776_a("AlignReqF", this.alignmentRequiredToCommand);
      data.func_74774_a("PledgeType", (byte)this.pledgeType.typeID);
      data.func_74757_a("CanMove", this.canMove);
      data.func_74757_a("TeleportAutomatically", this.teleportAutomatically);
      data.func_74768_a("MobKills", this.mobKills);
      data.func_74757_a("GuardMode", this.guardMode);
      data.func_74768_a("GuardRange", this.guardRange);
      data.func_74768_a("Task", this.hiredTask.ordinal());
      data.func_74768_a("Xp", this.xp);
      data.func_74768_a("XpLevel", this.xpLevel);
      if (!StringUtils.func_151246_b(this.hiredSquadron)) {
         data.func_74778_a("Squadron", this.hiredSquadron);
      }

      if (this.hiredInventory != null) {
         this.hiredInventory.writeToNBT(data);
      }

      nbt.func_74782_a("HiredNPCInfo", data);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      NBTTagCompound data = nbt.func_74775_l("HiredNPCInfo");
      if (data != null) {
         String savedUUID;
         if (data.func_74764_b("HiringPlayerName")) {
            savedUUID = data.func_74779_i("HiringPlayerName");
            this.hiringPlayerUUID = UUID.fromString(PreYggdrasilConverter.func_152719_a(savedUUID));
         } else if (data.func_74764_b("HiringPlayerUUID")) {
            savedUUID = data.func_74779_i("HiringPlayerUUID");
            if (!StringUtils.func_151246_b(savedUUID)) {
               this.hiringPlayerUUID = UUID.fromString(savedUUID);
            }
         }

         this.isActive = data.func_74767_n("IsActive");
         if (data.func_74764_b("AlignmentRequired")) {
            this.alignmentRequiredToCommand = (float)data.func_74762_e("AlignmentRequired");
         } else {
            this.alignmentRequiredToCommand = data.func_74760_g("AlignReqF");
         }

         if (data.func_74764_b("PledgeType")) {
            int pledgeID = data.func_74771_c("PledgeType");
            this.pledgeType = LOTRUnitTradeEntry.PledgeType.forID(pledgeID);
         }

         this.canMove = data.func_74767_n("CanMove");
         if (data.func_74764_b("TeleportAutomatically")) {
            this.teleportAutomatically = data.func_74767_n("TeleportAutomatically");
            this.mobKills = data.func_74762_e("MobKills");
            this.setGuardMode(data.func_74767_n("GuardMode"));
            this.setGuardRange(data.func_74762_e("GuardRange"));
         }

         this.setTask(LOTRHiredNPCInfo.Task.forID(data.func_74762_e("Task")));
         if (data.func_74764_b("Xp")) {
            this.xp = data.func_74762_e("Xp");
         }

         if (data.func_74764_b("XpLevel")) {
            this.xpLevel = data.func_74762_e("XpLevel");
         } else if (data.func_74764_b("XpLvl")) {
            this.xpLevel = data.func_74762_e("XpLvl");
            this.correctOutdatedLevelUpHealthGain();
         }

         if (data.func_74764_b("Squadron")) {
            this.hiredSquadron = data.func_74779_i("Squadron");
         }

         if (this.hiredInventory != null) {
            this.hiredInventory.readFromNBT(data);
         }
      }

   }

   private void correctOutdatedLevelUpHealthGain() {
      int levelsGained = this.xpLevel - 1;
      if (levelsGained > 0) {
         float oldHealthGain = 2.0F;
         float newHealthGain = 1.0F;
         float healthCorrectionReduction = (oldHealthGain - newHealthGain) * (float)levelsGained;
         IAttributeInstance attrHealth = this.theEntity.func_110148_a(SharedMonsterAttributes.field_111267_a);
         attrHealth.func_111128_a(attrHealth.func_111125_b() - (double)healthCorrectionReduction);
         this.theEntity.func_70606_j(this.theEntity.func_110143_aJ());
      }

   }

   public void sendBasicData(EntityPlayerMP entityplayer) {
      LOTRPacketHiredInfo packet = new LOTRPacketHiredInfo(this.theEntity.func_145782_y(), this.hiringPlayerUUID, this.hiredTask, this.getSquadron(), this.xpLevel);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   private void sendBasicDataToAllWatchers() {
      int x = MathHelper.func_76128_c(this.theEntity.field_70165_t) >> 4;
      int z = MathHelper.func_76128_c(this.theEntity.field_70161_v) >> 4;
      PlayerManager playermanager = ((WorldServer)this.theEntity.field_70170_p).func_73040_p();
      List players = this.theEntity.field_70170_p.field_73010_i;
      Iterator var5 = players.iterator();

      while(var5.hasNext()) {
         Object obj = var5.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         if (playermanager.func_72694_a(entityplayer, x, z)) {
            this.sendBasicData(entityplayer);
         }
      }

   }

   public void receiveBasicData(LOTRPacketHiredInfo packet) {
      this.hiringPlayerUUID = packet.hiringPlayer;
      this.setTask(packet.task);
      this.setSquadron(packet.squadron);
      this.xpLevel = packet.xpLvl;
   }

   public void sendClientPacket(boolean shouldOpenGui) {
      if (!this.theEntity.field_70170_p.field_72995_K && this.getHiringPlayer() != null) {
         LOTRPacketHiredGui packet = new LOTRPacketHiredGui(this.theEntity.func_145782_y(), shouldOpenGui);
         packet.isActive = this.isActive;
         packet.canMove = this.canMove;
         packet.teleportAutomatically = this.teleportAutomatically;
         packet.mobKills = this.mobKills;
         packet.xp = this.xp;
         packet.alignmentRequired = this.alignmentRequiredToCommand;
         packet.pledgeType = this.pledgeType;
         packet.inCombat = this.inCombat;
         packet.guardMode = this.guardMode;
         packet.guardRange = this.guardRange;
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)this.getHiringPlayer());
         if (shouldOpenGui) {
            this.isGuiOpen = true;
         }

      }
   }

   public void receiveClientPacket(LOTRPacketHiredGui packet) {
      this.isActive = packet.isActive;
      this.canMove = packet.canMove;
      this.teleportAutomatically = packet.teleportAutomatically;
      this.mobKills = packet.mobKills;
      this.xp = packet.xp;
      this.alignmentRequiredToCommand = packet.alignmentRequired;
      this.pledgeType = packet.pledgeType;
      this.inCombat = packet.inCombat;
      this.guardMode = packet.guardMode;
      this.guardRange = packet.guardRange;
   }

   public static enum Task {
      WARRIOR(true),
      FARMER(false);

      public final boolean displayXpLevel;

      private Task(boolean displayLvl) {
         this.displayXpLevel = displayLvl;
      }

      public static LOTRHiredNPCInfo.Task forID(int id) {
         LOTRHiredNPCInfo.Task[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRHiredNPCInfo.Task task = var1[var3];
            if (task.ordinal() == id) {
               return task;
            }
         }

         return WARRIOR;
      }
   }
}
