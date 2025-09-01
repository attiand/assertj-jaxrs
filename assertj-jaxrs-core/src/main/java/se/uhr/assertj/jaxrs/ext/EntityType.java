package se.uhr.assertj.jaxrs.ext;

public class EntityType<T> {

	private final String id;

	private final Class<T> type;

	public EntityType(String id, Class<T> type) {
		this.id = id;
		this.type = type;
	}

	public EntityType(Class<T> type) {
		this(type.getCanonicalName(), type);
	}

	public String id() {
		return id;
	}

	public Class<T> type() {
		return type;
	}
}
