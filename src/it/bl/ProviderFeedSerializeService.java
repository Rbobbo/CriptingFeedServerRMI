package it.bl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import it.server.beans.ProviderSerializableZipWriterBean;

/**
 * @author ricca
 * 
 * La classe fornisce utility per settare il provider utile
 * alla serializzazione di oggetti di tipo Serializable
 *
 */
public class ProviderFeedSerializeService
{
	
	public void generateOutputStream(ProviderSerializableZipWriterBean providerWriter) throws IOException
	{
		String outputPathZip = providerWriter.getPathZipOut();
		//Sostituisco l'estensione del file, qualsiasi sia tale estensione, con .zip
		//Creo l'output stream
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputPathZip));
		zos.putNextEntry(new ZipEntry(new File(outputPathZip).getName())); 
		providerWriter.setOs(zos);
		
		
	}

}
