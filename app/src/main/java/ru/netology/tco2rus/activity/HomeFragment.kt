package ru.netology.tco2rus.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.netology.tco2rus.R
import ru.netology.tco2rus.databinding.FragmentHomeBinding
import ru.netology.tco2rus.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = binding.mapView
        MapKitFactory.initialize(requireContext())

        viewModel.routePoints.observe(viewLifecycleOwner) { points ->
            drawRoute(points)
        }

        binding.fabCurrentOrders.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_ordersFragment)
        }
    }

    private fun drawRoute(points: List<Point>) {
        val mapObjects = mapView.map.mapObjects
        mapObjects.addPolyline(Polyline(points))
        mapView.map.move(CameraPosition(points[0], 10f, 0f, 0f))
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
