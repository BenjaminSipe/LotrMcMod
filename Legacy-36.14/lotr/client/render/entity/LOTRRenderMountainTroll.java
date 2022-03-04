package lotr.client.render.entity;

import lotr.client.model.LOTRModelTroll;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMountainTroll extends LOTRRenderTroll {
   private static LOTRRandomSkins mountainTrollSkins;
   private LOTREntityThrownRock heldRock;

   public LOTRRenderMountainTroll() {
      mountainTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/troll/mountainTroll");
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return mountainTrollSkins.getRandomSkin((LOTREntityTroll)entity);
   }

   protected void renderTrollWeapon(EntityLivingBase entity, float f) {
      LOTREntityMountainTroll troll = (LOTREntityMountainTroll)entity;
      if (troll.isThrowingRocks()) {
         if (((LOTRModelTroll)this.field_77045_g).field_78095_p <= 0.0F) {
            if (this.heldRock == null) {
               this.heldRock = new LOTREntityThrownRock(troll.field_70170_p);
            }

            this.heldRock.func_70029_a(troll.field_70170_p);
            this.heldRock.func_70107_b(troll.field_70165_t, troll.field_70163_u, troll.field_70161_v);
            ((LOTRModelTroll)this.field_77045_g).rightArm.func_78794_c(0.0625F);
            GL11.glTranslatef(0.375F, 1.5F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            this.scaleTroll(troll, true);
            this.field_76990_c.func_147940_a(this.heldRock, 0.0D, 0.0D, 0.0D, 0.0F, f);
         }
      } else {
         ((LOTRModelTroll)this.field_77045_g).renderWoodenClubWithSpikes(0.0625F);
      }

   }
}
