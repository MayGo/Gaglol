package ee.maix.gaglol;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ee.maix.gaglol.domain.Pic;

/**
 * Finds account objects using the Hibernate API.
 */
@Repository
@Transactional
public class HibernatePicRepository implements PicRepository {
	@Autowired
	private SessionFactory sessionFactory;

	public HibernatePicRepository(){
		
	}
	
	public Long savePic(Pic pic){
		return (Long) getCurrentSession().save(pic);
	}
	/**
	 * Creates an new hibernate-based account repository.
	 * 
	 * @param sessionFactory
	 *            the Hibernate session factory required to obtain sessions
	 */
	public HibernatePicRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public Pic findByHash(String hash) {
		Query query = getCurrentSession().createQuery(
				"select a from Pic a  where a.hash = ?");
		query.setString(0, hash);
		return (Pic) query.uniqueResult();
	}

	/**
	 * Returns the session associated with the ongoing reward transaction.
	 * 
	 * @return the transactional session
	 */
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Pic> getAllPics() {
		Query q = getCurrentSession().createQuery("from Pic");
		List<Pic> allPics = (List<Pic>) q.list();
		return allPics;
	}

	/**
	 * {@inheritDoc}
	 */
	public Pic getPicById(Long id) {
		return (Pic) getCurrentSession().get(Pic.class,id);
		 
	}
}