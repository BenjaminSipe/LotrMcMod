package lotr.common;

import lotr.common.network.LOTRPacketEnvironmentOverlay;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;

public class LOTRDamage {
   public static DamageSource frost = (new DamageSource("lotr.frost")).func_76348_h();
   public static DamageSource poisonDrink = (new DamageSource("lotr.poisonDrink")).func_76348_h().func_82726_p();
   public static DamageSource plantHurt = (new DamageSource("lotr.plantHurt")).func_76348_h();

   public static void doFrostDamage(EntityPlayerMP entityplayer) {
      LOTRPacketEnvironmentOverlay packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.FROST);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public static void doBurnDamage(EntityPlayerMP entityplayer) {
      LOTRPacketEnvironmentOverlay packet = new LOTRPacketEnvironmentOverlay(LOTRPacketEnvironmentOverlay.Overlay.BURN);
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }
}
