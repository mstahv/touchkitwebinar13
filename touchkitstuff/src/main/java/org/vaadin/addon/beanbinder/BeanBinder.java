package org.vaadin.addon.beanbinder;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;

/**
 * Bean binding in Vaadin 7 was renewed into much better form, but the thing I
 * do in varios apps for 95% of cases still requires four lines of boiler plate
 * code. 
 * 
 * TODO extract to separate add-on.
 */
public class BeanBinder {

	@SuppressWarnings("unchecked")
	public static FieldGroup bind(Object bean, Object fieldSource) {
		@SuppressWarnings("rawtypes")
		BeanItem beanItem = new BeanItem(bean);
		FieldGroup fieldGroup2 = new FieldGroup(beanItem);
		fieldGroup2.setBuffered(false);
		fieldGroup2.bindMemberFields(fieldSource);
		return fieldGroup2;
	}

}
