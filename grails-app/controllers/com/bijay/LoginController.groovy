package com.bijay

class LoginController {

    def form(String id) {
        [loginId: id]
    }

    def signIn(String loginId, String password) {
        def user = User.findByLoginId(loginId)
        if (user && user.password == password) {
            session.user = user
//            redirect(uri: '/')
            redirect(controller: 'user', action: 'profile', id: user.loginId)
        } else {
            flash.error = "Unknown Username or Password"
            redirect(action: 'form')
        }
    }

    def index() {}
}
