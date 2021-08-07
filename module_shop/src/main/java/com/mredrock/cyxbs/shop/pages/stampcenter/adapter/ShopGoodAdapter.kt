package com.mredrock.cyxbs.shop.pages.stampcenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aefottt.module_shop.R
import com.mredrock.cyxbs.common.utils.extensions.setOnSingleClickListener
import com.mredrock.cyxbs.shop.bean.Decoration
import com.mredrock.cyxbs.shop.bean.StampGood
import com.mredrock.cyxbs.shop.config.ShopConfig
import com.mredrock.cyxbs.shop.pages.detail.ui.DetailActivity
import kotlinx.android.synthetic.main.shop_recycle_item_good.view.*
import kotlinx.android.synthetic.main.shop_recycle_item_title_good.view.*
import java.io.Serializable

class ShopGoodAdapter(private val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var decorationList: List<Decoration>
    private lateinit var stampGoodList: List<StampGood>
    fun setDecorationData(decorationList: List<Decoration>){
        this.decorationList = decorationList
    }


    fun setStampGoodData(stampGoodList: List<StampGood>){
        this.stampGoodList = stampGoodList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0 || viewType == decorationList.size) {
            TitleHolder(parent)
        }else GoodHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0){
            (holder as TitleHolder).bindData("装饰")
        } else if (position > 0 && position < decorationList.size){
            (holder as GoodHolder).bindData(decorationList[position])
            holder.itemView.apply {
                setOnSingleClickListener {
                    DetailActivity.activityStart(context,shop_item_tv_title.text.toString(),ShopConfig.TASK_TYPE_TODAY)
                }
            }
        } else if (position == decorationList.size){
            (holder as TitleHolder).bindData("邮货")
        } else {
            (holder as GoodHolder).bindData(stampGoodList[position - decorationList.size])
            holder.itemView.apply {
                setOnSingleClickListener {
                    DetailActivity.activityStart(context,shop_item_tv_title.text.toString(),ShopConfig.TASK_TYPE_MORE)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return decorationList.size + stampGoodList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class GoodHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shop_recycle_item_good,parent,false))
    {
        fun bindData(goodData: Serializable){
            itemView.apply {
                if (goodData is Decoration) {
                    goodData.apply {
                        shop_item_tv_title.text = title
                        shop_item_tv_left_count.text = "库存量：${left_Count}"
                        shop_item_tv_price.text = price.toString()
                    }

                }else if (goodData is StampGood){
                    goodData.apply {
                        shop_item_tv_title.text = title
                        shop_item_tv_left_count.text = "库存量：${left_count}"
                        shop_item_tv_price.text = price.toString()
                    }
                }

            }
        }
    }

    class TitleHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shop_recycle_item_title_good,parent,false))
    {
        fun bindData(title: String) {
            itemView.shop_item_title_title.text = title
        }
    }
}