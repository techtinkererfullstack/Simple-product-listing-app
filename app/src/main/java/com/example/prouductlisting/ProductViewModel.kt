package com.example.prouductlisting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRipository
    val productLiveData = MutableLiveData<List<Product>>()

    init {
        val dao = AppDatabase.getDatabase(application).productDao()
        repository = ProductRipository(dao)
        getAllProductsViewModel()
    }

    fun getAllProductsViewModel() {
        productLiveData.value = repository.getAllProductsRepo()
    }

    fun insertProductViewModel(product: Product) {
        repository.insertProductRepo(product)
        getAllProductsViewModel()
    }

    fun updateProductViewModel(product: Product) {
        repository.updateProductRepo(product)
        getAllProductsViewModel()
    }

    fun deleteProductViewModel(product: Product) {
        repository.deleteProductRepo(product)
        getAllProductsViewModel()
    }


}




