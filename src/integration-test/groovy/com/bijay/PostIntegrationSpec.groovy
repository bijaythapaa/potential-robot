package com.bijay


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class PostIntegrationSpec extends Specification {

//    def setup() {
//    }
//
//    def cleanup() {
//    }
//
//    void "test something"() {
//        expect: "fix me"
//        true == false
//    }

    // Grails User.addToPosts() methods makes 1:m relationship easy
    def "Adding posts to user links post to user"() {
        given: "A brand new user"
        def user = new User(loginId: "bijay", password: "secret")
        user.save(failOnError = true)

        // we have to call .save() method to persist User data to database.
        // but User other object graphs (new Post objects via .addTPosts()) are automatically persisted in database.
        // so, no need to call .save() method explicitly.

        when: "Several posts are added to User"
        user.addToPosts(new Post(content: "First post ..."))
        user.addToPosts(new Post(content: "Second post ..."))
        user.addToPosts(new Post(content: "Third post ..."))

        then: "The user has list of posts attached"
        3 == User.get(user.id).posts.size()
    }

    // Accessing a User's post by walking the object graph
    def "Ensure posts linked to a user can be retrieved"() {
        given: "A user with several posts"
        def user = new User(loginId: "bijay", password: "secret")
        user.addToPosts(new Post(content: "First"))
        user.addToPosts(new Post(content: "Second"))
        user.addToPosts(new Post(content: "Third"))

        when: "The user is retrieved by their id"
        def foundUser = User.get(user.id)

        // iterates through User's post
        def sortedPostContent = foundUser.posts.collect({

            // 'it' is a keyword, points to the Post class.
            // I think, maybe, its like we do 'shadowing' in Java.
            it.content
        }).sort()

        // since, by default the 1:m collects data in Set : [a unordered data-structure], hence .sort() needed.

        then: "The posts appear on the retrieved user"
        sortedPostContent == ['First', 'Second', 'Third']
    }

    // A complex m:n scenario for posts and tags
    def "Exercise tagging several tags with various tags"() {
        given: "A user with set of tags"
        def user = new User(loginId: "bijay", password: "secret")
        def tagGroovy = new Tag(name: "groovy")
        def tagGrails = new Tag(name: "grails")
        user.addToTags(tagGroovy)
        user.addToTags(tagGrails)
        user.save(failOnError: true)

        when: "The user adds two new fresh posts"
        def groovyPost = new Post(content: "A groovy post ...")
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)

        def bothPost = new Post(content: "A groovy and grails post ...")
        user.addToPosts(bothPost)
        bothPost.addToTags(tagGroovy)
        bothPost.addToTags(tagGrails)

        then:
        user.tags*.name.sort() == ['grails', 'groovy']
        1 == groovyPost.tags.size()
        2 == bothPost.tags.size()
    }
}
