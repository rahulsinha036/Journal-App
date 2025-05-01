package net.engineeringdisgest.journalApp.service;

import net.engineeringdisgest.journalApp.entity.UserEntry;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(UserEntry.builder().userName("Shayam").password("Shayam").build()),
                Arguments.of(UserEntry.builder().userName("Ram").password("").build())
        );
    }
}
