package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAIHobbitTargetRuffian;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.projectile.LOTREntityPebble;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHobbitBounder extends LOTREntityHobbit {
   public EntityAIBase rangedAttackAI = this.createHobbitRangedAttackAI();
   public EntityAIBase meleeAttackAI = this.createHobbitMeleeAttackAI();

   public LOTREntityHobbitBounder(World world) {
      super(world);
      this.field_70714_bg.field_75782_a.clear();
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      int target = this.addTargetTasks(true);
      this.field_70715_bh.func_75776_a(target + 1, new LOTREntityAIHobbitTargetRuffian(this, LOTREntityBreeRuffian.class, 0, true));
      this.spawnRidingHorse = this.field_70146_Z.nextInt(3) == 0;
   }

   public EntityAIBase createHobbitRangedAttackAI() {
      return new LOTREntityAIRangedAttack(this, 1.5D, 20, 40, 12.0F);
   }

   public EntityAIBase createHobbitMeleeAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.5D, false);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(horseAttackSpeed).func_111128_a(2.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(3);
      if (i != 0 && i != 1) {
         if (i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
         }
      } else {
         this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
      }

      this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.sling));
      this.npcItemsInv.setIdleItem((ItemStack)null);
      ItemStack hat = new ItemStack(LOTRMod.leatherHat);
      LOTRItemLeatherHat.setHatColor(hat, 6834742);
      LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
      this.func_70062_b(4, hat);
      return data;
   }

   public LOTRNPCMount createMountToRide() {
      return new LOTREntityShirePony(this.field_70170_p);
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      }

      if (mode == LOTREntityNPC.AttackMode.MELEE) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.meleeAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

      if (mode == LOTREntityNPC.AttackMode.RANGED) {
         this.field_70714_bg.func_85156_a(this.meleeAttackAI);
         this.field_70714_bg.func_85156_a(this.rangedAttackAI);
         this.field_70714_bg.func_75776_a(2, this.rangedAttackAI);
         this.func_70062_b(0, this.npcItemsInv.getRangedWeapon());
      }

   }

   public void func_82196_d(EntityLivingBase target, float f) {
      EntityArrow template = new EntityArrow(this.field_70170_p, this, target, 1.0F, 0.5F);
      LOTREntityPebble pebble = (new LOTREntityPebble(this.field_70170_p, this)).setSling();
      pebble.func_70012_b(template.field_70165_t, template.field_70163_u, template.field_70161_v, template.field_70177_z, template.field_70125_A);
      pebble.field_70159_w = template.field_70159_w;
      pebble.field_70181_x = template.field_70181_x;
      pebble.field_70179_y = template.field_70179_y;
      this.func_85030_a("random.bow", 1.0F, 1.0F / (this.field_70146_Z.nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d(pebble);
   }

   public float getAlignmentBonus() {
      return 2.0F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int dropChance = 10 - i * 2;
      if (dropChance < 1) {
         dropChance = 1;
      }

      if (this.field_70146_Z.nextInt(dropChance) == 0) {
         this.func_145779_a(LOTRMod.pebble, 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1));
      }

   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 2 + this.field_70146_Z.nextInt(3);
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "hobbit/bounder/hired" : "hobbit/bounder/friendly";
      } else {
         return "hobbit/bounder/hostile";
      }
   }
}
