package com.github.fashionbrot.validated.test;

import lombok.Data;

@Data
public class CustomModel {

    @Custom(min=1)
    private String abc;

}
