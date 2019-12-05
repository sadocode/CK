package com.hkj.Crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArticleCrawler implements Runnable{

	// 기사의 URL
	private String path;
	
	// 기사를 저장할 위치
	private String savePath;
	
	// path로 불러올 페이지
	private Document articlePage;
	
	// 기사의 contentsId
	private String cid;
	
	// 기사의 section
	private String section;
	
	// 기사의 내용이 담긴 Elements
	private Elements article;
	
	// 기사 제목 
	private String title;
	
	// 기사 부제목
	private String subTitle;
	
	// 기사 본문 
	private Iterator<Element> body;
	
	// 기사 : 제목 + 부제목 + 본문 
	private String contents;
	
	// 기사 크롤링을 위한 css jquery
	private static final String ARTICLE_CONTENT = "div#articleWrap";
	private static final String TITLE = "h1.tit-article";
	private static final String SUBTITLE = "div.stit";
	private static final String BODY = "p";
	private static final String CID = "meta[property=dable:item_id]";
	private static final String SECTION = "meta[property=article:section]";
	private static final String CONTENT = "content";
	
	
	public ArticleCrawler(String path, String savePath)
	{
		if(path == null || path == "")
			throw new java.lang.NullPointerException("article path is null.");
		this.path = path;
		this.savePath = savePath;
	}
	public void run()
	{
		this.readPage();
		this.contents = this.readArticle();
		this.saveArticle(this.getFilePath());
	}
	private void readPage()
	{
		try
		{
			this.articlePage = Jsoup.connect(this.path).get();
		}
		catch(IOException ioe)
		{
			//
		}
	}
	private String readArticle()
	{
		
		this.cid = this.articlePage.select(CID).first().attr(CONTENT);
		this.section = this.articlePage.select(SECTION).first().attr(CONTENT);
		
		this.article = this.articlePage.select(ARTICLE_CONTENT);
		this.title = this.article.select(TITLE).text();
		this.subTitle = this.article.select(SUBTITLE).text();
		this.body = this.article.select(BODY).iterator();
		
		StringBuilder result = new StringBuilder(this.title);
				
		if(this.subTitle.length() != 0)
		{
			result.append("\n").append(this.subTitle);
		}
		while(this.body.hasNext())
		{
			result.append("\n").append(this.body.next().text());
		}
		return result.toString();
	}
	private void saveArticle(String filePath)
	{
		
		System.out.print(this.contents);
		FileOutputStream fos = null;
		
		try
		{

			fos = new FileOutputStream(filePath);
			fos.write(contents.getBytes());
			fos.flush();
			fos.close();
		}
		catch(IOException ioe)
		{
			System.out.println("error IO");
		}
		finally
		{
			if(fos != null)
				fos = null;
		}
		
	}
	private String getFilePath()
	{
		StringBuilder filePath = new StringBuilder();
		
		if(this.savePath.length() != 0)
		{
			filePath.append(this.savePath);
		}
		else
		{
			filePath.append(".");
		}
		
		if(!this.savePath.endsWith("/"))
			filePath.append("/");
		if(this.section.length() != 0)
			filePath.append(this.section).append("/");
		
		File folder = new File(filePath.toString());
		if(!folder.exists())
		{
			try
			{
				folder.mkdir();
			}
			catch(Exception e)
			{
				//
			}
		}
		
		filePath.append(this.cid).append(".txt");
		
		return filePath.toString();
	}
}
