package lotr.client.render.tileentity;

import java.util.HashMap;
import java.util.Iterator;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.LOTREntities;
import lotr.common.tileentity.LOTRTileEntityMobSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class LOTRTileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer {
   private int tempID;
   private HashMap initialisedItemEntities = new HashMap();
   private static double itemYaw;
   private static double prevItemYaw;

   public static void onClientTick() {
      itemYaw = MathHelper.func_76138_g(itemYaw);
      prevItemYaw = (double)(itemYaw++);
   }

   public void func_147496_a(World world) {
      this.loadEntities(world);
   }

   private void loadEntities(World world) {
      this.unloadEntities();
      LOTREntities.SpawnEggInfo info;
      Entity entity;
      if (world != null) {
         for(Iterator it = LOTREntities.spawnEggs.values().iterator(); it.hasNext(); this.initialisedItemEntities.put(info.spawnedID, entity)) {
            info = (LOTREntities.SpawnEggInfo)it.next();
            String entityName = LOTREntities.getStringFromID(info.spawnedID);
            entity = EntityList.func_75620_a(entityName, world);
            if (entity instanceof EntityLiving) {
               ((EntityLiving)entity).func_110161_a((IEntityLivingData)null);
            }
         }
      }

   }

   private void unloadEntities() {
      this.initialisedItemEntities.clear();
   }

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      World world = Minecraft.func_71410_x().field_71441_e;
      LOTRTileEntityMobSpawner mobSpawner = (LOTRTileEntityMobSpawner)tileentity;
      if (mobSpawner == null || mobSpawner.isActive()) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)d + 0.5F, (float)d1, (float)d2 + 0.5F);
         Entity entity = null;
         double yaw = 0.0D;
         double prevYaw = 0.0D;
         if (mobSpawner != null) {
            entity = mobSpawner.getMobEntity(world);
            yaw = mobSpawner.yaw;
            prevYaw = mobSpawner.prevYaw;
         } else {
            entity = (Entity)this.initialisedItemEntities.get(this.tempID);
            yaw = itemYaw;
            prevYaw = prevItemYaw;
         }

         if (entity != null) {
            float f1 = 0.4375F;
            GL11.glTranslatef(0.0F, 0.4F, 0.0F);
            GL11.glRotatef((float)(prevYaw + (yaw - prevYaw) * (double)f) * 10.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.4F, 0.0F);
            GL11.glScalef(f1, f1, f1);
            entity.func_70012_b(d, d1, d2, 0.0F, 0.0F);
            RenderManager.field_78727_a.func_147940_a(entity, 0.0D, 0.0D, 0.0D, 0.0F, f);
         }

         GL11.glPopMatrix();
      }
   }

   public void renderInvMobSpawner(int i) {
      if (!(Minecraft.func_71410_x().field_71462_r instanceof GuiIngameMenu)) {
         GL11.glPushMatrix();
         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
         this.tempID = i;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushAttrib(1048575);
         this.func_147500_a((TileEntity)null, 0.0D, 0.0D, 0.0D, LOTRTickHandlerClient.renderTick);
         GL11.glPopAttrib();
         this.tempID = 0;
         GL11.glPopMatrix();
      }
   }
}
