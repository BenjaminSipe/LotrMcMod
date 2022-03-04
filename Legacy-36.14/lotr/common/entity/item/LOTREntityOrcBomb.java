package lotr.common.entity.item;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockOrcBomb;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityOrcBomb extends EntityTNTPrimed {
   public int orcBombFuse;
   public boolean droppedByPlayer;
   public boolean droppedByHiredUnit;
   public boolean droppedTargetingPlayer;

   public LOTREntityOrcBomb(World world) {
      super(world);
   }

   public LOTREntityOrcBomb(World world, double d, double d1, double d2, EntityLivingBase entity) {
      super(world, d, d1, d2, entity);
   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, (byte)0);
   }

   public int getBombStrengthLevel() {
      return this.field_70180_af.func_75683_a(16);
   }

   public void setBombStrengthLevel(int i) {
      this.field_70180_af.func_75692_b(16, (byte)i);
      this.orcBombFuse = 40 + LOTRBlockOrcBomb.getBombStrengthLevel(i) * 20;
   }

   public void setFuseFromExplosion() {
      this.orcBombFuse = this.field_70170_p.field_73012_v.nextInt(this.orcBombFuse / 4) + this.orcBombFuse / 8;
   }

   public void setFuseFromHiredUnit() {
      int strength = LOTRBlockOrcBomb.getBombStrengthLevel(this.getBombStrengthLevel());
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.field_70181_x -= 0.04D;
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      this.field_70159_w *= 0.98D;
      this.field_70181_x *= 0.98D;
      this.field_70179_y *= 0.98D;
      if (this.field_70122_E) {
         this.field_70159_w *= 0.7D;
         this.field_70179_y *= 0.7D;
         this.field_70181_x *= -0.5D;
      }

      --this.orcBombFuse;
      if (this.orcBombFuse <= 0 && !this.field_70170_p.field_72995_K) {
         this.func_70106_y();
         this.explodeOrcBomb();
      } else {
         this.field_70170_p.func_72869_a("smoke", this.field_70165_t, this.field_70163_u + 0.7D, this.field_70161_v, 0.0D, 0.0D, 0.0D);
      }

   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("DroppedByPlayer", this.droppedByPlayer);
      nbt.func_74757_a("DroppedByHiredUnit", this.droppedByHiredUnit);
      nbt.func_74757_a("DroppedTargetingPlayer", this.droppedTargetingPlayer);
      nbt.func_74768_a("BombStrengthLevel", this.getBombStrengthLevel());
      nbt.func_74768_a("OrcBombFuse", this.orcBombFuse);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.droppedByPlayer = nbt.func_74767_n("DroppedByPlayer");
      this.droppedByHiredUnit = nbt.func_74767_n("DroppedByHiredUnit");
      this.droppedTargetingPlayer = nbt.func_74767_n("DroppedTargetingPlayer");
      this.setBombStrengthLevel(nbt.func_74762_e("BombStrengthLevel"));
      this.orcBombFuse = nbt.func_74762_e("OrcBombFuse");
   }

   private void explodeOrcBomb() {
      boolean doTerrainDamage = false;
      if (this.droppedByPlayer) {
         doTerrainDamage = true;
      } else if (this.droppedByHiredUnit) {
         doTerrainDamage = LOTRMod.canGrief(this.field_70170_p);
      } else if (this.droppedTargetingPlayer) {
         doTerrainDamage = LOTRMod.canGrief(this.field_70170_p);
      }

      int meta = this.getBombStrengthLevel();
      int strength = LOTRBlockOrcBomb.getBombStrengthLevel(meta);
      boolean fire = LOTRBlockOrcBomb.isFireBomb(meta);
      this.field_70170_p.func_72885_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)(strength + 1) * 4.0F, fire, doTerrainDamage);
   }
}
