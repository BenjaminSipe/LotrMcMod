package lotr.client.model;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemSling;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

public class LOTRArmorModels {
   public static LOTRArmorModels INSTANCE;
   private Map specialArmorModels = new HashMap();

   public static void setupArmorModels() {
      INSTANCE = new LOTRArmorModels();
   }

   private LOTRArmorModels() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   private Map getSpecialModels(ModelBiped key) {
      Map map = (Map)this.specialArmorModels.get(key);
      if (map == null) {
         map = new HashMap();
         ((Map)map).put(LOTRMod.leatherHat, new LOTRModelLeatherHat());
         ((Map)map).put(LOTRMod.helmetGondor, new LOTRModelGondorHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetElven, new LOTRModelGaladhrimHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetGondorWinged, new LOTRModelWingedHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetMorgul, new LOTRModelMorgulHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetGemsbok, new LOTRModelGemsbokHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetHighElven, new LOTRModelHighElvenHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetBlackUruk, new LOTRModelBlackUrukHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetUruk, new LOTRModelUrukHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetNearHaradWarlord, new LOTRModelNearHaradWarlordHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetDolAmroth, new LOTRModelSwanHelmet(1.0F));
         ((Map)map).put(LOTRMod.bodyDolAmroth, new LOTRModelSwanChestplate(1.0F));
         ((Map)map).put(LOTRMod.helmetMoredainLion, new LOTRModelMoredainLionHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetHaradRobes, new LOTRModelHaradTurban());
         ((Map)map).put(LOTRMod.bodyHaradRobes, new LOTRModelHaradRobes(1.0F));
         ((Map)map).put(LOTRMod.legsHaradRobes, new LOTRModelHaradRobes(0.5F));
         ((Map)map).put(LOTRMod.bootsHaradRobes, new LOTRModelHaradRobes(1.0F));
         ((Map)map).put(LOTRMod.helmetGondolin, new LOTRModelGondolinHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetRohanMarshal, new LOTRModelRohanMarshalHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetTauredainChieftain, new LOTRModelTauredainChieftainHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetTauredainGold, new LOTRModelTauredainGoldHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetGundabadUruk, new LOTRModelGundabadUrukHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetUrukBerserker, new LOTRModelUrukHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetDorwinionElf, new LOTRModelDorwinionElfHelmet(1.0F));
         ((Map)map).put(LOTRMod.partyHat, new LOTRModelPartyHat(0.6F));
         ((Map)map).put(LOTRMod.helmetArnor, new LOTRModelArnorHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetRhunGold, new LOTRModelEasterlingHelmet(1.0F, false));
         ((Map)map).put(LOTRMod.helmetRhunWarlord, new LOTRModelEasterlingHelmet(1.0F, true));
         ((Map)map).put(LOTRMod.helmetRivendell, new LOTRModelHighElvenHelmet(1.0F));
         ((Map)map).put(LOTRMod.bodyGulfHarad, new LOTRModelGulfChestplate(1.0F));
         ((Map)map).put(LOTRMod.helmetUmbar, new LOTRModelUmbarHelmet(1.0F));
         ((Map)map).put(LOTRMod.helmetHarnedor, new LOTRModelHarnedorHelmet(1.0F));
         ((Map)map).put(LOTRMod.bodyHarnedor, new LOTRModelHarnedorChestplate(1.0F));
         ((Map)map).put(LOTRMod.helmetBlackNumenorean, new LOTRModelBlackNumenoreanHelmet(1.0F));
         ((Map)map).put(LOTRMod.plate, new LOTRModelHeadPlate());
         ((Map)map).put(LOTRMod.woodPlate, new LOTRModelHeadPlate());
         ((Map)map).put(LOTRMod.ceramicPlate, new LOTRModelHeadPlate());
         Iterator var3 = ((Map)map).values().iterator();

         while(var3.hasNext()) {
            ModelBiped armorModel = (ModelBiped)var3.next();
            this.copyModelRotations(armorModel, key);
         }

         this.specialArmorModels.put(key, map);
      }

      return (Map)map;
   }

