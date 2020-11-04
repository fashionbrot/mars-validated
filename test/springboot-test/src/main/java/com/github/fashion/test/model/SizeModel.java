package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.Size;
import lombok.Data;

@Data
public class SizeModel {

    @Size(min = 1,max = 3,msg = "1-3之间")
    private String abc;

}
