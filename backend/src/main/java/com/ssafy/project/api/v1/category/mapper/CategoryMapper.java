package com.ssafy.project.api.v1.category.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.project.api.v1.category.dto.CategoryItem;

@Mapper
public interface CategoryMapper {

	List<CategoryItem> selectAll();
	
    boolean existsById(@Param("categoryId") Long categoryId);

}