   public ModelBiped getSpecialArmorModel(ItemStack itemstack, int slot, EntityLivingBase entity, ModelBiped mainModel) {
      if (itemstack == null) {
         return null;
      } else {
         Item item = itemstack.func_77973_b();
         ModelBiped model = (ModelBiped)this.getSpecialModels(mainModel).get(item);
         if (model == null) {
            return null;
         } else {
            if (model instanceof LOTRModelLeatherHat) {
               ((LOTRModelLeatherHat)model).setHatItem(itemstack);
            }

            if (model instanceof LOTRModelHaradRobes) {
               ((LOTRModelHaradRobes)model).setRobeItem(itemstack);
            }

            if (model instanceof LOTRModelPartyHat) {
               ((LOTRModelPartyHat)model).setHatItem(itemstack);
            }

            if (model instanceof LOTRModelHeadPlate) {
               ((LOTRModelHeadPlate)model).setPlateItem(itemstack);
            }

            this.copyModelRotations(model, mainModel);
            this.setupArmorForSlot(model, slot);
            return model;
         }
      }
   }

   @SubscribeEvent
   public void getPlayerArmorModel(SetArmorModel event) {
      RenderPlayer renderer = event.renderer;
      ModelBiped mainModel = renderer.field_77109_a;
      EntityPlayer entityplayer = event.entityPlayer;
      ItemStack armor = event.stack;
      int slot = event.slot;
      int result = this.getEntityArmorModel(renderer, mainModel, entityplayer, armor, 3 - slot);
      if (result > 0) {
         event.result = result;
      }

   }

   public int getEntityArmorModel(RendererLivingEntity renderer, ModelBiped mainModel, EntityLivingBase entity, ItemStack armor, int slot) {
      ModelBiped armorModel = this.getSpecialArmorModel(armor, slot, entity, mainModel);
      if (armorModel != null) {
         Item armorItem = armor == null ? null : armor.func_77973_b();
         if (armorItem instanceof ItemArmor) {
            Minecraft.func_71410_x().func_110434_K().func_110577_a(RenderBiped.getArmorResource(entity, armor, slot, (String)null));
         }

         renderer.func_77042_a(armorModel);
         this.setupModelForRender(armorModel, mainModel, entity);
         if (armorItem instanceof ItemArmor) {
            int color = ((ItemArmor)armorItem).func_82814_b(armor);
            if (color != -1) {
               float r = (float)(color >> 16 & 255) / 255.0F;
               float g = (float)(color >> 8 & 255) / 255.0F;
               float b = (float)(color & 255) / 255.0F;
               GL11.glColor3f(r, g, b);
               if (armor.func_77948_v()) {
                  return 31;
               }

               return 16;
            }
         }

         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         return armor.func_77948_v() ? 15 : 1;
      } else {
         return 0;
      }
   }

   @SubscribeEvent
   public void preRenderEntity(Pre event) {
      EntityLivingBase entity = event.entity;
      RendererLivingEntity renderer = event.renderer;
      if (entity instanceof EntityPlayer && renderer instanceof RenderPlayer) {
         EntityPlayer entityplayer = (EntityPlayer)entity;
         RenderPlayer renderplayer = (RenderPlayer)renderer;
         ModelBiped mainModel = renderplayer.field_77109_a;
         ModelBiped armorModel1 = renderplayer.field_77108_b;
         ModelBiped armorModel2 = renderplayer.field_77111_i;
         this.setupModelForRender(mainModel, mainModel, entityplayer);
         this.setupModelForRender(armorModel1, mainModel, entityplayer);
         this.setupModelForRender(armorModel2, mainModel, entityplayer);
      }

   }

