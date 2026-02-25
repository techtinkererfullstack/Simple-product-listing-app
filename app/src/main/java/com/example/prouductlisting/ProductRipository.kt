package com.example.prouductlisting

class ProductRipository(private val productDao: ProductDao) {


    fun getAllProductsRepo(): List<Product> {
        return productDao.getAllProducts()
    }

    fun insertProductRepo(product: Product) {
        productDao.insert(product)
    }

    fun updateProductRepo(product: Product) {
        productDao.update(product)
    }

    fun deleteProductRepo(product: Product) {
        productDao.delete(product)
    }

    fun getProductByIdRepo(productId: Int): Product {
        return productDao.getProductById(productId)
    }

}



