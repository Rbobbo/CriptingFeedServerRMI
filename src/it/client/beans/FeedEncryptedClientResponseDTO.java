package it.client.beans;

import java.io.InputStream;
import java.io.Serializable;

public class FeedEncryptedClientResponseDTO implements Serializable
{
	private static final long serialVersionUID = 5694990845524550932L;
	
	private Long elapsedTyme;
	private String pathFileCrypted;
	private String pathFileDecrypted;
	private byte[] byteDecripted;

	public Long getElapsedTyme() {
		return elapsedTyme;
	}
	public void setElapsedTyme(Long elapsedTyme) {
		this.elapsedTyme = elapsedTyme;
	}
	public String getPathFileDecrypted()
	{
		return pathFileDecrypted;
	}
	public void setPathFileDecrypted(String pathFileDecrypted)
	{
		this.pathFileDecrypted = pathFileDecrypted;
	}
	public String getPathFileCrypted()
	{
		return pathFileCrypted;
	}
	public void setPathFileCrypted(String pathFileCrypted)
	{
		this.pathFileCrypted = pathFileCrypted;
	}
	public byte[] getByteDecripted()
	{
		return byteDecripted;
	}
	public void setByteDecripted(byte[] byteDecripted)
	{
		this.byteDecripted = byteDecripted;
	}
}
