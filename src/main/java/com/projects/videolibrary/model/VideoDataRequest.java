package com.projects.videolibrary.model;

import java.util.UUID;

public record VideoDataRequest(
    UUID id,
    String videoId,
    UUID categoryId
) {

}
