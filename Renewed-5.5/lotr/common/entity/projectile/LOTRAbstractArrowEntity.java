package lotr.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class LOTRAbstractArrowEntity extends AbstractArrowEntity {
   protected LOTRAbstractArrowEntity(EntityType type, World w) {
      super(type, w);
   }

   protected LOTRAbstractArrowEntity(EntityType type, LivingEntity thrower, World w) {
      super(type, thrower, w);
   }

   protected LOTRAbstractArrowEntity(EntityType type, double x, double y, double z, World w) {
      super(type, x, y, z, w);
   }

   public IPacket func_213297_N() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected int calculateImpactDamageIncludingCritical(float baseDamage) {
      int dmgInt = MathHelper.func_76123_f(MathHelper.func_76131_a(baseDamage, 0.0F, 2.14748365E9F));
      if (this.func_70241_g()) {
         long extraDmg = (long)this.field_70146_Z.nextInt(dmgInt / 2 + 2);
         dmgInt = (int)Math.min(extraDmg + (long)dmgInt, 2147483647L);
      }

      return dmgInt;
   }

   protected int getLifespanTicksInGround() {
      return 1200;
   }

   protected void func_225516_i_() {
      ++this.field_70252_j;
      if (this.field_70252_j >= this.getLifespanTicksInGround()) {
         this.func_70106_y();
      }

   }
}
