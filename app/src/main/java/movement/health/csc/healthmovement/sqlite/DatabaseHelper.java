package movement.health.csc.healthmovement.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by csc on 18-1-2.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE new_exerciseing_table(_id INTEGER PRIMARY KEY AUTOINCREMENT, pic_name int ,exerciseing_num int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
