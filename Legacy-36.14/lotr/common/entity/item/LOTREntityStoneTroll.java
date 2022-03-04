package lotr.common.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityStoneTroll extends Entity implements LOTRBannerProtectable {
   private float trollHealth = 40.0F;
   public boolean placedByPlayer;
   private int entityAge;

   public LOTREntityStoneTroll(World world) {
      super(world);
      this.func_70105_a(1.6F, 3.2F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(16, (byte)0);
      this.field_70180_af.func_75682_a(17, (byte)0);
   }

   public int getTrollOutfit() {
      return this.field_70180_af.func_75683_a(16);
   }

   public void setTrollOutfit(int i) {
      this.field_70180_af.func_75692_b(16, (byte)i);
   }

   public boolean hasTwoHeads() {
      return this.field_70180_af.func_75683_a(17) == 1;
   }

   public void setHasTwoHeads(boolean flag) {
      this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(flag ? 1 : 0)));
   }

   public void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74776_a("TrollHealth", this.trollHealth);
      nbt.func_74774_a("TrollOutfit", (byte)this.getTrollOutfit());
      nbt.func_74757_a("PlacedByPlayer", this.placedByPlayer);
      nbt.func_74757_a("TwoHeads", this.hasTwoHeads());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.trollHealth = nbt.func_74760_g("TrollHealth");
      this.setTrollOutfit(nbt.func_74771_c("TrollOutfit"));
      this.placedByPlayer = nbt.func_74767_n("PlacedByPlayer");
      this.setHasTwoHeads(nbt.func_74767_n("TwoHeads"));
   }

   public boolean func_70067_L() {
      return true;
   }

   public AxisAlignedBB func_70046_E() {
      return this.field_70121_D;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70181_x -= 0.03999999910593033D;
      this.func_145771_j(this.field_70165_t, (this.field_70121_D.field_72338_b + this.field_70121_D.field_72337_e) / 2.0D, this.field_70161_v);
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      float f = 0.98F;
      if (this.field_70122_E) {
         f = 0.58800006F;
         Block i = this.field_70170_p.func_147439_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));
         if (i.func_149688_o() != Material.field_151579_a) {
            f = i.field_149765_K * 0.98F;
         }
      }

      this.field_70159_w *= (double)f;
      this.field_70181_x *= 0.98D;
      this.field_70179_y *= (double)f;
      if (this.field_70122_E) {
         this.field_70181_x *= -0.5D;
      }

      if (!this.field_70170_p.field_72995_K && !this.placedByPlayer) {
         ++this.entityAge;
         EntityPlayer entityplayer = this.field_70170_p.func_72890_a(this, -1.0D);
         if (entityplayer != null) {
            double d = entityplayer.field_70165_t - this.field_70165_t;
            double d1 = entityplayer.field_70163_u - this.field_70163_u;
            double d2 = entityplayer.field_70161_v - this.field_70161_v;
            double distanceSq = d * d + d1 * d1 + d2 * d2;
            if (distanceSq > 16384.0D) {
               this.func_70106_y();
            }

            if (this.entityAge > 600 && this.field_70146_Z.nextInt(800) == 0 && distanceSq > 1024.0D) {
               this.func_70106_y();
            } else if (distanceSq < 1024.0D) {
               this.entityAge = 0;
            }
         }
      }

   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         if (this.placedByPlayer) {
            if (damagesource.func_76364_f() instanceof EntityPlayer) {
               this.field_70170_p.func_72956_a(this, Blocks.field_150348_b.field_149762_H.func_150495_a(), (Blocks.field_150348_b.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150348_b.field_149762_H.func_150494_d() * 0.8F);
               this.field_70170_p.func_72960_a(this, (byte)17);
               this.func_70106_y();
               EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76364_f();
               if (!entityplayer.field_71075_bZ.field_75098_d) {
                  this.dropAsStatue();
               }

               return true;
            } else {
               return false;
            }
         } else {
            boolean drops = true;
            boolean dropStatue = false;
            EntityPlayer entityplayer;
            if (damagesource.func_76364_f() instanceof EntityPlayer) {
               entityplayer = (EntityPlayer)damagesource.func_76364_f();
               if (entityplayer.field_71075_bZ.field_75098_d) {
                  drops = false;
                  f = this.trollHealth;
               } else {
                  drops = true;
                  ItemStack itemstack = entityplayer.field_71071_by.func_70448_g();
                  if (itemstack != null && itemstack.func_77973_b() instanceof ItemPickaxe) {
                     dropStatue = true;
                     f = 1.0F + (float)entityplayer.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
                  } else {
                     dropStatue = false;
                     f = 1.0F;
                  }

                  if (itemstack != null) {
                     itemstack.func_77972_a(1, entityplayer);
                     if (itemstack.field_77994_a <= 0) {
                        entityplayer.func_71028_bD();
                     }
                  }
               }
            }

            this.trollHealth -= f;
            if (this.trollHealth <= 0.0F) {
               this.field_70170_p.func_72956_a(this, Blocks.field_150348_b.field_149762_H.func_150495_a(), (Blocks.field_150348_b.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150348_b.field_149762_H.func_150494_d() * 0.8F);
               this.field_70170_p.func_72960_a(this, (byte)17);
               if (drops) {
                  if (dropStatue) {
                     if (damagesource.func_76364_f() instanceof EntityPlayer) {
                        entityplayer = (EntityPlayer)damagesource.func_76364_f();
                        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getTrollStatue);
                     }

                     this.dropAsStatue();
                  } else {
                     int stone = 6 + this.field_70146_Z.nextInt(7);

                     for(int l = 0; l < stone; ++l) {
                        this.func_145779_a(Item.func_150898_a(Blocks.field_150347_e), 1);
                     }
                  }
               }

               this.func_70106_y();
            } else {
               this.field_70170_p.func_72956_a(this, Blocks.field_150348_b.field_149762_H.func_150495_a(), (Blocks.field_150348_b.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150348_b.field_149762_H.func_150494_d() * 0.5F);
               this.field_70170_p.func_72960_a(this, (byte)16);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private ItemStack getStatueItem() {
      ItemStack itemstack = new ItemStack(LOTRMod.trollStatue);
      itemstack.func_77964_b(this.getTrollOutfit());
      itemstack.func_77982_d(new NBTTagCompound());
      itemstack.func_77978_p().func_74757_a("TwoHeads", this.hasTwoHeads());
      return itemstack;
   }

   private void dropAsStatue() {
      this.func_70099_a(this.getStatueItem(), 0.0F);
   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte b) {
      int l;
      if (b == 16) {
         for(l = 0; l < 16; ++l) {
            this.field_70170_p.func_72869_a("blockcrack_" + Block.func_149682_b(Blocks.field_150348_b) + "_0", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      } else if (b == 17) {
         for(l = 0; l < 64; ++l) {
            this.field_70170_p.func_72869_a("blockcrack_" + Block.func_149682_b(Blocks.field_150348_b) + "_0", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.func_70103_a(b);
      }

   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return this.getStatueItem();
   }
}
