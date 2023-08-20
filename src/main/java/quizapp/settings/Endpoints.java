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

    public static final String HISTORY = "/secured/history";

    public static final String TAKE_QUIZ = "/secured/take_quiz";
    public static final String MAKE_QUIZ = "/secured/make_quiz";
    public static final String GRADE_QUIZ = "/secured/grade_quiz";
}
