package lotr.common.block;

import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTRBlockHithlainRope extends LOTRBlockRope {
   public LOTRBlockHithlainRope() {
      super(true);
      this.func_149715_a(0.375F);
   }

   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
      if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70617_f_()) {
         LOTRFaction ropeFaction = LOTRFaction.LOTHLORIEN;
         boolean harm = false;
         if (entity instanceof EntityPlayer) {
            harm = LOTRLevelData.getData((EntityPlayer)entity).getAlignment(ropeFaction) < 0.0F;
         } else {
            harm = LOTRMod.getNPCFaction(entity).isBadRelation(ropeFaction);
         }

         if (harm) {
            entity.func_70097_a(DamageSource.field_76376_m, 1.0F);
         }
      }

   }
}
