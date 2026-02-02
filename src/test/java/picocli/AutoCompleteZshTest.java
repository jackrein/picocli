package picocli;

import org.junit.Test;

import java.io.IOException;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static picocli.CommandLine.*;

public class AutoCompleteZshTest {
    @Test
    public void testZshCompletionScript() throws IOException {
        String actual = AutoComplete.zsh("test-command", new CommandLine(new TestCommand()));
        System.out.println(actual);
        String expected = format(AutoCompleteTest.loadTextFromClasspath("/zsh_completion.zsh"), CommandLine.VERSION);
        assertEquals(expected, actual);
    }

    /**
     * Command definition that attempts to be exhaustive in the ways that change zsh auto-completion behavior
     */
    @Command(
        name = "test-command",
        mixinStandardHelpOptions = true,
        subcommands = { AutoComplete.GenerateCompletion.class, TestCommand.SubCommand1.class, TestCommand.SubCommand2.class }
    )
    private static class TestCommand {
        @Command(
            name = "sub-1",
            description = {
                "What is your name",
                "Bob?"
            },
            mixinStandardHelpOptions = true,
            subcommands = { SubCommand1.SubSubCommand1.class }
        )
        public static class SubCommand1 {
            @Command(
                name = "sub-sub-1",abbreviateSynopsis = true,
                mixinStandardHelpOptions = true
            )
            public static class SubSubCommand1 {

            }
        }

        @Command(
            name = "sub-2",
            mixinStandardHelpOptions = true
        )
        public static class SubCommand2 {

        }
    }
}

