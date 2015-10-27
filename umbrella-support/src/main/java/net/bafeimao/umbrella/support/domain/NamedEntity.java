package net.bafeimao.umbrella.support.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class NamedEntity<K> extends BaseEntity<K> {
 	@XmlElement
	protected String name;

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
