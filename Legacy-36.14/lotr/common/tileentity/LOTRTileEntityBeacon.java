package lotr.common.tileentity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import org.apache.commons.lang3.StringUtils;

public class LOTRTileEntityBeacon extends TileEntity {
   private static final float beaconRange = 80.0F;
   private static final float beaconRangeSq = 6400.0F;
   private int ticksExisted;
   private boolean isLit;
   private int litCounter;
   private int unlitCounter;
   private static final int lightingTime = 100;
   private long stateChangeTime = -1L;
   private String beaconName;
   private UUID beaconFellowshipID;
   private List editingPlayers = new ArrayList();

   public void setLit(boolean flag) {
      boolean wasLit = this.isLit;
      this.isLit = flag;
      if (!this.isLit) {
         this.litCounter = 0;
      } else {
         this.unlitCounter = 0;
      }

      this.updateLight();
      this.stateChangeTime = this.field_145850_b.func_82737_E();
      if (wasLit && !this.isLit) {
         this.sendFellowshipMessage(false);
      }

   }

   private void updateLight() {
      this.field_145850_b.func_147463_c(EnumSkyBlock.Block, this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public boolean isLit() {
      return this.isLit;
   }

   public boolean isFullyLit() {
      return this.isLit() && this.litCounter == 100;
   }

   public void func_145845_h() {
      ++this.ticksExisted;
      if (!this.field_145850_b.field_72995_K) {
         if (this.isLit && this.litCounter < 100) {
            ++this.litCounter;
            if (this.litCounter == 100) {
               this.updateLight();
               this.sendFellowshipMessage(true);
            }
         }

         if (!this.isLit && this.unlitCounter < 100) {
            ++this.unlitCounter;
            if (this.unlitCounter == 100) {
               this.updateLight();
            }
         }

         if (this.ticksExisted % 10 == 0) {
            boolean spreadLit = this.isLit && this.litCounter >= 100;
            boolean spreadUnlit = !this.isLit && this.unlitCounter >= 100;
            if (spreadLit || spreadUnlit) {
               List nearbyTiles = new ArrayList();
               int range = 88;
               int chunkRange = range >> 4;
               int chunkX = this.field_145851_c >> 4;
               int chunkZ = this.field_145849_e >> 4;
               ChunkCoordinates coordsThis = new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e);

               for(int i1 = -chunkRange; i1 <= chunkRange; ++i1) {
                  for(int k1 = -chunkRange; k1 <= chunkRange; ++k1) {
                     int i2 = chunkX + i1;
                     int k2 = chunkZ + k1;
                     if (this.field_145850_b.func_72863_F().func_73149_a(i2, k2)) {
                        Chunk chunk = this.field_145850_b.func_72964_e(i2, k2);
                        if (chunk != null) {
                           Iterator var14 = chunk.field_150816_i.values().iterator();

                           while(var14.hasNext()) {
                              Object obj = var14.next();
                              TileEntity te = (TileEntity)obj;
                              if (!te.func_145837_r() && te instanceof LOTRTileEntityBeacon) {
                                 LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)te;
                                 if (coordsThis.func_71569_e(beacon.field_145851_c, beacon.field_145848_d, beacon.field_145849_e) <= 6400.0F) {
                                    nearbyTiles.add(beacon);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               Iterator var21;
               LOTRTileEntityBeacon other;
               if (spreadLit) {
                  var21 = nearbyTiles.iterator();

                  while(var21.hasNext()) {
                     other = (LOTRTileEntityBeacon)var21.next();
                     if (!other.isLit && this.stateChangeTime > other.stateChangeTime) {
                        other.setLit(true);
                     }
                  }
               }

               if (spreadUnlit) {
                  var21 = nearbyTiles.iterator();

                  while(var21.hasNext()) {
                     other = (LOTRTileEntityBeacon)var21.next();
                     if (other.isLit && this.stateChangeTime > other.stateChangeTime) {
                        other.setLit(false);
                     }
                  }
               }
            }
         }
      }

      Set removePlayers = new HashSet();
      Iterator var19 = this.editingPlayers.iterator();

      while(var19.hasNext()) {
         EntityPlayer entityplayer = (EntityPlayer)var19.next();
         if (entityplayer.field_70128_L) {
            removePlayers.add(entityplayer);
         }
      }

      this.editingPlayers.removeAll(removePlayers);
   }

   public boolean isPlayerEditing(EntityPlayer entityplayer) {
      return this.editingPlayers.contains(entityplayer);
   }

   public void addEditingPlayer(EntityPlayer entityplayer) {
      if (!this.editingPlayers.contains(entityplayer)) {
         this.editingPlayers.add(entityplayer);
      }

   }

   public void releaseEditingPlayer(EntityPlayer entityplayer) {
      this.editingPlayers.remove(entityplayer);
   }

   public UUID getFellowshipID() {
      return this.beaconFellowshipID;
   }

   public String getBeaconName() {
      return this.beaconName;
   }

   public void setFellowship(LOTRFellowship fs) {
      if (fs != null) {
         this.beaconFellowshipID = fs.getFellowshipID();
      } else {
         this.beaconFellowshipID = null;
      }

      this.beaconFellowshipID = fs == null ? null : fs.getFellowshipID();
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   public void setBeaconName(String name) {
      this.beaconName = name;
      this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
      this.func_70296_d();
   }

   private void sendFellowshipMessage(boolean lit) {
      if (this.beaconFellowshipID != null) {
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(this.beaconFellowshipID);
         if (fs != null) {
            String beaconMessageName = this.beaconName;
            if (StringUtils.isBlank(beaconMessageName)) {
               beaconMessageName = fs.getName();
            }

            IChatComponent message = new ChatComponentTranslation(lit ? "container.lotr.beacon.lit" : "container.lotr.beacon.unlit", new Object[]{beaconMessageName});
            message.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
            Iterator var5 = fs.getAllPlayerUUIDs().iterator();

            while(var5.hasNext()) {
               UUID player = (UUID)var5.next();
               EntityPlayer entityplayer = this.field_145850_b.func_152378_a(player);
               if (entityplayer != null) {
                  entityplayer.func_145747_a(message);
               }
            }
         }
      }

   }

   public void func_145841_b(NBTTagCompound nbt) {
      super.func_145841_b(nbt);
      nbt.func_74757_a("IsLit", this.isLit);
      nbt.func_74774_a("LitCounter", (byte)this.litCounter);
      nbt.func_74774_a("UnlitCounter", (byte)this.unlitCounter);
      nbt.func_74772_a("StateChangeTime", this.stateChangeTime);
      if (this.beaconName != null) {
         nbt.func_74778_a("BeaconName", this.beaconName);
      }

      if (this.beaconFellowshipID != null) {
         nbt.func_74778_a("BeaconFellowship", this.beaconFellowshipID.toString());
      }

   }

   public void func_145839_a(NBTTagCompound nbt) {
      super.func_145839_a(nbt);
      this.isLit = nbt.func_74767_n("IsLit");
      this.litCounter = nbt.func_74771_c("LitCounter");
      this.unlitCounter = nbt.func_74771_c("UnlitCounter");
      this.stateChangeTime = nbt.func_74763_f("StateChangeTime");
      if (nbt.func_74764_b("BeaconName")) {
         this.beaconName = nbt.func_74779_i("BeaconName");
      } else {
         this.beaconName = null;
      }

      if (nbt.func_74764_b("BeaconFellowship")) {
         this.beaconFellowshipID = UUID.fromString(nbt.func_74779_i("BeaconFellowship"));
      } else {
         this.beaconFellowshipID = null;
      }

   }

   public Packet func_145844_m() {
      NBTTagCompound data = new NBTTagCompound();
      this.func_145841_b(data);
      return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 0, data);
   }

   public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
      NBTTagCompound data = packet.func_148857_g();
      this.func_145839_a(data);
      this.updateLight();
   }
}
