package com.test.myapplication.model

data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
)