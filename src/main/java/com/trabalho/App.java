package com.trabalho;

import com.trabalho.sisop.VirtualMachine;

public class App {

    public static int PROCESS_ID;
    public static final int MEMORY_SIZE = 1024;
    public static final int QUANTITY_OF_FRAMES = 64;
    public static final int QUANTITY_OF_PAGES = 16;

    public static String ERROR = "error";
    public static String INFO = "info";

    public static String LOGLEVEL = "error";

    public static void main(String[] args) {


        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, LOGLEVEL);

        VirtualMachine virtualMachine = new VirtualMachine();
        virtualMachine.run();

    }
}
