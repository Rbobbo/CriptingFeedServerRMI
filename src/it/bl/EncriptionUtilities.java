package it.bl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.google.common.io.ByteStreams;

import it.server.beans.FeedLoadDTO;

@Component
public class EncriptionUtilities
{
	private static final String TRANSFORMATION = "AES";
	private static final Key secretKey = new SecretKeySpec("This is a secret".getBytes(), TRANSFORMATION);
	private static Cipher cipher;
	
	public EncriptionUtilities() throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		cipher = Cipher.getInstance(TRANSFORMATION);
		System.out.println("EncriptionUtilities init CHIPER executed correctly");
	}
	
	public InputStream getIOSFromFile(String pathFile) throws IOException
	{
		InputStream resultIOS = null;
		File fileToIOS = new File(pathFile);
		if(fileToIOS.exists() && fileToIOS.isFile())
		{
			resultIOS = FileUtils.openInputStream(fileToIOS);
			byte[] targetArray = ByteStreams.toByteArray(resultIOS);
			
			System.out.println("targetArray = "+targetArray);
		}
		fileToIOS = null;
		
		return resultIOS;
	}
	
	public void getEncriptInputStream(InputStream isRequest, String pathFileTarghet) throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
	{
		System.out.println("EncriptionUtilities.getEncriptInputStream begin encription "+isRequest);
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] fisByteNotEncoded =  IOUtils.toByteArray(isRequest);
//		byte[] fisByteEncoded = Base64.encodeBase64(fisByteNotEncoded);
//		byte[] fisByteEncodendEncrypted = cipher.doFinal(fisByteEncoded);
		byte[] fisByteEncodendEncrypted = cipher.doFinal(fisByteNotEncoded);
		fisByteNotEncoded = null;
		
		File refOutputPath = new File(pathFileTarghet);
        File targetDir = refOutputPath.getParentFile();
        FileUtils.forceMkdir(targetDir);
        FileUtils.writeByteArrayToFile(refOutputPath, fisByteEncodendEncrypted);
        fisByteEncodendEncrypted = null;
		
		System.out.println("EncriptionUtilities.getEncriptInputStream end encription and write file: "+refOutputPath);
	}
	
	public byte[]  getDecriptFile(String pathFileCrypted) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException
	{
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		File fileCrypted = new File(pathFileCrypted);
		InputStream isResult = new FileInputStream(fileCrypted);
		byte[] fisByte =  IOUtils.toByteArray(isResult);
		isResult.close();
		isResult = null;
		
		byte[] decriptedTest = cipher.doFinal(fisByte);
//		byte[] decriptedTest = new Base64().decode(cipher.doFinal(fisByte));
//		isResult = IOUtils.toInputStream(new String(decriptedTest), "UTF-8");

		return decriptedTest;
	}
	
	public boolean writeFileFromBytes(byte[] bytesToWrite, String pathFileToWrite) throws IOException
	{
		boolean isOkWrite = false;
		File targetFile = new File(pathFileToWrite);
		FileUtils.writeByteArrayToFile(targetFile, bytesToWrite);
		
		return isOkWrite;
	}
	
	public void cleanObject(FeedLoadDTO request)
	{
		request.setDataFeedRiskEngin(null);
		request.setFeedDecrypted(null);
		request.setFeedEncrypted(null);
		request.setObFromFeed(null);
		request.setOis(null);
	}
	
	

}
