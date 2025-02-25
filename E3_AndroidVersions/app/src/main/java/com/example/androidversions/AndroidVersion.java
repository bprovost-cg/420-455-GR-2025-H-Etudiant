package com.example.androidversions;

public class AndroidVersion {
    public String Name;

    public String CodeName;

    public String ApiVersion;

    public AndroidVersion(String name, String codeName, String apiVersion)
    {
        this.Name = name;
        this.CodeName = codeName;
        this.ApiVersion = apiVersion;
    }
}
