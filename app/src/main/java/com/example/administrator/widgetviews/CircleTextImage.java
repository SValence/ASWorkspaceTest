package com.example.administrator.widgetviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;

public class CircleTextImage extends AppCompatImageView {

    private static final String TAG = "CircleTextImage";
    private Bitmap mBitmap;
    private ColorFilter mColorFilter;
    private Paint mBitmapPaint;
    private Paint mBorderPaint;
    private Paint mTextPaint;
    private RectF mBorderRect;
    private RectF mBitmapRect;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_WIDTH = 0;
    private static final int DEFAUKT_TEXT_SIZE = 60;
    private int mBorderWidth;
    private int mBorderColor;
    private int mTextColor;
    private int mTextSize;
    private String mText;
    private float mBorderRadius;
    private float mBitmapRadius;
    private Drawable backDrawable;
    private BitmapShader mBitmapShader;
    private boolean initialized;
    private boolean sizeMeasured;
    private Matrix mShaderMatrix;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private float imgCenterX;
    private float imgCenterY;

    public CircleTextImage(Context context) {
        super(context);
        init();
    }

    public CircleTextImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleTextImage, defStyleAttr, 0);
            mBorderWidth = array.getDimensionPixelSize(R.styleable.CircleTextImage_border_width_, DEFAULT_WIDTH);
            mBorderColor = array.getColor(R.styleable.CircleTextImage_border_color_, DEFAULT_COLOR);
            mTextColor = array.getColor(R.styleable.CircleTextImage_img_text_color_, DEFAULT_COLOR);
            mTextSize = array.getDimensionPixelSize(R.styleable.CircleImageView_img_text_size, DEFAUKT_TEXT_SIZE);
            mText = array.getString(R.styleable.CircleImageView_img_text);
            array.recycle();
        }
        init();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;

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

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        backDrawable = background;
        setImagePrePare();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sizeMeasured = true;
        setImagePrePare();
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(ScaleType.CENTER_CROP);
        setImagePrePare();
    }

    private void init() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mBorderRect = new RectF();
        mBitmapRect = new RectF();
        mShaderMatrix = new Matrix();
        initialized = true;
    }

    private Bitmap getImageBitmap(Drawable drawable) {
        if (drawable == null) {
            Log.e(TAG, "Drawable is Null");
            return null;
        }
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
        if (!initialized || mBitmap == null || !sizeMeasured) return;

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        int viewWidth = getWidth();
        int viewHeight = getHeight();
        int imgHeight = viewHeight - mTextSize;
        int imgSize = Math.min(imgHeight, viewWidth);
        imgCenterX = (float) (viewWidth / 2.0);
        imgCenterY = (float) (imgSize / 2.0);

        mBorderRect.set((viewWidth - imgSize) / 2, 0, (viewWidth + imgSize) / 2, imgSize);
        mBorderRadius = Math.min((mBorderRect.width() - mBorderWidth) / 2, (mBorderRect.height() - mBorderWidth) / 2);
        mBitmapRect.set(mBorderRect);
        mBitmapRadius = Math.min(mBitmapRect.width() / 2, mBitmapRect.height() / 2);

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);

        setShaderMatrix();
        invalidate();
    }

    private void setShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mBitmapRect.height() > mBitmapRect.width() * mBitmapHeight) {
            scale = mBitmapRect.height() / (float) mBitmapHeight;
            dx = (mBitmapRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mBitmapRect.width() / (float) mBitmapWidth;
            dy = (mBitmapRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBitmapRect.left, (int) (dy + 0.5f) + mBitmapRect.top);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf != mColorFilter) {
            mColorFilter = cf;
            mBitmapPaint.setColorFilter(mColorFilter);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int[] state = backDrawable.getState();
        if (state.length > 0) {
            for (int i = 0; i < state.length; i++) {
                Log.e(TAG, "getDrawable().getCurrent().getState()_" + i + ":" + state[i]);
            }
        }
        canvas.drawCircle(imgCenterX, imgCenterY, mBitmapRadius, mBitmapPaint);
        if (mBorderWidth != 0) {
            canvas.drawCircle(imgCenterX, imgCenterY, mBorderRadius, mBorderPaint);
        }
        if (!TextUtils.isEmpty(mText)) {
            int mTextStartX = (int) ((getWidth() - mTextPaint.measureText(mText)) / 2);
            int mTextStartY = (int) (mBorderRect.height() + (mTextSize - mTextPaint.ascent() - mTextPaint.descent()) / 2);
            canvas.drawText(mText, mTextStartX, mTextStartY, mTextPaint);
        }
    }
}
