package net.not_assher.apostate.core.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.not_assher.apostate.core.cca.entity.PlayerComponent;
import net.not_assher.apostate.core.client.tooltip.TabletTooltipData;
import net.not_assher.apostate.core.index.ModCriteria;
import net.not_assher.apostate.core.index.ModDataComponentTypes;
import net.not_assher.apostate.core.index.tag.ModItemTags;
import net.not_assher.apostate.core.item.component.TabletComponent;
import net.not_assher.apostate.core.utilities.ModUtils;
import net.not_assher.apostate.core.utilities.records.Pact;

import java.util.Optional;

/**
 * @author Chemthunder
 */
public class DiviningTabletItem extends Item {
    public static final int MAX_USES = 5;

    public DiviningTabletItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        TabletComponent tablet = stack.getOrDefault(ModDataComponentTypes.TABLET, new TabletComponent(null, ItemStack.EMPTY));

        if (!user.getItemCooldownManager().isCoolingDown(stack)) {
            if (!tablet.isEmpty()) {
                if (tablet.hunted() != null && !tablet.ingredient().isEmpty()) {
                    PlayerEntity target = null;
                    for (PlayerEntity capture : world.getPlayers()) {
                        if (capture.getGameProfile().name().equals(tablet.hunted().getGameProfile().name())) {
                            target = capture;
                            break;
                        }
                    }

                    if (target != null) {
                        user.spawnItemParticles(stack, 15);
                        user.spawnItemParticles(tablet.ingredient(), 15);

                        ModUtils.spawnRotatedParticles(user, ParticleTypes.END_ROD, 15);

                        world.playSound(
                                null,
                                user.getBlockPos(),
                                SoundEvents.ITEM_SHIELD_BREAK.value(),
                                SoundCategory.PLAYERS,
                                0.8F,
                                0.7F
                        );

                        world.playSound(
                                null,
                                user.getBlockPos(),
                                SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK,
                                SoundCategory.PLAYERS,
                                1,
                                0.7F
                        );

                        if (user instanceof ServerPlayerEntity serverPlayer) {
                            ModCriteria.USE_TABLET.trigger(serverPlayer);
                        }

                        track(world, user, target, stack, tablet.ingredient().getItem());

                        if (!user.isCreative()) {
                            stack.set(ModDataComponentTypes.TABLET, new TabletComponent(tablet.hunted(), ItemStack.EMPTY));

                            int durability = stack.getOrDefault(ModDataComponentTypes.INTEGER, MAX_USES);

                            if (durability > 1) {
                                stack.set(ModDataComponentTypes.INTEGER, durability - 1);
                            } else {
                                stack.decrement(1);
                            }

                            user.getItemCooldownManager().set(stack, (60 * 20));
                        }
                        return ActionResult.SUCCESS;
                    } else {
                        user.sendMessage(Text.literal("The targeted player is not trackable!"), true);
                        return ActionResult.FAIL;
                    }
                }
            } else {
                user.sendMessage(Text.literal("Insert the proper ingredients to use this Divining Tablet!"), true);
                return ActionResult.FAIL;
            }
        }
        return super.use(world, user, hand);
    }

    public void track(World world, PlayerEntity player, PlayerEntity target, ItemStack stack, Item ingredient) {
        PlayerComponent playerComponent = PlayerComponent.KEY.get(player);

        if (ingredient.equals(Items.AMETHYST_SHARD)) {
            BlockPos pos = target.getBlockPos();

            player.sendMessage(
                    Text.literal(target.getName().getString()).formatted(Formatting.YELLOW)
                            .append(Text.literal(" is located at ").formatted(Formatting.DARK_GRAY))
                            .append(Text.empty()
                                    .append(Text.literal("[").formatted(Formatting.DARK_GRAY))
                                    .append(Text.literal(String.valueOf(pos.getX())).formatted(Formatting.RED))
                                    .append(Text.literal(", ").formatted(Formatting.DARK_GRAY))
                                    .append(Text.literal(String.valueOf(pos.getY())).formatted(Formatting.GREEN))
                                    .append(Text.literal("]").formatted(Formatting.DARK_GRAY))
                            ),
                    true
            );

            target.sendMessage(
                    Text.literal("Your position has been revealed to ").append(Text.literal(player.getName().getString() + "!")),
                    true
            );

            world.playSound(
                    null,
                    target.getBlockPos(),
                    SoundEvents.ENTITY_WITHER_DEATH,
                    SoundCategory.PLAYERS,
                    0.3F,
                    1
            );
        }

        if (ingredient.equals(Items.ECHO_SHARD)) {
            playerComponent.setEchoTicks(15 * 20);
            playerComponent.setTabletTarget(target.getGameProfile().name());
        }

        if (ingredient.equals(Items.EMERALD)) {
            playerComponent.setEmeraldTicks(20 * 20);
            playerComponent.setTabletTarget(target.getGameProfile().name());
        }
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        TabletComponent tablet = stack.getOrDefault(ModDataComponentTypes.TABLET, new TabletComponent(null, ItemStack.EMPTY));

        if (clickType == ClickType.RIGHT) {
            if (tablet.hunted() == null && tablet.ingredient().isEmpty()) {
                TabletComponent builtTablet = null;
                if (otherStack.contains(DataComponentTypes.PROFILE)) {
                    builtTablet = new TabletComponent(
                            otherStack.get(DataComponentTypes.PROFILE),
                            ItemStack.EMPTY
                    );
                }

                if (otherStack.contains(DataComponentTypes.WRITTEN_BOOK_CONTENT)) {
                    WrittenBookContentComponent book = otherStack.get(DataComponentTypes.WRITTEN_BOOK_CONTENT);

                    if (book != null) {
                        String author = book.author();

                        builtTablet = new TabletComponent(
                                ProfileComponent.ofDynamic(author),
                                ItemStack.EMPTY
                        );
                    }
                }

                if (otherStack.contains(ModDataComponentTypes.STORED_PACT)) {
                    Pact pact = otherStack.get(ModDataComponentTypes.STORED_PACT);

                    if (pact != null) {
                        String owner = pact.signer();

                        builtTablet = new TabletComponent(
                                ProfileComponent.ofDynamic(owner),
                                ItemStack.EMPTY
                        );
                    }
                }

                if (builtTablet != null) {
                    stack.set(ModDataComponentTypes.TABLET, builtTablet);
                }

                if (player.getEntityWorld().isClient()) {
                    player.playSound(SoundEvents.BLOCK_SNIFFER_EGG_PLOP);
                    player.playSound(SoundEvents.BLOCK_ROOTED_DIRT_BREAK);
                }
                return true;
            }

            if (otherStack.isIn(ModItemTags.ACCEPTABLE) && (tablet.hunted() != null && tablet.ingredient().isEmpty())) {
                ItemStack splitStack = otherStack.split(1);
                TabletComponent builtTablet = new TabletComponent(tablet.hunted(), splitStack.getItem().getDefaultStack());

                stack.set(ModDataComponentTypes.TABLET, builtTablet);

                if (player.getEntityWorld().isClient()) {
                    player.playSound(SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, 1, 0.2F);
                    player.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME);
                }
                return true;
            }
        }
        return false;
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        TooltipDisplayComponent display = stack.getOrDefault(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);
        TabletComponent tablet = stack.getOrDefault(ModDataComponentTypes.TABLET, new TabletComponent(null, ItemStack.EMPTY));

        if (tablet.shouldDisplay()) {
            return !display.shouldDisplay(ModDataComponentTypes.TABLET)
                    ? Optional.empty()
                    : Optional.of(tablet).map(component -> new TabletTooltipData(stack, tablet)
            );
        } else {
            return Optional.empty();
        }
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.clamp(Math.round((float) stack.getOrDefault(ModDataComponentTypes.INTEGER, MAX_USES) / MAX_USES * 13), 0, 13);
    }

    public int getItemBarColor(ItemStack stack) {
        return 0xFF05ff21;
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.INTEGER, MAX_USES) < MAX_USES;
    }
}
