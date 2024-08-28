package com.r3944realms.whimsy.datagen.provider;


import com.r3944realms.whimsy.content.sounds.ModSoundRegister;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider  extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param modId  The mod ID of the current mod.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    public ModSoundDefinitionsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    public SoundDefinition getSoundDefinition(String subTitle, SoundDefinition.Sound... sounds) {
        return SoundDefinition.definition().subtitle(subTitle).with(sounds);
    }

    @Override
    public void registerSounds() {
        add(
                ModSoundRegister.SANDS_OF_TIME,
                getSoundDefinition(
                        ModSoundRegister.getSubTitleTranslateKey("sands_of_time"),
                        sound(ModSoundRegister.RL_SANDS_OF_TIME, SoundDefinition.SoundType.SOUND)
                )
        );
        add(
                ModSoundRegister.HUB_MUSIC,
                getSoundDefinition(
                        ModSoundRegister.getSubTitleTranslateKey("hub_music"),
                        sound(ModSoundRegister.RL_HUB_MUSIC, SoundDefinition.SoundType.SOUND)
                )
        );
        add(
                ModSoundRegister.RL_ACE_RACE,
                getSoundDefinition(
                        ModSoundRegister.getSubTitleTranslateKey("ace_race"),
                        sound(ModSoundRegister.RL_ACE_RACE, SoundDefinition.SoundType.SOUND)
                )

        );
        add(
                ModSoundRegister.RL_GRID_RUNNERS,
                getSoundDefinition(
                        ModSoundRegister.getSubTitleTranslateKey("grid_runners"),
                        sound(ModSoundRegister.RL_GRID_RUNNERS, SoundDefinition.SoundType.SOUND)
                )

        );
        add(
                ModSoundRegister.RL_MELTDOWN,
                getSoundDefinition(
                        ModSoundRegister.getSubTitleTranslateKey("meltdown"),
                        sound(ModSoundRegister.RL_MELTDOWN, SoundDefinition.SoundType.SOUND)
                )
        );

    }
}
