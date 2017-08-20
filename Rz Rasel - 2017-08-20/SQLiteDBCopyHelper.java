package com.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Rz Rasel on 2017-04-18.
 */

public class SQLiteDBCopyHelper extends SQLiteOpenHelper {
    private Context context;
    //private static String DB_PATH = "";
    private static String SYS_DATABASE_PATH = "";
    private static String ASSETS_DATABASE_PATH = "";
    private static String DATABASE_NAME = "db.sqlite3";
    private static int DATABASE_VERSION = 1;

    private SQLiteDatabase sqLiteDatabase;

    public SQLiteDBCopyHelper(Context argContext, String argDatabaseName, String argAssetsPath) {
        super(argContext, argDatabaseName, null, DATABASE_VERSION);
        DATABASE_NAME = argDatabaseName;
        SYS_DATABASE_PATH = argContext.getDatabasePath(DATABASE_NAME).getPath();
        this.context = argContext;
        this.DATABASE_NAME = argDatabaseName;
        this.ASSETS_DATABASE_PATH = argDatabaseName;
        if (null != argAssetsPath && argAssetsPath.length() > 0 && !argAssetsPath.isEmpty()) {
            String newAssetsPath = argAssetsPath;
            int startIndex = argAssetsPath.indexOf("/", 0);
            int endIndex = argAssetsPath.lastIndexOf("/");
            if (startIndex == 0) {
                newAssetsPath = argAssetsPath.substring(1);
                endIndex = newAssetsPath.lastIndexOf("/");
            }
            if (endIndex != -1) {
                newAssetsPath = newAssetsPath.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
            }
            this.ASSETS_DATABASE_PATH = newAssetsPath + "/" + argDatabaseName;
            //String whatyouaresearching = myString.substring(0, myString.lastIndexOf("/"))
            System.out.println("START_INDEX: " + endIndex);
        }
        System.out.println(" ASSETS_DATABASE_PATH: " + ASSETS_DATABASE_PATH);
        System.out.println("SYSTEM_DATABASE_PATH: " + SYS_DATABASE_PATH);
        try {
            onCreateSysDatabase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
    }

    private void onCreateSysDatabase() throws IOException {
        if (!onCheckSysDatabase()) {
            this.getReadableDatabase();
            onCopyToSysDatabase();
            this.close();
        }
    }

    private boolean onCheckSysDatabase() {
        //File DbFile = new File(DB_PATH + DB_NAME);
        File dbFile = new File(SYS_DATABASE_PATH);
        return dbFile.exists();
    }

    public boolean onOpenDatabase() throws SQLException {
        sqLiteDatabase = SQLiteDatabase.openDatabase(SYS_DATABASE_PATH, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return sqLiteDatabase != null;
    }

    /*public void openDatabase() throws SQLException {
        /-*String myPath = DATABASE_PATH + DATABASE_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);*-/
    }*/

    public synchronized void onCloseDatabase() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    private void onCopyToSysDatabase() throws IOException {
        InputStream mInput = context.getAssets().open(ASSETS_DATABASE_PATH);
        String outfileName = SYS_DATABASE_PATH;
        OutputStream mOutput = new FileOutputStream(outfileName);
        byte[] buffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(buffer)) > 0) {
            mOutput.write(buffer, 0, mLength);
        }
        mOutput.flush();
        mInput.close();
        mOutput.close();
        System.out.println("FILE_COPIED");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.v("Database Upgrade", "Database version higher than old.");
            onDeleteSysDatabase();
            try {
                onCreateSysDatabase();

            } catch (IOException ioe) {

                throw new Error("Unable to create database");
            }
        }
    }

    public void onDeleteSysDatabase() {
        File file = new File(SYS_DATABASE_PATH);
        if (file.exists()) {
            file.delete();
            System.out.println("delete database file.");
        }
    }

    public boolean onInsert(String argTableName, ContentValues argContentValues) {
        long result = sqLiteDatabase.insert(argTableName, null, argContentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean onUpdate(String argTableName, ContentValues argContentValues, String argWhereClause) {
        //sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        //id = idValue AND name = nameValue
        //NAME + " = ? AND " + LASTNAME + " = ?", new String[]{"Manas", "Bajaj"}
        sqLiteDatabase.update(argTableName, argContentValues, argWhereClause, null);
        return true;
    }

    public Integer onDelete(String argTableName, String argWhereClause) {
        //KEY_DATE + "='date' AND " + KEY_GRADE + "='style2' AND " + KEY_STYLE + "='style'"
        return sqLiteDatabase.delete(argTableName, argWhereClause, null);
    }

    public Cursor getSqlQueryResults(String argSqlQuery) {
        Cursor cursor = sqLiteDatabase.rawQuery(argSqlQuery, null);
        return cursor;
    }

    public void onRawSqlQuery(String argSqlQuery) {
        sqLiteDatabase.execSQL(argSqlQuery);
    }

    public ContentValues getContentValues(String argKey, String argValue) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(argKey, argValue);
        return contentValues;
    }
}
/*
private SQLiteDBCopyHelper sqLiteDBCopyHelper = null;
private SQLiteDatabase sqLiteDatabase;
//|------------------------------------------------------------|
sqLiteDBCopyHelper = new SQLiteDBCopyHelper(context, "db_cmdss.sqlite3", "/database/");
//|------------------------------------------------------------|
if (sqLiteDBCopyHelper != null) {
    sqLiteDBCopyHelper.onOpenDatabase();
    String sqlQuery = "";
    ContentValues contentValues = new ContentValues();
    contentValues.put("tdm_id", System.currentTimeMillis() / 1000);
    contentValues.put("tdm_subject", "Message Subject");
    contentValues.put("tdm_body", "Message Body");
    contentValues.put("tdm_sender", userSession.getUserMobileNo());
    contentValues.put("tdm_receiver_type", "Test");
    contentValues.put("tdm_receiver", "all");
    sqLiteDBCopyHelper.onInsertData("tbl_draft_message", contentValues);
    sqlQuery = " SELECT * FROM tbl_draft_message ORDER BY tdm_subject ";
    Cursor cursor;
    cursor = sqLiteDBCopyHelper.getSqlQuery(sqlQuery);
    cursor.moveToFirst();
    while (cursor.moveToNext()) {
        String msgSubject = cursor.getString(cursor.getColumnIndex("tdm_subject"));
        String msgBody = cursor.getString(cursor.getColumnIndex("tdm_body"));
        System.out.println("MESSAGE: " + msgSubject + " - " + msgBody);
    }
    sqLiteDBCopyHelper.onCloseDatabase();
}
cursor.getString(0);
cursor.getCount()
if(cursor != null && cursor.getCount() > 0)
*/