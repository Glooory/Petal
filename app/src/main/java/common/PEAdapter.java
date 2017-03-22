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

public abstract class PEAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    protected final String mGeneralImageUrlFormat;
    protected final String mSmallImageUrlFormat;
    protected ImageLoader mImageLoader;

    public PEAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
        Resources resources = PEApplication.getContext().getResources();
        mGeneralImageUrlFormat = resources.getString(R.string.url_image_general_format);
        mSmallImageUrlFormat = resources.getString(R.string.url_image_small_format);
        mImageLoader = ((PEApplication) PEApplication.getContext()).getAppComponent().imageLoader();
    }
}
