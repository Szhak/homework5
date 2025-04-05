package ProxyPattern;


public class RealImageUploader implements ImageUploader {
    @Override
    public void uploadImage(String filename) {
        System.out.println("Изображение загружено: " + filename);
    }
}

