package it.bl.feed.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.io.ByteStreams;

import it.bl.EncriptionUtilities;
import it.bl.ProviderFeedSerializeService;
import it.bl.feed.FeedHandler;
import it.server.beans.FeedLoadDTO;
import it.server.beans.ProviderSerializableZipWriterBean;
import prometeia.utility.ImportException;

@Component
public class FeedHandlerImpl implements FeedHandler
{
	private static Logger logger = Logger.getLogger(FeedHandlerImpl.class);
	
	@Autowired
	private EncriptionUtilities eUty ;
	
	public FeedLoadDTO loadFeedFromZip(String pathFile) throws IOException
	{
		FeedLoadDTO responseFeedLoadBean = new FeedLoadDTO();
		ZipFile zipFile = null;
		InputStream fis = null;
		ObjectInputStream ois = null;
		
		try
		{
			System.out.println("FeedHandlerImpl.loadFeedFromZip init load file : "+pathFile);
			fis = readFeedFromZip(pathFile, zipFile);
			ois = new ObjectInputStream(fis);
			responseFeedLoadBean.setOis(ois);
		}
		catch (ImportException e)
		{
			e.printStackTrace();
		}
		
		return responseFeedLoadBean;
	}
	
	public Object deserialyze(ObjectInputStream input) throws ClassNotFoundException, IOException
	{
		Object objectFound = input.readObject();
		
		return objectFound;
	}
	
	public OutputStream serialyze(FeedLoadDTO feedLoad) throws IOException
	{
		ObjectOutputStream oos = null;
		if(feedLoad != null && feedLoad.getDataFeedRiskEngin() != null)
		{
			System.out.println("Started serialyze");
			ProviderFeedSerializeService providerService = new ProviderFeedSerializeService();
			ProviderSerializableZipWriterBean providerWriterBean = new ProviderSerializableZipWriterBean();
			
			providerWriterBean.setPathZipOut(feedLoad.getFeedName()+".serialized");
			providerService.generateOutputStream(providerWriterBean);
			
			oos = new ObjectOutputStream(providerWriterBean.getOs());
			oos.writeObject(feedLoad.getDataFeedRiskEngin());
			oos.flush();
			oos.close();
			System.out.println("Finished serialyzed file: "+feedLoad.getFeedName()+".serialized");
		}
		
		return oos;
	}
	
	/**
	 * @param fileZipFullName
	 * @param zipFile
	 * @return
	 * @throws ImportException 
	 */
	private static InputStream readFeedFromZip(String fileZipFullName, ZipFile zipFile) throws ImportException
	{
		InputStream is = null;
		
		File f = new File(fileZipFullName);
		if(f.exists())
		{
			try
			{
				zipFile = new ZipFile(f);
				for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();)
				{
					ZipEntry entry = (ZipEntry) e.nextElement();

					is = zipFile.getInputStream(entry);
				}
			}
			catch (IOException e)
			{
				throw (new ImportException(logger,0,"IO: Errore stream object durante l'importazione del DataFeed da Zip",e));
			}
		}
		return is;
	}

	@Override
	public FeedLoadDTO loadFeedFromFile(String pathFile) throws IOException
	{
		FeedLoadDTO responseFeedLoadBean = new FeedLoadDTO();
		ObjectInputStream ois = null;
		
		System.out.println("FeedHandlerImpl.loadFeedFromFile init load file : "+pathFile);
		ois = new ObjectInputStream(eUty.getIOSFromFile(pathFile));
		responseFeedLoadBean.setOis(ois);
		responseFeedLoadBean.setFeedName(pathFile);
	
		return responseFeedLoadBean;
	}

	@Override
	public String encryptFile(FeedLoadDTO inputFeed) throws IOException, IllegalBlockSizeException, BadPaddingException
	{
		eUty.cleanObject(inputFeed);
		String pathFileEncrypted = inputFeed.getFeedName()+".encritted";
		try
		{
			InputStream iosFeed = eUty.getIOSFromFile(inputFeed.getFeedName());
			eUty.getEncriptInputStream(iosFeed,pathFileEncrypted);
		}
		catch (InvalidKeyException e)
		{
			System.err.println("Something wrong init Chipter encryptFile :"+e.getMessage());
			e.printStackTrace();
		}
		catch(IOException ex)
		{
			System.err.println("Something wrong init Chipter encryptFile :"+ex.getMessage());
			ex.printStackTrace();
		}
		return pathFileEncrypted;
	}

	@Override
	public FeedLoadDTO decryptFile(FeedLoadDTO inputFeed) throws IOException, IllegalBlockSizeException, BadPaddingException
	{
		eUty.cleanObject(inputFeed);

		byte[] bytesFeedResult = null;
		try
		{
			bytesFeedResult = eUty.getDecriptFile(inputFeed.getFeedName());
//			InputStream iosRes = new ByteArrayInputStream(bytesFeedResult);
			inputFeed.setFeedDecriptes(bytesFeedResult);
			eUty.writeFileFromBytes(bytesFeedResult, inputFeed.getPathFileDecrypted());
			System.out.println("decryptFile File wrote :"+inputFeed.getPathFileDecrypted());
		}
		catch (InvalidKeyException e)
		{
			System.err.println("Something wrong init Chipter decryptFile :"+e.getMessage());
			e.printStackTrace();
		}
		
		return inputFeed;
	}
	
	
	
	
	

}
