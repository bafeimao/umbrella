/*
 * Copyright 2002-2015 by bafeimao.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
