package com.ashour.test.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashour.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileItemView extends RelativeLayout {
    @BindView(R.id.tv_item_title) TextView itemTitleTextView;
    @BindView(R.id.img_item_image) ImageView itemIconImageView;
    @BindView(R.id.rl_item_container) RelativeLayout itemContainerRelativeLayout;

    public ProfileItemView(Context context) {
        super(context);
        init();
    }

    public ProfileItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context, attrs);
    }

    public ProfileItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(context, attrs);
    }

    private void init() {
        View view = inflate(getContext(), R.layout.profile_item_view, this);
        ButterKnife.bind(this, view);
    }

    private void setAttrs(Context context, AttributeSet attrs) {
        init();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuItemView);
        itemTitleTextView.setText(a.getString(R.styleable.MenuItemView_title));
        itemIconImageView.setImageResource(a.getResourceId(R.styleable.MenuItemView_image, -1));

        a.recycle();
    }

    public void setItemTitleTextView(String titleTextView) {
        itemTitleTextView.setText(titleTextView);
    }

    public void setItemIconImageView(int id) {
        itemIconImageView.setImageResource(id);
    }
}
