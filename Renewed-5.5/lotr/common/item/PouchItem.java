package lotr.common.item;

import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import lotr.common.LOTRMod;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.LOTRContainers;
import lotr.common.init.LOTRItemGroups;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.inv.OpenPouchContainer;
import lotr.common.inv.PouchContainer;
import lotr.common.inv.PouchInventory;
import lotr.common.stat.LOTRStats;
import lotr.common.util.PlayerInventorySlotsHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class PouchItem extends Item {
   public static final Set ALL_POUCH_ITEMS = new HashSet();
   public static final Map POUCHES_BY_CAPACITY = new HashMap();
   private static final int DEFAULT_POUCH_COLOR = 10841676;
   public static final int MAX_NAME_LENGTH = 64;
   private final int capacity;
   private final SoundEvent openSound;
   private final SoundEvent closeSound;

   public PouchItem(Properties properties, int capacity, SoundEvent openSound, SoundEvent closeSound) {
      super(properties);
      this.capacity = capacity;
      this.openSound = openSound;
      this.closeSound = closeSound;
      ALL_POUCH_ITEMS.add(this);
      if (POUCHES_BY_CAPACITY.containsKey(capacity)) {
         throw new IllegalArgumentException(String.format("Tried to add a new pouch item with capacity %d, but a pouch item with that capacity already exists - %s", capacity, ((PouchItem)POUCHES_BY_CAPACITY.get(capacity)).getCapacity()));
      } else {
         POUCHES_BY_CAPACITY.put(capacity, this);
      }
   }

   public PouchItem(int capacity, SoundEvent openSound, SoundEvent closeSound) {
      this((new Properties()).func_200917_a(1).func_200916_a(LOTRItemGroups.MISC), capacity, openSound, closeSound);
   }

   public int getCapacity() {
      return this.capacity;
   }

   public SoundEvent getOpenSound() {
      return this.openSound;
   }

   public SoundEvent getCloseSound() {
      return this.closeSound;
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (!world.field_72995_K) {
         ContainerType containerType = (ContainerType)LOTRContainers.POUCH.get();
         ITextComponent containerTitle = heldItem.func_200301_q();
         int invSlot = PlayerInventorySlotsHelper.getHandHeldItemIndex(player, hand);
         PacketBuffer initData = new PacketBuffer(Unpooled.buffer());
         PouchContainer.writeContainerInitData(initData, invSlot);
         NetworkHooks.openGui((ServerPlayerEntity)player, new SimpleNamedContainerProvider((i, inv, p) -> {
            return containerType.create(i, inv, initData);
         }, containerTitle), (buf) -> {
            PouchContainer.writeContainerInitData(buf, invSlot);
         });
         player.func_195066_a(LOTRStats.OPEN_POUCH);
         world.func_184148_a((PlayerEntity)null, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), this.getOpenSound(), SoundCategory.PLAYERS, 1.0F, 1.0F);
      }

      return ActionResult.func_226248_a_(heldItem);
   }

   public static CompoundNBT getPouchRootNBT(ItemStack stack) {
      return stack.func_179543_a("Pouch");
   }

   public static CompoundNBT getOrCreatePouchRootNBT(ItemStack stack) {
      return stack.func_190925_c("Pouch");
   }

   public static int getPouchColor(ItemStack stack) {
      int dye = getSavedDyeColor(stack);
      return dye != -1 ? dye : 10841676;
   }

   private static int getSavedDyeColor(ItemStack stack) {
      CompoundNBT nbt = getPouchRootNBT(stack);
      return nbt != null && nbt.func_150297_b("Color", 3) ? nbt.func_74762_e("Color") : -1;
   }

   public static boolean isPouchDyed(ItemStack stack) {
      return getSavedDyeColor(stack) != -1;
   }

   public static void setPouchDyedByColor(ItemStack stack, int color) {
      CompoundNBT nbt = getOrCreatePouchRootNBT(stack);
      nbt.func_74768_a("Color", color);
      nbt.func_82580_o("ColorFaction");
   }

   public static void setPouchDyedByFaction(ItemStack stack, Faction faction) {
      CompoundNBT nbt = getOrCreatePouchRootNBT(stack);
      nbt.func_74768_a("Color", faction.getColor());
      nbt.func_74778_a("ColorFaction", faction.getName().toString());
   }

   public static Faction getPouchDyedByFaction(ItemStack stack, World world) {
      CompoundNBT nbt = getPouchRootNBT(stack);
      if (nbt != null && nbt.func_150297_b("ColorFaction", 8)) {
         ResourceLocation facName = new ResourceLocation(nbt.func_74779_i("ColorFaction"));
         return FactionSettingsManager.sidedInstance((IWorldReader)world).getCurrentLoadedFactions().getFactionByName(facName);
      } else {
         return null;
      }
   }

   public static void removePouchDye(ItemStack stack) {
      CompoundNBT nbt = getPouchRootNBT(stack);
      if (nbt != null) {
         nbt.func_82580_o("Color");
         nbt.func_82580_o("ColorFaction");
      }

   }

   public static boolean getPickedUpNewItems(ItemStack stack) {
      CompoundNBT nbt = getPouchRootNBT(stack);
      return nbt != null && nbt.func_74764_b("PickedUpNewItems") ? nbt.func_74767_n("PickedUpNewItems") : false;
   }

   public static void setPickedUpNewItems(ItemStack stack, boolean flag) {
      if (flag) {
         getOrCreatePouchRootNBT(stack).func_74757_a("PickedUpNewItems", true);
      } else {
         CompoundNBT nbt = getPouchRootNBT(stack);
         if (nbt != null) {
            nbt.func_82580_o("PickedUpNewItems");
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void func_77624_a(ItemStack stack, @Nullable World world, List tooltip, ITooltipFlag flag) {
      if (getPickedUpNewItems(stack)) {
         tooltip.add((new TranslationTextComponent("item.lotr.pouch.picked_up_new_items")).func_240699_a_(TextFormatting.YELLOW));
      }

      int slots = this.capacity;
      int slotsFull = this.determineSlotsFull(stack);
      tooltip.add((new TranslationTextComponent("item.lotr.pouch.slots", new Object[]{slotsFull, slots})).func_240699_a_(TextFormatting.GRAY));
      this.addShulkerBoxStyleTooltip(stack, tooltip);
      if (isPouchDyed(stack)) {
         Faction dyedByFaction = getPouchDyedByFaction(stack, world);
         if (dyedByFaction != null) {
            tooltip.add((new TranslationTextComponent("item.lotr.pouch.dyed.faction", new Object[]{dyedByFaction.getDisplayName()})).func_240699_a_(TextFormatting.GRAY));
         } else {
            tooltip.add((new TranslationTextComponent("item.lotr.pouch.dyed")).func_240699_a_(TextFormatting.GRAY));
         }
      }

   }

   private int determineSlotsFull(ItemStack pouch) {
      return this.getPouchInventoryForTooltip(pouch).getNumSlotsFull();
   }

   private PouchInventory getPouchInventoryForTooltip(ItemStack pouch) {
      PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
      if (player != null && player.field_71070_bA instanceof OpenPouchContainer) {
         OpenPouchContainer container = (OpenPouchContainer)player.field_71070_bA;
         if (container.isOpenPouch(pouch)) {
            return container.getPouchInventory();
         }
      }

      return PouchInventory.temporaryReadOnly(pouch);
   }

   private void addShulkerBoxStyleTooltip(ItemStack pouch, List tooltip) {
      TextFormatting textColor = TextFormatting.DARK_GRAY;
      PouchInventory pouchInv = this.getPouchInventoryForTooltip(pouch);
      int listed = 0;
      int total = 0;

      for(int i = 0; i < pouchInv.func_70302_i_(); ++i) {
         ItemStack stack = pouchInv.func_70301_a(i);
         if (!stack.func_190926_b()) {
            ++total;
            if (listed < 5) {
               ++listed;
               IFormattableTextComponent itemName = stack.func_200301_q().func_230532_e_();
               itemName.func_240702_b_(" x").func_240702_b_(String.valueOf(stack.func_190916_E()));
               tooltip.add(itemName.func_240699_a_(textColor));
            }
         }
      }

      if (total - listed > 0) {
         tooltip.add((new TranslationTextComponent("container.shulkerBox.more", new Object[]{total - listed})).func_240701_a_(new TextFormatting[]{TextFormatting.ITALIC, textColor}));
      }

   }

   public static PouchItem.AddItemResult tryAddItemToPouch(ItemStack pouch, ItemStack stack, boolean requireMatchAlreadyInPouch) {
      int stackSizeBefore = stack.func_190916_E();
      if (!stack.func_190926_b()) {
         PouchInventory pouchInv = PouchInventory.temporaryWritable(pouch);

         for(int i = 0; i < pouchInv.func_70302_i_() && !stack.func_190926_b(); ++i) {
            ItemStack itemInSlot = pouchInv.func_70301_a(i);
            if (itemInSlot.func_190926_b()) {
               if (requireMatchAlreadyInPouch) {
                  continue;
               }
            } else if (itemInSlot.func_190916_E() >= itemInSlot.func_77976_d() || itemInSlot.func_77973_b() != stack.func_77973_b() || !itemInSlot.func_77985_e() || !ItemStack.func_77970_a(itemInSlot, stack)) {
               continue;
            }

            if (itemInSlot.func_190926_b()) {
               pouchInv.func_70299_a(i, stack.func_77946_l());
               stack.func_190920_e(0);
               return PouchItem.AddItemResult.FULLY_ADDED;
            }

            int maxStackSize = Math.min(itemInSlot.func_77976_d(), pouchInv.func_70297_j_());
            int difference = maxStackSize - itemInSlot.func_190916_E();
            if (difference > stack.func_190916_E()) {
               difference = stack.func_190916_E();
            }

            stack.func_190918_g(difference);
            itemInSlot.func_190917_f(difference);
            pouchInv.func_70299_a(i, itemInSlot);
            if (stack.func_190926_b()) {
               return PouchItem.AddItemResult.FULLY_ADDED;
            }
         }
      }

      return stack.func_190916_E() < stackSizeBefore ? PouchItem.AddItemResult.SOME_ADDED : PouchItem.AddItemResult.NONE_ADDED;
   }

   public static void attemptRestockPouches(PlayerEntity player) {
      PlayerInventory inv = player.field_71071_by;
      List pouchSlots = new ArrayList();
      List itemSlots = new ArrayList();

      for(int i = 0; i < inv.field_70462_a.size(); ++i) {
         ItemStack stack = inv.func_70301_a(i);
         if (!stack.func_190926_b()) {
            if (stack.func_77973_b() instanceof PouchItem) {
               pouchSlots.add(i);
            } else {
               itemSlots.add(i);
            }
         }
      }

      boolean movedAny = false;
      Iterator var13 = itemSlots.iterator();

      while(true) {
         while(var13.hasNext()) {
            int i = (Integer)var13.next();
            ItemStack stack = inv.func_70301_a(i);
            Iterator var8 = pouchSlots.iterator();

            while(var8.hasNext()) {
               int p = (Integer)var8.next();
               ItemStack pouch = inv.func_70301_a(p);
               PouchItem.AddItemResult result = tryAddItemToPouch(pouch, stack, true);
               if (result != PouchItem.AddItemResult.NONE_ADDED) {
                  movedAny = true;
               }

               if (stack.func_190926_b()) {
                  inv.func_70299_a(i, ItemStack.field_190927_a);
                  break;
               }
            }
         }

         if (movedAny) {
            player.field_71070_bA.func_75142_b();
            player.field_70170_p.func_184148_a((PlayerEntity)null, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), LOTRSoundEvents.RESTOCK_POUCHES, SoundCategory.PLAYERS, 1.0F, 1.0F);
         }

         return;
      }
   }

   public static enum AddItemResult {
      SOME_ADDED,
      FULLY_ADDED,
      NONE_ADDED;
   }
}
