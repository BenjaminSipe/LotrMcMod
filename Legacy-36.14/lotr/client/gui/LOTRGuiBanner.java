package lotr.client.gui;

import com.google.common.base.Function;
import com.mojang.authlib.GameProfile;
import java.util.Arrays;
import java.util.UUID;
import lotr.common.LOTRBannerProtection;
import lotr.common.entity.item.LOTRBannerWhitelistEntry;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fellowship.LOTRFellowshipProfile;
import lotr.common.network.LOTRPacketBannerRequestInvalidName;
import lotr.common.network.LOTRPacketEditBanner;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiBanner extends LOTRGuiScreenBase {
   public static ResourceLocation bannerTexture = new ResourceLocation("lotr:gui/banner_edit.png");
   public final LOTREntityBanner theBanner;
   public int xSize = 200;
   public int ySize = 250;
   private int guiLeft;
   private int guiTop;
   private GuiButton buttonMode;
   private LOTRGuiButtonBanner buttonSelfProtection;
   private GuiButton buttonAddSlot;
   private GuiButton buttonRemoveSlot;
   private LOTRGuiButtonBanner buttonDefaultPermissions;
   private GuiTextField alignmentField;
   private static final int displayedPlayers = 6;
   private GuiTextField[] allowedPlayers = new GuiTextField[0];
   private boolean[] invalidUsernames = new boolean[0];
   private boolean[] validatedUsernames = new boolean[0];
   private boolean[] checkUsernames = new boolean[0];
   private static final int textboxInvalid = 16711680;
   private static final int textboxValid = 65280;
   private static final int textboxDefault = 16777215;
   private float currentScroll = 0.0F;
   private boolean isScrolling = false;
   private boolean wasMouseDown;
   private int scrollBarWidth = 12;
   private int scrollBarHeight = 132;
   private int scrollBarX = 181;
   private int scrollBarY = 68;
   private int scrollBarBorder = 1;
   private int scrollWidgetWidth = 10;
   private int scrollWidgetHeight = 17;
   private int permIconX = 3;
   private int permIconY = 0;
   private int permIconWidth = 10;
   private int permissionsMouseoverIndex = -1;
   private int permissionsMouseoverY = -1;
   private int permWindowBorder = 4;
   private int permWindowWidth = 150;
   private int permWindowHeight = 70;
   private int permissionsOpenIndex = -1;
   private int permissionsOpenY = -1;
   private LOTRBannerProtection.Permission mouseOverPermission = null;
   private boolean defaultPermissionsOpen = false;

   public LOTRGuiBanner(LOTREntityBanner banner) {
      this.theBanner = banner;
   }

   public void func_73866_w_() {
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      this.field_146292_n.add(this.buttonMode = new GuiButton(0, this.guiLeft + this.xSize / 2 - 80, this.guiTop + 20, 160, 20, ""));
      this.field_146292_n.add(this.buttonSelfProtection = new LOTRGuiButtonBanner(1, this.guiLeft + this.xSize / 2 - 24, this.guiTop + 224, 212, 100));
      this.field_146292_n.add(this.buttonAddSlot = new LOTRGuiButtonBannerWhitelistSlots(0, this.guiLeft + 179, this.guiTop + 202));
      this.field_146292_n.add(this.buttonRemoveSlot = new LOTRGuiButtonBannerWhitelistSlots(1, this.guiLeft + 187, this.guiTop + 202));
      this.field_146292_n.add(this.buttonDefaultPermissions = new LOTRGuiButtonBanner(2, this.guiLeft + this.xSize / 2 + 8, this.guiTop + 224, 200, 134));
      this.buttonDefaultPermissions.activated = true;
      this.alignmentField = new GuiTextField(this.field_146289_q, this.guiLeft + this.xSize / 2 - 70, this.guiTop + 100, 130, 18);
      this.alignmentField.func_146180_a(String.valueOf(this.theBanner.getAlignmentProtection()));
      this.alignmentField.func_146184_c(false);
      this.refreshWhitelist();

      for(int i = 0; i < this.allowedPlayers.length; ++i) {
         GuiTextField textBox = this.allowedPlayers[i];
         textBox.func_146193_g(16777215);
         GameProfile profile = this.theBanner.getWhitelistedPlayer(i);
         if (profile != null) {
            String name = profile.getName();
            if (!StringUtils.isBlank(name)) {
               textBox.func_146180_a(name);
               textBox.func_146193_g(65280);
               this.validatedUsernames[i] = true;
            }
         }

         this.allowedPlayers[i] = textBox;
      }

      this.allowedPlayers[0].func_146184_c(false);
      Arrays.fill(this.checkUsernames, false);
   }

   private void updateWhitelistedPlayer(int index, String username) {
      LOTRBannerWhitelistEntry prevEntry = this.theBanner.getWhitelistEntry(index);
      int prevPerms = -1;
      if (prevEntry != null) {
         prevPerms = prevEntry.encodePermBitFlags();
      }

      if (StringUtils.isBlank(username)) {
         this.theBanner.whitelistPlayer(index, (GameProfile)null);
      } else {
         if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
            String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
            if (StringUtils.isBlank(fsName)) {
               this.theBanner.whitelistPlayer(index, (GameProfile)null);
            } else {
               this.theBanner.whitelistPlayer(index, new LOTRFellowshipProfile(this.theBanner, (UUID)null, fsName));
            }
         } else {
            this.theBanner.whitelistPlayer(index, new GameProfile((UUID)null, username));
         }

         if (prevPerms >= 0) {
            this.theBanner.getWhitelistEntry(index).decodePermBitFlags(prevPerms);
         }
      }

   }

   private void refreshWhitelist() {
      int length = this.theBanner.getWhitelistLength();
      GuiTextField[] allowedPlayers_new = new GuiTextField[length];
      boolean[] invalidUsernames_new = new boolean[length];
      boolean[] validatedUsernames_new = new boolean[length];
      boolean[] checkUsernames_new = new boolean[length];

      for(int i = 0; i < length; ++i) {
         if (i < this.allowedPlayers.length) {
            allowedPlayers_new[i] = this.allowedPlayers[i];
         } else {
            allowedPlayers_new[i] = new GuiTextField(this.field_146289_q, 0, 0, 130, 18);
         }

         if (i < this.invalidUsernames.length) {
            invalidUsernames_new[i] = this.invalidUsernames[i];
         }

         if (i < this.validatedUsernames.length) {
            validatedUsernames_new[i] = this.validatedUsernames[i];
         }

         if (i < this.checkUsernames.length) {
            checkUsernames_new[i] = this.checkUsernames[i];
         }
      }

      this.allowedPlayers = allowedPlayers_new;
      this.invalidUsernames = invalidUsernames_new;
      this.validatedUsernames = validatedUsernames_new;
      this.checkUsernames = checkUsernames_new;
   }

   public void func_73863_a(int i, int j, float f) {
      this.permissionsMouseoverIndex = -1;
      this.permissionsMouseoverY = -1;
      this.mouseOverPermission = null;
      this.setupScrollBar(i, j);
      this.alignmentField.func_146189_e(false);
      this.alignmentField.func_146184_c(false);

      for(int l = 0; l < this.allowedPlayers.length; ++l) {
         GuiTextField textBox = this.allowedPlayers[l];
         textBox.func_146189_e(false);
         textBox.func_146184_c(false);
      }

      this.func_146276_q_();
      this.field_146297_k.func_110434_K().func_110577_a(bannerTexture);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String title = StatCollector.func_74838_a("lotr.gui.bannerEdit.title");
      this.field_146289_q.func_78276_b(title, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(title) / 2, this.guiTop + 6, 4210752);
      int windowY;
      String s;
      if (this.theBanner.isPlayerSpecificProtection()) {
         this.buttonMode.field_146126_j = StatCollector.func_74838_a("lotr.gui.bannerEdit.protectionMode.playerSpecific");
         s = StatCollector.func_74838_a("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.1");
         this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 46, 4210752);
         s = StatCollector.func_74838_a("lotr.gui.bannerEdit.protectionMode.playerSpecific.desc.2");
         this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 46 + this.field_146289_q.field_78288_b, 4210752);
         s = LOTRFellowshipProfile.getFellowshipCodeHint();
         this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 206, 4210752);
         windowY = 0 + Math.round(this.currentScroll * (float)(this.allowedPlayers.length - 6));
         int end = windowY + 6 - 1;
         windowY = Math.max(windowY, 0);
         end = Math.min(end, this.allowedPlayers.length - 1);

         int scroll;
         for(scroll = windowY; scroll <= end; ++scroll) {
            int displayIndex = scroll - windowY;
            GuiTextField textBox = this.allowedPlayers[scroll];
            textBox.func_146189_e(true);
            textBox.func_146184_c(scroll != 0);
            textBox.field_146209_f = this.guiLeft + this.xSize / 2 - 70;
            textBox.field_146210_g = this.guiTop + 70 + displayIndex * (textBox.field_146219_i + 4);
            textBox.func_146194_f();
            String number = scroll + 1 + ".";
            this.field_146289_q.func_78276_b(number, this.guiLeft + 24 - this.field_146289_q.func_78256_a(number), textBox.field_146210_g + 6, 4210752);
            if (scroll > 0 && this.validatedUsernames[scroll]) {
               this.field_146297_k.func_110434_K().func_110577_a(bannerTexture);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               int permX = textBox.field_146209_f + textBox.field_146218_h + this.permIconX;
               int permY = textBox.field_146210_g + this.permIconY;
               boolean mouseOver = i >= permX && i < permX + this.permIconWidth && j >= permY && j < permY + this.permIconWidth;
               this.func_73729_b(permX, permY, 200 + (mouseOver ? this.permIconWidth : 0), 150, this.permIconWidth, this.permIconWidth);
               if (mouseOver) {
                  this.permissionsMouseoverIndex = scroll;
                  this.permissionsMouseoverY = textBox.field_146210_g;
               }
            }
         }

         if (this.hasScrollBar()) {
            this.field_146297_k.func_110434_K().func_110577_a(bannerTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_73729_b(this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 200, 0, this.scrollBarWidth, this.scrollBarHeight);
            if (this.canScroll()) {
               scroll = (int)(this.currentScroll * (float)(this.scrollBarHeight - this.scrollBarBorder * 2 - this.scrollWidgetHeight));
               this.func_73729_b(this.guiLeft + this.scrollBarX + this.scrollBarBorder, this.guiTop + this.scrollBarY + this.scrollBarBorder + scroll, 212, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
            }
         }
      } else {
         this.permissionsOpenIndex = this.permissionsOpenY = -1;
         this.buttonMode.field_146126_j = StatCollector.func_74838_a("lotr.gui.bannerEdit.protectionMode.faction");
         s = StatCollector.func_74837_a("lotr.gui.bannerEdit.protectionMode.faction.desc.1", new Object[0]);
         this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 46, 4210752);
         s = StatCollector.func_74837_a("lotr.gui.bannerEdit.protectionMode.faction.desc.2", new Object[]{this.theBanner.getAlignmentProtection(), this.theBanner.getBannerType().faction.factionName()});
         this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 46 + this.field_146289_q.field_78288_b, 4210752);
         s = StatCollector.func_74838_a("lotr.gui.bannerEdit.protectionMode.faction.desc.3");
         this.field_146289_q.func_78276_b(s, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(s) / 2, this.guiTop + 46 + this.field_146289_q.field_78288_b * 2, 4210752);
         s = StatCollector.func_74838_a("lotr.gui.bannerEdit.protectionMode.faction.alignment");
         this.field_146289_q.func_78276_b(s, this.alignmentField.field_146209_f, this.alignmentField.field_146210_g - this.field_146289_q.field_78288_b - 3, 4210752);
         this.alignmentField.func_146189_e(true);
         this.alignmentField.func_146184_c(true);
         this.alignmentField.func_146194_f();
      }

      int windowX;
      String boxTitle;
      if (this.permissionsOpenIndex >= 0) {
         windowX = this.guiLeft + this.xSize + this.permWindowBorder;
         windowY = this.permissionsOpenY;
         boxTitle = this.allowedPlayers[this.permissionsOpenIndex].func_146179_b();
         boolean isFellowship = LOTRFellowshipProfile.hasFellowshipCode(boxTitle);
         if (isFellowship) {
            boxTitle = LOTRFellowshipProfile.stripFellowshipCode(boxTitle);
         }

         String boxTitle = StatCollector.func_74838_a(isFellowship ? "lotr.gui.bannerEdit.perms.fellowship" : "lotr.gui.bannerEdit.perms.player");
         String boxSubtitle = StatCollector.func_74837_a("lotr.gui.bannerEdit.perms.name", new Object[]{boxTitle});
         Function getEnabled = new Function() {
            public Boolean apply(LOTRBannerProtection.Permission p) {
               return LOTRGuiBanner.this.theBanner.getWhitelistEntry(LOTRGuiBanner.this.permissionsOpenIndex).isPermissionEnabled(p);
            }
         };
         this.drawPermissionsWindow(i, j, windowX, windowY, boxTitle, boxSubtitle, getEnabled, true);
      }

      String tooltip;
      if (this.defaultPermissionsOpen) {
         windowX = this.guiLeft + this.xSize + this.permWindowBorder;
         windowY = this.guiTop + this.ySize - this.permWindowHeight;
         boxTitle = StatCollector.func_74838_a("lotr.gui.bannerEdit.perms.default");
         tooltip = StatCollector.func_74837_a("lotr.gui.bannerEdit.perms.allPlayers", new Object[0]);
         Function getEnabled = new Function() {
            public Boolean apply(LOTRBannerProtection.Permission p) {
               return LOTRGuiBanner.this.theBanner.hasDefaultPermission(p);
            }
         };
         this.drawPermissionsWindow(i, j, windowX, windowY, boxTitle, tooltip, getEnabled, false);
      }

      super.func_73863_a(i, j, f);
      String username;
      float z;
      if (this.buttonSelfProtection.func_146115_a()) {
         z = this.field_73735_i;
         username = StatCollector.func_74838_a("lotr.gui.bannerEdit.selfProtection." + (this.buttonSelfProtection.activated ? "on" : "off"));
         this.func_146279_a(username, i, j);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = z;
      }

      if (this.buttonDefaultPermissions.func_146115_a()) {
         z = this.field_73735_i;
         username = StatCollector.func_74838_a("lotr.gui.bannerEdit.perms.default");
         this.func_146279_a(username, i, j);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = z;
      }

      if (this.permissionsMouseoverIndex >= 0) {
         z = this.field_73735_i;
         username = this.allowedPlayers[this.permissionsMouseoverIndex].func_146179_b();
         boolean isFellowship = LOTRFellowshipProfile.hasFellowshipCode(username);
         tooltip = StatCollector.func_74838_a(isFellowship ? "lotr.gui.bannerEdit.perms.fellowship" : "lotr.gui.bannerEdit.perms.player");
         this.func_146279_a(tooltip, i, j);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = z;
      }

   }

   private void drawPermissionsWindow(int i, int j, int windowX, int windowY, String boxTitle, String boxSubtitle, Function getEnabled, boolean includeFull) {
      func_73734_a(windowX, windowY, windowX + this.permWindowWidth, windowY + this.permWindowHeight, -1442840576);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146289_q.func_78276_b(boxTitle, windowX + 4, windowY + 4, 16777215);
      this.field_146289_q.func_78276_b(boxSubtitle, windowX + 4, windowY + 14, 11184810);
      this.field_146297_k.func_110434_K().func_110577_a(bannerTexture);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int x = windowX + 4;
      int y = windowY + 32;
      this.mouseOverPermission = null;
      LOTRBannerProtection.Permission[] var11 = LOTRBannerProtection.Permission.values();
      int var12 = var11.length;

      for(int var13 = 0; var13 < var12; ++var13) {
         LOTRBannerProtection.Permission p = var11[var13];
         if (includeFull || p != LOTRBannerProtection.Permission.FULL) {
            if (i >= x && i < x + 10 && j >= y && j < y + 10) {
               this.mouseOverPermission = p;
            }

            boolean enabled = (Boolean)getEnabled.apply(p);
            this.func_73729_b(x, y, 200 + (enabled ? 0 : 20) + (this.mouseOverPermission == p ? 10 : 0), 160 + p.ordinal() * 10, 10, 10);
            x += 14;
            if (p == LOTRBannerProtection.Permission.FULL) {
               x += 4;
            }
         }
      }

      if (this.mouseOverPermission != null) {
         String permName = StatCollector.func_74838_a("lotr.gui.bannerEdit.perm." + this.mouseOverPermission.codeName);
         this.field_146289_q.func_78279_b(permName, windowX + 4, windowY + 47, this.permWindowWidth - this.permWindowBorder * 2, 16777215);
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      this.buttonSelfProtection.activated = this.theBanner.isSelfProtection();
      this.buttonAddSlot.field_146125_m = this.buttonRemoveSlot.field_146125_m = this.theBanner.isPlayerSpecificProtection();
      this.buttonAddSlot.field_146124_l = this.theBanner.getWhitelistLength() < LOTREntityBanner.WHITELIST_MAX;
      this.buttonRemoveSlot.field_146124_l = this.theBanner.getWhitelistLength() > LOTREntityBanner.WHITELIST_MIN;
      this.alignmentField.func_146178_a();
      this.alignmentField.func_146189_e(!this.theBanner.isPlayerSpecificProtection());
      this.alignmentField.func_146184_c(this.alignmentField.func_146176_q());
      if (this.alignmentField.func_146176_q() && !this.alignmentField.func_146206_l()) {
         float prevAlignment = this.theBanner.getAlignmentProtection();
         float alignment = LOTRAlignmentValues.parseDisplayedAlign(this.alignmentField.func_146179_b());
         alignment = MathHelper.func_76131_a(alignment, LOTREntityBanner.ALIGNMENT_PROTECTION_MIN, LOTREntityBanner.ALIGNMENT_PROTECTION_MAX);
         this.theBanner.setAlignmentProtection(alignment);
         this.alignmentField.func_146180_a(LOTRAlignmentValues.formatAlignForDisplay(alignment));
         if (alignment != prevAlignment) {
            this.sendBannerData(false);
         }
      }

      for(int l = 0; l < this.allowedPlayers.length; ++l) {
         GuiTextField textBox = this.allowedPlayers[l];
         textBox.func_146178_a();
      }

   }

   private void setupScrollBar(int i, int j) {
      boolean isMouseDown = Mouse.isButtonDown(0);
      int i1 = this.guiLeft + this.scrollBarX;
      int j1 = this.guiTop + this.scrollBarY;
      int i2 = i1 + this.scrollBarWidth;
      int j2 = j1 + this.scrollBarHeight;
      if (!this.wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
         this.isScrolling = this.canScroll();
      }

      if (!isMouseDown) {
         this.isScrolling = false;
      }

      this.wasMouseDown = isMouseDown;
      if (this.isScrolling) {
         this.currentScroll = ((float)(j - j1) - (float)this.scrollWidgetHeight / 2.0F) / ((float)(j2 - j1) - (float)this.scrollWidgetHeight);
         if (this.currentScroll < 0.0F) {
            this.currentScroll = 0.0F;
         }

         if (this.currentScroll > 1.0F) {
            this.currentScroll = 1.0F;
         }
      }

   }

   private boolean hasScrollBar() {
      return this.theBanner.isPlayerSpecificProtection();
   }

   private boolean canScroll() {
      return true;
   }

   protected void func_73869_a(char c, int i) {
      if (!this.alignmentField.func_146176_q() || !this.alignmentField.func_146201_a(c, i)) {
         for(int l = 1; l < this.allowedPlayers.length; ++l) {
            GuiTextField textBox = this.allowedPlayers[l];
            if (textBox.func_146176_q() && textBox.func_146201_a(c, i)) {
               this.validatedUsernames[l] = false;
               this.checkUsernames[l] = true;
               textBox.func_146193_g(16777215);
               this.updateWhitelistedPlayer(l, (String)null);
               return;
            }
         }

         if (this.permissionsOpenIndex >= 0 && (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i())) {
            this.permissionsOpenIndex = this.permissionsOpenY = -1;
         } else if (!this.defaultPermissionsOpen || i != 1 && i != this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
            super.func_73869_a(c, i);
         } else {
            this.defaultPermissionsOpen = false;
         }
      }
   }

   protected void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (this.alignmentField.func_146176_q()) {
         this.alignmentField.func_146192_a(i, j, k);
      }

      int dx;
      for(dx = 1; dx < this.allowedPlayers.length; ++dx) {
         GuiTextField textBox = this.allowedPlayers[dx];
         if (textBox.func_146176_q()) {
            textBox.func_146192_a(i, j, k);
            if (!textBox.func_146206_l() && this.checkUsernames[dx]) {
               this.checkUsernameValid(dx);
               this.checkUsernames[dx] = false;
            }

            if (textBox.func_146206_l() && this.invalidUsernames[dx]) {
               this.invalidUsernames[dx] = false;
               textBox.func_146193_g(16777215);
               textBox.func_146180_a("");
            }
         }
      }

      if (this.permissionsMouseoverIndex >= 0) {
         this.permissionsOpenIndex = this.permissionsMouseoverIndex;
         this.permissionsOpenY = this.permissionsMouseoverY;
         this.permissionsMouseoverIndex = -1;
         this.permissionsMouseoverY = -1;
         this.defaultPermissionsOpen = false;
         this.buttonSound();
      } else {
         int dy;
         if (this.permissionsOpenIndex >= 0) {
            dx = i - (this.guiLeft + this.xSize + this.permWindowBorder);
            dy = j - this.permissionsOpenY;
            if (dx < 0 || dx >= this.permWindowWidth || dy < 0 || dy >= this.permWindowHeight) {
               this.permissionsOpenIndex = this.permissionsOpenY = -1;
               return;
            }

            if (this.mouseOverPermission != null) {
               LOTRBannerWhitelistEntry entry = this.theBanner.getWhitelistEntry(this.permissionsOpenIndex);
               if (this.mouseOverPermission == LOTRBannerProtection.Permission.FULL) {
                  if (entry.isPermissionEnabled(this.mouseOverPermission)) {
                     entry.clearPermissions();
                  } else {
                     entry.clearPermissions();
                     entry.addPermission(this.mouseOverPermission);
                  }
               } else if (entry.isPermissionEnabled(this.mouseOverPermission)) {
                  entry.removePermission(this.mouseOverPermission);
               } else {
                  if (entry.isPermissionEnabled(LOTRBannerProtection.Permission.FULL)) {
                     entry.removePermission(LOTRBannerProtection.Permission.FULL);
                  }

                  entry.addPermission(this.mouseOverPermission);
               }

               this.buttonSound();
               return;
            }
         }

         if (this.defaultPermissionsOpen) {
            dx = i - (this.guiLeft + this.xSize + this.permWindowBorder);
            dy = j - (this.guiTop + this.ySize - this.permWindowHeight);
            if ((dx < 0 || dx >= this.permWindowWidth || dy < 0 || dy >= this.permWindowHeight) && !this.buttonDefaultPermissions.func_146116_c(this.field_146297_k, i, j)) {
               this.defaultPermissionsOpen = false;
               return;
            }

            if (this.mouseOverPermission != null) {
               if (this.theBanner.hasDefaultPermission(this.mouseOverPermission)) {
                  this.theBanner.removeDefaultPermission(this.mouseOverPermission);
               } else {
                  this.theBanner.addDefaultPermission(this.mouseOverPermission);
               }

               this.sendBannerData(false);
               this.buttonSound();
               return;
            }
         }

      }
   }

   private void buttonSound() {
      this.buttonMode.func_146113_a(this.field_146297_k.func_147118_V());
   }

   private void checkUsernameValid(int index) {
      GuiTextField textBox = this.allowedPlayers[index];
      String username = textBox.func_146179_b();
      if (!StringUtils.isBlank(username) && !this.invalidUsernames[index]) {
         LOTRPacketBannerRequestInvalidName packet = new LOTRPacketBannerRequestInvalidName(this.theBanner, index, username);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   public void validateUsername(int index, String prevText, boolean valid) {
      GuiTextField textBox = this.allowedPlayers[index];
      String username = textBox.func_146179_b();
      if (username.equals(prevText)) {
         if (valid) {
            this.validatedUsernames[index] = true;
            this.invalidUsernames[index] = false;
            textBox.func_146193_g(65280);
            this.updateWhitelistedPlayer(index, username);
         } else {
            this.invalidUsernames[index] = true;
            this.validatedUsernames[index] = false;
            textBox.func_146193_g(16711680);
            textBox.func_146180_a(StatCollector.func_74838_a("lotr.gui.bannerEdit.invalidUsername"));
            this.updateWhitelistedPlayer(index, (String)null);
         }
      }

   }

   public void func_146274_d() {
      super.func_146274_d();
      int i = Mouse.getEventDWheel();
      if (i != 0 && this.canScroll()) {
         int j = this.allowedPlayers.length - 6;
         if (i > 0) {
            i = 1;
         }

         if (i < 0) {
            i = -1;
         }

         this.currentScroll = (float)((double)this.currentScroll - (double)i / (double)j);
         if (this.currentScroll < 0.0F) {
            this.currentScroll = 0.0F;
         }

         if (this.currentScroll > 1.0F) {
            this.currentScroll = 1.0F;
         }
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonMode) {
            this.theBanner.setPlayerSpecificProtection(!this.theBanner.isPlayerSpecificProtection());
         }

         if (button == this.buttonSelfProtection) {
            this.theBanner.setSelfProtection(!this.theBanner.isSelfProtection());
         }

         if (button == this.buttonAddSlot) {
            this.theBanner.resizeWhitelist(this.theBanner.getWhitelistLength() + 1);
            this.refreshWhitelist();
         }

         if (button == this.buttonRemoveSlot) {
            this.theBanner.resizeWhitelist(this.theBanner.getWhitelistLength() - 1);
            this.refreshWhitelist();
         }

         if (button == this.buttonDefaultPermissions) {
            this.defaultPermissionsOpen = true;
         }
      }

   }

   public void func_146281_b() {
      super.func_146281_b();
      this.sendBannerData(true);
   }

   private void sendBannerData(boolean sendWhitelist) {
      LOTRPacketEditBanner packet = new LOTRPacketEditBanner(this.theBanner);
      packet.playerSpecificProtection = this.theBanner.isPlayerSpecificProtection();
      packet.selfProtection = this.theBanner.isSelfProtection();
      packet.alignmentProtection = this.theBanner.getAlignmentProtection();
      packet.whitelistLength = this.theBanner.getWhitelistLength();
      if (sendWhitelist) {
         String[] whitelistSlots = new String[this.allowedPlayers.length];
         int[] whitelistPerms = new int[this.allowedPlayers.length];

         for(int index = 1; index < this.allowedPlayers.length; ++index) {
            String text = this.allowedPlayers[index].func_146179_b();
            this.updateWhitelistedPlayer(index, text);
            LOTRBannerWhitelistEntry entry = this.theBanner.getWhitelistEntry(index);
            if (entry == null) {
               whitelistSlots[index] = null;
            } else {
               GameProfile profile = entry.profile;
               if (profile == null) {
                  whitelistSlots[index] = null;
               } else {
                  String username = profile.getName();
                  if (StringUtils.isBlank(username)) {
                     whitelistSlots[index] = null;
                  } else {
                     whitelistSlots[index] = username;
                     whitelistPerms[index] = entry.encodePermBitFlags();
                  }
               }
            }
         }

         packet.whitelistSlots = whitelistSlots;
         packet.whitelistPerms = whitelistPerms;
      }

      packet.defaultPerms = this.theBanner.getDefaultPermBitFlags();
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
   }
}
