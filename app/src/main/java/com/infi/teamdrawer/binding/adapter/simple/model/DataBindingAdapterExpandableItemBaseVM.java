package com.infi.teamdrawer.binding.adapter.simple.model;

import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.infi.teamdrawer.BR;
import com.infi.teamdrawer.utils.JavaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto on 23/04/2017.
 * Have fun!
 */

public abstract class DataBindingAdapterExpandableItemBaseVM<T extends IDataBindingAdapterItemBaseVM>
        extends DataBindingAdapterItemBaseVM
        implements IDataBindingAdapterExpandableItemBaseVM<T> {

    private final ArrayList<T> mChildren = new ArrayList<>();

    private boolean mIsExpanded;

    private Integer mPreviousChildrenCount;
    private Integer mAddedChildIndex;
    private Integer mAddedChildrenCount;
    private Integer mRemovedChildIndex;

    private Callback mCallback;

    public interface Callback<VM extends DataBindingAdapterExpandableItemBaseVM> {
        void onExpandOrCollapse(VM viewModel);
    }

    public DataBindingAdapterExpandableItemBaseVM(int id) {
        super(id);
    }

    public DataBindingAdapterExpandableItemBaseVM() {
    }

    public void setExpandableCallback(Callback callback) {
        mCallback = callback;
    }

    @Bindable
    @Override
    public boolean isExpanded() {
        return mIsExpanded;
    }

    @NonNull
    @Bindable
    @Override
    public List<T> getChildren() {
        return mChildren;
    }

    @Override
    public Integer getPreviousChildrenCount() {
        return mPreviousChildrenCount;
    }

    @Bindable
    @Override
    public Integer getAddChildIndex() {
        return mAddedChildIndex;
    }

    @Override
    public Integer getAddedChildrenCount() {
        return mAddedChildrenCount;
    }

    @Bindable
    @Override
    public Integer getRemovedChildIndex() {
        return mRemovedChildIndex;
    }

    public boolean toggleExpanded() {
        setExpanded(!mIsExpanded);
        return mIsExpanded;
    }

    public boolean setExpanded(boolean isExpanded) {
        if (mIsExpanded != isExpanded) {
            mIsExpanded = isExpanded;
            notifyPropertyChanged(BR.expanded);
            if (mCallback != null) {
                //noinspection unchecked
                mCallback.onExpandOrCollapse(this);
            }
            return true;
        }

        return false;
    }

    public void setChildren(List<T> children) {
        if (!JavaUtils.equals(children, mChildren)) {
            mPreviousChildrenCount = mChildren.size();

            mChildren.clear();

            if (children != null) {
                mChildren.addAll(children);
            }

            mChildren.trimToSize();

            notifyPropertyChanged(BR.children);
        }
    }

    /**
     * Add a child to the END of the Children list
     */
    public void addChild(T child) {
        if (child != null) {
            mPreviousChildrenCount = mChildren.size();
            mAddedChildIndex = mChildren.size();
            mChildren.add(child);
            mAddedChildrenCount = 1;
            notifyPropertyChanged(BR.addChildIndex);
        }
    }

    /**
     * Add a child to the Children list at a specific position
     */
    public void addChildAt(T child, int position) {
        if (child != null) {
            mPreviousChildrenCount = mChildren.size();
            mChildren.add(position, child);
            mAddedChildIndex = position;
            mAddedChildrenCount = 1;
            notifyPropertyChanged(BR.addChildIndex);
        }
    }

    public void addChildren(List<T> children) {
        mPreviousChildrenCount = mChildren.size();
        mAddedChildIndex = mChildren.size();
        mChildren.addAll(children);
        mAddedChildrenCount = children.size();
        notifyPropertyChanged(BR.addChildIndex);
    }

    public void addChildrenAt(List<T> children, int position) {
        mPreviousChildrenCount = mChildren.size();
        mChildren.addAll(position, children);
        mAddedChildIndex = position;
        mAddedChildrenCount = children.size();
        notifyPropertyChanged(BR.addChildIndex);
    }

    /**
     * Remove child from the Children list
     */
    public void removeChild(T child) {
        if (child != null) {
            int indexOf = mChildren.indexOf(child);
            if (indexOf >= 0) {
                mPreviousChildrenCount = mChildren.size();
                mChildren.remove(indexOf);
                mRemovedChildIndex = indexOf;
                notifyPropertyChanged(BR.removedChildIndex);
            }
        }
    }

    /**
     * Remove child from Children list at a specific position
     */
    public void removeChild(int position) {
        if (position >= 0 && position < mChildren.size()) {
            mPreviousChildrenCount = mChildren.size();
            mChildren.remove(position);
            mRemovedChildIndex = position;
            notifyPropertyChanged(BR.removedChildIndex);
        }
    }
}
