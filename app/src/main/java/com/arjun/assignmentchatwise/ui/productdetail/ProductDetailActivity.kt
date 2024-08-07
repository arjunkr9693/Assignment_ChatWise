package com.arjun.assignmentchatwise.ui.productdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arjun.assignmentchatwise.data.Product
import com.bumptech.glide.Glide
import com.arjun.assignmentchatwise.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getParcelableExtra<Product>("product")
        if (product != null) {
            displayProductDetails(product)
        } else {
            finish()
        }
    }

    private fun displayProductDetails(product: Product) {
        binding.tvProductName.text = product.title
        binding.tvProductDescription.text = product.description
        binding.tvProductPrice.text = "$ ${product.price}"

        Glide.with(this)
            .load(product.thumbnail)
            .into(binding.ivProductImage)
    }
}