/*

 */
package com.cleverfishsoftware.estreaming.jms.consumer;

import org.apache.commons.cli2.CommandLine;
import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.option.PropertyOption;

/**
 *
 * @author peter
 */
public class MessageReceiverCLI {

    public static void main(String[] args) throws OptionException {
        final DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
        final ArgumentBuilder abuilder = new ArgumentBuilder();
        final GroupBuilder gbuilder = new GroupBuilder();

        Option help
                = obuilder
                .withShortName("help")
                .withShortName("h")
                .withDescription("print this message")
                .create();
        Option projecthelp
                = obuilder
                .withShortName("projecthelp")
                .withShortName("p")
                .withDescription("print project help information")
                .create();
        Option version
                = obuilder
                .withShortName("version")
                .withDescription("print the version information and exit")
                .create();
        Option diagnostics
                = obuilder
                .withShortName("diagnostics")
                .withDescription("print information that might be helpful to diagnose or report problems.")
                .create();
        Option quiet
                = obuilder
                .withShortName("quiet")
                .withShortName("q")
                .withDescription("be extra quiet")
                .create();
        Option verbose
                = obuilder
                .withShortName("verbose")
                .withShortName("v")
                .withDescription("be extra verbose")
                .create();
        Option debug
                = obuilder
                .withShortName("debug")
                .withShortName("d")
                .withDescription("print debugging information")
                .create();
        Option emacs
                = obuilder
                .withShortName("emacs")
                .withShortName("e")
                .withDescription("produce logging information without adornments")
                .create();
        Option lib
                = obuilder
                .withShortName("lib")
                .withDescription("specifies a path to search for jars and classes")
                .withArgument(
                        abuilder
                        .withName("path")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option logfile
                = obuilder
                .withShortName("logfile")
                .withShortName("l")
                .withDescription("use given file for log")
                .withArgument(
                        abuilder
                        .withName("file")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option logger
                = obuilder
                .withShortName("logger")
                .withDescription("the class which is to perform logging")
                .withArgument(
                        abuilder
                        .withName("classname")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option listener
                = obuilder
                .withShortName("listener")
                .withDescription("add an instance of class as a project listener")
                .withArgument(
                        abuilder
                        .withName("classname")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option noinput
                = obuilder
                .withShortName("noinput")
                .withDescription("do not allow interactive input")
                .create();
        Option buildfile
                = obuilder
                .withShortName("buildfile")
                .withShortName("file")
                .withShortName("f")
                .withDescription("use given buildfile")
                .withArgument(
                        abuilder
                        .withName("file")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option property = new PropertyOption();
        Option propertyfile
                = obuilder
                .withShortName("propertyfile")
                .withDescription("load all properties from file with -D properties taking precedence")
                .withArgument(
                        abuilder
                        .withName("name")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option inputhandler
                = obuilder
                .withShortName("inputhandler")
                .withDescription("the class which will handle input requests")
                .withArgument(
                        abuilder
                        .withName("class")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option find
                = obuilder
                .withShortName("find")
                .withShortName("s")
                .withDescription("search for buildfile towards the root of the filesystem and use it")
                .withArgument(
                        abuilder
                        .withName("file")
                        .withMinimum(1)
                        .withMaximum(1)
                        .create())
                .create();
        Option targets = abuilder.withName("target").create();

        Group options
                = gbuilder
                .withName("options")
                .withOption(help)
                .withOption(projecthelp)
                .withOption(version)
                .withOption(diagnostics)
                .withOption(quiet)
                .withOption(verbose)
                .withOption(debug)
                .withOption(emacs)
                .withOption(lib)
                .withOption(logfile)
                .withOption(logger)
                .withOption(listener)
                .withOption(noinput)
                .withOption(buildfile)
                .withOption(property)
                .withOption(propertyfile)
                .withOption(inputhandler)
                .withOption(find)
                .withOption(targets)
                .create();
        Parser parser = new Parser();
        parser.setGroup(options);
        CommandLine cl = parser.parse(args);

        if (cl.hasOption(help)) {
            displayHelp();
            return;
        }
        if (cl.hasOption("-version")) {
            displayVersion();
        }
    }

    private static void displayHelp() {
        System.out.println("help!");
    }    

    private static void displayVersion() {
        System.out.println("0.0.0.1");
    }
}
