/**
 * Copyright 2017 shalabh.us
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sephire.gamebook.awsapi.infrastructure;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import io.vavr.control.Option;
import org.sephire.gamebook.awsapi.configuration.dagger.ApplicationComponent;
import org.sephire.gamebook.awsapi.configuration.dagger.DaggerApplicationComponent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * Base Lambda Handler. All Lambda functions should extend from this or a
 * subclass of this Base Handler. That way the functions may benefit from
 * Dependency Injection and Error handling.
 * </p>
 *
 * <p>
 * Implements a template pattern with the following steps:
 * <ul>
 * <li>init - To initialize the dependencies
 * <li>process - To Process the request. This is what a handleRequest() would
 * normally do
 * <li>destroy - To destroy/close resources if needed.
 * </ul>
 * </p>
 *
 * @author Shalabh Jaiswal
 */
public abstract class ApiGatewayRequestHandler<REQUEST, RESPONSE, COMMAND_HANDLER> implements RequestStreamHandler
{
    // dagger app component
    private ApplicationComponent injector;

    public ApiGatewayRequestHandler(ApplicationComponent injector) {
        this.injector = injector;
    }

    public ApiGatewayRequestHandler() {
    }

    /**
     * Functions must implement the processing handler.
     * They receive an input http request and must return an output.
     *
     * All ApiGateway boilerplate is handled by this parent class.
     *
     * @param request A fully parsed ApiGateway event with optional body entity parsed if json
     * @param context
     * @return
     */
    protected abstract ApiGatewayHttpResponse<RESPONSE> process(ApiGatewayHttpRequest<REQUEST> request, Context context);

    /**
     * Get a dependency injector factory for the application.
     * If the AWS Function follows the Function->DomainCommand model,
     * the subclass won't need this injector.
     *
     * @return
     */
    protected ApplicationComponent getInjector() {
        if (injector == null) {
            injector = DaggerApplicationComponent.builder().build();
        }
        return injector;
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        ApiGatewayHttpRequest<REQUEST> request = new ApiGatewayHttpRequest(inputStream, getInputClass());
        ApiGatewayHttpResponse<RESPONSE> response = process(request, context);
        response.sendTo(outputStream);
    }

    /**
     * To be implemented by subclasses if the function admits a body entity.
     * <p>
     * For the moment, this solution will have to do to get the
     * input entity while avoiding performance penalties by doing advanced
     * reflection fuckery to get the class of the parameterized type.
     * <p>
     * <p>
     * TODO: Think of a better way to handle this
     *
     * @return
     */
    protected Option<Class<REQUEST>> getInputClass() {
        return Option.none();
    }

    /**
     * To be implemented by subclasses if the function returns a body entity.
     * <p>
     * For the moment, this solution will have to do to get the
     * output entity while avoiding performance penalties by doing advanced
     * reflection fuckery to get the class of the parameterized type.
     * TODO: Think of a better way to handle this
     *
     * @return
     */
    protected Option<Class<RESPONSE>> getOutputClass() {
        return Option.none();
    }

    /**
     * Following DDD concepts, an AWS Function is just an adapter to
     * the application's domain commands. This base class assumes that
     * each AWS Function is mapped to a domain command, and thus, it can
     * be automatically injected if the class is specified.
     *
     * @return
     */
    protected Option<Class<COMMAND_HANDLER>> getHandlerClass() {
        return Option.none();
    }

    /**
     * Gets an injected command handler.
     *
     * @return
     */
    protected Option<COMMAND_HANDLER> getCommandHandler() {
        return Option.none();
    }
}