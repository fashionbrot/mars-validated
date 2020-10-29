package com.github.fashion.test.util;

import lombok.Data;

/**
 * @author guoran.sun
 * @date 2019/12/17 12:35
 */
@Data
public class HttpResult {

    public int code;
    public String content;

    public boolean isSuccess(){
        return 200 == this.code;
    }
    public boolean isFail(){
        if (200 == this.code){
            return false;
        }
        if (404 == this.code || 500 == this.code){
            return true;
        }
        return true;
    }

    HttpResult(int code, String content) {
        this.code = code;
        this.content = content;
    }

}
