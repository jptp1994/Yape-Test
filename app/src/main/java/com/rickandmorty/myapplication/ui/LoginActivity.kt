package com.rickandmorty.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rick_and_morty.app.R
import com.rick_and_morty.app.databinding.ActivityLoginBinding
import com.rick_and_morty.domain.models.Credentials
import com.rick_and_morty.domain.models.Login
import com.rickandmorty.myapplication.extension.getUserName
import com.rickandmorty.myapplication.extension.isValidEmail
import com.rickandmorty.myapplication.extension.isValidPassword
import com.rickandmorty.myapplication.extension.observe
import com.rickandmorty.myapplication.extension.showSnackBar
import com.rickandmorty.ui.viewmodel.LoginUIModelData
import com.rickandmorty.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity:AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private var backPressedOnce = false

    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe(viewModel.getCharacter(), ::onViewStateChange)

        viewModel.isLoading.observe(this) {
            if (it == true) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.GONE
            }
        }
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            //if (binding.tiLogin.error.toString().isEmpty() && binding.tiPassword.error.toString().isEmpty()){
                viewModel.getUserByCredentials(
                    Credentials(
                        email=binding.etEmail.toString(),
                        password= binding.etPassword.toString(),
                        userName = binding.etEmail.toString().getUserName()
                    )
                )
            //} else{
             //   showSnackBar(binding.rootView, getString(R.string.error_login_field))
           // }
        }
        viewModel.getUserActive()
        validateFields()
    }

    private fun onViewStateChange(result: LoginUIModelData) {
        if (result.isRedelivered) return
        when (result) {
            is LoginUIModelData.Loading -> {
                viewModel.isLoading.value = true
            }
            is LoginUIModelData.SuccessLogin -> handleSuccess(result.data)
            is LoginUIModelData.Error ->
            showSnackBar(binding.rootView, result.error)
            else -> {
                showSnackBar(binding.rootView, getString(R.string.general_error))
            }
        }
    }

    private fun handleSuccess(data: Login) {
        startActivity(Intent(this, MainActivity::class.java))
        intent.putExtra("email",data.email)
        // close login activity
        finish()
    }


    private fun validateFields() {

        binding.etEmail.doAfterTextChanged {
            if (it.toString().isValidEmail()) {
                binding.tiLogin.error = null
            } else {
                binding.tiLogin.error = getString(R.string.email_invalid)
            }
        }
        binding.etPassword.doAfterTextChanged {
            if (it.toString().isValidPassword()) {
                binding.tiPassword.error = null
            } else {
                binding.tiPassword.error = getString(R.string.password_invalid)
            }
        }
    }


    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
            return
        }

        backPressedOnce = true
        showSnackBar(binding.rootView, getString(R.string.app_exit_label))

        lifecycleScope.launch {
            delay(2000)
            backPressedOnce = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}