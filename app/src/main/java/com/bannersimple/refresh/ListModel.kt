package com.bannersimple.refresh

/**
 * by y on 2016/8/7.
 */
class ListModel {

    var title: String? = null
    var titleImage: String? = null
    var slug: Int = 0
    var author: Author? = null

    class Author {
        var profileUrl: String? = null
        var bio: String? = null
        var name: String? = null

    }
}
