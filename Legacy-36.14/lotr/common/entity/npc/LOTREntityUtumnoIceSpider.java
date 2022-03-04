package lotr.common.entity.npc;

import lotr.common.LOTRDamage;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceSpider extends LOTREntitySpiderBase {
   public LOTREntityUtumnoIceSpider(World world) {
      super(world);
      this.isImmuneToFrost = true;
      this.isChilly = true;
   }

   protected int getRandomSpiderScale() {
      return this.field_70146_Z.nextInt(4);
   }

   protected int getRandomSpiderType() {
      return VENOM_SLOWNESS;
   }

   public LOTRFaction getFaction() {
      return LOTRFaction.UTUMNO;
   }

   protected boolean canRideSpider() {
      return false;
   }

   public boolean func_70652_k(Entity entity) {
      if (super.func_70652_k(entity)) {
         if (entity instanceof EntityPlayerMP) {
            LOTRDamage.doFrostDamage((EntityPlayerMP)entity);
         }

         return true;
      } else {
         return false;
      }
   }
}
