package com.ansen.shape;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.ansen.shape.module.ShapeAttribute;
import com.ansen.shape.util.ShapeUtil;

/**
 * 可绘制边框的渐变
 */
public class AnsenBorderFrameLayout extends FrameLayout implements IAnsenShapeView{
    private ShapeAttribute shapeAttribute;

    private LinearGradient shader;
    private Paint borderPaint;

    public AnsenBorderFrameLayout(Context context) {
        this(context,null);
    }

    public AnsenBorderFrameLayout(Context context, AttributeSet attrs){
        this(context, attrs,0);
    }

    public AnsenBorderFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        shapeAttribute=ShapeUtil.getShapeAttribute(context,attrs);
        if (!shapeAttribute.borderGradient) {
            //有边框渐变就不设置背景了
            ShapeUtil.setBackground(this, shapeAttribute);
        }

    }

    @Override
    public void resetBackground() {
        ShapeUtil.setBackground(this,shapeAttribute);
    }

    @Override
    public void setSolidColor(int solidColor) {
        shapeAttribute.solidColor=solidColor;
    }

    @Override
    public void setPressedSolidColor(int pressedSolidColor) {
        shapeAttribute.pressedSolidColor=pressedSolidColor;
    }

    @Override
    public void setStartColor(int startColor) {
        shapeAttribute.startColor=startColor;
    }

    @Override
    public void setCenterColor(int centerColor) {
        shapeAttribute.centerColor=centerColor;
    }

    @Override
    public void setEndColor(int endColor) {
        shapeAttribute.endColor=endColor;
    }

    @Override
    public void setColorOrientation(GradientDrawable.Orientation orientation) {
        shapeAttribute.colorOrientation=ShapeUtil.getOrientation(orientation);
    }

    @Override
    public void setStrokeColor(int strokeColor) {
        shapeAttribute.strokeColor=strokeColor;
    }

    @Override
    public void setStrokeWidth(float strokeWidth) {
        shapeAttribute.strokeWidth=strokeWidth;
    }

    @Override
    public void setCornersRadius(float cornersRadius) {
        shapeAttribute.cornersRadius=cornersRadius;
    }

    @Override
    public void setTopLeftRadius(float topLeftRadius) {
        shapeAttribute.topLeftRadius=topLeftRadius;
    }

    @Override
    public void setTopRightRadius(float topRightRadius) {
        shapeAttribute.topRightRadius=topRightRadius;
    }

    @Override
    public void setBottomLeftRadius(float bottomLeftRadius) {
        shapeAttribute.bottomLeftRadius=bottomLeftRadius;
    }

    @Override
    public void setBottomRightRadius(float bottomRightRadius) {
        shapeAttribute.bottomRightRadius=bottomRightRadius;
    }

    @Override
    public void setShape(int shape) {
        shapeAttribute.shape=shape;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        shapeAttribute.selected=selected;
        resetBackground();
    }

    public ShapeAttribute getShape() {
        return shapeAttribute;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int textWidth = getMeasuredWidth();
        if (textWidth > 0 && shapeAttribute != null && shapeAttribute.borderGradient) {
            setTextColorOrientation();
        }

        super.onDraw(canvas);

//        Log.i("ansen","onDraw width:"+getMeasuredWidth());
        if (textWidth > 0 && shapeAttribute.borderGradient) {//绘制渐变圆角边框
//            canvas.save();
//            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            if (borderPaint == null) {
                borderPaint = new Paint();
                borderPaint.setStyle(Paint.Style.STROKE);
                borderPaint.setStrokeWidth(shapeAttribute.getStrokeWidth());
                borderPaint.setAntiAlias(true);//抗锯齿
            }
            borderPaint.setShader(shader);//设置渐变背景

            Rect rect = new Rect();
            canvas.getClipBounds(rect);
            RectF rectF = new RectF(rect);
            float radius = shapeAttribute.cornersRadius;
            float marginWidth = shapeAttribute.getStrokeWidth();
            canvas.drawRoundRect(marginWidth, marginWidth, getMeasuredWidth()-marginWidth,getMeasuredHeight()-marginWidth, radius, radius, borderPaint);
            //canvas.drawRoundRect(rectF.left-radius/2, rectF.top-2.5f, rectF.right-2.5f, rectF.bottom-2.5f, radius, radius, borderPaint);
        }
    }

    private void setTextColorOrientation() {
        int[] textColors = {shapeAttribute.startColor, shapeAttribute.endColor};
        if (shapeAttribute.getCenterColor() != 0) {
            textColors = new int[]{shapeAttribute.startColor, shapeAttribute.centerColor, shapeAttribute.endColor};
        }
        int startX = 0, startY = 0, endX = 0, endY = 0;
        switch (shapeAttribute.colorOrientation) {
            case 2:
                startX = getMeasuredHeight();
                break;
            case 3:
                endY = getMeasuredHeight();
                break;
            case 4:
                startY = getMeasuredHeight();
                break;
            case 5:
                startX = getMeasuredWidth();
                endY = getMeasuredHeight();
                break;
            case 6:
                startX = getMeasuredWidth();
                startY = getMeasuredHeight();
                break;
            case 7:
                startY = getMeasuredHeight();
                endX = getMeasuredWidth();
                break;
            case 8:
                endX = getMeasuredWidth();
                endY = getMeasuredHeight();
                break;
            default:
                endX = getMeasuredWidth();
                break;
        }
        shader = new LinearGradient(startX, startY, endX, endY, textColors, null, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
