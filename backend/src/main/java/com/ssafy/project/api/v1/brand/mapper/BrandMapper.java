package com.ssafy.project.api.v1.brand.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrandMapper {

	String findBrand(@Param("merchantName") String normalizedMerchantName);

}
