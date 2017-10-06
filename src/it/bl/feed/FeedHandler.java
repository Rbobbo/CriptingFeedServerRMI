package it.bl.feed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import it.server.beans.FeedLoadDTO;

public interface FeedHandler
{
	public FeedLoadDTO loadFeedFromZip(String pathFileZip) throws IOException;
	
	public FeedLoadDTO loadFeedFromFile(String pathFile) throws IOException;
	
	public Object deserialyze(ObjectInputStream input) throws ClassNotFoundException, IOException;
	
	public OutputStream serialyze(FeedLoadDTO feedRequest) throws IOException;
	
	public String encryptFile(FeedLoadDTO inputFeed) throws IOException, IllegalBlockSizeException, BadPaddingException;
	public FeedLoadDTO decryptFile(FeedLoadDTO inputFeed) throws IOException, IllegalBlockSizeException, BadPaddingException;
}

