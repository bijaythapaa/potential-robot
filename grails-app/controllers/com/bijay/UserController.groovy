package com.bijay

import grails.validation.Validateable

//@Validateable
class UserRegistrationCommand implements Validateable {
    String loginId
    String password
    String passwordRepeat
    byte[] photo
    String fullName
    String bio
    String homepage
    String email
    String timezone
    String country
    String jabberAddress

//    def cryptoService
//    String getEncryptedPassword() {
//        return cryptoService.getEncryptedPassword(password)
//    }

    static constraints = {
        importFrom(Profile)
        importFrom(User)
        password(size: 6..8, blank: false, validator: { passwd, urc -> return passwd != urc.loginId })
        passwordRepeat(nullable: false, validator: { passwd2, urc -> return passwd2 == urc.password })
    }
}

class UserController {

    static scaffold = User

    def search() {}

    def results(String loginId) {
        def users = User.where {
            loginId =~ "%${loginId}%"
        }.list()
        return [users: users, term: params.loginId, totalUsers: User.count()]
    }

    // return all users whose login IDs match a given string, and
    // optionally were created in the system after a given time
//    def fetchUsers(String loginIdPart, Date fromDate = null) {
//        def users
//        if (fromDate) {
//            users = User.where {
//                loginId =~ "%${loginIdPart}%" && dateCreated >= fromDate
//            }.list()
//        } else {
//            users = User.where {
//                loginId =~ "${loginIdPart}"
//            }.list()
//        }

    // better approach than above
//        def users = User.where {
//            loginId =~ "%${loginIdPart}%"
//            if (fromDate) {
//                dateCreated >= fromDate
//            }
//        }.list()
//    }

    // Criteria query for above example
//    def fetchUsers(String loginIdPart, Date fromDate = null) {
//        def users = User.createCriteria().list {
//            and {
//                ilike("loginId", "%${loginIdPart}%")
//                if (fromDate) {
//                    ge("dateCreated", fromDate)
//                }
//            }
//        }
//    }

    // withCriteria() in-place of createCriteria().list
//    def fetchUsers(String loginIdPart, Date fromDate = null) {
//        def users = User.withCriteria {
//            and {
//                ilike("loginId", loginIdPart)
//                if (fromDate) {
//                    ge("dateCreated", fromDate)
//                }
//            }
//        }
//    }

    def advSearch() {}

    // advanced search
    def advResults() {
        def profileProps = Profile.metaClass.properties*.name

        def profiles = Profile.withCriteria {
            "${params.queryType}" {
                params.each { field, value ->
                    if (profileProps.contains(field) && value) {
                        ilike(field as String, "%${value}%")
                    }
                }
            }
        }
        return [profiles: profiles]
    }

    def register() {
        if (request.method == "POST") {
            def user = new User(params)
            if (user.validate()) {
                user.save()
                flash.message = "Successfully Created User"
                redirect(uri: '/')
            } else {
                flash.message = "Error Registering User"
                return [user: user]
            }
        }
    }

    // binds data from params to command object first
    def register2(UserRegistrationCommand urc) {
        // uses hasErrors to check validations
        if (urc.hasErrors()) {
            render(view: "register", model: [user: urc])
        } else {
            // binds data to new user object
            def user = new User(urc.properties)
            user.profile = new Profile(urc.properties)
            // saves and validate new user
            if (user.validate() && user.save()) {
                flash.message = "Welcome abroad, ${urc.fullName ?: urc.loginId}"
                redirect(uri: "/")
            } else {
                // maybe not unique loginId
                return [user: urc]
            }
        }
    }

    def profile(String id) {
        def user = User.findByLoginId(id)
        if (user) {
            return [profile: user.profile]
        } else {
            response.sendError(404)
        }
    }
}
