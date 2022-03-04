package lotr.client.render.tileentity;

import lotr.client.model.LOTRModelAleHorn;
import lotr.client.model.LOTRModelGlassBottle;
import lotr.client.model.LOTRModelGoblet;
import lotr.client.model.LOTRModelMug;
import lotr.client.model.LOTRModelSkullCup;
import lotr.client.model.LOTRModelWineGlass;
import lotr.client.render.LOTRRenderBlocks;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemMug;
import lotr.common.tileentity.LOTRTileEntityMug;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRRenderMug extends TileEntitySpecialRenderer {
   private static ResourceLocation mugTexture = new ResourceLocation("lotr:item/mug.png");
   private static ResourceLocation mugClayTexture = new ResourceLocation("lotr:item/mugClay.png");
   private static ResourceLocation gobletGoldTexture = new ResourceLocation("lotr:item/gobletGold.png");
   private static ResourceLocation gobletSilverTexture = new ResourceLocation("lotr:item/gobletSilver.png");
   private static ResourceLocation gobletCopperTexture = new ResourceLocation("lotr:item/gobletCopper.png");
   private static ResourceLocation gobletWoodTexture = new ResourceLocation("lotr:item/gobletWood.png");
   private static ResourceLocation skullTexture = new ResourceLocation("lotr:item/skullCup.png");
   private static ResourceLocation glassTexture = new ResourceLocation("lotr:item/wineGlass.png");
   private static ResourceLocation bottleTexture = new ResourceLocation("lotr:item/glassBottle.png");
   private static ResourceLocation hornTexture = new ResourceLocation("lotr:item/aleHorn.png");
   private static ResourceLocation hornGoldTexture = new ResourceLocation("lotr:item/aleHornGold.png");
   private static ModelBase mugModel = new LOTRModelMug();
   private static ModelBase gobletModel = new LOTRModelGoblet();
   private static ModelBase skullModel = new LOTRModelSkullCup();
   private static ModelBase glassModel = new LOTRModelWineGlass();
   private static ModelBase bottleModel = new LOTRModelGlassBottle();
   private static LOTRModelAleHorn hornModel = new LOTRModelAleHorn();
   private static RenderBlocks renderBlocks = new RenderBlocks();

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityMug mug = (LOTRTileEntityMug)tileentity;
      ItemStack mugItemstack = mug.getMugItemForRender();
      Item mugItem = mugItemstack.func_77973_b();
      boolean full = !mug.isEmpty();
      LOTRItemMug.Vessel vessel = mug.getVessel();
      GL11.glEnable(32826);
      GL11.glDisable(2884);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      float mugScale = 0.75F;
      GL11.glScalef(mugScale, mugScale, mugScale);
      float scale = 0.0625F;
      switch(mug.func_145832_p()) {
      case 0:
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 1:
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 2:
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 3:
         GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
      }

      if (vessel == LOTRItemMug.Vessel.SKULL || vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      }

      if (full) {
         GL11.glDisable(2896);
         GL11.glPushMatrix();
         this.func_147499_a(TextureMap.field_110576_c);
         IIcon liquidIcon = mugItem.func_77617_a(-1);
         if (vessel != LOTRItemMug.Vessel.MUG && vessel != LOTRItemMug.Vessel.MUG_CLAY) {
            if (vessel != LOTRItemMug.Vessel.GOBLET_GOLD && vessel != LOTRItemMug.Vessel.GOBLET_SILVER && vessel != LOTRItemMug.Vessel.GOBLET_COPPER && vessel != LOTRItemMug.Vessel.GOBLET_WOOD) {
               if (vessel == LOTRItemMug.Vessel.SKULL) {
                  this.renderMeniscus(liquidIcon, 5, 11, 3.0D, 9.0D, scale);
               } else if (vessel == LOTRItemMug.Vessel.GLASS) {
                  this.renderLiquid(liquidIcon, 6, 9, 6.0D, 9.0D, scale);
               } else if (vessel == LOTRItemMug.Vessel.BOTTLE) {
                  this.renderLiquid(liquidIcon, 6, 10, 1.0D, 5.0D, scale);
               } else if (vessel == LOTRItemMug.Vessel.HORN || vessel == LOTRItemMug.Vessel.HORN_GOLD) {
                  hornModel.prepareLiquid(scale);
                  this.renderMeniscus(liquidIcon, 6, 9, -1.5D, 5.0D, scale);
               }
            } else {
               this.renderMeniscus(liquidIcon, 6, 9, 1.5D, 8.0D, scale);
            }
         } else {
            this.renderMeniscus(liquidIcon, 6, 10, 2.0D, 7.0D, scale);
         }

         GL11.glPopMatrix();
         GL11.glEnable(2896);
      }

      GL11.glPushMatrix();
      ModelBase model = null;
      if (vessel == LOTRItemMug.Vessel.MUG) {
         this.func_147499_a(mugTexture);
         model = mugModel;
      } else if (vessel == LOTRItemMug.Vessel.MUG_CLAY) {
         this.func_147499_a(mugClayTexture);
         model = mugModel;
      } else if (vessel == LOTRItemMug.Vessel.GOBLET_GOLD) {
         this.func_147499_a(gobletGoldTexture);
         model = gobletModel;
      } else if (vessel == LOTRItemMug.Vessel.GOBLET_SILVER) {
         this.func_147499_a(gobletSilverTexture);
         model = gobletModel;
      } else if (vessel == LOTRItemMug.Vessel.GOBLET_COPPER) {
         this.func_147499_a(gobletCopperTexture);
         model = gobletModel;
      } else if (vessel == LOTRItemMug.Vessel.GOBLET_WOOD) {
         this.func_147499_a(gobletWoodTexture);
         model = gobletModel;
      } else if (vessel == LOTRItemMug.Vessel.SKULL) {
         this.func_147499_a(skullTexture);
         model = skullModel;
      } else if (vessel == LOTRItemMug.Vessel.GLASS) {
         this.func_147499_a(glassTexture);
         model = glassModel;
         GL11.glEnable(2884);
      } else if (vessel == LOTRItemMug.Vessel.BOTTLE) {
         this.func_147499_a(bottleTexture);
         model = bottleModel;
         GL11.glEnable(2884);
      } else if (vessel == LOTRItemMug.Vessel.HORN) {
         this.func_147499_a(hornTexture);
         model = hornModel;
      } else if (vessel == LOTRItemMug.Vessel.HORN_GOLD) {
         this.func_147499_a(hornGoldTexture);
         model = hornModel;
      }

      if (model != null) {
         ((ModelBase)model).func_78088_a((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
      }

      GL11.glPopMatrix();
      GL11.glPopMatrix();
      GL11.glDisable(3042);
      GL11.glEnable(2884);
      GL11.glDisable(32826);
   }

   private void renderMeniscus(IIcon icon, int uvMin, int uvMax, double width, double height, float scale) {
      float minU = icon.func_94214_a((double)uvMin);
      float maxU = icon.func_94214_a((double)uvMax);
      float minV = icon.func_94207_b((double)uvMin);
      float maxV = icon.func_94207_b((double)uvMax);
      width *= (double)scale;
      height *= (double)scale;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a(-width, -height, width, (double)minU, (double)maxV);
      tessellator.func_78374_a(width, -height, width, (double)maxU, (double)maxV);
      tessellator.func_78374_a(width, -height, -width, (double)maxU, (double)minV);
      tessellator.func_78374_a(-width, -height, -width, (double)minU, (double)minV);
      tessellator.func_78381_a();
   }

   private void renderLiquid(IIcon icon, int uvMin, int uvMax, double yMin, double yMax, float scale) {
      double edge = 0.001D;
      double xzMin = (double)uvMin * (double)scale;
      double xzMax = (double)uvMax * (double)scale;
      xzMin += edge;
      xzMax -= edge;
      float dxz = 0.5F - (float)(uvMin + uvMax) / 2.0F * scale;
      yMin = 16.0D - yMin;
      yMax = 16.0D - yMax;
      yMin *= (double)scale;
      yMax *= (double)scale;
      yMin += edge;
      yMax -= edge;
      GL11.glPushMatrix();
      GL11.glTranslatef(dxz, -0.5F, dxz);
      renderBlocks.func_147757_a(icon);
      LOTRRenderBlocks.renderStandardInvBlock(renderBlocks, LOTRMod.mugBlock, xzMin, yMax, xzMin, xzMax, yMin, xzMax);
      renderBlocks.func_147771_a();
      GL11.glPopMatrix();
   }
}
