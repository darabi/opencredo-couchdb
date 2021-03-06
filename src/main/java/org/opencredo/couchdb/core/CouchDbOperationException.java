/*
 * Copyright 2011 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencredo.couchdb.core;

/**
 * An exception thrown when an error occurs while performing a CouchDB operation.
 * @author Tareq Abedrabbo
 * @since 06/02/2011
 */
@SuppressWarnings("serial")
public class CouchDbOperationException extends RuntimeException {

    /**
     * Creates an instance of CouchDbOperationException.
     * @param message an error message
     * @param cause the cause of the exception
     */
    public CouchDbOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
