package com.dezhen.a500pxsave;

import android.os.Environment;

import java.io.File;

/**
 * Created by root on 27/07/17.
 */

public class CreatFile {
    private String path = "500pxSave";
    private File file;
    private String path_all;

    public String CreatFilePath(){

        path_all = Environment.getExternalStorageDirectory().getPath() + File.separator + path;
        file = new File(path_all);
        if(!file.exists()){
            file.mkdirs();
        }
        return path_all;
    }
}
