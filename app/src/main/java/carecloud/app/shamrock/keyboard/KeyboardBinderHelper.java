package carecloud.app.shamrock.keyboard;

import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Binds edit text to a custom keyboard
 */
public class KeyboardBinderHelper {

    private Activity              mActivity;
    private List<EditText>        mEdits;
    private List<EditTextWrapper> mWrappers;
    private MyKeyboard            mKeyboard;

    public KeyboardBinderHelper(Activity activity, KeyboardView kv, int langId, List<EditText> edits) {
        mActivity = activity;
        mKeyboard = new MyKeyboard(mActivity, kv, langId);
        mEdits = edits;
        mWrappers = new ArrayList<>();
    }

    public MyKeyboard getMyKeyboard() {
        return mKeyboard;
    }

    public void bindEditsToKeyboard() {
        for (int i = 0; i < mEdits.size(); i++) {
            EditText ed = mEdits.get(i);
            mWrappers.add(new EditTextWrapper(mActivity, ed, mKeyboard, ed.getText().toString()));
        }
    }
}
