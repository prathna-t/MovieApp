package com.example.movielibrary.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MovieContentProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "fit2081.app.amanda";
    //    resources identifier
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    private static final int MOVIES = 1;
    private static final int MOVIE_TITLE = 2;
    private static final int MOVIE_YEAR = 3;
    private static final int MOVIE_COUNTRY = 4;
    private static final int MOVIE_GENRE = 5;
    private static final int MOVIE_COST = 6;
    private static final int MOVIE_KEYWORDS = 7;

    MovieDatabase db;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    public MovieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deletionCount;

        deletionCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .delete(Movie.TABLE_NAME, selection, selectionArgs);

        return deletionCount;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sUriMatcher.match(uri);

        switch (match){
            case MOVIES:
                break;
            case MOVIE_TITLE:
                break;
            case MOVIE_YEAR:
                break;
            case MOVIE_COUNTRY:
                break;
            case MOVIE_GENRE:
                break;
            case MOVIE_COST:
                break;
            case MOVIE_KEYWORDS:
                break;
        }

        long rowId = db
                .getOpenHelper()
                .getWritableDatabase()
                .insert(Movie.TABLE_NAME, 0, values);

        return ContentUris.withAppendedId(CONTENT_URI, rowId);  // new Id with the new row

    }

    @Override
    public boolean onCreate() {
        db = MovieDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Movie.TABLE_NAME);
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);

        final Cursor cursor = db  // can retrive multiple row
                .getOpenHelper()
                .getReadableDatabase()
                .query(query, selectionArgs);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateCount;
        updateCount = db
                .getOpenHelper()
                .getWritableDatabase()
                .update(Movie.TABLE_NAME, 0, values, selection, selectionArgs);

        return updateCount;
    }
}