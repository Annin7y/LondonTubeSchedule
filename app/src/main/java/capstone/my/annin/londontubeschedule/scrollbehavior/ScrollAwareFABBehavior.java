package capstone.my.annin.londontubeschedule.scrollbehavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    //code copied and pasted from:
    // https://medium.com/@beraldofilippo/android-coordinatorlayout-scroll-aware-fab-f0c6264a5ed1

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        // This is mandatory if we're assigning the behavior straight from XML
        super();
    }


    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child, final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }


    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child, final View target, final int dxConsumed, final int dyConsumed, final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE ) {

            child.hide((new FloatingActionButton.OnVisibilityChangedListener() {
                /**
                 * Called when a FloatingActionButton has been hidden
                 *
                 * @param fab the FloatingActionButton that was hidden.
                 */
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onShown(fab);
                    ((View)fab).setVisibility(View.INVISIBLE);
                }
            }));

        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.show();
        }
    }}




