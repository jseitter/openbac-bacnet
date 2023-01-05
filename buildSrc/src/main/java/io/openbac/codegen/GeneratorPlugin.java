package io.openbac.codegen;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

public class GeneratorPlugin implements Plugin<Project> {

	public void apply(Project project) {

		
		
		// get all java source sets
		SourceSetContainer sourceSets = project.getExtensions().getByType(JavaPluginExtension.class)
				.getSourceSets();
		sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getJava().srcDir(new File(project.getBuildDir(),"generated/java"));
//		SourceSet s = sourceSets.maybeCreate("generated");
//		s.getJava().getSrcDirs().add(new File(project.getBuildDir(),"generated/java"));
//		sourceSets.add(s);
		project.getTasks().register("generate",GenerateTask.class);
		

	}

}
