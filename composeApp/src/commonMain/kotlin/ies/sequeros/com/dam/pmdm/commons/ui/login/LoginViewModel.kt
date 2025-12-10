package ies.sequeros.com.dam.pmdm.commons.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio
import ies.sequeros.com.dam.pmdm.commons.aplicacion.LoginAdminCommand
import ies.sequeros.com.dam.pmdm.commons.aplicacion.LoginAdminUseCase
import ies.sequeros.com.dam.pmdm.commons.aplicacion.LoginDependienteCommand
import ies.sequeros.com.dam.pmdm.commons.aplicacion.LoginDependienteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    repositorioDependientes: IDependienteRepositorio
) : ViewModel() {
    val loginAdminUseCase = LoginAdminUseCase(repositorioDependientes)
    val loginDependienteUseCase = LoginDependienteUseCase(repositorioDependientes)

    private val _username = MutableStateFlow<String>("")
    val username: StateFlow<String> = _username

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password

    private val _loginOk = MutableStateFlow<Boolean?>(null)
    val loginOk: StateFlow<Boolean?> = _loginOk

    // On values changes
    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun loginAdmin() {
        viewModelScope.launch {
            // Creamos el comando con los datos del formulario
            val command = LoginAdminCommand(
                username = username.value,
                password = password.value
            )
            // Llamamos al UseCase con el comando
            val ok = loginAdminUseCase.invoke(command)
            _loginOk.value = ok
        }
    }
    fun loginDependiente(){
        viewModelScope.launch {
            // Creamos el comando con los datos del formulario
            val command = LoginDependienteCommand(
                username = username.value,
                password = password.value
            )
            val ok = loginDependienteUseCase.invoke(command)
            _loginOk.value = ok
        }
    }

    fun resetLogin(){
        _loginOk.value = null
    }


}




