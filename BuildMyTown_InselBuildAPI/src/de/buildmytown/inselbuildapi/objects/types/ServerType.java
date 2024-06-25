package de.buildmytown.inselbuildapi.objects.types;

public enum ServerType {
	
	IB_LOBBY("IB-Lobby", "127.0.0.1", 25565),
	IB_ISLAND("IB-Inseln", "127.0.0.1", 25565),
	IB_DUNGEONS("IB-Dungeon", "127.0.0.1", 25565);
	
	private final String name;
	private final String address;
	private final int port;
	
	private ServerType(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public int getPort() {
		return this.port;
	}

}
