package com.rovirosa.rovirosa_spring.DTOs;

public class AppInfoDTO {
    private String appName;
    private String logo;

    public AppInfoDTO(String appName, String logo) {
        this.appName = appName;
        this.logo = logo;
    }

    public String getAppName() {
        return appName;
    }

    public String getLogo() {
        return logo;
    }

}
