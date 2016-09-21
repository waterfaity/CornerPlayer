package com.waterfairy.corner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.waterfairy.corner.R;
import com.waterfairy.corner.activity.MainActivity;
import com.waterfairy.corner.bean.ImageBean;
import com.waterfairy.corner.utils.ImageInfoUtils;
import com.waterfairy.corner.utils.MetricsUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shui on 2016/9/19.
 */
public class PicAdapter extends BaseAdapter {
    private List<ImageBean> mList;
    private Activity mContext;
    private int mWidth;
    private List<Integer> mChecked;
    private boolean isCheckboxVisibility;

    public PicAdapter(Activity mContext, List<ImageBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mWidth = MetricsUtils.getLen(MetricsUtils.item_library);
        mChecked = new ArrayList<>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        mViewHolder.checkBox.setTag(position);
        if (isCheckboxVisibility) {
            mViewHolder.checkBox.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.checkBox.setVisibility(View.GONE);
        }
        mViewHolder.checkBox.setChecked(false);
        mViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = ((CheckBox) v.getTag());
                checkBox.performClick();
                Integer position = (Integer) checkBox.getTag();
                if (checkBox.isChecked()) {
                    mChecked.add(position);
                } else {
                    mChecked.remove(position);
                }

            }
        });
        mViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongClickListener != null) {
                    int position = (int) ((CheckBox) v.getTag()).getTag();
                    onLongClickListener.onOnLongLick(v, position);
                }
                return false;
            }
        });
        return convertView;
    }

    public void setOnLongClickListener(OnLongClickListener clickListener) {

        this.onLongClickListener = clickListener;
    }

    class ViewHolder {

        private View view;
        private ImageView imageView;
        private TextView name;
        private CheckBox checkBox;

        public ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.file_img);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            name = (TextView) view.findViewById(R.id.file_name);
            imageView.getLayoutParams().width = mWidth;
            imageView.getLayoutParams().height = mWidth;
            imageView.setTag(checkBox);

        }
    }

    public interface OnLongClickListener {
        void onOnLongLick(View view, int position);
    }

    private OnLongClickListener onLongClickListener;

    private OnCheckedListener onCheckedListener;


    public void setOnCheckedListener(OnCheckedListener checkedListener) {
        this.onCheckedListener = checkedListener;

    }

    public interface OnCheckedListener {
        void onChecked(List<Integer> checked);
    }

    public List<Integer> getChecked() {
        return mChecked;
    }

    public void setCheckboxVisibility(boolean visibility) {
        mChecked.removeAll(mChecked);
        this.isCheckboxVisibility = visibility;
        notifyDataSetChanged();

    }
}
