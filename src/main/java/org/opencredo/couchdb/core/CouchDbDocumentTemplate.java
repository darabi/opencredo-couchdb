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

import org.opencredo.couchdb.CouchDbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * An implementation of CouchDbDocumentOperations that relies on RestOperations to communicate
 * with CouchDB
 * 
 * @author Tareq Abedrabbo
 * @since 31/01/2011
 */
public class CouchDbDocumentTemplate implements CouchDbDocumentOperations {

    protected transient final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestOperations restOperations = new RestTemplate();

    private String defaultDocumentUrl;


    /**
     * Creates an instance of CouchDbDocumentTemplate with no default database.
     */
    public CouchDbDocumentTemplate() {
        restOperations = new RestTemplate();
    }

    /**
     * Constructs an instance of CouchDbDocumentTemplate with a default database and a
     * custom RestOperations
     * @param defaultDatabaseUrl the default database to connect to
     * @param restOperations a custom RestOperations instance
     */
    public CouchDbDocumentTemplate(String defaultDatabaseUrl, RestOperations restOperations) {
        Assert.hasText(defaultDatabaseUrl, "databaseUrl must not be empty");
        Assert.notNull(restOperations, "restOperations cannot be null");
        this.restOperations = restOperations;
        defaultDocumentUrl = CouchDbUtils.addId(defaultDatabaseUrl);
    }

    /**
     * Creates an instance of CouchDbDocumentTemplate with a default database URL
     * @param defaultDatabaseUrl the default URL to communicate with
     */
    public CouchDbDocumentTemplate(String defaultDatabaseUrl) {
        this(defaultDatabaseUrl, new RestTemplate());
    }

    public Object readDocument(String id, Class<?> documentType) {
        Assert.state(defaultDocumentUrl != null, "defaultDatabaseUrl must be set to use this method");
        return restOperations.getForObject(defaultDocumentUrl, documentType, id);
    }

    public Object readDocument(URI uri, Class<?> documentType) {
        return restOperations.getForObject(uri, documentType);
    }

    public void writeDocument(String id, Object document) {
        Assert.state(defaultDocumentUrl != null, "defaultDatabaseUrl must be set to use this method");
        HttpEntity<?> httpEntity = createHttpEntity(document);
        restOperations.put(defaultDocumentUrl, httpEntity, id);
    }

    public void writeDocument(URI uri, Object document) {
        HttpEntity<?> httpEntity = createHttpEntity(document);
        restOperations.put(uri, httpEntity);
    }

    /** Sets RestOperations */
    public void setRestOperations(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    private HttpEntity<?> createHttpEntity(Object document) {

        if (document instanceof HttpEntity) {
            HttpEntity httpEntity = (HttpEntity) document;
            Assert.isTrue(httpEntity.getHeaders().getContentType().equals(MediaType.APPLICATION_JSON),
                    "HttpEntity payload with non application/json content type found.");
            return httpEntity;
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(document, httpHeaders);

        return httpEntity;
    }
}
