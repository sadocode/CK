package com.hkj.Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class App 
{
	private Crawler crawler;
	private Environment environment;
	
    public static void main( String[] args )
    {
    	
    	App app = new App();
	
    	// for nohup. shell script
    	/*
    	app.viewIntroduction();
    	
    	try
    	{
    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    		String command = null;
    		
    		while(!Thread.currentThread().isInterrupted() && !(command = br.readLine()).equals("terminate"))
    		{
    			app.executeCommand(command);
    		}
    	}
    	catch(IOException ioe)
    	{
    		//
    	}
    	*/
    	
    	app.executeCralwer();
    	while(!Thread.currentThread().isInterrupted())
		{
			//
		}
    }
    private void viewIntroduction()
    {
    	System.out.println("****************************************");
    	System.out.println("* 이 프로그램은 크롤링을 이용한 카프카 예제입니다.");
    	System.out.println("* 연합뉴스의 최신기사를 일정한 간격으로 크롤링하여 ");
    	System.out.println("* 로컬에 저장 및 섹션 별로 다르게 토픽을 지정하여 ");
    	System.out.println("* 카프카로 발행합니다.");
    	System.out.println("****************************************");
    	System.out.println("* 아래는 프로그램의 커맨드에 대한 설명입니다.");
    	System.out.println("* 커맨드는 공백없이 대소문자 구분하여 입력해야 합니다.");
    	System.out.println("* introduction : 설명 다시보기 ");
    	System.out.println("* version : 현재 버전의 정보를 보여줍니다.");
    	System.out.println("* versions : 역대 버전의 정보들을 보여줍니다.");
    	System.out.println("* execute : 크롤링을 실행시킵니다.");
    	System.out.println("* terminate : 프로그램을 종료시킵니다.");
    	System.out.println("****************************************");
    }
    private void viewVersions()
    {
    	this.version1_0_0();
    }
    private void viewCurrentVersion()
    {
    	this.version1_0_0();
    }
    
    private void executeCommand(String command)
    {
    	if(command.equals("execute"))
    	{
    		this.executeCralwer();
    	}
    	else if(command.equals("version"))
    	{
    		this.viewCurrentVersion();
    	}
    	else if(command.equals("versions"))
    	{
    		this.viewVersions();
    	}
    	else if(command.equals("introduction"))
    	{
    		this.viewIntroduction();
    	}
    	else
    	{
    		System.out.println("유효한 command를 입력해주세요.");
    	}
    }
    private void executeCralwer()
    {
    	if(this.crawler != null)
    	{
    		System.out.println("크롤러가 이미 실행중입니다.");
    		return;
    	}
    	else
    	{

    		try
    		{
    			//this.environment = new Environment();
    			//this.crawler = new Crawler(this.environment);
    			this.crawler = new Crawler(60, ".");
    			Thread thread = new Thread(crawler);
    			thread.start();
    		}
    		catch(IllegalArgumentException iae)
    		{
    			System.out.println("environment.property 파일의 프로퍼티를 정상적인 값으로 재설정하세요.");
    		}
    	}
    }
    
    
    private void version1_0_0()
    {
    	System.out.println("****************************************");
    	System.out.println("* Version 1.0.0");
    	System.out.println("* date : 2019.12.02");
    	System.out.println("* 이 버전에서는 카프카가 구현되어 있지 않습니다.");
    	System.out.println("* 프로그램을 실행하면, 연합뉴스의 최신기사를 일정한 간격으로");
    	System.out.println("* 크롤링한 후에 로컬에 저장합니다.");
    	System.out.println("****************************************");
    }
 
}
