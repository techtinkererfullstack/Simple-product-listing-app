package com.example.prouductlisting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.prouductlisting.databinding.ActivityAddNewProductScreenBinding

class AddNewProductScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewProductScreenBinding
    private var selectedImageUri: String = ""
    private var productId: Int = -1
    private lateinit var viewModel: ProductViewModel



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

        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]


        productId = intent.getIntExtra("id", -1)

        if (productId != -1) {

            val product = viewModel.getProductById(productId)

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


            if (productId != -1) {
               viewModel.updateProductViewModel(product)
            } else {
                viewModel.insertProductViewModel(product)
            }

            finish()
        }
    }
}
