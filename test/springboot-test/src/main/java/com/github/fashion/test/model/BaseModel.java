package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.Default;
import lombok.Data;

@Data
public class BaseModel {

    @Default("1")
    private Integer pageNo;

    @Default("20")
    private Integer pageSize;
}
