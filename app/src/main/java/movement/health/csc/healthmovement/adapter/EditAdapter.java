package movement.health.csc.healthmovement.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.utils.Utils;
import movement.health.csc.healthmovement.view.EditLayout;

/**
 * Created by csc on 18-1-31.
 */

public class EditAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private boolean isEdit;  //是否处于编辑状态
    private List<EditLayout> allItems = new ArrayList<>();
    private EditLayout mRightOpenItem;  //向右展开的删除项，只会存在一项

    private int itemPic[];
    private String[] itemNameText;
    List<Map<String, String>> data;
    private SharedPreferences settingsSp;
    public static int[] colorBbackgroundDeep;

    public EditAdapter(Context context, List<Map<String, String>> data) {

        this.mContext = context;
        this.data = data;
        TypedArray array = this.mContext.getResources().obtainTypedArray(R.array.exercising_pic);
        int len = array.length();
        itemPic = new int[len];
        for (int i = 0; i < len; i++) {
            itemPic[i] = array.getResourceId(i, 0);
        }
        itemNameText = this.mContext.getResources().getStringArray(R.array.sports_name);

        colorBbackgroundDeep = mContext.getResources().getIntArray(R.array.color_background_deep);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final EditLayout editLayout = viewHolder.editLayout;

        if (!allItems.contains(editLayout)) {
            allItems.add(editLayout);
        }

        editLayout.setEdit(isEdit);

        settingsSp = mContext.getSharedPreferences(Utils.SETTING_INFOS, 0);
        viewHolder.itemContext.setBackgroundColor(settingsSp.getInt(Utils.COLOR_BACKGROUND_DEEP, colorBbackgroundDeep[0]));
        viewHolder.itemNum.setText("" + (position + 1));
        viewHolder.itemPic.setImageResource(itemPic[Integer.parseInt(data.get(position).get(Utils.MAP_PIC_NAME))]);
        viewHolder.itemName.setText(itemNameText[Integer.parseInt(data.get(position).get(Utils.MAP_PIC_NAME))]);

        viewHolder.vPreDelete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (isEdit && mRightOpenItem != null) {
                            mRightOpenItem.openLeft();
                        } else {
                            editLayout.openRight();
                        }
                }
                return true;
            }
        });

        viewHolder.vDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (position >= 0) {
                    data.remove(position);
                    mRightOpenItem = null;
                    notifyItemRemoved(position);
                    if (position != data.size()) {
                        notifyItemRangeChanged(position, data.size() - position);
                    }
                }
            }
        });

        viewHolder.vSort.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isEdit && mRightOpenItem != null) {
                    mRightOpenItem.openLeft();
                } else {
                    mOnItemSortListener.onStartDrags(viewHolder);
                }
                return false;
            }
        });

        editLayout.setOnDragStateChangeListener(new EditLayout.OnStateChangeListener() {

            @Override
            public void onLeftOpen(EditLayout layout) {
                if (mRightOpenItem == layout) {
                    mRightOpenItem = null;
                }
            }

            @Override
            public void onRightOpen(EditLayout layout) {
                if (mRightOpenItem != layout) {
                    mRightOpenItem = layout;
                }
            }

            @Override
            public void onClose(EditLayout layout) {
                if (mRightOpenItem == layout) {
                    mRightOpenItem = null;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 设置编辑状态
     *
     * @param isEdit 是否为编辑状态
     */
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        if (isEdit) {
            openLeftAll();
        } else {
            closeAll();
        }
        for (EditLayout editLayout : allItems) {
            editLayout.setEdit(isEdit);
        }
    }

    private EditLayout.OnItemSortListener mOnItemSortListener;

    public void setOnItemSortListener(EditLayout.OnItemSortListener onItemSortListener) {
        mOnItemSortListener = onItemSortListener;
    }

    /**
     * 关闭所有 item
     */
    private void closeAll() {
        for (EditLayout editLayout : allItems) {
            editLayout.close();
        }
    }

    /**
     * 将所有 item 向左展开
     */
    private void openLeftAll() {
        for (EditLayout editLayout : allItems) {
            editLayout.openLeft();
        }
    }

    /**
     * 获取编辑状态
     *
     * @return 是否为编辑状态
     */
    public boolean isEdit() {
        return isEdit;
    }

    /**
     * 获取向右展开的 item
     *
     * @return 向右展开的 item
     */
    public EditLayout getRightOpenItem() {
        return mRightOpenItem;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        EditLayout editLayout;
        TextView itemNum;
        ImageView itemPic;
        TextView itemName;
        View vPreDelete;
        View vDelete;
        View vSort;
        LinearLayout itemContext;

        ViewHolder(View itemView) {
            super(itemView);
            editLayout = (EditLayout) itemView.findViewById(R.id.edit_layout);
            itemContext = (LinearLayout) itemView.findViewById(R.id.item_context);
            itemNum = (TextView) itemView.findViewById(R.id.list_num);
            itemPic = (ImageView) itemView.findViewById(R.id.list_pic);
            itemName = (TextView) itemView.findViewById(R.id.list_name);
            vPreDelete = itemView.findViewById(R.id.fl_pre_delete);
            vDelete = itemView.findViewById(R.id.fl_delete);
            vSort = itemView.findViewById(R.id.fl_sort);
        }
    }
}
