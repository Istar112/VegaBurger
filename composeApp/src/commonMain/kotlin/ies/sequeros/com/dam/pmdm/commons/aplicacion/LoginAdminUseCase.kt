package ies.sequeros.com.dam.pmdm.commons.aplicacion

import ies.sequeros.com.dam.pmdm.administrador.modelo.IDependienteRepositorio

class LoginAdminUseCase (
    private val repositorio: IDependienteRepositorio
) {
    suspend fun invoke(loginAdminCommand: LoginAdminCommand): Boolean {
        val dependiente = repositorio.findByName(loginAdminCommand.username)
        if (dependiente == null) {
            return false
        } else if (loginAdminCommand.password != dependiente?.password) {
            return false
        } else if (!dependiente.isAdmin) {
            return false
        }
        return true
    }
}