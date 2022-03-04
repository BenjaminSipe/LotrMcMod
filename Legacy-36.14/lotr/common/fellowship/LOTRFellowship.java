package lotr.common.fellowship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.network.LOTRPacketFellowshipNotification;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.ForgeHooks;
import org.apache.commons.lang3.StringUtils;

public class LOTRFellowship {
   private boolean needsSave;
   private UUID fellowshipUUID;
   private String fellowshipName;
   private boolean disbanded;
   private ItemStack fellowshipIcon;
   private UUID ownerUUID;
   private List memberUUIDs;
   private Set adminUUIDs;
   private Set waypointSharerUUIDs;
   private boolean doneRetroactiveWaypointSharerCheck;
   private boolean preventPVP;
   private boolean preventHiredFF;
   private boolean showMapLocations;

   public LOTRFellowship() {
      this.needsSave = false;
      this.disbanded = false;
      this.memberUUIDs = new ArrayList();
      this.adminUUIDs = new HashSet();
      this.waypointSharerUUIDs = new HashSet();
      this.doneRetroactiveWaypointSharerCheck = true;
      this.preventPVP = true;
      this.preventHiredFF = true;
      this.showMapLocations = true;
      this.fellowshipUUID = UUID.randomUUID();
   }

   public LOTRFellowship(UUID fsID) {
      this.needsSave = false;
      this.disbanded = false;
      this.memberUUIDs = new ArrayList();
      this.adminUUIDs = new HashSet();
      this.waypointSharerUUIDs = new HashSet();
      this.doneRetroactiveWaypointSharerCheck = true;
      this.preventPVP = true;
      this.preventHiredFF = true;
      this.showMapLocations = true;
      this.fellowshipUUID = fsID;
   }

   public LOTRFellowship(UUID owner, String name) {
      this();
      this.ownerUUID = owner;
      this.fellowshipName = name;
   }

   public void createAndRegister() {
      LOTRFellowshipData.addFellowship(this);
      LOTRLevelData.getData(this.ownerUUID).addFellowship(this);
      this.updateForAllMembers(new FellowshipUpdateType.Full());
      this.markDirty();
   }

   public void validate() {
      if (this.fellowshipUUID == null) {
         this.fellowshipUUID = UUID.randomUUID();
      }

      if (this.ownerUUID == null) {
         this.ownerUUID = UUID.randomUUID();
      }

   }

   public void markDirty() {
      this.needsSave = true;
   }

   public boolean needsSave() {
      return this.needsSave;
   }

   public void save(NBTTagCompound fsData) {
      fsData.func_74757_a("Disbanded", this.disbanded);
      if (this.ownerUUID != null) {
         fsData.func_74778_a("Owner", this.ownerUUID.toString());
      }

      NBTTagList memberTags = new NBTTagList();

      NBTTagCompound nbt;
      for(Iterator var3 = this.memberUUIDs.iterator(); var3.hasNext(); memberTags.func_74742_a(nbt)) {
         UUID member = (UUID)var3.next();
         nbt = new NBTTagCompound();
         nbt.func_74778_a("Member", member.toString());
         if (this.adminUUIDs.contains(member)) {
            nbt.func_74757_a("Admin", true);
         }
      }

      fsData.func_74782_a("Members", memberTags);
      NBTTagList waypointSharerTags = new NBTTagList();
      Iterator var7 = this.waypointSharerUUIDs.iterator();

      while(var7.hasNext()) {
         UUID waypointSharer = (UUID)var7.next();
         waypointSharerTags.func_74742_a(new NBTTagString(waypointSharer.toString()));
      }

      fsData.func_74782_a("WaypointSharers", waypointSharerTags);
      if (this.fellowshipName != null) {
         fsData.func_74778_a("Name", this.fellowshipName);
      }

      if (this.fellowshipIcon != null) {
         NBTTagCompound itemData = new NBTTagCompound();
         this.fellowshipIcon.func_77955_b(itemData);
         fsData.func_74782_a("Icon", itemData);
      }

      fsData.func_74757_a("PreventPVP", this.preventPVP);
      fsData.func_74757_a("PreventHiredFF", this.preventHiredFF);
      fsData.func_74757_a("ShowMap", this.showMapLocations);
      fsData.func_74757_a("DoneRetroactiveWaypointSharerCheck", this.doneRetroactiveWaypointSharerCheck);
      this.needsSave = false;
   }

