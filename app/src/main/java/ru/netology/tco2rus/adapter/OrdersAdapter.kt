package ru.netology.tco2rus.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.tco2rus.data.Order
import ru.netology.tco2rus.databinding.ItemOrderBinding

class OrdersAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<Order, OrdersAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.orderNumber.text = "Заказ #${order.id}"
            binding.status.text = order.status.name
            binding.root.setOnClickListener { onClick(order.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
