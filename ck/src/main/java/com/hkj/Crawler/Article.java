package com.hkj.Crawler;

public class Article {
	String contentsId;
	String section;
	String path;
	String time;
	
	public Article(String contentsId, String section, String path, String time)
	{
		if(contentsId == null || contentsId == "" || section == null || section == "" 
				|| path == null || path == "" || time == null || time == "")
			throw new java.lang.NullPointerException("parameter is null.");
		this.contentsId = contentsId;
		this.section = section;
		this.path = path;
		this.time = time;
	}
	public String getContentsId()
	{
		return this.contentsId;
	}
	public String getSection()
	{
		return this.section;
	}
	public String getPath()
	{
		return this.path;
	}
	public String getTime()
	{
		return this.time;
	}
}
