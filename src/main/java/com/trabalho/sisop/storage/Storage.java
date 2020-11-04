package com.trabalho.sisop.storage;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Queue;

@Slf4j
public class Storage {

    @Getter
    @Setter
    private static Queue<List<String>> programs;

    public static int getQuantityProgramsLoaded() {
        return programs.size();
    }
}
