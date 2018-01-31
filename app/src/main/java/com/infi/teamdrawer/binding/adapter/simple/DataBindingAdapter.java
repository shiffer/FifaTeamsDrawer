package com.infi.teamdrawer.binding.adapter.simple;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.infi.teamdrawer.BR;
import com.infi.teamdrawer.utils.JavaUtils;
import com.infi.teamdrawer.binding.DataBindingViewHolderObservable;
import com.infi.teamdrawer.binding.adapter.simple.model.IDataBindingAdapterExpandableItemBaseVM;
import com.infi.teamdrawer.binding.adapter.simple.model.IDataBindingAdapterItemBaseVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto on 12/01/2017.
 * Have fun!
 */

public class DataBindingAdapter<T extends IDataBindingAdapterItemBaseVM> extends RecyclerView.Adapter<DataBindingAdapter.ViewHolder> {

    private List<T> mItems = new ArrayList<>();

    public DataBindingAdapter() {}

    public DataBindingAdapter(List<T> list) {
        mItems = list;
    }

    public void setItems(List<T> items) {
        mItems.clear();

        if (items != null) {
            mItems.addAll(items);
        }

        ((ArrayList)mItems).trimToSize();

        notifyDataSetChanged();
    }

    public void insertItemAt(int index, T item) {
        if (index >= getItemCount()) {
            mItems.add(item);
            notifyItemInserted(getItemCount()-1);
        } else {
            mItems.add(index, item);
            notifyItemInserted(index);
        }
    }

    public void insertItemsAt(int index, List<T> items) {
        if (JavaUtils.isNotNullNotEmpty(items)) {
            if (index >= getItemCount()) {
                index = getItemCount();
                mItems.addAll(items);
            } else {
                mItems.addAll(index, items);
            }

            notifyItemRangeInserted(index, items.size());
        }
    }

    public void replaceItemsFromIndex(int index, List<T> items) {
        final int prevItemsCount = getItemCount();
        if (index < prevItemsCount) {
            List<T> tempList = new ArrayList<>(mItems.subList(0, index));
            mItems.clear();
            mItems.addAll(tempList);

            if (items != null) {
                mItems.addAll(items);
                if (getItemCount() > prevItemsCount) {
                    notifyItemRangeChanged(index, prevItemsCount - index);
                    notifyItemRangeInserted(prevItemsCount, getItemCount() - prevItemsCount);
                } else {
                    notifyItemRangeChanged(index, getItemCount());
                    if (getItemCount() < prevItemsCount) {
                        notifyItemRangeRemoved(getItemCount(), prevItemsCount - getItemCount());
                    }
                }
            } else {
                notifyItemRangeRemoved(index, (prevItemsCount - index));
            }

            ((ArrayList)mItems).trimToSize();
        } else {
            addItems(items);
        }
    }

    public T replaceItemAt(int index, T item) {
        T removed = mItems.set(index, item);
        notifyItemChanged(index);
        return removed;
    }

    public void addItems(List<T> items) {
        insertItemsAt(getItemCount(), items);
    }

    public void addItemsAt(List<T> items, int index) {
        insertItemsAt(index, items);
    }

    public void addItem(T item) {
        insertItemAt(getItemCount(), item);
    }

    public void addItemAt(int index, T item) {
        insertItemAt(index, item);
    }

    public void removeItem(T item) {
        int index = mItems.indexOf(item);
        if (index != -1) {
            mItems.remove(index);

            notifyItemRemoved(index);
        }
    }

    public void removeItemAt(int index) {
        if (index < mItems.size()) {
            mItems.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Nullable
    public T getItemAt(int position) {
        if (position < mItems.size()) {
            return mItems.get(position);
        }

        return null;
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //viewType is actually the layoutResId
        ViewDataBinding binder = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), viewType, parent, false);
        //noinspection unchecked
        return new ViewHolder(binder, mOnPropertyChangedCallback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //noinspection unchecked
        holder.bindData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getLayoutResId();
    }

    public boolean notifyItemChanged(T item) {
        int index = mItems.indexOf(item);
        if (index >= 0) {
            notifyItemChanged(index);
            return true;
        }
        return false;
    }

    private Observable.OnPropertyChangedCallback mOnPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @SuppressWarnings("SuspiciousMethodCalls")
        @Override
        public void onPropertyChanged(Observable observable, int variable) {

            //noinspection unchecked
            IDataBindingAdapterExpandableItemBaseVM<T> item = (IDataBindingAdapterExpandableItemBaseVM<T>) observable;

            if (variable == BR.expanded) {
                if (item.isExpanded()) {
                    expandItem(item);
                } else {
                    collapseItem(item);
                }
            } else if (variable == BR.children) {
                onChildrenChanged(item);
            } else if (variable == BR.addChildIndex) {
                onChildrenAdded(item);
            } else if (variable == BR.removedChildIndex) {
                onChildRemoved(item);
            }
        }
    };

