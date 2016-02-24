package UserAuthentication;

public class Login {

	public static void main(String[] args) {
		LoginHelper loginHelper = new LoginHelper();
		loginHelper.signUp("200106100", "1234");
		loginHelper.signUp("200107100", "feb23");
		loginHelper.login("jordy", "1234");
		
	}

}
