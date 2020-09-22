package com.github.fashionbrot.validated.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalValidatedProperties {

    public static final String BEAN_NAME = "globalValidatedProperties";

    public static final String FILENAME="fileName";

    public static final String LOCALE_PARAM_NAME="localeParamName";
    /**
     * file properties
     */
    String fileName;

    String localeParamName;

    String language;

}
