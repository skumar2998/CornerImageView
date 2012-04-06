package com.indexkey.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CornerImageView extends ImageView {

	private float mCornerRadius;

	public CornerImageView(Context context) {
		super(context);
	}

	public CornerImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CornerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.corners);

		mCornerRadius = typeArray.getDimension(
				R.styleable.corners_CornerRadius, 0f);

		setImageDrawable(getDrawable());
		typeArray.recycle();

	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		if (mCornerRadius != 0f) {
			long startTime = System.currentTimeMillis(); // 获取开始时间
			Drawable rounderDrawable = changeRounder(drawable);
			long endTime = System.currentTimeMillis(); // 获取结束时间
			// System.out.println("程序运行时间： " + (endTime - startTime) + "ns");
			Log.d("MyDraw", "程序运行时间： " + (endTime - startTime) + "ms");
			super.setImageDrawable(rounderDrawable);
		} else {
			super.setImageDrawable(drawable);
		}
	}

	public void setCornerRadius(float radius) {
		mCornerRadius = radius;

	}

	// //此方法随着radius的增大 而耗时增多。
	// private Drawable changeRounder(Drawable drawable) {
	// try {
	// Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
	// Bitmap output = bitmap.copy(Config.ARGB_8888, true);
	// int w = output.getWidth();
	// int h = output.getHeight();
	// Canvas canvas = new Canvas(output);
	//
	// int r = (int) (mCornerRadius * mCornerRadius);
	// for (int x = 0; x < mCornerRadius; x++) {
	// for (int y = 0; y < mCornerRadius; y++) {
	// int t = (int) ((x - mCornerRadius) * (x - mCornerRadius) + (y -
	// mCornerRadius)
	// * (y - mCornerRadius));
	// if (t > r) {
	// output.setPixel(x, y, Color.TRANSPARENT);
	// }
	// }
	// }
	//
	// r = (int) (mCornerRadius * mCornerRadius);
	// for (int x = (int) (w - mCornerRadius); x < w; x++) {
	// for (int y = 0; y < mCornerRadius; y++) {
	// int t = (int) ((x + mCornerRadius - w)
	// * (x + mCornerRadius - w) + (y - mCornerRadius)
	// * (y - mCornerRadius));
	// if (t > r) {
	// output.setPixel(x, y, Color.TRANSPARENT);
	// }
	// }
	// }
	//
	// r = (int) (mCornerRadius * mCornerRadius);
	// for (int x = 0; x < mCornerRadius; x++) {
	// for (int y = (int) (h - mCornerRadius); y < h; y++) {
	// int t = (int) ((x - mCornerRadius) * (x - mCornerRadius) + (y
	// - h + mCornerRadius)
	// * (y - h + mCornerRadius));
	// if (t > r) {
	// output.setPixel(x, y, Color.TRANSPARENT);
	// }
	// }
	// }
	//
	// r = (int) (mCornerRadius * mCornerRadius);
	// for (int x = (int) (w - mCornerRadius); x < w; x++) {
	// for (int y = (int) (h - mCornerRadius); y < h; y++) {
	// int t = (int) ((x - w + mCornerRadius)
	// * (x - w + mCornerRadius) + (y - h + mCornerRadius)
	// * (y - h + mCornerRadius));
	// if (t > r) {
	// output.setPixel(x, y, Color.TRANSPARENT);
	// }
	// }
	// }
	//
	// //抗锯齿
	// Paint paint = new Paint();
	// paint.setAntiAlias(true);
	// canvas.drawBitmap(output, 0, 0, paint);
	//
	// return new BitmapDrawable(output);
	// } catch (Exception ex) {
	// return null;
	// }
	// }

	private Drawable changeRounder(Drawable drawable) {

		try {

			// 获取原始图片
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			// 创建新输出图片，直接操作drawable 会抛出：Immutable bitmap passed to Canvas
			//
			// constructor错误的原因是如果不用copy的方法，直接引用会对资源文件进行修改，而android是不允许在代码里修改res文件里的图片
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			int color = Color.RED;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);

			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return new BitmapDrawable(output);

		} catch (Exception ex) {
			return null;

		}

	}

}
