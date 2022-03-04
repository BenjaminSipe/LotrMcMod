package lotr.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import javax.annotation.Nullable;
import lotr.common.dispenser.DispensePlate;
import lotr.common.entity.projectile.ThrownPlateEntity;
import lotr.common.init.LOTRMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

public class PlateItem extends LOTRArmorItem {
   private final BlockItem internalBlockItem;

   public PlateItem(Block block, Properties properties) {
      super(LOTRMaterial.COSMETIC, EquipmentSlotType.HEAD, properties);
      this.internalBlockItem = new BlockItem(block, properties);
      DispenserBlock.func_199774_a(this, new DispensePlate());
      GameData.getBlockItemMap().put(block, this);
   }

   public Block getBlock() {
      return this.internalBlockItem.func_179223_d();
   }

   public String func_77658_a() {
      return this.internalBlockItem.func_77658_a();
   }

   public void func_150895_a(ItemGroup group, NonNullList items) {
      this.internalBlockItem.func_150895_a(group, items);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_77624_a(ItemStack stack, @Nullable World world, List tooltip, ITooltipFlag flag) {
      this.internalBlockItem.func_77624_a(stack, world, tooltip, flag);
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      return this.internalBlockItem.func_195939_a(context);
   }

   public int getItemStackLimit(ItemStack stack) {
      return 64;
   }

   public Multimap func_111205_h(EquipmentSlotType slot) {
      Multimap map = HashMultimap.create(super.func_111205_h(slot));
      map.removeAll(Attributes.field_233826_i_);
      map.removeAll(Attributes.field_233827_j_);
      return map;
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (!world.field_72995_K) {
         ThrownPlateEntity plate = new ThrownPlateEntity(world, heldItem, player);
         plate.setThrownRetrograde(hand == Hand.OFF_HAND);
         plate.func_234612_a_(player, player.field_70125_A, player.field_70177_z, 0.0F, 1.5F, 1.0F);
         world.func_217376_c(plate);
      }

      world.func_184148_a((PlayerEntity)null, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), SoundEvents.field_187797_fA, SoundCategory.NEUTRAL, 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));
      player.func_71029_a(Stats.field_75929_E.func_199076_b(this));
      if (!player.field_71075_bZ.field_75098_d) {
         heldItem.func_190918_g(1);
      }

      return ActionResult.func_226248_a_(heldItem);
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
      return PlayerContainer.field_226615_c_.toString();
   }
}
