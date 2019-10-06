package com.rnd.yongapp_mvvm.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rnd.yongapp_mvvm.R;
import com.rnd.yongapp_mvvm.databinding.ItemBlogBinding;
import com.rnd.yongapp_mvvm.model.Blog;
import com.rnd.yongapp_mvvm.viewmodel.MainItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.BindingHolder> {

    private List<Blog> blogs;

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemBlogBinding itemBinding;

        public BindingHolder(ItemBlogBinding itemBinding) {
            super(itemBinding.itemBlogLayout);
            this.itemBinding = itemBinding;
        }
    }

    public MainAdapter() {
        blogs = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemBlogBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_blog, parent, false);
        return new BindingHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        MainItemViewModel mainItemViewModel = new MainItemViewModel(blogs.get(position));
        holder.itemBinding.setViewModel(mainItemViewModel);
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    public void addItem(Blog blog) {
        blogs.add(blog);
        notifyItemInserted(blogs.size() - 1);
        notifyDataSetChanged();
    }

    public void clearItems() {
        blogs.clear();
        notifyDataSetChanged();
    }
}