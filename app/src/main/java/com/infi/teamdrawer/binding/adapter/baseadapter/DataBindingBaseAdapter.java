package com.infi.teamdrawer.binding.adapter.baseadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.infi.teamdrawer.binding.adapter.simple.model.IDataBindingAdapterItemBaseVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto B. on 18/01/2017.
 * Have fun
 */

public class DataBindingBaseAdapter<T extends IDataBindingAdapterItemBaseVM> extends BaseAdapter {

    private final List<T> mItems = new ArrayList<>();

    public void setItems(List<T> items) {
        mItems.clear();

        if (items != null) {
            mItems.addAll(items);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        T item = mItems.get(position);
        ViewDataBinding binder = bindItem(item, convertView, parent);

        return binder.getRoot();
    }

    protected ViewDataBinding bindItem(T item, View convertView, ViewGroup parent) {
        ViewDataBinding binder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            binder = DataBindingUtil.inflate(inflater, item.getLayoutResId(), parent, false);
            binder.getRoot().setTag(binder);
        } else {
            binder = (ViewDataBinding) convertView.getTag();
        }

        binder.setVariable(item.getBindingVariable(), item);
        binder.executePendingBindings();

        return binder;
    }

    private class IDataBindingAdapterItemBaseVM {
    }
}
