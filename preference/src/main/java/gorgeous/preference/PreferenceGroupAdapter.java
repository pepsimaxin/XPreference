package gorgeous.preference;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceViewHolder;

@SuppressLint("RestrictedApi")
public class PreferenceGroupAdapter extends androidx.preference.PreferenceGroupAdapter{
    private static final String TAG = "gorgeous.preference.PreferenceGroupAdapter";

    public PreferenceGroupAdapter(@NonNull PreferenceGroup preferenceGroup) {
        super(preferenceGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        super.onBindViewHolder(holder, position);
        // customizer for gorgeous design.
        final Preference preference = getItem(position);
        final Preference prev = (position - 1) >= 0 ? getItem(position - 1) : null;
        final Preference next = (position + 1) < getItemCount() ? getItem(position + 1) : null;
        PreferenceStyleHelper.onBindViewHolder(holder, preference, prev, next);
    }
}
