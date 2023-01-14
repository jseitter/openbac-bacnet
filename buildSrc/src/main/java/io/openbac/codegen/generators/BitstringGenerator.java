package io.openbac.codegen.generators;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

/**
 * @author TBreckle
 * @author JSeitter
 */
public class BitstringGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(BitstringGenerator.class);

	public static String VERSION = "0.1";

	public static void doGenerate(File xmlSource, File outFolder) throws JDOMException, IOException {
		BitstringGenerator.processXmlFileToClassFiles(xmlSource, outFolder);
	}

	public static void processXmlFileToClassFiles(File xmlFilename, File outFolder) throws JDOMException, IOException {

		outFolder.mkdirs();
		final SAXBuilder sax = new SAXBuilder();
		final Document doc = sax.build(xmlFilename);

		final XPathFactory xpfac = XPathFactory.instance();
		final XPathExpression<Element> expressionClasses = xpfac.compile("//bitStringClass", Filters.element());
		final List<Element> bitstringClasses = expressionClasses.evaluate(doc);
		for (Element bitstringClass : bitstringClasses) {

			if (bitstringClass.hasAttributes()) {

				Map<String, Integer> bitstringMap = new HashMap<>();
				String name = bitstringClass.getAttributeValue("name");
				// String classFilename = new File(path, name + ".java").getAbsolutePath();
				LOG.info("processing " + name);

				if (name != null) {

					final XPathExpression<Element> expressionEntries = xpfac
							.compile("//bitStringClass[@name='" + name + "']/bitString", Filters.element());
					final List<Element> entries = expressionEntries.evaluate(doc);

					for (Element entry : entries) {

						if (bitstringClass.hasAttributes()) {

							final String entryName = entry.getAttributeValue("name");
							final Integer entryValue = Integer.parseInt(entry.getAttributeValue("bitNr"));

							if (entryName != null) {
								bitstringMap.put(entryName, entryValue);
								LOG.info("\tcreate entry: " + entryName + " with index " + entryValue);
							}

						}

					}

				}

				LOG.info("\tgenerate class");
				final TypeSpec clazz = BitstringGenerator.createBitStringClass(name, bitstringMap);

				LOG.info("\twrite class to file");
				JavaFile javaFile = JavaFile.builder("io.openbac.bacnet.type.bitstring", clazz).build();
				javaFile.writeTo(outFolder);

//				Writer writer = null;
//				try {
//					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFilename), "utf-8"));
//					writer.write(clazz);
//					LOG.info("\tdone");
//				} catch (final IOException ex) {
//					throw ex;
//				} finally {
//					if (writer != null) {
//						try {
//							writer.close();
//						} catch (final IOException ex) {
//						}
//					}
//				}
			}

		}

	}

	public static TypeSpec createBitStringClass(String className, Map<String, Integer> states) {


		
		// create the class
		Builder classBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);

		// create the Type of the just generated class for self Reference purpos
		ClassName genType = ClassName.get("io.openbac.bacnet.type.bitstring", className);
		
		// let it inherit BACnetEnumerated
		ClassName bacnetBitString = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetBitString");
		classBuilder.superclass(bacnetBitString);

		// create int constructor
		MethodSpec ctr = MethodSpec.constructorBuilder().addParameter(TypeName.LONG, "bits").addStatement("super(bits)")
				.addModifiers(Modifier.PUBLIC).build();
		classBuilder.addMethod(ctr);
		
		// create Enum constructor
		MethodSpec ctrEnum = MethodSpec.constructorBuilder().addParameter(bacnetBitString, "bitstring").addStatement("super(bitstring)")
				.addModifiers(Modifier.PUBLIC).build();
		classBuilder.addMethod(ctrEnum);

		// iterate the enums
		for (final Map.Entry<String, Integer> entry : states.entrySet()) {
			// add a constant with the bitstring value
			classBuilder
					.addField(
							FieldSpec
									.builder(TypeName.INT, entry.getKey().toUpperCase(), Modifier.PUBLIC,
											Modifier.STATIC, Modifier.FINAL)
									.initializer(entry.getValue().toString()).build());
		}

		return classBuilder.build();
	}

}
