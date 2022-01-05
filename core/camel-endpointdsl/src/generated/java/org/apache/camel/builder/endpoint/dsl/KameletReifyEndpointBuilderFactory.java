/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.builder.endpoint.dsl;

import javax.annotation.Generated;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.AbstractEndpointBuilder;
import org.apache.camel.spi.ExceptionHandler;

/**
 * To call Kamelets (indirectly)
 * 
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.EndpointDslMojo")
public interface KameletReifyEndpointBuilderFactory {


    /**
     * Builder for endpoint consumers for the Kamelet Reify component.
     */
    public interface KameletReifyEndpointConsumerBuilder
            extends
                EndpointConsumerBuilder {
        default AdvancedKameletReifyEndpointConsumerBuilder advanced() {
            return (AdvancedKameletReifyEndpointConsumerBuilder) this;
        }
        /**
         * Allows for bridging the consumer to the Camel routing Error Handler,
         * which mean any exceptions occurred while the consumer is trying to
         * pickup incoming messages, or the likes, will now be processed as a
         * message and handled by the routing Error Handler. By default the
         * consumer will use the org.apache.camel.spi.ExceptionHandler to deal
         * with exceptions, that will be logged at WARN or ERROR level and
         * ignored.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: consumer
         * 
         * @param bridgeErrorHandler the value to set
         * @return the dsl builder
         */
        default KameletReifyEndpointConsumerBuilder bridgeErrorHandler(
                boolean bridgeErrorHandler) {
            doSetProperty("bridgeErrorHandler", bridgeErrorHandler);
            return this;
        }
        /**
         * Allows for bridging the consumer to the Camel routing Error Handler,
         * which mean any exceptions occurred while the consumer is trying to
         * pickup incoming messages, or the likes, will now be processed as a
         * message and handled by the routing Error Handler. By default the
         * consumer will use the org.apache.camel.spi.ExceptionHandler to deal
         * with exceptions, that will be logged at WARN or ERROR level and
         * ignored.
         * 
         * The option will be converted to a &lt;code&gt;boolean&lt;/code&gt;
         * type.
         * 
         * Default: false
         * Group: consumer
         * 
         * @param bridgeErrorHandler the value to set
         * @return the dsl builder
         */
        default KameletReifyEndpointConsumerBuilder bridgeErrorHandler(
                String bridgeErrorHandler) {
            doSetProperty("bridgeErrorHandler", bridgeErrorHandler);
            return this;
        }
    }

    /**
     * Advanced builder for endpoint consumers for the Kamelet Reify component.
     */
    public interface AdvancedKameletReifyEndpointConsumerBuilder
            extends
                EndpointConsumerBuilder {
        default KameletReifyEndpointConsumerBuilder basic() {
            return (KameletReifyEndpointConsumerBuilder) this;
        }
        /**
         * To let the consumer use a custom ExceptionHandler. Notice if the
         * option bridgeErrorHandler is enabled then this option is not in use.
         * By default the consumer will deal with exceptions, that will be
         * logged at WARN or ERROR level and ignored.
         * 
         * The option is a:
         * &lt;code&gt;org.apache.camel.spi.ExceptionHandler&lt;/code&gt; type.
         * 
         * Group: consumer (advanced)
         * 
         * @param exceptionHandler the value to set
         * @return the dsl builder
         */
        default AdvancedKameletReifyEndpointConsumerBuilder exceptionHandler(
                ExceptionHandler exceptionHandler) {
            doSetProperty("exceptionHandler", exceptionHandler);
            return this;
        }
        /**
         * To let the consumer use a custom ExceptionHandler. Notice if the
         * option bridgeErrorHandler is enabled then this option is not in use.
         * By default the consumer will deal with exceptions, that will be
         * logged at WARN or ERROR level and ignored.
         * 
         * The option will be converted to a
         * &lt;code&gt;org.apache.camel.spi.ExceptionHandler&lt;/code&gt; type.
         * 
         * Group: consumer (advanced)
         * 
         * @param exceptionHandler the value to set
         * @return the dsl builder
         */
        default AdvancedKameletReifyEndpointConsumerBuilder exceptionHandler(
                String exceptionHandler) {
            doSetProperty("exceptionHandler", exceptionHandler);
            return this;
        }
        /**
         * Sets the exchange pattern when the consumer creates an exchange.
         * 
         * The option is a:
         * &lt;code&gt;org.apache.camel.ExchangePattern&lt;/code&gt; type.
         * 
         * Group: consumer (advanced)
         * 
         * @param exchangePattern the value to set
         * @return the dsl builder
         */
        default AdvancedKameletReifyEndpointConsumerBuilder exchangePattern(
                ExchangePattern exchangePattern) {
            doSetProperty("exchangePattern", exchangePattern);
            return this;
        }
        /**
         * Sets the exchange pattern when the consumer creates an exchange.
         * 
         * The option will be converted to a
         * &lt;code&gt;org.apache.camel.ExchangePattern&lt;/code&gt; type.
         * 
         * Group: consumer (advanced)
         * 
         * @param exchangePattern the value to set
         * @return the dsl builder
         */
        default AdvancedKameletReifyEndpointConsumerBuilder exchangePattern(
                String exchangePattern) {
            doSetProperty("exchangePattern", exchangePattern);
            return this;
        }
    }

    /**
     * Builder for endpoint producers for the Kamelet Reify component.
     */
    public interface KameletReifyEndpointProducerBuilder
            extends
                EndpointProducerBuilder {
        default AdvancedKameletReifyEndpointProducerBuilder advanced() {
            return (AdvancedKameletReifyEndpointProducerBuilder) this;
        }
        /**
         * Whether the producer should be started lazy (on the first message).
         * By starting lazy you can use this to allow CamelContext and routes to
         * startup in situations where a producer may otherwise fail during
         * starting and cause the route to fail being started. By deferring this
         * startup to be lazy then the startup failure can be handled during
         * routing messages via Camel's routing error handlers. Beware that when
         * the first message is processed then creating and starting the
         * producer may take a little time and prolong the total processing time
         * of the processing.
         * 
         * The option is a: &lt;code&gt;boolean&lt;/code&gt; type.
         * 
         * Default: false
         * Group: producer
         * 
         * @param lazyStartProducer the value to set
         * @return the dsl builder
         */
        default KameletReifyEndpointProducerBuilder lazyStartProducer(
                boolean lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
        /**
         * Whether the producer should be started lazy (on the first message).
         * By starting lazy you can use this to allow CamelContext and routes to
         * startup in situations where a producer may otherwise fail during
         * starting and cause the route to fail being started. By deferring this
         * startup to be lazy then the startup failure can be handled during
         * routing messages via Camel's routing error handlers. Beware that when
         * the first message is processed then creating and starting the
         * producer may take a little time and prolong the total processing time
         * of the processing.
         * 
         * The option will be converted to a &lt;code&gt;boolean&lt;/code&gt;
         * type.
         * 
         * Default: false
         * Group: producer
         * 
         * @param lazyStartProducer the value to set
         * @return the dsl builder
         */
        default KameletReifyEndpointProducerBuilder lazyStartProducer(
                String lazyStartProducer) {
            doSetProperty("lazyStartProducer", lazyStartProducer);
            return this;
        }
    }

    /**
     * Advanced builder for endpoint producers for the Kamelet Reify component.
     */
    public interface AdvancedKameletReifyEndpointProducerBuilder
            extends
                EndpointProducerBuilder {
        default KameletReifyEndpointProducerBuilder basic() {
            return (KameletReifyEndpointProducerBuilder) this;
        }
    }

    /**
     * Builder for endpoint for the Kamelet Reify component.
     */
    public interface KameletReifyEndpointBuilder
            extends
                KameletReifyEndpointConsumerBuilder,
                KameletReifyEndpointProducerBuilder {
        default AdvancedKameletReifyEndpointBuilder advanced() {
            return (AdvancedKameletReifyEndpointBuilder) this;
        }
    }

    /**
     * Advanced builder for endpoint for the Kamelet Reify component.
     */
    public interface AdvancedKameletReifyEndpointBuilder
            extends
                AdvancedKameletReifyEndpointConsumerBuilder,
                AdvancedKameletReifyEndpointProducerBuilder {
        default KameletReifyEndpointBuilder basic() {
            return (KameletReifyEndpointBuilder) this;
        }
    }

    public interface KameletReifyBuilders {
        /**
         * Kamelet Reify (camel-kamelet-reify)
         * To call Kamelets (indirectly)
         * 
         * Category: core
         * Since: 3.6
         * Maven coordinates: org.apache.camel:camel-kamelet-reify
         * 
         * Syntax: <code>kamelet-reify:delegateUri</code>
         * 
         * Path parameter: delegateUri (required)
         * The delegated uri
         * 
         * @param path delegateUri
         * @return the dsl builder
         */
        @Deprecated
        default KameletReifyEndpointBuilder kameletReify(String path) {
            return KameletReifyEndpointBuilderFactory.endpointBuilder("kamelet-reify", path);
        }
        /**
         * Kamelet Reify (camel-kamelet-reify)
         * To call Kamelets (indirectly)
         * 
         * Category: core
         * Since: 3.6
         * Maven coordinates: org.apache.camel:camel-kamelet-reify
         * 
         * Syntax: <code>kamelet-reify:delegateUri</code>
         * 
         * Path parameter: delegateUri (required)
         * The delegated uri
         * 
         * @param componentName to use a custom component name for the endpoint
         * instead of the default name
         * @param path delegateUri
         * @return the dsl builder
         */
        @Deprecated
        default KameletReifyEndpointBuilder kameletReify(
                String componentName,
                String path) {
            return KameletReifyEndpointBuilderFactory.endpointBuilder(componentName, path);
        }
    }
    @Deprecated
    static KameletReifyEndpointBuilder endpointBuilder(
            String componentName,
            String path) {
        class KameletReifyEndpointBuilderImpl extends AbstractEndpointBuilder implements KameletReifyEndpointBuilder, AdvancedKameletReifyEndpointBuilder {
            public KameletReifyEndpointBuilderImpl(String path) {
                super(componentName, path);
            }
        }
        return new KameletReifyEndpointBuilderImpl(path);
    }
}