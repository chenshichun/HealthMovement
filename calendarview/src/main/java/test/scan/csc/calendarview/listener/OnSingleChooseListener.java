package test.scan.csc.calendarview.listener;

import android.view.View;

import test.scan.csc.calendarview.bean.DateBean;

/**
 * 日期点击接口
 */
public interface OnSingleChooseListener {
    /**
     * @param view
     * @param date
     */
    void onSingleChoose(View view, DateBean date);
}
