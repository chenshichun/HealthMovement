package movement.health.csc.healthmovement.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 18-1-2.
 */

public class NewExercisingListViewAdapter extends BaseAdapter {
    Context context;
    List<Map<String, String>> data;
    private int itemPic[];
    private String[] itemNameText;

    public NewExercisingListViewAdapter(Context context, List<Map<String, String>> data) {
        this.context = context;
        this.data = data;
        TypedArray array = this.context.getResources().obtainTypedArray(R.array.exercising_pic);
        int len = array.length();
        itemPic = new int[len];
        for (int i = 0; i < len; i++) {
            itemPic[i] = array.getResourceId(i, 0);
        }
        itemNameText = this.context.getResources().getStringArray(R.array.sports_name);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_new_exercising_list_view, null);
            viewHolder.itemNum = (TextView) convertView.findViewById(R.id.list_num);
            viewHolder.itemPic = (ImageView) convertView.findViewById(R.id.list_pic);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.list_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemNum.setText(""+(position+1));
        viewHolder.itemPic.setImageResource(itemPic[Integer.parseInt(data.get(position).get(Utils.MAP_PIC_NAME))]);
        viewHolder.itemName.setText(itemNameText[Integer.parseInt(data.get(position).get(Utils.MAP_PIC_NAME))]);

        return convertView;
    }

    private static class ViewHolder {
        TextView itemNum;
        ImageView itemPic;
        TextView itemName;
    }
}
