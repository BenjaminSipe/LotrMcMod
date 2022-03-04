package lotr.client.render;

import lotr.client.particle.ElvenGlowParticle;
import lotr.client.particle.FallingLeafParticle;
import lotr.client.particle.GlitterParticle;
import lotr.client.particle.MagicWaterEffectParticle;
import lotr.client.particle.NPCSpeechParticle;
import lotr.client.particle.TranslucentDrippingParticle;
import lotr.client.particle.TranslucentFallingLiquidParticle;
import lotr.client.particle.TranslucentRainParticle;
import lotr.client.particle.TranslucentSplashParticle;
import lotr.client.particle.WaterfallParticle;
import lotr.client.particle.WhiteSmokeParticle;
import lotr.common.init.LOTRParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class LOTRClientParticles {
   public static void register(ParticleFactoryRegisterEvent event) {
      Minecraft mc = Minecraft.func_71410_x();
      ParticleManager particleMgr = mc.field_71452_i;
      particleMgr.func_215234_a((ParticleType)LOTRParticles.WATERFALL.get(), WaterfallParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.BLUE_ELVEN_GLOW.get(), ElvenGlowParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.GREEN_ELVEN_GLOW.get(), ElvenGlowParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.GOLD_ELVEN_GLOW.get(), ElvenGlowParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.SILVER_ELVEN_GLOW.get(), ElvenGlowParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.MALLORN_LEAF.get(), FallingLeafParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.MIRK_OAK_LEAF.get(), FallingLeafParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.GREEN_OAK_LEAF.get(), FallingLeafParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.RED_OAK_LEAF.get(), FallingLeafParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.GLITTER.get(), GlitterParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.RAIN.get(), TranslucentRainParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.DRIPPING_WATER.get(), TranslucentDrippingParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.FALLING_WATER.get(), TranslucentFallingLiquidParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.SPLASH.get(), TranslucentSplashParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.WHITE_SMOKE.get(), WhiteSmokeParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.MORGUL_WATER_EFFECT.get(), MagicWaterEffectParticle.MorgulWaterFactory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.MIRKWOOD_WATER_EFFECT.get(), MagicWaterEffectParticle.MirkwoodWaterFactory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.NPC_SPEECH.get(), NPCSpeechParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.NPC_QUESTION.get(), NPCSpeechParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.NPC_EXCLAMATION.get(), NPCSpeechParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.CULUMALDA_POLLEN.get(), FallingLeafParticle.Factory::new);
      particleMgr.func_215234_a((ParticleType)LOTRParticles.CULUMALD.get(), FallingLeafParticle.Factory::new);
   }
}
