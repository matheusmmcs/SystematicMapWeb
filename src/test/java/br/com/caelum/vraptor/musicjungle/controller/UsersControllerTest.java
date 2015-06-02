package br.com.caelum.vraptor.musicjungle.controller;


public class UsersControllerTest {
/*
	private MockResult result;
	private MockValidator validator;
	private UsersController controller;
	private User user;
	private User anotherUser;
	
	@Mock private UserDao userDao;
	@Mock private UserInfo info;
	@Mock private MapStudyDao musics;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		result = new MockResult();
		validator = new MockValidator();
		controller = new UsersController(userDao, result, validator, info, musics);
		user = new UserBuilder().withName("Renan").withLogin("renanigt").withPassword("1234").build();
		anotherUser = new UserBuilder().withName("Fulano").withLogin("fulano").withPassword("3456").build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldListAllUsers() {
		when(userDao.listAll()).thenReturn(Arrays.asList(user, anotherUser));
		controller.list();
		assertThat((List<User>)result.included().get("users"), contains(user, anotherUser));
	}
	
	@Test
	public void shouldAddUser() {
		controller.add(user);
		verify(userDao).add(user);
		assertThat(result.included().get("notice").toString(), is("User " + user.getName() + " successfully added"));
	}
	
	@Test
	public void shouldShowUser() {
		when(userDao.find(user.getLogin())).thenReturn(user);
		controller.show(user);
		assertThat((User) result.included().get("user"), is(user));
	}
	*/
}
