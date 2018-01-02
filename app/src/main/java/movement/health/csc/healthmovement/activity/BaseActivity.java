package movement.health.csc.healthmovement.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

import movement.health.csc.healthmovement.R;

/**
 * Created by csc on 17-10-16.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener {
    public static boolean DEBUG = true;
    public static int[] colorBbackground, colorBbackgroundDeep, kungfuPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        colorBbackground = getResources().getIntArray(R.array.color_background);
        colorBbackgroundDeep = getResources().getIntArray(R.array.color_background_deep);
        TypedArray array = getResources().obtainTypedArray(R.array.kung_fu_pic);
        int len = array.length();
        kungfuPic = new int[len];
        for (int i = 0; i < len; i++) {
            kungfuPic[i] = array.getResourceId(i, 0);
        }
        setBackgroundColor();
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public abstract void initData();

    public abstract void initView();

    public abstract void setBackgroundColor();
}
