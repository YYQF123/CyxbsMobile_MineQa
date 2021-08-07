package com.mredrock.cyxbs.shop.pages.stampcenter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.aefottt.module_shop.databinding.ShopRecycleItemTaskBinding
import com.mredrock.cyxbs.common.viewmodel.BaseViewModel
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.stampcenter.ui.fragment.TaskFragment
import com.mredrock.cyxbs.shop.pages.stampcenter.viewmodel.TaskViewModel

class ShopTaskAdapter (private val lifecycleOwner: LifecycleOwner, private val mViewModel: BaseViewModel, @LayoutRes private val bindingLayoutId: Int)
    : DataBindingAdapter<ShopRecycleItemTaskBinding>(lifecycleOwner,mViewModel,bindingLayoutId) {

    private val viewModel = mViewModel as TaskViewModel

    override fun createViewHolder(dataBinding: ShopRecycleItemTaskBinding, viewType: Int,parent: ViewGroup): RecyclerView.ViewHolder {
        return if (viewType == ShopConfig.TASK_ITEM_TYPE_TASK) {
            TaskViewHolder(dataBinding)
        }else TitleViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == (viewModel.getTodayTaskSize())) ShopConfig.TASK_ITEM_TYPE_TITLE
        else ShopConfig.TASK_ITEM_TYPE_TASK
    }

    override fun getItemCount(): Int {
        viewModel.apply {
            return getMoreTaskSize() + getTodayTaskSize() + 1
        }
    }

    override fun getDataType(position: Int): Int{
        Log.e("TaskAdapter", "today:"+viewModel.getTodayTaskData().toString()+" more:"+viewModel.getMoreTaskSize().toString())
        return if (position < viewModel.getTodayTaskSize()) ShopConfig.TASK_TYPE_TODAY
        else ShopConfig.TASK_TYPE_MORE
    }

    inner class TitleViewHolder(parent: ViewGroup) : CommonViewHolder(parent,R.layout.shop_recycle_item_title_task) {
        override fun bindData(position: Int, viewModel: BaseViewModel, dataType: Int) {
        }
    }

    inner class TaskViewHolder(private val dataBinding: ShopRecycleItemTaskBinding) : DataBindingViewHolder(dataBinding){
        override fun bindData(position: Int, viewModel: BaseViewModel, dataType: Int) {
            dataBinding.apply {
                this.viewModel = viewModel as TaskViewModel
                Log.e("ShopTaskAdapter",viewModel.getTodayTaskData().toString())
                this.position = if (position > viewModel.getTodayTaskSize()) position - viewModel.getTodayTaskSize() - 1
                                else position
                this.type = type
            }
        }

    }
}