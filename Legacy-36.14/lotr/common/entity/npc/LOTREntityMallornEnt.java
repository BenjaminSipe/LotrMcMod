package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIBossJumpAttack;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityMallornLeafBomb;
import lotr.common.item.LOTRItemBossTrophy;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMallornEntHeal;
import lotr.common.network.LOTRPacketMallornEntSummon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LOTREntityMallornEnt extends LOTREntityEnt implements LOTRBoss {
   public static float BOSS_SCALE = 1.5F;
   private static int SPAWN_TIME = 150;
   private static int MAX_LEAF_HEALINGS = 5;
   private LOTREntityMallornEnt.LeafHealInfo[] leafHealings;
   private EntityAIBase meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 2.0D, false);
   private EntityAIBase rangedAttackAI = new LOTREntityAIRangedAttack(this, 1.5D, 30, 50, 24.0F);

   public LOTREntityMallornEnt(World world) {
      super(world);
      this.func_70105_a(this.npcWidth * BOSS_SCALE, this.npcHeight * BOSS_SCALE);
      this.field_70714_bg.field_75782_a.clear();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(0, new LOTREntityAIBossJumpAttack(this, 1.5D, 0.02F));
      this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(4, new EntityAIWatchClosest(this, EntityLiving.class, 10.0F, 0.02F));
      this.field_70714_bg.func_75776_a(5, new EntityAILookIdle(this));
      this.resetLeafHealings();
   }

   private void resetLeafHealings() {
      this.leafHealings = new LOTREntityMallornEnt.LeafHealInfo[MAX_LEAF_HEALINGS];

      for(int i = 0; i < MAX_LEAF_HEALINGS; ++i) {
         this.leafHealings[i] = new LOTREntityMallornEnt.LeafHealInfo(this, i);
      }

   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(22, Short.valueOf((short)0));
      this.field_70180_af.func_75682_a(23, (byte)0);
   }

   public int getEntSpawnTick() {
      return this.field_70180_af.func_75693_b(22);
   }

   public void setEntSpawnTick(int i) {
      this.field_70180_af.func_75692_b(22, (short)i);
   }

   public boolean hasWeaponShield() {
      return this.field_70180_af.func_75683_a(23) == 1;
   }

   public void setHasWeaponShield(boolean flag) {
      this.field_70180_af.func_75692_b(23, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public boolean isWeaponShieldActive() {
      return this.hasWeaponShield() && !this.func_70027_ad();
   }

   public float getSpawningOffset(float f) {
      float f1 = ((float)this.getEntSpawnTick() + f) / (float)SPAWN_TIME;
      f1 = Math.min(f1, 1.0F);
      return (1.0F - f1) * -5.0F;
   }

   public boolean shouldBurningPanic() {
      return false;
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcAttackDamage).func_111128_a(8.0D);
   }

   public int getExtraHeadBranches() {
      if (this.hasWeaponShield()) {
         return 0;
      } else {
         int max = 8;
         float healthR = this.func_110143_aJ() / this.func_110138_aP();
         int branches = MathHelper.func_76123_f(healthR * (float)max);
         branches = MathHelper.func_76125_a(branches, 1, max);
         return branches;
      }
   }

   public float getBaseChanceModifier() {
      return this.bossInfo.getHealthChanceModifier();
   }

   public void sendEntBossSpeech(String speechBank) {
      List players = this.field_70170_p.field_73010_i;
      double range = 64.0D;
      Iterator var5 = players.iterator();

      while(var5.hasNext()) {
         Object obj = var5.next();
         EntityPlayer entityplayer = (EntityPlayer)obj;
         if (this.func_70068_e(entityplayer) <= range * range) {
            this.sendSpeechBank(entityplayer, "ent/mallornEnt/" + speechBank);
         }
      }

   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(1, this.meleeAttackAI);
      }

      if (mode == LOTREntityNPC.AttackMode.MELEE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(1, this.meleeAttackAI);
      }

      if (mode == LOTREntityNPC.AttackMode.RANGED) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(1, this.rangedAttackAI);
      }

   }

   public double getMeleeRange() {
      return 12.0D;
   }

   public void func_82196_d(EntityLivingBase target, float f) {
      LOTREntityMallornLeafBomb leaves = new LOTREntityMallornLeafBomb(this.field_70170_p, this, target);
      leaves.leavesDamage = 6.0F;
      this.field_70170_p.func_72838_d(leaves);
      this.func_85030_a("lotr:ent.mallorn.leafAttack", this.func_70599_aP(), this.func_70647_i());
      this.func_71038_i();
   }

   public void func_70636_d() {
      super.func_70636_d();
      int i;
      double d;
      double d1;
      double d2;
      int l;
      int i1;
      double d2;
      double d3;
      double d4;
      int l;
      if (this.getEntSpawnTick() < SPAWN_TIME) {
         if (!this.field_70170_p.field_72995_K) {
            this.setEntSpawnTick(this.getEntSpawnTick() + 1);
            if (this.getEntSpawnTick() == SPAWN_TIME) {
               this.bossInfo.doJumpAttack(1.5D);
            }
         } else {
            for(i = 0; i < 16; ++i) {
               d = this.field_70165_t + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D;
               d1 = this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O + (double)this.getSpawningOffset(0.0F);
               d2 = this.field_70161_v + this.field_70146_Z.nextGaussian() * (double)this.field_70130_N * 0.5D;
               LOTRMod.proxy.spawnParticle("mEntSpawn", d, d1, d2, 0.0D, 0.0D, 0.0D);
            }

            int leaves = 8;

            for(l = 0; l < leaves; ++l) {
               float leafR = (float)l / (float)leaves;
               float argBase = (float)this.getEntSpawnTick() + leafR;
               double r = 3.5D;
               double up = 0.5D;
               float[] var9 = new float[]{0.0F, 3.1415927F};
               l = var9.length;

               for(i1 = 0; i1 < l; ++i1) {
                  float extra = var9[i1];
                  float arg = argBase + extra;
                  d2 = this.field_70165_t + r * (double)MathHelper.func_76134_b(arg);
                  d3 = this.field_70161_v + r * (double)MathHelper.func_76126_a(arg);
                  d4 = this.field_70163_u + (double)leafR * up;
                  LOTRMod.proxy.spawnParticle("leafGold_40", d2, d4, d3, 0.0D, up, 0.0D);
               }
            }
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         float f = this.getBaseChanceModifier();
         f *= 0.05F;
         if (this.field_70146_Z.nextFloat() < f) {
            this.bossInfo.doTargetedJumpAttack(1.5D);
         }
      }

      LOTREntityMallornEnt.LeafHealInfo[] var25;
      int var26;
      LOTREntityMallornEnt.LeafHealInfo healing;
      int i;
      int k;
      if (!this.field_70170_p.field_72995_K && this.func_110143_aJ() < this.func_110138_aP()) {
         var25 = this.leafHealings;
         l = var25.length;

         for(var26 = 0; var26 < l; ++var26) {
            healing = var25[var26];
            if (!healing.active) {
               float f = this.getBaseChanceModifier();
               f *= 0.02F;
               if (this.field_70146_Z.nextFloat() < f) {
                  int range = 16;
                  i = MathHelper.func_76128_c(this.field_70165_t);
                  int j = MathHelper.func_76128_c(this.field_70163_u);
                  k = MathHelper.func_76128_c(this.field_70161_v);

                  for(l = 0; l < 30; ++l) {
                     i1 = i + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
                     int j1 = j + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
                     int k1 = k + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
                     Block block = this.field_70170_p.func_147439_a(i1, j1, k1);
                     if (block instanceof BlockLeavesBase) {
                        healing.active = true;
                        healing.leafX = i1;
                        healing.leafY = j1;
                        healing.leafZ = k1;
                        healing.healTime = 15 + this.field_70146_Z.nextInt(15);
                        this.sendHealInfoToWatchers(healing);
                        break;
                     }
                  }
               }
            }
         }
      }

      var25 = this.leafHealings;
      l = var25.length;

      double d4;
      double d5;
      for(var26 = 0; var26 < l; ++var26) {
         healing = var25[var26];
         if (healing.active) {
            int leafX = healing.leafX;
            int leafY = healing.leafY;
            i = healing.leafZ;
            Block block = this.field_70170_p.func_147439_a(leafX, leafY, i);
            k = this.field_70170_p.func_72805_g(leafX, leafY, i);
            if (block instanceof BlockLeavesBase) {
               if (!this.field_70170_p.field_72995_K) {
                  if (this.field_70173_aa % 20 == 0) {
                     this.func_70691_i(2.0F);
                     healing.healTime--;
                     if (this.func_110143_aJ() >= this.func_110138_aP() || healing.healTime <= 0) {
                        healing.active = false;
                        this.sendHealInfoToWatchers(healing);
                     }
                  }
               } else {
                  d4 = (double)((float)leafX + 0.5F);
                  d5 = (double)((float)leafY + 0.5F);
                  d2 = (double)((float)i + 0.5F);
                  d3 = this.field_70165_t - d4;
                  d4 = this.field_70163_u + (double)this.field_70131_O * 0.9D - d5;
                  double d5 = this.field_70161_v - d2;
                  d3 /= 25.0D;
                  d4 /= 25.0D;
                  d5 /= 25.0D;
                  LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.func_149682_b(block) + "_" + k, d4, d5, d2, d3, d4, d5);
               }
            } else if (!this.field_70170_p.field_72995_K) {
               healing.active = false;
               this.sendHealInfoToWatchers(healing);
            }
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         if (this.func_110143_aJ() < this.func_110138_aP() && this.field_70146_Z.nextInt(50) == 0) {
            this.trySummonEnts();
         }
      } else if (this.getEntSpawnTick() >= SPAWN_TIME) {
         for(i = 0; i < 2; ++i) {
            d = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            d1 = this.field_70163_u + (double)this.field_70131_O + this.field_70146_Z.nextDouble() * (double)this.field_70131_O * 0.5D;
            d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N;
            double d3 = MathHelper.func_82716_a(this.field_70146_Z, -0.2D, 0.2D);
            d4 = MathHelper.func_82716_a(this.field_70146_Z, -0.2D, 0.0D);
            d5 = MathHelper.func_82716_a(this.field_70146_Z, -0.2D, 0.2D);
            int time = 30 + this.field_70146_Z.nextInt(30);
            LOTRMod.proxy.spawnParticle("leafGold_" + time, d, d1, d2, d3, d4, d5);
         }
      }

   }

   private void trySummonEnts() {
      float f = this.getBaseChanceModifier();
      f *= 0.5F;
      List nearbyTrees = this.field_70170_p.func_72872_a(LOTREntityTree.class, this.field_70121_D.func_72314_b(24.0D, 8.0D, 24.0D));
      int maxNearbyTrees = 6;
      float nearbyModifier = (float)(maxNearbyTrees - nearbyTrees.size()) / (float)maxNearbyTrees;
      f *= nearbyModifier;
      if (this.field_70146_Z.nextFloat() < f) {
         LOTREntityTree tree = this.field_70146_Z.nextInt(3) == 0 ? new LOTREntityHuorn(this.field_70170_p) : new LOTREntityEnt(this.field_70170_p);
         int range = 12;
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70163_u);
         int k = MathHelper.func_76128_c(this.field_70161_v);

         for(int l = 0; l < 30; ++l) {
            int i1 = i + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
            int j1 = j + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
            int k1 = k + MathHelper.func_76136_a(this.field_70146_Z, -range, range);
            if (this.field_70170_p.func_147439_a(i1, j1 - 1, k1).func_149721_r() && !this.field_70170_p.func_147439_a(i1, j1, k1).func_149721_r() && !this.field_70170_p.func_147439_a(i1, j1 + 1, k1).func_149721_r()) {
               ((LOTREntityTree)tree).func_70012_b((double)i1 + 0.5D, (double)j1, (double)k1 + 0.5D, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
               ((LOTREntityTree)tree).liftSpawnRestrictions = true;
               if (((LOTREntityTree)tree).func_70601_bi()) {
                  ((LOTREntityTree)tree).liftSpawnRestrictions = false;
                  ((LOTREntityTree)tree).func_110161_a((IEntityLivingData)null);
                  this.field_70170_p.func_72838_d((Entity)tree);
                  this.sendEntSummon((LOTREntityTree)tree);
                  this.field_70170_p.func_72956_a((Entity)tree, "lotr:ent.mallorn.summonEnt", this.func_70599_aP(), this.func_70647_i());
                  break;
               }
            }
         }
      }

   }

   private void sendEntSummon(LOTREntityTree tree) {
      LOTRPacketMallornEntSummon packet = new LOTRPacketMallornEntSummon(this.func_145782_y(), tree.func_145782_y());
      LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(tree, 64.0D));
   }

   public void onPlayerStartTracking(EntityPlayerMP entityplayer) {
      super.onPlayerStartTracking(entityplayer);
      LOTREntityMallornEnt.LeafHealInfo[] var2 = this.leafHealings;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTREntityMallornEnt.LeafHealInfo healing = var2[var4];
         healing.sendData(entityplayer);
      }

   }

   private void sendHealInfoToWatchers(LOTREntityMallornEnt.LeafHealInfo healing) {
      int x = MathHelper.func_76128_c(this.field_70165_t) >> 4;
      int z = MathHelper.func_76128_c(this.field_70161_v) >> 4;
      PlayerManager playermanager = ((WorldServer)this.field_70170_p).func_73040_p();
      List players = this.field_70170_p.field_73010_i;
      Iterator var6 = players.iterator();

      while(var6.hasNext()) {
         Object obj = var6.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         if (playermanager.func_72694_a(entityplayer, x, z)) {
            healing.sendData(entityplayer);
         }
      }

   }

   public void receiveClientHealing(NBTTagCompound data) {
      LOTREntityMallornEnt.LeafHealInfo healing = new LOTREntityMallornEnt.LeafHealInfo(this, 0);
      healing.receiveData(data);
      this.leafHealings[healing.slot] = healing;
   }

   public void spawnEntSummonParticles(LOTREntityTree tree) {
      int type = tree.getTreeType();
      Block leafBlock = LOTREntityTree.LEAF_BLOCKS[type];
      int leafMeta = LOTREntityTree.LEAF_META[type];
      int particles = 60;

      int l;
      for(l = 0; l < particles; ++l) {
         float t = (float)l / (float)particles;
         LOTRMod.proxy.spawnParticle("mEntSummon_" + this.func_145782_y() + "_" + tree.func_145782_y() + "_" + t + "_" + Block.func_149682_b(leafBlock) + "_" + leafMeta, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
      }

      for(l = 0; l < 120; ++l) {
         double d = tree.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)tree.field_70130_N;
         double d1 = tree.field_70163_u + (double)tree.field_70131_O * 0.5D;
         double d2 = tree.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)tree.field_70130_N;
         double d3 = MathHelper.func_82716_a(this.field_70146_Z, -0.4D, 0.4D);
         double d4 = MathHelper.func_82716_a(this.field_70146_Z, -0.4D, 0.4D);
         double d5 = MathHelper.func_82716_a(this.field_70146_Z, -0.4D, 0.4D);
         LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.func_149682_b(leafBlock) + "_" + leafMeta, d, d1, d2, d3, d4, d5);
      }

   }

   protected boolean func_70610_aX() {
      return this.getEntSpawnTick() < SPAWN_TIME ? true : super.func_70610_aX();
   }

   public void onJumpAttackFall() {
      this.field_70170_p.func_72960_a(this, (byte)20);
      this.func_85030_a("lotr:troll.rockSmash", 1.5F, 0.75F);
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      int i;
      if (b == 20) {
         for(i = 0; i < 360; i += 2) {
            float angle = (float)Math.toRadians((double)i);
            double distance = 2.0D;
            double d = distance * (double)MathHelper.func_76126_a(angle);
            double d1 = distance * (double)MathHelper.func_76134_b(angle);
            LOTRMod.proxy.spawnParticle("mEntJumpSmash", this.field_70165_t + d, this.field_70121_D.field_72338_b + 0.1D, this.field_70161_v + d1, d * 0.2D, 0.2D, d1 * 0.2D);
         }
      }

      if (b == 21) {
         for(i = 0; i < 200; ++i) {
            double d = this.field_70165_t;
            double d1 = this.field_70163_u + (double)(this.field_70131_O * 0.5F);
            double d2 = this.field_70161_v;
            double d3 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
            double d4 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
            double d5 = MathHelper.func_82716_a(this.field_70146_Z, -0.1D, 0.1D);
            int time = 40 + this.field_70146_Z.nextInt(30);
            LOTRMod.proxy.spawnParticle("leafGold_" + time, d, d1, d2, d3, d4, d5);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      NBTTagList leafHealingTags = new NBTTagList();

      for(int i = 0; i < this.leafHealings.length; ++i) {
         LOTREntityMallornEnt.LeafHealInfo healing = this.leafHealings[i];
         NBTTagCompound healTag = new NBTTagCompound();
         healing.writeToNBT(healTag);
         leafHealingTags.func_74742_a(healTag);
      }

      nbt.func_74782_a("LeafHealings", leafHealingTags);
      nbt.func_74768_a("EntSpawnTick", this.getEntSpawnTick());
      nbt.func_74757_a("EntWeaponShield", this.hasWeaponShield());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.resetLeafHealings();
      NBTTagList leafHealingTags = nbt.func_150295_c("LeafHealings", 10);

      for(int i = 0; i < leafHealingTags.func_74745_c(); ++i) {
         NBTTagCompound healTag = leafHealingTags.func_150305_b(i);
         int slot = healTag.func_74771_c("Slot");
         if (slot >= 0 && slot < this.leafHealings.length) {
            LOTREntityMallornEnt.LeafHealInfo healing = this.leafHealings[slot];
            healing.readFromNBT(healTag);
         }
      }

      this.setEntSpawnTick(nbt.func_74762_e("EntSpawnTick"));
      this.setHasWeaponShield(nbt.func_74767_n("EntWeaponShield"));
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (this.getEntSpawnTick() < SPAWN_TIME) {
         return false;
      } else {
         if (LOTRMod.getDamagingPlayerIncludingUnits(damagesource) == null && f > 1.0F) {
            f = 1.0F;
         }

         if (!this.isTreeEffectiveDamage(damagesource)) {
            f *= 0.5F;
         }

         if (this.isWeaponShieldActive() && !damagesource.func_76347_k()) {
            f = 0.0F;
         }

         boolean flag = super.func_70097_a(damagesource, f);
         return flag;
      }
   }

   protected boolean doTreeDamageCalculation() {
      return false;
   }

   protected void func_70665_d(DamageSource damagesource, float f) {
      super.func_70665_d(damagesource, f);
      if (!this.field_70170_p.field_72995_K && !this.hasWeaponShield() && this.func_110143_aJ() <= 0.0F) {
         this.setHasWeaponShield(true);
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
         this.func_70606_j(this.func_110138_aP());
         this.sendEntBossSpeech("shield");
      }

   }

   public void func_70645_a(DamageSource damagesource) {
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72960_a(this, (byte)21);
         int fireRange = 12;
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70163_u);
         int k = MathHelper.func_76128_c(this.field_70161_v);

         for(int i1 = i - fireRange; i1 <= i + fireRange; ++i1) {
            for(int j1 = j - fireRange; j1 <= j + fireRange; ++j1) {
               for(int k1 = k - fireRange; k1 <= k + fireRange; ++k1) {
                  Block block = this.field_70170_p.func_147439_a(i1, j1, k1);
                  if (block instanceof BlockFire) {
                     this.field_70170_p.func_147468_f(i1, j1, k1);
                  }
               }
            }
         }
      }

      super.func_70645_a(damagesource);
   }

   public void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);

      int sticks;
      for(int wood = MathHelper.func_76136_a(this.field_70146_Z, 20, 30 + i * 20); wood > 0; wood -= sticks) {
         sticks = Math.min(20, wood);
         this.func_70099_a(new ItemStack(LOTRMod.wood, sticks, 1), 0.0F);
      }

      int dropped;
      for(sticks = MathHelper.func_76136_a(this.field_70146_Z, 30, 40 + i * 20); sticks > 0; sticks -= dropped) {
         dropped = Math.min(20, sticks);
         this.func_70099_a(new ItemStack(LOTRMod.mallornStick, dropped), 0.0F);
      }

      this.func_70099_a(new ItemStack(LOTRMod.bossTrophy, 1, LOTRItemBossTrophy.TrophyType.MALLORN_ENT.trophyID), 0.0F);
      float maceChance = 0.3F;
      maceChance += (float)i * 0.1F;
      if (this.field_70146_Z.nextFloat() < maceChance) {
         this.func_145779_a(LOTRMod.maceMallornCharred, 1);
      }

   }

   public LOTRAchievement getBossKillAchievement() {
      return LOTRAchievement.killMallornEnt;
   }

   public float getAlignmentBonus() {
      return 50.0F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 100;
   }

   protected LOTRAchievement getTalkAchievement() {
      return null;
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return null;
   }

   private static class LeafHealInfo {
      private LOTREntityMallornEnt theEnt;
      private int slot;
      private boolean active;
      private int leafX;
      private int leafY;
      private int leafZ;
      private int healTime;

      public LeafHealInfo(LOTREntityMallornEnt ent, int i) {
         this.theEnt = ent;
         this.slot = i;
      }

      public void writeToNBT(NBTTagCompound nbt) {
         nbt.func_74774_a("Slot", (byte)this.slot);
         nbt.func_74757_a("Active", this.active);
         nbt.func_74768_a("X", this.leafX);
         nbt.func_74768_a("Y", this.leafY);
         nbt.func_74768_a("Z", this.leafZ);
         nbt.func_74777_a("Time", (short)this.healTime);
      }

      public void readFromNBT(NBTTagCompound nbt) {
         this.slot = nbt.func_74771_c("Slot");
         this.active = nbt.func_74767_n("Active");
         this.leafX = nbt.func_74762_e("X");
         this.leafY = nbt.func_74762_e("Y");
         this.leafZ = nbt.func_74762_e("Z");
         this.healTime = nbt.func_74765_d("healTime");
      }

      public void sendData(EntityPlayerMP entityplayer) {
         NBTTagCompound nbt = new NBTTagCompound();
         this.writeToNBT(nbt);
         LOTRPacketMallornEntHeal packet = new LOTRPacketMallornEntHeal(this.theEnt.func_145782_y(), nbt);
         LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
      }

      public void receiveData(NBTTagCompound nbt) {
         this.readFromNBT(nbt);
      }
   }
}
