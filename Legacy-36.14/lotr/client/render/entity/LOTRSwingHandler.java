package lotr.client.render.entity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.client.LOTRAttackTiming;
import lotr.common.item.LOTRWeaponStats;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LOTRSwingHandler {
   private static Map entitySwings = new HashMap();
   private static float swingFactor = 0.8F;

   public LOTRSwingHandler() {
      FMLCommonHandler.instance().bus().register(this);
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onEntityUpdate(LivingUpdateEvent event) {
      EntityLivingBase entity = event.entityLiving;
      World world = entity.field_70170_p;
      if (world.field_72995_K) {
         LOTRSwingHandler.SwingTime swt = (LOTRSwingHandler.SwingTime)entitySwings.get(entity);
         if (swt == null && entity.field_82175_bq && entity.field_110158_av == 0) {
            ItemStack item = entity.func_70694_bm();
            if (LOTRWeaponStats.isMeleeWeapon(item)) {
               int time = false;
               int time;
               if (entity instanceof EntityPlayer) {
                  time = LOTRWeaponStats.getAttackTimePlayer(item);
               } else {
                  time = LOTRWeaponStats.getAttackTimePlayer(item);
               }

               time = Math.round((float)time * swingFactor);
               swt = new LOTRSwingHandler.SwingTime();
               swt.swing = 1;
               swt.swingPrev = 0;
               swt.swingMax = time;
               entitySwings.put(entity, swt);
            }
         }
      }

   }

   @SubscribeEvent
   public void onClientTick(ClientTickEvent event) {
      if (event.phase == Phase.START) {
         Minecraft mc = Minecraft.func_71410_x();
         if (mc.field_71441_e == null) {
            entitySwings.clear();
         } else if (!mc.func_147113_T()) {
            List removes = new ArrayList();
            Iterator var4 = entitySwings.entrySet().iterator();

            while(var4.hasNext()) {
               Entry e = (Entry)var4.next();
               EntityLivingBase entity = (EntityLivingBase)e.getKey();
               LOTRSwingHandler.SwingTime swt = (LOTRSwingHandler.SwingTime)e.getValue();
               swt.swingPrev = swt.swing++;
               if (swt.swing > swt.swingMax) {
                  removes.add(entity);
               }
            }

            var4 = removes.iterator();

            while(var4.hasNext()) {
               EntityLivingBase entity = (EntityLivingBase)var4.next();
               entitySwings.remove(entity);
            }
         }
      }

   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent event) {
      if (event.phase == Phase.START) {
         EntityPlayer entityplayer = Minecraft.func_71410_x().field_71439_g;
         if (entityplayer != null) {
            this.tryUpdateSwing(entityplayer);
         }
      }

   }

   @SubscribeEvent
   public void preRenderPlayer(Pre event) {
      this.tryUpdateSwing(event.entityPlayer);
   }

   @SubscribeEvent
   public void preRenderLiving(net.minecraftforge.client.event.RenderLivingEvent.Pre event) {
      this.tryUpdateSwing(event.entity);
   }

   private void tryUpdateSwing(EntityLivingBase entity) {
      if (entity == Minecraft.func_71410_x().field_71439_g) {
         if (LOTRAttackTiming.fullAttackTime > 0) {
            float max = (float)LOTRAttackTiming.fullAttackTime;
            float swing = (max - (float)LOTRAttackTiming.attackTime) / max;
            float pre = (max - (float)LOTRAttackTiming.prevAttackTime) / max;
            swing /= swingFactor;
            pre /= swingFactor;
            if (swing <= 1.0F) {
               entity.field_70733_aJ = swing;
               entity.field_70732_aI = pre;
            }
         }
      } else {
         LOTRSwingHandler.SwingTime swt = (LOTRSwingHandler.SwingTime)entitySwings.get(entity);
         if (swt != null) {
            entity.field_70733_aJ = (float)swt.swing / (float)swt.swingMax;
            entity.field_70732_aI = (float)swt.swingPrev / (float)swt.swingMax;
         }
      }

   }

   private static class SwingTime {
      public int swingPrev;
      public int swing;
      public int swingMax;

      private SwingTime() {
      }

      // $FF: synthetic method
      SwingTime(Object x0) {
         this();
      }
   }
}
