package com.cursos.spring.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Role {

    CUSTOMER( Arrays.asList( Permission.READ_ALL_PRODUCTS ) ),
    ADMINISTRATOR( Arrays.asList( Permission.SAVE_ONE_PRODUCT, Permission.READ_ALL_PRODUCTS ) );

    private final List< Permission > permissions;

}