package com.trabalho.sisop.utils;

public class Parser {

    public final static int ARRAY_OUTBOUND_FIX = 1;

    public static String[] parseOperation(final String instructions) {
        return instructions.split("[ ,]+");
    }

    public static int parseParamater(final String instruction) {

        if (isRegister(instruction)) {
            return (Character.getNumericValue(instruction.charAt(1)) - ARRAY_OUTBOUND_FIX);
        }

        if(isDirectMemoryAcess(instruction)) {
            return Integer.parseInt(instruction.substring(1,3));
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
