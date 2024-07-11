package com.r3944realms.whimsy.init;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.utils.logger.logger;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

public class FilePathInit {
    public static void configWhimsyFile(String[] children) {
        File configFile = new File(FMLPaths.CONFIGDIR.get().toFile(), WhimsyMod.MOD_ID);
        if (!configFile.exists()) {
            boolean mkdirSuccess = configFile.mkdirs();
            if(!mkdirSuccess) {
                logger.error("failed to create config directory for whimsicality");
                throw new RuntimeException("failed to create config directory for whimsicality");
            } else {
                for (String child : children) {
                    File file = new File(configFile, child);
                    if(!file.exists()) {
                        boolean mkdirChildrenSuccess = file.mkdirs();
                        if(!mkdirChildrenSuccess) {
                            logger.error("failed to create "+ child +" directory for whimsicality");
                            throw new RuntimeException("failed to create "+ child +" directory for whimsicality");
                        }
                    }
                }

            }
        }
    }
}
