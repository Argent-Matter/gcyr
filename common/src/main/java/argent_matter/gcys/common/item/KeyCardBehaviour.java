package argent_matter.gcys.common.item;

import argent_matter.gcys.common.data.GcysItems;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class KeyCardBehaviour implements IInteractionItem {

    @Nullable
    public static UUID getOwner(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("KeyCardOwner", Tag.TAG_INT_ARRAY)) {
            return stack.getTag().getUUID("KeyCardOwner");
        }
        return null;
    }

    public static void setOwner(ItemStack stack, LivingEntity entity) {
        stack.getOrCreateTag().putUUID("KeyCardOwner", entity.getUUID());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide && !itemStack.getOrCreateTag().contains("KeyCardOwner", Tag.TAG_INT_ARRAY) && player.isCrouching()) {
            setOwner(itemStack, player);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
