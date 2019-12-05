package com.hkj.Crawler;

public class App 
{
	private Crawler crawler;
	private Thread thread;
	
    public static void main( String[] args )
    {
    	App app = new App();
    	int temp = 0;
    	
    	try
    	{
    		if(args.length == 0)
    		{
    			app.crawler = new Crawler();
    		}
    		else if(args.length == 1)
    		{
    			temp = Integer.parseInt(args[0]);
    			app.crawler = new Crawler(temp);
    		}
    		else if(args.length == 2)
    		{
    			temp = Integer.parseInt(args[0]);
    			app.crawler = new Crawler(temp, args[1]);
    		}
    		else
    		{
    			throw new java.lang.IllegalArgumentException("There are too many arguments");
    		}
    	}
    	catch(NumberFormatException nfe)
    	{
    		app.crawler = new Crawler();
    	}
    	catch(IllegalArgumentException iae)
    	{
    		app.crawler = new Crawler();
    	}
    	finally
    	{
    		app.thread = new Thread(app.crawler);
	    	app.thread.start();
    	}
    }
   
}
