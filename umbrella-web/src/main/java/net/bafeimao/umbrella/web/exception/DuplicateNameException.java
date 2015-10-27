package net.bafeimao.umbrella.web.exception;

public class DuplicateNameException extends Exception {
	private static final long serialVersionUID = 8668209900919154257L;

	public DuplicateNameException() {
		super("Duplicated Name.");
	}
}
