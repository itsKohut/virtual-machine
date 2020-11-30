package com.trabalho.sisop.utils;

import static java.lang.Character.getNumericValue;

public class Parser {

    public final static int ARRAY_OUTBOUND_FIX = 1;

    public static String[] parseOperation(String instructions) {
        return instructions.split("[ ,]+");
    }

    public static int parseParamater(String instruction) {

        if (isRegister(instruction)) {
            if (instruction.length() == 2) {
                return (getNumericValue(instruction.charAt(1)) - ARRAY_OUTBOUND_FIX);
            }
            return 9;
        }

        if (isDirectMemoryAcess(instruction)) {
            return Integer.parseInt(instruction.substring(1, instruction.length() - 1));
        }

        return Integer.parseInt(instruction);
    }

    private static boolean isRegister(String instruction) {
        return instruction.startsWith("R");
    }

    private static boolean isDirectMemoryAcess(String instruction) {
        return instruction.startsWith("[");
    }
}
