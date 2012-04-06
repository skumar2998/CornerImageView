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
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		Paint paint = new Paint();
		// узуж╣ди╚©И
		int color = 0xff424242;
		paint.setColor(color);

		Rect r = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
		RectF rect = new RectF(r);
		canvas.drawRoundRect(rect, 15, 15, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
		canvas.drawBitmap(bitmap, r, r, paint);
	}

}
