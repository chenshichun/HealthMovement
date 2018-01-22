package test.scan.csc.calendarview.listener;

import android.view.View;
import android.widget.TextView;

import test.scan.csc.calendarview.bean.DateBean;


public interface CalendarViewAdapter {
    /**
     * 返回阳历、阴历两个TextView
     *
     * @param view
     * @param date
     * @return
     */
    TextView[] convertView(View view, DateBean date);
}
