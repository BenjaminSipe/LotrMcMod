package lotr.common.entity.npc;

import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.item.LOTRItemMug;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityBreeRuffian extends LOTREntityBreeMan {
   private static ItemStack[] ruffianWeapons;
   private int ruffianAngerTick;

   public LOTREntityBreeRuffian(World world) {
      super(world);
      int target = this.addTargetTasks(false);
      this.field_70715_bh.func_75776_a(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, EntityPlayer.class, 0, true, new IEntitySelector() {
         public boolean func_82704_a(Entity entity) {
            EntityPlayer player = (EntityPlayer)entity;
            return LOTREntityBreeRuffian.this.canRuffianTarget(player);
         }
      }));
   }

   protected void addBreeAvoidAI(int prio) {
      this.field_70714_bg.func_75776_a(prio, new EntityAIAvoidEntity(this, LOTREntityRanger.class, 12.0F, 1.0D, 1.5D));
      this.field_70714_bg.func_75776_a(prio, new EntityAIAvoidEntity(this, LOTREntityBreeGuard.class, 12.0F, 1.0D, 1.5D));
   }

   public final boolean canRuffianTarget(EntityPlayer player) {
      PotionEffect nausea = player.func_70660_b(Potion.field_76431_k);
      if (nausea != null) {
         int nauseaTime = nausea.func_76459_b() / 20;
         int minNauseaTime = 20;
         int fullNauseaTime = 120;
         float chance = (float)(nauseaTime - minNauseaTime) / (float)(fullNauseaTime - minNauseaTime);
         chance *= 0.05F;
         return this.field_70146_Z.nextFloat() < chance;
      } else {
         return false;
      }
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public void setupNPCName() {
      boolean flag = this.field_70146_Z.nextBoolean();
      if (flag) {
         this.familyInfo.setName(LOTRNames.getDunlendingName(this.field_70146_Z, this.familyInfo.isMale()));
      } else {
         this.familyInfo.setName(LOTRNames.getBreeName(this.field_70146_Z, this.familyInfo.isMale()));
      }

   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(ruffianWeapons.length);
      this.npcItemsInv.setMeleeWeapon(ruffianWeapons[i].func_77946_l());
      if (this.field_70146_Z.nextInt(4) == 0) {
         ItemStack hat = new ItemStack(LOTRMod.leatherHat);
         if (this.field_70146_Z.nextBoolean()) {
            LOTRItemLeatherHat.setHatColor(hat, 0);
         } else {
            LOTRItemLeatherHat.setHatColor(hat, 6834742);
         }

         LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
         this.func_70062_b(4, hat);
      }

      return data;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.RUFFIAN;
   }

   public LOTRFaction getInfluenceZoneFaction() {
      return LOTRFaction.ISENGARD;
   }

   public boolean isCivilianNPC() {
      return false;
   }

   public float getAlignmentBonus() {
      return 0.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int coins = 2 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt((i + 1) * 3);

      for(int l = 0; l < coins; ++l) {
         this.func_145779_a(LOTRMod.silverCoin, 1);
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.func_70099_a(LOTRItemMug.Vessel.SKULL.getEmptyVessel(), 0.0F);
      }

   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.ruffianAngerTick > 0) {
         --this.ruffianAngerTick;
      }

   }

   public boolean lootsExtraCoins() {
      return true;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (!super.func_70097_a(damagesource, f)) {
         return false;
      } else if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityLivingBase) {
         EntityLivingBase attacker = (EntityLivingBase)damagesource.func_76346_g();
         this.ruffianAngerTick += 100;
         double range = (double)this.ruffianAngerTick / 25.0D;
         range = Math.min(range, 24.0D);
         List nearbyRuffians = this.field_70170_p.func_72872_a(LOTREntityBreeRuffian.class, this.field_70121_D.func_72314_b(range, range, range));
         Iterator var7 = nearbyRuffians.iterator();

         while(true) {
            LOTREntityBreeRuffian ruffian;
            do {
               do {
                  if (!var7.hasNext()) {
                     return true;
                  }

                  Object o = var7.next();
                  ruffian = (LOTREntityBreeRuffian)o;
               } while(!ruffian.func_70089_S());
            } while(ruffian.hiredNPCInfo.isActive && ruffian.hiredNPCInfo.getHiringPlayer() == attacker);

            if (ruffian.func_70638_az() == null) {
               ruffian.func_70624_b(attacker);
               if (attacker instanceof EntityPlayer) {
                  EntityPlayer player = (EntityPlayer)attacker;
                  String speech = ruffian.getSpeechBank(player);
                  ruffian.sendSpeechBank(player, speech);
               }
            }
         }
      } else {
         return true;
      }
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "bree/ruffian/hired" : "bree/ruffian/friendly";
      } else {
         return "bree/ruffian/hostile";
      }
   }

   public int getMiniquestColor() {
      return LOTRFaction.ISENGARD.getFactionColor();
   }

   public boolean canPickpocket() {
      return false;
   }

   static {
      ruffianWeapons = new ItemStack[]{new ItemStack(Items.field_151040_l), new ItemStack(Items.field_151040_l), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.battleaxeIron)};
   }
}
