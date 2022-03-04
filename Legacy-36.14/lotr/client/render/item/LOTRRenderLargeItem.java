package lotr.client.render.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.client.LOTRClientProxy;
import lotr.common.item.LOTRItemLance;
import lotr.common.item.LOTRItemPike;
import lotr.common.item.LOTRItemSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class LOTRRenderLargeItem implements IItemRenderer {
   private static Map sizeFolders = new HashMap();
   private final Item theItem;
   private final String folderName;
   private final float largeIconScale;
   private IIcon largeIcon;
   private List extraTokens = new ArrayList();

   private static ResourceLocation getLargeTexturePath(Item item, String folder) {
      String prefix = "lotr:";
      String itemName = item.func_77658_a();
      itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
      String s = prefix + "textures/items/" + folder + "/" + itemName;
      s = s + ".png";
      return new ResourceLocation(s);
   }

   public static LOTRRenderLargeItem getRendererIfLarge(Item item) {
      Iterator var1 = sizeFolders.keySet().iterator();

      while(var1.hasNext()) {
         String folder = (String)var1.next();
         float iconScale = (Float)sizeFolders.get(folder);

         try {
            ResourceLocation resLoc = getLargeTexturePath(item, folder);
            IResource res = Minecraft.func_71410_x().func_110442_L().func_110536_a(resLoc);
            if (res != null) {
               return new LOTRRenderLargeItem(item, folder, iconScale);
            }
         } catch (IOException var6) {
         }
      }

      return null;
   }

   public LOTRRenderLargeItem(Item item, String dir, float f) {
      this.theItem = item;
      this.folderName = dir;
      this.largeIconScale = f;
   }

   public LOTRRenderLargeItem.ExtraLargeIconToken extraIcon(String name) {
      LOTRRenderLargeItem.ExtraLargeIconToken token = new LOTRRenderLargeItem.ExtraLargeIconToken(name);
      this.extraTokens.add(token);
      return token;
   }

   public void registerIcons(IIconRegister register) {
      this.largeIcon = this.registerLargeIcon(register, (String)null);

      LOTRRenderLargeItem.ExtraLargeIconToken token;
      for(Iterator var2 = this.extraTokens.iterator(); var2.hasNext(); token.icon = this.registerLargeIcon(register, token.name)) {
         token = (LOTRRenderLargeItem.ExtraLargeIconToken)var2.next();
      }

   }

   private IIcon registerLargeIcon(IIconRegister register, String extra) {
      String prefix = "lotr:";
      String itemName = this.theItem.func_77658_a();
      itemName = itemName.substring(itemName.indexOf(prefix) + prefix.length());
      String path = prefix + this.folderName + "/" + itemName;
      if (!StringUtils.func_151246_b(extra)) {
         path = path + "_" + extra;
      }

      return register.func_94245_a(path);
   }

   private void doTransformations() {
      GL11.glTranslatef(-(this.largeIconScale - 1.0F) / 2.0F, -(this.largeIconScale - 1.0F) / 2.0F, 0.0F);
      GL11.glScalef(this.largeIconScale, this.largeIconScale, 1.0F);
   }

   public void renderLargeItem() {
      this.renderLargeItem(this.largeIcon);
   }

   public void renderLargeItem(LOTRRenderLargeItem.ExtraLargeIconToken token) {
      this.renderLargeItem(token.icon);
   }

   private void renderLargeItem(IIcon icon) {
      this.doTransformations();
      Minecraft.func_71410_x().func_110434_K().func_110577_a(TextureMap.field_110576_c);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Tessellator tess = Tessellator.field_78398_a;
      ItemRenderer.func_78439_a(tess, icon.func_94212_f(), icon.func_94206_g(), icon.func_94209_e(), icon.func_94210_h(), icon.func_94211_a(), icon.func_94216_b(), 0.0625F);
   }

   public boolean handleRenderType(ItemStack itemstack, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      GL11.glPushMatrix();
      Entity holder = (Entity)data[1];
      boolean isFirstPerson = holder == Minecraft.func_71410_x().field_71439_g && Minecraft.func_71410_x().field_71474_y.field_74320_O == 0;
      Item item = itemstack.func_77973_b();
      if (item instanceof LOTRItemSpear && holder instanceof EntityPlayer && ((EntityPlayer)holder).func_71011_bu() == itemstack) {
         GL11.glRotatef(260.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
      }

      EntityLivingBase entityliving;
      if (item instanceof LOTRItemPike && holder instanceof EntityLivingBase) {
         entityliving = (EntityLivingBase)holder;
         if (entityliving.func_70694_bm() == itemstack && entityliving.field_70733_aJ <= 0.0F) {
            if (entityliving.func_70093_af()) {
               if (isFirstPerson) {
                  GL11.glRotatef(270.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
               } else {
                  GL11.glTranslatef(0.0F, -0.1F, 0.0F);
                  GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
               }
            } else if (!isFirstPerson) {
               GL11.glTranslatef(0.0F, -0.3F, 0.0F);
               GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
            }
         }
      }

      if (item instanceof LOTRItemLance && holder instanceof EntityLivingBase) {
         entityliving = (EntityLivingBase)holder;
         if (entityliving.func_70694_bm() == itemstack) {
            if (isFirstPerson) {
               GL11.glRotatef(260.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
            } else {
               GL11.glTranslatef(0.7F, 0.0F, 0.0F);
               GL11.glRotatef(-30.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
            }
         }
      }

      this.renderLargeItem();
      if (itemstack != null && itemstack.hasEffect(0)) {
         LOTRClientProxy.renderEnchantmentEffect();
      }

      GL11.glPopMatrix();
   }

   static {
      sizeFolders.put("large", 2.0F);
      sizeFolders.put("large2", 3.0F);
   }

   public static class ExtraLargeIconToken {
      public String name;
      public IIcon icon;

      public ExtraLargeIconToken(String s) {
         this.name = s;
      }
   }
}
