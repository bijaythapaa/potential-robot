package com.bijay

class User {

    String loginId
    String password
    Date dateCreated

    static hasOne = [profile: Profile]
    static hasMany = [posts: Post, tags: Tag, following: User]

    static mapping = {
        posts sort: "dateCreated"
    }

    static constraints = {
        loginId size: 3..20, unique: true, nullable: false
        password size: 6..8, nullable: false

        // Cross-Field validation trick
        // here, it validates that password and loginId of user can't be the same.
        password size: 6..8, blank: false, validator: { passwd, user ->
            return passwd != user.loginId
        }

        // controls ordering of associated fields without any validation constraints
        tags()
        posts()

        profile nullable: true
    }
}
