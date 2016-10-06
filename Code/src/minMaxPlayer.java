import java.util.List;

public class minMaxPlayer implements Player {

    public minMaxPlayer(){

    }

    @Override
    public State play(State gameState) {
        MiniMax miniMax = new MiniMax();
        return miniMax.ids(gameState,4);
    }
    
}
