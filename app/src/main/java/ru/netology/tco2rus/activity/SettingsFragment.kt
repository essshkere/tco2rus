package ru.netology.tco2rus.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.tco2rus.R
import ru.netology.tco2rus.adapter.HistoryAdapter
import ru.netology.tco2rus.databinding.FragmentHistoryBinding
import ru.netology.tco2rus.databinding.FragmentSettingsBinding
import ru.netology.tco2rus.viewmodel.HistoryViewModel
import ru.netology.tco2rus.viewmodel.SettingsViewModel

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadNotificationSettings()
        setupClickListeners()
        setupObservers()
    }

    private fun loadNotificationSettings() {
        val (notificationsEnabled, soundEnabled) = viewModel.loadNotificationSettings()
        binding.notificationsSwitch.isChecked = notificationsEnabled
        binding.soundSwitch.isChecked = soundEnabled
    }

    private fun setupClickListeners() {
        binding.changePasswordButton.setOnClickListener {
            val currentPassword = binding.currentPasswordEditText.text.toString()
            val newPassword = binding.newPasswordEditText.text.toString()
            val repeatPassword = binding.repeatPasswordEditText.text.toString()

            if (validatePasswords(currentPassword, newPassword, repeatPassword)) {
                viewModel.changePassword(currentPassword, newPassword)
            }
        }

        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveNotificationSettings(
                isChecked,
                binding.soundSwitch.isChecked
            )
        }

        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveNotificationSettings(
                binding.notificationsSwitch.isChecked,
                isChecked
            )
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.changePasswordButton.isEnabled = !isLoading
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    viewModel.clearError()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isPasswordChanged.collect { isChanged ->
                if (isChanged) {
                    Toast.makeText(requireContext(), "Пароль успешно изменен", Toast.LENGTH_SHORT).show()
                    clearPasswordFields()
                    viewModel.resetPasswordChanged()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoggedOut.collect { isLoggedOut ->
                if (isLoggedOut) {
                    navigateToLogin()
                    viewModel.resetLoggedOut()
                }
            }
        }
    }

    private fun validatePasswords(
        currentPassword: String,
        newPassword: String,
        repeatPassword: String
    ): Boolean {
        var isValid = true

        if (currentPassword.isBlank()) {
            binding.currentPasswordEditText.error = "Введите текущий пароль"
            isValid = false
        }

        if (newPassword.isBlank()) {
            binding.newPasswordEditText.error = "Введите новый пароль"
            isValid = false
        } else if (newPassword.length < 6) {
            binding.newPasswordEditText.error = "Пароль слишком короткий"
            isValid = false
        }

        if (repeatPassword != newPassword) {
            binding.repeatPasswordEditText.error = "Пароли не совпадают"
            isValid = false
        }

        return isValid
    }

    private fun showLogoutConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Выход из аккаунта")
            .setMessage("Вы уверены, что хотите выйти?")
            .setPositiveButton("Выйти") { _, _ ->
                viewModel.logout()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun clearPasswordFields() {
        binding.currentPasswordEditText.text?.clear()
        binding.newPasswordEditText.text?.clear()
        binding.repeatPasswordEditText.text?.clear()
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            R.id.action_settingsFragment_to_loginFragment
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
