package com.infi.teamdrawer.binding;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.infi.teamdrawer.utils.JavaUtils;
import com.infi.teamdrawer.R;
import com.infi.teamdrawer.utils.TintUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto on 10/11/2016.
 * Have fun!
 */

public class BindingUtils {
    private static final String TAG = "BindingUtils";
    public static final int NULL_RES_ID = 0;

    @BindingAdapter(value = {"scrollToPosition", "smoothScroll"}, requireAll = false)
    public static void scrollToPosition(RecyclerView view, Integer position, boolean smooth) {
        if (position != null) {
            if (smooth) {
                view.smoothScrollToPosition(position);
            } else {
                view.scrollToPosition(position);
            }
        }
    }

    @BindingAdapter(value = {"adapter"}, requireAll = false)
    public static void setRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        if (recyclerView.getAdapter() != adapter) {
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("requestFocus")
    public static void requestFocus(View view, Boolean focus) {
        if (focus != null) {
            if (focus) {
                view.requestFocus();
            } else {
                view.clearFocus();
            }
        }
    }

    @BindingAdapter({"backgroundTint"})
    public static void setBackgroundTint(FloatingActionButton view, int color) {
        if (view.getBackgroundTintList() != null
                && view.getBackgroundTintList().getDefaultColor() == color) return;
        setBackgroundTint(view, ColorStateList.valueOf(color));
    }

    @BindingAdapter({"backgroundTint"})
    public static void setBackgroundTint(FloatingActionButton view, ColorStateList color) {
        view.setBackgroundTintList(color);
    }

    @BindingAdapter("drawableResId")
    public static void setFabIcon(FloatingActionButton fab, @DrawableRes int drawableResId) {
        Drawable dr = ContextCompat.getDrawable(fab.getContext(), drawableResId);
        fab.setImageDrawable(dr);
    }

    /********************
     * View
     ***********************/
    @BindingAdapter(value = {"paddingStartResId", "paddingTopResId", "paddingEndResId", "paddingBottomResId"}, requireAll = false)
    public static void setPaddingResId(View view, @DimenRes int startResId, @DimenRes int topResId,
                                       @DimenRes int endResId, @DimenRes int bottomResId) {

        int start = view.getPaddingStart();
        int top = view.getPaddingTop();
        int end = view.getPaddingEnd();
        int bottom = view.getPaddingBottom();

        Resources res = view.getResources();

        if (startResId != 0) {
            start = res.getDimensionPixelSize(startResId);
        }
        if (topResId != 0) {
            top = res.getDimensionPixelSize(topResId);
        }
        if (endResId != 0) {
            end = res.getDimensionPixelSize(endResId);
        }
        if (bottomResId != 0) {
            bottom = res.getDimensionPixelSize(bottomResId);
        }

        if (start != view.getPaddingStart() || top != view.getPaddingTop()
                || end != view.getPaddingEnd() || bottom != view.getPaddingBottom()) {
            view.setPaddingRelative(start, top, end, bottom);
        }
    }

    /********************
     * TextInputLayout
     ***********************/
    @BindingAdapter(value = {"error", "errorResId"})
    public static void setInputError(TextInputLayout view, String error, @StringRes int errorResId) {
        if (error == null && errorResId != 0) {
            error = view.getResources().getString(errorResId);
        }

        view.setError(error);
    }

    /********************
     * TextView
     ***********************/
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];

