package com.waterfairy.corner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.waterfairy.corner.R;
import com.waterfairy.corner.bean.ImageBean;
import com.waterfairy.corner.utils.ImageInfoUtils;
import com.waterfairy.corner.utils.MetricsUtils;

import java.io.File;
import java.util.List;

/**
 * Created by shui on 2016/9/19.
 */
public class PicAdapter extends BaseAdapter {
    private List<ImageBean> mList;
    private Context mContext;
    private int mWidth;

    public PicAdapter(Context mContext, List<ImageBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mWidth = MetricsUtils.getLen(MetricsUtils.item_library);
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private ViewHolder mViewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_res, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        mViewHolder = (ViewHolder) convertView.getTag();

        ImageBean imageBean = mList.get(position);
        if (imageBean.getType() == ImageInfoUtils.TYPE_IMAGE) {
            Picasso.with(mContext)
                    .load(new File(imageBean.getPath()))
                    .centerCrop()
                    .resize(mWidth, mWidth)
                    .into(mViewHolder.imageView);
        }
        mViewHolder.name.setText(imageBean.getName());
        return convertView;
    }

    class ViewHolder {

        private View view;
        private ImageView imageView;
        private TextView name;

        public ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.file_img);
            name = (TextView) view.findViewById(R.id.file_name);
            imageView.getLayoutParams().width = mWidth;
            imageView.getLayoutParams().height = mWidth;
        }
    }
}
