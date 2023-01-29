package com.example.wingsgroup.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wingsgroup.data.domain.Cart
import com.example.wingsgroup.databinding.ListCartItemBinding

class CartListAdapter constructor(
    private val deleteListener: (String) -> Unit
): ListAdapter<Cart, CartListAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position), deleteListener)
    }

    class CartViewHolder(val binding: ListCartItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CartViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListCartItemBinding.inflate(inflater, parent, false)
                return CartViewHolder(binding)
            }
        }

        fun bind(cart: Cart, deleteListener: (String) -> Unit) {
            binding.cart = cart

            binding.deleteBtn.setOnClickListener { deleteListener(cart.product_code) }

            if(cart.img != 0)
                binding.cartImg.setImageDrawable(ContextCompat.getDrawable(binding.cartImg.context, cart.img))

            binding.executePendingBindings()
        }
    }

    class CartDiffCallback: DiffUtil.ItemCallback<Cart>() {
        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.product_code == newItem.product_code
        }

        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }
    }
}