package carecloud.app.shamrock.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import carecloud.app.shamrock.Constants;
import carecloud.app.shamrock.R;


/**
 * Custom keyboard
 */
public class MyKeyboard implements KeyboardView.OnKeyboardActionListener {

    private static final String LOG_TAG = "MyKeyboard";
    private Keyboard     mKeyboard;
    private KeyboardView mKv;
    private EditText     mTargetEdit;
    private Context      mContext;
    private boolean mCaps = false;
    private boolean      mVisible;
    private TargetEditor mTargetEditor;

    public MyKeyboard(Context context, KeyboardView keyView, int langId) {
        mContext = context;
        mKv = keyView;
        mKeyboard = new Keyboard(context, getKeyResource(langId));
        mKv.setKeyboard(mKeyboard);
        mKv.setOnKeyboardActionListener(this);
        mTargetEditor = new TargetEditor();
        mVisible = false;
    }

    public void setTargetEdit(EditText targetEdit) {
        mTargetEdit = targetEdit;
        if (mTargetEdit != null) {
            mTargetEditor.initTargetEditBuffer();
        }
    }

    /**
     * Shows/hides the keyboard
     *
     * @param visible
     */
    public void toggleVisible(boolean visible) {
        if (visible) {
            mKv.setVisibility(View.VISIBLE);
        } else {
            mKv.setVisibility(View.INVISIBLE);
        }
        mVisible = visible;
    }

    /**
     * Returns the visibility of the keyboard
     *
     * @return
     */
    public boolean isVisible() {
        return mVisible;
    }

    private int getKeyResource(int langId) {
        switch (langId) {
            case Constants.LANG_ES:
                return R.xml.qwerty_es;
            default:
                return R.xml.qwerty;
        }
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        playClick(primaryCode);
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                // delete last char
                mTargetEditor.delLastChar();
                Log.v(LOG_TAG, "del");
                break;
            case Keyboard.KEYCODE_SHIFT:
                mCaps = !mCaps;
                mKeyboard.setShifted(mCaps);
                mKv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                // hide keyboard
                toggleVisible(false);
                break;
            case -66:
                mTargetEditor.addChar((int)'ñ'); // todo refactor
                break;
            default:
                int code = primaryCode;
                Log.v(LOG_TAG, "code :" + code);
                if (Character.isLetter(code) && mCaps) {
                    code = Character.toUpperCase(code);
                }
                // display char in the editor
                mTargetEditor.addChar(code);
        }
    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    /**
     *
     */
    private class TargetEditor {

        private StringBuilder mTargetEditBuffer;

        public TargetEditor() {
            mTargetEditBuffer = new StringBuilder();
        }

        public void initTargetEditBuffer() {
            if (mTargetEdit != null) {
                int lastIndex = mTargetEditBuffer.length();
                String text = mTargetEdit.getText().toString();
                mTargetEditBuffer.delete(0, lastIndex);
                mTargetEditBuffer.append(text);
            }
        }

        public void delLastChar() {
            if (mTargetEdit == null) {
                return;
            }

            int lastIndex = mTargetEdit.getText().length() - 1;
            if (lastIndex >= 0) {
                mTargetEditBuffer.deleteCharAt(lastIndex);
                // update edit
                mTargetEdit.setText(mTargetEditBuffer.toString());
                // set cursor position
                mTargetEdit.setSelection(lastIndex);
            }
        }

        public void addChar(int code) {
            if (mTargetEdit == null) {
                return;
            }

            // add the character
            mTargetEditBuffer.append((char) code);
            // update the edit
            mTargetEdit.setText(mTargetEditBuffer.toString());
            // set char on the last position
            mTargetEdit.setSelection(mTargetEdit.getText().length());
        }
    }
}
