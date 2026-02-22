package com.example.prouductlisting

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val productName:String,
    val productCategory:String,
    val productPrice: String,
    val productAddDate:String,
    val productImageName:String
)
