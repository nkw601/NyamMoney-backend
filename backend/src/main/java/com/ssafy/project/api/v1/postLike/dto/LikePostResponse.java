package com.ssafy.project.api.v1.postLike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikePostResponse {
    private Long postId;
    private boolean likeState;
    private int likesCount;
}

