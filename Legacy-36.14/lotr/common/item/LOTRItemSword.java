package lotr.common.item;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.UUID;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRItemSword extends ItemSword {
   @SideOnly(Side.CLIENT)
   public IIcon glowingIcon;
   private boolean isElvenBlade;
   protected float lotrWeaponDamage;

   public LOTRItemSword(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemSword(ToolMaterial material) {
      super(material);
      this.isElvenBlade = false;
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      this.lotrWeaponDamage = material.func_78000_c() + 4.0F;
   }

   public LOTRItemSword addWeaponDamage(float f) {
      this.lotrWeaponDamage += f;
      return this;
   }

   public float getLOTRWeaponDamage() {
      return this.lotrWeaponDamage;
   }

   public LOTRItemSword setIsElvenBlade() {
      this.isElvenBlade = true;
      return this;
   }

   public boolean isElvenBlade() {
      return this.isElvenBlade;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      super.func_94581_a(iconregister);
      if (this.isElvenBlade) {
         this.glowingIcon = iconregister.func_94245_a(this.func_111208_A() + "_glowing");
      }

   }

   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
      return this.func_77661_b(itemstack) == EnumAction.none ? itemstack : super.func_77659_a(itemstack, world, entityplayer);
   }

   public Multimap func_111205_h() {
      Multimap multimap = super.func_111205_h();
      multimap.removeAll(SharedMonsterAttributes.field_111264_e.func_111108_a());
      multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "LOTR Weapon modifier", (double)this.lotrWeaponDamage, 0));
      return multimap;
   }

   public static UUID accessWeaponDamageModifier() {
      return field_111210_e;
   }
}
