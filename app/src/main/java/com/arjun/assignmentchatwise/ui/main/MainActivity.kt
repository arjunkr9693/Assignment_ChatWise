package com.arjun.assignmentchatwise.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arjun.assignmentchatwise.databinding.ActivityMainBinding
import com.arjun.assignmentchatwise.ui.product.ProductsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewProducts.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
        }
    }
}
