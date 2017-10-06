package it.main;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.client.interfaces.prometeia.FeedExec;
import it.server.impl.FeedExecImpl;

public class MainRunable
{

	public static void main(String[] args)
	{
		System.out.println("Context initialized InitServer");

		FeedExec feedExecService = new FeedExecImpl();
		try
		{
			UnicastRemoteObject.unexportObject(feedExecService, true);
		}
		catch (NoSuchObjectException e1)
		{
			System.err.println(e1.getMessage()+" : don't care");
		}
		try
		{
			FeedExec stub = (FeedExec) UnicastRemoteObject.exportObject(feedExecService, 0); 
			Registry registry = LocateRegistry.createRegistry(9345);

			registry.rebind(FeedExec.nameRemote, stub);
			
            System.out.println("FROM SERVER rmi - Remote service bound for FEED "); 
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		catch(NullPointerException ex)
		{
			System.out.println("Init main runnable corrrotto \n \n");
			ex.printStackTrace();
		}

	}

}
