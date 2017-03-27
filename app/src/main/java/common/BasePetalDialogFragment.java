package common;

import android.support.v7.app.AppCompatDialogFragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 17/3/20.
 */

public class BasePetalDialogFragment extends AppCompatDialogFragment {

    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
