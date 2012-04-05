package com.indexkey.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CornerImageView extends ImageView {

	public CornerImageView(Context context) {
		super(context);
	}

	public CornerImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public CornerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	@Override
	public void setImageDrawable(Drawable drawable) {

		Drawable rounderDrawable = changeRounder(drawable);
		super.setImageDrawable(rounderDrawable);
	}

	private Drawable changeRounder(Drawable drawable) {

		try {
			// 获取原始图片
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			// 创建新输出图片，直接操作drawable 会抛出：Immutable bitmap passed to Canvas
			// constructor错误的原因是如果不用copy的方法，直接引用会对资源文件进行修改，而android是不允许在代码里修改res文件里的图片
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			// 构建画布
			Canvas canvas = new Canvas(output);

			// 遮罩层颜色
			int color = Color.RED;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			RectF rectF = new RectF(rect);
			// 圆角弧度
			float roundPx = 20;

			paint.setAntiAlias(true);
			// 清除画布 （因为其由output副本创建） 使其圆角为空
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			// 绘制遮罩层
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			// 以 SRC_IN 模式在 output 形成的画布上绘制 Bitmap对象
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return new BitmapDrawable(output);

		} catch (Exception ex) {
			return null;

		}

	}
}
