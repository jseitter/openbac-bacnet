package io.openbac.codegen.generators;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

public class ObjectGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(EnumeratedGenerator.class);

	
	public static void doGenerate(File xmlSource, File outputPackage) throws JDOMException, IOException {
		ObjectGenerator.processXmlFileToClassFiles(xmlSource, outputPackage);
	}

	/**
	 * Primitive Object Types
	 */
	private static ClassName nullType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetNull");
	private static ClassName boolType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetBoolean");
	private static ClassName unsignedType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetUnsignedInteger");
	private static ClassName signedType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetSignedInteger");
	private static ClassName bitStringType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetBitString");
	private static ClassName octetStringType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetOctetString");
	private static ClassName characterStringType = ClassName.get("io.openbac.bacnet.type.primitive",
			"BACnetCharacterString");
	private static ClassName enumeratedType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetEnumerated");
	private static ClassName realType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetReal");
	private static ClassName doubleType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetDouble");
	private static ClassName dateType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetDate");
	private static ClassName timeType = ClassName.get("io.openbac.bacnet.type.primitive", "BACnetTime");
	private static ClassName objIdentifierType = ClassName.get("io.openbac.bacnet.type.primitive",
			"BACnetObjectIdentifier");

	private static ClassName bacnetObject = ClassName.get("io.openbac.bacnet.object",
			"BACnetObject");
	
	private static void processXmlFileToClassFiles(File xmlSource, File outputPackage)
			throws JDOMException, IOException {
		outputPackage.mkdirs();
		final SAXBuilder sax = new SAXBuilder();
		final Document doc = sax.build(xmlSource);

		final XPathFactory xpfac = XPathFactory.instance();
		final XPathExpression<Element> expressionClasses = xpfac.compile("//objectClass", Filters.element());
		final List<Element> objectClasses = expressionClasses.evaluate(doc);

		for (Element e : objectClasses) {

			final String name = e.getAttributeValue("name");
			LOG.info("processing " + name);

			if (!outputPackage.exists())
				outputPackage.mkdirs();

			
			Builder objectClassBuilder = TypeSpec.classBuilder(e.getAttributeValue("name"))
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addSuperinterface(bacnetObject);
			

			List<Element> props = e.getChildren("property");
			for (Element p : props) {

				String datatype = p.getAttributeValue("datatype");
				ClassName fieldType = null;
				if (datatype.equals("BACnetBoolean"))
					fieldType = boolType;
				if (datatype.equals("BACnetNull"))
					fieldType = nullType;
				if (datatype.equals("BACnetUnsignedInteger"))
					fieldType = unsignedType;
				if (datatype.equals("BACnetSignedInteger"))
					fieldType = signedType;
				if (datatype.equals("BACnetBitString"))
					fieldType = bitStringType;
				if (datatype.equals("BACnetOctetString"))
					fieldType = octetStringType;
				if (datatype.equals("BACnetCharacterString"))
					fieldType = characterStringType;
				if (datatype.equals("BACnetEnumerated"))
					fieldType = enumeratedType;
				if (datatype.equals("BACnetReal"))
					fieldType = realType;
				if (datatype.equals("BACnetDouble"))
					fieldType = doubleType;
				if (datatype.equals("BACnetDate"))
					fieldType = dateType;
				if (datatype.equals("BACnetTime"))
					fieldType = timeType;
				if (datatype.equals("BACnetObjectIdentifier"))
					fieldType = objIdentifierType;
				if (fieldType != null) {
					FieldSpec spec =FieldSpec.builder(fieldType, p.getAttributeValue("name"), Modifier.PRIVATE).build();
					objectClassBuilder.addField(spec);
					addGetterAndSetter(spec, objectClassBuilder);
				} else {
					LOG.error("datatype not found !!! SKIPPING IT");
				}
			}

			TypeSpec objectClass = objectClassBuilder.build();
			JavaFile file = JavaFile.builder("io.openbac.bacnet.object", objectClass).build();

			file.writeTo(outputPackage);

		}

		
	}
	public static void addGetterAndSetter(FieldSpec fieldSpec, Builder classBuilder) {
		addGetter(fieldSpec, classBuilder);
		addSetter(fieldSpec, classBuilder);
	}


	private static void addSetter(FieldSpec fieldSpec, Builder classBuilder) {
		String setterName  = "set"+capitalizeFirstLetter(fieldSpec.name);
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(setterName).addModifiers(Modifier.PUBLIC);
		methodBuilder.addParameter(fieldSpec.type, fieldSpec.name);
		methodBuilder.addStatement("this."+fieldSpec.name+"="+fieldSpec.name);
		classBuilder.addMethod(methodBuilder.build());	
	}


	public static void addGetter(FieldSpec fieldSpec, Builder classBuilder) {
		String getterName  = "get"+capitalizeFirstLetter(fieldSpec.name);
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(getterName).returns(fieldSpec.type).addModifiers(Modifier.PUBLIC);
		methodBuilder.addStatement("return this."+fieldSpec.name);
		classBuilder.addMethod(methodBuilder.build());	
	}



	private static String capitalizeFirstLetter(final String str) {
		final String s = str.substring(0, 1).toUpperCase() + str.substring(1);
		return s;
	}
}
