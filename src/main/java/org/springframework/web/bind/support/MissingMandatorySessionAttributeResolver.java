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
* Created with IntelliJ IDEA.
* User: 605868845
* Date: 05/05/13
* Time: 15:37
* To change this template use File | Settings | File Templates.
*/
class MissingMandatorySessionAttributeResolver extends AbstractChainingSessionAttributeResolver {
    protected MissingMandatorySessionAttributeResolver() {
        super(null);
    }

    @Override
    protected Object resolveSessionAttributeInternal(SessionHandler handler, SessionAttributeParameter parameter) throws MissingServletRequestSessionAttributeException {
        LOGGER.error("All resolution strategies have failed, finally throwing exception against the container.");
        throw new MissingServletRequestSessionAttributeException(parameter.resolvedAttributeName());
    }
}
