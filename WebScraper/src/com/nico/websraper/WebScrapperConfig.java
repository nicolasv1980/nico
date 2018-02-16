package com.nico.websraper;

import java.util.ArrayList;
import java.util.List;

public class WebScrapperConfig {
	private String name;
	private String baseUrl;
	private String brandPattern;
	private String modelPattern;
	private String pricePattern;
	private String categoryPattern;
	private String productPageTag;
	private String productPageContent;
	private List<String> exclusions;
	
	public WebScrapperConfig(String name, String baseUrl, String brandPattern, String modelPattern, String pricePattern,
			String categoryPattern, String productPageTag, String productPageContent) {
		super();
		this.name = name;
		this.baseUrl = baseUrl;
		this.brandPattern = brandPattern;
		this.modelPattern = modelPattern;
		this.pricePattern = pricePattern;
		this.categoryPattern = categoryPattern;
		this.productPageTag = productPageTag;
		this.productPageContent = productPageContent;
		this.exclusions = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	public String getBrandPattern() {
		return brandPattern;
	}
	public String getModelPattern() {
		return modelPattern;
	}
	public String getPricePattern() {
		return pricePattern;
	}
	public String getCategoryPattern() {
		return categoryPattern;
	}
	public String getBaseUrl(){
		return baseUrl;
	}

	public String getProductPageTag() {
		return productPageTag;
	}

	public String getProductPageContent() {
		return productPageContent;
	}

	public List<String> getExclusions() {
		return exclusions;
	}
}
