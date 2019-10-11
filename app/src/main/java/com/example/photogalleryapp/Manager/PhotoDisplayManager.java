package com.example.photogalleryapp.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;

import java.io.File;

public class PhotoDisplayManager {
    private static PhotoDisplayManager manager_instance = null;
    private SparseArray<String> bitmap_list = new SparseArray<>();
    private int current_index;
    private int size;
    private File absPath;
    private PhotoDisplayManager() {
        current_index = -1;
        size = 0;
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.photogalleryapp/files/Pictures");
        File[] fList = file.listFiles();
        if (fList != null) {
            current_index = 0;
            absPath = fList[current_index];
            int i = 0;
            for (File f : fList) {
                bitmap_list.put(i++, f.getPath());
                size++;
            }
        }
    }

    public String getFileName(){

        File pPath = new File(bitmap_list.get(current_index));
        if(pPath!=null) {
            String fFullName = pPath.getName();
            String fName = fFullName.split("\\.")[0];
            return fName;
        }else{
            return "No name yet";
        }
    }

    public File getPath(){
//        File pPath = new File(bitmap_list.get(current_index));
//        String fPath=Uri.fromFile(pPath).toString();
        File pPath = absPath;
            return pPath;
    }

    public Bitmap getNextPhoto() {
        if (size == 0) {
            return null;
        }
        current_index = (++current_index) % size;
        bitmap_list.get(current_index);
        Log.d("RETURN", "IMAGE PATH: " + bitmap_list.get(current_index));
        return BitmapFactory.decodeFile(bitmap_list.get(current_index));
    }

    public Bitmap getPrevPhoto() {
        if (size == 0) {
            return null;
        }
        current_index = (--current_index < 0 ? size - 1 : current_index);
        return BitmapFactory.decodeFile(bitmap_list.get(current_index));
    }

    public void clear() {
        bitmap_list.clear();
    }

    public static PhotoDisplayManager getInstance() {
        if (manager_instance == null)
            manager_instance = new PhotoDisplayManager();
        return manager_instance;
    }

}
