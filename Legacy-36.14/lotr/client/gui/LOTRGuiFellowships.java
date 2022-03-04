package lotr.client.gui;

import com.google.common.math.IntMath;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRTitle;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.network.LOTRPacketFellowshipAcceptInviteResult;
import lotr.common.network.LOTRPacketFellowshipCreate;
import lotr.common.network.LOTRPacketFellowshipDisband;
import lotr.common.network.LOTRPacketFellowshipDoPlayer;
import lotr.common.network.LOTRPacketFellowshipInvitePlayer;
import lotr.common.network.LOTRPacketFellowshipLeave;
import lotr.common.network.LOTRPacketFellowshipRename;
import lotr.common.network.LOTRPacketFellowshipRespondInvite;
import lotr.common.network.LOTRPacketFellowshipSetIcon;
import lotr.common.network.LOTRPacketFellowshipToggle;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiFellowships extends LOTRGuiMenuBase {
   public static final ResourceLocation iconsTextures = new ResourceLocation("lotr:gui/fellowships.png");
   private LOTRGuiFellowships.Page page;
   private List allFellowshipsLeading;
   private List allFellowshipsOther;
   private List allFellowshipInvites;
   private LOTRFellowshipClient mouseOverFellowship;
   private LOTRFellowshipClient viewingFellowship;
   private UUID mouseOverPlayer;
   private boolean mouseOverPlayerRemove;
   private boolean mouseOverPlayerOp;
   private boolean mouseOverPlayerDeop;
   private boolean mouseOverPlayerTransfer;
   private UUID removingPlayer;
   private UUID oppingPlayer;
   private UUID deoppingPlayer;
   private UUID transferringPlayer;
   private boolean mouseOverInviteAccept;
   private boolean mouseOverInviteReject;
   private LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult acceptInviteResult;
   private String acceptInviteResultFellowshipName;
   private GuiButton buttonCreate;
   private GuiButton buttonCreateThis;
   private LOTRGuiButtonFsOption buttonInvitePlayer;
   private GuiButton buttonInviteThis;
   private LOTRGuiButtonFsOption buttonDisband;
   private GuiButton buttonDisbandThis;
   private GuiButton buttonLeave;
   private GuiButton buttonLeaveThis;
   private LOTRGuiButtonFsOption buttonSetIcon;
   private GuiButton buttonRemove;
   private GuiButton buttonTransfer;
   private LOTRGuiButtonFsOption buttonRename;
   private GuiButton buttonRenameThis;
   private GuiButton buttonBack;
   private GuiButton buttonInvites;
   private LOTRGuiButtonFsOption buttonPVP;
   private LOTRGuiButtonFsOption buttonHiredFF;
   private LOTRGuiButtonFsOption buttonMapShow;
   private GuiButton buttonOp;
   private GuiButton buttonDeop;
   private List orderedFsOptionButtons;
   private GuiTextField textFieldName;
   private GuiTextField textFieldPlayer;
   private GuiTextField textFieldRename;
   private static final int MAX_NAME_LENGTH = 40;
   public static final int entrySplit = 5;
   public static final int entryBorder = 10;
   public static final int selectBorder = 2;
   private int scrollWidgetWidth;
   private int scrollWidgetHeight;
   private int scrollBarX;
   private LOTRGuiScrollPane scrollPaneLeading;
   private LOTRGuiScrollPane scrollPaneOther;
   private LOTRGuiScrollPane scrollPaneMembers;
   private LOTRGuiScrollPane scrollPaneInvites;
   private static final int maxDisplayedFellowships = 12;
   private int displayedFellowshipsLeading;
   private int displayedFellowshipsOther;
   private static final int maxDisplayedMembers = 11;
   private int displayedMembers;
   private static final int maxDisplayedInvites = 15;
   private int displayedInvites;
   private int tickCounter;

   public LOTRGuiFellowships() {
      this.page = LOTRGuiFellowships.Page.LIST;
      this.allFellowshipsLeading = new ArrayList();
      this.allFellowshipsOther = new ArrayList();
      this.allFellowshipInvites = new ArrayList();
      this.orderedFsOptionButtons = new ArrayList();
      this.xSize = 256;
      this.scrollWidgetWidth = 9;
      this.scrollWidgetHeight = 8;
      this.scrollBarX = this.xSize + 2 + 1;
      this.scrollPaneLeading = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
      this.scrollPaneOther = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
      this.scrollPaneMembers = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
      this.scrollPaneInvites = new LOTRGuiScrollPane(this.scrollWidgetWidth, this.scrollWidgetHeight);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if (this.field_146297_k.field_71439_g != null) {
         this.refreshFellowshipList();
      }

      int midX = this.guiLeft + this.xSize / 2;
      this.field_146292_n.add(this.buttonCreate = new GuiButton(0, midX - 100, this.guiTop + 230, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.create")));
      this.field_146292_n.add(this.buttonCreateThis = new GuiButton(1, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.createThis")));
      this.field_146292_n.add(this.buttonInvitePlayer = new LOTRGuiButtonFsOption(2, midX, this.guiTop + 232, 0, 48, StatCollector.func_74838_a("lotr.gui.fellowships.invite")));
      this.field_146292_n.add(this.buttonInviteThis = new GuiButton(3, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.inviteThis")));
      this.field_146292_n.add(this.buttonDisband = new LOTRGuiButtonFsOption(4, midX, this.guiTop + 232, 16, 48, StatCollector.func_74838_a("lotr.gui.fellowships.disband")));
      this.field_146292_n.add(this.buttonDisbandThis = new GuiButton(5, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.disbandThis")));
      this.field_146292_n.add(this.buttonLeave = new GuiButton(6, midX - 60, this.guiTop + 230, 120, 20, StatCollector.func_74838_a("lotr.gui.fellowships.leave")));
      this.field_146292_n.add(this.buttonLeaveThis = new GuiButton(7, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.leaveThis")));
      this.field_146292_n.add(this.buttonSetIcon = new LOTRGuiButtonFsOption(8, midX, this.guiTop + 232, 48, 48, StatCollector.func_74838_a("lotr.gui.fellowships.setIcon")));
      this.field_146292_n.add(this.buttonRemove = new GuiButton(9, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.remove")));
      this.field_146292_n.add(this.buttonTransfer = new GuiButton(10, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.transfer")));
      this.field_146292_n.add(this.buttonRename = new LOTRGuiButtonFsOption(11, midX, this.guiTop + 232, 32, 48, StatCollector.func_74838_a("lotr.gui.fellowships.rename")));
      this.field_146292_n.add(this.buttonRenameThis = new GuiButton(12, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.renameThis")));
      this.field_146292_n.add(this.buttonBack = new GuiButton(13, this.guiLeft - 10, this.guiTop, 20, 20, "<"));
      this.field_146292_n.add(this.buttonInvites = new LOTRGuiButtonFsInvites(14, this.guiLeft + this.xSize - 16, this.guiTop, ""));
      this.field_146292_n.add(this.buttonPVP = new LOTRGuiButtonFsOption(15, midX, this.guiTop + 232, 64, 48, StatCollector.func_74838_a("lotr.gui.fellowships.togglePVP")));
      this.field_146292_n.add(this.buttonHiredFF = new LOTRGuiButtonFsOption(16, midX, this.guiTop + 232, 80, 48, StatCollector.func_74838_a("lotr.gui.fellowships.toggleHiredFF")));
      this.field_146292_n.add(this.buttonMapShow = new LOTRGuiButtonFsOption(17, midX, this.guiTop + 232, 96, 48, StatCollector.func_74838_a("lotr.gui.fellowships.toggleMapShow")));
      this.field_146292_n.add(this.buttonOp = new GuiButton(18, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.op")));
      this.field_146292_n.add(this.buttonDeop = new GuiButton(19, midX - 100, this.guiTop + 170, 200, 20, StatCollector.func_74838_a("lotr.gui.fellowships.deop")));
      this.orderedFsOptionButtons.clear();
      this.orderedFsOptionButtons.add(this.buttonInvitePlayer);
      this.orderedFsOptionButtons.add(this.buttonDisband);
      this.orderedFsOptionButtons.add(this.buttonRename);
      this.orderedFsOptionButtons.add(this.buttonSetIcon);
      this.orderedFsOptionButtons.add(this.buttonMapShow);
      this.orderedFsOptionButtons.add(this.buttonPVP);
      this.orderedFsOptionButtons.add(this.buttonHiredFF);
      this.textFieldName = new GuiTextField(this.field_146289_q, midX - 80, this.guiTop + 40, 160, 20);
      this.textFieldName.func_146203_f(40);
      this.textFieldPlayer = new GuiTextField(this.field_146289_q, midX - 80, this.guiTop + 40, 160, 20);
      this.textFieldRename = new GuiTextField(this.field_146289_q, midX - 80, this.guiTop + 40, 160, 20);
      this.textFieldRename.func_146203_f(40);
   }

   private void refreshFellowshipList() {
      this.allFellowshipsLeading.clear();
      this.allFellowshipsOther.clear();
      List fellowships = new ArrayList(LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getClientFellowships());
      Iterator var2 = fellowships.iterator();

      while(var2.hasNext()) {
         LOTRFellowshipClient fs = (LOTRFellowshipClient)var2.next();
         if (fs.isOwned()) {
            this.allFellowshipsLeading.add(fs);
         } else {
            this.allFellowshipsOther.add(fs);
         }
      }

      this.allFellowshipInvites.clear();
      this.allFellowshipInvites.addAll(LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getClientFellowshipInvites());
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.tickCounter;
      this.refreshFellowshipList();
      this.textFieldName.func_146178_a();
      if (this.page != LOTRGuiFellowships.Page.CREATE) {
         this.textFieldName.func_146180_a("");
      }

      this.textFieldPlayer.func_146178_a();
      if (this.page != LOTRGuiFellowships.Page.INVITE || this.isFellowshipMaxSize(this.viewingFellowship)) {
         this.textFieldPlayer.func_146180_a("");
      }

      this.textFieldRename.func_146178_a();
      if (this.page != LOTRGuiFellowships.Page.RENAME) {
         this.textFieldRename.func_146180_a("");
      }

   }

   private boolean isFellowshipMaxSize(LOTRFellowshipClient fellowship) {
      if (fellowship == null) {
         return false;
      } else {
         int limit = LOTRConfig.getFellowshipMaxSize(this.field_146297_k.field_71441_e);
         return limit >= 0 && fellowship.getPlayerCount() >= limit;
      }
   }

   private void alignOptionButtons() {
      List activeOptionButtons = new ArrayList();
      Iterator var2 = this.orderedFsOptionButtons.iterator();

      while(var2.hasNext()) {
         GuiButton button = (GuiButton)var2.next();
         if (button.field_146125_m) {
            activeOptionButtons.add(button);
         }
      }

      if (this.buttonLeave.field_146125_m) {
         activeOptionButtons.add(this.buttonLeave);
      }

      int midX = this.guiLeft + this.xSize / 2;
      int numActive = activeOptionButtons.size();
      if (numActive > 0) {
         int gap = 8;
         int allWidth = 0;

         GuiButton button;
         for(Iterator var6 = activeOptionButtons.iterator(); var6.hasNext(); allWidth += button.field_146120_f) {
            button = (GuiButton)var6.next();
            if (allWidth > 0) {
               allWidth += gap;
            }
         }

         int x = midX - allWidth / 2;

         for(int i = 0; i < activeOptionButtons.size(); ++i) {
            GuiButton button = (GuiButton)activeOptionButtons.get(i);
            button.field_146128_h = x;
            x += button.field_146120_f;
            x += gap;
         }
      }

   }

   public void func_73863_a(int i, int j, float f) {
      LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
      boolean viewingOwned = this.viewingFellowship != null && this.viewingFellowship.isOwned();
      boolean viewingAdminned = this.viewingFellowship != null && this.viewingFellowship.isAdminned();
      this.mouseOverFellowship = null;
      this.mouseOverPlayer = null;
      this.mouseOverPlayerRemove = false;
      this.mouseOverPlayerOp = false;
      this.mouseOverPlayerDeop = false;
      this.mouseOverPlayerTransfer = false;
      if (this.page != LOTRGuiFellowships.Page.REMOVE) {
         this.removingPlayer = null;
      }

      if (this.page != LOTRGuiFellowships.Page.OP) {
         this.oppingPlayer = null;
      }

      if (this.page != LOTRGuiFellowships.Page.DEOP) {
         this.deoppingPlayer = null;
      }

      if (this.page != LOTRGuiFellowships.Page.TRANSFER) {
         this.transferringPlayer = null;
      }

      this.mouseOverInviteAccept = false;
      this.mouseOverInviteReject = false;
      if (this.page != LOTRGuiFellowships.Page.ACCEPT_INVITE_RESULT) {
         this.acceptInviteResult = null;
         this.acceptInviteResultFellowshipName = null;
      }

      boolean creationEnabled = LOTRConfig.isFellowshipCreationEnabled(this.field_146297_k.field_71441_e);
      boolean canPlayerCreateNew = playerData.canCreateFellowships(true);
      this.buttonCreate.field_146125_m = this.page == LOTRGuiFellowships.Page.LIST;
      this.buttonCreate.field_146124_l = this.buttonCreate.field_146125_m && creationEnabled && canPlayerCreateNew;
      this.buttonCreateThis.field_146125_m = this.page == LOTRGuiFellowships.Page.CREATE;
      String checkValidName = this.checkValidFellowshipName(this.textFieldName.func_146179_b());
      this.buttonCreateThis.field_146124_l = this.buttonCreateThis.field_146125_m && checkValidName == null;
      this.buttonInvitePlayer.field_146125_m = this.buttonInvitePlayer.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
      boolean canInvite = this.page == LOTRGuiFellowships.Page.INVITE && !this.isFellowshipMaxSize(this.viewingFellowship);
      this.buttonInviteThis.field_146125_m = canInvite;
      String checkValidPlayer = "";
      if (canInvite) {
         checkValidPlayer = this.checkValidPlayerName(this.textFieldPlayer.func_146179_b());
         this.buttonInviteThis.field_146124_l = this.buttonInviteThis.field_146125_m && checkValidPlayer == null;
      }

      this.buttonDisband.field_146125_m = this.buttonDisband.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && viewingOwned;
      this.buttonDisbandThis.field_146125_m = this.buttonDisbandThis.field_146124_l = this.page == LOTRGuiFellowships.Page.DISBAND;
      this.buttonLeave.field_146125_m = this.buttonLeave.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && !viewingOwned;
      this.buttonLeaveThis.field_146125_m = this.buttonLeaveThis.field_146124_l = this.page == LOTRGuiFellowships.Page.LEAVE;
      this.buttonSetIcon.field_146125_m = this.buttonSetIcon.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
      this.buttonRemove.field_146125_m = this.buttonRemove.field_146124_l = this.page == LOTRGuiFellowships.Page.REMOVE;
      this.buttonTransfer.field_146125_m = this.buttonTransfer.field_146124_l = this.page == LOTRGuiFellowships.Page.TRANSFER;
      this.buttonRename.field_146125_m = this.buttonRename.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && viewingOwned;
      this.buttonRenameThis.field_146125_m = this.page == LOTRGuiFellowships.Page.RENAME;
      String checkValidRename = this.checkValidFellowshipName(this.textFieldRename.func_146179_b());
      this.buttonRenameThis.field_146124_l = this.buttonRenameThis.field_146125_m && checkValidRename == null;
      this.buttonBack.field_146125_m = this.buttonBack.field_146124_l = this.page != LOTRGuiFellowships.Page.LIST;
      this.buttonInvites.field_146125_m = this.buttonInvites.field_146124_l = this.page == LOTRGuiFellowships.Page.LIST;
      this.buttonPVP.field_146125_m = this.buttonPVP.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
      if (this.buttonPVP.field_146124_l) {
         this.buttonPVP.setIconUV(64, this.viewingFellowship.getPreventPVP() ? 80 : 48);
      }

      this.buttonHiredFF.field_146125_m = this.buttonHiredFF.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && (viewingOwned || viewingAdminned);
      if (this.buttonHiredFF.field_146124_l) {
         this.buttonHiredFF.setIconUV(80, this.viewingFellowship.getPreventHiredFriendlyFire() ? 80 : 48);
      }

      this.buttonMapShow.field_146125_m = this.buttonMapShow.field_146124_l = this.page == LOTRGuiFellowships.Page.FELLOWSHIP && viewingOwned;
      if (this.buttonMapShow.field_146124_l) {
         this.buttonMapShow.setIconUV(96, this.viewingFellowship.getShowMapLocations() ? 48 : 80);
      }

      this.buttonOp.field_146125_m = this.buttonOp.field_146124_l = this.page == LOTRGuiFellowships.Page.OP;
      this.buttonDeop.field_146125_m = this.buttonDeop.field_146124_l = this.page == LOTRGuiFellowships.Page.DEOP;
      this.alignOptionButtons();
      this.setupScrollBars(i, j);
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.func_73863_a(i, j, f);
      String s = StatCollector.func_74838_a("lotr.gui.fellowships.title");
      this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
      int x;
      int y;
      List lines;
      int iconHFFX;
      int iconMapX;
      if (this.page == LOTRGuiFellowships.Page.LIST) {
         x = this.guiLeft;
         y = this.scrollPaneLeading.paneY0;
         s = StatCollector.func_74838_a("lotr.gui.fellowships.leading");
         this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
         y += this.field_146289_q.field_78288_b + 10;
         lines = this.sortFellowshipsForDisplay(this.allFellowshipsLeading);
         int[] leadingMinMax = this.scrollPaneLeading.getMinMaxIndices(lines, this.displayedFellowshipsLeading);

         for(int index = leadingMinMax[0]; index <= leadingMinMax[1]; ++index) {
            LOTRFellowshipClient fs = (LOTRFellowshipClient)lines.get(index);
            this.drawFellowshipEntry(fs, x, y, i, j, false);
            y += this.field_146289_q.field_78288_b + 5;
         }

         y = this.scrollPaneOther.paneY0;
         s = StatCollector.func_74838_a("lotr.gui.fellowships.member");
         this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
         y += this.field_146289_q.field_78288_b + 10;
         List sortedOther = this.sortFellowshipsForDisplay(this.allFellowshipsOther);
         int[] otherMinMax = this.scrollPaneOther.getMinMaxIndices(sortedOther, this.displayedFellowshipsOther);

         for(iconHFFX = otherMinMax[0]; iconHFFX <= otherMinMax[1]; ++iconHFFX) {
            LOTRFellowshipClient fs = (LOTRFellowshipClient)sortedOther.get(iconHFFX);
            this.drawFellowshipEntry(fs, x, y, i, j, false);
            y += this.field_146289_q.field_78288_b + 5;
         }

         String invites = String.valueOf(playerData.getClientFellowshipInvites().size());
         iconMapX = this.buttonInvites.field_146128_h - 2 - this.field_146289_q.func_78256_a(invites);
         int invitesY = this.buttonInvites.field_146129_i + this.buttonInvites.field_146121_g / 2 - this.field_146289_q.field_78288_b / 2;
         this.field_146289_q.func_78276_b(invites, iconMapX, invitesY, 16777215);
         if (this.buttonInvites.func_146115_a()) {
            this.renderIconTooltip(i, j, StatCollector.func_74838_a("lotr.gui.fellowships.invitesTooltip"));
         }

         if (this.buttonCreate.func_146115_a()) {
            if (!creationEnabled) {
               s = StatCollector.func_74838_a("lotr.gui.fellowships.creationDisabled");
               this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.buttonCreate.field_146129_i + this.buttonCreate.field_146121_g + 4, 16777215);
            } else if (!canPlayerCreateNew) {
               s = StatCollector.func_74838_a("lotr.gui.fellowships.createLimit");
               this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.buttonCreate.field_146129_i + this.buttonCreate.field_146121_g + 4, 16777215);
            }
         }

         if (this.scrollPaneLeading.hasScrollBar) {
            this.scrollPaneLeading.drawScrollBar();
         }

         if (this.scrollPaneOther.hasScrollBar) {
            this.scrollPaneOther.drawScrollBar();
         }
      } else if (this.page == LOTRGuiFellowships.Page.CREATE) {
         s = StatCollector.func_74838_a("lotr.gui.fellowships.createName");
         this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.textFieldName.field_146210_g - 4 - this.field_146289_q.field_78288_b, 16777215);
         this.textFieldName.func_146194_f();
         if (checkValidName != null) {
            this.drawCenteredString(checkValidName, this.guiLeft + this.xSize / 2, this.textFieldName.field_146210_g + this.textFieldName.field_146219_i + this.field_146289_q.field_78288_b, 16711680);
         }
      } else if (this.page == LOTRGuiFellowships.Page.FELLOWSHIP) {
         x = this.guiLeft;
         y = this.guiTop + 10;
         s = StatCollector.func_74837_a("lotr.gui.fellowships.nameAndPlayers", new Object[]{this.viewingFellowship.getName(), this.viewingFellowship.getPlayerCount()});
         this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
         y += this.field_146289_q.field_78288_b;
         y += 5;
         if (this.viewingFellowship.getIcon() != null) {
            this.drawFellowshipIcon(this.viewingFellowship, this.guiLeft + this.xSize / 2 - 8, y, 1.0F);
         }

         boolean preventPVP = this.viewingFellowship.getPreventPVP();
         boolean preventHiredFF = this.viewingFellowship.getPreventHiredFriendlyFire();
         boolean mapShow = this.viewingFellowship.getShowMapLocations();
         int iconPVPX = this.guiLeft + this.xSize - 36;
         iconHFFX = this.guiLeft + this.xSize - 16;
         iconMapX = this.guiLeft + this.xSize - 56;
         int iconSize = 16;
         this.field_146297_k.func_110434_K().func_110577_a(iconsTextures);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(iconPVPX, y, 64, preventPVP ? 80 : 48, iconSize, iconSize);
         this.func_73729_b(iconHFFX, y, 80, preventHiredFF ? 80 : 48, iconSize, iconSize);
         this.func_73729_b(iconMapX, y, 96, mapShow ? 48 : 80, iconSize, iconSize);
         if (i >= iconPVPX && i < iconPVPX + iconSize && j >= y && j < y + iconSize) {
            this.renderIconTooltip(i, j, StatCollector.func_74838_a(preventPVP ? "lotr.gui.fellowships.pvp.prevent" : "lotr.gui.fellowships.pvp.allow"));
         }

         if (i >= iconHFFX && i < iconHFFX + iconSize && j >= y && j < y + iconSize) {
            this.renderIconTooltip(i, j, StatCollector.func_74838_a(preventHiredFF ? "lotr.gui.fellowships.hiredFF.prevent" : "lotr.gui.fellowships.hiredFF.allow"));
         }

         if (i >= iconMapX && i < iconMapX + iconSize && j >= y && j < y + iconSize) {
            this.renderIconTooltip(i, j, StatCollector.func_74838_a(mapShow ? "lotr.gui.fellowships.mapShow.on" : "lotr.gui.fellowships.mapShow.off"));
         }

         y += iconSize;
         y += 10;
         int titleOffset = 0;
         Iterator var25 = this.viewingFellowship.getAllPlayerUuids().iterator();

         while(var25.hasNext()) {
            UUID playerUuid = (UUID)var25.next();
            LOTRTitle.PlayerTitle title = this.viewingFellowship.getTitleFor(playerUuid);
            if (title != null) {
               String titleName = title.getFormattedTitle(this.field_146297_k.field_71439_g);
               int thisTitleWidth = this.field_146289_q.func_78256_a(titleName + " ");
               titleOffset = Math.max(titleOffset, thisTitleWidth);
            }
         }

         this.drawPlayerEntry(this.viewingFellowship.getOwnerProfile(), x, y, titleOffset, i, j);
         y += this.field_146289_q.field_78288_b + 10;
         List membersSorted = this.sortMembersForDisplay(this.viewingFellowship);
         int[] membersMinMax = this.scrollPaneMembers.getMinMaxIndices(membersSorted, this.displayedMembers);

         for(int index = membersMinMax[0]; index <= membersMinMax[1]; ++index) {
            GameProfile member = (GameProfile)membersSorted.get(index);
            this.drawPlayerEntry(member, x, y, titleOffset, i, j);
            y += this.field_146289_q.field_78288_b + 5;
         }

         Iterator var47 = this.field_146292_n.iterator();

         while(var47.hasNext()) {
            Object bObj = var47.next();
            GuiButton button = (GuiButton)bObj;
            if (button instanceof LOTRGuiButtonFsOption && button.field_146125_m && button.func_146115_a()) {
               s = button.field_146126_j;
               this.drawCenteredString(s, this.guiLeft + this.xSize / 2, button.field_146129_i + button.field_146121_g + 4, 16777215);
            }
         }

         if (this.scrollPaneMembers.hasScrollBar) {
            this.scrollPaneMembers.drawScrollBar();
         }
      } else {
         Iterator var32;
         String line;
         if (this.page == LOTRGuiFellowships.Page.INVITE) {
            if (this.isFellowshipMaxSize(this.viewingFellowship)) {
               x = this.guiLeft + this.xSize / 2;
               y = this.guiTop + 30;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.invite.maxSize", new Object[]{this.viewingFellowship.getName(), LOTRConfig.getFellowshipMaxSize(this.field_146297_k.field_71441_e)});
               lines = this.field_146289_q.func_78271_c(s, this.xSize);

               for(var32 = lines.iterator(); var32.hasNext(); y += this.field_146289_q.field_78288_b) {
                  line = (String)var32.next();
                  this.drawCenteredString(line, x, y, 16777215);
               }
            } else {
               s = StatCollector.func_74837_a("lotr.gui.fellowships.inviteName", new Object[]{this.viewingFellowship.getName()});
               this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.textFieldPlayer.field_146210_g - 4 - this.field_146289_q.field_78288_b, 16777215);
               this.textFieldPlayer.func_146194_f();
               if (checkValidPlayer != null) {
                  this.drawCenteredString(checkValidPlayer, this.guiLeft + this.xSize / 2, this.textFieldPlayer.field_146210_g + this.textFieldPlayer.field_146219_i + this.field_146289_q.field_78288_b, 16711680);
               }
            }
         } else {
            int var10000;
            if (this.page == LOTRGuiFellowships.Page.DISBAND) {
               x = this.guiLeft + this.xSize / 2;
               y = this.guiTop + 30;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.disbandCheck1", new Object[]{this.viewingFellowship.getName()});
               this.drawCenteredString(s, x, y, 16777215);
               y += this.field_146289_q.field_78288_b;
               s = StatCollector.func_74838_a("lotr.gui.fellowships.disbandCheck2");
               this.drawCenteredString(s, x, y, 16777215);
               y += this.field_146289_q.field_78288_b * 2;
               s = StatCollector.func_74838_a("lotr.gui.fellowships.disbandCheck3");
               this.drawCenteredString(s, x, y, 16777215);
               var10000 = y + this.field_146289_q.field_78288_b;
            } else if (this.page == LOTRGuiFellowships.Page.LEAVE) {
               x = this.guiLeft + this.xSize / 2;
               y = this.guiTop + 30;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.leaveCheck1", new Object[]{this.viewingFellowship.getName()});
               this.drawCenteredString(s, x, y, 16777215);
               y += this.field_146289_q.field_78288_b;
               s = StatCollector.func_74838_a("lotr.gui.fellowships.leaveCheck2");
               this.drawCenteredString(s, x, y, 16777215);
               var10000 = y + this.field_146289_q.field_78288_b * 2;
            } else if (this.page == LOTRGuiFellowships.Page.REMOVE) {
               x = this.guiLeft + this.xSize / 2;
               y = this.guiTop + 30;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.removeCheck", new Object[]{this.viewingFellowship.getName(), this.viewingFellowship.getUsernameFor(this.removingPlayer)});
               lines = this.field_146289_q.func_78271_c(s, this.xSize);

               for(var32 = lines.iterator(); var32.hasNext(); y += this.field_146289_q.field_78288_b) {
                  line = (String)var32.next();
                  this.drawCenteredString(line, x, y, 16777215);
               }
            } else if (this.page == LOTRGuiFellowships.Page.OP) {
               x = this.guiLeft + this.xSize / 2;
               y = this.guiTop + 30;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.opCheck1", new Object[]{this.viewingFellowship.getName(), this.viewingFellowship.getUsernameFor(this.oppingPlayer)});
               lines = this.field_146289_q.func_78271_c(s, this.xSize);

               for(var32 = lines.iterator(); var32.hasNext(); y += this.field_146289_q.field_78288_b) {
                  line = (String)var32.next();
                  this.drawCenteredString(line, x, y, 16777215);
               }

               y += this.field_146289_q.field_78288_b;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.opCheck2", new Object[]{this.viewingFellowship.getName(), this.viewingFellowship.getUsernameFor(this.oppingPlayer)});
               lines = this.field_146289_q.func_78271_c(s, this.xSize);

               for(var32 = lines.iterator(); var32.hasNext(); y += this.field_146289_q.field_78288_b) {
                  line = (String)var32.next();
                  this.drawCenteredString(line, x, y, 16777215);
               }
            } else if (this.page == LOTRGuiFellowships.Page.DEOP) {
               x = this.guiLeft + this.xSize / 2;
               y = this.guiTop + 30;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.deopCheck", new Object[]{this.viewingFellowship.getName(), this.viewingFellowship.getUsernameFor(this.deoppingPlayer)});
               lines = this.field_146289_q.func_78271_c(s, this.xSize);

               for(var32 = lines.iterator(); var32.hasNext(); y += this.field_146289_q.field_78288_b) {
                  line = (String)var32.next();
                  this.drawCenteredString(line, x, y, 16777215);
               }
            } else if (this.page == LOTRGuiFellowships.Page.TRANSFER) {
               x = this.guiLeft + this.xSize / 2;
               y = this.guiTop + 30;
               s = StatCollector.func_74837_a("lotr.gui.fellowships.transferCheck1", new Object[]{this.viewingFellowship.getName(), this.viewingFellowship.getUsernameFor(this.transferringPlayer)});
               lines = this.field_146289_q.func_78271_c(s, this.xSize);

               for(var32 = lines.iterator(); var32.hasNext(); y += this.field_146289_q.field_78288_b) {
                  line = (String)var32.next();
                  this.drawCenteredString(line, x, y, 16777215);
               }

               y += this.field_146289_q.field_78288_b;
               s = StatCollector.func_74838_a("lotr.gui.fellowships.transferCheck2");
               this.drawCenteredString(s, x, y, 16777215);
               var10000 = y + this.field_146289_q.field_78288_b;
            } else if (this.page == LOTRGuiFellowships.Page.RENAME) {
               s = StatCollector.func_74837_a("lotr.gui.fellowships.renameName", new Object[]{this.viewingFellowship.getName()});
               this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.textFieldRename.field_146210_g - 4 - this.field_146289_q.field_78288_b, 16777215);
               this.textFieldRename.func_146194_f();
               if (checkValidRename != null) {
                  this.drawCenteredString(checkValidRename, this.guiLeft + this.xSize / 2, this.textFieldRename.field_146210_g + this.textFieldRename.field_146219_i + this.field_146289_q.field_78288_b, 16711680);
               }
            } else {
               int l;
               if (this.page == LOTRGuiFellowships.Page.INVITATIONS) {
                  x = this.guiLeft;
                  y = this.guiTop + 10;
                  s = StatCollector.func_74838_a("lotr.gui.fellowships.invites");
                  this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
                  y += this.field_146289_q.field_78288_b + 10;
                  if (this.allFellowshipInvites.isEmpty()) {
                     y += this.field_146289_q.field_78288_b;
                     s = StatCollector.func_74838_a("lotr.gui.fellowships.invitesNone");
                     this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
                  } else {
                     int[] invitesMinMax = this.scrollPaneInvites.getMinMaxIndices(this.allFellowshipInvites, this.displayedInvites);

                     for(l = invitesMinMax[0]; l <= invitesMinMax[1]; ++l) {
                        LOTRFellowshipClient fs = (LOTRFellowshipClient)this.allFellowshipInvites.get(l);
                        this.drawFellowshipEntry(fs, x, y, i, j, true);
                        y += this.field_146289_q.field_78288_b + 5;
                     }
                  }

                  if (this.scrollPaneInvites.hasScrollBar) {
                     this.scrollPaneInvites.drawScrollBar();
                  }
               } else if (this.page == LOTRGuiFellowships.Page.ACCEPT_INVITE_RESULT) {
                  x = this.guiLeft + this.xSize / 2;
                  y = this.guiTop + 30;
                  if (this.acceptInviteResult == null) {
                     int waitingDots = IntMath.mod(this.tickCounter / 10, 3);
                     s = "";

                     for(l = 0; l < waitingDots; ++l) {
                        s = s + ".";
                     }

                     this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
                  } else if (this.acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.JOINED) {
                     s = "Joining... (you shouldn't be able to see this message)";
                     this.drawCenteredString(s, this.guiLeft + this.xSize / 2, y, 16777215);
                  } else {
                     if (this.acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.DISBANDED) {
                        s = StatCollector.func_74837_a("lotr.gui.fellowships.invited.disbanded", new Object[]{this.acceptInviteResultFellowshipName});
                     } else if (this.acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.TOO_LARGE) {
                        s = StatCollector.func_74837_a("lotr.gui.fellowships.invited.maxSize", new Object[]{this.acceptInviteResultFellowshipName, LOTRConfig.getFellowshipMaxSize(this.field_146297_k.field_71441_e)});
                     } else if (this.acceptInviteResult == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.NONEXISTENT) {
                        s = StatCollector.func_74837_a("lotr.gui.fellowships.invited.notFound", new Object[0]);
                     } else {
                        s = "If you can see this message, something has gone wrong!";
                     }

                     lines = this.field_146289_q.func_78271_c(s, this.xSize);

                     for(var32 = lines.iterator(); var32.hasNext(); y += this.field_146289_q.field_78288_b) {
                        line = (String)var32.next();
                        this.drawCenteredString(line, x, y, 16777215);
                     }
                  }
               }
            }
         }
      }

   }

   private void drawFellowshipEntry(LOTRFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite) {
      this.drawFellowshipEntry(fs, x, y, mouseX, mouseY, isInvite, this.xSize);
   }

   public void drawFellowshipEntry(LOTRFellowshipClient fs, int x, int y, int mouseX, int mouseY, boolean isInvite, int selectWidth) {
      int selectX0 = x - 2;
      int selectX1 = x + selectWidth + 2;
      int selectY0 = y - 2;
      int selectY1 = y + this.field_146289_q.field_78288_b + 2;
      if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
         func_73734_a(selectX0, selectY0, selectX1, selectY1, 1442840575);
         this.mouseOverFellowship = fs;
      }

      boolean isMouseOver = this.mouseOverFellowship == fs;
      this.drawFellowshipIcon(fs, x, y, 0.5F);
      String fsName = fs.getName();
      int maxLength = 110;
      if (this.field_146289_q.func_78256_a(fsName) > maxLength) {
         String ellipsis;
         for(ellipsis = "..."; this.field_146289_q.func_78256_a(fsName + ellipsis) > maxLength; fsName = fsName.substring(0, fsName.length() - 1)) {
         }

         fsName = fsName + ellipsis;
      }

      GameProfile owner = fs.getOwnerProfile();
      boolean ownerOnline = isPlayerOnline(owner);
      this.field_146289_q.func_78276_b(fsName, x + 15, y, 16777215);
      this.field_146289_q.func_78276_b(owner.getName(), x + 130, y, ownerOnline ? 16777215 : (isMouseOver ? 12303291 : 7829367));
      if (isInvite) {
         int iconWidth = 8;
         int iconAcceptX = x + this.xSize - 18;
         int iconRejectX = x + this.xSize - 8;
         boolean accept = false;
         boolean reject = false;
         if (isMouseOver) {
            this.mouseOverInviteAccept = mouseX >= iconAcceptX && mouseX <= iconAcceptX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
            accept = this.mouseOverInviteAccept;
            this.mouseOverInviteReject = mouseX >= iconRejectX && mouseX <= iconRejectX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
            reject = this.mouseOverInviteReject;
         }

         this.field_146297_k.func_110434_K().func_110577_a(iconsTextures);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(iconAcceptX, y, 16, 16 + (accept ? 0 : iconWidth), iconWidth, iconWidth);
         this.func_73729_b(iconRejectX, y, 8, 16 + (reject ? 0 : iconWidth), iconWidth, iconWidth);
      } else {
         String memberCount = String.valueOf(fs.getPlayerCount());
         String onlineMemberCount = this.countOnlineMembers(fs) + " | ";
         this.field_146289_q.func_78276_b(memberCount, x + this.xSize - this.field_146289_q.func_78256_a(memberCount), y, isMouseOver ? 12303291 : 7829367);
         this.field_146289_q.func_78276_b(onlineMemberCount, x + this.xSize - this.field_146289_q.func_78256_a(memberCount) - this.field_146289_q.func_78256_a(onlineMemberCount), y, 16777215);
      }

   }

   private void drawPlayerEntry(GameProfile player, int x, int y, int titleOffset, int mouseX, int mouseY) {
      UUID playerUuid = player.getId();
      String playerUsername = player.getName();
      int selectX0 = x - 2;
      int selectX1 = x + this.xSize + 2;
      int selectY0 = y - 2;
      int selectY1 = y + this.field_146289_q.field_78288_b + 2;
      if (mouseX >= selectX0 && mouseX <= selectX1 && mouseY >= selectY0 && mouseY <= selectY1) {
         func_73734_a(selectX0, selectY0, selectX1, selectY1, 1442840575);
         this.mouseOverPlayer = playerUuid;
      }

      boolean isMouseOver = playerUuid.equals(this.mouseOverPlayer);
      String titleName = null;
      LOTRTitle.PlayerTitle title = this.viewingFellowship.getTitleFor(playerUuid);
      if (title != null) {
         titleName = title.getFormattedTitle(this.field_146297_k.field_71439_g);
      }

      if (titleName != null) {
         this.field_146289_q.func_78276_b(titleName, x, y, 16777215);
      }

      boolean online = isPlayerOnline(player);
      this.field_146289_q.func_78276_b(playerUsername, x + titleOffset, y, online ? 16777215 : (isMouseOver ? 12303291 : 7829367));
      boolean isOwner = this.viewingFellowship.getOwnerUuid().equals(playerUuid);
      boolean isAdmin = this.viewingFellowship.isAdmin(playerUuid);
      if (isOwner) {
         this.field_146297_k.func_110434_K().func_110577_a(iconsTextures);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(x + titleOffset + this.field_146289_q.func_78256_a(playerUsername + " "), y, 0, 0, 8, 8);
      } else if (isAdmin) {
         this.field_146297_k.func_110434_K().func_110577_a(iconsTextures);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(x + titleOffset + this.field_146289_q.func_78256_a(playerUsername + " "), y, 8, 0, 8, 8);
      }

      boolean owned = this.viewingFellowship.isOwned();
      boolean adminned = this.viewingFellowship.isAdminned();
      if (!isOwner && (owned || adminned)) {
         int iconWidth = 8;
         int iconRemoveX = x + this.xSize - 28;
         int iconOpDeopX = x + this.xSize - 18;
         int iconTransferX = x + this.xSize - 8;
         if (adminned) {
            iconRemoveX = x + this.xSize - 8;
         }

         boolean remove = false;
         boolean opDeop = false;
         boolean transfer = false;
         if (isMouseOver) {
            this.mouseOverPlayerRemove = mouseX >= iconRemoveX && mouseX <= iconRemoveX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
            remove = this.mouseOverPlayerRemove;
            if (owned) {
               if (isAdmin) {
                  this.mouseOverPlayerDeop = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
                  opDeop = this.mouseOverPlayerDeop;
               } else {
                  this.mouseOverPlayerOp = mouseX >= iconOpDeopX && mouseX <= iconOpDeopX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
                  opDeop = this.mouseOverPlayerOp;
               }

               this.mouseOverPlayerTransfer = mouseX >= iconTransferX && mouseX <= iconTransferX + iconWidth && mouseY >= y && mouseY <= y + iconWidth;
               transfer = this.mouseOverPlayerTransfer;
            }
         }

         this.field_146297_k.func_110434_K().func_110577_a(iconsTextures);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(iconRemoveX, y, 8, 16 + (remove ? 0 : iconWidth), iconWidth, iconWidth);
         if (owned) {
            if (isAdmin) {
               this.func_73729_b(iconOpDeopX, y, 32, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
            } else {
               this.func_73729_b(iconOpDeopX, y, 24, 16 + (opDeop ? 0 : iconWidth), iconWidth, iconWidth);
            }

            this.func_73729_b(iconTransferX, y, 0, 16 + (transfer ? 0 : iconWidth), iconWidth, iconWidth);
         }
      }

   }

   private void drawFellowshipIcon(LOTRFellowshipClient fsClient, int x, int y, float scale) {
      ItemStack fsIcon = fsClient.getIcon();
      if (fsIcon != null) {
         GL11.glDisable(3042);
         GL11.glDisable(3008);
         RenderHelper.func_74520_c();
         GL11.glDisable(2896);
         GL11.glEnable(32826);
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         GL11.glScalef(scale, scale, 1.0F);
         renderItem.func_82406_b(this.field_146297_k.field_71466_p, this.field_146297_k.func_110434_K(), fsIcon, Math.round((float)x / scale), Math.round((float)y / scale));
         GL11.glPopMatrix();
         GL11.glDisable(2896);
      }

   }

   private void renderIconTooltip(int x, int y, String s) {
      float z = this.field_73735_i;
      int stringWidth = 200;
      List desc = this.field_146289_q.func_78271_c(s, stringWidth);
      this.func_146283_a(desc, x, y);
      GL11.glDisable(2896);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73735_i = z;
   }

   public static boolean isPlayerOnline(GameProfile player) {
      EntityClientPlayerMP mcPlayer = Minecraft.func_71410_x().field_71439_g;
      List list = mcPlayer.field_71174_a.field_147303_b;
      Iterator var3 = list.iterator();

      GuiPlayerInfo info;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         Object obj = var3.next();
         info = (GuiPlayerInfo)obj;
      } while(!info.field_78831_a.equalsIgnoreCase(player.getName()));

      return true;
   }

   private int countOnlineMembers(LOTRFellowshipClient fs) {
      int i = 0;
      List allPlayers = fs.getAllPlayerProfiles();
      Iterator var4 = allPlayers.iterator();

      while(var4.hasNext()) {
         GameProfile player = (GameProfile)var4.next();
         if (isPlayerOnline(player)) {
            ++i;
         }
      }

      return i;
   }

   private List sortFellowshipsForDisplay(List list) {
      List sorted = new ArrayList(list);
      Collections.sort(sorted, new Comparator() {
         public int compare(LOTRFellowshipClient fs1, LOTRFellowshipClient fs2) {
            int count1 = fs1.getPlayerCount();
            int count2 = fs2.getPlayerCount();
            return count1 == count2 ? fs1.getName().toLowerCase().compareTo(fs2.getName().toLowerCase()) : -Integer.valueOf(count1).compareTo(count2);
         }
      });
      return sorted;
   }

   private List sortMembersForDisplay(final LOTRFellowshipClient fs) {
      List members = new ArrayList(fs.getMemberProfiles());
      Collections.sort(members, new Comparator() {
         public int compare(GameProfile player1, GameProfile player2) {
            boolean admin1 = fs.isAdmin(player1.getId());
            boolean admin2 = fs.isAdmin(player2.getId());
            boolean online1 = LOTRGuiFellowships.isPlayerOnline(player1);
            boolean online2 = LOTRGuiFellowships.isPlayerOnline(player2);
            if (online1 == online2) {
               if (admin1 == admin2) {
                  return player1.getName().toLowerCase().compareTo(player2.getName().toLowerCase());
               }

               if (admin1 && !admin2) {
                  return -1;
               }

               if (!admin1 && admin2) {
                  return 1;
               }
            } else {
               if (online1 && !online2) {
                  return -1;
               }

               if (!online1 && online2) {
                  return 1;
               }
            }

            return 0;
         }
      });
      return members;
   }

   protected void func_73869_a(char c, int i) {
      if (this.page != LOTRGuiFellowships.Page.CREATE || !this.textFieldName.func_146201_a(c, i)) {
         if (this.page != LOTRGuiFellowships.Page.INVITE || !this.textFieldPlayer.func_146201_a(c, i)) {
            if (this.page != LOTRGuiFellowships.Page.RENAME || !this.textFieldRename.func_146201_a(c, i)) {
               if (this.page != LOTRGuiFellowships.Page.LIST) {
                  if (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
                     if (this.page != LOTRGuiFellowships.Page.INVITE && this.page != LOTRGuiFellowships.Page.DISBAND && this.page != LOTRGuiFellowships.Page.LEAVE && this.page != LOTRGuiFellowships.Page.REMOVE && this.page != LOTRGuiFellowships.Page.OP && this.page != LOTRGuiFellowships.Page.DEOP && this.page != LOTRGuiFellowships.Page.TRANSFER && this.page != LOTRGuiFellowships.Page.RENAME) {
                        if (this.page == LOTRGuiFellowships.Page.ACCEPT_INVITE_RESULT) {
                           if (this.acceptInviteResult != null) {
                              this.page = LOTRGuiFellowships.Page.INVITATIONS;
                           }
                        } else {
                           this.page = LOTRGuiFellowships.Page.LIST;
                        }
                     } else {
                        this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
                     }
                  }
               } else {
                  super.func_73869_a(c, i);
               }

            }
         }
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (this.page == LOTRGuiFellowships.Page.LIST && this.mouseOverFellowship != null) {
         this.buttonSound();
         this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
         this.viewingFellowship = this.mouseOverFellowship;
      }

      if (this.page == LOTRGuiFellowships.Page.CREATE) {
         this.textFieldName.func_146192_a(i, j, k);
      }

      if (this.page == LOTRGuiFellowships.Page.INVITE) {
         this.textFieldPlayer.func_146192_a(i, j, k);
      }

      if (this.page == LOTRGuiFellowships.Page.RENAME) {
         this.textFieldRename.func_146192_a(i, j, k);
      }

      if (this.page == LOTRGuiFellowships.Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerRemove) {
         this.buttonSound();
         this.page = LOTRGuiFellowships.Page.REMOVE;
         this.removingPlayer = this.mouseOverPlayer;
      }

      if (this.page == LOTRGuiFellowships.Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerOp) {
         this.buttonSound();
         this.page = LOTRGuiFellowships.Page.OP;
         this.oppingPlayer = this.mouseOverPlayer;
      }

      if (this.page == LOTRGuiFellowships.Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerDeop) {
         this.buttonSound();
         this.page = LOTRGuiFellowships.Page.DEOP;
         this.deoppingPlayer = this.mouseOverPlayer;
      }

      if (this.page == LOTRGuiFellowships.Page.FELLOWSHIP && this.mouseOverPlayer != null && this.mouseOverPlayerTransfer) {
         this.buttonSound();
         this.page = LOTRGuiFellowships.Page.TRANSFER;
         this.transferringPlayer = this.mouseOverPlayer;
      }

      if (this.page == LOTRGuiFellowships.Page.INVITATIONS && this.mouseOverFellowship != null && this.mouseOverInviteAccept) {
         this.buttonSound();
         this.acceptInvitation(this.mouseOverFellowship);
         this.mouseOverFellowship = null;
         this.page = LOTRGuiFellowships.Page.ACCEPT_INVITE_RESULT;
      }

      if (this.page == LOTRGuiFellowships.Page.INVITATIONS && this.mouseOverFellowship != null && this.mouseOverInviteReject) {
         this.buttonSound();
         this.rejectInvitation(this.mouseOverFellowship);
         this.mouseOverFellowship = null;
      }

   }

   private void acceptInvitation(LOTRFellowshipClient invite) {
      LOTRPacketFellowshipRespondInvite packet = new LOTRPacketFellowshipRespondInvite(invite, true);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }

   private void rejectInvitation(LOTRFellowshipClient invite) {
      LOTRPacketFellowshipRespondInvite packet = new LOTRPacketFellowshipRespondInvite(invite, false);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }

   private void buttonSound() {
      this.buttonBack.func_146113_a(this.field_146297_k.func_147118_V());
   }

   private void setupScrollBars(int i, int j) {
      if (this.page == LOTRGuiFellowships.Page.LIST) {
         this.displayedFellowshipsLeading = this.allFellowshipsLeading.size();
         this.displayedFellowshipsOther = this.allFellowshipsOther.size();
         this.scrollPaneLeading.hasScrollBar = false;
         this.scrollPaneOther.hasScrollBar = false;

         while(this.displayedFellowshipsLeading + this.displayedFellowshipsOther > 12) {
            if (this.displayedFellowshipsOther >= this.displayedFellowshipsLeading) {
               --this.displayedFellowshipsOther;
               this.scrollPaneOther.hasScrollBar = true;
            } else {
               --this.displayedFellowshipsLeading;
               this.scrollPaneLeading.hasScrollBar = true;
            }
         }

         this.scrollPaneLeading.paneX0 = this.guiLeft;
         this.scrollPaneLeading.scrollBarX0 = this.guiLeft + this.scrollBarX;
         this.scrollPaneLeading.paneY0 = this.guiTop + 10;
         this.scrollPaneLeading.paneY1 = this.scrollPaneLeading.paneY0 + this.field_146289_q.field_78288_b + 10 + (this.field_146289_q.field_78288_b + 5) * this.displayedFellowshipsLeading;
         this.scrollPaneLeading.mouseDragScroll(i, j);
         this.scrollPaneOther.paneX0 = this.guiLeft;
         this.scrollPaneOther.scrollBarX0 = this.guiLeft + this.scrollBarX;
         this.scrollPaneOther.paneY0 = this.scrollPaneLeading.paneY1 + 5;
         this.scrollPaneOther.paneY1 = this.scrollPaneOther.paneY0 + this.field_146289_q.field_78288_b + 10 + (this.field_146289_q.field_78288_b + 5) * this.displayedFellowshipsOther;
         this.scrollPaneOther.mouseDragScroll(i, j);
      }

      if (this.page == LOTRGuiFellowships.Page.FELLOWSHIP) {
         this.displayedMembers = this.viewingFellowship.getMemberUuids().size();
         this.scrollPaneMembers.hasScrollBar = false;
         if (this.displayedMembers > 11) {
            this.displayedMembers = 11;
            this.scrollPaneMembers.hasScrollBar = true;
         }

         this.scrollPaneMembers.paneX0 = this.guiLeft;
         this.scrollPaneMembers.scrollBarX0 = this.guiLeft + this.scrollBarX;
         this.scrollPaneMembers.paneY0 = this.guiTop + 10 + this.field_146289_q.field_78288_b + 5 + 16 + 10 + this.field_146289_q.field_78288_b + 10;
         this.scrollPaneMembers.paneY1 = this.scrollPaneMembers.paneY0 + (this.field_146289_q.field_78288_b + 5) * this.displayedMembers;
         this.scrollPaneMembers.mouseDragScroll(i, j);
      } else {
         this.scrollPaneMembers.hasScrollBar = false;
         this.scrollPaneMembers.mouseDragScroll(i, j);
      }

      if (this.page == LOTRGuiFellowships.Page.INVITATIONS) {
         this.displayedInvites = this.allFellowshipInvites.size();
         this.scrollPaneInvites.hasScrollBar = false;
         if (this.displayedInvites > 15) {
            this.displayedInvites = 15;
            this.scrollPaneInvites.hasScrollBar = true;
         }

         this.scrollPaneInvites.paneX0 = this.guiLeft;
         this.scrollPaneInvites.scrollBarX0 = this.guiLeft + this.scrollBarX;
         this.scrollPaneInvites.paneY0 = this.guiTop + 10 + this.field_146289_q.field_78288_b + 10;
         this.scrollPaneInvites.paneY1 = this.scrollPaneInvites.paneY0 + (this.field_146289_q.field_78288_b + 5) * this.displayedInvites;
         this.scrollPaneInvites.mouseDragScroll(i, j);
      }

   }

   public void func_146274_d() {
      super.func_146274_d();
      int k = Mouse.getEventDWheel();
      if (k != 0) {
         k = Integer.signum(k);
         int l;
         if (this.page == LOTRGuiFellowships.Page.LIST) {
            if (this.scrollPaneLeading.hasScrollBar && this.scrollPaneLeading.mouseOver) {
               l = this.allFellowshipsLeading.size() - this.displayedFellowshipsLeading;
               this.scrollPaneLeading.mouseWheelScroll(k, l);
            }

            if (this.scrollPaneOther.hasScrollBar && this.scrollPaneOther.mouseOver) {
               l = this.allFellowshipsOther.size() - this.displayedFellowshipsOther;
               this.scrollPaneOther.mouseWheelScroll(k, l);
            }
         }

         if (this.page == LOTRGuiFellowships.Page.FELLOWSHIP && this.scrollPaneMembers.hasScrollBar && this.scrollPaneMembers.mouseOver) {
            l = this.viewingFellowship.getMemberUuids().size() - this.displayedMembers;
            this.scrollPaneMembers.mouseWheelScroll(k, l);
         }

         if (this.page == LOTRGuiFellowships.Page.INVITATIONS && this.scrollPaneInvites.hasScrollBar && this.scrollPaneInvites.mouseOver) {
            l = this.allFellowshipInvites.size() - this.displayedInvites;
            this.scrollPaneInvites.mouseWheelScroll(k, l);
         }
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonCreate) {
            this.page = LOTRGuiFellowships.Page.CREATE;
         } else {
            String name;
            if (button == this.buttonCreateThis) {
               name = this.textFieldName.func_146179_b();
               if (this.checkValidFellowshipName(name) == null) {
                  name = StringUtils.trim(name);
                  LOTRPacketFellowshipCreate packet = new LOTRPacketFellowshipCreate(name);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
               }

               this.page = LOTRGuiFellowships.Page.LIST;
            } else if (button == this.buttonInvitePlayer) {
               this.page = LOTRGuiFellowships.Page.INVITE;
            } else if (button == this.buttonInviteThis) {
               name = this.textFieldPlayer.func_146179_b();
               if (this.checkValidPlayerName(name) == null) {
                  name = StringUtils.trim(name);
                  LOTRPacketFellowshipInvitePlayer packet = new LOTRPacketFellowshipInvitePlayer(this.viewingFellowship, name);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
               }

               this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
            } else if (button == this.buttonDisband) {
               this.page = LOTRGuiFellowships.Page.DISBAND;
            } else if (button == this.buttonDisbandThis) {
               LOTRPacketFellowshipDisband packet = new LOTRPacketFellowshipDisband(this.viewingFellowship);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
               this.page = LOTRGuiFellowships.Page.LIST;
            } else if (button == this.buttonLeave) {
               this.page = LOTRGuiFellowships.Page.LEAVE;
            } else if (button == this.buttonLeaveThis) {
               LOTRPacketFellowshipLeave packet = new LOTRPacketFellowshipLeave(this.viewingFellowship);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
               this.page = LOTRGuiFellowships.Page.LIST;
            } else if (button == this.buttonSetIcon) {
               LOTRPacketFellowshipSetIcon packet = new LOTRPacketFellowshipSetIcon(this.viewingFellowship);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else {
               LOTRPacketFellowshipDoPlayer packet;
               if (button == this.buttonRemove) {
                  packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.removingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.REMOVE);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
               } else if (button == this.buttonOp) {
                  packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.oppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.OP);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
               } else if (button == this.buttonDeop) {
                  packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.deoppingPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.DEOP);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
               } else if (button == this.buttonTransfer) {
                  packet = new LOTRPacketFellowshipDoPlayer(this.viewingFellowship, this.transferringPlayer, LOTRPacketFellowshipDoPlayer.PlayerFunction.TRANSFER);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
               } else if (button == this.buttonRename) {
                  this.page = LOTRGuiFellowships.Page.RENAME;
               } else if (button == this.buttonRenameThis) {
                  name = this.textFieldRename.func_146179_b();
                  if (this.checkValidFellowshipName(name) == null) {
                     name = StringUtils.trim(name);
                     LOTRPacketFellowshipRename packet = new LOTRPacketFellowshipRename(this.viewingFellowship, name);
                     LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  }

                  this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
               } else if (button == this.buttonBack) {
                  this.func_73869_a('E', 1);
               } else if (button == this.buttonInvites) {
                  this.page = LOTRGuiFellowships.Page.INVITATIONS;
               } else {
                  LOTRPacketFellowshipToggle packet;
                  if (button == this.buttonPVP) {
                     packet = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.PVP);
                     LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  } else if (button == this.buttonHiredFF) {
                     packet = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.HIRED_FF);
                     LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  } else if (button == this.buttonMapShow) {
                     packet = new LOTRPacketFellowshipToggle(this.viewingFellowship, LOTRPacketFellowshipToggle.ToggleFunction.MAP_SHOW);
                     LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  } else {
                     super.func_146284_a(button);
                  }
               }
            }
         }
      }

   }

   private String checkValidFellowshipName(String name) {
      if (!StringUtils.isWhitespace(name)) {
         return LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).anyMatchingFellowshipNames(name, true) ? StatCollector.func_74838_a("lotr.gui.fellowships.nameExists") : null;
      } else {
         return "";
      }
   }

   private String checkValidPlayerName(String name) {
      if (!StringUtils.isWhitespace(name)) {
         return this.viewingFellowship.containsPlayerUsername(name) ? StatCollector.func_74837_a("lotr.gui.fellowships.playerExists", new Object[]{name}) : null;
      } else {
         return "";
      }
   }

   public LOTRFellowshipClient getMouseOverFellowship() {
      return this.mouseOverFellowship;
   }

   public void clearMouseOverFellowship() {
      this.mouseOverFellowship = null;
   }

   public void displayAcceptInvitationResult(UUID fellowshipID, String name, LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult result) {
      if (this.page == LOTRGuiFellowships.Page.ACCEPT_INVITE_RESULT) {
         if (result == LOTRPacketFellowshipAcceptInviteResult.AcceptInviteResult.JOINED) {
            this.page = LOTRGuiFellowships.Page.FELLOWSHIP;
            this.viewingFellowship = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getClientFellowshipByID(fellowshipID);
         } else {
            this.acceptInviteResult = result;
            this.acceptInviteResultFellowshipName = name;
         }
      }

   }

   public static enum Page {
      LIST,
      CREATE,
      FELLOWSHIP,
      INVITE,
      DISBAND,
      LEAVE,
      REMOVE,
      OP,
      DEOP,
      TRANSFER,
      RENAME,
      INVITATIONS,
      ACCEPT_INVITE_RESULT;
   }
}
