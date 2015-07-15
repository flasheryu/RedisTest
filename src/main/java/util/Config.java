package util;

import java.util.ArrayList;

public class Config {
	private final int numMsg=100000;
	private final int numByte=10;
	
	private final ArrayList<Integer> numMsgList=new ArrayList<Integer>(); 
	private final ArrayList<Integer> numByteList=new ArrayList<Integer>();
	
	public Config(){
//		numMsgList.add(0,1000000);
//		numMsgList.add(1,100000);
//		numMsgList.add(2,10000);
//		numMsgList.add(3,1000);
//		numMsgList.add(4,100);
//		numMsgList.add(5,10);
//		numMsgList.add(6,1);
		
		numMsgList.add(0,10000);
		numMsgList.add(1,1000);
		numMsgList.add(2,100);
		numMsgList.add(3,10);
		numMsgList.add(4,1);
		
//		numMsgList.add(0,1000);
//		numMsgList.add(1,100);
		
		numByteList.add(0,10);
		numByteList.add(1,100);
		numByteList.add(2,1000);
		
	}
	
	public int getNumMsg(){
		return this.numMsg;
	}
	
	public int getNumByte(){
		return this.numByte;
	}
	
	public ArrayList<Integer> getNumMsgList(){
		return this.numMsgList;
	}
	
	public int getNumMsgListSize(){
		return this.numMsgList.size();
	}	
	
	public ArrayList<Integer> getNumByteList(){
		return this.numByteList;
	}
	
	public int getNumByteListSize(){
		return this.numByteList.size();
	}
	
}
