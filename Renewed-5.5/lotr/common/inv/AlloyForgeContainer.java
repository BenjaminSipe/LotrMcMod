package lotr.common.inv;

import lotr.common.init.LOTRContainers;
import lotr.common.tileentity.AbstractAlloyForgeTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;

public class AlloyForgeContainer extends AbstractAlloyForgeContainer {
   public AlloyForgeContainer(int id, PlayerInventory inv, PacketBuffer extraData) {
      super((ContainerType)LOTRContainers.ALLOY_FORGE.get(), id, inv, unpackForge(inv, extraData));
   }

   public AlloyForgeContainer(int id, PlayerInventory inv, AbstractAlloyForgeTileEntity forge, IIntArray data) {
      super((ContainerType)LOTRContainers.ALLOY_FORGE.get(), id, inv, forge, data);
   }

   private static AbstractAlloyForgeTileEntity unpackForge(PlayerInventory inv, PacketBuffer extraData) {
      return (AbstractAlloyForgeTileEntity)inv.field_70458_d.field_70170_p.func_175625_s(extraData.func_179259_c());
   }
}
