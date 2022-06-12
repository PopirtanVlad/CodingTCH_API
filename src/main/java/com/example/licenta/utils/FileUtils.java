package com.example.licenta.utils;

public class FileUtils {

    public static String getFilePath(String problemDir, String fileName){
        if(fileName.contains(".ref")){
            return "problems/" + problemDir + "/references/" + fileName;
        }
        if(fileName.contains(".in")){
            return "problems/" + problemDir + "/inputs/" + fileName;
        }
        if(fileName.contains(".desc")){
            return "problems/" + problemDir + "/" + fileName;
        }
        return "submissions/" + problemDir + "/" + fileName;
    }

}
