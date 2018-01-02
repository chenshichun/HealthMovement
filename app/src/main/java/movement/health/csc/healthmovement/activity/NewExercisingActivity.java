package movement.health.csc.healthmovement.activity;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.adapter.ExercisingNameGridViewAdapter;
import movement.health.csc.healthmovement.adapter.NewExercisingListViewAdapter;
import movement.health.csc.healthmovement.sqlite.SQLHelper;
import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 17-12-15.
 */

public class NewExercisingActivity extends BaseActivity {
    @BindView(R.id.new_exercising_ll)
    LinearLayout newExercisingLl;
    @BindView(R.id.new_exercising_main_header_rl)
    RelativeLayout mainHeaderRl;
    @BindView(R.id.new_exercising_cancel)
    TextView newExecrcisingCancel;
    @BindView(R.id.customize_gridview)
    GridView customizeGridView;
    @BindView(R.id.exercising_name_gallery)
    Gallery exercisingNameGallery;
    @BindView(R.id.title_name_et)
    EditText titleNameEt;
    @BindView(R.id.word_count_limit_tv)
    TextView wordCountLimitTv;
    @BindView(R.id.new_exercising_list_view)
    ListView newExercisingListView;

    private SharedPreferences settingsSp;
    private List<Map<String, Object>> customizeList;
    private ArrayList<HashMap<String, Object>> exercisingNameList;
    private SimpleAdapter customizeAdapter;
    private String[] numText, itemText;
    private int itemPic[];
    private String[] itemNameText;
    private ExercisingNameGridViewAdapter mExercisingNameGridViewAdapter;
    private NewExercisingListViewAdapter mNewExercisingListViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercising);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void initData() {
        numText = getResources().getStringArray(R.array.num_text);
        itemText = getResources().getStringArray(R.array.practice_difficulty);
        customizeList = new ArrayList<Map<String, Object>>();
        getCustomizeData();
        String[] customizeFrom = {"customize_num_text", "customize_item_text"};
        int[] customizeTo = {R.id.item_num, R.id.item_text};
        customizeAdapter = new SimpleAdapter(this, customizeList, R.layout.customize_gridview_item, customizeFrom, customizeTo);
        customizeGridView.setAdapter(customizeAdapter);

        TypedArray array = getResources().obtainTypedArray(R.array.exercising_pic);
        int len = array.length();
        itemPic = new int[len];
        for (int i = 0; i < len; i++) {
            itemPic[i] = array.getResourceId(i, 0);
        }

        itemNameText = getResources().getStringArray(R.array.sports_name);
        mExercisingNameGridViewAdapter = new ExercisingNameGridViewAdapter(this, exercisingNameData());
        exercisingNameGallery.setAdapter(mExercisingNameGridViewAdapter);

        exercisingNameGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SQLHelper.insertSqlite(getApplicationContext(), position, 5);
                mNewExercisingListViewAdapter = new NewExercisingListViewAdapter(getApplicationContext(),
                        SQLHelper.queryAllMessage(getApplicationContext()));
                newExercisingListView.setAdapter(mNewExercisingListViewAdapter);
                setListViewHeightBasedOnChildren(newExercisingListView);
            }
        });

        changeNum();

        titleNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeNum();
            }
        });

        SQLHelper.createSql(this);
        mNewExercisingListViewAdapter = new NewExercisingListViewAdapter(getApplicationContext(),
                SQLHelper.queryAllMessage(getApplicationContext()));
        newExercisingListView.setAdapter(mNewExercisingListViewAdapter);
        setListViewHeightBasedOnChildren(newExercisingListView);
    }

    private void changeNum() {
        if (titleNameEt.getText().length() > 0) {
            wordCountLimitTv.setText(getString(R.string.word_count_limit, titleNameEt.getText().length()));
        } else {
            wordCountLimitTv.setText("");
        }
    }

    @Override
    public void initView() {

    }

    public List<Map<String, Object>> getCustomizeData() {
        for (int i = 0; i < itemText.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("customize_num_text", numText[i]);
            map.put("customize_item_text", itemText[i]);
            customizeList.add(map);
        }

        return customizeList;
    }

    private ArrayList<HashMap<String, Object>> exercisingNameData() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < itemPic.length; i++) {
            HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
            tempHashMap.put(Utils.EXERCISING_NAME_TEXT, itemNameText[i]);
            tempHashMap.put(Utils.EXERCISING_NAME_PIC, itemPic[i]);
            arrayList.add(tempHashMap);
        }
        return arrayList;
    }

    @Override
    public void setBackgroundColor() {
        settingsSp = getSharedPreferences(Utils.SETTING_INFOS, 0);
        mainHeaderRl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        titleNameEt.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        exercisingNameGallery.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
        newExercisingListView.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        newExercisingLl.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND, colorBbackground[0]));
        getWindow().setStatusBarColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
    }

    @OnClick(R.id.new_exercising_cancel)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_exercising_cancel:
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
