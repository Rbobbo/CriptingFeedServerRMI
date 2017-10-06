package it.client.beans;

import java.io.Serializable;

public class FeedLoadClientResponseDTO implements Serializable
{
	private static final long serialVersionUID = 6162887627696512424L;
	
	private Long elapsedTyme;
	private Serializable objLoad;

	public Long getElapsedTyme() {
		return elapsedTyme;
	}

	public void setElapsedTyme(Long elapsedTyme) {
		this.elapsedTyme = elapsedTyme;
	}

	public Serializable getObjLoad()
	{
		return objLoad;
	}

	public void setObjLoad(Serializable objLoad)
	{
		this.objLoad = objLoad;
	}

}
