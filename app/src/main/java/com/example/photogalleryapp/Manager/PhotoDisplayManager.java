package com.example.photogalleryapp.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import android.util.Log;
import android.util.SparseArray;

import com.example.photogalleryapp.Utils.Camera;
import com.example.photogalleryapp.Utils.Camera.FileNameParser;
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
    private Filter filter;

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
        int i = photo_name_list.size();
        boolean match;
        Date date;
        String filename;
        do {
            current_index = (++current_index) % size;
            filename = photo_name_list.get(current_index);
            date = photo_date_list.get(current_index);
            if (filter != null) {
                match = filter.applyFilter(filename, date);
            } else {
                match = true;
            }
            i--;
        } while (!match && i != 0);
        return (i == 0 ? null : new Photo(Camera.FileNameParser.parse(filename),
                BitmapFactory.decodeFile(source_path + "/" + filename, options), date));
    }

    public Photo getPrevPhoto() {
        if (size == 0) {
            return null;
        }
        int i = photo_name_list.size();
        boolean match;
        Date date;
        String filename;
        do {
            current_index = (--current_index < 0 ? size - 1 : current_index);
            filename = photo_name_list.get(current_index);
            date = photo_date_list.get(current_index);
            if (filter != null) {
                match = filter.applyFilter(filename, date);
            } else {
                match = true;
            }
            i--;
        } while (!match && i != 0);
        return (i == 0 ? null : new Photo(Camera.FileNameParser.parse(filename),
                BitmapFactory.decodeFile(source_path + "/" + filename, options), date));
    }

    public Photo getCurrentPhoto() {
        if (size == 0 || current_index == -1) {
            return null;
        }
        String filename = photo_name_list.get(current_index);
        Date date = photo_date_list.get(current_index);
        return new Photo(Camera.FileNameParser.parse(filename),
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
                photo_name_list.put(i, f.getName());
                photo_date_list.put(i++, new Date(f.lastModified()));
                size++;
            }
        }
    }
    public String getCurrentFilePath(){
        String filename = photo_name_list.get(current_index);
        return source_path+"/"+filename;
    }
    public void removeFilter() {
        filter = null;
    }

    public void setFilter(String keyword, Date startDate, Date endDate) {
        filter = new Filter(keyword, startDate, endDate);
        int i = photo_name_list.size();
        boolean match;
        Date date;
        String filename;
        do {
            filename = photo_name_list.get(current_index);
            date = photo_date_list.get(current_index);
            current_index
                    = (match = filter.applyFilter(filename, date)) ?
                    current_index : (++current_index) % size;
            i--;
        } while (!match && i != 0);
        if (i == 0)
            current_index = -1;
    }

    public Filter getFilter() {
        return this.filter;
    }

    public void clear() {
        photo_name_list.clear();
        photo_name_list.clear();
    }

    public boolean renameCurrentPhoto(String newName) {
        if (current_index == -1) return false;
        String oldName_withID = photo_name_list.get(current_index);
        String ID = FileNameParser.parseUniqueID(oldName_withID);
        Log.d("ID", ID);
        String newName_withID = newName.concat(ID);
        File file = new File(source_path + '/' + oldName_withID);
        File newFile = new File(source_path + '/' + newName_withID);
        photo_name_list.put(current_index, newName_withID);
        return file.renameTo(newFile);
    }

    public static PhotoDisplayManager getInstance() {
        if (manager_instance == null)
            manager_instance = new PhotoDisplayManager();
        return manager_instance;
    }

    public class Filter {
        private String keyword;
        private Date dateStart;
        private Date dateEnd;

        Filter(String keyword, Date dateStart, Date dateEnd) {
            this.keyword = keyword;
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
        }

        boolean applyFilter(String keyword, Date date) {
            if (date.compareTo(dateStart) >= 0 && date.compareTo(dateEnd) <= 0) {
                if (this.keyword == null) {
                    return true;
                }
                return keyword.contains(this.keyword);
            }
            return false;
        }

        public String getKeyword() {
            return this.keyword;
        }
    }
}
