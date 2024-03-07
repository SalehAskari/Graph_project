import java.util.*;

public class Graph {
    int size;
    private HashMap<User, HashMap<User, ArrayList<String>>> user;

    public Graph() {
        user = new HashMap<>();
        size = 0;
    }

    public boolean addUser(User enter) {
        if (!user.containsKey(enter)) {
            size++;
            user.put(enter, new HashMap<>());
            for (User e : user.keySet()) {
                if (e.getConnectionId().contains(enter.getId())) {
                    addEdge(e, enter);
                }
                if (enter.getConnectionId().contains(e.getId())) {
                    addEdge(enter, e);
                }
            }
            return true;
        }
        return false;
    }

    public boolean addEdge(User enter_1, User enter_2) {
        if (user.containsKey(enter_1) && user.containsKey(enter_2)) {
            enter_1.getConnectionId().add(enter_2.getId());
            user.get(enter_1).put(enter_2,new ArrayList<>());
            return true;
        }
        return false;
    }

    public boolean removeUser(User target) {
        HashMap<User , ArrayList> neighbors = getNeighbors(target);
        for (User u : new ArrayList<>(neighbors.keySet())){
            removeEdge(target , u);
        }
        for (User u : user.keySet()){
            if (u.getConnectionId().remove(target.getId())){
                removeEdge(u , target);
            }
        }
        if(user.containsKey(target)){
            user.remove(target);
            return true;
        }
        return false;
    }
    public User searchByID(int id){
        return getUser(id);
    }
    public User searchByNAME(String name){
        return getUser(name);
    }
    public String showProfile(User target){
        String out = "[ID: " + target.getId()+ "]" +" {FLOWING: "+ numberFollowing(target) +"} {FOLLOWERS: " +numberFollowers(target)+"}"+
                "\n[NAME: " + target.getName()+
                "]\n[DATE OF BIRTH: " + target.getDateOfBirth()+
                "]\n[UNIVERSITY LOCATION: " + target.getUniversityLocation()+
                "]\n[FIELD: " + target.getField()+
                "]\n[WORK PLACE: " + target.getWorkplace()+
                "]\n[SPECIAL TIES: " + target.getSpecialties().toString()+
                "]\n[CONNECTION ID: " + target.getConnectionId().toString()+"]";
        return out;

    }


    public boolean removeEdge(User enter_1, User enter_2) {
        enter_1.getConnectionId().remove(enter_2.getId());
        if (getNeighbors(enter_1).containsKey(enter_2)){
            user.get(enter_1).remove(enter_2);
            return true;
        }
        return false;
    }

    public HashMap getNeighbors(User enter) {
        return user.get(enter);
    }

    private Queue getNeighbors(User target,User enter, Queue queue , Set seen) {
        HashMap<User , Integer> hashMap = getNeighbors(enter);
        for (User temp : hashMap.keySet()) {
            if (!temp.equals(target) && !seen.contains(temp.getId())){
            queue.add(temp);
            seen.add(temp.getId());
            }
        }
        return queue;
    }
    public String to_String() {
        String out = "ALL USER\n";
        for (User u : user.keySet()) {
            out += "[ID: " + u.getId() + "\tNAME: " + u.getName() +
                    "]\n{FOLLOWING: " + numberFollowing(u) + "\tFOLLOWERS: " + numberFollowers(u) +"}\n\n" ;
        }
        return out;
    }
    public String getBestConnection(User target) {
        Queue<User> queue = new LinkedList<>();
        MaxHeap maxHeap = new MaxHeap();
        Priority priority;
        Set<Integer> seen = new HashSet<>();

        queue = getNeighbors( target, target, queue , seen);
        while (!queue.isEmpty()) {
            User temp = queue.poll();
            int count = countEdges(target , temp);
            if (count <= 5) {
                queue = getNeighbors(target , temp, queue , seen);
            }
            priority = new Priority();
            priority.setDegree(count);
            priority.weight(target, temp);
            maxHeap.insert(priority.getScore(), temp);
        }
        while (!queue.isEmpty()){
            User temp = queue.poll();
            int count = countEdges(target , temp);
            priority = new Priority();
            priority.setDegree(count);
            priority.weight(target, temp);
            maxHeap.insert(priority.getScore(), temp);
        }
        String out = "";
        Set<Integer> set = new HashSet<>();
        User score = null;
        int theEnd = 20;
        for (int i = 0; i < theEnd && !maxHeap.isEmpty(); i++) {
            score = maxHeap.pop();
            if (set.add(score.getId())){
                out +="[ID: " + score.getId() + " NAME: " + score.getName() + "]\n";
            }
            else theEnd++;
        }
        return out;
    }
    public User getUser(int id) {
        for (User temp : user.keySet()) {
            if (temp.getId() == id) return temp;
        }
        return null;
    }
    public User getUser(String name) {
        for (User temp : user.keySet()) {
            if (temp.getName().equals(name)) return temp;
        }
        return null;
    }
    public int countEdges(User user1, User user2) {
        if (user.containsKey(user1) && user.containsKey(user2)) {
            Set<User> visited = new HashSet<>();
            return dfsCountEdges(user1, user2, visited);
        } else {
            return -1;
        }
    }

    private int dfsCountEdges(User current, User target, Set<User> visited) {
        if (current.equals(target)) {
            return 0;
        }
        visited.add(current);
        for (User neighbor : user.get(current).keySet()) {
            if (!visited.contains(neighbor)) {
                int count = dfsCountEdges(neighbor, target, visited);
                if (count >= 0) {
                    return count + 1;
                }
            }
        }
        return -1;
    }
    // user donbal mikonad
    public String following(User target){
        Set<User> neighbors = getNeighbors(target).keySet();
        String out = "FOLLOWING [ID: " + target.getId() + " NAME: " + target.getName()+ "]\n";
        for (User u : neighbors){
            out += "[ID: " + u.getId() + " NAME: " + u.getName() + "]\n";
        }
        return out;
    }
    // user ra donbal mikonand

    public String followers(User target){
        String out = "FOLLOWERS [ID: " + target.getId() + " NAME: " + target.getName()+ "]\n";
        for (User u : user.keySet()){
            if (getNeighbors(u).keySet().contains(target)){
                out += "[ID: " + u.getId() + " NAME: " + u.getName() + "]\n";
            }
        }
        return out;
    }
    public int numberFollowing(User target){
        return getNeighbors(target).keySet().size();
    }
    public int numberFollowers(User target){
        int count = 0;
        for (User u : user.keySet()){
            if (getNeighbors(u).keySet().contains(target)){
                count++;
            }
        }
        return count;
    }
    public void comment(User sender ,User receiver ,String comment){
        user.get(sender).get(receiver).add(comment);
    }
    public String getComment(User receiver){
        String out = "";
        for (User u : user.keySet()){
            if (u.getConnectionId().contains(receiver.getId())){
                for(String s : user.get(u).get(receiver)){
                    out += "[ID: " + u.getId() +"][NAME: " + u.getName()+"]COMMENT -> " + s+"\n";
                }
            }
        }
        return out;
    }
}
