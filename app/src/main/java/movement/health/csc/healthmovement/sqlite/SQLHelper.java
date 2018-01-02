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
    private static String SQL_NAME = "new_exercise_db";
    private static String DB_TABLE = "new_exerciseing_table";


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
    public static void insertSqlite(Context context, int picName, int num) {
        ContentValues values = new ContentValues();
        values.put(Utils.DB_PIC_NAME, picName);
        values.put(Utils.DB_EXERCISEING_NUM, num);
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DB_TABLE, null, values);
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
     * 查询表中所有的数据
     * 返回一个list
     */
    public static List<Map<String, String>> queryAllMessage(Context context) {

        List<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null, null);
        Log.d("chenshichun"," ::::::cursor:::"+cursor);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String _id = cursor.getString(cursor.getColumnIndex(Utils.DB_ID));
                String pic_name = cursor.getString(cursor.getColumnIndex(Utils.DB_PIC_NAME));
                String exerciseing_num = cursor.getString(cursor.getColumnIndex(Utils.DB_EXERCISEING_NUM));
                Log.d("chenshichun"," ::::::_id:::"+_id+"  pic_name:: "+pic_name +" exerciseing_num:: "+exerciseing_num);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Utils.MAP_ID, _id);
                map.put(Utils.MAP_PIC_NAME, pic_name);
                map.put(Utils.MAP_EXERCISEING_NUM, exerciseing_num);
                tempList.add(map);
            }
            db.close();
        }
        return tempList;
    }


    public static void deleteAll(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, SQL_NAME);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
        db.close();
    }
}
