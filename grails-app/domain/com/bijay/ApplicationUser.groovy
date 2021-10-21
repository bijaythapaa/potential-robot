package com.bijay

class ApplicationUser {

    String applicationName
    String password
    String apiKey

    static constraints = {
        // share Constraints between classes
        importFrom User, include: ['password']
        applicationName blank: false, unique: true
        apiKey blank: false
    }
}
