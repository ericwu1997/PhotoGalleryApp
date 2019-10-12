package com.example.photogalleryapp.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.collection.ArrayMap;

import android.util.Log;
import android.util.SparseArray;

import com.example.photogalleryapp.Utils.Photo;

import java.io.File;
import java.util.Date;


public class PhotoDisplayManager {
    private String source_path;
    private static PhotoDisplayManager manager_instance = null;
    private SparseArray<String> photo_name_list;
    private SparseArray<Date> photo_date_list;
    private int current_index;
    private int size;
    private BitmapFactory.Options options;

    private PhotoDisplayManager() {
        photo_name_list = new SparseArray<>();
        photo_date_list = new SparseArray<>();

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        current_index = -1;
        size = 0;
    }

    public Photo getNextPhoto() {
        if (size == 0) {
            return null;
        }
        current_index = (++current_index) % size;
        String filename = photo_name_list.get(current_index);
        Date date = photo_date_list.get(current_index);
        return new Photo(filename,
                BitmapFactory.decodeFile(source_path + "/" + filename, options), date);
    }

    public Photo getPrevPhoto() {
        if (size == 0) {
            return null;
        }
        current_index = (--current_index < 0 ? size - 1 : current_index);
        String filename = photo_name_list.get(current_index);
        Date date = photo_date_list.get(current_index);
        return new Photo(filename,
                BitmapFactory.decodeFile(source_path + "/" + filename, options), date);
    }

    public Photo getCurrentPhoto() {
        if (size == 0) {
            return null;
        }
        String filename = photo_name_list.get(current_index);
        Date date = photo_date_list.get(current_index);
        return new Photo(filename,
                BitmapFactory.decodeFile(source_path + "/" + filename, options), date);
    }

    public void addToList(String name, Date date) {
        photo_name_list.put(size, name);
        photo_date_list.put(size, date);
        current_index = ++size - 1;
    }

    public void readFromFolder(String path) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), path);
        File[] fList = file.listFiles();
        source_path = file.getPath();
        if (fList != null) {
            current_index = 0;
            int i = 0;
            for (File f : fList) {
                Log.d("PATH", f.getPath());
                photo_name_list.put(i, f.getName());
                photo_date_list.put(i++, new Date(f.lastModified()));
                size++;
            }
        }
    }

    public ArrayMap<String, Bitmap> getPhotoList() {
        ArrayMap<String, Bitmap> photoList = new ArrayMap<>();
        String filename;
        for (int i = 0; i < photo_name_list.size(); i++) {
            filename = photo_name_list.get(i);
            photoList.put(filename,
                    BitmapFactory.decodeFile(source_path + "/" + filename, options));
        }
        return photoList;
    }

    public void setCurrentIndexByName(String caption) {
        current_index = photo_name_list.indexOfValue(caption);
    }

    public int getCurrentIndex() {
        return current_index;
    }

    public void clear() {
        photo_name_list.clear();
        photo_name_list.clear();
    }

    public static PhotoDisplayManager getInstance() {
        if (manager_instance == null)
            manager_instance = new PhotoDisplayManager();
        return manager_instance;
    }
}
