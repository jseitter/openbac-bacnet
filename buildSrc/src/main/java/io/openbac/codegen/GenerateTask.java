package io.openbac.codegen;

import java.io.File;
import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.jdom2.JDOMException;

import io.openbac.codegen.generators.EnumeratedGenerator;
import io.openbac.codegen.generators.ObjectGenerator;

public abstract class GenerateTask extends DefaultTask {


	@Input
    abstract Property<File> getGeneratorOutput();
    
	public GenerateTask() {
		getGeneratorOutput().convention(new File(this.getProject().getBuildDir(),"generated/java/"));
	}
	
	@TaskAction
    public void runGenerator() throws JDOMException, IOException {
		System.out.println("Yeahh running the generator");
		
		EnumeratedGenerator.doGenerate(new File(this.getProject().getProjectDir(),"src/model/enums.xml"), getGeneratorOutput().get());
		ObjectGenerator.doGenerate(new File(this.getProject().getProjectDir(),"src/model/objects.xml"), getGeneratorOutput().get());
	
	}
	
	
	
}
