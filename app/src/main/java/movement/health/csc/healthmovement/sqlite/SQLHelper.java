package movement.health.csc.healthmovement.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 18-1-2.
 */

public class SQLHelper {
    private static String SQL_NAME = "new_exerciseing_db";
    private static String DB_TABLE = "new_exerciseing_table";
    private static String CUSTOMIZE_TABLE = "customize_exerciseing_table";

    /**
     * 创建数据库
     */
    public static void createSql(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        @SuppressWarnings("unused")
        SQLiteDatabase db = dbHelper.getReadableDatabase();
    }

    /**
     * 插入数据
     */
    public static void insertSqlite(Context context, int picName, int num, int table_num) {
        ContentValues values = new ContentValues();
        values.put(Utils.DB_PIC_NAME, picName);
        values.put(Utils.DB_EXERCISEING_NUM, num);
        values.put(Utils.DB_EXERCISEING_TABLE, table_num);
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DB_TABLE, null, values);
        db.close();
    }

    /**
     * 插入自定义列表数据
     */
    public static void insertCustomizeSqlite(Context context, String customizeName, String customizeTime) {
        ContentValues values = new ContentValues();
        values.put(Utils.DB_CUSTOMIZE_NAME, customizeName);
        values.put(Utils.DB_CUSTOMIZE_TIME, customizeTime);
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(CUSTOMIZE_TABLE, null, values);
        db.close();
    }

    /**
     * 删除数据
     */
    public static void deleteDetailDate(Context context, String id) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DB_TABLE, "_id=?", new String[]{id});
        db.close();
    }

    /**
     * 删除自定义列表数据
     */
    public static void deleteCustomizeDate(Context context, String id) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(CUSTOMIZE_TABLE, "_id=?", new String[]{id});
        db.close();
    }

    /**
     * 修改
     */
    public static void updateCustomDate(Context context, String id,
                                        String customizeName, String customizeTime) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utils.DB_CUSTOMIZE_NAME, customizeName);
        values.put(Utils.DB_CUSTOMIZE_TIME, customizeTime);
        db.update(CUSTOMIZE_TABLE, values,
                "_id=?", new String[]{id});
        db.close();
    }

    /**
     * 查询表中所有的数据
     * 返回一个list
     */
    public static List<Map<String, String>> queryAllMessage(Context context, int tableNum) {

        List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String _id = cursor.getString(cursor.getColumnIndex(Utils.DB_ID));
                String pic_name = cursor.getString(cursor.getColumnIndex(Utils.DB_PIC_NAME));
                String exerciseing_num = cursor.getString(cursor.getColumnIndex(Utils.DB_EXERCISEING_NUM));
                String exerciseing_table_num = cursor.getString(cursor.getColumnIndex(Utils.DB_EXERCISEING_TABLE));

                if (exerciseing_table_num.equals("" + tableNum)) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(Utils.MAP_ID, _id);
                    map.put(Utils.MAP_PIC_NAME, pic_name);
                    tempList.add(map);
                    map.put(Utils.MAP_EXERCISEING_NUM, exerciseing_num);
                }
            }
            db.close();
        }
        return tempList;
    }

    /**
     * 查询自定义表中所有的数据
     * 返回一个list
     */
    public static List<Map<String, String>> queryAllCustomizeMessage(Context context) {

        List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(CUSTOMIZE_TABLE, null, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String _id = cursor.getString(cursor.getColumnIndex(Utils.DB_ID));
                String pic_name = cursor.getString(cursor.getColumnIndex(Utils.DB_CUSTOMIZE_NAME));
                String exerciseing_num = cursor.getString(cursor.getColumnIndex(Utils.DB_CUSTOMIZE_TIME));

                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Utils.CUSTOMIZE_MAP_ID, _id);
                map.put(Utils.CUSTOMIZE_MAP_NAME, pic_name);
                map.put(Utils.CUSTOMIZE_MAP_TIME, exerciseing_num);
                tempList.add(map);
            }
            db.close();
        }
        return tempList;
    }


    public static void deleteTable(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
        db.close();
    }
}
