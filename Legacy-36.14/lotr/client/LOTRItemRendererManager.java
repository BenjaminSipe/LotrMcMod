package lotr.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.client.render.item.LOTRRenderBannerItem;
import lotr.client.render.item.LOTRRenderBlownItem;
import lotr.client.render.item.LOTRRenderBow;
import lotr.client.render.item.LOTRRenderCrossbow;
import lotr.client.render.item.LOTRRenderElvenBlade;
import lotr.client.render.item.LOTRRenderInvTableCommand;
import lotr.client.render.item.LOTRRenderLargeItem;
import lotr.client.render.item.LOTRRenderSkullStaff;
import lotr.client.render.tileentity.LOTRRenderAnimalJar;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemAnimalJar;
import lotr.common.item.LOTRItemBow;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemSword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;

public class LOTRItemRendererManager implements IResourceManagerReloadListener {
   private static LOTRItemRendererManager INSTANCE;
   private static List largeItemRenderers = new ArrayList();

   public static void load() {
      Minecraft mc = Minecraft.func_71410_x();
      IResourceManager resMgr = mc.func_110442_L();
      INSTANCE = new LOTRItemRendererManager();
      INSTANCE.func_110549_a(resMgr);
      ((IReloadableResourceManager)resMgr).func_110542_a(INSTANCE);
      MinecraftForge.EVENT_BUS.register(INSTANCE);
   }

   public void func_110549_a(IResourceManager resourceManager) {
      largeItemRenderers.clear();

      try {
         Field[] var2 = LOTRMod.class.getFields();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];
            if (field.get((Object)null) instanceof Item) {
               Item item = (Item)field.get((Object)null);
               MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)null);
               LOTRRenderLargeItem largeItemRenderer = LOTRRenderLargeItem.getRendererIfLarge(item);
               boolean isLarge = largeItemRenderer != null;
               if (item instanceof LOTRItemCrossbow) {
                  MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderCrossbow());
               } else if (item instanceof LOTRItemBow) {
                  MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderBow(largeItemRenderer));
               } else if (item instanceof LOTRItemSword && ((LOTRItemSword)item).isElvenBlade()) {
                  double d = 24.0D;
                  if (item == LOTRMod.sting) {
                     d = 40.0D;
                  }

                  MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderElvenBlade(d, largeItemRenderer));
               } else if (isLarge) {
                  MinecraftForgeClient.registerItemRenderer(item, largeItemRenderer);
               }

               if (largeItemRenderer != null) {
                  largeItemRenderers.add(largeItemRenderer);
               }
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

      MinecraftForgeClient.registerItemRenderer(Item.func_150898_a(LOTRMod.commandTable), new LOTRRenderInvTableCommand());
      MinecraftForgeClient.registerItemRenderer(LOTRMod.hobbitPipe, new LOTRRenderBlownItem());
      MinecraftForgeClient.registerItemRenderer(LOTRMod.commandHorn, new LOTRRenderBlownItem());
      MinecraftForgeClient.registerItemRenderer(LOTRMod.conquestHorn, new LOTRRenderBlownItem());
      MinecraftForgeClient.registerItemRenderer(LOTRMod.banner, new LOTRRenderBannerItem());
      MinecraftForgeClient.registerItemRenderer(LOTRMod.orcSkullStaff, new LOTRRenderSkullStaff());
      Iterator itItems = Item.field_150901_e.iterator();

      while(itItems.hasNext()) {
         Item item = (Item)itItems.next();
         if (item instanceof LOTRItemAnimalJar) {
            MinecraftForgeClient.registerItemRenderer(item, new LOTRRenderAnimalJar());
         }
      }

   }

   @SubscribeEvent
   public void preTextureStitch(Pre event) {
      TextureMap map = event.map;
      if (map.func_130086_a() == 1) {
         Iterator var3 = largeItemRenderers.iterator();

         while(var3.hasNext()) {
            LOTRRenderLargeItem largeRenderer = (LOTRRenderLargeItem)var3.next();
            largeRenderer.registerIcons(map);
         }
      }

   }
}
