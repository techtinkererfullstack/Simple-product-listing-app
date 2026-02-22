package com.example.prouductlisting

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prouductlisting.databinding.ActivityProductListScreenBinding

class ProductListScreen : AppCompatActivity() {
    private lateinit var binding: ActivityProductListScreenBinding

    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductListScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddNewProductScreen::class.java))
        }
        db = AppDatabase.getDatabase(this)
        loadData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {

        val list = db.productDao().getAllProducts()

        val adapter = ProductAdapter(
            list,

            onEdit = { product->
                val intent = Intent(this@ProductListScreen, AddNewProductScreen::class.java)


                intent.putExtra("productImageName",product.productImageName)
                intent.putExtra("id",product.id)
                intent.putExtra("productName",product.productName)
                intent.putExtra("productCategory",product.productCategory)
                intent.putExtra("productPrice",product.productPrice)
                intent.putExtra("addedDate",product.productAddDate)
                startActivity(intent)

            },
            onDelete = {product ->
                db.productDao().delete(product)
                loadData()

            }
        )

        binding.recyclerView.adapter = adapter

    }

}