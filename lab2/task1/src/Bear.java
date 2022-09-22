import java.security.SecureRandom;

public class Bear {
    private final int X;
    private final int Y;

    public Bear(){
        SecureRandom random = new SecureRandom();
        this.X = random.nextInt(Constants.FIELD_SIZE);
        this.Y = random.nextInt(Constants.FIELD_SIZE);
    }

    public void setPosition(boolean[][] field){
        field[X][Y] = true;
    }
}
