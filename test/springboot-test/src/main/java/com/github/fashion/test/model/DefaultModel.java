package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashion.test.test.Custom;
import lombok.Data;

@Data
public class DefaultModel extends BaseModel{

    @Custom(min = 1)
    private String abc;

    @Default("wang")
    private String wang;

    @Default("abc")
    private String test;
}
