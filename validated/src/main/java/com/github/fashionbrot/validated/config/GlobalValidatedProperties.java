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
    /**
     * file properties
     */
    String fileName;
}
