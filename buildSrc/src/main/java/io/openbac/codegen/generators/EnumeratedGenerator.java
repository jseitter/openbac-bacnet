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
public class EnumeratedGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(EnumeratedGenerator.class);

	public static String VERSION = "0.1";

	public static void doGenerate(File xmlSource, File outFolder) throws JDOMException, IOException {
		EnumeratedGenerator.processXmlFileToClassFiles(xmlSource, outFolder);
	}

	public static void processXmlFileToClassFiles(File xmlFilename, File outFolder) throws JDOMException, IOException {

		outFolder.mkdirs();
		final SAXBuilder sax = new SAXBuilder();
		final Document doc = sax.build(xmlFilename);

		final XPathFactory xpfac = XPathFactory.instance();
		final XPathExpression<Element> expressionClasses = xpfac.compile("//enumeratedClass", Filters.element());
		final List<Element> enumeratedClasses = expressionClasses.evaluate(doc);
		for (Element enumeratedClass : enumeratedClasses) {

			if (enumeratedClass.hasAttributes()) {

				Map<String, Integer> enumerationMap = new HashMap<>();
				String name = enumeratedClass.getAttributeValue("name");
				// String classFilename = new File(path, name + ".java").getAbsolutePath();
				LOG.info("processing " + name);

				if (name != null) {

					final XPathExpression<Element> expressionEntries = xpfac
							.compile("//enumeratedClass[@name='" + name + "']/enumerationEntry", Filters.element());
					final List<Element> entries = expressionEntries.evaluate(doc);

					for (Element entry : entries) {

						if (enumeratedClass.hasAttributes()) {

							final String entryName = entry.getAttributeValue("name");
							final Integer entryValue = Integer.parseInt(entry.getAttributeValue("value"));

							if (entryName != null) {
								enumerationMap.put(entryName, entryValue);
								LOG.info("\tcreate entry: " + entryName + " with index " + entryValue);
							}

						}

					}

				}

				LOG.info("\tgenerate class");
				final TypeSpec clazz = EnumeratedGenerator.createEnumeratedClass(name, enumerationMap);

				LOG.info("\twrite class to file");
				JavaFile javaFile = JavaFile.builder("io.openbac.bacnet.type.enumerated", clazz).build();
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

	public static TypeSpec createEnumeratedClass(String enumeratedName, Map<String, Integer> states) {


		
		// create the class
		Builder classBuilder = TypeSpec.classBuilder(enumeratedName).addModifiers(Modifier.PUBLIC);

		// create the Type of the just generated class for self Reference purpos
		ClassName genType = ClassName.get("io.openbac.bacnet.type.enumerated", enumeratedName);
		
		// let it inherit BACnetEnumerated
		ClassName bacnetEnumerated = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetEnumerated");
		classBuilder.superclass(bacnetEnumerated);

		// create int constructor
		MethodSpec ctr = MethodSpec.constructorBuilder().addParameter(TypeName.INT, "id").addStatement("super(id)")
				.addModifiers(Modifier.PUBLIC).build();
		classBuilder.addMethod(ctr);
		
		// create Enum constructor
		MethodSpec ctrEnum = MethodSpec.constructorBuilder().addParameter(bacnetEnumerated, "enumerated").addStatement("super(enumerated)")
				.addModifiers(Modifier.PUBLIC).build();
		classBuilder.addMethod(ctrEnum);

		// iterate the enums
		for (final Map.Entry<String, Integer> entry : states.entrySet()) {
			// add a constant with the enum value
			classBuilder
					.addField(
							FieldSpec
									.builder(TypeName.INT, entry.getKey().toUpperCase(), Modifier.PUBLIC,
											Modifier.STATIC, Modifier.FINAL)
									.initializer(entry.getValue().toString()).build());

			// add another constant with the initialized enum object
			classBuilder.addField(FieldSpec
					.builder(genType, entry.getKey() + "Obj", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
					.initializer("new $T(" + entry.getValue().toString() + ")",genType).build());

		}

		return classBuilder.build();
	}

	public static String createEnumeratedClassOLD(String enumeratedName, Map<String, Integer> states) {

		final StringBuilder sb = new StringBuilder();
		sb.append("package io.openbac.bacnet.type.enumerated;").append(System.lineSeparator())
				.append(System.lineSeparator());
		sb.append("import io.openbac.bacnet.type.primitive.BACnetEnumerated;").append(System.lineSeparator())
				.append(System.lineSeparator());
		sb.append("/**").append(System.lineSeparator());
		sb.append(" * enumerated class for ").append(enumeratedName).append(System.lineSeparator());
		sb.append(" * @author EnumeratedGenerator v").append(VERSION).append(" (part of openbac)")
				.append(System.lineSeparator());
		sb.append(" */").append(System.lineSeparator());

		sb.append("public class ").append(enumeratedName).append(" extends BACnetEnumerated {")
				.append(System.lineSeparator()).append(System.lineSeparator());

		for (final Map.Entry<String, Integer> entry : states.entrySet()) {

			sb.append("\tpublic final static ").append(enumeratedName).append(" ").append(entry.getKey())
					.append(" = new ").append(enumeratedName).append("(").append(entry.getValue()).append(");");
			sb.append(System.lineSeparator());

		}

		sb.append(System.lineSeparator());
// Commented out copy Constructor
//		sb.append("\tpublic ").append(enumeratedName).append("(").append(enumeratedName).append(" value) {")
//				.append(System.lineSeparator());
//		sb.append("\t\tthis(value.intValue());").append(System.lineSeparator());
//		sb.append("\t}").append(System.lineSeparator()).append(System.lineSeparator());

		sb.append(System.lineSeparator());
		sb.append("\tpublic ").append(enumeratedName).append("(BACnetEnumerated value) {")
				.append(System.lineSeparator());
		sb.append("\t\tthis(value.intValue());").append(System.lineSeparator());
		sb.append("\t}").append(System.lineSeparator()).append(System.lineSeparator());

		sb.append(System.lineSeparator());
		sb.append("\tpublic ").append(enumeratedName).append("(Integer value) {").append(System.lineSeparator());
		sb.append("\t\tsuper(value);").append(System.lineSeparator());
		sb.append("\t}").append(System.lineSeparator()).append(System.lineSeparator());

		sb.append("\t@Override").append(System.lineSeparator());
		sb.append("\tpublic String toString() {").append(System.lineSeparator()).append(System.lineSeparator());
		sb.append("\t\tint type = intValue();").append(System.lineSeparator());

		for (final Map.Entry<String, Integer> entry : states.entrySet()) {

			sb.append("\t\tif (type == ").append(entry.getKey()).append(".intValue()) {")
					.append(System.lineSeparator());
			sb.append("\t\t\treturn \"").append(entry.getKey()).append("\";").append(System.lineSeparator());
			sb.append("\t\t}").append(System.lineSeparator());

		}

		sb.append("\t\treturn \"Unknown type: \" + type;").append(System.lineSeparator())
				.append(System.lineSeparator());
		sb.append("\t}").append(System.lineSeparator()).append(System.lineSeparator());
		sb.append("}").append(System.lineSeparator());

		return sb.toString();

	}

}
