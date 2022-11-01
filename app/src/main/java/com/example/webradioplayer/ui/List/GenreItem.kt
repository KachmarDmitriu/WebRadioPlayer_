package com.example.webradioplayer.ui.List

import android.view.View

class GenreItem {
    private val model: Country,
    private val onCountryClick: (Country) -> Unit
    ) : BindableItem<ItemCountryBinding>() {

        override fun bind(viewBinding: ItemCountryBinding, position: Int) {
            viewBinding.country = model
            viewBinding.root.setOnClickListener {
                onCountryClick.invoke(model)
            }
        }

        override fun getLayout() = R.layout.item_country

        override fun initializeViewBinding(view: View) = ItemCountryBinding.bind(view)
}