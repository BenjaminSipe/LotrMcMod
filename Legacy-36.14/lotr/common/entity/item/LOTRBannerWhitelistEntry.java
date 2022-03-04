package lotr.common.entity.item;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lotr.common.LOTRBannerProtection;

public class LOTRBannerWhitelistEntry {
   public final GameProfile profile;
   private Set perms = new HashSet();

   public LOTRBannerWhitelistEntry(GameProfile p) {
      this.profile = p;
      if (this.profile == null) {
         throw new IllegalArgumentException("Banner whitelist entry cannot have a null profile!");
      }
   }

   public LOTRBannerWhitelistEntry setFullPerms() {
      this.clearPermissions();
      this.addPermission(LOTRBannerProtection.Permission.FULL);
      return this;
   }

   public boolean isPermissionEnabled(LOTRBannerProtection.Permission p) {
      return this.perms.contains(p);
   }

   public boolean allowsPermission(LOTRBannerProtection.Permission p) {
      return this.isPermissionEnabled(LOTRBannerProtection.Permission.FULL) || this.isPermissionEnabled(p);
   }

   public void addPermission(LOTRBannerProtection.Permission p) {
      this.perms.add(p);
   }

   public void removePermission(LOTRBannerProtection.Permission p) {
      this.perms.remove(p);
   }

   public void setPermissions(List perms) {
      this.clearPermissions();
      Iterator var2 = perms.iterator();

      while(var2.hasNext()) {
         LOTRBannerProtection.Permission p = (LOTRBannerProtection.Permission)var2.next();
         this.addPermission(p);
      }

   }

   public void clearPermissions() {
      this.perms.clear();
   }

   public Set listPermissions() {
      return this.perms;
   }

   public int encodePermBitFlags() {
      return static_encodePermBitFlags(this.perms);
   }

   public void decodePermBitFlags(int i) {
      this.setPermissions(static_decodePermBitFlags(i));
   }

   public static int static_encodePermBitFlags(Collection permList) {
      int i = 0;

      LOTRBannerProtection.Permission p;
      for(Iterator var2 = permList.iterator(); var2.hasNext(); i |= p.bitFlag) {
         p = (LOTRBannerProtection.Permission)var2.next();
      }

      return i;
   }

   public static List static_decodePermBitFlags(int i) {
      List decoded = new ArrayList();
      LOTRBannerProtection.Permission[] var2 = LOTRBannerProtection.Permission.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         LOTRBannerProtection.Permission p = var2[var4];
         if ((i & p.bitFlag) != 0) {
            decoded.add(p);
         }
      }

      return decoded;
   }
}
