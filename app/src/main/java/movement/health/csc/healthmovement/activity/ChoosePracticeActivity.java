package movement.health.csc.healthmovement.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.adapter.ChoosePracticeAdapter;
import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 17-10-16.
 */

public class ChoosePracticeActivity extends BaseActivity {

    @BindView(R.id.done)
    TextView done;
    @BindView(R.id.main_header_rl)
    RelativeLayout mainHeaderRl;
    @BindView(R.id.choose_practice_gridview)
    GridView mGridView;
    @BindView(R.id.choose_practice_Rl)
    LinearLayout choosePracticeRl;

    private SharedPreferences settingsSp;
    private ChoosePracticeAdapter mChoosePracticeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_practice);
        ButterKnife.bind(this);
        initData();
    }


    @Override
    public void initData() {
        ArrayList<HashMap<String, Object>> data = getData();
        mChoosePracticeAdapter = new ChoosePracticeAdapter(this, data);
        mGridView.setAdapter(mChoosePracticeAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView sportImageView = (ImageView) view.findViewById(R.id.sport_iv);

                if (sportImageView.isSelected() == false) {
                    sportImageView.setSelected(true);
                    sportImageView.setPressed(true);
                } else {
                    sportImageView.setSelected(false);
                    sportImageView.setPressed(false);

                }
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void setBackgroundColor() {
        settingsSp = getSharedPreferences(Utils.SETTING_INFOS, 0);
        mainHeaderRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        choosePracticeRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
        getWindow().setStatusBarColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
    }

    private ArrayList<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        String sportsName[] = getResources().getStringArray(R.array.sports_name);
        for (int i = 0; i < kungfuPic.length; i++) {
            HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
            tempHashMap.put(Utils.SPORT_IMAGE, kungfuPic[i]);
            tempHashMap.put(Utils.SPORT_TITLE, sportsName[i]);
            arrayList.add(tempHashMap);
        }
        return arrayList;
    }

    @OnClick({R.id.done, R.id.main_header_rl, R.id.choose_practice_Rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.done:
                finish();
                break;
        }
    }
}
