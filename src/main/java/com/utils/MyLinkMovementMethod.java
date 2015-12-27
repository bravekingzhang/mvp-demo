package com.utils;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by brzhang on 15/12/24.
 * Description :
 */
public class MyLinkMovementMethod extends LinkMovementMethod {


    private static final String TAG = MyLinkMovementMethod.class.getName();
    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        Log.e(TAG, buffer.toString());
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget);
                }
            }
        }
        return true;
        //return super.onTouchEvent(widget, buffer, event);

    }

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new MyLinkMovementMethod();

        return sInstance;
    }

    private static MyLinkMovementMethod sInstance;
}
