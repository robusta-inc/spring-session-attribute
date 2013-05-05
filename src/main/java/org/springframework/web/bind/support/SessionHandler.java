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
package org.springframework.web.bind.support;

import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author sudhir.ravindramohan
 * @since 1.0
 */
class SessionHandler {
    private final HttpSession session;
    protected SessionHandler(NativeWebRequest nativeWebRequest) {
        this.session = nativeWebRequest.getNativeRequest(HttpServletRequest.class).getSession();
    }

    public Object getAttributeIfDefined(String attributeName) {
        return session.getAttribute(attributeName);
    }

    public Object addToSession(String attributeName, Object attributeValue) {
        session.setAttribute(attributeName, attributeValue);
        return attributeValue;
    }
}
