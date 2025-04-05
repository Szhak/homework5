package ProxyPattern;

public class ProtectedImageUploader implements ImageUploader {
    private final RealImageUploader realUploader = new RealImageUploader();

    private boolean isAuthorized = false;

    public boolean login(String username, String password) {
        isAuthorized = username.equals("Zhandos") && password.equals("Crush");
        return isAuthorized;
    }

    @Override
    public void uploadImage(String filename) {
        if (isAuthorized) {
            realUploader.uploadImage(filename);
        } else {
            System.out.println("Ошибка: авторизуйтесь перед загрузкой изображения.");
        }
    }
}
