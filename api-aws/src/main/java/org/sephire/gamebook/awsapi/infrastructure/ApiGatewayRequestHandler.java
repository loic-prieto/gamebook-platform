/**
 * Copyright 2017 shalabh.us
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.sephire.gamebook.awsapi.infrastructure.ReflectionUtils.getParameterClass;

/**
 * <p>
 * Base Lambda Handler. All Lambda functions should extend from this or a
 * subclass of this Base Handler. That way the functions may benefit from
 * Dependency Injection and Error handling.
 * </p>
 * <p>
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
public abstract class ApiGatewayRequestHandler<REQUEST_ENTITY, RESPONSE_ENTITY>
		implements RequestStreamHandler {

	private static final int REQUEST_ENTITY_PARAMETER_POSITION = 0;
	private static final int COMMAND_HANDLER_PARAMETER_POSITION = 2;

	/**
	 * Functions must implement the processing handler.
	 * They receive an input http request and must return an output.
	 * <p>
	 * All ApiGateway boilerplate is handled by this parent class.
	 *
	 * @param request A fully parsed ApiGateway event with optional body entity parsed if json
	 * @param context an AWs Lambda request context
	 * @return a fully initialized ApiGatewayHttpResponse that will be serialized as output of the response.
	 */
	protected abstract ApiGatewayHttpResponse<RESPONSE_ENTITY> process(ApiGatewayHttpRequest<REQUEST_ENTITY> request, Context context);

	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		// If the request entity parameter is empty, no need to parse it
		Class<?> genericEntityClass = getParameterClass(this.getClass(), REQUEST_ENTITY_PARAMETER_POSITION);
		Option<Class<REQUEST_ENTITY>> entityClass =
				genericEntityClass.getName().equals(Void.class) ?
						Option.none() :
						Option.of(genericEntityClass).map(clazz -> (Class<REQUEST_ENTITY>) clazz);

		ApiGatewayHttpRequest<REQUEST_ENTITY> request = new ApiGatewayHttpRequest(inputStream, entityClass);
		ApiGatewayHttpResponse<RESPONSE_ENTITY> response = process(request, context);
		response.sendTo(outputStream);
	}

}