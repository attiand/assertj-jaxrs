package se.uhr.assertj.jaxrs.ext;

import java.io.InputStream;
import java.util.ServiceLoader;

public class EntityExtensionServiceLoader {

	private EntityExtensionServiceLoader() {

	}

	public static <U> U load(EntityType<U> type, InputStream is) {
		@SuppressWarnings("unchecked")
		EntityExtension<U> ext = (EntityExtension<U>) ServiceLoader.load(EntityExtension.class)
				.stream()
				.map(p -> p.get())
				.filter(e -> e.type().equals(type.type()))
				.filter(e -> e.id().equals(type.id()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("Can't find requested extension for type: " + type.type()));

		return ext.create(is);
	}
}
