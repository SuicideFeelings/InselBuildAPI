package de.buildmytown.inselbuildapi.objects.types;

public enum ServerTask {
	
	test("STOP_ISLAND");
	
	
	private final String data;
	
	
	private ServerTask(final String data) {
		this.data = data;
	}
	
	public String getData() {
		return this.data;
	}
	
	

}
