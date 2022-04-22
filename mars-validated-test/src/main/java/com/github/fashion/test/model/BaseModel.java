package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.NotNull;
import lombok.Data;

@Data
public class BaseModel {

    @NotNull
    private Integer pageNo;

    @Default("20")
    private Integer pageSize;
}
