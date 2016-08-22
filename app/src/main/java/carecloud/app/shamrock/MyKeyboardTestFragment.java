package carecloud.app.shamrock;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import carecloud.app.shamrock.keyboard.EditTextRestorer;
import carecloud.app.shamrock.keyboard.KeyboardBinderHelper;
import carecloud.app.shamrock.keyboard.MyKeyboard;

/**
 * Fragment that will show a keyboard
 */
public class MyKeyboardTestFragment extends Fragment {

    private static final String LOG_TAG = MyKeyboardTestFragment.class.getSimpleName();
    private ArrayList<EditText> mEdits;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // inflate
        View mRootView = inflater.inflate(R.layout.fragment_with_keyboard, container, false);

        mEdits = new ArrayList<>();
        mEdits.add((EditText) mRootView.findViewById(R.id.edit1));
        mEdits.add((EditText) mRootView.findViewById(R.id.edit2));

        // restore texts to edits
        EditTextRestorer.restoreTexts(savedInstanceState, mEdits);

        KeyboardBinderHelper mBinder = new KeyboardBinderHelper(getActivity(),
                                                                (KeyboardView) mRootView.findViewById(R.id.keyboard),
                                                                Constants.LANG_ES,
                                                                mEdits);
        // bind edits to the keyboard
        mBinder.bindEditsToKeyboard();

        // toggle keyboard visible, if that's the case
        MyKeyboard mMyKeyboard = mBinder.getMyKeyboard();
        mMyKeyboard.toggleVisible(mMyKeyboard.isVisible());

        // capture button for test
        Button mBtnCapture = (Button) mRootView.findViewById(R.id.button_capture_text);
        mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Text from first edit: " + mEdits.get(0).getText(), Snackbar.LENGTH_SHORT).show();
            }
        });

        return mRootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        EditTextRestorer.saveTexts(outState, mEdits);
    }

}