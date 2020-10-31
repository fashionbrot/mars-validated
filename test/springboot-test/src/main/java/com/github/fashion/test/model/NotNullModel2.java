package com.github.fashion.test.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NotNullModel2 {

    @NotNull(message = "notNullModel abc=null")
    private String abc;
}
