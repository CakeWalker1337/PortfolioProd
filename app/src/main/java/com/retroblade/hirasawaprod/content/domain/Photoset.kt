package com.retroblade.hirasawaprod.content.domain

/**
 * @author m.a.kovalev
 */
class Photoset(
    val id: String,
    val owner: String,
    val ownerName: String,
    val photos: List<Photo>
)