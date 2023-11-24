package com.raxors.photobooth.data.mappers

import com.raxors.photobooth.data.models.response.ImageResponse
import com.raxors.photobooth.data.models.response.RelationshipResponse
import com.raxors.photobooth.data.models.response.UserResponse
import com.raxors.photobooth.domain.models.Image
import com.raxors.photobooth.domain.models.Relationship
import com.raxors.photobooth.domain.models.User

fun ImageResponse.toImage() = Image(
    this.id,
    this.path,
    this.thumbnailPath,
    this.createdTime,
    this.ownerId
)