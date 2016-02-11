package UserAuthentication;

public class Login {

	public static void main(String[] args) {
		LoginHelper loginHelper = new LoginHelper();
		loginHelper.signUp("jordy", "1234");
		loginHelper.signUp("beena", "feb23");
		loginHelper.login("jordy", "123");
	}

}
