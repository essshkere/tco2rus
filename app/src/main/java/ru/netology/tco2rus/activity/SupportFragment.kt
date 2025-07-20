package ru.netology.tco2rus.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.netology.tco2rus.R
import ru.netology.tco2rus.adapter.OrdersAdapter
import ru.netology.tco2rus.data.DriverProfile
import ru.netology.tco2rus.databinding.FragmentOrdersBinding
import ru.netology.tco2rus.databinding.FragmentProfileBinding
import ru.netology.tco2rus.databinding.FragmentSupportBinding
import ru.netology.tco2rus.viewmodel.OrdersViewModel
import ru.netology.tco2rus.viewmodel.ProfileViewModel
import ru.netology.tco2rus.viewmodel.SupportViewModel

@AndroidEntryPoint
class SupportFragment : Fragment() {

    private var _binding: FragmentSupportBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SupportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.sendButton.setOnClickListener {
            val subject = binding.subjectEditText.text.toString()
            val message = binding.messageEditText.text.toString()

            if (validateInput(subject, message)) {
                viewModel.sendMessage(subject, message)
            }
        }

        binding.callButton.setOnClickListener {
            makePhoneCall()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInput(subject: String, message: String): Boolean {
        var isValid = true

        if (subject.isBlank()) {
            binding.subjectEditText.error = "Введите тему"
            isValid = false
        }

        if (message.isBlank()) {
            binding.messageEditText.error = "Введите сообщение"
            isValid = false
        }

        return isValid
    }

    private fun sendEmail(subject: String, message: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:support@o2rus.ru")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Отправить через"))
            viewModel.logCall() // Логируем факт обращения
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Не найдено приложение для отправки email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makePhoneCall() {
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:+78001234567")
        }

        try {
            startActivity(callIntent)
            viewModel.logCall()
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Не найдено приложение для звонков", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
