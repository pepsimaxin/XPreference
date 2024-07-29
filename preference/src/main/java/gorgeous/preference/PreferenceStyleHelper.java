package gorgeous.preference;

import android.view.View;

import androidx.preference.DialogPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;

import gorgeous.preference.widget.PreferenceLinearLayout;

class PreferenceStyleHelper {
    private static final String EXTRA_ISOLATED = "gorgeous.preference.ISOLATED";

    private PreferenceStyleHelper() {
    }

    public static void onBindViewHolder(PreferenceViewHolder holder, Preference preference,
                                        Preference prev, Preference next) {
        if (!(preference instanceof PreferenceGroup)) {
            bindPreference(holder, preference, prev, next);
        }
    }

    private static void bindPreference(PreferenceViewHolder holder, Preference preference,
                                       Preference prev, Preference next) {
        bindPreferenceArrow(holder, preference);
        bindPreferenceBackground(holder, preference, prev, next);
    }

    /**
     * Adjust right arrow visibility
     */
    private static void bindPreferenceArrow(PreferenceViewHolder holder, Preference preference) {
        final View arrowRight = holder.findViewById(R.id.arrow_right);
        if (arrowRight != null) {
            arrowRight.setVisibility(isRightArrowVisible(preference) ? View.VISIBLE : View.GONE);
        }
    }

    private static void bindPreferenceBackground(PreferenceViewHolder holder,
                                                 Preference preference,
                                                 Preference prev,
                                                 Preference next) {
        if (!(holder.itemView instanceof PreferenceLinearLayout)) {
            // can't set state
            return;
        }

        // adjust background if in group
        final PreferenceGroup parent = preference.getParent();
        final int siblingCount = parent != null ? parent.getPreferenceCount() : 0;

        final boolean divideAbove = siblingCount == 1
                || isNotDirectChild(parent, prev)
                || (parent != null && parent.getPreference(0) == preference);
        final boolean divideBelow = siblingCount == 1
                || isNotDirectChild(parent, next)
                || (parent != null && parent.getPreference(siblingCount - 1) == preference);

        final PreferenceLinearLayout.State state;
        if (divideAbove && divideBelow) {
            state = PreferenceLinearLayout.State.SINGLE;
        } else if (divideAbove) {
            state = PreferenceLinearLayout.State.FIRST;
        } else if (divideBelow) {
            state = PreferenceLinearLayout.State.LAST;
        } else {
            state = PreferenceLinearLayout.State.MIDDLE;
        }
        holder.setDividerAllowedAbove(divideAbove);
        holder.setDividerAllowedBelow(divideBelow);
        ((PreferenceLinearLayout) holder.itemView).setState(state);
    }

    /**
     * Whether we should show the arrow icon.
     */
    private static boolean isRightArrowVisible(Preference preference) {
        if (preference instanceof TwoStatePreference
                || preference.getWidgetLayoutResource() != 0) {
            return false;
        }

        return preference.getIntent() != null
                || preference.getFragment() != null
                || preference.getOnPreferenceClickListener() != null
                || preference instanceof DialogPreference;
    }

    /**
     * Whether the preference is a direct child or a parent preference.
     */
    private static boolean isNotDirectChild(PreferenceGroup parent, Preference preference) {
        return preference == null
                || preference.getParent() != parent
                || preference instanceof PreferenceGroup
                || preference.getExtras().getBoolean(EXTRA_ISOLATED, false);
    }
}
