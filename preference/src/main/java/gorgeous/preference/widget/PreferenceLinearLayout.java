package gorgeous.preference.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import gorgeous.preference.R;

/**
 * A {@link LinearLayout} that handles first, middle, last and single state.
 * @see  android.R.attr#state_first
 * @see  android.R.attr#state_middle
 * @see  android.R.attr#state_last
 * @see  android.R.attr#state_single
 *
 * Created by marco, at 2024/07/24
 */
public class PreferenceLinearLayout extends LinearLayout {
    private State mState = State.MIDDLE;

    private final int paddingLeft         = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_left);
    private final int paddingRight        = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_right);
    private final int paddingTopFirst     = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_top_first);
    private final int paddingTopLast      = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_top_last);
    private final int paddingTopSingle    = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_top_single);
    private final int paddingTopMiddle    = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_top_middle);
    private final int paddingBottomFirst  = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_bottom_first);
    private final int paddingBottomLast   = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_bottom_last);
    private final int paddingBottomSingle = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_bottom_single);
    private final int paddingBottomMiddle = getContext().getResources().getDimensionPixelOffset(R.dimen.preference_padding_bottom_middle);

    public PreferenceLinearLayout(Context context) {
        super(context);
    }

    public PreferenceLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreferenceLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setState(State state) {
        if (mState != state) {
            mState = state;
            refreshDrawableState();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (mState == null) {
            return super.onCreateDrawableState(extraSpace);
        }

        int[] drawableState =
                super.onCreateDrawableState(extraSpace + mState.mStateIds.length);
        mergeDrawableStates(drawableState, mState.mStateIds);
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        // It seems that there is no better way to set the upper and lower spacing, @marco
        if (mState == State.SINGLE) {
            setPadding(paddingLeft, paddingTopSingle, paddingRight, paddingBottomSingle);
        } else if (mState == State.FIRST) {
            setPadding(paddingLeft, paddingTopFirst, paddingRight, paddingBottomFirst);
        } else if (mState == State.LAST) {
            setPadding(paddingLeft, paddingTopLast, paddingRight, paddingBottomLast);
        } else {
            setPadding(paddingLeft, paddingTopMiddle, paddingRight, paddingBottomMiddle);
        }
    }

    public enum State {
        FIRST(new int[] {android.R.attr.state_first}),
        MIDDLE(new int[] {android.R.attr.state_middle}),
        LAST(new int[] {android.R.attr.state_last}),
        SINGLE(new int[] {android.R.attr.state_single});

        private final int[] mStateIds;
        State(int[] stateIds) {
            mStateIds = stateIds;
        }
    }
}
