package net.bafeimao.umbrella.support.util;

public abstract class JsonProcessException extends RuntimeException {
	private static final long serialVersionUID = -2245049475652136445L;
	
	public JsonProcessException() {}
	
	public JsonProcessException(String message) {
		super(message);
	}
	
	public JsonProcessException(String message, Throwable cause) {
		super(message, cause);
	}
}
