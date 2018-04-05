package com.manojbehera.tsr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manojbehera.tsr.data.Server;

@RestController
public class ServerCapacityControl {
	
	@Autowired
	private Server server;
	
	@RequestMapping(value="/capacity")
	public Server getServerCapacity() {
		return server;
	}
	
	@RequestMapping(value="/use/{gb}")
	public Server increaseUsedSpace(@PathVariable Integer gb) {
		server.setUsedSpace(server.getUsedSpace() + gb);
		return server;
	}
	
	@RequestMapping(value="/freespace")
	public Server resetUsedSpace() {
		server.setUsedSpace(0);
		return server;
	}
	
	

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

}
