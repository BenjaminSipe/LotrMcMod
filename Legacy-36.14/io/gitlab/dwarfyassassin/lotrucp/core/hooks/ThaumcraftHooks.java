package io.gitlab.dwarfyassassin.lotrucp.core.hooks;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.gitlab.dwarfyassassin.lotrucp.core.UCPCoreMod;
import io.gitlab.dwarfyassassin.lotrucp.server.util.PlayerUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRBannerProtection;
import lotr.common.LOTRReflection;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class ThaumcraftHooks {
   private static boolean doneReflection = false;
   private static Class class_golem;
   private static Method method_getOwnerName;

   public static LOTRBannerProtection.ProtectType thaumcraftGolemBannerProtection(EntityPlayer player, LOTREntityBanner banner) {
      World world = player.field_70170_p;
      if (player instanceof FakePlayer) {
         FakePlayer fakePlayer = (FakePlayer)player;
         GameProfile profile = fakePlayer.func_146103_bH();
         if (profile.getName().equals("FakeThaumcraftGolem")) {
            if (!doneReflection) {
               try {
                  class_golem = Class.forName("thaumcraft.common.entities.golems.EntityGolemBase");
                  method_getOwnerName = class_golem.getDeclaredMethod("getOwnerName");
               } catch (ClassNotFoundException var17) {
                  UCPCoreMod.log.error("Was unable to find Thaumcraft EntityGolemBase class");
                  var17.printStackTrace();
               } catch (NoSuchMethodException var18) {
                  UCPCoreMod.log.error("Was unable to find Thaumcraft EntityGolemBase.getOwnerName method");
                  var18.printStackTrace();
               }

               doneReflection = true;
            }

            if (class_golem == null || method_getOwnerName == null) {
               UCPCoreMod.log.error("Failed to reflectively locate Thaumcraft EntityGolemBase class or getOwnerName method.Found class = %s, found method = %s", new Object[]{class_golem, method_getOwnerName});
               return null;
            }

            List foundGolems = world.func_72872_a(class_golem, player.field_70121_D.func_72314_b(1.0D, 1.0D, 1.0D));
            Entity closestGolem = null;
            double foundDistance = Double.MAX_VALUE;
            Iterator var9 = foundGolems.iterator();

            while(var9.hasNext()) {
               Entity golem = (Entity)var9.next();
               double distance = player.func_70068_e(golem);
               if (distance < foundDistance) {
                  closestGolem = golem;
               }
            }

            if (closestGolem == null) {
               return null;
            }

            UUID uuid = null;

            try {
               String golemOwner = (String)method_getOwnerName.invoke(closestGolem);
               uuid = PlayerUtils.getLastKownUUIDFromUsername(golemOwner);
            } catch (IllegalAccessException var14) {
               UCPCoreMod.log.error("Was unable to invoke Thaumcraft EntityGolemBase.getOwnerName method");
               var14.printStackTrace();
            } catch (IllegalArgumentException var15) {
               UCPCoreMod.log.error("Was unable to invoke Thaumcraft EntityGolemBase.getOwnerName method");
               var15.printStackTrace();
            } catch (InvocationTargetException var16) {
               UCPCoreMod.log.error("Was unable to invoke Thaumcraft EntityGolemBase.getOwnerName method");
               var16.printStackTrace();
            }

            if (uuid == null) {
               UCPCoreMod.log.error("Was unable to find the player UUID from Thaumcraft EntityGolemBase.getOwnerName - UUID is %s", new Object[]{uuid});
               return null;
            }

            try {
               LOTRReflection.setFinalField(GameProfile.class, profile, uuid, (String[])("id"));
               ReflectionHelper.setPrivateValue(Entity.class, fakePlayer, uuid, new String[]{"entityUniqueID", "field_96093_i"});
            } catch (Exception var13) {
               UCPCoreMod.log.error("Was unable to set a FakeThaumcraftGolem player uuid to " + uuid.toString());
               var13.printStackTrace();
            }
         }
      }

      return null;
   }
}
