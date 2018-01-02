package movement.health.csc.healthmovement.adapter;

import android.content.Context;
import android.database.DataSetObserver;
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
 * Created by csc on 17-10-16.
 */

public class ChoosePracticeAdapter extends BaseAdapter {
    private Context mComtext;
    private ArrayList<HashMap<String, Object>> data;

    public ChoosePracticeAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
        this.mComtext = context;
        this.data = data;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mComtext.getApplicationContext()).inflate(R.layout.item_choose_practice, null);
            viewHolder.sportImageView = (ImageView) convertView.findViewById(R.id.sport_iv);
            viewHolder.sportTitle = (TextView) convertView.findViewById(R.id.sport_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sportImageView.setImageResource((Integer) data.get(position).get(Utils.SPORT_IMAGE));
        viewHolder.sportTitle.setText((String) data.get(position).get(Utils.SPORT_TITLE));

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private static class ViewHolder {
        ImageView sportImageView;
        TextView sportTitle;
    }

}
