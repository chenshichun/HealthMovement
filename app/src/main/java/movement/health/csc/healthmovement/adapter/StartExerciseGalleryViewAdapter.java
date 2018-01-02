package movement.health.csc.healthmovement.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movement.health.csc.healthmovement.R;
import movement.health.csc.healthmovement.view.CircleProgressView;
import movement.health.csc.healthmovement.view.GalleryView;

/**
 * Created by csc on 17-10-18.
 */
public class StartExerciseGalleryViewAdapter extends BaseAdapter {
    private ImageView[] mImages; // 保存倒影图片的数组

    private Context mContext;
    public List<Map<String, Object>> list;
    private int selectItem;

    public Integer[] imgs = {R.drawable.kung_fu0_normal, R.drawable.kung_fu1_normal,
            R.drawable.kung_fu2_normal, R.drawable.kung_fu3_normal, R.drawable.kung_fu4_normal,
            R.drawable.kung_fu5_normal, R.drawable.kung_fu6_normal};
    public String[] titles = {"美图01", "美图02", "美图03", "美图04", "美图05", "美图06",
            "美图07"};

    public StartExerciseGalleryViewAdapter(Context c) {
        this.mContext = c;
        list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imgs.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", imgs[i]);
            list.add(map);
        }
        mImages = new ImageView[list.size()];
    }

    /**
     * 反射倒影
     */
    public boolean createReflectedImages() {
        final int reflectionGap = 4;
        final int Height = 100;
        int index = 0;
        for (Map<String, Object> map : list) {
            Integer id = (Integer) map.get("image");
            // 根据给定的资源ID从指定的资源中解析、创建Bitmap对象。
            Bitmap originalImage = BitmapFactory.decodeResource(
                    mContext.getResources(), id); // 获取原始图片
            // 获得实际图片的宽高
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            float scale = Height / (float) height;

            Matrix sMatrix = new Matrix();
            // 第一次缩放图片动作
            sMatrix.postScale(scale, scale);

            Bitmap miniBitmap = Bitmap.createBitmap(originalImage, 0, 0,
                    originalImage.getWidth(), originalImage.getHeight(),
                    sMatrix, false);
            // 获取第一次缩放后的图片的宽高
            int mwidth = miniBitmap.getWidth();
            int mheight = miniBitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.preScale(1, -1); // 图片矩阵变换（从低部向顶部的倒影）
            Bitmap reflectionImage = Bitmap.createBitmap(miniBitmap, 0, mheight / 2,
                    mwidth, mheight / 2, matrix, false); // 截取原图下半部分
            Bitmap bitmapWithReflection = Bitmap.createBitmap(mwidth,
                    mheight + mheight / 2, Bitmap.Config.ARGB_8888);// 创建倒影图片（高度为原图3/2）

            Canvas canvas = new Canvas(bitmapWithReflection); // 绘制倒影图（原图 + 间距 +
            // 倒影）
            canvas.drawBitmap(miniBitmap, 0, 0, null); // 绘制原图
            Paint paint = new Paint();
            canvas.drawRect(0, mheight, mwidth, mheight, paint); // 绘制原图与倒影的间距
            canvas.drawBitmap(reflectionImage, 0, mheight, null); // 绘制倒影图,绘制原图与倒影的间距(第四个参数改变)

            paint = new Paint();
            LinearGradient shader = new LinearGradient(0,
                    miniBitmap.getHeight(), 0, bitmapWithReflection.getHeight()
                    + reflectionGap, 0x70ffffff, 0x00ffffff,
                    Shader.TileMode.CLAMP);
            paint.setShader(shader); // 线性渐变效果
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN)); // 倒影遮罩效果
            canvas.drawRect(0, mheight, mwidth,
                    bitmapWithReflection.getHeight() + reflectionGap, paint); // 绘制倒影的阴影效果

            ImageView imageView = new ImageView(mContext);
            imageView.setImageBitmap(bitmapWithReflection); // 设置倒影图片
            imageView.setLayoutParams(new GalleryView.LayoutParams(
                    (int) (width * scale),
                    (int) (mheight * 3 / 2.0 + reflectionGap)));
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            mImages[index++] = imageView;
        }
        return true;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return mImages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.item_start_exercise_gallery_view, null);
            viewHolder.sportImageView = (ImageView) convertView.findViewById(R.id.sports_iv);
            viewHolder.mCircleProgressView = (CircleProgressView) convertView.findViewById(R.id.item_circle_progress_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.sportImageView.setBackgroundResource(imgs[position]);
        viewHolder.mCircleProgressView.setProgressBottomColor(Color.rgb(0x3f, 0x3e, 0x3f));
        viewHolder.mCircleProgressView.setProgressBottom(100);

        if(selectItem==position){
            viewHolder.sportImageView.setScaleX(2.5f);
            viewHolder.sportImageView.setScaleY(2.5f);
        }else{
            viewHolder.sportImageView.setScaleX(1.0f);
            viewHolder.sportImageView.setScaleY(1.0f);
        }
            return convertView;
    }

    private static class ViewHolder {
        ImageView sportImageView;
        CircleProgressView mCircleProgressView;
    }

    public float getScale(boolean focused, int offset) {
        return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
    }

}