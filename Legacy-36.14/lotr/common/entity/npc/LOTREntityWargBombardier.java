package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIWargBombardierAttack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class LOTREntityWargBombardier extends LOTREntityWarg {
   public LOTREntityWargBombardier(World world) {
      super(world);
   }

   public EntityAIBase getWargAttackAI() {
      return new LOTREntityAIWargBombardierAttack(this, 1.7D);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(21, (byte)35);
      this.field_70180_af.func_75682_a(22, (byte)1);
   }

   public int getBombFuse() {
      return this.field_70180_af.func_75683_a(21);
   }

   public void setBombFuse(int i) {
      this.field_70180_af.func_75692_b(21, (byte)i);
   }

   public int getBombStrengthLevel() {
      return this.field_70180_af.func_75683_a(22);
   }

   public void setBombStrengthLevel(int i) {
      this.field_70180_af.func_75692_b(22, (byte)i);
   }

   public LOTREntityNPC createWargRider() {
      return null;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74774_a("BombFuse", (byte)this.getBombFuse());
      nbt.func_74774_a("BombStrengthLevel", (byte)this.getBombStrengthLevel());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setBombFuse(nbt.func_74771_c("BombFuse"));
      this.setBombStrengthLevel(nbt.func_74771_c("BombStrengthLevel"));
   }

   public boolean canWargBeRidden() {
      return false;
   }

   public boolean isMountSaddled() {
      return false;
   }

   public void func_70636_d() {
      super.func_70636_d();
      if (this.getBombFuse() < 35) {
         this.field_70170_p.func_72869_a("smoke", this.field_70165_t, this.field_70163_u + 2.2D, this.field_70161_v, 0.0D, 0.0D, 0.0D);
      }

   }

   public void setAttackTarget(EntityLivingBase target, boolean speak) {
      super.setAttackTarget(target, speak);
      if (target != null) {
         this.field_70170_p.func_72956_a(this, "game.tnt.primed", 1.0F, 1.0F);
      }

   }
}
