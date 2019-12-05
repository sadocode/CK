package com.hkj.Crawler;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler implements Runnable{
	
	// 크롤링 주기
	private int coolTime;
	
	// 기사 저장 path
	private String savePath;
	
	// property를 얻어올 클래스
	// private Environment environment;
	
	// 최신기사 25개를 저장하는 리스트.
	private List<String> articlePathList;
	
	// 크롤링해야하는 타겟 기사를 저장하는 리스트.
	private List<String> targetArticleList;
	
	// 최신기사 페이지 
	private Document recentArticleDocument;
	
	// 이전 크롤링 시에 가장 최신이었던 기사  
	private String recentArticlePath;
	
	// 최신기사 페이지 URL
	private static final String RECENT_ARTICLE_PATH = "https://www.yna.co.kr/news?site=navi_news";
	
	// 기사 선택을 위한 css JQuery
	private static final String ARTICLE_LIST_ELEMENT = "div.headlines";
	private static final String ARTICLE_PATH = "strong.news-tl > a";
	
	// 기사 각각의 path에 붙여줄 prefix
	private static final String PREFIX = "https:";
	/*
	public Crawler(Environment environment)
	{
		if(environment == null)
			throw new java.lang.NullPointerException("environment is null.");
		
		this.environment = environment;
		this.savePath = this.environment.getSavePath();
		
		this.coolTime = this.environment.getCoolTime();
		System.out.println("@@@@@"+this.coolTime);
		if(this.coolTime < 60 || this.coolTime > 1000)
			throw new java.lang.IllegalArgumentException("coolTime is abnormal.");
	}
	*/
	public Crawler(int coolTime, String savePath)
	{
		if(coolTime < 60 || coolTime > 1000)
			throw new java.lang.IllegalArgumentException("coolTime is abnormal.");
		if(savePath == null)
			throw new java.lang.NullPointerException("savePath is null.");
		this.coolTime = coolTime;
		this.savePath = savePath;
	}
	public void run()
	{
		// interrupt 조건 넣어줘야함.
		while(!Thread.currentThread().isInterrupted())
		{
			this.readPage();
			this.readArticlePath();
			if(this.compareToRecentPath() != 0)
			{
				this.loadArticleCrawler();
			}
			try
			{
				Thread.sleep(this.coolTime * 1000);
			}
			catch(InterruptedException ie)
			{
				//
			}
		}
	}
	
	/**
	 * 최신 기사 페이지를 읽어오는 메소드.
	 */
	public void readPage()
	{
		try
		{
			this.recentArticleDocument = Jsoup.connect(RECENT_ARTICLE_PATH).get();
		}
		catch(IOException ioe)
		{
			//
		}
	}
	/**
	 * 최신기사 리스트에 나열된 각각의 기사의 URL을 읽어오는 메소드.
	 */
	public void readArticlePath()
	{
		Elements articleListElement = this.recentArticleDocument.select(ARTICLE_LIST_ELEMENT);
		
		Iterator<Element> articleIterator = articleListElement.select(ARTICLE_PATH).iterator();
		
		this.initArticlePathList();
		
		while(articleIterator.hasNext())
		{
			// test 후 고쳐야하는 코드임.
			String temp = articleIterator.next().attr("href");
			System.out.println(temp);
			this.articlePathList.add(temp);
		}
	}
	private void initArticlePathList()
	{
		this.articlePathList = new LinkedList<String>();
	}
	
	private int compareToRecentPath()
	{
		// 프로그램을 처음 실행했을 때는 recentArticlePath == null이다.
		// 이 때는 기사 크롤링을 진행하지 않는다.
		if(this.recentArticlePath == null)
			this.recentArticlePath = this.articlePathList.get(0);
		
		int index = this.articlePathList.indexOf(this.recentArticlePath);
		
		if(index != 0)
		{
			this.recentArticlePath = this.articlePathList.get(0);
		}
		this.initTargetArticleList();
		
		for(int i = 0; i < index; i++)
		{
			this.targetArticleList.add(PREFIX + this.articlePathList.get(i));
		}
		
		return this.targetArticleList.size();
	}
	private void initTargetArticleList()
	{
		this.targetArticleList = new LinkedList<String>();
	}
	private void loadArticleCrawler()
	{
		int size = this.targetArticleList.size();
		
		for(int i = 0; i < size; i++)
		{
			this.loadArticleCrawler(this.targetArticleList.get(i));
		}
	}
	private void loadArticleCrawler(String path)
	{
		ArticleCrawler articleCrawler = new ArticleCrawler(path, this.savePath);
		Thread thread = new Thread(articleCrawler);
		thread.start();
	}
	
}
