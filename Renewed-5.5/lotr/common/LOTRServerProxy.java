package lotr.common;

import java.io.File;
import java.util.Optional;
import lotr.common.entity.item.RingPortalEntity;
import lotr.common.network.SPacketSetAttackTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class LOTRServerProxy implements LOTRProxy {
   public boolean isClient() {
      return false;
   }

   public boolean isSingleplayer() {
      return false;
   }

   public File getGameRootDirectory() {
      return ServerLifecycleHooks.getCurrentServer().func_71238_n();
   }

   public World getClientWorld() {
      throw new UnsupportedOperationException("Cannot get the client world on the server side");
   }

   public PlayerEntity getClientPlayer() {
      throw new UnsupportedOperationException("Cannot get the client player on the server side");
   }

   public Optional getSidedAttackTarget(MobEntity entity) {
      return Optional.ofNullable(entity.func_70638_az());
   }

   public void receiveClientAttackTarget(SPacketSetAttackTarget packet) {
      throw new UnsupportedOperationException("Cannot receive client attack targets on the server side");
   }

   public void setInRingPortal(Entity entity, RingPortalEntity portal) {
      LOTRMod.serverTickHandler.prepareRingPortal(entity, portal);
   }
}
