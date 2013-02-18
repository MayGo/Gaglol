package ee.maix.gaglol.domain;

import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;

@Entity
public class PicPortal extends BaseEntity {

	private URL pageUrl;
	
	@OneToMany(mappedBy = "picPortal", cascade = {CascadeType.ALL})
	@Cascade(value = {org.hibernate.annotations.CascadeType.ALL,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
	// region="blStandardElements")
	@BatchSize(size = 50)
	protected Set<PicPost> picPosts = new TreeSet<PicPost>();

	
	@SuppressWarnings("unused")
	private PicPortal() {
		// Required by JPA
	}
	public PicPortal(URL pageUrl){
		this.pageUrl=pageUrl;
	}
	
	public URL getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(URL pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Set<PicPost> getPicPosts() {
		return picPosts;
	}

	public void setPicPosts(Set<PicPost> picPosts) {
		this.picPosts = picPosts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pageUrl == null) ? 0 : pageUrl.hashCode());
		result = prime * result
				+ ((picPosts == null) ? 0 : picPosts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PicPortal other = (PicPortal) obj;
		if (pageUrl == null) {
			if (other.pageUrl != null)
				return false;
		} else if (!pageUrl.equals(other.pageUrl))
			return false;
		if (picPosts == null) {
			if (other.picPosts != null)
				return false;
		} else if (!picPosts.equals(other.picPosts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PicPortal [pageUrl=" + pageUrl + ", picPosts=" + picPosts + "]";
	}
}
