package com.example.webradioplayer.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.webradioplayer.R
import com.example.webradioplayer.adapters.GenreAdapter
import com.example.webradioplayer.databinding.GenreFragmentBinding

class GenreFragment: Fragment() {

  //  class ProductListFragment : Fragment() {
        private var mGenreAdapter: GenreAdapter? = null
        private var mBinding: GenreFragmentBinding? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.genre_fragment, container, false)
            mGenreAdapter = GenreAdapter(mProductClickCallback)
            mBinding.genreList.adapter productsList.setAdapter(mGenreAdapter)
            return mBinding.getRoot()
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val viewModel: ProductListViewModel =
                ViewModelProvider(this).get(ProductListViewModel::class.java)
            mBinding.productsSearchBtn.setOnClickListener { v ->
                val query: Editable = mBinding.productsSearchBox.getText()
                viewModel.setQuery(query)
            }
            subscribeUi(viewModel.getProducts())
        }

        private fun subscribeUi(liveData: LiveData<List<ProductEntity?>>) {
            // Update the list when the data changes
            liveData.observe(
                viewLifecycleOwner
            ) { myProducts: List<ProductEntity?>? ->
                if (myProducts != null) {
                    mBinding.setIsLoading(false)
                    mGenreAdapter.setProductList(myProducts)
                } else {
                    mBinding.setIsLoading(true)
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings()
            }
        }

        override fun onDestroyView() {
            mBinding = null
            mGenreAdapter = null
            super.onDestroyView()
        }

        private val mProductClickCallback: ProductClickCallback =
            ProductClickCallback { product ->
                if (lifecycle.currentState
                        .isAtLeast(Lifecycle.State.STARTED)
                ) {
                    (requireActivity() as MainActivity).show(product)
                }
            }

        companion object {
            const val TAG = "ProductListFragment"
        }
}