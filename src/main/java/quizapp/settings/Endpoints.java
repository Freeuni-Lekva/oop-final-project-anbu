package quizapp.settings;

public class Endpoints {

    /****************************** Endpoint Specification ********************************/
    public static final String LOGIN = "/auth/login";
    public static final String REGISTER = "/auth/register";
    public static final String LOGOUT = "/auth/logout";

    public static final String MESSAGE = "/secured/message";
    public static final String ADD_FRIEND = "/secured/addFriend";
    public static final String REJECT_FRIEND_REQUEST = "/secured/rejectfriendrequest";
    public static final String REMOVE_FRIEND = "/secured/removefriend";

    public static final String SEARCH = "/secured/search";
    public static final String USER = "/secured/user";

    public static final String HOMEPAGE = "/secured/homepage";
}
