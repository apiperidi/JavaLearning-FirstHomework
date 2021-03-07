package com.example.MyFirstClassProject;

import java.io.File;

public enum Directory {
    HOME_DIRECTORY(System.getProperty("user.home"), 1), JAVA_HOME(System.getProperty("java.home"), 2), FILE_DIRECTORY(
            System.getProperty("user.home") + File.separator + "data_files" + File.separator, 3);

    private String path;
    private int id;

    Directory(String path, int id) {
        this.path = path;
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }
}
