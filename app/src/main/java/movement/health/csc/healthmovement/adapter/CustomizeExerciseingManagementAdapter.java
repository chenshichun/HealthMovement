package movement.health.csc.healthmovement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.utils.Utils;

/**
 * Created by csc on 18-1-15.
 */

public class CustomizeExerciseingManagementAdapter extends BaseAdapter {
    Context context;
    List<Map<String, String>> data;

    public CustomizeExerciseingManagementAdapter(Context context, List<Map<String, String>> data) {
        this.context = context;
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
            convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_customize_exerciseing_management_list_view, null);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.customize_exerciseing_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemName.setText((String) data.get(position).get(Utils.CUSTOMIZE_MAP_NAME));

        return convertView;
    }

    private static class ViewHolder {
        TextView itemName;
    }
}
