package com.raxors.photobooth.data.mappers

import com.raxors.photobooth.data.models.response.RelationshipResponse
import com.raxors.photobooth.data.models.response.UserResponse
import com.raxors.photobooth.domain.models.Relationship
import com.raxors.photobooth.domain.models.User

fun UserResponse.toUser() = User(
    this.id,
    this.username,
    this.name,
    this.status,
    this.imagePath,
    this.thumbnailPath,
    this.relationship?.toRelationship()
)

fun RelationshipResponse.toRelationship() = Relationship.valueOf(this.toString())