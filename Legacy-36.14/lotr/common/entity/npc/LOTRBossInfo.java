package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTRNPCTargetSelector;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRBossInfo {
   private LOTREntityNPC theNPC;
   private LOTRBoss theBoss;
   public EntityPlayer lastAttackingPlayer;
   private Map playerHurtTimes = new HashMap();
   private static int PLAYER_HURT_COOLDOWN = 600;
   private static float PLAYER_DAMAGE_THRESHOLD = 40.0F;
   public boolean jumpAttack;

   public LOTRBossInfo(LOTRBoss boss) {
      this.theBoss = boss;
      this.theNPC = (LOTREntityNPC)this.theBoss;
   }

   public float getHealthChanceModifier() {
      float f = 1.0F - this.theNPC.func_110143_aJ() / this.theNPC.func_110138_aP();
      return MathHelper.func_76129_c(f);
   }

   public List getNearbyEnemies() {
      List enemies = new ArrayList();
      List players = this.theNPC.field_70170_p.func_72872_a(EntityPlayer.class, this.theNPC.field_70121_D.func_72314_b(12.0D, 6.0D, 12.0D));

      for(int i = 0; i < players.size(); ++i) {
         EntityPlayer entityplayer = (EntityPlayer)players.get(i);
         if (!entityplayer.field_71075_bZ.field_75098_d && LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction()) < 0.0F) {
            enemies.add(entityplayer);
         }
      }

      enemies.addAll(this.theNPC.field_70170_p.func_82733_a(EntityLiving.class, this.theNPC.field_70121_D.func_72314_b(12.0D, 6.0D, 12.0D), new LOTRNPCTargetSelector(this.theNPC)));
      return enemies;
   }

   public void doJumpAttack(double jumpSpeed) {
      this.jumpAttack = true;
      this.theNPC.field_70181_x = jumpSpeed;
   }

   public void doTargetedJumpAttack(double jumpSpeed) {
      if (!this.theNPC.field_70170_p.field_72995_K && this.lastAttackingPlayer != null && (this.lastAttackingPlayer.field_70163_u - this.theNPC.field_70163_u > 10.0D || this.theNPC.func_70068_e(this.lastAttackingPlayer) > 400.0D) && this.theNPC.field_70122_E) {
         this.doJumpAttack(jumpSpeed);
         this.theNPC.field_70159_w = this.lastAttackingPlayer.field_70165_t - this.theNPC.field_70165_t;
         this.theNPC.field_70181_x = this.lastAttackingPlayer.field_70163_u - this.theNPC.field_70163_u;
         this.theNPC.field_70179_y = this.lastAttackingPlayer.field_70161_v - this.theNPC.field_70161_v;
         LOTREntityNPC var10000 = this.theNPC;
         var10000.field_70159_w /= 10.0D;
         var10000 = this.theNPC;
         var10000.field_70181_x /= 10.0D;
         var10000 = this.theNPC;
         var10000.field_70179_y /= 10.0D;
         if (this.theNPC.field_70181_x < jumpSpeed) {
            this.theNPC.field_70181_x = jumpSpeed;
         }

         this.theNPC.func_70671_ap().func_75651_a(this.lastAttackingPlayer, 100.0F, 100.0F);
         this.theNPC.func_70671_ap().func_75649_a();
         this.theNPC.field_70177_z = this.theNPC.field_70759_as;
      }

   }

   public float onFall(float fall) {
      if (!this.theNPC.field_70170_p.field_72995_K && this.jumpAttack) {
         fall = 0.0F;
         this.jumpAttack = false;
         List enemies = this.getNearbyEnemies();
         float attackDamage = (float)this.theNPC.func_110148_a(LOTREntityNPC.npcAttackDamage).func_111126_e();

         for(int i = 0; i < enemies.size(); ++i) {
            EntityLivingBase entity = (EntityLivingBase)enemies.get(i);
            float strength = 12.0F - this.theNPC.func_70032_d(entity) / 3.0F;
            strength /= 12.0F;
            entity.func_70097_a(DamageSource.func_76358_a(this.theNPC), strength * attackDamage * 3.0F);
            float knockback = strength * 3.0F;
            entity.func_70024_g((double)(-MathHelper.func_76126_a(this.theNPC.field_70177_z * 3.1415927F / 180.0F) * knockback * 0.5F), 0.25D * (double)knockback, (double)(MathHelper.func_76134_b(this.theNPC.field_70177_z * 3.1415927F / 180.0F) * knockback * 0.5F));
         }

         this.theBoss.onJumpAttackFall();
      }

      return fall;
   }

   private void clearSurroundingBlocks() {
      if (LOTRMod.canGrief(this.theNPC.field_70170_p)) {
         int xzRange = MathHelper.func_76123_f(this.theNPC.field_70130_N / 2.0F * 1.5F);
         int yRange = MathHelper.func_76123_f(this.theNPC.field_70131_O * 1.5F);
         int xzDist = xzRange * xzRange + xzRange * xzRange;
         int i = MathHelper.func_76128_c(this.theNPC.field_70165_t);
         int j = MathHelper.func_76128_c(this.theNPC.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.theNPC.field_70161_v);

         for(int i1 = i - xzRange; i1 <= i + xzRange; ++i1) {
            for(int j1 = j; j1 <= j + yRange; ++j1) {
               for(int k1 = k - xzRange; k1 <= k + xzRange; ++k1) {
                  int i2 = i1 - i;
                  int k2 = k1 - k;
                  int dist = i2 * i2 + k2 * k2;
                  if (dist < xzDist) {
                     Block block = this.theNPC.field_70170_p.func_147439_a(i1, j1, k1);
                     if (block != null && !block.func_149688_o().func_76224_d()) {
                        float resistance = block.getExplosionResistance(this.theNPC, this.theNPC.field_70170_p, i1, j1, k1, this.theNPC.field_70165_t, this.theNPC.field_70121_D.field_72338_b + (double)(this.theNPC.field_70131_O / 2.0F), this.theNPC.field_70161_v);
                        if (block instanceof LOTRWorldProviderUtumno.UtumnoBlock && LOTRDimension.getCurrentDimension(this.theNPC.field_70170_p) != LOTRDimension.UTUMNO) {
                           resistance = 100.0F;
                        }

                        if (resistance < 2000.0F) {
                           block.func_149690_a(this.theNPC.field_70170_p, i1, j1, k1, this.theNPC.field_70170_p.func_72805_g(i1, j1, k1), resistance / 100.0F, 0);
                           this.theNPC.field_70170_p.func_147468_f(i1, j1, k1);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public void onUpdate() {
      if (this.lastAttackingPlayer != null && (!this.lastAttackingPlayer.func_70089_S() || this.lastAttackingPlayer.field_71075_bZ.field_75098_d)) {
         this.lastAttackingPlayer = null;
      }

      if (!this.theNPC.field_70170_p.field_72995_K) {
         Map playerHurtTimes_new = new HashMap();
         Iterator var2 = this.playerHurtTimes.entrySet().iterator();

         while(var2.hasNext()) {
            Entry entry = (Entry)var2.next();
            UUID player = (UUID)entry.getKey();
            int time = (Integer)((Pair)entry.getValue()).getLeft();
            float damage = (Float)((Pair)entry.getValue()).getRight();
            --time;
            if (time > 0) {
               playerHurtTimes_new.put(player, Pair.of(time, damage));
            }
         }

         this.playerHurtTimes = playerHurtTimes_new;
      }

      if (!this.theNPC.field_70170_p.field_72995_K && this.jumpAttack && this.theNPC.field_70173_aa % 5 == 0) {
         this.clearSurroundingBlocks();
      }

   }

   public void onHurt(DamageSource damagesource, float f) {
      if (!this.theNPC.field_70170_p.field_72995_K) {
         EntityPlayer playerSource;
         if (damagesource.func_76346_g() instanceof EntityPlayer) {
            playerSource = (EntityPlayer)damagesource.func_76346_g();
            if (!playerSource.field_71075_bZ.field_75098_d) {
               this.lastAttackingPlayer = playerSource;
            }
         }

         playerSource = LOTRMod.getDamagingPlayerIncludingUnits(damagesource);
         if (playerSource != null) {
            UUID player = playerSource.func_110124_au();
            int time = PLAYER_HURT_COOLDOWN;
            float totalDamage = f;
            if (this.playerHurtTimes.containsKey(player)) {
               Pair pair = (Pair)this.playerHurtTimes.get(player);
               totalDamage = f + (Float)pair.getRight();
            }

            this.playerHurtTimes.put(player, Pair.of(time, totalDamage));
         }
      }

   }

   public void onDeath(DamageSource damagesource) {
      this.onHurt(damagesource, 0.0F);
      if (!this.theNPC.field_70170_p.field_72995_K) {
         Iterator var2 = this.playerHurtTimes.entrySet().iterator();

         while(var2.hasNext()) {
            Entry e = (Entry)var2.next();
            UUID player = (UUID)e.getKey();
            Pair pair = (Pair)e.getValue();
            float damage = (Float)pair.getRight();
            if (damage >= PLAYER_DAMAGE_THRESHOLD) {
               LOTRLevelData.getData(player).addAchievement(this.theBoss.getBossKillAchievement());
            }
         }
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      NBTTagCompound data = new NBTTagCompound();
      NBTTagList playerHurtTags = new NBTTagList();
      Iterator var4 = this.playerHurtTimes.entrySet().iterator();

      while(var4.hasNext()) {
         Entry entry = (Entry)var4.next();
         UUID player = (UUID)entry.getKey();
         Pair pair = (Pair)entry.getValue();
         int time = (Integer)pair.getLeft();
         float damage = (Float)pair.getRight();
         NBTTagCompound playerTag = new NBTTagCompound();
         playerTag.func_74778_a("UUID", player.toString());
         playerTag.func_74768_a("Time", time);
         playerTag.func_74776_a("Damage", damage);
         playerHurtTags.func_74742_a(playerTag);
      }

      data.func_74782_a("PlayerHurtTimes", playerHurtTags);
      data.func_74757_a("JumpAttack", this.jumpAttack);
      nbt.func_74782_a("NPCBossInfo", data);
   }

   public void readFromNBT(NBTTagCompound nbt) {
      NBTTagCompound data = nbt.func_74775_l("NPCBossInfo");
      if (data != null) {
         NBTTagList playerHurtTags = data.func_150295_c("PlayerHurtTimes", 10);

         for(int i = 0; i < playerHurtTags.func_74745_c(); ++i) {
            NBTTagCompound playerTag = playerHurtTags.func_150305_b(i);
            UUID player = UUID.fromString(playerTag.func_74779_i("UUID"));
            if (player != null) {
               int time = playerTag.func_74762_e("Time");
               float damage = playerTag.func_74760_g("Damage");
               this.playerHurtTimes.put(player, Pair.of(time, damage));
            }
         }

         this.jumpAttack = data.func_74767_n("JumpAttack");
      }

   }
}
