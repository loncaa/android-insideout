package hr.magicpot.app.insideout;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

/**
 * source: https://stackoverflow.com/questions/13496099/any-simple-way-to-require-an-edittextpreference-value-to-not-be-blank-in-android
 */

public class SafeEditTextPreference extends EditTextPreference {
    public SafeEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SafeEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SafeEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SafeEditTextPreference(Context context) {
        super(context);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        boolean valid = this.getEditText().getText().toString().length() > 0;
        super.onDialogClosed(valid);
    }
}
