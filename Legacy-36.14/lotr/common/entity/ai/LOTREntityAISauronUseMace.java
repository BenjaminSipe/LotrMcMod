package lotr.common.entity.ai;

import java.util.List;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntitySauron;
import lotr.common.item.LOTRItemSauronMace;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTREntityAISauronUseMace extends EntityAIBase {
   private LOTREntitySauron theSauron;
   private int attackTick = 0;
   private World theWorld;

   public LOTREntityAISauronUseMace(LOTREntitySauron sauron) {
      this.theSauron = sauron;
      this.theWorld = this.theSauron.field_70170_p;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      int targets = 0;
      List nearbyEntities = this.theWorld.func_72872_a(EntityLivingBase.class, this.theSauron.field_70121_D.func_72314_b(6.0D, 6.0D, 6.0D));

      for(int i = 0; i < nearbyEntities.size(); ++i) {
         EntityLivingBase entity = (EntityLivingBase)nearbyEntities.get(i);
         if (entity.func_70089_S()) {
            if (entity instanceof EntityPlayer) {
               EntityPlayer entityplayer = (EntityPlayer)entity;
               if (!entityplayer.field_71075_bZ.field_75098_d && (LOTRLevelData.getData(entityplayer).getAlignment(this.theSauron.getFaction()) < 0.0F || this.theSauron.func_70638_az() == entityplayer)) {
                  ++targets;
               }
            } else if (this.theSauron.getFaction().isBadRelation(LOTRMod.getNPCFaction(entity))) {
               ++targets;
            } else if (this.theSauron.func_70638_az() == entity || entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() == this.theSauron) {
               ++targets;
            }
         }
      }

      if (targets >= 4) {
         return true;
      } else if (targets > 0 && this.theSauron.func_70681_au().nextInt(100) == 0) {
         return true;
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return this.theSauron.getIsUsingMace();
   }

   public void func_75249_e() {
      this.attackTick = 40;
      this.theSauron.setIsUsingMace(true);
   }

   public void func_75251_c() {
      this.attackTick = 40;
      this.theSauron.setIsUsingMace(false);
   }

   public void func_75246_d() {
      this.attackTick = Math.max(this.attackTick - 1, 0);
      if (this.attackTick <= 0) {
         this.attackTick = 40;
         LOTRItemSauronMace.useSauronMace(this.theSauron.func_71124_b(0), this.theWorld, this.theSauron);
         this.theSauron.setIsUsingMace(false);
      }

   }
}
