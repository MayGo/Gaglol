package ee.maix.gaglol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ee.maix.gaglol.domain.BaseEntity;
import ee.maix.gaglol.domain.Pic;
import ee.maix.gaglol.domain.PicPortal;
import ee.maix.gaglol.domain.PicPost;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Unit test for the Hibernate-based account manager implementation. Tests
 * application behavior to verify the Account Hibernate mapping is correct.
 */

public class HibernatePicRepositoryTests {

	private String driverClassName = "org.hsqldb.jdbcDriver";
	private String url = "jdbc:hsqldb:mem:pics";

	private HibernatePicRepository picRepository;
	private SessionFactory sessionFactory;

	private PlatformTransactionManager transactionManager;

	private TransactionStatus transactionStatus;

	@Before
	public void setUp() throws Exception {
		// setup the repository to test
		 sessionFactory = createTestSessionFactory();
		
		picRepository = new HibernatePicRepository(sessionFactory);
		// begin a transaction
		transactionManager = new HibernateTransactionManager(sessionFactory);
		transactionStatus = transactionManager
				.getTransaction(new DefaultTransactionDefinition());
		addData();
		sessionFactory.getCurrentSession().flush();
	}

	@Test
	public void testGetPic() {

		Pic pic = picRepository.getPicById(Long.valueOf(1));
		// assert the returned account contains what you expect given the state
		// of the database
		assertNotNull("pic should never be null", pic);
		assertEquals("wrong entity id", Long.valueOf(1), pic.getId());
		assertEquals("wrong hash", "hash1", pic.getHash());
		assertEquals("wrong picpost collection size", 1, pic.getPicPosts()
				.size());

		// PicPost p1 = pic.getPicPost(new PicPortal());
		// assertNotNull("There should be a picpost for that picportal", p1);

	}

	@Test
	public void testGetAllAccounts() {
		List<Pic> accounts = picRepository.getAllPics();
		assertEquals("Wrong number of accounts", 2, accounts.size());
	}
	// @Test
	public void testUpdatePic() {
		Pic oldPic = picRepository.getPicById(Long.valueOf(1));
		// oldPic.setName("Ben Hale");
		// picRepository.update(oldPic);
		// Pic newAccount = picRepository.getPic(Integer.valueOf(0));
		// assertEquals("Did not persist the name change", "Ben Hale",
		// newAccount.getName());
	}

	// @Test
	// public void testUpdateAccountBeneficiaries() {
	// Map<String, Percentage> allocationPercentages = new HashMap<String,
	// Percentage>();
	// allocationPercentages.put("Annabelle", Percentage.valueOf("25%"));
	// allocationPercentages.put("Corgan", Percentage.valueOf("75%"));
	// picRepository.updateBeneficiaryAllocationPercentages(Integer.valueOf(0),
	// allocationPercentages);
	// Account account = picRepository.getAccount(Integer.valueOf(0));
	// assertEquals("Invalid adjusted percentage", Percentage.valueOf("25%"),
	// account.getBeneficiary("Annabelle").getAllocationPercentage());
	// assertEquals("Invalid adjusted percentage", Percentage.valueOf("75%"),
	// account.getBeneficiary("Corgan").getAllocationPercentage());
	// }
	//
	// @Test
	// public void testAddBeneficiary() {
	// picRepository.addBeneficiary(Integer.valueOf(0), "Ben");
	// Account account = picRepository.getAccount(Integer.valueOf(0));
	// assertEquals("Should only have three beneficiaries", 3,
	// account.getBeneficiaries().size());
	// }
	//
	// @Test
	// public void testRemoveBeneficiary() {
	// Map<String, Percentage> allocationPercentages = new HashMap<String,
	// Percentage>();
	// allocationPercentages.put("Corgan", Percentage.oneHundred());
	// picRepository.removeBeneficiary(Integer.valueOf(0), "Annabelle",
	// allocationPercentages);
	// Account account = picRepository.getAccount(Integer.valueOf(0));
	// assertEquals("Should only have one beneficiary", 1,
	// account.getBeneficiaries().size());
	// assertEquals("Corgan should now have 100% allocation",
	// Percentage.oneHundred(), account
	// .getBeneficiary("Corgan").getAllocationPercentage());
	// }

	@After
	public void tearDown() throws Exception {
		// rollback the transaction to avoid corrupting other tests
		if (transactionManager != null)
			transactionManager.rollback(transactionStatus);
		
		sessionFactory.openSession().createSQLQuery( "SHUTDOWN" ).executeUpdate();
	}
	
	
	private void addData() throws MalformedURLException {
		Pic pic1=new Pic("hash1");
		Pic pic2=new Pic("hash2");
		PicPortal picPortal1=new PicPortal(new URL("http://9gag.com/"));
		pic1.addPicPost(new URL("http://9gag.com/gag/6433076"),new URL("http://d24w6bsrhbeh9d.cloudfront.net/photo/6371291_700b_v1.jpg"), "title1", picPortal1);
		pic2.addPicPost(new URL("http://9gag.com/gag/6433077"),new URL("http://d24w6bsrhbeh9d.cloudfront.net/photo/6379192_700b.jpg"), "title2", picPortal1);
		picRepository.savePic(pic1);
		picRepository.savePic(pic2);
	}
	
	
	private SessionFactory createTestSessionFactory() throws Exception {
		// create a FactoryBean to help create a Hibernate SessionFactory
		AnnotationSessionFactoryBean factoryBean = new AnnotationSessionFactoryBean();
		factoryBean.setHibernateProperties(createHibernateProperties());
		factoryBean.setDataSource(createTestDataSource());
		factoryBean.setPackagesToScan(new String[]{"ee.maix.gaglol.domain"});
		// initialize according to the Spring InitializingBean contract
		factoryBean.afterPropertiesSet();
		factoryBean.createDatabaseSchema();
		// get the created session factory
		return (SessionFactory) factoryBean.getObject();
	}

	private DataSource createTestDataSource() {
		BasicDataSource db = new BasicDataSource();
		db.setDriverClassName(driverClassName);
		db.setUrl(url);
		db.setUsername("sa");
		db.setPassword("");
		return db;
	}

	private Properties createHibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", " create-drop");
		properties.setProperty("hibernate.archive.autodetection", "class");
		properties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.HSQLDialect");
		properties.setProperty("hibernate.connection.driver_class",
				driverClassName);
		properties.setProperty("hibernate.connection.username", "sa");
		properties.setProperty("hibernate.connection.password", "");
		properties.setProperty("hibernate.connection.url", url);
		return properties;
	}
}
