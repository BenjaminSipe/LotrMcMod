package lotr.common.network;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lotr.common.LOTRMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketUpdatePlayerLocations implements IMessage {
   private List playerLocations = new ArrayList();
   private static Map cachedProfileBytes = new HashMap();

   public void addPlayerLocation(GameProfile profile, double x, double z) {
      if (profile.isComplete()) {
         this.playerLocations.add(new LOTRPacketUpdatePlayerLocations.PlayerLocationInfo(profile, x, z));
      }

   }

   public void toBytes(ByteBuf data) {
      int players = this.playerLocations.size();
      data.writeShort(players);
      Iterator var3 = this.playerLocations.iterator();

      while(var3.hasNext()) {
         LOTRPacketUpdatePlayerLocations.PlayerLocationInfo player = (LOTRPacketUpdatePlayerLocations.PlayerLocationInfo)var3.next();
         GameProfile profile = player.playerProfile;
         UUID playerID = profile.getId();
         byte[] profileBytes = null;
         if (cachedProfileBytes.containsKey(playerID)) {
            profileBytes = (byte[])cachedProfileBytes.get(playerID);
         } else {
            ByteBuf tempBuf = Unpooled.buffer();

            try {
               NBTTagCompound profileData = new NBTTagCompound();
               NBTUtil.func_152460_a(profileData, profile);
               (new PacketBuffer(tempBuf)).func_150786_a(profileData);
               byte[] tempBytes = tempBuf.array();
               profileBytes = Arrays.copyOf(tempBytes, tempBytes.length);
               cachedProfileBytes.put(playerID, profileBytes);
            } catch (IOException var11) {
               FMLLog.severe("Error writing player profile data", new Object[0]);
               var11.printStackTrace();
            }
         }

         if (profileBytes == null) {
            data.writeShort(-1);
         } else {
            byte[] copied = Arrays.copyOf(profileBytes, profileBytes.length);
            data.writeShort(copied.length);
            data.writeBytes(copied);
            data.writeDouble(player.posX);
            data.writeDouble(player.posZ);
         }
      }

   }

   public void fromBytes(ByteBuf data) {
      this.playerLocations.clear();
      int players = data.readShort();

      for(int l = 0; l < players; ++l) {
         int len = data.readShort();
         if (len >= 0) {
            ByteBuf profileBytes = data.readBytes(len);
            GameProfile profile = null;

            try {
               NBTTagCompound profileData = (new PacketBuffer(profileBytes)).func_150793_b();
               profile = NBTUtil.func_152459_a(profileData);
            } catch (IOException var12) {
               FMLLog.severe("Error reading player profile data", new Object[0]);
               var12.printStackTrace();
            }

            double posX = data.readDouble();
            double posZ = data.readDouble();
            if (profile != null) {
               LOTRPacketUpdatePlayerLocations.PlayerLocationInfo playerInfo = new LOTRPacketUpdatePlayerLocations.PlayerLocationInfo(profile, posX, posZ);
               this.playerLocations.add(playerInfo);
            }
         }
      }

   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketUpdatePlayerLocations packet, MessageContext context) {
         LOTRMod.proxy.clearMapPlayerLocations();
         Iterator var3 = packet.playerLocations.iterator();

         while(var3.hasNext()) {
            LOTRPacketUpdatePlayerLocations.PlayerLocationInfo info = (LOTRPacketUpdatePlayerLocations.PlayerLocationInfo)var3.next();
            LOTRMod.proxy.addMapPlayerLocation(info.playerProfile, info.posX, info.posZ);
         }

         return null;
      }
   }

   private static class PlayerLocationInfo {
      public final GameProfile playerProfile;
      public final double posX;
      public final double posZ;

      public PlayerLocationInfo(GameProfile profile, double x, double z) {
         this.playerProfile = profile;
         this.posX = x;
         this.posZ = z;
      }
   }
}
