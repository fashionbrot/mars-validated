package com.fashion.spv.test.model;

import com.fashion.spv.validated.annotation.Default;
import lombok.Data;

@Data
public class BaseModel {

    @Default("1")
    private Integer pageNo;

    @Default("20")
    private Integer pageSize;
}
