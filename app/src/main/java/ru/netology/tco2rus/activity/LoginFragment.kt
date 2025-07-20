package ru.netology.tco2rus.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.netology.tco2rus.R
import ru.netology.tco2rus.databinding.FragmentLoginBinding
import ru.netology.tco2rus.viewmodel.LoginState
import ru.netology.tco2rus.viewmodel.LoginViewModel


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        binding.loginButton.setOnClickListener {
            val login = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateInput(login, password)) {
                viewModel.login(login, password)
            }
        }

        binding.registerLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setupObservers() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Loading -> showLoading(true)
                is LoginState.Success -> onLoginSuccess()
                is LoginState.Error -> showError(state.message)
            }
        }
    }

    private fun validateInput(login: String, password: String): Boolean {
        var isValid = true

        if (login.isBlank()) {
            binding.loginInputLayout.error = "Введите логин"
            isValid = false
        } else {
            binding.loginInputLayout.error = null
        }

        if (password.isBlank()) {
            binding.passwordInputLayout.error = "Введите пароль"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordInputLayout.error = "Пароль слишком короткий"
            isValid = false
        } else {
            binding.passwordInputLayout.error = null
        }

        return isValid
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.loginButton.text = if (isLoading) "" else getString(R.string.login_button)
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onLoginSuccess() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun showError(message: String) {
        binding.errorTextView.text = message
        binding.errorTextView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
