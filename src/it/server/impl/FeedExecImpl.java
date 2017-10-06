package it.server.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.bl.feed.FeedHandler;
import it.client.beans.FeedEncryptedClientRequestDTO;
import it.client.beans.FeedEncryptedClientResponseDTO;
import it.client.beans.FeedLoadClientResponseDTO;
import it.client.interfaces.prometeia.FeedExec;
import it.server.beans.FeedLoadDTO;
import prometeia.riskEngine.rischio.DataFeed;

@Component
public class FeedExecImpl implements FeedExec, Serializable
{
	private static final long serialVersionUID = 455744541095370828L;

	@Autowired
	public FeedHandler fh ;
	
	private static FeedLoadDTO serverFeedLoad = new FeedLoadDTO();

	@Override
	public FeedLoadClientResponseDTO execLoadFeedZip(String pathFileZip)
	{
		System.out.println("FeedExecImpl.execLoadFeedZip init");
		
		FeedLoadClientResponseDTO response = new FeedLoadClientResponseDTO();
		long millisCurrentStart = System.currentTimeMillis();
		try
		{
			serverFeedLoad = fh.loadFeedFromZip(pathFileZip);
			long millisCurrentEnd = System.currentTimeMillis();
			response.setElapsedTyme(millisCurrentEnd - millisCurrentStart);
			response.setObjLoad(serverFeedLoad.getDataFeedRiskEngin());
		}
		catch (IOException e)
		{
			System.err.println("Error during..something : "+e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("FeedExecImpl.execLoadFeedZip finished");
		return response	;
		
	}

	@Override
	public FeedLoadClientResponseDTO execDeserializeFeedLoaded() throws RemoteException
	{
		System.out.println("FeedExecImpl.execDeserializeFeedLoaded init");
		
		FeedLoadClientResponseDTO response = new FeedLoadClientResponseDTO();
		
		long millisCurrentStart = System.currentTimeMillis();
		try
		{
			serverFeedLoad.setObFromFeed(fh.deserialyze(serverFeedLoad.getOis()));
			if(serverFeedLoad.getObFromFeed() instanceof DataFeed)
			{
				System.out.println("execDeserializeFeedLoaded found object type DataFeed");
				serverFeedLoad.setDataFeedRiskEngin((DataFeed)serverFeedLoad.getObFromFeed() );
				response.setObjLoad(serverFeedLoad.getDataFeedRiskEngin());
			}
			
			long millisCurrentEnd = System.currentTimeMillis();
			response.setElapsedTyme(millisCurrentEnd - millisCurrentStart);
		}
		catch (IOException e)
		{
			System.err.println("Error during..something : "+e.getMessage());
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			System.err.println("Error during..something : "+e.getMessage());
			e.printStackTrace();
		}
	
		
		System.out.println("FeedExecImpl.execDeserializeFeedLoaded finished");
		return response;
	}

	@Override
	public FeedLoadClientResponseDTO execLoadFeedFile(String pathFile) throws RemoteException
	{
		System.out.println("FeedExecImpl.execLoadFeedFile init");
		
		FeedLoadClientResponseDTO response = new FeedLoadClientResponseDTO();
		long millisCurrentStart = System.currentTimeMillis();
		try
		{
			serverFeedLoad = fh.loadFeedFromFile(pathFile);
			
			long millisCurrentEnd = System.currentTimeMillis();
			response.setElapsedTyme(millisCurrentEnd - millisCurrentStart);
		}
		catch (IOException e)
		{
			System.err.println("Error during..something : "+e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("FeedExecImpl.execLoadFeedFile finished");
		return response	;
	}

	@Override
	public FeedLoadClientResponseDTO execSerializeFeedLoaded() throws RemoteException
	{
		FeedLoadClientResponseDTO response = new FeedLoadClientResponseDTO();
		long millisCurrentStart = System.currentTimeMillis();
		boolean isOkSeryalized = false;
		
		try
		{
			OutputStream obSerialized = fh.serialyze(serverFeedLoad);
			
			isOkSeryalized = (obSerialized == null) ? false : true;
			long millisCurrentEnd = System.currentTimeMillis();
			response.setElapsedTyme(millisCurrentEnd - millisCurrentStart);
		}
		catch (IOException e)
		{
			System.err.println("Error during..something : "+e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("execSerializeFeedLoaded Seryalized process : "+isOkSeryalized);
		return response;
	}

	@Override
	public FeedEncryptedClientResponseDTO execEncrypt() throws RemoteException
	{
		System.out.println("FeedExecImpl.execCrypt init");
		
		FeedEncryptedClientResponseDTO response = new FeedEncryptedClientResponseDTO();
		
		if(serverFeedLoad.getOis() != null)
		{
			long millisCurrentStart = System.currentTimeMillis();
			
			try
			{
				String pathOutput = fh.encryptFile(serverFeedLoad);
				response.setPathFileCrypted(pathOutput);
				
				long millisCurrentEnd = System.currentTimeMillis();
				response.setElapsedTyme(millisCurrentEnd - millisCurrentStart);
			}
			catch (Exception e)
			{
				System.err.println("FeedExecImpl.execCrypt ERROR: "+e.getMessage());
				e.printStackTrace();
			}
			
		}
		
		System.out.println("FeedExecImpl.execCrypt finished into file:"+response.getPathFileCrypted());
		return response;
	}

	@Override
	public FeedEncryptedClientResponseDTO execDecrypt(FeedEncryptedClientRequestDTO request) throws RemoteException
	{
		System.out.println("FeedExecImpl.execDecrypt init");
		
		FeedEncryptedClientResponseDTO response = new FeedEncryptedClientResponseDTO();
		
		long millisCurrentStart = System.currentTimeMillis();
		
		try
		{
			FeedLoadDTO feedLoadRequestDecrypt = new FeedLoadDTO();
			String fileToDecript = request.getPathFileToDecrypt();
			String fileToDecriptTarghet = fileToDecript+".decripted";
			
			feedLoadRequestDecrypt.setFeedName(fileToDecript);
			feedLoadRequestDecrypt.setPathFileDecrypted(fileToDecriptTarghet);
			
			serverFeedLoad = fh.decryptFile(feedLoadRequestDecrypt);
			response.setByteDecripted(serverFeedLoad.getFeedDecriptes());
			response.setPathFileCrypted(fileToDecript);
			response.setPathFileDecrypted(serverFeedLoad.getPathFileDecrypted());
			
			long millisCurrentEnd = System.currentTimeMillis();
			response.setElapsedTyme(millisCurrentEnd - millisCurrentStart);
		}
		catch (Exception e)
		{
			System.err.println("FeedExecImpl.execDecrypt ERROR: "+e.getMessage());
			e.printStackTrace();
		}
			
		System.out.println("FeedExecImpl.execDecrypt finished  "+serverFeedLoad.getFeedName());
		return response;
	}

	
	

}