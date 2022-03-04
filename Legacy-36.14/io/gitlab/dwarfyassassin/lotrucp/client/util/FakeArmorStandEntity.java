package io.gitlab.dwarfyassassin.lotrucp.client.util;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class FakeArmorStandEntity extends EntityLivingBase {
   public static final FakeArmorStandEntity INSTANCE = new FakeArmorStandEntity();

   public FakeArmorStandEntity() {
      super(FMLClientHandler.instance().getWorldClient());
   }

   public ItemStack func_70694_bm() {
      return null;
   }

   public ItemStack func_71124_b(int p_71124_1_) {
      return null;
   }

   public void func_70062_b(int p_70062_1_, ItemStack p_70062_2_) {
   }

   public ItemStack[] func_70035_c() {
      return null;
   }
}
