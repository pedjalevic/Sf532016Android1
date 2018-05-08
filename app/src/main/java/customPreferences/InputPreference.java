package customPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class InputPreference extends EditTextPreference {

    /* Call requiers API lvl 21
    public InputPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    */

    public InputPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InputPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputPreference(Context context) {
        super(context);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        SharedPreferences sharedPref = getSharedPreferences();
        String newAdminName = sharedPref.getString("adminName", "Admin");

        setSummary(newAdminName);
    }
}
