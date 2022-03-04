package lotr.common.entity.npc;

import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.ai.LOTREntityAIFarm;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAINPCHurtByTarget;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityHaradSlave extends LOTREntityMan implements LOTRFarmhand {
   public Item seedsItem;

   public LOTREntityHaradSlave(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.3D, false));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIFarm(this, 1.0D, 1.0F));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIEat(this, LOTRFoods.HARAD_SLAVE, 12000));
      this.field_70714_bg.func_75776_a(6, new LOTREntityAIDrink(this, LOTRFoods.HARAD_SLAVE_DRINK, 8000));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.field_70715_bh.field_75782_a.clear();
      this.field_70715_bh.func_75776_a(1, new LOTREntityAINPCHurtByTarget(this, false));
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   public void setupNPCName() {
      this.field_70180_af.func_75682_a(20, (byte)0);
      float f = this.field_70146_Z.nextFloat();
      if (f < 0.05F) {
         this.setSlaveType(LOTREntityHaradSlave.SlaveType.TAURETHRIM);
      } else if (f < 0.2F) {
         this.setSlaveType(LOTREntityHaradSlave.SlaveType.MORWAITH);
      } else if (f < 0.7F) {
         this.setSlaveType(LOTREntityHaradSlave.SlaveType.NEAR_HARAD);
      } else {
         this.setSlaveType(LOTREntityHaradSlave.SlaveType.GONDOR);
      }

      LOTREntityHaradSlave.SlaveType type = this.getSlaveType();
      if (type == LOTREntityHaradSlave.SlaveType.GONDOR) {
         this.familyInfo.setName(LOTRNames.getGondorName(this.field_70146_Z, this.familyInfo.isMale()));
      } else if (type == LOTREntityHaradSlave.SlaveType.NEAR_HARAD) {
         if (this.field_70146_Z.nextBoolean()) {
            this.familyInfo.setName(LOTRNames.getHarnennorName(this.field_70146_Z, this.familyInfo.isMale()));
         } else {
            this.familyInfo.setName(LOTRNames.getNomadName(this.field_70146_Z, this.familyInfo.isMale()));
         }
      } else if (type == LOTREntityHaradSlave.SlaveType.MORWAITH) {
         this.familyInfo.setName(LOTRNames.getMoredainName(this.field_70146_Z, this.familyInfo.isMale()));
      } else if (type == LOTREntityHaradSlave.SlaveType.TAURETHRIM) {
         this.familyInfo.setName(LOTRNames.getTauredainName(this.field_70146_Z, this.familyInfo.isMale()));
      }

   }

   public LOTREntityHaradSlave.SlaveType getSlaveType() {
      int i = this.field_70180_af.func_75683_a(20);
      int i = MathHelper.func_76125_a(i, 0, LOTREntityHaradSlave.SlaveType.values().length);
      return LOTREntityHaradSlave.SlaveType.values()[i];
   }

   public void setSlaveType(LOTREntityHaradSlave.SlaveType t) {
      int i = t.ordinal();
      this.field_70180_af.func_75692_b(20, (byte)i);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeBronze));
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      return data;
   }

   public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
      if (mode == LOTREntityNPC.AttackMode.IDLE) {
         this.func_70062_b(0, this.npcItemsInv.getIdleItem());
      } else {
         this.func_70062_b(0, this.npcItemsInv.getMeleeWeapon());
      }

   }

   public IPlantable getUnhiredSeeds() {
      return this.seedsItem == null ? (IPlantable)Items.field_151014_N : (IPlantable)this.seedsItem;
   }

   public LOTRFaction getFaction() {
      return this.getSlaveType().faction;
   }

   public LOTRFaction getHiringFaction() {
      return LOTRFaction.NEAR_HARAD;
   }

   public boolean canBeFreelyTargetedBy(EntityLiving attacker) {
      return !LOTRMod.getNPCFaction(attacker).isBadRelation(this.getHiringFaction()) ? false : super.canBeFreelyTargetedBy(attacker);
   }

   public String getNPCName() {
      return this.familyInfo.getName();
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74778_a("SlaveType", this.getSlaveType().saveName());
      if (this.seedsItem != null) {
         nbt.func_74768_a("SeedsID", Item.func_150891_b(this.seedsItem));
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("SlaveType")) {
         LOTREntityHaradSlave.SlaveType type = LOTREntityHaradSlave.SlaveType.forName(nbt.func_74779_i("SlaveType"));
         if (type != null) {
            this.setSlaveType(type);
         }
      }

      if (nbt.func_74764_b("SeedsID")) {
         Item item = Item.func_150899_d(nbt.func_74762_e("SeedsID"));
         if (item != null && item instanceof IPlantable) {
            this.seedsItem = item;
         }
      }

   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "nearHarad/slave/hired" : "nearHarad/slave/neutral";
   }

   public static enum SlaveType {
      GONDOR(LOTRFaction.GONDOR, "gondor"),
      NEAR_HARAD(LOTRFaction.NEAR_HARAD, "nearHarad"),
      MORWAITH(LOTRFaction.MORWAITH, "morwaith"),
      TAURETHRIM(LOTRFaction.TAURETHRIM, "taurethrim");

      public LOTRFaction faction;
      public String skinDir;

      private SlaveType(LOTRFaction f, String s) {
         this.faction = f;
         this.skinDir = s;
      }

      public String saveName() {
         return this.name();
      }

      public static LOTREntityHaradSlave.SlaveType forName(String s) {
         LOTREntityHaradSlave.SlaveType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTREntityHaradSlave.SlaveType type = var1[var3];
            if (type.saveName().equals(s)) {
               return type;
            }
         }

         return null;
      }
   }
}
