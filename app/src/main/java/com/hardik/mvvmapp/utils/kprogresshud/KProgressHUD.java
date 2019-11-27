package com.hardik.mvvmapp.utils.kprogresshud;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.hardik.mvvmapp.R;

@SuppressWarnings("ALL")
public class KProgressHUD {

  // To avoid redundant APIs, all HUD functions will be forward to
  // a custom dialog
  private final ProgressDialog mProgressDialog;
  private final Context mContext;
  private float mDimAmount;
  private int mWindowColor;
  private float mCornerRadius;
  private int mAnimateSpeed;
  private int mMaxProgress;
  private boolean mIsAutoDismiss;
  private float scale;

  private KProgressHUD(Context context) {
    mContext = context;
    mProgressDialog = new ProgressDialog(context);
    mDimAmount = 0;
    //noinspection deprecation
    mWindowColor = context.getResources().getColor(R.color.kprogresshud_default_color);
    mAnimateSpeed = 1;
    mCornerRadius = 10;
    mIsAutoDismiss = true;

    //setStyle(Style.SPIN_INDETERMINATE);
  }

  /**
   * Create a new HUD. Have the same effect as the constructor.
   * For convenient only.
   *
   * @param context Activity context that the HUD bound to
   * @return An unique HUD instance
   */
  public static KProgressHUD create(Context context) {
    return new KProgressHUD(context);
  }

  /**
   * Specify the HUD style (not needed if you use a custom view)
   *
   * @param style One of the KProgressHUD.Style values
   * @return Current HUD
   */
  //public KProgressHUD setStyle(Style style) {
  //  View view = null;
  //  switch (style) {
  //    case SPIN_INDETERMINATE:
  //      //view = new SpinView(mContext);
  //      view = new ImageView(mContext);
  //      ((ImageView) view).setImageResource(R.drawable.loveva_heart_preloader);
  //      break;
  //    //case PIE_DETERMINATE:
  //    //  view = new PieView(mContext);
  //    //  break;
  //    //case ANNULAR_DETERMINATE:
  //    //  view = new AnnularView(mContext);
  //    //  break;
  //    //case BAR_DETERMINATE:
  //    //  view = new BarView(mContext);
  //    //  break;
  //    // No custom view style here, because view will be added later
  //  }
  //  mProgressDialog.setView(view);
  //  return this;
  //}

  /**
   * Specify the dim area around the HUD, like in Dialog
   *
   * @param dimAmount May take value from 0 to 1.
   * 0 means no dimming, 1 mean darkness
   * @return Current HUD
   */
  public KProgressHUD setDimAmount(float dimAmount) {
    if (dimAmount >= 0 && dimAmount <= 1) {
      mDimAmount = dimAmount;
    }
    return this;
  }

  /**
   * Set HUD size. If not the HUD view will use WRAP_CONTENT instead
   *
   * @param width in dp
   * @param height in dp
   * @return Current HUD
   */
  public KProgressHUD setSize(int width, int height) {
    mProgressDialog.setSize(width, height);
    return this;
  }

  /**
   * Specify the HUD background color
   *
   * @param color ARGB color
   * @return Current HUD
   */
  public KProgressHUD setWindowColor(int color) {
    mWindowColor = color;
    return this;
  }

  /**
   * Specify corner radius of the HUD (default is 10)
   *
   * @param radius Corner radius in dp
   * @return Current HUD
   */
  public KProgressHUD setCornerRadius(float radius) {
    mCornerRadius = radius;
    return this;
  }

  /**
   * Change animate speed relative to default. Only have effect when use with indeterminate style
   *
   * @param scale 1 is default, 2 means double speed, 0.5 means half speed..etc.
   * @return Current HUD
   */
  public KProgressHUD setAnimationSpeed(int scale) {
    mAnimateSpeed = scale;
    return this;
  }

  /**
   * Optional label to be displayed on the HUD
   *
   * @return Current HUD
   */
  public KProgressHUD setLabel(String label) {
    mProgressDialog.setLabel(label);
    return this;
  }

  /**
   * Optional detail description to be displayed on the HUD
   *
   * @return Current HUD
   */
  public KProgressHUD setDetailsLabel(String detailsLabel) {
    mProgressDialog.setDetailsLabel(detailsLabel);
    return this;
  }

  /**
   * Max value for use in one of the determinate styles
   *
   * @return Current HUD
   */
  public KProgressHUD setMaxProgress(int maxProgress) {
    mMaxProgress = maxProgress;
    return this;
  }

  /**
   * Set current progress. Only have effect when use with a determinate style, or a custom
   * view which implements Determinate interface.
   */
  //public void setProgress(int progress) {
  //  mProgressDialog.setProgress(progress);
  //}