    @BindingAdapter(value = {"textStyle"})
    public static void setTextStyle(TextView view, int typeFace) {
        try {
            view.setTypeface(Typeface.DEFAULT, typeFace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Manage the case we don't want any filter to be applied
     *
     * @param view
     * @param value
     */
    @BindingAdapter({"android:maxLength"})
    public static void setMaxLength(TextView view, int value) {
        if (value == -1) {
            if (view.getFilters() == null) {
                view.setFilters(NO_FILTERS);
            } else {
                InputFilter[] filters = view.getFilters();
                List<InputFilter> newFilters = new ArrayList<>();
                for (InputFilter filter : filters) {
                    if (!(filter instanceof InputFilter.LengthFilter)) {
                        newFilters.add(filter);
                    }
                }

                view.setFilters(newFilters.toArray(new InputFilter[newFilters.size()]));
            }
        } else {
            TextViewBindingAdapter.setMaxLength(view, value);
        }
    }

    @BindingAdapter("drawableStartCompat")
    public static void setStartDrawable(TextView view, int res) {
        if (res == 0) return;

        Drawable[] drawables = view.getCompoundDrawablesRelative();
        final Drawable start = ContextCompat.getDrawable(view.getContext(), res);
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(start, drawables[1], drawables[2], drawables[3]);
    }

    @BindingAdapter(value = {"text", "cursorAtEnd"}, requireAll = false)
    public static void setText(TextView view, Object text, boolean cursorAtEnd) {
        CharSequence resultText;
        if (text instanceof CharSequence) {
            resultText = (CharSequence) text;
        } else if (text instanceof Integer) {
            resultText = view.getResources().getText((Integer) text);
        } else {
            resultText = view.getText();
        }

        if (!TextUtils.equals(resultText, view.getText())) {
            view.setText(resultText);
        }

        if (cursorAtEnd && view instanceof EditText) {
            ((EditText) view).setSelection(resultText.length());
        }
    }

    @BindingAdapter(value = {"textSizeResId"})
    public static void setTextSize(TextView textView, @DimenRes int sizeResId) {
        float size = textView.getResources().getDimension(sizeResId);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    @BindingAdapter({"textColorResId"})
    public static void setTextColor(TextView view, @ColorRes int colorResId) {
        if (colorResId != 0) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), colorResId));
        }
    }

