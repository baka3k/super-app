package com.baka3k.core.model

data class Author(
    val id: String,
    val name: String,
    val imageUrl: String,
    val twitter: String,
    val mediumPage: String,
    val bio: String,
)

val previewAuthors = listOf(
    Author(
        id = "22",
        name = "Alex Vanyo",
        mediumPage = "https://medium.com/@alexvanyo",
        twitter = "https://twitter.com/alex_vanyo",
        imageUrl = "https://pbs.twimg.com/profile_images/1431339735931305989/nOE2mmi2_400x400.jpg",
        bio = "Alex joined Android DevRel in 2021, and has worked supporting form factors from small watches to large foldables and tablets. His special interests include insets, Compose, testing and state."
    ),
    Author(
        id = "3",
        name = "Simona Stojanovic",
        mediumPage = "https://medium.com/@anomisSi",
        twitter = "https://twitter.com/anomisSi",
        imageUrl = "https://pbs.twimg.com/profile_images/1437506849016778756/pG0NZALw_400x400.jpg",
        bio = "Android Developer Relations Engineer @Google, working on the Compose team and taking care of Layouts & Navigation."
    )
)
