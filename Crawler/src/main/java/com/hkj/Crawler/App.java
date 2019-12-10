package com.hkj.Crawler;

import java.util.regex.Pattern;

public class App 
{
	private Crawler crawler;
	private Thread thread;
	
	private static final String TIME = "^-time=[0-9]+$";
	private static final String PATH = "-path=";
	private int coolTime;
	private String savePath;
	
	private Crawler initCrawler(String[] args)
	{
		Crawler temp = null;
		
		if(args.length == 0)
			return temp = new Crawler();
		
		for(int i = 0; i < args.length; i++)
		{
			if(pathMatch(args[i]))
			{
				this.savePath = args[i].substring(6);
				break;
			}
		}
		for(int i = 0; i < args.length; i++)
		{
			if(timeMatch(args[i]))
			{
				try
				{
					this.coolTime = Integer.parseInt(args[i].substring(6));
				}
				catch(NumberFormatException nfe)
				{
					this.coolTime = 0;
				}
				break;
			}
		}	
		
		if(this.savePath != null & this.coolTime != 0)
		{
			try
			{
				temp = new Crawler(this.coolTime, this.savePath);
			}
			catch(IllegalArgumentException iae)
			{
				temp = new Crawler(this.savePath);
			}
		}
		else if(this.savePath != null & this.coolTime == 0)
		{
			temp = new Crawler(this.savePath);
		}
		else if(this.savePath == null & this.coolTime != 0)
		{
			try
			{
				temp = new Crawler(this.coolTime);
			}
			catch(IllegalArgumentException iae)
			{
				temp = new Crawler();
			}
		}
		else
		{
			temp = new Crawler();
		}
		return temp;
	}
	private boolean timeMatch(String data)
	{
		return Pattern.matches(TIME, data);
	}
	private boolean pathMatch(String data)
	{
		return data.startsWith(PATH);
	}
    public static void main( String[] args )
    {
    	App app = new App();
    	
    	app.crawler = app.initCrawler(args);
    		
    	
    	app.thread = new Thread(app.crawler);
    	app.thread.start();
    	
    }
   
}
