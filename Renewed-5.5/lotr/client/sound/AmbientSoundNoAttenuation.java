package lotr.client.sound;

import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AmbientSoundNoAttenuation extends SimpleSound {
   public AmbientSoundNoAttenuation(SoundEvent evt, SoundCategory cat, float vol, float pit, BlockPos pos) {
      super(evt, cat, vol, pit, pos);
      this.field_147666_i = AttenuationType.NONE;
   }

   public AmbientSoundNoAttenuation modifyAmbientVolume(PlayerEntity player, int maxRange) {
      float distFr = MathHelper.func_76133_a(player.func_70092_e(this.field_147660_d, this.field_147661_e, this.field_147658_f));
      distFr /= (float)maxRange;
      distFr = Math.min(distFr, 1.0F);
      distFr = 1.0F - distFr;
      distFr *= 1.5F;
      distFr = MathHelper.func_76131_a(distFr, 0.1F, 1.0F);
      this.field_147662_b *= distFr;
      return this;
   }
}
