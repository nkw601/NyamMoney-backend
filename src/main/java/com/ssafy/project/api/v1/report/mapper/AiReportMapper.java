package com.ssafy.project.api.v1.report.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.api.v1.report.dto.CategoryStats;

@Mapper
public interface AiReportMapper {

	List<CategoryStats> selectMonthlyCategoryStats(Long userId, LocalDateTime startAt, LocalDateTime endAt);

}
