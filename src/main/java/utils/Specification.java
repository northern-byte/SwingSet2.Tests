package utils;

import exceptions.NoSuchPropertyException;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class Specification {
    private final static String SEPARATOR = ":";
    private final static String SPECIFICATIONS_PROPERTY_NAME = "specifications";

    private final String DEFAULT_SPECIFICATION = "default";
    private final LinkedList<String> specificationsLinkedList = new LinkedList<>();

    public Specification(String specification) {
        specificationsLinkedList.add(specification);
        specificationsLinkedList.add(DEFAULT_SPECIFICATION);
    }

    public Specification() {
        specificationsLinkedList.add(DEFAULT_SPECIFICATION);
    }

    public synchronized Prop get(String key) {
        Prop specProp = null;
        for (String spec : specificationsLinkedList) {
            try {
                specProp = ResourceManager.getSpecProp((String.format("%s.%s", spec, key)));
            } catch (NoSuchPropertyException ignored) {

            }
        }

        if (specProp == null) {
            throw new NoSuchPropertyException(String.format("Property %s was not found in specifications: %s ", key,
                    specificationsLinkedList.toString()));
        }

        return specProp;
    }

    public static Collection<String> getSpecifications() {
        String specificationsString = Platform.getConfigProp(SPECIFICATIONS_PROPERTY_NAME).asString();
        return Arrays.asList(specificationsString.split(SEPARATOR));
    }
}
