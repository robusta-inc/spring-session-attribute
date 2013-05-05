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
 * @author sudhir.ravindramohan
 * @since 1.0
 */
abstract class AbstractChainingSessionAttributeResolver implements SessionAttributeResolver {
    private final SessionAttributeResolver nextInChain;

    protected AbstractChainingSessionAttributeResolver(SessionAttributeResolver nextInChain) {
        this.nextInChain = nextInChain;
    }


    @Override
    public Object resolveSessionAttribute(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
        Object resolution = resolveSessionAttributeInternal(handler, parameter);
        if(resolution == null) {
            Assert.notNull(nextInChain == null, "Session Attribute is unresolved and next in chain is null, verify composition of the session attribute resolved chain.");
            //noinspection ConstantConditions
            return nextInChain.resolveSessionAttribute(handler, parameter);
        } else {
            return resolution;
        }
    }

    /**
     * Implementations should do a best case effort to resolve
     * the session attribute. If resolved the resolution should be
     * returned. Or should return {@code null}
     * @param handler SessionHandler
     * @param parameter SessionAttributeParameter
     * @return Object - Resolved Session Attribute or Null when unresolved.
     * @throws org.springframework.web.bind.MissingServletRequestSessionAttributeException
     */
    protected abstract Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException;
}
