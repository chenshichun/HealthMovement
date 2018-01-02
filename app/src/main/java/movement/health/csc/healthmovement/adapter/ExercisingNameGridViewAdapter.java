package movement.health.csc.healthmovement.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 17-12-25.
 */

public class ExercisingNameGridViewAdapter extends BaseAdapter {
    private static final boolean DEBUG = true;
    private Context mContext;
    private ArrayList<HashMap<String, Object>> data;

    public ExercisingNameGridViewAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
        this.mContext = context;
        this.data = data;
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
            convertView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.exercising_name_gridview_item, null);
            viewHolder.itemIv = (ImageView) convertView.findViewById(R.id.item_iv);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemIv.setImageResource((Integer) data.get(position).get(Utils.EXERCISING_NAME_PIC));
        viewHolder.itemName.setText((String)data.get(position).get(Utils.EXERCISING_NAME_TEXT));
        return convertView;
    }

    private static class ViewHolder {
        ImageView itemIv;
        TextView itemName;
    }
}
