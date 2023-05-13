package com.moa.member.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseDto {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
