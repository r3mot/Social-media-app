package social.Database.QueryStrings;

public class Insert {
    
    public static final String NEW_USER = "INSERT INTO USERS(USERNAME, PASSWORD, F_NAME, L_NAME, MAJOR, STANDING, YEAR, D_JOB, IMAGE) VALUES (?,?,?,?,?,?,?,?,?)";

    public static final String NEW_POST = "INSERT INTO POSTS(USERNAME, NAME, CONTENT, DATE, IMAGE) VALUES (?,?,?,?,?)";

    public static final String NEW_CLUB = "INSERT INTO CLUBS(EMAIL, PASSWORD, NAME, PURPOSE, CONTACT, WEBSITE, ICON) VALUES (?,?,?,?,?,?,?)";

    public static final String ADD_FRIEND = "INSERT INTO FRIENDS(CURRENT_USER, FRIEND_USERNAME, FRIEND_F_NAME, FRIEND_L_NAME, FRIEND_MAJOR, FRIEND_STANDING, FRIEND_YEAR, FRIEND_D_JOB, FRIEND_IMAGE) VALUES (?,?,?,?,?,?,?,?,?)";
    
    public static final String GET_FRIENDS = "SELECT FRIEND_USERNAME, FRIEND_F_NAME, FRIEND_L_NAME, FRIEND_MAJOR, FRIEND_STANDING, FRIEND_YEAR, FRIEND_D_JOB, FRIEND_IMAGE FROM FRIENDS WHERE CURRENT_USER=?";

    public static final String ADD_CLUB = "INSERT INTO FRIENDS(CURRENT_USER, FRIEND_EMAIL, FRIEND_NAME, FRIEND_PURPOSE, FRIEND_CONTACT, FRIEND_WEBSITE, FRIEND_ICON) VALUES (?,?,?,?,?,?)";

    public static final String GET_CLUBS = "SELECT FRIEND_EMAIL, FRIEND_NAME, FRIEND_PURPOSE, FRIEND_CONTACT, FRIEND_WEBSITE, FRIEND_ICON FROM FRIENDS WHERE CURRENT_USER=?";
}
