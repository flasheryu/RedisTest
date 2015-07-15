package helloWorld;

import java.util.ArrayList;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.tests.HostAndPortUtil;
import util.Config;

public class Sub {
	  protected static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);
	  public static Config config=new Config();

//	  protected static int numMsg=config.getNumMsg();
//	  protected static int numByte=config.getNumByte();
	  
	  protected static ArrayList<Integer> numMsgList=config.getNumMsgList();
	  protected static ArrayList<Integer> numByteList=config.getNumByteList();
	  public static int size=config.getNumMsgListSize();
	  public static int size_byte=config.getNumByteListSize();

	  static float[][] avgTime=new float[size][size_byte];
	  
	  public static long start;
	  public static long end;
	  public static long count;
	  public static int flag=0;
	  public static int flag_byte=0;
	    
	  public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
	    Jedis j = new Jedis(hnp.getHost(), hnp.getPort());
	    j.connect();
	    j.flushAll();
	    count=0;
		j.subscribe(new JedisPubSub() {
			public void onMessage(String channel, String message) {
			  if(count==0)	{start = System.nanoTime(); System.out.println(" [x] start '" + start + "'");}
			  count++;
			  try {
				Thread.sleep(50);
			  } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
			  for(int m=flag_byte; m<size_byte; m++){
				  for(int n=flag; n<size; n++){
					  if(count==numMsgList.get(flag)){
						  end = System.nanoTime();
						  avgTime[flag][flag_byte]=(float)((end-start)/1000)/count;
						  System.out.println(" [x] Result for '" + count + " messages of size " +numByteList.get(flag_byte)+" is :");
						  System.out.println(" [x] start '" + start + "'");
						  System.out.println(" [x] end '" + end + "'");
						  System.out.println(" [x] end-start '" + (float)((end-start)/1000)/count + "' micro seconds");
						  System.out.println(" [x] end-start absolute '" + (float)(end-start)/1000 + "' micro seconds");
						  System.out.println(" [x] received message is "+message+".");
						  System.out.println(" ");
						  start = System.nanoTime();
						  count=0;
						  if(flag==size-1) {flag_byte++; flag=0;}
						  else flag++;
					  } 
				  }
				  if(flag_byte==size_byte)	unsubscribe();
			  }
		    }
		      public void onUnsubscribe(String channel, int subscribedChannels) {
		    	System.out.println(" [x] Finished!");
	    		for(int n=0; n<size_byte; n++)
	    			for(int m=size; m>0; m--)
	    				System.out.println(" [x] avgTime for "+numMsgList.get(m-1)+" of "+numByteList.get(n)+" byte messages is "+avgTime[m-1][n]+" micro seconds.");
		      }
		    }, "foo");

	    j.flushAll();
		j.disconnect();
		j.close();
	  }
}
