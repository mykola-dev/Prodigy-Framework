package ds.sample.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

// simple viewmodel adapter
abstract public class ViewModelAdapter<VM, D> extends RecyclerView.Adapter<ViewModelHolder<ViewDataBinding, VM>> {

    private List<D> data;
    private Context context;
    private int viewModelId;

    public ViewModelAdapter(Context context) {
        this(context, new ArrayList<D>());
    }

    public ViewModelAdapter(Context ctx, List<D> data) {
        this(ctx, data, ds.sample.BR.viewModel);
    }

    public ViewModelAdapter(Context ctx, List<D> data, int vmId) {
        this.data = data;
        this.context = ctx;
        viewModelId = vmId;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewModelHolder<ViewDataBinding, VM> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, getLayoutId(), parent, false);
        return new ViewModelHolder<>(binding, instantiateViewModel());
    }

    @Override
    public void onBindViewHolder(ViewModelHolder<ViewDataBinding, VM> holder, int position) {
        D item = getItem(position);
        onFillViewModel(holder.getBinding(), holder.getViewModel(), item, position);
        holder.getBinding().setVariable(viewModelId, holder.getViewModel());
        holder.getBinding().executePendingBindings();
    }

    public D getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected Context getContext() {
        return context;
    }

    public void add(D newItem) {
        data.add(newItem);
        notifyItemInserted(data.size());
    }

    public void addAll(List<D> newData) {
        int from = data.size();
        data.addAll(newData);
        notifyItemRangeInserted(from, data.size());
    }

    public void setItems(List<D> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public List<D> getItems() {
        return data;
    }

    protected abstract int getLayoutId();

    protected abstract void onFillViewModel(ViewDataBinding binding, VM viewModel, D item, int position);

    protected abstract VM instantiateViewModel();
}
