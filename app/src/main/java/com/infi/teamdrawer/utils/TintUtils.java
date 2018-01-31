package com.infi.teamdrawer.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by roberto on 10/11/2016.
 * Have fun!
 */

public class TintUtils {

    public static Drawable tint(@NonNull Context context, @DrawableRes int drawableResId, @ColorRes int colorResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, colorResId));
        return drawable;
    }

    public static Drawable tint(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable == null)
            return null;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            DrawableCompat.setTint(drawable, color);
        }

        return drawable;
    }

    public static void tintMenuAndToolbar(@Nullable Toolbar toolbar, @ColorInt int color) {
        tintToolbar(toolbar, color);
        if (toolbar != null) {
            tintMenu(toolbar.getMenu(), color);
        }
    }

    public static void tintMenu(@Nullable Menu menu, @ColorInt int color) {
        if (menu != null) {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                if (item != null) {
                    Drawable icon = item.getIcon();
                    if (icon != null) {
                        icon = icon.mutate();
                        tint(icon, color);
                        item.setIcon(icon);
                    }
                }
            }
        }
    }

    public static void tintToolbar(@Nullable Toolbar toolbar, @ColorInt int color) {
        if (toolbar != null) {
            final Drawable overflowIcon = toolbar.getOverflowIcon();
            Drawable navigationIcon = toolbar.getNavigationIcon();

            tint(overflowIcon, color);

            if (navigationIcon != null) {
                navigationIcon = navigationIcon.mutate();
                tint(navigationIcon, color);
                toolbar.setNavigationIcon(navigationIcon);
            }
        }
    }
}
