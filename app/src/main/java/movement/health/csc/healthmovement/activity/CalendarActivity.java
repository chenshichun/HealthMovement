package movement.health.csc.healthmovement.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import movement.health.csc.healthmovement.R;
import test.scan.csc.calendarview.listener.OnPagerChangeListener;
import test.scan.csc.calendarview.weiget.CalendarView;

/**
 * Created by csc on 18-1-22.
 */

public class CalendarActivity extends BaseActivity {
    @BindView(R.id.calendar)
    CalendarView mCalendarView;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        title.setText(2017 + "年" + 11 + "月");
        List<String> list = new ArrayList<>();
        list.add("2017.11.11");
        list.add("2017.11.12");
        list.add("2017.12.22");
        list.add("2017.12.25");

        mCalendarView
                .setStartEndDate("2017.1", "2019.12")
                .setDisableStartEndDate("2017.10.7", "2019.10.7")
                .setInitDate("2017.11")
                .setMultiDate(list)
                .init();

        mCalendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void setBackgroundColor() {

    }

    public void lastMonth(View view) {
        mCalendarView.lastMonth();
    }

    public void nextMonth(View view) {
        mCalendarView.nextMonth();
    }
}
