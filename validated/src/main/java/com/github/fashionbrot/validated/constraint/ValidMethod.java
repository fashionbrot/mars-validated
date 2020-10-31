package com.github.fashionbrot.validated.constraint;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ValidMethod {

    MSG("msg"),
    GROUPS("groups");

    private String method;
}
