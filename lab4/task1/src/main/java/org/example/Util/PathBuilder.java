package org.example.Util;

import java.io.File;

public class PathBuilder {
    private PathBuilder(){}

    public static String getPath(String... path){
        return String.join(File.separator,path);
    }
}
