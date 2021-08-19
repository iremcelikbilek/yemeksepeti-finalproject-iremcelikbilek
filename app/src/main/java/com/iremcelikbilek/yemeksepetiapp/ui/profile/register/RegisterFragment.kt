package com.iremcelikbilek.yemeksepetiapp.ui.profile.register

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iremcelikbilek.yemeksepetiapp.R
import com.iremcelikbilek.yemeksepetiapp.databinding.FragmentRegisterBinding
import com.iremcelikbilek.yemeksepetiapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        binding.registerButton.setOnClickListener {
            val name = binding.nameEdtTxt.text.toString()
            val lastname = binding.lastNameEdtTxt.text.toString()
            val phone = binding.phoneEdtTxt.text.toString()
            val email = binding.mailEdtTxt.text.toString()
            val password = binding.passwordEdtTxt.text.toString()

            viewModel.register(name, lastname, phone, email, password).observe(viewLifecycleOwner, Observer {
                when(it.status) {
                    Resource.Status.LOADING -> {

                    }

                    Resource.Status.SUCCESS -> {
                        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)

                    }

                    Resource.Status.ERROR -> {
                        val dialog = AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage("${it.message}")
                            .setPositiveButton("ok") { dialog, button ->
                                dialog.dismiss()
                            }
                        dialog.show()

                    }
                }
            })
        }
    }
}