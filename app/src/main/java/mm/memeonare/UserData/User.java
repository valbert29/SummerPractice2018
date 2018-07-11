package mm.memeonare.UserData;

public class User {
    private static String nickName;


    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        User.nickName = nickName;
    }
}
