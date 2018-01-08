package movement.health.csc.healthmovement.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by csc on 17-10-13.
 */

public class Utils {
    public static final String SETTING_INFOS = "SETTINGInfos";
    public static final String COLOR_BACKGROUND_DEEP = "COLOR_BACKGROUND_DEEP";
    public static final String COLOR_BACKGROUND = "COLOR_BACKGROUND";
    public static final String SPORT_IMAGE = "SPORT_IMAGE";
    public static final String SPORT_TITLE = "SPORT_TITLE";

    public static final String START_SPORT_IMAGE = "START_SPORT_IMAGE";
    public static final String CIRCLE_PROGRESS = "CIRCLE_PROGRESS";

    public static final String EXERCISING_NAME_PIC = "EXERCISING_NAME_PIC";
    public static final String EXERCISING_NAME_TEXT = "EXERCISING_NAME_TEXT";

    public static final String DB_ID = "_id";
    public static final String DB_PIC_NAME = "pic_name";
    public static final String DB_EXERCISEING_NUM = "exerciseing_num";
    public static final String DB_EXERCISEING_TABLE = "exerciseing_table";


    public static final String DB_CUSTOMIZE_NAME = "db_customize_name";
    public static final String DB_CUSTOMIZE_TIME = "db_customize_time";

    public static final String MAP_ID = "ID";
    public static final String MAP_PIC_NAME = "PIC_NAME";
    public static final String MAP_EXERCISEING_NUM = "EXERCISEING_NUM";

    public static final String CUSTOMIZE_MAP_ID = "CUSTOMIZE_MAP_ID";
    public static final String CUSTOMIZE_MAP_NAME = "CUSTOMIZE_MAP_NAME";
    public static final String CUSTOMIZE_MAP_TIME = "CUSTOMIZE_MAP_TIME";

    /*
    * SharedPreferences 存数组
    * */
    public static void setSharedPreference(Context mContext, String key, String[] values) {
        String regularEx = "#";
        String str = "";
        SharedPreferences sp = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (values != null && values.length > 0) {
            for (String value : values) {
                str += value;
                str += regularEx;
            }
            SharedPreferences.Editor et = sp.edit();
            et.putString(key, str);
            et.commit();
        }
    }

    /*
    * SharedPreferences 取数组
    * */
    public static String[] getSharedPreference(Context mContext,String key) {
        String regularEx = "#";
        String[] str = null;
        SharedPreferences sp = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        String values;
        values = sp.getString(key, "");
        str = values.split(regularEx);

        return str;
    }
}


