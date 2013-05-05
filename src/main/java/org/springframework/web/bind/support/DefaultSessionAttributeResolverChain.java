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

import org.springframework.web.bind.MissingServletRequestSessionAttributeException;

/**
 * A chain of responsibility in resolving the session attribute.
 * @author sudhir.ravindramohan
 * @since 1.0
 */
public final class DefaultSessionAttributeResolverChain implements SessionAttributeResolver {
    protected DefaultSessionAttributeResolverChain() {
        resolverChain = new AlwaysCreateSessionAttributeResolver(
                new SessionLookupSessionAttributeResolver(
                        new CreatableParameterSessionAttributeResolver(
                                new OptionalParameterSessionAttributeResolver(
                                        new MissingMandatorySessionAttributeResolver()))));

    }

    private final SessionAttributeResolver resolverChain;

    @Override
    public Object resolveSessionAttribute(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
        return resolverChain.resolveSessionAttribute(handler, parameter);
    }
}