    @SuppressWarnings("ResourceAsColor")
    @BindingAdapter(requireAll = false, value = {
            "drawableStartResId", "drawableStartTintResId",
            "drawableTopResId", "drawableTopTintResId",
            "drawableEndResId", "drawableEndTintResId",
            "drawableBottomResId", "drawableBottomTintResId",
            "drawableTintResId"}/*for all drawables*/)
    public static void setDrawables(TextView textView,
                                    @DrawableRes int startResId, @ColorRes int colorStartResId,
                                    @DrawableRes int topResId, @ColorRes int colorTopResId,
                                    @DrawableRes int endResId, @ColorRes int colorEndResId,
                                    @DrawableRes int bottomResId, @ColorRes int colorBottomResId,
                                    @ColorRes int colorResId) {

        Drawable[] drawables = textView.getCompoundDrawables();
        final Context context = textView.getContext();
        int color;
        try {
            color = colorResId != 0 ? ContextCompat.getColor(context, colorResId) : 0;
        } catch (Resources.NotFoundException e) {
            color = colorResId;
        }

        int colorStart = color != 0 ? color : colorStartResId != 0 ? ContextCompat.getColor(context, colorStartResId) : 0;
        int colorTop = color != 0 ? color : colorTopResId != 0 ? ContextCompat.getColor(context, colorTopResId) : 0;
        int colorEnd = color != 0 ? color : colorEndResId != 0 ? ContextCompat.getColor(context, colorEndResId) : 0;
        int colorBottom = color != 0 ? color : colorBottomResId != 0 ? ContextCompat.getColor(context, colorBottomResId) : 0;

        Drawable start = setDrawableAndColor(context, drawables[0], startResId, colorStart);
        Drawable top = setDrawableAndColor(context, drawables[1], topResId, colorTop);
        Drawable end = setDrawableAndColor(context, drawables[2], endResId, colorEnd);
        Drawable bottom = setDrawableAndColor(context, drawables[3], bottomResId, colorBottom);

        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    @BindingAdapter(value = {"textToHighlight"})
    public static void highlightText(TextView textView, String textToHighlight) {
        String text = textView.getText().toString();
        if (JavaUtils.isNotEmpty(text)) {
            if (JavaUtils.isNotEmpty(textToHighlight) && text.toLowerCase().contains(textToHighlight.toLowerCase())) {
                int startPos = text.toLowerCase().indexOf(textToHighlight.toLowerCase());
                int endPos = startPos + textToHighlight.length();

                Spannable spanText = Spannable.Factory.getInstance().newSpannable(text);
                spanText.setSpan(new BackgroundColorSpan(ContextCompat.getColor(textView.getContext(), R.color.colorAccent)),
                        startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(spanText, TextView.BufferType.SPANNABLE);
            } else {
                textView.setText(text);
            }
        }
    }

    private static Drawable setDrawableAndColor(Context context, Drawable drawable, @DrawableRes int resId,
                                                @ColorInt int color) {
        if (resId != 0) {
            drawable = ContextCompat.getDrawable(context, resId);
        }

        if (color != 0 && drawable != null) {
            drawable = TintUtils.tint(drawable.mutate(), color);
        }

        return drawable;
    }

    @BindingAdapter(value = {"hintResId", "hintString", "hintArgs", "hintArgsResId"}, requireAll = false)
    public static void setHint(TextInputLayout view, @StringRes int stringResId, String text, Object[] args, int[] argsResId) {
        view.setHint(resolveTextString(view, stringResId, text, args, argsResId));
    }

    @BindingAdapter(value = {"hintResId", "hintString", "hintArgs", "hintArgsResId"}, requireAll = false)
    public static void setHint(TextView view, @StringRes int stringResId, String text, Object[] args, int[] argsResId) {
        view.setHint(resolveTextString(view, stringResId, text, args, argsResId));
    }

    @BindingAdapter(value = {"textResId", "textString", "textArgs", "textArgsResId"}, requireAll = false)
    public static void setText(TextView view, @StringRes int stringResId, String text, Object[] args, int[] argsResId) {
        view.setText(resolveTextString(view, stringResId, text, args, argsResId));
    }

    private static String resolveTextString(View view, @StringRes int stringResId, String text, Object[] args, int[] argsResId) {
        final Resources resources = view.getResources();

        if (stringResId != NULL_RES_ID) {
            text = resources.getString(stringResId);
        }

        if ((args != null || argsResId != null) && text != null) {

            if (argsResId != null) {
                args = new String[argsResId.length];
                for (int i = 0; i < argsResId.length; i++) {
                    try {
                        args[i] = resources.getString(argsResId[i]);
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else { //args cant be null here
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof TextResIdHolder) {
                        try {
                            args[i] = resources.getString(((TextResIdHolder) args[i]).getTextResId());
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            try {
                text = String.format(text, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return text;
    }


    @BindingAdapter(value = {"iconsColor", "iconsColorResId"}, requireAll = false)
    public static void setToolbarIconsColor(Toolbar toolbar, @ColorInt int color, @ColorRes int colorResId) {
        if (colorResId != 0) {
            color = ContextCompat.getColor(toolbar.getContext(), colorResId);
        }

        TintUtils.tintToolbar(toolbar, color);
    }

    @BindingAdapter(value = {"titleResId", "titleString", "titleArgs", "titleArgsResId"}, requireAll = false)
    public static void setText(Toolbar view, @StringRes int stringResId, String text, Object[] args, int[] argsResId) {
        view.setTitle(resolveTextString(view, stringResId, text, args, argsResId));
    }

    /**********************
     * Checkbox
     ***********************/
    @BindingAdapter(value = {"buttonTintColorRes", "buttonTintColor"}, requireAll = false)
    public static void setCheckboxTint(CompoundButton view, @ColorRes int colorResId, @ColorInt int color) {
        if (colorResId != NULL_RES_ID) {
            color = ContextCompat.getColor(view.getContext(), colorResId);
        }

        if (color != Color.TRANSPARENT) {
            ColorStateList tint = ColorStateList.valueOf(color);
            CompoundButtonCompat.setButtonTintList(view, tint);
        }
    }

    /**********************
     * ImageView
     ***********************/
    @BindingAdapter(value = {"drawableRes", "drawableTintColor", "drawableTintColorRes", "bitmap"}, requireAll = false)
    public static void setImageDrawableResource(final ImageView view, @DrawableRes int drawableResId, @ColorInt int color,
                                                @ColorRes int colorResId, Bitmap bitmap) {
        Drawable drawable;
        if (drawableResId != NULL_RES_ID) {
            drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
        } else if (bitmap != null) {
            drawable = new BitmapDrawable(view.getResources(), bitmap);
        } else {
            drawable = view.getDrawable();
        }

        if (drawable != null) {
            if (colorResId != NULL_RES_ID) {
                color = ContextCompat.getColor(view.getContext(), colorResId);
            }

            if (color != Color.TRANSPARENT) {
                drawable = drawable.mutate();
                try {
                    TintUtils.tint(drawable, color);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        view.setImageDrawable(drawable);
    }

    /********************
     * Url
     ***********************/
//    @BindingAdapter(value = {"imageUrl", "error", "errorResId", "placeHolder", "placeHolderResId", "isRound", "isDarken",
//            "roundCornerRadius", "skipSizing", "centerCrop", "cacheStrategy", "skipMemoryCache"}, requireAll = false)
//    public static void loadImage(ImageView imageView, Object image, Drawable error, @DrawableRes int errorResId,
//                                 Drawable placeHolder, @DrawableRes int placeHolderResId, boolean isRound, boolean isDarken, int roundCornerRadius,
//                                 boolean shouldSkipSizing, boolean isCenterCrop, DiskCacheStrategy cacheStrategy, boolean skipMemoryCache) {
//        if (image instanceof Drawable) {
//            imageView.setImageDrawable((Drawable) image);
//            return;
//        }
//
//        final Context context = imageView.getContext();
//
//        if (placeHolderResId != NULL_RES_ID) {
//            placeHolder = ContextCompat.getDrawable(context, placeHolderResId);
//        }
//
//        if (errorResId != NULL_RES_ID) {
//            error = ContextCompat.getDrawable(context, errorResId);
//        }
//
//        RequestBuilder<Drawable> builder = GlideApp.with(context).load(image);
//
//        completeRequest(builder, imageView, error, placeHolder, isRound, isDarken, roundCornerRadius, isCenterCrop, skipMemoryCache, cacheStrategy);
//    }
//
//    private static void completeRequest(RequestBuilder<Drawable> request, ImageView imageView, Drawable error, Drawable placeHolder,
//                                        boolean isRound, boolean isDarken, int roundCornerRadius, boolean isCenterCrop,
//                                        boolean skipMemoryCache, DiskCacheStrategy cacheStrategy) {
//
//        if (request == null) return;
//
//        final RequestOptions requestOptions = new RequestOptions()
//                .error(error)
//                .placeholder(placeHolder)
//                .diskCacheStrategy(cacheStrategy != null ? cacheStrategy : DiskCacheStrategy.DATA)
//                .skipMemoryCache(skipMemoryCache);
//
//        if (isCenterCrop) {
//            requestOptions.centerCrop();
//        }
//
//        ArrayList<Transformation<Bitmap>> transformationsArrayList = new ArrayList<>();
//        if (isRound) {
//            requestOptions.circleCrop();
////            transformationsArrayList.add(new CropCircleTransformation(imageView.getContext()));
//        }
//        if (isDarken) {
//            transformationsArrayList.add(new ColorFilterTransformation(
//                    ContextCompat.getColor(imageView.getContext(), R.color.gray_half_transparent)));
//        }
//        if (roundCornerRadius != 0) {
//            transformationsArrayList.add(new RoundedCornersTransformation(roundCornerRadius, 0));
//        }
//
//        if (!transformationsArrayList.isEmpty()) {
//            //noinspection unchecked
//            requestOptions.transforms(transformationsArrayList.toArray(new Transformation[transformationsArrayList.size()]));
////            requestOptions.transforms(transformationsArrayList);
//        }
//
//        request.apply(requestOptions);
//        request.into(imageView);
//    }
}
