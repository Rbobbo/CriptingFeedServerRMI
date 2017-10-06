package it.client.beans;

import java.io.Serializable;

public class FeedEncryptedClientRequestDTO implements Serializable
{
	
	private static final long serialVersionUID = 2131123344793603236L;
	
	private String pathFileToDecrypt;

	public String getPathFileToDecrypt()
	{
		return pathFileToDecrypt;
	}

	public void setPathFileToDecrypt(String pathFileToDecrypt)
	{
		this.pathFileToDecrypt = pathFileToDecrypt;
	}

}
