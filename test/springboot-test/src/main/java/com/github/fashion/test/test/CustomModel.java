package com.github.fashion.test.test;

import lombok.Data;

@Data
public class CustomModel {

    @Custom(min=1)
    private String abc;

}
