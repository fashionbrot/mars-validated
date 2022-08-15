package com.github.fashion.test.model;

import com.github.fashion.test.groups.EditGroup;
import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.NotNull;
import lombok.Data;

@Data
public class GroupModel {


    private String abc;

    @NotNull(msg = "id 不能为空",groups = {EditGroup.class})
    private Long id;

    @NotEmpty(msg = "name 不能为空")
    private String name;

}
