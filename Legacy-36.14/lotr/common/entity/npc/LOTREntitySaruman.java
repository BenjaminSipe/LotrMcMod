package lotr.common.entity.npc;

import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LOTREntitySaruman extends LOTREntityNPC {
   private LOTREntityRabbit targetingRabbit;
   private int ticksChasingRabbit;
   private String randomNameTag;

   public LOTREntitySaruman(World world) {
      super(world);
      this.func_70105_a(0.6F, 1.8F);
      this.func_70661_as().func_75491_a(true);
      this.func_70661_as().func_75498_b(true);
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 1.0D));
      this.field_70714_bg.func_75776_a(3, new LOTREntityAIEat(this, new LOTRFoods(new ItemStack[]{new ItemStack(Blocks.field_150364_r)}), 200));
      this.field_70714_bg.func_75776_a(4, new EntityAIWatchClosest(this, EntityLivingBase.class, 20.0F, 0.05F));
      this.field_70714_bg.func_75776_a(5, new EntityAILookIdle(this));
   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
   }

   public IEntityLivingData func_110161_a(IEntityLivingData data) {
      data = super.func_110161_a(data);
      this.func_70062_b(0, new ItemStack(LOTRMod.gandalfStaffWhite));
      return data;
   }

   public String func_94057_bL() {
      if (this.randomNameTag == null) {
         StringBuilder tmp = new StringBuilder();

         for(int l = 0; l < 100; ++l) {
            tmp.append((char)this.field_70146_Z.nextInt(1000));
         }

         this.randomNameTag = tmp.toString();
      }

      return this.randomNameTag;
   }

   public boolean func_94056_bM() {
      return true;
   }

   public boolean func_94062_bN() {
      return true;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.ISENGARD;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (!this.field_70170_p.field_72995_K) {
         if (this.field_70146_Z.nextInt(10) == 0) {
            this.func_85030_a(this.func_70639_aQ(), this.func_70599_aP(), this.func_70647_i());
         }

         List allMobsExcludingRabbits = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(24.0D, 24.0D, 24.0D));

         for(int i = 0; i < allMobsExcludingRabbits.size(); ++i) {
            Entity entity = (Entity)allMobsExcludingRabbits.get(i);
            if (!(entity instanceof LOTREntityRabbit) && !(entity instanceof LOTREntityGandalf)) {
               double dSq = this.func_70068_e(entity);
               if (dSq <= 0.0D) {
                  dSq = 1.0E-5D;
               }

               float strength = 1.0F;
               if (entity instanceof EntityPlayer) {
                  strength /= 3.0F;
               }

               double force = (double)strength / dSq;
               double x = entity.field_70165_t - this.field_70165_t;
               double y = entity.field_70163_u - this.field_70163_u;
               double z = entity.field_70161_v - this.field_70161_v;
               x *= force;
               y *= force;
               z *= force;
               if (entity instanceof EntityPlayerMP) {
                  ((EntityPlayerMP)entity).field_71135_a.func_147359_a(new S27PacketExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0F, new ArrayList(), Vec3.func_72443_a(x, y, z)));
               } else {
                  entity.field_70159_w += x;
                  entity.field_70181_x += y;
                  entity.field_70179_y += z;
               }
            }
         }

         if (this.field_70146_Z.nextInt(40) == 0) {
            LOTREntityRabbit rabbit = new LOTREntityRabbit(this.field_70170_p);
            int i = MathHelper.func_76128_c(this.field_70165_t) - this.field_70146_Z.nextInt(16) + this.field_70146_Z.nextInt(16);
            int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - this.field_70146_Z.nextInt(8) + this.field_70146_Z.nextInt(8);
            int k = MathHelper.func_76128_c(this.field_70161_v) - this.field_70146_Z.nextInt(16) + this.field_70146_Z.nextInt(16);
            rabbit.func_70012_b((double)i, (double)j, (double)k, 0.0F, 0.0F);
            AxisAlignedBB aabb = rabbit.field_70121_D;
            if (this.field_70170_p.func_72855_b(aabb) && this.field_70170_p.func_72945_a(rabbit, aabb).isEmpty() && !this.field_70170_p.func_72953_d(aabb)) {
               this.field_70170_p.func_72838_d(rabbit);
            }
         }

         if (this.targetingRabbit == null && this.field_70146_Z.nextInt(20) == 0) {
            List rabbits = this.field_70170_p.func_72872_a(LOTREntityRabbit.class, this.field_70121_D.func_72314_b(24.0D, 24.0D, 24.0D));
            if (!rabbits.isEmpty()) {
               LOTREntityRabbit rabbit = (LOTREntityRabbit)rabbits.get(this.field_70146_Z.nextInt(rabbits.size()));
               if (rabbit.field_70154_o == null) {
                  this.targetingRabbit = rabbit;
               }
            }
         }

         if (this.targetingRabbit != null) {
            if (!this.targetingRabbit.func_70089_S()) {
               this.targetingRabbit = null;
            } else {
               this.func_70661_as().func_75497_a(this.targetingRabbit, 1.0D);
               if ((double)this.func_70032_d(this.targetingRabbit) < 1.0D) {
                  Object entityToMount;
                  for(entityToMount = this; ((Entity)entityToMount).field_70153_n != null; entityToMount = ((Entity)entityToMount).field_70153_n) {
                  }

                  this.targetingRabbit.func_70078_a((Entity)entityToMount);
                  this.targetingRabbit = null;
               }
            }
         }
      }

   }

   protected String func_70639_aQ() {
      return "lotr:orc.say";
   }

   public int func_70627_aG() {
      return 10;
   }

   protected void func_70628_a(boolean flag, int i) {
      super.func_70628_a(flag, i);
      int j = this.field_70146_Z.nextInt(2) + this.field_70146_Z.nextInt(i + 1);

      for(int k = 0; k < j; ++k) {
         this.func_145779_a(Items.field_151103_aS, 1);
      }

   }
}
