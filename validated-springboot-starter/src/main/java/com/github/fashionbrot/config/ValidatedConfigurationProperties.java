package com.github.fashionbrot.config;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mars.validated")
public class ValidatedConfigurationProperties {

    /**
     * properties file name
     * @return
     */
    String fileName = "valid";

    /**
     * language
     * @return
     */
    String language =  "zh_CN";
    /**
     * request locale param name
     * @return
     */
    String localeParamName = "lang";


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLocaleParamName() {
        return localeParamName;
    }

    public void setLocaleParamName(String localeParamName) {
        this.localeParamName = localeParamName;
    }
}
