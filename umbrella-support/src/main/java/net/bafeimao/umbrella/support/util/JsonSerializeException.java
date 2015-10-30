package net.bafeimao.umbrella.support.util;

public class JsonSerializeException extends JsonProcessException {
	private static final long serialVersionUID = 3837584243921004990L;
	
	public JsonSerializeException() {}
	
	public JsonSerializeException(String message) {
		super(message);
	}
	
	public JsonSerializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
