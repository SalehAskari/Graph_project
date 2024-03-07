import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Graph graph = new Graph();
        start(graph);
        User me = go(graph);
        boolean logOut = false;
        while (!logOut){
            System.out.print("[LOG IN: 0] | [SING IN: 1] | [ALL USER: 2] | [DELETE ACCOUNT: 3]" +
                    " | [OFFER TO FOLLOW: 4] | [SEARCH: 5] | [PROFILE: 6] | [FOLLOWERS: 7] | [FOLLOWING: 8] | [SHOW COMMENT'S: 9] | [SELECT USER: E ID]\n" +
                    "ANSWER: ");
            String enter = scanner.nextLine();
            String []split = enter.split(" ");
            if (split.length == 1){
                switch (Integer.parseInt(split[0])){
                    case 0:
                        me = logIn(graph);
                        break;
                    case 1:
                       me = singIn(graph);
                        break;
                    case 2:
                        System.out.println(graph.to_String());
                        break;
                    case 3:
                        if(graph.removeUser(me)){
                            System.out.println("DELETE -> [ID: "+me.getId() +" NAME: " + me.getName()+"]");
                            me = go(graph);
                        }
                        else {
                            System.out.println("NOT FIND!");
                        }

                        break;
                    case 4:
                        System.out.println(graph.getBestConnection(me));
                        break;
                    case 5:
                        System.out.print("PLEASE ENTER NAME OR ID: ");
                        String temp = scanner.nextLine();
                        User u;
                        if (Character.isDigit(temp.charAt(0))){
                            u = graph.searchByID(Integer.parseInt(temp));
                        }
                        else {
                            u = graph.searchByNAME(temp);
                        }
                        if (u != null){
                            System.out.println(graph.showProfile(u));
                        }
                        else {
                            System.out.println("NOT FIND!");
                        }
                        break;
                    case 6:
                        System.out.println(graph.showProfile(me));
                        break;
                    case 7:
                        System.out.println(graph.followers(me));
                        break;
                    case 8:
                        System.out.println(graph.following(me));
                        break;
                    case 9:
                        System.out.println(graph.getComment(me));
                        break;
                    default:
                        break;
                }
            }
            else if (split.length == 2) {
                User target = graph.getUser(Integer.parseInt(enter.substring(2)));
                int select = 1;
                boolean status = true;
                if (target == null){
                    System.out.println("NOT FIND!");
                    select = 0;
                    status = false;
                } else if (me.getId() == target.getId()) {
                    System.out.println("IT IS YOR ID!");
                    select = 0;
                    status = false;
                }
                else {
                    System.out.println(graph.showProfile(target));
                }
                while (select != 0) {
                    System.out.print("[BACK: 0] | [FOLLOW: 1] | [UNFOLLOW: 2] | [SEND COMMENT: 3] | [SHOW COMMENT'S: 4] | [FOLLOWERS: 5] | [FOLLOWING: 6]\nANSWER: ");
                    select = scanner.nextInt();
                    switch (select) {
                        case 0:
                            break;
                        case 1:
                            if (graph.addEdge(me, target)) {
                                System.out.println("FOLLOWED");
                            } else {
                                System.out.println("ALREADY FOLLOWED!");
                            }
                            break;
                        case 2:
                            if (graph.removeEdge(me, target)) {
                                System.out.println("UNFOLLOWED");
                            } else {
                                System.out.println("UNSUCCESSFUL!");
                            }
                            break;
                        case 3:
                            if (!me.getConnectionId().contains(target.getId())) {
                                System.out.println("YOU ARE NOT FOLLOWING THIS USER!");
                                break;
                            }
                            scanner.nextLine();
                            User receiver = target;
                            System.out.print("[ID: " + me.getId() + " NAME: " + me.getName() + "] -> [ID: " + receiver.getId() + " NAME: " + receiver.getName() + "]\nCOMMENT: ");
                            graph.comment(me, receiver, scanner.nextLine());
                            break;
                        case 4:
                            System.out.println(graph.getComment(target));
                            break;
                        case 5:
                            System.out.println(graph.followers(target));
                            break;
                        case 6:
                            System.out.println(graph.following(target));
                            break;
                        default:
                            System.out.println("NOT FIND [ID: " + target + "]");
                            break;
                    }
                }
                if (status) scanner.nextLine();
            }
        }
    }
    public static void start(Graph graph){
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("users(999).json")) {

            Type userListType = new TypeToken<List<User>>(){}.getType();
            List<User> userList = gson.fromJson(reader, userListType);

            for (User user : userList) {
               graph.addUser(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static User singIn(Graph graph){
        Scanner scanner = new Scanner(System.in);
        User newUser = new User();
        System.out.print("NAME: ");
        newUser.setName(scanner.nextLine());
        System.out.print("ID: ");
        int id = scanner.nextInt();
        while (graph.getUser(id) != null || id <= 0){
            System.out.print("ًٍREGISTERED OR ILLEGAL!\nID: ");
            id = scanner.nextInt();
        }
        newUser.setId(id);
        scanner.nextLine();
        System.out.print("DATE OF BIRTH: ");
        newUser.setDateOfBirth(scanner.nextLine());
        System.out.print("UNIVERSITY LOCATION: ");
        newUser.setUniversityLocation(scanner.nextLine());
        System.out.print("FIELD: ");
        newUser.setField(scanner.nextLine());
        System.out.print("WORK PLACE: ");
        newUser.setWorkplace(scanner.nextLine());
        System.out.print("SEPARATE YOUR SPECIALTIES WITH ',': ");
        String temp = scanner.nextLine();
        Set<String> stringSet = new HashSet<>();
        for (String s : temp.split(",")){
            stringSet.add(s);
        }
        newUser.setSpecialties(stringSet);
        System.out.println(graph.to_String());
        System.out.print("DO YOU WANT TO FOLLOW SOMEONE FROM THE ABOVE LIST?\n" +
                "IF YOU WANT, SEPARATE THEM WITH ',' AND OTHERWISE PRESS ENTER: ");
        temp = scanner.nextLine();
        Set<Integer> integerSet = new HashSet<>();
        for (String s : temp.split(",")){
            if(graph.getUser(Integer.parseInt(s)) == null){
                System.out.println("[ID: " + s + "]NOT FIND!");
            }else {
                integerSet.add(Integer.parseInt(s));
            }
        }
        newUser.setConnectionId(integerSet);
        if (graph.addUser(newUser)){
            System.out.println("SUCCESS!");
        }
        else {
            System.out.println("NO SUCCESS!");
            singIn(graph);
        }
        return newUser;
    }
    public static User logIn(Graph graph){
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID: ");
        User user = graph.searchByID(scanner.nextInt());
        if (user == null){
            System.out.println("NO FIND!");
            logIn(graph);
        }
        return user;
    }
    public static User go(Graph graph){
        Scanner scanner = new Scanner(System.in);
        User user = null;
        System.out.print("[LOG IN: 0] | [SING IN: 1]\nANSWER: ");
        int answer = scanner.nextInt();
        switch (answer){
            case 0:
                user = logIn(graph);
                break;
            case 1:
                user = singIn(graph);
                break;
            default:
                go(graph);
                break;
        }
        return user;
    }

}
