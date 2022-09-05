package com.test.myapplication.model

data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<Link>,
    val metadata: Metadata,
    val version: String
)