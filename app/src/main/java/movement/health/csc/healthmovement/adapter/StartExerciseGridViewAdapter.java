package movement.health.csc.healthmovement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.utils.Utils;
import movement.health.csc.healthmovement.view.CircleProgressView;

/**
 * Created by csc on 17-10-18.
 */

public class StartExerciseGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, Object>> data;

    public StartExerciseGridViewAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
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
            convertView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.item_start_exerise_gridview, null);
            viewHolder.sportImageView = (ImageView) convertView.findViewById(R.id.sport_bottom_iv);
            viewHolder.mCircleProgressView = (CircleProgressView) convertView.findViewById(R.id.circle_progress_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sportImageView.setImageResource((Integer) data.get(position).get(Utils.START_SPORT_IMAGE));
        viewHolder.mCircleProgressView.setProgress((Integer) data.get(position).get(Utils.CIRCLE_PROGRESS));
        viewHolder.mCircleProgressView.setProgressColor(Color.rgb(0xff, 0xff, 0xff));
        viewHolder.mCircleProgressView.setProgressBottomColor(Color.rgb(0x3f, 0x3e, 0x3f));
        viewHolder.mCircleProgressView.setProgressBottom(100);

        return convertView;
    }

    private static class ViewHolder {
        ImageView sportImageView;
        CircleProgressView mCircleProgressView;
    }
}
