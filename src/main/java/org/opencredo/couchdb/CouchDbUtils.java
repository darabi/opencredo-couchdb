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

package org.opencredo.couchdb;

/**
 * Utility class with operations to manipulate CouchDB URLS
 *
 * @author Tareq Abedrabbo (tareq.abedrabbo@opencredo.com)
 * @since 13/01/2011
 */
public class CouchDbUtils {

    private CouchDbUtils() {
    }

    /**
     * Adds and id variable to a URL
     * @param url the URL to modify
     * @return the modified URL
     */
    public static String addId(String url) {
        return ensureTrailingSlash(url) + "{id}";
    }

    /**
     * Ensures that a URL ends with a slash.
     * @param url the URL to modify
     * @return the modified URL
     */
    private static String ensureTrailingSlash(String url) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        return url;
    }

}