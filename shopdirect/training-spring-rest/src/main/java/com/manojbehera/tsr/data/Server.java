package com.manojbehera.tsr.data;

import org.springframework.stereotype.Component;

@Component
public class Server {
	
	private int totalSpace = 60;
	private int usedSpace;
	
	public int getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(int totalSpace) {
		this.totalSpace = totalSpace;
	}
	public int getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(int usedSpace) {
		this.usedSpace = usedSpace;
	}
	
	

}
