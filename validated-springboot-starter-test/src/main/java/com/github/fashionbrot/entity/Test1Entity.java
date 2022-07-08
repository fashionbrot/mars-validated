package com.github.fashionbrot.entity;

import com.github.fashionbrot.constraint.ObjectBean;
import lombok.Data;

@Data
public class Test1Entity {

    @ObjectBean
    private String a1;

    private String a2;

}
