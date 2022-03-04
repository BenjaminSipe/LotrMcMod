package lotr.client;

import com.google.common.collect.Lists;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotr.client.gui.LOTRGuiAchievementHoverEvent;
import lotr.client.gui.LOTRGuiAnvil;
import lotr.client.gui.LOTRGuiBarrel;
import lotr.client.gui.LOTRGuiButtonLock;
import lotr.client.gui.LOTRGuiButtonRestockPouch;
import lotr.client.gui.LOTRGuiChestWithPouch;
import lotr.client.gui.LOTRGuiDownloadTerrain;
import lotr.client.gui.LOTRGuiMainMenu;
import lotr.client.gui.LOTRGuiPouch;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRChatEvents;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRModInfo;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.inventory.LOTRContainerCoinExchange;
import lotr.common.item.LOTRItemCoin;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMountOpenInv;
import lotr.common.network.LOTRPacketRestockPouches;
import lotr.common.world.LOTRWorldProvider;
import lotr.compatibility.LOTRModChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiHandler {
   private static RenderItem itemRenderer = new RenderItem();
   public static boolean coinCountLeftSide = false;
   public static final Set coinCount_excludedContainers = new HashSet();
   public static final Set coinCount_excludedGUIs = new HashSet();
   public static final Set coinCount_excludedInvTypes = new HashSet();
   public static final Set coinCount_excludedContainers_clsNames = new HashSet();
   public static final Set coinCount_excludedGUIs_clsNames = new HashSet();
   public static final Set coinCount_excludedInvTypes_clsNames = new HashSet();
   public static final Set pouchRestock_leftPositionGUIs = new HashSet();
   public static final Set pouchRestock_sidePositionGUIs = new HashSet();
   private int descScrollIndex = -1;

   public LOTRGuiHandler() {
      FMLCommonHandler.instance().bus().register(this);
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onGuiOpen(GuiOpenEvent event) {
      GuiScreen gui = event.gui;
      if (LOTRConfig.customMainMenu && gui != null && gui.getClass() == GuiMainMenu.class) {
         gui = new LOTRGuiMainMenu();
         event.gui = (GuiScreen)gui;
      }

      if (gui != null && gui.getClass() == GuiDownloadTerrain.class) {
         Minecraft mc = Minecraft.func_71410_x();
         WorldProvider provider = mc.field_71441_e.field_73011_w;
         if (provider instanceof LOTRWorldProvider) {
            GuiScreen gui = new LOTRGuiDownloadTerrain(mc.field_71439_g.field_71174_a);
            event.gui = gui;
         }
      }

   }

   @SubscribeEvent
   public void preInitGui(Pre event) {
      GuiScreen gui = event.gui;
      Minecraft mc = Minecraft.func_71410_x();
      EntityClientPlayerMP entityplayer = mc.field_71439_g;
      World world = mc.field_71441_e;
      if ((gui instanceof GuiInventory || gui instanceof GuiContainerCreative) && entityplayer != null && world != null && entityplayer.field_70154_o instanceof LOTREntityNPCRideable) {
         LOTREntityNPCRideable mount = (LOTREntityNPCRideable)entityplayer.field_70154_o;
         if (mount.getMountInventory() != null) {
            entityplayer.func_71053_j();
            LOTRPacketMountOpenInv packet = new LOTRPacketMountOpenInv();
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
            event.setCanceled(true);
            return;
         }
      }

   }

   @SubscribeEvent
   public void postInitGui(Post event) {
      GuiScreen gui = event.gui;
      List buttons = event.buttonList;
      if (gui instanceof GuiOptions) {
         GuiButton buttonDifficulty = this.getDifficultyButton((GuiOptions)gui, buttons);
         if (buttonDifficulty != null) {
            GuiButton lock = new LOTRGuiButtonLock(1000000, buttonDifficulty.field_146128_h + buttonDifficulty.field_146120_f + 4, buttonDifficulty.field_146129_i);
            lock.field_146124_l = !LOTRLevelData.isDifficultyLocked();
            buttons.add(lock);
            buttonDifficulty.field_146124_l = !LOTRLevelData.isDifficultyLocked();
         }
      }

      this.addPouchRestockButton(gui, buttons);
   }

   private void addPouchRestockButton(GuiScreen gui, List buttons) {
      if (gui instanceof GuiContainer && !(gui instanceof LOTRGuiPouch) && !(gui instanceof LOTRGuiChestWithPouch)) {
         GuiContainer guiContainer = (GuiContainer)gui;
         EntityPlayer thePlayer = guiContainer.field_146297_k.field_71439_g;
         InventoryPlayer playerInv = thePlayer.field_71071_by;
         boolean containsPlayer = false;
         Slot topRightPlayerSlot = null;
         Slot topLeftPlayerSlot = null;
         Container container = guiContainer.field_147002_h;
         Iterator var10 = container.field_75151_b.iterator();

         while(var10.hasNext()) {
            Object obj = var10.next();
            Slot slot = (Slot)obj;
            boolean acceptableSlotIndex = slot.getSlotIndex() < playerInv.field_70462_a.length;
            if (gui instanceof GuiContainerCreative) {
               acceptableSlotIndex = slot.getSlotIndex() >= 9;
            }

            if (slot.field_75224_c == playerInv && acceptableSlotIndex) {
               containsPlayer = true;
               boolean isTopRight = false;
               if (topRightPlayerSlot == null) {
                  isTopRight = true;
               } else if (slot.field_75221_f < topRightPlayerSlot.field_75221_f) {
                  isTopRight = true;
               } else if (slot.field_75221_f == topRightPlayerSlot.field_75221_f && slot.field_75223_e > topRightPlayerSlot.field_75223_e) {
                  isTopRight = true;
               }

               if (isTopRight) {
                  topRightPlayerSlot = slot;
               }

               boolean isTopLeft = false;
               if (topLeftPlayerSlot == null) {
                  isTopLeft = true;
               } else if (slot.field_75221_f < topLeftPlayerSlot.field_75221_f) {
                  isTopLeft = true;
               } else if (slot.field_75221_f == topLeftPlayerSlot.field_75221_f && slot.field_75223_e < topLeftPlayerSlot.field_75223_e) {
                  isTopLeft = true;
               }

               if (isTopLeft) {
                  topLeftPlayerSlot = slot;
               }
            }
         }

         if (containsPlayer) {
            int guiLeft = LOTRReflectionClient.getGuiLeft(guiContainer);
            int guiTop = LOTRReflectionClient.getGuiTop(guiContainer);
            int guiXSize = LOTRReflectionClient.getGuiXSize(guiContainer);
            int buttonX = topRightPlayerSlot.field_75223_e + 7;
            int buttonY = topRightPlayerSlot.field_75221_f - 14;
            if (pouchRestock_leftPositionGUIs.contains(gui.getClass())) {
               buttonX = topLeftPlayerSlot.field_75223_e - 1;
               buttonY = topLeftPlayerSlot.field_75221_f - 14;
            } else if (pouchRestock_sidePositionGUIs.contains(gui.getClass())) {
               buttonX = topRightPlayerSlot.field_75223_e + 21;
               buttonY = topRightPlayerSlot.field_75221_f - 1;
            }

            if (LOTRModChecker.hasNEI() && guiContainer instanceof InventoryEffectRenderer && LOTRReflectionClient.hasGuiPotionEffects((InventoryEffectRenderer)guiContainer)) {
               buttonX -= 60;
            }

            buttons.add(new LOTRGuiButtonRestockPouch(guiContainer, 2000, guiLeft + buttonX, guiTop + buttonY));
         }
      }

   }

   @SubscribeEvent
   public void postActionPerformed(net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post event) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = event.gui;
      List buttons = event.buttonList;
      GuiButton button = event.button;
      if (gui instanceof GuiOptions && button instanceof LOTRGuiButtonLock && button.field_146124_l && mc.func_71356_B()) {
         LOTRLevelData.setSavedDifficulty(mc.field_71474_y.field_74318_M);
         LOTRLevelData.setDifficultyLocked(true);
         button.field_146124_l = false;
         GuiButton buttonDifficulty = this.getDifficultyButton((GuiOptions)gui, buttons);
         if (buttonDifficulty != null) {
            buttonDifficulty.field_146124_l = false;
         }
      }

      if (button instanceof LOTRGuiButtonRestockPouch && button.field_146124_l) {
         LOTRPacketRestockPouches packet = new LOTRPacketRestockPouches();
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   private GuiButton getDifficultyButton(GuiOptions gui, List buttons) {
      Iterator var3 = buttons.iterator();

      while(var3.hasNext()) {
         Object obj = var3.next();
         if (obj instanceof GuiOptionButton) {
            GuiOptionButton button = (GuiOptionButton)obj;
            if (button.func_146136_c() == Options.DIFFICULTY) {
               return button;
            }
         }
      }

      return null;
   }

   @SubscribeEvent
   public void preDrawScreen(net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Pre event) {
      Minecraft mc = Minecraft.func_71410_x();
      GuiScreen gui = event.gui;
      int mouseX = event.mouseX;
      int mouseY = event.mouseY;
      if (gui instanceof GuiModList) {
         ModContainer mod = LOTRMod.getModContainer();
         ModMetadata meta = mod.getMetadata();
         if (this.descScrollIndex == -1) {
            meta.description = LOTRModInfo.concatenateDescription(0);
            this.descScrollIndex = 0;
         }

         while(Mouse.next()) {
            int dwheel = Mouse.getEventDWheel();
            if (dwheel != 0) {
               int scroll = Integer.signum(dwheel);
               this.descScrollIndex -= scroll;
               this.descScrollIndex = MathHelper.func_76125_a(this.descScrollIndex, 0, LOTRModInfo.description.length - 1);
               meta.description = LOTRModInfo.concatenateDescription(this.descScrollIndex);
            }
         }
      }

      if (gui instanceof GuiContainer && LOTRConfig.displayCoinCounts) {
         mc.field_71441_e.field_72984_F.func_76320_a("invCoinCount");
         GuiContainer guiContainer = (GuiContainer)gui;
         Container container = guiContainer.field_147002_h;
         Class containerCls = container.getClass();
         Class guiCls = guiContainer.getClass();
         boolean excludeContainer = coinCount_excludedContainers.contains(containerCls) || coinCount_excludedContainers_clsNames.contains(containerCls.getName());
         boolean excludeGui = coinCount_excludedGUIs.contains(guiCls) || coinCount_excludedGUIs_clsNames.contains(guiCls.getName());
         int guiLeft;
         if (guiContainer instanceof GuiContainerCreative) {
            guiLeft = LOTRReflectionClient.getCreativeTabIndex((GuiContainerCreative)guiContainer);
            if (guiLeft != CreativeTabs.field_78036_m.func_78021_a()) {
               excludeGui = true;
            }
         }

         if (!excludeContainer && !excludeGui) {
            guiLeft = -1;
            int guiTop = -1;
            int guiXSize = -1;
            List differentInvs = new ArrayList();
            Map invHighestY = new HashMap();

            int highestY;
            for(int i = 0; i < container.field_75151_b.size(); ++i) {
               Slot slot = container.func_75139_a(i);
               IInventory inv = slot.field_75224_c;
               if (inv != null) {
                  Class invClass = inv.getClass();
                  boolean excludeInv = coinCount_excludedInvTypes.contains(invClass) || coinCount_excludedInvTypes_clsNames.contains(invClass.getName());
                  if (!excludeInv) {
                     if (!differentInvs.contains(inv)) {
                        differentInvs.add(inv);
                     }

                     int slotY = slot.field_75221_f;
                     if (!invHighestY.containsKey(inv)) {
                        invHighestY.put(inv, slotY);
                     } else {
                        highestY = (Integer)invHighestY.get(inv);
                        if (slotY < highestY) {
                           invHighestY.put(inv, slotY);
                        }
                     }
                  }
               }
            }

            Iterator var38 = differentInvs.iterator();

            while(var38.hasNext()) {
               IInventory inv = (IInventory)var38.next();
               int coins = LOTRItemCoin.getContainerValue(inv, true);
               if (coins > 0) {
                  String sCoins = String.valueOf(coins);
                  int sCoinsW = mc.field_71466_p.func_78256_a(sCoins);
                  int border = 2;
                  highestY = 18 + sCoinsW + 1;
                  if (guiLeft == -1) {
                     guiTop = LOTRReflectionClient.getGuiTop(guiContainer);
                     guiXSize = LOTRReflectionClient.getGuiXSize(guiContainer);
                     guiLeft = gui.field_146294_l / 2 - guiXSize / 2;
                     if (guiContainer instanceof InventoryEffectRenderer && LOTRReflectionClient.hasGuiPotionEffects((InventoryEffectRenderer)gui)) {
                        int potionExtraX = true;
                        if (!LOTRModChecker.hasNEI()) {
                           guiLeft += 60;
                        }
                     }
                  }

                  int guiGap = 8;
                  int x = guiLeft + guiXSize + guiGap;
                  if (coinCountLeftSide) {
                     x = guiLeft - guiGap;
                     x -= highestY;
                  }

                  int y = (Integer)invHighestY.get(inv) + guiTop;
                  int rectX0 = x - border;
                  int rectX1 = x + highestY + border;
                  int rectY0 = y - border;
                  int rectY1 = y + 16 + border;
                  float a0 = 1.0F;
                  float a1 = 0.1F;
                  GL11.glDisable(3553);
                  GL11.glDisable(3008);
                  GL11.glShadeModel(7425);
                  GL11.glPushMatrix();
                  GL11.glTranslatef(0.0F, 0.0F, -500.0F);
                  Tessellator tessellator = Tessellator.field_78398_a;
                  tessellator.func_78382_b();
                  tessellator.func_78369_a(0.0F, 0.0F, 0.0F, a1);
                  tessellator.func_78377_a((double)rectX1, (double)rectY0, 0.0D);
                  tessellator.func_78369_a(0.0F, 0.0F, 0.0F, a0);
                  tessellator.func_78377_a((double)rectX0, (double)rectY0, 0.0D);
                  tessellator.func_78377_a((double)rectX0, (double)rectY1, 0.0D);
                  tessellator.func_78369_a(0.0F, 0.0F, 0.0F, a1);
                  tessellator.func_78377_a((double)rectX1, (double)rectY1, 0.0D);
                  tessellator.func_78381_a();
                  GL11.glPopMatrix();
                  GL11.glShadeModel(7424);
                  GL11.glEnable(3008);
                  GL11.glEnable(3553);
                  GL11.glPushMatrix();
                  GL11.glTranslatef(0.0F, 0.0F, 500.0F);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  itemRenderer.func_77015_a(mc.field_71466_p, mc.func_110434_K(), new ItemStack(LOTRMod.silverCoin), x, y);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  GL11.glDisable(2896);
                  mc.field_71466_p.func_78276_b(sCoins, x + 16 + 2, y + (16 - mc.field_71466_p.field_78288_b + 2) / 2, 16777215);
                  GL11.glPopMatrix();
                  GL11.glDisable(2896);
                  GL11.glEnable(3008);
                  GL11.glEnable(3042);
                  GL11.glDisable(2884);
               }
            }

            mc.field_71441_e.field_72984_F.func_76319_b();
         }
      }

   }

   @SubscribeEvent
   public void postDrawScreen(net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent.Post event) {
      Minecraft mc = Minecraft.func_71410_x();
      EntityPlayer entityplayer = mc.field_71439_g;
      GuiScreen gui = event.gui;
      int mouseX = event.mouseX;
      int mouseY = event.mouseY;
      if (gui instanceof GuiChat) {
         IChatComponent component = mc.field_71456_v.func_146158_b().func_146236_a(Mouse.getX(), Mouse.getY());
         if (component != null && component.func_150256_b().func_150210_i() != null) {
            HoverEvent hoverevent = component.func_150256_b().func_150210_i();
            if (hoverevent.func_150701_a() == LOTRChatEvents.SHOW_LOTR_ACHIEVEMENT) {
               LOTRGuiAchievementHoverEvent proxyGui = new LOTRGuiAchievementHoverEvent();
               proxyGui.func_146280_a(mc, gui.field_146294_l, gui.field_146295_m);

               try {
                  String unformattedText = hoverevent.func_150702_b().func_150260_c();
                  int splitIndex = unformattedText.indexOf("$");
                  String categoryName = unformattedText.substring(0, splitIndex);
                  LOTRAchievement.Category category = LOTRAchievement.categoryForName(categoryName);
                  int achievementID = Integer.parseInt(unformattedText.substring(splitIndex + 1));
                  LOTRAchievement achievement = LOTRAchievement.achievementForCategoryAndID(category, achievementID);
                  IChatComponent name = new ChatComponentTranslation("lotr.gui.achievements.hover.name", new Object[]{achievement.getAchievementChatComponent(entityplayer)});
                  IChatComponent subtitle = new ChatComponentTranslation("lotr.gui.achievements.hover.subtitle", new Object[]{achievement.getDimension().getDimensionName(), category.getDisplayName()});
                  subtitle.func_150256_b().func_150217_b(true);
                  String desc = achievement.getDescription(entityplayer);
                  ArrayList list = Lists.newArrayList(new String[]{name.func_150254_d(), subtitle.func_150254_d()});
                  list.addAll(mc.field_71466_p.func_78271_c(desc, 150));
                  proxyGui.func_146283_a(list, mouseX, mouseY);
               } catch (Exception var20) {
                  proxyGui.func_146279_a(EnumChatFormatting.RED + "Invalid LOTRAchievement!", mouseX, mouseY);
               }
            }
         }
      }

   }

   static {
      coinCount_excludedInvTypes.add(LOTRContainerCoinExchange.InventoryCoinExchangeSlot.class);
      coinCount_excludedInvTypes.add(InventoryCraftResult.class);
      coinCount_excludedInvTypes_clsNames.add("thaumcraft.common.entities.InventoryMob");
      pouchRestock_leftPositionGUIs.add(LOTRGuiAnvil.class);
      pouchRestock_leftPositionGUIs.add(GuiRepair.class);
      pouchRestock_sidePositionGUIs.add(LOTRGuiBarrel.class);
   }
}
