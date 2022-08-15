package com.github.fashion.test.model;

import com.github.fashion.test.groups.EditGroup;
import com.github.fashionbrot.validated.annotation.NotNull;
import lombok.Data;

@Data
public class NotNullModel {

    @NotNull(msg = "notNullModel abc=null",groups = {EditGroup.class})
    private String abc;

    @NotNull(msg = "notNullModel bbb=null")
    private String bbb;


}
