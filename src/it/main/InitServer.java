package it.main;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.client.interfaces.prometeia.FeedExec;

@Component
public class InitServer 
{
	@Autowired
	public  FeedExec feedExecService ;

	@EventListener({ContextRefreshedEvent.class})
	void contectRefreshedEvent()
	{
		System.out.println("Context initialized InitServer");

		try {
			UnicastRemoteObject.unexportObject(feedExecService, true);
		} catch (NoSuchObjectException e1) {
			System.out.println("Don't worry : "+e1.getMessage());
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
			System.out.println("Init SPRING corrrotto \n \n");
			ex.printStackTrace();
		}
	
	}

}
