package movement.health.csc.healthmovement.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.adapter.CustomizeExerciseingManagementAdapter;
import movement.health.csc.healthmovement.sqlite.SQLHelper;
import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 18-1-15.
 */

public class CustomExercisingManagementActivity extends BaseActivity {
    @BindView(R.id.mangement_ll)
    LinearLayout mangementLl;
    @BindView(R.id.mangement_header_rl)
    RelativeLayout managementHeaderRl;
    @BindView(R.id.ic_cancel_ib)
    ImageButton mCancelIb;
    @BindView(R.id.ic_add_ib)
    ImageButton mAddIb;
    @BindView(R.id.customize_exerciseing_management_list_view)
    ListView mCustomizeExerciseingManagementListView;

    private SharedPreferences settingsSp;
    private CustomizeExerciseingManagementAdapter mCustomizeExerciseingManagementAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_exercising_management);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        mCustomizeExerciseingManagementAdapter = new CustomizeExerciseingManagementAdapter(this, SQLHelper.queryAllCustomizeMessage(this));
        mCustomizeExerciseingManagementListView.setAdapter(mCustomizeExerciseingManagementAdapter);
        setListViewHeightBasedOnChildren(mCustomizeExerciseingManagementListView);
    }

    @Override
    public void initView() {

    }

    @Override
    public void setBackgroundColor() {
        settingsSp = getSharedPreferences(Utils.SETTING_INFOS, 0);
        mangementLl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
        managementHeaderRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        mCustomizeExerciseingManagementListView.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
    }


    @OnClick({R.id.ic_cancel_ib, R.id.ic_add_ib})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_cancel_ib:
                finish();
                break;
            case R.id.ic_add_ib:
                Bundle bundle = new Bundle();
                bundle.putInt("sql_position", 0);
                bundle.putInt("list_view_count", (mCustomizeExerciseingManagementListView.getCount()+1));
                bundle.putString("item_name", getResources().getString(R.string.customize_exerc) + (mCustomizeExerciseingManagementListView.getCount()+1));
                startActivity(NewExercisingActivity.class, bundle);
                finish();
                break;
        }
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {

        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
