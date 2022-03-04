package lotr.client.render.item;

import java.util.List;
import lotr.client.LOTRClientProxy;
import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityOrc;
import lotr.common.item.LOTRItemSword;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderElvenBlade implements IItemRenderer {
   private double distance;
   private LOTRRenderLargeItem largeItemRenderer;
   private LOTRRenderLargeItem.ExtraLargeIconToken tokenGlowing;

   public LOTRRenderElvenBlade(double d, LOTRRenderLargeItem large) {
      this.distance = d;
      this.largeItemRenderer = large;
      if (this.largeItemRenderer != null) {
         this.tokenGlowing = this.largeItemRenderer.extraIcon("glowing");
      }

   }

   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      EntityLivingBase entity = (EntityLivingBase)data[1];
      Item item = itemstack.func_77973_b();
      entity.field_70170_p.field_72984_F.func_76320_a("elvenBlade");
      boolean glows = false;
      List orcs = entity.field_70170_p.func_72872_a(LOTREntityOrc.class, entity.field_70121_D.func_72314_b(this.distance, this.distance, this.distance));
      if (!orcs.isEmpty()) {
         glows = true;
      }

      if (glows) {
         GL11.glDisable(2896);
      }

      if (this.largeItemRenderer != null) {
         if (glows) {
            this.largeItemRenderer.renderLargeItem(this.tokenGlowing);
         } else {
            this.largeItemRenderer.renderLargeItem();
         }
      } else {
         IIcon icon = ((EntityLivingBase)data[1]).func_70620_b(itemstack, 0);
         if (glows) {
            icon = ((LOTRItemSword)item).glowingIcon;
         }

         icon = RenderBlocks.getInstance().func_147758_b(icon);
         float minU = icon.func_94209_e();
         float maxU = icon.func_94212_f();
         float minV = icon.func_94206_g();
         float maxV = icon.func_94210_h();
         int width = icon.func_94211_a();
         int height = icon.func_94211_a();
         Tessellator tessellator = Tessellator.field_78398_a;
         ItemRenderer.func_78439_a(tessellator, maxU, minV, minU, maxV, width, height, 0.0625F);
      }

      if (itemstack != null && itemstack.hasEffect(0)) {
         LOTRClientProxy.renderEnchantmentEffect();
      }

      if (glows) {
         GL11.glEnable(2896);
         if (LOTRConfig.elvenBladeGlow) {
            for(int i = 0; i < 4; ++i) {
               LOTRClientProxy.renderEnchantmentEffect();
            }
         }
      }

      GL11.glDisable(32826);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      entity.field_70170_p.field_72984_F.func_76319_b();
   }
}
