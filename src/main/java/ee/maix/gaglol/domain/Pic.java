package ee.maix.gaglol.domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Index;

@Entity
@Table(name="pic")
public class Pic extends BaseEntity {
	String hash;

	@OneToMany(mappedBy = "pic")

	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE,
	// region="blStandardElements")
	@BatchSize(size = 50)
	protected Set<PicPost> picPosts = new HashSet<PicPost>();

	@SuppressWarnings("unused")
	private Pic() {
		// Required by JPA
	}
	/**
	 * Creates a new pic, reflecting an pictures hash with list of posts that has that picture.
	 * @param hash Pictures  hash
	 */
	public Pic(String hash) {
		this.hash = hash;
	
	}
	
	/**
	 * Add a single picPost
	 * @param url of the post
	 * @imgUrl  url the url of the picpost(to the image itself) (should be unique)
	 * @param title the title of the picpost
	 * @param picPortal portal where picture is
	 */
	public void addPicPost(URL url, URL imgUrl, String title, PicPortal picPortal) {
		picPosts.add(new PicPost(url, imgUrl, title, picPortal, this));
	}
	/**
	 * Add a single picPost
	 * @param url the url of the picpost(to the image itself) (should be unique)
	 * @param imgUrl url of the post
	 * @param title the title of the picpost
	 * @param picPortal portal where picture is
	 */
	public void addPicPost(URL url, URL imgUrl, String title) {
		picPosts.add(new PicPost(url, imgUrl, title, this));
	}
	/**
	 * Returns a single pics picpost. Callers should not attempt to hold on or modify the returned object. This
	 * method should only be used transitively; for example, called to facilitate reporting or testing.
	 * @param name the name of the beneficiary e.g "Annabelle"
	 * @return the beneficiary object
	 */
	public PicPost getPicPost(PicPortal picPortal) {
		for (PicPost p : picPosts) {
			if (p.getPicPortal().equals(picPortal)) {
				return p;
			}
		}
		throw new IllegalArgumentException("No pics for this picPortal '" + picPortal.getPageUrl() + "'");
	}
	
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result
				+ ((picPosts == null) ? 0 : picPosts.size());
		//TODO:picPOsts.hashCode() replaced with size to prevent stackoverflow. Rethink
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
		Pic other = (Pic) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
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
		return "Pic [hash=" + hash + ", picPosts=" + picPosts + "]";
	}

}
