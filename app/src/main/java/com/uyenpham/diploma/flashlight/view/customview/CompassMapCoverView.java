package com.uyenpham.diploma.flashlight.view.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.uyenpham.diploma.flashlight.R;

/**
 * Created by Ka on 2/5/2018.
 */

public class CompassMapCoverView extends View
{
    String[] a;
    int b = Color.parseColor("#99000000");
    Paint c = new Paint(1);
    TextPaint d = new TextPaint();
    Xfermode e = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    float f;
    float g;
    float h;
    float i = 0.0F;

    public CompassMapCoverView(Context paramContext)
    {
        super(paramContext);
        a();
    }

    public CompassMapCoverView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        a();
    }

    public CompassMapCoverView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        a();
    }

    @TargetApi(21)
    public CompassMapCoverView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
    {
        super(paramContext, paramAttributeSet, paramInt1, paramInt2);
        a();
    }

    void a()
    {
        this.c.setColor(this.b);
        this.f = (this.d.getFontMetrics().bottom - this.d.getFontMetrics().top);
        this.d.setTextAlign(Paint.Align.CENTER);
        this.d.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.text_size_17));
    }

    void a(Canvas paramCanvas)
    {
        int j = paramCanvas.saveLayer(0.0F, 0.0F, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        paramCanvas.drawCircle(getWidth() / 2.0F, getHeight() / 2.0F, this.h, this.c);
        this.c.setXfermode(this.e);
        paramCanvas.drawRect(0.0F, 0.0F, getWidth(), getHeight(), this.c);
        this.c.setXfermode(null);
        paramCanvas.restoreToCount(j);
    }

    void b(Canvas paramCanvas)
    {
        if ((this.a == null) || (this.a.length != 4)) {
            return;
        }
        float f1 = getWidth() / 2.0F;
        float f2 = getHeight() / 2.0F;
        float f3 = f2 - this.g;
        paramCanvas.save();
        paramCanvas.rotate(this.i, f1, f2);
        for (int j =0; j<a.length;j++)
        {
            String str = this.a[j];
            paramCanvas.rotate(90.0F * j, f1, f2);
            if (j == 0) {
                this.d.setColor(Color.RED);
            }else {
                this.d.setColor(Color.WHITE);
            }
            paramCanvas.drawText(str, f1, f3, this.d);
        }
        paramCanvas.restore();
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        a(paramCanvas);
        b(paramCanvas);
    }

    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        this.g = (0.9F * (Math.min(paramInt1, paramInt2) / 2.0F - this.f) + this.d.getFontMetrics().bottom);
        this.h = (this.g - this.f / 2.0F);
    }

    public void setCompassRotation(float paramFloat)
    {
        this.i = paramFloat;
        invalidate();
    }

    public void setDirections(String[] paramArrayOfString)
    {
        this.a = paramArrayOfString;
    }
}

