package com.r3944realms.whimsy.blocks.enchantmentBlock;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.utils.LevelHelper.LevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


public class BlockStateSaverAndLoader extends SavedData {
    public Set<BlockEnchantInfo> blockEnchantments = new CopyOnWriteArraySet<>();
    public static class BlockEnchantInfo {
        public BlockPos blockPos;
        public ListTag enchantments;
        public ResourceKey<Level> level;

        public BlockEnchantInfo(ResourceKey<Level> level, BlockPos blockPos, ListTag enchantments) {
            this.level = level;
            this.blockPos = blockPos;
            this.enchantments = enchantments;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlockEnchantInfo that = (BlockEnchantInfo) o;
            return Objects.equals(blockPos, that.blockPos) &&
                    Objects.equals(enchantments, that.enchantments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(blockPos, enchantments);
        }
    }
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        ListTag blockEnchantmentsTagList = new ListTag();
        for (BlockEnchantInfo blockEnchantment : blockEnchantments) {
            CompoundTag blockEnchantmentTag = new CompoundTag();
            ResourceLocation location = blockEnchantment.level.location();
            blockEnchantmentTag.putString("Level", location.getNamespace() + "@" + location.getPath());
            blockEnchantmentTag.putIntArray("BlockPos", new int[]{blockEnchantment.blockPos.getX(), blockEnchantment.blockPos.getY(), blockEnchantment.blockPos.getZ()});
            blockEnchantmentTag.put("Enchantments", blockEnchantment.enchantments);
            blockEnchantmentsTagList.add(blockEnchantmentTag);
        }
        pTag.put("BlockEnchantments", blockEnchantmentsTagList);
        return pTag;
    }
    public static BlockStateSaverAndLoader createFromTag(CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        BlockStateSaverAndLoader state = new BlockStateSaverAndLoader();

        ListTag blockEnchantmentsList = pTag.getList("BlockEnchantments", 10);//Compound 标签类型 10
        for (int i = 0; i < blockEnchantmentsList.size(); ++i) {
            CompoundTag blockEnchantmentTag = blockEnchantmentsList.getCompound(i);
            ResourceKey<Level> levelResourceKey = LevelHelper.getDesignatedLevelResKey(blockEnchantmentTag.getString("Level"));
            int[] posArray = blockEnchantmentTag.getIntArray("BlockPos");
            BlockPos blockPos = new BlockPos(posArray[0], posArray[1], posArray[2]);
            ListTag enchantments = blockEnchantmentTag.getList("Enchantments", 10);

            state.blockEnchantments.add(new BlockEnchantInfo(levelResourceKey, blockPos, enchantments));
        }

        return state;
    }
    private static final Factory<BlockStateSaverAndLoader> type = new Factory<>(
            BlockStateSaverAndLoader::new,
            BlockStateSaverAndLoader::createFromTag
    );

    public static BlockStateSaverAndLoader getServerStates(MinecraftServer server, ResourceKey<Level> level) {
        if (server != null) {
            DimensionDataStorage persistStateManager = Objects.requireNonNull(server.getLevel(level)).getDataStorage();
            BlockStateSaverAndLoader state = persistStateManager.computeIfAbsent(type, WhimsyMod.MOD_ID + "_block_enchantments");
            state.setDirty();
            return state;
        }
        return null;
    }
    public void removeBlockEnchantment(MinecraftServer server, ResourceKey<Level> level, BlockPos targetBlockPos) {
        // 移除这个元素
        boolean removeIf = blockEnchantments.removeIf(blockEnchantment -> blockEnchantment.blockPos.equals(targetBlockPos) && blockEnchantment.level.equals(level));
    }
}
