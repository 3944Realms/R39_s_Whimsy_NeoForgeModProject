package com.r3944realms.whimsy.init;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.utils.logger.logger;
import net.neoforged.fml.loading.FMLPaths;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FilePathHelper {
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
    public static void HCJFileCreator() {
        PrivateHCJFileInitHelper.createHCJFDirAndFiles();
    }
    public static Path get_HCJ_HTML_Path() {
        return PrivateHCJFileInitHelper.GameDir.resolve("WhimsyHTML").resolve("index.html");
    }
    public static void ReCreateHCJFile(String InputValueText) {
        PrivateHCJFileInitHelper.ReCreateHTMLFile(InputValueText);
    }
    public static void writeFile(Path filePath, String content) {
        File file = filePath.toFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            logger.debug("File written: {}", filePath);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
