package deadline.grade;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by john on 2017/4/24.
 */

public class ScanAnimationView extends FrameLayout {
    private ImageView mLeftImage;
    private RotateAnimation mRotateAnimation;
    private boolean isStart = false;
    private ImageView point1, point2, point3, point4, point5, point6;
    private boolean isShowPoint = false;

    public ScanAnimationView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ScanAnimationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScanAnimationView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initView(context);
        initAnimation(context);
    }

    private void initAnimation(Context context) {
        mRotateAnimation = new RotateAnimation(315, 410, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(14);
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mRotateAnimation.setRepeatMode(RotateAnimation.REVERSE);
        mRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isStart = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                isShowPoint = !isShowPoint;
                if (isShowPoint) {
                    startScaleAnimation(point1);
                    startScaleAnimation(point2);
                    startScaleAnimation(point3);
                    startScaleAnimation(point4);
                    startScaleAnimation(point5);
                    startScaleAnimation(point6);


                }
            }
        });
    }

    private void startScaleAnimation(final View view) {
        view.setVisibility(VISIBLE);
        FrameLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Random random = new Random();
        layoutParams.leftMargin = 300 + random.nextInt(7) * 100;
        layoutParams.topMargin = 300 + random.nextInt(3) * 100;
        view.setLayoutParams(layoutParams);

        AnimationSet animationSet = new AnimationSet(true);
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.4f, 1.0f, 0.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationSet.addAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setDuration(500);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }

    private ImageView creatImageView(Context context, @DrawableRes int image) {
        Random random = new Random();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(image);
        int i = random.nextInt(50);
        addView(imageView, new ViewGroup.LayoutParams(50+i, 50+i));
        return imageView;
    }


    private void initView(Context context) {
        point1 = creatImageView(context, R.mipmap.main_scan_point_1);
        point2 = creatImageView(context, R.mipmap.main_scan_point_2);
        point3 = creatImageView(context, R.mipmap.main_scan_point_1);
        point4 = creatImageView(context, R.mipmap.main_scan_point_2);
        point5 = creatImageView(context, R.mipmap.main_scan_point_1);
        point6 = creatImageView(context, R.mipmap.main_scan_point_2);

        mLeftImage = new ImageView(context);
        mLeftImage.setImageResource(R.mipmap.scan_left);
        mLeftImage.setScaleType(ImageView.ScaleType.CENTER);
        addView(mLeftImage, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }


    public void startScanAnimation() {
        if (isStart) return;
        if (mRotateAnimation == null) return;
        if (mLeftImage == null) return;
        isStart = true;

        mLeftImage.startAnimation(mRotateAnimation);

    }

    public void cancelScanAnimation() {
        if (!isStart) return;
        if (mRotateAnimation == null) return;

        mRotateAnimation.cancel();
        mRotateAnimation = null;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.max(measureHeight, measureWidth), Math.max(measureHeight, measureWidth));
    }

}
