package com.github.fashionbrot.validated.util;

public class PatternSts {

    public static final String BANKCARD_REGEXP="^([1-9]{1})(\\d{14}|\\d{18})$";

    public static final String CREDITCARD_REGEXP="(?:3[47][0-9]{13})|(?:3(?:0[0-5]|[68][0-9])[0-9]{11})|(?:6(?:011|5[0-9]{2})(?:[0-9]{12}))|(?:(?:2131|1800|35\\\\d{3})\\\\d{11})|(?:(?:5[0678]\\\\d\\\\d|6304|6390|67\\\\d\\\\d)\\\\d{8,15})|(?:(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12})|(?:4[0-9]{12})(?:[0-9]{3})?";

    public static final String EMAIL_REGEXP="^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public static final String PHONE_REGEXP="^(((13[0-9])|(14[579])|(15[^4,\\D])|(18[0-9])|(17[0-9]))|(19[8,9])|166)\\d{8}$";

    public static final String IDCARD_REGEXP="(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|([1−9]\\d5\\d2((0[1−9])|(10|11|12))(([0−2][1−9])|10|20|30|31)\\d2[0−9Xx])";


}

