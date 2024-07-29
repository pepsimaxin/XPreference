package gorgeous.preference;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.EditTextPreference;
import androidx.preference.EditTextPreferenceDialogFragmentCompat;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.MultiSelectListPreferenceDialogFragmentCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;

import gorgeous.preference.widget.PreferenceLinearLayout;

public abstract class PreferenceFragmentCompat extends androidx.preference.PreferenceFragmentCompat{

    private static final String DIALOG_FRAGMENT_TAG =
            "gorgeous.preference.PreferenceFragment.DIALOG";

    private final DividerDecoration mDividerDecoration = new DividerDecoration();

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        TypedArray a = requireContext().obtainStyledAttributes(null,
                androidx.preference.R.styleable.PreferenceFragmentCompat,
                R.attr.preferenceFragmentCompatStyle,
                0);

        @SuppressLint("PrivateResource")
        final boolean allowDividerAfterLastItem = a.getBoolean(
                androidx.preference.R.styleable.PreferenceFragmentCompat_allowDividerAfterLastItem, true);

        a.recycle();

        getListView().addItemDecoration(mDividerDecoration);
        mDividerDecoration.setAllowDividerAfterLastItem(allowDividerAfterLastItem);

        return view;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    protected RecyclerView.Adapter<?> onCreateAdapter(@NonNull PreferenceScreen preferenceScreen) {
        return new PreferenceGroupAdapter(preferenceScreen);
    }

    @Override
    public void onDisplayPreferenceDialog(@NonNull Preference preference) {
        // check if dialog is already showing
        if (getParentFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG) != null) {
            return;
        }

        final DialogFragment f;
        if (preference instanceof EditTextPreference) {
            f = EditTextPreferenceDialogFragmentCompat.newInstance(preference.getKey());
        } else if (preference instanceof ListPreference) {
            f = ListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
        } else if (preference instanceof MultiSelectListPreference) {
            f = MultiSelectListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
        } else {
            super.onDisplayPreferenceDialog(preference);
            return;
        }
        f.setTargetFragment(this, 0);
        f.show(getParentFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void setDivider(@Nullable Drawable divider) {
        // skip super divider logic
        super.setDivider(null);
    }

    @Override
    public void setDividerHeight(int height) {
        // skip super divider logic
        super.setDividerHeight(0);
        mDividerDecoration.setDividerHeight(height);
    }

    /**
     * A divider decoration that fixes bug for androidx version.
     * Does not support divider drawable for now.
     */
    private class DividerDecoration extends RecyclerView.ItemDecoration {
        private int mDividerHeight;
        private boolean mAllowDividerAfterLastItem = true;

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            final RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
            if (shouldDrawDividerAbove(holder, parent)) {
                outRect.top = mDividerHeight;
            }

            final RecyclerView.Adapter<?> adapter = parent.getAdapter();
            final int itemCount = adapter != null ? adapter.getItemCount() : 0;
            if (holder.getAdapterPosition() == itemCount - 1 && mAllowDividerAfterLastItem) {
                outRect.bottom = 60/*mDividerHeight*/;
            }
            TextView mTitleView = holder.itemView.findViewById(android.R.id.title);
            if (mTitleView != null) {
                if (holder.getAdapterPosition() == 0
                        && !TextUtils.isEmpty(mTitleView.getText())) {
                    if (holder.itemView instanceof PreferenceLinearLayout) {
                        outRect.top = 24;
                    } else {
                        // When the first Preference is PreferenceCategory, remove the top spacing
                        outRect.top = -48;
                    }
                }
            }
        }

        private boolean shouldDrawDividerAbove(RecyclerView.ViewHolder holder,
                                               RecyclerView parent) {
            final boolean dividerAllowedAbove = holder instanceof PreferenceViewHolder
                    && ((PreferenceViewHolder) holder).isDividerAllowedAbove();
            if (!dividerAllowedAbove) {
                return false;
            }
            boolean prevAllowed = false;
            // use adapter position to find previous holder
            final int position = holder.getAdapterPosition();
            RecyclerView.ViewHolder prevHolder =
                    parent.findViewHolderForAdapterPosition(position - 1);
            final int index = parent.indexOfChild(holder.itemView);
            if (prevHolder == null && index > 0) {
                final View prevView = parent.getChildAt(index - 1);
                prevHolder = parent.getChildViewHolder(prevView);
            }

            if (prevHolder != null) {
                prevAllowed = prevHolder instanceof PreferenceViewHolder
                        && ((PreferenceViewHolder) prevHolder).isDividerAllowedBelow();
            }
            return prevAllowed;
        }

        public void setDividerHeight(int dividerHeight) {
            mDividerHeight = dividerHeight;
            getListView().invalidateItemDecorations();
        }

        public void setAllowDividerAfterLastItem(boolean allowDividerAfterLastItem) {
            mAllowDividerAfterLastItem = allowDividerAfterLastItem;
        }
    }
}
