package com.tms.model.dto;

import com.tms.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsResponseDto {

    private String title;

    private String imageNews;

    private String descriptionNews;

    private Long authorNewsId;

}
