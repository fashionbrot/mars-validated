package com.github.fashion.test.model;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.NotEmpty;
import lombok.Data;

/**
 * @author fashionbrot
 */
@Data
public class UserReq {


    @Default("1")
    private String id;

    @javax.validation.constraints.NotEmpty(message = "username: 不能为空")
    @NotEmpty
    private String username;

    @javax.validation.constraints.NotEmpty(message = "password: 不能为空")
    @NotEmpty
    private String password;

}
