package by.intervale.akella266.rssreader.views.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mScrollChild;

    public ScrollChildSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollChild != null){
            return mScrollChild.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }

    public void setmScrollChild(View mScrollChild) {
        this.mScrollChild = mScrollChild;
    }
}
