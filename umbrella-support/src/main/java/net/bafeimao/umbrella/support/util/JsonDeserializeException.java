package net.bafeimao.umbrella.support.util;

public class JsonDeserializeException extends JsonProcessException {
	private static final long serialVersionUID = -8497185424604801275L;
	
	public JsonDeserializeException() {}
	
	public JsonDeserializeException(String message) {
		super(message);
	}
	
	public JsonDeserializeException(String message, Throwable cause) {
		super(message, cause);
	}
}
