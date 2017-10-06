package it.client.interfaces.prometeia;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.client.beans.FeedEncryptedClientRequestDTO;
import it.client.beans.FeedEncryptedClientResponseDTO;
import it.client.beans.FeedLoadClientResponseDTO;

public interface FeedExec extends Remote
{
	public final String nameRemote = "FeedExecService";
	
	public abstract FeedLoadClientResponseDTO execLoadFeedZip(String pathFileZip) throws RemoteException;

	public abstract FeedLoadClientResponseDTO execLoadFeedFile(String pathFileZip) throws RemoteException;
	
	public abstract FeedLoadClientResponseDTO execDeserializeFeedLoaded() throws RemoteException;
	
	public abstract FeedLoadClientResponseDTO execSerializeFeedLoaded() throws RemoteException;
	
	public abstract FeedEncryptedClientResponseDTO execEncrypt() throws RemoteException;
	
	public abstract FeedEncryptedClientResponseDTO execDecrypt(FeedEncryptedClientRequestDTO request) throws RemoteException;
}
