package org.sephire.gamebook.awsapi;

import me.ccampo.spring.aws.lambda.SpringRequestHandler;
import org.sephire.gamebook.awsapi.configuration.spring.ApplicationConfiguration;
import org.sephire.gamebook.core.application.shared.commands.Command;
import org.sephire.gamebook.core.application.shared.commands.CommandResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main entrypoint for Lambda functions, takes care of
 * initializing dependency injection and other spring functions.
 * Since this is a lambda function, the configuration should be as lightweight
 * and fast as possible to reduce execution time.
 */
public class MainHandler extends SpringRequestHandler<Command, CommandResult> {

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

    @Override
    public ApplicationContext getApplicationContext() {
        return context;
    }
}