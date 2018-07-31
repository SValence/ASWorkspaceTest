package com.example.administrator.widgetviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.administrator.myapplication.R;

public class CircleTextImage extends AppCompatImageView {

    private static final String TAG = "CircleTextImage";
    private Bitmap mBitmap;
    private ColorFilter mColorFilter;
    private Paint mBitmapPaint;
    private Paint mBorderPaint;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_WIDTH = 0;
    private int mBorderWidth;
    private int mBorderColor;

    public CircleTextImage(Context context) {
        this(context, null, 0);
    }

    public CircleTextImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs!=null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleTextImage, defStyleAttr, 0);
            mBorderWidth = array.getDimensionPixelSize(R.styleable.CircleTextImage_border_width, DEFAULT_WIDTH);
            mBorderColor = array.getColor(R.styleable.CircleTextImage_border_color, DEFAULT_COLOR);
            array.recycle();
        }
        init();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setImagePrePare();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getImageBitmap(drawable);
        setImagePrePare();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap = getImageBitmap(getDrawable());
        setImagePrePare();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = getImageBitmap(getDrawable());
        setImagePrePare();
    }

    private void init() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
    }

    private Bitmap getImageBitmap(Drawable drawable) {
        if (drawable == null) {
            Log.e(TAG, "Drawable is Null");
            return null;
        }
        if (drawable instanceof BitmapDrawable) return ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap;
        if (drawable instanceof ColorDrawable) {
            bitmap = Bitmap.createBitmap(2, 2,
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void setImagePrePare() {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf != mColorFilter) {
            mColorFilter = cf;
            mBitmapPaint.setColorFilter(mColorFilter);
            invalidate();
        }
    }
}
