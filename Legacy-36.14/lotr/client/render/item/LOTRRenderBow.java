package lotr.client.render.item;

import java.util.HashMap;
import java.util.Map;
import lotr.client.LOTRClientProxy;
import lotr.common.item.LOTRItemBow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderBow implements IItemRenderer {
   private LOTRRenderLargeItem largeItemRenderer;
   private Map tokensPullStates;
   public static boolean renderingWeaponRack = false;

   public LOTRRenderBow(LOTRRenderLargeItem large) {
      this.largeItemRenderer = large;
      if (this.largeItemRenderer != null) {
         this.tokensPullStates = new HashMap();
         LOTRItemBow.BowState[] var2 = LOTRItemBow.BowState.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            LOTRItemBow.BowState state = var2[var4];
            if (state != LOTRItemBow.BowState.HELD) {
               LOTRRenderLargeItem.ExtraLargeIconToken token = this.largeItemRenderer.extraIcon(state.iconName);
               this.tokensPullStates.put(state, token);
            }
         }
      }

   }

   public boolean isLargeBow() {
      return this.largeItemRenderer != null;
   }

   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      GL11.glPushMatrix();
      EntityLivingBase entity = (EntityLivingBase)data[1];
      if (!renderingWeaponRack && (Minecraft.func_71410_x().field_71474_y.field_74320_O != 0 || entity != Minecraft.func_71410_x().field_71439_g)) {
         GL11.glTranslatef(0.9375F, 0.0625F, 0.0F);
         GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(0.6666667F, 0.6666667F, 0.6666667F);
         GL11.glTranslatef(0.0F, 0.3F, 0.0F);
         GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
         GL11.glScalef(2.6666667F, 2.6666667F, 2.6666667F);
         GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
         GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
         GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(0.625F, -0.625F, 0.625F);
         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glTranslatef(0.0F, -0.3F, 0.0F);
         GL11.glScalef(1.5F, 1.5F, 1.5F);
         GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
      }

      int useCount;
      if (this.largeItemRenderer != null) {
         Item item = itemstack.func_77973_b();
         if (!(item instanceof LOTRItemBow)) {
            throw new RuntimeException("Attempting to render a large bow which is not a bow");
         }

         LOTRItemBow bow = (LOTRItemBow)item;
         LOTRItemBow.BowState bowState = LOTRItemBow.BowState.HELD;
         if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            ItemStack usingItem = entityplayer.func_71011_bu();
            useCount = entityplayer.func_71052_bv();
            bowState = bow.getBowState(entityplayer, usingItem, useCount);
         } else {
            bowState = bow.getBowState(entity, itemstack, 0);
         }

         if (bowState == LOTRItemBow.BowState.HELD) {
            this.largeItemRenderer.renderLargeItem();
         } else {
            this.largeItemRenderer.renderLargeItem((LOTRRenderLargeItem.ExtraLargeIconToken)this.tokensPullStates.get(bowState));
         }
      } else {
         IIcon icon = ((EntityLivingBase)data[1]).func_70620_b(itemstack, 0);
         icon = RenderBlocks.getInstance().func_147758_b(icon);
         float minU = icon.func_94209_e();
         float maxU = icon.func_94212_f();
         float minV = icon.func_94206_g();
         float maxV = icon.func_94210_h();
         useCount = icon.func_94211_a();
         int height = icon.func_94211_a();
         Tessellator tessellator = Tessellator.field_78398_a;
         ItemRenderer.func_78439_a(tessellator, maxU, minV, minU, maxV, useCount, height, 0.0625F);
      }

      if (itemstack != null && itemstack.hasEffect(0)) {
         LOTRClientProxy.renderEnchantmentEffect();
      }

      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }
}
