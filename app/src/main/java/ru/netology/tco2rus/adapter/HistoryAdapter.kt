package ru.netology.tco2rus.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.netology.tco2rus.R
import ru.netology.tco2rus.data.HistoryOrder

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var orders = emptyList<HistoryOrder>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderNumberTextView: TextView = itemView.findViewById(R.id.orderNumberTextView)
        private val fromAddressTextView: TextView = itemView.findViewById(R.id.fromAddressTextView)
        private val toAddressTextView: TextView = itemView.findViewById(R.id.toAddressTextView)
        private val travelTimeTextView: TextView = itemView.findViewById(R.id.travelTimeTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)

        fun bind(order: HistoryOrder) {
            orderNumberTextView.text = "Заказ #${order.id}"
            fromAddressTextView.text = order.fromAddress
            toAddressTextView.text = order.toAddress
            travelTimeTextView.text = "Время: ${order.travelTime}"
            statusTextView.text = when (order.status) {
                "COMPLETED" -> "Доставлено"
                "CANCELLED" -> "Отменено"
                else -> "В обработке"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount() = orders.size

    fun submitList(newOrders: List<HistoryOrder>) {
        orders = newOrders
        notifyDataSetChanged()
    }
}
