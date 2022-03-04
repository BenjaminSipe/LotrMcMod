package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIBanditFlee;
import lotr.common.entity.ai.LOTREntityAIBanditSteal;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBandit;
import lotr.common.fac.LOTRFaction;
import lotr.common.inventory.LOTRInventoryNPC;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.item.LOTRItemMug;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class LOTREntityBandit extends LOTREntityMan implements IBandit {
   private static ItemStack[] weapons;
   private LOTRInventoryNPC banditInventory = IBandit.Helper.createInv(this);

   public LOTREntityBandit(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new LOTREntityAIAttackOnCollide(this, 1.5D, false));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIBanditSteal(this, 1.8D));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIBanditFlee(this, 1.5D));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 1.5D));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0F, 0.1F));
      this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0F, 0.05F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
      this.addTargetTasks(true, LOTREntityAINearestAttackableTargetBandit.class);
   }

   public LOTREntityNPC getBanditAsNPC() {
      return this;
   }

   public int getMaxThefts() {
      return 3;
   }

   public LOTRInventoryNPC getBanditInventory() {
      return this.banditInventory;
   }

   public boolean canTargetPlayerForTheft(EntityPlayer player) {
      return true;
   }

   public void setupNPCGender() {
      this.familyInfo.setMale(true);
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      int i = this.field_70146_Z.nextInt(weapons.length);
      this.npcItemsInv.setMeleeWeapon(weapons[i].func_77946_l());
      this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
      if (this.field_70146_Z.nextInt(3) == 0) {
         ItemStack hat = new ItemStack(LOTRMod.leatherHat);
         LOTRItemLeatherHat.setHatColor(hat, 0);
         LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
         this.func_70062_b(4, hat);
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

   public int func_70658_aO() {
      return 10;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.HOSTILE;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      this.banditInventory.writeToNBT(nbt);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.banditInventory.readFromNBT(nbt);
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      int coins;
      for(coins = 0; coins < bones; ++coins) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

      coins = 10 + this.field_70146_Z.nextInt(10) + this.field_70146_Z.nextInt((i + 1) * 10);

      for(int l = 0; l < coins; ++l) {
         this.func_145779_a(LOTRMod.silverCoin, 1);
      }

      if (this.field_70146_Z.nextInt(5) == 0) {
         this.func_70099_a(LOTRItemMug.Vessel.SKULL.getEmptyVessel(), 0.0F);
      }

   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer && !this.banditInventory.isEmpty()) {
         EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killThievingBandit);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.banditInventory.dropAllItems();
      }

   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      return "misc/bandit/hostile";
   }

   public String getTheftSpeechBank(EntityPlayer player) {
      return this.getSpeechBank(player);
   }

   public IChatComponent getTheftChatMsg(EntityPlayer player) {
      return new ChatComponentTranslation("chat.lotr.banditSteal", new Object[0]);
   }

   static {
      weapons = new ItemStack[]{new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerIron)};
   }
}
