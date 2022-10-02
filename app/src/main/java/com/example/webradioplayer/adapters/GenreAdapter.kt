package com.example.webradioplayer.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.webradioplayer.R
import com.example.webradioplayer.databinding.GenreItemBinding
import com.example.webradioplayer.ui.Callback.IGenreClickCallback


class GenreAdapter(clickCallback: IGenreClickCallback?) :
        RecyclerView.Adapter<GenreAdapter.GenreViewHolder?>() {
        var mProductList: List<Product?>? = null
        private val mGenreClickCallback: ProductClickCallback?
        fun setProductList(productList: List<Product?>) {
            if (mProductList == null) {
                mProductList = productList
                notifyItemRangeInserted(0, productList.size)
            } else {
                val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun getOldListSize(): Int {
                        return mProductList!!.size
                    }

                    override fun getNewListSize(): Int {
                        return productList.size
                    }

                    override fun areItemsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        return mProductList!![oldItemPosition].getId() ===
                                productList[newItemPosition].getId()
                    }

                    override fun areContentsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        val newProduct: Product? = productList[newItemPosition]
                        val oldProduct: Product? = mProductList!![oldItemPosition]
                        return (newProduct.getId() === oldProduct.getId() && TextUtils.equals(
                            newProduct.getDescription(),
                            oldProduct.getDescription()
                        )
                                && TextUtils.equals(newProduct.getName(), oldProduct.getName())
                                && newProduct.getPrice() === oldProduct.getPrice())
                    }
                })
                mProductList = productList
                result.dispatchUpdatesTo(this)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
            val binding: GenreItemBinding = DataBindingUtil
                .inflate(
                    LayoutInflater.from(parent.context), R.layout.genre_item,
                    parent, false
                )
            binding.setCallback(mGenreClickCallback)
            return GenreViewHolder(binding)
        }

        override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
            holder.binding.setProduct(mProductList!![position])
            holder.binding.executePendingBindings()
        }

        override fun getItemCount(): Int {
            return if (mProductList == null) 0 else mProductList!!.size
        }

        override fun getItemId(position: Int): Long {
            return mProductList!![position].getId()
        }

        class GenreViewHolder(binding: GenreItemBinding) :
            RecyclerView.ViewHolder(binding.getRoot()) {
            val binding: GenreItemBinding

            init {
                this.binding = binding
            }
        }

        init {
            mGenreClickCallback = clickCallback
            setHasStableIds(true)
        }
    }
