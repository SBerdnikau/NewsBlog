package com.tms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDto {
    private String commentTopic;
    private String descriptionComments;
    private Long newsId;
    private Long authorCommentId;
}