    private void onChildrenChanged(IDataBindingAdapterExpandableItemBaseVM<T> item) {
        if (item.isExpanded()) {
            // Get parent index
            //noinspection SuspiciousMethodCalls
            int parentIndex = mItems.indexOf(item);
            if (parentIndex >= 0) {
                // Remove previous items
                Integer previousChildrenCount = item.getPreviousChildrenCount();
                if (previousChildrenCount != null && previousChildrenCount > 0) {
                    for (int i = 0; i < previousChildrenCount; i++) {
                        removeItemAt(parentIndex + 1);
                    }
                }
                // Add new items
                List<T> children = item.getChildren();
                if (JavaUtils.isNotNullNotEmpty(children)) {
                    addItemsAt(children, parentIndex + 1);
                }
            }
        }
    }

    private void onChildrenAdded(IDataBindingAdapterExpandableItemBaseVM<T> item) {
        if (item.isExpanded()) {
            Integer addedChildIndex = item.getAddChildIndex();
            if (addedChildIndex != null) {
                //noinspection SuspiciousMethodCalls
                int parentIndex = mItems.indexOf(item);
                if (parentIndex >= 0) {
                    List<T> children = item.getChildren();
                    if (addedChildIndex < children.size()) {

                        List<T> newChildren = item.getChildren().subList(addedChildIndex, addedChildIndex + item.getAddedChildrenCount());

                        addItemsAt(newChildren, parentIndex + 1 + addedChildIndex);
                    }
                }
            }
        }
    }

    private void onChildRemoved(IDataBindingAdapterExpandableItemBaseVM<T> item) {
        if (item.isExpanded()) {
            Integer removedChildIndex = item.getRemovedChildIndex();
            if (removedChildIndex != null) {
                //noinspection SuspiciousMethodCalls
                int parentIndex = mItems.indexOf(item);
                if (parentIndex >= 0) {
                    removeItemAt(parentIndex + 1 + removedChildIndex);
                }
            }
        }
    }

    protected boolean expandItem(IDataBindingAdapterExpandableItemBaseVM<T> item) {
        //noinspection SuspiciousMethodCalls
        int index = mItems.indexOf(item);
        return expandItem(item, index);
    }

    protected boolean expandItem(IDataBindingAdapterExpandableItemBaseVM<T> item, int parentIndex) {
        List<T> children = item.getChildren();

        if (parentIndex == -1 || children.isEmpty()) return false;

        insertItemsAt(parentIndex+1, children);

        return true;
    }

    protected boolean collapseItem(IDataBindingAdapterExpandableItemBaseVM<T> item) {
        //noinspection SuspiciousMethodCalls
        int index = mItems.indexOf(item);
        return collapseItem(item, index);
    }

    protected boolean collapseItem(IDataBindingAdapterExpandableItemBaseVM<T> item, int parentIndex) {
        List<T> children = item.getChildren();

        if (parentIndex == -1 || children.isEmpty()) return false;

        final int childrenSize = children.size();
        for (int i = childrenSize -1; i >= 0; i--) {
            int index = parentIndex + i + 1;
            IDataBindingAdapterItemBaseVM it = mItems.get(index);
            if (it instanceof IDataBindingAdapterExpandableItemBaseVM) {
                //noinspection unchecked
                collapseItem((IDataBindingAdapterExpandableItemBaseVM<T>) it);
            }

            mItems.remove(index);
        }

        notifyItemRangeRemoved(parentIndex + 1, childrenSize);

        return true;
    }

    public static class ViewHolder<VM extends IDataBindingAdapterItemBaseVM & Observable, B extends ViewDataBinding>
            extends DataBindingViewHolderObservable<B, VM> {

        private Observable.OnPropertyChangedCallback mOnPropertyChangedCallback;

        public ViewHolder(B binder, Observable.OnPropertyChangedCallback propertyChangedCallback) {
            super(binder);
            mOnPropertyChangedCallback = propertyChangedCallback;
        }

        @Override
        public void bindData(VM data) {
            getBinder().setVariable(data.getBindingVariable(), data);
            //getBinder().getRoot().setTag(R.id.adapter_view_item, data);

            if (data instanceof IDataBindingAdapterExpandableItemBaseVM) {
                data.addOnPropertyChangedCallback(mOnPropertyChangedCallback);
            }
        }
    }
}
