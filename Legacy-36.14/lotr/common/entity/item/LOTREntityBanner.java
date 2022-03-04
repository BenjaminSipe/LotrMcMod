package lotr.common.entity.item;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.fellowship.LOTRFellowshipProfile;
import lotr.common.item.LOTRItemBanner;
import lotr.common.network.LOTRPacketBannerData;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.util.LOTRLog;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class LOTREntityBanner extends Entity {
   private NBTTagCompound protectData;
   private boolean wasEverProtecting = false;
   private boolean playerSpecificProtection;
   private boolean structureProtection = false;
   private int customRange;
   private boolean selfProtection = true;
   public static float ALIGNMENT_PROTECTION_MIN = 1.0F;
   public static float ALIGNMENT_PROTECTION_MAX = 10000.0F;
   private float alignmentProtection;
   public static int WHITELIST_DEFAULT = 16;
   public static int WHITELIST_MIN = 1;
   public static int WHITELIST_MAX = 4000;
   private LOTRBannerWhitelistEntry[] allowedPlayers;
   private Set defaultPermissions;
   private boolean clientside_playerHasPermission;

   public LOTREntityBanner(World world) {
      super(world);
      this.alignmentProtection = ALIGNMENT_PROTECTION_MIN;
      this.allowedPlayers = new LOTRBannerWhitelistEntry[WHITELIST_DEFAULT];
      this.defaultPermissions = new HashSet();
      this.func_70105_a(1.0F, 3.0F);
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(18, (byte)0);
   }

   private int getBannerTypeID() {
      return this.field_70180_af.func_75683_a(18);
   }

   private void setBannerTypeID(int i) {
      this.field_70180_af.func_75692_b(18, (byte)i);
   }

   public void setBannerType(LOTRItemBanner.BannerType type) {
      this.setBannerTypeID(type.bannerID);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.forID(this.getBannerTypeID());
   }

   public int getProtectionRange() {
      if (!this.structureProtection && !LOTRConfig.allowBannerProtection) {
         return 0;
      } else if (this.customRange > 0) {
         return this.customRange;
      } else {
         int i = MathHelper.func_76128_c(this.field_70165_t);
         int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(this.field_70161_v);
         Block block = this.field_70170_p.func_147439_a(i, j - 1, k);
         int meta = this.field_70170_p.func_72805_g(i, j - 1, k);
         return LOTRBannerProtection.getProtectionRange(block, meta);
      }
   }

   public boolean isProtectingTerritory() {
      return this.getProtectionRange() > 0;
   }

   public AxisAlignedBB createProtectionCube() {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      int range = this.getProtectionRange();
      return AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).func_72314_b((double)range, (double)range, (double)range);
   }

   public boolean isPlayerSpecificProtection() {
      return this.playerSpecificProtection;
   }

   public void setPlayerSpecificProtection(boolean flag) {
      this.playerSpecificProtection = flag;
      if (!this.field_70170_p.field_72995_K) {
         this.updateForAllWatchers(this.field_70170_p);
      }

   }

   public boolean isSelfProtection() {
      return !LOTRConfig.allowSelfProtectingBanners ? false : this.selfProtection;
   }

   public void setSelfProtection(boolean flag) {
      this.selfProtection = flag;
      if (!this.field_70170_p.field_72995_K) {
         this.updateForAllWatchers(this.field_70170_p);
      }

   }

   public boolean isStructureProtection() {
      return this.structureProtection;
   }

   public void setStructureProtection(boolean flag) {
      this.structureProtection = flag;
      if (!this.field_70170_p.field_72995_K) {
         this.updateForAllWatchers(this.field_70170_p);
      }

   }

   public void setCustomRange(int i) {
      this.customRange = MathHelper.func_76125_a(i, 0, 64);
      if (!this.field_70170_p.field_72995_K) {
         this.updateForAllWatchers(this.field_70170_p);
      }

   }

   public float getAlignmentProtection() {
      return this.alignmentProtection;
   }

   public void setAlignmentProtection(float f) {
      this.alignmentProtection = MathHelper.func_76131_a(f, ALIGNMENT_PROTECTION_MIN, ALIGNMENT_PROTECTION_MAX);
      if (!this.field_70170_p.field_72995_K) {
         this.updateForAllWatchers(this.field_70170_p);
      }

   }

   public void setPlacingPlayer(EntityPlayer player) {
      this.whitelistPlayer(0, player.func_146103_bH());
   }

   public GameProfile getPlacingPlayer() {
      return this.getWhitelistedPlayer(0);
   }

   public GameProfile getWhitelistedPlayer(int index) {
      return this.allowedPlayers[index] == null ? null : this.allowedPlayers[index].profile;
   }

   public LOTRBannerWhitelistEntry getWhitelistEntry(int index) {
      return this.allowedPlayers[index];
   }

   public void whitelistPlayer(int index, GameProfile profile) {
      List defaultPerms = new ArrayList();
      defaultPerms.add(LOTRBannerProtection.Permission.FULL);
      this.whitelistPlayer(index, profile, defaultPerms);
   }

   public void whitelistPlayer(int index, GameProfile profile, List perms) {
      if (index >= 0 && index < this.allowedPlayers.length) {
         if (profile == null) {
            this.allowedPlayers[index] = null;
         } else {
            LOTRBannerWhitelistEntry entry = new LOTRBannerWhitelistEntry(profile);
            entry.setPermissions(perms);
            this.allowedPlayers[index] = entry;
         }

         if (!this.field_70170_p.field_72995_K) {
            this.updateForAllWatchers(this.field_70170_p);
         }

      }
   }

   public void whitelistFellowship(int index, LOTRFellowship fs, List perms) {
      if (this.isValidFellowship(fs)) {
         this.whitelistPlayer(index, new LOTRFellowshipProfile(this, fs.getFellowshipID(), ""), perms);
      }

   }

   public LOTRFellowship getPlacersFellowshipByName(String fsName) {
      GameProfile owner = this.getPlacingPlayer();
      if (owner != null) {
         UUID ownerID = owner.getId();
         if (ownerID != null) {
            return LOTRLevelData.getData(ownerID).getFellowshipByName(fsName);
         }
      }

      return null;
   }

   public boolean hasDefaultPermission(LOTRBannerProtection.Permission p) {
      return this.defaultPermissions.contains(p);
   }

   public void addDefaultPermission(LOTRBannerProtection.Permission p) {
      if (p != LOTRBannerProtection.Permission.FULL) {
         this.defaultPermissions.add(p);
         if (!this.field_70170_p.field_72995_K) {
            this.updateForAllWatchers(this.field_70170_p);
         }

      }
   }

   public void removeDefaultPermission(LOTRBannerProtection.Permission p) {
      this.defaultPermissions.remove(p);
      if (!this.field_70170_p.field_72995_K) {
         this.updateForAllWatchers(this.field_70170_p);
      }

   }

   public int getDefaultPermBitFlags() {
      return LOTRBannerWhitelistEntry.static_encodePermBitFlags(this.defaultPermissions);
   }

   public void setDefaultPermissions(Collection perms) {
      this.defaultPermissions.clear();
      Iterator var2 = perms.iterator();

      while(var2.hasNext()) {
         LOTRBannerProtection.Permission p = (LOTRBannerProtection.Permission)var2.next();
         if (p != LOTRBannerProtection.Permission.FULL) {
            this.defaultPermissions.add(p);
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         this.updateForAllWatchers(this.field_70170_p);
      }

   }

   public boolean isPlayerWhitelisted(EntityPlayer entityplayer, LOTRBannerProtection.Permission perm) {
      if (this.playerSpecificProtection) {
         if (this.hasDefaultPermission(perm)) {
            return true;
         }

         GameProfile playerProfile = entityplayer.func_146103_bH();
         if (playerProfile != null && playerProfile.getId() != null) {
            UUID playerID = playerProfile.getId();
            LOTRBannerWhitelistEntry[] var5 = this.allowedPlayers;
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               LOTRBannerWhitelistEntry entry = var5[var7];
               if (entry != null) {
                  GameProfile profile = entry.profile;
                  boolean playerMatch = false;
                  if (profile instanceof LOTRFellowshipProfile) {
                     LOTRFellowshipProfile fsPro = (LOTRFellowshipProfile)profile;
                     if (!this.field_70170_p.field_72995_K) {
                        LOTRFellowship fs = fsPro.getFellowship();
                        if (fs != null && fs.containsPlayer(playerID)) {
                           playerMatch = true;
                        }
                     } else {
                        LOTRFellowshipClient fs = fsPro.getFellowshipClient();
                        if (fs != null && fs.containsPlayer(playerID)) {
                           playerMatch = true;
                        }
                     }
                  } else if (profile.getId() != null && profile.getId().equals(playerID)) {
                     playerMatch = true;
                  }

                  if (playerMatch && entry.allowsPermission(perm)) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public int getWhitelistLength() {
      return this.allowedPlayers.length;
   }

   public void resizeWhitelist(int length) {
      length = MathHelper.func_76125_a(length, WHITELIST_MIN, WHITELIST_MAX);
      if (length != this.allowedPlayers.length) {
         LOTRBannerWhitelistEntry[] resized = new LOTRBannerWhitelistEntry[length];

         for(int i = 0; i < length; ++i) {
            if (i < this.allowedPlayers.length) {
               resized[i] = this.allowedPlayers[i];
            }
         }

         this.allowedPlayers = resized;
         if (!this.field_70170_p.field_72995_K) {
            this.updateForAllWatchers(this.field_70170_p);
         }

      }
   }

   public boolean isPlayerAllowedByFaction(EntityPlayer entityplayer, LOTRBannerProtection.Permission perm) {
      if (!this.playerSpecificProtection) {
         if (this.hasDefaultPermission(perm)) {
            return true;
         }

         float alignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getBannerType().faction);
         if (alignment >= this.getAlignmentProtection()) {
            return true;
         }
      }

      return false;
   }

   public boolean func_70067_L() {
      return true;
   }

   public AxisAlignedBB func_70046_E() {
      return null;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      boolean protecting = this.isProtectingTerritory();
      if (!this.field_70170_p.field_72995_K && protecting) {
         this.wasEverProtecting = true;
      }

      if (!this.field_70170_p.field_72995_K && this.getPlacingPlayer() == null && this.playerSpecificProtection) {
         this.playerSpecificProtection = false;
      }

      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.func_145771_j(this.field_70165_t, (this.field_70121_D.field_72338_b + this.field_70121_D.field_72337_e) / 2.0D, this.field_70161_v);
      this.field_70159_w = this.field_70179_y = 0.0D;
      this.field_70181_x = 0.0D;
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      boolean onSolidBlock = World.func_147466_a(this.field_70170_p, i, j - 1, k) && this.field_70121_D.field_72338_b == (double)MathHelper.func_76143_f(this.field_70121_D.field_72338_b);
      if (!this.field_70170_p.field_72995_K && !onSolidBlock) {
         this.dropAsItem(true);
      }

      this.field_70158_ak = protecting;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      nbt.func_74774_a("BannerType", (byte)this.getBannerTypeID());
      if (this.protectData == null && this.wasEverProtecting) {
         this.protectData = new NBTTagCompound();
      }

      if (this.protectData != null) {
         this.writeProtectionToNBT(this.protectData);
         nbt.func_74782_a("ProtectData", this.protectData);
      }

   }

   public void func_70037_a(NBTTagCompound nbt) {
      this.setBannerTypeID(nbt.func_74771_c("BannerType"));
      if (nbt.func_74764_b("PlayerProtection")) {
         this.readProtectionFromNBT(nbt);
         this.protectData = new NBTTagCompound();
         this.writeProtectionToNBT(this.protectData);
      } else if (nbt.func_74764_b("ProtectData")) {
         this.readProtectionFromNBT(nbt.func_74775_l("ProtectData"));
      }

   }

   public final void writeProtectionToNBT(NBTTagCompound nbt) {
      nbt.func_74757_a("PlayerProtection", this.playerSpecificProtection);
      nbt.func_74757_a("StructureProtection", this.structureProtection);
      nbt.func_74777_a("CustomRange", (short)this.customRange);
      nbt.func_74757_a("SelfProtection", this.selfProtection);
      nbt.func_74776_a("AlignProtectF", this.alignmentProtection);
      nbt.func_74768_a("WhitelistLength", this.allowedPlayers.length);
      NBTTagList allowedPlayersTags = new NBTTagList();

      for(int i = 0; i < this.allowedPlayers.length; ++i) {
         LOTRBannerWhitelistEntry entry = this.allowedPlayers[i];
         if (entry != null) {
            GameProfile profile = entry.profile;
            if (profile != null) {
               NBTTagCompound playerData = new NBTTagCompound();
               playerData.func_74768_a("Index", i);
               boolean isFellowship = profile instanceof LOTRFellowshipProfile;
               playerData.func_74757_a("Fellowship", isFellowship);
               if (isFellowship) {
                  LOTRFellowship fs = ((LOTRFellowshipProfile)profile).getFellowship();
                  if (fs != null) {
                     playerData.func_74778_a("FellowshipID", fs.getFellowshipID().toString());
                  }
               } else {
                  NBTTagCompound profileData = new NBTTagCompound();
                  NBTUtil.func_152460_a(profileData, profile);
                  playerData.func_74782_a("Profile", profileData);
               }

               NBTTagList permTags = new NBTTagList();
               Iterator var9 = entry.listPermissions().iterator();

               while(var9.hasNext()) {
                  LOTRBannerProtection.Permission p = (LOTRBannerProtection.Permission)var9.next();
                  String pName = p.codeName;
                  permTags.func_74742_a(new NBTTagString(pName));
               }

               playerData.func_74782_a("Perms", permTags);
               playerData.func_74757_a("PermsSaved", true);
               allowedPlayersTags.func_74742_a(playerData);
            }
         }
      }

      nbt.func_74782_a("AllowedPlayers", allowedPlayersTags);
      if (!this.defaultPermissions.isEmpty()) {
         NBTTagList permTags = new NBTTagList();
         Iterator var13 = this.defaultPermissions.iterator();

         while(var13.hasNext()) {
            LOTRBannerProtection.Permission p = (LOTRBannerProtection.Permission)var13.next();
            String pName = p.codeName;
            permTags.func_74742_a(new NBTTagString(pName));
         }

         nbt.func_74782_a("DefaultPerms", permTags);
      }

   }

   public final void readProtectionFromNBT(NBTTagCompound nbt) {
      this.protectData = (NBTTagCompound)nbt.func_74737_b();
      this.playerSpecificProtection = nbt.func_74767_n("PlayerProtection");
      this.structureProtection = nbt.func_74767_n("StructureProtection");
      this.customRange = nbt.func_74765_d("CustomRange");
      this.customRange = MathHelper.func_76125_a(this.customRange, 0, 64);
      if (nbt.func_74764_b("SelfProtection")) {
         this.selfProtection = nbt.func_74767_n("SelfProtection");
      } else {
         this.selfProtection = true;
      }

      if (nbt.func_74764_b("AlignmentProtection")) {
         this.setAlignmentProtection((float)nbt.func_74762_e("AlignmentProtection"));
      } else {
         this.setAlignmentProtection(nbt.func_74760_g("AlignProtectF"));
      }

      int wlength = WHITELIST_DEFAULT;
      if (nbt.func_74764_b("WhitelistLength")) {
         wlength = nbt.func_74762_e("WhitelistLength");
      }

      this.allowedPlayers = new LOTRBannerWhitelistEntry[wlength];
      NBTTagList allowedPlayersTags = nbt.func_150295_c("AllowedPlayers", 10);

      for(int i = 0; i < allowedPlayersTags.func_74745_c(); ++i) {
         NBTTagCompound playerData = allowedPlayersTags.func_150305_b(i);
         int index = playerData.func_74762_e("Index");
         if (index >= 0 && index < wlength) {
            GameProfile profile = null;
            boolean isFellowship = playerData.func_74767_n("Fellowship");
            if (isFellowship) {
               if (playerData.func_74764_b("FellowshipID")) {
                  UUID fsID = UUID.fromString(playerData.func_74779_i("FellowshipID"));
                  if (fsID != null) {
                     LOTRFellowshipProfile pr = new LOTRFellowshipProfile(this, fsID, "");
                     if (pr.getFellowship() != null) {
                        profile = pr;
                     }
                  }
               }
            } else if (playerData.func_74764_b("Profile")) {
               NBTTagCompound profileData = playerData.func_74775_l("Profile");
               profile = NBTUtil.func_152459_a(profileData);
            }

            if (profile != null) {
               LOTRBannerWhitelistEntry entry = new LOTRBannerWhitelistEntry((GameProfile)profile);
               this.allowedPlayers[i] = entry;
               boolean savedWithPerms = playerData.func_74767_n("PermsSaved");
               if (savedWithPerms) {
                  if (playerData.func_150297_b("Perms", 9)) {
                     NBTTagList permTags = playerData.func_150295_c("Perms", 8);

                     for(int p = 0; p < permTags.func_74745_c(); ++p) {
                        String pName = permTags.func_150307_f(p);
                        LOTRBannerProtection.Permission perm = LOTRBannerProtection.Permission.forName(pName);
                        if (perm != null) {
                           entry.addPermission(perm);
                        }
                     }
                  }
               } else {
                  entry.setFullPerms();
               }
            }
         }
      }

      this.validateWhitelistedFellowships();
      this.defaultPermissions.clear();
      if (nbt.func_74764_b("DefaultPerms")) {
         NBTTagList permTags = nbt.func_150295_c("DefaultPerms", 8);

         for(int p = 0; p < permTags.func_74745_c(); ++p) {
            String pName = permTags.func_150307_f(p);
            LOTRBannerProtection.Permission perm = LOTRBannerProtection.Permission.forName(pName);
            if (perm != null) {
               this.defaultPermissions.add(perm);
            }
         }
      }

   }

   private boolean isValidFellowship(LOTRFellowship fs) {
      GameProfile owner = this.getPlacingPlayer();
      return fs != null && !fs.isDisbanded() && owner != null && owner.getId() != null && fs.containsPlayer(owner.getId());
   }

   private void validateWhitelistedFellowships() {
      GameProfile owner = this.getPlacingPlayer();

      for(int i = 0; i < this.allowedPlayers.length; ++i) {
         GameProfile profile = this.getWhitelistedPlayer(i);
         if (profile instanceof LOTRFellowshipProfile) {
            LOTRFellowshipProfile fsProfile = (LOTRFellowshipProfile)profile;
            LOTRFellowship fs = fsProfile.getFellowship();
            if (!this.isValidFellowship(fs)) {
               this.allowedPlayers[i] = null;
            }
         }
      }

   }

   public boolean canPlayerEditBanner(EntityPlayer entityplayer) {
      GameProfile owner = this.getPlacingPlayer();
      if (owner != null && owner.getId() != null && entityplayer.func_110124_au().equals(owner.getId())) {
         return true;
      } else {
         return !this.isStructureProtection() && MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(entityplayer.func_146103_bH()) && entityplayer.field_71075_bZ.field_75098_d;
      }
   }

   public boolean func_130002_c(EntityPlayer entityplayer) {
      if (!this.field_70170_p.field_72995_K && this.isProtectingTerritory() && this.canPlayerEditBanner(entityplayer)) {
         this.sendBannerToPlayer(entityplayer, true, true);
      }

      return true;
   }

   public boolean func_70097_a(DamageSource damagesource, float f) {
      boolean isProtectionBanner = this.isProtectingTerritory();
      boolean isPlayerDamage = damagesource.func_76346_g() instanceof EntityPlayer;
      if (isProtectionBanner && !isPlayerDamage) {
         return false;
      } else {
         if (!this.field_70128_L && !this.field_70170_p.field_72995_K) {
            if (isPlayerDamage) {
               EntityPlayer entityplayer = (EntityPlayer)damagesource.func_76346_g();
               if (LOTRBannerProtection.isProtected(this.field_70170_p, this, LOTRBannerProtection.forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL), true)) {
                  if (!isProtectionBanner) {
                     return false;
                  }

                  if (this.selfProtection) {
                     return false;
                  }

                  if (this.structureProtection && damagesource.func_76346_g() != damagesource.func_76364_f()) {
                     return false;
                  }
               }

               if (isProtectionBanner && this.selfProtection && !this.canPlayerEditBanner(entityplayer)) {
                  return false;
               }
            }

            this.func_70018_K();
            this.field_70170_p.func_72956_a(this, Blocks.field_150344_f.field_149762_H.func_150495_a(), (Blocks.field_150344_f.field_149762_H.func_150497_c() + 1.0F) / 2.0F, Blocks.field_150344_f.field_149762_H.func_150494_d() * 0.8F);
            boolean drop = true;
            if (damagesource.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)damagesource.func_76346_g()).field_71075_bZ.field_75098_d) {
               drop = false;
            }

            this.dropAsItem(drop);
         }

         return true;
      }
   }

   private void dropAsItem(boolean drop) {
      this.func_70106_y();
      if (drop) {
         this.func_70099_a(this.getBannerItem(), 0.0F);
      }

   }

   public ItemStack getPickedResult(MovingObjectPosition target) {
      return this.getBannerItem();
   }

   private ItemStack getBannerItem() {
      ItemStack item = new ItemStack(LOTRMod.banner, 1, this.getBannerType().bannerID);
      if (this.wasEverProtecting && this.protectData == null) {
         this.protectData = new NBTTagCompound();
      }

      if (this.protectData != null) {
         this.writeProtectionToNBT(this.protectData);
         if (!this.structureProtection) {
            LOTRItemBanner.setProtectionData(item, this.protectData);
         }
      }

      return item;
   }

   public void sendBannerToPlayer(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
      this.sendBannerData(entityplayer, sendWhitelist, openGui);
   }

   private void updateForAllWatchers(World world) {
      int x = MathHelper.func_76128_c(this.field_70165_t) >> 4;
      int z = MathHelper.func_76128_c(this.field_70161_v) >> 4;
      PlayerManager playermanager = ((WorldServer)this.field_70170_p).func_73040_p();
      List players = this.field_70170_p.field_73010_i;
      Iterator var6 = players.iterator();

      while(var6.hasNext()) {
         Object obj = var6.next();
         EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
         if (playermanager.func_72694_a(entityplayer, x, z)) {
            this.sendBannerData(entityplayer, false, false);
         }
      }

   }

   private void sendBannerData(EntityPlayer entityplayer, boolean sendWhitelist, boolean openGui) {
      LOTRPacketBannerData packet = new LOTRPacketBannerData(this.func_145782_y(), openGui);
      packet.playerSpecificProtection = this.playerSpecificProtection;
      packet.selfProtection = this.selfProtection;
      packet.structureProtection = this.structureProtection;
      packet.customRange = this.customRange;
      packet.alignmentProtection = this.getAlignmentProtection();
      packet.whitelistLength = this.getWhitelistLength();
      int maxSendIndex = sendWhitelist ? this.allowedPlayers.length : 1;
      String[] whitelistSlots = new String[maxSendIndex];
      int[] whitelistPerms = new int[maxSendIndex];

      for(int index = 0; index < maxSendIndex; ++index) {
         LOTRBannerWhitelistEntry entry = this.allowedPlayers[index];
         if (entry == null) {
            whitelistSlots[index] = null;
         } else {
            GameProfile profile = entry.profile;
            if (profile == null) {
               whitelistSlots[index] = null;
            } else {
               if (profile instanceof LOTRFellowshipProfile) {
                  LOTRFellowshipProfile fsProfile = (LOTRFellowshipProfile)profile;
                  LOTRFellowship fs = fsProfile.getFellowship();
                  if (this.isValidFellowship(fs)) {
                     whitelistSlots[index] = LOTRFellowshipProfile.addFellowshipCode(fs.getName());
                  }
               } else {
                  if (StringUtils.func_151246_b(profile.getName())) {
                     MinecraftServer.func_71276_C().func_147130_as().fillProfileProperties(profile, true);
                  }

                  String username = profile.getName();
                  if (StringUtils.func_151246_b(username)) {
                     whitelistSlots[index] = null;
                     if (index == 0) {
                        LOTRLog.logger.info("LOTR: Banner needs to be replaced at " + MathHelper.func_76128_c(this.field_70165_t) + " " + MathHelper.func_76128_c(this.field_70163_u) + " " + MathHelper.func_76128_c(this.field_70161_v) + " dim_" + this.field_71093_bK);
                     }
                  } else {
                     whitelistSlots[index] = username;
                  }
               }

               if (whitelistSlots[index] != null) {
                  whitelistPerms[index] = entry.encodePermBitFlags();
               }
            }
         }
      }

      packet.whitelistSlots = whitelistSlots;
      packet.whitelistPerms = whitelistPerms;
      packet.defaultPerms = this.getDefaultPermBitFlags();
      packet.thisPlayerHasPermission = this.isPlayerPermittedInSurvival(entityplayer);
      LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
   }

   private boolean isPlayerPermittedInSurvival(EntityPlayer entityplayer) {
      return (new LOTRBannerProtection.FilterForPlayer(entityplayer, LOTRBannerProtection.Permission.FULL)).ignoreCreativeMode().protects(this) == LOTRBannerProtection.ProtectType.NONE;
   }

   public boolean clientside_playerHasPermissionInSurvival() {
      return this.clientside_playerHasPermission;
   }

   public void setClientside_playerHasPermissionInSurvival(boolean flag) {
      this.clientside_playerHasPermission = flag;
   }
}
