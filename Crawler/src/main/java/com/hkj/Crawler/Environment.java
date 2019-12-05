package com.hkj.Crawler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {
	private Properties environment;
	private int coolTime;
	private String savePath;
	
	private static final String PROPERTY = "environment.properties";
	private static final String COOLTIME = "coolTime";
	private static final String SAVEPATH = "savePath";
	
	public Environment()
	{
		this.loadProperty();
	}
	public void loadProperty()
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(PROPERTY);
			this.environment.load(fis);
			this.coolTime = Integer.parseInt(this.environment.getProperty(COOLTIME));
			this.savePath = this.environment.getProperty(SAVEPATH);
			
			fis.close();
		}
		catch(IOException ioe)
		{
			//
		}
		finally
		{
			if(fis != null)
				fis = null;
		}
	}
	public void loadProperty(String name)
	{
		// 이거 좀더 객체지향적으로 사용할 수 있을건데, 아직은 가져올 값이 많지 않으니 이대로 둔다.
	}
	public int getCoolTime()
	{
		return this.coolTime;
	}
	public String getSavePath()
	{
		return this.savePath;
	}
}
