package com.arjun.assignmentchatwise.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjun.assignmentchatwise.databinding.ActivityProductsBinding
import com.arjun.assignmentchatwise.ui.productdetail.ProductDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        if (viewModel.products.value.isEmpty()) {
            viewModel.fetchProducts()
        }

        binding.btnTryAgain.setOnClickListener {
            binding.errorLayout.visibility = View.GONE
            viewModel.fetchProducts()
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductsAdapter { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.products.collectLatest { products ->
                adapter.submitList(products)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.isError.collectLatest { isError ->
                if (isError) {
                    showError()
                }
                else {
                    showData()
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the scroll position
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        val scrollPosition = layoutManager.findFirstVisibleItemPosition()
        outState.putInt("SCROLL_POSITION", scrollPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the scroll position
        val scrollPosition = savedInstanceState.getInt("SCROLL_POSITION", 0)
        binding.recyclerView.layoutManager?.scrollToPosition(scrollPosition)
    }

    private fun showError() {
        binding.recyclerView.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
    }
    private fun showData() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE
    }
}