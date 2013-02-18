package ee.maix.gaglol.domain;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PicPost extends BaseEntity {

	private URL url;
	private URL imgUrl;

	String title;

	@ManyToOne(optional = false)
	PicPortal picPortal;
	
	@ManyToOne(optional = false)
	//@JoinColumn(name = "CUSTOMER_ID")
	protected Pic pic;
	
	
	@SuppressWarnings("unused")
	private PicPost() {
		// Required by JPA
	}
	public PicPost(URL url, URL imgUrl, String title, Pic pic){
		this.url=url;
		this.imgUrl=imgUrl;
		this.title=title;
		
		try {
			this.picPortal=new PicPortal(new URL("http://"+url.getHost()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pic=pic;
	}
	public PicPost(URL url,  URL imgUrl, String title, PicPortal picPortal, Pic pic){
		this.url=url;
		this.imgUrl=imgUrl;
		this.title=title;
		this.picPortal=picPortal;
		this.pic=pic;
	}
	
	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
	public URL getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(URL url) {
		this.imgUrl = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PicPortal getPicPortal() {
		return picPortal;
	}

	public void setPicPortal(PicPortal picPortal) {
		this.picPortal = picPortal;
	}

	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
		result = prime * result
				+ ((picPortal == null) ? 0 : picPortal.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		PicPost other = (PicPost) obj;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		if (picPortal == null) {
			if (other.picPortal != null)
				return false;
		} else if (!picPortal.equals(other.picPortal))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PicPost [url=" + url + ", title=" + title + ", picPortal="
				+ picPortal + ", pic=" + pic + "]";
	}

}
