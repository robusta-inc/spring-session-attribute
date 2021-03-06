/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.web.bind;

/**
 * {@link org.springframework.web.bind.ServletRequestBindingException} subclass that indicates a missing attribute.
 *
 * @author sudhir.ravindramohan
 * @since 1.0
 */
public class MissingServletRequestSessionAttributeException extends ServletRequestBindingException {
	private final String attributeName;

	/**
	 * Constructor for MissingServletRequestParameterException.
	 * @param attributeName the name of the missing parameter
	 */
	public MissingServletRequestSessionAttributeException(String attributeName) {
		super("");
		this.attributeName = attributeName;
	}


	@Override
	public String getMessage() {
		return "Required " + this.attributeName + " attribute '" + this.attributeName + "' is not present in session";
	}
}
