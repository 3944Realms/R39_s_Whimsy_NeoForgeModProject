package com.r3944realms.whimsy.init;

import com.r3944realms.whimsy.utils.logger.logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
@Deprecated
public class Dependencies {
    public static void initialize() {
        initializeDependencies();
    }

    private static void initializeDependencies() {
        try {
            logger.info("Initializing dependencies...");
            loadDependencies();
        } catch (Exception e) {
            logger.debug("Error initializing dependencies. {}", e.getMessage());
        }
    }

    private static void loadDependencies() throws Exception {
        File libDir = new File(Dependencies.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "META-INF/jarJar");
        File[] files = libDir.listFiles((dir, name) -> name.endsWith(".jar"));

        if (files != null) {
            URL[] urls = new URL[files.length];
            for (int i = 0; i < files.length; i++) {
                urls[i] = files[i].toURI().toURL();
                logger.info("Adding jar {} to dependencies.", files[i].getName());
            }
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Dependencies.class.getClassLoader());
            Thread.currentThread().setContextClassLoader(urlClassLoader);
        }
    }
}
