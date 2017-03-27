package common;

import android.content.res.Resources;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.glooory.petal.R;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;

/**
 * Created by Glooory on 17/3/22.
 */

public abstract class BasePetalAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    protected final String mGeneralImageUrlFormat;
    protected final String mSmallImageUrlFormat;
    protected ImageLoader mImageLoader;

    public BasePetalAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
        Resources resources = PetalApplication.getContext().getResources();
        mGeneralImageUrlFormat = resources.getString(R.string.url_image_general_format);
        mSmallImageUrlFormat = resources.getString(R.string.url_image_small_format);
        mImageLoader = ((PetalApplication) PetalApplication.getContext()).getAppComponent().imageLoader();
    }
}
