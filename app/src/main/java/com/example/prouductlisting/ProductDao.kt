package com.example.prouductlisting

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)

    @Update
    fun update(product:Product)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Int): Product

}