package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;

/**
 * A base list adapter class that Spinner can populate a list of the type M items for databinding
 * This class was modified from [com.geico.mobile.android.ace.coreframework.ui.AceBaseListAdapter]
 *
 * @param <M> The type of the data being adapted
 * @author John Sung, Geico
 */
public abstract class AceBaseListAdapter<M> extends BaseAdapter implements ListAdapter, SpinnerAdapter {
    private final List<M> items;

    public AceBaseListAdapter(@NonNull List<M> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewDataBinding binding = (convertView != null) ? DataBindingUtil.getBinding(convertView)
                : DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutResourceId(), parent, false);
        bindItem(binding, position);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    protected abstract void bindItem(ViewDataBinding holder, int position);

    protected abstract int getLayoutResourceId();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    protected M getItemAt(int position) {
        return (M) getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }
}
