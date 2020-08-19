package com.trabalho.sisop.io;

import com.google.common.io.Resources;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
public class IO {

    public final static String FULL_PATH_FILES_DIR = "src/main/resources/files";
    public final static String RESOURCE_PROGRAM_BASE_PATH = "files/program";

    @SneakyThrows
    public static Queue<List<String>> loadPrograms() {

        URL url;
        Queue<List<String>> programsQueue = new LinkedList<>();
        int programs = programsCounter();

        for (int i = 0; i < programs; i++) {
            url = Resources.getResource(RESOURCE_PROGRAM_BASE_PATH + i);
            programsQueue.add(Resources.readLines(url, StandardCharsets.UTF_8));
        }
        log.info("{} program(s) has been loaded to storage", programs);
        return programsQueue;
    }

    private static int programsCounter() {
        return new File(FULL_PATH_FILES_DIR).listFiles().length;
    }
}
