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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.MissingServletRequestSessionAttributeException;

/**
 * @author sudhir.ravindramohan
 * @since 1.0
 */
abstract class AbstractChainingSessionAttributeResolver implements SessionAttributeResolver {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final SessionAttributeResolver nextInChain;
    protected static final Object PASSING_THE_BUCK_AROUND = new Object() {
        @Override
        public String toString() {
            return "PASSING_THE_BUCK_AROUND";
        }
    };

    protected AbstractChainingSessionAttributeResolver(SessionAttributeResolver nextInChain) {
        this.nextInChain = nextInChain;
    }


    @Override
    public Object resolveSessionAttribute(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
        LOGGER.trace("Passing on SessionHandler and SessionAttributeParameter to resolveSessionAttributeInternal");
        Object resolution = resolveSessionAttributeInternal(handler, parameter);
        LOGGER.trace("resolveSessionAttributeInternal returned: '{}'", resolution);
        Assert.notNull(resolution, String.format("Not expecting a null resolution, if unable to resolve, implementations should return: '%s'", PASSING_THE_BUCK_AROUND));
        if(resolution == PASSING_THE_BUCK_AROUND) {
            LOGGER.trace("The buck has been passed, calling next resolver in the chain: '{}'", nextInChain);
            Assert.notNull(nextInChain, "Session Attribute is unresolved and next in chain is null, verify composition of the session attribute resolved chain.");
            //noinspection ConstantConditions
            return nextInChain.resolveSessionAttribute(handler, parameter);
        } else {
            LOGGER.trace("Session Attribute has been resolved, returning resolution: '{}'", resolution);
            return resolution;
        }
    }

    /**
     * Implementations should do a best case effort to resolve
     * the session attribute. If resolved the resolution should be
     * returned. Or should return {@code PASSING_THE_BUCK_AROUND}
     * @param handler SessionHandler
     * @param parameter SessionAttributeParameter
     * @return Object - Resolved Session Attribute or Null when unresolved.
     * @throws org.springframework.web.bind.MissingServletRequestSessionAttributeException
     */
    protected abstract Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException;

    protected final Object passTheBuck() {
        return PASSING_THE_BUCK_AROUND;
    }
}
