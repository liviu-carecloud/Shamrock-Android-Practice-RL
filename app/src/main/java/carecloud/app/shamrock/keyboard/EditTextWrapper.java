package carecloud.app.shamrock.keyboard;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Wrapper for an EditText that is used with MyKeyboard
 */
public class EditTextWrapper {

    private static final String LOG_TAG = EditTextWrapper.class.getSimpleName();
    private Activity   mActivity;
    private EditText   mEditText;
    private MyKeyboard myKeyboard;

    public EditTextWrapper(Activity activity, EditText editText, MyKeyboard keyboard, String text) {

        Log.v(LOG_TAG, "EditTextWrapper");

        mActivity = activity;
        mEditText = editText;
        myKeyboard = keyboard;

        setText(text);
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSystemKeyboard(); // just in case
                myKeyboard.toggleVisible(true);
                view.requestFocus();
                return true;
            }
        });

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    myKeyboard.setTargetEdit(mEditText);
                } else {
                    myKeyboard.setTargetEdit(null);
                }
            }
        });
    }

    public void setText(String text) {
        if(mEditText != null) {
            mEditText.setText(text);
            mEditText.setSelection(text.length());
        }
    }

    public String getText() {
        if(mEditText != null) {
            return mEditText.getText().toString();
        }
        return null;
    }

    private void hideSystemKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
