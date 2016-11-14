package org.mirah.maven;

import org.apache.maven.plugin.CompilerMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

import java.util.List;
import java.util.ArrayList;

import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

import org.mirah.tool.Mirahc;

public abstract class AbstractMirahMojo extends CompilerMojo {
    /**
     * Project classpath.
     *
     */
    @Parameter(defaultValue="${project.compileClasspathElements}", required = true, readonly = true)
    protected List<String> classpathElements;
    /**
     * The source directories containing the sources to be compiled.
     *
     */
    @Parameter(defaultValue="${project.compileSourceRoots}" , required = true, readonly = true)
    protected List<String> compileSourceRoots;
    /**
     * Classes destination directory
     *
     */
    @Parameter(defaultValue="${project.build.outputDirectory}")
    protected String outputDirectory;
    /**
     * Classes source directory
     *
     */
    @Parameter(defaultValue="${basedir}/src/main/mirah}")
    protected String sourceDirectory;
    /**
     * Show log
     *
     * @parameter verbose, default false
     */
    @Parameter(defaultValue="false")
    protected boolean verbose;


    /**
     * The -encoding argument for the Mirahc compiler.
     *
     * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
     */
    private String encoding;

    public static void mojoCompile(List<String> arguments) throws MojoExecutionException {
        try {
            Mirahc mirahc = new Mirahc();
            int result = mirahc.compile(arguments.toArray(new String[arguments.size()]));
            if (result != 0) throw new MojoExecutionException("Compilation failed with arguments: " + arguments);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    protected List<String> getClassPathElements() {
        return classpathElements;
    }

    protected void executeMirahCompiler(String output, String sourceDirectory, boolean verbose, boolean bytecode) throws MojoExecutionException {
        File d = new File(output);
        if (!d.exists()) {
            d.mkdirs();
        }

        List<String> arguments = new ArrayList<String>();
        if (!bytecode) {
            arguments.add("-plugins");
            arguments.add("stub:" + output + "|+pl");
            arguments.add("--skip-compile");
        }
        if (verbose)
            arguments.add("-V");

        arguments.add("-d");
        arguments.add(output);

        /* do I really need this? */
        arguments.add("-cp");
        arguments.add(StringUtils.join(getClassPathElements().iterator(), File.pathSeparator));

        if (encoding != null) {
            arguments.add("-encoding");
            arguments.add(encoding);
            getLog().info("Use source encoding: " + encoding);
        }

        File file = new File(sourceDirectory);
        if (!file.exists()) {
            getLog().info("Source directory: " + file.getAbsolutePath() + " does not exists or not accessible. Skip mirahc.");
        } else {
            arguments.add(sourceDirectory);
            mojoCompile(arguments);
        }

    }
}
