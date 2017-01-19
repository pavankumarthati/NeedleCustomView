package app.needle.com;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pavan on 19/1/17.
 */

public class Needle extends View {

    private Paint linePaint;
    private Paint lineSkewPaint;
    private Path linePath;
    private float density;
    private float startY;
    private float startX;
    private Matrix matrix;

    public Needle(Context context) {
        super(context);
        init();
    }

    public Needle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Needle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.needle_stroke_width));
        linePaint.setColor(Color.RED);
        linePaint.setShadowLayer(8, 1, 1, Color.GRAY);

        linePath = new Path();
        startX = 400;
        startY = 500;
        density = getResources().getDisplayMetrics().density;
        linePath.moveTo(startX, startY);
        linePath.lineTo(startX + (35 * density) , startY - (5 * density));
        linePath.lineTo(startX + (120 * density), startY);
        linePath.lineTo(startX + (35 * density), startY + (5 * density));
        linePath.lineTo(startX, startY);
        linePath.addCircle(startX + (35 * density), startY, getResources().getDimensionPixelOffset(R.dimen.needle_skrew_outer_radius), Path.Direction.CW);
        linePath.close();
        lineSkewPaint = new Paint();
        lineSkewPaint.setAntiAlias(true);
        lineSkewPaint.setStyle(Paint.Style.FILL);
        lineSkewPaint.setColor(Color.BLACK);
        lineSkewPaint.setShader(new RadialGradient(startX + (35 * density), startY, getResources().getDimensionPixelOffset(R.dimen.needle_skrew_inner_radius), Color.DKGRAY, Color.BLACK, Shader.TileMode.CLAMP));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.postRotate(1, startX + (35 * density), startY + (5 * density));
        canvas.concat(matrix);
        canvas.drawPath(linePath, linePaint);
        canvas.drawCircle(startX + (35 * density), startY, getResources().getDimensionPixelOffset(R.dimen.needle_skrew_inner_radius), lineSkewPaint);
        postInvalidateDelayed(100);
    }
}
