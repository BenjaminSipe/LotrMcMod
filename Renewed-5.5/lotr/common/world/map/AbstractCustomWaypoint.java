package lotr.common.world.map;

import java.util.UUID;
import javax.annotation.Nullable;
import lotr.common.data.DataUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractCustomWaypoint extends VerticallyPositionedWaypoint {
   public static final int MAX_NAME_LENGTH = 40;
   public static final int MAX_LORE_LENGTH = 160;
   private final UUID createdPlayer;
   private final int id;
   private String name;
   private String lore;
   private final BlockPos worldPos;
   private final double mapX;
   private final double mapZ;

   public AbstractCustomWaypoint(MapSettings map, UUID createdPlayer, int id, String name, String lore, BlockPos worldPos) {
      this.createdPlayer = createdPlayer;
      this.id = id;
      this.name = name;
      this.lore = lore;
      this.worldPos = worldPos;
      this.mapX = map.worldToMapX_frac((double)worldPos.func_177958_n());
      this.mapZ = map.worldToMapZ_frac((double)worldPos.func_177952_p());
   }

   public UUID getCreatedPlayer() {
      return this.createdPlayer;
   }

   public int getCustomId() {
      return this.id;
   }

   public String getRawName() {
      return this.name;
   }

   protected void setName(String name) {
      this.name = name;
   }

   public ITextComponent getDisplayName() {
      return new StringTextComponent(this.name);
   }

   public String getRawLore() {
      return this.lore;
   }

   protected void setLore(String lore) {
      this.lore = lore;
   }

   @Nullable
   public ITextComponent getDisplayLore() {
      return !this.lore.isEmpty() ? new StringTextComponent(this.lore) : null;
   }

   public double getMapX() {
      return this.mapX;
   }

   public double getMapZ() {
      return this.mapZ;
   }

   public int getWorldX() {
      return this.worldPos.func_177958_n();
   }

   public int getWorldZ() {
      return this.worldPos.func_177952_p();
   }

   public int getWorldY() {
      return this.worldPos.func_177956_o();
   }

   public BlockPos getPosition() {
      return this.worldPos;
   }

   public BlockPos getTravelPosition(ServerWorld world, PlayerEntity player) {
      return CustomWaypointStructureHandler.INSTANCE.findRandomTravelPositionForCompletedWaypoint(world, this, player);
   }

   public boolean verifyFastTravellable(ServerWorld world, PlayerEntity player) {
      return CustomWaypointStructureHandler.INSTANCE.checkCompletedWaypointHasMarkerAndHandleIfNot(world, this, player);
   }

   public boolean hasPlayerUnlocked(PlayerEntity player) {
      return true;
   }

   public ITextComponent getNotUnlockedMessage(PlayerEntity player) {
      return StringTextComponent.field_240750_d_;
   }

   public boolean isCustom() {
      return true;
   }

   public boolean isSharedCustom() {
      return false;
   }

   public boolean isSharedHidden() {
      return false;
   }

   public Waypoint.WaypointDisplayState getDisplayState(PlayerEntity player) {
      return Waypoint.WaypointDisplayState.CUSTOM;
   }

   protected static AbstractCustomWaypoint baseLoad(MapSettings map, CompoundNBT nbt, AbstractCustomWaypoint.AbstractCustomWaypointConstructor constructor) {
      UUID createdPlayer = DataUtil.getUniqueIdBackCompat(nbt, "CreatedPlayer");
      int id = nbt.func_74762_e("ID");
      String name = nbt.func_74779_i("Name");
      String lore = nbt.func_74779_i("Lore");
      int posX = nbt.func_74762_e("PosX");
      int posY = nbt.func_74762_e("PosY");
      int posZ = nbt.func_74762_e("PosZ");
      BlockPos worldPos = new BlockPos(posX, posY, posZ);
      return constructor.create(map, createdPlayer, id, name, lore, worldPos);
   }

   protected void save(CompoundNBT nbt) {
      nbt.func_186854_a("CreatedPlayer", this.createdPlayer);
      nbt.func_74768_a("ID", this.id);
      nbt.func_74778_a("Name", this.name);
      nbt.func_74778_a("Lore", this.lore);
      nbt.func_74768_a("PosX", this.worldPos.func_177958_n());
      nbt.func_74768_a("PosY", this.worldPos.func_177956_o());
      nbt.func_74768_a("PosZ", this.worldPos.func_177952_p());
   }

   protected static AbstractCustomWaypoint baseRead(MapSettings map, PacketBuffer buf, AbstractCustomWaypoint.AbstractCustomWaypointConstructor constructor) {
      UUID createdPlayer = buf.func_179253_g();
      int id = buf.func_150792_a();
      String name = buf.func_218666_n();
      String lore = buf.func_218666_n();
      BlockPos worldPos = buf.func_179259_c();
      return constructor.create(map, createdPlayer, id, name, lore, worldPos);
   }

   protected void write(PacketBuffer buf) {
      buf.func_179252_a(this.createdPlayer);
      buf.func_150787_b(this.id);
      buf.func_180714_a(this.name);
      buf.func_180714_a(this.lore);
      buf.func_179255_a(this.worldPos);
   }

   protected abstract void removeFromPlayerData(PlayerEntity var1);

   @FunctionalInterface
   protected interface AbstractCustomWaypointConstructor {
      AbstractCustomWaypoint create(MapSettings var1, UUID var2, int var3, String var4, String var5, BlockPos var6);
   }
}
