package com.r3944realms.whimsy.content.blocks.enchantmentBlock;

import com.r3944realms.whimsy.CommonEventHandler;
import com.r3944realms.whimsy.modInterface.block.IBlockBehaviour$PropertiesExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class BlockEnchantmentStorage {
    private static final Logger log = LoggerFactory.getLogger(BlockEnchantmentStorage.class);

    public static void addBlockEnchantment(ResourceKey<Level> level, BlockPos blockPos, ListTag enchantments) {
        if(enchantments.isEmpty()) return;
        MinecraftServer server = CommonEventHandler.getServerInstance();

        BlockStateSaverAndLoader serverStates = BlockStateSaverAndLoader.getServerStates(server, level);
        try {
            BlockState blockState = Objects.requireNonNull(server.getLevel(level)).getBlockState(blockPos);
            ((IBlockBehaviour$PropertiesExtension)blockState.getBlock().properties()).setEnchantmentList(enchantments);
        } catch (NullPointerException e) {
            log.error("NullPointer from getLevel(). Failed to add enchantment to block {}", blockPos, e);
            return;
        }
        serverStates.blockEnchantments.add(new BlockStateSaverAndLoader.BlockEnchantInfo(level, blockPos, enchantments));

    }

    public static void removeBlockEnchantment(ResourceKey<Level> level, BlockPos blockPos) {
        MinecraftServer server = CommonEventHandler.getServerInstance();

        BlockStateSaverAndLoader state = BlockStateSaverAndLoader.getServerStates(server, level);
        try {
            BlockState blockState = Objects.requireNonNull(server.getLevel(level)).getBlockState(blockPos);
            ((IBlockBehaviour$PropertiesExtension)blockState.getBlock().properties()).setEnchantmentList(null);
        } catch (NullPointerException e) {
            log.error("NullPointer from getLevel(). Failed to remove enchantment from block {}", blockPos, e);
            return;
        }
        state.removeBlockEnchantment(server, level, blockPos);
    }

    public static ListTag getEnchantmentsAtPosition(ResourceKey<Level> level, BlockPos blockPos) {
        MinecraftServer server = CommonEventHandler.getServerInstance();
        if(server == null) {
           return null;
        }
        // 获取 BlockStateSaverAndLoader 实例
        BlockStateSaverAndLoader state = BlockStateSaverAndLoader.getServerStates(server, level);
        // 遍历附魔信息列表，找到指定位置的方块附魔信息
        for (BlockStateSaverAndLoader.BlockEnchantInfo blockEnchantment : state.blockEnchantments) {
            if (blockEnchantment.blockPos.equals(blockPos)) {
                // 返回指定位置的方块附魔名称
                return blockEnchantment.enchantments;
            }
        }
        // 如果没有找到指定位置的方块附魔信息，则返回空列表
        return new ListTag();
    }

    public static int getEnchantmentLevel(ResourceKey<Enchantment> enchantment, ResourceKey<Level> levelRes, BlockPos blockPos) {
        MinecraftServer server = CommonEventHandler.getServerInstance();
        // 获取方块的附魔信息
        ListTag enchantments = getEnchantmentsAtPosition(levelRes, blockPos);

        // 遍历附魔信息
        if (enchantments != null) {
            for (int i = 0; i < enchantments.size(); i++) {
                // 获取单个附魔信息
                CompoundTag enchantmentInfo = enchantments.getCompound(i);

                // 提取附魔名称和等级
                String enchantmentName = enchantmentInfo.getString("id");
                int level = enchantmentInfo.getInt("lvl");

                // 检查附魔名称是否匹配
                if (enchantmentName.equals(String.valueOf(enchantment))) {
                    // 返回附魔等级
                    return level;
                }
            }
        }

        // 如果没有找到匹配的附魔信息，默认返回0
        return 0;
    }

}
