package lotr.client.render.tileentity;

import io.gitlab.dwarfyassassin.lotrucp.client.util.FakeArmorStandEntity;
import lotr.client.LOTRClientProxy;
import lotr.client.model.LOTRArmorModels;
import lotr.client.model.LOTRModelArmorStand;
import lotr.common.item.LOTRItemPlate;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class LOTRRenderArmorStand extends TileEntitySpecialRenderer {
   private static ResourceLocation standTexture = new ResourceLocation("lotr:item/armorStand.png");
   private static ModelBase standModel = new LOTRModelArmorStand();
   private static ModelBiped modelBipedMain = new ModelBiped(0.0F);
   private static ModelBiped modelBiped1 = new ModelBiped(1.0F);
   private static ModelBiped modelBiped2 = new ModelBiped(0.5F);
   private static float BIPED_ARM_ROTATION = -7.07353F;
   private static float BIPED_TICKS_EXISTED = 46.88954F;

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntityArmorStand armorStand = (LOTRTileEntityArmorStand)tileentity;
      EntityLivingBase fakeArmorEntity = FakeArmorStandEntity.INSTANCE;
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(32826);
      GL11.glEnable(3008);
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
      switch(armorStand.func_145832_p() & 3) {
      case 0:
         GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 1:
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 2:
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         break;
      case 3:
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      }

      GL11.glScalef(-1.0F, -1.0F, 1.0F);
      float scale = 0.0625F;
      this.func_147499_a(standTexture);
      standModel.func_78088_a(fakeArmorEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
      LOTRArmorModels.INSTANCE.setupModelForRender(modelBipedMain, (ModelBiped)null, fakeArmorEntity);
      GL11.glTranslatef(0.0F, -0.1875F, 0.0F);

      for(int slot = 0; slot < 4; ++slot) {
         ItemStack itemstack = armorStand.func_70301_a(slot);
         if (itemstack != null && (itemstack.func_77973_b() instanceof ItemArmor || itemstack.func_77973_b() instanceof LOTRItemPlate)) {
            boolean isArmor = itemstack.func_77973_b() instanceof ItemArmor;
            if (isArmor) {
               this.func_147499_a(RenderBiped.getArmorResource(fakeArmorEntity, itemstack, slot, (String)null));
            }

            ModelBiped armorModel = slot == 2 ? modelBiped2 : modelBiped1;
            LOTRArmorModels.INSTANCE.setupArmorForSlot(armorModel, slot);
            armorModel = ForgeHooksClient.getArmorModel(fakeArmorEntity, itemstack, slot, armorModel);
            ModelBiped specialModel = LOTRArmorModels.INSTANCE.getSpecialArmorModel(itemstack, slot, fakeArmorEntity, modelBipedMain);
            if (specialModel != null) {
               armorModel = specialModel;
            }

            LOTRArmorModels.INSTANCE.setupModelForRender(armorModel, (ModelBiped)null, fakeArmorEntity);
            float f1 = 1.0F;
            boolean isColoredArmor = false;
            float f3;
            float f4;
            if (isArmor) {
               int j = ((ItemArmor)itemstack.func_77973_b()).func_82814_b(itemstack);
               if (j != -1) {
                  f3 = (float)(j >> 16 & 255) / 255.0F;
                  float f3 = (float)(j >> 8 & 255) / 255.0F;
                  f4 = (float)(j & 255) / 255.0F;
                  GL11.glColor3f(f1 * f3, f1 * f3, f1 * f4);
                  isColoredArmor = true;
               } else {
                  GL11.glColor3f(f1, f1, f1);
               }
            } else {
               GL11.glColor3f(f1, f1, f1);
            }

            armorModel.func_78088_a(fakeArmorEntity, BIPED_ARM_ROTATION, 0.0F, BIPED_TICKS_EXISTED, 0.0F, 0.0F, scale);
            if (isColoredArmor) {
               this.func_147499_a(RenderBiped.getArmorResource((Entity)null, itemstack, slot, "overlay"));
               f1 = 1.0F;
               GL11.glColor3f(f1, f1, f1);
               armorModel.func_78088_a(fakeArmorEntity, BIPED_ARM_ROTATION, 0.0F, BIPED_TICKS_EXISTED, 0.0F, 0.0F, scale);
            }

            if (itemstack.func_77948_v()) {
               float f2 = (float)armorStand.ticksExisted + f;
               this.func_147499_a(LOTRClientProxy.enchantmentTexture);
               GL11.glEnable(3042);
               f3 = 0.5F;
               GL11.glColor4f(f3, f3, f3, 1.0F);
               GL11.glDepthFunc(514);
               GL11.glDepthMask(false);

               for(int k = 0; k < 2; ++k) {
                  GL11.glDisable(2896);
                  f4 = 0.76F;
                  GL11.glColor4f(0.5F * f4, 0.25F * f4, 0.8F * f4, 1.0F);
                  GL11.glBlendFunc(768, 1);
                  GL11.glMatrixMode(5890);
                  GL11.glLoadIdentity();
                  float f5 = 0.33333334F;
                  GL11.glScalef(f5, f5, f5);
                  GL11.glRotatef(30.0F - (float)k * 60.0F, 0.0F, 0.0F, 1.0F);
                  float f6 = f2 * (0.001F + (float)k * 0.003F) * 20.0F;
                  GL11.glTranslatef(0.0F, f6, 0.0F);
                  GL11.glMatrixMode(5888);
                  armorModel.func_78088_a(fakeArmorEntity, BIPED_ARM_ROTATION, 0.0F, BIPED_TICKS_EXISTED, 0.0F, 0.0F, scale);
               }

               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glMatrixMode(5890);
               GL11.glDepthMask(true);
               GL11.glLoadIdentity();
               GL11.glMatrixMode(5888);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GL11.glDepthFunc(515);
            }
         }
      }

      GL11.glEnable(2884);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
   }
}
