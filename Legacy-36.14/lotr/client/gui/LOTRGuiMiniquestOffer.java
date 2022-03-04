package lotr.client.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.client.render.entity.LOTRRenderBiped;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.common.network.LOTRPacketMiniquestOffer;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiMiniquestOffer extends LOTRGuiScreenBase {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/quest/miniquest.png");
   private static RenderItem renderItem = new RenderItem();
   private LOTRMiniQuest theMiniQuest;
   private LOTREntityNPC theNPC;
   private String description;
   private Random rand;
   private int openTick;
   public int xSize = 256;
   public int ySize = 200;
   private int guiLeft;
   private int guiTop;
   private int descriptionX = 85;
   private int descriptionY = 30;
   private int descriptionWidth = 160;
   private int npcX = 46;
   private int npcY = 90;
   private GuiButton buttonAccept;
   private GuiButton buttonDecline;
   private boolean sentClosePacket = false;
   private LOTRGuiMiniquestOffer.NPCAction npcAction;
   private int actionTick = 0;
   private int actionTime;
   private float actionSlow;
   private float headYaw;
   private float prevHeadYaw;
   private float headPitch;
   private float prevHeadPitch;

   public LOTRGuiMiniquestOffer(LOTRMiniQuest quest, LOTREntityNPC npc) {
      this.theMiniQuest = quest;
      this.theNPC = npc;
      this.rand = this.theNPC.func_70681_au();
      this.openTick = 0;
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.field_146292_n.add(this.buttonAccept = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 20 - 80, this.guiTop + this.ySize - 30, 80, 20, StatCollector.func_74838_a("lotr.gui.miniquestOffer.accept")));
      this.field_146292_n.add(this.buttonDecline = new LOTRGuiButtonRedBook(1, this.guiLeft + this.xSize / 2 + 20, this.guiTop + this.ySize - 30, 80, 20, StatCollector.func_74838_a("lotr.gui.miniquestOffer.decline")));
   }

   public void func_73876_c() {
      super.func_73876_c();
      if (!this.theNPC.func_70089_S() || this.field_146297_k.field_71439_g.func_70032_d(this.theNPC) > 8.0F) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

      this.prevHeadYaw = this.headYaw;
      this.prevHeadPitch = this.headPitch;
      if (this.npcAction == null) {
         if (this.openTick < 100) {
            this.npcAction = LOTRGuiMiniquestOffer.NPCAction.TALKING;
            this.actionTime = 100;
            this.actionSlow = 1.0F;
         } else if (this.rand.nextInt(200) == 0) {
            this.npcAction = LOTRGuiMiniquestOffer.NPCAction.getRandomAction(this.rand);
            if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.TALKING) {
               this.actionTime = 40 + this.rand.nextInt(60);
               this.actionSlow = 1.0F;
            } else if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.LOOKING) {
               this.actionTime = 60 + this.rand.nextInt(60);
               this.actionSlow = 1.0F;
            } else if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.SHAKING) {
               this.actionTime = 100 + this.rand.nextInt(60);
               this.actionSlow = 1.0F;
            } else if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.LOOKING_UP) {
               this.actionTime = 30 + this.rand.nextInt(50);
               this.actionSlow = 1.0F;
            }
         }
      } else {
         ++this.actionTick;
      }

      if (this.npcAction != null) {
         if (this.actionTick >= this.actionTime) {
            this.npcAction = null;
            this.actionTick = 0;
            this.actionTime = 0;
         } else {
            float slow;
            if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.TALKING) {
               if (this.actionTick % 20 == 0) {
                  this.actionSlow = 0.7F + this.rand.nextFloat() * 1.5F;
               }

               slow = this.actionSlow * 2.0F;
               this.headYaw = MathHelper.func_76126_a((float)this.actionTick / slow) * (float)Math.toRadians(10.0D);
               this.headPitch = (MathHelper.func_76126_a((float)this.actionTick / slow * 2.0F) + 1.0F) / 2.0F * (float)Math.toRadians(-20.0D);
            } else if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.SHAKING) {
               this.actionSlow += 0.01F;
               this.headYaw = MathHelper.func_76126_a((float)this.actionTick / this.actionSlow) * (float)Math.toRadians(30.0D);
               this.headPitch += (float)Math.toRadians(0.4D);
            } else if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.LOOKING) {
               slow = this.actionSlow * 16.0F;
               this.headYaw = MathHelper.func_76126_a((float)this.actionTick / slow) * (float)Math.toRadians(60.0D);
               this.headPitch = (MathHelper.func_76126_a((float)this.actionTick / slow * 2.0F) + 1.0F) / 2.0F * (float)Math.toRadians(-15.0D);
            } else if (this.npcAction == LOTRGuiMiniquestOffer.NPCAction.LOOKING_UP) {
               this.headYaw = 0.0F;
               this.headPitch = (float)Math.toRadians(-20.0D);
            }
         }
      } else {
         this.headYaw = 0.0F;
         this.headPitch = MathHelper.func_76126_a((float)this.openTick * 0.07F) * (float)Math.toRadians(5.0D);
      }

      ++this.openTick;
   }

   public void func_73863_a(int i, int j, float f) {
      if (this.description == null) {
         this.description = LOTRSpeech.formatSpeech(this.theMiniQuest.quoteStart, this.field_146297_k.field_71439_g, (String)null, this.theMiniQuest.getObjectiveInSpeech());
      }

      this.func_146276_q_();
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String name = this.theNPC.getNPCName();
      this.drawCenteredString(name, this.guiLeft + this.xSize / 2, this.guiTop + 8, 8019267);
      this.renderNPC(this.guiLeft + this.npcX, this.guiTop + this.npcY, (float)(this.guiLeft + this.npcX - i), (float)(this.guiTop + this.npcY - j), f);
      this.field_146289_q.func_78279_b(this.description, this.guiLeft + this.descriptionX, this.guiTop + this.descriptionY, this.descriptionWidth, 8019267);
      String objective = this.theMiniQuest.getQuestObjective();
      int objLineWidth = this.xSize - 64;
      List objectiveLines = this.field_146289_q.func_78271_c(objective, objLineWidth);
      int objY = this.guiTop + this.ySize - 50;

      for(Iterator var10 = objectiveLines.iterator(); var10.hasNext(); objY += this.field_146289_q.field_78288_b) {
         Object obj = var10.next();
         String line = (String)obj;
         this.drawCenteredString(line, this.guiLeft + this.xSize / 2, objY, 8019267);
      }

      int objFirstLineWidth = this.field_146289_q.func_78256_a((String)objectiveLines.get(0));
      RenderHelper.func_74520_c();
      GL11.glDisable(2896);
      GL11.glEnable(32826);
      GL11.glEnable(2896);
      GL11.glEnable(2884);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int iconW = 16;
      int iconB = 6;
      int iconY = objY + this.field_146289_q.field_78288_b / 2 - iconW / 2;
      renderItem.func_82406_b(this.field_146297_k.field_71466_p, this.field_146297_k.func_110434_K(), this.theMiniQuest.getQuestIcon(), this.guiLeft + this.xSize / 2 - objFirstLineWidth / 2 - iconW - iconB, iconY);
      renderItem.func_82406_b(this.field_146297_k.field_71466_p, this.field_146297_k.func_110434_K(), this.theMiniQuest.getQuestIcon(), this.guiLeft + this.xSize / 2 + objFirstLineWidth / 2 + iconB, iconY);
      GL11.glDisable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.func_73863_a(i, j, f);
   }

   private void renderNPC(int i, int j, float dx, float dy, float f) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float scale = 70.0F;
      GL11.glEnable(2903);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)i, (float)j, 40.0F);
      GL11.glScalef(-scale, -scale, -scale);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef((float)Math.atan((double)(dx / 40.0F)) * 20.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-((float)Math.atan((double)(dy / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.0F, this.theNPC.field_70129_M, 0.0F);
      RenderManager.field_78727_a.field_78735_i = 180.0F;
      GL11.glDisable(2884);
      GL11.glEnable(32826);
      GL11.glEnable(3008);
      Render render = RenderManager.field_78727_a.func_78713_a(this.theNPC);
      if (render instanceof LOTRRenderBiped) {
         LOTRRenderBiped npcRenderer = (LOTRRenderBiped)render;
         ModelBiped model = npcRenderer.field_77071_a;
         model.field_78091_s = this.theNPC.func_70631_g_();
         this.field_146297_k.func_110434_K().func_110577_a(npcRenderer.func_110775_a(this.theNPC));
         GL11.glTranslatef(0.0F, -model.field_78116_c.field_78797_d / 16.0F, 0.0F);
         float yaw = this.prevHeadYaw + (this.headYaw - this.prevHeadYaw) * f;
         float pitch = this.prevHeadPitch + (this.headPitch - this.prevHeadPitch) * f;
         yaw = (float)Math.toDegrees((double)yaw);
         pitch = (float)Math.toDegrees((double)pitch);
         GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
         model.field_78116_c.field_78795_f = model.field_78114_d.field_78795_f = 0.0F;
         model.field_78116_c.field_78796_g = model.field_78114_d.field_78796_g = 0.0F;
         model.field_78116_c.field_78808_h = model.field_78114_d.field_78808_h = 0.0F;
         model.field_78116_c.func_78785_a(0.0625F);
         model.field_78114_d.func_78785_a(0.0625F);

         for(int pass = 0; pass < 4; ++pass) {
            int shouldRenderPass = npcRenderer.func_77032_a(this.theNPC, pass, 1.0F);
            if (shouldRenderPass > 0) {
               model = npcRenderer.npcRenderPassModel;
               model.field_78091_s = this.theNPC.func_70631_g_();
               List modelParts = model.field_78092_r;
               boolean[] prevShowModels = new boolean[modelParts.size()];

               int l;
               ModelRenderer part;
               for(l = 0; l < modelParts.size(); ++l) {
                  part = (ModelRenderer)modelParts.get(l);
                  prevShowModels[l] = part.field_78806_j;
                  boolean isHeadPart = false;
                  if (this.recursiveCheckForModel(model.field_78116_c, part)) {
                     isHeadPart = true;
                  } else if (this.recursiveCheckForModel(model.field_78114_d, part)) {
                     isHeadPart = true;
                  }

                  if (!isHeadPart) {
                     part.field_78806_j = false;
                  }
               }

               model.func_78088_a(this.theNPC, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               if ((shouldRenderPass & 240) == 16) {
                  npcRenderer.func_82408_c(this.theNPC, pass, 1.0F);
                  model.func_78088_a(this.theNPC, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               }

               for(l = 0; l < modelParts.size(); ++l) {
                  part = (ModelRenderer)modelParts.get(l);
                  part.field_78806_j = prevShowModels[l];
               }
            }
         }
      }

      GL11.glEnable(2884);
      GL11.glDisable(32826);
      GL11.glPopMatrix();
      RenderHelper.func_74518_a();
      GL11.glDisable(32826);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77476_b);
      GL11.glDisable(3553);
      OpenGlHelper.func_77473_a(OpenGlHelper.field_77478_a);
   }

   private boolean recursiveCheckForModel(ModelRenderer base, ModelRenderer match) {
      if (base == match) {
         return true;
      } else {
         if (base.field_78805_m != null) {
            Iterator var3 = base.field_78805_m.iterator();

            while(var3.hasNext()) {
               Object obj = var3.next();
               ModelRenderer part = (ModelRenderer)obj;
               if (this.recursiveCheckForModel(part, match)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         boolean close = false;
         if (button == this.buttonAccept) {
            LOTRPacketMiniquestOffer.sendClosePacket(this.field_146297_k.field_71439_g, this.theNPC, true);
            close = true;
         } else if (button == this.buttonDecline) {
            LOTRPacketMiniquestOffer.sendClosePacket(this.field_146297_k.field_71439_g, this.theNPC, false);
            close = true;
         }

         if (close) {
            this.sentClosePacket = true;
            this.field_146297_k.field_71439_g.func_71053_j();
         }
      }

   }

   public void func_146281_b() {
      super.func_146281_b();
      if (!this.sentClosePacket) {
         LOTRPacketMiniquestOffer.sendClosePacket(this.field_146297_k.field_71439_g, this.theNPC, false);
         this.sentClosePacket = true;
      }

   }

   private static enum NPCAction {
      TALKING(1.0F),
      SHAKING(0.1F),
      LOOKING(0.3F),
      LOOKING_UP(0.4F);

      private static float totalWeight = -1.0F;
      private final float weight;

      private NPCAction(float f) {
         this.weight = f;
      }

      public static LOTRGuiMiniquestOffer.NPCAction getRandomAction(Random rand) {
         if (totalWeight <= 0.0F) {
            totalWeight = 0.0F;
            LOTRGuiMiniquestOffer.NPCAction[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               LOTRGuiMiniquestOffer.NPCAction action = var1[var3];
               totalWeight += action.weight;
            }
         }

         float f = rand.nextFloat();
         f *= totalWeight;
         LOTRGuiMiniquestOffer.NPCAction chosen = null;
         LOTRGuiMiniquestOffer.NPCAction[] var9 = values();
         int var10 = var9.length;

         for(int var5 = 0; var5 < var10; ++var5) {
            LOTRGuiMiniquestOffer.NPCAction action = var9[var5];
            f -= action.weight;
            if (f <= 0.0F) {
               chosen = action;
               break;
            }
         }

         return chosen;
      }
   }
}
