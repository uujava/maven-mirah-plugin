package org.mirah.maven;

import org.apache.maven.plugin.CompilationFailureException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Compiles Mirah source files
 *
 */
@Mojo(name = "compile", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.COMPILE)
public class MirahCompilerMojo extends AbstractMirahMojo {
    public void execute() throws MojoExecutionException, CompilationFailureException {
        executeMirahCompiler(outputDirectory, sourceDirectory, verbose, true);
    }
}
