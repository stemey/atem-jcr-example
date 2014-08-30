package org.atemsource.jcr.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.atemsource.atem.api.EntityTypeRepository;
import org.atemsource.atem.api.type.EntityType;
import org.atemsource.atem.impl.dynamic.DynamicEntity;
import org.atemsource.atem.utility.transform.api.SimpleTransformationContext;
import org.atemsource.atem.utility.transform.api.meta.DerivedType;
import org.springframework.stereotype.Component;

@Component
public class RepositoryHelper {

	@Inject
	private Repository repository;

	@Inject
	private EntityTypeRepository entityTypeRepository;

	public DynamicEntity get(String path) throws PathNotFoundException, RepositoryException {
		Session session = repository.login("default");
		try {
			Node node = session.getNode(path);
			EntityType<Node> entityType = entityTypeRepository
					.getEntityType(node);

			EntityType<Node> jsonType = entityTypeRepository
					.getEntityType("map::" + entityType.getCode());
			DerivedType<Node, Object> derivedType = (DerivedType<Node, Object>) jsonType
					.getMetaType()
					.getMetaAttribute(DerivedType.META_ATTRIBUTE_CODE)
					.getValue(jsonType);
			DynamicEntity value = (DynamicEntity) derivedType
					.getTransformation()
					.getAB()
					.convert(
							node,
							new SimpleTransformationContext(
									entityTypeRepository));

			// value.get("carousel").get("rows");

			return value;

		} finally {
			session.logout();
		}
	}
	
	public void loadRefs(DynamicEntity entity,String key, String typeCode) throws PathNotFoundException, RepositoryException {
		Session session = repository.login("default");
		try {
			EntityType<Node> itemType = entityTypeRepository
					.getEntityType("map::"+typeCode);
			DerivedType<Node,Object> itemDerivedType = (DerivedType<Node,Object>) itemType.getMetaType()
					.getMetaAttribute(DerivedType.META_ATTRIBUTE_CODE)
					.getValue(itemType);

			List<Object> itemUrls =new ArrayList<Object>();
			Collection<String> items=(Collection<String>) entity.get(key);
			for (String item:items) {
				Node itemNode = session.getNode(item);
				Map<String,Object> itemValue = (Map<String, Object>) itemDerivedType
						.getTransformation()
						.getAB()
						.convert(
								itemNode,
								new SimpleTransformationContext(
										entityTypeRepository));
				itemUrls.add(itemValue);
			}
			entity.put(key, itemUrls);

		} finally {
			session.logout();
		}
	}
}
