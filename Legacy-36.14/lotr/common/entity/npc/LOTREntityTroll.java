package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetTroll;
import lotr.common.entity.ai.LOTREntityAITrollFleeSun;
import lotr.common.entity.item.LOTREntityStoneTroll;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityTroll extends LOTREntityNPC {
   private int sneeze;
   public int sniffTime;
   public boolean trollImmuneToSun = false;

   public LOTREntityTroll(World world) {
      super(world);
      float f = this.getTrollScale();
      this.func_70105_a(1.6F * f, 3.2F * f);
      this.func_70661_as().func_75491_a(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIRestrictSun(this));
      this.field_70714_bg.func_75776_a(2, new LOTREntityAIHiredRemainStill(this));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAITrollFleeSun(this, 2.5D));
      this.field_70714_bg.func_75776_a(4, this.getTrollAttackAI());
      this.field_70714_bg.func_75776_a(5, new LOTREntityAIFollowHiringPlayer(this));
      this.field_70714_bg.func_75776_a(6, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0F, 0.02F));
      this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(8, new EntityAIWatchClosest(this, EntityLiving.class, 12.0F, 0.01F));
      this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
      this.addTargetTasks(true, LOTREntityAINearestAttackableTargetTroll.class);
      this.spawnsInDarkness = true;
   }

   public float getTrollScale() {
      return 1.0F;
   }

   public EntityAIBase getTrollAttackAI() {
      return new LOTREntityAIAttackOnCollide(this, 1.4D, false);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, (byte)this.field_70146_Z.nextInt(3));
      this.field_70180_af.func_75682_a(17, Short.valueOf((short)-1));
      this.field_70180_af.func_75682_a(18, (byte)0);
      this.field_70180_af.func_75682_a(19, (byte)0);
   }

   public void setupNPCName() {
      this.familyInfo.setName(LOTRNames.getTrollName(this.field_70146_Z));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(60.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(24.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.2D);
      this.func_110148_a(npcAttackDamage).func_111128_a(5.0D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      if (this.field_70146_Z.nextInt(10) == 0) {
         this.setHasTwoHeads(true);
         double maxHealth = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b();
         maxHealth *= 1.5D;
         this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(maxHealth);
         this.func_70606_j(this.func_110138_aP());
         double attack = this.func_110148_a(npcAttackDamage).func_111125_b();
         attack += 3.0D;
         this.func_110148_a(npcAttackDamage).func_111128_a(attack);
         double speed = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b();
         speed *= 1.4D;
         this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(speed);
      }

      return data;
   }

   public int func_70658_aO() {
      return 8;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.ANGMAR;
   }

   protected boolean hasTrollName() {
      return true;
   }

   public String getNPCName() {
      return this.hasTrollName() ? this.familyInfo.getName() : super.getNPCName();
   }

   public int getTrollOutfit() {
      return this.field_70180_af.func_75683_a(16);
   }

   public void setTrollOutfit(int i) {
      this.field_70180_af.func_75692_b(16, (byte)i);
   }

   public int getTrollBurnTime() {
      return this.field_70180_af.func_75693_b(17);
   }

   public void setTrollBurnTime(int i) {
      this.field_70180_af.func_75692_b(17, (short)i);
   }

   public int getSneezingTime() {
      return this.field_70180_af.func_75683_a(18);
   }

   public void setSneezingTime(int i) {
      this.field_70180_af.func_75692_b(18, (byte)i);
   }

   public boolean hasTwoHeads() {
      return this.field_70180_af.func_75683_a(19) == 1;
   }

   public void setHasTwoHeads(boolean flag) {
      this.field_70180_af.func_75692_b(19, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("TrollOutfit", (byte)this.getTrollOutfit());
      nbt.func_74768_a("TrollBurnTime", this.getTrollBurnTime());
      nbt.func_74768_a("Sneeze", this.sneeze);
      nbt.func_74768_a("SneezeTime", this.getSneezingTime());
      nbt.func_74757_a("ImmuneToSun", this.trollImmuneToSun);
      nbt.func_74757_a("TwoHeads", this.hasTwoHeads());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setTrollOutfit(nbt.func_74771_c("TrollOutfit"));
      this.setTrollBurnTime(nbt.func_74762_e("TrollBurnTime"));
      this.sneeze = nbt.func_74762_e("Sneeze");
      this.setSneezingTime(nbt.func_74762_e("SneezeTime"));
      this.trollImmuneToSun = nbt.func_74767_n("ImmuneToSun");
      this.setHasTwoHeads(nbt.func_74767_n("TwoHeads"));
      if (nbt.func_74764_b("TrollName")) {
         this.familyInfo.setName(nbt.func_74779_i("TrollName"));
      }

   }

   protected boolean conquestSpawnIgnoresDarkness() {
      return this.trollImmuneToSun;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.getTrollBurnTime() >= 0 && this.func_70089_S()) {
         if (!this.field_70170_p.field_72995_K) {
            BiomeGenBase biome = this.field_70170_p.func_72807_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70161_v));
            if (!this.trollImmuneToSun && (!(biome instanceof LOTRBiome) || !((LOTRBiome)biome).canSpawnHostilesInDay()) && this.field_70170_p.func_72935_r() && this.field_70170_p.func_72937_j(MathHelper.func_76128_c(this.field_70165_t), (int)this.field_70121_D.field_72338_b, MathHelper.func_76128_c(this.field_70161_v))) {
               this.setTrollBurnTime(this.getTrollBurnTime() - 1);
               if (this.getTrollBurnTime() == 0) {
                  this.onTrollDeathBySun();
                  if (this.hiredNPCInfo.isActive && this.hiredNPCInfo.getHiringPlayer() != null) {
                     this.hiredNPCInfo.getHiringPlayer().func_145747_a(new ChatComponentTranslation("lotr.hiredNPC.trollStone", new Object[]{this.func_70005_c_()}));
                  }
               }
            } else {
               this.setTrollBurnTime(-1);
            }
         } else {
            this.field_70170_p.func_72869_a("largesmoke", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      }

      if (this.sniffTime > 0) {
         --this.sniffTime;
      }

      if (!this.field_70170_p.field_72995_K && this.getSneezingTime() > 0) {
         this.setSneezingTime(this.getSneezingTime() - 1);
         if (this.getSneezingTime() == 8) {
            this.field_70170_p.func_72956_a(this, "lotr:troll.sneeze", this.func_70599_aP() * 1.5F, this.func_70647_i());
         }

         if (this.getSneezingTime() == 4) {
            int slimes = 2 + this.field_70146_Z.nextInt(3);

            for(int i = 0; i < slimes; ++i) {
               EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v, new ItemStack(Items.field_151123_aH));
               entityitem.field_145804_b = 40;
               float f = 1.0F;
               entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70759_as / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F) * f);
               entityitem.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70759_as / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F) * f);
               entityitem.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.1415927F) * f + 0.1F);
               f = 0.02F;
               float f1 = this.field_70146_Z.nextFloat() * 3.1415927F * 2.0F;
               f *= this.field_70146_Z.nextFloat();
               entityitem.field_70159_w += Math.cos((double)f1) * (double)f;
               entityitem.field_70181_x += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
               entityitem.field_70179_y += Math.sin((double)f1) * (double)f;
               this.field_70170_p.func_72838_d(entityitem);
            }
         }

         if (this.getSneezingTime() == 0) {
            this.sneeze = 0;
         }
      }

   }

   public void onTrollDeathBySun() {
      this.field_70170_p.func_72956_a(this, "lotr:troll.transform", this.func_70599_aP(), this.func_70647_i());
      this.field_70170_p.func_72960_a(this, (byte)15);
      this.func_70106_y();
      LOTREntityStoneTroll stoneTroll = new LOTREntityStoneTroll(this.field_70170_p);
      stoneTroll.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
      stoneTroll.setTrollOutfit(this.getTrollOutfit());
      stoneTroll.setHasTwoHeads(this.hasTwoHeads());
      this.field_70170_p.func_72838_d(stoneTroll);
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      if (b == 15) {
         this.func_70656_aK();
      } else if (b == 16) {
         this.sniffTime = 16;
      } else {
         super.func_70103_a(b);
      }

   }

   public boolean func_70085_c(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K && this.canTrollBeTickled(entityplayer)) {
         ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
         if (itemstack != null && LOTRMod.isOreNameEqual(itemstack, "feather") && this.getSneezingTime() == 0) {
            if (this.field_70146_Z.nextBoolean()) {
               ++this.sneeze;
            }

            if (!entityplayer.field_71075_bZ.field_75098_d) {
               --itemstack.field_77994_a;
            }

            if (itemstack.field_77994_a <= 0) {
               entityplayer.field_71071_by.func_70299_a(entityplayer.field_71071_by.field_70461_c, (ItemStack)null);
            }

            this.npcTalkTick = this.getNPCTalkInterval() / 2;
            if (this.sneeze >= 3) {
               this.setSneezingTime(16);
               LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.makeTrollSneeze);
            } else {
               LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, "troll/tickle", entityplayer));
               this.field_70170_p.func_72956_a(this, "lotr:troll.sniff", this.func_70599_aP(), this.func_70647_i());
               this.field_70170_p.func_72960_a(this, (byte)16);
            }
         }
      }

      return super.func_70085_c(entityplayer);
   }

   protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
      return this.canNPCTalk() && this.isFriendlyAndAligned(entityplayer) && LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F && this.func_70638_az() == null && this.getTrollBurnTime() == -1;
   }

   public void func_70653_a(Entity entity, float f, double d, double d1) {
      super.func_70653_a(entity, f, d, d1);
      this.field_70159_w /= 2.0D;
      this.field_70181_x /= 2.0D;
      this.field_70179_y /= 2.0D;
   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         float attackDamage = (float)this.func_110148_a(LOTREntityNPC.npcAttackDamage).func_111126_e();
         float knockbackModifier = 0.25F * attackDamage;
         entity.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F), (double)knockbackModifier * 0.1D, (double)(MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * knockbackModifier * 0.5F));
         return true;
      } else {
         return false;
      }
   }

   public void func_70645_a(DamageSource damagesource) {
      super.func_70645_a(damagesource);
      if (!this.field_70170_p.field_72995_K && damagesource.func_76346_g() instanceof EntityPlayer && this.getTrollBurnTime() >= 0) {
         LOTRLevelData.getData((EntityPlayer)damagesource.func_76346_g()).addAchievement(LOTRAchievement.killTrollFleeingSun);
      }

   }

   protected LOTRAchievement getKillAchievement() {
      return LOTRAchievement.killTroll;
   }

   public float getAlignmentBonus() {
      return 3.0F;
   }

   protected int func_70693_a(EntityPlayer entityplayer) {
      return 4 + this.field_70146_Z.nextInt(5);
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int bones = 2 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(int l = 0; l < bones; ++l) {
         this.func_145779_a(LOTRMod.trollBone, 1);
      }

      this.dropTrollItems(flag, i);
   }

   public void dropTrollItems(boolean flag, int i) {
      int animalDrops;
      int l;
      if (this.field_70146_Z.nextInt(3) == 0) {
         animalDrops = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

         for(l = 0; l < animalDrops; ++l) {
            this.func_145779_a(Items.field_151123_aH, 1);
         }
      }

      animalDrops = 1 + this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(i + 1);

      for(l = 0; l < animalDrops; ++l) {
         int drop = this.field_70146_Z.nextInt(10);
         switch(drop) {
         case 0:
            this.func_70099_a(new ItemStack(Items.field_151116_aA, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 1:
            this.func_70099_a(new ItemStack(Items.field_151082_bd, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
            break;
         case 2:
            this.func_70099_a(new ItemStack(Items.field_151076_bf, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
            break;
         case 3:
            this.func_70099_a(new ItemStack(Items.field_151008_G, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 4:
            this.func_70099_a(new ItemStack(Items.field_151147_al, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
            break;
         case 5:
            this.func_70099_a(new ItemStack(Blocks.field_150325_L, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 6:
            this.func_70099_a(new ItemStack(Items.field_151078_bh, 1 + this.field_70146_Z.nextInt(3)), 0.0F);
            break;
         case 7:
            this.func_70099_a(new ItemStack(LOTRMod.rabbitRaw, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
            break;
         case 8:
            this.func_70099_a(new ItemStack(LOTRMod.muttonRaw, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
            break;
         case 9:
            this.func_70099_a(new ItemStack(LOTRMod.deerRaw, 1 + this.field_70146_Z.nextInt(2)), 0.0F);
         }
      }

   }

   public String func_70639_aQ() {
      return "lotr:troll.say";
   }

   public String func_70621_aR() {
      return "lotr:troll.say";
   }

   public String func_70673_aS() {
      return "lotr:troll.say";
   }

   protected float func_70599_aP() {
      return 1.5F;
   }

   protected void func_145780_a(int i, int j, int k, Block block) {
      this.func_85030_a("lotr:troll.step", 0.75F, this.func_70647_i());
   }

   public String getSpeechBank(EntityPlayer entityplayer) {
      if (this.getTrollBurnTime() >= 0) {
         return null;
      } else if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0F && this.isFriendlyAndAligned(entityplayer)) {
         return this.hiredNPCInfo.getHiringPlayer() == entityplayer ? "troll/hired" : "troll/friendly";
      } else {
         return "troll/hostile";
      }
   }

   public boolean shouldRenderHeadHurt() {
      return this.field_70737_aN > 0 || this.getSneezingTime() > 0;
   }

   public boolean canReEquipHired(int slot, ItemStack itemstack) {
      return false;
   }
}
