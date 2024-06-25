package de.buildmytown.inselbuildapi.objects.types;

public enum PlayerTask {

	START_ISLAND("START_ISLAND"),
	STOP_ISLAND("STOP_ISLAND");
	
	
	private final String data;
	
	
	private PlayerTask(final String data) {
		this.data = data;
	}
	
	public String getData() {
		return this.data;
	}
	
}
