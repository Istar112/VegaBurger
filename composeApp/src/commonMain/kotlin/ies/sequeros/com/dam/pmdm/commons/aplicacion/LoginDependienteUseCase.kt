package ies.sequeros.com.dam.pmdm.commons.aplicacion

import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio

class LoginDependienteUseCase (
    private val repositorio: IDependienteRepositorio
) {
    suspend fun invoke(loginDependienteCommand: LoginDependienteCommand): Boolean {
        val dependiente = repositorio.findByName(loginDependienteCommand.username)
        if (dependiente == null) {
            return false
        } else if (loginDependienteCommand.password != dependiente?.password) {
            return false
        }
        return true
    }
}