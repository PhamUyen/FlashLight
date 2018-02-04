package com.uyenpham.diploma.flashlight.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uyenpham.diploma.flashlight.R;

public class TextSeekbarView extends LinearLayout {
    private TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;

    public TextSeekbarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initial();
    }

    public TextSeekbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public TextSeekbarView(Context context) {
        super(context);
        initial();
    }

    private void initial() {
        View view = View
                .inflate(getContext(), R.layout.progress_bar, null);
        if (view != null) {
            addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
        tv0 = view.findViewById(R.id.tv0);
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv6 = view.findViewById(R.id.tv6);
        tv7 = view.findViewById(R.id.tv7);
        tv8 = view.findViewById(R.id.tv8);
        tv9 = view.findViewById(R.id.tv9);
    }

    public void setEnableText(int number) {
        resetAllText();
        switch (number) {
            case 0:
                tv0.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 1:
                tv1.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 2:
                tv2.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 3:
                tv3.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 4:
                tv4.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 5:
                tv5.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 6:
                tv6.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 7:
                tv7.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 8:
                tv8.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            case 9:
                tv9.setTextColor(getResources().getColor(R.color.color_text_enable));
                break;
            default:
                break;
        }
    }
    private void resetAllText(){
        tv0.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv1.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv2.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv3.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv4.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv5.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv6.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv7.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv9.setTextColor(getResources().getColor(R.color.color_text_disable));
        tv8.setTextColor(getResources().getColor(R.color.color_text_disable));
    }
}

