package com.baka3k.core.data.movie.model

class PhotoSize {
    companion object {
        const val photoBasePath = "https://image.tmdb.org/t/p/"
        const val sizeSmall = 0
        const val sizeMedium = 1
        const val sizeOriginal = 10
    }

    enum class BackDrop(private val size: String) : Size {
        w300("w300"),
        w780("w780"),
        w1280("w1280"),
        original("original");

        override fun getSize(): String {
            return this.size
        }
    }

    enum class Logo(private val size: String) : Size {
        w45("w45"),
        w92("w92"),
        w154("w154"),
        w185("w185"),
        w300("w300"),
        w500("w500"),
        original("original");

        override fun getSize(): String {
            return this.size
        }
    }

    enum class Poster(private val size: String) : Size {
        w45("w45"),
        w92("w92"),
        w154("w154"),
        w185("w185"),
        w300("w342"),
        w500("w500"),
        w780("w780"),
        original("original");

        override fun getSize(): String {
            return this.size
        }
    }

    enum class Profile(private val size: String) : Size {
        w45("w45"),
        w185("w185"),
        h632("h632"),
        original("original");

        override fun getSize(): String {
            return this.size
        }
    }

    enum class Still(private val size: String) : Size {
        w92("w92"),
        w185("w185"),
        w300("w300"),
        original("original");

        override fun getSize(): String {
            return this.size
        }
    }

    interface Size {
        fun getSize(): String
    }
}

fun String.asPhotoUrl(size: PhotoSize.Size): String {
    return "${PhotoSize.photoBasePath}${size.getSize()}$this"
}