   public void load(NBTTagCompound fsData) {
      this.disbanded = fsData.func_74767_n("Disbanded");
      if (fsData.func_74764_b("Owner")) {
         this.ownerUUID = UUID.fromString(fsData.func_74779_i("Owner"));
      }

      this.memberUUIDs.clear();
      this.adminUUIDs.clear();
      NBTTagList memberTags = fsData.func_150295_c("Members", 10);

      NBTTagCompound itemData;
      UUID member;
      for(int i = 0; i < memberTags.func_74745_c(); ++i) {
         itemData = memberTags.func_150305_b(i);
         member = UUID.fromString(itemData.func_74779_i("Member"));
         if (member != null) {
            this.memberUUIDs.add(member);
            if (itemData.func_74764_b("Admin")) {
               boolean isAdmin = itemData.func_74767_n("Admin");
               if (isAdmin) {
                  this.adminUUIDs.add(member);
               }
            }
         }
      }

      this.waypointSharerUUIDs.clear();
      NBTTagList waypointSharerTags = fsData.func_150295_c("WaypointSharers", 8);

      for(int i = 0; i < waypointSharerTags.func_74745_c(); ++i) {
         member = UUID.fromString(waypointSharerTags.func_150307_f(i));
         if (member != null && this.containsPlayer(member)) {
            this.waypointSharerUUIDs.add(member);
         }
      }

      if (fsData.func_74764_b("Name")) {
         this.fellowshipName = fsData.func_74779_i("Name");
      }

      if (fsData.func_74764_b("Icon")) {
         itemData = fsData.func_74775_l("Icon");
         this.fellowshipIcon = ItemStack.func_77949_a(itemData);
      }

      if (fsData.func_74764_b("PreventPVP")) {
         this.preventPVP = fsData.func_74767_n("PreventPVP");
      }

      if (fsData.func_74764_b("PreventPVP")) {
         this.preventHiredFF = fsData.func_74767_n("PreventHiredFF");
      }

      if (fsData.func_74764_b("ShowMap")) {
         this.showMapLocations = fsData.func_74767_n("ShowMap");
      }

      this.validate();
      this.doneRetroactiveWaypointSharerCheck = fsData.func_74767_n("DoneRetroactiveWaypointSharerCheck");
   }

   public UUID getFellowshipID() {
      return this.fellowshipUUID;
   }

   public UUID getOwner() {
      return this.ownerUUID;
   }

   public boolean isOwner(UUID player) {
      return this.ownerUUID.equals(player);
   }

   public void setOwner(UUID owner) {
      UUID prevOwner = this.ownerUUID;
      if (prevOwner != null && !this.memberUUIDs.contains(prevOwner)) {
         this.memberUUIDs.add(0, prevOwner);
      }

      this.ownerUUID = owner;
      if (this.memberUUIDs.contains(owner)) {
         this.memberUUIDs.remove(owner);
      }

      if (this.adminUUIDs.contains(owner)) {
         this.adminUUIDs.remove(owner);
      }

      LOTRLevelData.getData(this.ownerUUID).addFellowship(this);
      this.updateForAllMembers(new FellowshipUpdateType.SetOwner(this.ownerUUID));
      this.markDirty();
   }

   public String getName() {
      return this.fellowshipName;
   }

   public void setName(String name) {
      this.fellowshipName = name;
      this.updateForAllMembers(new FellowshipUpdateType.Rename());
      this.markDirty();
   }

   public boolean containsPlayer(UUID player) {
      return this.isOwner(player) || this.hasMember(player);
   }

   public int getPlayerCount() {
      return this.memberUUIDs.size() + 1;
   }

   public boolean hasMember(UUID player) {
      return this.memberUUIDs.contains(player);
   }

