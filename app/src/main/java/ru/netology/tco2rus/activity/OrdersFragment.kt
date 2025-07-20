package ru.netology.tco2rus.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.tco2rus.R
import ru.netology.tco2rus.adapter.OrdersAdapter
import ru.netology.tco2rus.databinding.FragmentOrdersBinding
import ru.netology.tco2rus.viewmodel.OrdersViewModel

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var adapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OrdersAdapter { orderId ->
            openOrderDetails(orderId)
        }

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.activeOrders.collect { orders ->
                adapter.submitList(orders)
            }
        }
    }

    private fun openOrderDetails(orderId: Long) {
        findNavController().navigate(
            R.id.action_ordersFragment_to_orderDetailsFragment,
            bundleOf("orderId" to orderId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
