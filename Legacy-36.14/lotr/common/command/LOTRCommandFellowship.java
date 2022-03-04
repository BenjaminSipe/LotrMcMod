package lotr.common.command;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;

public class LOTRCommandFellowship extends CommandBase {
   public String func_71517_b() {
      return "fellowship";
   }

   public int func_82362_a() {
      return 3;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.fellowship.usage";
   }

   private UUID getPlayerIDByName(ICommandSender sender, String username) {
      try {
         EntityPlayerMP entityplayer = func_82359_c(sender, username);
         if (entityplayer != null) {
            return entityplayer.func_110124_au();
         }
      } catch (PlayerNotFoundException var4) {
      }

      GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152655_a(username);
      return profile != null ? profile.getId() : null;
   }

   public static String[] fixArgsForFellowship(String[] args, int startIndex, boolean autocompleting) {
      if (!args[startIndex].startsWith("\"")) {
         if (!autocompleting) {
            throw new WrongUsageException("commands.lotr.fellowship.edit.nameError", new Object[0]);
         } else {
            return args;
         }
      } else {
         int endIndex = startIndex;
         boolean foundEnd = false;

         while(!foundEnd) {
            if (args[endIndex].endsWith("\"")) {
               foundEnd = true;
            } else {
               if (endIndex >= args.length - 1) {
                  if (!autocompleting) {
                     throw new WrongUsageException("commands.lotr.fellowship.edit.nameError", new Object[0]);
                  }
                  break;
               }

               ++endIndex;
            }
         }

         String fsName = "";

         int diff;
         for(diff = startIndex; diff <= endIndex; ++diff) {
            if (diff > startIndex) {
               fsName = fsName + " ";
            }

            fsName = fsName + args[diff];
         }

         if (!autocompleting || foundEnd) {
            fsName = fsName.replace("\"", "");
         }

         diff = endIndex - startIndex;
         String[] argsNew = new String[args.length - diff];

         for(int i = 0; i < argsNew.length; ++i) {
            if (i < startIndex) {
               argsNew[i] = args[i];
            } else if (i == startIndex) {
               argsNew[i] = fsName;
            } else {
               argsNew[i] = args[i + diff];
            }
         }

         return argsNew;
      }
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      String ownerName;
      String fsName;
      if (args.length >= 3 && args[0].equals("create")) {
         args = fixArgsForFellowship(args, 2, false);
         ownerName = args[1];
         fsName = args[2];
         if (fsName == null) {
            throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", new Object[]{ownerName, fsName});
         } else {
            UUID playerID = this.getPlayerIDByName(sender, ownerName);
            if (playerID != null) {
               LOTRPlayerData playerData = LOTRLevelData.getData(playerID);
               LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
               if (fellowship == null) {
                  playerData.createFellowship(fsName, false);
                  func_152373_a(sender, this, "commands.lotr.fellowship.create", new Object[]{ownerName, fsName});
               } else {
                  throw new WrongUsageException("commands.lotr.fellowship.create.exists", new Object[]{ownerName, fsName});
               }
            } else {
               throw new PlayerNotFoundException();
            }
         }
      } else if (args[0].equals("option")) {
         args = fixArgsForFellowship(args, 2, false);
         if (args.length >= 4) {
            ownerName = args[1];
            fsName = args[2];
            if (fsName == null) {
               throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", new Object[]{ownerName, fsName});
            } else {
               String option = args[3];
               UUID ownerID = this.getPlayerIDByName(sender, ownerName);
               if (ownerID == null) {
                  throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
               } else {
                  LOTRPlayerData ownerData = LOTRLevelData.getData(ownerID);
                  LOTRFellowship fellowship = ownerData.getFellowshipByName(fsName);
                  if (fellowship != null && fellowship.isOwner(ownerID)) {
                     if (option.equals("disband")) {
                        ownerData.disbandFellowship(fellowship, ownerName);
                        func_152373_a(sender, this, "commands.lotr.fellowship.disband", new Object[]{ownerName, fsName});
                     } else {
                        String playerName;
                        if (option.equals("rename")) {
                           playerName = "";
                           int startIndex = 4;
                           if (args[startIndex].startsWith("\"")) {
                              int endIndex = startIndex;

                              while(!args[endIndex].endsWith("\"")) {
                                 ++endIndex;
                                 if (endIndex >= args.length) {
                                    throw new WrongUsageException("commands.lotr.fellowship.rename.error", new Object[0]);
                                 }
                              }

                              for(int i = startIndex; i <= endIndex; ++i) {
                                 if (i > startIndex) {
                                    playerName = playerName + " ";
                                 }

                                 playerName = playerName + args[i];
                              }

                              playerName = playerName.replace("\"", "");
                           }

                           if (!StringUtils.isBlank(playerName)) {
                              ownerData.renameFellowship(fellowship, playerName);
                              func_152373_a(sender, this, "commands.lotr.fellowship.rename", new Object[]{ownerName, fsName, playerName});
                           } else {
                              throw new WrongUsageException("commands.lotr.fellowship.rename.error", new Object[0]);
                           }
                        } else {
                           UUID playerID;
                           if (option.equals("icon")) {
                              playerName = func_147178_a(sender, args, 4).func_150260_c();
                              if (playerName.equals("clear")) {
                                 ownerData.setFellowshipIcon(fellowship, (ItemStack)null);
                                 func_152373_a(sender, this, "commands.lotr.fellowship.icon", new Object[]{ownerName, fsName, "[none]"});
                              } else {
                                 playerID = null;

                                 ItemStack itemstack;
                                 try {
                                    NBTBase nbt = JsonToNBT.func_150315_a(playerName);
                                    if (!(nbt instanceof NBTTagCompound)) {
                                       func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", new Object[]{"Not a valid tag"});
                                       return;
                                    }

                                    NBTTagCompound compound = (NBTTagCompound)nbt;
                                    itemstack = ItemStack.func_77949_a(compound);
                                 } catch (NBTException var13) {
                                    func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", new Object[]{var13.getMessage()});
                                    return;
                                 }

                                 if (itemstack != null) {
                                    ownerData.setFellowshipIcon(fellowship, itemstack);
                                    func_152373_a(sender, this, "commands.lotr.fellowship.icon", new Object[]{ownerName, fsName, itemstack.func_82833_r()});
                                 } else {
                                    func_152373_a(sender, this, "commands.lotr.fellowship.icon.tagError", new Object[]{"No item"});
                                 }
                              }
                           } else {
                              boolean prevent;
                              if (!option.equals("pvp") && !option.equals("hired-ff")) {
                                 if (option.equals("map-show")) {
                                    playerName = args[4];
                                    if (playerName.equals("on")) {
                                       prevent = true;
                                    } else {
                                       if (!playerName.equals("off")) {
                                          throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
                                       }

                                       prevent = false;
                                    }

                                    ownerData.setFellowshipShowMapLocations(fellowship, prevent);
                                    if (prevent) {
                                       func_152373_a(sender, this, "commands.lotr.fellowship.mapShow.on", new Object[]{ownerName, fsName});
                                    } else {
                                       func_152373_a(sender, this, "commands.lotr.fellowship.mapShow.off", new Object[]{ownerName, fsName});
                                    }

                                    return;
                                 }

                                 if (args.length < 3) {
                                    throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
                                 }

                                 playerName = args[4];
                                 playerID = this.getPlayerIDByName(sender, playerName);
                                 if (playerID == null) {
                                    throw new PlayerNotFoundException();
                                 }

                                 LOTRPlayerData playerData = LOTRLevelData.getData(playerID);
                                 if (option.equals("invite")) {
                                    if (!fellowship.containsPlayer(playerID)) {
                                       ownerData.invitePlayerToFellowship(fellowship, playerID, ownerName);
                                       func_152373_a(sender, this, "commands.lotr.fellowship.invite", new Object[]{ownerName, fsName, playerName});
                                       return;
                                    }

                                    throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", new Object[]{ownerName, fsName, playerName});
                                 }

                                 if (option.equals("add")) {
                                    if (!fellowship.containsPlayer(playerID)) {
                                       ownerData.invitePlayerToFellowship(fellowship, playerID, ownerName);
                                       playerData.acceptFellowshipInvite(fellowship, false);
                                       func_152373_a(sender, this, "commands.lotr.fellowship.add", new Object[]{ownerName, fsName, playerName});
                                       return;
                                    }

                                    throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyIn", new Object[]{ownerName, fsName, playerName});
                                 }

                                 if (option.equals("remove")) {
                                    if (fellowship.hasMember(playerID)) {
                                       ownerData.removePlayerFromFellowship(fellowship, playerID, ownerName);
                                       func_152373_a(sender, this, "commands.lotr.fellowship.remove", new Object[]{ownerName, fsName, playerName});
                                       return;
                                    }

                                    throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[]{ownerName, fsName, playerName});
                                 }

                                 if (option.equals("transfer")) {
                                    if (fellowship.hasMember(playerID)) {
                                       ownerData.transferFellowship(fellowship, playerID, ownerName);
                                       func_152373_a(sender, this, "commands.lotr.fellowship.transfer", new Object[]{ownerName, fsName, playerName});
                                       return;
                                    }

                                    throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[]{ownerName, fsName, playerName});
                                 }

                                 if (option.equals("op")) {
                                    if (fellowship.hasMember(playerID)) {
                                       if (!fellowship.isAdmin(playerID)) {
                                          ownerData.setFellowshipAdmin(fellowship, playerID, true, ownerName);
                                          func_152373_a(sender, this, "commands.lotr.fellowship.op", new Object[]{ownerName, fsName, playerName});
                                          return;
                                       }

                                       throw new WrongUsageException("commands.lotr.fellowship.edit.alreadyOp", new Object[]{ownerName, fsName, playerName});
                                    }

                                    throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[]{ownerName, fsName, playerName});
                                 }

                                 if (option.equals("deop")) {
                                    if (fellowship.hasMember(playerID)) {
                                       if (fellowship.isAdmin(playerID)) {
                                          ownerData.setFellowshipAdmin(fellowship, playerID, false, ownerName);
                                          func_152373_a(sender, this, "commands.lotr.fellowship.deop", new Object[]{ownerName, fsName, playerName});
                                          return;
                                       }

                                       throw new WrongUsageException("commands.lotr.fellowship.edit.notOp", new Object[]{ownerName, fsName, playerName});
                                    }

                                    throw new WrongUsageException("commands.lotr.fellowship.edit.notMember", new Object[]{ownerName, fsName, playerName});
                                 }
                              } else {
                                 playerName = args[4];
                                 if (playerName.equals("prevent")) {
                                    prevent = true;
                                 } else {
                                    if (!playerName.equals("allow")) {
                                       throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
                                    }

                                    prevent = false;
                                 }

                                 if (option.equals("pvp")) {
                                    ownerData.setFellowshipPreventPVP(fellowship, prevent);
                                    if (prevent) {
                                       func_152373_a(sender, this, "commands.lotr.fellowship.pvp.prevent", new Object[]{ownerName, fsName});
                                    } else {
                                       func_152373_a(sender, this, "commands.lotr.fellowship.pvp.allow", new Object[]{ownerName, fsName});
                                    }

                                    return;
                                 }

                                 if (option.equals("hired-ff")) {
                                    ownerData.setFellowshipPreventHiredFF(fellowship, prevent);
                                    if (prevent) {
                                       func_152373_a(sender, this, "commands.lotr.fellowship.hiredFF.prevent", new Object[]{ownerName, fsName});
                                    } else {
                                       func_152373_a(sender, this, "commands.lotr.fellowship.hiredFF.allow", new Object[]{ownerName, fsName});
                                    }

                                    return;
                                 }
                              }

                              throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
                           }
                        }
                     }
                  } else {
                     throw new WrongUsageException("commands.lotr.fellowship.edit.notFound", new Object[]{ownerName, fsName});
                  }
               }
            }
         } else {
            throw new PlayerNotFoundException();
         }
      } else {
         throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
      }
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (!sender.func_70003_b(this.func_82362_a(), this.func_71517_b())) {
         return null;
      } else if (args.length == 1) {
         return func_71530_a(args, new String[]{"create", "option"});
      } else if (args.length == 2) {
         return func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z());
      } else {
         if (args.length > 2) {
            String function = args[0];
            if (function.equals("create")) {
               return null;
            }

            if (function.equals("option")) {
               String[] argsOriginal = (String[])Arrays.copyOf(args, args.length);
               args = fixArgsForFellowship(args, 2, true);
               String ownerName = args[1];
               UUID ownerID = this.getPlayerIDByName(sender, ownerName);
               if (ownerID != null) {
                  LOTRPlayerData playerData = LOTRLevelData.getData(ownerID);
                  String fsName = args[2];
                  if (args.length == 3) {
                     return listFellowshipsMatchingLastWord(args, argsOriginal, 2, playerData, true);
                  }

                  if (fsName != null) {
                     LOTRFellowship fellowship = playerData.getFellowshipByName(fsName);
                     if (fellowship != null) {
                        if (args.length == 4) {
                           return func_71530_a(args, new String[]{"invite", "add", "remove", "transfer", "op", "deop", "disband", "rename", "icon", "pvp", "hired-ff", "map-show"});
                        }

                        String option = args[3];
                        ArrayList adminNames;
                        if (option.equals("invite") || option.equals("add")) {
                           adminNames = new ArrayList();
                           GameProfile[] players = MinecraftServer.func_71276_C().func_71203_ab().func_152600_g();
                           GameProfile[] var19 = players;
                           int var20 = players.length;

                           for(int var15 = 0; var15 < var20; ++var15) {
                              GameProfile playerProfile = var19[var15];
                              UUID playerID = playerProfile.getId();
                              if (!fellowship.containsPlayer(playerID)) {
                                 adminNames.add(playerProfile.getName());
                              }
                           }

                           return func_71530_a(args, (String[])adminNames.toArray(new String[0]));
                        }

                        Iterator var12;
                        UUID playerID;
                        GameProfile playerProfile;
                        if (option.equals("remove") || option.equals("transfer")) {
                           adminNames = new ArrayList();
                           var12 = fellowship.getMemberUUIDs().iterator();

                           while(var12.hasNext()) {
                              playerID = (UUID)var12.next();
                              playerProfile = MinecraftServer.func_71276_C().func_152358_ax().func_152652_a(playerID);
                              if (playerProfile != null && playerProfile.getName() != null) {
                                 adminNames.add(playerProfile.getName());
                              }
                           }

                           return func_71530_a(args, (String[])adminNames.toArray(new String[0]));
                        }

                        if (option.equals("op")) {
                           adminNames = new ArrayList();
                           var12 = fellowship.getMemberUUIDs().iterator();

                           while(var12.hasNext()) {
                              playerID = (UUID)var12.next();
                              if (!fellowship.isAdmin(playerID)) {
                                 playerProfile = MinecraftServer.func_71276_C().func_152358_ax().func_152652_a(playerID);
                                 if (playerProfile != null && playerProfile.getName() != null) {
                                    adminNames.add(playerProfile.getName());
                                 }
                              }
                           }

                           return func_71530_a(args, (String[])adminNames.toArray(new String[0]));
                        }

                        if (option.equals("deop")) {
                           adminNames = new ArrayList();
                           var12 = fellowship.getMemberUUIDs().iterator();

                           while(var12.hasNext()) {
                              playerID = (UUID)var12.next();
                              if (fellowship.isAdmin(playerID)) {
                                 playerProfile = MinecraftServer.func_71276_C().func_152358_ax().func_152652_a(playerID);
                                 if (playerProfile != null && playerProfile.getName() != null) {
                                    adminNames.add(playerProfile.getName());
                                 }
                              }
                           }

                           return func_71530_a(args, (String[])adminNames.toArray(new String[0]));
                        }

                        if (option.equals("pvp") || option.equals("hired-ff")) {
                           return func_71530_a(args, new String[]{"prevent", "allow"});
                        }

                        if (option.equals("map-show")) {
                           return func_71530_a(args, new String[]{"on", "off"});
                        }
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   public static List listFellowshipsMatchingLastWord(String[] argsFixed, String[] argsOriginal, int fsNameIndex, LOTRPlayerData playerData, boolean leadingOnly) {
      String fsName = argsFixed[fsNameIndex];
      List allFellowshipNames = leadingOnly ? playerData.listAllLeadingFellowshipNames() : playerData.listAllFellowshipNames();
      List autocompletes = new ArrayList();
      Iterator var8 = allFellowshipNames.iterator();

      while(true) {
         String autocompFsName;
         do {
            if (!var8.hasNext()) {
               return func_71530_a(argsOriginal, (String[])autocompletes.toArray(new String[0]));
            }

            String nextFsName = (String)var8.next();
            autocompFsName = "\"" + nextFsName + "\"";
         } while(!autocompFsName.toLowerCase().startsWith(fsName.toLowerCase()));

         if (argsOriginal.length > argsFixed.length) {
            int diff = argsOriginal.length - argsFixed.length;

            for(int j = 0; j < diff; ++j) {
               autocompFsName = autocompFsName.substring(autocompFsName.indexOf(" ") + 1);
            }
         }

         if (autocompFsName.indexOf(" ") >= 0) {
            autocompFsName = autocompFsName.substring(0, autocompFsName.indexOf(" "));
         }

         autocompletes.add(autocompFsName);
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      if (args.length >= 5 && args[0].equals("option")) {
         String option = args[3];
         if (option.equals("invite") || option.equals("add") || option.equals("remove") || option.equals("transfer")) {
            return i == 4;
         }
      }

      return false;
   }
}
