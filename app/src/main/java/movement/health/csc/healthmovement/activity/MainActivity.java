package movement.health.csc.healthmovement.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.utils.Utils;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_header_rl)
    RelativeLayout mainHeaderRl;
    @BindView(R.id.viewpage_title)
    TextView viewpageTitle;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.start_exercisePoint)
    ImageView startExercisePointIv;
    @BindView(R.id.customize_exercisePoint)
    ImageView customizeExercisePointIv;
    @BindView(R.id.remind_text)
    TextView remindText;
    @BindView(R.id.setting_btn)
    ImageButton settingBtn;
    @BindView(R.id.choose_practiceIb)
    ImageButton choosePracticeBtn;
    @BindView(R.id.main_bottomRl)
    RelativeLayout mainBottomRl;
    @BindView(R.id.main_rl)
    RelativeLayout mainRl;

    private ArrayList<View> pageview;
    private SharedPreferences settingsSp;
    View startExerciseView, customizeExercisingView;
    LinearLayout repidExercisingLl,everyDayExercisingEl,difficultExercisingLl,penitenceExercisingLl;
    LinearLayout newExercisingLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    //数据适配器
    PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        //获取当前窗体界面数
        public int getCount() {
            return pageview.size();
        }

        @Override
        //断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //是从ViewGroup中移出当前View
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(pageview.get(arg1));
        }

        //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(pageview.get(arg1));
            return pageview.get(arg1);
        }
    };

    @Override
    public void initData() {


        pageview = new ArrayList<View>();
        pageview.add(startExerciseView);
        pageview.add(customizeExercisingView);

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewpageTitle.setText(R.string.start_exercising);
                        startExercisePointIv.setBackgroundResource(R.drawable.iphone_dian_light_white);
                        customizeExercisePointIv.setBackgroundResource(R.drawable.iphone_dian_gray);
                        remindText.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        viewpageTitle.setText(R.string.customize_exercising);
                        startExercisePointIv.setBackgroundResource(R.drawable.iphone_dian_gray);
                        customizeExercisePointIv.setBackgroundResource(R.drawable.iphone_dian_light_white);
                        remindText.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initView() {

        LayoutInflater inflater = getLayoutInflater();
        customizeExercisingView = inflater.inflate(R.layout.viewpage_customize_exercising_foot_view, null);
        newExercisingLl = ButterKnife.findById(customizeExercisingView,R.id.new_exercising_ll);
        newExercisingLl.setOnClickListener(this);

        startExerciseView = inflater.inflate(R.layout.viewpage_start_exercising, null);
        repidExercisingLl = ButterKnife.findById(startExerciseView,R.id.repid_exercising_ll);

        repidExercisingLl.setOnClickListener(this);
        everyDayExercisingEl = ButterKnife.findById(startExerciseView,R.id.every_day_exercising_ll);
        everyDayExercisingEl.setOnClickListener(this);
        difficultExercisingLl = ButterKnife.findById(startExerciseView,R.id.difficult_exercising_ll);
        difficultExercisingLl.setOnClickListener(this);
        penitenceExercisingLl = ButterKnife.findById(startExerciseView,R.id.penitence_exercising_ll);
        penitenceExercisingLl.setOnClickListener(this);
    }

    @Override
    public void setBackgroundColor() {
        settingsSp = getSharedPreferences(Utils.SETTING_INFOS, 0);
        mainRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
        mainHeaderRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        getWindow().setStatusBarColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
    }

    @OnClick({R.id.main_header_rl, R.id.viewpage_title, R.id.viewpager, R.id.start_exercisePoint, R.id.customize_exercisePoint,
            R.id.remind_text, R.id.setting_btn, R.id.choose_practiceIb, R.id.main_bottomRl, R.id.main_rl })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_header_rl:
                break;
            case R.id.viewpage_title:
                break;
            case R.id.viewpager:
                break;
            case R.id.start_exercisePoint:
                break;
            case R.id.customize_exercisePoint:
                break;
            case R.id.remind_text:
                break;
            case R.id.setting_btn:
                startActivity(SettingActivity.class, null);
                break;
            case R.id.choose_practiceIb:
                startActivity(ChoosePracticeActivity.class, null);
                break;
            case R.id.main_bottomRl:
                break;
            case R.id.main_rl:
                break;
            case R.id.repid_exercising_ll:
            case R.id.every_day_exercising_ll:
            case R.id.difficult_exercising_ll:
            case R.id.penitence_exercising_ll:
                startActivity(StartExercisingActivity.class, null);
                break;
            case R.id.new_exercising_ll:
                startActivity(NewExercisingActivity.class, null);
                break;
        }
    }
}
