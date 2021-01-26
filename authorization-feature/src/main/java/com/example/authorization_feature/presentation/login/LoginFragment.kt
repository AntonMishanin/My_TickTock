package com.example.authorization_feature.presentation.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.authorization_feature.navigator.AuthorizationNavigator
import com.example.authorization_feature.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    private var loginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val navigator = requireActivity() as AuthorizationNavigator

        viewModel.onViewCreated(navigator)
        initListeners()

        viewModel.enableLoginButton.observe(viewLifecycleOwner) { enableLoginButton ->
            loginButton?.isEnabled = enableLoginButton
        }
    }

    override fun onDestroyView() {
        viewModel.onDestroyView()
        super.onDestroyView()
    }

    private fun initListeners() {
        val userName = requireView().findViewById<EditText>(R.id.editText_user_name_login)
        userName.addTextChangedListener {
            viewModel.enteringUserName(it)
        }

        val password = requireView().findViewById<EditText>(R.id.editText_user_password_login)
        password.addTextChangedListener {
            viewModel.enteringPassword(it)
        }

        loginButton = view?.findViewById(R.id.button_login)
        loginButton?.setOnClickListener {
            viewModel.onClickLogin()
        }

        val registrationButton = view?.findViewById<Button>(R.id.button_go_to_registration)
        registrationButton?.setOnClickListener {
            viewModel.onClickRegistration()
        }
    }
}