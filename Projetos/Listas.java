import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Listas {
    public static void main(String[] args) {

       System.out.println("Lista com repetição (List)");
       List<String> list=new ArrayList<>(); 
       list.add("Cachorro");
       list.add("Cachorro");
       list.add("Gato");
       list.add("Gato");
       System.out.println(list);

       System.out.println("Lista sem repetição (Set)");
       Set<String> listSet=new HashSet<>();
       listSet.add("Cachorro");
       listSet.add("Cachorro");
       listSet.add("Gato");
       listSet.add("Gato");
       System.out.println(listSet);

       System.out.println("Lista (Map)");
       Map<Integer, String> listMap=new HashMap<>();
       listMap.put(1,"Cachorro");
       listMap.put(1,"Cachorro");
       listMap.put(2, "Gato");
       listMap.put(2, "Gato");
       System.out.println(listMap);
    }
  
}
