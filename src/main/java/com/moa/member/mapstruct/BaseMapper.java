package com.moa.member.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.moa.member.dto.BaseDto;
import com.moa.member.entity.BaseEntity;

@Mapper(componentModel = "spring")
public interface BaseMapper {
	BaseMapper instance = Mappers.getMapper(BaseMapper.class);

	BaseEntity dtoToEntity(BaseDto baseDto);

	BaseDto entityToDto(BaseEntity baseEntity);
}
