package it.server.beans;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.zip.ZipOutputStream;

public class ProviderSerializableZipWriterBean implements Serializable
{
	private static final long serialVersionUID = 7039787711052321302L;
	
	private String pathZipOut;
	private ZipOutputStream zos;
	private OutputStream os;
	
	
	public String getPathZipOut() {
		return pathZipOut;
	}
	public void setPathZipOut(String pathZipOut) {
		this.pathZipOut = pathZipOut;
	}
	public ZipOutputStream getZos() {
		return zos;
	}
	public void setZos(ZipOutputStream zos) {
		this.zos = zos;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	
	
	

}
