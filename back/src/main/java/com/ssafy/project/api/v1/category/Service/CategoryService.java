package com.ssafy.project.api.v1.category.Service;

import com.ssafy.project.api.v1.category.dto.CategoryListResponse;

public interface CategoryService {

	CategoryListResponse getCategories();
	
	public String mapCategoryByIndustry(String industryLcls, String industryMcls);
	
	public String mapRetailCategory(String industryMcls);
	
	public String mapManufacturingCategory(String industryMcls);

}
