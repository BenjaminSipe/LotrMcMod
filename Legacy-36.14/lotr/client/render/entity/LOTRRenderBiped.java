package lotr.client.render.entity;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import lotr.client.model.LOTRArmorModels;
import lotr.client.model.LOTRModelBiped;
import lotr.client.render.LOTRRenderShield;
import lotr.common.LOTRShields;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public abstract class LOTRRenderBiped extends RenderBiped {
   public static final float PLAYER_SCALE = 0.9375F;
   private ModelBiped capeModel = new LOTRModelBiped();
   public ModelBiped npcRenderPassModel;

   public LOTRRenderBiped(ModelBiped model, float f) {
      super(model, f);
   }

   protected void func_82421_b() {
      this.field_82423_g = new LOTRModelBiped(1.0F);
      this.field_82425_h = new LOTRModelBiped(0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      return super.func_110775_a(entity);
   }

   public void func_76986_a(EntityLiving entity, double d, double d1, double d2, float f, float f1) {
      super.func_76986_a(entity, d, d1, d2, f, f1);
      if (Minecraft.func_71382_s() && entity instanceof LOTREntityNPC) {
         LOTREntityNPC npc = (LOTREntityNPC)entity;
         if (npc.hiredNPCInfo.getHiringPlayer() == this.field_76990_c.field_78734_h) {
            LOTRNPCRendering.renderHiredIcon(npc, d, d1 + 0.5D, d2);
            LOTRNPCRendering.renderNPCHealthBar(npc, d, d1 + 0.5D, d2);
         }

         LOTRNPCRendering.renderQuestBook(npc, d, d1, d2);
         LOTRNPCRendering.renderQuestOffer(npc, d, d1, d2);
      }

   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      ItemStack armor = entity.func_71124_b(3 - pass + 1);
      int specialArmorResult = LOTRArmorModels.INSTANCE.getEntityArmorModel(this, this.field_77071_a, entity, armor, pass);
      if (specialArmorResult > 0) {
         return specialArmorResult;
      } else {
         int result = super.func_77032_a(entity, pass, f);
         return result;
      }
   }

   public void func_77042_a(ModelBase model) {
      super.func_77042_a(model);
      if (model instanceof ModelBiped) {
         this.npcRenderPassModel = (ModelBiped)model;
      }

   }

   public void func_82408_c(EntityLiving entity, int pass, float f) {
      super.func_82408_c(entity, pass, f);
   }

   protected void func_82420_a(EntityLiving entity, ItemStack itemstack) {
      LOTRArmorModels.INSTANCE.setupModelForRender(this.field_77071_a, this.field_77071_a, entity);
      LOTRArmorModels.INSTANCE.setupModelForRender(this.field_82425_h, this.field_77071_a, entity);
      LOTRArmorModels.INSTANCE.setupModelForRender(this.field_82423_g, this.field_77071_a, entity);
      if (this.npcRenderPassModel != null) {
         LOTRArmorModels.INSTANCE.setupModelForRender(this.npcRenderPassModel, this.field_77071_a, entity);
      }

   }

   protected void func_77029_c(EntityLivingBase entity, float f) {
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      ItemStack headItem = entity.func_71124_b(4);
      float f1;
      if (headItem != null) {
         GL11.glPushMatrix();
         this.field_77071_a.field_78116_c.func_78794_c(0.0625F);
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(headItem, ItemRenderType.EQUIPPED);
         boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, headItem, ItemRendererHelper.BLOCK_3D);
         if (!(headItem.func_77973_b() instanceof ItemBlock)) {
            if (headItem.func_77973_b() == Items.field_151144_bL) {
               f1 = 1.0625F;
               GL11.glScalef(f1, -f1, -f1);
               GameProfile gameprofile = null;
               if (headItem.func_77942_o()) {
                  NBTTagCompound nbttagcompound = headItem.func_77978_p();
                  if (nbttagcompound.func_150297_b("SkullOwner", (new NBTTagCompound()).func_74732_a())) {
                     gameprofile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
                  } else if (nbttagcompound.func_150297_b("SkullOwner", (new NBTTagString()).func_74732_a()) && !StringUtils.func_151246_b(nbttagcompound.func_74779_i("SkullOwner"))) {
                     gameprofile = new GameProfile((UUID)null, nbttagcompound.func_74779_i("SkullOwner"));
                  }
               }

               TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, headItem.func_77960_j(), gameprofile);
            }
         } else {
            if (is3D || RenderBlocks.func_147739_a(Block.func_149634_a(headItem.func_77973_b()).func_149645_b())) {
               f1 = 0.625F;
               GL11.glTranslatef(0.0F, -0.25F, 0.0F);
               GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(f1, -f1, -f1);
            }

            this.field_76990_c.field_78721_f.func_78443_a(entity, headItem, 0);
         }

         GL11.glPopMatrix();
      }

      ItemStack heldItem = entity.func_70694_bm();
      if (heldItem != null) {
         GL11.glPushMatrix();
         if (this.field_77045_g.field_78091_s) {
            float f1 = 0.5F;
            GL11.glTranslatef(0.0F, 0.625F, 0.0F);
            GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
            GL11.glScalef(f1, f1, f1);
         }

         this.field_77071_a.field_78112_f.func_78794_c(0.0625F);
         GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(heldItem, ItemRenderType.EQUIPPED);
         boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, heldItem, ItemRendererHelper.BLOCK_3D);
         float f1;
         if (heldItem.func_77973_b() instanceof ItemBlock && (is3D || RenderBlocks.func_147739_a(Block.func_149634_a(heldItem.func_77973_b()).func_149645_b()))) {
            f1 = 0.5F;
            GL11.glTranslatef(0.0F, this.getHeldItemYTranslation(), -0.3125F);
            f1 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
         } else if (heldItem.func_77973_b() == Items.field_151031_f) {
            f1 = 0.625F;
            GL11.glTranslatef(0.0F, this.getHeldItemYTranslation(), 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(f1, -f1, f1);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else if (heldItem.func_77973_b().func_77662_d()) {
            f1 = 0.625F;
            if (heldItem.func_77973_b().func_77629_n_()) {
               GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0.0F, -this.getHeldItemYTranslation(), 0.0F);
            }

            GL11.glTranslatef(0.0F, this.getHeldItemYTranslation(), 0.0F);
            GL11.glScalef(f1, -f1, f1);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            f1 = 0.375F;
            GL11.glTranslatef(0.25F, this.getHeldItemYTranslation(), -0.1875F);
            GL11.glScalef(f1, f1, f1);
            GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
         }

         this.field_76990_c.field_78721_f.func_78443_a(entity, heldItem, 0);
         if (heldItem.func_77973_b().func_77623_v()) {
            for(int x = 1; x < heldItem.func_77973_b().getRenderPasses(heldItem.func_77960_j()); ++x) {
               this.field_76990_c.field_78721_f.func_78443_a(entity, heldItem, x);
            }
         }

         GL11.glPopMatrix();
      }

      ItemStack heldItemLeft = ((LOTREntityNPC)entity).getHeldItemLeft();
      if (heldItemLeft != null) {
         GL11.glPushMatrix();
         if (this.field_77045_g.field_78091_s) {
            f1 = 0.5F;
            GL11.glTranslatef(0.0F, 0.625F, 0.0F);
            GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
            GL11.glScalef(f1, f1, f1);
         }

         this.field_77071_a.field_78113_g.func_78794_c(0.0625F);
         GL11.glTranslatef(0.0625F, 0.4375F, 0.0625F);
         IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(heldItemLeft, ItemRenderType.EQUIPPED);
         boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, heldItemLeft, ItemRendererHelper.BLOCK_3D);
         float f1;
         if (!(heldItemLeft.func_77973_b() instanceof ItemBlock) || !is3D && !RenderBlocks.func_147739_a(Block.func_149634_a(heldItemLeft.func_77973_b()).func_149645_b())) {
            if (heldItemLeft.func_77973_b().func_77662_d()) {
               f1 = 0.625F;
               if (heldItemLeft.func_77973_b().func_77629_n_()) {
                  GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glTranslatef(0.0F, -this.getHeldItemYTranslation(), 0.0F);
               }

               GL11.glTranslatef(0.0F, this.getHeldItemYTranslation(), 0.0F);
               GL11.glScalef(f1, -f1, f1);
               GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
               f1 = 0.3175F;
               GL11.glTranslatef(0.0F, this.getHeldItemYTranslation(), 0.0F);
               GL11.glScalef(f1, -f1, f1);
               GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
         } else {
            f1 = 0.5F;
            GL11.glTranslatef(0.0F, this.getHeldItemYTranslation(), -0.3125F);
            f1 *= 0.75F;
            GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
         }

         this.field_76990_c.field_78721_f.func_78443_a(entity, heldItemLeft, 0);
         if (heldItemLeft.func_77973_b().func_77623_v()) {
            for(int i = 1; i < heldItemLeft.func_77973_b().getRenderPasses(heldItemLeft.func_77960_j()); ++i) {
               this.field_76990_c.field_78721_f.func_78443_a(entity, heldItemLeft, i);
            }
         }

         GL11.glPopMatrix();
      }

      this.renderNPCCape((LOTREntityNPC)entity);
      this.renderNPCShield((LOTREntityNPC)entity);
   }

   protected ResourceLocation getCapeToRender(LOTREntityNPC entity) {
      return entity.npcCape;
   }

   protected void renderNPCCape(LOTREntityNPC entity) {
      ResourceLocation capeTexture = this.getCapeToRender(entity);
      if (capeTexture != null) {
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 0.0F, 0.125F);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
         this.func_110776_a(capeTexture);
         this.capeModel.func_78111_c(0.0625F);
         GL11.glPopMatrix();
      }

   }

   protected void renderNPCShield(LOTREntityNPC entity) {
      LOTRShields shield = entity.npcShield;
      if (shield != null) {
         LOTRRenderShield.renderShield(shield, entity, this.field_77071_a);
      }

   }

   protected void func_77041_b(EntityLivingBase entity, float f) {
      float f1 = 0.9375F;
      GL11.glScalef(f1, f1, f1);
   }

   public float getHeldItemYTranslation() {
      return 0.1875F;
   }
}
