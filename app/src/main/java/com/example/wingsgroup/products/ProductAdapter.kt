package com.example.wingsgroup.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wingsgroup.R
import com.example.wingsgroup.data.domain.Product
import com.example.wingsgroup.data.local.ProductModel
import com.example.wingsgroup.databinding.ListProductItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductAdapter(
    val clickListener: (String, ImageView) -> Unit
): ListAdapter<ProductItem, RecyclerView.ViewHolder>(ProductDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Product>?) {
        adapterScope.launch {
            val items = when(list) {
                null -> listOf(ProductItem.Header)
                else -> listOf(ProductItem.Header) + list.map { ProductItem.ProductData(it) }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is ProductItem.Header -> ProductItem.ITEM_VIEW_TYPE_HEADER
            is ProductItem.ProductData -> ProductItem.ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ProductItem.ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ProductItem.ITEM_VIEW_TYPE_ITEM -> ProductViewHolder.from(parent)
            else -> throw ClassCastException("Unknown Product Item Type ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ProductViewHolder -> {
                val item = getItem(position) as ProductItem.ProductData
                holder.bind(item.product, clickListener)
            }
        }
    }

    class ProductViewHolder private constructor(val binding: ListProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListProductItemBinding.inflate(inflater, parent, false)
                return ProductViewHolder(binding)
            }
        }

        fun bind(item: Product, clickListener: (String, ImageView) -> Unit) {
            binding.product = item
            binding.productImg.setImageDrawable(ContextCompat.getDrawable(binding.productImg.context, item.img))
            binding.productImg.transitionName = item.code
            binding.itemContainer.setOnClickListener{ clickListener(item.code, binding.productImg) }
            binding.executePendingBindings()
        }
    }

    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup) : TextViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return TextViewHolder(inflater.inflate(R.layout.header_product_list, parent, false))
            }
        }
    }

    class ProductDiffCallback: DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

sealed class ProductItem {
    abstract val id: String

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
    }

    data class ProductData(val product: Product) : ProductItem() {
        override val id: String = product.code
    }

    object Header: ProductItem() {
        override val id: String = ""
    }
}