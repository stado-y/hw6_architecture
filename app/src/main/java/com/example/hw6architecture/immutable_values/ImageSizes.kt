package com.example.hw6architecture.immutable_values

object ImageSizes {

    enum class Logo(val size: String) {

        SMALL("w45"),
        MEDIUM("w185"),
        LARGE("w500"),
    }

    enum class Backdrop(val size: String) {

        SMALL("w300"),
        MEDIUM("w780"),
        LARGE("w1280"),
    }

    enum class Poster(val size: String) {

        SMALL("w92"),
        MEDIUM("w185"),
        LARGE("w780"),
    }

    enum class Profile(val size: String) {

        SMALL("w45"),
        MEDIUM("w185"),
        LARGE("h632"),
    }

    enum class Still(val size: String) {

        SMALL("w92"),
        MEDIUM("w185"),
        LARGE("w300"),
    }
}