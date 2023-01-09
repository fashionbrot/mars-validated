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

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
