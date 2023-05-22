package com.moa.member.mastruct;

import com.moa.member.dto.BaseDto;
import com.moa.member.entity.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BaseMapper {
    BaseMapper instance = Mappers.getMapper(BaseMapper.class);

    BaseEntity dtoToEntity(BaseDto baseDto);
    BaseDto entityToDto(BaseEntity baseEntity);
}