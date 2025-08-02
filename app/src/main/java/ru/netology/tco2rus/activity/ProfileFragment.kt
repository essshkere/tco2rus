package ru.netology.tco2rus.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.netology.tco2rus.R
import ru.netology.tco2rus.data.DriverProfile
import ru.netology.tco2rus.databinding.FragmentProfileBinding
import ru.netology.tco2rus.viewmodel.ProfileViewModel
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profile.collect { profile ->
                profile?.let { updateUI(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.saveButton.isEnabled = !isLoading
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
            viewModel.error.collect { error ->
                error?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    viewModel.clearError()
                }
            }
        }
    }

    private fun updateUI(profile: DriverProfile) {
        with(binding) {
            nameEditText.setText(profile.fullName)
            phoneEditText.setText(profile.phone)
            columnNumberEditText.setText(profile.columnNumber)
            columnPhoneEditText.setText(profile.columnPhone)
            logistPhoneEditText.setText(profile.logistPhone)

            Glide.with(requireContext())
                .load(profile.avatarUrl)
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .into(avatarImageView)
        }
    }

    private fun setupClickListeners() {
        binding.saveButton.setOnClickListener {
            val updatedProfile = DriverProfile(
                id = viewModel.profile.value?.id ?: 0,
                fullName = binding.nameEditText.text.toString(),
                phone = binding.phoneEditText.text.toString(),
                email = viewModel.profile.value?.email ?: "",
                columnNumber = binding.columnNumberEditText.text.toString(),
                columnPhone = binding.columnPhoneEditText.text.toString(),
                logistPhone = binding.logistPhoneEditText.text.toString(),
                avatarUrl = viewModel.profile.value?.avatarUrl ?: ""
            )
            viewModel.updateProfile(updatedProfile)
        }

        binding.changeAvatarButton.setOnClickListener {
            //выбор фото из галереи/камеры
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        // Intent для выбора изображения
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
