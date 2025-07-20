package ru.netology.tco2rus.activity

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.tco2rus.R
import ru.netology.tco2rus.adapter.HistoryAdapter
import ru.netology.tco2rus.databinding.FragmentHistoryBinding
import ru.netology.tco2rus.databinding.FragmentRegisterBinding
import ru.netology.tco2rus.viewmodel.HistoryViewModel
import ru.netology.tco2rus.viewmodel.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (validateInput(name, email, phone, password, confirmPassword)) {
                viewModel.register(name, email, phone, password)
            }
        }

        binding.loginLinkTextView.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registrationState.collect { state ->
                when (state) {
                    is RegisterViewModel.RegistrationState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.registerButton.isEnabled = false
                    }
                    is RegisterViewModel.RegistrationState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        navigateToMain()
                    }
                    is RegisterViewModel.RegistrationState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.registerButton.isEnabled = true
                        showError(state.message)
                    }
                    RegisterViewModel.RegistrationState.Idle -> Unit
                }
            }
        }
    }

    private fun validateInput(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        if (name.isBlank()) {
            binding.nameInputLayout.error = "Введите ФИО"
            isValid = false
        } else {
            binding.nameInputLayout.error = null
        }

        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = "Введите корректный email"
            isValid = false
        } else {
            binding.emailInputLayout.error = null
        }

        if (phone.isBlank() || !Patterns.PHONE.matcher(phone).matches()) {
            binding.phoneInputLayout.error = "Введите корректный телефон"
            isValid = false
        } else {
            binding.phoneInputLayout.error = null
        }

        if (password.isBlank() || password.length < 6) {
            binding.passwordInputLayout.error = "Пароль должен быть не менее 6 символов"
            isValid = false
        } else {
            binding.passwordInputLayout.error = null
        }

        if (confirmPassword != password) {
            binding.confirmPasswordInputLayout.error = "Пароли не совпадают"
            isValid = false
        } else {
            binding.confirmPasswordInputLayout.error = null
        }

        return isValid
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
