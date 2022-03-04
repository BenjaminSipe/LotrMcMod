package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRMaterial;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.quest.LOTRMiniQuestFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityHalfTroll extends LOTREntityNPC {
   public LOTREntityHalfTroll(World world) {
      super(world);
      this.func_70105_a(1.0F, 2.4F);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, this.createHalfTrollAttackAI());
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(5, new LOTREntityAIEat(this, LOTRFoods.HALF_TROLL, 6000));
      this.field_70714_bg.func_75776_a(5, new LOTREntityAIDrink(this, LOTRFoods.HALF_TROLL_DRINK, 6000));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      int target = this.addTargetTasks(true);
      this.field_70715_bh.func_75776_a(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 1000, false));
      this.spawnsInDarkness = true;
   }

   public EntityAIBase createHalfTrollAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   public void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getOrcName(this.field_70146_Z));
   }

   private boolean getHalfTrollModelFlag(int part) {
      int i = this.field_70180_af.func_75683_a(17);
      int pow2 = 1 << part;
      return (i & pow2) != 0;
   }

   private void setHalfTrollModelFlag(int part, boolean flag) {
      int i = this.field_70180_af.func_75683_a(17);
      int pow2 = 1 << part;
      int i;
      if (flag) {
         i = i | pow2;
      } else {
         i = i & ~pow2;
      }

      this.field_70180_af.func_75692_b(17, (byte)i);
   }

   public boolean hasMohawk() {
      return this.getHalfTrollModelFlag(1);
   }

   public void setHasMohawk(boolean flag) {
      this.setHalfTrollModelFlag(1, flag);
   }

   public boolean hasHorns() {
      return this.getHalfTrollModelFlag(2);
   }

   public void setHasHorns(boolean flag) {
      this.setHalfTrollModelFlag(2, flag);
   }

   public boolean hasFullHorns() {
      return this.getHalfTrollModelFlag(3);
   }

   public void setHasFullHorns(boolean flag) {
      this.setHalfTrollModelFlag(3, flag);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(35.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcAttackDamage).func_111128_a(6.0D);
      this.func_110148_a(horseAttackSpeed).func_111128_a(1.5D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.setHasMohawk(this.field_70146_Z.nextBoolean());
      if (this.field_70146_Z.nextBoolean()) {
         this.setHasHorns(true);
         this.setHasFullHorns(this.field_70146_Z.nextBoolean());
      }

      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public LOTRFaction getFaction() {
      return LOTRFaction.HALF_TROLL;
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("Mohawk", this.hasMohawk());
      nbt.func_74757_a("Horns", this.hasHorns());
      nbt.func_74757_a("HornsFull", this.hasFullHorns());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setHasMohawk(nbt.func_74767_n("Mohawk"));
      this.setHasHorns(nbt.func_74767_n("Horns"));
      this.setHasFullHorns(nbt.func_74767_n("HornsFull"));
      if (nbt.func_74764_b("HalfTrollName")) {
         this.familyInfo.setName(nbt.func_74779_i("HalfTrollName"));
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int flesh = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      int bones;
      for(bones = 0; bones < flesh; ++bones) {
         this.func_145779_a(Items.field_151078_bh, 1);
      }

      bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(LOTRMod.trollBone, 1);
      }

   }

   public float getAlignmentBonus() {
      return 1.0F;
   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killHalfTroll;
   }

   protected String func_70639_aQ() {
      return "lotr:halfTroll.say";
   }

   protected String func_70621_aR() {
      return "lotr:halfTroll.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:halfTroll.death";
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "halfTroll/halfTroll/hired" : "halfTroll/halfTroll/friendly";
      } else {
         return "halfTroll/halfTroll/hostile";
      }
   }

   public LOTRMiniQuest createMiniQuest() {
      return LOTRMiniQuestFactory.HALF_TROLL.createQuest(this);
   }

   public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
      return LOTRMiniQuestFactory.HALF_TROLL;
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      LOTRInventoryHiredReplacedItems var10001 = this.hiredReplacedInv;
      if (slot != 0) {
         var10001 = this.hiredReplacedInv;
         if (slot != 1) {
            var10001 = this.hiredReplacedInv;
            if (slot != 2) {
               var10001 = this.hiredReplacedInv;
               if (slot != 3) {
                  return super.canReEquipHired(slot, itemstack);
               }
            }
         }
      }

      return itemstack != null && itemstack.func_77973_b() instanceof ItemArmor && ((ItemArmor)itemstack.func_77973_b()).func_82812_d() == LOTRMaterial.HALF_TROLL.toArmorMaterial();
   }
}
