import java.util.*;

public class Main{


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String s = in.nextLine();
        List<Character> list = new ArrayList<>();

        char[] ss = s.toCharArray();

        for (char c : ss) {
            if (Character.isLetter(c)){
                list.add(c);
            }
        }

        Collections.sort(list, (o1, o2) -> Character.toLowerCase(o1) - Character.toLowerCase(o2));

        StringBuilder stringBuilder = new StringBuilder();
        int l = 0;
        for (int i = 0; i < ss.length; i++) {
            if (Character.isLetter(ss[i]) && l < list.size()) {
                stringBuilder.append(list.get(l));
                l ++;
            }else {
                stringBuilder.append(ss[i]);
            }
        }

        System.out.println(stringBuilder);
    }
}

