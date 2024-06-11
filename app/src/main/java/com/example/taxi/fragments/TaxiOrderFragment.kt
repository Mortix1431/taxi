package com.example.taxi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taxi.R
import com.example.taxi.adapters.TaxiOrderViewModel
import com.example.taxi.models.Order
import com.example.taxi.utils.UserMethods
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TaxiOrderFragment : Fragment() {
    private val viewModel: TaxiOrderViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var startAddress: EditText
    private lateinit var endAddress: EditText
    private lateinit var priceOffer: EditText
    private lateinit var comment: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_taxi_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapview)
        startAddress = view.findViewById(R.id.start_address)
        endAddress = view.findViewById(R.id.end_address)
        priceOffer = view.findViewById(R.id.price_offer)
        comment = view.findViewById(R.id.comment)

        mapView.map.addTapListener(GeoObjectTapListener { geoObjectTapEvent ->
            val point = geoObjectTapEvent.geoObject.geometry.firstOrNull()?.point
            point?.let {
                if (startAddress.hasFocus()) {
                    viewModel.setStartPoint(it)
                    startAddress.setText(it.toString())
                } else if (endAddress.hasFocus()) {
                    viewModel.setEndPoint(it)
                    endAddress.setText(it.toString())
                }
            }
            true
        })

        view.findViewById<Button>(R.id.order_button).setOnClickListener {
            val start = startAddress.text.toString()
            val end = endAddress.text.toString()
            val price = priceOffer.text.toString()
            val commentText = comment.text.toString()

            lifecycleScope.launch {
                val userSession = UserMethods().getUserSession()
                if (start.isNotEmpty() && end.isNotEmpty() && price.isNotEmpty() && userSession != null) {
                    val order = Order(
                        orderId = UUID.randomUUID().toString(),
                        orderDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                        orderTotal = price.toDouble(),
                        startLocation = start,
                        endLocation = end,
                        comment = commentText,
                        userId = userSession.id
                    )
                    UserMethods().createOrder(order)
                    Toast.makeText(context, "Заказ отправлен", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.startPoint.observe(viewLifecycleOwner) { point ->
            addPlacemark(point)
        }

        viewModel.endPoint.observe(viewLifecycleOwner) { point ->
            addPlacemark(point)
        }
    }

    private fun addPlacemark(point: Point) {
        val placemark: PlacemarkMapObject = mapView.map.mapObjects.addPlacemark(point)
        placemark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.ic_my_location))
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}