   public void setupModelForRender(ModelBiped model, ModelBiped mainModel, EntityLivingBase entity) {
      if (mainModel != null) {
         model.field_78095_p = mainModel.field_78095_p;
         model.field_78093_q = mainModel.field_78093_q;
         model.field_78091_s = mainModel.field_78091_s;
         model.field_78117_n = mainModel.field_78117_n;
      } else {
         model.field_78095_p = 0.0F;
         model.field_78093_q = false;
         model.field_78091_s = false;
         model.field_78117_n = false;
      }

      if (entity instanceof LOTREntityNPC) {
         model.field_78114_d.field_78806_j = ((LOTREntityNPC)entity).shouldRenderNPCHair();
      }

      ItemStack heldRight;
      if (entity instanceof EntityPlayer) {
         heldRight = entity == null ? null : entity.func_70694_bm();
         model.field_78118_o = mainModel.field_78118_o;
         this.setupHeldItem(model, entity, heldRight, true);
      } else {
         heldRight = entity == null ? null : entity.func_70694_bm();
         ItemStack heldLeft = entity == null ? null : (entity instanceof LOTREntityNPC ? ((LOTREntityNPC)entity).getHeldItemLeft() : null);
         this.setupHeldItem(model, entity, heldRight, true);
         this.setupHeldItem(model, entity, heldLeft, false);
      }

   }

   private void setupHeldItem(ModelBiped model, EntityLivingBase entity, ItemStack itemstack, boolean rightArm) {
      int value = 0;
      boolean aimBow = false;
      if (itemstack != null) {
         value = 1;
         Item item = itemstack.func_77973_b();
         boolean isRanged = false;
         if (itemstack.func_77975_n() == EnumAction.bow) {
            if (item instanceof LOTRItemSpear) {
               isRanged = entity instanceof EntityPlayer;
            } else if (item instanceof ItemSword) {
               isRanged = false;
            } else {
               isRanged = true;
            }
         }

         if (item instanceof LOTRItemSling) {
            isRanged = true;
         }

         if (isRanged) {
            boolean aiming = model.field_78118_o;
            if (entity instanceof EntityPlayer && LOTRItemCrossbow.isLoaded(itemstack)) {
               aiming = true;
            }

            if (entity instanceof LOTREntityNPC) {
               aiming = ((LOTREntityNPC)entity).clientCombatStance;
            }

            if (aiming) {
               value = 3;
               aimBow = true;
            }
         }

         if (item instanceof LOTRItemBanner) {
            value = 3;
         }

         if (entity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (entityplayer.func_71052_bv() > 0 && itemstack.func_77975_n() == EnumAction.block) {
               value = 3;
            }
         }

         if (entity instanceof LOTREntityNPC && ((LOTREntityNPC)entity).clientIsEating) {
            value = 3;
         }
      }

      if (rightArm) {
         model.field_78120_m = value;
         model.field_78118_o = aimBow;
      } else {
         model.field_78119_l = value;
      }

   }

   public void copyModelRotations(ModelBiped target, ModelBiped src) {
      this.copyBoxRotations(target.field_78116_c, src.field_78116_c);
      this.copyBoxRotations(target.field_78114_d, src.field_78114_d);
      this.copyBoxRotations(target.field_78115_e, src.field_78115_e);
      this.copyBoxRotations(target.field_78112_f, src.field_78112_f);
      this.copyBoxRotations(target.field_78113_g, src.field_78113_g);
      this.copyBoxRotations(target.field_78123_h, src.field_78123_h);
      this.copyBoxRotations(target.field_78124_i, src.field_78124_i);
   }

   public void copyBoxRotations(ModelRenderer target, ModelRenderer src) {
      target.field_78800_c = src.field_78800_c;
      target.field_78797_d = src.field_78797_d;
      target.field_78798_e = src.field_78798_e;
      target.field_78795_f = src.field_78795_f;
      target.field_78796_g = src.field_78796_g;
      target.field_78808_h = src.field_78808_h;
   }

   public void setupArmorForSlot(ModelBiped model, int slot) {
      model.field_78116_c.field_78806_j = slot == 0;
      model.field_78114_d.field_78806_j = slot == 0;
      model.field_78115_e.field_78806_j = slot == 1 || slot == 2;
      model.field_78112_f.field_78806_j = slot == 1;
      model.field_78113_g.field_78806_j = slot == 1;
      model.field_78123_h.field_78806_j = slot == 2 || slot == 3;
      model.field_78124_i.field_78806_j = slot == 2 || slot == 3;
   }
}
