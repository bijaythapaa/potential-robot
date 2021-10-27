package com.bijay

class PhotoUploadCommand {
    // holds uploaded photo data
    byte[] photo
    String loginId
}

class ImageController {

    def upload(PhotoUploadCommand puc) {
        def user = User.findByLoginId(puc.loginId)
        user.profile.photo = puc.photo
        redirect(controller: "user", action: "profile", id: puc.loginId)
    }

    def form() {
        // pass through to upload file
        [userList: User.list()]
    }

    def rawUpload() {
        // a Spring Multipart file
        def mpf = request.getFile('photo')
        // 1024 * 1024 is less than 1MB
        if (!mpf?.empty && mpf.size < 1024 * 1024) {
            mpf.transferTo(new File("/home/bijay.thapa/Documents/GrailsRepo/hubbub/images/${params.loginId}/mugshot.gif"))
        }
    }

    def renderImage(String id) {
        def user = User.findByLoginId(id: id)
        if (user?.profile?.photo) {
            response.setContentLength(user.profile.photo.size())
            response.outputStream.write(user.profile.photo)
        } else {
            response.sendError(404)
        }
    }

    def index() {}
}
