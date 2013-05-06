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

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * A web argument resolver that resolves method parameters annotated with
 * {@link SessionAttribute}.
 *
 * <p>If the MethodParameter is not annotated, this resolver does a no-op</p>
 * <p>When annotated, delegates to a strategy based resolution -
 * {@link SessionAttributeResolver}. To extend this default behavior
 * override initializeResolver</p>
 * @author sudhir.ravindramohan
 * @since 1.0
 * @see org.springframework.web.bind.annotation.SessionAttribute
 * @see SessionAttributeResolver
 * @see DefaultSessionAttributeResolverChain
 */
public class SessionAttributeWebArgumentResolver implements WebArgumentResolver {

    private final SessionAttributeResolver resolverChain = initializeResolver();

    protected DefaultSessionAttributeResolverChain initializeResolver() {
        return new DefaultSessionAttributeResolverChain();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if(methodParameter.hasParameterAnnotation(SessionAttribute.class)) {
            SessionAttributeParameter parameter = new SessionAttributeParameter(methodParameter);
            SessionHandler handler = new SessionHandler(webRequest);
            return resolverChain.resolveSessionAttribute(handler, parameter);
        }
        return WebArgumentResolver.UNRESOLVED;
    }
}
