package io.openbac.codegen.generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.Element;
import org.jdom2.Document;

/**
 * @author TBreckle
 * @author JSeitter
 */
public class EnumeratedGenerator {

	public static String VERSION = "0.1";

	public static void doGenerate(File xmlSource, File outputPackage) throws JDOMException, IOException {
		EnumeratedGenerator.processXmlFileToClassFiles(xmlSource, outputPackage);
	}

	public static void processXmlFileToClassFiles(File xmlFilename, File path) throws JDOMException, IOException {

		path.mkdirs();
		final SAXBuilder sax = new SAXBuilder();
		final Document doc = sax.build(xmlFilename);

		final XPathFactory xpfac = XPathFactory.instance();
		final XPathExpression<Element> expressionClasses = xpfac.compile("//enumeratedClass", Filters.element());
		final List<Element> enumeratedClasses = expressionClasses.evaluate(doc);
		for (Element enumeratedClass : enumeratedClasses) {

			if (enumeratedClass.hasAttributes()) {

				final Map<String, Integer> enumerationMap = new HashMap<>();
				final String name = enumeratedClass.getAttributeValue("name");
				final String classFilename = new File(path, name + ".java").getAbsolutePath();
				System.out.println("processing " + name + " to " + classFilename);

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
								System.out.println("\tcreate entry: " + entryName + " with index " + entryValue);
							}

						}

					}

				}

				System.out.println("\tgenerate class");
				final String clazz = EnumeratedGenerator.createEnumeratedClass(name, enumerationMap);

				System.out.println("\twrite class to file");
				Writer writer = null;
				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFilename), "utf-8"));
					writer.write(clazz);
					System.out.println("\tdone");
				} catch (final IOException ex) {
					throw ex;
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (final IOException ex) {
						}
					}
				}
			}

		}

	}

	public static String createEnumeratedClass(String enumeratedName, Map<String, Integer> states) {

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
