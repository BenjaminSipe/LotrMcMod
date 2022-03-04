package lotr.common;

import com.mojang.authlib.GameProfile;
import io.gitlab.dwarfyassassin.lotrucp.core.hooks.ThaumcraftHooks;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRBannerProtection {
   public static final int MAX_RANGE = 64;
   private static Map protectionBlocks = new HashMap();
   private static Map lastWarningTimes;

   public static int getProtectionRange(Block block, int meta) {
      Integer i = (Integer)protectionBlocks.get(Pair.of(block, meta));
      return i == null ? 0 : i;
   }

   public static boolean isProtected(World world, Entity entity, LOTRBannerProtection.IFilter protectFilter, boolean sendMessage) {
      int i = MathHelper.func_76128_c(entity.field_70165_t);
      int j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(entity.field_70161_v);
      return isProtected(world, i, j, k, protectFilter, sendMessage);
   }

   public static boolean isProtected(World world, int i, int j, int k, LOTRBannerProtection.IFilter protectFilter, boolean sendMessage) {
      return isProtected(world, i, j, k, protectFilter, sendMessage, 0.0D);
   }

   public static boolean isProtected(World world, int i, int j, int k, LOTRBannerProtection.IFilter protectFilter, boolean sendMessage, double searchExtra) {
      if (!LOTRConfig.allowBannerProtection) {
         return false;
      } else {
         String protectorName = null;
         AxisAlignedBB originCube = AxisAlignedBB.func_72330_a((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).func_72314_b(searchExtra, searchExtra, searchExtra);
         AxisAlignedBB searchCube = originCube.func_72314_b(64.0D, 64.0D, 64.0D);
         List banners = world.func_72872_a(LOTREntityBanner.class, searchCube);
         if (!banners.isEmpty()) {
            for(int l = 0; l < banners.size(); ++l) {
               LOTREntityBanner banner = (LOTREntityBanner)banners.get(l);
               AxisAlignedBB protectionCube = banner.createProtectionCube();
               if (banner.isProtectingTerritory() && protectionCube.func_72326_a(searchCube) && protectionCube.func_72326_a(originCube)) {
                  LOTRBannerProtection.ProtectType result = protectFilter.protects(banner);
                  if (result != LOTRBannerProtection.ProtectType.NONE) {
                     if (result == LOTRBannerProtection.ProtectType.FACTION) {
                        protectorName = banner.getBannerType().faction.factionName();
                        break;
                     }

                     if (result == LOTRBannerProtection.ProtectType.PLAYER_SPECIFIC) {
                        GameProfile placingPlayer = banner.getPlacingPlayer();
                        if (placingPlayer != null) {
                           if (StringUtils.isBlank(placingPlayer.getName())) {
                              MinecraftServer.func_71276_C().func_147130_as().fillProfileProperties(placingPlayer, true);
                           }

                           protectorName = placingPlayer.getName();
                        } else {
                           protectorName = "?";
                        }
                        break;
                     }

                     if (result == LOTRBannerProtection.ProtectType.STRUCTURE) {
                        protectorName = StatCollector.func_74838_a("chat.lotr.protectedStructure");
                        break;
                     }
                  }
               }
            }
         }

         if (protectorName != null) {
            if (sendMessage) {
               protectFilter.warnProtection(new ChatComponentTranslation("chat.lotr.protectedLand", new Object[]{protectorName}));
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static LOTRBannerProtection.IFilter anyBanner() {
      return new LOTRBannerProtection.IFilter() {
         public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
            return banner.isStructureProtection() ? LOTRBannerProtection.ProtectType.STRUCTURE : LOTRBannerProtection.ProtectType.FACTION;
         }

         public void warnProtection(IChatComponent message) {
         }
      };
   }

   public static LOTRBannerProtection.IFilter forPlayer(EntityPlayer entityplayer) {
      return forPlayer(entityplayer, LOTRBannerProtection.Permission.FULL);
   }

   public static LOTRBannerProtection.IFilter forPlayer(EntityPlayer entityplayer, LOTRBannerProtection.Permission perm) {
      return new LOTRBannerProtection.FilterForPlayer(entityplayer, perm);
   }

   public static LOTRBannerProtection.IFilter forPlayer_returnMessage(final EntityPlayer entityplayer, final LOTRBannerProtection.Permission perm, final IChatComponent[] protectionMessage) {
      return new LOTRBannerProtection.IFilter() {
         private LOTRBannerProtection.IFilter internalPlayerFilter = LOTRBannerProtection.forPlayer(entityplayer, perm);

         public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
            return this.internalPlayerFilter.protects(banner);
         }

         public void warnProtection(IChatComponent message) {
            this.internalPlayerFilter.warnProtection(message);
            protectionMessage[0] = message;
         }
      };
   }

   public static LOTRBannerProtection.IFilter forNPC(final EntityLiving entity) {
      return new LOTRBannerProtection.IFilter() {
         public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
            if (banner.isStructureProtection()) {
               return LOTRBannerProtection.ProtectType.STRUCTURE;
            } else {
               return banner.getBannerType().faction.isBadRelation(LOTRMod.getNPCFaction(entity)) ? LOTRBannerProtection.ProtectType.FACTION : LOTRBannerProtection.ProtectType.NONE;
            }
         }

         public void warnProtection(IChatComponent message) {
         }
      };
   }

   public static LOTRBannerProtection.IFilter forInvasionSpawner(LOTREntityInvasionSpawner spawner) {
      return forFaction(spawner.getInvasionType().invasionFaction);
   }

   public static LOTRBannerProtection.IFilter forFaction(final LOTRFaction theFaction) {
      return new LOTRBannerProtection.IFilter() {
         public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
            if (banner.isStructureProtection()) {
               return LOTRBannerProtection.ProtectType.STRUCTURE;
            } else {
               return banner.getBannerType().faction.isBadRelation(theFaction) ? LOTRBannerProtection.ProtectType.FACTION : LOTRBannerProtection.ProtectType.NONE;
            }
         }

         public void warnProtection(IChatComponent message) {
         }
      };
   }

   public static LOTRBannerProtection.IFilter forTNT(final EntityTNTPrimed bomb) {
      return new LOTRBannerProtection.IFilter() {
         public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
            if (banner.isStructureProtection()) {
               return LOTRBannerProtection.ProtectType.STRUCTURE;
            } else {
               EntityLivingBase bomber = bomb.func_94083_c();
               if (bomber == null) {
                  return LOTRBannerProtection.ProtectType.FACTION;
               } else if (bomber instanceof EntityPlayer) {
                  return LOTRBannerProtection.forPlayer((EntityPlayer)bomber, LOTRBannerProtection.Permission.FULL).protects(banner);
               } else {
                  return bomber instanceof EntityLiving ? LOTRBannerProtection.forNPC((EntityLiving)bomber).protects(banner) : LOTRBannerProtection.ProtectType.NONE;
               }
            }
         }

         public void warnProtection(IChatComponent message) {
         }
      };
   }

   public static LOTRBannerProtection.IFilter forTNTMinecart(EntityMinecartTNT minecart) {
      return new LOTRBannerProtection.IFilter() {
         public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
            return banner.isStructureProtection() ? LOTRBannerProtection.ProtectType.STRUCTURE : LOTRBannerProtection.ProtectType.FACTION;
         }

         public void warnProtection(IChatComponent message) {
         }
      };
   }

   public static LOTRBannerProtection.IFilter forThrown(final EntityThrowable throwable) {
      return new LOTRBannerProtection.IFilter() {
         public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
            if (banner.isStructureProtection()) {
               return LOTRBannerProtection.ProtectType.STRUCTURE;
            } else {
               EntityLivingBase thrower = throwable.func_85052_h();
               if (thrower == null) {
                  return LOTRBannerProtection.ProtectType.FACTION;
               } else if (thrower instanceof EntityPlayer) {
                  return LOTRBannerProtection.forPlayer((EntityPlayer)thrower, LOTRBannerProtection.Permission.FULL).protects(banner);
               } else {
                  return thrower instanceof EntityLiving ? LOTRBannerProtection.forNPC((EntityLiving)thrower).protects(banner) : LOTRBannerProtection.ProtectType.NONE;
               }
            }
         }

         public void warnProtection(IChatComponent message) {
         }
      };
   }

   private static void setWarningCooldown(EntityPlayer entityplayer) {
      lastWarningTimes.put(entityplayer.func_110124_au(), LOTRConfig.bannerWarningCooldown);
   }

   private static boolean hasWarningCooldown(EntityPlayer entityplayer) {
      return lastWarningTimes.containsKey(entityplayer.func_110124_au());
   }

   public static void updateWarningCooldowns() {
      Set removes = new HashSet();
      Iterator var1 = lastWarningTimes.entrySet().iterator();

      while(var1.hasNext()) {
         Entry e = (Entry)var1.next();
         UUID player = (UUID)e.getKey();
         int time = (Integer)e.getValue();
         --time;
         e.setValue(time);
         if (time <= 0) {
            removes.add(player);
         }
      }

      var1 = removes.iterator();

      while(var1.hasNext()) {
         UUID player = (UUID)var1.next();
         lastWarningTimes.remove(player);
      }

   }

   static {
      Pair BRONZE = Pair.of(LOTRMod.blockOreStorage, 2);
      Pair SILVER = Pair.of(LOTRMod.blockOreStorage, 3);
      Pair GOLD = Pair.of(Blocks.field_150340_R, 0);
      protectionBlocks.put(BRONZE, 8);
      protectionBlocks.put(SILVER, 16);
      protectionBlocks.put(GOLD, 32);
      lastWarningTimes = new HashMap();
   }

   public static class FilterForPlayer implements LOTRBannerProtection.IFilter {
      private EntityPlayer thePlayer;
      private LOTRBannerProtection.Permission thePerm;
      private boolean ignoreCreativeMode = false;

      public FilterForPlayer(EntityPlayer p, LOTRBannerProtection.Permission perm) {
         this.thePlayer = p;
         this.thePerm = perm;
      }

      public LOTRBannerProtection.FilterForPlayer ignoreCreativeMode() {
         this.ignoreCreativeMode = true;
         return this;
      }

      public LOTRBannerProtection.ProtectType protects(LOTREntityBanner banner) {
         if (this.thePlayer instanceof FakePlayer) {
            LOTRBannerProtection.ProtectType hook = ThaumcraftHooks.thaumcraftGolemBannerProtection(this.thePlayer, banner);
            if (hook != null) {
               return hook;
            }
         }

         if (this.thePlayer.field_71075_bZ.field_75098_d && !this.ignoreCreativeMode) {
            return LOTRBannerProtection.ProtectType.NONE;
         } else if (banner.isStructureProtection()) {
            return LOTRBannerProtection.ProtectType.STRUCTURE;
         } else if (banner.isPlayerSpecificProtection()) {
            return !banner.isPlayerWhitelisted(this.thePlayer, this.thePerm) ? LOTRBannerProtection.ProtectType.PLAYER_SPECIFIC : LOTRBannerProtection.ProtectType.NONE;
         } else {
            return !banner.isPlayerAllowedByFaction(this.thePlayer, this.thePerm) ? LOTRBannerProtection.ProtectType.FACTION : LOTRBannerProtection.ProtectType.NONE;
         }
      }

      public void warnProtection(IChatComponent message) {
         if (!(this.thePlayer instanceof FakePlayer)) {
            if (this.thePlayer instanceof EntityPlayerMP && !this.thePlayer.field_70170_p.field_72995_K) {
               EntityPlayerMP entityplayermp = (EntityPlayerMP)this.thePlayer;
               entityplayermp.func_71120_a(this.thePlayer.field_71069_bz);
               if (!LOTRBannerProtection.hasWarningCooldown(entityplayermp)) {
                  entityplayermp.func_145747_a(message);
                  LOTRBannerProtection.setWarningCooldown(entityplayermp);
               }
            }

         }
      }
   }

   public interface IFilter {
      LOTRBannerProtection.ProtectType protects(LOTREntityBanner var1);

      void warnProtection(IChatComponent var1);
   }

   public static enum Permission {
      FULL,
      DOORS,
      TABLES,
      CONTAINERS,
      PERSONAL_CONTAINERS,
      FOOD,
      BEDS,
      SWITCHES;

      public final int bitFlag = 1 << this.ordinal();
      public final String codeName = this.name();

      public static LOTRBannerProtection.Permission forName(String s) {
         LOTRBannerProtection.Permission[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRBannerProtection.Permission p = var1[var3];
            if (p.codeName.equals(s)) {
               return p;
            }
         }

         return null;
      }
   }

   public static enum ProtectType {
      NONE,
      FACTION,
      PLAYER_SPECIFIC,
      STRUCTURE;
   }
}
