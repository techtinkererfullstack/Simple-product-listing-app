package com.example.prouductlisting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.prouductlisting.databinding.ActivityAddNewProductScreenBinding

class AddNewProductScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewProductScreenBinding
    private var selectedImageUri: String = ""
    private var productId: Int = -1

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            this.contentResolver.takePersistableUriPermission(uri, flag)
            selectedImageUri = uri.toString()
            binding.imageName.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId = intent.getIntExtra("id", -1)

        if (productId != -1) {
            val db = AppDatabase.getDatabase(this)
            val product = db.productDao().getProductById(productId)

            binding.productNameET.setText(product.productName)
            binding.productCategoryET.setText(product.productCategory)
            binding.productPriceET.setText(product.productPrice)
            binding.addedDateET.setText(product.productAddDate)
            selectedImageUri = product.productImageName
            if (selectedImageUri.isNotEmpty()) {
                binding.imageName.setImageURI(Uri.parse(selectedImageUri))
            }
        }

        binding.selectImageButton.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.saveButton.setOnClickListener {
            val productName = binding.productNameET.text.toString()
            val productCategory = binding.productCategoryET.text.toString()
            val productPrice = binding.productPriceET.text.toString()
            val productAddDate = binding.addedDateET.text.toString()

            val product = Product(
                id = if (productId != -1) productId else 0,
                productName = productName,
                productCategory = productCategory,
                productPrice = productPrice,
                productAddDate = productAddDate,
                productImageName = selectedImageUri
            )

            val db = AppDatabase.getDatabase(this)
            if (productId != -1) {
                db.productDao().update(product)
            } else {
                db.productDao().insert(product)
            }

            finish()
        }
    }
}
