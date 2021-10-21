package com.bijay

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
}
