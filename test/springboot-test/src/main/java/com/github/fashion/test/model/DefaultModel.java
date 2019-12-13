package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.Default;
import lombok.Data;

@Data
public class DefaultModel extends BaseModel{

    @Default("abc")
    private String abc;

    @Default("wang")
    private String wang;
}
