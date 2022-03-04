package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import lotr.client.event.LOTRGuiHandler;
import lotr.common.item.PouchItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.tuple.Pair;

public class PouchRestockButton extends Button {
   private static final ResourceLocation TEXTURE = new ResourceLocation("lotr", "textures/gui/widgets.png");
   private static final ITextComponent TOOLTIP = new TranslationTextComponent("gui.lotr.restock_pouches");
   private final ContainerScreen parentScreen;
   private final LOTRGuiHandler.PouchRestockButtonPositioner positioner;
   private int prevContainerGuiLeft;
   private int prevContainerGuiTop;
   private int prevContainerGuiXSize;
   private int prevContainerGuiYSize;
   private boolean prevCreativeTabWasInventory;

   public PouchRestockButton(ContainerScreen parent, int xIn, int yIn, LOTRGuiHandler.PouchRestockButtonPositioner positioner, IPressable onPressIn) {
      super(xIn, yIn, 10, 10, StringTextComponent.field_240750_d_, onPressIn);
      this.parentScreen = parent;
      this.positioner = positioner;
      this.prevContainerGuiLeft = this.parentScreen.getGuiLeft();
      this.prevContainerGuiTop = this.parentScreen.getGuiTop();
      this.prevContainerGuiXSize = this.parentScreen.getXSize();
      this.prevContainerGuiYSize = this.parentScreen.getYSize();
      this.checkIsCreativeTabInventory(parent.getMinecraft());
   }

   public static Optional getRestockButtonPosition(Minecraft minecraft, ContainerScreen containerScreen, LOTRGuiHandler.PouchRestockButtonPositioner positioner) {
      PlayerEntity thePlayer = minecraft.field_71439_g;
      PlayerInventory playerInv = thePlayer.field_71071_by;
      boolean containsPlayer = false;
      Slot topRightPlayerSlot = null;
      Slot topLeftPlayerSlot = null;
      Container container = containerScreen.func_212873_a_();
      Iterator var9 = container.field_75151_b.iterator();

      while(var9.hasNext()) {
         Slot slot = (Slot)var9.next();
         if (slot.field_75224_c == playerInv) {
            int slotIndex = slot.getSlotIndex();
            boolean acceptableSlotIndex = slotIndex < playerInv.field_70462_a.size();
            if (acceptableSlotIndex) {
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
      }

      if (containsPlayer) {
         int guiLeft = containerScreen.getGuiLeft();
         int guiTop = containerScreen.getGuiTop();
         Pair buttonCoords = positioner.getButtonPosition(topLeftPlayerSlot, topRightPlayerSlot);
         buttonCoords = Pair.of(guiLeft + (Integer)buttonCoords.getLeft(), guiTop + (Integer)buttonCoords.getRight());
         return Optional.of(buttonCoords);
      } else {
         return Optional.empty();
      }
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft minecraft = Minecraft.func_71410_x();
      this.checkPouchRestockPositionAndVisibility(minecraft);
      super.func_230430_a_(matStack, mouseX, mouseY, f);
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft minecraft = Minecraft.func_71410_x();
      FontRenderer fr = minecraft.field_71466_p;
      minecraft.func_110434_K().func_110577_a(TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, 0, 128 + yOffset * this.field_230689_k_, this.field_230688_j_, this.field_230689_k_);
      this.func_230441_a_(matStack, minecraft, mouseX, mouseY);
      if (this.func_230449_g_()) {
         this.parentScreen.func_238652_a_(matStack, TOOLTIP, mouseX, mouseY);
      }

   }

   private void checkPouchRestockPositionAndVisibility(Minecraft minecraft) {
      PlayerInventory inv = minecraft.field_71439_g.field_71071_by;
      this.field_230693_o_ = this.field_230694_p_ = inv.func_213902_a(new HashSet(PouchItem.ALL_POUCH_ITEMS));
      int guiLeft = this.parentScreen.getGuiLeft();
      int guiTop = this.parentScreen.getGuiTop();
      int guiXSize = this.parentScreen.getXSize();
      int guiYSize = this.parentScreen.getYSize();
      if (guiLeft != this.prevContainerGuiLeft || guiTop != this.prevContainerGuiTop || guiXSize != this.prevContainerGuiXSize || guiYSize != this.prevContainerGuiYSize) {
         this.repositionButton(minecraft);
         this.prevContainerGuiLeft = guiLeft;
         this.prevContainerGuiTop = guiTop;
         this.prevContainerGuiXSize = guiXSize;
         this.prevContainerGuiYSize = guiYSize;
      }

      this.checkIsCreativeTabInventory(minecraft);
   }

   private void checkIsCreativeTabInventory(Minecraft minecraft) {
      if (this.parentScreen instanceof CreativeScreen) {
         int creativeTabIndex = ((CreativeScreen)this.parentScreen).func_147056_g();
         boolean creativeTabInventory = creativeTabIndex == ItemGroup.field_78036_m.func_78021_a();
         if (creativeTabInventory != this.prevCreativeTabWasInventory) {
            this.repositionButton(minecraft);
            this.prevCreativeTabWasInventory = creativeTabInventory;
         }

         if (!creativeTabInventory) {
            this.field_230693_o_ = this.field_230694_p_ = false;
         }
      }

   }

   private void repositionButton(Minecraft minecraft) {
      Optional optButtonCoords = getRestockButtonPosition(minecraft, this.parentScreen, this.positioner);
      if (optButtonCoords.isPresent()) {
         this.field_230690_l_ = (Integer)((Pair)optButtonCoords.get()).getLeft();
         this.field_230691_m_ = (Integer)((Pair)optButtonCoords.get()).getRight();
      } else {
         this.field_230693_o_ = this.field_230694_p_ = false;
      }

   }
}
