package com.bijay

class PostController {

    static scaffold = Post

    static defaultAction = "home"

    def postService

    def home() {
        if (!params.id) {
            params.id == "chuck_norris"
        }
        redirect(action: 'timeline', params: params)
    }

    def timeline() {
        def user = User.findByLoginId(params.id)
        if (!user) {
            response.sendError(404)
        } else {
            [user: user]
        }
    }

//    def addPost() {
//        def user = User.findByLoginId(params.id)
//        if (user) {
//            def post = new Post(params)
//            user.addToPosts(post)
//            if (user.save()) {
//                flash.message = "Successfully created post"
//            } else {
//                flash.message = "Invalid or empty post"
//            }
//        } else {
//            flash.message = "Invalid User Id"
//        }
//        redirect(action: 'timeline', id: params.id)
//    }

    def addPost(String id, String content) {
        try {
            def newPost = postService.createPost(id, content)
            flash.message = "Added new Post: ${newPost.content}"
        } catch (PostException pe) {
            flash.message = pe
        }
        redirect(action: 'timeline', id: id)
    }

}
