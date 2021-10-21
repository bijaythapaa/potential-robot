package com.bijay

class Post {
    String content
    Date dateCreated

    static constraints = {
        content blank: false
    }

    static belongsTo = [user: User]
    static hasMany = [tags: Tag]

    static mapping = {
//        sort dateCreated: "desc"
        dateCreated sort: "desc"
    }
}
