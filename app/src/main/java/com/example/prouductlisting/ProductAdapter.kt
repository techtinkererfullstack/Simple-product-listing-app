package com.example.prouductlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prouductlisting.databinding.ProductListBinding

class ProductAdapter(
    private val list: List<Product>,
    private val onEdit:(Product)-> Unit,
    private val onDelete:(Product)-> Unit,
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ProductListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ProductListBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product = list[position]


        holder.binding.productImageNameTV.text = product.productImageName.toString()
        holder.binding.productNameTV.text = product.productName.toString()
        holder.binding.productCategory.text = product.productCategory.toString()
        holder.binding.productPriceTV.text = product.productPrice.toString()
        holder.binding.productPostDateTV.text = product.productAddDate.toString()



        holder.binding.editBTN.setOnClickListener {
            onEdit(product)
        }
        holder.binding.deleteBTN.setOnClickListener {
            onDelete(product)
        }
    }

    override fun getItemCount(): Int = list.size



}