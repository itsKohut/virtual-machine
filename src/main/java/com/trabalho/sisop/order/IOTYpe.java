package com.trabalho.sisop.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IOTYpe {
    READ(1), WRITE(2);

    public int code;

    public static IOTYpe getEnum(int code) {
        return code == 1 ? READ : WRITE;
    }
}
