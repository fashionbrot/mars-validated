package com.fashion.spv.test.model;

import com.fashion.spv.validated.annotation.Default;
import lombok.Data;

@Data
public class DefaultModel extends BaseModel{

    @Default("abc")
    private String abc;

    @Default("wang")
    private String wang;
}
