package com.indexkey.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
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

	private float topLeftRadius = 0f;
	private float topRightRadius = 0f;
	private float bottomLeftRadius = 0f;
	private float bottomRightRadius = 0f;

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

		topLeftRadius = typeArray.getDimension(
				R.styleable.corners_topLeftRadius, 0f);
		topRightRadius = typeArray.getDimension(
				R.styleable.corners_topRightRadius, 0f);
		bottomLeftRadius = typeArray.getDimension(
				R.styleable.corners_bottomLeftRadius, 0f);
		bottomRightRadius = typeArray.getDimension(
				R.styleable.corners_bottomRightRadius, 0f);

		setImageDrawable(getDrawable());
		typeArray.recycle();

	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		Drawable rounderDrawable = changeRounder(drawable);
		super.setImageDrawable(rounderDrawable);
	}

	private Drawable changeRounder(Drawable drawable) {

		try {
			// ��ȡԭʼͼƬ
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			// ���������ͼƬ��ֱ�Ӳ���drawable ���׳���Immutable bitmap passed to Canvas
			// constructor�����ԭ�����������copy�ķ�����ֱ�����û����Դ�ļ������޸ģ���android�ǲ������ڴ������޸�res�ļ����ͼƬ
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			// ��������
			Canvas canvas = new Canvas(output);

			// ���ֲ���ɫ
			int color = Color.RED;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			// ������� ����Ϊ����output���������� ʹ��Բ��Ϊ��
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			// �������ֲ� TODO need to review
			// canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint)
			// canvas.drawPath(path, paint)
			canvas.drawRoundRect(rectF, topLeftRadius, topLeftRadius, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			// �� SRC_IN ģʽ�� output �γɵĻ����ϻ��� Bitmap����
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return new BitmapDrawable(output);

		} catch (Exception ex) {
			return null;

		}

	}
}
