package com.example.prouductlisting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prouductlisting.databinding.ActivityProductListScreenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProductListScreen : AppCompatActivity() {
    private lateinit var binding: ActivityProductListScreenBinding


    private lateinit var viewModel: ProductViewModel
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

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /*
        In Kotlin, when you define a function with the operator keyword and the name get, you allow the object to be accessed using array-like square bracket syntax.

        When you write:
        ViewModelProvider(this)[ProductViewModel::class.java]

        The Kotlin compiler translates it under the hood to:
        ViewModelProvider(this).get(ProductViewModel::class.java)

        Why use it?

        Readability: It treats the ViewModelProvider like a persistent "collection" or "map" of ViewModels. You are essentially saying, "Give me the instance of this class from your store."

        Conciseness: It removes the need for explicit .get() calls, making the code feel more native to the language's design.

        The Technical Mapping

        Kotlin has a set of reserved operators that map to specific function names. Here is a quick look at how the compiler treats them:
        Kotlin Syntax	Translated Function Call
        a[i]	a.get(i)
        a[i, j]	a.get(i, j)
        a[i] = b	a.set(i, b)

        A Quick Example

        You can actually do this with your own classes! If you have a custom data container, you can enable the [] syntax like this:
        class MyLibrary {
              operator fun get(index: Int): String {
                return "Returning item at $index"
                }
            }

        val lib = MyLibrary()
        println(lib[5]) // This calls lib.get(5)

         */
        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        viewModel.productLiveData.observe(this) { list ->
            binding.totalProduct.text = "Total Products: ${list.size}"

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

                    val view = layoutInflater.inflate(R.layout.dialog_delete, null)
                    val dialog = MaterialAlertDialogBuilder(this)
                        .setView(view)
                        .create()
                    view.findViewById<Button>(R.id.btnConfirmDelete).setOnClickListener {
                       viewModel.deleteProductViewModel(product)
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            )

            binding.recyclerView.adapter = adapter
        }

    }

    override fun onResume() {
        super.onResume()
       viewModel.getAllProductsViewModel()
    }


}