package com.example.aburom.newsapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aburom.newsapp.model.News;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;

    public DbHelper(Context context) {
        super(context, News.DB_NAME, null, 1);
        sqLiteDatabase = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(News.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(News.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertNews(String title, String time, String description, byte[] image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(News.COL_TITLE, title);
        contentValues.put(News.COL_TIME, time);
        contentValues.put(News.COL_DESCRIPTION, description);
        contentValues.put(News.COL_IMAGE, image);
        return sqLiteDatabase.insert(News.TABLE_NAME, null, contentValues) > 0;
    }

    public boolean deleteNews(int id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(News.COL_ID, id);
        return sqLiteDatabase.delete(News.TABLE_NAME, News.COL_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean toggleFavoriteNews(String id, int favorite) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(News.COL_FAVORITE, favorite);
        return sqLiteDatabase.update(News.TABLE_NAME, contentValues, News.COL_ID + "= ?", new String[]{id}) > 0;
    }

//    public boolean updateNews(String oldID, String name,String average){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(News.COL_TITLE,name);
//        contentValues.put(News.COL_AVERAGE,average);
//        return sqLiteDatabase.update(News.TABLE_NAME,contentValues,News.COL_ID+"= ?",new String[]{oldID})>0;
//    }

//    public boolean deleteNews(String oldID){
//        return sqLiteDatabase.delete(News.TABLE_NAME,News.COL_ID+"= ?",new String[]{oldID})>0;
//    }

    public ArrayList<News> getAllNews() {
        ArrayList<News> NewsArrayList = new ArrayList<>();

        String query = News.GET_NEWS;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(cursor.getColumnIndex(news.COL_ID)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(news.COL_TITLE)));
                news.setDescription(cursor.getString(cursor.getColumnIndex(news.COL_DESCRIPTION)));

                if (cursor.getInt(cursor.getColumnIndex(news.COL_FAVORITE)) == 0) {
                    news.setFavorite(false);
                } else {
                    news.setFavorite(true);
                }
                news.setTime(cursor.getString(cursor.getColumnIndex(news.COL_TIME)));
                news.setImage(cursor.getBlob(cursor.getColumnIndex(news.COL_IMAGE)));


                NewsArrayList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return NewsArrayList;
    }

    public ArrayList<News> getFavoriteNews() {
        ArrayList<News> NewsArrayList = new ArrayList<>();

        String query = News.GET_FAVORITE_NEWS;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(cursor.getColumnIndex(news.COL_ID)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(news.COL_TITLE)));
                news.setDescription(cursor.getString(cursor.getColumnIndex(news.COL_DESCRIPTION)));

                if (cursor.getInt(cursor.getColumnIndex(news.COL_FAVORITE)) == 0) {
                    news.setFavorite(false);
                } else {
                    news.setFavorite(true);
                }

                news.setTime(cursor.getString(cursor.getColumnIndex(news.COL_TIME)));
                news.setImage(cursor.getBlob(cursor.getColumnIndex(news.COL_IMAGE)));


                NewsArrayList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return NewsArrayList;
    }

    public ArrayList<News> getSearchedNews(String searchQuery) {
        ArrayList<News> newsArrayList = new ArrayList<>();

        String query = News.GET_NEWS;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(cursor.getColumnIndex(news.COL_ID)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(news.COL_TITLE)));
                news.setDescription(cursor.getString(cursor.getColumnIndex(news.COL_DESCRIPTION)));

                if (cursor.getInt(cursor.getColumnIndex(news.COL_FAVORITE)) == 0) {
                    news.setFavorite(false);
                } else {
                    news.setFavorite(true);
                }

                news.setTime(cursor.getString(cursor.getColumnIndex(news.COL_TIME)));
                news.setImage(cursor.getBlob(cursor.getColumnIndex(news.COL_IMAGE)));

                if (searchQuery != null) {
                    if (news.getTitle().contains(searchQuery)) {
                        newsArrayList.add(news);
                    }
                }


            } while (cursor.moveToNext());
        }
        cursor.close();
        return newsArrayList;
    }
}
