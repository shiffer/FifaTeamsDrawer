package com.infi.teamdrawer.binding.adapter.simple.model;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by roberto on 23/04/2017.
 * Have fun!
 */

public interface IDataBindingAdapterExpandableItemBaseVM<T extends IDataBindingAdapterItemBaseVM> extends IDataBindingAdapterItemBaseVM {

    boolean isExpanded();

    @NonNull
    List<T> getChildren();

    Integer getPreviousChildrenCount();

    Integer getAddChildIndex();

    Integer getAddedChildrenCount();

    Integer getRemovedChildIndex();
}
