package net.bafeimao.umbrella.support.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseEntity<K> {
	@XmlElement
	protected K id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}
}
