package com.r3944realms.whimsy.network.configuration;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.gamerules.ClientRender.MustOthersHiddenNameTag;
import com.r3944realms.whimsy.content.gamerules.Gamerules;
import com.r3944realms.whimsy.network.payload.BooleanGameRuleValueChangeData;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ConfigurationTask;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
public record ClientGameRulesSyncConfigurationTask(ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {
    private static final String TaskName = "client_game_rules_sync";
    public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, TaskName));

    @Override
    public void run(@NotNull Consumer<CustomPacketPayload> sender) {
        final BooleanGameRuleValueChangeData syncPayload = new BooleanGameRuleValueChangeData(MustOthersHiddenNameTag.ID, Gamerules.gamerulesBooleanValuesClient.get(MustOthersHiddenNameTag.ID));
        sender.accept(syncPayload);
        this.listener().finishCurrentTask(this.type());
    }


    @Override
    public @NotNull Type type() {
        return TYPE;
    }


}
