package helloWorld;

import java.util.ArrayList;

import redis.clients.jedis.*;
import redis.clients.jedis.tests.HostAndPortUtil;
import util.Config;
import util.GenString;

public class Pub{
	  protected static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);
	  public static Config config=new Config();
	  public static int size=config.getNumMsgListSize();
	  public static int size_byte=config.getNumByteListSize();
	  
	  public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
	    Jedis j = new Jedis(hnp.getHost(), hnp.getPort());
	    j.connect();
	    j.flushAll();
	    
//		int numMsg=config.getNumMsg();
//	    int numByte=config.getNumByte();
	    
	    ArrayList<Integer> numMsgList=config.getNumMsgList();
		ArrayList<Integer> numByteList=config.getNumByteList();
		
		long start;
		long end;
		long microseconds;
		
		for (int m=0; m<size_byte; m++){
		    String message =GenString.gen(numByteList.get(m));
		    
		    for (int n=0; n<size; n++){
			    start = System.nanoTime();
			    for(int i=0; i<numMsgList.get(n); i++)
			          // now that I'm subscribed... publish
			      j.publish("foo", message);
					
			    end = System.nanoTime();
			    microseconds = (end - start) / 1000;
				    
				System.out.println(" [x] Total time used for "+numMsgList.get(n)+" messages of "+numByteList.get(m)+" bytes: " + microseconds + " micro seconds");
				System.out.println(" [x] Time used per message of "+numByteList.get(m)+" bytes: " + (float)microseconds/(numMsgList.get(n)*1000) + " milli seconds");
				System.out.println(" [x] Sent '" + message + "'");
				System.out.println(" [x] ");
		    }
		}
		j.flushAll();
		j.disconnect();
		j.close();
	  }
	  
}
