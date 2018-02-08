package com.uyenpham.diploma.flashlight.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uyenpham.diploma.flashlight.model.App;
import com.uyenpham.diploma.flashlight.model.Contact;
import com.uyenpham.diploma.flashlight.model.FlashPatternt;

import java.util.ArrayList;

/**
 * Created by Ka on 12/6/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Flashlight";

    //Table_user
    private static final String TABLE_CONTACT = "Contact";
    private static final String COLUMN_CONTACT_ID = "id";
    private static final String COLUMN_CONTACT_NAME = "contact_name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_IS_FLASH_CALL = "isFlashCall";
    private static final String COLUMN_IS_FLASH_SMS = "isFlashSMS";
    private static final String COLUMN_PATTERN_CALL = "patternCall";
    private static final String COLUMN_PATTERN_SMS = "patternSMS";

    //table_APP
    private static final String TABLE_APP = "APP";
    private static final String COLUMN_APP_ID = "id";
    private static final String COLUMN_APP_NAME = "app_name";
    private static final String COLUMN_ICON_APP = "icon_app";
    private static final String COLUMN_IS_FLASH ="isFlash";
    private static final String COLUMN_PATTERN_APP ="pattern_app";

    //table_PATTERN
    private static final String TABLE_PATTERN = "PATTERN";
    private static final String COLUMN_PATTERN_ID = "pattern_id";
    private static final String COLUMN_PATTERN_NAME = "pattern_name";
    private static final String COLUMN_PATTERN_TIME_STR = "pattern_timestr";
    private static final String COLUMN_PATTERN_TIME = "pattern_time";
    private static final String COLUMN_PATTERN_TYPE = "pattern_type";

    private String CREATE_TABLE_CONTACT = "CREATE TABLE " + TABLE_CONTACT + "(" + COLUMN_CONTACT_ID
            + " TEXT PRIMARY KEY, " + COLUMN_CONTACT_NAME
            + " TEXT, " + COLUMN_NUMBER
            + " TEXT , " + COLUMN_IS_FLASH_CALL
            + " INTEGER , " + COLUMN_IS_FLASH_SMS
            + " INTEGER, " + COLUMN_PATTERN_CALL
            + " INTEGER, " + COLUMN_PATTERN_SMS
            + " INTEGER " + ")";

    private String CREATE_TABLE_APP = "CREATE TABLE " + TABLE_APP + "(" + COLUMN_APP_ID
            + " TEXT PRIMARY KEY , " + COLUMN_APP_NAME
            + " TEXT , " + COLUMN_ICON_APP
            + " BLOB , " + COLUMN_IS_FLASH
            + " INTEGER , " + COLUMN_PATTERN_APP
            + " INTEGER " + ")";

    private String CREATE_TABLE_PATTERN = "CREATE TABLE " + TABLE_PATTERN + "(" + COLUMN_PATTERN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PATTERN_NAME
            + " TEXT," + COLUMN_PATTERN_TIME_STR
            + " TEXT," + COLUMN_PATTERN_TIME
            + " INTEGER," + COLUMN_PATTERN_TYPE
            + " INTEGER " + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
        db.execSQL(CREATE_TABLE_APP);
        db.execSQL(CREATE_TABLE_PATTERN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATTERN);

        // reCreate.
        onCreate(db);
    }

    /**
     * insert data for table user
     *
     * @param contact contact object want to save
     * @return
     */
    public boolean insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CONTACT_ID, contact.getId());
        contentValues.put(COLUMN_CONTACT_NAME, contact.getName());
        contentValues.put(COLUMN_NUMBER, contact.getNumber());
        contentValues.put(COLUMN_IS_FLASH_CALL, contact.isFlashCall());
        contentValues.put(COLUMN_IS_FLASH_SMS, contact.isFlashSMS());
        contentValues.put(COLUMN_PATTERN_CALL, contact.getPatternCall());
        contentValues.put(COLUMN_PATTERN_SMS, contact.getPatternSMS());
        db.insert(TABLE_CONTACT, null, contentValues);
        return true;
    }

    /**
     * insert data for table user
     *
     * @param app contact object want to save
     * @return
     */
    public boolean insertApp(App app) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_APP_ID, app.getId());
        contentValues.put(COLUMN_APP_NAME, app.getName());
        contentValues.put(COLUMN_ICON_APP, CommonFuntions.getBytes(app.getIcon()));
        contentValues.put(COLUMN_IS_FLASH, app.isFlash());
        contentValues.put(COLUMN_PATTERN_APP, app.getPatternFlash());
        db.insert(TABLE_APP, null, contentValues);
        return true;
    }

    public boolean insertPattern(FlashPatternt patternt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATTERN_ID, patternt.getId());
        contentValues.put(COLUMN_PATTERN_NAME, patternt.getName());
        contentValues.put(COLUMN_PATTERN_TIME_STR, patternt.getTimeStr());
        contentValues.put(COLUMN_PATTERN_TIME, patternt.getTime());
        contentValues.put(COLUMN_PATTERN_TYPE, patternt.getTpye());
        db.insert(TABLE_PATTERN, null, contentValues);
        return true;
    }
    /**
     * delete a user in table user by id
     *
     * @param id id of user want to delete
     * @return
     */
    public boolean deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONTACT, COLUMN_CONTACT_ID + " =  '" + id + "'", null) > 0;
    }
    public boolean deleteApp(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_APP, COLUMN_APP_ID + " =  '" + id + "'", null) > 0;
    }

    public boolean deletePattern(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PATTERN, COLUMN_PATTERN_ID + " =  '" + id + "'", null) > 0;
    }
    /**
     * get user info
     *
     * @return contact objectF
     */
    public Contact getContactByID(String id) {
        Contact contact = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CONTACT +" WHERE "+COLUMN_CONTACT_ID+ " = '" + id +"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4)
                        , cursor.getInt(5), cursor.getInt(6));
            } while (cursor.moveToNext());
        }
        return contact;
    }
    /**
     * get user info
     *
     * @return contact objectF
     */
    public Contact getContactByNumber(String id) {
        Contact contact = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CONTACT +" WHERE "+COLUMN_NUMBER+ " = '" + id +"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4)
                        , cursor.getInt(5), cursor.getInt(6));
            } while (cursor.moveToNext());
        }
        return contact;
    }
    public App getAppByID(String id) {
        App app = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_APP +" WHERE "+COLUMN_APP_ID+ " = '" + id +"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                app = new App(cursor.getString(0), cursor.getString(1), CommonFuntions.getImage(cursor.getBlob(2)),
                        cursor.getInt(3), cursor.getInt(4));
            } while (cursor.moveToNext());
        }
        return app;
    }

    public FlashPatternt getPattertByID(int id) {
        FlashPatternt patternt = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_PATTERN +" WHERE "+COLUMN_PATTERN_ID+ " = '" + id +"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                patternt = new FlashPatternt(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4));
            } while (cursor.moveToNext());
        }
        return patternt;
    }
    public ArrayList<FlashPatternt> getPattertByType(int type) {
        ArrayList<FlashPatternt> patternt = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_PATTERN +" WHERE "+COLUMN_PATTERN_TYPE+ " = '" + type +"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                patternt.add(new FlashPatternt(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4)));
            } while (cursor.moveToNext());
        }
        return patternt;
    }

    /**
     * get number of user in table user
     *
     * @return total of user in table
     */
    public int getContactCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_CONTACT;
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
    public int getAppCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_APP;
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
    public int getPatternCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_PATTERN;
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
    /**
     * update info user in table user
     *
     * @param contact user info want to update
     * @return
     */
    public boolean updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CONTACT_ID, contact.getId());
        contentValues.put(COLUMN_CONTACT_NAME, contact.getName());
        contentValues.put(COLUMN_NUMBER, contact.getNumber());
        contentValues.put(COLUMN_IS_FLASH_CALL, contact.isFlashCall());
        contentValues.put(COLUMN_IS_FLASH_SMS, contact.isFlashSMS());
        contentValues.put(COLUMN_PATTERN_CALL, contact.getPatternCall());
        contentValues.put(COLUMN_PATTERN_SMS, contact.getPatternSMS());
        db.update(TABLE_CONTACT, contentValues, COLUMN_CONTACT_ID+" = ? ", new String[]{contact.getId()});
        return true;
    }

    public boolean updateApp(App app) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_APP_ID, app.getId());
        contentValues.put(COLUMN_APP_NAME, app.getName());
        contentValues.put(COLUMN_ICON_APP, CommonFuntions.getBytes(app.getIcon()));
        contentValues.put(COLUMN_IS_FLASH, app.isFlash());
        contentValues.put(COLUMN_PATTERN_APP, app.getPatternFlash());
        db.update(TABLE_APP, contentValues, COLUMN_APP_ID+" = ? ", new String[]{app.getId()});
        return true;
    }
    public boolean updatePattern(FlashPatternt patternt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATTERN_ID, patternt.getId());
        contentValues.put(COLUMN_PATTERN_NAME, patternt.getName());
        contentValues.put(COLUMN_PATTERN_TIME_STR, patternt.getTimeStr());
        contentValues.put(COLUMN_PATTERN_TIME, patternt.getTime());
        contentValues.put(COLUMN_PATTERN_TYPE, patternt.getTpye());
        db.update(TABLE_PATTERN, contentValues, COLUMN_PATTERN_ID+" = ? ", new String[]{patternt.getId()+""});
        return true;
    }
    /**
     * get APP send to server info
     *
     * @return InfoAPPForServer object
     */
    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CONTACT;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4)
                        , cursor.getInt(5), cursor.getInt(6));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public ArrayList<FlashPatternt> getAllPattern() {
        ArrayList<FlashPatternt> patternList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_PATTERN;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FlashPatternt patternt = new FlashPatternt(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getInt(4));
                patternList.add(patternt);
            } while (cursor.moveToNext());
        }
        return patternList;
    }
    public ArrayList<App> getAllApp() {
        ArrayList<App> appList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_APP;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                App app = new App(cursor.getString(0), cursor.getString(1), CommonFuntions.getImage(cursor.getBlob(2)),
                        cursor.getInt(3), cursor.getInt(4));
                appList.add(app);
            } while (cursor.moveToNext());
        }
        return appList;
    }

    public void deleteAllContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_CONTACT);
    }
    public void deleteAllApp() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_APP);
    }
}
