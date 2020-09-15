package com.trabalho.sisop;

import com.trabalho.sisop.memory.MemoryFrame;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ProgramManager {

    public static final Map<Integer, MemoryFrame[]> pagTable = new HashMap<>();
}
