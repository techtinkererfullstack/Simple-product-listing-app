package com.example.prouductlisting

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.prouductlisting.databinding.ActivityAddNewProductScreenBinding

class AddNewProductScreen : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewProductScreenBinding

    private lateinit var db: AppDatabase

    private var productid = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNewProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //getDatabase function called
        db = AppDatabase.getDatabase(this)

        productid =intent.getIntExtra("id",-1)

        if (productid!=-1){

            binding.imageNameET.setText(intent.getStringExtra("productImageName"))
            binding.productNameET.setText(intent.getStringExtra("productName"))
            binding.productCategoryET.setText(intent.getStringExtra("productCategory"))
            binding.productPriceET.setText(intent.getStringExtra("productPrice"))
            binding.addedDateET.setText(intent.getStringExtra("addedDate"))


        }

        binding.saveButton.setOnClickListener {

            val productImageName = binding.imageNameET.text.toString()
            val productName = binding.productNameET.text.toString()
            val productCategory = binding.productCategoryET.text.toString()
            val productPrice = binding.productPriceET.text.toString()
            val productAddedDate = binding.addedDateET.text.toString()

            if (productid== -1){
                //insert
                val product = Product(productImageName=productImageName, productName = productName, productCategory = productCategory, productPrice = productPrice, productAddDate = productAddedDate)
                db.productDao().insert(product)

            }else{
                //Update
                val product =Product(id = productid,productName=productName, productCategory = productCategory, productPrice = productPrice, productImageName=productImageName, productAddDate = productAddedDate)
                db.productDao().update(product)
            }
            Toast.makeText(this@AddNewProductScreen, "Product saved successfully", Toast.LENGTH_SHORT).show()
            finish()


        }


    }
}