package softwaredesign.adnursing.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private List<ImageView> imageViewList;

    public BannerAdapter(List<ImageView> list) {
        this.imageViewList = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViewList.get(position % imageViewList.size()));
        return imageViewList.get(position % imageViewList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViewList.get(position% imageViewList.size()));
    }

}