   public void addMember(UUID player) {
      if (!this.isOwner(player) && !this.memberUUIDs.contains(player)) {
         this.memberUUIDs.add(player);
         LOTRLevelData.getData(player).addFellowship(this);
         this.updateForAllMembers(new FellowshipUpdateType.AddMember(player));
         this.markDirty();
      }

   }

   public void removeMember(UUID player) {
      if (this.memberUUIDs.contains(player)) {
         this.memberUUIDs.remove(player);
         if (this.adminUUIDs.contains(player)) {
            this.adminUUIDs.remove(player);
         }

         if (this.waypointSharerUUIDs.contains(player)) {
            this.waypointSharerUUIDs.remove(player);
         }

         LOTRLevelData.getData(player).removeFellowship(this);
         this.updateForAllMembers(new FellowshipUpdateType.RemoveMember(player));
         this.markDirty();
      }

   }

   public List getMemberUUIDs() {
      return this.memberUUIDs;
   }

   public List getAllPlayerUUIDs() {
      List list = new ArrayList();
      list.add(this.ownerUUID);
      list.addAll(this.memberUUIDs);
      return list;
   }

   public boolean isAdmin(UUID player) {
      return this.hasMember(player) && this.adminUUIDs.contains(player);
   }

   public void setAdmin(UUID player, boolean flag) {
      if (this.memberUUIDs.contains(player)) {
         if (flag && !this.adminUUIDs.contains(player)) {
            this.adminUUIDs.add(player);
            this.updateForAllMembers(new FellowshipUpdateType.SetAdmin(player));
            this.markDirty();
         } else if (!flag && this.adminUUIDs.contains(player)) {
            this.adminUUIDs.remove(player);
            this.updateForAllMembers(new FellowshipUpdateType.RemoveAdmin(player));
            this.markDirty();
         }
      }

   }

   public Set getWaypointSharerUUIDs() {
      return this.waypointSharerUUIDs;
   }

   public boolean isWaypointSharer(UUID player) {
      return this.waypointSharerUUIDs.contains(player);
   }

   public void markIsWaypointSharer(UUID player, boolean flag) {
      if (this.containsPlayer(player)) {
         if (flag && !this.waypointSharerUUIDs.contains(player)) {
            this.waypointSharerUUIDs.add(player);
            this.markDirty();
         } else if (!flag && this.waypointSharerUUIDs.contains(player)) {
            this.waypointSharerUUIDs.remove(player);
            this.markDirty();
         }
      }

   }

   public void doRetroactiveWaypointSharerCheckIfNeeded() {
      if (!this.doneRetroactiveWaypointSharerCheck) {
         this.waypointSharerUUIDs.clear();
         if (!this.disbanded) {
            List allPlayersSafe = this.getAllPlayerUUIDs();
            Iterator var2 = allPlayersSafe.iterator();

            while(var2.hasNext()) {
               UUID player = (UUID)var2.next();
               if (LOTRLevelData.getData(player).hasAnyWaypointsSharedToFellowship(this)) {
                  this.waypointSharerUUIDs.add(player);
               }
            }

            LOTRLog.logger.info("Fellowship " + this.getName() + " did retroactive waypoint sharer check and found " + this.waypointSharerUUIDs.size() + " out of " + allPlayersSafe.size() + " players");
         }

         this.doneRetroactiveWaypointSharerCheck = true;
         this.markDirty();
      }

   }

   public ItemStack getIcon() {
      return this.fellowshipIcon;
   }

   public void setIcon(ItemStack itemstack) {
      this.fellowshipIcon = itemstack;
      this.updateForAllMembers(new FellowshipUpdateType.ChangeIcon());
      this.markDirty();
   }

   public boolean getPreventPVP() {
      return this.preventPVP;
   }

   public void setPreventPVP(boolean flag) {
      this.preventPVP = flag;
      this.updateForAllMembers(new FellowshipUpdateType.TogglePvp());
      this.markDirty();
   }

   public boolean getPreventHiredFriendlyFire() {
      return this.preventHiredFF;
   }

