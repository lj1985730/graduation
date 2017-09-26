package com.graduation.authentication.base;

import org.junit.Test;

import java.util.UUID;

/**
 * Created by Liu on 2016/8/29.
 */
public class SimpleTest {

    @Test
    public void genUUID() {
        for (int i = 10; i > 0; i--) {
            System.out.println(UUID.randomUUID().toString().toUpperCase());
        }
    }
}
