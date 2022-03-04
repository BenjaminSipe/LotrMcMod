package lotr.common.entity.npc;

import java.util.List;
import lotr.common.LOTRConfig;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetOrc;
import lotr.common.entity.ai.LOTREntityAIOrcAvoidGoodPlayer;
import lotr.common.entity.ai.LOTREntityAIOrcSkirmish;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.item.LOTRItemMug;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTREntityOrc extends LOTREntityNPC {
   public boolean isWeakOrc = true;
   private int orcSkirmishTick;
   public EntityLivingBase currentRevengeTarget;

   public LOTREntityOrc(World world) {
      super(world);
      this.func_70105_a(0.5F, 1.55F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIAvoidEntity(this, LOTREntityOrcBomb.class, 12.0F, 1.5D, 2.0D));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIOrcAvoidGoodPlayer(this, 8.0F, 1.5D));
      this.field_70714_bg.func_75776_a(4, this.createOrcAttackAI());
      this.field_70714_bg.func_75776_a(5, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(6, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(7, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(8, new LOTREntityAIEat(this, LOTRFoods.ORC, 6000));
      this.field_70714_bg.func_75776_a(8, new LOTREntityAIDrink(this, LOTRFoods.ORC_DRINK, 6000));
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.05F));
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.05F));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(11, new EntityAILookIdle(this));
      int target = this.addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
      this.field_70715_bh.func_75776_a(target + 1, new LOTREntityAIOrcSkirmish(this, true));
      if (!this.isOrcBombardier()) {
         this.field_70715_bh.func_75776_a(target + 2, new LOTREntityAINearestAttackableTargetOrc(this, LOTREntityRabbit.class, 2000, false));
      }

      this.spawnsInDarkness = true;
   }

   public abstract EntityAIBase createOrcAttackAI();

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(17, -1);
   }

   public boolean isOrcBombardier() {
      return false;
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getOrcName(this.field_70146_Z));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(18.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      return data;
   }

   protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (this.npcItemsInv.getBomb() != null) {
         this.func_70062_b(0, this.npcItemsInv.getBombingItem());
      } else if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public int func_70658_aO() {
      return this.isWeakOrc ? MathHelper.func_76128_c((double)super.func_70658_aO() * 0.75D) : super.func_70658_aO();
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public void func_70604_c(EntityLivingBase entity) {
      super.func_70604_c(entity);
      if (entity != null) {
         this.currentRevengeTarget = entity;
      }

   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K && this.func_70638_az() == null) {
         this.currentRevengeTarget = null;
      }

      int meta;
      if (!this.field_70170_p.field_72995_K && this.isWeakOrc) {
         int i = MathHelper.func_76128_c(this.field_70165_t);
         meta = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
         boolean flag = this.field_70170_p.func_72935_r() && this.field_70170_p.func_72937_j(i, meta, k);
         if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
            flag = false;
         }

         if (flag && this.field_70173_aa % 20 == 0) {
            this.func_70690_d(new PotionEffect(Potion.field_76429_m.field_76415_H, 200, -1));
            this.func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, 200));
         }
      }

      if (!this.field_70170_p.field_72995_K && this.isOrcSkirmishing()) {
         if (!LOTRConfig.enableOrcSkirmish) {
            this.orcSkirmishTick = 0;
         } else if (!(this.func_70638_az() instanceof LOTREntityOrc)) {
            --this.orcSkirmishTick;
         }
      }

      if (this.isOrcBombardier()) {
         if (!this.field_70170_p.field_72995_K) {
            ItemStack bomb = this.npcItemsInv.getBomb();
            meta = -1;
            if (bomb != null && Block.func_149634_a(bomb.func_77973_b()) instanceof LOTRBlockOrcBomb) {
               meta = bomb.func_77960_j();
            }

            this.field_70180_af.func_75692_b(17, (byte)meta);
         } else {
            int meta = this.field_70180_af.func_75683_a(17);
            if (meta == -1) {
               this.npcItemsInv.setBomb((ItemStack)null);
            } else {
               this.npcItemsInv.setBomb(new ItemStack(LOTRMod.orcBomb, 1, meta));
            }
         }
      }

   }

   public boolean canOrcSkirmish() {
      return !this.questInfo.anyActiveQuestPlayers();
   }

   public boolean isOrcSkirmishing() {
      return this.orcSkirmishTick > 0;
   }

   public void setOrcSkirmishing() {
      int prevSkirmishTick = this.orcSkirmishTick;
      this.orcSkirmishTick = 160;
      if (!this.field_70170_p.field_72995_K && prevSkirmishTick == 0) {
         List nearbyPlayers = this.field_70170_p.func_72872_a(EntityPlayer.class, this.field_70121_D.func_72314_b(24.0D, 24.0D, 24.0D));

         for(int i = 0; i < nearbyPlayers.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)nearbyPlayers.get(i);
            LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, this.getOrcSkirmishSpeech(), entityplayer));
         }
      }

   }

   protected String getOrcSkirmishSpeech() {
      return "";
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("OrcSkirmish", this.orcSkirmishTick);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("OrcName")) {
         this.familyInfo.setName(nbt.func_74779_i("OrcName"));
      }

      this.orcSkirmishTick = nbt.func_74762_e("OrcSkirmish");
   }

   protected float getPoisonedArrowChance() {
      return 0.06666667F;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int flesh = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      int bones;
      for(bones = 0; bones < flesh; ++bones) {
         this.func_145779_a(Items.field_151078_bh, 1);
      }

      bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      int rareDropChance;
      for(rareDropChance = 0; rareDropChance < bones; ++rareDropChance) {
         this.func_145779_a(LOTRMod.orcBone, 1);
      }

      int dropType;
      if (this.field_70146_Z.nextInt(10) == 0) {
         rareDropChance = 1 + this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

         for(dropType = 0; dropType < rareDropChance; ++dropType) {
            this.func_145779_a(LOTRMod.maggotyBread, 1);
         }
      }

      if (flag) {
         rareDropChance = 20 - i * 4;
         rareDropChance = Math.max(rareDropChance, 1);
         if (this.field_70146_Z.nextInt(rareDropChance) == 0) {
            dropType = this.field_70146_Z.nextInt(2);
            if (dropType == 0) {
               ItemStack orcDrink = new ItemStack(LOTRMod.mugOrcDraught);
               orcDrink.func_77964_b(1 + this.field_70146_Z.nextInt(3));
               LOTRItemMug.setVessel(orcDrink, LOTRFoods.ORC_DRINK.getRandomVessel(this.field_70146_Z), true);
               this.func_70099_a(orcDrink, 0.0F);
            } else if (dropType == 1) {
               int ingots = 1 + this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

               for(int l = 0; l < ingots; ++l) {
                  if (!(this instanceof LOTREntityUrukHai) && !(this instanceof LOTREntityGundabadUruk)) {
                     if (this instanceof LOTREntityBlackUruk) {
                        this.func_145779_a(LOTRMod.blackUrukSteel, 1);
                     } else {
                        this.func_145779_a(LOTRMod.orcSteel, 1);
                     }
                  } else {
                     this.func_145779_a(LOTRMod.urukSteel, 1);
                  }
               }
            }
         }
      }

      this.dropOrcItems(flag, i);
   }

   protected void dropOrcItems(boolean flag, int i) {
   }

   public boolean func_70601_bi() {
      if (super.func_70601_bi()) {
         if (this.liftSpawnRestrictions) {
            return true;
         } else {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            BiomeGenBase biome = this.field_70170_p.func_72807_a(i, k);
            if (biome instanceof LOTRBiome && ((LOTRBiome)biome).isDwarvenBiome(this.field_70170_p)) {
               return this.field_70170_p.func_147439_a(i, j - 1, k) == biome.field_76752_A;
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   protected String func_70639_aQ() {
      return "lotr:orc.say";
   }

   protected String func_70621_aR() {
      return "lotr:orc.hurt";
   }

   protected String func_70673_aS() {
      return "lotr:orc.death";
   }

   public ItemStack getHeldItemLeft() {
      return this.isOrcBombardier() && this.npcItemsInv.getBomb() != null ? this.npcItemsInv.getBomb() : super.getHeldItemLeft();
   }
}
