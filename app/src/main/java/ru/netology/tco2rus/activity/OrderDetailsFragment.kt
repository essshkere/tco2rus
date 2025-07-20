package ru.netology.tco2rus.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.netology.tco2rus.R
import ru.netology.tco2rus.api.dto.Order
import ru.netology.tco2rus.data.OrderStatus
import ru.netology.tco2rus.databinding.FragmentOrderDetailsBinding
import ru.netology.tco2rus.viewmodel.OrderDetailsViewModel
import ru.netology.tco2rus.viewmodel.OrderStatusChangeResult

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderDetailsViewModel by viewModels()
    private var orderId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderId = it.getLong("orderId", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
        loadOrderDetails()
    }

    private fun setupViews() {
        binding.btnChangeStatus.setOnClickListener {
            viewModel.changeOrderStatus()
        }

        binding.btnOpenMap.setOnClickListener {
            openMapWithRoute()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.order.collect { order ->
                    order?.let { updateUI(it) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.statusChangeResult.collect { result ->
                    when (result) {
                        is OrderStatusChangeResult.Success -> {
                            Toast.makeText(context, "Статус изменен", Toast.LENGTH_SHORT).show()
                            updateStatusButton(result.order.status)
                        }
                        is OrderStatusChangeResult.Error -> {
                            Toast.makeText(
                                context,
                                "Ошибка: ${result.errorMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        null -> {}
                    }
                }
            }
        }
    }

    private fun loadOrderDetails() {
        if (orderId != -1L) {
            viewModel.loadOrder(orderId)
        } else {
            Toast.makeText(context, "Неверный ID заказа", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun updateUI(order: Order) {
        binding.tvOrderTitle.text = "Детали заказа #${order.id}"
        binding.tvOrderStatus.text = "Статус: ${order.getStatusText()}"
        binding.tvFromAddress.text = order.fromAddress
        binding.tvToAddress.text = order.toAddress
        binding.tvClientName.text = "Клиент: ${order.clientName}"
        binding.tvCargoType.text = "Груз: ${order.cargoType} (${order.cargoWeight} кг)"
        binding.tvLoadingDate.text = "Дата погрузки: ${order.loadingDate}"
        binding.tvDeliveryDate.text = "Срок доставки: ${order.deliveryDate}"
        binding.tvPrice.text = "Стоимость: ${order.price} руб."

        updateStatusButton(order.status)
    }

    private fun updateStatusButton(status: OrderStatus) {
        binding.btnChangeStatus.text = when (status) {
            OrderStatus.NEW -> "Взять в работу"
            OrderStatus.ACCEPTED -> "Начать погрузку"
            OrderStatus.LOADING -> "Завершить погрузку"
            OrderStatus.ON_WAY -> "Прибыть на место"
            OrderStatus.UNLOADING -> "Завершить разгрузку"
            OrderStatus.DELIVERED -> "Получить документы"
            OrderStatus.COMPLETED -> "Завершено"
        }
    }

    private fun openMapWithRoute() {
        viewModel.order.value?.let { order ->
            val action = OrderDetailsFragmentDirections.actionOrderDetailsFragmentToRouteFragment(
                fromLat = order.fromLat.toFloat(),
                fromLon = order.fromLon.toFloat(),
                toLat = order.toLat.toFloat(),
                toLon = order.toLon.toFloat()
            )
            findNavController().navigate(action)
        } ?: Toast.makeText(context, "Данные маршрута недоступны", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(orderId: Long) = OrderDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong("orderId", orderId)
            }
        }
    }
}
