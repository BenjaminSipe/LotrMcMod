package lotr.common.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class LOTRCommandEnchant extends CommandBase {
   public String func_71517_b() {
      return "lotrEnchant";
   }

   public int func_82362_a() {
      return 2;
   }

   public String func_71518_a(ICommandSender sender) {
      return "commands.lotr.lotrEnchant.usage";
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 2) {
         EntityPlayerMP entityplayer = func_82359_c(sender, args[0]);
         ItemStack itemstack = entityplayer.func_70694_bm();
         if (itemstack == null) {
            throw new WrongUsageException("commands.lotr.lotrEnchant.noItem", new Object[0]);
         }

         String option = args[1];
         String enchName;
         LOTREnchantment ench;
         if (option.equals("add") && args.length >= 3) {
            enchName = args[2];
            ench = LOTREnchantment.getEnchantmentByName(enchName);
            if (ench != null) {
               if (!LOTREnchantmentHelper.hasEnchant(itemstack, ench) && ench.canApply(itemstack, false) && LOTREnchantmentHelper.checkEnchantCompatible(itemstack, ench)) {
                  LOTREnchantmentHelper.setHasEnchant(itemstack, ench);
                  func_152373_a(sender, this, "commands.lotr.lotrEnchant.add", new Object[]{enchName, entityplayer.func_70005_c_(), itemstack.func_82833_r()});
                  return;
               }

               throw new WrongUsageException("commands.lotr.lotrEnchant.cannotAdd", new Object[]{enchName, itemstack.func_82833_r()});
            }

            throw new WrongUsageException("commands.lotr.lotrEnchant.unknown", new Object[]{enchName});
         }

         if (option.equals("remove") && args.length >= 3) {
            enchName = args[2];
            ench = LOTREnchantment.getEnchantmentByName(enchName);
            if (ench != null) {
               if (LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                  LOTREnchantmentHelper.removeEnchant(itemstack, ench);
                  func_152373_a(sender, this, "commands.lotr.lotrEnchant.remove", new Object[]{enchName, entityplayer.func_70005_c_(), itemstack.func_82833_r()});
                  return;
               }

               throw new WrongUsageException("commands.lotr.lotrEnchant.cannotRemove", new Object[]{enchName, itemstack.func_82833_r()});
            }

            throw new WrongUsageException("commands.lotr.lotrEnchant.unknown", new Object[]{enchName});
         }

         if (option.equals("clear")) {
            LOTREnchantmentHelper.clearEnchantsAndProgress(itemstack);
            func_152373_a(sender, this, "commands.lotr.lotrEnchant.clear", new Object[]{entityplayer.func_70005_c_(), itemstack.func_82833_r()});
            return;
         }
      }

      throw new WrongUsageException(this.func_71518_a(sender), new Object[0]);
   }

   public List func_71516_a(ICommandSender sender, String[] args) {
      if (args.length == 1) {
         return func_71530_a(args, MinecraftServer.func_71276_C().func_71213_z());
      } else if (args.length == 2) {
         return func_71530_a(args, new String[]{"add", "remove", "clear"});
      } else {
         if (args.length == 3) {
            EntityPlayerMP entityplayer;
            ItemStack itemstack;
            ArrayList enchNames;
            Iterator var6;
            LOTREnchantment ench;
            if (args[1].equals("add")) {
               entityplayer = func_82359_c(sender, args[0]);
               itemstack = entityplayer.func_70694_bm();
               if (itemstack != null) {
                  enchNames = new ArrayList();
                  var6 = LOTREnchantment.allEnchantments.iterator();

                  while(var6.hasNext()) {
                     ench = (LOTREnchantment)var6.next();
                     if (!LOTREnchantmentHelper.hasEnchant(itemstack, ench) && ench.canApply(itemstack, false) && LOTREnchantmentHelper.checkEnchantCompatible(itemstack, ench)) {
                        enchNames.add(ench.enchantName);
                     }
                  }

                  return func_71530_a(args, (String[])enchNames.toArray(new String[0]));
               }
            } else if (args[1].equals("remove")) {
               entityplayer = func_82359_c(sender, args[0]);
               itemstack = entityplayer.func_70694_bm();
               if (itemstack != null) {
                  enchNames = new ArrayList();
                  var6 = LOTREnchantment.allEnchantments.iterator();

                  while(var6.hasNext()) {
                     ench = (LOTREnchantment)var6.next();
                     if (LOTREnchantmentHelper.hasEnchant(itemstack, ench)) {
                        enchNames.add(ench.enchantName);
                     }
                  }

                  return func_71530_a(args, (String[])enchNames.toArray(new String[0]));
               }
            }
         }

         return null;
      }
   }

   public boolean func_82358_a(String[] args, int i) {
      return i == 1;
   }
}