   public void setPreventHiredFriendlyFire(boolean flag) {
      this.preventHiredFF = flag;
      this.updateForAllMembers(new FellowshipUpdateType.ToggleHiredFriendlyFire());
      this.markDirty();
   }

   public boolean getShowMapLocations() {
      return this.showMapLocations;
   }

   public void setShowMapLocations(boolean flag) {
      this.showMapLocations = flag;
      this.updateForAllMembers(new FellowshipUpdateType.ToggleShowMapLocations());
      this.markDirty();
   }

   public void updateForAllMembers(FellowshipUpdateType updateType) {
      Iterator var2 = this.getAllPlayerUUIDs().iterator();

      while(var2.hasNext()) {
         UUID player = (UUID)var2.next();
         LOTRLevelData.getData(player).updateFellowship(this, updateType);
      }

   }

   public void setDisbandedAndRemoveAllMembers() {
      this.disbanded = true;
      this.markDirty();
      List copyMemberIDs = new ArrayList(this.memberUUIDs);
      Iterator var2 = copyMemberIDs.iterator();

      while(var2.hasNext()) {
         UUID player = (UUID)var2.next();
         this.removeMember(player);
      }

   }

   public boolean isDisbanded() {
      return this.disbanded;
   }

   public void sendFellowshipMessage(EntityPlayerMP sender, String message) {
      if (sender.func_147096_v() == EnumChatVisibility.HIDDEN) {
         ChatComponentTranslation msgCannotSend = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
         msgCannotSend.func_150256_b().func_150238_a(EnumChatFormatting.RED);
         sender.field_71135_a.func_147359_a(new S02PacketChat(msgCannotSend));
      } else {
         sender.func_143004_u();
         message = StringUtils.normalizeSpace(message);
         if (StringUtils.isBlank(message)) {
            return;
         }

         for(int i = 0; i < message.length(); ++i) {
            if (!ChatAllowedCharacters.func_71566_a(message.charAt(i))) {
               sender.field_71135_a.func_147360_c("Illegal characters in chat");
               return;
            }
         }

         ClickEvent fMsgClickEvent = new ClickEvent(Action.SUGGEST_COMMAND, "/fmsg \"" + this.getName() + "\" ");
         IChatComponent msgComponent = ForgeHooks.newChatWithLinks(message);
         msgComponent.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
         IChatComponent senderComponent = sender.func_145748_c_();
         senderComponent.func_150256_b().func_150241_a(fMsgClickEvent);
         ChatComponentTranslation chatComponent = new ChatComponentTranslation("chat.type.text", new Object[]{senderComponent, ""});
         chatComponent = ForgeHooks.onServerChatEvent(sender.field_71135_a, message, chatComponent);
         if (chatComponent == null) {
            return;
         }

         chatComponent.func_150257_a(msgComponent);
         IChatComponent fsComponent = new ChatComponentTranslation("commands.lotr.fmsg.fsPrefix", new Object[]{this.getName()});
         fsComponent.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
         fsComponent.func_150256_b().func_150241_a(fMsgClickEvent);
         IChatComponent fullChatComponent = new ChatComponentTranslation("%s %s", new Object[]{fsComponent, chatComponent});
         MinecraftServer server = MinecraftServer.func_71276_C();
         server.func_145747_a(fullChatComponent);
         Packet packetChat = new S02PacketChat(fullChatComponent, false);
         Iterator var11 = server.func_71203_ab().field_72404_b.iterator();

         while(var11.hasNext()) {
            Object player = var11.next();
            EntityPlayerMP entityplayer = (EntityPlayerMP)player;
            if (this.containsPlayer(entityplayer.func_110124_au())) {
               entityplayer.field_71135_a.func_147359_a(packetChat);
            }
         }
      }

   }

   public void sendNotification(EntityPlayer entityplayer, String key, Object... args) {
      IChatComponent message = new ChatComponentTranslation(key, args);
      message.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
      entityplayer.func_145747_a(message);
      LOTRPacketFellowshipNotification packet = new LOTRPacketFellowshipNotification(message);
      LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
   }
}
