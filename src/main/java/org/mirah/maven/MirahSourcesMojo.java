package org.mirah.maven;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.util.List;

/**
 * Compiles Mirah source files
 *
 */
@Mojo(name = "generate-sources",   defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class MirahSourcesMojo extends AbstractMirahMojo {
    /**
     * Path to source
     */
    @Parameter(defaultValue="${project.build.directory}/generated-sources/mirah" , required = true, readonly = true)
    private String generatedSrc;

    public void execute() throws MojoExecutionException, CompilationFailureException {
        compileSourceRoots.add(generatedSrc);
        executeMirahCompiler(generatedSrc, outputDirectory, verbose, false);
    }
}
