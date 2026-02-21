package com.example.prouductlisting

import androidx.room.Entity

@Entity(tableName = "products")
data class Product(
    val id:Int = 0,
    val productName:String,
    val productCategory:String,
    val productPrice: Int=0,
    val productAddDate:String,
    val productImageName:String
)
