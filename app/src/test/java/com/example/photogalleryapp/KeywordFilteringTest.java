package com.example.photogalleryapp;

import android.util.Log;

import com.example.photogalleryapp.Manager.PhotoDisplayManager;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class KeywordFilteringTest {

    @Test
    public void addition_isCorrect() {
        PhotoDisplayManager p = PhotoDisplayManager.getInstance();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2019);
        c.set(Calendar.MONTH, 10);
        c.set(Calendar.DAY_OF_MONTH, 10);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date d = c.getTime();
        p.addToList("Dog 1234567.jpg", d);
        p.addToList("Cat 1234567.jpg", d);
        p.addToList("Pig 1234567.jpg", d);
        p.addToList("Cow 1234567.jpg", d);
        p.moveToNext();
        p.setFilter("Cow", d, d);
        assertTrue(p.getFilter().applyFilter("Cow", d));
    }
}