  /**
   * Provide a custom view to be displayed.
   *
   * @param view Must not be null
   * @return Current HUD
   */
  //public KProgressHUD setCustomView(View view) {
  //  if (view != null) {
  //    mProgressDialog.setView(view);
  //  } else {
  //    throw new RuntimeException("Custom view must not be null!");
  //  }
  //  return this;
  //}

  /**
   * Specify whether this HUD can be cancelled by using back button (default is false)
   *
   * @return Current HUD
   */
  public KProgressHUD setCancellable(boolean isCancellable) {
    mProgressDialog.setCancelable(isCancellable);
    return this;
  }

  /**
   * Specify whether this HUD closes itself if progress reaches max. Default is true.
   *
   * @return Current HUD
   */
  public KProgressHUD setAutoDismiss(boolean isAutoDismiss) {
    mIsAutoDismiss = isAutoDismiss;
    return this;
  }

  public KProgressHUD show() {
    if (!isShowing()) {
      mProgressDialog.show();
    }
    return this;
  }

  public boolean isShowing() {
    return mProgressDialog != null && mProgressDialog.isShowing();
  }

  public void dismiss() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.dismiss();
    }
  }

  public enum Style {
    SPIN_INDETERMINATE, PIE_DETERMINATE, ANNULAR_DETERMINATE, BAR_DETERMINATE
  }

  private class ProgressDialog extends Dialog {

    private TextView mLabelText;
    private TextView mDetailsText;
    private String mLabel;
    private String mDetailsLabel;
    private LinearLayout mBackgroundLayout;
    private ImageView imageViewLoader;
    private int mWidth;
    private int mHeight;

    ProgressDialog(Context context) {
      super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.kprogresshud_hud);

      Window window = getWindow();
      if (window != null) {
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.dimAmount = mDimAmount;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
      }
      setCanceledOnTouchOutside(false);

      initViews();
    }

    private void initViews() {
      mBackgroundLayout = findViewById(R.id.background);
      imageViewLoader = findViewById(R.id.imageViewLoader);
      if (mWidth != 0) {
        updateBackgroundSize();
      }

      Glide.with(mContext)
          .load(R.drawable.loveva_heart_preloader)
          .into(imageViewLoader);

      mLabelText = findViewById(R.id.label);
      if (mLabel != null) {
        mLabelText.setText(mLabel);
        mLabelText.setVisibility(View.VISIBLE);
      } else {
        mLabelText.setVisibility(View.GONE);
      }
      mDetailsText = findViewById(R.id.details_label);
      if (mDetailsLabel != null) {
        mDetailsText.setText(mDetailsLabel);
        mDetailsText.setVisibility(View.VISIBLE);
      } else {
        mDetailsText.setVisibility(View.GONE);
      }
    }

    //private void addViewToFrame(View view) {
    //  if (view == null) return;
    //  int wrapParam = ViewGroup.LayoutParams.MATCH_PARENT;
    //  ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(wrapParam, wrapParam);
    //  mCustomViewContainer.addView(view, params);
    //}

    private void updateBackgroundSize() {
      ViewGroup.LayoutParams params = mBackgroundLayout.getLayoutParams();
      params.width = dpToPixel(mWidth, getContext());
      params.height = dpToPixel(mHeight, getContext());
      mBackgroundLayout.setLayoutParams(params);
    }

    //void setView(View view) {
    //  if (view != null) {
    //    if (view instanceof Determinate) {
    //      mDeterminateView = (Determinate) view;
    //    }
    //    //if (view instanceof Indeterminate) {
    //    //  mIndeterminateView = (Indeterminate) view;
    //    //}
    //    mView = view;
    //    if (isShowing()) {
    //      mCustomViewContainer.removeAllViews();
    //      addViewToFrame(view);
    //    }
    //  }
    //}

    void setLabel(String label) {
      mLabel = label;
      if (mLabelText != null) {
        if (label != null) {
          mLabelText.setText(label);
          mLabelText.setVisibility(View.VISIBLE);
        } else {
          mLabelText.setVisibility(View.GONE);
        }
      }
    }

    void setDetailsLabel(String detailsLabel) {
      mDetailsLabel = detailsLabel;
      if (mDetailsText != null) {
        if (detailsLabel != null) {
          mDetailsText.setText(detailsLabel);
          mDetailsText.setVisibility(View.VISIBLE);
        } else {
          mDetailsText.setVisibility(View.GONE);
        }
      }
    }

    void setSize(int width, int height) {
      mWidth = width;
      mHeight = height;
      if (mBackgroundLayout != null) {
        updateBackgroundSize();
      }
    }
  }

  int dpToPixel(float dp, Context context) {
    if (scale == 0) {
      scale = context.getResources().getDisplayMetrics().density;
    }
    return (int) (dp * scale);
  }
}
