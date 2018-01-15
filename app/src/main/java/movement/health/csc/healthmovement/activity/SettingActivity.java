package movement.health.csc.healthmovement.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 17-10-13.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ic_cancel_ib)
    ImageButton icCancelIb;
    @BindView(R.id.setting_header_rl)
    RelativeLayout settingHeaderRl;
    @BindView(R.id.orangeGroupID)
    RadioButton orangeRadioButton;
    @BindView(R.id.greenGroupID)
    RadioButton greenRadioButton;
    @BindView(R.id.blueGroupID)
    RadioButton blueRadioButton;
    @BindView(R.id.redGroupID)
    RadioButton redRadioButton;
    @BindView(R.id.pinkGroupID)
    RadioButton pinkRadioButton;
    @BindView(R.id.blackGroupID)
    RadioButton blackRadioButton;
    @BindView(R.id.radioGroupID)
    RadioGroup radioGroup;
    @BindView(R.id.activity_setting_ll)
    LinearLayout activitySettingLl;
    private SharedPreferences settingsSp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }


    @Override
    public void initData() {
        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());
        settingsSp = getSharedPreferences(Utils.SETTING_INFOS, 0);
    }

    @Override
    public void initView() {

    }

    @Override
    public void setBackgroundColor() {
        activitySettingLl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
        radioGroup.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        settingHeaderRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        getWindow().setStatusBarColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
    }

    @OnClick(R.id.ic_cancel_ib)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_cancel_ib:
                finish();
                break;
        }
    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.orangeGroupID:
                    settingsSp.edit().putInt(Utils.COLOR_BACKGROUND, colorBbackground[0])
                            .putInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]).commit();
                    break;
                case R.id.greenGroupID:
                    settingsSp.edit().putInt(Utils.COLOR_BACKGROUND, colorBbackground[1])
                            .putInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[1]).commit();
                    break;
                case R.id.blueGroupID:
                    settingsSp.edit().putInt(Utils.COLOR_BACKGROUND, colorBbackground[2])
                            .putInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[2]).commit();
                    break;
                case R.id.redGroupID:
                    settingsSp.edit().putInt(Utils.COLOR_BACKGROUND, colorBbackground[3])
                            .putInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[3]).commit();
                    break;
                case R.id.pinkGroupID:
                    settingsSp.edit().putInt(Utils.COLOR_BACKGROUND, colorBbackground[4])
                            .putInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[4]).commit();
                    break;
                case R.id.blackGroupID:
                    settingsSp.edit().putInt(Utils.COLOR_BACKGROUND, colorBbackground[5])
                            .putInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[5]).commit();
                    break;
            }

            setBackgroundColor();
        }
    }

}
