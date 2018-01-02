package movement.health.csc.healthmovement.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.adapter.StartExerciseGalleryViewAdapter;
import movement.health.csc.healthmovement.adapter.StartExerciseGridViewAdapter;
import movement.health.csc.healthmovement.utils.Utils;
import movement.health.csc.healthmovement.view.CircleProgressView;
import movement.health.csc.healthmovement.view.EcoGallery;
import movement.health.csc.healthmovement.view.EcoGalleryAdapterView;
import movement.health.csc.healthmovement.view.GalleryView;

/**
 * Created by csc on 17-10-16.
 */

public class StartExercisingActivity extends BaseActivity {
    @BindView(R.id.ic_cancel_ib)
    ImageButton icCancelIb;
    @BindView(R.id.start_exercise_header_rl)
    RelativeLayout mStartExerciseHeaderRl;
    @BindView(R.id.circle_progress_view)
    CircleProgressView mCircleProgressView;
    @BindView(R.id.kungfu_iv)
    ImageView kungfuIv;
    @BindView(R.id.countdown_num)
    TextView countdownNum;
    @BindView(R.id.mygallery)
    GalleryView mStartExerciseGalleryView;
    @BindView(R.id.eco_gallery)
    EcoGallery ecoGallery;
    @BindView(R.id.start_exercise_gridview)
    GridView mStartExerciseGridview;
    @BindView(R.id.start_exercise_bottom_rl)
    RelativeLayout mStartExerciseBottomRl;
    @BindView(R.id.root)
    RelativeLayout mRoot;
    @BindView(R.id.chronometer)
    Chronometer chronometer;
    private SharedPreferences settingsSp;
    private int currentButtonProgress, currentProgress;
    private boolean isStartCountdown = false;
    private StartExerciseGalleryViewAdapter mStartExerciseGalleryViewAdapter;
    private SampleAdapter mAdapter;
    private long recordingTime = 0;// 记录下来的总时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercising);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mCircleProgressView.setProgress(0);
        mCircleProgressView.setProgressBottom(0);
        mCircleProgressView.setProgressColor(Color.rgb(0xff, 0xff, 0xff));
        mCircleProgressView.setProgressBottomColor(Color.rgb(0x3f, 0x3e, 0x3f));
        mCircleProgressView.setmShowProgress(0, 100, true);

        mCircleProgressView.setCircleLineStrokeWidth(20);
        timer.schedule(task, 1000, 800); // 1s后执行task,经过1s再次执行
        mStartExerciseGridview.setAdapter(new StartExerciseGridViewAdapter(this, getData()));

        mStartExerciseGalleryViewAdapter = new StartExerciseGalleryViewAdapter(this);
//      mStartExerciseGalleryViewAdapter.createReflectedImages();    // 创建倒影效果
        mStartExerciseGalleryView.setAdapter(mStartExerciseGalleryViewAdapter);
        mStartExerciseGalleryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (DEBUG)
                mStartExerciseGalleryViewAdapter.setSelectItem(position);
                mStartExerciseGalleryViewAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new SampleAdapter();
        ecoGallery.setAdapter(mAdapter);
        ecoGallery.setOnItemClickListener(new EcoGalleryAdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(EcoGalleryAdapterView<?> parent, View view, int position, long id) {
                Log.d("chenshichun", " " + this.getClass().getCanonicalName() + " ::::::position:::" + position);

            }
        });
        chronometer.setFormat("%s");
    }

    int circleProgress[] = {20, 30, 40, 50, 60, 70, 80};

    private ArrayList<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        String sportsName[] = getResources().getStringArray(R.array.sports_name);
        for (int i = 0; i < kungfuPic.length; i++) {
            HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
            tempHashMap.put(Utils.START_SPORT_IMAGE, kungfuPic[i]);
            tempHashMap.put(Utils.CIRCLE_PROGRESS, circleProgress[i]);
            arrayList.add(tempHashMap);
        }
        return arrayList;
    }


    @Override
    public void setBackgroundColor() {
        settingsSp = getSharedPreferences(Utils.SETTING_INFOS, 0);
        mRoot.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
        getWindow().setStatusBarColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!isStartCountdown) {
                        mCircleProgressView.setmShowProgress(currentProgress, currentProgress + 20, false);
                        currentProgress = currentProgress + 20;

                        switch (currentProgress) {
                            case 20:
                                kungfuIv.setImageResource(R.drawable.kung_fu0_normal);
                                break;
                            case 40:
                                kungfuIv.setImageResource(R.drawable.kung_fu1_normal);
                                break;
                            case 60:
                                kungfuIv.setImageResource(R.drawable.kung_fu2_normal);
                                break;
                            case 80:
                                kungfuIv.setImageResource(R.drawable.kung_fu3_normal);
                                break;
                            case 100:
                                kungfuIv.setImageResource(R.drawable.kung_fu4_normal);
                                isStartCountdown = true;
                                break;
                        }
                    } else {
                        kungfuIv.setVisibility(View.GONE);
                        mCircleProgressView.setProgressBottom(0);
                        currentProgress = currentProgress - 34;

                        if (currentProgress < 0)
                            currentProgress = 0;
                        if (currentProgress > 0)
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        mCircleProgressView.setmShowProgress(currentProgress + 34, currentProgress, false);

                        switch (currentProgress) {
                            case 66:
                                countdownNum.setText("3");
                                break;
                            case 32:
                                countdownNum.setText("2");
                                mStartExerciseHeaderRl.setVisibility(View.VISIBLE);
                                mStartExerciseHeaderRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
                                mStartExerciseBottomRl.setVisibility(View.VISIBLE);
                                mStartExerciseBottomRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
                                getWindow().setStatusBarColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
                                break;
                            case 0:
                                countdownNum.setText("1");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        countdownNum.setVisibility(View.GONE);
                                        ecoGallery.setVisibility(View.VISIBLE);
                                        onRecordStart();
                                    }
                                }, 300);
                                timer.cancel();
                                task.cancel();
                                break;
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @OnClick(R.id.ic_cancel_ib)
    public void onClick() {
        finish();
    }


    public class SampleAdapter extends BaseAdapter {

        public SampleAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_start_exercise_gallery_view, parent, false);
            }

            ImageView iv = (ImageView) convertView.findViewById(R.id.sports_iv);
            CircleProgressView mCircleProgressView = (CircleProgressView) convertView.findViewById(R.id.item_circle_progress_view);
            mCircleProgressView.setProgressBottomColor(Color.rgb(0x3f, 0x3e, 0x3f));
            mCircleProgressView.setProgressBottom(100);
            int resid = getResources().getIdentifier("kung_fu" + position % 10 + "_normal", "drawable",
                    parent.getContext().getPackageName());
            iv.setImageResource(resid);
            return convertView;
        }
    }

    public void onRecordStart() {
        chronometer.setBase(SystemClock.elapsedRealtime() - recordingTime);// 跳过已经记录了的时间，起到继续计时的作用
        chronometer.start();
    }

    public void onRecordPause() {
        chronometer.stop();
        recordingTime = SystemClock.elapsedRealtime()
                - chronometer.getBase();// 保存这次记录了的时间
    }

    public void onRecordStop() {
        recordingTime = 0;
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

}
