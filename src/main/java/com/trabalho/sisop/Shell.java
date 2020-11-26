package com.trabalho.sisop;

import com.google.common.io.Resources;
import com.trabalho.sisop.process.ProcessManager;
import lombok.SneakyThrows;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.trabalho.sisop.loader.Loader.RESOURCE_PROGRAM_BASE_PATH;

public class Shell extends Thread {

    private int program;

    public Shell(int program) {
        this.program = program;
    }

    @SneakyThrows
    public void run() {

        if (program >= 1 && program <= 4) {
            URL url = Resources.getResource(RESOURCE_PROGRAM_BASE_PATH + (program - 1));
            List<String> program = Resources.readLines(url, StandardCharsets.UTF_8);
            ProcessManager.processCreation(program);
        }
    }
}