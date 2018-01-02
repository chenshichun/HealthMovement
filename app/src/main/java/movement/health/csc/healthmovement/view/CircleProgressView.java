package movement.health.csc.healthmovement.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by csc on 17-10-13.
 */

public class CircleProgressView extends View {

    private static final String TAG = "CircleProgressBar";

    private int mMaxProgress = 100;

    private int mProgressBottom = 0;
    private int mProgress = 0;
    private int mColor = Color.rgb(0xf8, 0x60, 0x30);
    private int mBottomColor = Color.rgb(0xe9, 0xe9, 0xe9);
    private int mCircleLineStrokeWidth = 5;
    private int fromProgrgess, mShowProgress;
    private final int mTxtStrokeWidth = 2;
    // 画圆所在的距形区域
    private final RectF mRectF;
    private final Paint mPaint;
    private final Context mContext;
    private String mTxtHint1;
    private String mTxtHint2;
    private boolean isDownProgress = true;

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBottomColor);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        // 位置
        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth / 2; // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth / 2; // 右下角y

        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, -90, /*360*/((float) mProgressBottom / mMaxProgress) * 360, false, mPaint);
        mPaint.setColor(mColor);
        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mPaint);

        // 绘制进度文案显示
        mPaint.setStrokeWidth(mTxtStrokeWidth);
        String text = mProgress + "%";
        int textHeight = height / 4;
        mPaint.setTextSize(textHeight);
        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);

        if (!TextUtils.isEmpty(mTxtHint1)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint1;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            mPaint.setColor(Color.rgb(0x99, 0x99, 0x99));
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
//            canvas.drawText(text, width / 2 - textWidth / 2, height / 4 + textHeight / 2, mPaint);
        }

        if (!TextUtils.isEmpty(mTxtHint2)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint2;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
//            canvas.drawText(text, width / 2 - textWidth / 2, 3 * height / 4 + textHeight / 2, mPaint);
        }
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        this.invalidate();
    }

    public void setProgressBottom(int progress) {
        this.mProgressBottom = progress;
        this.invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

    public void setProgressBottomColor(int color) {
        this.mBottomColor = color;
        this.postInvalidate();
    }

    public void setProgressColor(int color) {
        this.mColor = color;
        this.postInvalidate();
    }

    public void setCircleLineStrokeWidth(int width) {
        this.mCircleLineStrokeWidth = width;
        this.postInvalidate();

    }

    public String getmTxtHint1() {
        return mTxtHint1;
    }

    public void setmTxtHint1(String mTxtHint1) {
        this.mTxtHint1 = mTxtHint1;
    }

    public String getmTxtHint2() {
        return mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
    }

    public void setmShowProgress(int fromProgrgess, int toProgress, boolean isDownProgress) {
        this.isDownProgress = isDownProgress;
        this.mShowProgress = toProgress;
        this.fromProgrgess = fromProgrgess;
        new Thread(new CircleThread()).start();
    }

    private Handler circleHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                int temp = (Integer) msg.obj;
                if (isDownProgress) {
                    setProgressBottom(temp);
                } else {
                    setProgress(temp);
                }
            }
        }
    };

    private class CircleThread implements Runnable {

        int m = 0;
        int i = fromProgrgess;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(50);
                    Message msg = new Message();
                    if (fromProgrgess > mShowProgress) {
                        m--;
                        if (i > mShowProgress && i > 0) {
                            i += m;
                            if (i < 0)
                                i = 0;

                        } else {
                            i = mShowProgress;
                            return;
                        }
                    } else {
                        m++;
                        if (i < mShowProgress) {
                            i += m;
                        } else {
                            i = mShowProgress;
                            return;
                        }
                    }
                    msg.what = 1;
                    msg.obj = i;
                    circleHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}