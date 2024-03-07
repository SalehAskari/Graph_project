import java.util.ArrayList;
import java.util.HashMap;

public class MaxHeap {
    private ArrayList<Score> treeHeap;
    private int index;


    public MaxHeap() {
        treeHeap = new ArrayList<>();
        index = 0;


    }

    public void insert(int score , User enter_2){
        Score temp = new Score(score , enter_2);
        treeHeap.add(index , temp);
        if(index>0) upHeap();
        index++;
    }
    public User top(){
        if (index == 0) return null;
       return treeHeap.get(0).getUser();
    }
    public User pop() {
        if (index == 0) return null;
        index--;
        User out = treeHeap.get(0).getUser();
        treeHeap.set(0 ,treeHeap.get(index) );
        if(index > 1) downHeap();
        return out;
    }
public void print() {
    int numberOfLevels = (int) Math.floor(Math.log(index) / Math.log(2));
    int count = 0;

    for (int level = 0; level <= numberOfLevels; level++) {
        int levelSize = (int) Math.pow(2, level);
        int levelStartIndex = count;

        for (int i = 0; i < levelSize && levelStartIndex < index; i++) {
            System.out.print(treeHeap.get(levelStartIndex) + " ");
            levelStartIndex++;
            count++;
        }

        System.out.println();
    }
}
    private void upHeap(){
        int child = index;
        int father = (int) Math.ceil((double) child / 2) - 1;
            while ((child > 0) && (treeHeap.get(child).getScore() > treeHeap.get(father).getScore())){
                Score temp = treeHeap.get(father);
                treeHeap.set(father , treeHeap.get(child));
                treeHeap.set(child , temp);
                child = father;
                father = (int) Math.ceil((double) child / 2) - 1;
            }
        }
private void downHeap() {
    int father = 0;
    int leftChild = (2 * father) + 1;
    int rightChild = (2 * father) + 2;
    int child;

    while (leftChild < index) {
        if (rightChild < index && treeHeap.get(leftChild).getScore() < treeHeap.get(rightChild).getScore()) {
            child = rightChild;
        } else {
            child = leftChild;
        }

        if (treeHeap.get(father).getScore() >= treeHeap.get(child).getScore()) {
            break;
        }

        Score temp = treeHeap.get(father);
        treeHeap.set(father , treeHeap.get(child));
        treeHeap.set(child , temp);

        father = child;
        leftChild = (2 * father) + 1;
        rightChild = (2 * father) + 2;
    }
}
public boolean isEmpty(){
        if (index == 0){
            return true;
        }
        return false;
}
}
class Score{
    private int score;
    private User user;

    public Score(int score, User user) {
        this.score = score;
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
