package lotr.common.entity.npc;

import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.ai.goal.FriendlyNPCConversationGoal;
import lotr.common.entity.npc.ai.goal.HobbitSmokeGoal;
import lotr.common.entity.npc.ai.goal.NPCDrinkGoal;
import lotr.common.entity.npc.ai.goal.NPCEatGoal;
import lotr.common.entity.npc.ai.goal.TalkToCurrentGoal;
import lotr.common.entity.npc.ai.goal.WatchSunriseSunsetGoal;
import lotr.common.entity.npc.data.NPCFoodPool;
import lotr.common.entity.npc.data.NPCFoodPools;
import lotr.common.entity.npc.data.name.NPCNameGenerator;
import lotr.common.entity.npc.data.name.NPCNameGenerators;
import lotr.common.init.LOTRAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class HobbitEntity extends AbstractMannishEntity {
   public HobbitEntity(EntityType type, World w) {
      super(type, w);
   }

   protected NPCNameGenerator getNameGenerator() {
      return NPCNameGenerators.HOBBIT;
   }

   public static MutableAttribute regAttrs() {
      return NPCEntity.registerBaseNPCAttributes().func_233815_a_(Attributes.field_233818_a_, 16.0D).func_233815_a_(Attributes.field_233821_d_, 0.2D).func_233815_a_((Attribute)LOTRAttributes.NPC_CONVERSATION_RANGE.get(), 6.0D);
   }

   protected void addNPCAI() {
      super.addNPCAI();
      this.field_70714_bg.func_75776_a(0, new SwimGoal(this));
      this.field_70714_bg.func_75776_a(1, new AvoidEntityGoal(this, OrcEntity.class, 12.0F, 1.5D, 1.8D));
      this.field_70714_bg.func_75776_a(2, new PanicGoal(this, 1.6D));
      this.field_70714_bg.func_75776_a(9, new OpenDoorGoal(this, true));
      this.field_70714_bg.func_75776_a(10, new TalkToCurrentGoal(this));
      this.field_70714_bg.func_75776_a(11, new FriendlyNPCConversationGoal(this, 0.001F));
      this.field_70714_bg.func_75776_a(12, new WatchSunriseSunsetGoal(this, 0.01F));
      this.field_70714_bg.func_75776_a(13, new WaterAvoidingRandomWalkingGoal(this, 1.1D));
      this.field_70714_bg.func_75776_a(14, new NPCEatGoal(this, this.getEatPool(), 3000));
      this.field_70714_bg.func_75776_a(14, new NPCDrinkGoal(this, this.getDrinkPool(), 3000));
      this.field_70714_bg.func_75776_a(14, new HobbitSmokeGoal(this, 4000));
      this.field_70714_bg.func_75776_a(15, new LookAtGoal(this, PlayerEntity.class, 8.0F, 0.05F));
      this.field_70714_bg.func_75776_a(15, new LookAtGoal(this, NPCEntity.class, 5.0F, 0.05F));
      this.field_70714_bg.func_75776_a(16, new LookAtGoal(this, LivingEntity.class, 8.0F, 0.02F));
      this.field_70714_bg.func_75776_a(17, new LookRandomlyGoal(this));
   }

   protected NPCFoodPool getEatPool() {
      return NPCFoodPools.HOBBIT;
   }

   protected NPCFoodPool getDrinkPool() {
      return NPCFoodPools.HOBBIT_DRINK;
   }

   public boolean useSmallArmsModel() {
      return (Boolean)LOTRConfig.CLIENT.hobbitWomenUseAlexModelStyle.get() && this.personalInfo.isFemale();
   }
}
