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

import org.springframework.util.Assert;
import org.springframework.web.bind.MissingServletRequestSessionAttributeException;

/**
 * A chain of responsibility in resolving the session attribute.
 * <h6>Default implementation</h6>
 * <ol>
 *     <li>Create a new instance in session when
 *     SessionAttribute.createNew is true</li>
 *     <li>Lookup an existing instance in session when such
 *     an instance exists</li>
 *     <li>When not found in session
 *          <ol>
 *              <li>SessionAttribute.createIfMissing is true
 *              creates a new instance in session</li>
 *              <li>SessionAttribute.required is false, then
 *              returns {@link WebArgumentResolver}.UNRESOLVED</li>
 *          </ol>
 *     </li>
 *     <li>If none of the resolution strategies work, throws
 *     {@link MissingServletRequestSessionAttributeException}</li>
 * </ol>
 * @author sudhir.ravindramohan
 * @since 1.0
 * @see AlwaysCreateSessionAttributeResolver
 * @see SessionLookupSessionAttributeResolver
 * @see CreatableParameterSessionAttributeResolver
 * @see OptionalParameterSessionAttributeResolver
 * @see MissingMandatorySessionAttributeResolver
 */
public class DefaultSessionAttributeResolverChain implements SessionAttributeResolver {
    protected DefaultSessionAttributeResolverChain() {
        this(new AlwaysCreateSessionAttributeResolver(
                new SessionLookupSessionAttributeResolver(
                        new CreatableParameterSessionAttributeResolver(
                                new OptionalParameterSessionAttributeResolver(
                                        new MissingMandatorySessionAttributeResolver())))));

    }

    protected final SessionAttributeResolver resolverChain;

    public DefaultSessionAttributeResolverChain(SessionAttributeResolver resolverChain) {
        Assert.notNull(resolverChain, "Require a valid SessionAttributeResolver for initialization");
        this.resolverChain = resolverChain;
    }

    @Override
    public Object resolveSessionAttribute(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
        return resolverChain.resolveSessionAttribute(handler, parameter);
    }
}
