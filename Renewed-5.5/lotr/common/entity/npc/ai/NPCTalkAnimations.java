package lotr.common.entity.npc.ai;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.init.LOTRParticles;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketNPCTalkAnimations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class NPCTalkAnimations {
   private final NPCEntity npc;
   private boolean isTalking;
   private NPCTalkAnimations.TalkAction talkAction;
   private float actionSlow;
   private int actionTime;
   private boolean doGestureMain;
   private boolean doGestureOff;
   private int timeUntilGesture;
   private int actionTick = 0;
   private int totalTalkingTime = 0;
   private float headYaw;
   private float headPitch;
   private float prevHeadYaw;
   private float prevHeadPitch;
   private int gestureMainTime;
   private int gestureMainTick;
   private int prevGestureMainTick;
   private int gestureOffTime;
   private int gestureOffTick;
   private int prevGestureOffTick;
   private int timeSinceParticle = 0;
   private boolean spawnedFirstParticle;
   private boolean sendData = true;

   public NPCTalkAnimations(NPCEntity npc) {
      this.npc = npc;
   }

   public void startTalking() {
      this.isTalking = true;
      this.talkAction = null;
      this.totalTalkingTime = 0;
      this.timeUntilGesture = this.getRandomTimeUntilGesture();
      this.markDirty();
   }

   public void stopTalking() {
      this.isTalking = false;
      this.talkAction = null;
      this.totalTalkingTime = 0;
      this.timeUntilGesture = 0;
      this.markDirty();
   }

   private int getRandomTimeUntilGesture() {
      return 20 + this.npc.func_70681_au().nextInt(180);
   }

   public boolean isTalking() {
      return this.isTalking;
   }

   public float getHeadYawRadians(float f) {
      return this.prevHeadYaw + (this.headYaw - this.prevHeadYaw) * f;
   }

   public float getHeadPitchRadians(float f) {
      return this.prevHeadPitch + (this.headPitch - this.prevHeadPitch) * f;
   }

   private void startClientGestureMain() {
      if (this.gestureMainTime <= 0) {
         this.gestureMainTime = this.getRandomGestureDuration();
      }

   }

   private void startClientGestureOff() {
      if (this.gestureOffTime <= 0) {
         this.gestureOffTime = this.getRandomGestureDuration();
      }

   }

   private void startClientGestureBoth() {
      if (this.gestureMainTime <= 0 && this.gestureOffTime <= 0) {
         this.gestureMainTime = this.gestureOffTime = this.getRandomGestureDuration();
      } else {
         this.startClientGestureMain();
         this.startClientGestureOff();
      }

   }

   private final int getRandomGestureDuration() {
      return 10 + this.npc.func_70681_au().nextInt(30);
   }

   public float getMainhandGestureAmount(float f) {
      if (this.gestureMainTime > 0) {
         float gesture = (float)this.prevGestureMainTick + (float)(this.gestureMainTick - this.prevGestureMainTick) * f;
         return MathHelper.func_76126_a(gesture / (float)this.gestureMainTime * 3.1415927F);
      } else {
         return 0.0F;
      }
   }

   public float getOffhandGestureAmount(float f) {
      if (this.gestureOffTime > 0) {
         float gesture = (float)this.prevGestureOffTick + (float)(this.gestureOffTick - this.prevGestureOffTick) * f;
         return MathHelper.func_76126_a(gesture / (float)this.gestureOffTime * 3.1415927F);
      } else {
         return 0.0F;
      }
   }

   public void updateAnimation() {
      this.npc.field_70170_p.func_217381_Z().func_76320_a("NPCTalkAnimations#updateAnimation");
      Random rand = this.npc.func_70681_au();
      if (!this.npc.field_70170_p.field_72995_K) {
         if (!this.isTalking) {
            this.totalTalkingTime = 0;
            this.timeUntilGesture = 0;
         } else {
            ++this.totalTalkingTime;
            if (this.talkAction == null) {
               if (this.totalTalkingTime < 10 || rand.nextInt(30) == 0) {
                  this.talkAction = NPCTalkAnimations.TalkAction.getRandomAction(rand);
                  if (this.talkAction == NPCTalkAnimations.TalkAction.TALKING) {
                     this.actionTime = 40 + rand.nextInt(60);
                     this.actionSlow = 1.0F;
                  } else if (this.talkAction == NPCTalkAnimations.TalkAction.LOOKING_AROUND) {
                     this.actionTime = 60 + rand.nextInt(60);
                     this.actionSlow = 1.0F;
                  } else if (this.talkAction == NPCTalkAnimations.TalkAction.SHAKING_HEAD) {
                     this.actionTime = 100 + rand.nextInt(60);
                     this.actionSlow = 1.0F;
                  } else if (this.talkAction == NPCTalkAnimations.TalkAction.LOOKING_UP) {
                     this.actionTime = 30 + rand.nextInt(50);
                     this.actionSlow = 1.0F;
                  }

                  this.markDirty();
               }
            } else {
               ++this.actionTick;
            }

            if (this.talkAction != null) {
               if (this.actionTick >= this.actionTime) {
                  this.talkAction = null;
                  this.actionTick = 0;
                  this.actionTime = 0;
                  this.markDirty();
               } else if (this.talkAction == NPCTalkAnimations.TalkAction.TALKING && this.actionTick % 20 == 0) {
                  this.actionSlow = 0.7F + rand.nextFloat() * 1.5F;
                  this.markDirty();
               }
            }

            --this.timeUntilGesture;
            if (this.timeUntilGesture <= 0) {
               if (rand.nextFloat() < 0.1F) {
                  this.doGestureMain = this.doGestureOff = true;
               } else if (rand.nextInt(3) == 0) {
                  this.doGestureOff = true;
               } else {
                  this.doGestureMain = true;
               }

               this.timeUntilGesture = this.getRandomTimeUntilGesture();
               this.markDirty();
            }
         }

         if (this.sendData) {
            this.sendDataToAllWatchers();
            this.sendData = false;
            this.doGestureMain = this.doGestureOff = false;
         }
      } else {
         this.prevHeadYaw = this.headYaw;
         this.prevHeadPitch = this.headPitch;
         if (this.isTalking) {
            ++this.totalTalkingTime;
            if (this.talkAction != null) {
               ++this.actionTick;
               float slow;
               if (this.talkAction == NPCTalkAnimations.TalkAction.TALKING) {
                  slow = this.actionSlow * 2.0F;
                  this.headYaw = MathHelper.func_76126_a((float)this.actionTick / slow) * (float)Math.toRadians(10.0D);
                  this.headPitch = (MathHelper.func_76126_a((float)this.actionTick / slow * 2.0F) + 1.0F) / 2.0F * (float)Math.toRadians(-20.0D);
               } else if (this.talkAction == NPCTalkAnimations.TalkAction.SHAKING_HEAD) {
                  this.actionSlow += 0.01F;
                  this.headYaw = MathHelper.func_76126_a((float)this.actionTick / this.actionSlow) * (float)Math.toRadians(30.0D);
                  this.headPitch += (float)Math.toRadians(0.4D);
               } else if (this.talkAction == NPCTalkAnimations.TalkAction.LOOKING_AROUND) {
                  slow = this.actionSlow * 16.0F;
                  this.headYaw = MathHelper.func_76126_a((float)this.actionTick / slow) * (float)Math.toRadians(50.0D);
                  this.headPitch = (MathHelper.func_76126_a((float)this.actionTick / slow * 2.0F) + 1.0F) / 2.0F * (float)Math.toRadians(-15.0D);
               } else if (this.talkAction == NPCTalkAnimations.TalkAction.LOOKING_UP) {
                  this.headYaw = 0.0F;
                  this.headPitch = (float)Math.toRadians(-20.0D);
               }
            } else {
               this.actionTick = 0;
               this.headYaw = 0.0F;
               this.headPitch = MathHelper.func_76126_a((float)this.totalTalkingTime * 0.07F) * (float)Math.toRadians(5.0D);
            }
         } else {
            this.headYaw = this.headPitch = 0.0F;
            this.totalTalkingTime = this.actionTick = 0;
         }

         this.prevGestureMainTick = this.gestureMainTick;
         this.prevGestureOffTick = this.gestureOffTick;
         if (this.gestureMainTime > 0) {
            ++this.gestureMainTick;
            if (this.prevGestureMainTick >= this.gestureMainTime) {
               this.gestureMainTime = this.prevGestureMainTick = this.gestureMainTick = 0;
            }
         }

         if (this.gestureOffTime > 0) {
            ++this.gestureOffTick;
            if (this.prevGestureOffTick > this.gestureOffTime) {
               this.gestureOffTime = this.prevGestureOffTick = this.gestureOffTick = 0;
            }
         }

         if (this.isTalking) {
            ++this.timeSinceParticle;
            if (!this.spawnedFirstParticle && this.timeSinceParticle > 20 || this.timeSinceParticle > 30 + rand.nextInt(150)) {
               this.spawnParticle(rand);
               this.timeSinceParticle = 0;
               this.spawnedFirstParticle = true;
            }
         } else {
            this.timeSinceParticle = 0;
            this.spawnedFirstParticle = false;
         }
      }

      this.npc.field_70170_p.func_217381_Z().func_76319_b();
   }

   private void markDirty() {
      if (!this.npc.field_70170_p.field_72995_K) {
         this.sendData = true;
      }

   }

   public void sendData(ServerPlayerEntity player) {
      SPacketNPCTalkAnimations packet = new SPacketNPCTalkAnimations(this);
      LOTRPacketHandler.sendTo(packet, player);
   }

   private void sendDataToAllWatchers() {
      SPacketNPCTalkAnimations packet = new SPacketNPCTalkAnimations(this);
      LOTRPacketHandler.sendToAllTrackingEntity(packet, this.npc);
   }

   public void write(PacketBuffer buf) {
      buf.func_150787_b(this.npc.func_145782_y());
      buf.writeBoolean(this.isTalking);
      buf.writeByte(this.talkAction != null ? this.talkAction.ordinal() : -1);
      buf.writeFloat(this.actionSlow);
      buf.writeBoolean(this.doGestureMain);
      buf.writeBoolean(this.doGestureOff);
   }

   public static void read(PacketBuffer buf, World world) {
      int entityId = buf.func_150792_a();
      Entity entity = world.func_73045_a(entityId);
      if (entity instanceof NPCEntity) {
         NPCEntity npc = (NPCEntity)entity;
         NPCTalkAnimations talkAnim = npc.getTalkAnimations();
         talkAnim.isTalking = buf.readBoolean();
         int actionId = buf.readByte();
         talkAnim.talkAction = actionId >= 0 ? NPCTalkAnimations.TalkAction.forId(actionId) : null;
         talkAnim.actionSlow = buf.readFloat();
         boolean doGestureMain = buf.readBoolean();
         boolean doGestureOff = buf.readBoolean();
         if (doGestureMain && doGestureOff) {
            talkAnim.startClientGestureBoth();
         } else {
            if (doGestureMain) {
               talkAnim.startClientGestureMain();
            }

            if (doGestureOff) {
               talkAnim.startClientGestureOff();
            }
         }
      }

   }

   private void spawnParticle(Random rand) {
      Vector3d eyePos = this.npc.func_174824_e(1.0F);
      Vector3d look = this.npc.func_70676_i(1.0F);
      double dx = look.func_82615_a() - eyePos.func_82615_a();
      double dz = look.func_82616_c() - eyePos.func_82616_c();
      float sideFovAngle = (float)Math.toRadians(MathHelper.func_82716_a(rand, 45.0D, 90.0D) * (double)(rand.nextBoolean() ? -1 : 1));
      Vector3d sideLook = look.func_178785_b(sideFovAngle);
      double nx = sideLook.field_72450_a;
      double nz = sideLook.field_72449_c;
      double r = MathHelper.func_82716_a(rand, 0.25D, 0.5D);
      double px = eyePos.func_82615_a() + r * nx;
      double py = eyePos.func_82617_b() + MathHelper.func_82716_a(rand, -0.2D, 0.2D);
      double pz = eyePos.func_82616_c() + r * nz;
      this.npc.field_70170_p.func_195594_a(this.getSpeechParticle(rand), px, py, pz, nx * 0.03D, 0.05D, nz * 0.03D);
   }

   private IParticleData getSpeechParticle(Random rand) {
      float f = rand.nextFloat();
      if (f < 0.1F) {
         return (IParticleData)LOTRParticles.NPC_QUESTION.get();
      } else {
         return f < 0.2F ? (IParticleData)LOTRParticles.NPC_EXCLAMATION.get() : (IParticleData)LOTRParticles.NPC_SPEECH.get();
      }
   }

   public static enum TalkAction {
      TALKING(1.0F),
      SHAKING_HEAD(0.1F),
      LOOKING_AROUND(0.3F),
      LOOKING_UP(0.4F);

      private final float weight;
      private static final float totalWeight = (float)(Double)Stream.of(values()).collect(Collectors.summingDouble((action) -> {
         return (double)action.weight;
      }));

      private TalkAction(float w) {
         this.weight = w;
      }

      public static NPCTalkAnimations.TalkAction getRandomAction(Random rand) {
         float f = rand.nextFloat() * totalWeight;
         NPCTalkAnimations.TalkAction chosen = null;
         NPCTalkAnimations.TalkAction[] var3 = values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            NPCTalkAnimations.TalkAction action = var3[var5];
            f -= action.weight;
            if (f <= 0.0F) {
               chosen = action;
               break;
            }
         }

         return chosen;
      }

      public static NPCTalkAnimations.TalkAction forId(int i) {
         return values()[MathHelper.func_76125_a(i, 0, values().length - 1)];
      }
   }
}
