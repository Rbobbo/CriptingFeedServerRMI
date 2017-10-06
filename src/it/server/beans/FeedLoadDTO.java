package it.server.beans;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import prometeia.riskEngine.rischio.DataFeed;

public class FeedLoadDTO
{
	private String feedName;
	private String pathFileDecrypted;
	private Object obFromFeed ;
	private ObjectInputStream ois;
	private DataFeed dataFeedRiskEngin ;
	private OutputStream feedEncrypted;
	private InputStream feedDecrypted;
	private byte[] feedDecriptes;
	
	public Object getObFromFeed() {
		return obFromFeed;
	}
	public void setObFromFeed(Object obFromFeed) {
		this.obFromFeed = obFromFeed;
	}
	public ObjectInputStream getOis() {
		return ois;
	}
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	public String getFeedName() {
		return feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}
	public DataFeed getDataFeedRiskEngin() {
		return dataFeedRiskEngin;
	}
	public void setDataFeedRiskEngin(DataFeed dataFeedRiskEngin) {
		this.dataFeedRiskEngin = dataFeedRiskEngin;
	}
	public OutputStream getFeedEncrypted()
	{
		return feedEncrypted;
	}
	public void setFeedEncrypted(OutputStream feedEncrypted)
	{
		this.feedEncrypted = feedEncrypted;
	}
	public InputStream getFeedDecrypted()
	{
		return feedDecrypted;
	}
	public void setFeedDecrypted(InputStream feedDecrypted)
	{
		this.feedDecrypted = feedDecrypted;
	}
	public String getPathFileDecrypted()
	{
		return pathFileDecrypted;
	}
	public void setPathFileDecrypted(String pathFileDecrypted)
	{
		this.pathFileDecrypted = pathFileDecrypted;
	}
	public byte[] getFeedDecriptes()
	{
		return feedDecriptes;
	}
	public void setFeedDecriptes(byte[] feedDecriptes)
	{
		this.feedDecriptes = feedDecriptes;
	}
	

}
