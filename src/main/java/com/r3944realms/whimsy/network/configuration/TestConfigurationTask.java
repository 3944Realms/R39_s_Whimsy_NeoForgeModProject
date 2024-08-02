package com.r3944realms.whimsy.network.configuration;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.network.payload.TestModData;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.configuration.ServerConfigurationPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ConfigurationTask;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.configuration.ICustomConfigurationTask;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
@Deprecated
public record TestConfigurationTask(ServerConfigurationPacketListener listener) implements ICustomConfigurationTask {
    private static final String TaskName = "MyTestConfigurationTask";
    public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, TaskName));



    @Override
    public void run(@NotNull Consumer<CustomPacketPayload> sender) {
        final TestModData payload = new TestModData("testMessage",0);
        sender.accept(payload);
        this.listener().finishCurrentTask(this.type());
    }

    @Override
    public @NotNull Type type() {
        return TYPE;
    }

    @SubscribeEvent
    public static void register(final RegisterConfigurationTasksEvent event) {
        event.register(new TestConfigurationTask(event.getListener()));
    }

}
